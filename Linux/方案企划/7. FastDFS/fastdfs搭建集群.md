## 1、搭建拓扑

![](https://gitee.com/luoxian1011/pictures/raw/master/lALPDgfLQvD4USPNAY_NA0Y_838_399.png)

| IP            | 名称           |
| ------------- | -------------- |
| 192.168.1.200 | dfs-client-vip |
| 192.168.1.71  | dfs-client1    |
| 192.168.1.72  | dfs-client2    |
| 192.168.1.73  | dfs-tracker1   |
| 192.168.1.74  | dfs-tracker2   |
| 192.168.1.75  | dfs-storage1   |
| 192.168.1.76  | dfs-storage2   |

| 说明         | 位置           |
| ------------ | -------------- |
| 所有安装包   | /usr/local/src |
| 数据存储位置 | /data/fastdfs  |



## 2、安装过程

### 	1、部署环境（6台）

#### 		1、部署安装包

```
# 部署依赖环境
yum install -y unzip make cmake gcc gcc-c++

# 上传安装包
fastdfs.zip fastdfs-nginx-module.zip libfastcommon.zip nginx-1.14.0.tar.gz

# 解压安装包
for i in `ls`; do unzip $i; done
```

#### 		2、安装libfastcommon

```
# 安装libfastcommon
cd libfastcommon
sh make.sh && sh make.sh install
```

#### 		3、安装FastDFS

```
# 安装FastDFS
cd fastdfs
sh make.sh && sh make.sh install
chmod +x /etc/init.d/fdfs*
```



### 	2、Tracker

#### 		1、创建数据和日志目录地址

```
mkdir -p /data/fdfs_tracker
mkdir -p /data/fdfs_storage
```

#### 		2、修改tracker配置文件（在/etc/fdfs目录下）

```
cp /etc/fdfs/tracker.conf.sample /etc/fdfs/tracker.conf
vim /etc/fdfs/tracker.conf
修改以下内容：base_path=/data/fdfs_tracker
```

#### 		3、启动Tracker

```
/etc/init.d/fdfs_trackerd start 
ss -anplt | grep tracker
firewall-cmd --add-port=22122/tcp --permanent
firewall-cmd --add-port=22122/tcp
```

##### 	2、Storage（也可以在traker上开启）

#### 		1、配置Storage 数据和日志目录地址

```
mkdir /data/fdfs_storage/base
mkdir /data/fdfs_storage/store
```

#### 		2、修改storage配置文件

```
cp /etc/fdfs/storage.conf.sample /etc/fdfs/storage.conf
vim /etc/fdfs/storage.conf
修改以下内容：
base_path=/data/fdfs_storage/base
store_path0=/data/fdfs_storage/store
tracker_server=192.168.1.73:22122
tracker_server=192.168.1.74:22122
log_level=info # 默认
```

#### 		3、启动Storage

```
/etc/init.d/fdfs_storage start 
ss -anplt | grep storage
firewall-cmd --add-port=23000/tcp --permanent
firewall-cmd --add-port=23000/tcp
```

### 	3、Client

#### 		1、修改Client配置文件

```
cp /etc/fdfs/client.conf.sample /etc/fdfs/client.conf
vim /etc/fdfs/client.conf
修改以下内容：
base_path=/tmp
tracker_server=192.168.1.73:22122
tracker_server=192.168.1.74:22122
```

#### 		2、client命令

```
# 上传文件 用法为 fdfs_upload_file + 配置文件 + 文件
fdfs_upload_file /etc/fdfs/client.conf /etc/passwd
cd /data/fdfs_storage/store/data/00/00/
```

```
# 下载文件 用法为 fdfs_download_file + 配置文件 + 文件id
fdfs_download_file /etc/fdfs/client.conf group1/M00/00/00/wKgKDF6o5wmALoEjAAADdloJ8DU5743377

# md5sum <文件> ：计算文件的md5值
md5sum /etc/passwd
```

```
# 查看文件信息 用法为 fdfs_file_info + 配置文件 + 文件id
fdfs_file_info /etc/fdfs/client.conf group1/M00/00/00/wKgKDF6o5wmALoEjAAADdloJ8DU5743377
```

```
# 删除文件 用法为 fdfs_delete_file + 配置文件 + 文件id
fdfs_delete_file /etc/fdfs/client.conf  group1/M00/00/00/wKgKDF6o5wmALoEjAAADdloJ8DU5743377
```

```
# 查看集群信息 用法为 fdfs_monitor + 配置文件
fdfs_monitor /etc/fdfs/client.conf
```

### 	4、安装nginx（6台）

#### 		1、部署nginx

```
yum install -y openssl openssl-devel zlib zlib-devel pcre pcre-devel
useradd -r -s /sbin/nologin www
tar -zxf nginx-1.14.0.tar.gz
cd nginx-1.14.0
./configure --prefix=/usr/local/nginx1.14  --with-http_stub_status_module --user=www --group=www --add-module=/usr/local/src/fastdfs-nginx-module/src
make && make install
ln -s /usr/local/nginx1.14/sbin/nginx /usr/local/sbin/
nginx -t
```

#### 		2、安装插件

```
# 拷贝配置文件到/etc/fdfs 目录
cd /usr/local/src/fastdfs-nginx-module/src
cp mod_fastdfs.conf /etc/fdfs/
cd /usr/local/src/fastdfs/conf
cp anti-steal.jpg http.conf mime.types /etc/fdfs/

# 生成一个日志文件并配置权限
touch /var/log/mod_fastdfs.log
chown www.www /var/log/mod_fastdfs.log

# 修改nginx配置文件
vim /usr/local/nginx1.14/conf/nginx.conf
    location /group1/M00 {
        root /data/fdfs_storage/store/;
        ngx_fastdfs_module;
    }

vim /etc/fdfs/mod_fastdfs.conf  
    tracker_server=192.168.10.11:22122
    tracker_server=192.168.10.12:22122
    url_have_group_name = true
    store_path0=/data/fdfs_storage/store
    log_filename=/var/log/mod_fastdfs.log
```

#### 		3、启动nginx

```
nginx

# 上传一张图片
fdfs_upload_file /etc/fdfs/client.conf 1.jpg

# 打开网页 并输入 IP 地址/和上传文件得到的文件 ID 及位置信息即可看到图片
```

### 	5、安装keepalived

#### 		1、部署keepalived

```
# 安装相关 keepalived 依赖
yum -y install kernel-devel openssl-devel popt-devel gcc*	   
# 解压源码包
tar xf keepalived-2.1.2.tar.gz	   
cd keepalived-2.1.2
# 编译安装
./configure --prefix=/ && make && make install
# 设置 Keepalived 开机自启
systemctl enable keepalived
```

#### 		2、配置文件

##### 			1、主节点配置

```
vim /etc/keepalived/keepalived.conf

! Configuration File for keepalived

global_defs {
   router_id dfs-client1
}
vrrp_script chk_nginx {
        script "/etc/keepalived/nginx_check.sh"
        interval 2
        weight -20
}
vrrp_instance VI_1 {
    state MASTER        # 标识为主服务
    interface ens33     #绑定虚拟机的IP
    virtual_router_id 51# 虚拟路由id，和从机保持一致
    priority 100        #权重，需要高于从机
    advert_int 1
    authentication {
        auth_type PASS
        auth_pass 1111
    }
    track_script {
        chk_nginx ## 执行 Nginx 监控的服务
    }
    virtual_ipaddress {
        192.168.1.200 
    }
}
```

##### 			2、从节点配置

```
vim /etc/keepalived/keepalived.conf

! Configuration File for keepalived

global_defs {
   router_id dfs-client2
}
vrrp_script chk_nginx {
        script "/etc/keepalived/nginx_check.sh" ## 检测 nginx 状态的脚本路径
        interval 2 ## 检测时间间隔
        weight -20 ## 如果条件成立，权重-20
}

vrrp_instance VI_1 {
    state BACKUP
    interface ens33
    virtual_router_id 51
    priority 90
    advert_int 1
    authentication {
        auth_type PASS
        auth_pass 1111
    }
        track_script {
            chk_nginx ## 执行 Nginx 监控的服务
        }
    virtual_ipaddress {
        192.168.1.200
    }
}
```

#### 		3、编写监测心跳脚本

```
# 编写检查脚本，两台都需要
vim /etc/keepalived/nginx_check.sh

#!/bin/bash
counter=$(ps -C nginx --no-heading|wc -l)
if [ "${counter}" = "0" ]; then
    /usr/local/sbin/nginx
    sleep 2
    counter=$(ps -C nginx --no-heading|wc -l)
    if [ "${counter}" = "0" ]; then
        /etc/init.d/keepalived stop
    fi
fi

# 添加执行权限
chmod +x /etc/keepalived/nginx_check.sh
```

#### 		4、启动keepalived

```
systemctl start keepalived
```

