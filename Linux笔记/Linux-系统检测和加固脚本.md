# Linux-系统检测和加固脚本

[【分享】非常全面的CentOS7系统安全检测和加固脚本 - 云+社区 - 腾讯云 (tencent.com)](https://cloud.tencent.com/developer/article/1760519)

- Git: https://github.com/xiaoyunjie/Shell_Script
- CentOS_Check_Script.sh: https://raw.githubusercontent.com/xiaoyunjie/Shell_Script/master/Check_script/bash/CentOS_Check_Script.sh

[toc]

## Linux系统进行一键检测和一键加固

**主要是为了Linux系统的安全，通过脚本对Linux系统进行一键检测和一键加固**

### Check_Script

```bash
#包含2个文件
CentOS_Check_Script.sh
README.txt
```

**操作说明**

```bash
#执行CentOS-Check_Script.sh脚本文件进行检查,命令格式如下
sudo sh CentOS_Check_Script.sh | tee check_`date +%Y%m%d_%H%M%S`.txt
```

**检查说明**

此脚本是按三级等保要求，编写的一键检查脚本，此脚本只适合linux分支中的redhat、centos，运行脚本将结果输出到自定义的文件中，脚本结果需要人为检查。

此检查脚本包含以下几块内容：
- 系统基本信息
- 资源使用情况
- 系统用户情况
- 身份鉴别安全
- 访问控制安全
- 安全审计
- 剩余信息保护
- 入侵防范安全
- 恶意代码防范
- 资源控制安全


----

### Protective_Script

```bash
#包含2个文件
CentOS_Protective_Script.sh
README.txt
```

**操作说明**
```bash
#执行CentOS_Protective_Script.sh脚本文件进行加固,命令格式如下
sudo sh CentOS_Protective_Script.sh
#执行完成后,请按脚本提示重启相应服务
```

**功能说明**
-  一键进行全部加固
-  设置密码复杂度
-  添加openroot账号
-  禁止root远程登入
-  设置history保存行数以及命令时间，设置窗口超时时间
-  更改SSH端口
-  登入失败处理
-  还原配置文件

## [linux系统加固 - Hydraxx - 博客园 (cnblogs.com)](https://www.cnblogs.com/Hydraxx/p/10757155.html)

### 常规安全配置


重要目录和文件权限：合理配置重要目录和文件权限，增强安全性

检查方法：使用ls -l命令查看目录和文件的权限设置情况

加固方法：对于重要目录，建议执行类似操作 chmod -R 750 /etc/rc.d/init.d/* 这样除了root可读，写，执行这个目录下的脚本


### umask值

操作目的：设置默认的umask值，增强安全性

检查方法：使用umask查看umask值是否为027

加固方法：使用命令 vim /etc/profile 添加umask 027 即新创建的文件属主读写执行权限，同组用户读和执行权限，其他用户无权限。使用命令umask 027应用设置

 
### bash历史命令

操作目的：设置bash保留历史命令的条数

检查方法：使用 cat /etc/profile|查看HISTSIZE 和 HISTFILESIZE 查看保留历史命令的条数

加固方法：vim /etc/profile 修改配置文件

HISTSIZE=5 即保留5条命令
 

### 登陆超时

操作目的：设置系统登陆后，连接超时时间，增强安全性

检查方法：使用命令 cat /etc/profile | grep TMOUT 查看tmout是否被设置

加固方法：vim /etc/profile 修改配置文件，把TMOUT设置为180 即超时时间为3分钟


### root路径

操作目的：检查系统root用户环境变量path设置中是否包含 “ . ”

检查方法：root用户环境变量path中不应包含当前目录 “ . ”，以root身份执行命令：#echo $PATH /usr/local:/sbin:/bin:/usr/bin:/root/bin:.

加固方法：vim /etc/profile 修改echo $PATH后面的路径

 

### 禁用无用账号

操作目的：减少无用账号，降低风险

检查方法：使用 cat /etc/passwd 查看口令文件，确认不必要的账号。不必要的账号，shell应该sbin/nologin

加固方法：使用passwd -l user 锁定不必要的账号
 

### 账号策略

操作目的：防止口令暴力破解，降低风险

检查方法：使用命令 cat /etc/pam.d/system-auth 查看配置文件

加固方法：设置连续输错3次密码账号锁定2小时，vim /etc/pam.d/system-auth 修改配置文件

auth required pam_tally.so onerr=fail deny=3 unlock_time=7200

 

### 检查特殊账号

操作目的：查看空口令和root权限的账号

检查方法：使用命令 awk -F:'($3==0)'/etc/passwd 查看uid为0的账号

　　　　　　　　   awk -F:'($2=="")'/etc/shadow 查看空口令账号

加固方法：使用命令 passwd user 为空口令账号设定密码，uid为0的账号应该只有root 使用usermod -u uid user


### 口令周期策略

操作目的：加强口令的复杂度，降低被猜解的可能性

检查方法：使用命令 cat /etc/login.defs | grep PASS 和 cat /etc/pam.d/system-auth 查看密码策略

加固方法：vim /etc/login.defs 修改配置文件

PASS_MAX_DAYS  60 新建用户的密码最长使用天数
PASS_MIN_DAYS  0   新建用户的密码最短使用天数
PASS_WARN_AGE  7  新建用户的密码到期提前提醒天数

或 chage -m 0 -M 30 -E 2020-01-01- -W user（将user用户的密码长度设为30天，最短提醒天数为0 账号2020年1月1号过期，过期前7天警告用户）


### 口令复杂度策略

操作目的：加强口令的复杂程度，降低被猜解的可能性

检查方法：使用命令 cat /etc/pam.d/system-auth | grep pam_cracklib.so 查看密码复杂度策略设置

加固方法：建议在/etc/pam.d/system-auth文件中配置：password requisite pam_cracklib.so difok=3 minlen=8 ucredit=-1 lcredit=-1 dcredit=-1 至少八位。包含一位大写字母，一位小写字母和一位数字


### 限制root远程登陆

操作目的：限制root远程telnet登陆

检查方法：检查方法 `cat /etc/ssh/sshd_config 查看PermitRootLogin是否为no`

加固方法：编辑文件 `vim /etc/ssh/sshd_config`

　　　　　　　`PermitRootLogin no不允许root登陆`

　　　　　　   Protocol 2 修改ssh使用的协议版本

　　　　　　   MaxAuthTries 3 修改允许密码错误次数

​            或echo "tty1" > /etc/securetty

​            chmod 700 /root

 

### 限制能够su为root的用户

操作目的：检查是否使用pam认证模块禁止wheel组之外的用户su为root

```
检查方法：cat /etc/pam.d/su,查看是否有auth required /lib/security/pam_wheel.so这样的配置条目
```

加固方法：`vim /etc/pam.d/su`

```
　　　　 在头部添加 auth required/lib/security/pam_wheel.so group=wheel
```

 

### 检查Grub/Lilo密码

操作目的：查看系统引导管理器是否设置密码

检查方法： `cat /etc/grub.conf|grep password  查看grub是否设置密码`

```
 　　　　cat /etc/lilo.conf|grep password  查看lilo是否设置密码
```

加固方法：为grub或lilo设置密码（风险：etc/grub.conf通常会链接到/boot/grub/grub.conf）

​         vim /etc/grub.conf 添加一行 password --md5 密码

​         vim /etc/lilo.conf password=密码


### 弱口令审计

操作目的：检查系统弱口令

检查方法：john /etc/shadow --single john /etc/shadow --wordlist=pass.dic

加固方法：使用passwd 用户 命令为用户设置复杂的密码


### NFS共享

使用exportfs查看NFS输出的共享目录。使用vim /etc/exports编辑配置文件，删除不必要的共享
 

### 限制crtl+alt+del命令

操作目的：防止使用crtl+alt+del重启系统

检查方法：cat /etc/init/control-alt-delete.conf

加固方法：vim /etc/init/control-alt-delete.conf 注释两行代码

### 禁ping

echo 0 > /proc/sys/net/ipv4/icmp_echo_igore_all

 

### 后门检测工具

Rootkit Hunter(http://rkhunter.sourceforge.net/)

解压后安装 ./installer.sh --layout default --install

rkhunter --check 检测命令（自动检测）