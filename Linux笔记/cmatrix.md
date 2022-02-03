# cmatrix


```
centos7安装代码雨

https://www.jianshu.com/p/3fe67d85850b

点我进网盘下载源码包
提取码：4mim
```



```
解压不解释：
tar xf cmatrix-1.2a.tar.gz
编译安装也不用说
./configure
make && make install

# 报错：make: *** [cmatrix.o] Error 1
# yum search curses.h
# yum install ncurses-devel.x86_64 -y

安装完成后直接运行
cmatrix
```

