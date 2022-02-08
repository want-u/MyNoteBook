# Linux-dmidecode命令

[Linux dmidecode命令](https://deepinout.com/linux-cmd/linux-hardware-management-related-cmd/linux-cmd-dmidecode.html)

[toc]

**Linux dmidecode命令**：DMI表解码器。

## Linux dmidecode命令 功能描述

使用dmidecode命令可以在Linux系统上获取有关硬件方面的信息，比如BIOS、系统、主板、CPU、内存及缓存等。
dmidecode的作用是将DMI数据库中的信息解码，以可读的文本方式显示。由于DMI信息可以人为修改，因此里面的信息不一定是系统准确的信息。
DMI是帮助收集计算机系统信息的管理系统，DMI信息的收集必须在严格遵照SMBIOS规范的前提下进行。SMBIOS是主板或系统制造者以标准格式显示产品管理信息所需遵循的统一规范。
DMI充当了管理工具和系统层之间接口的角色。它建立了标准的可管理系统，更加方便了计算机厂商和用户对系统的了解。DMI的主要组成部分是MIF数据库。这个数据库包括了所有有关计算机系统和配件的信息。通过DMI信息，用户可以获取序列号、计算机厂商、串口信息及其他系统配件信息。

## Linux dmidecode命令 语法

```shell
dmidecode [选项]
```

命令中各选项的含义如表所示。

![dmidecode命令选项含义](https://static.deepinout.com/deepinout/linux-cmd/20211010082557-1.jpeg)

SMBIOS规范定义了表所示的DMI类型。
![DMI类型](https://static.deepinout.com/deepinout/linux-cmd/20211010082557-2.jpeg)

有效的字符串关键字如表所示。
![有效的字符串关键字](https://static.deepinout.com/deepinout/linux-cmd/20211010082557-3.jpeg)

## Linux dmidecode命令 示例

**查看服务器硬件的详细信息**

```shell
[root@rhel ～]# dmidecode
```

**查看服务器型号**

```shell
[root@rhel ～]# dmidecode | grep 'Product Name'
  Product Name: PowerEdge R710
  Product Name: 0XDX06
```

**查看主板序列号**

```shell
[root@rhel ～]# dmidecode |grep 'Serial Number'
  Serial Number: HRRM33X
  Serial Number: ..CN1374011H00PY.
  Serial Number: HRRM33X
  Serial Number: Not Specified
  Serial Number: Not Specified
  Serial Number:
  Serial Number: 83673427
  Serial Number: 83673300
  Serial Number:
  Serial Number:
  Serial Number:
  Serial Number:
  Serial Number:
  Serial Number:
  Serial Number:
  Serial Number:
  Serial Number:
  Serial Number:
  Serial Number:
  Serial Number:
  Serial Number:
  Serial Number:
  Serial Number:
```



**查看系统序列号**

```shell
[root@rhel ～]# dmidecode -s system-serial-number
HRRM33X
```



**查看内存详细信息**

```shell
[root@rhel ～]# dmidecode -t memory
```



**查看OEM信息**

```shell
[root@rhel ～]# dmidecode -t 11
# dmidecode 2.10
SMBIOS 2.6 present.
Handle 0x0B00, DMI type 11, 5 bytes
OEM Strings
String 1: Dell System
String 2: 5[0000]
```



**查看服务器系统制造商**

```shell
[root@rhel ～]# dmidecode -s system-manufacturer
Dell Inc.
```



**查看服务器系统产品名称（服务器品牌型号）**

```shell
[root@rhel ～]# dmidecode -s system-product-name
PowerEdge R710
```



**查看内存插槽数和内存大小**

```shell
[root@rhel ～]# dmidecode|grep -P -A5 ''Memory Device'' |grep Size
  Size: No Module Installed
  Size: 2048 MB
  Size: 2048 MB
  Size: No Module Installed
  Size: No Module Installed
  Size: No Module Installed
  Size: No Module Installed
  Size: No Module Installed
  Size: No Module Installed
  Size: No Module Installed
  Size: No Module Installed
  Size: No Module Installed
  Size: No Module Installed
  Size: No Module Installed
  Size: No Module Installed
  Size: No Module Installed
  Size: No Module Installed
  Size: No Module Installed
```



**查看最大支持内存数**

```shell
[root@rhel ～]# dmidecode -t memory |grep ''Maximum Capacity''
  Maximum Capacity: 288 GB
```



**查看CPU详细信息**

```shell
[root@rhel ～]# dmidecode -t processor
# dmidecode 2.10
SMBIOS 2.6 present.
Handle 0x0400, DMI type 4, 40 bytes
Processor Information
  Socket Designation: CPU1
  Type: Central Processor
  Family: Xeon
  Manufacturer: Intel
  ID: C2 06 02 00 FF FB EB BF
  Signature: Type 0, Family 6, Model 44, Stepping 2
  Flags:
          FPU (Floating-point unit on-chip)
          VME (Virtual mode extension)
          DE (Debugging extension)
          PSE (Page size extension)
          TSC (Time stamp counter)
          MSR (Model specific registers)
          PAE (Physical address extension)
          MCE (Machine check exception)
          CX8 (CMPXCHG8 instruction supported)
          APIC (On-chip APIC hardware supported)
          SEP (Fast system call)
          MTRR (Memory type range registers)
          PGE (Page global enable)
          MCA (Machine check architecture)
          CMOV (Conditional move instruction supported)
          PAT (Page attribute table)
          PSE-36 (36-bit page size extension)
          CLFSH (CLFLUSH instruction supported)
          DS (Debug store)
          ACPI (ACPI supported)
          MMX (MMX technology supported)
          FXSR (Fast floating-point save and restore)
          SSE (Streaming SIMD extensions)
          SSE2 (Streaming SIMD extensions 2)
          SS (Self-snoop)
          HTT (Hyper-threading technology)
          TM (Thermal monitor supported)
          PBE (Pending break enabled)
    Version: Intel(R)Xeon(R)CPU     E5606 @2.13GHz
    Voltage: 1.2 V
    External Clock: 4800 MHz
    Max Speed: 3600 MHz
    Current Speed: 2133 MHz
    Status: Populated, Enabled
    Upgrade: Socket LGA1366
    L1 Cache Handle: 0x0700
    L2 Cache Handle: 0x0701
    L3 Cache Handle: 0x0702
    Serial Number: Not Specified
    Asset Tag: Not Specified
    Part Number: Not Specified
    Core Count: 4
    Core Enabled: 4
    Thread Count: 4
    Characteristics:
          64-bit capable
Handle 0x0401, DMI type 4, 40 bytes
Processor Information
    Socket Designation: CPU2
    Type: Central Processor
    Family: Unknown
    Manufacturer: Intel
    ID: 00 00 00 00 00 00 00 00
    Version: Not Specified
    Voltage: 1.2 V
    External Clock: Unknown
    Max Speed: 3600 MHz
    Current Speed: Unknown
    Status: Unpopulated
    Upgrade: Socket LGA1366
    L1 Cache Handle: 0x0703
    L2 Cache Handle: 0x0704
    L3 Cache Handle: 0x0705
    Serial Number: Not Specified
    Asset Tag: Not Specified
    Part Number: Not Specified
    Characteristics:
          64-bit capable
```



**查看CPU信息**

```shell
[root@rhel ～]# dmidecode | grep CPU
  Socket Designation: CPU1
  Version: Intel(R)Xeon(R)CPU              E5606        @2.13GHz
  Socket Designation: CPU2
          CPU.Socket.1
          CPU.Socket.2
```


```
[root@localhost ~]$ dmidecode                          # 打印所有硬件信息
[root@localhost ~]$ dmidecode -q                       # 打印所有硬件信息，比较简洁
[root@localhost ~]$ dmidecode | grep 'Product Name'    # 以过滤的方式来查看指定的硬件信息（查看服务器型号）
[root@localhost ~]$ dmidecode -t bios              # 查看BIOS相关的硬件信息
[root@localhost ~]$ dmidecode -t system            # 查看系统相关的硬件信息
[root@localhost ~]$ dmidecode -t baseboard         # 查看主板相关的硬件信息
[root@localhost ~]$ dmidecode -t chassis           # 查看机箱相关的硬件信息
[root@localhost ~]$ dmidecode -t processor         # 查看处理器相关的硬件信息
[root@localhost ~]$ dmidecode -t memory            # 查看内存相关的硬件信息
[root@localhost ~]$dmidecode |grep 'Serial Number'　　# 查看主板的序列号
[root@localhost ~]$dmidecode -s system-serial-number # 查看系统序列号
[root@localhost ~]$dmidecode -t 11                 # 查看OEM信息
```
