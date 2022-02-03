# 服务器抓包命令：tcpdump详解
https://www.cnblogs.com/ranxf/p/7941184.html

官网地址：http://www.tcpdump.org/tcpdump_man.html

[toc]

### 简介：

tcpdump，就是：dump the traffic on a network，根据使用者的定义对网络上的数据包进行截获的包分析工具。

一个No-GUI的抓包分析工具。

tcpdump，可以将网络中传送的数据包的“头”完全截获下来提供分析。

它支持针对网络层、协议、主机、网络或端口的过滤，并提供and、or、not等逻辑语句来帮助你去掉无用的信息。

Linux已经自动安装，可直接使用。

### 概要：

tcpdump采用命令行方式，它的命令格式为：

```
tcpdump [ -AbdDefhHIJKlLnNOpqStuUvxX# ] [ -B buffer_size ]

         [ -c count ] [ -C file_size ]
         [ -E spi@ipaddr algo:secret,... ]
         [ -F file ] [ -G rotate_seconds ] [ -i interface ]
         [ --immediate-mode ] [ -j tstamp_type ] [ -m module ]
         [ -M secret ] [ --number ] [ --print ] [ -Q in|out|inout ]
         [ -r file ] [ -s snaplen ] [ -T type ] [ --version ]
         [ -V file ] [ -w file ] [ -W filecount ] [ -y datalinktype ]
         [ -z postrotate-command ] [ -Z user ]
         [ --time-stamp-precision=tstamp_precision ]
         [ expression ] 
```

