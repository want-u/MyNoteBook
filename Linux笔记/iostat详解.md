# iostat详解

https://www.cnblogs.com/ftl1012/p/iostat.html

[toc]

iostat是I/O statistics（输入/输出统计）的缩写，iostat工具将对系统的磁盘操作活动进行监视。它的特点是汇报磁盘活动统计情况，同时也会汇报出CPU使用情况。iostat也有一个弱点，就是它不能对某个进程进行深入分析，仅对系统的整体情况进行分析

### 常见命令展示 

iostat 安装

```
# iostat属于sysstat软件包。可以直接安装。
yum ``install` `sysstat
```

显示所有设备负载情况

```
iostat
```

[![image](https://images2018.cnblogs.com/blog/519608/201807/519608-20180714122505632-629235895.png)](https://images2018.cnblogs.com/blog/519608/201807/519608-20180714122505171-1370362743.png)

 

**说明：**

**cpu属性值说明：**

> %user：CPU处在用户模式下的时间百分比。
>
> %nice：CPU处在带NICE值的用户模式下的时间百分比。
>
> %system：CPU处在系统模式下的时间百分比。
>
> %iowait：CPU等待输入输出完成时间的百分比。
>
> %steal：管理程序维护另一个虚拟处理器时，虚拟CPU的无意识等待时间百分比。
>
> %idle：CPU空闲时间百分比。

**备注：**

> 如果%iowait的值过高，表示硬盘存在I/O瓶颈
>
> 如果%idle值高，表示CPU较空闲
>
> 如果%idle值高但系统响应慢时，可能是CPU等待分配内存，应加大内存容量。
>
> 如果%idle值持续低于10，表明CPU处理能力相对较低，系统中最需要解决的资源是CPU。

**cpu属性值说明:**

> tps：该设备每秒的传输次数
>
> kB_read/s：每秒从设备（drive expressed）读取的数据量；
>
> kB_wrtn/s：每秒向设备（drive expressed）写入的数据量；
>
> kB_read： 读取的总数据量；
>
> kB_wrtn：写入的总数量数据量；

定时显示所有信息

```
# 【每隔2秒刷新显示，且显示3次】``iostat 2 3
```

显示指定磁盘信息

```
iostat -d ``/dev/sda
```

显示tty和Cpu信息

```
iostat -t
```

以M为单位显示所有信息

```
iostat -m
```

查看设备使用率（%util）、响应时间（await）

```
# 【-d 显示磁盘使用情况，-x 显示详细信息】``# d: detail``iostat -d -x -k 1 1
```

[![image](https://images2018.cnblogs.com/blog/519608/201807/519608-20180714122506528-1524239825.png)](https://images2018.cnblogs.com/blog/519608/201807/519608-20180714122506039-1662749392.png)

**说明：**

> rrqm/s： 每秒进行 merge 的读操作数目.即 delta(rmerge)/s
>
> wrqm/s： 每秒进行 merge 的写操作数目.即 delta(wmerge)/s
>
> %util： 一秒中有百分之多少的时间用于 I/O
>
> 如果%util接近100%，说明产生的I/O请求太多，I/O系统已经满负荷
>
>   idle小于70% IO压力就较大了，一般读取速度有较多的wait。

查看cpu状态

```
iostat -c 1 1
```

[![image](https://images2018.cnblogs.com/blog/519608/201807/519608-20180714122507434-479168560.png)](https://images2018.cnblogs.com/blog/519608/201807/519608-20180714122506991-961212043.png)