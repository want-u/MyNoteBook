# 离线安装gcc环境

- https://www.cnblogs.com/lgx211/p/12652197.html
- https://segmentfault.com/a/1190000040003049

[toc]

### 下载

方式1：如果有网的虚拟机还没有安装，可以直接

```shell
yum install --downloadonly --downloaddir=/root/soft/gcc gcc

yum install --downloadonly --downloaddir=/root/soft/gcc++ gcc-c++
```

方式2：如果有网的虚拟机已经安装过，可以

```shell
yum -y install yum-utils

yumdownloader --resolve --destdir=/root/soft/gcc gcc
yumdownloader --resolve --destdir=/root/soft/gcc++ gcc-c++
```

可以看到，gcc的依赖如下

![gcc_lgx211](https://img-blog.csdnimg.cn/20200407110423699.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80Mzc1MDIxMg==,size_16,color_FFFFFF,t_70)
gcc++的依赖如下
![gcc++_lgx211](https://img-blog.csdnimg.cn/2020040711050839.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80Mzc1MDIxMg==,size_16,color_FFFFFF,t_70)
方式3：看到我给的依赖包，直接网上下载好，这里是地址，可以在里面找到

http://mirrors.163.com/centos/6/os/x86_64/Packages/

http://mirrors.aliyun.com/centos/7/os/x86_64/Packages/

安装所需要的包

```
#图中gcc所示需要的依赖
cpp-4.8.5-39.el7.x86_64.rpm
gcc-4.8.5-39.el7.x86_64.rpm
glibc-devel-2.12-1.212.el6.x86_64.rpm
glibc-headers-2.17-292.el7.x86_64.rpm 
kernel-headers-3.10.0-1062.el7.x86_64.rpm
libmpc-1.0.1-3.el7.x86_64.rpm
mpfr-3.1.1-4.el7.x86_64.rpm
```

### 上传

```shell
put D:\AppStore\Linux\gcc\cpp-4.8.5-39.el7.x86_64.rpm  /root
put D:\AppStore\Linux\gcc\gcc-4.8.5-39.el7.x86_64.rpm  /root
put D:\AppStore\Linux\gcc\glibc-devel-2.12-1.212.el6.x86_64.rpm  /root
put D:\AppStore\Linux\gcc\glibc-headers-2.17-292.el7.x86_64.rpm  /root
put D:\AppStore\Linux\gcc\kernel-headers-3.10.0-1062.el7.x86_64.rpm  /root 
put D:\AppStore\Linux\gcc\libmpc-1.0.1-3.el7.x86_64.rpm  /root 
put D:\AppStore\Linux\gcc\mpfr-3.1.1-4.el7.x86_64.rpm  /root  
```

### 安装

我这就强制安装了，因为逐个安装，很容易因为递进依赖，而报一堆错，需要下载一堆特别多的包。

```shell
rpm  -ivh  *.rpm --nodeps --force
```

### 检查

```shell
gcc -v
```