```
-A  以ASCII码方式显示每一个数据包(不会显示数据包中链路层头部信息). 在抓取包含网页数据的数据包时, 可方便查看数据(nt: 即Handy for capturing web pages).

-b  使用ASDOT表示法在BGP数据包中打印AS号，而不是ASPLAIN表示法
  -B buffer_size
  --buffer-size=buffer_size
将操作系统捕获缓冲区大小设置为buffer_size，单位为KiB（1024字节）

-c  count
    tcpdump将在接受到count个数据包后退出.

-C  file-size (nt: 此选项用于配合-w file 选项使用)
    该选项使得tcpdump 在把原始数据包直接保存到文件中之前, 检查此文件大小是否超过file-size. 如果超过了, 将关闭此文件,另创一个文件继续用于原始数据包的记录. 新创建的文件名与-w 选项指定的文件名一致, 但文件名后多了一个数字.该数字会从1开始随着新创建文件的增多而增加. file-size的单位是百万字节(nt: 这里指1,000,000个字节,并非1,048,576个字节, 后者是以1024字节为1k, 1024k字节为1M计算所得, 即1M=1024 ＊ 1024 ＝ 1,048,576)

-d  以容易阅读的形式,在标准输出上打印出编排过的包匹配码, 随后tcpdump停止.(nt | rt: human readable, 容易阅读的,通常是指以ascii码来打印一些信息. compiled, 编排过的. packet-matching code, 包匹配码,含义未知, 需补充)

-dd 以C语言的形式打印出包匹配码.

-ddd 以十进制数的形式打印出包匹配码(会在包匹配码之前有一个附加的'count'前缀).

-D  打印系统中所有tcpdump可以在其上进行抓包的网络接口. 每一个接口会打印出数字编号, 相应的接口名字, 以及可能的一个网络接口描述. 其中网络接口名字和数字编号可以用在tcpdump 的-i flag 选项(nt: 把名字或数字代替flag), 来指定要在其上抓包的网络接口.
    此选项在不支持接口列表命令的系统上很有用(nt: 比如, Windows 系统, 或缺乏 ifconfig -a 的UNIX系统); 接口的数字编号在windows 2000 或其后的系统中很有用, 因为这些系统上的接口名字比较复杂, 而不易使用.
    如果tcpdump编译时所依赖的libpcap库太老,-D 选项不会被支持, 因为其中缺乏 pcap_findalldevs()函数.

-e  每行的打印输出中将包括数据包的数据链路层头部信息

-E  spi@ipaddr algo:secret,...
    可通过spi@ipaddr algo:secret 来解密IPsec ESP包(nt | rt:IPsec Encapsulating Security Payload,IPsec 封装安全负载, IPsec可理解为, 一整套对ip数据包的加密协议, ESP 为整个IP 数据包或其中上层协议部分被加密后的数据,前者的工作模式称为隧道模式; 后者的工作模式称为传输模式 . 工作原理, 另需补充).
    需要注意的是, 在终端启动tcpdump 时, 可以为IPv4 ESP packets 设置密钥(secret）.
    可用于加密的算法包括des-cbc, 3des-cbc, blowfish-cbc, rc3-cbc, cast128-cbc, 或者没有(none).默认的是des-cbc(nt: des, Data Encryption Standard, 数据加密标准, 加密算法未知, 另需补充).secret 为用于ESP 的密钥, 使用ASCII 字符串方式表达. 如果以 0x 开头, 该密钥将以16进制方式读入.
    该选项中ESP 的定义遵循RFC2406, 而不是 RFC1827. 并且, 此选项只是用来调试的, 不推荐以真实密钥(secret)来使用该选项, 因为这样不安全: 在命令行中输入的secret 可以被其他人通过ps 等命令查看到.
    除了以上的语法格式(nt: 指spi@ipaddr algo:secret), 还可以在后面添加一个语法输入文件名字供tcpdump 使用(nt：即把spi@ipaddr algo:secret,... 中...换成一个语法文件名). 此文件在接受到第一个ESP　包时会打开此文件, 所以最好此时把赋予tcpdump 的一些特权取消(nt: 可理解为, 这样防范之后, 当该文件为恶意编写时,不至于造成过大损害).

-f  显示外部的IPv4 地址时(nt: foreign IPv4 addresses, 可理解为, 非本机ip地址), 采用数字方式而不是名字.(此选项是用来对付Sun公司的NIS服务器的缺陷(nt: NIS, 网络信息服务, tcpdump 显示外部地址的名字时会用到她提供的名称服务): 此NIS服务器在查询非本地地址名字时,常常会陷入无尽的查询循环).
    由于对外部(foreign)IPv4地址的测试需要用到本地网络接口(nt: tcpdump 抓包时用到的接口)及其IPv4 地址和网络掩码. 如果此地址或网络掩码不可用, 或者此接口根本就没有设置相应网络地址和网络掩码(nt: linux 下的 'any' 网络接口就不需要设置地址和掩码, 不过此'any'接口可以收到系统中所有接口的数据包), 该选项不能正常工作.

-F  file    使用file 文件作为过滤条件表达式的输入, 此时命令行上的输入将被忽略.

-i  interface
    指定tcpdump 需要监听的接口.  如果没有指定, tcpdump 会从系统接口列表中搜寻编号最小的已配置好的接口(不包括 loopback 接口).一但找到第一个符合条件的接口, 搜寻马上结束.
    在采用2.2版本或之后版本内核的Linux 操作系统上, 'any' 这个虚拟网络接口可被用来接收所有网络接口上的数据包(nt: 这会包括目的是该网络接口的, 也包括目的不是该网络接口的). 需要注意的是如果真实网络接口不能工作在'混杂'模式(promiscuous)下,则无法在'any'这个虚拟的网络接口上抓取其数据包.
    如果 -D 标志被指定, tcpdump会打印系统中的接口编号，而该编号就可用于此处的interface 参数.

-l  对标准输出进行行缓冲(nt: 使标准输出设备遇到一个换行符就马上把这行的内容打印出来).在需要同时观察抓包打印以及保存抓包记录的时候很有用. 比如, 可通过以下命令组合来达到此目的:
    ``tcpdump  -l  |  tee dat'' 或者 ``tcpdump  -l   > dat  &  tail  -f  dat''.(nt: 前者使用tee来把tcpdump 的输出同时放到文件dat和标准输出中, 而后者通过重定向操作'>', 把tcpdump的输出放到dat 文件中, 同时通过tail把dat文件中的内容放到标准输出中)

-L  列出指定网络接口所支持的数据链路层的类型后退出.(nt: 指定接口通过-i 来指定)

-m  module
    通过module 指定的file 装载SMI MIB 模块(nt: SMI，Structure of Management Information, 管理信息结构MIB, Management Information Base, 管理信息库. 可理解为, 这两者用于SNMP(Simple Network Management Protoco)协议数据包的抓取. 具体SNMP 的工作原理未知, 另需补充).
    此选项可多次使用, 从而为tcpdump 装载不同的MIB 模块.

-M  secret  如果TCP 数据包(TCP segments)有TCP-MD5选项(在RFC 2385有相关描述), 则为其摘要的验证指定一个公共的密钥secret.

-n  不对地址(比如, 主机地址, 端口号)进行数字表示到名字表示的转换.

-N  不打印出host 的域名部分. 比如, 如果设置了此选现, tcpdump 将会打印'nic' 而不是 'nic.ddn.mil'.

-O  不启用进行包匹配时所用的优化代码. 当怀疑某些bug是由优化代码引起的, 此选项将很有用.

-p  一般情况下, 把网络接口设置为非'混杂'模式. 但必须注意 , 在特殊情况下此网络接口还是会以'混杂'模式来工作； 从而, '-p' 的设与不设, 不能当做以下选现的代名词:'ether host {local-hw-add}' 或  'ether broadcast'(nt: 前者表示只匹配以太网地址为host 的包, 后者表示匹配以太网地址为广播地址的数据包).

-q  快速(也许用'安静'更好?)打印输出. 即打印很少的协议相关信息, 从而输出行都比较简短.

-R  设定tcpdump 对 ESP/AH 数据包的解析按照 RFC1825而不是RFC1829(nt: AH, 认证头, ESP， 安全负载封装, 这两者会用在IP包的安全传输机制中). 如果此选项被设置, tcpdump 将不会打印出'禁止中继'域(nt: relay prevention field). 另外,由于ESP/AH规范中没有规定ESP/AH数据包必须拥有协议版本号域,所以tcpdump不能从收到的ESP/AH数据包中推导出协议版本号.

-r  file
    从文件file 中读取包数据. 如果file 字段为 '-' 符号, 则tcpdump 会从标准输入中读取包数据.

-S  打印TCP 数据包的顺序号时, 使用绝对的顺序号, 而不是相对的顺序号.(nt: 相对顺序号可理解为, 相对第一个TCP 包顺序号的差距,比如, 接受方收到第一个数据包的绝对顺序号为232323, 对于后来接收到的第2个,第3个数据包, tcpdump会打印其序列号为1, 2分别表示与第一个数据包的差距为1 和 2. 而如果此时-S 选项被设置, 对于后来接收到的第2个, 第3个数据包会打印出其绝对顺序号:232324, 232325).

-s  snaplen
    设置tcpdump的数据包抓取长度为snaplen, 如果不设置默认将会是68字节(而支持网络接口分接头(nt: NIT, 上文已有描述,可搜索'网络接口分接头'关键字找到那里)的SunOS系列操作系统中默认的也是最小值是96).68字节对于IP, ICMP(nt: Internet Control Message Protocol,因特网控制报文协议), TCP 以及 UDP 协议的报文已足够, 但对于名称服务(nt: 可理解为dns, nis等服务), NFS服务相关的数据包会产生包截短. 如果产生包截短这种情况, tcpdump的相应打印输出行中会出现''[|proto]''的标志（proto 实际会显示为被截短的数据包的相关协议层次). 需要注意的是, 采用长的抓取长度(nt: snaplen比较大), 会增加包的处理时间, 并且会减少tcpdump 可缓存的数据包的数量， 从而会导致数据包的丢失. 所以, 在能抓取我们想要的包的前提下, 抓取长度越小越好.把snaplen 设置为0 意味着让tcpdump自动选择合适的长度来抓取数据包.

-T  type
    强制tcpdump按type指定的协议所描述的包结构来分析收到的数据包.  目前已知的type 可取的协议为:
    aodv (Ad-hoc On-demand Distance Vector protocol, 按需距离向量路由协议, 在Ad hoc(点对点模式)网络中使用),
    cnfp (Cisco  NetFlow  protocol),  rpc(Remote Procedure Call), rtp (Real-Time Applications protocol),
    rtcp (Real-Time Applications con-trol protocol), snmp (Simple Network Management Protocol),
    tftp (Trivial File Transfer Protocol, 碎文件协议), vat (Visual Audio Tool, 可用于在internet 上进行电
    视电话会议的应用层协议), 以及wb (distributed White Board, 可用于网络会议的应用层协议).

-t     在每行输出中不打印时间戳

-tt    不对每行输出的时间进行格式处理(nt: 这种格式一眼可能看不出其含义, 如时间戳打印成1261798315)

-ttt   tcpdump 输出时, 每两行打印之间会延迟一个段时间(以毫秒为单位)

-tttt  在每行打印的时间戳之前添加日期的打印

-u     打印出未加密的NFS 句柄(nt: handle可理解为NFS 中使用的文件句柄, 这将包括文件夹和文件夹中的文件)

-U    使得当tcpdump在使用-w 选项时, 其文件写入与包的保存同步.(nt: 即, 当每个数据包被保存时, 它将及时被写入文件中,而不是等文件的输出缓冲已满时才真正写入此文件)
      -U 标志在老版本的libcap库(nt: tcpdump 所依赖的报文捕获库)上不起作用, 因为其中缺乏pcap_cump_flush()函数.

-v    当分析和打印的时候, 产生详细的输出. 比如, 包的生存时间, 标识, 总长度以及IP包的一些选项. 这也会打开一些附加的包完整性检测, 比如对IP或ICMP包头部的校验和.

-vv   产生比-v更详细的输出. 比如, NFS回应包中的附加域将会被打印, SMB数据包也会被完全解码.

-vvv  产生比-vv更详细的输出. 比如, telent 时所使用的SB, SE 选项将会被打印, 如果telnet同时使用的是图形界面,
      其相应的图形选项将会以16进制的方式打印出来(nt: telnet 的SB,SE选项含义未知, 另需补充).

-w    把包数据直接写入文件而不进行分析和打印输出. 这些包数据可在随后通过-r 选项来重新读入并进行分析和打印.

-W    filecount
      此选项与-C 选项配合使用, 这将限制可打开的文件数目, 并且当文件数据超过这里设置的限制时, 依次循环替代之前的文件, 这相当于一个拥有filecount 个文件的文件缓冲池. 同时, 该选项会使得每个文件名的开头会出现足够多并用来占位的0, 这可以方便这些文件被正确的排序.

-x    当分析和打印时, tcpdump 会打印每个包的头部数据, 同时会以16进制打印出每个包的数据(但不包括连接层的头部).总共打印的数据大小不会超过整个数据包的大小与snaplen 中的最小值. 必须要注意的是, 如果高层协议数据没有snaplen 这么长,并且数据链路层(比如, Ethernet层)有填充数据, 则这些填充数据也会被打印.(nt: so for link  layers  that pad, 未能衔接理解和翻译, 需补充 )

-xx   tcpdump 会打印每个包的头部数据, 同时会以16进制打印出每个包的数据, 其中包括数据链路层的头部.

-X    当分析和打印时, tcpdump 会打印每个包的头部数据, 同时会以16进制和ASCII码形式打印出每个包的数据(但不包括连接层的头部).这对于分析一些新协议的数据包很方便.

-XX   当分析和打印时, tcpdump 会打印每个包的头部数据, 同时会以16进制和ASCII码形式打印出每个包的数据, 其中包括数据链路层的头部.这对于分析一些新协议的数据包很方便.

-y    datalinktype
      设置tcpdump 只捕获数据链路层协议类型是datalinktype的数据包

-Z    user
      使tcpdump 放弃自己的超级权限(如果以root用户启动tcpdump, tcpdump将会有超级用户权限), 并把当前tcpdump的用户ID设置为user, 组ID设置为user首要所属组的ID(nt: tcpdump 此处可理解为tcpdump 运行之后对应的进程)
      此选项也可在编译的时候被设置为默认打开.(nt: 此时user 的取值未知, 需补充)
```

