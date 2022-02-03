# docker-判断是否工作在docker环境

https://blog.csdn.net/ternence_hsu/article/details/100678307

[toc]

### 方式一：判断根目录下 .dockerenv 文件

docker环境下：ls -alh /.dockerenv , 非docker环境，没有这个.dockerenv文件的

```bash
root@4cb54de415d4:/# ls -alh /.dockerenv 
-rwxr-xr-x 1 root root 0 Sep  6 07:09 /.dockerenv
12
```

*注：定制化比较高的docker系统也可能没有这个文件*

### 方式二：查询系统进程的cgroup信息

docker 环境下：cat /proc/1/cgroup

```bash
root@4cb54de415d4:/# cat /proc/1/cgroup 
10:devices:/docker/4cb54de415d470461a636d52a9a4f731eddbbcfdf80b4d0b46466ec1cf27f730
9:perf_event:/docker/4cb54de415d470461a636d52a9a4f731eddbbcfdf80b4d0b46466ec1cf27f730
8:net_cls,net_prio:/docker/4cb54de415d470461a636d52a9a4f731eddbbcfdf80b4d0b46466ec1cf27f730
7:cpu,cpuacct:/docker/4cb54de415d470461a636d52a9a4f731eddbbcfdf80b4d0b46466ec1cf27f730
6:freezer:/docker/4cb54de415d470461a636d52a9a4f731eddbbcfdf80b4d0b46466ec1cf27f730
5:memory:/docker/4cb54de415d470461a636d52a9a4f731eddbbcfdf80b4d0b46466ec1cf27f730
4:cpuset:/docker/4cb54de415d470461a636d52a9a4f731eddbbcfdf80b4d0b46466ec1cf27f730
3:blkio:/docker/4cb54de415d470461a636d52a9a4f731eddbbcfdf80b4d0b46466ec1cf27f730
2:pids:/docker/4cb54de415d470461a636d52a9a4f731eddbbcfdf80b4d0b46466ec1cf27f730
1:name=systemd:/docker/4cb54de415d470461a636d52a9a4f731eddbbcfdf80b4d0b46466ec1cf27f730
1234567891011
```

kvm环境或者物理机环境下：cat /proc/1/cgroup

```bash
root@n12-015-133:~# cat /proc/1/cgroup 
10:cpuset:/
9:freezer:/
8:memory:/init.scope
7:perf_event:/
6:blkio:/init.scope
5:net_cls,net_prio:/
4:cpu,cpuacct:/init.scope
3:pids:/init.scope
2:devices:/init.scope
1:name=systemd:/init.scope
1234567891011
```

判断响应内容即可，主要看name和devices信息，目前来说最靠谱的方式