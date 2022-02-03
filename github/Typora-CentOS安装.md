# Typora-CentOS安装

## 1. 下载Typora

进入官网：https://www.typora.io/

找到Download/Linux，注意Centos不是使用的apt管理程序，因此需要直接下载二进制文件，点击binary file即可打开网页开始下载(或者复制连接到地址窗口)。

## 2. 安装Typora

安装依赖libXScrnSaver


```
yum install libXScrnSaver-devel.x86_64
```


下载的文件名为Typora-linux-x64.tar.gz，将该文件解压，并将解压出的文件复制到/opt目录下。

```
tar -xvzf Typora-linux-x64.tar.gz
sudo cp bin/Typora-linux-x64/ /opt/Typora -R
```

将Typora加入环境PATH中，修改自己用户下的.bashrc即可

```
cd ~
sudo vim .bashrc
```

在最后加入以下内容，保存即可。

```
#Typora
export PATH=$PATH:/opt/Typora
```

此时重新启动终端，输入Typora即可打开软件。此时可能会出现提示

```
[3469:1122/213956.912137:FATAL:setuid_sandbox_host.cc(157)] The SUID sandbox helper binary was found, but is not configured correctly. Rather than run without sandboxing I'm aborting now. You need to make sure that /opt/Typora/chrome-sandbox is owned by root and has mode 4755.
```

代表chrome-sandbox所属用户组和权限不正确。在命令窗口中输入以下内容

```
sudo chown root /opt/Typora/chrome-sandbox
sudo chmod 4755 /opt/Typora/chrome-sandbox
```

然后Typora即可运行。

## 3. 添加菜单项

```
cd /usr/share/applications
vim typora.desktop
```

```
[Desktop Entry]
#写自己的Typora版本
Version=9.3.4
Name=Typora
Exec=/opt/Typora/Typora  %U
Icon=/opt/Typora/resources/app/asserts/icon/icon_128x128.png
Type=Application
Terminal=false
```