### 命令实例：

#### 1. 默认启动tcpdump

直接启动tcpdump将监视第一个网络接口上所有流过的数据包。

```
tcpdump
```

执行后的部分结果：

```
10:50:48.432101 ARP, Request who-has 192.168.2.147 tell 192.168.2.1, length 46
10:50:48.550082 IP6 fe80::9cd6:4a5e:4746:2d9a.62613 > ff02::1:3.hostmon: UDP, length 24
10:50:53.628962 IP 192.168.2.170.13109 > google-public-dns-a.google.com.domain: 2173+ PTR? 3.0.0.0.1.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.2.0.f.f.ip6.arpa. (90)
10:50:53.759788 IP 192.168.2.106.59326 > 239.255.255.250.1900: UDP, length 129
10:50:53.786505 IP google-public-dns-a.google.com.domain > 192.168.2.170.13109: 2173 NXDomain 0/1/0 (154)
10:50:53.786675 IP 192.168.2.170.21608 > google-public-dns-a.google.com.domain: 41478+ PTR? a.9.d.2.6.4.7.4.e.5.a.4.6.d.c.9.0.0.0.0.0.0.0.0.0.0.0.0.0.8.e.f.ip6.arpa. (90)
10:50:53.860584 IP 192.168.2.106.59326 > 239.255.255.250.1900: UDP, length 129
10:50:53.898146 IP 192.168.2.112.20141 > 234.123.12.1.20141: UDP, length 224
10:50:58.937174 ARP, Request who-has 192.168.2.6 tell 192.168.2.221, length 46
10:50:58.937326 IP 192.168.2.170.55192 > google-public-dns-a.google.com.domain: 23886+ PTR? 221.2.168.192.in-addr.arpa. (44)
10:50:59.507074 IP 192.168.2.142.netbios-ns > 192.168.2.255.netbios-ns: NBT UDP PACKET(137): QUERY; REQUEST; BROADCAST
10:50:59.591335 ARP, Request who-has 192.168.2.147 tell 192.168.2.1, length 46
^C10:50:59.734162 IP 10.1.2.185.58549 > 239.255.255.250.1900: UDP, length 273
```

