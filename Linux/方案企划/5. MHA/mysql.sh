#!/bin/bash
yum install -y libaio
tar -zxf mysql-5.7.29-linux-glibc2.12-x86_64.tar.gz
#将二进制包移动到/usr/local/mysql
mv mysql-5.7.29-linux-glibc2.12-x86_64 /usr/local/mysql
#创建data目录
mkdir /usr/local/mysql/data
#创建用户和组并赋予文件夹权限
groupadd -r mysql
useradd -r -g mysql -M -s /bin/false mysql
chown -R mysql:mysql /usr/local/mysql
#删除centos7中自带的mariadb-libs
#rpm -e mariadb-libs --nodeps
#设置my.cnf配置文件
cat> /etc/my.cnf <<EOF
[client]
socket=/usr/local/mysql/mysql.sock
[mysqld]
basedir=/usr/local/mysql
datadir=/usr/local/mysql/data
pid-file=/usr/local/mysql/data/mysql.pid
socket=/usr/local/mysql/mysql.sock
log-error=/usr/local/mysql/data/mysql.err
EOF
#做服务的软链接
ln -s /usr/local/mysql/bin/* /usr/local/bin/
#mysql初始化
mysqld --initialize --user=mysql --basedir=/usr/local/mysql/ --datadir=/usr/local/mysql/data
#服务自启动
mv /usr/local/mysql/support-files/mysql.server /etc/init.d/mysqld
chkconfig --add mysqld
chkconfig mysqld on
#启动mysql并修改密码为123
/etc/init.d/mysqld start
mysqlpwd=` grep password /usr/local/mysql/data/mysql.err |awk -F 'root@localhost: ' '{print $2}'`
mysql -uroot -p${mysqlpwd} -e 'alter user root@localhost identified by"123"' --connect-expired-password

