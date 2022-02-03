## 附：tcpdump的表达元



```
(nt: True 在以下的描述中含义为: 相应条件表达式中只含有以下所列的一个特定表达元, 此时表达式为真, 即条件得到满足)

dst host host
如果IPv4/v6 数据包的目的域是host, 则与此对应的条件表达式为真.host 可以是一个ip地址, 也可以是一个主机名.
src host host
如果IPv4/v6 数据包的源域是host, 则与此对应的条件表达式为真.
host 可以是一个ip地址, 也可以是一个主机名.
host host

如果IPv4/v6数据包的源或目的地址是 host, 则与此对应的条件表达式为真.以上的几个host 表达式之前可以添加以下关键字:ip, arp, rarp, 以及 ip6.比如:
ip host host
也可以表达为:
ether proto \ip and host host(nt: 这种表达方式在下面有说明, 其中ip之前需要有\来转义,因为ip 对tcpdump 来说已经是一个关键字了.)

如果host 是一个拥有多个IP 的主机, 那么任何一个地址都会用于包的匹配(nt: 即发向host 的数据包的目的地址可以是这几个IP中的任何一个, 从host 接收的数据包的源地址也可以是这几个IP中的任何一个).

ether dst ehost
如果数据包(nt: 指tcpdump 可抓取的数据包, 包括ip 数据包, tcp数据包)的以太网目标地址是ehost,则与此对应的条件表达式为真. Ehost 可以是/etc/ethers 文件中的名字或一个数字地址(nt: 可通过 man ethers 看到对/etc/ethers 文件的描述, 样例中用的是数字地址)

ether src ehost
如果数据包的以太网源地址是ehost, 则与此对应的条件表达式为真.

ether host ehost
如果数据包的以太网源地址或目标地址是ehost, 则与此对应的条件表达式为真.

gateway host
如果数据包的网关地址是host, 则与此对应的条件表达式为真. 需要注意的是, 这里的网关地址是指以太网地址, 而不是IP 地址(nt | rt: I.e., 例如, 可理解为'注意'.the Ethernet source or destination address, 以太网源和目标地址, 可理解为, 指代上句中的'网关地址' ).host 必须是名字而不是数字, 并且必须在机器的'主机名-ip地址'以及'主机名-以太地址'两大映射关系中 有其条目(前一映射关系可通过/etc/hosts文件, DNS 或 NIS得到, 而后一映射关系可通过/etc/ethers 文件得到. nt: /etc/ethers并不一定存在 , 可通过man ethers 看到其数据格式, 如何创建该文件, 未知,需补充).也就是说host 的含义是 ether host ehost 而不是 host host, 并且ehost必须是名字而不是数字.
目前, 该选项在支持IPv6地址格式的配置环境中不起作用(nt: configuration, 配置环境, 可理解为,通信双方的网络配置).

dst net net
如果数据包的目标地址(IPv4或IPv6格式)的网络号字段为 net, 则与此对应的条件表达式为真.
net 可以是从网络数据库文件/etc/networks 中的名字, 也可以是一个数字形式的网络编号.

一个数字IPv4 网络编号将以点分四元组(比如, 192.168.1.0), 或点分三元组(比如, 192.168.1 ), 或点分二元组(比如, 172.16), 或单一单元组(比如, 10)来表达;

对应于这四种情况的网络掩码分别是:四元组:255.255.255.255(这也意味着对net 的匹配如同对主机地址(host)的匹配:地址的四个部分都用到了),三元组:255.255.255.0, 二元组: 255.255.0.0, 一元组:255.0.0.0.

对于IPv6 的地址格式, 网络编号必须全部写出来(8个部分必须全部写出来); 相应网络掩码为:
ff:ff:ff:ff:ff:ff:ff:ff, 所以IPv6 的网络匹配是真正的'host'方式的匹配(nt | rt | rc:地址的8个部分都会用到,是否不属于网络的字节填写0, 需接下来补充), 但同时需要一个网络掩码长度参数来具体指定前面多少字节为网络掩码(nt: 可通过下面的net net/len 来指定)

src net net
如果数据包的源地址(IPv4或IPv6格式)的网络号字段为 net, 则与此对应的条件表达式为真.

net net
如果数据包的源或目的地址(IPv4或IPv6格式)的网络号字段为 net, 则与此对应的条件表达式为真.

net net mask netmask
如果数据包的源或目的地址(IPv4或IPv6格式)的网络掩码与netmask 匹配, 则与此对应的条件表达式为真.此选项之前还可以配合src和dst来匹配源网络地址或目标网络地址(nt: 比如 src net net mask 255.255.255.0).该选项对于ipv6 网络地址无效.

net net/len
如果数据包的源或目的地址(IPv4或IPv6格式)的网络编号字段的比特数与len相同, 则与此对应的条件表达式为真.此选项之前还可以配合src和dst来匹配源网络地址或目标网络地址(nt | rt | tt: src net net/24, 表示需要匹配源地址的网络编号有24位的数据包).

dst port port
如果数据包(包括ip/tcp, ip/udp, ip6/tcp or ip6/udp协议)的目的端口为port, 则与此对应的条件表达式为真.port 可以是一个数字也可以是一个名字(相应名字可以在/etc/services 中找到该名字, 也可以通过man tcp 和man udp来得到相关描述信息 ). 如果使用名字, 则该名字对应的端口号和相应使用的协议都会被检查. 如果只是使用一个数字端口号,则只有相应端口号被检查(比如, dst port 513 将会使tcpdump抓取tcp协议的login 服务和udp协议的who 服务数据包, 而port domain 将会使tcpdump 抓取tcp协议的domain 服务数据包, 以及udp 协议的domain 数据包)(nt | rt: ambiguous name is used 不可理解, 需补充).

src port port
如果数据包的源端口为port, 则与此对应的条件表达式为真.

port port
如果数据包的源或目的端口为port, 则与此对应的条件表达式为真.

dst portrange port1-port2
如果数据包(包括ip/tcp, ip/udp, ip6/tcp or ip6/udp协议)的目的端口属于port1到port2这个端口范围(包括port1, port2), 则与此对应的条件表达式为真. tcpdump 对port1 和port2 解析与对port 的解析一致(nt:在dst port port 选项的描述中有说明).

src portrange port1-port2
如果数据包的源端口属于port1到port2这个端口范围(包括 port1, port2), 则与此对应的条件表达式为真.

portrange port1-port2
如果数据包的源端口或目的端口属于port1到port2这个端口范围(包括 port1, port2), 则与此对应的条件表达式为真.

以上关于port 的选项都可以在其前面添加关键字:tcp 或者udp, 比如:
tcp src port port
这将使tcpdump 只抓取源端口是port 的tcp数据包.

less length
如果数据包的长度比length 小或等于length, 则与此对应的条件表达式为真. 这与'len <= length' 的含义一致.

greater length
如果数据包的长度比length 大或等于length, 则与此对应的条件表达式为真. 这与'len >= length' 的含义一致.

ip proto protocol
如果数据包为ipv4数据包并且其协议类型为protocol, 则与此对应的条件表达式为真.
Protocol 可以是一个数字也可以是名字, 比如:icmp6, igmp, igrp(nt: Interior Gateway Routing Protocol,内部网关路由协议), pim(Protocol Independent Multicast, 独立组播协议, 应用于组播路由器),ah, esp(nt: ah, 认证头, esp 安全负载封装, 这两者会用在IP包的安全传输机制中 ), vrrp(Virtual Router Redundancy Protocol, 虚拟路由器冗余协议), udp, or tcp. 由于tcp , udp 以及icmp是tcpdump 的关键字,所以在这些协议名字之前必须要用\来进行转义(如果在C-shell 中需要用\\来进行转义). 注意此表达元不会把数据包中协议头链中所有协议头内容全部打印出来(nt: 实际上只会打印指定协议的一些头部信息, 比如可以用tcpdump -i eth0 'ip proto \tcp and host 192.168.3.144', 则只打印主机192.168.3.144 发出或接收的数据包中tcp 协议头所包含的信息)

ip6 proto protocol
如果数据包为ipv6数据包并且其协议类型为protocol, 则与此对应的条件表达式为真.
注意此表达元不会把数据包中协议头链中所有协议头内容全部打印出来

ip6 protochain protocol
如果数据包为ipv6数据包并且其协议链中包含类型为protocol协议头, 则与此对应的条件表达式为真. 比如,
ip6 protochain 6

将匹配其协议头链中拥有TCP 协议头的IPv6数据包.此数据包的IPv6头和TCP头之间可能还会包含验证头, 路由头, 或者逐跳寻径选项头.
由此所触发的相应BPF(Berkeley Packets Filter, 可理解为, 在数据链路层提供数据包过滤的一种机制)代码比较繁琐,
并且BPF优化代码也未能照顾到此部分, 从而此选项所触发的包匹配可能会比较慢.

ip protochain protocol
与ip6 protochain protocol 含义相同, 但这用在IPv4数据包.

ether broadcast
如果数据包是以太网广播数据包, 则与此对应的条件表达式为真. ether 关键字是可选的.

ip broadcast
如果数据包是IPv4广播数据包, 则与此对应的条件表达式为真. 这将使tcpdump 检查广播地址是否符合全0和全1的一些约定,并查找网络接口的网络掩码(网络接口为当时在其上抓包的网络接口).

如果抓包所在网络接口的网络掩码不合法, 或者此接口根本就没有设置相应网络地址和网络， 亦或是在linux下的'any'网络接口上抓包(此'any'接口可以收到系统中不止一个接口的数据包(nt: 实际上, 可理解为系统中所有可用的接口)),网络掩码的检查不能正常进行.


ether multicast
如果数据包是一个以太网多点广播数据包(nt: 多点广播, 可理解为把消息同时传递给一组目的地址, 而不是网络中所有地址,后者为可称为广播(broadcast)), 则与此对应的条件表达式为真. 关键字ether 可以省略. 此选项的含义与以下条件表达式含义一致:`ether[0] & 1 != 0'(nt: 可理解为, 以太网数据包中第0个字节的最低位是1, 这意味这是一个多点广播数据包).

