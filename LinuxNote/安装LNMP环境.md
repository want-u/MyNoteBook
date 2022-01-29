# **安装LNMP环境**

## **1、安装 nginx**

```
yum install -y gcc gcc-c++ openssl-devel zlib-devel pcre-devel 

useradd -r -s /sbin/nologin www

tar -zxf nginx-1.14.0.tar.gz

cd nginx-1.14.0

./configure --prefix=/usr/local/nginx1.14 --with-http_dav_module --with-http_stub_status_module --with-http_addition_module --with-http_sub_module --with-http_flv_module --with-http_mp4_module --with-http_ssl_module --with-http_gzip_static_module --user=www --group=www && make && make install 

ln -s /usr/local/nginx1.14/sbin/nginx /usr/local/sbin/

nginx -t

nginx

netstat -anpt | grep nginx

firewall-cmd --permanent --add-port=80/tcp

firewall-cmd --reload
```



## **2、安装 mysql**

使用脚本安装，root，123

二进制包：mysql-5.7.22-linux-glibc2.12-x86_64.tar.gz 

```
sh mysql.sh

mysql -uroot -p123
```



## **3、安装 php**

**1)安装 libmcrypt**

```
tar zxf libmcrypt-2.5.7.tar.gz

cd libmcrypt-2.5.7/

./configure && make && make install
```

**2)安装 PHP**

```
yum install -y libxml2-devel bzip2-devel freetype-devel libcurl-devel libjpeg-devel libpng-devel 

tar zxf php-5.6.27.tar.gz

cd php-5.6.27/

./configure --prefix=/usr/local/php5.6 --with-config-file-path=/etc --with-mysql=/usr/local/mysql --with-mysqli=/usr/local/mysql/bin/mysql_config  --with-mysql-sock=/usr/local/mysql/mysql.sock --with-gd --with-iconv --with-libxml-dir=/usr  --with-mhash --with-mcrypt --with-config-file-scan-dir=/etc/php.d --with-bz2 --with-zlib --with-freetype-dir --with-png-dir --with-jpeg-dir --enable-xml --enable-bcmath --enable-shmop --enable-sysvsem --enable-inline-optimization --enable-mbregex --enable-fpm --enable-mbstring --enable-ftp --enable-gd-native-ttf --with-openssl --enable-pcntl --enable-sockets --with-xmlrpc --enable-zip --enable-soap --without-pear --with-gettext --enable-session --with-mcrypt --with-curl && make && make install
```

```
# 【lnmp分布部署】若未搭建mysql，先将mysql参数取消

# ./configure --prefix=/usr/local/php5.6 --with-config-file-path=/etc --with-mysql --with-mysqli --with-gd --with-iconv --with-libxml-dir=/usr  --with-mhash --with-mcrypt --with-config-file-scan-dir=/etc/php.d --with-bz2 --with-zlib --with-freetype-dir --with-png-dir --with-jpeg-dir --enable-xml --enable-bcmath --enable-shmop --enable-sysvsem --enable-inline-optimization --enable-mbregex --enable-fpm --enable-mbstring --enable-ftp --enable-gd-native-ttf --with-openssl --enable-pcntl --enable-sockets --with-xmlrpc --enable-zip --enable-soap --without-pear --with-gettext --enable-session --with-mcrypt --with-curl && make && make install
```

```
cp php.ini-production /etc/php.ini
```

编辑配置文件/etc/php.ini

```
date.timezone = PRC #设置时区
expose_php = Off #禁止显示 php 版本的信息
short_open_tag = On //支持 php 短标签
post_max_size = 16M //上传文件大小
max_execution_time = 300 //php 脚本最大执行时间
max_input_time = 300 //以秒为单位对通过 POST、GET 以及 PUT 方式接收数据时间进行限制
always_populate_raw_post_data = -1
mbstring.func_overload = 0
```

**3）****创建** **php-fpm** **服务启动脚本：**

```
cp sapi/fpm/init.d.php-fpm /etc/init.d/php-fpm

chmod +x /etc/init.d/php-fpm

chkconfig --add php-fpm

chkconfig php-fpm on

cp /usr/local/php5.6/etc/php-fpm.conf.default /usr/local/php5.6/etc/php-fpm.conf

vim /usr/local/php5.6/etc/php-fpm.conf

pid = run/php-fpm.pid user = www group = www listen =127.0.0.1:9000 pm.max_children = 300 pm.start_servers = 10 pm.min_spare_servers = 10 pm.max_spare_servers =50

service php-fpm restart

netstat -antpu | grep 9000
```

**将nginx配置文件中添加php模块：**

```
vim /usr/local/nginx1.14/conf/nginx.conf

location / {
            root   html;
            index  index.php index.html index.htm;	#调整php优先级
        }

location ~ \.php$ {	                                #取消注释
            root           html;
            fastcgi_pass   127.0.0.1:9000;
            fastcgi_index  index.php;
            fastcgi_param  SCRIPT_FILENAME  /scripts$fastcgi_script_name;
            include        fastcgi.conf;		#改为fastcgi.conf
        }
```

**重启nginx：**

```
nginx -s reload
```

**编辑php测试页面：**

```
<?php phpinfo();?>
```

**编辑数据库测试页面：**

```
<?php

$link=mysql_connect('127.0.0.1','root','123');

if($link) echo "ok";

mysql_close();

?>
```

