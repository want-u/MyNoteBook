# Linux-查看硬件信息命令

- [Linux查看硬件信息命令 - cloudos - 博客园 (cnblogs.com)](https://www.cnblogs.com/cloudos/p/8416415.html)
- [Linux dmidecode命令 - 大熊子 - 博客园 (cnblogs.com)](https://www.cnblogs.com/yinfutao/p/10621313.html)

[toc]

## **一、查看服务器硬件信息**

（1）查看服务器型号、序列号

[root@Master ~]# dmidecode|grep "System Information" -A9|egrep "Manufacturer|Product|Serial"

**![img](https://images2017.cnblogs.com/blog/1121174/201802/1121174-20180205101645216-1465909505.png)**

 （2）查看主板型号

[root@Master ~]# dmidecode |grep -A16 "System Information$"
![img](https://images2017.cnblogs.com/blog/1121174/201802/1121174-20180205122329279-255997038.png)

 （3）查看BIOS信息



```
[root@Master ~]# dmidecode -t bios
# dmidecode 2.12
SMBIOS 2.8 present.

Handle 0x0008, DMI type 0, 24 bytes
BIOS Information
        Vendor: HP
        Version: U19
        Release Date: 12/27/2015
        Address: 0xF0000
        Runtime Size: 64 kB
        ROM Size: 16384 kB
        Characteristics:
                PCI is supported
                PNP is supported
                BIOS is upgradeable
                BIOS shadowing is allowed
                ESCD support is available
                Boot from CD is supported
                Selectable boot is supported
                EDD is supported
                5.25"/360 kB floppy services are supported (int 13h)
                5.25"/1.2 MB floppy services are supported (int 13h)
                3.5"/720 kB floppy services are supported (int 13h)
                Print screen service is supported (int 5h)
                8042 keyboard services are supported (int 9h)
                Serial services are supported (int 14h)
                Printer services are supported (int 17h)
                CGA/mono video services are supported (int 10h)
                ACPI is supported
                USB legacy is supported
                BIOS boot specification is supported
                Function key-initiated network boot is supported
                Targeted content distribution is supported
                UEFI is supported
        BIOS Revision: 2.0
        Firmware Revision: 2.40
```



 （4）查看内存槽及内存条



```
[root@Master ~]# dmidecode -t memory | head -45 | tail -23
Memory Device
        Array Handle: 0x000A
        Error Information Handle: Not Provided
        Total Width: 72 bits
        Data Width: 64 bits
        Size: 16384 MB
        Form Factor: DIMM
        Set: None
        Locator: PROC 1 DIMM 1
        Bank Locator: Not Specified
        Type: DDR4
        Type Detail: Synchronous Registered (Buffered)
        Speed: 2133 MHz
        Manufacturer: HP     
        Serial Number: Not Specified
        Asset Tag: Not Specified
        Part Number: 752369-081
        Rank: 2
        Configured Clock Speed: 2133 MHz
        Minimum Voltage:  1.2 V
        Maximum Voltage:  1.2 V
        Configured Voltage:  1.2 V

[root@Master ~]#
```



 （5）查看网卡信息



```
[root@Master ~]# dmesg | grep -i Ethernet
igb: Intel(R) Gigabit Ethernet Network Driver - version 5.3.0-k
igb 0000:04:00.0: Intel(R) Gigabit Ethernet Network Connection
igb 0000:04:00.1: Intel(R) Gigabit Ethernet Network Connection
igb 0000:04:00.2: Intel(R) Gigabit Ethernet Network Connection
igb 0000:04:00.3: Intel(R) Gigabit Ethernet Network Connection
igb 0000:02:00.0: Intel(R) Gigabit Ethernet Network Connection
igb 0000:02:00.1: Intel(R) Gigabit Ethernet Network Connection 
```



（6） 查看pci信息，即主板所有硬件槽信息



```
[root@Master ~]# lspci | head -10
00:00.0 Host bridge: Intel Corporation Xeon E7 v3/Xeon E5 v3/Core i7 DMI2 (rev 02)
00:01.0 PCI bridge: Intel Corporation Xeon E7 v3/Xeon E5 v3/Core i7 PCI Express Root Port 1 (rev 02)
00:01.1 PCI bridge: Intel Corporation Xeon E7 v3/Xeon E5 v3/Core i7 PCI Express Root Port 1 (rev 02)
00:02.0 PCI bridge: Intel Corporation Xeon E7 v3/Xeon E5 v3/Core i7 PCI Express Root Port 2 (rev 02)
00:02.1 PCI bridge: Intel Corporation Xeon E7 v3/Xeon E5 v3/Core i7 PCI Express Root Port 2 (rev 02)
00:02.2 PCI bridge: Intel Corporation Xeon E7 v3/Xeon E5 v3/Core i7 PCI Express Root Port 2 (rev 02)
00:02.3 PCI bridge: Intel Corporation Xeon E7 v3/Xeon E5 v3/Core i7 PCI Express Root Port 2 (rev 02)
00:03.0 PCI bridge: Intel Corporation Xeon E7 v3/Xeon E5 v3/Core i7 PCI Express Root Port 3 (rev 02)
00:03.1 PCI bridge: Intel Corporation Xeon E7 v3/Xeon E5 v3/Core i7 PCI Express Root Port 3 (rev 02)
00:03.2 PCI bridge: Intel Corporation Xeon E7 v3/Xeon E5 v3/Core i7 PCI Express Root Port 3 (rev 02)
```



 

## 二**、查看CPU信息

**

（1）查看cpu型号

```
[root@Master ~]# cat /proc/cpuinfo | grep name | cut -f2 -d: | uniq -c
     40  Intel(R) Xeon(R) CPU E5-2650 v3 @ 2.30GHz
```

（2）查看系统中实际物理CPU的数量（物理）

```
[root@Master ~]# grep 'physical id' /proc/cpuinfo | sort | uniq | wc -l
2
```

（3）系统中实际物理CPU的数量（核数）

```
[root@Master ~]# cat /proc/cpuinfo |grep 'processor'|wc -l
40
```

 （4）查看每个物理CPU中core的个数(即核数)

```
[root@Master ~]# cat /proc/cpuinfo |grep "cores"|uniq 
cpu cores       : 10
```

（5）查看CPU的主频

```
[root@Master ~]# cat /proc/cpuinfo |grep MHz|uniq 
cpu MHz         : 1200.000
cpu MHz         : 2300.000
cpu MHz         : 1200.000
```

（6）查看CPU的详细信息



```
[root@Master ~]# cat /proc/cpuinfo | head -20
processor       : 0                           //逻辑处理器的ID
vendor_id       : GenuineIntel
cpu family      : 6
model           : 63
model name      : Intel(R) Xeon(R) CPU E5-2650 v3 @ 2.30GHz   //CPU型号
stepping        : 2
microcode       : 54
cpu MHz         : 1200.000
cache size      : 25600 KB
physical id     : 0
siblings        : 20                //相同物理封装处理器中逻辑处理器数
core id         : 0
cpu cores       : 10               //相同物理封装处理器中的内核数
apicid          : 0
initial apicid  : 0
fpu             : yes
fpu_exception   : yes
cpuid level     : 15
wp              : yes
flags           : fpu vme de pse tsc msr pae mce cx8 apic sep mtrr pge mca cmov pat pse36 clflush dts acpi mmx fxsr sse sse2 ss ht tm pbe syscall nx pdpe1gb rdtscp lm constant_tsc arch_perfmon pebs bts rep_good xtopology nonstop_tsc aperfmperf pni pclmulqdq dtes64 monitor ds_cpl vmx smx est tm2 ssse3 fma cx16 xtpr pdcm pcid dca sse4_1 sse4_2 x2apic movbe popcnt tsc_deadline_timer aes xsave avx f16c rdrand lahf_lm abm ida arat epb xsaveopt pln pts dtherm tpr_shadow vnmi flexpriority ept vpid fsgsbase bmi1 avx2 smep bmi2 erms invpcid cqm cqm_llc cqm_occup_llc
```



 （7）查看CPU的相关信息



```
[root@Master ~]# lscpu
Architecture:          x86_64
CPU op-mode(s):        32-bit, 64-bit
Byte Order:            Little Endian
CPU(s):                40
On-line CPU(s) list:   0-39
Thread(s) per core:    2
Core(s) per socket:    10
Socket(s):             2
NUMA node(s):          2
Vendor ID:             GenuineIntel
CPU family:            6
Model:                 63
Model name:            Intel(R) Xeon(R) CPU E5-2650 v3 @ 2.30GHz
Stepping:              2
CPU MHz:               1200.000
BogoMIPS:              4594.43
Virtualization:        VT-x
L1d cache:             32K
L1i cache:             32K
L2 cache:              256K
L3 cache:              25600K
NUMA node0 CPU(s):     0-9,20-29
NUMA node1 CPU(s):     10-19,30-39
[root@Master ~]# 
```



 （8）查看cpu运行模式

```
[root@Master ~]# getconf LONG_BIT
64
```

 （9）查看cpu是否支持64bit

```
[root@Master ~]#  cat /proc/cpuinfo | grep flags | grep ' lm ' | wc -l
40
(结果大于0, 说明支持64bit计算. lm指long mode, 支持lm则是64bit)
```

 

## 三**、查看内存信息

**（1）查看内存硬件信息



```
root@Master ~]# dmidecode -t memory | head -45 | tail -24
Handle 0x000C, DMI type 17, 40 bytes
Memory Device
        Array Handle: 0x000A
        Error Information Handle: Not Provided
        Total Width: 72 bits
        Data Width: 64 bits
        Size: 16384 MB
        Form Factor: DIMM
        Set: None
        Locator: PROC 1 DIMM 1
        Bank Locator: Not Specified
        Type: DDR4
        Type Detail: Synchronous Registered (Buffered)
        Speed: 2133 MHz
        Manufacturer: HP     
        Serial Number: Not Specified
        Asset Tag: Not Specified
        Part Number: 752369-081
        Rank: 2
        Configured Clock Speed: 2133 MHz
        Minimum Voltage:  1.2 V
        Maximum Voltage:  1.2 V
        Configured Voltage:  1.2 V
```



 （2）最大支持多少内存

[root@Master ~]# dmidecode|grep -P 'Maximum\s+Capacity'
![img](https://images2017.cnblogs.com/blog/1121174/201802/1121174-20180205124358951-360746286.png)

 （3）Linux 查看内存的插槽数,已经使用多少插槽.每条内存多大：
![img](https://images2017.cnblogs.com/blog/1121174/201802/1121174-20180205124522935-887718013.png)

 （4）Linux 查看内存的频率：

[root@Master ~]# dmidecode|grep -A16 "Memory Device"|grep 'Speed'

![img](https://images2017.cnblogs.com/blog/1121174/201802/1121174-20180205124724295-289044696.png)

 （5）Linux 查看内存的详细信息：



```
[root@Master ~]# cat /proc/meminfo |head -20
MemTotal:       131897620 kB
MemFree:          414124 kB
Buffers:         1040560 kB
Cached:         111083180 kB
SwapCached:         1320 kB
Active:          7637912 kB
Inactive:       110607108 kB
Active(anon):    4406900 kB
Inactive(anon):  1714900 kB
Active(file):    3231012 kB
Inactive(file): 108892208 kB
Unevictable:    10878976 kB
Mlocked:         5521652 kB
SwapTotal:      16777212 kB
SwapFree:       16737536 kB
Dirty:               180 kB
Writeback:             0 kB
AnonPages:      16999324 kB
Mapped:           149020 kB
Shmem:               172 kB
```



（6）Linux 查看内存的使用情况

```
[root@Master ~]# free -m    // -b,-k,-m,-g show output in bytes, KB, MB, or GB
             total       used       free     shared    buffers     cached
Mem:        128806     123407       5398          0       1015     103474
-/+ buffers/cache:      18918     109888
Swap:        16383         38      16345
```

 

##  **四、查看硬盘信息

**（1）查看挂接的分区状态

[root@Master ~]# fdisk -l |grep Disk
![img](https://images2017.cnblogs.com/blog/1121174/201802/1121174-20180205130727873-76807497.png)

（2）查看硬盘和分区分布



```
[root@Master ~]# lsblk
NAME   MAJ:MIN RM   SIZE RO TYPE MOUNTPOINT
sda      8:0    0 279.4G  0 disk 
├─sda1   8:1    0    50M  0 part /boot
├─sda2   8:2    0   100G  0 part /
├─sda3   8:3    0    50G  0 part /home
├─sda4   8:4    0     1K  0 part 
├─sda5   8:5    0    16G  0 part [SWAP]
├─sda6   8:6    0  13.3G  0 part /tmp
└─sda7   8:7    0   100G  0 part /usr
sdb      8:16   0  83.7T  0 disk 
└─sdb1   8:17   0  83.7T  0 part /PureDisk
```



 （3）查看硬盘和分区的详细信息



```
[root@Master ~]# fdisk -l           

Disk /dev/sda: 300.0 GB, 299966445568 bytes
255 heads, 63 sectors/track, 36468 cylinders
Units = cylinders of 16065 * 512 = 8225280 bytes
Sector size (logical/physical): 512 bytes / 512 bytes
I/O size (minimum/optimal): 262144 bytes / 262144 bytes
Disk identifier: 0x000384d9

   Device Boot      Start         End      Blocks   Id  System
/dev/sda1   *           1           7       51200   83  Linux
Partition 1 does not end on cylinder boundary.
/dev/sda2               7       13061   104857600   83  Linux
/dev/sda3           13061       19588    52428800   83  Linux
/dev/sda4           19588       36469   135597056    5  Extended
/dev/sda5           19588       21677    16777216   82  Linux swap / Solaris
/dev/sda6           21677       23415    13962240   83  Linux
/dev/sda7           23416       36469   104854528   83  Linux

WARNING: GPT (GUID Partition Table) detected on '/dev/sdb'! The util fdisk doesn't support GPT. Use GNU Parted.


Disk /dev/sdb: 92017.3 GB, 92017310654464 bytes
255 heads, 63 sectors/track, 11187134 cylinders
Units = cylinders of 16065 * 512 = 8225280 bytes
Sector size (logical/physical): 512 bytes / 512 bytes
I/O size (minimum/optimal): 262144 bytes / 6029312 bytes
Disk identifier: 0x00000000

   Device Boot      Start         End      Blocks   Id  System
/dev/sdb1               1      267350  2147483647+  ee  GPT
Partition 1 does not start on physical sector boundary.
```



 （4）查看挂接的分区状态



```
[root@Master ~]# mount | column -t
/dev/sda2  on  /                         type  ext4         (rw)
proc       on  /proc                     type  proc         (rw)
sysfs      on  /sys                      type  sysfs        (rw)
devpts     on  /dev/pts                  type  devpts       (rw,gid=5,mode=620)
tmpfs      on  /dev/shm                  type  tmpfs        (rw)
/dev/sda1  on  /boot                     type  ext4         (rw)
/dev/sda3  on  /home                     type  ext4         (rw)
/dev/sda6  on  /tmp                      type  ext4         (rw)
/dev/sda7  on  /usr                      type  ext4         (rw)
/dev/sdb1  on  /PureDisk                 type  ext4         (rw)
none       on  /proc/sys/fs/binfmt_misc  type  binfmt_misc  (rw
```



 （5）查看挂接的分区状态

```
[root@Master ~]# swapon -s   
Filename                                Type            Size    Used    Priority
/dev/sda5                               partition       16777212        39676   -1
```

 （6）查看硬盘使用情况



```
[root@Master ~]# df -hT
Filesystem     Type   Size  Used Avail Use% Mounted on
/dev/sda2      ext4    99G  561M   93G   1% /
tmpfs          tmpfs   63G   76K   63G   1% /dev/shm
/dev/sda1      ext4    45M   38M  4.9M  89% /boot
/dev/sda3      ext4    50G   52M   47G   1% /home
/dev/sda6      ext4    13G   34M   13G   1% /tmp
/dev/sda7      ext4    99G   21G   73G  23% /usr
/dev/sdb1      ext4    84T   41T   40T  51% /PureDisk
```



 （7） 硬盘检测命令smartctl



```
[root@Master ~]# smartctl -a /dev/sda
smartctl 5.43 2012-06-30 r3573 [x86_64-linux-2.6.32-642.el6.x86_64] (local build)
Copyright (C) 2002-12 by Bruce Allen, http://smartmontools.sourceforge.net

Vendor:               HP      
Product:              LOGICAL VOLUME  
Revision:             3.56
User Capacity:        299,966,445,568 bytes [299 GB]
Logical block size:   512 bytes
Logical Unit id:      0x600508b1001cc8a1b9ec4dacc5ab35dc
Serial number:        PDNNK0BRH9U0AG  
Device type:          disk
Local Time is:        Mon Feb  5 13:13:33 2018 CST
Device supports SMART and is Enabled
Temperature Warning Disabled or Not Supported
SMART Health Status: OK

Error Counter logging not supported
Device does not support Self Test logging
```



 

## **五、查看网卡信息

**

（1)查看网卡硬件信息

[root@Master ~]# lspci | grep -i 'eth'
![img](https://images2017.cnblogs.com/blog/1121174/201802/1121174-20180205132316029-133877174.png)

  (2)查看系统的所有网络接口

```
[root@Master ~]# ifconfig -a
[root@Master ~]# ip link show
```

  (3)查看某个网络接口的详细信息，例如eth0的详细参数和指标



```
[root@Master ~]# ethtool eth0
Settings for eth0:
        Supported ports: [ FIBRE ]
        Supported link modes:   10000baseT/Full 
        Supported pause frame use: No
        Supports auto-negotiation: No
        Advertised link modes:  10000baseT/Full 
        Advertised pause frame use: No
        Advertised auto-negotiation: No
        Speed: 10000Mb/s
        Duplex: Full
        Port: FIBRE
        PHYAD: 0
        Transceiver: external
        Auto-negotiation: off
        Supports Wake-on: d
        Wake-on: d
        Current message level: 0x00000007 (7)
                               drv probe link
        Link detected: yes
```



  (4)查看所有网卡的链路状态



```
[root@Master ~]# for i in `seq 0 9`;do ethtool eth${i} | egrep 'eth|Link';done
Settings for eth0:
        Link detected: yes   
Settings for eth1:
        Link detected: yes
Settings for eth2:
        Link detected: no
Settings for eth3:
        Link detected: no
Settings for eth4:
        Link detected: no
Settings for eth5:
        Link detected: no
Settings for eth6:
        Link detected: no
Settings for eth7:
        Link detected: no
Settings for eth8:
        Link detected: no
Settings for eth9:
        Link detected: no
```



 

##  **六、列出所有PCI设备信息

**



```
[root@Master ~]# lspci -tv | more
-+-[0000:ff]-+-08.0  Intel Corporation Xeon E7 v3/Xeon E5 v3/Core i7 QPI Link 0
 |           +-08.3  Intel Corporation Xeon E7 v3/Xeon E5 v3/Core i7 QPI Link 0
 |           +-09.0  Intel Corporation Xeon E7 v3/Xeon E5 v3/Core i7 QPI Link 1
 |           +-09.3  Intel Corporation Xeon E7 v3/Xeon E5 v3/Core i7 QPI Link 1
 |           +-0b.0  Intel Corporation Xeon E7 v3/Xeon E5 v3/Core i7 R3 QPI Link 0 & 1 Monitoring
 |           +-0b.1  Intel Corporation Xeon E7 v3/Xeon E5 v3/Core i7 R3 QPI Link 0 & 1 Monitoring
 |           +-0b.2  Intel Corporation Xeon E7 v3/Xeon E5 v3/Core i7 R3 QPI Link 0 & 1 Monitoring
 |           +-0c.0  Intel Corporation Xeon E7 v3/Xeon E5 v3/Core i7 Unicast Registers
 |           +-0c.1  Intel Corporation Xeon E7 v3/Xeon E5 v3/Core i7 Unicast Registers
 |           +-0c.2  Intel Corporation Xeon E7 v3/Xeon E5 v3/Core i7 Unicast Registers
 |           +-0c.3  Intel Corporation Xeon E7 v3/Xeon E5 v3/Core i7 Unicast Registers
 |           +-0c.4  Intel Corporation Xeon E7 v3/Xeon E5 v3/Core i7 Unicast Registers
 |           +-0c.5  Intel Corporation Xeon E7 v3/Xeon E5 v3/Core i7 Unicast Registers
 |           +-0c.6  Intel Corporation Xeon E7 v3/Xeon E5 v3/Core i7 Unicast Registers
 |           +-0c.7  Intel Corporation Xeon E7 v3/Xeon E5 v3/Core i7 Unicast Registers
 |           +-0d.0  Intel Corporation Xeon E7 v3/Xeon E5 v3/Core i7 Unicast Registers
 |           +-0d.1  Intel Corporation Xeon E7 v3/Xeon E5 v3/Core i7 Unicast Registers
 |           +-0f.0  Intel Corporation Xeon E7 v3/Xeon E5 v3/Core i7 Buffered Ring Agent
 |           +-0f.1  Intel Corporation Xeon E7 v3/Xeon E5 v3/Core i7 Buffered Ring Agent
 |           +-0f.2  Intel Corporation Xeon E7 v3/Xeon E5 v3/Core i7 Buffered Ring Agent
 |           +-0f.3  Intel Corporation Xeon E7 v3/Xeon E5 v3/Core i7 Buffered Ring Agent
```

