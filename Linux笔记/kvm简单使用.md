# kvm简单使用 

https://www.cnblogs.com/cnsre/p/15555270.html

[toc]

## virsh命令常用参数

| **参数**                               | **参数说明**                                 |
| -------------------------------------- | -------------------------------------------- |
| **基础操作**                           |                                              |
| **list**                               | 查看虚拟机列表，列出域                       |
| **start**                              | 启动虚拟机，开始一个（以前定义的）非活跃的域 |
| **shutdown**                           | 关闭虚拟机，关闭一个域                       |
| **destroy(危险)**                      | 强制关闭虚拟机，销毁（停止）域               |
| **vncdisplay**                         | 查询虚拟机vnc端口号                          |
| **配置管理操作**                       |                                              |
| **dumpxml**                            | 导出主机配置信息                             |
| **undefine**                           | 删除主机                                     |
| **define**                             | 导入主机配置                                 |
| **domrename**                          | 对虚拟机进行重命名                           |
| **挂起与恢复**                         |                                              |
| **suspend**                            | 挂起虚拟机                                   |
| **resume**                             | 恢复虚拟机                                   |
| **自启动管理**                         |                                              |
| **autostart**                          | 虚拟机开机启动                               |
| **autostart --disable**                | 取消虚拟机开机启动                           |
| **以上参数通过 “virsh --help” 获得。** |                                              |

## 删除虚拟机



```shell
virsh destroy njvm01     #强制关闭电源

virsh undefine njvm01   #删除虚拟机

[root@nkgtsv01 data]# virsh shutdown njvm01

域 njvm01 被关闭

[root@nkgtsv01 data]# virsh start njvm01

域 njvm02 已开始

[root@nkgtsv01 data]# virsh list --all   查看虚拟机状态
```

## 设置虚拟机自启动



```shell
virsh autostart njvm01

virsh autostart --disable njvm02
```

## 启动，关闭和重启一个虚拟机



```shell
virsh start njvm01

virsh shutdown njvm01

virsh reboot njvm01
```

## 宿主机链接到kvm虚拟机



```shell
virsh  console njvm01
```

## 克隆虚拟机



```shell
virt-clone -o njvm01-n njvm02-f /data/kvm-img/njvm02.img
```

## 修改njvm05 配置



```shell
virsh edit njvm01
```

## 挂起及恢复虚拟机



```shell
挂起：

virsh suspend njvm01

恢复：

virsh resume njvm01
```

## 创建KVM linux



```shell
virt-install --name njvm01 --boot network,cdrom,menu=on --ram 8000 --vcpus=2 --os-variant=rhel6 --accelerate --cdrom=/home/iso/CentOS-7-x86-64-DVD-1708.iso --disk path=/data/kvm-i/njvm01.img,size=200,bus=virtio --bridge=br0,model=virtio --autostart --vnc --vncport=5930 --vnclisten=0.0.0.0
```

## 创建KVM Windows



```shell
virt-install --name njvmwin --boot network,cdrom,menu=on --ram 6411 --os-type=windows --vcpus=1 --os-variant=rhel6 --accelerate --cdrom=/data/BBackup/  --disk path=/data/kvm-images/njvmwin.img,size=200,bus=virtio --bridge=br0, --autostart --vnc --vncport=5910 --vnclisten=0.0.0.0


virt-install -n njvmwin --vcpus=1 --ram=6411--os-type=windows --os-variant=win2k8 -c /vm/iso/cn_windows_server_2012_r2_sp1_x64.iso --disk path=/usr/share/virtio-win/virtio-win-0.1.126_amd64.vfd,device=floppy --disk path=/vm/win2012.img,format=qcow2,bus=virtio --graphics vnc,listen=0.0.0.0 --noautoconsole
```

## vm添加vnc端口

https://www.cnblogs.com/chenjiahe/p/5919742.html



```XML
<graphics type='vnc' port='5900' autoport='no' listen='0.0.0.0'>

  <listen type='address' address='0.0.0.0'/>

 </graphics>
```

注意

要用 virsh edit vi命令不会生效



