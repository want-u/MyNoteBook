# Shell按行读取文件
https://www.cnblogs.com/weijiangbao/p/7816949.html
## 写法一：


```
#!/bin/bash
#描述:
#       while循环读行操作
PATH=/bin:/sbin:/usr/bin:/usr/sbin:/usr/local/bin:/usr/local/sbin:~/bin
export PATH

while read line
do
　　echo $line
done < filename(待读取的文件)
```
## 写法二：


```
#!/bin/bash
#描述:
#      cat后读行操作
PATH=/bin:/sbin:/usr/bin:/usr/sbin:/usr/local/bin:/usr/local/sbin:~/bin
export PATH

cat filename(待读取的文件) | while read line
do
　　echo $line
done
```

## 写法三：

```
#!/bin/bash
#描述:
#      for循环读行操作，与while读行有区别
PATH=/bin:/sbin:/usr/bin:/usr/sbin:/usr/local/bin:/usr/local/sbin:~/bin
export PATH

for line in `cat filename(待读取的文件)`
do
　　echo $line
done
```

注：for循环读行操作，与while读行的区别

while是完全按行读取，不管行内有多少段文字；

for是按行读取，如果行内文字有空格，则分开读取，即一次读取一个字符串。
![image](https://images2017.cnblogs.com/blog/1133613/201711/1133613-20171110234345403-219379134.png)


---------------------------- 人生，总有那么几步需要勇气！ ----------------------------
