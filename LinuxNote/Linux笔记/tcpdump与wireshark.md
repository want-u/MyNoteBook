[toc]

#### 7. tcpdump与wireshark

**Tcpdump + Wireshark 的完美组合实现：在 Linux 里抓包，然后在wireshark里分析包**

```
root@ranxf:/home/ranxf# tcpdump tcp -i ens3f0 -t -s 0 -c 100 and dst port ! 22 and src net 192.168.2.0/24 -w ./target.cap
```

1. tcp：放于第一个参数的位置，用来过滤数据包的类型（另外有IP，icmp arp rarp 和 tcp、udp、icmp这些选项等都要放到第一个参数的位置）；
2. -i ens3f0 只抓经过接口eth1的包；
3. -t : 不显示时间戳；
4. -s 0 : 抓取数据包时默认抓取长度为68字节。加上-S 0 后可以抓到完整的数据包。
5. -c 100 : 只抓取100个数据包；
6. dst port ! 22 : 不抓取目标端口是22的数据包；
7. src net 192.168.2.0/24 : 数据包的源网络地址为192.168.2.0/24；
8. -w ./target.cap : 保存成cap文件，方便用ethereal(即wireshark)分析![img](https://images2017.cnblogs.com/blog/1114252/201712/1114252-20171201152628648-176430919.jpg)

#### 8. 使用tcpdump抓取HTTP包

```
tcpdump  -XvvennSs 0 -i p4p1 tcp[20:2]=0x4745 or tcp[20:2]=0x4854
```

 0x4745 为"GET"前两个字母"GE",0x4854 为"HTTP"前两个字母"HT"。

tcpdump 对截获的数据并没有进行彻底解码，数据包内的大部分内容是使用十六进制的形式直接打印输出的。显然这不利于分析网络故障，通常的解决办法是先使用带-w参数的tcpdump 截获数据并保存到文件中，然后再使用其他程序(如Wireshark)进行解码分析，当然也应该定义过滤规则。

#### 9. 输出信息格式

首先我们注意一下，基本上tcpdump总的的输出格式为：系统时间 来源主机.端口 > 目标主机.端口 数据包参数

tcpdump 的输出格式与协议有关.以下简要描述了大部分常用的格式及相关例子.

##### 1、链路层

```
对于FDDI网络, '-e' 使tcpdump打印出指定数据包的'frame control' 域, 源和目的地址, 以及包的长度.(frame control域
控制对包中其他域的解析). 一般的包(比如那些IP datagrams)都是带有'async'(异步标志)的数据包，并且有取值0到7的优先级;
比如 'async4'就代表此包为异步数据包，并且优先级别为4. 通常认为,这些包们会内含一个 LLC包(逻辑链路控制包); 这时,如果此包
不是一个ISO datagram或所谓的SNAP包，其LLC头部将会被打印(nt:应该是指此包内含的 LLC包的包头).

对于Token Ring网络(令牌环网络), '-e' 使tcpdump打印出指定数据包的'frame control'和'access control'域, 以及源和目的地址,
外加包的长度. 与FDDI网络类似, 此数据包通常内含LLC数据包. 不管 是否有'-e'选项.对于此网络上的'source-routed'类型数据包(nt:
意译为:源地址被追踪的数据包,具体含义未知,需补充), 其包的源路由信息总会被打印.


对于802.11网络(WLAN,即wireless local area network), '-e' 使tcpdump打印出指定数据包的'frame control域,
包头中包含的所有地址, 以及包的长度.与FDDI网络类似, 此数据包通常内含LLC数据包.

(注意: 以下的描述会假设你熟悉SLIP压缩算法 (nt:SLIP为Serial Line Internet Protocol.), 这个算法可以在
RFC-1144中找到相关的蛛丝马迹.)

对于SLIP网络(nt:SLIP links, 可理解为一个网络, 即通过串行线路建立的连接, 而一个简单的连接也可看成一个网络),
数据包的'direction indicator'('方向指示标志')("I"表示入, "O"表示出), 类型以及压缩信息将会被打印. 包类型会被首先打印.

类型分为ip, utcp以及ctcp(nt:未知, 需补充). 对于ip包,连接信息将不被打印(nt:SLIP连接上,ip包的连接信息可能无用或没有定义.
reconfirm).对于TCP数据包, 连接标识紧接着类型表示被打印. 如果此包被压缩, 其被编码过的头部将被打印.
此时对于特殊的压缩包,会如下显示:
*S+n 或者 *SA+n, 其中n代表包的(顺序号或(顺序号和应答号))增加或减少的数目(nt | rt:S,SA拗口, 需再译).
对于非特殊的压缩包,0个或更多的'改变'将会被打印.'改变'被打印时格式如下:
'标志'+/-/=n 包数据的长度 压缩的头部长度.
其中'标志'可以取以下值:
U(代表紧急指针), W(指缓冲窗口), A(应答), S(序列号), I(包ID),而增量表达'=n'表示被赋予新的值, +/-表示增加或减少.

比如, 以下显示了对一个外发压缩TCP数据包的打印, 这个数据包隐含一个连接标识(connection identifier); 应答号增加了6,
顺序号增加了49, 包ID号增加了6; 包数据长度为3字节(octect), 压缩头部为6字节.(nt:如此看来这应该不是一个特殊的压缩数据包).

ARP/RARP 数据包

tcpdump对Arp/rarp包的输出信息中会包含请求类型及该请求对应的参数. 显示格式简洁明了. 以下是从主机rtsg到主机csam的'rlogin'
(远程登录)过程开始阶段的数据包样例:
arp who-has csam tell rtsg
arp reply csam is-at CSAM
第一行表示:rtsg发送了一个arp数据包(nt:向全网段发送,arp数据包）以询问csam的以太网地址
Csam（nt:可从下文看出来, 是Csam）以她自己的以太网地址做了回应(在这个例子中, 以太网地址以大写的名字标识, 而internet
地址(即ip地址)以全部的小写名字标识).

如果使用tcpdump -n, 可以清晰看到以太网以及ip地址而不是名字标识:
arp who-has 128.3.254.6 tell 128.3.254.68
arp reply 128.3.254.6 is-at 02:07:01:00:01:c4

如果我们使用tcpdump -e, 则可以清晰的看到第一个数据包是全网广播的, 而第二个数据包是点对点的:
RTSG Broadcast 0806 64: arp who-has csam tell rtsg
CSAM RTSG 0806 64: arp reply csam is-at CSAM
第一个数据包表明:以arp包的源以太地址是RTSG, 目标地址是全以太网段, type域的值为16进制0806(表示ETHER_ARP(nt:arp包的类型标识)),
包的总长度为64字节.
```



##### 2、TCP 数据包

```
(注意:以下将会假定你对 RFC-793所描述的TCP熟悉. 如果不熟, 以下描述以及tcpdump程序可能对你帮助不大.(nt:警告可忽略,
只需继续看, 不熟悉的地方可回头再看.).


通常tcpdump对tcp数据包的显示格式如下:
src > dst: flags data-seqno ack window urgent options

src 和 dst 是源和目的IP地址以及相应的端口. flags 标志由S(SYN), F(FIN), P(PUSH, R(RST),
W(ECN CWT(nt | rep:未知, 需补充))或者 E(ECN-Echo(nt | rep:未知,　需补充))组成,
单独一个'.'表示没有flags标识. 数据段顺序号(Data-seqno)描述了此包中数据所对应序列号空间中的一个位置(nt:整个数据被分段,
每段有一个顺序号, 所有的顺序号构成一个序列号空间)(可参考以下例子). Ack 描述的是同一个连接,同一个方向,下一个本端应该接收的
(对方应该发送的)数据片段的顺序号. Window是本端可用的数据接收缓冲区的大小(也是对方发送数据时需根据这个大小来组织数据).
Urg(urgent) 表示数据包中有紧急的数据. options 描述了tcp的一些选项, 这些选项都用尖括号来表示(如 <mss 1024>).

src, dst 和 flags 这三个域总是会被显示. 其他域的显示与否依赖于tcp协议头里的信息.

这是一个从trsg到csam的一个rlogin应用登录的开始阶段.
rtsg.1023 > csam.login: S 768512:768512(0) win 4096 <mss 1024>
csam.login > rtsg.1023: S 947648:947648(0) ack 768513 win 4096 <mss 1024>
rtsg.1023 > csam.login: . ack 1 win 4096
rtsg.1023 > csam.login: P 1:2(1) ack 1 win 4096
csam.login > rtsg.1023: . ack 2 win 4096
rtsg.1023 > csam.login: P 2:21(19) ack 1 win 4096
csam.login > rtsg.1023: P 1:2(1) ack 21 win 4077
csam.login > rtsg.1023: P 2:3(1) ack 21 win 4077 urg 1
csam.login > rtsg.1023: P 3:4(1) ack 21 win 4077 urg 1
第一行表示有一个数据包从rtsg主机的tcp端口1023发送到了csam主机的tcp端口login上(nt:udp协议的端口和tcp协议的端
口是分别的两个空间, 虽然取值范围一致). S表示设置了SYN标志. 包的顺序号是768512, 并且没有包含数据.(表示格式
为:'first:last(nbytes)', 其含义是'此包中数据的顺序号从first开始直到last结束，不包括last. 并且总共包含nbytes的
用户数据'.) 没有捎带应答(nt:从下文来看，第二行才是有捎带应答的数据包), 可用的接受窗口的大小为4096bytes, 并且请求端(rtsg)
的最大可接受的数据段大小是1024字节(nt:这个信息作为请求发向应答端csam, 以便双方进一步的协商).

Csam 向rtsg 回复了基本相同的SYN数据包, 其区别只是多了一个' piggy-backed ack'(nt:捎带回的ack应答, 针对rtsg的SYN数据包).

rtsg 同样针对csam的SYN数据包回复了一ACK数据包作为应答. '.'的含义就是此包中没有标志被设置. 由于此应答包中不含有数据, 所以
包中也没有数据段序列号. 提醒! 此ACK数据包的顺序号只是一个小整数1. 有如下解释:tcpdump对于一个tcp连接上的会话, 只打印会话两端的
初始数据包的序列号,其后相应数据包只打印出与初始包序列号的差异.即初始序列号之后的序列号,　可被看作此会话上当前所传数据片段在整个
要传输的数据中的'相对字节'位置(nt:双方的第一个位置都是1, 即'相对字节'的开始编号).　'-Ｓ'将覆盖这个功能,　
使数据包的原始顺序号被打印出来.

 

第六行的含义为:rtsg 向 csam发送了19字节的数据(字节的编号为2到20，传送方向为rtsg到csam). 包中设置了PUSH标志. 在第7行,
csam 喊到， 她已经从rtsg中收到了21以下的字节, 但不包括21编号的字节. 这些字节存放在csam的socket的接收缓冲中, 相应地,
csam的接收缓冲窗口大小会减少19字节(nt:可以从第5行和第7行win属性值的变化看出来). csam在第7行这个包中也向rtsg发送了一个
字节. 在第8行和第9行, csam 继续向rtsg 分别发送了两个只包含一个字节的数据包, 并且这个数据包带PUSH标志.

如果所抓到的tcp包(nt:即这里的snapshot)太小了，以至tcpdump无法完整得到其头部数据, 这时, tcpdump会尽量解析这个不完整的头,
并把剩下不能解析的部分显示为'[|tcp]'. 如果头部含有虚假的属性信息(比如其长度属性其实比头部实际长度长或短), tcpdump会为该头部
显示'[bad opt]'. 如果头部的长度告诉我们某些选项(nt | rt:从下文来看， 指tcp包的头部中针对ip包的一些选项, 回头再翻)会在此包中,
而真正的IP(数据包的长度又不够容纳这些选项, tcpdump会显示'[bad hdr length]'.


抓取带有特殊标志的的TCP包(如SYN-ACK标志, URG-ACK标志等).

在TCP的头部中, 有8比特(bit)用作控制位区域, 其取值为:
CWR | ECE | URG | ACK | PSH | RST | SYN | FIN
(nt | rt:从表达方式上可推断:这8个位是用或的方式来组合的, 可回头再翻)

现假设我们想要监控建立一个TCP连接整个过程中所产生的数据包. 可回忆如下:TCP使用3次握手协议来建立一个新的连接; 其与此三次握手
连接顺序对应，并带有相应TCP控制标志的数据包如下:
1) 连接发起方(nt:Caller)发送SYN标志的数据包
2) 接收方(nt:Recipient)用带有SYN和ACK标志的数据包进行回应
3) 发起方收到接收方回应后再发送带有ACK标志的数据包进行回应


0 15 31
-----------------------------------------------------------------
| source port | destination port |
-----------------------------------------------------------------
| sequence number |
-----------------------------------------------------------------
| acknowledgment number |
-----------------------------------------------------------------
| HL | rsvd |C|E|U|A|P|R|S|F| window size |
-----------------------------------------------------------------
| TCP checksum | urgent pointer |
-----------------------------------------------------------------

一个TCP头部,在不包含选项数据的情况下通常占用20个字节(nt | rt:options 理解为选项数据，需回译). 第一行包含0到3编号的字节,
第二行包含编号4-7的字节.

如果编号从0开始算, TCP控制标志位于13字节(nt:第四行左半部分).

 

0 7| 15| 23| 31
----------------|---------------|---------------|----------------
| HL | rsvd |C|E|U|A|P|R|S|F| window size |
----------------|---------------|---------------|----------------
| | 13th octet | | |

让我们仔细看看编号13的字节:

| |
|---------------|
|C|E|U|A|P|R|S|F|
|---------------|
|7 5 3 0|


这里有我们感兴趣的控制标志位. 从右往左这些位被依次编号为0到7, 从而 PSH位在3号, 而URG位在5号.

 

提醒一下自己, 我们只是要得到包含SYN标志的数据包. 让我们看看在一个包的包头中, 如果SYN位被设置, 到底
在13号字节发生了什么:

|C|E|U|A|P|R|S|F|
|---------------|
|0 0 0 0 0 0 1 0|
|---------------|
|7 6 5 4 3 2 1 0|


在控制段的数据中, 只有比特1(bit number 1)被置位.

假设编号为13的字节是一个8位的无符号字符型,并且按照网络字节号排序(nt:对于一个字节来说，网络字节序等同于主机字节序), 其二进制值
如下所示:
00000010

并且其10进制值为:

0*2^7 + 0*2^6 + 0*2^5 + 0*2^4 + 0*2^3 + 0*2^2 + 1*2^1 + 0*2^0 = 2(nt: 1 * 2^6 表示1乘以2的6次方, 也许这样更
清楚些, 即把原来表达中的指数7 6 ... 0挪到了下面来表达)

接近目标了, 因为我们已经知道, 如果数据包头部中的SYN被置位, 那么头部中的第13个字节的值为2(nt: 按照网络序, 即大头方式, 最重要的字节
在前面(在前面,即该字节实际内存地址比较小, 最重要的字节,指数学表示中数的高位, 如356中的3) ).

表达为tcpdump能理解的关系式就是:
tcp[13] 2

从而我们可以把此关系式当作tcpdump的过滤条件, 目标就是监控只含有SYN标志的数据包:
tcpdump -i xl0 tcp[13] 2 (nt: xl0 指网络接口, 如eth0)

这个表达式是说"让TCP数据包的第13个字节拥有值2吧", 这也是我们想要的结果.


现在, 假设我们需要抓取带SYN标志的数据包, 而忽略它是否包含其他标志.(nt:只要带SYN就是我们想要的). 让我们来看看当一个含有
SYN-ACK的数据包(nt:SYN 和 ACK 标志都有), 来到时发生了什么:
|C|E|U|A|P|R|S|F|
|---------------|
|0 0 0 1 0 0 1 0|
|---------------|
|7 6 5 4 3 2 1 0|

13号字节的1号和4号位被置位, 其二进制的值为:
00010010

转换成十进制就是:

0*2^7 + 0*2^6 + 0*2^5 + 1*2^4 + 0*2^3 + 0*2^2 + 1*2^1 + 0*2 = 18(nt: 1 * 2^6 表示1乘以2的6次方, 也许这样更
清楚些, 即把原来表达中的指数7 6 ... 0挪到了下面来表达)

现在, 却不能只用'tcp[13] 18'作为tcpdump的过滤表达式, 因为这将导致只选择含有SYN-ACK标志的数据包, 其他的都被丢弃.
提醒一下自己, 我们的目标是: 只要包的SYN标志被设置就行, 其他的标志我们不理会.

为了达到我们的目标, 我们需要把13号字节的二进制值与其他的一个数做AND操作(nt:逻辑与)来得到SYN比特位的值. 目标是:只要SYN 被设置
就行, 于是我们就把她与上13号字节的SYN值(nt: 00000010).

00010010 SYN-ACK 00000010 SYN
AND 00000010 (we want SYN) AND 00000010 (we want SYN)
-------- --------
= 00000010 = 00000010

我们可以发现, 不管包的ACK或其他标志是否被设置, 以上的AND操作都会给我们相同的值, 其10进制表达就是2(2进制表达就是00000010).
从而我们知道, 对于带有SYN标志的数据包, 以下的表达式的结果总是真(true):

( ( value of octet 13 ) AND ( 2 ) ) ( 2 ) (nt: value of octet 13, 即13号字节的值)

灵感随之而来, 我们于是得到了如下的tcpdump 的过滤表达式
tcpdump -i xl0 'tcp[13] & 2 2'

注意, 单引号或反斜杆(nt: 这里用的是单引号)不能省略, 这可以防止shell对&的解释或替换.
```



##### 3、UDP数据包

```
UDP 数据包的显示格式，可通过rwho这个具体应用所产生的数据包来说明:
actinide.who > broadcast.who: udp 84

其含义为:actinide主机上的端口who向broadcast主机上的端口who发送了一个udp数据包(nt: actinide和broadcast都是指Internet地址).
这个数据包承载的用户数据为84个字节.

一些UDP服务可从数据包的源或目的端口来识别，也可从所显示的更高层协议信息来识别. 比如, Domain Name service requests(DNS 请求,
在RFC-1034/1035中), 和Sun RPC calls to NFS(对NFS服务器所发起的远程调用(nt: 即Sun RPC)，在RFC-1050中有对远程调用的描述).

UDP 名称服务请求

(注意:以下的描述假设你对Domain Service protoco(nt:在RFC-103中有所描述), 否则你会发现以下描述就是天书(nt:希腊文天书,
不必理会, 吓吓你的, 接着看就行))

名称服务请求有如下的格式:
src > dst: id op? flags qtype qclass name (len)
(nt: 从下文来看, 格式应该是src > dst: id op flags qtype qclass? name (len))
比如有一个实际显示为:
h2opolo.1538 > helios.domain: 3+ A? ucbvax.berkeley.edu. (37)

主机h2opolo 向helios 上运行的名称服务器查询ucbvax.berkeley.edu 的地址记录(nt: qtype等于A). 此查询本身的id号为'3'. 符号
'+'意味着递归查询标志被设置(nt: dns服务器可向更高层dns服务器查询本服务器不包含的地址记录). 这个最终通过IP包发送的查询请求
数据长度为37字节, 其中不包括UDP和IP协议的头数据. 因为此查询操作为默认值(nt | rt: normal one的理解), op字段被省略.
如果op字段没被省略, 会被显示在'3' 和'+'之间. 同样, qclass也是默认值, C_IN, 从而也没被显示, 如果没被忽略, 她会被显示在'A'之后.

异常检查会在方括中显示出附加的域:　如果一个查询同时包含一个回应(nt: 可理解为, 对之前其他一个请求的回应), 并且此回应包含权威或附加记录段,　
ancount, nscout, arcount(nt: 具体字段含义需补充) 将被显示为'[na]', '[nn]', '[nau]', 其中n代表合适的计数. 如果包中以下
回应位(比如AA位, RA位, rcode位), 或者字节2或3中任何一个'必须为0'的位被置位(nt: 设置为1), '[b2&3]=x' 将被显示, 其中x表示
头部字节2与字节3进行与操作后的值.

UDP 名称服务应答

对名称服务应答的数据包，tcpdump会有如下的显示格式
src > dst: id op rcode flags a/n/au type class data (len)
比如具体显示如下:
helios.domain > h2opolo.1538: 3 3/3/7 A 128.32.137.3 (273)
helios.domain > h2opolo.1537: 2 NXDomain* 0/1/0 (97)

第一行表示: helios 对h2opolo 所发送的3号查询请求回应了3条回答记录(nt | rt: answer records), 3条名称服务器记录,
以及7条附加的记录. 第一个回答记录(nt: 3个回答记录中的第一个)类型为A(nt: 表示地址), 其数据为internet地址128.32.137.3.
此回应UDP数据包, 包含273字节的数据(不包含UPD和IP的头部数据). op字段和rcode字段被忽略(nt: op的实际值为Query, rcode, 即
response code的实际值为NoError), 同样被忽略的字段还有class 字段(nt | rt: 其值为C_IN, 这也是A类型记录默认取值)

第二行表示: helios 对h2opolo 所发送的2号查询请求做了回应. 回应中, rcode编码为NXDomain(nt: 表示不存在的域)), 没有回答记录,
但包含一个名称服务器记录, 不包含权威服务器记录(nt | ck: 从上文来看, 此处的authority records 就是上文中对应的additional
records). '*'表示权威服务器回答标志被设置(nt: 从而additional records就表示的是authority records).
由于没有回答记录, type, class, data字段都被忽略.

flag字段还有可能出现其他一些字符, 比如'-'(nt: 表示可递归地查询, 即RA 标志没有被设置), '|'(nt: 表示被截断的消息, 即TC 标志
被置位). 如果应答(nt | ct: 可理解为, 包含名称服务应答的UDP数据包, tcpdump知道这类数据包该怎样解析其数据)的'question'段一个条
目(entry)都不包含(nt: 每个条目的含义, 需补充),'[nq]' 会被打印出来.

要注意的是:名称服务器的请求和应答数据量比较大, 而默认的68字节的抓取长度(nt: snaplen, 可理解为tcpdump的一个设置选项)可能不足以抓取
数据包的全部内容. 如果你真的需要仔细查看名称服务器的负载, 可以通过tcpdump 的-s 选项来扩大snaplen值.
```



##### 4、SMB/CIFS 解码

```
tcpdump 已可以对SMB/CIFS/NBT相关应用的数据包内容进行解码(nt: 分别为'Server Message Block Common', 'Internet File System'
'在TCP/IP上实现的网络协议NETBIOS的简称'. 这几个服务通常使用UDP的137/138以及TCP的139端口). 原来的对IPX和NetBEUI SMB数据包的
解码能力依然可以被使用(nt: NetBEUI为NETBIOS的增强版本).


tcpdump默认只按照最简约模式对相应数据包进行解码, 如果我们想要详尽的解码信息可以使用其-v 启动选现. 要注意的是, -v 会产生非常详细的信息,
比如对单一的一个SMB数据包, 将产生一屏幕或更多的信息, 所以此选项, 确有需要才使用.

关于SMB数据包格式的信息, 以及每个域的含义可以参看www.cifs.org 或者samba.org 镜像站点的pub/samba/specs/ 目录. linux 上的SMB 补丁
(nt | rt: patch)由 Andrew Tridgell (tridge@samba.org)提供.


NFS 请求和回应

tcpdump对Sun NFS(网络文件系统)请求和回应的UDP数据包有如下格式的打印输出:
src.xid > dst.nfs: len op args
src.nfs > dst.xid: reply stat len op results

以下是一组具体的输出数据
sushi.6709 > wrl.nfs: 112 readlink fh 21,24/10.73165
wrl.nfs > sushi.6709: reply ok 40 readlink "../var"
sushi.201b > wrl.nfs:
144 lookup fh 9,74/4096.6878 "xcolors"
wrl.nfs > sushi.201b:
reply ok 128 lookup fh 9,74/4134.3150

第一行输出表明: 主机sushi向主机wrl发送了一个'交换请求'(nt: transaction), 此请求的id为6709(注意, 主机名字后是交换
请求id号, 而不是源端口号). 此请求数据为112字节, 其中不包括UDP和IP头部的长度. 操作类型为readlink(nt: 即此操作为读符号链接操作),
操作参数为fh 21,24/10.73165(nt: 可按实际运行环境, 解析如下, fd 表示描述的为文件句柄, 21,24 表示此句柄所对应设
备的主/从设备号对, 10表示此句柄所对应的i节点编号(nt:每个文件都会在操作系统中对应一个i节点, 限于unix类系统中),
73165是一个编号(nt: 可理解为标识此请求的一个随机数, 具体含义需补充)).

第二行中, wrl 做了'ok'的回应, 并且在results 字段中返回了sushi想要读的符号连接的真实目录(nt: 即sushi要求读的符号连接其实是一个目录).

第三行表明: sushi 再次请求 wrl 在'fh 9,74/4096.6878'所描述的目录中查找'xcolors'文件. 需要注意的是, 每行所显示的数据含义依赖于其中op字段的
类型(nt: 不同op 所对应args 含义不相同), 其格式遵循NFS 协议, 追求简洁明了.

 

如果tcpdump 的-v选项(详细打印选项) 被设置, 附加的信息将被显示. 比如:
sushi.1372a > wrl.nfs:
148 read fh 21,11/12.195 8192 bytes @ 24576
wrl.nfs > sushi.1372a:
reply ok 1472 read REG 100664 ids 417/0 sz 29388

(-v 选项一般还会打印出IP头部的TTL, ID， length, 以及fragmentation 域, 但在此例中, 都略过了(nt: 可理解为,简洁起见, 做了删减))
在第一行, sushi 请求wrl 从文件 21,11/12.195(nt: 格式在上面有描述)中, 自偏移24576字节处开始, 读取8192字节数据.
Wrl 回应读取成功; 由于第二行只是回应请求的开头片段, 所以只包含1472字节(其他的数据将在接着的reply片段中到来, 但这些数据包不会再有NFS
头, 甚至UDP头信息也为空(nt: 源和目的应该要有), 这将导致这些片段不能满足过滤条件, 从而没有被打印). -v 选项除了显示文件数据信息, 还会显示
附加显示文件属性信息: file type(文件类型, ''REG'' 表示普通文件), file mode(文件存取模式, 8进制表示的), uid 和gid(nt: 文件属主和
组属主), file size (文件大小).

如果-v 标志被多次重复给出(nt: 如-vv)， tcpdump会显示更加详细的信息.

必须要注意的是, NFS 请求包中数据比较多, 如果tcpdump 的snaplen(nt: 抓取长度) 取太短将不能显示其详细信息. 可使用
'-s 192'来增加snaplen, 这可用以监测NFS应用的网络负载(nt: traffic).

NFS 的回应包并不严格的紧随之前相应的请求包(nt: RPC operation). 从而, tcpdump 会跟踪最近收到的一系列请求包, 再通过其
交换序号(nt: transaction ID)与相应请求包相匹配. 这可能产生一个问题， 如果回应包来得太迟, 超出tcpdump 对相应请求包的跟踪范围,
该回应包将不能被分析.
```



##### 5、AFS请求和回应

```
AFS(nt: Andrew 文件系统, Transarc , 未知, 需补充)请求和回应有如下的答应

src.sport > dst.dport: rx packet-type
src.sport > dst.dport: rx packet-type service call call-name args
src.sport > dst.dport: rx packet-type service reply call-name args

elvis.7001 > pike.afsfs:
rx data fs call rename old fid 536876964/1/1 ".newsrc.new"
new fid 536876964/1/1 ".newsrc"
pike.afsfs > elvis.7001: rx data fs reply rename

在第一行, 主机elvis 向pike 发送了一个RX数据包.
这是一个对于文件服务的请求数据包(nt: RX data packet, 发送数据包 , 可理解为发送包过去, 从而请求对方的服务), 这也是一个RPC
调用的开始(nt: RPC, remote procedure call). 此RPC 请求pike 执行rename(nt: 重命名) 操作, 并指定了相关的参数:
原目录描述符为536876964/1/1, 原文件名为 '.newsrc.new', 新目录描述符为536876964/1/1, 新文件名为 '.newsrc'.
主机pike 对此rename操作的RPC请求作了回应(回应表示rename操作成功, 因为回应的是包含数据内容的包而不是异常包).

一般来说, 所有的'AFS RPC'请求被显示时, 会被冠以一个名字(nt: 即decode, 解码), 这个名字往往就是RPC请求的操作名.
并且, 这些RPC请求的部分参数在显示时, 也会被冠以一个名字(nt | rt: 即decode, 解码, 一般来说也是取名也很直接, 比如,
一个interesting 参数, 显示的时候就会直接是'interesting', 含义拗口, 需再翻).

这种显示格式的设计初衷为'一看就懂', 但对于不熟悉AFS 和 RX 工作原理的人可能不是很
有用(nt: 还是不用管, 书面吓吓你的, 往下看就行).

如果 -v(详细)标志被重复给出(nt: 如-vv), tcpdump 会打印出确认包(nt: 可理解为, 与应答包有区别的包)以及附加头部信息
(nt: 可理解为, 所有包, 而不仅仅是确认包的附加头部信息), 比如, RX call ID(请求包中'请求调用'的ID),
call number('请求调用'的编号), sequence number(nt: 包顺序号),
serial number(nt | rt: 可理解为与包中数据相关的另一个顺信号, 具体含义需补充), 请求包的标识. (nt: 接下来一段为重复描述,
所以略去了), 此外确认包中的MTU协商信息也会被打印出来(nt: 确认包为相对于请求包的确认包, Maximum Transmission Unit, 最大传输单元).

如果 -v 选项被重复了三次(nt: 如-vvv), 那么AFS应用类型数据包的'安全索引'('security index')以及'服务索引'('service id')将会
被打印.

对于表示异常的数据包(nt: abort packet, 可理解为, 此包就是用来通知接受者某种异常已发生), tcpdump 会打印出错误号(error codes).
但对于Ubik beacon packets(nt: Ubik 灯塔指示包, Ubik可理解为特殊的通信协议, beacon packets, 灯塔数据包, 可理解为指明通信中
关键信息的一些数据包), 错误号不会被打印, 因为对于Ubik 协议, 异常数据包不是表示错误, 相反却是表示一种肯定应答(nt: 即, yes vote).

AFS 请求数据量大, 参数也多, 所以要求tcpdump的 snaplen 比较大, 一般可通过启动tcpdump时设置选项'-s 256' 来增大snaplen, 以
监测AFS 应用通信负载.

AFS 回应包并不显示标识RPC 属于何种远程调用. 从而, tcpdump 会跟踪最近一段时间内的请求包, 并通过call number(调用编号), service ID
(服务索引) 来匹配收到的回应包. 如果回应包不是针对最近一段时间内的请求包, tcpdump将无法解析该包.
```



##### 6、KIP AppleTalk协议

```
(nt | rt: DDP in UDP可理解为, DDP, The AppleTalk Data Delivery Protocol,
相当于支持KIP AppleTalk协议栈的网络层协议, 而DDP 本身又是通过UDP来传输的,
即在UDP 上实现的用于其他网络的网络层，KIP AppleTalk是苹果公司开发的整套网络协议栈).

AppleTalk DDP 数据包被封装在UDP数据包中, 其解封装(nt: 相当于解码)和相应信息的转储也遵循DDP 包规则.
(nt:encapsulate, 封装, 相当于编码, de-encapsulate, 解封装, 相当于解码, dump, 转储, 通常就是指对其信息进行打印).

/etc/atalk.names 文件中包含了AppleTalk 网络和节点的数字标识到名称的对应关系. 其文件格式通常如下所示:
number name

1.254 ether
16.1 icsd-net
1.254.110 ace

头两行表示有两个AppleTalk 网络. 第三行给出了特定网络上的主机(一个主机会用3个字节来标识,
而一个网络的标识通常只有两个字节, 这也是两者标识的主要区别)(nt: 1.254.110 可理解为ether网络上的ace主机).
标识与其对应的名字之间必须要用空白分开. 除了以上内容, /etc/atalk.names中还包含空行以及注释行(以'#'开始的行).


AppleTalk 完整网络地址将以如下格式显示:
net.host.port

以下为一段具体显示:
144.1.209.2 > icsd-net.112.220
office.2 > icsd-net.112.220
jssmag.149.235 > icsd-net.2

(如果/etc/atalk.names 文件不存在, 或者没有相应AppleTalk 主机/网络的条目, 数据包的网络地址将以数字形式显示).

在第一行中, 网络144.1上的节点209通过2端口,向网络icsd-net上监听在220端口的112节点发送了一个NBP应用数据包
(nt | rt: NBP, name binding protocol, 名称绑定协议, 从数据来看, NBP服务器会在端口2提供此服务.
'DDP port 2' 可理解为'DDP 对应传输层的端口2', DDP本身没有端口的概念, 这点未确定, 需补充).

第二行与第一行类似, 只是源的全部地址可用'office'进行标识.
第三行表示: jssmag网络上的149节点通过235向icsd-net网络上的所有节点的2端口(NBP端口)发送了数据包.(需要注意的是,
在AppleTalk 网络中如果地址中没有节点, 则表示广播地址, 从而节点标识和网络标识最好在/etc/atalk.names有所区别.
nt: 否则一个标识x.port 无法确定x是指一个网络上所有主机的port口还是指定主机x的port口).

tcpdump 可解析NBP (名称绑定协议) and ATP (AppleTalk传输协议)数据包, 对于其他应用层的协议, 只会打印出相应协议名字(
如果此协议没有注册一个通用名字, 只会打印其协议号)以及数据包的大小.


NBP 数据包会按照如下格式显示:
icsd-net.112.220 > jssmag.2: nbp-lkup 190: "=:LaserWriter@*"
jssmag.209.2 > icsd-net.112.220: nbp-reply 190: "RM1140:LaserWriter@*" 250
techpit.2 > icsd-net.112.220: nbp-reply 190: "techpit:LaserWriter@*" 186

第一行表示: 网络icsd-net 中的节点112 通过220端口向网络jssmag 中所有节点的端口2发送了对'LaserWriter'的名称查询请求(nt:
此处名称可理解为一个资源的名称, 比如打印机). 此查询请求的序列号为190.

第二行表示: 网络jssmag 中的节点209 通过2端口向icsd-net.112节点的端口220进行了回应: 我有'LaserWriter'资源, 其资源名称
为'RM1140', 并且在端口250上提供改资源的服务. 此回应的序列号为190, 对应之前查询的序列号.

第三行也是对第一行请求的回应: 节点techpit 通过2端口向icsd-net.112节点的端口220进行了回应:我有'LaserWriter'资源, 其资源名称
为'techpit', 并且在端口186上提供改资源的服务. 此回应的序列号为190, 对应之前查询的序列号.


ATP 数据包的显示格式如下:
jssmag.209.165 > helios.132: atp-req 12266<0-7> 0xae030001
helios.132 > jssmag.209.165: atp-resp 12266:0 (512) 0xae040000
helios.132 > jssmag.209.165: atp-resp 12266:1 (512) 0xae040000
helios.132 > jssmag.209.165: atp-resp 12266:2 (512) 0xae040000
helios.132 > jssmag.209.165: atp-resp 12266:3 (512) 0xae040000
helios.132 > jssmag.209.165: atp-resp 12266:5 (512) 0xae040000
helios.132 > jssmag.209.165: atp-resp 12266:6 (512) 0xae040000
helios.132 > jssmag.209.165: atp-resp*12266:7 (512) 0xae040000
jssmag.209.165 > helios.132: atp-req 12266<3,5> 0xae030001
helios.132 > jssmag.209.165: atp-resp 12266:3 (512) 0xae040000
helios.132 > jssmag.209.165: atp-resp 12266:5 (512) 0xae040000
jssmag.209.165 > helios.132: atp-rel 12266<0-7> 0xae030001
jssmag.209.133 > helios.132: atp-req* 12267<0-7> 0xae030002

第一行表示节点 Jssmag.209 向节点helios 发送了一个会话编号为12266的请求包, 请求helios
回应8个数据包(这8个数据包的顺序号为0-7(nt: 顺序号与会话编号不同, 后者为一次完整传输的编号,
前者为该传输中每个数据包的编号. transaction, 会话, 通常也被叫做传输)). 行尾的16进制数字表示
该请求包中'userdata'域的值(nt: 从下文来看, 这并没有把所有用户数据都打印出来 ).

Helios 回应了8个512字节的数据包. 跟在会话编号(nt: 12266)后的数字表示该数据包在该会话中的顺序号.
括号中的数字表示该数据包中数据的大小, 这不包括atp 的头部. 在顺序号为7数据包(第8行)外带了一个'*'号,
表示该数据包的EOM 标志被设置了.(nt: EOM, End Of Media, 可理解为, 表示一次会话的数据回应完毕).

接下来的第9行表示, Jssmag.209 又向helios 提出了请求: 顺序号为3以及5的数据包请重新传送. Helios 收到这个
请求后重新发送了这个两个数据包, jssmag.209 再次收到这两个数据包之后, 主动结束(release)了此会话.

在最后一行, jssmag.209 向helios 发送了开始下一次会话的请求包. 请求包中的'*'表示该包的XO 标志没有被设置.
(nt: XO, exactly once, 可理解为在该会话中, 数据包在接受方只被精确地处理一次, 就算对方重复传送了该数据包,
接收方也只会处理一次, 这需要用到特别设计的数据包接收和处理机制).
```



##### 7、IP 数据包破碎

```
(nt: 指把一个IP数据包分成多个IP数据包)

碎片IP数据包(nt: 即一个大的IP数据包破碎后生成的小IP数据包)有如下两种显示格式.
(frag id:size@offset+)
(frag id:size@offset)
(第一种格式表示, 此碎片之后还有后续碎片. 第二种格式表示, 此碎片为最后一个碎片.)

id 表示破碎编号(nt: 从下文来看, 会为每个要破碎的大IP包分配一个破碎编号, 以便区分每个小碎片是否由同一数据包破碎而来).
size 表示此碎片的大小 , 不包含碎片头部数据. offset表示此碎片所含数据在原始整个IP包中的偏移((nt: 从下文来看,
一个IP数据包是作为一个整体被破碎的, 包括头和数据, 而不只是数据被分割).

每个碎片都会使tcpdump产生相应的输出打印. 第一个碎片包含了高层协议的头数据(nt:从下文来看, 被破碎IP数据包中相应tcp头以及
IP头都放在了第一个碎片中 ), 从而tcpdump会针对第一个碎片显示这些信息, 并接着显示此碎片本身的信息. 其后的一些碎片并不包含
高层协议头信息, 从而只会在显示源和目的之后显示碎片本身的信息. 以下有一个例子: 这是一个从arizona.edu 到lbl-rtsg.arpa
途经CSNET网络(nt: CSNET connection 可理解为建立在CSNET 网络上的连接)的ftp应用通信片段:
arizona.ftp-data > rtsg.1170: . 1024:1332(308) ack 1 win 4096 (frag 595a:328@0+)
arizona > rtsg: (frag 595a:204@328)
rtsg.1170 > arizona.ftp-data: . ack 1536 win 2560

有几点值得注意:
第一, 第二行的打印中, 地址后面没有端口号.
这是因为TCP协议信息都放到了第一个碎片中, 当显示第二个碎片时, 我们无法知道此碎片所对应TCP包的顺序号.

第二, 从第一行的信息中, 可以发现arizona需要向rtsg发送308字节的用户数据, 而事实是, 相应IP包经破碎后会总共产生512字节
数据(第一个碎片包含308字节的数据, 第二个碎片包含204个字节的数据, 这超过了308字节). 如果你在查找数据包的顺序号空间中的
一些空洞(nt: hole,空洞, 指数据包之间的顺序号没有上下衔接上), 512这个数据就足够使你迷茫一阵(nt: 其实只要关注308就行,
不必关注破碎后的数据总量).

一个数据包(nt | rt: 指IP数据包)如果带有非IP破碎标志, 则显示时会在最后显示'(DF)'.(nt: 意味着此IP包没有被破碎过).
```



##### 8、时间戳

tcpdump的所有输出打印行中都会默认包含时间戳信息.
时间戳信息的显示格式如下
hh:mm:ss.frac　(nt: 小时:分钟:秒.(nt: frac未知, 需补充))
此时间戳的精度与内核时间精度一致,　反映的是内核第一次看到对应数据包的时间(nt: saw, 即可对该数据包进行操作).　
而数据包从物理线路传递到内核的时间, 以及内核花费在此包上的中断处理时间都没有算进来.

