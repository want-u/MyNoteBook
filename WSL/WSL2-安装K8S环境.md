# WSL2-安装K8S环境

- [在WSL2上部署标准k8s集群并使用Prometheus监控spring cloud服务 - 简书 (jianshu.com)](https://www.jianshu.com/p/d1df238ff0e1)
- [Kubernetes 使用kubeadm创建集群 - 云+社区 - 腾讯云 (tencent.com)](https://cloud.tencent.com/developer/article/1882694)
- [在Ubuntu 20.04上安装K8S环境 - Fzu_吴鹏辉 - 博客园 (cnblogs.com)](https://www.cnblogs.com/wwph/p/14203626.html)
- [kube-apiserver启动基本参数详解 - 简书 (jianshu.com)](https://www.jianshu.com/p/d09d5e570fbb)
- [k8s安装遇到错误： failed to load Kubelet config file /var/lib/kubelet/config.yaml, error failed to read kub_yuxuan89814的专栏-程序员ITS201_/var/lib/kubelet/config.yaml - 程序员ITS201](https://its201.com/article/yuxuan89814/118220640)
- [k8s初始化过程中，failed with error: Get “http://localhost:10248/healthz“_稻草人-CSDN博客](https://blog.csdn.net/sinat_32900379/article/details/122135698)

[toc]

本篇文章将教会大家如何在 WSL2 部署一个标准版的 k8s 集群

环境：

- 安装 WSL (操作系统必须是 Windows 10 build 2004 以后的版本)
- 安装 docker 环境

- C盘 剩余空间最好在 64G 以上
- 内存最好是 16G 以上，8g 内存运行 k8s 集群后，就没有剩余内存



## 部署前准备

### 禁用 swap

因为 kubelet 不支持 swap 内存，因此需要通过设置 wslconfig 禁用 swap 。

在 用户根目录 创建 .wslconfig 文件，例如： 'C:\\Users\\luoxian\\.wslconfig' ，添加以下内容：

```
[wsl2]
swap=0 # 关闭swap

```

在 powershell 或 cmd 运行以下命令，关闭 wsl，再点击开始菜单 Ubuntu 图标，即完成重启 WSL。

```
wsl --shutdown
# 直接执行wsl，亦可以启动
```

### 开启systemctl[临时]

安装 daemonize

```
sudo apt-get install daemonize
```

执行以下两句命令开启

```
sudo daemonize /usr/bin/unshare --fork --pid --mount-proc /lib/systemd/systemd --system-unit=basic.target
exec sudo nsenter -t $(pidof systemd) -a su - $LOGNAME
```

验证 systemctl

```
❯ systemctl status docker
```

### 修改docker配置文件

使用 root 权限创建 '/etc/docker/daemon.json' 文件，并填写以下内容：

```
{
    "exec-opts": ["native.cgroupdriver=systemd"],
    "log-driver": "json-file",
    "log-opts": {"max-size": "50m", "max-file": "3"},
    "registry-mirrors": ["https://8p2ftmks.mirror.aliyuncs.com", "http://hub-mirror.c.163.com"]
}
```

```
# 不添加此参数，会有如下报错 "exec-opts":["native.cgroupdriver=systemd"] 
[WARNING IsDockerSystemdCheck]: detected "cgroupfs" as the Docker cgroup driver. The recommended driver is "systemd". Please follow the guide at https://kubernetes.io/docs/setup/cri/
```

重启 docker

```
sudo systemctl daemon-reload
sudo systemctl start docker
```

## 安装k8s工具

### 镜像源[√]

这里使用了阿里云的镜像源安装，也可以直接用官方源安装

```
sudo apt-get update && sudo apt-get install -y ca-certificates curl software-properties-common apt-transport-https curl
curl -s https://mirrors.aliyun.com/kubernetes/apt/doc/apt-key.gpg | sudo apt-key add -
sudo tee /etc/apt/sources.list.d/kubernetes.list <<EOF 
deb https://mirrors.aliyun.com/kubernetes/apt/ kubernetes-xenial main
EOF

sudo apt-get update
sudo apt-get install -y kubelet kubeadm kubectl
sudo apt-mark hold kubelet kubeadm kubect
```

### 官方源

添加 `apt-key.gpg` 步骤可能需要梯子，可以通过手动下载 [apt-key.gpg](https://links.jianshu.com/go?to=https%3A%2F%2Fpackages.cloud.google.com%2Fapt%2Fdoc%2Fapt-key.gpg)，再用这个命令 `cat /mnt/c/Users/huang/Downloads/apt-key.gpg | sudo apt-key add -` ，`/mnt/c/` 表示挂载 `C盘`，后面加上 `apt-key.gpg` 所在目录

```sh
sudo apt-get update && sudo apt-get install -y apt-transport-https curl
# 添加apt-key.gpg步骤可能需要梯子
curl -s https://packages.cloud.google.com/apt/doc/apt-key.gpg | sudo apt-key add -
cat <<EOF | sudo tee /etc/apt/sources.list.d/kubernetes.list
deb https://apt.kubernetes.io/ kubernetes-xenial main
EOF
sudo apt-get update
sudo apt-get install -y kubelet kubeadm kubectl
sudo apt-mark hold kubelet kubeadm kubectl
```

## 初始化

kubeadm init 命令详解：

```shell
kubeadm init [flags]
      --apiserver-advertise-address string   设置 apiserver 绑定的 IP. 
                                             -- 这个参数就是master主机的IP地址，例如我的Master主机的IP是：192.168.181.131
      --apiserver-bind-port int32            设置apiserver 监听的端口. (默认 6443)
      --apiserver-cert-extra-sans strings    api证书中指定额外的Subject Alternative Names (SANs) 可以是IP 也可以是DNS名称。 证书是和SAN绑定的。
      --cert-dir string                      证书存放的目录 (默认 "/etc/kubernetes/pki")
      --certificate-key string               kubeadm-cert secret 中 用于加密 control-plane 证书的key
      --config string                        kubeadm 配置文件的路径.
      --cri-socket string                    CRI socket 文件路径，如果为空 kubeadm 将自动发现相关的socket文件; 只有当机器中存在多个 CRI  socket 或者 存在非标准 CRI socket 时才指定.
      --dry-run                              测试，并不真正执行;输出运行后的结果.
      --feature-gates string                 指定启用哪些额外的feature 使用 key=value 对的形式。
  -h, --help                                 帮助文档
      --ignore-preflight-errors strings      忽略前置检查错误，被忽略的错误将被显示为警告. 例子: 'IsPrivilegedUser,Swap'. Value 'all' ignores errors from all checks.
      --image-repository string              选择拉取 control plane images 的镜像repo (default "k8s.gcr.io").
                                             -- 镜像仓库地址，由于国外地址无法访问，故使用的阿里云仓库地址：registry.aliyuncs.com/google_containers
      --kubernetes-version string            选择K8S版本. (default "stable-1")
      --node-name string                     指定node的名称，默认使用 node 的 hostname.
      --pod-network-cidr string              指定 pod 的网络， control plane 会自动将 网络发布到其他节点的node，让其上启动的容器使用此网络
                                             -- flannel : 需要在kubeadm init 时设置 --pod-network-cidr=10.244.0.0/16
      --service-cidr string                  指定service 的IP 范围. (default "10.96.0.0/12")
      --service-dns-domain string            指定 service 的 dns 后缀, e.g. "myorg.internal". (default "cluster.local")
      --skip-certificate-key-print           不打印 control-plane 用于加密证书的key.
      --skip-phases strings                  跳过指定的阶段（phase）
      --skip-token-print                     不打印 kubeadm init 生成的 default bootstrap token 
      --token string                         指定 node 和control plane 之间，简历双向认证的token ，格式为 [a-z0-9]{6}\.[a-z0-9]{16} - e.g. abcdef.0123456789abcdef
      --token-ttl duration                   token 自动删除的时间间隔。 (e.g. 1s, 2m, 3h). 如果设置为 '0', token 永不过期 (default 24h0m0s)
      --upload-certs                         上传 control-plane 证书到 kubeadm-certs Secret.
```

### 执行初始化

```
# 执行初始化，master节点执行

kubeadm init --image-repository registry.aliyuncs.com/google_containers --pod-network-cidr 10.244.0.0/16
# --image-repository registry.aliyuncs.com/google_containers 
# --pod-network-cidr 10.244.0.0/16

...
# 初始化成功后会显示如下结果
Your Kubernetes control-plane has initialized successfully!
...

# 初始化失败可查看提示 或查看日志
# journalctl -u kubelet.service

# 重新初始化
# kubeadm reset
```


### 初始化后配置
```
mkdir -p $HOME/.kube
sudo cp -i /etc/kubernetes/admin.conf $HOME/.kube/config
sudo chown $(id -u):$(id -g) $HOME/.kube/config
```


### 查看k8s信息
```
# 获取节点相应服务的信息
❯ kubectl get nodes
NAME              STATUS   ROLES                  AGE   VERSION
laptop-jok07hrh   Ready    control-plane,master   10m   v1.23.3

# 查看pod，指定namespace为 kube-system
❯ kubectl get pods -n kube-system
NAME                                      READY   STATUS              RESTARTS   AGE
coredns-6d8c4cb4d-2kd5g                   0/1     ContainerCreating   0          10m
coredns-6d8c4cb4d-7r2c6                   0/1     ContainerCreating   0          10m
etcd-laptop-jok07hrh                      1/1     Running             1          11m
kube-apiserver-laptop-jok07hrh            1/1     Running             1          11m
kube-controller-manager-laptop-jok07hrh   1/1     Running             4          11m
kube-proxy-fw798                          1/1     Running             0          10m
kube-scheduler-laptop-jok07hrh            1/1     Running             4          11m
```
### 安装 Pod 网络 - flannel

```
# 安装 Pod 网络 - flannel
❯ kubectl apply -f https://raw.githubusercontent.com/coreos/flannel/master/Documentation/kube-flannel.yml

❯ kubectl get pods -n kube-system
NAME                                      READY   STATUS    RESTARTS   AGE
coredns-6d8c4cb4d-2kd5g                   1/1     Running   0          13m
coredns-6d8c4cb4d-7r2c6                   1/1     Running   0          13m
etcd-laptop-jok07hrh                      1/1     Running   1          13m
kube-apiserver-laptop-jok07hrh            1/1     Running   1          13m
kube-controller-manager-laptop-jok07hrh   1/1     Running   4          13m
kube-flannel-ds-9fmvd                     1/1     Running   0          2m6s
kube-proxy-fw798                          1/1     Running   0          13m
kube-scheduler-laptop-jok07hrh            1/1     Running   4          13m
```

### 查看节点加入集群的命令
```
❯ kubeadm token create --print-join-command
kubeadm join 172.27.237.245:6443 --token qfufs7.m7zjysuvuf9jcyzw --discovery-token-ca-cert-hash sha256:9fc5bdbf45024aa0031e7a029c4b1909ffbf200f286910a10c67cdcc931322c1

```

### 部署应用

```
# 部署应用：nginx
[root@k8s-master ~]# kubectl create deployment test1 --image=nginx

# 查看部署的应用
[root@k8s-master ~]# kubectl get deployments.apps
NAME    READY   UP-TO-DATE   AVAILABLE   AGE
test1   1/1     1            1           10m

# 查看pod
❯ kubectl get pods -o wide
NAME                     READY   STATUS    RESTARTS   AGE   IP           NODE              NOMINATED NODE   READINESS GATES
test1-7f655bfbf5-sjsrk   1/1     Running   0          31m   10.244.0.4   laptop-jok07hrh   <none>           <none>

# 查看pod日志
kubectl logs test1-7f655bfbf5-sjsrk

# 查看pod信息
kubectl describe pod test1-7f655bfbf5-sjsrk

# 删除应用
# kubectl delete deployments.apps test1
```

### 删除Master节点污点

单机情况下可能遇到的问题

**Warning  FailedScheduling  4s (x3 over 5s)  default-scheduler  0/1 nodes are available: 1 node(s) had taints that the pod didn't tolerate.**

由于Master节点默认带有污点 `node-role.kubernetes.io/master:NoSchedule` ，所以无法直接部署容器到Master节点上

```
❯ kubectl get nodes
NAME              STATUS   ROLES                  AGE   VERSION
laptop-jok07hrh   Ready    control-plane,master   23m   v1.23.3

❯ kubectl taint node laptop-jok07hrh node-role.kubernetes.io/master-
node/laptop-jok07hrh untainted

# 查看pod信息，-o wide显示节点信息
❯ kubectl get pods -o wide
NAME                     READY   STATUS    RESTARTS   AGE     IP           NODE              NOMINATED NODE   READINESS GATES
test1-7f655bfbf5-sjsrk   1/1     Running   0          8m11s   10.244.0.4   laptop-jok07hrh   <none>           <none>

```

### 发布服务

```
# 发布服务
❯ kubectl expose deployment test1 --type=NodePort --port=80
service/test1 exposed

# 查看服务 kubectl get svc
❯ kubectl get service
NAME         TYPE        CLUSTER-IP     EXTERNAL-IP   PORT(S)        AGE
kubernetes   ClusterIP   10.96.0.1      <none>        443/TCP        28m
test1        NodePort    10.105.21.48   <none>        80:30740/TCP   4s

# 测试页面
[root@k8s-master ~]# curl localhost:30740

# 查看pod信息
kubectl describe pod test1-7f655bfbf5-sjsrk
...
Events:
  Type    Reason     Age   From               Message
  ----    ------     ----  ----               -------
  Normal  Scheduled  35m   default-scheduler  Successfully assigned default/test1-857949f6d6-7x7mp to wslk8s-control-plane
  Normal  Pulling    35m   kubelet            Pulling image "nginx"
  Normal  Pulled     34m   kubelet            Successfully pulled image "nginx"
  Normal  Created    34m   kubelet            Created container nginx
  Normal  Started    34m   kubelet            Started container nginx


# 查看日志
kubectl logs test1-7f655bfbf5-sjsrk
...
10.244.0.1 - - [08/Feb/2022:06:01:49 +0000] "GET / HTTP/1.1" 200 615 "-" "curl/7.58.0" "-"
```



