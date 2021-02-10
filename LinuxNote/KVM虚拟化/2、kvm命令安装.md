# 命令行使用KVM



## 1. qemu-img

创建磁盘文件

通过文件管理可以直接查看、修改、复制虚拟机的内部文件。例如，当系统因为配置文件无法启动时，可以直接修改虚拟机的文件。虚拟机磁盘文件主要有raw和qcow2格式。raw格式性能最好，速度最快，它的缺点就是不支持一些新的功能，如支持镜像,zlib磁盘压缩,AES加密等。要使用镜像功能，磁盘格式必须为qcow2。

raw格式的话，速度稍微快点，在高版本的qemu-kvm中，几乎不比qcow2的格式快，而qcow2格式节省空间，可动态增长，在公有云中广泛使用，建议使用qcow2。所有有时候，我们需要将raw格式的磁盘转换成qcow2格式。

### 1.1 create 

创建一个raw格式的磁盘文件

```
[root@localhost ~]# qemu-img create vm002.img 15G 
Formatting 'vm002.img', fmt=raw size=10737418240
```

### 1.2 qcow2 

创建一个qcow2 格式的磁盘文件

```
# qemu-img create -f qcow2 vm002.qcow2 10G
```

### 1.3 qemu-img info 

查看一下磁盘信息

```
[root@localhost kvm-vm]# ll
总用量 1436600
-rw------- 1 root root 10739318784 12月 10 16:40 centos7-vm001.qcow2
drwx------ 2 root root       16384 12月 10 16:10 lost+found
-rw-r--r-- 1 root root 10737418240 12月 12 14:19 vm002.img

[root@localhost kvm-vm]# qemu-img info vm002.img
image: vm002.img
file format: raw
virtual size: 10G (10737418240 bytes)
disk size: 0
```

虽然通过ls命令看到磁盘为8G，但是实际空间大小为0，所以通过qemu-img创建的磁盘为稀疏模式磁盘。

### 1.4 convert

转换磁盘格式为qcow2

```bash
[root@localhost kvm-vm]# qemu-img convert -f raw -O qcow2 vm002.img vm002.qcow2
[root@localhost kvm-vm]# ll
总用量 1436796
-rw------- 1 root root 10739318784 12月 10 16:40 centos7-vm001.qcow2
drwx------ 2 root root       16384 12月 10 16:10 lost+found
-rw-r--r-- 1 root root 10737418240 12月 12 14:19 vm002.img
-rw-r--r-- 1 root root      197120 12月 12 14:24 vm002.qcow2

[root@localhost kvm-vm]# qemu-img info vm002.qcow2
image: vm002.qcow2
file format: qcow2
virtual size: 10G (10737418240 bytes)
disk size: 196K
cluster_size: 65536
Format specific information:
    compat: 1.1
    lazy refcounts: false
```



## 2. virt-install

### 2.1 VNC安装

 安装虚拟机

```bash
[root@localhost kvm-vm]# virt-install --name vm002 --memory 1024 --vcpus 1 --disk /kvm-vm/vm002.qcow2,format=qcow2,size=15  --location=/iso/centos7.iso --graphics vnc,listen=0.0.0.0,port=5910 --noautoconsole

开始安装......
搜索文件 .treeinfo......                                           |  354 B  00:00:00     
搜索文件 vmlinuz......                                             | 6.3 MB  00:00:00     
搜索文件 initrd.img......                                          |  50 MB  00:00:00     
域安装仍在进行。您可以重新连接
到控制台以便完成安装进程。

[root@localhost kvm-vm]# ss -anplt | grep 5910
# 使用vnc连接，进入安装界面
# 参数说明 --------------------------------------------------------------
--name vm002                                     # 指定虚拟机名称 
--memory 1024 --vcpus 1                          # 指定内存和cpu 
--disk /kvm-vm/vm002.qcow2,format=qcow2,size=15  # 指定磁盘文件  
--location=/iso/centos7.iso                      # 指定本地安装镜像 
--graphics vnc,listen=0.0.0.0,port=5910 --noautoconsole  # 指定虚拟机显示协议为VNC
```

### 2.2 命令行安装

【console=ttyS0】

```bash
# 创建一块20G的磁盘
qemu-img create -f qcow2 vm003.qcow2 20G

# 安装虚拟机命令
virt-install -n vm003 --memory 1024 --vcpus 1 -l /iso/centos7.iso --disk /kvm-vm/vm003.qcow2,format=qcow2,size=20,sparse --graphics none -x 'console=ttyS0' --accelerate

# 参数说明 -----------------------------------------------------------
-n vm003                                                # 指定虚拟机名称
--memory 1024 --vcpus 1                                 # 指定内存和cpu
-l /iso/centos7.iso                                     # 指定本地安装镜像
--disk /kvm-vm/vm003.qcow2,format=qcow2,size=20,sparse  # 指定磁盘文件，sparse为疏密格式
--graphics none                                         # 不使用图形化
-x 'console=ttyS0'                                      # 转化到伪终端
--accelerate                                            # 安装优化`
```

```
# 进入命令行安装界面
# 选择数字进入设置，按 c 继续，按 q 退出

Starting installer, one moment...
anaconda 21.48.22.147-1 for CentOS 7 started.
 * installation log files are stored in /tmp during the installation
 * shell is available on TTY2
 * when reporting a bug add logs from /tmp as separate text/plain attachments
07:14:43 Not asking for VNC because we don't have a network
================================================================================
================================================================================
Installation

 1) [x] Language settings                 2) [!] Time settings
        (English (United States))                (Timezone is not set.)
 3) [!] Installation source               4) [!] Software selection
        (Processing...)                          (Processing...)
 5) [!] Installation Destination          6) [x] Kdump
        (No disks selected)                      (Kdump is enabled)
 7) [ ] Network configuration             8) [!] Root password
        (Not connected)                          (Password is not set.)
 9) [!] User creation
        (No user will be created)
  Please make your choice from above ['q' to quit | 'b' to begin installation |
  'r' to refresh]:         

[anaconda] 1:main* 2:shell  3:log  4:storage-lo> Switch tab: Alt+Tab | Help: F1 
```



```
# 初始安装设置
================================================================================
Installation

 1) [x] Language settings                 2) [x] Time settings
        (English (United States))                (Asia/Singapore timezone)
 3) [x] Installation source               4) [x] Software selection
        (Local media)                            (Minimal Install)
 5) [x] Installation Destination          6) [x] Kdump
        (Automatic partitioning                  (Kdump is disabled)
        selected)                         8) [x] Root password
 7) [ ] Network configuration                    (Password is set.)
        (Not connected)
 9) [ ] User creation
        (No user will be created)
  Please make your choice from above ['q' to quit | 'b' to begin installation |
  'r' to refresh]: b
[anaconda] 1:main* 2:shell  3:log  4:storage-lo> Switch tab: Alt+Tab | Help: F1 
```



```
# 开机登陆界面
CentOS Linux 7 (Core)
Kernel 3.10.0-957.el7.x86_64 on an x86_64

localhost login: root
Password: 

# 配置网络
[root@localhost ~]# ip a

# 退出虚拟机
快捷键：ctrl + ]
```


## 3. virsh

【命令行管理】

```
[root@localhost kvm-vm]# virsh list
 Id    名称                         状态
----------------------------------------------------
 3     vm002                          running
 6     vm003                          running
```

