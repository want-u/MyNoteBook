# dig工具
[toc]

   dig是一个DNS查询工具，多数管理员会使用dig命令来解决DNS的问题。

   此命令的适用范围：RedHat、RHEL、Ubuntu、CentOS、Fedora。
   
   安装：yum install bind-utils -y


 

## **1**、语法

   dig [选项]

 

## **2**、参数列表

| 选项        | 说明                              |
| ----------- | --------------------------------- |
| @server | 指定服务器地址                    |
| **-b** host | 指定通过哪个主机查询              |
| **-f** file | 从指定文件来查询                  |
| **-p** port | 指定使用的端口                    |
| **-t** type | 指定要查询的DNS类型，例如A\MX\PRT |
| **-x** ip   | 指定DNS你想查询，输入ip得到域名   |
| **-4**      | 使用ipv4                          |
| **-6**      | 使用ipv6                          |

 

## **3**、实例

### 1）查询域名信息

```
[root@localhost ~]# dig www.baidu.com
; <<>> DiG 9.8.2rc1-RedHat-9.8.2-0.17.rc1.el6_4.6 <<>> www.baidu.com
;; global options: +cmd
;; Got answer:
;; ->>HEADER<<- opcode: QUERY, status: NOERROR, id: 23028
;; flags: qr rd ra; QUERY: 1, ANSWER: 3, AUTHORITY: 0, ADDITIONAL: 0
 
;; QUESTION SECTION:
;www.baidu.com.      IN  A

;; ANSWER SECTION:
www.baidu.com.    572 IN  CNAME  www.a.shifen.com.
www.a.shifen.com. 65  IN  A   111.13.100.92
www.a.shifen.com. 65  IN  A   111.13.100.91

;; Query time: 103 msec
;; SERVER: 172.20.10.1#53(172.20.10.1)
;; WHEN: Sat Oct  6 08:08:04 2018
;; MSG SIZE  rcvd: 90
```

### 2）反向查询

```
[root@localhost ~]# dig -t a -x 111.13.100.92

; <<>> DiG 9.8.2rc1-RedHat-9.8.2-0.17.rc1.el6_4.6 <<>> -t a -x 111.13.100.92
;; global options: +cmd
;; Got answer:
;; ->>HEADER<<- opcode: QUERY, status: NXDOMAIN, id: 8225
;; flags: qr rd ra; QUERY: 1, ANSWER: 0, AUTHORITY: 0, ADDITIONAL: 0

;; QUESTION SECTION:
;92.100.13.111.in-addr.arpa.    IN  A

;; Query time: 81 msec
;; SERVER: 172.20.10.1#53(172.20.10.1)
;; WHEN: Sat Oct  6 08:09:51 2018
;; MSG SIZE  rcvd: 44
```