```
--name njvm01 \                    #虚拟机名

--ram=1024 \                      #分配内存大小，MB

--vcpus=1 \                       #配置虚拟机的vcpu 数目

--check-cpu \                     #检查确定vcpu是否超过物理 CPU数目，如果超过则发出警告。

--os-type=linux \                  #要安装的操作系统类型，例如：'linux'、'unix'、'windows'

--os-variant=rhel6 \               #操作系统版本，如：'Fedora6', 'rhel5', 'solaris10',  'win2k'

--disk path=/virhost/node7.img,device=disk,bus=virtio,size=20,sparse=true \  #虚拟  机所用磁盘或镜像文件，size大小G

--bridge=br0 \                     #指定网络，采用透明网桥

--noautoconsole \                  #不自动开启控制台

--pxe                              #网络安装

 virsh start njvm01                 #开机

 virsh destroy njvm01               #强制关闭电源

 virsh shutdown njvm01              #关机

 virsh list --all                  #查看虚拟机状态  

 virsh reboot njvm01                #重启
 
 virt-viewer name                   #查看安装状态
```

## xml文件详解

使用virt-install 工具安装虚拟机后，在目录 `/etc/libvirt/qemu/` 下生成 `xml` 配置文件



```xml
 <domain type='kvm'>        # 虚拟机类型

   <name>centos</name>                  虚拟机名称

   <uuid>78dx24ef-1d2d-810x-9213-2c02df529cx</uuid>   uuid唯一标示

   <memory unit='KiB'>2048576</memory>          指定虚拟机内存大小，给出了单位

   <vcpu placement='static'>2</vcpu>           虚拟机占用虚拟cpu个数，这里指物理cpu的核心数量

   <os>

    <type arch='x86_64' machine='rhel6.3.0'>hvm</type>  指定虚拟系统架构

    <boot dev='hd'/>                   启动类型，从硬盘启动

   </os>

   <devices>

    <emulator>/usr/libexec/qemu-kvm</emulator>      驱动程序，同上，使用的是qemu-kvm

    <disk type='file' device='disk'>           指定磁盘类型

    <driver name='qemu' type='raw' cache='none'/>    指定磁盘格式，这里是raw，也支持qcow2.

    <source file='/home/data/img/centos.img'/>        img文件路径

    <target dev='hda' bus='ide'/>            磁盘文件标示，驱动类型

    <address type='drive' controller='0' bus='0' target='0' unit='0'/>

    <interface type='bridge'>

     <mac address='34:72:00:15:65:e6'/>         自动生成，可以手动指定。

     <source bridge='br0'/>               桥接到哪一个接口

    </interface>

   </devices>

 </domain>
```

## 列出虚拟机的所有网口



```
virsh domiflist njvm01

结果如下：

Interface Type    Source   Model    MAC ------------------------------------------------------- vnet0   bridge   br0   virtio   34:72:00:15:65:e6 vnet1   bridge   br1   virtio   52:54:10:f5:c5:6c
```

## 新增一个网口



```shell
virsh attach-interface domain --type bridge --source br1 --model virtio --config           # 下次启动生效

virsh attach-interface domain --type bridge --source br1 --model virtio --current          # 立即生效

virsh detach-interface domain --type bridge --mac 34:72:00:15:65:e6 --config               # 下次启动生效

virsh detach-interface domain --type bridge --mac 34:72:00:15:65:e6 --current              # 立即生效
```

## 删除网卡命令



```shell
virsh detach-interface njvm01 --type network --mac 34:72:00:15:65:e6
```

## 永久添加网卡



```shell
virsh attach-interface domain --type network --source default --model virtio --config 
```

## 临时添加网卡



```
virsh attach-interface njvm01 --type network --source default

virsh attach-interface njvm01  --type network --source default --config
```

## 关闭或打开某个网口



```shell
virsh domif-setlink domain vnet0 down

virsh domif-setlink domain vnet0 up
```

## 获取某个网口状态



```shell
virsh domif-getlink win2k8 vnet1
```

## 列出所有的块设备



```shell
virsh domblklist win2k8
```