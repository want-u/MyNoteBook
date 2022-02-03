# ps -ef | grep 详解

https://www.jianshu.com/p/a122905ed002

[toc]

`ps`命令将某个进程显示出来



> **-A：**显示所有程序。
>  **-e ：**此参数的效果和指定"A"参数相同。
>  **-f：**显示UID，PPIP，C与STIME栏位。

`grep`命令是查找
 中间的`|`是管道命令 是指`ps`命令与`grep`同时执行
 `ps`是LINUX下最常用的也是非常强大的进程查看命令

`grep`命令是查找，是一种强大的文本搜索工具，它能[使用正则表达式](https://www.baidu.com/s?wd=使用正则表达式&tn=44039180_cpr&fenlei=mv6quAkxTZn0IZRqIHckPjm4nH00T1d9uWD3PhP9n1b4m1nduAcz0ZwV5Hcvrjm3rH6sPfKWUMw85HfYnjn4nH6sgvPsT6KdThsqpZwYTjCEQLGCpyw9Uz4Bmy-bIi4WUvYETgN-TLwGUv3EPjfvrHnzPWT3)搜索文本，并把匹配的行打印出来。

`grep`全称是Global Regular Expression Print，表示全局正则表达式版本，它的使用权限是所有用户。

以下这条命令是检查 ssh 进程是否存在：



```cpp
➜  ~ ps -ef | grep ssh
  501  4352  3554   0  6:02下午 ttys002    0:00.01 grep --color=auto --exclude-dir=.bzr --exclude-dir=CVS --exclude-dir=.git --exclude-dir=.hg --exclude-dir=.svn ssh
```

| UID    |  PID   |  PPID  |          C           |    STIME     |        TTY         |       TIME        |        CMD        |
| ------ | :----: | :----: | :------------------: | :----------: | :----------------: | :---------------: | :---------------: |
| 拥有者 | 程序ID | 父级ID | CPU 使用的资源百分比 | 进程启动时间 | 登入者的终端机位置 | 进程使用掉的 CPU 时间 |    下达的指令     |
| 0      |   1    |   0    |          0           |   9:20上午   |         ??         |      2:11.61      |   /sbin/launchd   |
| 5      |   60   |   1    |          0           |   9:20上午   |         ??         |      0:02.16      | /usr/sbin/syslogd |

各相关字段的意义：


```
> **UID：**程序被该 UID 所拥有
>  **PID：**就是这个程序的 ID
>  **PPID：**则是其上级父程序的ID
>  **C：**CPU使用的资源百分比
>  **STIME：**进程启动时间
>  **TTY ：**登入者的终端机位置
>  **TIME：**进程使用掉的CPU时间。
>  **CMD：**所下达的是什么指令
```


举个栗子🌰:



```objectivec
➜  ~ ps -ef
  UID   PID  PPID   C STIME   TTY           TIME CMD
    0     1     0   0  9:20上午 ??         2:11.61 /sbin/launchd
    0    60     1   0  9:20上午 ??         0:02.16 /usr/sbin/syslogd
    0    61     1   0  9:20上午 ??         0:07.43 /usr/libexec/UserEventAgent (System)
    0    64     1   0  9:20上午 ??         0:01.60 /System/Library/PrivateFrameworks/Uninstall.framework/Resources/uninstalld
    0    65     1   0  9:20上午 ??         0:03.81 /usr/libexec/kextd
    0    66     1   0  9:20上午 ??         0:30.18 /System/Library/Frameworks/CoreServices.framework/Versions/A/Frameworks/FSEvents.framework/Versions/A/Support/fseventsd
    0    68     1   0  9:20上午 ??         0:02.66 /System/Library/PrivateFrameworks/MediaRemote.framework/Support/mediaremoted
   55    70     1   0  9:20上午 ??         0:00.49 /System/Library/CoreServices/appleeventsd --server
    0    71     1   0  9:20上午 ??         0:06.58 /usr/sbin/systemstats --daemon
```