#### 2. 监视指定网络接口的数据包

```
tcpdump -i p4p1
```

执行后的部分结果：

```
10:54:09.027883 IP 10.1.1.17.afs3-prserver > 10.1.1.166.33150: Flags [.], ack 1130671, win 32768, options [nop,nop,TS val 121585530 ecr 238212756], length 0
10:54:09.027905 IP 10.1.1.166.33150 > 10.1.1.17.afs3-prserver: Flags [P.], seq 1132119:1133477, ack 1, win 229, options [nop,nop,TS val 238212756 ecr 121585530], length 1358
10:54:09.027956 IP 10.1.1.166.59544 > 10.1.1.177.22084: Flags [.], seq 1133477:1134925, ack 1, win 229, options [nop,nop,TS val 238212756 ecr 16389299], length 1448
10:54:09.028036 IP 10.1.1.166.33150 > 10.1.1.17.afs3-prserver: Flags [.], seq 1133477:1134925, ack 1, win 229, options [nop,nop,TS val 238212756 ecr 121585530], length 1448
10:54:09.028084 IP 10.1.1.17.afs3-prserver > 10.1.1.166.33150: Flags [.], ack 1133477, win 32768, options [nop,nop,TS val 121585530 ecr 238212756], length 0
10:54:09.028110 IP 10.1.1.166.33150 > 10.1.1.17.afs3-prserver: Flags [P.], seq 1134925:1135001, ack 1, win 229, options [nop,nop,TS val 238212756 ecr 121585530], length 76
10:54:09.028160 IP 10.1.1.177.22084 > 10.1.1.166.59544: Flags [.], ack 1134925, win 32768, options [nop,nop,TS val 16389299 ecr 238212756], length 0
10:54:09.028182 IP 10.1.1.166.59544 > 10.1.1.177.22084: Flags [P.], seq 1134925:1135001, ack 1, win 229, options [nop,nop,TS val 238212756 ecr 16389299], length 76
10:54:09.028317 IP 10.1.1.17.afs3-prserver > 10.1.1.166.33150: Flags [.], ack 1135001, win 32768, options [nop,nop,TS val 121585530 ecr 238212756], length 0
10:54:09.067375 IP 10.1.1.94.20000 > 10.1.1.166.42788: UDP, length 1412
10:54:09.067545 IP 10.1.1.166.33150 > 10.1.1.17.afs3-prserver: Flags [P.], seq 1135001:1136419, ack 1, win 229, options [nop,nop,TS val 238212766 ecr 121585530], length 1418
10:54:09.068198 IP 10.1.1.94.20000 > 10.1.1.166.42788: UDP, length 1412
10:54:09.068289 IP 10.1.1.94.20000 > 10.1.1.166.42788: UDP, length 1412
10:54:09.068362 IP 10.1.1.166.59544 > 10.1.1.177.22084: Flags [.], seq 1135001:1136449, ack 1, win 229, options [nop,nop,TS val 238212766 ecr 16389299], length 1448
10:54:09.068392 IP 10.1.1.166.59544 > 10.1.1.177.22084: Flags [.], seq 1136449:1137897, ack 1, win 229, options [nop,nop,TS val 238212766 ecr 16389299], length 1448
10:54:09.068403 IP 10.1.1.166.33150 > 10.1.1.17.afs3-prserver: Flags [.], seq 1136419:1137867, ack 1, win 229, options [nop,nop,TS val 238212766 ecr 121585530], length 1448
10:54:09.068501 IP 10.1.1.94.20000 > 10.1.1.166.42788: UDP, length 1412
10:54:09.068584 IP 10.1.1.177.22084 > 10.1.1.166.59544: Flags [.], ack 1137897, win 32768, options [nop,nop,TS val 16389303 ecr 238212756], length 0
10:54:09.068608 IP 10.1.1.166.59544 > 10.1.1.177.22084: Flags [P.], seq 1137897:1139255, ack 1, win 229, options [nop,nop,TS val 238212766 ecr 16389303], length 1358
10:54:09.068678 IP 10.1.1.166.33150 > 10.1.1.17.afs3-prserver: Flags [.], seq 1137867:1139315, ack 1, win 229, options [nop,nop,TS val 238212766 ecr 121585530], length 1448
10:54:09.068684 IP 10.1.1.94.20000 > 10.1.1.166.42788: UDP, length 1412
10:54:09.068702 IP 10.1.1.94.20000 > 10.1.1.166.42788: UDP, length 1412
10:54:09.068706 IP 10.1.1.94.20000 > 10.1.1.166.42788: UDP, length 29
10:54:09.068848 IP 10.1.1.17.afs3-prserver > 10.1.1.166.33150: Flags [.], ack 1137867, win 32768, options [nop,nop,TS val 121585534 ecr 238212766], length 0
10:54:09.068872 IP 10.1.1.166.33150 > 10.1.1.17.afs3-prserver: Flags [P.], seq 1139315:1140673, ack 1, win 229, options [nop,nop,TS val 238212767 ecr 121585534], length 1358
10:54:09.068928 IP 10.1.1.166.59544 > 10.1.1.177.22084: Flags [.], seq 1139255:1140703, ack 1, win 229, options [nop,nop,TS val 238212767 ecr 16389303], length 1448
```

