# KVM文件管理

通过文件管理可以直接查看、修改、复制虚拟机的内部文件。
例如，当系统因为配置文件无法启动时，可以直接修改虚拟机的文件。

虚拟机磁盘文件主要有raw和qcow2格式。

- raw格式性能最好，速度最快，它的缺点就是不支持一些新的功能，如支持镜像,zlib磁盘压缩,AES加密等。
- 要使用镜像功能，磁盘格式必须为qcow2。raw格式的话，速度稍微快点，在高版本的qemu-kvm中，几乎不比qcow2的格式快
- 而qcow2格式节省空间，可动态增长，在公有云中广泛使用，建议使用qcow2。

所有有时候，我们需要将raw格式的磁盘转换成qcow2格式

(qemu-img convert -f raw -O qcow2 /kvmvm/vmtest01.img /kvm-vm/vmtest01.qcow2)。

## 1. 离线访问工具应用场景

1. 观看或下载位于虚拟机磁盘中的文件
2. 编辑或上传文件到虚拟机磁盘
3. 读取或写入的虚拟机配置
4. 准备新的磁盘映像，其中包含文件、目录、文件系统、分区、逻辑卷和其他选项
5. 拯救和修复客户无法启动或需要更改启动配置的虚拟机
6. 监控虚拟机的磁盘使用情况
7. 根据组织安全标准审计虚拟机的合规性
8. 通过克隆和修改模板来部署虚拟机
9. 读取CD和DVD ISO和软盘映像



## 2. 虚拟磁盘离线访问工具

### 2.1 安装软件工具

- 安装 libguestfs libguestfs-tools 软件工具，就可以直接读取qcow2格式的磁盘文件
- 【 -a 指定虚拟磁盘文件， -d 指定虚拟机域】
- 例如：virt-inspector -d vm001

```
yum install -y  libguestfs libguestfs-tools
```

==下面的这些操作最好要在虚拟机关机状态下做，否则会出错。==

### 2.2 virt-inspector 

显示os版本、内核、驱动、挂载点、应用等等，很少使用

```
# virt-inspector vm001
# virt-inspector /kvm-vm/vm001.qcow2
# virt-inspector --format=qcow2 /kvm-vm/vm001.qcow2 |more
```

### 2.3 virt­cat

类似于cat命令

```
# virt-cat /kvm-vm/vm001.qcow2 /etc/hostname
# virt-cat vm001 /etc/hostname
```

### 2.4 vrit-edit

用于编辑文件，用法与vim基本一致，例如把主机名改成vmtest001.test.com

```
# virt-edit vm001 /etc/hostname
# 修改为vm001
```

然后再查看，发现主机名已经更改

```
# virt-cat vm001 /etc/hostname
vm001
```

### 2.5 virt-df

用于查看虚拟机磁盘信息

```
[root@kvm001 ~]# virt-df -h vm001
文件系统                            大小 已用空间 可用空间 使用百分比%
vm001:/dev/sda1                          1014M       132M       882M   13%
vm001:/dev/centos/root                     17G       1.4G        16G    9%
```

### 2.6 virt-copy-out

这个命令可以把虚拟机里的文件复制出来, 用法如下

> virt-copy-out -d domname file|dir [file|dir ...] localdir
> virt-copy-out -a disk.img file|dir [file|dir ...] localdir

```
[root@kvm001 ~]# virt-copy-out -d vm001 /etc/hostname ./
[root@kvm001 ~]# ls
anaconda-ks.cfg hostname
```

### 2.7 virt-copy-in

是将文件复制到虚拟机里面,用法和virt-copy-out基本相同

```
[root@localhost kvm-vm]# echo "this is a test" > test.txt
[root@localhost kvm-vm]# virt-copy-in -d vm001 test.txt /opt/
[root@localhost kvm-vm]# virt-cat vm001 /opt/test.txt
this is a test
```

### 2.8 guestfish

进入交互式读写文件

> guestfish [--ro|--rw] -i -a /path/to/disk.img
> guestfish [--ro|--rw] -i -d name-of-libvirt-domain

```
guestfish --rw -i vm001
...
><fs> cat /opt/test.txt 
this is a test
><fs> vi /etc/passwd
```

### 2.9 guestmount

在一些使用场景中，直接把虚机镜像文件挂接在本地系统中，也是一个简便的办法

```
# guestmount vm1.qcow2 -m /dev/vg_centosbase/lv_root --rw /mnt
```

如果事先不知道虚机内部分区的路径信息，则可以使用参数-i来让guestmount自己决定使用哪一个挂接点：

```
# guestmount -d vm001 -i --rw /mnt/
# cd /mnt/
# ls
bin   dev  home  lib64  mnt  proc  run   srv  tmp  var
boot  etc  lib   media  opt  root  sbin  sys  usr
```

将/mnt下的挂载进行卸载

```
# guestunmount /mnt
```

