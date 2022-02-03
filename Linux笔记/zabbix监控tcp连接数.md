# zabbix 监控 tcp 连接数 

https://www.cnblogs.com/cnsre/p/12882307.html

 [toc]

### 一、zabbix-agent 服务器配置

#### 1、编辑zabbix_agent配置文件，添加以下内容



```
 vim  /etc/zabbix/zabbix_agentd.conf
 ##添加此行
 UserParameter=tcp.state[*],/etc/zabbix/auto_detection_tcp_state.sh $1
```

 

#### 2、重启zabbix-agent



```
 service zabbix_agentd restart
```

 

#### 3、脚本内容





```
cat /etc/zabbix/auto_detection_tcp_state.sh
#!/bin/bash
#TCP连接数及状态
 
if [ $# -ne 1 ];then
        echo -e "\033[32mUsage: sh $0 {ESTABLISHED|LISTEN|TIME_WAIT|CLOSED|CLOSE_WAIT|CLOSING|FIN_WAIT1|FIN_WAIT2|LAST_ACK|SYN_RECV|SYN_SENT}\033[0m"
        exit 1
fi
 
case $1 in
        #socket已经建立连接
        ESTABLISHED)
        result=$(netstat -an | awk '/^tcp/ {print $0}'|grep -wc "ESTABLISHED")
        echo $result
        ;;
        #监听状态  
        LISTEN)
        result=$(netstat -an | awk '/^tcp/ {print $0}'|grep -wc "LISTEN")
        echo $result
        ;;
        #表示收到了对方的FIN报文，并发送出了ACK报文，等待2MSL后就可回到CLOSED状态
        TIME_WAIT)
        result=$(netstat -an | awk '/^tcp/ {print $0}'|grep -wc "TIME_WAIT")
        echo $result
        ;;
        #socket没有被使用，无连接
        CLOSED)
        result=$(netstat -an | awk '/^tcp/ {print $0}'|grep -wc "CLOSED")
        echo $result
        ;;
        #等待关闭连接
        CLOSE_WAIT)
        result=$(netstat -an | awk '/^tcp/ {print $0}'|grep -wc "CLOSE_WAIT")
        echo $result
        ;;
        #服务器端和客户端都同时关闭连接
        CLOSING)
        result=$(netstat -an | awk '/^tcp/ {print $0}'|grep -wc "CLOSING")
        echo $result
        ;;
        #套接字已关闭，连接正在关闭
        FIN_WAIT1)
        result=$(netstat -an | awk '/^tcp/ {print $0}'|grep -wc "FIN_WAIT1")
        echo $result
        ;;
        #连接已关闭，套接字正在等待从远程端关闭
        FIN_WAIT2)
        result=$(netstat -an | awk '/^tcp/ {print $0}'|grep -wc "FIN_WAIT2")
        echo $result
        ;;
        #远端关闭，当前socket被动关闭后发送FIN报文，等待对方ACK报文
        LAST_ACK)
        result=$(netstat -an | awk '/^tcp/ {print $0}'|grep -wc "LAST_ACK")
        echo $result
        ;;
        #接收到SYN报文
        SYN_RECV)
        result=$(netstat -an | awk '/^tcp/ {print $0}'|grep -wc "SYN_RECV")
        echo $result
        ;;
        #已经发送SYN报文
        SYN_SENT)
        result=$(netstat -an | awk '/^tcp/ {print $0}'|grep -wc "SYN_SENT")
        echo $result
        ;;
        *)
        echo -e "\033[32mUsage: sh $0 {ESTABLISHED|LISTEN|TIME_WAIT|CLOSED|CLOSE_WAIT|CLOSING|FIN_WAIT1|FIN_WAIT2|LAST_ACK|SYN_RECV|SYN_SENT}\033[0m"
esac
```



 

#### 4、授权并验证脚本



```
 chmod +x auto_detection_tcp_state.sh
./auto_detection_tcp_state.sh   LISTEN
9
zabbix_get -s 10.0.10.243 -k "tcp.state[LISTEN]"
9
```

 

### 二、Zabbix监控平台配置

#### 1、模板

 

#### 2、导入TCP状态监控模板

配置->模板->导入(右上角)->选择下载的模板文件->最后点击导入

![image-20211124074827456](https://gitee.com/luoxian1011/pictures/raw/master/image-20211124074827456.png)

 

![image-20211124074955317](https://gitee.com/luoxian1011/pictures/raw/master/image-20211124074955317.png)

#### 3、关联模板

要把导入的模板关联到相对应的主机上边、配置->点击你的主机->模板->选择刚才导入模板，点击添加，最后点击更新即可。

![image-20211124075024269](https://gitee.com/luoxian1011/pictures/raw/master/image-20211124075024269.png)

### 最后展示

![image-20211124075058047](https://gitee.com/luoxian1011/pictures/raw/master/image-20211124075058047.png)

 

![image-20211124075115902](https://gitee.com/luoxian1011/pictures/raw/master/image-20211124075115902.png)

ESTABLISHED socket已经建立连接

CLOSED socket没有被使用，无连接

CLOSING 服务器端和客户端都同时关闭连接

CLOSE_WAIT 等待关闭连接

TIME_WAIT 表示收到了对方的FIN报文，并发送出了ACK报文，等待2MSL后就可回到CLOSED状态

LAST_ACK 远端关闭，当前socket被动关闭后发送FIN报文，等待对方ACK报文

LISTEN 监听状态

SYN_RECV 接收到SYN报文

SYN_SENT 已经发送SYN报文

FIN_WAIT1 The socket is closed, and the connection is shutting down

FIN_WAIT2 Connection is closed, and the socket is waiting for a shutdown from the remote

 

### TCP 相关资料

![img](https://img2020.cnblogs.com/blog/2022973/202005/2022973-20200513144948869-626585545.png)

time_wait状态产生的原因，危害，如何避免

https://blog.csdn.net/huangyimo/article/details/81505558

什么是time_wait？如何产生的？

https://blog.csdn.net/qq_26674507/article/details/89489027