# tsar-字段说明

- https://blog.csdn.net/chdhust/article/details/52270528

#### cpu

```
字段含义
user: 表示CPU执行用户进程的时间,通常期望用户空间CPU越高越好.
sys: 表示CPU在内核运行时间,系统CPU占用率高,表明系统某部分存在瓶颈.通常值越低越好.
wait: CPU在等待I/O操作完成所花费的时间.系统部应该花费大量时间来等待I/O操作,否则就说明I/O存在瓶颈.
hirq: 系统处理硬中断所花费的时间百分比
sirq: 系统处理软中断所花费的时间百分比
util: CPU总使用的时间百分比
nice: 系统调整进程优先级所花费的时间百分比
steal: 被强制等待（involuntary wait）虚拟CPU的时间,此时hypervisor在为另一个虚拟处理器服务
ncpu: CPU的总个数

采集方式
CPU的占用率计算,都是根据/proc/stat计数器文件而来,stat文件的内容基本格式是:

cpu  67793686 1353560 66172807 4167536491 2705057 0 195975 609768
cpu0 10529517 944309 11652564 835725059 2150687 0 74605 196726
cpu1 14380773 127146 13908869 832565666 150815 0 31780 108418
cpu是总的信息,cpu0,cpu1等是各个具体cpu的信息,共有8个值,单位是ticks,分别是

User time, 67793686 Nice time, 1353560 System time, 66172807 Idle time, 4167536491 Waiting time, 2705057 Hard Irq time, 0 SoftIRQ time, 195975 Steal time, 609768

CPU总时间=user+system+nice+idle+iowait+irq+softirq+Stl
各个状态的占用=状态的cpu时间％CPU总时间＊100%
比较特殊的是CPU总使用率的计算(util),目前的算法是:
util = 1 - idle - iowait - steal
```

#### mem

```
字段含义
free: 空闲的物理内存的大小
used: 已经使用的内存大小
buff: buff使用的内存大小,buffer is something that has yet to be "written" to disk.
cach: 操作系统会把经常访问的东西放在cache中加快执行速度,A cache is something that has been "read" from the disk and stored for later use
total: 系统总的内存大小
util: 内存使用率

采集方法
内存的计数器在/proc/meminfo,里面有一些关键项

    MemTotal:      7680000 kB
    MemFree:        815652 kB
    Buffers:       1004824 kB
    Cached:        4922556 kB
含义就不解释了,主要介绍一下内存使用率的计算算法:
util = (total - free - buff - cache) / total * 100%
```

#### load

```
字段含义
load1: 一分钟的系统平均负载
load5: 五分钟的系统平均负载
load15:十五分钟的系统平均负载
runq: 在采样时刻,运行队列的任务的数目,与/proc/stat的procs_running表示相同意思
plit: 在采样时刻,系统中活跃的任务的个数（不包括运行已经结束的任务）

采集方法
/proc/loadavg文件中保存的有负载相关的数据
0.00 0.01 0.00 1/271 23741
分别是1分钟负载,五分钟负载,十五分钟负载,运行进程／总进程 最大的pid
只需要采集前五个数据既可得到所有信息
注意:只有当系统负载除cpu核数>1的时候,系统负载较高
```

#### traffic

```
字段含义
bytin: 入口流量byte/s
bytout: 出口流量byte/s
pktin: 入口pkt/s
pktout: 出口pkt/s

采集方法
流量的计数器信息来自:/proc/net/dev

    face |bytes    packets errs drop fifo frame compressed multicast|bytes    packets errs drop fifo colls carrier compressed
    lo:1291647853895 811582000    0    0    0     0          0         0 1291647853895 811582000    0    0    0     0       0          0
    eth0:853633725380 1122575617    0    0    0     0          0         0 1254282827126 808083790    0    0    0     0       0          0
字段的含义第一行已经标示出来,每一行代表一个网卡,tsar主要采集的是出口和入口的bytes／packets
注意tsar只对以eth和em开头的网卡数据进行了采集,像lo这种网卡直接就忽略掉了,流量的单位是byte
```

#### tcp

