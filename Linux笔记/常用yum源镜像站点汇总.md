# 常用yum源镜像站点汇总

https://www.itbkz.com/1105.html

[toc]

## 1.注意备份

```
mkdir /etc/yum.repos.d/bak
mv /etc/yum.repos.d/* /etc/yum.repos.d/bak
```
```
# 拓展源
yum -y install epel-release
```


更换[yum源](https://www.itbkz.com/tag/yum源)时请先备份系统原有[yum源](https://www.itbkz.com/tag/yum源)

## 2.阿里云镜像站

官方网站：[点击到达](https://developer.aliyun.com/mirror)

### 2.1.基础源

#### 2.1.1.CentOS8.X

```
curl -o /etc/yum.repos.d/CentOS-8.repo http://mirrors.aliyun.com/repo/Centos-8.repo
```

#### 2.1.2.CentOS7.X

```
curl -o /etc/yum.repos.d/Centos-7.repo http://mirrors.aliyun.com/repo/Centos-7.repo
```

#### 2.1.3.CentOS6.X

```
curl -o /etc/yum.repos.d/Centos-6.repo http://mirrors.aliyun.com/repo/Centos-6.repo
```

### 2.2.epel源

#### 2.1.2.CentOS7.X

```
curl -o /etc/yum.repos.d/epel-7.repo http://mirrors.aliyun.com/repo/epel-7.repo
```

#### 2.1.3.CentOS6.X

```
curl -o /etc/yum.repos.d/epel-6.repo http://mirrors.aliyun.com/repo/epel-6.repo
```

## 3.清华大学镜像站

官方网站：[点击到达](https://mirror.tuna.tsinghua.edu.cn/)

## 4.科学技术大学

官方网站：[点击到达](https://mirrors.ustc.edu.cn/)

## 5.网易镜像站

官方网站：[点击到达](http://mirrors.163.com/)

### 2.1.基础源

#### 2.1.2.CentOS7.X

```
curl -o /etc/yum.repos.d/CentOS7-Base-163.repo http://mirrors.163.com/.help/CentOS7-Base-163.repo
```

#### 2.1.3.CentOS6.X

```
curl -o /etc/yum.repos.d/CentOS6-Base-163.repo http://mirrors.163.com/.help/CentOS6-Base-163.repo
```

#### 5.1.3.CentOS5.X

```
curl -o /etc/yum.repos.d/CentOS5-Base-163.repo http://mirrors.163.com/.help/CentOS5-Base-163.repo
```

## 6.Nginx官方源

官方网站：[点击到达](http://nginx.org/)
下载页面：[点击到达](http://nginx.org/packages/)

## 7.Mysql官方源

官方网站：[点击到达](https://www.mysql.com/)
下载页面：[点击到达](https://dev.mysql.com/downloads/mysql/)

## 8.Php官方源

官方网站：[点击到达](https://www.php.net/)
下载页面：[点击到达](http://mirror.webtatic.com/yum/)

## 9.Zabbix官方源

官方网站：[点击到达](https://www.zabbix.com/)
下载页面：[点击到达](https://www.zabbix.com/download)

## 10.微软源

### 10.1.CentOS7.X

```
方法1：rpm -Uvh https://packages.microsoft.com/config/centos/7/packages-microsoft-prod.rpm方法2：yum install -y https://packages.microsoft.com/config/centos/7/packages-microsoft-prod.rpm方法3：cat >>/etc/yum.repos.d/microsoft-prod.repo<<EOF[packages-microsoft-com-prod]name=packages-microsoft-com-prodbaseurl=https://packages.microsoft.com/centos/7/prodenabled=1gpgcheck=1gpgkey=https://packages.microsoft.com/keys/microsoft.ascsslverify=1EOF
```