ip multicast
如果数据包是ipv4多点广播数据包, 则与此对应的条件表达式为真.

ip6 multicast
如果数据包是ipv6多点广播数据包, 则与此对应的条件表达式为真.

ether proto protocol
如果数据包属于以下以太协议类型, 则与此对应的条件表达式为真.
协议(protocol)字段, 可以是数字或以下所列出了名字: ip, ip6, arp, rarp, atalk(AppleTalk网络协议),
aarp(nt: AppleTalk Address Resolution Protocol, AppleTalk网络的地址解析协议),
decnet(nt: 一个由DEC公司所提供的网络协议栈), sca(nt: 未知, 需补充),
lat(Local Area Transport, 区域传输协议, 由DEC公司开发的以太网主机互联协议),
mopdl, moprc, iso(nt: 未知, 需补充), stp(Spanning tree protocol, 生成树协议, 可用于防止网络中产生链接循环),
ipx（nt: Internetwork Packet Exchange, Novell 网络中使用的网络层协议）, 或者
netbeui(nt: NetBIOS Extended User Interface，可理解为, 网络基本输入输出系统接口扩展).

protocol字段可以是一个数字或以下协议名之一:ip, ip6, arp, rarp, atalk, aarp, decnet, sca, lat,
mopdl, moprc, iso, stp, ipx, 或者netbeui.
必须要注意的是标识符也是关键字, 从而必须通过'\'来进行转义.

