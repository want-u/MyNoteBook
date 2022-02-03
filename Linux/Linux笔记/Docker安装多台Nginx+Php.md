# Docker安装多台Nginx+Php

## 拓扑：

    php-fpm_1 9000
    php-fpm_2 9000
    php-fpm_3 9000
    nginx_1 81
    nginx_2 82
    nginx_3 83
    

---

准备目录和配置文件
    
    mkdir /data/nginx/{conf,www} -p

## 1. 安装php

    # 下载php镜像
    # docker pull php:7.1.30-fpm
    docker pull php:5.6-fpm
    # 查看docker已下载镜像
    docker images 
    # 运行php容器
    docker run --name  php-fpm_1 -v /data/nginx/www:/www  -d --restart=always php:5.6-fpm
    docker run --name  php-fpm_2 -v /data/nginx/www:/www  -d --restart=always php:5.6-fpm
    docker run --name  php-fpm_3 -v /data/nginx/www:/www  -d --restart=always php:5.6-fpm
    # 进入容器
    docker exec -it php-fpm_1 bash
    # 安装php插件命令
    docker-php-ext-install mysql
    docker-php-ext-install mysqli
    # 访问mysql时指定宿主ip即可


## 2. 安装Nginx镜像

    docker pull nginx

    不指定版本，默认下载最新版本
    安装后，执行 docker images 查看 nginx 镜像。
## 3. 创建Nginx容器

    # 将配置文件放在conf下，网页项目放在www下
    # nginx配置文件在下面
    cd /data/nginx/conf/
    vim nginx.conf
    echo test > /data/nginx/www/index.html
    echo "<?php phpinfo();?>" > /data/nginx/www/index.php
    
    
    docker run -d --name nginx_1 -p 81:80 -v /data/nginx/conf/nginx.conf:/etc/nginx/nginx.conf -v /data/nginx/www:/home/nginx/www --link php-fpm_1:php --restart=always nginx
    docker run -d --name nginx_2 -p 82:80 -v /data/nginx/conf/nginx.conf:/etc/nginx/nginx.conf -v /data/nginx/www:/home/nginx/www --link php-fpm_2:php --restart=always nginx
    docker run -d --name nginx_3 -p 83:80 -v /data/nginx/conf/nginx.conf:/etc/nginx/nginx.conf -v /data/nginx/www:/home/nginx/www --link php-fpm_3:php --restart=always nginx
    
    注意，如上命令执行时不要有回车，该格式只是为了更好讲解。
    
## 4. docker run：创建容器的命令

- -d：在run后面加上-d参数,则会创建一个守护式容器在后台运行（这样创建容器后不会自动登录容器，如果只加-i -t两个参数，创建后就会自动进去容器）。
- --name ：为创建的容器命名。
- -p：表示端口映射，前者是宿主机端口，后者是容器内的映射端口。可以使用多个－p做多个端口映射
- -v：表示目录映射关系（前者是宿主机目录，后者是映射到宿主机上
- 的目录），可以使用多个－v做多个目录或文件映射。注意：最好做目录映射，在宿主机上做修改，然后共享到容器上。
- --privileged：是否让docker 应用容器 获取宿主机root权限（特殊权限-）
- --net=host：直接访问容器端口（进入容器显示主机名名）
- -i：表示运行容器
- -t：表示容器启动后会进入其命令行。加入这两个参数后，容器创建就能登录进去。即分配一个伪终端。

## 5. nginx配置文件：

    worker_processes  1;
    
    events {
        worker_connections  1024;
    }
    
    http {
        include       mime.types;
        default_type  application/octet-stream;
    
        sendfile        on;
        #tcp_nopush     on;
    
        #keepalive_timeout  0;
        keepalive_timeout  65;
    
        #gzip  on;
    
        server {
            listen       80;
            server_name  localhost;
    
            # Vue路由模式为history需添加的配置
            location / {
                if (!-e $request_filename) {
                    rewrite ^(.*)$ /index.html?s=$1 last;
                    break;
                }
                root   /home/nginx/www;
                index  index.php index.html;
            }
            location ~ \.php$ {
                fastcgi_pass   php:9000;
                fastcgi_index  index.php;
                fastcgi_param  SCRIPT_FILENAME  /www/$fastcgi_script_name;
                include        fastcgi_params;
            }
    
            # 获取真实IP以及Websocket需添加的配置
            proxy_set_header Host $host;
            proxy_set_header X-Real-IP $remote_addr;
            proxy_set_header REMOTE-HOST $remote_addr;
            proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
            proxy_set_header Upgrade $http_upgrade;
            proxy_set_header Connection "upgrade";
    
            # 客户端Body大小限制（文件上传大小限制配置）
            client_max_body_size 5m;
    
            error_page   500 502 503 504 404  /50x.html;
            location = /50x.html {
                root   html;
            }
    
        }
    }

## 6. php测试文件

    <?php phpinfo();?>

