# Filebeat-实时日志监控最佳配置

 https://blog.csdn.net/neosmith/article/details/87784228
 
[toc]

## filebeat组件

在使用filebeat监控日志文件时，如果不会做任何配置的话你可能会发现一些奇怪的问题，即有时新的日志行会马上发送到目标输出地，有时候却要延迟近10s才会被发送。要解决这个问题，首先要明确filebeat中几个组件的作用和几个重要的参数的含意，如下：

- Input组件
   `input`组件负责监控日志文件(目录)自身的变化情况，如某文件被移动、删除了或创建了新文件，然后将这些信息提供给`Harvester`使用。
- Harvester组件
   `Harvester`用于监控一个日志文件内容的变化情况。如，是否新加了一行数据，是否读到了EOF等。
- Spooler组件
   `Spooler`用于将`Harvester`"收割"到的新事件(日志行)发送到指定目的地，如ES, Kafka, Logstash, File等。

明确了这几个核心组件的功能后就不难理解日志为什么会延迟，首先`Input`会以一定间隔扫描日志文件的变化情况，如果在间隔之间删除、创建了文件，那就不会被探测到; 其次`Harvester`在检测日志文件内容时也会有一定频率，而且如果读到EOF还会触发新的策略; 最后`Spooler`对于需要发送的事件也有一个缓存，如果设置不当可能会导致Spooler积攒一堆事件后才发送从而进一步加重日志延迟。

## filebeat参数

下面看一下影响日志实时性的几个核心参数：

### scan_frequency

这个参数用于控制`Input`多久扫描一次日志文件的变更情况，默认值为`10s`。

也就是说，如果此时你的日志框架对文件进行了分割，那么最多需要等待`10s` filebeat才会发现被分割出来的新文件，然后启动新`Harvester`来监控新文件的内容变化情况。

这个参数可以不做调整，因为日志分割是一个低频操作，而且一般都是在零点发生，这时候对日志的实时性没有这么高的要求。

### close_inactive

这个参数用于控制自日志文件内容没有发生变更开始等待多久就将文件关闭，同时退出对应的`harvester`，默认为`5m`。

如果`harvester`退出，则此期间文件内容的任何变化都不会被检测到，直到下一个`scan_frequency`到来时再重新打开文件并创建对应的`harvester`。

其实这个参数是跟你的日志更新频率息息相关的，如果你总是在`close_inactive`时间内更新日志文件，则不会有什么问题，但是如果你两条日志经常超出这个时间，那么就会导致下一条日志会被延迟检测，因为需要等待`scan_frequency`流逝以等待filebeat重新将文件打开。

### backoff

 这个可以说是与实时性相关最重要的参数了，它的意思是当`harvester`检测并“收割”到一行日志更新后再等待多久才继续检查是否有新的日志行更新，默认为`1s`。

也就是说，如果你一次写了两行日志，那么filebeat先会读取到第一行，然后再等`1s`才能读取下到一行。

### max_backoff 和 backoff_factor

 `max_backoff`意为两次扫描行等待时间的最大值，默认为`10s`。

这是啥意思呢？不是已经设了`backoff` = 1s了吗？这里就涉及到EOF的问题。当`harvester`读取到文件末尾时，它就不会再等`backoff`时间了，而是等待`backoff * backoff_factor`秒才进行下一次检测(backoff_factor默认为2)，直到达到`mac_backoff`为止。

也就是说，当读到EOF后，`harvester`会先等待2s再检测变化，如果没有新内容，则等待4s, 再没有新内容，等8s, 直到达到10s最大值为止。如果将`backoff_factor`设为1则意为关闭此特性。

这里如果对日志实时性要求较高，则最好把此参数设为1s, 或者将`mac_backoff`设为1s也能达到一样的效果。

### flush.timeout

 这个参数用来控制当事件队列中的最老的一条记录存在多少秒后就强制刷新队列，即将队列中所有事件全部发送出去，默认为1s。

这个值无需改动。

### tail_files

 这个参数用来控制当开始监控一个新文件时(新文件的判断标准是在registry中是否存有这个文件的offset信息)是否从文件末尾开始读取，默认为`false`。

此参数在第一次启动Filebeat时最好设成true, 因为如果不改的话，filebeat会把已经存在的一大坨日志文件全部发出去，可能会压垮下游。

但当filebeat是在重启时，最好设为false, 因为如果为true, 当日志文件切割时新切割出的文件有可能会因为这个参数导致filebeat丢掉第一行数据。

## filebeat配置

综上，将配置汇总后如下：

```css
close_inactive: 12h
backoff: 1s
max_backoff: 1s
backoff_factor: 1
flush.timeout: 1s
```

