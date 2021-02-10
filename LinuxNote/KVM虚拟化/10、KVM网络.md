# KVM网络



## 1. KVM网络配置

- NAT模式
- 桥接模式
- 自定义的隔离虚拟网络

### 1.1 qemu-kvm支持的网络

- 虚拟机的网络模式
  - 基于NAT（Network Address Translation）的虚拟网络
  - 基于网桥（Bridge）的虚拟网络
  - 用户自定义的隔离的虚拟网络
  - 直接分配网络设备（包括VT-d和SR-IOV）
- 虚拟机的网卡：
  - RTL8139、e1000、.....
  - virtio

### 1.2 virsh命令查看网络

```
# 查看网络列表
[root@server1 ~]# virsh net-list
# 查看默认网络信息
[root@server1 ~]# virsh net-info default
# 查看网络信息的xml格式，可重定向导出网络配置文件
[root@server1 ~]# virsh net-dumpxml default
```

### 1.3 查看qemu网络配置

```
# cat /etc/libvirt/qemu/vm001.xml
# 虚拟机配置文件中的网络部分
<interface type='network'>
      <mac address='52:54:00:ad:6b:f7'/>
      <source network='default'/>
      <model type='virtio'/>
      <address type='pci' domain='0x0000' bus='0x00' slot='0x03' function='0x0'/>
</interface>
```



## 2. 基于NAT的虚拟网络

​			   —— KVM安装时默认的网络配置

