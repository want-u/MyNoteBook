# Docker部署
[toc]

- RPM安装

    下载地址：https://download.docker.com/linux/centos/7/x86_64/stable/Packages/
    
    下载RPM：
    
    docker-ce-17.03.0.ce-1.el7.centos.x86_64.rpm
    
    docker-ce-selinux-17.03.0.ce-1.el7.centos.noarch.rpm
    
    执行安装：yum install -y docker*
- yum安装

    yum install docker
    
- 使用官方安装脚本自动安装

    安装命令如下：
    
    curl -fsSL https://get.docker.com | bash -s docker --mirror Aliyun
    
    也可以使用国内 daocloud 一键安装命令：
    
    curl -sSL https://get.daocloud.io/docker | sh

    
## Docker镜像加速配置
    cp /lib/systemd/system/docker.service /etc/systemd/system/docker.service
    chmod 777 /etc/systemd/system/docker.service 
    vim /etc/systemd/system/docker.service
        ExecStart=/usr/bin/dockerd --registry-mirror=https://8p2ftmks.mirror.aliyuncs.com
    
    systemctl   daemon-reload
    systemctl   restart   docker
    ps   -ef   |   grep docker
    
## Docker化应用体验
    docker run --name db --env MYSQL_ROOT_PASSWORD=example -d mariadb
    docker run --name MyWordPress --link db:mysql -p 8080:80 -d wordpress
    
## Docker基础命令
    docker info            守护进程的系统资源设置
    docker search Docker   仓库的查询
    docker pull	Docker 	   仓库的下载
    docker images Docker   镜像的查询
    docker rmi Docker	   镜像的删除
    docker ps -a		   容器的查询
    docker rm              容器的删除
    docker rm `docker ps -aq` 删除全部容器
    docker run			   容器的创建启动
    docker start/stop      容器启动停止
    docker cp              用于容器与主机之间的数据拷贝。
    docker cp /www/runoob 96f7f14e99ab:/www/
    
## 单一容器管理命令
    docker ps --no-trunc					查看
	docker stop/start CONTAINERID 		    停止
	docker start/stop MywordPress 			通过容器别名启动/停止
	docker inspect MywordPress   			查看容器所有基本信息
	docker logs MywordPress  			    查看容器日志
	docker stats MywordPress  			    查看容器所占用的系统资源
	docker exec 容器名 容器内执行的命令       容器执行命令
	docker exec -it 容器名 /bin/bash  		登入容器的bash
	docker exec -it 容器名 bash  		    登入容器的bash
	
## Run常用的一些参数
    --restart=always   		容器的自动启动
    -h x.xx.xx	 			设置容器主机名
    -dns xx.xx.xx.xx	 	设置容器使用的 DNS 服务器
    --dns-search			DNS 搜索设置
    --add-host hostname:IP	注入 hostname <> IP 解析
    --rm					服务停止时自动删除    
    -v: 绑定一个卷          主机的目录 /data映射到容器的 /data[-v /data:/data]
    
    -a stdin: 指定标准输入输出内容类型，可选 STDIN/STDOUT/STDERR 三项；

    -d: 后台运行容器，并返回容器ID；
    -i: 以交互模式运行容器，通常与 -t 同时使用；
    -P: 随机端口映射，容器内部端口随机映射到主机的端口
    -p: 指定端口映射，格式为：主机(宿主)端口:容器端口
    -t: 为容器重新分配一个伪输入终端，通常与 -i 同时使用；
    --name="nginx-lb": 为容器指定一个名称；
    --dns 8.8.8.8: 指定容器使用的DNS服务器，默认和宿主一致；
    --dns-search example.com: 指定容器DNS搜索域名，默认和宿主一致；
    -h "mars": 指定容器的hostname；
    -e username="ritchie": 设置环境变量；
    --env-file=[]: 从指定文件读入环境变量；
    --cpuset="0-2" or --cpuset="0,1,2": 绑定容器到指定CPU运行；
    -m :设置容器使用内存最大值
    --net="bridge": 指定容器的网络连接类型，支持 bridge/host/none/container: 四种类型；
    --link=[]: 添加链接到另一个容器；
    --expose=[]: 开放一个端口或一组端口；
    --volume , -v: 绑定一个卷