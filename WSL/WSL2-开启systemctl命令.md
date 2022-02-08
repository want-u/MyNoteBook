# wsl2-开启systemctl命令

- [wsl2开启systemctl命令](https://www.cnblogs.com/gcxblogs/p/14669506.html)
- [WSL2 Ubuntu 永久开启 systemctl 命令的简单方法 - MorStar - 博客园 (cnblogs.com)](https://www.cnblogs.com/MorStar/p/15078738.html)

[toc]

## 默认情况下在wsl2中使用systemctl命令会报错

```shell
$ sudo systemctl status cron.service
System has not been booted with systemd as init system (PID 1). Can't operate.
Failed to connect to bus: Host is down
```

## 临时解决办法

### 安装daemonize

```csharp
sudo apt-get install daemonize
```

### 执行以下两句命令开启

```shell
sudo daemonize /usr/bin/unshare --fork --pid --mount-proc /lib/systemd/systemd --system-unit=basic.target

exec sudo nsenter -t $(pidof systemd) -a su - $LOGNAME
```

### 验证systemctl

```
❯ systemctl status docker
● docker.service - Docker Application Container Engine
   Loaded: loaded (/lib/systemd/system/docker.service; enabled; vendor preset: enabled)
   Active: active (running) since Mon 2022-02-07 22:56:33 CST; 1s ago
     Docs: https://docs.docker.com
 Main PID: 1669 (dockerd)
    Tasks: 20
   CGroup: /system.slice/docker.service
           └─1669 /usr/bin/dockerd -H fd:// --containerd=/run/containerd/containerd.sock

Feb 07 22:56:29 LAPTOP-JOK07HRH dockerd[1669]: time="2022-02-07T22:56:29.646710349+08:00" level=warning msg="Your kernel does not support cgroup blkio throttle.write_bps_device"
Feb 07 22:56:29 LAPTOP-JOK07HRH dockerd[1669]: time="2022-02-07T22:56:29.646718174+08:00" level=warning msg="Your kernel does not support cgroup blkio throttle.read_iops_device"
Feb 07 22:56:29 LAPTOP-JOK07HRH dockerd[1669]: time="2022-02-07T22:56:29.646721650+08:00" level=warning msg="Your kernel does not support cgroup blkio throttle.write_iops_device"
Feb 07 22:56:29 LAPTOP-JOK07HRH dockerd[1669]: time="2022-02-07T22:56:29.646845076+08:00" level=info msg="Loading containers: start."
Feb 07 22:56:32 LAPTOP-JOK07HRH dockerd[1669]: time="2022-02-07T22:56:32.727028805+08:00" level=info msg="Default bridge (docker0) is assigned with an IP address 172.17.0.0/16. Daemon option --bip can be us
Feb 07 22:56:33 LAPTOP-JOK07HRH dockerd[1669]: time="2022-02-07T22:56:33.406178503+08:00" level=info msg="Loading containers: done."
Feb 07 22:56:33 LAPTOP-JOK07HRH dockerd[1669]: time="2022-02-07T22:56:33.418240520+08:00" level=info msg="Docker daemon" commit=459d0df graphdriver(s)=overlay2 version=20.10.12
Feb 07 22:56:33 LAPTOP-JOK07HRH dockerd[1669]: time="2022-02-07T22:56:33.418303741+08:00" level=info msg="Daemon has completed initialization"
Feb 07 22:56:33 LAPTOP-JOK07HRH systemd[1]: Started Docker Application Container Engine.
Feb 07 22:56:33 LAPTOP-JOK07HRH dockerd[1669]: time="2022-02-07T22:56:33.425384504+08:00" level=info msg="API listen on /var/run/docker.sock"

```

## systemctl命令

systemctl是Systemd 的主命令，可用于管理系统。

重载所有修改过的配置文件

```
sudo systemctl daemon-reload
```


列出所有已经加载的systemd units

```
systemctl
systemctl | grep docker.service
```


列出所有service

```
systemctl list-units --type=service
systemctl --type=service
```


列出所有active状态（运行或退出）的服务

```
systemctl list-units --type=service --state=active
```


列出所有正在运行的服务

```
systemctl list-units --type=service --state=running
```


列出所有正在运行或failed状态的服务

```
systemctl list-units --type service --state running,failed
```


列出所有enabled状态的服务

```
systemctl list-unit-files --state=enabled
```

