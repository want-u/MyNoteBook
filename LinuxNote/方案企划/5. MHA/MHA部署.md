



## 拓扑

| 主机名               | IP            | 角色       |
| -------------------- | ------------- | ---------- |
| mha-mysql-vip        | 192.168.1.160 |            |
| mha-mysql-manager    | 192.168.1.61  | manager    |
| mha-mysql-master     | 192.168.1.62  | master     |
| mha-mysql-master-bak | 192.168.1.63  | master-bak |
| mha-mysql-slave      | 192.168.1.64  | slave      |

## 部署MHA

### 安装mysql

```
# 上传mysql编译好的二进制包 和 一键安装脚本
# 编译二进制包：https://cdn.mysql.com//Downloads/MySQL-5.7/mysql-5.7.29-linux-glibc2.12-x86_64.tar.gz
# 一键脚本：https://gitee.com/luoxian1011/files/raw/master/mysql.sh

# 一键安装mysql，root密码为123
sh mysql.sh
```

### 建立ssh无密环境 

```
#### 在所有mha集群节点上执行免密
ssh-keygen -t rsa
for i in 61 62 63 64
do ssh-copy-id -i ~/.ssh/id_rsa.pub root@192.168.1.$i
done
```

```
# 测试ssh无交互登录
for i in 61 62 63 64
do ssh root@192.168.1.$i hostname
done
```

### 配置半同步复制 

#### 安装半同步插件

**分别在主从节点上安装相关的插件（master, master-bak, slave）** 

**所有mysql数据库服务器，安装半同步插件**(semisync_master.so,semisync_slave.so)

```
mysql> INSTALL PLUGIN rpl_semi_sync_master SONAME 'semisync_master.so'; 
mysql> INSTALL PLUGIN rpl_semi_sync_slave SONAME 'semisync_slave.so';
```

其他mysql主机采用同样的方法安装 检查Plugin是否已正确安装：

```
mysql> show plugins;
...
| rpl_semi_sync_master       | ACTIVE   | REPLICATION        | semisync_master.so | GPL     |
| rpl_semi_sync_slave        | ACTIVE   | REPLICATION        | semisync_slave.so  | GPL     |
+----------------------------+----------+--------------------+--------------------+---------+
```

**查看半同步相关信息**

```
mysql> show variables like '%rpl_semi_sync%';
+-------------------------------------------+------------+
| Variable_name                             | Value      |
+-------------------------------------------+------------+
| rpl_semi_sync_master_enabled              | OFF        |
| rpl_semi_sync_master_timeout              | 10000      |
| rpl_semi_sync_master_trace_level          | 32         |
| rpl_semi_sync_master_wait_for_slave_count | 1          |
| rpl_semi_sync_master_wait_no_slave        | ON         |
| rpl_semi_sync_master_wait_point          | AFTER_SYNC |
| rpl_semi_sync_slave_enabled              | OFF        |
| rpl_semi_sync_slave_trace_level          | 32         |
+-------------------------------------------+-----------+
# 上图可以看到半同复制插件已经安装，只是还没有启用，所以是off
```

#### 修改my.cnf文件

```
#### master 主机：
server-id = 1
log-bin=mysql-bin
binlog_format=mixed
log-bin-index=mysql-bin.index
rpl_semi_sync_master_enabled=1
rpl_semi_sync_master_timeout=1000
rpl_semi_sync_slave_enabled=1
relay_log_purge=0
relay-log = relay-bin
relay-log-index = slave-relay-bin.index

# 参数注释
# rpl_semi_sync_master_enabled=1 1表是启用，0表示关闭 
# rpl_semi_sync_master_timeout=10000：
# 毫秒单位 ，该参数主服务器等待确认消息10秒后，不再等待，变为异步方式。
```

```
#### master-bak 主机：
server-id = 2
log-bin=mysql-bin
binlog_format=mixed
log-bin-index=mysql-bin.index
relay_log_purge=0
relay-log = relay-bin
relay-log-index = slave-relay-bin.index
rpl_semi_sync_master_enabled=1
rpl_semi_sync_master_timeout=10000
rpl_semi_sync_slave_enabled=1

# 参数注释
# relay_log_purge=0
# 禁止 SQL 线程在执行完一个 relay log 后自动将其删除
# 对于MHA场景下，对于某些滞后从库的恢复依赖于其他从库的relay log
# 因此采取禁用自动删除功能
```

