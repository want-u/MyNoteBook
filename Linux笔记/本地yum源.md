# centos7 配置本地yum源
https://blog.csdn.net/no_ob/article/details/78668255

准备工作

        虚拟机版本——VMware Workstation Pro 12.x
        系统版本——CentOS-7-x86_64-DVD-1511.iso
        系统安装完毕，正常登录 

一、挂载镜像

1.虚拟机方式

1）操作之前请将以下虚拟机设置处请勾选，该选项是需要系统开机之后才能勾选。

2）挂载镜像

    mount /dev/sr0 /mnt

2.非虚拟机方式

1）上传镜像文件 CentOS-7-x86_64-DVD-1511.iso

2）挂载镜像

    mount CentOS-7-x86_64-DVD-1511.iso /mnt

二、备份原yum源配置

    cd /etc/yum.repos.d 
    mkdir bak && mv * bak

三、创建本地yum源配置文件

cp bak/CentOS-Base.repo CentOS-Base.repo

```
[base-local]        #这个是本源的名字，不能和其他的重复（随便，不重复）
name=CentOS-local   #名字（随便）
baseurl=file:///mnt #上方步骤一挂载镜像创建的目录
enabled=1           #yum源是否启用 1-启用 0-不启用
gpgcheck=1          #安全检测 1-开启 0-不开启
```

注意：以上是CentOS-local.repo文件配置内容，每一项对应的注释说明需删除（我写出来只是为了供读者看^_^），不删除会遇见以下错误（估计是 centos在这个文件中不支持注释吧。或者是注释的位置不对。）

四、更新yum源配置

    yum clean all
    yum makecache

五、测试yum命令
    
    yum install nginx