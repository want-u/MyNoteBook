# Mycat部署

## 拓扑

| 主机名               | IP            | 角色               |
| -------------------- | ------------- | ------------------ |
| mycat-vip            | 192.168.1.150 |                    |
| mycat1               | 192.168.1.51  | 数据库中间件       |
| mycat2               | 192.168.1.52  | 数据库中间件[备用] |
|                      |               |                    |
| mha-mysql-vip        | 192.168.1.160 |                    |
| mha-mysql-manager    | 192.168.1.61  | mha集群            |
| mha-mysql-master     | 192.168.1.62  | mha集群            |
| mha-mysql-master-bak | 192.168.1.63  | mha集群            |
| mha-mysql-slave      | 192.168.1.64  | mha集群            |

**1、安装准备**

1、jdk：要求jdk必须是1.7及以上版本 

2、Mysql：推荐mysql是5.5以上版本 

3、Mycat：Mycat的官方网站：http://www.mycat.org.cn/   

下载地址： https://github.com/MyCATApache/Mycatdownload

4、关闭防火墙和SElinux

## 部署

### 主从授权

主从都授权（Mycat访问需要）：

```
grant all on *.* to root@'192.168.1.%' identified by '123';

create database test_db;
use test_db;
create table t2(id int, name varchar(50));
insert into t2 values (1,'ccc'),(2,'ddd');

# 为测试读写分离准备数据
# 在master-bak上insert into t2 values (1,63);
# 在slave上insert into t2 values (1,64);

```

### 安装Mycat



第一步：下载Mycat-server-xxxx-linux.tar.gz   

第二步：将压缩包解压缩。建议将mycat放到/usr/local/mycat目录下。  