#### 3. 监视指定主机的数据包

**打印所有进入或离开“主机名”的数据包.或者是IP，即截获所有10.1.1.94 的主机收到的和发出的所有的数据包**

```
tcpdump host 10.1.1.94
```

**打印设备与服务器之间通信的数据包：**

```
打印helios 与 hot 或者与 ace 之间通信的数据包
tcpdump host helios and \( hot or ace \)
tcpdump host 10.1.1.94 and 10.1.1.166 
```

**执行后的部分结果：**

```
11:17:35.964589 IP 10.1.1.94.20000 > 10.1.1.166.48684: UDP, length 909
11:17:35.973403 IP 10.1.1.94.20000 > 10.1.1.166.42788: UDP, length 1412
11:17:35.973515 IP 10.1.1.94.20000 > 10.1.1.166.42788: UDP, length 1412
11:17:35.973525 IP 10.1.1.94.20000 > 10.1.1.166.42788: UDP, length 265
11:17:36.004030 IP 10.1.1.94.20000 > 10.1.1.166.48684: UDP, length 1412
```

 **截获主机10.1.1.64 和主机10.1.1.166 或10.1.1.61的通信**

```
tcpdump host 10.1.1.94 and \(10.1.1.166 or 10.1.1.61\)
```

**打印ace与任何其他主机之间通信的IP 数据包, 但不包括与helios之间的数据包.**

