# column-格式化列信息

https://blog.csdn.net/lemontree1945/article/details/79757995

column命令的使用格式如下：

column [-tx] [-c columns] [-s sep] [file ...]

常用的选项

| 选项         | 含义                                                         |
| ------------ | ------------------------------------------------------------ |
| -c *字符数*  | 指定显示的列宽                                               |
| -s  *分隔符* | 使用-t选项时，指定分隔符（允许指定多个分隔符）               |
| -t           | 判断输入行的列数来创建一个表。<br />分隔符是使用在-s中指定的字符。如果没有指定分隔符，默认是空格 |
| -x           | 更改排列顺序（左→右）。默认的顺序为（上→下）                 |

-t：默认空格是分隔符

```
[root@server1 ~]# mount | column -t
sysfs                    on  /sys                             type  sysfs       (rw,nosuid,nodev,noexec,relatime)
proc                     on  /proc                            type  proc        (rw,nosuid,nodev,noexec,relatime)
devtmpfs                 on  /dev                             type  devtmpfs    (rw,nosuid,size=2001188k,nr_inodes=500297,mode=755)
securityfs               on  /sys/kernel/security             type  securityfs  (rw,nosuid,nodev,noexec,relatime)
tmpfs                    on  /dev/shm                         type  tmpfs       (rw,nosuid,nodev)
devpts                   on  /dev/pts                         type  devpts      (rw,nosuid,noexec,relatime,gid=5,mode=620,ptmxmode=000)
tmpfs                    on  /run                             type  tmpfs       (rw,nosuid,nodev,mode=755)
tmpfs                    on  /sys/fs/cgroup                   type  tmpfs       (ro,nosuid,nodev,noexec,mode=755)
cgroup                   on  /sys/fs/cgroup/systemd           type  cgroup      (rw,nosuid,nodev,noexec,relatime,xattr,release_agent=/usr/lib/systemd/systemd-cgroups-agent,name=systemd)
pstore                   on  /sys/fs/pstore                   type  pstore      (rw,nosuid,nodev,noexec,relatime)
cgroup                   on  /sys/fs/cgroup/cpuset            type  cgroup      (rw,nosuid,nodev,noexec,relatime,cpuset)
cgroup                   on  /sys/fs/cgroup/perf_event        type  cgroup      (rw,nosuid,nodev,noexec,relatime,perf_event)
cgroup                   on  /sys/fs/cgroup/net_cls,net_prio  type  cgroup      (rw,nosuid,nodev,noexec,relatime,net_prio,net_cls)
```

-s：指定分隔符

```
[root@server1 ~]# cat /etc/passwd | column -s ":" -t
root             x  0    0    root                        /root               /bin/bash
bin              x  1    1    bin                         /bin                /sbin/nologin
daemon           x  2    2    daemon                      /sbin               /sbin/nologin
adm              x  3    4    adm                         /var/adm            /sbin/nologin
lp               x  4    7    lp                          /var/spool/lpd      /sbin/nologin
sync             x  5    0    sync                        /sbin               /bin/sync
shutdown         x  6    0    shutdown                    /sbin               /sbin/shutdown
halt             x  7    0    halt                        /sbin               /sbin/halt
mail             x  8    12   mail                        /var/spool/mail     /sbin/nologin
```