(SNAP：子网接入协议 （SubNetwork Access Protocol）)

在光纤分布式数据网络接口(其表达元形式可以是'fddi protocol arp'), 令牌环网(其表达元形式可以是'tr protocol arp'),
以及IEEE 802.11 无线局域网(其表达元形式可以是'wlan protocol arp')中, protocol
标识符来自802.2 逻辑链路控制层头,
在FDDI, Token Ring 或 802.1头中会包含此逻辑链路控制层头.

当以这些网络上的相应的协议标识为过滤条件时, tcpdump只是检查LLC头部中以0x000000为组成单元标识符(OUI, 0x000000
标识一个内部以太网)的一段'SNAP格式结构'中的protocol ID 域, 而不会管包中是否有一段OUI为0x000000的'SNAP格式
结构'(nt: SNAP, SubNetwork Access Protocol,子网接入协议 ). 以下例外:

iso tcpdump 会检查LLC头部中的DSAP域(Destination service Access Point, 目标服务接入点)和
SSAP域(源服务接入点).(nt: iso 协议未知, 需补充)

stp 以及 netbeui
tcpdump 将会检查LLC 头部中的目标服务接入点(Destination service Access Point);

atalk
tcpdump 将会检查LLC 头部中以0x080007 为OUI标识的'SNAP格式结构', 并会检查AppleTalk etype域.
(nt: AppleTalk etype 是否位于SNAP格式结构中, 未知, 需补充).

