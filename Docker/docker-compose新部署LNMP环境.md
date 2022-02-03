# docker-compose新部署LNMP环境

[docker-compose一键部署LNMP环境(Nginx+MySQL+PHP)_陈洋的博客-CSDN博客_docker-compose lnmp](https://blog.csdn.net/xiazichenxi/article/details/95652457)



[toc]

## 版本信息

```
OS: Ubuntu 18.04.5 LTS on Windows 10 x86_64
Docker version 20.10.12, build e91ed57
docker-compose version 1.28.4, build cabd5cfb
MySQL 5.7.37
PHP 7.0.33 (cli)
PHP 7.0.33 (fpm-fcgi)
```

Git地址：https://gitee.com/luoxian1011/let/tree/master/lnmp

```
git clone https://gitee.com/luoxian1011/let.git
```


展示页面：

![image-20220203133209730](https://gitee.com/luoxian1011/pictures/raw/master//image-20220203133209730.png)

## 开始部署

### 创建lnmp目录

```
mkdir -p lnmp && cd lnmp
mkdir -p php

# 结构如下
❯ tree lnmp -L 2
lnmp
├── docker-compose.yml
└── php
    └── Dockerfile

1 directory, 2 files
```

### 编辑 php/Dockerfile

- 由于官方php-fpm镜像缺少一些扩展，所以要先用dockerfile构建新的镜像
- 注意配置时区和mysql扩展

```
vi php/Dockerfile
```



```
FROM php:7.0-fpm
RUN cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime \
    && echo "Asia/Shanghai" > /etc/timezone
RUN apt-get update && apt-get install -y \
        libfreetype6-dev \
        libjpeg62-turbo-dev \
        libmcrypt-dev \
        libpng-dev \
        libmemcached-dev \
        zlib1g-dev \
        libcurl4-openssl-dev \
        libxml2-dev \
        --no-install-recommends && rm -rf /var/lib/apt/lists/* \
    && docker-php-ext-install -j$(nproc) \
        iconv mcrypt gettext curl mysqli pdo pdo_mysql zip \
        mbstring bcmath opcache xml simplexml sockets hash soap \
    && docker-php-ext-configure gd --with-freetype-dir=/usr/include/ --with-jpeg-dir=/usr/include/ \
    && docker-php-ext-install -j$(nproc) gd

CMD ["php-fpm", "-F"]
```

### 编辑docker-compose.yml

- 官方推荐使用volume优于bind mount
- volume默认存放在/var/lib/docker/volumes

```
vi docker-compose.yml
```



```
version: "3"

services:
  mysql:
    hostname: mysql
    restart: always
    image: mysql:5.7
    container_name: mysql
    ports:
      - "3306:3306"
    volumes:
      - mysql-config:/etc/mysql
      - mysql-log:/var/log/mysql
      - mysql-data:/var/lib/mysql
    environment:
      MYSQL_ROOT_PASSWORD: 123456
      MYSQL_USER: user
      MYSQL_PASSWORD: user123
      TZ: Asia/Shanghai

  php:
    hostname: php
    restart: always
    container_name: php
    build:
      context: ./php
      dockerfile: Dockerfile
    ports:
      - "9000:9000"
    environment:
      TZ: Asia/Shanghai
    links:
      - mysql:mysql
    volumes:
      - nginx-html:/var/www/html
      - php-config:/usr/local/etc
  nginx:
    hostname: nginx
    restart: always
    container_name: nginx
    image: nginx:1.17.0
    ports:
      - "80:80"
      - "443:443"
    environment:
      TZ: Asia/Shanghai
    links:
      - "php:php"
    volumes:
      - nginx-config:/etc/nginx
      - nginx-log:/var/log/nginx
      - nginx-html:/usr/share/nginx/html

volumes:
  mysql-config:
  mysql-log:
  mysql-data:
  nginx-html:
  php-config:
  nginx-config:
  nginx-log:
```

## 启动服务

```
docker-compose up -d   # 启动
docker-compose restart # 重启
```

## 数据持久化目录

- /var/lib/docker/volumes

```
docker volume ls 			# 查看volume

lnmp_mysql-config 	# 数据库配置文件
lnmp_mysql-data 	# 数据库数据文件
lnmp_mysql-log 		# 数据库日志
lnmp_php-config 	# PHP配置文件
lnmp_nginx-config	# nginx 配置文件
lnmp_nginx-html 	# nginx web目录
lnmp_nginx-log 		# nginx日志目录
metadata.db 		# volumes元数据信息
```

### php配置文件

- /var/lib/docker/volumes/lnmp_php-config/_data
- 默认的php.ini文件是没有的，需要手动把模板配置文件复制为php.ini
- 同时修改时区

```
# 把模板配置文件复制为php.ini
cd /var/lib/docker/volumes/lnmp_php-config/_data/php
cp php.ini-production php.ini

# 修改php.ini
sed -i 's/;date.timezone =/date.timezone = PRC/' php.ini
```

### nginx配置

```
# nginx主配置文件（如果少加了include文件夹，访问会报错 curl: (56) Recv failure: Connection reset by peer）
vi /var/lib/docker/volumes/lnmp_nginx-config/_data/nginx.conf
```

```
user nginx;
worker_processes 4;
worker_cpu_affinity 0001 0010 0100 1000;
error_log /var/log/nginx/error.log;
pid /var/run/nginx.pid;

events {
    use epoll;
    worker_connections 65535;
    multi_accept on;
}
http {
     include mime.types;
     default_type application/octet-stream;

     log_format main_json '{"remote_addr":"$remote_addr",'
                            '"domain":"$server_name",'
                            '"http_x_forwarded_for":"$http_x_forwarded_for",'
                            '"time_local":"$time_iso8601",'
                            '"request":"$request",'
                            '"request_body":"$request_body",'
                            '"status":$status,'
                            '"body_bytes_sent":"$body_bytes_sent",'
                            '"http_referer":"$http_referer",'
                            '"upstream_response_time":"$upstream_response_time",'
                            '"request_time":"$request_time",'
                            '"http_user_agent":"$http_user_agent",'
                            '"upstream_addr":"$upstream_addr",'
                            '"upstream_status":"$upstream_status"}';
     access_log /var/log/nginx/access.log main_json;
     
     sendfile on;
     tcp_nopush on;
     keepalive_timeout 65;
     tcp_nodelay on;
     client_header_buffer_size 4k;
     open_file_cache max=102400 inactive=20s;
     open_file_cache_valid 30s;
     open_file_cache_min_uses 1;
     client_header_timeout 15;
     client_body_timeout 15;
     reset_timedout_connection on;
     send_timeout 15;
     server_tokens off;
     client_max_body_size 10m;
     
     fastcgi_connect_timeout 600;
     fastcgi_send_timeout 600;
     fastcgi_read_timeout 600;
     fastcgi_buffer_size 64k;
     fastcgi_buffers 4 64k;
     fastcgi_busy_buffers_size 128k;
     fastcgi_temp_file_write_size 128k;
     fastcgi_intercept_errors on;
    
     gzip on;
     gzip_min_length 2k;
     gzip_buffers 4 32k;
     gzip_http_version 1.1;
     gzip_comp_level 6;
     gzip_types text/plain text/css text/javascript application/json application/javascript 
     application/x-javascript application/xml;
     gzip_vary on;
     gzip_proxied any;
     
     include /etc/nginx/conf.d/*.conf;
    }
```



```
vi /var/lib/docker/volumes/lnmp_nginx-config/_data/conf.d/default.conf
```

- nginx主页根目录 : root   /usr/share/nginx/html;
- php   主页根目录 : root           /var/www/html;

```
server {
    listen       80;
    server_name  localhost;
    location / {
        root   /usr/share/nginx/html;
        index  index.html index.htm index.php;
    }
    error_page   500 502 503 504  /50x.html;
    location = /50x.html {
        root   /usr/share/nginx/html;
    }
    location ~ \.php$ {
        root           /var/www/html;  # 在php服务器中/var/www/html目录，存放php文件
        fastcgi_pass   php:9000;       # 这里php就是php容器的名字
        fastcgi_index  index.php;
        fastcgi_param  SCRIPT_FILENAME  $document_root$fastcgi_script_name;
        include        fastcgi_params;
    }
}
```

## 重启加载配置

```
docker-compose restart # 重启
```

### Wsl-问题记录


```
ls -l /var/lib/docker/volumes/lnmp_mysql-config/_data/my.cnf
lrwxrwxrwx 1 root root   24 Jan 27 08:58 my.cnf -> /etc/alternatives/my.cnf
# 文件为软连接，无法挂载到容器，此次暂时这样解决一下
cd /var/lib/docker/volumes/lnmp_mysql-config/_data/
rm -f my.cnf && touch my.cnf

# 后续发现nginx的日志软连接到/dev/stdout，可能是wsl的问题，暂时解决一下
cd /var/lib/docker/volumes/lnmp_nginx-log/_data/
rm -f access.log error.log && touch access.log error.log

# 重启
docker-compose restart
```


## 异常处理记录

### mysql5.6 启动失败，报错权限问题


```
容器 mysqld: Can't create/write to file '/tmp/iby6gPxz' (Errcode: 13 - Permission denied)

# 暂时将mysql版本改到5.7
```


### mysql5.7 Warning: World-writable config file ‘/etc/my.cnf’ is ignored


```
ls -l /var/lib/docker/volumes/lnmp_mysql-config/_data/my.cnf
lrwxrwxrwx 1 root root   24 Jan 27 08:58 my.cnf -> /etc/alternatives/my.cnf
# 文件为软连接，无法挂载到容器，此次暂时这样解决一下
rm -f my.cnf && touch my.cnf

# 后续发现nginx的日志软连接到/dev/stdout，可能是wsl的问题，暂时解决一下
rm -f access.log error.log && touch access.log error.log

# 重启
docker-compose restart
```


### mysql5.7 Table ‘performance_schema.session_variables’ doesn’t exist 问题

https://www.cnblogs.com/linux90/p/12166959.html

performance_schema在mysql5.5以上就有自带

performance_schema（安装数据库时自带的）如果装数据库或者使用数据时不小心删除了，就会出现Table‘performance_schema.session_variables’ doesn’t exist的异常。


```
第一步：
# mysql_upgrade -uroot -p

第二步：
# docker-compose restart
```


### mysql5.7 的docker logs时区问题

https://www.cnblogs.com/minseo/p/11364003.html

1、临时修改

```
# mysql -uroot -p123456 -h 127.0.0.1

mysql> show variables like "log_timestamps";
mysql> set global log_timestamps="SYSTEM";

mysql> show variables like "log_timestamps";
+----------------+--------+
| Variable_name  | Value  |
+----------------+--------+
| log_timestamps | SYSTEM |
+----------------+--------+
1 row in set (0.00 sec)

# 使用错误的密码登录一次产生日志再次查看日志时间对了
```

2、设置永久生效,修改MySQL配置文件my.cnf

```sh
# 文件为软连接，此次暂时这样解决一下
# rm -f my.cnf && touch my.cnf
# vi /var/lib/docker/volumes/lnmp_mysql-config/_data/my.cnf

[mysqld]
log_timestamps=SYSTEM
```

```
# 重启容器
docker-compose restart

# 查看日志
docker-compose logs -f 
```

如果是使用docker-compose启动则需要修改配置文件添加以下内容

```
volumes:
      - ./mysql/conf:/etc/mysql/conf.d
      - ./mysql/data:/var/lib/mysql
      - /etc/localtime:/etc/localtime:ro
      - /etc/timezone:/etc/timezone:ro
```

PS：如果docker-compose已经启动则添加配置重启无效需要删除容器再重新up才能生效

```
docker-compose down
docker-compose up -d
```

## 测试验证

### 查看容器状态

```
docker container ls
```



```
CONTAINER ID        IMAGE                     COMMAND                  CREATED             STATUS                  PORTS                                                                  NAMES
b7531773beaa        nginx:1.17.0              "nginx -g 'daemon of…"   14 minutes ago      Up 7 minutes            0.0.0.0:80->80/tcp, 0.0.0.0:443->443/tcp                               nginx
aaebabd0a32a        composelnmp_php           "docker-php-entrypoi…"   14 minutes ago      Up 7 minutes            0.0.0.0:9000->9000/tcp                                                 php
df7b8bd4975b        mysql:5.6                 "docker-entrypoint.s…"   14 minutes ago      Up 7 minutes            0.0.0.0:3306->3306/tcp                                                 mysql
```

### 验证nginx

```
# 改一下首页index文件

echo " hello world! " > /var/lib/docker/volumes/lnmp_nginx-html/_data/index.html
```

浏览器访问：http://localhost

### 验证mysql

```
[root@localhost ~]# docker container exec -it mysql bash 
root@mysql:/# mysql -uroot -p
Enter password: 
Welcome to the MySQL monitor.  Commands end with ; or \g.
Your MySQL connection id is 1
Server version: 5.6.44 MySQL Community Server (GPL)

Copyright (c) 2000, 2019, Oracle and/or its affiliates. All rights reserved.

Oracle is a registered trademark of Oracle Corporation and/or its
affiliates. Other names may be trademarks of their respective
owners.

Type 'help;' or '\h' for help. Type '\c' to clear the current input statement.

mysql> select version();
+-----------+
| version() |
+-----------+
| 5.6.44    |
+-----------+
1 row in set (0.00 sec)
```

### 验证php访问

```
echo "<?php phpinfo();" > /var/lib/docker/volumes/lnmp_nginx-html/_data/test.php
```

浏览器访问：http://localhost/test.php

### 验证mysql访问

```
# 由于wsl每次启动ip会发生变化，这里采用docker0作为入口
# wsl - bridge - 查看ip 默认是 172.17.0.1
docker network inspect bridge | grep Gateway

                    "Gateway": "172.17.0.1"
```



```
vi /var/lib/docker/volumes/lnmp_nginx-html/_data/testdb.php
```

```php
<?PHP
    // 主机填写主机ip，测试localhost和127.0.0.1都有报错
    $conn=mysqli_connect("172.17.0.1","user","user123");
    if($conn){
        echo"[ok] - mysqli_connect - ";
    }else{
        echo"[error] mysqli_connect - ";
    }
?>


```

浏览器访问：http://localhost/testdb.php