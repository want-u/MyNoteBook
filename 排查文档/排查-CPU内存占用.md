# 排查-CPU内存占用

[Linux服务器内存消耗过高 (aliyun.com)](https://help.aliyun.com/document_detail/92980.html)


[toc]

## 查看内存使用


```
❯ free -h
              total        used        free      shared  buff/cache   available
Mem:           7.7G        120M        7.3G         64K        259M        7.4G
Swap:          2.0G          0B        2.0G

```


## 按内存升序排列

执行如下命令，根据rss列排序结果，核实最大消耗内存进程。

```
ps aux --sort=rss

# ps -eo pid,rss,pmem,pcpu,vsz,args --sort=rss

❯ ps aux --sort=rss
USER       PID %CPU %MEM    VSZ   RSS TTY      STAT START   TIME COMMAND
root     16290  0.0  0.0   1764    68 ?        Ss   20:27   0:00 /init
root      5224  0.0  0.0   1764    84 ?        S    16:48   0:00 /init
root      5468  0.0  0.0   1764    84 ?        S    16:49   0:01 /init
root     16291  0.0  0.0   1764    84 ?        S    20:27   0:00 /init
root         1  0.0  0.0   1744  1080 ?        Sl   16:44   0:00 /init
root     16326  0.0  0.0   6776  1160 pts/4    Sl   20:27   0:00 /root/.cache/gitstatus/gitstatusd-linux-x86_64 -G v1.5.1 -s -1 -u -1 -d -1 -c -1 -m -1 -v FATAL -t 32
root     16292  0.0  0.0  12992  1984 pts/1    Ss+  20:27   0:00 ./wsltermd /root
root     16854  0.0  0.0  40100  3460 pts/4    R+   23:28   0:00 ps aux --sort=rss
luoxian   5349  0.0  0.0  58444  4016 ?        S    16:49   0:00 -su
root     16324  0.0  0.0  70788  4076 pts/4    S    20:27   0:00 -/bin/zsh
root      5247  0.0  0.0  59032  4788 ?        S    16:48   0:00 -/bin/zsh
root     16314  0.0  0.0  59056  5064 pts/4    S    20:27   0:00 -/bin/zsh
root     16323  0.0  0.0  70804  5672 pts/4    S    20:27   0:00 -/bin/zsh
999      15654  0.1  0.1  41640  9464 ?        Ssl  20:03   0:12 redis-server *:6379
root     15611  0.0  0.1 1226900 9900 ?        Sl   20:03   0:00 /usr/bin/docker-proxy -proto tcp -host-ip 0.0.0.0 -host-port 6379 -container-ip 172.26.0.2 -container-port 6379
root     16293  0.0  0.1  72352 10460 pts/4    Ss   20:27   0:02 -/bin/zsh
root     15618  0.0  0.1 1228052 14028 ?       Sl   20:03   0:00 /usr/bin/docker-proxy -proto tcp -host-ip :: -host-port 6379 -container-ip 172.26.0.2 -container-port 6379
root     15635  0.0  0.2 711444 17508 ?        Sl   20:03   0:00 /usr/bin/containerd-shim-runc-v2 -namespace moby -id 264432405da3153d51d474ca8590ae4e8a15aa6248b3ea3610662cc868e890
root      8812  0.0  0.7 2311712 57008 ?       Ssl  17:33   0:18 containerd --config /var/run/docker/containerd/containerd.toml --log-level info
root      8795  0.2  1.2 2457388 100484 ?      Sl   17:33   0:46 /usr/bin/dockerd -p /var/run/docker.pid

```

## 按cpu升序排列


```
ps aux --sort=%cpu

❯ ps aux --sort=%cpu
USER       PID %CPU %MEM    VSZ   RSS TTY      STAT START   TIME COMMAND
root         1  0.0  0.0   1744  1080 ?        Sl   16:44   0:00 /init
root      5224  0.0  0.0   1764    84 ?        S    16:48   0:00 /init
root      5247  0.0  0.0  59032  4788 ?        S    16:48   0:00 -/bin/zsh
luoxian   5349  0.0  0.0  58444  4016 ?        S    16:49   0:00 -su
root      5468  0.0  0.0   1764    84 ?        S    16:49   0:01 /init
root      8812  0.0  0.7 2311712 57008 ?       Ssl  17:33   0:18 containerd --config /var/run/docker/containerd/containerd.toml --log-level info
root     15611  0.0  0.1 1226900 9900 ?        Sl   20:03   0:00 /usr/bin/docker-proxy -proto tcp -host-ip 0.0.0.0 -host-port 6379 -container-ip 172.26.0.2 -container-port 6379
root     15618  0.0  0.1 1228052 14028 ?       Sl   20:03   0:00 /usr/bin/docker-proxy -proto tcp -host-ip :: -host-port 6379 -container-ip 172.26.0.2 -container-port 6379
root     15635  0.0  0.2 711444 17508 ?        Sl   20:03   0:00 /usr/bin/containerd-shim-runc-v2 -namespace moby -id 264432405da3153d51d474ca8590ae4e8a15aa6248b3ea3610662cc868e890
root     16290  0.0  0.0   1764    68 ?        Ss   20:27   0:00 /init
root     16291  0.0  0.0   1764    84 ?        S    20:27   0:00 /init
root     16292  0.0  0.0  12992  1984 pts/1    Ss+  20:27   0:00 ./wsltermd /root
root     16293  0.0  0.1  72492 10476 pts/4    Ss   20:27   0:02 -/bin/zsh
root     16314  0.0  0.0  59056  5064 pts/4    S    20:27   0:00 -/bin/zsh
root     16323  0.0  0.0  70804  5672 pts/4    S    20:27   0:00 -/bin/zsh
root     16324  0.0  0.0  70788  4076 pts/4    S    20:27   0:00 -/bin/zsh
root     16326  0.0  0.0   6776  1160 pts/4    Sl   20:27   0:00 /root/.cache/gitstatus/gitstatusd-linux-x86_64 -G v1.5.1 -s -1 -u -1 -d -1 -c -1 -m -1 -v FATAL -t 32
root     16859  0.0  0.0  40100  3424 pts/4    R+   23:28   0:00 ps aux --sort=%cpu
999      15654  0.1  0.1  41640  9464 ?        Ssl  20:03   0:12 redis-server *:6379
root      8795  0.2  1.2 2457388 100484 ?      Sl   17:33   0:46 /usr/bin/dockerd -p /var/run/docker.pid

```

## top命令

进入top后

- m:切换显示内存信息。
- t:切换显示进程和CPU状态信息。
- c:切换显示命令名称和完整命令行。
- M:根据驻留内存大小进行排序。
- P:根据CPU使用百分比大小进行排序。