此外, 在以太网中, 对于ether proto protocol 选项, tcpdump 会为 protocol 所指定的协议检查
以太网类型域(the Ethernet type field), 但以下这些协议除外:

iso, stp, and netbeui
tcpdump 将会检查802.3 物理帧以及LLC 头(这两种检查与FDDI, TR, 802.11网络中的相应检查一致);
(nt: 802.3, 理解为IEEE 802.3, 其为一系列IEEE 标准的集合. 此集合定义了有线以太网络中的物理层以及数据
链路层的媒体接入控制子层. stp 在上文已有描述)

atalk
tcpdump 将会检查以太网物理帧中的AppleTalk etype 域 ,　同时也会检查数据包中LLC头部中的'SNAP格式结构'
(这两种检查与FDDI, TR, 802.11网络中的相应检查一致)

aarp tcpdump 将会检查AppleTalk ARP etype 域, 此域或存在于以太网物理帧中, 或存在于LLC(由802.2 所定义)的
'SNAP格式结构'中, 当为后者时, 该'SNAP格式结构'的OUI标识为0x000000;
(nt: 802.2, 可理解为, IEEE802.2, 其中定义了逻辑链路控制层(LLC), 该层对应于OSI 网络模型中数据链路层的上层部分.
LLC 层为使用数据链路层的用户提供了一个统一的接口(通常用户是网络层). LLC层以下是媒体接入控制层(nt: MAC层,
对应于数据链路层的下层部分).该层的实现以及工作方式会根据不同物理传输媒介的不同而有所区别(比如, 以太网, 令牌环网,
光纤分布数据接口(nt: 实际可理解为一种光纤网络), 无线局域网(802.11), 等等.)

ipx tcpdump 将会检查物理以太帧中的IPX etype域, LLC头中的IPX DSAP域，无LLC头并对IPX进行了封装的802.3帧,
以及LLC 头部'SNAP格式结构'中的IPX etype 域(nt | rt: SNAP frame, 可理解为, LLC 头中的'SNAP格式结构'.
该含义属初步理解阶段, 需补充).

decnet src host
如果数据包中DECNET源地址为host, 则与此对应的条件表达式为真.
(nt:decnet, 由Digital Equipment Corporation 开发, 最早用于PDP-11 机器互联的网络协议)

decnet dst host
如果数据包中DECNET目的地址为host, 则与此对应的条件表达式为真.
(nt: decnet 在上文已有说明)

decnet host host
如果数据包中DECNET目的地址或DECNET源地址为host, 则与此对应的条件表达式为真.
(nt: decnet 在上文已有说明)

ifname interface
如果数据包已被标记为从指定的网络接口中接收的, 则与此对应的条件表达式为真.
(此选项只适用于被OpenBSD中pf程序做过标记的包(nt: pf, packet filter, 可理解为OpenBSD中的防火墙程序))

on interface
与 ifname interface 含义一致.

rnr num
如果数据包已被标记为匹配PF的规则, 则与此对应的条件表达式为真.
(此选项只适用于被OpenBSD中pf程序做过标记的包(nt: pf, packet filter, 可理解为OpenBSD中的防火墙程序))

rulenum num
与 rulenum num 含义一致.

reason code
如果数据包已被标记为包含PF的匹配结果代码, 则与此对应的条件表达式为真.有效的结果代码有: match, bad-offset,
fragment, short, normalize, 以及memory.
(此选项只适用于被OpenBSD中pf程序做过标记的包(nt: pf, packet filter, 可理解为OpenBSD中的防火墙程序))

rset name
如果数据包已被标记为匹配指定的规则集, 则与此对应的条件表达式为真.
(此选项只适用于被OpenBSD中pf程序做过标记的包(nt: pf, packet filter, 可理解为OpenBSD中的防火墙程序))

ruleset name
与 rset name 含义一致.

srnr num
如果数据包已被标记为匹配指定的规则集中的特定规则(nt: specified PF rule number, 特定规则编号, 即特定规则),
则与此对应的条件表达式为真.(此选项只适用于被OpenBSD中pf程序做过标记的包(nt: pf, packet filter, 可理解为
OpenBSD中的防火墙程序))

subrulenum num
与 srnr 含义一致.

action act
如果包被记录时PF会执行act指定的动作, 则与此对应的条件表达式为真. 有效的动作有: pass, block.
(此选项只适用于被OpenBSD中pf程序做过标记的包(nt: pf, packet filter, 可理解为OpenBSD中的防火墙程序))

ip, ip6, arp, rarp, atalk, aarp, decnet, iso, stp, ipx, netbeui
与以下表达元含义一致:
ether proto p
p是以上协议中的一个.

lat, moprc, mopdl
与以下表达元含义一致:
ether proto p
p是以上协议中的一个. 必须要注意的是tcpdump目前还不能分析这些协议.

vlan [vlan_id]
如果数据包为IEEE802.1Q VLAN 数据包, 则与此对应的条件表达式为真.
(nt: IEEE802.1Q VLAN, 即IEEE802.1Q 虚拟网络协议, 此协议用于不同网络的之间的互联).
如果[vlan_id] 被指定, 则只有数据包含有指定的虚拟网络id(vlan_id), 则与此对应的条件表达式为真.
要注意的是, 对于VLAN数据包, 在表达式中遇到的第一个vlan关键字会改变表达式中接下来关键字所对应数据包中数据的
开始位置(即解码偏移). 在VLAN网络体系中过滤数据包时, vlan [vlan_id]表达式可以被多次使用. 关键字vlan每出现一次都会增加
4字节过滤偏移(nt: 过滤偏移, 可理解为上面的解码偏移).

例如:
vlan 100 && vlan 200
表示: 过滤封装在VLAN100中的VLAN200网络上的数据包
再例如:
vlan && vlan 300 && ip
表示: 过滤封装在VLAN300 网络中的IPv4数据包, 而VLAN300网络又被更外层的VLAN封装


mpls [label_num]
如果数据包为MPLS数据包, 则与此对应的条件表达式为真.
(nt: MPLS, Multi-Protocol Label Switch, 多协议标签交换, 一种在开放的通信网上利用标签引导数据传输的技术).

如果[label_num] 被指定, 则只有数据包含有指定的标签id(label_num), 则与此对应的条件表达式为真.
要注意的是, 对于内含MPLS信息的IP数据包(即MPLS数据包), 在表达式中遇到的第一个MPLS关键字会改变表达式中接下来关键字所对应数据包中数据的
开始位置(即解码偏移). 在MPLS网络体系中过滤数据包时, mpls [label_num]表达式可以被多次使用. 关键字mpls每出现一次都会增加
4字节过滤偏移(nt: 过滤偏移, 可理解为上面的解码偏移).

例如:
mpls 100000 && mpls 1024
表示: 过滤外层标签为100000 而层标签为1024的数据包

再如:
mpls && mpls 1024 && host 192.9.200.1
表示: 过滤发往或来自192.9.200.1的数据包, 该数据包的内层标签为1024, 且拥有一个外层标签.

pppoed
如果数据包为PPP-over-Ethernet的服务器探寻数据包(nt: Discovery packet,
其ethernet type 为0x8863),则与此对应的条件表达式为真.
(nt: PPP-over-Ethernet, 点对点以太网承载协议, 其点对点的连接建立分为Discovery阶段(地址发现) 和
PPPoE 会话建立阶段 , discovery 数据包就是第一阶段发出来的包. ethernet type
是以太帧里的一个字段，用来指明应用于帧数据字段的协议)

pppoes
如果数据包为PPP-over-Ethernet会话数据包(nt: ethernet type 为0x8864, PPP-over-Ethernet在上文已有说明, 可搜索
关键字'PPP-over-Ethernet'找到其描述), 则与此对应的条件表达式为真.

要注意的是, 对于PPP-over-Ethernet会话数据包, 在表达式中遇到的第一个pppoes关键字会改变表达式中接下来关键字所对应数据包中数据的
开始位置(即解码偏移).

例如:
pppoes && ip
表示: 过滤嵌入在PPPoE数据包中的ipv4数据包

tcp, udp, icmp
与以下表达元含义一致:
ip proto p or ip6 proto p
其中p 是以上协议之一(含义分别为: 如果数据包为ipv4或ipv6数据包并且其协议类型为 tcp,udp, 或icmp则与此对
应的条件表达式为真)

iso proto protocol
如果数据包的协议类型为iso-osi协议栈中protocol协议, 则与此对应的条件表达式为真.(nt: [初解]iso-osi 网络模型中每
层的具体协议与tcp/ip相应层采用的协议不同. iso-osi各层中的具体协议另需补充 )

protocol 可以是一个数字编号, 或以下名字中之一:
clnp, esis, or isis.
(nt: clnp, Connectionless Network Protocol, 这是OSI网络模型中网络层协议 , esis, isis 未知, 需补充)

clnp, esis, isis
是以下表达的缩写
iso proto p
其中p 是以上协议之一


l1, l2, iih, lsp, snp, csnp, psnp
为IS-IS PDU 类型 的缩写.
(nt: IS-IS PDU, Intermediate system to intermediate system Protocol Data Unit, 中间系统到
中间系统的协议数据单元. OSI(Open Systems Interconnection)网络由终端系统, 中间系统构成.
终端系统指路由器, 而终端系统指用户设备. 路由器形成的本地组称之为'区域'（Area）和多个区域组成一个'域'（Domain）.
IS-IS 提供域内或区域内的路由. l1, l2, iih, lsp, snp, csnp, psnp 表示PDU的类型, 具体含义另需补充)

vpi n
如果数据包为ATM数据包, 则与此对应的条件表达式为真. 对于Solaris 操作系统上的SunATM设备 ,
如果数据包为ATM数据包, 并且其虚拟路径标识为n, 则与此对应的条件表达式为真.
(nt: ATM, Asychronous Transfer Mode, 实际上可理解为由ITU-T(国际电信联盟电信标准化部门)提出的一个与
TCP/IP中IP层功能等同的一系列协议, 具体协议层次另需补充)

vci n
如果数据包为ATM数据包, 则与此对应的条件表达式为真. 对于Solaris 操作系统上的SunATM设备 ,
如果数据包为ATM数据包, 并且其虚拟通道标识为n, 则与此对应的条件表达式为真.
(nt: ATM, 在上文已有描述)

lane
如果数据包为ATM LANE 数据包, 则与此对应的条件表达式为真. 要注意的是, 如果是模拟以太网的LANE数据包或者
LANE逻辑单元控制包, 表达式中第一个lane关键字会改变表达式中随后条件的测试. 如果没有
指定lane关键字, 条件测试将按照数据包中内含LLC(逻辑链路层)的ATM包来进行.

llc
如果数据包为ATM数据包, 则与此对应的条件表达式为真. 对于Solaris 操作系统上的SunATM设备 ,
如果数据包为ATM数据包,　并且内含LLC则与此对应的条件表达式为真

oamf4s
如果数据包为ATM数据包, 则与此对应的条件表达式为真. 对于Solaris 操作系统上的SunATM设备 , 如果数据包为ATM数据包
并且是Segment OAM F4 信元(VPI=0 并且 VCI=3), 则与此对应的条件表达式为真.

(nt: OAM, Operation Administration and Maintenance, 操作管理和维护,可理解为:ATM网络中用于网络
管理所产生的ATM信元的分类方式.

ATM网络中传输单位为信元, 要传输的数据终究会被分割成固定长度(53字节)的信元,
(初理解: 一条物理线路可被复用, 形成虚拟路径(virtual path). 而一条虚拟路径再次被复用, 形成虚拟信道(virtual channel)).
通信双方的编址方式为:虚拟路径编号(VPI)/虚拟信道编号(VCI)).

OAM F4 flow 信元又可分为segment 类和end-to-end 类, 其区别未知, 需补充.)

