# 最小化安装KVM & 搭建LNMP



## 1. 环境准备

### 1.1 关闭防火墙

```
systemctl stop firewalld
systemctl disable firewalld
setenforce 0
sed -i 's/SELINUX=enforcing/SELINUX=disabled/g' /etc/selinux/config
```

### 1.2 更新YUM源

```
curl -o /etc/yum.repos.d/CentOS-Base.repo http://mirrors.aliyun.com/repo/Centos-7.repo
curl -o /etc/yum.repos.d/epel.repo http://mirrors.aliyun.com/repo/epel-7.repo
yum clean all
yum install -y vim lrzsz mlocate net-tools wget bash-completion
# yum update -y【更新可选】
```

### 1.3 添加磁盘

```bash
echo "- - -" > /sys/class/scsi_host/host0/scan
lsblk 
pvcreate /dev/sdb 
vgcreate vmvg /dev/sdb
lvcreate -n kvmlv -l 100%VG vmvg
mkfs.ext4 /dev/vmvg/kvmlv
mkdir /kvm-vm
mount /dev/vmvg/kvmlv /kvm-vm/
echo "/dev/vmvg/kvmlv /kvm-vm/ ext4 defaults 0 0" >> /etc/fstab
mount -a 
cd /kvm-vm/
ll
```



## 2. 安装KVM

```
# 安装KVM
yum install -y qemu* libvirt virt-install bridge-utils openssl-libs-1.0.2k*
lsmod | grep kvm
# 配置日志
echo 'log_level = 1' >> /etc/libvirt/libvirtd.conf
echo 'log_outputs="1:file:/var/log/libvirt/libvirtd.log"' >> /etc/libvirt/libvirtd.conf
# 重启服务
systemctl restart libvirtd
systemctl status libvirtd
```


### 2.1 添加系统镜像

```
mkdir /iso
dd if=/dev/sr0 of=/iso/centos7.iso
```

### 2.2 创建qcow2磁盘

```
qemu-img create -f qcow2 vm001.qcow2 20G
ll -h
```



## 3. 安装虚拟机

```
virt-install -n vm001 --memory 1024 --vcpus 1 -l /iso/centos7.iso --disk /kvm-vm/vm001.qcow2,format=qcow2,size=10,sparse --graphics none -x 'console=ttyS0' --accelerate
# 1、编辑虚拟机配置文件【/etc/libvirt/qemu/】
# virsh edit vm001
# 2、虚拟机内无法上网，可能需要开启路由转发，并刷新参数
# echo "net.ipv4.ip_forward = 1" >> /etc/sysctl.conf
# sysctl -p
# 3、systemctl restart libvirtd
```



## 4. 克隆虚拟机

```
virsh list --all
virsh shutdown vm001

virt-clone -o vm001 -n clone_vm002 -f /kvm-vm/clone_vm002.img
virt-clone -o vm001 -n clone_vm003 -f /kvm-vm/clone_vm003.img
```

```
# 配置克隆机器的ip
# 第一台：192.168.122.10
# 第二台：192.168.122.20
# 第三台：192.168.122.30

virsh list --all
virsh start clone_vm002
# 【敲回车进入】
virsh console clone_vm002
```

```
# 上传所需文件包
scp nginx-1.14.0.tar.gz 192.168.122.10:/root
scp mysql* 192.168.122.20:/root
scp php-5.6.27.tar.gz libmcrypt-2.5.7.tar.gz 192.168.122.30:/root
```



## 5. 安装LNMP环境

| 192.168.122.10 | nginx | 静态页面 |
| :------------: | :---: | :------: |
| 192.168.122.20 | mysql |  数据库  |
| 192.168.122.30 |  php  | 动态页面 |



### 5.1 安装 nginx

