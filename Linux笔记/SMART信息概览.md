# SMART信息概览

- https://zhuanlan.zhihu.com/p/165947075
- https://www.jianshu.com/p/d5389994fad1

[toc]

# [Linux运维 -- 硬件]smartctl的使用

## 1. 是什么

常用的磁盘检查工具，smart（Self-Monitoring,Analysis and Reporting Technology）

## 2. 安装

### （1）ubuntu



```csharp
$ sudo apt-get install smartmontools
```

### （2）rhat & Centos



```ruby
$ yum install smartmontools
```

## 3. 使用

### （1） 看磁盘是否支持smartctl



```dart
$ sudo smartctl -i /dev/sda1 
smartctl 6.2 2013-07-26 r3841 [x86_64-linux-2.6.18-164.11.1.el5] (local build)
Copyright (C) 2002-13, Bruce Allen, Christian Franke, www.smartmontools.org

=== START OF INFORMATION SECTION ===
Model Family:     Seagate Constellation ES (SATA 6Gb/s)
Device Model:     ST1000NM0011
Serial Number:    Z1N0EVRZ
LU WWN Device Id: 5 000c50 03f123968
Firmware Version: SN02
User Capacity:    1,000,204,886,016 bytes [1.00 TB]
Sector Size:      512 bytes logical/physical
Rotation Rate:    7202 rpm
Device is:        In smartctl database [for details use: -P show]
ATA Version is:   ATA8-ACS T13/1699-D revision 4
SATA Version is:  SATA 3.0, 6.0 Gb/s (current: 3.0 Gb/s)
Local Time is:    Sun Aug 23 23:27:54 2015 CST
SMART support is: Available - device has SMART capability.          
SMART support is: Enabled
```

最后两行给出了是否支持smartctl

### （2）手动开启支持smartctl



```csharp
$ smartctl --smart=on --offlineauto=on --saveauto=on /dev/sda1
```

各个参数意思如下：

> -s VALUE, --smart=VALUE
>  Enable/disable SMART on device (on/off)
>
> -o VALUE, --offlineauto=VALUE (ATA)
>  Enable/disable automatic offline testing on device (on/off)
>
> -S VALUE, --saveauto=VALUE (ATA)
>  Enable/disable Attribute autosave on device (on/off)

### （3）检查磁盘的健康状况



```bash
$ sudo smartctl -H /dev/sda1 
smartctl 6.2 2013-07-26 r3841 [x86_64-linux-2.6.18-164.11.1.el5] (local build)
Copyright (C) 2002-13, Bruce Allen, Christian Franke, www.smartmontools.org

=== START OF READ SMART DATA SECTION ===
SMART overall-health self-assessment test result: PASSED
```

### （4）显示磁盘的属性值



```bash
$ sudo smartctl -A /dev/sdl1
smartctl 6.2 2013-07-26 r3841 [x86_64-linux-2.6.18-164.11.1.el5] (local build)
Copyright (C) 2002-13, Bruce Allen, Christian Franke, www.smartmontools.org

=== START OF READ SMART DATA SECTION ===
SMART Attributes Data Structure revision number: 10
Vendor Specific SMART Attributes with Thresholds:
ID# ATTRIBUTE_NAME          FLAG     VALUE WORST THRESH TYPE      UPDATED  WHEN_FAILED RAW_VALUE
  1 Raw_Read_Error_Rate     0x000f   084   063   044    Pre-fail  Always       -       238687534
  3 Spin_Up_Time            0x0003   099   099   000    Pre-fail  Always       -       0
  4 Start_Stop_Count        0x0032   100   100   020    Old_age   Always       -       3
  5 Reallocated_Sector_Ct   0x0033   100   100   036    Pre-fail  Always       -       0
  7 Seek_Error_Rate         0x000f   087   060   030    Pre-fail  Always       -       573183052
  9 Power_On_Hours          0x0032   063   063   000    Old_age   Always       -       33120
 10 Spin_Retry_Count        0x0013   100   100   097    Pre-fail  Always       -       0
 12 Power_Cycle_Count       0x0032   100   100   020    Old_age   Always       -       3
184 End-to-End_Error        0x0032   100   100   099    Old_age   Always       -       0
187 Reported_Uncorrect      0x0032   100   100   000    Old_age   Always       -       0
188 Command_Timeout         0x0032   100   100   000    Old_age   Always       -       0
189 High_Fly_Writes         0x003a   100   100   000    Old_age   Always       -       0
190 Airflow_Temperature_Cel 0x0022   075   049   045    Old_age   Always       -       25 (Min/Max 20/30)
191 G-Sense_Error_Rate      0x0032   100   100   000    Old_age   Always       -       0
192 Power-Off_Retract_Count 0x0032   100   100   000    Old_age   Always       -       1
193 Load_Cycle_Count        0x0032   100   100   000    Old_age   Always       -       567
194 Temperature_Celsius     0x0022   025   051   000    Old_age   Always       -       25 (0 20 0 0 0)
195 Hardware_ECC_Recovered  0x001a   120   099   000    Old_age   Always       -       238687534
197 Current_Pending_Sector  0x0012   100   100   000    Old_age   Always       -       2
198 Offline_Uncorrectable   0x0010   100   100   000    Old_age   Offline      -       2
199 UDMA_CRC_Error_Count    0x003e   200   200   000    Old_age   Always       -       0
```

