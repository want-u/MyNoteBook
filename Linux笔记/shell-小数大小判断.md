# shell-小数大小判断

https://blog.csdn.net/weixin_42410172/article/details/89703362

1.在shell if判断中，需使用bc命令对多位小数进行判断，用-gt、-eq、-le…或<、>、=、expr命令无法对多位小数进行判断


```
[Devin@Devin2 ~]$ if [ $(echo "120.254 > 111.91" | bc) = 1 ];then
>     echo True
> else
>     echo False
> fi
True
判断正确
```