oamf4e
如果数据包为ATM数据包, 则与此对应的条件表达式为真. 对于Solaris 操作系统上的SunATM设备 , 如果数据包为ATM数据包
并且是 end-to-end OAM F4 信元(VPI=0 并且 VCI=4), 则与此对应的条件表达式为真.
(nt: OAM 与 end-to-end OAM F4 在上文已有描述, 可搜索'oamf4s'来定位)

oamf4
如果数据包为ATM数据包, 则与此对应的条件表达式为真. 对于Solaris 操作系统上的SunATM设备 , 如果数据包为ATM数据包
并且是 end-to-end 或 segment OAM F4 信元(VPI=0 并且 VCI=3 或者 VCI=4), 则与此对应的条件表达式为真.
(nt: OAM 与 end-to-end OAM F4 在上文已有描述, 可搜索'oamf4s'来定位)

oam
如果数据包为ATM数据包, 则与此对应的条件表达式为真. 对于Solaris 操作系统上的SunATM设备 , 如果数据包为ATM数据包
并且是 end-to-end 或 segment OAM F4 信元(VPI=0 并且 VCI=3 或者 VCI=4), 则与此对应的条件表达式为真.
(nt: 此选项与oamf4重复, 需确认)

metac
如果数据包为ATM数据包, 则与此对应的条件表达式为真. 对于Solaris 操作系统上的SunATM设备 , 如果数据包为ATM数据包
并且是来自'元信令线路'(nt: VPI=0 并且 VCI=1, '元信令线路', meta signaling circuit, 具体含义未知, 需补充),
则与此对应的条件表达式为真.

