## CentOS-Win10双系统

1. 第一部分：制作u盘启动盘
2. 第二部分：安装centos

假设电脑上已经装好win10系统；步骤如下：

------

### 第一部分：制作u盘启动盘

第一步：下载centos的iso文件，可以去官网下载；
第二步：下载UltraISO(软碟通)软件到电脑上，并安装好，可以先试用或者网上找个注册码注册一
下；
第三步：点击菜单栏的“打开”按钮）；
第四步：找到下载好的 centOS ISO镜像文件，选定该文件打开：
第五步：看下图：

第六步：确认无误后，点击“写入”。如提示不能写入，则先点击“格式化”，再点击“写入”。

等待写入完成......点击返回。关闭软碟通。

【至此，centOS系统安装U盘就制作好了。】

第七步：点击我的电脑，右键"管理",然后选择"磁盘管理"：

第八步：进入磁盘管理后，将磁盘分分区出空白分区来

例如，D盘还有100g的空白区，可以右键然后删除卷，选择50g删除，如果D盘不够，可以从其
他盘分一点空白分区。最后在安装时，空白的分区会自动放在一起；


### 第二部分：安装centos

#### 重启

按F12(不同电脑不同按键)出现如下界面：这个时候有些电脑可能会出现

USB的legacy和 uefi两种安装导向，如果选择legacy,则会出现如下界面：

此时按下Tab键,会出现如下界面：

看到最下面的一行英文，然后做如下修改：将命令修改为：>vmlinuz initrd=initrd.img linux
dd quiet 改好之后回车，然后就会列出你的设备列表了，在这个列表里面，不懂
Linux的我都能很清楚的辨认哪个是我的U盘，不信你看下面这张图：

很明显，sdc4就是我的U盘了，当然你得看看你自己的到底是什么，然后记下来之后就可以直接关机了(因为我曾经试着在这里按提示输入序号，没想到之后是个死循环，不知道是哪里出错了还是怎么地，所以我后来直接关机)。

#### 重启之后

跟1一样，再次按tab键，然后将底下的命令改成：

```
>vmlinuz initrd=initrd.img inst.stage2=hd:/dev/sdc4 quiet
注意sdc4,当然不同的电脑u盘可能不一样。
```
改好后回车，然后就是安装界面了；


