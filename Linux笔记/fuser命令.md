# fuser命令

https://blog.csdn.net/wll_1017/article/details/25645945

## fuser功能

fuser 可以显示出当前哪个程序在使用磁盘上的某个文件、挂载点、甚至网络端口，并给出程序进程的详细信息. 

fuser显示使用指定文件或者文件系统的进程ID.默认情况下每个文件名后面跟一个字母表示访问类型。 

访问类型如下： 
- c 代表当前目录 
- e 将此文件作为程序的可执行对象使用 
- f 打开的文件。默认不显示。 
- F 打开的文件，用于写操作。默认不显示。 
- r 根目录。 
- m 映射文件或者共享库。

## fuser 的返回值：
fuser如果没有找到任何进程正在使用指定的file, filesystem 或 socket, 或者在查找过程中发生了fatal error，则返回non-zero 值。

fuser如果找到至少一个进程正在使用指定的file, filesystem 或 socket，则返回zero。

## fuser 常用场景
fuser通常被用在诊断系统的“resource busy”问题，通常是在你希望umount指定的挂载点得时候遇到。 如果你希望kill所有正在使用某一指定的file, file system or sockets的进程的时候，你可以使用-k option。

fuser –k /path/to/your/filename

这时fuser会向所以正在使用/path/to/your/filename的进程发送SIGKILL。如果你希望在发送之前得到提示，可以使用-i 选项。

fuser –k –i /path/to/your/filename


```
s 将此文件作为共享库（或其他可装载对象）使用
当指定的文件没有被访问，或者出现错误的时候，fuser会返回非零。 
为了查看使用tcp和udp套接字的进程，需要-n选项并指定名称空间。默认IpV4和IpV6都会显示。套接字可以是本地的或者是远程的端口，和远程的地址。所有的域是可选的，但是其前面的','必须存在。如下： 
[lcl_port][,[rmt_host][,[rmt_port]]] 
对于ip地址和port，名称和数字表示都可以使用。 
fuser只把PID输出到标准输出，其他的都输出到标准错误输出。 
常用选项
-a 显示所有命令行中指定的文件，默认情况下被访问的文件才会被显示。 
-c 和-m一样，用于POSIX兼容。 
-k 杀掉访问文件的进程。如果没有指定-signal就会发送SIGKILL信号。 
-i 杀掉进程之前询问用户，如果没有-k这个选项会被忽略。 
-l 列出所有已知的信号名称。 
-m name 指定一个挂载文件系统上的文件或者被挂载的块设备（名称name）。这样所有访问这个文件或者文件系统的进程都会被列出来。如果指定的是一个目录会自动转换成"name/",并使用所有挂载在那个目录下面的文件系统。 
-n space 指定一个不同的命名空间(space).这里支持不同的空间文件(文件名，此处默认)、tcp(本地tcp端口)、udp(本地udp端口)。对于端口， 可以指定端口号或者名称，如果不会引起歧义那么可以使用简单表示的形式，例如：name/space (即形如:80/tcp之类的表示)。 
-s 静默模式，这时候-u,-v会被忽略。-a不能和-s一起使用。 
-signal 使用指定的信号，而不是用SIGKILL来杀掉进程。可以通过名称或者号码来表示信号(例如-HUP,-1),这个选项要和-k一起使用，否则会被忽略。 
-u 在每个PID后面添加进程拥有者的用户名称。 
-v 详细模式。输出似ps命令的输出，包含PID,USER,COMMAND等许多域,如果是内核访问的那么PID为kernel. -V 输出版本号。 
-4 使用IPV4套接字,不能和-6一起应用，只在-n的tcp和udp的命名存在时不被忽略。 
-6 使用IPV6套接字,不能和-4一起应用，只在-n的tcp和udp的命名存在时不被忽略。 
- 重置所有的选项，把信号设置为SIGKILL.
```

```
(1)用fuser命令的四步:
1.确认挂接点有那些进程需要杀掉
#fuser -cu /mount_point
2.向进程发出SIGKILL信号:
#fuser -ck /mount_point
3.确认看是否还有进程在访问挂接点
#fuser -c /mount_point
4.umount挂接点
#umount /mount_point
```
