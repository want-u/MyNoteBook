# awk命令
https://www.runoob.com/linux/linux-comm-awk.html
[toc]
awk 命令的基本格式为：

```
[root@localhost ~]# awk [选项] '脚本命令' 文件名
```
## awk 常用的选项

| 选项       | 含义                                                         |
| ---------- | ------------------------------------------------------------ |
| -F fs      | 指定以 fs 作为输入行的分隔符，awk 命令默认分隔符为空格或制表符。 |
| -f file    | 从脚本文件中读取 awk 脚本指令，以取代直接在命令行中输入指令。 |
| -v var=val | 在执行处理过程之前，设置一个变量 var，并给其设备初始值为 val。 |

## awk 内置变量


| 变量        | 描述                                              |
| :---------- | :------------------------------------------------ |
| $n          | 当前记录的第n个字段，字段间由FS分隔               |
| $0          | 完整的输入记录                                    |
| ARGC        | 命令行参数的数目                                  |
| ARGIND      | 命令行中当前文件的位置(从0开始算)                 |
| ARGV        | 包含命令行参数的数组                              |
| CONVFMT     | 数字转换格式(默认值为%.6g)ENVIRON环境变量关联数组 |
| ERRNO       | 最后一个系统错误的描述                            |
| FIELDWIDTHS | 字段宽度列表(用空格键分隔)                        |
| FILENAME    | 当前文件名                                        |
| FNR         | 各文件分别计数的行号                              |
| FS          | 字段分隔符(默认是任何空格)                        |
| IGNORECASE  | 如果为真，则进行忽略大小写的匹配                  |
| NF          | 一条记录的字段的数目                              |
| NR          | 已经读出的记录数，就是行号，从1开始               |
| OFMT        | 数字的输出格式(默认值是%.6g)                      |
| OFS         | 输出字段分隔符，默认值与输入字段分隔符一致。      |
| ORS         | 输出记录分隔符(默认值是一个换行符)                |
| RLENGTH     | 由match函数所匹配的字符串的长度                   |
| RS          | 记录分隔符(默认是一个换行符)                      |
| RSTART      | 由match函数所匹配的字符串的第一个位置             |
| SUBSEP      | 数组下标分隔符(默认值是/034)                      |

## awk 打印指定行

```
# awk 打印第 6 行内容
awk 'NR==6' file.txt

# awk 打印最后一行
awk 'END{print}'
```

##  awk 格式化输出
```
# awk 格式化输出
echo $total_data | awk -F "Total Disk Size:" '{print $2}' | awk '{printf("%.3f\n", $1)}'

# awk 打印指定行并格式化
echo $total_data | grep Used | awk -F : 'NR==5{printf("%.3f\n", $2/1024/1024/1024.0)}'
```

## awk 计算

```
echo $Total_Disk $rds_backup | awk '{printf("%.2f\n", $2/$1*100)}'
```


## awk 排除前二行（条件判断）
https://blog.csdn.net/weixin_34190136/article/details/90338388

因为linux shell命令行输出的前面几行一般是指导或是格式字段说明，

而不是实现的数据，所以在作过滤时，一般需要排除前面的几行。

现需要找出指定机器开放的所有端口。

我遇到的情况是要排除前面两行，再进行切割。

如下：


```
netstat -nltp|awk '{if (NR>2){print $4}}'|cut -d ':' -f 2
```

## awk 列求和

1.简单的按列求和
```
[linux@test /tmp]$ cat test
123.52
125.54
126.36

[linux@test /tmp]$ awk '{sum += $1};END {print sum}' test
375.42
```
2.对符合某些条件的行，按列求和

```
[linux@test /tmp]$ cat test
aaa 123.52
bbb 125.54
aaa 123.52
aaa 123.52
ccc 126.36

对文件test中 第一列为aaa的行求和
[linux@test /tmp]$ awk '/aaa/ {sum += $2};END {print sum}' test
370.56
```

## awk 行求和
https://www.cnblogs.com/lottu/p/3366010.html


```
1 awk '{s=0;for(i=2;i<=NF;i++){s+=$i};print $0 , s}'
2 awk '{for(i=2;i<=NF;i++){a[NR]+=$i}print $0,a[NR]}'

第二种方法优势在于；利用行数作为下标；这样不用每次都初始化a[NR]=0！
```

## awk 行转列
https://blog.csdn.net/ITyunwei_sky/article/details/97937757


```
echo 1 2 3 | awk '{for(i=1;i<=NF;i++){print $i}}'

# 使用xargs：
echo “I am handson.” |xargs -n1
    -n num 后面加次数，表示命令在执行的时候一次用的argument的个数，默认是用所有的。
```
