# Zabbix监控ssl证书到期时间 

https://www.cnblogs.com/cnsre/p/13666267.html

[toc]

**先放上脚本**





```
#!/bin/sh
host=$1
end_date=`openssl s_client -servername $host -host $host -port 443 -showcerts </dev/null 2>/dev/null |
  sed -n '/BEGIN CERTIFICATE/,/END CERT/p' |
  openssl x509 -text 2>/dev/null |
  sed -n 's/ *Not After : *//p'`
if [ -n "$end_date" ]
then
    end_date_seconds=`date '+%s' --date "$end_date"`
    now_seconds=`date '+%s'`
    echo "($end_date_seconds-$now_seconds)/24/3600" | bc
fi
```



 

## 一、server端操作

找一个安装有agent 的server 进行一下操作

### 1、安装所需组件





```
yum install bc gcc gcc-c++ -y
安装openssl
yum install openssl -y
验证openssl
[root@elk scripts]openssl version OpenSSL 1.0.2k-fips 26 Jan 2017
```



 

### 2、验证脚本



```
赋予权限 [root@bac scripts] chmod +x check_ssl.sh [root@elk scripts]./check_ssl.sh xxxx-xxxx.cn 565 #单位为天
```

 

### 3、zabbix 配置文件中添加配置



```
[root@elk scripts]cat /etc/zabbix/zabbix_agentd.conf |grep ssl UserParameter=check.ssl[*],/etc/zabbix/scripts/check_ssl.sh $1
```

 

### 4、重启zabbix agent



```
systemctl restart zabbix-agent
```

 

## 二、web端操作

### 创建监控项

#### 1、登录zabbix 创建新的监控项

![img](https://img2020.cnblogs.com/blog/2022973/202009/2022973-20200914140736134-1669444962.png)

 

 

名称随意填写

键值为你添加在配置文件中的check.ssl[*] *为你的域名

更新间隔因为证书不需要做实时的检查 所以时间可以设置长一点

#### 2、检查创建监控项是否生效

创建完成以后可以在检测---最新数据中查看监控项

![img](https://img2020.cnblogs.com/blog/2022973/202009/2022973-20200914140557609-1268283887.png)

 

 

### 创建触发器

#### 1、监控项有了 接下来创建触发器

![img](https://img2020.cnblogs.com/blog/2022973/202009/2022973-20200914140607666-49728151.png)

 

 

### 测试告警

#### 1. 创建完毕 来测试下告警

把阈值调为600 天来测试下告警

![img](https://img2020.cnblogs.com/blog/2022973/202009/2022973-20200914140618888-418244856.png)

 

 

#### 2.触发告警

因为我们把更新间隔调的时间比较长 所以我们为了快速验证告警可以调小

![img](https://img2020.cnblogs.com/blog/2022973/202009/2022973-20200914140627530-1936286694.png)

 

 

这个时候我们可以看到我们的告警信息

![img](https://img2020.cnblogs.com/blog/2022973/202009/2022973-20200914140634411-391419829.png)

 

 

到这SSL 证书监控告警已经完成。

#### 3、看到告警信息之后记得阈值调回来