```
#### Slave 主机：
server-id = 3
log-bin = mysql-bin
relay-log = relay-bin
relay-log-index = slave-relay-bin.index
read_only = 1
rpl_semi_sync_slave_enabled=1
```

重启mysql；查看半同步相关信息

```
# 重启mysql
/etc/init.d/mysqld restart
# 登陆
mysql -uroot -p123
# 查看
mysql> show variables like '%rpl_semi_sync%';
+-------------------------------------------+------------+
| Variable_name                             | Value      |
+-------------------------------------------+------------+
| rpl_semi_sync_master_enabled              | ON         |
| rpl_semi_sync_master_timeout              | 1000       |
| rpl_semi_sync_master_trace_level          | 32         |
| rpl_semi_sync_master_wait_for_slave_count | 1          |
| rpl_semi_sync_master_wait_no_slave        | ON         |
| rpl_semi_sync_master_wait_point          | AFTER_SYNC |
| rpl_semi_sync_slave_enabled              | ON         |
| rpl_semi_sync_slave_trace_level          | 32         |
+-------------------------------------------+-----------+
```

查看半同步状态：

```

mysql> show status like '%rpl_semi_sync%';
+--------------------------------------------+-------+
| Variable_name                              | Value |
+--------------------------------------------+-------+
| Rpl_semi_sync_master_clients               | 0     |
| Rpl_semi_sync_master_net_avg_wait_time     | 0     |
| Rpl_semi_sync_master_net_wait_time         | 0     |
| Rpl_semi_sync_master_net_waits             | 0     |
| Rpl_semi_sync_master_no_times              | 0     |
| Rpl_semi_sync_master_no_tx                | 0     |
| Rpl_semi_sync_master_status               | ON    |
| Rpl_semi_sync_master_timefunc_failures    | 0     |
| Rpl_semi_sync_master_tx_avg_wait_time     | 0     |
| Rpl_semi_sync_master_tx_wait_time         | 0     |
| Rpl_semi_sync_master_tx_waits             | 0     |
| Rpl_semi_sync_master_wait_pos_backtraverse| 0     |
| Rpl_semi_sync_master_wait_sessions        | 0     |
| Rpl_semi_sync_master_yes_tx               | 0     |
| Rpl_semi_sync_slave_status                | OFF   |
+--------------------------------------------+------+
```

### 配置主从同步

master1主机：

```

grant replication slave on *.* to mharep@'192.168.1.%' identified by '123';
grant all privileges on *.* to manager@'192.168.1.%' identified by '123';
show master status;
# 第一条grant命令是创建一个用于主从复制的帐号，在master和candicate master的主机上创建即可。 
# 第二条grant命令是创建MHA管理账号，所有mysql服务器上都需要执行。
# MHA会在配置文件里要求能远程登录到数据库，所以要进行必要的赋权
```

master2主机：

```

grant replication slave on *.* to mharep@'192.168.1.%' identified by '123';
grant all privileges on *.* to manager@'192.168.1.%' identified by '123';
change master to
master_host='192.168.1.62',
master_port=3306,
master_user='mharep',
master_password='123',
master_log_file='mysql-bin.000001',
master_log_pos=742;
start slave;
show slave status\G
```

Slave主机：

```

grant all privileges on *.* to manager@'192.168.1.%' identified by '123';
change master to
master_host='192.168.1.62',
master_port=3306,
master_user='mharep',
master_password='123',
master_log_file='mysql-bin.000001',
master_log_pos=742;
start slave;
show slave status\G
```

查看master1服务器的半同步状态：

```

mysql> show status like '%rpl_semi_sync%';
```

### 配置mysql-mha

mha包括manager节点和data节点，data节点包括原有的MySQL复制结构中的主机，至少3台，即1主2从，当master failover后，还能保证主从结构；

