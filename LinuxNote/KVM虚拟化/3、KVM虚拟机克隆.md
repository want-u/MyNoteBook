# KVM 实战虚拟机克隆



## 1. 准备

在克隆虚拟机之前，必须先暂停或者停掉kvm 虚拟机。以虚拟机 snale 为例，先暂停虚拟机，如下

```
 1 [root@kvm ~ 17:09:40]#virsh list
 2  Id    名称                         状态
 3 ----------------------------------------------------
 4  1     snale                          running
 5 
 6 [root@kvm ~ 17:10:34]#virsh suspend snale
 7 域 snale 被挂起
 8 
 9 [root@kvm ~ 17:10:55]#virsh list
10  Id    名称                         状态
11 ----------------------------------------------------
12  1     snale                          暂停
```



## 2. 针对虚拟机本身直接克隆

### 2.1 执行命令virt-clone

```
1 [root@kvm ~ 17:27:40]#virt-clone -o snale -n snale_clone -f /kvm3/snale_clone.img
2 正在克隆 snale.img                        |  18 GB     02:30     
3 
4 Clone 'snale_clone' created successfully.
```

**参数说明：**

- -o   :指定源虚拟机
- -n   :命名新克隆的虚拟机
- -f   :指定新克隆虚拟机的位置

克隆完成后，在生成硬盘映像文件的同时，也会克隆虚拟机的配置文件：

```
1 [root@kvm ~ 17:36:13]#ls /kvm3
2 snale_clone.img
3 [root@kvm ~ 17:36:16]#ls /etc/libvirt/qemu
4 autostart  networks  snale2.xml  snale_clone.xml  snale.xml
```

###  2.2 查看各虚拟机的状态

```
1 [root@kvm ~ 17:40:28]#virsh list --all
2  Id    名称                         状态
3 ----------------------------------------------------
4  1     snale                          暂停
5  -     snale2                         关闭
6  -     snale_clone                    关闭
```

### 2.3 修改配置文件

此时是无法启动的，要先修改它的配置文件，更改VNC端口，将port从5901改成5903

```
 1 [root@kvm qemu 17:56:28]#virsh edit snale_clone
 2 编辑了域 snale_clone XML 配置。
 3 
 4 [root@kvm qemu 17:57:11]#
 5 [root@kvm qemu 17:57:11]#virsh start snale_clone
 6 域 snale_clone 已开始
 7 
 8 [root@kvm qemu 17:57:23]#virsh list
 9  Id    名称                         状态
10 ----------------------------------------------------
11  1     snale                          暂停
12  4     snale_clone                    running
```



## 3. 复制配置文件与磁盘文件克隆

### 3.1 导入kvm虚拟机配置文件

这里采用 oeltest01做为模板，进行克隆。

```
# virsh shutdown vm001
# virsh dumpxml vm001 > /etc/libvirt/qemu/vm002.xml
```

### 3.2 复制kvm虚拟机磁盘文件

```
cp /kvm-vm/vm001.qcow2 /kvm-vm/vm002.qcow2
```

### 3.3 直接编辑修改配置文件

==修改 name , uuid , disk 位置, vnc 端口, mac地址==

```
# vim /etc/libvirt/qemu/vm002.xml
```

```
<!--
WARNING: THIS IS AN AUTO-GENERATED FILE. CHANGES TO IT ARE LIKELY TO BE
OVERWRITTEN AND LOST. Changes to this xml configuration should be made using:
  virsh edit vm002
or other application using the libvirt API.
-->

<domain type='kvm'>
  <name>vm002</name>
  <uuid>a543a6a9-d9d5-447a-bca3-6039ac6c39ef</uuid>
  <memory unit='KiB'>1048576</memory>
  <currentMemory unit='KiB'>1048576</currentMemory>
  <vcpu placement='static'>1</vcpu>
  <os>
    <type arch='x86_64' machine='pc-i440fx-rhel7.0.0'>hvm</type>
    <boot dev='hd'/>
  </os>
  <features>
    <acpi/>
    <apic/>
  </features>
  <cpu mode='custom' match='exact' check='partial'>
    <model fallback='allow'>Broadwell-noTSX-IBRS</model>
    <feature policy='require' name='md-clear'/>
    <feature policy='require' name='spec-ctrl'/>
    <feature policy='require' name='ssbd'/>
  </cpu>
  <clock offset='utc'>
    <timer name='rtc' tickpolicy='catchup'/>
    <timer name='pit' tickpolicy='delay'/>
    <timer name='hpet' present='no'/>
  </clock>
  <on_poweroff>destroy</on_poweroff>
  <on_reboot>restart</on_reboot>
  <on_crash>destroy</on_crash>
  <pm>
    <suspend-to-mem enabled='no'/>
    <suspend-to-disk enabled='no'/>
  </pm>
  <devices>
    <emulator>/usr/libexec/qemu-kvm</emulator>
    <disk type='file' device='disk'>
      <driver name='qemu' type='qcow2'/>
      <source file='/kvm-vm/vm002.qcow2'/>
      <target dev='vda' bus='virtio'/>
      <address type='pci' domain='0x0000' bus='0x00' slot='0x06' function='0x0'/>
    </disk>
    <disk type='file' device='cdrom'>
      <driver name='qemu' type='raw'/>
      <target dev='hda' bus='ide'/>
      <readonly/>
      <address type='drive' controller='0' bus='0' target='0' unit='0'/>
    </disk>
    <controller type='usb' index='0' model='ich9-ehci1'>
      <address type='pci' domain='0x0000' bus='0x00' slot='0x04' function='0x7'/>
    </controller>
    <controller type='usb' index='0' model='ich9-uhci1'>
      <master startport='0'/>
      <address type='pci' domain='0x0000' bus='0x00' slot='0x04' function='0x0' multifunction='on'/>
    </controller>
    <controller type='usb' index='0' model='ich9-uhci2'>
      <master startport='2'/>
      <address type='pci' domain='0x0000' bus='0x00' slot='0x04' function='0x1'/>
    </controller>
    <controller type='usb' index='0' model='ich9-uhci3'>
      <master startport='4'/>
      <address type='pci' domain='0x0000' bus='0x00' slot='0x04' function='0x2'/>
    </controller>
    <controller type='pci' index='0' model='pci-root'/>
    <controller type='ide' index='0'>
      <address type='pci' domain='0x0000' bus='0x00' slot='0x01' function='0x1'/>
    </controller>
    <controller type='virtio-serial' index='0'>
      <address type='pci' domain='0x0000' bus='0x00' slot='0x05' function='0x0'/>
    </controller>
    <interface type='network'>
      <mac address='52:54:00:ed:fb:78'/>
      <source network='default'/>
      <model type='virtio'/>
      <address type='pci' domain='0x0000' bus='0x00' slot='0x03' function='0x0'/>
    </interface>
    <serial type='pty'>
      <target type='isa-serial' port='0'>
        <model name='isa-serial'/>
      </target>
    </serial>
    <console type='pty'>
      <target type='serial' port='0'/>
```

