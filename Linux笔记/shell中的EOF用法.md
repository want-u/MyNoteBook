# shell中的EOF用法

https://www.cnblogs.com/cangqinglang/p/12444577.html

[toc]

## 1、EOF 

 Shell中通常将EOF与 << 结合使用，表示后续的输入作为子命令或子Shell的输入，直到遇到EOF为止，再返回到主调Shell。
 
 可以把EOF替换成其他东西，意思是把内容当作标准输入传给程序。

  回顾一下< <的用法。当shell看到< <的时候，它就会知道下一个词是一个分界符。
  
  在该分界符以后的内容都被当作输入，直到shell又看到该分界符(位于单独的一行)。这个分界符可以是你所定义的任何字符串。

例子：自动登录mysql（root:root,passwd:123456),查询test库，test1表里的user=aa的记录。

```
#!/bin/sh
mysql -uroot -p123456 <<EOF
use test;
select * from testaa while a=10000; ###1000 not usr single quote mark,because a is int
# type,only char type need single quote mark.
exit
EOF
```
特殊用法：
```
: << COMMENTBLOCK
   shell脚本代码段
COMMENTBLOCK
```
用来注释整段脚本代码。 : 是shell中的空语句。
```
echo start
:<<COMMENTBLOCK
echo
echo "this is a test"
echo
COMMENTBLOCK
echo end
```
这段脚本执行时，中间部分不会被执行：
 
```
[root@newserver shell]# sh eof.sh
start
end
```
## 2、cat和eof结合使用具有追加功能

使用Cat和EOF多行输入
```
cat >> /root/test.txt << EOF 

abcdef 

hello word ! 

FFFFFFFFFFFFFFFFFFFFFFF 

EOF
```
## 3、变量$处理
https://www.cnblogs.com/sanduzxcvbnm/p/14638070.html

在使用cat EOF中出现$变量通常会直接被执行，显示执行的结果。若想保持$变量不变需要使用 \ 符进行注释


```
[root@localhost ~]# cat >> aa.txt << EOF
> echo \$aa
> EOF
[root@localhost ~]# cat aa.txt 
echo $aa
```
当存在$变量过多，或存在赋值命令的时候可直接在EOF上加上双引号就行。这时就不用使用\进行注释了。


```
[root@localhost ~]# cat >> bb.txt << "EOF"
> echo $aa
> EOF
[root@localhost ~]# cat bb.txt 
echo $aa
```
