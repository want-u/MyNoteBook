# CentOS 7 修改内核默认启动顺序

1. 查看当前内核：


```
cat /boot/grub2/grub.cfg |grep menuentry
# 命令查看当前操作系统有几个系统内核所有的内核

menuentry 'CentOS Linux (3.10.0-1160.11.1.el7.x86_64) 7 (Core)' --class centos --class gnu-linux --class gnu --class os --unrestricted $menuentry_id_option 'gnulinux-3.10.0-957.el7.x86_64-advanced-3e6e6dd1-8c21-423b-aca4-d3083c0e92e0' {
menuentry 'CentOS Linux (3.10.0-957.el7.x86_64) 7 (Core)' --class centos --class gnu-linux --class gnu --class os --unrestricted $menuentry_id_option 'gnulinux-3.10.0-957.el7.x86_64-advanced-3e6e6dd1-8c21-423b-aca4-d3083c0e92e0' {
menuentry 'CentOS Linux (0-rescue-a9ab216e85e24b739582407268ae6377) 7 (Core)' --class centos --class gnu-linux --class gnu --class os --unrestricted $menuentry_id_option 'gnulinux-0-rescue-a9ab216e85e24b739582407268ae6377-advanced-3e6e6dd1-8c21-423b-aca4-d3083c0e92e0' {
```


2. 查看当前默认内核：


```
grub2-editenv list
# 命令查看系统当前的默认内核
# 也可以使用uname -r查看，当前内核是
saved_entry=CentOS Linux (3.10.0-1160.11.1.el7.x86_64) 7 (Core)
```


3. 设置默认内核：


```
# 设置默认启动内核为3.10.0-957
grub2-set-default  'CentOS Linux (3.10.0-957.el7.x86_64) 7 (Core)'
```



4. 重启：


```
然后重启生效，查看内核是否变成3.10.0-957，如图内核已经修改
```
