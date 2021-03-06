# K8s

**Kubernetes**是一个开源的，用于管理云平台中多个主机上的容器化的应用，Kubernetes的目标是让部署容器化的应用简单并且高效（powerful）,Kubernetes提供了应用部署，规划，更新，维护的一种机制。

## 部署K8s

在所有的节点安装

### 环境要求

https://kubernetes.io/zh/docs/setup/production-environment/tools/kubeadm/install-kubeadm/

- 每台机器 2 GB 或更多的 RAM （如果少于这个数字将会影响你应用的运行内存)
- 2 CPU 核或更多
- 集群中的所有机器的网络彼此均能相互连接(公网和内网都可以)
- 节点之中不可以有重复的主机名、MAC 地址或 product_uuid。请参见[这里](https://kubernetes.io/zh/docs/setup/production-environment/tools/kubeadm/install-kubeadm/#verify-mac-address)了解更多详细信息。
- 开启机器上的某些端口。请参见[这里](https://kubernetes.io/zh/docs/setup/production-environment/tools/kubeadm/install-kubeadm/#check-required-ports) 了解更多详细信息。
- 禁用交换分区。为了保证 kubelet 正常工作，你 **必须** 禁用交换分区。

### 环境拓扑

[内存>2G 核心数>2]

|    主机    |      IP       |  角色  |
| :--------: | :-----------: | :----: |
| k8s-master | 192.168.10.11 | master |
| k8s-node1  | 192.168.10.12 |  node  |
| k8s-node2  | 192.168.10.13 |  node  |

### 准备环境

1. 修改主机名

```
# 修改主机名
hostnamectl set-hostname k8s-master; bash
hostnamectl set-hostname k8s-node1; bash
hostnamectl set-hostname k8s-node2; bash

# 修改hosts文件
vim /etc/hosts

192.168.10.11 k8s-master
192.168.10.12 k8s-node1
192.168.10.13 k8s-node2
```

2. 确保每个节点上 MAC 地址和 product_uuid 的唯一性

```
# 确保每个节点上 MAC 地址和 product_uuid 的唯一性
# 1. MAC地址 必须唯一
cat /sys/class/net/ens33/address 
# 2. product_uuid 必须唯一
cat /sys/class/dmi/id/product_uuid 
```

3. 禁用交换分区。为了保证 kubelet 正常工作

```
# 禁用交换分区。为了保证 kubelet 正常工作
swapoff -a
sed -i 's/\(.*swap\)/#\1/g' /etc/fstab
# 添加内核参数 vim /etc/sysctl.conf
vm.swappiness = 0
# 刷新内核参数
sysctl -p
```

4. 允许 iptables 检查桥接流量，确保 br_netfilter 模块被加载

```
# 允许 iptables 检查桥接流量，确保 br_netfilter 模块被加载
# 加载模块
lsmod | grep netfilter
modprobe br_netfilter
# 添加内核参数 vim /etc/sysctl.conf
net.bridge.bridge-nf-call-arptables = 1
net.bridge.bridge-nf-call-ip6tables = 1
net.bridge.bridge-nf-call-iptables = 1
# 刷新内核参数
sysctl -p
```

5. 关闭防火墙和selinux

```
# 关闭防火墙和selinux
systemctl disable firewalld && systemctl stop firewalld
setenforce 0
sed -i 's/\(SELINUX=\).*/\1disabled/g' /etc/selinux/config
```

### 安装 runtime

为了在 Pod 中运行容器，Kubernetes 使用 [容器运行时（Container Runtime）](https://kubernetes.io/zh/docs/setup/production-environment/container-runtimes)。

```
# 部署docker源
# step 1: 安装必要的一些系统工具
sudo yum install -y yum-utils device-mapper-persistent-data lvm2
# Step 2: 添加软件源信息
sudo yum-config-manager --add-repo https://mirrors.aliyun.com/docker-ce/linux/centos/docker-ce.repo
# Step 3: 更新并安装docker-ce-18.09.9
sudo yum makecache fast
sudo yum -y install docker-ce-18.09.9
sudo systemctl restart docker

# 配置加速器
vim /etc/docker/daemon.json
{"registry-mirrors":["https://8p2ftmks.mirror.aliyuncs.com"]}
# 之后重新启动服务：
sudo systemctl daemon-reload
sudo systemctl restart docker
```

### 安装k8s工具

```
# 添加阿里镜像源
cat <<EOF > /etc/yum.repos.d/kubernetes.repo
[kubernetes]
name=Kubernetes
baseurl=https://mirrors.aliyun.com/kubernetes/yum/repos/kubernetes-el7-x86_64/
enabled=1
gpgcheck=1
repo_gpgcheck=1
gpgkey=https://mirrors.aliyun.com/kubernetes/yum/doc/yum-key.gpg https://mirrors.aliyun.com/kubernetes/yum/doc/rpm-package-key.gpg
EOF
```

```
# 安装Kubernetes (k8s)
yum install -y kubelet-1.18.16 kubeadm-1.18.16 kubectl-1.18.16
systemctl enable kubelet && systemctl start kubelet
```

```
# 查看所需镜像列表
[root@k8s-master ~]# kubeadm config images list 
I0306 15:43:35.262568   19369 version.go:255] remote version is much newer: v1.20.4; falling back to: stable-1.18
W0306 15:43:40.955243   19369 configset.go:202] WARNING: kubeadm cannot validate component configs for API groups [kubelet.config.k8s.io kubeproxy.config.k8s.io]
k8s.gcr.io/kube-apiserver:v1.18.16
k8s.gcr.io/kube-controller-manager:v1.18.16
k8s.gcr.io/kube-scheduler:v1.18.16
k8s.gcr.io/kube-proxy:v1.18.16
k8s.gcr.io/pause:3.2
k8s.gcr.io/etcd:3.4.3-0
k8s.gcr.io/coredns:1.6.7

# 下载国内镜像
docker pull registry.cn-hangzhou.aliyuncs.com/google_containers/kube-apiserver:v1.18.16
docker pull registry.cn-hangzhou.aliyuncs.com/google_containers/kube-controller-manager:v1.18.16
docker pull registry.cn-hangzhou.aliyuncs.com/google_containers/kube-scheduler:v1.18.16
docker pull registry.cn-hangzhou.aliyuncs.com/google_containers/kube-proxy:v1.18.16
docker pull registry.cn-hangzhou.aliyuncs.com/google_containers/pause:3.2
docker pull registry.cn-hangzhou.aliyuncs.com/google_containers/etcd:3.4.3-0
docker pull registry.cn-hangzhou.aliyuncs.com/google_containers/coredns:1.6.7

# 重新打标签
docker tag registry.cn-hangzhou.aliyuncs.com/google_containers/kube-apiserver:v1.18.16 k8s.gcr.io/kube-apiserver:v1.18.16
docker tag registry.cn-hangzhou.aliyuncs.com/google_containers/kube-controller-manager:v1.18.16 k8s.gcr.io/kube-controller-manager:v1.18.16
docker tag registry.cn-hangzhou.aliyuncs.com/google_containers/kube-scheduler:v1.18.16 k8s.gcr.io/kube-scheduler:v1.18.16
docker tag registry.cn-hangzhou.aliyuncs.com/google_containers/kube-proxy:v1.18.16 k8s.gcr.io/kube-proxy:v1.18.16
docker tag registry.cn-hangzhou.aliyuncs.com/google_containers/pause:3.2 k8s.gcr.io/pause:3.2
docker tag registry.cn-hangzhou.aliyuncs.com/google_containers/etcd:3.4.3-0 k8s.gcr.io/etcd:3.4.3-0
docker tag registry.cn-hangzhou.aliyuncs.com/google_containers/coredns:1.6.7 k8s.gcr.io/coredns:1.6.7

# 就镜像打包，上传到其他节点
docker save -o k8s-18-images.tar k8s.gcr.io/kube-apiserver:v1.18.16 k8s.gcr.io/kube-controller-manager:v1.18.16 k8s.gcr.io/kube-scheduler:v1.18.16 k8s.gcr.io/kube-proxy:v1.18.16 k8s.gcr.io/pause:3.2 k8s.gcr.io/etcd:3.4.3-0 k8s.gcr.io/coredns:1.6.7
scp k8s-18-images.tar k8s-node1:/usr/src/
scp k8s-18-images.tar k8s-node2:/usr/src/
# 导入镜像
[root@k8s-node1 ~]# docker load -i /usr/src/k8s-18-images.tar 
[root@k8s-node2 ~]# docker load -i /usr/src/k8s-18-images.tar 
```

```
#### 尝试初始化 ####
# 参数 --image-repository registry.aliyuncs.com/google_containers
[root@ken ~]# kubeadm init --image-repository registry.aliyuncs.com/google_containers --kubernetes-version v1.15.1 --apiserver-advertise-address 172.20.10.2 --pod-network-cidr=10.244.0.0/16
[init] Using Kubernetes version: v1.15.1

You can now join any number of machines by running the following on each node
as root:

  kubeadm join 172.20.10.2:6443 --token rn816q.zj0crlasganmrzsr --discovery-token-ca-cert-hash sha256:e339e4dbf6bd1323c13e794760fff3cbeb7a3f6f42b71d4cb3cffdde72179903
```

### 安装pod网络

要让 Kubernetes Cluster 能够工作，必须安装 Pod 网络，否则 Pod 之间无法通信。

Kubernetes 支持多种网络方案，这里我们先使用 flannel

```
# 配置yml文件
# 访问复制，新建kube-flannel.yml文件
https://raw.githubusercontent.com/coreos/flannel/master/Documentation/kube-flannel.yml

# 拉取镜像
docker pull quay.io/coreos/flannel:v0.13.1-rc2

# 导入到其他节点
# docker save -o flannel.tar quay.io/coreos/flannel:v0.13.1-rc2
scp flannel.tar k8s-node1:/usr/src/
scp flannel.tar k8s-node2:/usr/src/
[root@k8s-node1 ~]# docker load -i /usr/src/flannel.tar 
[root@k8s-node2 ~]# docker load -i /usr/src/flannel.tar 
```

## 初始化

```
# master节点执行：

kubeadm init  --apiserver-advertise-address 192.168.10.11  --pod-network-cidr 10.244.0.0/16
mkdir -p $HOME/.kube
sudo cp -i /etc/kubernetes/admin.conf $HOME/.kube/config
sudo chown $(id -u):$(id -g) $HOME/.kube/config
```

```
# node节点执行：

kubeadm join 192.168.10.11:6443 --token oyg7d9.bgimipoquhfb2s3h \
    --discovery-token-ca-cert-hash sha256:b4b6fc537914aa870e33f8e9234ae7d95f46840f857e51e7088b3de4586192a6
    
```

```
在master节点执行：

# 安装 Pod 网络
kubectl apply -f kube-flannel.yml

# 查看节点状态
[root@k8s-master src]# kubectl get node
NAME         STATUS   ROLES    AGE     VERSION
k8s-master   Ready    master   10m     v1.18.16
k8s-node1    Ready    <none>   7m58s   v1.18.16
k8s-node2    Ready    <none>   7m27s   v1.18.16

# 查看pod信息
[root@k8s-master src]# kubectl get pods -n kube-system
NAME                                 READY   STATUS    RESTARTS   AGE
coredns-66bff467f8-5jhng             1/1     Running   0          10m
coredns-66bff467f8-6rszg             1/1     Running   0          10m
etcd-k8s-master                      1/1     Running   0          10m
kube-apiserver-k8s-master            1/1     Running   0          10m
kube-controller-manager-k8s-master   1/1     Running   0          10m
kube-flannel-ds-6s6tm                1/1     Running   0          7m5s
kube-flannel-ds-n5v5w                1/1     Running   0          7m5s
kube-flannel-ds-qrcbw                1/1     Running   0          7m5s
kube-proxy-6pq6r                     1/1     Running   0          7m25s
kube-proxy-kzss9                     1/1     Running   0          10m
kube-proxy-zcrtp                     1/1     Running   0          7m56s
kube-scheduler-k8s-master            1/1     Running   0          10m
```

