# KVM迁移



## 1. 静态迁移(冷迁移)

​	对于静态迁移，你可以在宿主机上保存一个完整的客户机镜像快照，然后在宿主机中关闭或者暂停该客户机，然后将该客户机的镜像文件复制到另一台宿主机中，使用在源主机中启动该客户机时的命令来启动复制过来的镜像。

### 1.1 静态迁移

1. 拷贝镜像文件和虚拟机配置文件
2. 重新定义此虚拟机



## 2. 动态迁移(热迁移)

​	如果源宿主机和目的宿主机共享存储系统，则只需要通过网络发送客户机的 vCPU 执行状态、内存中的内容、虚机设备的状态到目的主机上。否则，还需要将客户机的磁盘存储发到目的主机上。

​	共享存储系统指的是源和目的虚机的镜像文件目录是在一个共享的存储上的。

### 2.1 动态迁移的过程

在基于共享存储系统时，KVM 动态迁移的具体过程为：

1. 迁移开始时，客户机依然在宿主机上运行，与此同时，客户机的内存页被传输到目的主机上。
2. QEMU/KVM 会监控并记录下迁移过程中所有已被传输的内存页的任何修改，并在所有内存页都传输完成后即开始传输在前面过程中内存页的更改内容。
3. QEMU/KVM 会估计迁移过程中的传输速度，当剩余的内存数据量能够在一个可以设定的时间周期（默认 30 毫秒）内传输完成时，QEMU/KVM 会关闭源宿主机上的客户机，再将剩余的数据量传输到目的主机上，最后传输过来的内存内容在目的宿主机上恢复客户机的运行状态。
4. 至此，KVM 的动态迁移操作就完成了。迁移后的客户机尽可能与迁移前一致，除非目的主机上缺少一些配置，比如网桥等。

​	注意，当客户机中内存使用率非常大而且修改频繁时，内存中数据不断被修改的速度大于KVM能够传输的内存速度时，动态迁移的过程是完成不了的，这时候只能静态迁移。

​	关于实时迁移的效率，业界不少人提出了改进的建议，比如通过使用内存压缩技术，减少需要传输
的内存的大小

### 2.2 迁移注意事项

1. 最好迁移的服务器cpu品牌一样
2. 64位只能在64位宿主机间迁移，32位可以迁移32位和64位宿主机
3. 宿主机名字不能冲突
4. 目的宿主机和源宿主机软件配置尽可能的相同，如 有相同的桥接网卡，资源池等。
5. 两台迁移的主机 cat /proc/cpuinfo |grep nx 的设置是相同的
   - NX，全名为“No eXecute”，即“禁止运行”，是应用在CPU的一种技术，用作把存储器
     区域分隔为只供存储处理器指令集，或只供数据使用。

### 2.3 动态迁移

1. 创建共享存储
2. 两台机器挂载共享存储(手工挂载；使用资源池)
3. 启动动态迁移
4. 创建迁移后的虚拟机配置文件
5. 重新定义虚拟机

### 2.4 实验拓扑

