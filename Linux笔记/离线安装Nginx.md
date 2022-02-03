# CentOS7离线安装Nginx

- https://www.cnblogs.com/sanluorenjian/p/13189999.html
- https://blog.csdn.net/xiaoxiaokeren/article/details/106879773

[toc]

离线安装nginx

## **安装环境**

操作系统：centos7(CentOS-7-x86_64-DVD-1810.iso)

联网状态：无互联网-内网

一、准备环境包gcc gcc-c++

二、准备准备离线安装包

- openssl
- zlib
- pcre
- nginx

 
推荐大家下载包的网站：

- https://centos.pkgs.org/
- http://mirrors.aliyun.com/centos/7/os/x86_64/Packages/
- http://rpmfind.net/linux/rpm2html/search.php
```
下载

方式1：如果有网的虚拟机还没有安装，可以直接

yum install --downloadonly --downloaddir=/root/soft/gcc gcc
yum install --downloadonly --downloaddir=/root/soft/gcc++ gcc-c++

方式2：如果有网的虚拟机已经安装过，可以

yum -y install yum-utils
yumdownloader --resolve --destdir=/root/soft/gcc gcc
yumdownloader --resolve --destdir=/root/soft/gcc++ gcc-c++
```


这两个网站都可以下载所需要的安装包，不同的就是版本差异，大家各取所取。

当执行下面的语句后，系统会自动选出所需的依赖包进行安装，不需要的就会自动清理。



```
# 强制安装(rpm or yum)
rpm -Uvh *.rpm --nodeps --force
yum localinstall -y *.rpm
# 强制卸载
rpm -e xxx.rpm --nodeps
```
## **1、安装gcc gcc-c++**

```
# http://mirror.centos.org/centos/7/os/x86_64/Packages/gcc-4.8.5-44.el7.x86_64.rpm
# http://mirror.centos.org/centos/7/os/x86_64/Packages/cpp-4.8.5-44.el7.x86_64.rpm

# http://mirror.centos.org/centos/7/os/x86_64/Packages/gcc-c++-4.8.5-44.el7.x86_64.rpm
# 正常安装，逐个查询依赖包
[root@localhost src]# rpm -Uvh gcc-4.8.5-44.el7.x86_64.rpm
error: Failed dependencies:
        cpp = 4.8.5-44.el7 is needed by gcc-4.8.5-44.el7.x86_64
        glibc-devel >= 2.2.90-12 is needed by gcc-4.8.5-44.el7.x86_64
        libgcc >= 4.8.5-44.el7 is needed by gcc-4.8.5-44.el7.x86_64
        libgomp = 4.8.5-44.el7 is needed by gcc-4.8.5-44.el7.x86_64
        libmpc.so.3()(64bit) is needed by gcc-4.8.5-44.el7.x86_64
        libmpfr.so.4()(64bit) is needed by gcc-4.8.5-44.el7.x86_64
# rpm -Uvh mpfr-3.1.1-4.el7.x86_64.rpm
# rpm -Uvh libmpc-1.0.1-3.el7.x86_64.rpm
# rpm -Uvh cpp-4.8.5-44.el7.x86_64.rpm
# rpm -Uvh glibc-common-2.17-317.el7.x86_64.rpm --nodeps --force
# rpm -Uvh glibc-2.17-317.el7.x86_64.rpm
# rpm -Uvh kernel-headers-3.10.0-1160.el7.x86_64.rpm
# rpm -Uvh glibc-headers-2.17-317.el7.x86_64.rpm
# rpm -Uvh glibc-devel-2.17-317.el7.x86_64.rpm
# rpm -Uvh libgcc-4.8.5-44.el7.x86_64.rpm
# rpm -Uvh libgomp-4.8.5-44.el7.x86_64.rpm
# rpm -Uvh gcc-4.8.5-44.el7.x86_64.rpm

# rpm -Uvh libstdc++-4.8.5-44.el7.x86_64.rpm
# rpm -Uvh libstdc++-devel-4.8.5-44.el7.x86_64.rpm
# rpm -Uvh gcc-c++-4.8.5-44.el7.x86_64.rpm

# 强制，不推荐
rpm -Uvh gcc-4.8.5-44.el7.x86_64.rpm --nodeps --force
rpm -Uvh gcc-c++-4.8.5-44.el7.x86_64.rpm --nodeps --force

# 最后就查询一下
gcc -v
g++ -v
```


## **2、安装pcre**