![image-20201222224023522](https://gitee.com/luoxian1011/pictures/raw/master/image-20201222224023522.png)

### 2.1 查看网桥虚拟机接口

默认宿主机会有一个虚拟网卡virbr0，其实是一个虚拟交换机

每当打开一台虚拟机，都将多一个网卡（vnet0即是一个虚拟机的网卡）

```
# 查看网桥虚拟机接口，virbr0默认绑定一个网卡virbr0-nic
[root@server1 networks]# brctl show
bridge name	bridge id			STP enabled	interfaces
virbr0		8000.525400a6c0f9	yes			virbr0-nic

# 启动一台虚拟机
[root@server1 networks]# virsh start vm001
域 vm001 已开始

# 再次查看，可见多出一个vnet0网卡
[root@server1 networks]# brctl show
bridge name	bridge id			STP enabled	interfaces
virbr0		8000.525400a6c0f9	yes			virbr0-nic
											vnet0
```

这时候主机就变成了一个路由器，可以看到路由功能已经打开

```
[root@kvm001 ~]# cat /proc/sys/net/ipv4/ip_forward
1
```

查看iptables的nat表，可以看到做了SNAT

```
[root@kvm001 ~]# iptables -t nat -L
Chain POSTROUTING (policy ACCEPT)
target     prot opt source               destination         
RETURN     all  --  192.168.122.0/24     base-address.mcast.net/24 
RETURN     all  --  192.168.122.0/24     255.255.255.255     
MASQUERADE  tcp  --  192.168.122.0/24    !192.168.122.0/24     masq ports: 1024-65535
MASQUERADE  udp  --  192.168.122.0/24    !192.168.122.0/24     masq ports: 1024-65535
MASQUERADE  all  --  192.168.122.0/24    !192.168.122.0/24
# 通过 iptables -nvL 可以看到从virbr0接口进来的DNS报文和DHCP报文是允许的
```

通过virsh命令查看虚拟机的网卡情况

```
virsh # domiflist vm001
接口     类型     源        型号      MAC
-------------------------------------------------------
vnet0      network    default    virtio      52:54:00:ae:1b:12

virsh # domifaddr vm001
 名称     MAC 地址           Protocol     Address
-------------------------------------------------------------------------------
 vnet0      52:54:00:ae:1b:12    ipv4         192.168.122.109/24
```

### 2.2 查看默认网络配置文件

```
[root@server1 ~]# cd /etc/libvirt/qemu/networks/
[root@server1 networks]# ll
总用量 4
drwx------. 2 root root  25 12月 16 21:21 autostart
-rw-------. 1 root root 576 12月 16 21:21 default.xml
[root@server1 networks]# vim default.xml
```

```
# 配置文件，路由默认为192.168.122.1
<network>
  <name>default</name>
  <uuid>16cb2d94-82d3-4178-8689-b8c2b114a1b3</uuid>
  <forward mode='nat'/>
  <bridge name='virbr0' stp='on' delay='0'/>
  <mac address='52:54:00:b4:cf:39'/>
  <ip address='192.168.122.1' netmask='255.255.255.0'>
    <dhcp>
      <range start='192.168.122.2' end='192.168.122.254'/>
    </dhcp>
  </ip>
</network>
```

### 2.3 添加一个自定义nat网络

#### 2.4.1 编辑网络配置文件

```
[root@server1 ~]# cd /etc/libvirt/qemu/networks/
[root@server1 networks]# cp default.xml mynat.xml
```

```
# 修改mynat.xml配置文件【name，uuid，mac，bridge，网段】
<network>
  <name>mynat</name>
  <uuid>16cb3d94-82d3-4178-8689-b8c2b114a1b3</uuid>
  <forward mode='nat'/>
  <bridge name='virbr1' stp='on' delay='0'/>
  <mac address='52:54:00:b4:cf:49'/>
  <ip address='192.168.1.1' netmask='255.255.255.0'>
    <dhcp>
      <range start='192.168.1.2' end='192.168.1.254'/>
    </dhcp>
  </ip>
</network>
```

#### 2.4.2 载入网络配置

```
virsh # net-define --file /etc/libvirt/qemu/networks/mynat.xml 
从 mynat定义网络/etc/libvirt/qemu/networks/mynat.xml

virsh # net-list --all
 名称               状态     自动开始  持久
----------------------------------------------------------
 default              活动     是           是
 mynat                不活跃  否           是

virsh # net-start mynat
网络 mynat 已开始

virsh # net-autostart mynat
网络mynat标记为自动启动

virsh # net-list --all
 名称               状态     自动开始  持久
----------------------------------------------------------
 default              活动     是           是
 mynat                活动     是           是
```

#### 2.4.3 创建存储池

```
[root@server1 networks]# cd /kvm-vm/
[root@server1 kvm-vm]# mkdir dirpool
virsh # pool-define-as dirpool dir --target /kvm-vm/dirpool/
virsh # pool-build dirpool 
virsh # pool-start dirpool   
virsh # pool-autostart dirpool 
virsh # pool-list --all
```

#### 2.4.4 创建存储卷

```
virsh # vol-create-as dirpool dirvm001.qcow2 8G --format qcow2
创建卷 dirvm001.qcow2

virsh # vol-list dirpool 
 名称               路径                                  
------------------------------------------------------------------------------
 dirvm001.qcow2       /kvm-vm/dirpool/dirvm001.qcow2
```

#### 2.4.5 创建虚拟机

```
virt-install -n mynat_vm002 -r 512 --vcpus 1 --disk vol=dirpool/dirvm001.qcow2 -l /iso/centos7.iso --graphics none -x 'console=ttyS0' --accelerate --network network=mynat

# --network network=mynat
# --network bridge=virbr1 指定网桥为virbr1【mynat模式】
```

#### 2.4.6 直接通过修改网络配置文件来修改网络

```
# 关机修改配置文件
virsh shutdown vm001
virsh edit vm001

# 修改为如下参数：
<interface type='network'>
<source network='mynat'/> #这一行修改源为network='mynat'
 
# 开机
virsh start vm001
```



## 3. 基于网桥的虚拟网络

![image-20201222233609546](https://gitee.com/luoxian1011/pictures/raw/master/image-20201222233609546.png)

### 3.1 在本机添加网桥

```
[root@kvm ~]# systemctl stop NetworkManager  	#停止此服务
[root@kvm ~]# virsh iface-bridge ens33 br0  	#执行此命令时，若提示以下信息，不用在意，因为其已经存在了
使用附加设备 br0 生成桥接 ens33 失败
已启动桥接接口 br0
[root@kvm ~]# ls /etc/sysconfig/network-scripts/ | grep br0  
ifcfg-br0    									#确定有此文件就行
```

### 3.2 重启网络

```
/etc/init.d/network restart
# 查看ip信息
ip a
2: ens33: <BROADCAST,MULTICAST,UP,LOWER_UP> mtu 1500 qdisc pfifo_fast master br0 state UP group default qlen 1000
    link/ether 00:0c:29:d2:a7:f0 brd ff:ff:ff:ff:ff:ff
5: br0: <BROADCAST,MULTICAST,UP,LOWER_UP> mtu 1500 qdisc noqueue state UP group default qlen 1000
    link/ether 00:0c:29:d2:a7:f0 brd ff:ff:ff:ff:ff:ff
    inet 192.168.10.11/24 brd 192.168.10.255 scope global br0
       valid_lft forever preferred_lft forever
    inet6 fe80::20c:29ff:fed2:a7f0/64 scope link 
       valid_lft forever preferred_lft forever
```

### 3.3 通过 virsh attach-interface 添加网络

```
virsh attach-interface vm001 --type bridge --source virbr2 --model virtio --config # 下次启动生效
virsh attach-interface vm001 --type bridge --source virbr2 --model virtio --current # 立即生效
```

```
virsh # domiflist vm001
接口     类型     源        型号      MAC
-------------------------------------------------------
vnet0      network    default    virtio      52:54:00:ae:1b:12
vnet2      bridge     virbr2     virtio      52:54:00:55:46:f7
```

附注：直接通过修改网络配置文件来修改网络

```
# 关机修改配置文件
virsh shutdown vm001
virsh edit vm001

# 修改为如下参数：
<interface type='bridge'> #这一行修改接口模式为"bridge"
<source bridge='br0'/> #这一行修改源为"bridge='br0'"
 
# 开机
virsh start vm001
```

### 3.4 重启虚拟机获取IP

- 当有dhcp服务时，自动获取ip即可；

- 没有dhcp时，手动配置ip信息能够访问外网即可



## 4. 用户自定义的隔离的虚拟网络

### 4.1 添加自定义网络文件

```
vim /etc/libvirt/qemu/networks/host.xml
```

```
<!-- host网络配置 -->
<network>
  <name>host</name>
  <uuid>76061a8a-5226-499d-a1d7-5dd245d8055b</uuid>
  <bridge name='virbr3' stp='on' delay='0'/>
  <mac address='52:54:00:4a:b5:7c'/>
  <ip address='192.168.100.1' netmask='255.255.255.0'>
    <dhcp>
      <range start='192.168.100.128' end='192.168.100.254'/>
    </dhcp>
  </ip>
</network>
```

### 4.2 载入网络配置

```
virsh # net-define --file /etc/libvirt/qemu/networks/host.xml
virsh # net-start host
virsh # net-autostart host
virsh # net-list --all
```

### 4.3 虚拟机修改网络

```
# 关机修改配置文件
virsh shutdown vm001
virsh edit vm001

# 修改为如下参数：
<interface type='network'>
<source network='host'/> #这一行修改源为network='host'
 
# 开机
virsh start vm001
```





---



**附注：**

##### 3.1 在本机添加网桥（手动添加配置文件）

```
cd /etc/sysconfig/network-scripts/
cp ifcfg-ens33 ifcfg-virbr2
```

```
# 网桥配置文件
TYPE=Bridge
BOOTPROTO=static
NAME=virbr2
DEVICE=virbr2
ONBOOT=yes
IPADDR=192.168.10.11
NETMASK=255.255.255.0
GATEWAY=192.168.10.1
DNS1=114.114.114.114
```

##### 3.2 修改本机的网卡配置

```
vim /etc/sysconfig/network-scripts/ifcfg-ens33
```

```
# 网卡配置文件，指定网桥为virbr2
TYPE=Ethernet
NAME=ens33
DEVICE=ens33
ONBOOT=yes
BRIDGE=virbr2
```

### 