```
字段含义
active:主动打开的tcp连接数目
pasive:被动打开的tcp连接数目
iseg: 收到的tcp报文数目
outseg:发出的tcp报文数目
EstRes:Number of resets that have occurred at ESTABLISHED
AtmpFa:Number of failed connection attempts
CurrEs:当前状态为ESTABLISHED的tcp连接数
retran:系统的重传率

采集方法
tcp的相关计数器文件是:/proc/net/snmp

    Tcp: RtoAlgorithm RtoMin RtoMax MaxConn ActiveOpens PassiveOpens AttemptFails EstabResets CurrEstab InSegs OutSegs RetransSegs InErrs OutRsts
    Tcp: 1 200 120000 -1 31702170 14416937 935062 772446 16 1846056224 1426620266 448823 0 5387732
我们主要关注其中的ActiveOpens/PassiveOpens/AttemptFails/EstabResets/CurrEstab/InSegs/OutSegs/RetransSegs
主要关注一下重传率的计算方式:
retran = (RetransSegs－last RetransSegs) ／ (OutSegs－last OutSegs) * 100%
```

#### udp

```
字段含义
idgm: 收到的udp报文数目
odgm: 发送的udp报文数目
noport:udp协议层接收到目的地址或目的端口不存在的数据包
idmerr:udp层接收到的无效数据包的个数

采集方法
UDP的数据来源文件和TCP一样,也是在/proc/net/snmp

    Udp: InDatagrams NoPorts InErrors OutDatagrams
    Udp: 31609577 10708119 0 159885874
```

#### io

```
字段含义
rrqms: The number of read requests merged per second that were issued to the device.
wrqms: The number of write requests merged per second that were issued to the device.
rs: The number of read requests that were issued to the device per second.
ws: The number of write requests that were issued to the device per second.
rsecs: The number of sectors read from the device per second.
wsecs: The number of sectors written to the device per second.
rqsize:The average size (in sectors) of the requests that were issued to the device.
qusize:The average queue length of the requests that were issued to the device.
await: The average time (in milliseconds) for I/O requests issued to the device to be served. This includes the time spent by the requests in queue and the time spent servicing them.
svctm: The average service time (in milliseconds) for I/O requests that were issued to the device.
util: Percentage of CPU time during which I/O requests were issued to the device (bandwidth utilization for the device).Device saturation occurs when this value is close to 100%.

采集方法
IO的计数器文件是:/proc/diskstats,比如:

    202    0 xvda 12645385 1235409 416827071 59607552 193111576 258112651 3679534806 657719704 0 37341324 717325100
    202    1 xvda1 421 2203 3081 9888 155 63 421 1404 0 2608 11292
每一行字段的含义是:

major: 主设备号
minor: 次设备号,设备号是用来区分磁盘的类型和厂家信息
name: 设备名称
rd_ios: 读完成次数,number of issued reads. This is the total number of reads completed successfully
rd_merges: 合并读完成次数,为了效率可能会合并相邻的读和写.从而两次4K的读在它最终被处理到磁盘上之前可能会变成一次8K的读,才被计数（和排队）,因此只有一次I/O操作
rd_sectors: 读扇区的次数,number of sectors read. This is the total number of sectors read successfully.
rd_ticks: 读花费的毫秒数,number of milliseconds spent reading. This is the total number of milliseconds spent by all reads
wr_ios: 写完成次数,number of writes completed. This is the total number of writes completed successfully
wr_merges: 合并写完成次数,number of writes merged Reads and writes which are adjacent to each other may be merged for efficiency. Thus two 4K reads may become one 8K read before it is ultimately handed to the disk, and so it will be counted (and queued) as only one I/O.
wr_sectors: 写扇区次数,number of sectors written. This is the total number of sectors written successfully
wr_ticks: 写花费的毫秒数,number of milliseconds spent writing. This is the total number of milliseconds spent by all writes.
cur_ios: 正在处理的输入/输出请求数,number of I/Os currently in progress. The only field that should go to zero. Incremented as requests are given to appropriate request_queue_t and decremented as they finish.
ticks: 输入/输出操作花费的毫秒数
aveq: 输入/输出操作花费的加权毫秒数
通过这些计数器可以算出来上面的每个字段的值

double n_ios = rd_ios + wr_ios;
double n_ticks = rd_ticks + wr_ticks;
double n_kbytes = (rd_sectors + wr_sectors) / 2;
st_array[0] = rd_merges / (inter * 1.0);
st_array[1] = wr_merges / (inter * 1.0);
st_array[2] = rd_ios / (inter * 1.0);
st_array[3] = wr_ios / (inter * 1.0);
st_array[4] = rd_sectors / (inter * 2.0);
st_array[5] = wr_sectors / (inter * 2.0);
st_array[6] = n_ios ? n_kbytes / n_ios : 0.0;
st_array[7] = aveq / (inter * 1000);
st_array[8] = n_ios ? n_ticks / n_ios : 0.0;
st_array[9] = n_ios ? ticks / n_ios : 0.0;
st_array[10] = ticks / (inter * 10.0); 
 
注意:

扇区一般都是512字节,因此有的地方除以2了 ws是指真正落到io设备上的写次数, wrqpms是指系统调用合并的写次数, 它们之间的大小关系没有可比性,因为不知道多少请求能够被合并,比如发起了100个read系统调用,每个读4K,假如这100个都是连续的读,由于硬盘通常允许最大的request为256KB,那么block层会把这100个读请求合并成2个request,一个256KB,另一个144KB,rrqpm/s为100,因为100个request都发生了合并,不管它最后合并成几个；r/s为2,因为最后的request数为2
```