```
yum -y install gcc gcc-c++ make libtool openssl openssl-devel zlib-devel pcre-devel
useradd -r -s /sbin/nologin www
tar -zxf nginx-1.14.0.tar.gz
cd nginx-1.14.0

./configure --prefix=/usr/local/nginx1.14 --with-http_dav_module --with-http_stub_status_module --with-http_addition_module --with-http_sub_module --with-http_flv_module --with-http_mp4_module --with-http_ssl_module --with-http_gzip_static_module --user=www --group=www && make && make install 

ln -s /usr/local/nginx1.14/sbin/nginx /usr/local/sbin/
nginx -t
nginx
ss -anplt | grep nginx
firewall-cmd --permanent --add-port=80/tcp
firewall-cmd --reload
curl localhost
```

### 5.2 安装 mysql

```
# 使用脚本安装，root，123
https://cdn.mysql.com//Downloads/MySQL-5.7/mysql-5.7.29-linux-glibc2.12-x86_64.tar.gz
# 二进制包：mysql-5.7.22-linux-glibc2.12-x86_64.tar.gz 
sh mysql.sh
mysql -uroot -p123
mysql> grant all on *.* to root@'192.168.122.30' identified by '123';
```

### 5.3 安装 php

#### 5.3.1 安装 libmcrypt

```
tar zxf libmcrypt-2.5.7.tar.gz
cd libmcrypt-2.5.7/
./configure --prefix=/usr/local/libmcrypt && make && make install
```

#### 5.3.2 安装 PHP

```
yum -y install libxml2-devel libcurl-devel openssl-devel bzip2-devel
tar zxf php-5.6.27.tar.gz
cd php-5.6.27/

./configure --prefix=/usr/local/php5.6 --with-mysql=mysqlnd --with-pdo-mysql=mysqlnd --with-mysqli=mysqlnd --with-openssl --enable-fpm --enable-sockets --enable-sysvshm --enable-mbstring --with-freetype-dir --with-jpeg-dir --with-png-dir --with-zlib --with-libxml-dir=/usr --enable-xml --with-mhash --with-mcrypt=/usr/local/libmcrypt --with-config-file-path=/etc --with-config-file-scan-dir=/etc/php.d --with-bz2 --enable-maintainer-zts && make && make install 
```

#### 5.3.3 创建 php-fpm 启动脚本

```
cp php.ini-production /etc/php.ini
vi  /etc/php.ini
```

```
# 文件内容
opcache.enable=1;
opcache.memory_consumption=64;
opcache.interned_strings_buffer=16;
opcache.max_accelerated_files=32531;
opcache.validate_timestamps=0;
opcache.revalidate_freq=0;
opcache.fast_shutdown=1;
```

```
cp sapi/fpm/init.d.php-fpm /etc/init.d/php-fpm
chmod +x /etc/init.d/php-fpm
chkconfig --add php-fpm
chkconfig php-fpm on
cp /usr/local/php5.6/etc/php-fpm.conf.default /usr/local/php5.6/etc/php-fpm.conf
vi /usr/local/php5.6/etc/php-fpm.conf
```

```
# 文件内容
pid = run/php-fpm.pid
listen =192.168.122.30:9000
pm.max_children = 300
pm.start_servers = 10
pm.min_spare_servers = 10
pm.max_spare_servers =50
```

```
service php-fpm restart
systemctl stop firewalld
setenforce 0
ss -anplt | grep 9000
```

#### 5.3.4 在nginx添加php模块

```
vi /usr/local/nginx1.14/conf/nginx.conf
```

```
# 文件内容
location / {
            root   html;
            index  index.php index.html index.htm;	#调整php优先级
        }

location ~ \.php$ {
            root           /www;  # 在php服务器创建/www目录，存放php文件
            fastcgi_pass   192.168.122.30:9000;
            fastcgi_index  index.php;
            fastcgi_param  SCRIPT_FILENAME  $document_root$fastcgi_script_name;
            include        fastcgi_params;
        }
```

### 5.4 重启nginx

```
nginx -s reload
```

### 5.5 测试页面

```
# 【php服务器】编辑php测试页面：
mkdir /www
echo "<?php phpinfo();?>" > /www/index.php
# 访问返回php信息页面
```

```
【php服务器】编辑数据库测试页面：
vi /www/testdb.php
# 访问成功返回ok
```

```
# 文件内容
<?php
$link=mysql_connect('192.168.122.20','root','123');
if($link) echo "ok";
mysql_close();
?>
```

