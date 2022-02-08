# dmesg-内核日志

- [Linux命令之dmesg命令_月生的静心苑-CSDN博客_dmesg](https://blog.csdn.net/carefree2005/article/details/120737841)
- [Linux内核I/O系统报错日志与硬盘故障对应关系 – Linux Kernel Exploration (ilinuxkernel.com)](http://ilinuxkernel.com/?p=386)



[toc]

## 一、命令简介

  Linux dmesg（英文全称：display message）命令用于显示开机信息。kernel 会将开机信息存储在 ring buffer 中。

  您若是开机时来不及查看信息，可利用 dmesg 来查看。开机信息亦保存在 /var/log 目录中，名称为 dmesg 的文件里。

### Linux内核I/O系统报错日志与硬盘故障对应关系

http://ilinuxkernel.com/?p=386

例：end_request: I/O error, dev sdi
```

end_request: I/O error, dev sdi

内核不能从硬盘上的文件系统读取数据。

（1）硬盘扇区坏。
（2）硬盘与磁盘控制器连接信号质量不稳定，导致数据出现异常。
```

## 二、使用示例

### 1、查看命令版本

```
❯ dmesg -V
dmesg from util-linux 2.31.1
```

### 2、获取命令帮助

```
❯ dmesg -help
用法：
dmesg [选项]

选项：
-C, --clear 清除内核环形缓冲区(ring butter)
-c, --read-clear 读取并清除所有消息
-D, --console-off 禁止向终端打印消息
-d, --show-delta 显示打印消息之间的时间差
-e, --reltime 以易读格式显示本地时间和时间差
```

### 3、查看所有开机日志信息

```
❯ dmesg
[    0.000000] Linux version 5.10.16.3-microsoft-standard-WSL2 (oe-user@oe-host) (x86_64-msft-linux-gcc (GCC) 9.3.0, GNU ld (GNU Binutils) 2.34.0.20200220) #1 SMP Fri Apr 2 22:23:49 UTC 2021
[    0.000000] Command line: initrd=\initrd.img panic=-1 nr_cpus=16 swiotlb=force pty.legacy_count=0
[    0.000000] KERNEL supported cpus:
[    0.000000]   Intel GenuineIntel
[    0.000000]   AMD AuthenticAMD
[    0.000000]   Centaur CentaurHauls
[    0.000000] x86/fpu: Supporting XSAVE feature 0x001: 'x87 floating point registers'
[    0.000000] x86/fpu: Supporting XSAVE feature 0x002: 'SSE registers'
[    0.000000] x86/fpu: Supporting XSAVE feature 0x004: 'AVX registers'
[    0.000000] x86/fpu: xstate_offset[2]:  576, xstate_sizes[2]:  256
```

### 4、过滤想查看信息

```
# https://www.cnblogs.com/chris-cp/p/6340289.html
# 当dmesg的时候，出现下面的信息说明磁盘有问题

Info fld=0x139066d0
end_request: I/O error, dev sda, sector 328230608
Buffer I/O error on device sda, logical block 41028826
sd 0:0:0:0: SCSI error: return code = 0x08000002
sda: Current: sense key: Medium Error
    Add. Sense: Unrecovered read error
 
Info fld=0x139066d0
end_request: I/O error, dev sda, sector 328230608
Buffer I/O error on device sda, logical block 41028826
sd 0:0:0:0: SCSI error: return code = 0x08000002
sda: Current: sense key: Medium Error
    Add. Sense: Unrecovered read error
```

```
#建议使用-i参数过滤时忽略大小写

❯ dmesg |grep -i cpu
[    0.000000] Command line: initrd=\initrd.img panic=-1 nr_cpus=16 swiotlb=force pty.legacy_count=0
[    0.000000] KERNEL supported cpus:
[    0.008447] smpboot: Allowing 16 CPUs, 0 hotplug CPUs
[    0.011749] setup_percpu: NR_CPUS:256 nr_cpumask_bits:256 nr_cpu_ids:16 nr_node_ids:1
[    0.012257] percpu: Embedded 52 pages/cpu s173272 r8192 d31528 u262144
[    0.012260] pcpu-alloc: s173272 r8192 d31528 u262144 alloc=1*2097152
[    0.012260] pcpu-alloc: [0] 00 01 02 03 04 05 06 07 [0] 08 09 10 11 12 13 14 15
[    0.012273] Kernel command line: initrd=\initrd.img panic=-1 nr_cpus=16 swiotlb=force pty.legacy_count=0
[    0.026454] SLUB: HWalign=64, Order=0-3, MinObjects=0, CPUs=16, Nodes=1
[    0.038813] rcu:     RCU restricting CPUs from NR_CPUS=256 to nr_cpu_ids=16.
[    0.038815] rcu: Adjusting geometry for rcu_fanout_leaf=16, nr_cpu_ids=16
[    0.040840] random: crng done (trusting CPU's manufacturer)
[    0.041256] x86/cpu: User Mode Instruction Prevention (UMIP) activated
[    0.041445] smpboot: CPU0: AMD Ryzen 7 5800H with Radeon Graphics (family: 0x19, model: 0x50, stepping: 0x0)
[    0.041776] smp: Bringing up secondary CPUs ...
[    0.041818] .... node  #0, CPUs:        #1  #2  #3  #4  #5  #6  #7  #8  #9 #10 #11 #12 #13 #14 #15
[    0.042136] smp: Brought up 1 node, 16 CPUs
[    0.053355] cpuidle: using governor menu

❯ dmesg |grep -i sda
[    0.310783] sd 0:0:0:0: [sda] 536870912 512-byte logical blocks: (275 GB/256 GiB)
[    0.310784] sd 0:0:0:0: [sda] 4096-byte physical blocks
[    0.310841] sd 0:0:0:0: [sda] Write Protect is off
[    0.310842] sd 0:0:0:0: [sda] Mode Sense: 0f 00 00 00
[    0.310954] sd 0:0:0:0: [sda] Write cache: enabled, read cache: enabled, doesn't support DPO or FUA
[    0.811538] sd 0:0:0:0: [sda] Attached SCSI disk
[    2.538873] EXT4-fs (sda): 7 orphan inodes deleted
[    2.538874] EXT4-fs (sda): recovery complete
[    2.544443] EXT4-fs (sda): mounted filesystem with ordered data mode. Opts: discard,errors=remount-ro,data=ordered

```

5、便于阅读的方式显示日志日期和时间

```
❯ dmesg -d -T |grep -i Memory
[Mon Feb  7 22:50:31 2022 <    0.000000>] Early memory node ranges
[Mon Feb  7 22:50:31 2022 <    0.012880>] Memory: 4094132K/8311420K available (16403K kernel code, 2459K rwdata, 3464K rodata, 1444K init, 1164K bss, 245632K reserved, 0K cma-reserved)
[Mon Feb  7 22:50:31 2022 <    0.000139>] Freeing SMP alternatives memory: 52K
[Mon Feb  7 22:50:31 2022 <    0.000000>] x86/mm: Memory block size: 128MB
[Mon Feb  7 22:50:31 2022 <    0.000102>] Freeing initrd memory: 60K
[Mon Feb  7 22:50:31 2022 <    0.000193>] Non-volatile memory driver v1.3
[Mon Feb  7 22:50:31 2022 <    0.000007>] hv_balloon: Using Dynamic Memory protocol version 2.0
[Mon Feb  7 22:50:31 2022 <    0.000013>] IPVS: Connection hash table configured (size=4096, memory=64Kbytes)
[Mon Feb  7 22:50:31 2022 <    0.000001>] hv_balloon: Cold memory discard hint enabled
[Mon Feb  7 22:50:31 2022 <    0.001404>] Freeing unused kernel image (initmem) memory: 1444K
[Mon Feb  7 22:50:31 2022 <    0.000812>] Freeing unused kernel image (text/rodata gap) memory: 2028K
[Mon Feb  7 22:50:31 2022 <    0.000238>] Freeing unused kernel image (rodata/data gap) memory: 632K
[Mon Feb  7 22:51:19 2022 <   46.006477>] hv_balloon: Max. dynamic memory size: 8118 MB
[Mon Feb  7 22:54:53 2022 <  213.111410>] cgroup: systemd (421) created nested cgroup for controller "memory" which has incomplete hierarchy support. Nested cgroups may change behavior in the future.
[Mon Feb  7 22:54:53 2022 <    0.000001>] cgroup: "memory" requires setting use_hierarchy to 1 on the root
```

6、实时监控查看日志末尾N行



```
#实时查看日志末尾10行
❯ watch "dmesg | tail -10"
```

7、查看指定级别格式日志

```
❯ dmesg -l warn
[    0.061159] PCI: System does not support PCI
                                               [    2.640390] WARNING: mount: waiting for virtio device...
                                                                                                          [    2.801508] WARNING: mount: waiting for virtio device...
                                                                                                                                                                     [  262.076913] cgroup: systemd (421) created nested cgroup for controller "memory" which has incomplete hierarchy support. Nested cgroups may change behavior in the future.
                                                                                                                                   [  262.076914] cgroup: "memory" requires setting use_hierarchy to 1 on the root
    [  262.115442] systemd-journald[46]: File /var/log/journal/c5517558d0354497a660c55872a23557/system.journal corrupted or uncleanly shut down, renaming and replacing.
                                                                                                                                                                        #      
```

支持的日志级别(优先级)：
emerg - 系统无法使用
alert - 操作必须立即执行
crit - 紧急条件
err - 错误条件
warn - 警告条件
notice - 正常但重要的条件
info - 信息
debug - 调试级别的消息

8、打印并清除内核环形缓冲区

```
# 清空dmesg日志
❯ dmesg -c

```

9、直接查看dmesg日志信息

```
❯ dmesg | more
```



## 三、使用语法及参数说明

1、使用语法

```
#dmesg [选项]
```

2、参数说明

```
参数选项	               参数说明

-C, --clear	           清除内核环形缓冲区(ring butter)
-c, --read-clear	   读取并清除所有消息
-D, --console-off	   禁止向终端打印消息
-d, --show-delta	   显示打印消息之间的时间差
-e, --reltime	       以易读格式显示本地时间和时间差
-E, --console-on	   启用向终端打印消息
-F, --file <文件>	   用 文件 代替内核日志缓冲区
-f, --facility <列表>  将输出限制为定义的设施
-H, --human	           易读格式输出
-k, --kernel	       显示内核消息
-L, --color	           显示彩色消息
-l, --level <列表>	   限制输出级别
-n, --console-level <级别>	设置打印到终端的消息级别
-P, --nopager	       不将输出通过管道传递给分页程序
-r, --raw	           打印原生消息缓冲区
-S, --syslog	       强制使用 syslog(2) 而非 /dev/kmsg
-s, --buffer-size <大小>	查询内核环形缓冲区所用的缓冲区大小
-T, --ctime	           显示易读的时间戳(如果您使用了SUSPEND/RESUME 则可能不准)
-t, --notime	       不打印消息时间戳
-u, --userspace	       显示用户空间消息
-w, --follow	       等待新消息
-x, --decode	       将设施和级别解码为可读的字符串
-h, --help	           显示此帮助并退出
-V, --version	       输出版本信息并退出
```