第三步：做连链接：ln -s /usr/local/mycat/bin/* /usr/local/bin/

mycat start  

停止：./mycat stop  

mycat支持的命令{ console | start | stop | restart | status | dump }  

Mycat的默认端口号为：8066

```
yum install -y java java-devel

tar -xf Mycat-server-1.6.7.5-release-20200410174409-linux.tar.gz
mv mycat/ /usr/local/
ln -s /usr/local/mycat/bin/* /usr/local/bin/
```

### 配置文件

mycat的conf目录下的配置文件

必须保证XML文件的第一个元素前面不能空格

（也就是保证第一个元素是 <?xml version="1.0" encoding="UTF-8"?>开头，规范的XML格式）

①schema.xml： 定义逻辑库，表、分片节点等内容 

②rule.xml： 定义分片规则

③server.xml： 定义用户以及系统相关变量，如端口等

**1、 修改配置文件server.xml 修改用户信息，与MySQL区分， 如下：**

vim /usr/local/mycat/conf/server.xml

```
<user name="mycat" defaultAccount="true">
    <property name="password">123456</property>
    <property name="schemas">TESTDB</property>
    <!-- 表级 DML 权限设置 -->
    <!--
    <privileges check="false">
        <schema name="TESTDB" dml="0110" >
            <table name="tb01" dml="0000"></table>
            <table name="tb02" dml="1111"></table>
        </schema>
    </privileges>
    -->
</user>
```

**2、修改schema.xml**

vim /usr/local/mycat/conf/schema.xml

读写分离配置

- writeHost：192.168.1.160:3306
- readHost：192.168.1.63:3306；192.168.1.64:3306

- balance="1"，全部的 readHost与 stand by writeHost 参与 select 语句的负载均衡，简单的说，当双主双从 模式(M1->S1， M2->S2，并且M1 与 M2 互为主备)，正常情况下， M2,S1,S2 都参与 select 语句的负载均衡。 
- writeType=“0”, 所有写操作都发送到可用的writeHost上。

```
<?xml version="1.0"?>
<!DOCTYPE mycat:schema SYSTEM "schema.dtd">
<mycat:schema xmlns:mycat="http://io.mycat/">
    <!-- 添加 dataNode="dn1" -->
    <schema name="TESTDB" checkSQLschema="false" sqlMaxLimit="100" dataNode="dn1">
    </schema>
    <dataNode name="dn1" dataHost="host1" database="test_db" />
    <dataHost name="host1" maxCon="1000" minCon="10" balance="1"
                    writeType="0" dbType="mysql" dbDriver="native"
                    switchType="1" slaveThreshold="100">
        <heartbeat>select user()</heartbeat>
        <!-- can have multi write hosts -->
        <writeHost host="hostM1" url="192.168.1.160:3306" user="root" password="123">
            <!-- can have multi read hosts -->
            <readHost host="hostS2" url="192.168.1.63:3306" user="root" password="123" />
            <readHost host="hostS3" url="192.168.1.64:3306" user="root" password="123" />
        </writeHost>
    </dataHost>
</mycat:schema>
```

**3、启动mycat**

```
[root@mycat-5 ~]# mycat console
或者后台运行
[root@mycat-5 ~]# mycat start

[root@localhost ~]# ss -anple |grep java
```

**4、测试连接**

```
# mysql -umycat -p123456 -h 192.168.1.51 -P8066
mysql> show databases;
mysql> use TESTDB;
mysql> show tables;
mysql> select * from t2;
```

## keepalived高可用

将配置好的mycat打包发送到mycat2

```
[root@mycat1 local]# cd /usr/local/
[root@mycat1 local]# tar zcvf mycat_ok.tar  mycat/
[root@mycat1 local]# scp mycat_ok.tar mycat2:/usr/local/

[root@mycat2 local]# yum install -y java java-devel
[root@mycat2 local]# tar -xf mycat_ok.tar
[root@mycat2 local]# ln -s /usr/local/mycat/bin/* /usr/local/bin/
```

启动两台mycat，测试访问

```
[root@mycat1 local]# mycat start
[root@mycat2 local]# mycat start
[root@mycat2 local]# ss -anplt | grep java
LISTEN     0      1      127.0.0.1:32000                    *:*                   users:(("java",pid=1647,fd=4))
LISTEN     0      50        [::]:36602                 [::]:*                   users:(("java",pid=1647,fd=74))
LISTEN     0      50        [::]:1984                  [::]:*                   users:(("java",pid=1647,fd=71))
LISTEN     0      100       [::]:8066                  [::]:*                   users:(("java",pid=1647,fd=94))
LISTEN     0      50        [::]:41826                 [::]:*                   users:(("java",pid=1647,fd=70))
LISTEN     0      100       [::]:9066                  [::]:*                   users:(("java",pid=1647,fd=90))

```

### 安装keepalived

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

### 配置文件

1. 主节点配置文件

[root@lb-nginx1 ~]# vim /etc/keepalived/keepalived.conf

```
! Configuration File for keepalived

global_defs {
   router_id mycat1
}
vrrp_script chk_mycat {
        script "/etc/keepalived/mycat_check.sh"
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
        chk_mycat ## 执行 Nginx 监控的服务
    }
    virtual_ipaddress {
        192.168.1.150 
    }
}
```

2. 从节点配置文件

[root@lb-nginx2 ~]# vim /etc/keepalived/keepalived.conf

```
! Configuration File for keepalived

global_defs {
   router_id mycat2
}
vrrp_script chk_mycat {
        script "/etc/keepalived/mycat_check.sh" ## 检测 nginx 状态的脚本路径
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
            chk_mycat ## 执行 Nginx 监控的服务
        }
    virtual_ipaddress {
        192.168.1.150
    }
}
```

3. 编写监测心跳脚本

```
# 编写检查脚本，两台都需要
vim /etc/keepalived/mycat_check.sh

#!/bin/bash
counter=$(ps -C java --no-heading|wc -l)
if [ "${counter}" = "0" ]; then
	/etc/init.d/keepalived stop
fi

# 添加执行权限
chmod +x /etc/keepalived/mycat_check.sh
```

### 启动keepalived

```
systemctl start keepalived
```

