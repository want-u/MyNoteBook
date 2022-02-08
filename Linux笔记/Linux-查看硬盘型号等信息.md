#  Linux-查看硬盘型号等信息

[linux下查看硬盘型号等信息_petermis_51CTO博客](https://blog.51cto.com/petermis/959206)

[toc]

在准备替换或加装硬盘时，如何得到硬盘信息？
可以用smartctl,hdparm等命令来查询。

## 系统日志

在日志中显示硬盘有坏扇区

```
tail /var/log/messages

Mar 1 09:42:55 c1g smartd[1848]: Device: /dev/hda, 2 Currently unreadable (pending) sectors
Mar 1 09:42:55 c1g smartd[1848]: Device: /dev/hda, 2 Offline uncorrectable sectors
dmesg中也有错误信息
dmesg

ide: failed opcode was: unknown
hda: no DRQ after issuing WRITE
ide0: reset: success
hda: status timeout: status=0xd0 { Busy }
```



## 硬盘健康检查

对硬盘做一下健康检查

检测通过，保险起见还是准备换硬盘。

```
smartctl -H /dev/hda

smartctl version 5.33 [i386-redhat-linux-gnu] Copyright (C) 2002-4 Bruce Allen
Home page is http://smartmontools.sourceforge.net/

=== START OF READ SMART DATA SECTION ===
SMART overall-health self-assessment test result: PASSED
```

检测通过，保险起见还是准备换硬盘。

## 查看硬盘型号

### smartctl

查看下当前硬盘的型号,可以得到硬盘接口为SATAII，及尺寸大小3.5"

```
smartctl -a /dev/hda

smartctl version 5.33 [i386-redhat-linux-gnu] Copyright (C) 2002-4 Bruce Allen
Home page is http://smartmontools.sourceforge.net/

=== START OF INFORMATION SECTION ===
Device Model: ST3160815AS
Serial Number: 6RA7DWM4
Firmware Version: 4.AAB
User Capacity: 160,040,803,840 bytes
Device is: Not in smartctl database [for details use: -P showall]
ATA Version is: 7
ATA Standard is: Exact ATA specification draft version not indicated
Local Time is: Mon Mar 1 10:36:55 2010 CST
SMART support is: Available - device has SMART capability.
SMART support is: Enabled

=== START OF READ SMART DATA SECTION ===
SMART overall-health self-assessment test result: PASSED
```

### hdparm

hdparm也可以得到硬盘型号

```
hdparm -i /dev/hda

/dev/hda:

Model=ST3160815AS, FwRev=4.AAB, SerialNo=6RA7DWM4
Config={ HardSect NotMFM HdSw>15uSec Fixed DTR>10Mbs RotSpdTol>.5% }
RawCHS=16383/16/63, TrkSize=0, SectSize=0, ECCbytes=4
BuffType=unknown, BuffSize=8192kB, MaxMultSect=16, MultSect=off
CurCHS=16383/16/63, CurSects=16514064, LBA=yes, LBAsects=268435455
IORDY=on/off, tPIO={min:120,w/IORDY:120}, tDMA={min:120,rec:120}
PIO modes: pio0 pio1 pio2 pio3 pio4
DMA modes: mdma0 mdma1 mdma2
UDMA modes: udma0 udma1 udma2
AdvancedPM=no WriteCache=enabled
Drive conforms to: device does not report version:

* signifies the current active mode
```



###   dmidecode

也可以通过主板型号来得知支持的硬盘

```
dmidecode

System Information
Manufacturer: Gigabyte Technology Co., Ltd.
Product Name: 945GCM-S2L
```

```
/dev/hd~ ==> IDE
/dev/sd~ ==> SCSI、SATA、USB、FLASH
/dev/md~ ==> RAID

至于RAID大小要看系统及文件系统的支持了。 
```



## smartctl使用详解

功能:可以查看生产厂商、型号、序列号、容量、是否支持SMART、开启否
主页:http://smartmontoo ls.sourceforge.net/
Centos安装:

```
yum -y install smartmontools
```

```
smartctl
    -i 指定设备
    -d 指定设备类型,例如：ata, scsi, marvell, sat, 3ware,N
    -a 或A 显示所有信息
    -l 指定日志的类型，例如:TYPE: error, selftest, selective, directory,background, scttemp[sts,hist]
    -H 查看硬盘健康状态
    -t short 后台检测硬盘，消耗时间短
    -t long 后台检测硬盘，消耗时间长
    -C -t short 前台检测硬盘，消耗时间短
    -C -t long 前台检测硬盘，消耗时间长
    -X 中断后台检测硬盘
    -l selftest 显示硬盘检测日志
```

HP硬盘：

```
smartctl -s on -d  cciss,0 /dev/cciss/c0d0 开启SMART
smartctl  -a  -d cciss,0 -i /dev/cciss/c0d0  显示所有SMART信息
smartctl  -l error  -d cciss,0 -i /dev/cciss/c0d0 查看磁盘错误日志
```

+++++++++++++++++++++++smarted服务+++++++++++++++++

简单说下smartctl:smartd由kernel-utils包缺省安装。用 命令 rpm -ql kernel-utils 可以列出kernel-utils包中的文件
它是一个守护进程（一个帮助程序），它能监视拥有自我监视，分析和汇报技术(Self-Monitoring, Analysis, and Reporting Technology - SMART)的硬盘。

```
主配置文件：/etc/smartd.conf
添加一行/dev/cciss/c0d0 -H -m sys@5iqiong.com
```

上边的配置表示smartd以静默状态工作，当SMART中报告PASSED的时候不理睬一旦出现Failure，立刻用邮件通知用户指定的邮箱