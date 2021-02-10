## KVM图形化界面

**KVM（Kernel­based Virtual Machine）** http://www.linux­kvm.org/ 

基于内核的虚拟机，配合QEMU（处理器虚拟软件），需要CPU支持虚拟化技术（并且在BIOS里打开虚拟化选项），效率可达到物理机的80％以上。

### 1. KVM的安装

环境要求：

- 如果是物理服务器，需要在BIOS中打开虚拟化功能（Virtualization Technology），一般的服务器默认是打开这个功能的。

![image](9E162300D0CB4C888D114CF98A8907F9)

- 如果是用VMware Workstation做实验，建立的虚拟机的CPU要勾选虚拟化功能，这样虚拟机才会支持KVM虚拟化

![image](1DF778677FB14D22AEA119FA11B02DFA)



**安装方式：**

验证cpu是否支持虚拟化

```
[root@localhost ~]# egrep -c "(vmx|svm)" /proc/cpuinfo
```

#### 1.1 安装系统时

这里以CentOS7.3为例，

选择安装包的时候，如下图选择即可，选择“带GUI的服务器”，并勾选“虚拟化客户端”，“虚拟化Hypervisor”，“虚拟化工具”。

![image](3411D14445DB4366997586F73E52DF9D)

#### 1.2 最小化安装

那你应该安装以下所需软件：

```
yum groupinstall "GNOME 桌面" -y //安装GNOME桌面环境

# yum install qemu* -y
yum install qemu-kvm -y //KVM核心模块
yum install qemu-kvm-tools -y //KVM调试工具，可以选择性安装
yum install qemu-img -y //创建、转换和修改镜像，能处理被qemu支持的所有镜像格式

yum install libvirt -y //管理虚拟机的工具包
yum install virt-install -y //virt-install是一个使用libvirt库构建新虚拟机的命令行工具
yum install virt-manager -y //图形界面管理虚拟机
yum install bridge-utils -y //配置linux以太网桥
```

#### 1.3 验证

检查KVM模块是否安装

```
[root@localhost ~]# lsmod | grep kvm
kvm_intel 170181 0
kvm 554609 1 kvm_intel
irqbypass 13503 1 kvm
[root@localhost ~]# systemctl status libvirtd
```

如果libvirtd服务停止运行，那么你将不能管理虚拟机，也就是不能使用virt-manager等工具来管理虚拟机。

```
# 安装VNC服务端
yum install -y tigervnc-server
# 拷贝模板文件
cp /usr/lib/systemd/system/vncserver@.service /etc/systemd/system/vncserver@:1.service
# 修改配置文件
vim /etc/systemd/system/vncserver@:1.service
# 修改：<USER>

# 修改vnc密码
vncpasswd

systemctl start vncserver@:1.service
ss -anplt | grep vnc
```



### 2. 创建虚拟机

#### 2.1 准备工作

- 操作系统安装介质

ISO文件，并上传到系统的/iso目录下

```bash
# 制作系统盘
mkdir /iso
dd if=/dev/sr0 of=/iso/centos7.iso
# 换ubuntu镜像
dd if=/dev/sr0 of=/iso/ubuntu-20.10.iso
[root@localhost ~]# ls /iso/
CentOS-7-x86_64-DVD-1611.iso
```

- 磁盘空间准备

1. 添加新磁盘
2. 创建LVM
3. 创建文件系统及挂载点，并设置开机自动挂载

```
echo "- - -" > /sys/class/scsi_host/host0/scan
pvcreate /dev/sdb
vgcreate vmvg /dev/sdb
lvcreate -n kvmlv -l 100%VG vmvg
mkfs.ext4 /dev/vmvg/kvmlv
mkdir /kvm-vm
mount /dev/vmvg/kvmlv /kvm-vm/
echo "/dev/vmvg/kvmlv /kvm-vm/ ext4 defaults 0 0" >> /etc/fstab
mount -a 
```

#### 2.2 virt-manager图形界面创建

virt-manager是基于libvirt的图形化虚拟机管理软件。

![image](8F8FBE711FB444B5847D9311EC197BAE)

1. 在命令行中以root身份输入virt-manager命令，出现virt-manager管理界面
2. 在虚拟系统管理器中选择“新建虚拟机”，选择“本地安装介质”，“前进”
3. 选择“浏览”，选择镜像文件
4. 选择内存和CPU设置
5. 选择虚拟机存储硬盘，选择刚刚创建的硬盘文件。
6. 单击完成后，虚拟机就开始创建，然后按照操作系统即可



虚拟机的所有配置是放置在一个xml文件中，位置在/etc/libvirt/qemu/目录中
可以看到创建的2个虚拟机，libvirt的xml文件分为几个重要部分，整体结构如下：
下面是部分截图：
具体格式可以参看http://libvirt.org/formatdomain.html

==虚拟机目录：/etc/libvirt/qemu/==

#### 2.3 virt-manager管理虚拟机

virt-manager应用程序是一个桌面用户接口，用于通过libvirt管理虚拟机。

它的主要目标是管理KVM,不过也可以管理Xen和LXC（Linux容器）。

它提供了运行域的汇总视图，当前性能和资源利用统计。向导可以允许创建新域，以及配置、调整域的资源分配和虚拟硬件。内嵌的VNC和SPICE的客户端提供了一个全图形控制台。

![image](B416C4E1983B4C48B1013720822C1543)

virt-manager主要功能：

1. 定义和创建虚拟机
2. 硬件管理
3. 性能监视
4. 虚拟机的保存和恢复、暂停和继续、关闭和启动
5. 控制台
6. 在线和离线迁移
7. 启用virt-manager

    - 方法1：应用程序----系统工具----虚拟系统管理器
    - 方法2：输入virt-manager命令

```
# 继续添加一块硬盘，拓展LV卷大小
echo "- - -" > /sys/class/scsi_host/host0/scan
pvcreate /dev/sdc
vgextend vmvg /dev/sdc
lvextend -l 100%VG /dev/vmvg/kvmlv
resize2fs /dev/vmvg/kvmlv
df -Th
```