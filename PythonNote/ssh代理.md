# ssh代理
[toc]

## 1、使用ProxyCommand透过多层跳板机
https://cikeblog.com/proxycommand.html

编写ssh的配置文件：需要将下面的内容添加到自己的~/.ssh/config文件中

(~是自己的家命令,如果是root用户就是/root,如果是非root用户就是/home/用户名,这个文件如果不存在就自己新建出来.)

```
Host tiaoban
 Hostname 跳板机的ip
 Port 跳板机的端口(如果是非22的需要填写)
 User root(如果非root,换成跳板机的用户)

Host mubiaoji
 Hostname 目标机的IP
 Port 跳板机的端口(如果是非22的需要填写)
 User root(如果非root,换成跳板机的用户)
 ProxyCommand ssh -q -x -W %h:%p tiaoban

    %h 表示要连接的目标机,也就是Hostname指定的ip或者主机名
    %p 表示要连接到目标机的端口.这儿可以直接写死固定值,但是使用%h和%p可以保证在Hostname和Port变化的情况下ProxyCommand这行不用跟着变化
```
使用ssh连接: ssh mubiaoji

示例：
```
# ssh连接跳板机器：当前环境中的跳板机 hostname一致，port参考代理端口个位减一

# 跳板机
Host tiaoban
        User root
        Hostname 100.xx.xx.9
        Port 50100

# 通过跳板 跳到mubiaoji
Host mubiaoji
        User root
        Hostname 10.xx.xx.56
        ProxyCommand ssh -q -x -W %h:%p tiaoban

# 正常ssh别名
Host host1
        Hostname 100.xx.xx.166
        Port 53300
        User root

# 直接指定代理连接
Host host2
        Hostname 10.xx.xx.155
        Port 22
        User root
        ProxyCommand nc -X connect -x 100.xx.xx.166:53301 %h %p

```

## 2、建立本地转发的隧道(正向代理)
所谓“正向代理”就是在本地启动端口，把本地端口数据转发到远端。

https://www.cnblogs.com/shiqi17/p/15324325.html

https://zhuanlan.zhihu.com/p/57630633


参数说明：

```
本地端口通过跳板映射到其他机器
这时访问 HostA:PortA 相当于访问 HostC:PortC。

HostA 上启动一个 PortA 端口，通过 HostB 转发到 HostC:PortC上，在 HostA 上运行：
ssh -NfL PortA:HostC:PortC user@HostB

-L：正向代理
-N：不打开远程shell，处于等待状态（不加-N则直接登录进去） 
-f：后台启用 
```

示例（在本地启动6000端口，代理mubiaoji的22端口）：

```
ssh -fNL 6000:127.0.0.1:22  mubiaoji
```

Windows环境中

查看端口：


```
netstat -ano | findstr "6000" 
netstat -ano | findstr "7000" 
```

杀掉代理：


```
killall _ssh
```


连接隧道


```
ssh -p 6000 root@127.0.0.1
```

直接使用代理参数连接


```
ssh -o ProxyCommand="nc -X connect -x 100.xx.xx.166:53301 %h %p" root@10.xx.xx.155
```

## 3、开启本地socks5代理


在 HostA 的本地 7000 端口启动一个 socks5 服务，通过本地 socks5 代理的数据会通过 ssh 链接先发送给 HostB，再从 HostB 转发送给远程主机：


```
HostA$ ssh -D localhost:7000  HostB
```
那么在 HostA 上面，浏览器配置 socks5 代理为 127.0.0.1:7000 ，看网页时就能把数据通过 HostB 代理出去，类似 ss/ssr 版本，只不过用 ssh 来实现。


参数说明

```
-D：socks5 代理：相当于 ss/ssr

ssh -NfD 127.0.0.1:7000  mubiaoji

ssh -NfD 127.0.0.1:7000 root@100.xx.xx.166 -p53300
```



```
测试是否可以连外网（endpoint的ip）

curl --socks5 127.0.0.1:7000 10.xx.xx.17
```

## 4、代码中设置代理


本地代码测试endpoint建议使用ip，或者自行添加hosts解析

java设置代理(源程序设置代理)


https://docs.oracle.com/javase/8/docs/technotes/guides/net/proxies.html


```
- SOCKS
    System.setProperty("socksProxyHost", "127.0.0.1");
    System.setProperty("socksProxyPort", "7000");

- HTTP（未验证）
    System.setProperty("http.proxySet", "true");
    System.setProperty("http.proxyHost", proxyHost);
    System.setProperty("http.proxyPort", proxyPort);

命令行代码设置代理（未验证）
    java -DsocksProxyHost=代理服务器的IP地址 -DsocksProxyPort=代理服务器监听端口     要执行的源程序
```

python设置代理(源程序设置代理)


https://www.cnblogs.com/woaixuexi9999/p/9360581.html

```
在代码中添加：

import socket

import socks  # pip install PySocks

- SOCKS
    socks.set_default_proxy(socks.SOCKS5, addr='127.0.0.1', port=7000)  # 设置socks代理
    socket.socket = socks.socksocket  # 把代理应用到socket

- HTTP
    socks.set_default_proxy(socks.HTTP, addr='100.xx.xx.166', port=53301)  # 设置socks代理
    socket.socket = socks.socksocket  # 把代理应用到socket
```


打开cmd设置代理：

    set http_proxy=http://0.0.0.0:8000