bcc
如果数据包为ATM数据包, 则与此对应的条件表达式为真. 对于Solaris 操作系统上的SunATM设备 , 如果数据包为ATM数据包
并且是来自'广播信令线路'(nt: VPI=0 并且 VCI=2, '广播信令线路', broadcast signaling circuit, 具体含义未知, 需补充),
则与此对应的条件表达式为真.

sc
如果数据包为ATM数据包, 则与此对应的条件表达式为真. 对于Solaris 操作系统上的SunATM设备 , 如果数据包为ATM数据包
并且是来自'信令线路'(nt: VPI=0 并且 VCI=5, '信令线路', signaling circuit, 具体含义未知, 需补充),
则与此对应的条件表达式为真.

ilmic
如果数据包为ATM数据包, 则与此对应的条件表达式为真. 对于Solaris 操作系统上的SunATM设备 , 如果数据包为ATM数据包
并且是来自'ILMI线路'(nt: VPI=0 并且 VCI=16, 'ILMI', Interim Local Management Interface , 可理解为
基于SNMP(简易网络管理协议)的用于网络管理的接口)
则与此对应的条件表达式为真.

connectmsg

如果数据包为ATM数据包, 则与此对应的条件表达式为真. 对于Solaris 操作系统上的SunATM设备 , 如果数据包为ATM数据包
并且是来自'信令线路'并且是Q.2931协议中规定的以下几种消息: Setup, Calling Proceeding, Connect,
Connect Ack, Release, 或者Release Done. 则与此对应的条件表达式为真.
(nt: Q.2931 为ITU(国际电信联盟)制定的信令协议. 其中规定了在宽带综合业务数字网络的用户接口层建立, 维护, 取消
网络连接的相关步骤.)

