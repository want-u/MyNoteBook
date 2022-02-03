# 启动和停止LNet

http://wiki.lustrefs.cn/index.php?title=%E5%90%AF%E5%8A%A8%E5%92%8C%E5%81%9C%E6%AD%A2LNet

[toc]

与Lustre文件系统中的大多数服务一样，LNet在Linux内核中运行，并作为内核模块被集成。LNet的启动分两步:

1. 加载内核模块
2. 开始服务

`lnet`内核模块可以通过`modprobe`命令直接加载，也可以通过加载一个依赖于Lnet的内核模块的方式间接加载。在正常操作中，`lnet`模块一般是随着启动一个Lustre服务（例如在客户端上挂载一个文件系统）被间接加载的。但LNet 也可被视为独立于Lustre的模块，并自行启动。这对于测试和调试很有用。还可以在系统启动时、和提交加载更高级别的服务(如Lustre )之前，提供一些正确性验证。

要加载LNet 内核模块，可以运行:

```
modprobe [-v] lnet
```

其中的`-v`标志是可选的，它提供详细的输出，虽然这对于调试很有用，但通常会省略。例如:

```
[root@rh7z-pe ~]# modprobe -v lnet
insmod /lib/modules/3.10.0-327.13.1.el7_lustre.x86_64/extra/kernel/net/lustre/libcfs.ko 
insmod /lib/modules/3.10.0-327.13.1.el7_lustre.x86_64/extra/kernel/net/lustre/lnet.ko networks="tcp0(eth1)" 
```

请注意，上例中还加载了一个名为`libcfs.ko`的模块，`libcfs`模块是一个Lustre和LNet之间使用的API，它为进程管理、内存管理和调试等功能提供原语。

加载模块后，需要启动LNet服务:

```
lctl network up
# or
lctl network configure
```

`lctl network`命令适用于Lustre的所有版本，在2.7版本之前，这是手动启动LNet的唯一方式。在Lustre 2.7及更高版本中，增加了`lnetctl`工具:

```
lnetctl lnet configure [--all]
```

`lnetctl configure`命令不会根据内核模块参数自动配置网络，该命令会启动 `lnet`服务，但是不会配置接口。使用`--all`标志会加载和启动所有被定义为内核模块选项的网络。

要查看加载的配置:

```
lctl list_nids

# or for dynamic lnet in Lustre 2.7+
lnetctl net show [--verbose]
lnetctl export
```

`lnetctl export`命令相当于`lnetctl net show –verbose`命令。

要关闭LNet并卸载这个内核模块，需要先停止主机上的LNet网络:

```
lctl network down
# or 
lctl network unconfigure
```

然后使用`lustre_rmmod`命令卸载这个内核模块:

```
lustre_rmmod
```

也可以直接使用`rmmod`卸载模块:

```
rmmod lnet
rmmod libcfs
```

推荐使用`lustre_rmmod`卸载lustre和LNet内核模块，因为它会检查依赖性，并且不需要系统管理员识别出所有要卸载的模块以及确定它们的卸载顺序。

LNet也可以作为`lustre`内核模块的依赖项间接被加载。如果以这种方式加载LNet，其启动行为会有所不同，因为内核模块选项中定义的LNet网络会自动配置并联网。这一点通过加载Lustre模块的例子很容易说明:

```
[root@rh7z-pe ~]# modprobe -v lustre
insmod /lib/modules/3.10.0-327.13.1.el7_lustre.x86_64/extra/kernel/net/lustre/libcfs.ko 
insmod /lib/modules/3.10.0-327.13.1.el7_lustre.x86_64/extra/kernel/net/lustre/lnet.ko networks="tcp0(eth1)" 
insmod /lib/modules/3.10.0-327.13.1.el7_lustre.x86_64/extra/kernel/fs/lustre/obdclass.ko 
insmod /lib/modules/3.10.0-327.13.1.el7_lustre.x86_64/extra/kernel/fs/lustre/ptlrpc.ko 
insmod /lib/modules/3.10.0-327.13.1.el7_lustre.x86_64/extra/kernel/fs/lustre/fld.ko 
insmod /lib/modules/3.10.0-327.13.1.el7_lustre.x86_64/extra/kernel/fs/lustre/fid.ko 
insmod /lib/modules/3.10.0-327.13.1.el7_lustre.x86_64/extra/kernel/fs/lustre/lov.ko 
insmod /lib/modules/3.10.0-327.13.1.el7_lustre.x86_64/extra/kernel/fs/lustre/mdc.ko 
insmod /lib/modules/3.10.0-327.13.1.el7_lustre.x86_64/extra/kernel/fs/lustre/lmv.ko 
insmod /lib/modules/3.10.0-327.13.1.el7_lustre.x86_64/extra/kernel/fs/lustre/lustre.ko
```