- master，master-bak，slavev：只需安装node包。
- manager需要安装node包和manager包。 

**1、 在所有主机上安装mha所依赖的软件包（需要系统自带的yum源并联网）**

```
yum -y install perl-DBD-MySQL perl-Config-Tiny perl-Log-Dispatch perl-Parallel-ForkManager perl-Config-IniFiles ncftp perl-Params-Validate perl-CPAN perl-Test-Mock-LWP.noarch perl-LWP-Authen-Negotiate.noarch perl-devel perl-ExtUtils-CBuilder perl-ExtUtils-MakeMaker

```

**2、 以下操作管理节点需要两个都安装， 在3台数据库节点只要安装MHA的node节点：**

**在所有数据库节点上安装：**mha4mysql-node-0.56.tar.gz

```
tar zxf mha4mysql-node-0.58.tar.gz
cd mha4mysql-node-0.58
perl Makefile.PL
make && make install
```

**在管理节点需要两个都安装**：mha4mysqlnode-0.56.tar.gz和mha4mysql-manager-0.56.tar.gz 

```
tar zxf mha4mysql-node-0.58.tar.gz
cd mha4mysql-node-0.58
perl Makefile.PL
make && make install

tar zxf mha4mysql-manager-0.58.tar.gz
cd mha4mysql-manager-0.58/
perl Makefile.PL
make && make install
```

**在管理节点根据提示输入：**

```
mkdir /etc/masterha
mkdir -p /masterha/app1
mkdir /scripts
cp samples/conf/* /etc/masterha/
cp samples/scripts/* /scripts/
```

## 配置mha

**编辑/etc/masterha/app1.conf，内容如下：**

```
vim /etc/masterha/app1.cnf

[server default]
manager_workdir=/masterha/app1
manager_log=/masterha/app1/manager.log
user=manager
password=123
ssh_user=root
repl_user=mharep
repl_password=123
ping_interval=1

[server1]
hostname=192.168.1.62
port=3306
master_binlog_dir=/usr/local/mysql/data
candidate_master=1

[server2]
hostname=192.168.1.63
port=3306
master_binlog_dir=/usr/local/mysql/data
candidate_master=1

[server3]
hostname=192.168.1.64
port=3306
master_binlog_dir=/usr/local/mysql/data
no_master=1
```

**清空masterha_default.cnf**

```

>/etc/masterha/masterha_default.cnf
```

**配关配置项的解释：** 

```
# 设置manager的工作目录
manager_workdir=/masterha/app1 		

# 设置manager的日志 
manager_log=/masterha/app1/manager.log 	

# 设置监控用户manager
user=manager 		

# 监控用户manager的密码 
password=123456 	

# ssh连接用户 repl_user=mharep //主从复制用户
ssh_user=root 	

# 主从复制用户密码 ping_interval=1 //设置监控主库，发送ping包的时间间隔，默认是3秒，尝试三次没有回应的时候自动进行
repl_password=123			

# 设置master 保存binlog的位置，以便MHA可以找到master的日志，我这里的也就是mysql的数据目录 
master_binlog_dir=/usr/local/mysql/data 

# 设置为候选master，如果设置该参数以后，发生主从切换以后将会将此从库提升为主库。
candidate_master=1 				
```

**SSH 有效性验证：**

```
masterha_check_ssh --global_conf=/etc/masterha/masterha_default.cnf --conf=/etc/masterha/app1.cnf

```

**集群复制的有效性验证： mysql必须都启动**

```
ln -s /usr/local/mysql/bin/* /usr/local/bin/  # 四台主机都要做链接
masterha_check_repl --global_conf=/etc/masterha/masterha_default.cnf --conf=/etc/masterha/app1.cnf

```

**启动 manager：**

```
nohup masterha_manager --conf=/etc/masterha/app1.cnf &>/tmp/mha_manager.log &

```

**状态检查：**

```
masterha_check_status --conf=/etc/masterha/app1.cnf
```

**故障转移验证：**

