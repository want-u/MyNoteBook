# 快速搭建 kvm web 管理工具 WebVirtMgr

https://www.cnblogs.com/cnsre/p/15566077.html

[toc]

`WebVirtMgr`是近两年来发展较快，比较活跃，非常清新的一个KVM管理平台，提供对宿主机和虚机的统一管理，它有别于kvm自带的图形管理工具（virtual machine manager），让`kvm`管理变得更为可视化，对中小型`kvm`应用场景带来了更多方便。

## WebVirtMgr介绍

WebVirtMgr采用几乎纯Python开发，其前端是基于Python的Django，后端是基于Libvirt的Python接口，将日常kvm的管理操作变的更加的可视化。

- WebVirtMgr 特点

操作简单，易于使用 、通过libvirt的API接口对kvm进行管理、提供对虚拟机生命周期管理

- WebVirtMgr 功能

宿主机管理支持以下功能、CPU利用率、内存利用率、网络资源池管理、存储资源池管理、虚拟机镜像、虚拟机克隆、快照管理、日志管理、虚机迁移、虚拟机管理支持以下功能、CPU利用率、内存利用率、光盘管理、关/开/暂停虚拟机、安装虚拟机、VNC console连接、创建快照

### 官方文档

https://github.com/retspen/webvirtmgr/wiki/Install-WebVirtMgr

## 安装前的部署

安装一些依赖包



```shell
yum -y install git python-pip libvirt-python libxml2-python python-websockify supervisor nginx gcc python-devel wget vim net-tools lrzsz 
```

### 安装pip



```shell
wget https://bootstrap.pypa.io/get-pip.py 
python get-pip.py 
pip -V　
```

