# Linux-主机硬件检测脚本


```
############################################################################
#                                                                          #
#                               主机硬件检测                               #
#                                                                          #
############################################################################

>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>系统基本信息<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
系统时间:       Sat Feb  5 10:29:40 EST 2022
机器型号:       VMware Virtual Platform
系统名称:       CentOS Linux
系统版本:       7.6.1810(Core)
内核版本:       3.10.0-957.el7.x86_64
系统架构:       x86_64

..主机名:       localhost.localdomain
..IP地址:       192.168.10.11/24

.CPU型号:       AMD Ryzen 7 5800H with Radeon Graphics
.CPU核数:       4

.MEM信息:
      1         Size: 4096 MB

>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>资源使用情况<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
内存总量:       3771 Mb
已用内存:       111 Mb
可用内存:       3501 Mb
系统负载:       0.05, 0.01, 0.01
运行时间:       up 0 min, 1

-------------------------------dividing line--------------------------------
磁盘使用:
Filesystem              Type      Size  Used Avail Use% Mounted on
/dev/mapper/centos-root xfs        17G  1.6G   16G  10% /
devtmpfs                devtmpfs  1.9G     0  1.9G   0% /dev
tmpfs                   tmpfs     1.9G     0  1.9G   0% /dev/shm
tmpfs                   tmpfs     1.9G   12M  1.9G   1% /run
tmpfs                   tmpfs     1.9G     0  1.9G   0% /sys/fs/cgroup
/dev/sda1               xfs      1014M  146M  869M  15% /boot
tmpfs                   tmpfs     378M     0  378M   0% /run/user/0

磁盘分区:
NAME            MAJ:MIN RM  SIZE RO TYPE MOUNTPOINT
sda               8:0    0   20G  0 disk
├─sda1            8:1    0    1G  0 part /boot
└─sda2            8:2    0   19G  0 part
  ├─centos-root 253:0    0   17G  0 lvm  /
  └─centos-swap 253:1    0    2G  0 lvm  [SWAP]
sr0              11:0    1  4.3G  0 rom

磁盘序列:
[0:0:0:0]    disk    VMware,  VMware Virtual S 1.0   /dev/sda
[2:0:0:0]    cd/dvd  NECVMWar VMware IDE CDR10 1.00  /dev/sr0

```

---


```
#!/bin/bash
##Filename:     CentOS_Check_Script.sh
##Date:         2019-03-01
##Description:  Security detection script

echo "############################################################################"
echo "#                                                                          #"
echo "#                               主机硬件检测                               #"
echo "#                                                                          #"
echo "############################################################################"
echo ""
echo ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>系统基本信息<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<"

hostname=$(uname -n)
system=$(cat /etc/os-release | grep "^NAME" | awk -F\" '{print $2}')
version=$(cat /etc/redhat-release 2>&1 | awk '{print $4$5}')
kernel=$(uname -r)
platform=$(uname -p)
address=$(ip addr | grep inet | grep -v "inet6" | grep -v "127.0.0.1" | awk '{ print $2; }' | tr '\n' '\t' )
cpumodel=$(cat /proc/cpuinfo | grep name | cut -f2 -d: | sed 's/^[ \t]*//g'| uniq)
cpu=$(cat /proc/cpuinfo | grep 'processor' | sort | uniq | wc -l)
mem=$(dmidecode -t memory | egrep -v " Size|No Module Installed" | grep 'Size' | sort | uniq -c)
partinfo=$(lsblk)
machinemodel=$(dmidecode -s system-product-name)
date=$(date)

echo "系统时间:       $date"
echo "机器型号:       $machinemodel"
echo "系统名称:       $system"
echo "系统版本:       $version"
echo "内核版本:       $kernel"
echo "系统架构:       $platform"
echo ""
echo "..主机名:       $hostname"
echo "..IP地址:       $address"
echo ""
echo ".CPU型号:       $cpumodel"
echo ".CPU核数:       $cpu"
echo ""
echo ".MEM信息:       "
echo "$mem"
echo ""

echo ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>资源使用情况<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<"

summemory=$(free -m |grep "Mem:" | awk '{print $2}')
freememory=$(free -m |grep "Mem:" | awk '{print $4}')
usagememory=$(free -m |grep "Mem:" | awk '{print $3}')
uptime=$(uptime | awk '{print $2" "$3" "$4" "$5}' | sed 's/,$//g')
loadavg=$(uptime | awk '{print $9" "$10" "$11" "$12" "$13}')
dfinfo=$(df -Th)
scsi=$(lsscsi)

echo "内存总量:       $summemory Mb"
echo "已用内存:       $usagememory Mb"
echo "可用内存:       $freememory Mb"
echo "系统负载:       $loadavg"
echo "运行时间:       $uptime"
echo ""
echo "-------------------------------dividing line--------------------------------"
echo "磁盘使用:       "
echo "$dfinfo"
echo ""
echo "磁盘分区:       "
echo "$partinfo"
echo ""
echo "磁盘序列:       "
echo "$scsi"
echo ""
echo "-------------------------------dividing line--------------------------------"
echo  "路由信息:"
route -n
echo ""
echo "-------------------------------dividing line--------------------------------"
echo  "监听端口:"
netstat -tunlp
echo ""
echo "-------------------------------dividing line--------------------------------"
echo  "当前建立的连接:"
netstat -n | awk '/^tcp/ {++S[$NF]} END {for(a in S) print a, S[a]}'
echo ""
echo "-------------------------------dividing line--------------------------------"
echo "开机启动的服务:"
systemctl list-unit-files | grep enabled
echo ""
echo ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>系统用户情况<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<"
echo "活动用户:"
w | tail -n +2
echo ""
echo "-------------------------------dividing line--------------------------------"
echo "僵尸进程:"
ps -ef | grep zombie | grep -v grep
if [ "$?" -eq 1 ];then
    echo ">>>无僵尸进程"
else
    echo ">>>有僵尸进程------[需调整]"
fi
echo ""
echo "-------------------------------dividing line--------------------------------"
echo "耗CPU最多的进程:"
ps auxf |sort -nr -k 3 |head -5
echo ""
echo "-------------------------------dividing line--------------------------------"
echo "耗内存最多的进程:"
ps auxf |sort -nr -k 4 |head -5
echo ""
echo "-------------------------------dividing line--------------------------------"
echo  "环境变量:"
env
echo ""


```