metaconnect
如果数据包为ATM数据包, 则与此对应的条件表达式为真. 对于Solaris 操作系统上的SunATM设备 , 如果数据包为ATM数据包
并且是来自'元信令线路'并且是Q.2931协议中规定的以下几种消息: Setup, Calling Proceeding, Connect,
Connect Ack, Release, 或者Release Done. 则与此对应的条件表达式为真.

expr relop expr
如果relop 两侧的操作数(expr)满足relop 指定的关系, 则与此对应的条件表达式为真.
relop 可以是以下关系操作符之一: >, <, <=, =, !=.
expr 是一个算术表达式. 此表达式中可使用整型常量(表示方式与标准C中一致), 二进制操作符(+, -, *, /, &, |,
<<, >>), 长度操作符, 以及对特定数据包中数据的引用操作符. 要注意的是, 所有的比较操作都默认操作数是无符号的,
例如, 0x80000000 和 0xffffffff 都是大于0的(nt: 对于有符号的比较, 按照补码规则, 0xffffffff
会小于0). 如果要引用数据包中的数据, 可采用以下表达方式:
proto [expr : size]

proto 的取值可以是以下取值之一:ether, fddi, tr, wlan, ppp, slip, link, ip, arp, rarp,
tcp, udp, icmp, ip6 或者 radio. 这指明了该引用操作所对应的协议层.(ether, fddi, wlan,
tr, ppp, slip and link 对应于数据链路层, radio 对应于802.11(wlan,无线局域网)某些数据包中的附带的
"radio"头(nt: 其中描述了波特率, 数据加密等信息)).
要注意的是, tcp, udp 等上层协议目前只能应用于网络层采用为IPv4或IPv6协议的网络(此限制会在tcpdump未来版本中
进行修改). 对于指定协议的所需数据, 其在包数据中的偏移字节由expr 来指定.