```
# http://mirror.centos.org/centos/7/os/x86_64/Packages/pcre-8.32-17.el7.x86_64.rpm
# http://mirror.centos.org/centos/7/os/x86_64/Packages/pcre-devel-8.32-17.el7.x86_64.rpm


# 由于机器上已经有低版本的pcre，所以强制安装
rpm -ivh pcre-8.32-17.el7.x86_64.rpm --force
rpm -ivh pcre-devel-8.32-17.el7.x86_64.rpm --force
```


## **3、安装zlib**


```
# http://mirror.centos.org/centos/7/os/x86_64/Packages/zlib-1.2.7-18.el7.x86_64.rpm
# http://mirror.centos.org/centos/7/os/x86_64/Packages/zlib-devel-1.2.7-18.el7.x86_64.rpm

rpm -ivh zlib-1.2.7-18.el7.x86_64.rpm   --force
rpm -ivh zlib-devel-1.2.7-18.el7.x86_64.rpm   --force
```

## **4、安装openssl**

```
# 这里强制安装有问题，会在编译时报错
# http://mirror.centos.org/centos/7/os/x86_64/Packages/openssl-1.0.2k-19.el7.x86_64.rpm
# http://mirror.centos.org/centos/7/os/x86_64/Packages/openssl-devel-1.0.2k-19.el7.x86_64.rpm

rpm -ivh openssl-1.0.2k-19.el7.x86_64.rpm --force
rpm -ivh openssl-devel-1.0.2k-19.el7.x86_64.rpm --force

```
 

## **5、安装nginx**


```
# 安装nginx的依赖包(离线安装，已使用本地rpm安装依赖，不必使用yum)
# yum install -y gcc gcc-c++ openssl-devel zlib-devel pcre-devel 

# 在同环境有网的机器上下载依赖
# yum -y install yum-utils
# yumdownloader --resolve --destdir=/root/soft/nginx-req gcc gcc-c++ openssl-devel zlib-devel pcre-devel
# 安装依赖
# yum localinstall -y *.rpm

# http://nginx.org/download/nginx-1.14.0.tar.gz

# 创建www用户
useradd -r -s /sbin/nologin www
# 解压源码包
tar -zxf nginx-1.14.0.tar.gz

cd nginx-1.14.0
# 编译安装
./configure --prefix=/usr/local/nginx1.14 --with-http_dav_module --with-http_stub_status_module --with-http_addition_module --with-http_sub_module --with-http_flv_module --with-http_mp4_module --with-http_ssl_module --with-http_gzip_static_module --user=www --group=www && make && make install 

# 编译报错查看文件：
# cat objs/autoconf.err

# 创建软连接
ln -s /usr/local/nginx1.14/sbin/nginx /usr/local/sbin/
# 检测配置文件
nginx -t
# 启动nginx
nginx

# 查看80端口
ss -anplt | grep nginx
# 添加防火墙规则
systemctl start firewalld
firewall-cmd --permanent --add-port=80/tcp
firewall-cmd --reload
# 关闭selinux
setenforce 0
sed -i 's/SELINUX=enforcing/SELINUX=disabled/g' /etc/selinux/config
# 访问测试
curl localhost
```

## 一键部署

```
[root@localhost src]# ll
total 68656
-rwxr-xr-x. 1 root root     1778 Nov 21 05:53 install.sh
drwxr-xr-x. 9 1001 1001      186 Nov 21 05:21 nginx-1.14.0
drwxr-xr-x. 2 root root     4096 Nov 21 02:54 nginx-req
-rw-r--r--. 1 root root 70294411 Nov 21 05:15 nginx.tar.gz
```


```
#!/bin/bash
# install nginx offline
# step1: install dependencies
rpm -Uvh nginx-req/*.rpm --nodeps --force
# step2: create user
useradd -r -s /sbin/nologin www
# step3: make
cd nginx-1.14.0
./configure --prefix=/usr/local/nginx1.14 --with-http_dav_module --with-http_stub_status_module --with-http_addition_module --with-http_sub_module --with-http_flv_module --with-http_mp4_module --with-http_ssl_module --with-http_gzip_static_module --user=www --group=www && make && make install
# step4: create ln for nginx
ln -sf /usr/local/nginx1.14/sbin/nginx /usr/local/sbin/
# step5: start nginx
nginx -t
nginx
# step6: add firewall rule
systemctl start firewalld
firewall-cmd --permanent --add-port=80/tcp
firewall-cmd --reload
# step7: off th selinux
setenforce 0
sed -i 's/SELINUX=enforcing/SELINUX=disabled/g' /etc/selinux/config
# step8: curl test
curl localhost
# step9: create /etc/init.d/nginx
cat > /etc/init.d/nginx << "EOF"
#!/bin/bash
# chkconfig: - 85 15
# description: nginx is a World Wide Web server. It is used to serve
nginx=/usr/local/nginx1.14/sbin/nginx
case $1 in
start)
ss -anplt|grep nginx
if [ $? -eq 0 ]; then
echo "nginx server is already running"
else
echo "nginx server begin start...."
$nginx

fi
;;
stop)
$nginx -s stop
if [ $? -eq 0 ]; then
echo "nginx server is stop"
else
echo "nginx server stop fail,try again!"
fi
;;
status)
ss -anplt|grep nginx
if [ $? -eq 0 ]; then
echo "nginx server is running"
else
echo "nginx server is stoped"
fi
;;
restart)
$nginx -s reload
if [ $? -eq 0 ]; then
echo "nginx server is begin restart"
else
echo "nginx server restart fail!"
fi
;;
*)
echo "please enter {start|restart|status|stop}"
;;
esac
EOF
# step10: onboot
chmod +x /etc/init.d/nginx
chkconfig --add nginx
chkconfig nginx on
echo "INSTALL NGINX DONE!"
```






