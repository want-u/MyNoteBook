# ImagePullBackOff 错误处理

https://www.cnblogs.com/digdeep/p/12319340.html

![img](https://img2018.cnblogs.com/i-beta/699877/202002/699877-20200216225352732-1436300669.png)

 kubectl create -f nginx-deployment.yaml 执行之后，kubectl get pods 一致出现 ImagePullBackOff，一直无法执行成功。

yaml文件内容：

![img](https://img2018.cnblogs.com/i-beta/699877/202002/699877-20200216230511068-511818206.png)

调查错误原因，在 master 服务器上面分别执行命令: **kubectl describe pod** ，查看三个nginx实例的报错信息：

![img](https://img2018.cnblogs.com/i-beta/699877/202002/699877-20200216225653814-1246740654.png)

 ![img](https://img2018.cnblogs.com/i-beta/699877/202002/699877-20200216225736394-2075632446.png)

 第一个nginx正常，在看第二个：

![img](https://img2018.cnblogs.com/i-beta/699877/202002/699877-20200216225810358-1581215302.png)

 可以看到报错信息：

Warning Failed         47m         kubelet, 192.168.118.213 **Failed to pull image "nginx:1.10": rpc error: code = Unknown desc = Error response from daemon: Get https://registry-1.docker.io/v2/: net/http: request canceled (Client.Timeout exceeded while awaiting headers)**

原因是第一条服务器配置了docker的国内阿里云镜像，而后面两台服务器忘记了给docker配置国内镜像。

![img](https://img2018.cnblogs.com/i-beta/699877/202002/699877-20200216225935699-1327332140.png)

 配置上国内镜像，然后执行：

systemctl daemon-reload
systemctl restart docker

就可以了：

![img](https://img2018.cnblogs.com/i-beta/699877/202002/699877-20200216230051740-1005565069.png)

 三台nginx全部是Running！

搞定。 