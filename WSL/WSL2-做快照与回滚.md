# WSL2-做快照与回滚

https://blog.csdn.net/weixin_43425561/article/details/115765148

[toc]

前言
提示：阿里云ECS服务器上对磁盘的快照与回滚能否用在WSL2上呢？下面我们来实践一下，以下例子是基于WSL2，同理应适用于WSL。

提示：以下是本篇文章正文内容，下面案例可供参考

一、查看已安装的系统
在win10终端或powershell里面键入：

```
wsl -l -v
```


示例：为Ubuntu-18.04系统做快照

二、做快照
代码如下（示例）：

```
wsl --export Ubuntu-18.04 d:\WslDir\wsl-ubuntu18.04.tar
```


三.回滚
1.注销当前系统

```
wsl --unregister Ubuntu-18.04
```


2.回滚

```
wsl --import Ubuntu-18.04 d:\wsl d:\WslDir\wsl-ubuntu18.04.tar --version 2
```

3.设置默认登陆用户为安装时用户名

```
ubuntu1804 config --default-user luoxian
```

如果是ubuntu20.04，命令ubuntu1804改为ubuntu2004即可；USERNAME是登录用户名称，如Raymond

总结

以上就是在WSL2做快照与回滚的步骤，很适合在本地反复开发测试。上述方法可做成快照和回滚两个脚本，就会更方便了。