```
# All Dependencies
cpp-4.8.5-44.el7.x86_64.rpm
e2fsprogs-1.42.9-19.el7.x86_64.rpm
e2fsprogs-libs-1.42.9-19.el7.x86_64.rpm
gcc-4.8.5-44.el7.x86_64.rpm
gcc-c++-4.8.5-44.el7.x86_64.rpm
glibc-2.17-325.el7_9.i686.rpm
glibc-2.17-325.el7_9.x86_64.rpm
glibc-common-2.17-325.el7_9.x86_64.rpm
glibc-devel-2.17-325.el7_9.x86_64.rpm
glibc-headers-2.17-325.el7_9.x86_64.rpm
kernel-headers-3.10.0-1160.45.1.el7.x86_64.rpm
keyutils-libs-1.5.8-3.el7.i686.rpm
keyutils-libs-devel-1.5.8-3.el7.x86_64.rpm
krb5-devel-1.15.1-50.el7.i686.rpm
krb5-devel-1.15.1-50.el7.x86_64.rpm
krb5-libs-1.15.1-50.el7.i686.rpm
krb5-libs-1.15.1-50.el7.x86_64.rpm
libcom_err-1.42.9-19.el7.i686.rpm
libcom_err-1.42.9-19.el7.x86_64.rpm
libcom_err-devel-1.42.9-19.el7.x86_64.rpm
libgcc-4.8.5-44.el7.i686.rpm
libgcc-4.8.5-44.el7.x86_64.rpm
libgomp-4.8.5-44.el7.x86_64.rpm
libkadm5-1.15.1-50.el7.i686.rpm
libkadm5-1.15.1-50.el7.x86_64.rpm
libmpc-1.0.1-3.el7.x86_64.rpm
libselinux-2.5-15.el7.i686.rpm
libselinux-2.5-15.el7.x86_64.rpm
libselinux-devel-2.5-15.el7.x86_64.rpm
libselinux-python-2.5-15.el7.x86_64.rpm
libselinux-utils-2.5-15.el7.x86_64.rpm
libsepol-2.5-10.el7.i686.rpm
libsepol-devel-2.5-10.el7.x86_64.rpm
libss-1.42.9-19.el7.x86_64.rpm
libstdc++-4.8.5-44.el7.i686.rpm
libstdc++-4.8.5-44.el7.x86_64.rpm
libstdc++-devel-4.8.5-44.el7.x86_64.rpm
libverto-0.2.5-4.el7.i686.rpm
libverto-devel-0.2.5-4.el7.x86_64.rpm
mpfr-3.1.1-4.el7.x86_64.rpm
nspr-4.32.0-1.el7_9.x86_64.rpm
nss-softokn-freebl-3.67.0-3.el7_9.i686.rpm
nss-softokn-freebl-3.67.0-3.el7_9.x86_64.rpm
nss-util-3.67.0-1.el7_9.x86_64.rpm
openssl-1.0.2k-22.el7_9.x86_64.rpm
openssl-devel-1.0.2k-22.el7_9.i686.rpm
openssl-devel-1.0.2k-22.el7_9.x86_64.rpm
openssl-libs-1.0.2k-22.el7_9.i686.rpm
openssl-libs-1.0.2k-22.el7_9.x86_64.rpm
pcre-8.32-17.el7.i686.rpm
pcre-devel-8.32-17.el7.i686.rpm
pcre-devel-8.32-17.el7.x86_64.rpm
zlib-1.2.7-19.el7_9.i686.rpm
zlib-1.2.7-19.el7_9.x86_64.rpm
zlib-devel-1.2.7-19.el7_9.i686.rpm
zlib-devel-1.2.7-19.el7_9.x86_64.rpm
```
