# github-加速访问

通过修改系统hosts文件的办法，绕过国内dns解析，直接访问GitHub的CDN节点，从而达到加速的目的



### 1. 获取GitHub官方CDN地址

```
第一种方式：

打开https://www.ipaddress.com/

查询以下三个链接的DNS解析地址

1. github.com
2. assets-cdn.github.com
3. github.global.ssl.fastly.net
```

```
第二种方式：

使用nslookup命令查询dns解析

nslookup github.com
nslookup assets-cdn.github.com
nslookup github.global.ssl.fastly.net

```

记录下查询到的IP地址

### 2. 修改系统Hosts文件

打开系统hosts文件(需管理员权限)。
路径：C:\Windows\System32\drivers\etc

```
13.229.188.59	github.com
185.199.108.153	assets-cdn.github.com
104.244.46.244	github.global.ssl.fastly.net
```

### 3. 刷新系统DNS缓存

Windows+X 打开系统命令行（管理员身份）或powershell

 手动刷新系统DNS缓存

```
ipconfig /flushdns
```