```
tcpdump ip host ace and not helios
```

**如果想要获取主机10.1.1.94除了和主机10.1.1.166之外所有主机通信的ip包，使用命令：**

```
tcpdump ip host 10.1.1.94 and ! 10.1.1.166
```

**截获主机hostname发送的所有数据**

```
tcpdump -i p4p1 src host 10.1.1.166
```

**监视所有送到主机hostname的数据包：**

```
tcpdump -i p4p1 dst host 10.1.1.166
```

执行后的部分结果：

```
12:09:08.486526 IP 10.1.1.94.20000 > 10.1.1.166.42788: UDP, length 1412
12:09:08.486698 IP 10.1.1.94.20000 > 10.1.1.166.42788: UDP, length 1412
12:09:08.486701 IP 10.1.1.177.61674 > 10.1.1.166.41174: Flags [.], ack 3105219, win 32768, options [nop,nop,TS val 16839247 ecr 239337621], length 0
12:09:08.486763 IP 10.1.1.94.20000 > 10.1.1.166.42788: UDP, length 1412
12:09:08.486767 IP 10.1.1.17.afs3-prserver > 10.1.1.166.33150: Flags [.], ack 3103801, win 32768, options [nop,nop,TS val 122035470 ecr 239337621], length 0
12:09:08.486948 IP 10.1.1.94.20000 > 10.1.1.166.42788: UDP, length 1412
12:09:08.486951 IP 10.1.1.17.afs3-prserver > 10.1.1.166.33150: Flags [.], ack 3108055, win 32768, options [nop,nop,TS val 122035470 ecr 239337621], length 0
12:09:08.487036 IP 10.1.1.177.61674 > 10.1.1.166.41174: Flags [.], ack 3108085, win 32768, options [nop,nop,TS val 16839247 ecr 239337621], length 0
12:09:08.487131 IP 10.1.1.94.20000 > 10.1.1.166.42788: UDP, length 1412
12:09:08.487135 IP 10.1.1.94.20000 > 10.1.1.166.42788: UDP, length 1412
```



