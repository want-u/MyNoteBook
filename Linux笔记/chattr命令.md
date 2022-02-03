# chattr +i 用户也没法随意删除

https://www.cnblogs.com/cnsre/p/12882190.html

[toc]

root用户也没法用rm随意删除文件？

 

### 前言

在你的印象中，是不是root用户就可以为所欲为呢？随便一个rm -rf *，一波骚操作走人？可能没那么容易。

先来个示例，创建一个文本文件test.txt



```
$ touch test.txt
```

 

将其权限位修改为777



```
$ chmod 777 test.txt
$ ls -al test.txt
-rwxrwxrwx 1 root root 13 Aug 31 15:03 test.txt
```

 

然后做一点暂时不告诉你的小操作。最后尝试删除：



```
$ rm test.txt
rm: cannot remove ‘test.txt’: Operation not permitted
```

 

咦？root用户，777权限位，为什么还删不掉？

### i属性

那么我在中间做了一个什么操作呢？实际上只是执行了一条命令：



```
chattr +i test.txt
```

 

就导致了前面我们看到的效果。Linux中的文件除了我们能看到的内容外，还有一些文件属性，其中，i属性用于防止文件被修改，重命名，删除。在执行前面的命令之后，我们可以通过下面的方式查看其属性：



```
$ lsattr test.txt
----i--------e-- test.txt
```

 

如果想要移除该属性，只需要执行；



```
chattr -i test.txt
```

 

这个时候就可以删除啦。当然了，仅有超级用户可以对这个属性进行修改。所以说，root用户为所欲为还是可以的，不过得加点小操作。

### 文件属性

实际上除了前面说的i属性，还有其他属性，包括：

- a 只能以追加的方式打开文件写（适用于日志文件）
- A 不更新文件的atime，即访问时间
- d dump命令运行时，不进行备份操作
- S 文件有更新时便写磁盘，等同于对文件使用sync

还有其他属性就不一一列举了，有兴趣的可以参考man手册。

### 总结

所以如果你发现有的文件权限位是777，但是用root用户也没法删除，不如使用lsattr看看是不是有i属性。