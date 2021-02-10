# virsh管理虚拟机



## 1. KVM基本功能管理

### 1.1 查看虚拟机

【list】

```
[root@localhost kvm-vm]# virsh list
 Id    名称                         状态
----------------------------------------------------
 3     vm002                          running
 6     vm003                          running

[root@localhost kvm-vm]# virsh list --all
 Id    名称                         状态
----------------------------------------------------
 3     vm002                          running
 6     vm003                          running
 -     centos7.0                      关闭
```

### 1.2 连接到虚拟机

连接【console】

使用【ctrl + ] 退出虚拟机】



```
[root@localhost kvm-vm]# virsh console 6
连接到域 vm003
换码符为 ^]


CentOS Linux 7 (Core)
Kernel 3.10.0-957.el7.x86_64 on an x86_64

localhost login: root
Password: 
Last login: Sat Dec 12 15:26:39 on ttyS0
[root@localhost ~]# 
```

### 1.3 开启虚拟机

【start 】

```
virsh start centos7.0
```

### 1.4 指定配置文件启动

【create】

```
virsh create /etc/libvirt/qemu/vm001.xml
```

### 1.5 导出虚拟机配置

【dumpxml】

```
virsh dumpxml vm001 > /etc/libvirt/qemu/vm001-bak.xml
```

### 1.6 关闭虚拟机

【shutdown】

```
[root@localhost kvm-vm]# virsh shutdown centos7.0
域 centos7.0 被关闭

[root@localhost kvm-vm]# virsh shutdown 3
域 3 被关闭
[root@localhost kvm-vm]# virsh shutdown centos7.0
域 centos7.0 被关闭

[root@localhost kvm-vm]# virsh shutdown 3
域 3 被关闭
```

### 1.7 强制关机

【destroy】

```
[root@localhost kvm-vm]# virsh destroy centos7.0
```



## 2. 虚拟机的删除和添加

### 2.1 删除虚拟机

【undefine】

```
[root@localhost ~]# virsh shutdown vm001
域 vm002 被关闭

[root@localhost ~]# virsh undefine vm001
域 vm002 已经被取消定义

# 查看删除结果，vm001的配置文件被删除，但是磁盘文件不会被删除。
[root@localhost ~]# ls /etc/libvirt/qemu
networks  vm001-bak.xml
[root@server1 kvm-vm]# ls /kvm-vm/
lost+found  vm001.qcow2

# 通过virsh list --all 查看不到vm002的信息，说明此虚拟机被删除
[root@localhost ~]# virsh list --all
```

### 2.2 添加虚拟机

【define】

```
# 通过备份的配置文件重新定义虚拟机：
# xml配置文件区别在名称，uuid，磁盘和mac参数
[root@server1 kvm-vm]# mv /etc/libvirt/qemu/vm001-bak.xml /etc/libvirt/qemu/vm001.xml 
[root@server1 kvm-vm]# virsh define /etc/libvirt/qemu/vm001.xml 
定义域 vm001（从 /etc/libvirt/qemu/vm001.xml）
[root@server1 kvm-vm]# virsh list --all
 Id    名称                         状态
----------------------------------------------------
 -     vm001                          关闭
```



## 3. 虚拟机的挂起和恢复

### 3.1 挂起

【suspend】

```
[root@localhost kvm-vm]# virsh start vm001
域 vm001 已开始

[root@server1 kvm-vm]# virsh list --all
 Id    名称                         状态
----------------------------------------------------
 14    vm001                          running

[root@server1 kvm-vm]# virsh suspend vm001
域 vm001 被挂起

[root@server1 kvm-vm]# virsh list --all
 Id    名称                         状态
----------------------------------------------------
 14    vm001                          暂停

```

### 3.2 恢复

【resume】

```
[root@server1 kvm-vm]# virsh resume vm001
域 vm001 被重新恢复

[root@server1 kvm-vm]# virsh list --all
 Id    名称                         状态
----------------------------------------------------
 14    vm001                          running

[root@server1 kvm-vm]# virsh shutdown vm001
域 vm001 被关闭
```



## 4. 虚拟机的自启动

### 4.1 跟随KVM启动

【autostart】

```
[root@localhost kvm-vm]# virsh autostart vm001
域 vm003标记为自动开始
```

### 4.2 禁用自启动

【autostart --disable】

```
[root@localhost kvm-vm]# virsh autostart --disable vm001
域 vm003取消标记为自动开始
```



## 5. 虚拟机信息

### 5.1 virsh修改配置文件

```
[root@localhost ~]# virsh edit vm001
```

### 5.2 查看虚拟机信息

```
[root@localhost ~]# virsh dominfo vm001
Id:             -
名称：       vm001
UUID:           c543a6a9-d9d5-447a-bca3-6039ac6c39ef
OS 类型：    hvm
状态：       关闭
CPU：          1
最大内存： 1048576 KiB
使用的内存： 1048576 KiB
持久：       是
自动启动： 禁用
管理的保存： 否
安全性模式： none
安全性 DOI： 0
```



## 6. KVM虚拟机克隆

### 6.1 virt-clone命令

1. 查看虚拟机状态

```
[root@kvm001 ~]# virsh list --all
 Id    名称                         状态
----------------------------------------------------
 -     vm001                          关闭
```

2. 从vm001克隆到vm002

```
[root@kvm001 ~]# virt-clone -o vm001 -n vm002 -f /kvm-vm/vm002.qcow2
```

3. 查看虚拟机状态

```
[root@kvm001 ~]# virsh list --all
```

4. 启动虚拟机

```
[root@kvm001 ~]# virsh start vm002
```

### 6.2 手动克隆

==**修改配置文件中的name，uuid，source和mac**==

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
    </console>
    <channel type='unix'>
      <target type='virtio' name='org.qemu.guest_agent.0'/>
      <address type='virtio-serial' controller='0' bus='0' port='1'/>
    </channel>
    <input type='mouse' bus='ps2'/>
    <input type='keyboard' bus='ps2'/>
    <memballoon model='virtio'>
      <address type='pci' domain='0x0000' bus='0x00' slot='0x07' function='0x0'/>
    </memballoon>
    <rng model='virtio'>
      <backend model='random'>/dev/urandom</backend>
      <address type='pci' domain='0x0000' bus='0x00' slot='0x08' function='0x0'/>
    </rng>
  </devices>
</domain>
```