#### paritition

```
字段含义
bfree: 分区空闲的字节
bused: 分区使用中的字节
btotl: 分区总的大小
util: 分区使用率

采集方法
首先通过/etc/mtab获取到分区信息,然后通过statfs访问该分区的信息,查询文件系统相关信息,包含:

    struct statfs {
    long f_type; 
    long f_bsiz
    e; 
    long f_blocks; 
    long f_bfree; 
    long f_bavail; 
    long f_files; 
    long f_ffree; 
    fsid_t f_fsid; 
    long f_namelen; 
    };
然后就可以计算出tsar需要的信息,分区的字节数＝块数＊块大小＝f_blocks * f_bsize
```

#### pcsw

```
字段含义
cswch: 进程切换次数
proc: 新建的进程数

采集方法
计数器在/proc/stat:

    ctxt 19873315174
    processes 296444211
分别代表进程切换次数,以及进程数
```

#### tcpx

```
字段含义
recvq sendq est twait fwait1 fwait2 lisq lising lisove cnest ndrop edrop rdrop pdrop kdrop
分别代表
tcprecvq tcpsendq tcpest tcptimewait tcpfinwait1 tcpfinwait2 tcplistenq tcplistenincq tcplistenover tcpnconnest tcpnconndrop tcpembdrop tcprexmitdrop tcppersistdrop tcpkadrop

采集方法
计数器来自:/proc/net/netstat /proc/net/snmp 里面用到的数据有:

    TcpExt: SyncookiesSent SyncookiesRecv SyncookiesFailed EmbryonicRsts PruneCalled RcvPruned OfoPruned OutOfWindowIcmps LockDroppedIcmps ArpFilter TW TWRecycled TWKilled PAWSPassive PAWSActive PAWSEstab DelayedACKs DelayedACKLocked DelayedACKLost ListenOverflows ListenDrops TCPPrequeued TCPDirectCopyFromBacklog TCPDirectCopyFromPrequeue TCPPrequeueDropped TCPHPHits TCPHPHitsToUser TCPPureAcks TCPHPAcks TCPRenoRecovery TCPSackRecovery TCPSACKReneging TCPFACKReorder TCPSACKReorder TCPRenoReorder TCPTSReorder TCPFullUndo TCPPartialUndo TCPDSACKUndo TCPLossUndo TCPLoss TCPLostRetransmit TCPRenoFailures TCPSackFailures TCPLossFailures TCPFastRetrans TCPForwardRetrans TCPSlowStartRetrans TCPTimeouts TCPRenoRecoveryFail TCPSackRecoveryFail TCPSchedulerFailed TCPRcvCollapsed TCPDSACKOldSent TCPDSACKOfoSent TCPDSACKRecv TCPDSACKOfoRecv TCPAbortOnSyn TCPAbortOnData TCPAbortOnClose TCPAbortOnMemory TCPAbortOnTimeout TCPAbortOnLinger TCPAbortFailed TCPMemoryPressures
    TcpExt: 0 0 0 80 539 0 0 0 0 0 3733709 51268 0 0 0 80 5583301 5966 104803 146887 146887 6500405 39465075 2562794034 0 689613557 2730596 540646233 234702206 0 44187 2066 94 240 0 114 293 1781 7221 60514 185158 2 2 3403 400 107505 5860 24813 174014 0 2966 7 168787 106151 40 32851 2 0 2180 9862 0 15999 0 0 0
具体字段找到并且获取即可
```

#### percpu ncpu

```
字段含义
字段含义等同cpu模块,只不过能够支持采集具体的每一个cpu的信息

采集方法
等同于cpu模块
```


##### pernic

```
字段含义
字段含义等同traffic模块,只不过能够支持采集具体的每一个网卡的信息

采集方法
等同于traffic模块
```
