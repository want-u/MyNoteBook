# locate

linux中locate命令

可以快速定位我们需要查找的文件

但是在yum中，locate的安装包名为 mlocate（yum list | grep locate可以查看）

安装方法：

```
yum -y install mlocate
```


安装完毕后就可以使用了

```
updatedb 
locate 文件名
```


