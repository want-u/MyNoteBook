## 安装LNMP环境[分离]

| 192.168.122.10 | nginx | 静态页面 |
| :------------: | :---: | :------: |
| 192.168.122.20 | mysql |  数据库  |
| 192.168.122.30 |  php  | 动态页面 |



### 1 安装 nginx
在192.168.122.10上安装 nginx

```
# 安装nginx的依赖包
yum -y install gcc gcc-c++ make libtool openssl openssl-devel zlib-devel pcre-devel
# 创建www用户
useradd -r -s /sbin/nologin www
# 解压源码包
tar -zxf nginx-1.14.0.tar.gz

cd nginx-1.14.0
# 编译安装
./configure --prefix=/usr/local/nginx1.14 --with-http_dav_module --with-http_stub_status_module --with-http_addition_module --with-http_sub_module --with-http_flv_module --with-http_mp4_module --with-http_ssl_module --with-http_gzip_static_module --user=www --group=www && make && make install 
# 创建软连接
ln -s /usr/local/nginx1.14/sbin/nginx /usr/local/sbin/
# 检测配置文件
nginx -t
# 启动nginx
nginx

# 查看80端口
ss -anplt | grep nginx
# 添加防火墙规则
firewall-cmd --permanent --add-port=80/tcp
firewall-cmd --reload
# 访问测试
curl localhost
```

### 2 安装 mysql
在192.168.122.20上安装 mysql

```
# 使用脚本安装，root，123
https://cdn.mysql.com//Downloads/MySQL-5.7/mysql-5.7.29-linux-glibc2.12-x86_64.tar.gz
# 二进制包：mysql-5.7.22-linux-glibc2.12-x86_64.tar.gz 
# 安装脚本：mysql.sh
sh mysql.sh

mysql -uroot -p123
# 为php服务器赋权
mysql> grant all on *.* to root@'192.168.122.30' identified by '123';
```

### 3 安装 php
在192.168.122.30上安装 php

#### 3.1 安装 libmcrypt

```
tar zxf libmcrypt-2.5.7.tar.gz
cd libmcrypt-2.5.7/
./configure --prefix=/usr/local/libmcrypt && make && make install
```

#### 3.2 安装 PHP

```
yum -y install libxml2-devel libcurl-devel openssl-devel bzip2-devel
tar zxf php-5.6.27.tar.gz
cd php-5.6.27/

./configure --prefix=/usr/local/php5.6 --with-mysql=mysqlnd --with-pdo-mysql=mysqlnd --with-mysqli=mysqlnd --with-openssl --enable-fpm --enable-sockets --enable-sysvshm --enable-mbstring --with-freetype-dir --with-jpeg-dir --with-png-dir --with-zlib --with-libxml-dir=/usr --enable-xml --with-mhash --with-mcrypt=/usr/local/libmcrypt --with-config-file-path=/etc --with-config-file-scan-dir=/etc/php.d --with-bz2 --enable-maintainer-zts && make && make install 
```

#### 3.3 创建 php-fpm 启动脚本

```
# php的配置文件
cp php.ini-production /etc/php.ini
vi  /etc/php.ini
```

```
# 文件修改内容
opcache.enable=1;
opcache.memory_consumption=64;
opcache.interned_strings_buffer=16;
opcache.max_accelerated_files=32531;
opcache.validate_timestamps=0;
opcache.revalidate_freq=0;
opcache.fast_shutdown=1;
```

```
# fpm-php的启动脚本
cp sapi/fpm/init.d.php-fpm /etc/init.d/php-fpm
chmod +x /etc/init.d/php-fpm
# 配置fpm-php开机自启
chkconfig --add php-fpm
chkconfig php-fpm on
```

```
# fpm-php的配置文件
cp /usr/local/php5.6/etc/php-fpm.conf.default /usr/local/php5.6/etc/php-fpm.conf
vi /usr/local/php5.6/etc/php-fpm.conf
```

```
# 文件修改内容
pid = run/php-fpm.pid
listen =192.168.122.30:9000
pm.max_children = 300
pm.start_servers = 10
pm.min_spare_servers = 10
pm.max_spare_servers =50
```

```
# 启动php-fpm
service php-fpm restart

# 添加防火墙规则
firewall-cmd --permanent --add-port=9000/tcp
firewall-cmd --reload
setenforce 0
ss -anplt | grep 9000
```

#### 3.4 在nginx添加php模块

```
# 修改nginx配置文件
vi /usr/local/nginx1.14/conf/nginx.conf
```

```
# 文件内容
location / {
            root   html;
            index  index.php index.html index.htm;	# 调整php优先级
        }

location ~ \.php$ {
            root           /www;  # 在php服务器创建/www目录，存放php文件
            fastcgi_pass   192.168.122.30:9000; # php服务器
            fastcgi_index  index.php;
            fastcgi_param  SCRIPT_FILENAME  $document_root$fastcgi_script_name;  # 修改SCRIPT_FILENAME
            include        fastcgi_params;
        }
```

### 4 重启nginx

```
nginx -s reload
```

### 5 测试php页面

```
# 【php服务器：192.168.122.30】编辑php测试页面：
mkdir /www
echo "<?php phpinfo();?>" > /www/index.php
# 访问返回php信息页面
curl 192.168.122.10/index.php
```

```
# 【php服务器：192.168.122.30】编辑数据库测试页面：
vi /www/testdb.php
# 访问成功返回ok
curl 192.168.122.10/testdb.php
```

```
# testdb.php文件内容
<?php
$link=mysql_connect('192.168.122.20','root','123');
if($link) echo "ok";
mysql_close();
?>
```