![sre运维|Linux运维|自动化运维|关键词](https://gitee.com/xsre/ImagesHosting/raw/master/cnsre/20211117094212.png)



```shell
pip install numpy
```

![sre运维|Linux运维|自动化运维|关键词](https://gitee.com/xsre/ImagesHosting/raw/master/cnsre/20211117094233.png)

### 安装python的需要包和配置Django环境



```shell
git clone git://github.com/retspen/webvirtmgr.git
```

![sre运维|Linux运维|自动化运维|关键词](https://gitee.com/xsre/ImagesHosting/raw/master/cnsre/20211117094257.png)

### 安装nginx



```shell
rpm -Uvh http://nginx.org/packages/centos/7/noarch/RPMS/nginx-release-centos-7-0.el7.ngx.noarch.rpm 
yum install nginx -y
```

### 安装supervisor

安装参考

https://www.2cto.com/kf/201712/702837.html

开机自启参考

https://blog.csdn.net/binggoogle/article/details/53203991



```shell
cat  /etc/supervisord.conf
```

{{< alert theme="warning" dir="ltr" >}}
⚠️ 注意

**如果没有这个文件按照一下步骤安装**

**有的话忽略此步骤**
{{< /alert >}}



```shell
pip install supervisor
mkdir /etc/supervisord.d/
echo_supervisord_conf > /etc/supervisord.conf
```

新建文件夹



```shell
vim /etc/supervisord.d/app.conf
```

配置文件 app.conf

内容为



```shell
[program:appname]
command=/root/soft/push.api
directory=/root/soft/push.api
autostart=true
autorestart=true
user=root
stdout_logfile = /var/log/supervisor/pushapi.log
stderr_logfile = /var/log/supervisor/pushapi-error.log
```

修改 在配置文件最下方修改为



```shell
vim  /etc/supervisord.conf
[include]
files = /etc/supervisord.d/*.ini
```

![sre运维|Linux运维|自动化运维|关键词](https://gitee.com/xsre/ImagesHosting/raw/master/cnsre/20211117094630.png)



```shell
supervisord -c /etc/supervisord.conf
/usr/bin/supervisorctl start all
/usr/bin/supervisorctl stop all
```



## 安装环境



```shell
cd webvirtmgr 
pip install -r requirements.txt 
```

![sre运维|Linux运维|自动化运维|关键词](https://gitee.com/xsre/ImagesHosting/raw/master/cnsre/20211117094713.png)



```shell
./manage.py syncdb
```

![sre运维|Linux运维|自动化运维|关键词](https://gitee.com/xsre/ImagesHosting/raw/master/cnsre/20211117094735.png)

### 创建用户

输入以下用户信息



```shell
You just installed Django's auth system, which means you don't have any superusers defined.
Would you like to create one now? (yes/no): yes
Username (leave blank to use 'root'): admin
Email address: 275301281@qq.com
Password: admin
Password (again):admin 
Superuser created successfully.
Installing custom SQL ...
Installing indexes ...
Installed 6 object(s) from 1 fixture(s)
./manage.py collectstatic
```

### 配置一个超级用户



```shell
./manage.py createsuperuser
```

![sre运维|Linux运维|自动化运维|关键词](https://gitee.com/xsre/ImagesHosting/raw/master/cnsre/20211117094814.png)



```shell
WARNING:root:No local_settings file found.
Username (leave blank to use 'root'): yes
Email address: 275301281@qq.com
Password: Lenovo@123
Password (again): Lenovo@123
Superuser created successfully.
```

### 设置nginx

a、使用:8000端口

#### 移动这个 `webvirtmgr` 目录到 `/var/www` 下



```shell
cd  ..
mv webvirtmgr /var/www/
```

{{< alert theme="warning" dir="ltr" >}}
⚠️ **注意：**
**webvirtmgr 目录下还有一个名称为webvirtmgr 的文件夹**
**不要单独移动 webvirtmgr/webvirtmgr 文件**
{{< /alert >}}

![sre运维|Linux运维|自动化运维|关键词](https://gitee.com/xsre/ImagesHosting/raw/master/cnsre/20211117095023.png)

#### 编辑配置文件



```shell
vim /etc/nginx/conf.d/webvirtmgr.conf

server {
    listen 80 default_server;

    server_name $hostname;
    #access_log /var/log/nginx/webvirtmgr_access_log; 

    location /static/ {
        root /var/www/webvirtmgr/webvirtmgr; # or /srv instead of /var
        expires max;
    }

    location / {
        proxy_pass http://127.0.0.1:8000;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-for $proxy_add_x_forwarded_for;
        proxy_set_header Host $host:$server_port;
        proxy_set_header X-Forwarded-Proto $scheme;
        proxy_connect_timeout 600;
        proxy_read_timeout 600;
        proxy_send_timeout 600;
        client_max_body_size 1024M; # Set higher depending on your needs 
    }
}
```

#### 启动nginx并设置开机自启动

(如果不设置开机自启动，重启服务器supervisor无法管理Django进程)，并开机自启动supervisord



```shell
/etc/init.d/nginx start
```

或者



```shell
systemctl restart   nginx 
systemctl enable supervisord 
```

分配权限



```shell
chown nginx.nginx /var/www/webvirtmgr
```

### 设置supervisor

在`/etc/supervisord.conf`末尾加入下面的配置：



```shell
vi /etc/supervisord.conf


[program:webvirtmgr]
command=/usr/bin/python /var/www/webvirtmgr/manage.py run_gunicorn -c /var/www/webvirtmgr/conf/gunicorn.conf.py
directory=/var/www/webvirtmgr
autostart=true
autorestart=true
logfile=/var/log/supervisor/webvirtmgr.log
log_stderr=true
user=nginx

[program:webvirtmgr-console]
command=/usr/bin/python /var/www/webvirtmgr/console/webvirtmgr-console
directory=/var/www/webvirtmgr
autostart=true
autorestart=true
#stdout_logfile=/var/log/supervisor/webvirtmgr-console.log
redirect_stderr=true
user=nginx	
```

{{< alert theme="warning" dir="ltr" >}}
⚠️ **注意**

**进程无法启动或者报错 可以选择吧 log 注释取消**
{{< /alert >}}

#### 重启supervisord

开机自启参考

https://blog.csdn.net/binggoogle/article/details/53203991

设置完之后重启即可



```shell
systemctl restart  supervisord.service
systemctl enable  supervisord.service
systemctl status   supervisord.service 
```

![sre运维|Linux运维|自动化运维|关键词](https://gitee.com/xsre/ImagesHosting/raw/master/cnsre/20211117095311.png)

#### 更新



```shell
cd /var/www/webvirtmgr git pull
```

![sre运维|Linux运维|自动化运维|关键词](https://gitee.com/xsre/ImagesHosting/raw/master/cnsre/20211117095320.png)



```shell
./manage.py collectstatic
```

![sre运维|Linux运维|自动化运维|关键词](https://gitee.com/xsre/ImagesHosting/raw/master/cnsre/20211117095327.png)



```shell
systemctl  restart supervisord
```

#### 如果有错误或不运行



```shell
 ./manage.py runserver 0:8000
#或者后台运行脚本
nohup python  /var/www/webvirtmgr/manage.py runserver 0:8000  >/dev/null &
nohup python  /var/www/console/webvirtmgr-console   >/dev/null &
```

访问：[http://x.x.x.x:8000](https://cnsre.cn/)(x.x.x.x - your server IP address )，输入创建的用户和密码，如果没有创建，请用python manager.py createsuperuser,命令创建。登录后如下图所示

![sre运维|Linux运维|自动化运维|关键词](https://gitee.com/xsre/ImagesHosting/raw/master/cnsre/20211117095541.png)

配置虚拟机所在宿主机

webvirtmgr客户端就这样搭建完了，接下来需要配置虚拟机所在宿主机的，参考git地址.

## 配置宿主机

### 下载并执行脚本

如果虚拟机比较多，该脚本执行时间会比较长，因为会执行 `service libvirt-guests restart`，会将所有运行的虚拟机挂起然后再恢复，感觉这一步不是必须的，因为我有一台只设置ssh认证，也可以正常连接。



```shell
curl http://retspen.github.io/libvirt-bootstrap.sh | sudo sh
```

如果没有curl就用wget



```shell
wget -O - http://retspen.github.io/libvirt-bootstrap.sh | sudo sh
```

### 配置防火墙



```shell
iptables -I INPUT -m state --state NEW -m tcp -p tcp --dport 16509 -j ACCEPT
```

### 设置TCP授权

参考：[https://github.com/retspen/webvirtmgr/wiki/Setup-TCP-authorization](https://link.jianshu.com/?t=https://github.com/retspen/webvirtmgr/wiki/Setup-TCP-authorization)

webvirtmgr新建服务器连接时需要此账号

#### 用saslpasswd2命令给libvirt的用户cnsre设置密码



```shell
saslpasswd2 -a libvirt cnsre
Password: cnsre
Again (for verification): cnsre
```

#### 生成一个密码库



```shell
sasldblistusers2 -f /etc/libvirt/passwd.db 

cnsre@webvirtmgr.cn: userPassword
```

#### 设置ssh授权



```shell
ssh-keygen -t rsa       # 产生公私钥
```

直接回车，回车，回车

![sre运维|Linux运维|自动化运维|关键词](https://gitee.com/xsre/ImagesHosting/raw/master/cnsre/20211117100234.png)



```shell
ssh-copy-id 192.168.1.120  
```

{{< alert theme="warning" dir="ltr" >}}
⚠️ **注意**

**由于这里webvirtmgr和kvm服务部署在同一台机器，所以这里本地信任。**

**如果kvm部署在其他机器，那么这个是其他它的ip 同时也要设置ssh key密钥**
{{< /alert >}}

提示输入密码的时候直接输入之前1.120的密码

![sre运维|Linux运维|自动化运维|关键词](https://gitee.com/xsre/ImagesHosting/raw/master/cnsre/20211117100257.png)



```shell
ssh 192.168.1.120 -L localhost:8000:localhost:8000 -L localhost:6080:localhost:6080
```

### web 平台加入其他kvm宿主机

![sre运维|Linux运维|自动化运维|关键词](https://gitee.com/xsre/ImagesHosting/raw/master/cnsre/20211117100407.png)

在部署web管理的主机上执行命令



```shell
ssh-keygen -t rsa
```

然后在执行



```shell
ssh-copy-id 192.168.1.165
```

![sre运维|Linux运维|自动化运维|关键词](https://gitee.com/xsre/ImagesHosting/raw/master/cnsre/20211117100434.png)

添加新的kvm宿主机

![sre运维|Linux运维|自动化运维|关键词](https://gitee.com/xsre/ImagesHosting/raw/master/cnsre/20211117100443.png)

查看新加的kvm宿主机状态 看有无报错

![sre运维|Linux运维|自动化运维|关键词](https://gitee.com/xsre/ImagesHosting/raw/master/cnsre/20211117100449.png)

### 删除新加的账号



```shell
sudo saslpasswd2 -a libvirt -d cnsre
```

### 确认验证新加的账号配置



```shell
virsh -c qemu+tcp://IP_address/system nodeinfo
(virsh -c qemu+tcp://192.168.1.50/system nodeinfo)
Please enter your authentication name: cnsre
Please enter your password: xxxxxx
CPU model:           x86_64
CPU(s):              2
CPU frequency:       2611 MHz
CPU socket(s):       1
Core(s) per socket:  2
Thread(s) per core:  1
NUMA cell(s):        1
Memory size:         2019260 kB
```

{{< alert theme="warning" dir="ltr" >}}
⚠️ **注意**

**账号全名带hostname，如 [cnsre@webvirtmgr.cn](https://cnsre.cn/)**

**测试的时候这一步测试没有成功 但是可以链接**
{{< /alert >}}

### 设置ssh认证

{{< notice warning "注意" >}}
ssh和tcp设置一种即可，其实就是设置无密码登录，要注意的是从webvirtmgr的什么用户到宿主机的什么用户的无密码登录，比如我用root跑的django webvirtmgr，而宿主机也是root跑的virsh，所以需要设置root到root的无密码登录。而git官网推荐的是用nginx用户跑django webvirtmgr，webvirtmgr用户跑的virsh，所以设置的是nginx用户到宿主机webvirtmgr用户的无密码登录。
{{< /notice >}}

参考：[https://github.com/retspen/webvirtmgr/wiki/Setup-SSH-Authorizatio](https://link.jianshu.com/?t=https://github.com/retspen/webvirtmgr/wiki/Setup-SSH-Authorization)

### 使用tcp认证连接服务器

访问：[http://192.168.1.120:8000](https://cnsre.cn/)，xxxx是webvirtmgr的ip地址，点击new connection

### 填写kvm宿主机的一些信息

![sre运维|Linux运维|自动化运维|关键词](https://gitee.com/xsre/ImagesHosting/raw/master/cnsre/20211117100652.png)

基础架构可以看到一些vm虚拟机

![sre运维|Linux运维|自动化运维|关键词](https://gitee.com/xsre/ImagesHosting/raw/master/cnsre/20211117100700.png)

## KVM WEB管理常见报错

网页控制台 远程链接报错1006

安装vnc即可



```shell
yum install -y novnc
```

网页控制台 远程链接报错505



```shell
cd /var/www/console/
./webvirtmgr-console  &
```

后台运行脚本



```shell
nohup python  /var/www/webvirtmgr/manage.py runserver 0:8000  >/dev/null & 
nohup python  /var/www/console/webvirtmgr-console   >/dev/null &
```