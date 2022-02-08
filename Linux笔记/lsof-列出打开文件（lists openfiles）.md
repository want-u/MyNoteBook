# lsof-列出打开文件（lists openfiles）

[Linux 命令神器：lsof - 简书 (jianshu.com)](https://www.jianshu.com/p/a3aa6b01b2e1)

[toc]

lsof是系统管理/[安全](http://linuxaria.com/tag/security)的尤伯工具。将这个工具称之为lsof真实名副其实，因为它是指“列出打开文件（lists openfiles）”。而有一点要切记，在Unix中一切（包括网络套接口）都是文件。

有趣的是，lsof也是有着最多开关的Linux/Unix命令之一。它有那么多的开关，它有许多选项支持使用-和+前缀。



```css
1.  usage:  [-?abhlnNoOPRstUvV]  [+|-c c]  [+|-d s]  [+D D]  [+|-f[cgG]]
2.  [-F [f]]  [-g [s]]  [-i [i]]  [+|-L [l]]  [+|-M]  [-o [o]]
3.  [-p s]  [+|-r [t]]  [-S [t]]  [-T [t]]  [-u s]  [+|-w]  [-x [fl]]  [--]  [names]
```

正如你所见，lsof有着实在是令人惊讶的选项数量。你可以使用它来获得你系统上设备的信息，你能通过它了解到指定的用户在指定的地点正在碰什么东西，或者甚至是一个进程正在使用什么文件或网络连接。

对于我，lsof替代了netstat和ps的全部工作。它可以带来那些工具所能带来的一切，而且要比那些工具多得多。那么，让我们来看看它的一些基本能力吧：

### 关键选项

理解一些关于lsof如何工作的关键性东西是很重要的。最重要的是，当你给它传递选项时，默认行为是对结果进行“或”运算。因此，如果你正是用-i来拉出一个端口列表，同时又用-p来拉出一个进程列表，那么默认情况下你会获得两者的结果。

下面的一些其它东西需要牢记：

- 默认 : 没有选项，lsof列出活跃进程的所有打开文件
- 组合 : 可以将选项组合到一起，如-abc，但要当心哪些选项需要参数
- -a : 结果进行“与”运算（而不是“或”）
- -l : 在输出显示用户ID而不是用户名
- -h : 获得帮助
- -t : 仅获取进程ID
- -U : 获取UNIX套接口地址
- -F : 格式化输出结果，用于其它命令。可以通过多种方式格式化，如-F pcfn（用于进程id、命令名、文件描述符、文件名，并以空终止）

### 获取网络信息

正如我所说的，我主要将lsof用于获取关于系统怎么和网络交互的信息。这里提供了关于此信息的一些主题：

#### 使用-i显示所有连接

有些人喜欢用netstat来获取网络连接，但是我更喜欢使用lsof来进行此项工作。结果以对我来说很直观的方式呈现，我仅仅只需改变我的语法，就可以通过同样的命令来获取更多信息。

语法: lsof -i[46] [protocol][@hostname|hostaddr][:service|port]



```css
1.  #  lsof  -i

3.  COMMAND  PID USER   FD   TYPE DEVICE SIZE NODE NAME
4.  dhcpcd 6061 root 4u  IPv4  4510 UDP *:bootpc
5.  sshd  7703 root 3u  IPv6  6499 TCP *:ssh  (LISTEN)
6.  sshd  7892 root 3u  IPv6  6757 TCP 10.10.1.5:ssh->192.168.1.5:49901  (ESTABLISHED)
```

#### 使用-i 6仅获取IPv6流量



```bash
1.  #  lsof  -i 6
```

#### 仅显示TCP连接（同理可获得UDP连接）

你也可以通过在-i后提供对应的协议来仅仅显示TCP或者UDP连接信息。



```css
1.  #  lsof  -iTCP

3.  COMMAND  PID USER   FD   TYPE DEVICE SIZE NODE NAME
4.  sshd  7703 root 3u  IPv6  6499 TCP *:ssh  (LISTEN)
5.  sshd  7892 root 3u  IPv6  6757 TCP 10.10.1.5:ssh->192.168.1.5:49901  (ESTABLISHED)
```

#### 使用-i:port来显示与指定端口相关的网络信息

或者，你也可以通过端口搜索，这对于要找出什么阻止了另外一个应用绑定到指定端口实在是太棒了。



```css
1.  #  lsof  -i :22

3.  COMMAND  PID USER   FD   TYPE DEVICE SIZE NODE NAME
4.  sshd  7703 root 3u  IPv6  6499 TCP *:ssh  (LISTEN)
5.  sshd  7892 root 3u  IPv6  6757 TCP 10.10.1.5:ssh->192.168.1.5:49901  (ESTABLISHED)
```

#### 使用@host来显示指定到指定主机的连接

这对于你在检查是否开放连接到网络中或互联网上某个指定主机的连接时十分有用。



```css
1.  #  lsof  -i@172.16.12.5

3.  sshd  7892 root 3u  IPv6  6757 TCP 10.10.1.5:ssh->172.16.12.5:49901  (ESTABLISHED)
```

#### 使用@host:port显示基于主机与端口的连接

你也可以组合主机与端口的显示信息。



```ruby
1.  #  lsof  -i@172.16.12.5:22

3.  sshd  7892 root 3u  IPv6  6757 TCP 10.10.1.5:ssh->172.16.12.5:49901  (ESTABLISHED)
```

#### 找出监听端口

找出正等候连接的端口。



```bash
1.  #  lsof  -i -sTCP:LISTEN
```

你也可以grep “LISTEN”来完成该任务。



```ruby
1.  #  lsof  -i |  grep  -i LISTEN

3.  iTunes 400 daniel 16u  IPv4  0x4575228  0t0 TCP *:daap (LISTEN)
```

#### 找出已建立的连接

你也可以显示任何已经连接的连接。



```bash
1.  #  lsof  -i -sTCP:ESTABLISHED
```

你也可以通过grep搜索“ESTABLISHED”来完成该任务。



```ruby
1.  #  lsof  -i |  grep  -i ESTABLISHED

3.  firefox-b 169 daniel 49u  IPv4  0t0 TCP 1.2.3.3:1863->1.2.3.4:http (ESTABLISHED)
```

### 用户信息

你也可以获取各种用户的信息，以及它们在系统上正干着的事情，包括它们的网络活动、对文件的操作等。

#### 使用-u显示指定用户打开了什么



```bash
1.  #  lsof  -u daniel

3.  -- snipped --
4.  Dock  155 daniel  txt REG 14,2  2798436  823208  /usr/lib/libicucore.A.dylib
5.  Dock  155 daniel  txt REG 14,2  1580212  823126  /usr/lib/libobjc.A.dylib
6.  Dock  155 daniel  txt REG 14,2  2934184  823498  /usr/lib/libstdc++.6.0.4.dylib
7.  Dock  155 daniel  txt REG 14,2  132008  823505  /usr/lib/libgcc_s.1.dylib
8.  Dock  155 daniel  txt REG 14,2  212160  823214  /usr/lib/libauto.dylib
9.  -- snipped --
```

#### 使用-u user来显示除指定用户以外的其它所有用户所做的事情



```bash
1.  #  lsof  -u ^daniel

3.  -- snipped --
4.  Dock  155 jim  txt REG 14,2  2798436  823208  /usr/lib/libicucore.A.dylib
5.  Dock  155 jim  txt REG 14,2  1580212  823126  /usr/lib/libobjc.A.dylib
6.  Dock  155 jim  txt REG 14,2  2934184  823498  /usr/lib/libstdc++.6.0.4.dylib
7.  Dock  155 jim  txt REG 14,2  132008  823505  /usr/lib/libgcc_s.1.dylib
8.  Dock  155 jim  txt REG 14,2  212160  823214  /usr/lib/libauto.dylib
9.  -- snipped --
```

#### 杀死指定用户所做的一切事情

可以消灭指定用户运行的所有东西，这真不错。



```bash
1.  #  kill  -9  `lsof -t -u daniel`
```

### 命令和进程

可以查看指定程序或进程由什么启动，这通常会很有用，而你可以使用lsof通过名称或进程ID过滤来完成这个任务。下面列出了一些选项：

#### 使用-c查看指定的命令正在使用的文件和网络连接



```bash
1.  #  lsof  -c syslog-ng

3.  COMMAND    PID USER   FD   TYPE     DEVICE    SIZE       NODE NAME
4.  syslog-ng 7547 root  cwd    DIR 3,3  4096  2  /
5.  syslog-ng 7547 root  rtd    DIR 3,3  4096  2  /
6.  syslog-ng 7547 root  txt    REG 3,3  113524  1064970  /usr/sbin/syslog-ng
7.  -- snipped --
```

#### 使用-p查看指定进程ID已打开的内容



```bash
1.  #  lsof  -p 10075

3.  -- snipped --
4.  sshd  10068 root  mem    REG 3,3  34808  850407  /lib/libnss_files-2.4.so
5.  sshd  10068 root  mem    REG 3,3  34924  850409  /lib/libnss_nis-2.4.so
6.  sshd  10068 root  mem    REG 3,3  26596  850405  /lib/libnss_compat-2.4.so
7.  sshd  10068 root  mem    REG 3,3  200152  509940  /usr/lib/libssl.so.0.9.7
8.  sshd  10068 root  mem    REG 3,3  46216  510014  /usr/lib/liblber-2.3
9.  sshd  10068 root  mem    REG 3,3  59868  850413  /lib/libresolv-2.4.so
10.  sshd  10068 root  mem    REG 3,3  1197180  850396  /lib/libc-2.4.so
11.  sshd  10068 root  mem    REG 3,3  22168  850398  /lib/libcrypt-2.4.so
12.  sshd  10068 root  mem    REG 3,3  72784  850404  /lib/libnsl-2.4.so
13.  sshd  10068 root  mem    REG 3,3  70632  850417  /lib/libz.so.1.2.3
14.  sshd  10068 root  mem    REG 3,3  9992  850416  /lib/libutil-2.4.so
15.  -- snipped --
```

#### -t选项只返回PID



```bash
1.  #  lsof  -t -c Mail

3.  350
```

### 文件和目录

通过查看指定文件或目录，你可以看到系统上所有正与其交互的资源——包括用户、进程等。

#### 显示与指定目录交互的所有一切



```bash
1.  #  lsof  /var/log/messages/

3.  COMMAND    PID USER   FD   TYPE DEVICE   SIZE   NODE NAME
4.  syslog-ng 7547 root 4w REG 3,3  217309  834024  /var/log/messages
```

### 显示与指定文件交互的所有一切



```bash
1.  #  lsof  /home/daniel/firewall_whitelist.txt
```

### 高级用法

与[tcpdump](http://danielmiessler.com/study/tcpdump/)类似，当你开始组合查询时，它就显示了它强大的功能。

#### 显示daniel连接到1.1.1.1所做的一切



```css
1.  #  lsof  -u daniel -i @1.1.1.1

3.  bkdr 1893 daniel 3u  IPv6  3456 TCP 10.10.1.10:1234->1.1.1.1:31337  (ESTABLISHED)
```

#### 同时使用-t和-c选项以给进程发送 HUP 信号



```bash
1.  #  kill  -HUP `lsof -t -c sshd`
```

#### lsof +L1显示所有打开的链接数小于1的文件

这通常（当不总是）表示某个攻击者正尝试通过删除文件入口来隐藏文件内容。



```bash
1.  #  lsof  +L1

3.  (hopefully nothing)
```

#### 显示某个端口范围的打开的连接



```ruby
1.  #  lsof  -i @fw.google.com:2150=2180
```

### 结尾

本入门教程只是管窥了lsof功能的一斑，要查看完整参考，运行man lsof命令或查看[在线版本](http://www.netadmintools.com/html/lsof.man.html)。希望本文对你有所助益，也随时[欢迎你的评论和指正](http://danielmiessler.com/connect/)。