基本上，SMART属性表列出了制造商在硬盘中定义好的属性值，以及这些属性相关的故障阈值。这个表由驱动固件自动生成和更新。

- VAULE字段：各厂商自定义内容，一般是表示该SMART项目当前的状态，比如作为一个百分数使用，100%表示最好。
- WORST字段：各厂商自定义内容，一般是记录该SMART项目历史最差的状态，参考价值不大。
- THRESH字段：当某个SMART项目需要有报警属性，FLAG的0BIT置1时，如果本字段内容非0，那么当SMARTCTL工具读取到VAULE的值小于THRESH时，会在WHEN_FAILED字段里面打印出FAILING_NOW字样表示本项目报警。如果WORST的值小于THRESH时，就会在这里打印出In_the_past字样表示本项目曾经报过警。
- TYPE字段：这个字段其实是从FLAG字段里面取出来的，如果FLAG的0BIT置1，那么这里就会显示Pre-fail，否则显示Old_age。
- UPDATED字段：这个字段也是从FLAG字段里面取出来的，如果FLAG的1BIT置1，那么这里就会显示Always，否则显示Offline。
- RAW_VALUE字段，这个是SMART信息采集出的原始值，每一个值都有不同的解析方法，比如有些项目是把这个字段当成一个单纯的数值来用，有些项目把这个字段的高位和低位分别当成其他的意义，如果同学们想要了解的话，那么就需要去查源代码来确定具体的含义。

### （5）测试磁盘

- short 测试



```bash
$ sudo smartctl -t short /dev/sda
smartctl 6.2 2013-07-26 r3841 [x86_64-linux-2.6.18-164.11.1.el5] (local build)
Copyright (C) 2002-13, Bruce Allen, Christian Franke, www.smartmontools.org

=== START OF OFFLINE IMMEDIATE AND SELF-TEST SECTION ===
Sending command: "Execute SMART Short self-test routine immediately in off-line mode".
Drive command "Execute SMART Short self-test routine immediately in off-line mode" successful.
Testing has begun.
Please wait 1 minutes for test to complete.
Test will complete after Mon Aug 24 00:01:22 2015

Use smartctl -X to abort test.
```

- long测试



```cpp
$ sudo smartctl -t long /dev/sda
```

- 看测试进度



```bash
$ sudo smartctl -l selftest /dev/sda
smartctl 6.2 2013-07-26 r3841 [x86_64-linux-2.6.18-164.11.1.el5] (local build)
Copyright (C) 2002-13, Bruce Allen, Christian Franke, www.smartmontools.org

=== START OF READ SMART DATA SECTION ===
SMART Self-test log structure revision number 1
Num  Test_Description    Status                  Remaining  LifeTime(hours)  LBA_of_first_error
# 1  Short offline       Completed without error       00%     33120         -
```

- 停止测试



```bash
$ sudo smartctl -X /dev/sda
smartctl 6.2 2013-07-26 r3841 [x86_64-linux-2.6.18-164.11.1.el5] (local build)
Copyright (C) 2002-13, Bruce Allen, Christian Franke, www.smartmontools.org

=== START OF OFFLINE IMMEDIATE AND SELF-TEST SECTION ===
Sending command: "Abort SMART off-line mode self-test routine".
Self-testing aborted!
```