请注意，`lnet.ko`模块是作为依赖项加载的。控制台和内核环形缓冲区的输出如下所示:

```
[266699.213610] LNet: HW CPU cores: 2, npartitions: 1
[266699.232630] alg: No test for adler32 (adler32-zlib)
[266699.234184] alg: No test for crc32 (crc32-table)
[266707.286906] Lustre: Lustre: Build Version: jenkins-arch=x86_64,build_type=server,distro=el7,ib_stack=inkernel-40--PRISTINE-3.10.0-327.13.1.el7_lustre.x86_64
[266707.338890] LNet: Added LNI 192.168.207.2@tcp [8/256/0/180]
[266707.339851] LNet: Accept secure, port 988
```

从上例中的输出可以看出，LNet网络是自动加载的。

在这种情况下`lustre_rmmod`的动作，会与单独加载LNet的情况有所不同。如果管理员独立于Lustre模块自行加载和配置LNet，那么在移除Lustre模块之前，必须要手动取消LNet网络:

```
[root@rh7z-pe ~]# modprobe lnet
[root@rh7z-pe ~]# lctl network up
LNET configured
[root@rh7z-pe ~]# lctl list_nids
192.168.207.2@tcp
[root@rh7z-pe ~]# lustre_rmmod
Modules still loaded: 
lnet/klnds/socklnd/ksocklnd.o lnet/lnet/lnet.o libcfs/libcfs/libcfs.o
[root@rh7z-pe ~]# lctl network down
LNET ready to unload
[root@rh7z-pe ~]# lustre_rmmod
[root@rh7z-pe ~]# lsmod |grep lnet
```

但是，如果`lnet`模块是作为Lustre内核模块的依赖项而被间接加载的，那么`lustre_rmmod`将正常卸载所有模块，包括`lnet`:

```
[root@rh7z-pe ~]# modprobe lustre
[root@rh7z-pe ~]# lctl list_nids
192.168.207.2@tcp
[root@rh7z-pe ~]# lustre_rmmod
[root@rh7z-pe ~]# lsmod | grep -E lnet\|lustre
```

虽然这种行为是前后一致的，但不完全直观的。会出现这种行为的原因与网络的一个特殊功能有关: 路由。LNet路由允许连接到多个网络结构的节点在不同网络之间转发信息。LNet路由是一个复杂的话题，本节不讨论。有关网络路由器的详细信息，请参阅:

http://www.intel.com/content/www/us/en/software/configuring-lnet-routers-file-systems-lustre-guide.html

因为路由是网络层的功能，而不是Lustre文件系统本身的功能，所以`lustre_rmmod`假定，如果一台主机只加载并运行了`lnet`模块，那么该模块正在提供路由服务。因此`lustre_rmmod`将拒绝卸载`lnet`模块，除非`lnet`服务被明确取消了。

另一方面，如果是lustre内核模块被加载，并且没有挂载文件系统，那么`lustre_rmmod`会假设主机是空闲服务器或客户端，所以会卸载整个堆栈，包括`lnet`模块。

如果有一个Lustre OSD挂载在主机上，那么`lustre_rmmod`命令无法卸载Lustre内核模块，并将报告错误:

```
[root@rh7z-mds1 ~]# df -ht lustre
File system      Size  Used Avail Use% Mounted on
mgspool/mgt     960M  2.2M  956M   1% /lfs/mgt

[root@rh7z-mds1 ~]# lustre_rmmod
  0 UP osd-zfs MGS-osd MGS-osd_UUID 5
  1 UP mgs MGS MGS 7
  2 UP mgc MGC192.168.227.11@tcp1 c2108a9c-a62f-6626-48e4-68f1caf1bce3 5
Modules still loaded: 
lustre/mgs/mgs.o lustre/mgc/mgc.o lustre/quota/lquota.o lustre/fid/fid.o lustre/fld/fld.o lnet/klnds/socklnd/ksocklnd.o lustre/ptlrpc/ptlrpc.o lustre/obdclass/obdclass.o lnet/lnet/lnet.o libcfs/libcfs/libcfs.o

[root@rh7z-mds1 ~]# df -ht lustre
File system      Size  Used Avail Use% Mounted on
mgspool/mgt     960M  2.2M  956M   1% /lfs/mgt
```

从这个例子中可以看出，由于已经挂载了MGS，`lustre_rmmod`不会移除内核模块。相反，它会显示出主机上正在运行的活动服务，并退出该命令。MGT仍处于挂载状态，MGS也处于在运行状态。`lustre_rmmod`命令是确保正确安全卸载lustre内核模块的非常有用的工具。