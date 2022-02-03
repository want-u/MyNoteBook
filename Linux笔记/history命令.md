# history命令

显示操作记录的时间和用户身份

在/etc/profile中添加如下变量即可:


```
vim /etc/profile
# 追加内容
export HISTTIMEFORMAT="%Y-%m-%d %H:%M:%S  `whoami` "

source /etc/profile
```