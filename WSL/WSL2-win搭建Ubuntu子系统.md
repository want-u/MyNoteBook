# WSL2-win搭建Ubuntu子系统

https://www.jianshu.com/p/fcf21d45ea74

[toc]

在win10上使用Ubuntu除了使用虚拟机外，还有一种官方支持的Linux子系统模式。子系统上的流畅度比虚拟机高出了不知多少！
废话不多说，下面开始搭建~！

目前官方已更新wls2版本[→官方教程传送门←](https://links.jianshu.com/go?to=https%3A%2F%2Fdocs.microsoft.com%2Fzh-cn%2Fwindows%2Fwsl%2Finstall-win10)

## 搭建Ubuntu子系统

#### 开启开发者模式

系统设置 -> 更新和安全 -> 针对开发人员 -> 选择开发者模式

![img](https://upload-images.jianshu.io/upload_images/14867887-ae25fff5199a11bc.png?imageMogr2/auto-orient/strip|imageView2/2/w/606/format/webp)

image.png

#### 启用 Linux 子系统组件

系统设置 -> 应用 -> 右侧的程序和功能 -> 启动或关闭windows功能 -> 勾选适用于 Linux 的 Windows 子系统
设置完成后重启更新即可

![img](https://upload-images.jianshu.io/upload_images/14867887-3d7c1e9d7db9a43c.png?imageMogr2/auto-orient/strip|imageView2/2/w/1122/format/webp)

image.png

#### 安装 Linux 子系统

打开 Windows 应用市场，输入 linux 搜索，选择你自己想要的系统版本，我选择的是 Ubuntu ，然后下载安装。



![img](https://upload-images.jianshu.io/upload_images/14867887-5316fcc0128d3725.png?imageMogr2/auto-orient/strip|imageView2/2/w/861/format/webp)

#### mobaterm访问

MobaXTerm：Terminal - Colors scheme : Dark background / Light text

```
1、重启即可加载wsl会话
2、设置root密码： sudo passwd
```


## 其他问题

#### 启动Ubuntu报错


```
WslRegisterDistribution failed with error: 0x80370102 Error: 0x80370102 ???????????????????
```

可能是wls 设置的是 wls2（我这里是家庭版，默认没有开启Hyper-V功能）

只需要在powershell 终将 wsl --set-default-version 1 默认安装WSL1

```
wsl --set-default-version 1
```


就解决了我的问题，

如果你们遇到其他问题了可以参考一下文章:

WSL安装Linux报错WslRegisterDistribution failed with error: 0x80370102-PHP基础-自如初个人博客

#### Wsl2-启动报错


```
请启用虚拟机平台 Windows 功能并确保在 BIOS 中启用虚拟化。
```

在windows功能中勾选hyper-v

![image](https://img-blog.csdnimg.cn/img_convert/e944ed4016f9e1ca7107ad17b7093c8f.png)

注：若家庭版功能没有hyper-v，参考这里

- https://www.cnblogs.com/guangzhou11/p/11622212.html

重启电脑

然后开启hyper-v模式
在管理员powershell中执行

```
bcdedit /set hypervisorlaunchtype auto
```

#### Wsl2-指定用户

使用管理员打开CMD（找回root密码），运行：


```
wsl --user root
```

#### Wsl2-更新源

1. 备份原有软件源文件

```
sudo cp /etc/apt/sources.list /etc/apt/sources.list.bak_yyyymmdd
```

2. 修改sources.list文件

```
# 修改为如下地址：
sudo vi /etc/apt/sources.list
```

```
#163源
deb http://mirrors.163.com/ubuntu/ bionic main restricted universe multiverse
deb http://mirrors.163.com/ubuntu/ bionic-security main restricted universe multiverse
deb http://mirrors.163.com/ubuntu/ bionic-updates main restricted universe multiverse
deb http://mirrors.163.com/ubuntu/ bionic-proposed main restricted universe multiverse
deb http://mirrors.163.com/ubuntu/ bionic-backports main restricted universe multiverse
deb-src http://mirrors.163.com/ubuntu/ bionic main restricted universe multiverse
deb-src http://mirrors.163.com/ubuntu/ bionic-security main restricted universe multiverse
deb-src http://mirrors.163.com/ubuntu/ bionic-updates main restricted universe multiverse
deb-src http://mirrors.163.com/ubuntu/ bionic-proposed main restricted universe multiverse
deb-src http://mirrors.163.com/ubuntu/ bionic-backports main restricted universe multiverse
```

3. 更新系统软件源

```
# 执行命令，更新系统软件源地址：
sudo apt-get update
sudo apt-get upgrade
```

```
# 安装lrzsz tree
sudo apt-get -y install lrzsz tree
# 安装 build-essential 软件包集合 （其中就包括 gcc、G ++ 和 make 等）
sudo apt install build-essential
```

#### Wsl2-sudo

每次使用 `sudo` 时都需要输入密码确实烦人, 毕竟是私人电脑, 安全性有锁屏密码保护就可以了, 为了使用方便, 不仿取消使用 `sudo` 时需要输入 `root` 用户密码的设定:

同时按下 `ctrl` + `alt` + `t` 打开终端, 输入 `sudo vi /etc/sudoers` , 在打开的文件中, 将

```
%sudo ALL=(ALL:ALL) ALL
```

改为：

```shell
%sudo ALL=(ALL:ALL) NOPASSWD:ALL
```

即可。

#### Wsl2-安装docker

1、接下来添加Docker源

```
curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo apt-key add -

sudo add-apt-repository \
   "deb [arch=amd64] https://mirrors.tuna.tsinghua.edu.cn/docker-ce/linux/ubuntu \
   $(lsb_release -cs) \
   stable"

sudo apt update
```

2、安装Docker

```
sudo apt install -y docker-ce

sudo service docker start
sudo service docker status
sudo docker --version
Docker version 20.10.12, build e91ed57

sudo docker images
sudo docker ps
```

3、国内镜像源

```
sudo vim /etc/docker/daemon.json

{
    "log-driver": "json-file",
    "log-opts": {"max-size": "50m", "max-file": "3"},
    "registry-mirrors": ["https://8p2ftmks.mirror.aliyuncs.com", "http://hub-mirror.c.163.com"]
}

sudo service docker restart
sudo docker info
```

3、安装Docker-compose

```
# 1. 下载或上传docker-compose文件
sudo curl -L "https://github.com/docker/compose/releases/download/1.28.4/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose

# 2. 赋权
sudo chmod +x /usr/local/bin/docker-compose

# 3. 查看版本
docker-compose --version
docker-compose version 1.28.4, build 1110ad01

# 4. 查看编排
docker-compose ps
```


#### Wsl/wsl2 更换虚拟硬盘位置(导出,导入)

windows wsl子系统默认硬盘存放位置在c盘，c盘一般分区都不会太大，就需要迁移虚拟硬盘来解决，网上有较多建软链的方式，这里不再记录，只叙述打包导出导入的方式



  1.关闭wsl

```bash
wsl --shutdown
```

  2.查看wsl信息

```bash
  wsl -l -v
  NAME            STATE           VERSION
* Ubuntu-18.04    Running         1
  Ubuntu-20.04    Running         2
```

  3.导出虚拟机 wsl --export <DistroName> <PathToTarArchive>

```bash
wsl --export Ubuntu-18.04 d:\WslDir\wsl-ubuntu18.04.tar
```

  4.注销原有虚拟机 wsl --unregister <DistroName>

```bash
wsl --unregister Ubuntu-18.04
```

  5.导入虚拟机至新位置 wsl --import <DistroName> <PathToDistroNewDirectory> <PathToTarArchive>

```bash
wsl --import Ubuntu-18.04 d:\WslSys\ d:\WslDir\wsl-ubuntu18.04.tar  --version 2
```

  6.设置默认用户（这里直接指定原有虚拟机存在用户即可）

```bash
ubuntu1804 config --default-user luoxian
```


#### Wsl2-快照&回滚(与上一步骤相同)

前言 提示：阿里云ECS服务器上对磁盘的快照与回滚能否用在WSL2上呢？下面我们来实践一下，以下例子是基于WSL2，同理应适用于WSL。

提示：以下是本篇文章正文内容，下面案例可供参考

一、查看已安装的系统 在win10终端或powershell里面键入：


```
wsl -l -v
```

示例：为Ubuntu-18.04系统做快照

二、做快照 代码如下（示例）：


```
wsl --export Ubuntu-18.04 d:\WslDir\wsl-ubuntu18.04.tar
```

三.回滚 1.注销当前系统


```
wsl --unregister Ubuntu-18.04
```

2.回滚


```
wsl --import Ubuntu-18.04 d:\WslSys d:\WslDir\wsl-ubuntu18.04.tar --version 2
```

3.设置默认登陆用户为安装时用户名


```
ubuntu1804 config --default-user luoxian
```

如果是ubuntu20.04，命令ubuntu1804改为ubuntu2004即可；USERNAME是登录用户名称，如Raymond

总结：
以上就是在WSL2做快照与回滚的步骤，很适合在本地反复开发测试。上述方法可做成快照和回滚两个脚本，就会更方便了。

#### Wsl2-关闭

不使用WSL2的时候我们可以关闭它以节省内存

使用管理员打开CMD，运行：

```shell
wsl --shutdown
```

开始菜单会出现Ubuntu菜单项。打开它即开机

