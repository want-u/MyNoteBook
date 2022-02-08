# systemctl-日志

- [查看 systemctl 崩溃日志 及 运行日志](https://www.cnblogs.com/mafeng/p/11759123.html)
- [如何查看systemctl启动服务的日志 - 简书 (jianshu.com)](https://www.jianshu.com/p/24637256ebdc)

## /var/log/messages

这里的日志也是多个进程混合的，但是方便的是，这里可以查看系统日志

```
❯ tail -50 /var/log/messages
Feb  7 23:14:18 LAPTOP-JOK07HRH rsyslogd:  [origin software="rsyslogd" swVersion="8.32.0" x-pid="206" x-info="http://www.rsyslog.com"] exiting on signal 15.
Feb  7 23:14:18 LAPTOP-JOK07HRH rsyslogd: imuxsock: Acquired UNIX socket '/run/systemd/journal/syslog' (fd 3) from systemd.  [v8.32.0]
Feb  7 23:14:18 LAPTOP-JOK07HRH rsyslogd: rsyslogd's groupid changed to 106
Feb  7 23:14:18 LAPTOP-JOK07HRH rsyslogd: rsyslogd's userid changed to 102
Feb  7 23:14:18 LAPTOP-JOK07HRH rsyslogd:  [origin software="rsyslogd" swVersion="8.32.0" x-pid="4257" x-info="http://www.rsyslog.com"] start
```



## /var/log/syslog

查看指定服务

```
❯ grep -i "docker" /var/log/syslog | tail -50
Feb  7 22:56:29 LAPTOP-JOK07HRH dockerd[1669]: time="2022-02-07T22:56:29.630066309+08:00" level=info msg="ClientConn switching balancer to \"pick_first\"" module=grpc
Feb  7 22:56:29 LAPTOP-JOK07HRH dockerd[1669]: time="2022-02-07T22:56:29.637077007+08:00" level=info msg="[graphdriver] using prior storage driver: overlay2"
Feb  7 22:56:29 LAPTOP-JOK07HRH dockerd[1669]: time="2022-02-07T22:56:29.646683608+08:00" level=warning msg="Your kernel does not support cgroup blkio weight"
Feb  7 22:56:29 LAPTOP-JOK07HRH dockerd[1669]: time="2022-02-07T22:56:29.646702344+08:00" level=warning msg="Your kernel does not support cgroup blkio weight_device"
Feb  7 22:56:29 LAPTOP-JOK07HRH dockerd[1669]: time="2022-02-07T22:56:29.646707002+08:00" level=warning msg="Your kernel does not support cgroup blkio throttle.read_bps_device"
Feb  7 22:56:29 LAPTOP-JOK07HRH dockerd[1669]: time="2022-02-07T22:56:29.646710349+08:00" level=warning msg="Your kernel does not support cgroup blkio throttle.write_bps_device"
Feb  7 22:56:29 LAPTOP-JOK07HRH dockerd[1669]: time="2022-02-07T22:56:29.646718174+08:00" level=warning msg="Your kernel does not support cgroup blkio throttle.read_iops_device"
Feb  7 22:56:29 LAPTOP-JOK07HRH dockerd[1669]: time="2022-02-07T22:56:29.646721650+08:00" level=warning msg="Your kernel does not support cgroup blkio throttle.write_iops_device"
Feb  7 22:56:29 LAPTOP-JOK07HRH dockerd[1669]: time="2022-02-07T22:56:29.646845076+08:00" level=info msg="Loading containers: start."
Feb  7 22:56:32 LAPTOP-JOK07HRH dockerd[1669]: time="2022-02-07T22:56:32.727028805+08:00" level=info msg="Default bridge (docker0) is assigned with an IP address 172.17.0.0/16. Daemon option --bip can be used to set a preferred IP address"
Feb  7 22:56:33 LAPTOP-JOK07HRH dockerd[1669]: time="2022-02-07T22:56:33.406178503+08:00" level=info msg="Loading containers: done."
Feb  7 22:56:33 LAPTOP-JOK07HRH dockerd[1669]: time="2022-02-07T22:56:33.418240520+08:00" level=info msg="Docker daemon" commit=459d0df graphdriver(s)=overlay2 version=20.10.12
Feb  7 22:56:33 LAPTOP-JOK07HRH dockerd[1669]: time="2022-02-07T22:56:33.418303741+08:00" level=info msg="Daemon has completed initialization"
Feb  7 22:56:33 LAPTOP-JOK07HRH systemd[1]: Started Docker Application Container Engine.
Feb  7 22:56:33 LAPTOP-JOK07HRH dockerd[1669]: time="2022-02-07T22:56:33.425384504+08:00" level=info msg="API listen on /var/run/docker.sock"
Feb  7 23:14:39 LAPTOP-JOK07HRH systemd[1]: Stopping Docker Application Container Engine...
Feb  7 23:14:39 LAPTOP-JOK07HRH dockerd[1669]: time="2022-02-07T23:14:39.650748331+08:00" level=info msg="Processing signal 'terminated'"
Feb  7 23:14:39 LAPTOP-JOK07HRH dockerd[1669]: time="2022-02-07T23:14:39.651413602+08:00" level=info msg="stopping event stream following graceful shutdown" error="<nil>" module=libcontainerd namespace=moby
Feb  7 23:14:39 LAPTOP-JOK07HRH dockerd[1669]: time="2022-02-07T23:14:39.651618153+08:00" level=info msg="Daemon shutdown complete"
Feb  7 23:14:39 LAPTOP-JOK07HRH dockerd[1669]: time="2022-02-07T23:14:39.651677235+08:00" level=info msg="stopping event stream following graceful shutdown" error="context canceled" module=libcontainerd namespace=plugins.moby

```



## journalctl

使用journalctl可以查看使用systemctl启动的service，具体的参数的是这里介绍两种常用的

```
#显示所有的systemctl的日志，这里的日志是多个服务混合的，
#一般在刚启动完成一个service的时候进行查看比较方便

❯ journalctl -f
-- Logs begin at Mon 2022-02-07 22:38:25 CST. --
Feb 07 23:31:26 LAPTOP-JOK07HRH kubelet[6908]: E0207 23:31:26.859791    6908 server.go:205] "Failed to load kubelet config file" err="failed to load Kubelet config file /var/lib/kubelet/config.yaml, error failed to read kubelet config file \"/var/lib/kubelet/config.yaml\", error: open /var/lib/kubelet/config.yaml: no such file or directory" path="/var/lib/kubelet/config.yaml"
Feb 07 23:31:26 LAPTOP-JOK07HRH systemd[1]: kubelet.service: Main process exited, code=exited, status=1/FAILURE
Feb 07 23:31:26 LAPTOP-JOK07HRH systemd[1]: kubelet.service: Failed with result 'exit-code'.
Feb 07 23:31:37 LAPTOP-JOK07HRH systemd[1]: kubelet.service: Service hold-off time over, scheduling restart.
Feb 07 23:31:37 LAPTOP-JOK07HRH systemd[1]: kubelet.service: Scheduled restart job, restart counter is at 215.
Feb 07 23:31:37 LAPTOP-JOK07HRH systemd[1]: Stopped kubelet: The Kubernetes Node Agent.
Feb 07 23:31:37 LAPTOP-JOK07HRH systemd[1]: Started kubelet: The Kubernetes Node Agent.
Feb 07 23:31:37 LAPTOP-JOK07HRH kubelet[6929]: E0207 23:31:37.107808    6929 server.go:205] "Failed to load kubelet config file" err="failed to load Kubelet config file /var/lib/kubelet/config.yaml, error failed to read kubelet config file \"/var/lib/kubelet/config.yaml\", error: open /var/lib/kubelet/config.yaml: no such file or directory" path="/var/lib/kubelet/config.yaml"
Feb 07 23:31:37 LAPTOP-JOK07HRH systemd[1]: kubelet.service: Main process exited, code=exited, status=1/FAILURE
Feb 07 23:31:37 LAPTOP-JOK07HRH systemd[1]: kubelet.service: Failed with result 'exit-code'.


```

```
journalctl -u 服务名

❯ journalctl -u docker | tail -50
Feb 07 22:56:29 LAPTOP-JOK07HRH dockerd[1669]: time="2022-02-07T22:56:29.630066309+08:00" level=info msg="ClientConn switching balancer to \"pick_first\"" module=grpc
Feb 07 22:56:29 LAPTOP-JOK07HRH dockerd[1669]: time="2022-02-07T22:56:29.637077007+08:00" level=info msg="[graphdriver] using prior storage driver: overlay2"
Feb 07 22:56:29 LAPTOP-JOK07HRH dockerd[1669]: time="2022-02-07T22:56:29.646683608+08:00" level=warning msg="Your kernel does not support cgroup blkio weight"
Feb 07 22:56:29 LAPTOP-JOK07HRH dockerd[1669]: time="2022-02-07T22:56:29.646702344+08:00" level=warning msg="Your kernel does not support cgroup blkio weight_device"
Feb 07 22:56:29 LAPTOP-JOK07HRH dockerd[1669]: time="2022-02-07T22:56:29.646707002+08:00" level=warning msg="Your kernel does not support cgroup blkio throttle.read_bps_device"
Feb 07 22:56:29 LAPTOP-JOK07HRH dockerd[1669]: time="2022-02-07T22:56:29.646710349+08:00" level=warning msg="Your kernel does not support cgroup blkio throttle.write_bps_device"
Feb 07 22:56:29 LAPTOP-JOK07HRH dockerd[1669]: time="2022-02-07T22:56:29.646718174+08:00" level=warning msg="Your kernel does not support cgroup blkio throttle.read_iops_device"
Feb 07 22:56:29 LAPTOP-JOK07HRH dockerd[1669]: time="2022-02-07T22:56:29.646721650+08:00" level=warning msg="Your kernel does not support cgroup blkio throttle.write_iops_device"
Feb 07 22:56:29 LAPTOP-JOK07HRH dockerd[1669]: time="2022-02-07T22:56:29.646845076+08:00" level=info msg="Loading containers: start."
Feb 07 22:56:32 LAPTOP-JOK07HRH dockerd[1669]: time="2022-02-07T22:56:32.727028805+08:00" level=info msg="Default bridge (docker0) is assigned with an IP address 172.17.0.0/16. Daemon option --bip can be used to set a preferred IP address"
Feb 07 22:56:33 LAPTOP-JOK07HRH dockerd[1669]: time="2022-02-07T22:56:33.406178503+08:00" level=info msg="Loading containers: done."
Feb 07 22:56:33 LAPTOP-JOK07HRH dockerd[1669]: time="2022-02-07T22:56:33.418240520+08:00" level=info msg="Docker daemon" commit=459d0df graphdriver(s)=overlay2 version=20.10.12
Feb 07 22:56:33 LAPTOP-JOK07HRH dockerd[1669]: time="2022-02-07T22:56:33.418303741+08:00" level=info msg="Daemon has completed initialization"
Feb 07 22:56:33 LAPTOP-JOK07HRH systemd[1]: Started Docker Application Container Engine.
Feb 07 22:56:33 LAPTOP-JOK07HRH dockerd[1669]: time="2022-02-07T22:56:33.425384504+08:00" level=info msg="API listen on /var/run/docker.sock"
Feb 07 23:14:39 LAPTOP-JOK07HRH systemd[1]: Stopping Docker Application Container Engine...
Feb 07 23:14:39 LAPTOP-JOK07HRH dockerd[1669]: time="2022-02-07T23:14:39.650748331+08:00" level=info msg="Processing signal 'terminated'"
Feb 07 23:14:39 LAPTOP-JOK07HRH dockerd[1669]: time="2022-02-07T23:14:39.651413602+08:00" level=info msg="stopping event stream following graceful shutdown" error="<nil>" module=libcontainerd namespace=moby
Feb 07 23:14:39 LAPTOP-JOK07HRH dockerd[1669]: time="2022-02-07T23:14:39.651618153+08:00" level=info msg="Daemon shutdown complete"
Feb 07 23:14:39 LAPTOP-JOK07HRH dockerd[1669]: time="2022-02-07T23:14:39.651677235+08:00" level=info msg="stopping event stream following graceful shutdown" error="context canceled" module=libcontainerd namespace=plugins.moby
```