```
# 先停掉 master1，候选master2库（Slave）会自动failover为Master. MHA自动停止
# 重启master1，设为master2的从

# 得到change master信息
grep 'CHANGE' /masterha/app1/manager.log

# 删除manager的 masterha/app1/app1.failover.complete文件
rm -f /masterha/app1/app1.failover.complete

# 启动MHA
nohup masterha_manager --conf=/etc/masterha/app1.cnf &>/tmp/mha_manager.log &

# 检查状态：
masterha_check_status --conf=/etc/masterha/app1.cnf

# 检查日志：
tail -f /masterha/app1/manager.log
```

## 配置VIP

通过脚本的方式管理VIP。这里是修改/scripts/master_ip_failover

**1）手动在master服务器上绑定一个vip**

```
ifconfig ens33:0 192.168.1.160/24
```

**2）在mha-manager上修改/scripts/ master_ip_failover，内容如下**

```
cat /scripts/master_ip_failover

#!/usr/bin/env perl
use strict;
use warnings FATAL => 'all';
use Getopt::Long;
my (
$command,$ssh_user,$orig_master_host,$orig_master_ip,$orig_master_port,
$new_master_host,$new_master_ip,$new_master_port
);
my $vip = '192.168.1.160';
my $key = '0';
my $ssh_start_vip = "/sbin/ifconfig ens33:$key $vip";
my $ssh_stop_vip = "/sbin/ifconfig ens33:$key down";
GetOptions(
'command=s' => \$command,
'ssh_user=s' => \$ssh_user,
'orig_master_host=s' => \$orig_master_host,
'orig_master_ip=s' => \$orig_master_ip,
'orig_master_port=i' => \$orig_master_port,
'new_master_host=s' => \$new_master_host,
'new_master_ip=s' => \$new_master_ip,
'new_master_port=i' => \$new_master_port,
);
exit &main();
sub main {
print "\n\nIN SCRIPT TEST====$ssh_stop_vip==$ssh_start_vip===\n\n";
if ( $command eq "stop" || $command eq "stopssh" ) {
my $exit_code = 1;
eval {
print "Disabling the VIP on old master: $orig_master_host \n";
&stop_vip();
$exit_code = 0;
};
if ($@) {
warn "Got Error: $@\n";
exit $exit_code;
}
exit $exit_code;
}
elsif ( $command eq "start" ) {
my $exit_code = 10;
eval {
print "Enabling the VIP - $vip on the new master - $new_master_host
\n";
&start_vip();
$exit_code = 0;
};
if ($@) {
warn $@;
exit $exit_code;
}
exit $exit_code;
}
elsif ( $command eq "status" ) {
print "Checking the Status of the script.. OK \n";
#`ssh $ssh_user\@cluster1 \" $ssh_start_vip \"`;
exit 0;
}
else {
&usage();
exit 1;
}
}
# A simple system call that enable the VIP on the new master
sub start_vip() {
`ssh $ssh_user\@$new_master_host \" $ssh_start_vip \"`;
}
# A simple system call that disable the VIP on the old_master
sub stop_vip() {
return 0 unless ($ssh_user);
`ssh $ssh_user\@$orig_master_host \" $ssh_stop_vip \"`;
}
sub usage {
print
"Usage: master_ip_failover --command=start|stop|stopssh|status --
orig_master_host=host --orig_master_ip=ip --orig_master_port=port --
new_master_host=host --new_master_ip=ip --new_master_port=port\n";
}
```

**3）在配置文件/etc/masterha/app1.cnf 中启用下面的参数(在[server default下面添加])**

```
grep "master_ip_failover_script" /etc/masterha/app1.cnf
master_ip_failover_script=/scripts/master_ip_failover
```

**4）启动MHA**

```
nohup masterha_manager --conf=/etc/masterha/app1.cnf &>/tmp/mha_manager.log &
masterha_check_status --conf=/etc/masterha/app1.cnf
masterha_check_repl --conf=/etc/masterha/app1.cnf

# 停止mha
masterha_stop --conf=/etc/masterha/app1.cnf
```

**5）测试：**

 在master上停止mysqld服务 到slave(192.168.10.13)查看slave的状态：

 查看VIP绑定