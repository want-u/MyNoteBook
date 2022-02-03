# Linux 配置ip 子接口 多网卡绑定

https://blog.csdn.net/zonghua521/article/details/78198001

[toc]

```
# My Note
ifconfig ens33:1 10.0.0.2 netmask 255.255.255.0  
```


[linux](https://so.csdn.net/so/search?from=pc_blog_highlight&q=linux)系统配置ip地址，图形化界面略过，这里只介绍文本行。做以下设置注意是否有此权限



```
查看当前路由及网关信息:
[root@localhost ~]# netstat -r
Kernel IP routing table
Destination     Gateway         Genmask         Flags   MSS Window  irtt Iface
192.168.1.0     *               255.255.255.0   U         0 0          0 eth0
169.254.0.0     *               255.255.0.0     U         0 0          0 eth0
default         192.168.1.1     0.0.0.0         UG        0 0          0 eth0
 
查看物理网卡信息：
[root@iyunshusdk ~]# mii-tool [eth0]
eth0: negotiated 100baseTx-FD, link ok
```


```
ethtool eth0  查看网卡物理信息

ethtool -i eth0 查看网卡驱动信息

ethtool -S eth0 查看网卡工作信息
```




## ifconfig命令

```
查看网卡接口信息，默认列出所有接口
[root@localhost ~]# ifconfig

如果想知道主机所有网络接口的情况，请用下面的命令：
[root@localhost ~]# ifconfig -a
   
如果想查看某个端口，比如查看eth0 的状态，就可以用下面的方法：
[root@localhost ~]# ifconfig eth0
    
临时配置网卡信息，无需重启（重启即消失）
     
   
```


```
ifconfig 网络端口 IP地址 hw <HW> MAC地址 netmask 掩码地址 broadcast 广播地址 [up/down] 

[root@localhost ~]# **ifconfig eth0 10.1.1.10 netmask 255.0.0.0**
```




```
临时配置网关　（重启即消失）　
[root@localhost ~]# route add default gw 192.168.0.254

 
网络接口配置文件 （黄色背景最好都设置）：
[root@localhost ~]# cat /etc/sysconfig/network-scripts/ifcfg-eth0
# Intel Corporation 82545EM Gigabit Ethernet Controller (Copper)
TYPE=Ethernet       #网卡类型
DEVICE=eth0         #网卡接口名称
ONBOOT=yes          #系统启动时是否自动加载
BOOTPROTO=static    #启用地址协议 --static:静态协议 --bootp协议 --dhcp协议
IPADDR=192.168.1.11      #网卡IP地址
NETMASK=255.255.255.0    #网卡网络地址
GATEWAY=192.168.1.1      #网卡网关地址
DNS1=10.203.104.41       #网卡DNS地址
HWADDR=00:0C:29:13:5D:74 #网卡设备MAC地址
BROADCAST=192.168.1.255  #网卡广播地址 
 


```
## 添加子接口（虚拟接口）

需要禁用NetworkManager 服务

```
service NetworkManager stop    #停止服务
chkconfig NetworkManager off    #禁用服务，下次不自动启动
```

临时: 

```
ifconfig eth0:1 10.0.0.2 netmask 255.255.255.0  
或者 ip addr add 10.0.0.2/24 dev eth0 label eth0:0
```



永久：
```
在/etc/syscofig/network-scripts/ 下新建文件
ifcfg-eth0:1
增加内容
DEVICE=eth0:1         #网卡接口名称
ONBOOT=yes          #系统启动时是否自动加载
BOOTPROTO=static    #启用地址协议 --static:静态协议 --bootp协议 --dhcp协议
IPADDR=192.168.1.11      #网卡IP地址
NETMASK=255.255.255.0   (PREFIX=24)  #网卡网络地址
GATEWAY=192.168.1.1      #网卡网关地址
ONPARENT=YES             存在依附关系 
 
   注意：真正的接口标识，不是ifcfg-xxx的文件名，而是ifcfg-eth0中的DEVICE项目,network文件就是把/etc/sysconfig/network-script目录下的所有文件都遍历一便，凡是ONBOOT=yes的都enable ，并且enable 到DEVICE=eth0这个名字上
 
```
## 网络服务启动与关闭

```
方法一:
[root@localhost ~]# service network stop    #关闭网络服务
[root@localhost ~]# service network start   #启动网络服务
[root@localhost ~]# service network restart #重启网络服务
方法二：
[root@localhost ~]# /etc/init.d/network stop
[root@localhost ~]# /etc/init.d/network start
[root@localhost ~]# /etc/init.d/network restart 
网卡状态查询
[root@localhost ~]# service network status
Configured devices:
lo eth0
Currently active devices:
lo eth0
[root@localhost ~]# /etc/init.d/network status

```
## 多网卡绑定
linux有七种网卡绑定模式：
- round robin  平衡轮询
- active-backup  主备模式
- load balancing (xor)，  广播
- fault-tolerance (broadcast)
- lacp
- transmit load balancing
- adaptive load balancing

mode=1（active-backup）：
一个网卡处于活动状态 ，一个处于备份状态，所有流量都在主链路上处理。当活动网卡down掉时，启用备份的网卡。


首先在/etc/sysconfig/network-scripts/下创建虚拟网卡bond0的配置文件 **ifcfg-bond0** ，内容如下

```

DEVICE=bond0
BOOTPROTO=none
IPADDR=192.168.168.1
NETMASK=255.255.255.0
ONBOOT=yes
TYPE=Ethernet
GATEWAY=192.168.168.250
USERCTL=no
```


**[BONDING_OPTS="mode=1 miimon=100"]  （本句配置对应后面选项二）**

然后分别修改eth0和eth1的配置文件
ifcfg-eth0内容：


```
DEVICE=eth0
BOOTPROTO=none
ONBOOT=yes
USERCTL=no
MASTER=bond0   
SLAVE=yes
```


ifcfg-eth1内容


```
DEVICE=eth1
BOOTPROTO=none
ONBOOT=yes
USERCTL=no
MASTER=bond0
SLAVE=yes
```






因为linux的虚拟网卡是在内核模块中实现的，所以需要安装的时候已经装好该module。



**选项一：**
在**/etc/modules.conf**文件中添加如下内容（如果没有该文件，则新建一个）：


```
alias bond0 bonding
options bond0 miimon=100 mode=1 primary=eth0
```


其中miimon=100表示每100ms检查一次链路连接状态，如果不通则会切换物理网卡

mode=1表示主备模式，也就是只有一块网卡是active的，只提供失效保护。如果mode=0则是负载均衡模式的，所有的网卡都是active，还有其他一些模式很少用到
primary=eth0表示主备模式下eth0为默认的active网卡

miimon是毫秒数，每100毫秒触发检测线路稳定性的事件。
mode 是ifenslave的工作状态。



**选项二：**

**创建配置文件 

```
cat /etc/modprobe.d/bonding.conf
写入内容
alias bond0 bonding    （告诉操作系统 bond0 使用的驱动是bonding）**
```


**
**

**
**

**重启服务，完成**

**
**



查看绑定在哪张网卡上 


```
\# cat /proc/net/bonding/bond0
Ethernet Channel Bonding Driver: v3.0.3 (March 23, 2006)

Bonding Mode: fault-tolerance (**active-backup**)
Primary Slave: None
Currently Active Slave: eth0 
MII Status: up
MII Polling Interval (ms): 500
Up Delay (ms): 0
Down Delay (ms): 0

Slave Interface: eth0 
MII Status: up
Link Failure Count: 0
Permanent HW addr: 00:0c:29:01:4f:77

Slave Interface: eth1 
MII Status: up
Link Failure Count: 0
Permanent HW addr: 00:0c:29:01:4f:8b
```