#### 4. 监视指定主机和端口的数据包

如果想要获取主机10.1.1.94接收或发出的telnet包，使用如下命令

```
tcpdump tcp port 23 and host 10.1.1.94
```

对本机的udp 123 端口进行监视（123 为ntp的服务端口）

```
tcpdump udp port 123 
```

执行后部分结果：

```
13:27:13.315533 IP biisoni.miuku.net.ntp > 192.168.2.170.ntp: NTPv4, Server, length 48
13:27:14.050667 IP 192.168.2.170.ntp > ntp.wdc1.us.leaseweb.net.ntp: NTPv4, Client, length 48
13:27:14.050676 IP 192.168.2.170.ntp > 85.199.214.100.ntp: NTPv4, Client, length 48
13:27:14.265706 IP 85.199.214.100.ntp > 192.168.2.170.ntp: NTPv4, Server, length 48
13:27:14.468254 IP ntp.wdc1.us.leaseweb.net.ntp > 192.168.2.170.ntp: NTPv4, Server, length 48
13:27:15.050665 IP 192.168.2.170.ntp > darwin.kenyonralph.com.ntp: NTPv4, Client, length 48
13:27:15.270369 IP darwin.kenyonralph.com.ntp > 192.168.2.170.ntp: NTPv4, Server, length 48
13:27:16.050669 IP 192.168.2.170.ntp > time6.aliyun.com.ntp: NTPv4, Client, length 48
13:27:16.102683 IP time6.aliyun.com.ntp > 192.168.2.170.ntp: NTPv4, Server, length 48
13:27:19.050666 IP 192.168.2.170.ntp > 120.25.115.19.ntp: NTPv4, Client, length 48
13:27:19.088117 IP 120.25.115.19.ntp > 192.168.2.170.ntp: NTPv4, Server, length 48
13:27:21.050707 IP 192.168.2.170.ntp > cpt-ntp.mweb.co.za.ntp: NTPv4, Client, length 48
13:27:21.511001 IP cpt-ntp.mweb.co.za.ntp > 192.168.2.170.ntp: NTPv4, Server, length 48
```



#### 5. 监视指定网络的数据包

打印本地主机和伯克利网络上的主机之间的所有流量（所有通信数据包）：

```
tcpdump net ucb-ether
```

打印所有通过网关snup（网关名字）的ftp数据包(注意, 表达式被单引号括起来了, 这可以防止shell对其中的括号进行错误解析)

```
tcpdump 'gateway snup and (port ftp or ftp-data)'
```

打印所有源地址或目标地址是本地主机的IP数据包（要打印既不是来自本地主机也不是本地主机的流量）

