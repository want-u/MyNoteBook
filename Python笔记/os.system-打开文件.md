# os.system-打开文件

https://blog.csdn.net/caviar126/article/details/114239075

[toc]

```
import os

# 将文件名传入，直接以默认方式打开文件
os.system('c76a6d08cb77e2a31d0cf0c7c843cad9.jpg')
```

## os.system方法

```
import os

# 等待notepad窗口退出后再继续执行（主进程打开新的窗口，原窗口进入休眠状态，待新的窗口关闭，主进程再重新唤醒原窗口）
os.system('"notepad.exe"')
print('已经退出了notepad')
```



## os.startfile方法

```
# 打开窗口后不等待窗口退出直接继续执行（主进程创建一个子进程去打新的窗口，主进程创建完成子进程后立即继续往下执行）
os.startfile('"notepad.exe"')
print('不等待notepad退出直接继续执行')
```
