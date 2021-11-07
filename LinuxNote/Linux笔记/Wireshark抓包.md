# Wireshark抓包，带你快速入门

https://www.cnblogs.com/zhaopei/p/12152139.html

[toc]

## 前言

关于抓包我们平时使用的最多的可能就是Chrome浏览器自带的Network面板了（浏览器上F12就会弹出来）。

另外还有一大部分人使用Fiddler，Fiddler也是一款非常优秀的抓包工具。

但是这两者只能对于HTTP和HTTPS进行抓包分析。如果想要对更底层的协议进行分析（如TCP的三次握手）就需要用到我们今天来说的工具Wireshark，同样是一款特牛逼的软件，且开源免费自带中文语言包。

## 安装和基本使用

Wireshark开源地址：https://github.com/wireshark/wireshark

Wireshark下载地址：https://www.wireshark.org/download

这里有它的历史版本。今天我们就来安装最新版本3.2.0，一路默认“下一步”安装大法就可以了。安装好后默认就是中文版。

### 开始抓包

![img](https://img2018.cnblogs.com/blog/208266/202001/208266-20200103104604251-1377382924.png)

### 显示过滤器

![img](https://img2018.cnblogs.com/blog/208266/202001/208266-20200103110632715-2057081165.png)
你会发现第一部分内容跳到非常快，根本没法找到自己想要分析的内容。这里我们可以使用显示过滤器，只显示我们想要看的内容。
在显示过滤器填入`http.request.method == "GET"`,然后用Chrome浏览器访问http://fanyi-pro.baidu.com/index（特意找的一个http网站）
![img](https://img2018.cnblogs.com/blog/208266/202001/208266-20200103140704080-1068182466.png)

除了过滤Get请求外，常用的显示过滤器还有：

- tcp、udp 前者表示只显示tcp，后者表示只显示udp。也可以!tcp，表示显示除了tcp之外的。还可以tcp or udp，表示显示tcp和udp。
- ip.src == 192.168.1.120 and ip.dst == 208.101.60.87 ，ip.src表示客户端ip（源地址ip）、ip.dst表示服务器ip（目标地址ip）
- tcp.port == 80 || udp.srcport == 80 ，tcp.port 表示tcp的端口为80，udp.srcport表示udp源端口为80。||表示或者和or等效，&&和and等效。（还有tcp.srcport、tcp.dstport等等）

### 捕获过滤器

显示过滤器是指捕获了所有经过网卡的封包，然后在显示的时候进行过滤显示。明显，如果流量过大会导致捕获的内容过多，筛选变得卡顿。所以，我们可以在捕获阶段的时候就过滤掉无用的流量。

- udp、tcp 前者表示只显示tcp，后者表示只显示udp。也可以!tcp，表示显示除了tcp之外的。还可以tcp or udp，表示显示tcp和udp。
- host 192.168.1.110 ,表示只捕获ip地址为192.168.1.110的封包（这里的语法和显示过滤器不一样，请注意）
- dst port 80 or port 443、not port 53，表达端口的过滤（这里的语法和显示过滤器不一样，请注意）
  ![img](https://img2018.cnblogs.com/blog/208266/202001/208266-20200103143400934-1554783886.png)

### 着色规则

我们看到第一部分内容，封包列表有各种不同的背景色。其不同颜色代表不同意义。淡蓝色代码udp协议，红字黑底代表有问题的封包。更多具体规则可 识图->着色规则
![img](https://img2018.cnblogs.com/blog/208266/202001/208266-20200103144200741-1661435758.png)

### 抓取localhost（环回地址）

localhost走的其实是npcap loopback adapter网卡（环回网卡），Wireshark抓包其实就是对网卡抓包。所以，Wireshark肯定是可以对localhost等环回地址进行抓包，只要捕获的时候选中网卡即可。
![img](https://img2018.cnblogs.com/blog/208266/202001/208266-20200107080901359-2072231286.png)

### 抓取移动设备流量

如果要抓取移动设备，可以先在笔记本电脑开启wifi热点。捕获对应的虚拟网卡，最后用手机连上wifi就可以进行抓包了。
win10自带了wifi热点，不用去找第三方的wifi热点软件了。如果手机连不上，可以尝试关掉笔记本的防火墙试试。
![img](https://img2018.cnblogs.com/blog/208266/202001/208266-20200107081212797-1589823091.png)

## TCP/IP四层协议

![img](https://img2018.cnblogs.com/blog/208266/202001/208266-20200103174503837-1341454628.png)
下面的动图是各层对应的数据
![img](https://img2018.cnblogs.com/blog/208266/202001/208266-20200103174941642-1425266745.gif)
从上面的动图我们可以发现，应用层到传输层再到网络层到以太网层，其对应的数据包也在对应的往前移。
我们可以想象一下，应用层数据往上传递，每经过一层就包上一个新得信封。等数据送到目的主机，然后每往下一层就拆一个信封，最后拆到应用层也就是最开始得数据了。
![img](https://img2018.cnblogs.com/blog/208266/202001/208266-20200104153052146-504489755.png)

## TCP三次握手

对于三次握手我想很多人只听过没见过，那么今天我们就来见见。
三次握手是过程： 1、客户端发送同步SYN标志位和序列号Seq(a) 2、服务器回复SYNACK、Seq(b)、Ack（a+1）3、客户端回复SYN、Seq(a+1)、Ack（b+1）
![img](https://img2018.cnblogs.com/blog/208266/202001/208266-20200104165827834-596000272.png)
那么在Wireshark中怎么观察呢。我们还是以`http://fanyi-pro.baidu.com/`地址为例。首先打开Chrome输入地址，F12打开浏览器的Network面板，刷新页面在面板中找到服务器IP。 打开Wireshark开始抓包，并在显示过滤器只显示IP地址对应的数据。
![img](https://img2018.cnblogs.com/blog/208266/202001/208266-20200104202428333-156570861.png)
![img](https://img2018.cnblogs.com/blog/208266/202001/208266-20200104203316667-296804111.png)
![img](https://img2018.cnblogs.com/blog/208266/202001/208266-20200104203605716-1443585935.png)

## TCP四次挥手

除了三次握手，还有对应的四次挥手。不知道是不是我网络不好，“挥手”的时候老是出现重传错误干扰（就是前面说的那种红字黑底封包）。下面是我本地环境自己写代码的抓包效果。
与握手不同是挥手是发送FIN标志位断开连接，其他都差不多。
![img](https://img2018.cnblogs.com/blog/208266/202001/208266-20200105104511852-1476737313.png)
Wireshark抓包如下
![img](https://img2018.cnblogs.com/blog/208266/202001/208266-20200105104659266-1789245957.png)

## HTTPS的抓包

因为HTTPS是HTTP的基础下加入SSL加密层，所以Wireshark抓到是密文。也就看不到请求参数和响应结果，甚至连url链接都是密文。
要想在Wireshark抓包明文数据，可进行如下操作：

- 1、windows电脑配置环境变量 SSLKEYLOGFILE D:\testssl.txt
  ![img](https://img2018.cnblogs.com/blog/208266/202001/208266-20200106182640317-1358797336.png)
- 2、Wireshark 编辑 - 首选项 - Protocols - TLS 最后一个选中D:\testssl.txt。
  ![img](https://img2018.cnblogs.com/blog/208266/202001/208266-20200106182753816-1104721539.png)

如果是HTTP2可以进行`http2.headers.method == "GET"`或`http2.headers.method == "POST"`，如果是HTTP可以进行`http.request.method == "GET"`过滤。

## UDP协议

Wireshark除了可以抓包TCP同样也可以对UDP进行抓包。
![img](https://img2018.cnblogs.com/blog/208266/202001/208266-20200104204528014-1341468019.png)
其实这个抓取的是BACnet报文，而这个BACnetIP正是基于UDP的一个协议。

## ModBusTcp协议

![img](https://img2018.cnblogs.com/blog/208266/202001/208266-20200104205313743-1103426576.png)

## 结束

授人以鱼不如授人以渔。Wireshark不仅可以对我们常见的HTTP、HTTPS、TCP等协议进行抓包分析，还能对工业上的BACnet、ModBus、S7Communication和其他PLC协议进行报文抓包分析。如[这位大佬](https://m.baidu.com/mip/c/www.360doc.cn/mip/763580999.html)就通过抓包破解了西门子PLC没有公开的协议。
希望有兴趣的朋友可以一起来完善[IoTClient组件](https://github.com/zhaopeiym/IoTClient)。