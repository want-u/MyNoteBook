## ntp时间服务器

## 阿里同步：
ntpdate ntp1.aliyun.com

### （一）确认ntp的安装
    1）确认是否已安装ntp
    【命令】rpm -qa | grep ntp
    若只有ntpdate而未见ntp，则需删除原有ntpdate。如：
    ntpdate-4.2.6p5-22.el7_0.x86_64
    fontpackages-filesystem-1.44-8.el7.noarch
    python-ntplib-0.3.2-1.el7.noarch
    
    2）删除已安装ntp
    【命令】yum -y remove ntpdate-4.2.6p5-22.el7.x86_64
    
    3）重新安装ntp
    【命令】yum -y install ntp
    
### （二）配置ntp服务

    1）修改所有节点的/etc/ntp.conf
    【命令】vim /etc/ntp.conf
    【内容】
    restrict 192.168.10.11 nomodify notrap nopeer noquery          //当前节点IP地址
    restrict 192.168.10.1 mask 255.255.255.0 nomodify notrap  //集群所在网段的网关（Gateway），子网掩码（Genmask）
    
    2）选择一个主节点，修改其/etc/ntp.conf
    【命令】vim /etc/ntp.conf
    【内容】在server部分添加一下部分，并注释掉server 0 ~ n
    server 127.127.1.0
    Fudge 127.127.1.0 stratum 10
    
    3）主节点以外，继续修改/etc/ntp.conf
    【命令】vi /etc/ntp.conf
    【内容】在server部分添加如下语句，将server指向主节点，并注释掉server 0 ~ n
    server 192.168.10.11
    Fudge 192.168.10.11 stratum 10
    
## （三）启动ntp服务、查看状态

    1）启动ntp服务
    【命令】systemctl start ntpd
    【命令】systemctl enable ntpd
    
    2）查看ntp服务器有无和上层ntp连通
    【命令】ntpstat
    ntp服务器配置完毕后，需要等待5-10分钟才能与/etc/ntp.conf中配置的标准时间进行同步
    【正常状态】
    synchronised to local net (127.127.1.0) at stratum 6
     time correct to within 7948 ms
     polling server every 64 s
     
    3）查看ntp服务器与上层ntp的状态
    【命令】ntpq -p
    
## （四）客户端

    1）测试ntp服务器
    【命令】ntpdate 192.168.10.11
    
    2）启动ntp服务
    【命令】systemctl start ntpd
    【命令】systemctl enable ntpd
    
    3）任务计划
    10 5 * * * root /usr/sbin/ntpdate 192.168.10.11 && /sbin/hwclock -w
    
## TIP检查

    1、设置服务器时间
    date -s "2018-08-08 08:08:08"
    2、客户端同步
    systemctl stop ntpd
    ntpdate 192.168.10.11
    systemctl start ntpd
    
    


    