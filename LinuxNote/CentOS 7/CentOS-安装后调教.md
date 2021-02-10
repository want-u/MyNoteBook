## CentOS-安装后调教

### CentOS7安装wine环境

https://blog.csdn.net/yannantian198656/article/details/

------



### NTFS挂载：

https://my.oschina.net/jacochan/blog/

#### 关闭win10的快速启动。

系统的设置-电源管理-关闭快速启动

#### 安装ntfs-3g和ntfsprogs 

```
 yum install ntfs-3g ntfsprogs
```



#### 查找NTFS分区

```
fdisk -l | grep NTFS
```

根据显示的NTFS分区列表依次执行 ntfsfix /dev/sdb序号，比如：

```
ntfsfix /dev/sdb
```

然后挂载即可

```
echo "/dev/sdb2 /home/luoxian/Tuan ntfs-3g defaults 0 0" >> /etc/fstab
mount -a
```

win引导修复

```
grub2-mkconfig -o /boot/grub2/grub.cfg
```



--------------------------------------------------------------------------------


### GNOME Classic界面：

https://www.jianshu.com/p/618cad6a74d
系统启动后

    单击位于"Sign In"按钮旁边的按钮。
    在列表中选择"GNOME"。 (默认是GNOME Classic)
    单击"Sign In"并使用GNOME Shell登录。

------

### Edge：


https://www.microsoftedgeinsider.com/en-us/download

--------------------------------------------------------------------------------
-------------

### 安装录屏软件simplescreenrecorder

#### 下载num-dextop最新版本

打开http://li.nux.ro/download/nux/dextop/el7/x86_64/，。

我下载的版本为：
[http://li.nux.ro/download/nux/dextop/el7/x86_64/nux-dextop-release-0-](http://li.nux.ro/download/nux/dextop/el7/x86_64/nux-dextop-release-0-5.el7.nux.noarch.rpm)

#### 安装nux－dextop

```
sudo rpm -Uvh nux-dextop-release*rpm
```

#### 安装simplescreenrecorder

```
sudo yum install simplescreenrecorder
```



### centos播放视频

#### 安装epel、elrepo、nux-dextop源

epel地址：

```
wget -O /etc/yum.repos.d/epel.repo http://mirrors.aliyun.com/repo/epel-7.repo
```

elrepo地址:

```
rpm -Uvh https://www.elrepo.org/elrepo-release-7.0-3.el7.elrepo.noarch.rpm
```

nux-dextop地址：

```
yum -y install epel-release && rpm -Uvh http://li.nux.ro/download/nux/dextop/el7/x86_64/nux-dextop-release-0-5.el7.nux.noarch.rpm
```

#### root账户下安装以下解码器：

```
yum -y install vlc gstreamer-ffmpeg gstreamer-plugins-ugly gstreamer-plugins-bad ffmpeg libvdpau mpg123 mplayer mplayer-gui gstreamer1-libav 
```




#### 重启系统。