(如果本地网络通过网关连到了另一网络, 则另一网络并不能算作本地网络.(nt: 此句翻译曲折,需补充).localnet 实际使用时要真正替换成本地网络的名字)

```
tcpdump ip and not net localnet
```

#### 6. 监视指定协议的数据包

打印TCP会话中的的开始和结束数据包, 并且数据包的源或目的不是本地网络上的主机.(nt: localnet, 实际使用时要真正替换成本地网络的名字))

或者翻译为：打印涉及非本地主机的每个TCP会话的开始和结束数据包（SYN和FIN数据包）

```
tcpdump 'tcp[tcpflags] & (tcp-syn|tcp-fin) != 0 and not src and dst net localnet'
```

执行时部分结果：

```
root@ranxf:/opt/navicat120_premium_en_x64# tcpdump 'tcp[tcpflags] & (tcp-syn|tcp-fin) != 0 and not src and dst net 192.168.2.170'
tcpdump: verbose output suppressed, use -v or -vv for full protocol decode
listening on ens3f0, link-type EN10MB (Ethernet), capture size 262144 bytes
14:54:53.562553 IP 192.168.2.170.44642 > tl-in-f139.1e100.net.https: Flags [S], seq 2036945403, win 29200, options [mss 1460,sackOK,TS val 2659717232 ecr 0,nop,wscale 7], length 0
14:54:53.562562 IP 192.168.2.170.44644 > tl-in-f139.1e100.net.https: Flags [S], seq 2344512723, win 29200, options [mss 1460,sackOK,TS val 2659717232 ecr 0,nop,wscale 7], length 0
14:55:09.690551 IP 192.168.2.170.44644 > tl-in-f139.1e100.net.https: Flags [S], seq 2344512723, win 29200, options [mss 1460,sackOK,TS val 2659721264 ecr 0,nop,wscale 7], length 0
14:55:09.690560 IP 192.168.2.170.44642 > tl-in-f139.1e100.net.https: Flags [S], seq 2036945403, win 29200, options [mss 1460,sackOK,TS val 2659721264 ecr 0,nop,wscale 7], length 0
```

打印所有来自端口80的HTTP HTTP数据包，即仅打印包含数据的数据包，而不打印例如SYN和FIN数据包以及ACK数据包。（IPv6是留给读者的一个练习。）

```
tcpdump 'tcp port 80 and (((ip[2:2] - ((ip[0]&0xf)<<2)) - ((tcp[12]&0xf0)>>2)) != 0)'
```

(nt: 可理解为, ip[2:2]表示整个ip数据包的长度, (ip[0]&0xf)<<2)表示ip数据包包头的长度(ip[0]&0xf代表包中的IHL域, 而此域的单位为32bit, 要换算成字节数需要乘以4,　即左移2.　(tcp[12]&0xf0)>>4 表示tcp头的长度, 此域的单位也是32bit,　换算成比特数为 ((tcp[12]&0xf0) >> 4)　<<　２,　
即 ((tcp[12]&0xf0)>>2).　((ip[2:2] - ((ip[0]&0xf)<<2)) - ((tcp[12]&0xf0)>>2)) != 0　表示: 整个ip数据包的长度减去ip头的长度,再减去
tcp头的长度不为0, 这就意味着, ip数据包中确实是有数据.对于ipv6版本只需考虑ipv6头中的'Payload Length' 与 'tcp头的长度'的差值, 并且其中表达方式'ip[]'需换成'ip6[]'.)

 

打印长度超过576字节, 并且网关地址是snup的IP数据包

```
tcpdump 'gateway snup and ip[2:2] > 576'
```

打印所有IP层广播或多播的数据包， 但不是物理以太网层的广播或多播数据报：

```
tcpdump 'ether[0] & 1 = 0 and ip[16] >= 224'
```

打印除'echo request'或者'echo reply'类型以外的ICMP数据包( 比如,需要打印所有非ping 程序产生的数据包时可用到此表达式 .
(nt: 'echo reuqest' 与 'echo reply' 这两种类型的ICMP数据包通常由ping程序产生))

```
tcpdump 'icmp[icmptype] != icmp-echo and icmp[icmptype] != icmp-echoreply'
```



参考：

https://www.cnblogs.com/ggjucheng/archive/2012/01/14/2322659.html

https://www.2cto.com/net/201605/509209.html