以上表达中size 是可选的, 用来指明我们关注那部分数据段的长度(nt:通常这段数据
是数据包的一个域)， 其长度可以是1, 2, 或4个字节. 如果不给定size, 默认是1个字节. 长度操作符的关键字为len,
这代码整个数据包的长度.

例如, 'ether[0] & 1 != 0' 将会使tcpdump 抓取所有多点广播数据包.(nt: ether[0]字节的最低位为1表示
数据包目的地址是多点广播地址). 'ip[0] & 0xf != 5' 对应抓取所有带有选项的
IPv4数据包. 'ip[6:2] & 0x1fff = 0'对应抓取没被破碎的IPv4数据包或者
其片段编号为0的已破碎的IPv4数据包. 这种数据检查方式也适用于tcp和udp数据的引用,
即, tcp[0]对应于TCP 头中第一个字节, 而不是对应任何一个中间的字节.

一些偏移以及域的取值除了可以用数字也可用名字来表达. 以下为可用的一些域(协议头中的域)的名字: icmptype (指ICMP 协议头
中type域), icmpcode (指ICMP 协议头code 域), 以及tcpflags(指TCP协议头的flags 域)

以下为ICMP 协议头中type 域的可用取值:
icmp-echoreply, icmp-unreach, icmp-sourcequench, icmp-redirect, icmp-echo, icmp-routeradvert,
icmp-routersolicit, icmp-timx-ceed, icmp-paramprob, icmp-tstamp, icmp-tstampreply,
icmp-ireq, icmp-ireqreply, icmp-maskreq, icmp-maskreply.

以下为TCP 协议头中flags 域的可用取值:tcp-fin, tcp-syn, tcp-rst, tcp-push,
tcp-ack, tcp-urg.
```



 