![image-20201223003555126](https://gitee.com/luoxian1011/pictures/raw/master/image-20201223003555126.png)

| 主机  |    IP地址     | 主机名 |
| :---: | :-----------: | :----: |
| KVM01 | 192.168.10.11 | kvm01  |
| KVM02 | 192.168.10.12 | kvm02  |
| NFS01 | 192.168.10.13 | nfs01  |

### 2.5 案例实施步骤

1. 设置主机名、/etc/hosts 互相解析，保证网络连接
2. 两台主机的KVM连接NFS共享存储
3. 在源主机的KVM中新建虚拟机并安装操作系统
4. 连接KVM，并进行迁移。



## 3. 动态迁移部署

### 3.1 配置主机名和hosts

—— 配置主机名

```
[root@server1 kvm-vm]# hostnamectl set-hostname kvm01
[root@server1 kvm-vm]# bash
```

```
[root@server1 kvm-vm]# hostnamectl set-hostname kvm02
[root@server1 kvm-vm]# bash
```

```
[root@server1 kvm-vm]# hostnamectl set-hostname nfs01
[root@server1 kvm-vm]# bash
```

—— 配置hosts文件

```
192.168.10.11   kvm01
192.168.10.12   kvm02
192.168.10.13   nfs01
```

```
[root@kvm01 kvm-vm]# scp /etc/hosts kvm02:/etc/
[root@kvm01 kvm-vm]# scp /etc/hosts nfs01:/etc/
```



### 3.1 配置NFS共享存储

NFS01服务器192.168.10.13配置如下：

```
[root@nfs ~]# yum -y install nfs-utils rpcbind         #安装所需软件包
[root@localhost ~]# systemctl enable nfs               #设置NFS开机自启
[root@localhost ~]# systemctl enable rpcbind           #设置rpcbind开机自启

[root@nfs ~]# mkdir -p /nfsshare        	#创建需要共享的目录
[root@nfs ~]# vim /etc/exports    			#编辑NFS的配置文件，默认为空
/nfsshare    *(rw,sync,no_root_squash)
[root@nfs ~]# systemctl restart rpcbind   	#启动该服务
[root@nfs ~]# systemctl restart nfs   		#启动该服务
[root@nfs ~]# netstat -anpt | grep rpc   	#确认服务已启动
[root@nfs ~]# showmount -e     				#查看本机共享的目录
Export list for nfs:
/nfsshare *

[root@nfs ~]# firewall-cmd --add-service=rpc-bind --permanent 
[root@nfs ~]# firewall-cmd --add-service=nfs --permanent 
[root@nfs ~]# firewall-cmd --add-service=mountd --permanent 
[root@nfs ~]# systemctl restart firewalld   #重启防火墙，使配置生效
```

### 3.2 两台KVM服务器配置

—— **两台kvm主机都需要进行下面的配置**：

1. 安装rpcbind软件包，并启动rpcbind服务，为了使用showmount查询工具，所以将nfs-utils也一起装上：

```
[root@localhost ~]# yum -y install nfs-utils rpcbind 
[root@localhost ~]# systemctl enable rpcbind
[root@localhost ~]# systemctl start rpcbind
[root@kvm ~]# showmount -e 192.168.10.13  		#查询nfs服务器共享的目录
Export list for 192.168.10.13:
/nfsshare *
[root@kvm ~]# mount -t nfs 192.168.10.13:/nfsshare /media/  #测试挂载
```

2. 创建NFS存储池

```
[root@server1 kvm-vm]# mkdir -p /kvm-vm/nfspool
[root@server1 kvm-vm]# virsh 
virsh # pool-define-as nfspool netfs --source-host 192.168.10.13 --source-path /nfsshare --target /kvm-vm/nfspool/
virsh # pool-build nfspool   
virsh # pool-start nfspool 
virsh # pool-autostart nfspool 
```

​	至此，就保证了两台kvm服务器使用的目录都是同一块磁盘存储的（注意：两台kvm虚拟机的挂载nfs文件系统的目录路径必须一致，我这里两台kvm虚拟机都是挂载到了/kvm-vm/nfspool/目录下，否则会在后面的操作中发生错误）。

### 3.3 创建桥接网络

将新建的虚拟机网络配置为Bridge模式，可以ping通外网

—— 以下操作主要是为了模拟虚拟机为公网用户提供服务中进行热迁移

- kvm1操作如下：

```
[root@kvm ~]# systemctl stop NetworkManager  	#停止此服务
[root@kvm ~]# virsh iface-bridge ens33 br0  	#执行此命令时，若提示以下信息，不用在意，因为其已经存在了
使用附加设备 br0 生成桥接 ens33 失败
已启动桥接接口 br0
[root@kvm ~]# ls /etc/sysconfig/network-scripts/ | grep br0  
ifcfg-br0    									#确定有此文件就行
[root@kvm ~]# virsh destroy nfsvm001   		#关闭新建的虚拟机
域 centos7.0 被删除

[root@kvm ~]# virsh edit nfsvm001   			#编辑虚拟机的配置文件，定位到interface
<interface type='bridge'>   		#将此处改为bridge
 <mac address='52:54:00:a9:cc:5f'/> #删除Mac地址这行
   <source bridge='br0'/>     		#将此处更改为这样
												#保存退出即可
[root@kvm1 ~]# virsh start nfsvm001
域 centos7.0 已开始
```

重启虚拟机，配置虚拟机的网络【桥接】，确认能够ping通公网

- kvm2操作如下：

```
[root@kvm ~]# systemctl stop NetworkManager  	#停止此服务
[root@kvm ~]# virsh iface-bridge ens33 br0   	#执行此命令时，若提示以下信息，不用在意，因为其已经存在了
使用附加设备 br0 生成桥接 ens33 失败
已启动桥接接口 br0
[root@kvm ~]# ls /etc/sysconfig/network-scripts/ | grep br0  
ifcfg-br0    									#确定有此文件就行
#由于kvm2没有虚拟机，所以只需将网络更改为桥接模式即可。
#以上配置是为了防止虚拟机迁移到这台服务器后，无法和公网进行联系。
```



### 3.4 通过nfspool创建虚拟机

—— 在kvm1上新建一个虚拟机以便进行迁移测试

创建卷nfsvm001

```
virsh # vol-create-as nfspool nfsvm001.qcow2 8G --format qcow2
创建卷 nfsvm002.qcow2
```

```
# 保证在第二台kvm服务器的目录下也可以看到
[root@server1 dirpool]# ll /kvm-vm/nfspool/
总用量 196
-rw-------. 1 root root 197120 12月 17 15:14 nfsvm002.qcow2
```

```
virt-install -n nfsvm001 -r 512 --vcpus 1 --disk /kvm-vm/nfspool/nfsvm001.qcow2,format=qcow2,size=8,sparse -l /iso/centos7.iso --graphics none -x 'console=ttyS0' --accelerate
```

### 3.5 进行热迁移

网络：网桥

kvm1执行命令热迁移

```
# qemu+ssh协议测试
virsh -c qemu+ssh://kvm02/system list
```



```
# 热迁移
virsh migrate --live --unsafe --verbose nfsvm001 qemu+ssh://kvm02/system
```



迁移到kvm2的虚拟机并没有配置文件，需要自行导出

```
# 迁移到的虚拟机dump配置文件
virsh dumpxml nfsvm001 > /etc/libvirt/qemu/nfsvm001.xml
```

