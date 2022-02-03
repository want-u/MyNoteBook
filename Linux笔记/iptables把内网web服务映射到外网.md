# iptables把内网web服务映射到外网

[iptables案例：iptables把内网web服务映射到外网_麦子地的专栏-CSDN博客_iptables内网ip映射到外网](https://blog.csdn.net/wheat_ground/article/details/78562459)

[toc]

## 背景和原理

左边为机器A，右边为机器B。B有两块网卡，一块和A在一个局域网，一块可以连外网。A外网不通。
下面希望外网的用户可以访问A机器提供的web服务。
![这里写图片描述](https://img-blog.csdn.net/20171117155301901?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvd2hlYXRfZ3JvdW5k/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

## iptables配置

### 机器B

开启内核路由转发功能

```
[root@xuegod63 ~]# vim /etc/sysctl.conf
#改：#net.ipv4.ip_forward = 0
#为： net.ipv4.ip_forward = 1
#改完使配置生效：
[root@xuegod63 ~]# sysctl -p12345
```

iptables转发配置

```
[root@xuegod63 ~]#  iptables -t nat -A PREROUTING -i eth0 -p tcp --dport 80 -j DNAT --to 192.168.240.2:80
或：
[root@localhost ~]# iptables -t nat -A PREROUTING -d 192.168.1.250 -p tcp --dport 80 -j DNAT --to 192.168.240.2:80123
```

### 机器A

安装apache服务

```
[root@localhost ~]# yum install httpd -y
[root@localhost ~]# /etc/init.d/httpd start12
```

### 外网机器访问

![这里写图片描述](https://img-blog.csdn.net/20171117160045450?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvd2hlYXRfZ3JvdW5k/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)