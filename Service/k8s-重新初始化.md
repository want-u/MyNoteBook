# k8s-重新初始化

[k8s初始化失败，重新初始化 - 灰蓝色的白云梦想 - 博客园 (cnblogs.com)](https://www.cnblogs.com/lina-2159/p/14061115.html)

[toc]

## k8s初始化失败，重新初始化

![img](https://img2020.cnblogs.com/blog/1752532/202011/1752532-20201130141332998-486442289.png)

## 第一种 : 手动删除

### 1. 删除/etc/kubernetes/文件夹下的所有文件

### 2. 删除$HOME/.kube文件夹

### 3. 删除/var/lib/etcd文件夹

```
[root@k8s-master ~]# rm -rf /etc/kubernetes/*
[root@k8s-master ~]# rm -rf ~/.kube/*
[root@k8s-master ~]# rm -rf /var/lib/etcd/*
```

### 4、停用上图所列的端口号



```
[root@k8s-master ~]# lsof -i :6443|grep -v "PID"|awk '{print "kill -9",$2}'|sh
[root@k8s-master ~]# lsof -i :10251|grep -v "PID"|awk '{print "kill -9",$2}'|sh
[root@k8s-master ~]# lsof -i :10252|grep -v "PID"|awk '{print "kill -9",$2}'|sh
[root@k8s-master ~]# lsof -i :10250|grep -v "PID"|awk '{print "kill -9",$2}'|sh
[root@k8s-master ~]# lsof -i :2379|grep -v "PID"|awk '{print "kill -9",$2}'|sh
[root@k8s-master ~]# lsof -i :2380|grep -v "PID"|awk '{print "kill -9",$2}'|sh
```



## 第二种 : kubectl命令清除环境

```
[root@k8s-master ~]# kubeadm reset
```

![img](https://img2020.cnblogs.com/blog/1752532/202111/1752532-20211123142405471-1000635770.png)

 