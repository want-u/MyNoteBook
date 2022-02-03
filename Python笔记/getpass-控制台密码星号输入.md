# **getpass-控制台密码星号输入**

https://cloud.tencent.com/developer/article/1406641

[toc]

## **getpass模块**

- 1. getpass.getpass()
- 2. getpass.getuser()

**getpass模块，提示用户输入密码而不回显。**

### **getpass.getpass(prompt='Password: ', stream=None)**

用参数'prompt'提示用户开始输入，默认值为"Password:"。zai Unix上，'prompt'提示符会被写入到类文件流中。
默认写入到控制终端（/dev/tty），如果无法使用，会被写入到sys.stderr中。
如果输入无回显不可用，getpass()将返回并输入一条告警到流，从sys.stdin中读取并抛出异常GetPassWarning。

EXP：

```
>>> import getpass
>>> pwd = getpass.getpass("Please input your password: ")
Please input your password:
>>> print(pwd)
123456789
>>>
```

### **getpass.getuser()**

返回用户登录名。
该函数按照顺序检查环境变量LOGNAME、USER、LNAME和USERNAME，并返回第一个非空字符串的变量值。如果这些环境变量都没有被设置，则在支持pwd模块的系统上返回密码数据库中额登录名，否则，抛出异常。

EXP：

\>>> import getpass
\>>> user = getpass.getuser()
\>>> print(user)
Administrator
\>>>

### **在PyCharm中使用**

（此模块在Pycharm中无法使用）

```
'''
利用getpass模块的函数，检查密码输入
Author: 
'''
import getpass
def check_pass(user, pwd):
    if user == 'Administrator' and pwd == '123456':
        print("UserName is: %s, PassWord is: %s" %(user, pwd))
        print("Check OK!")
    else:
        print("UserName or Password error!")


user = getpass.getuser()
pwd = getpass.getpass('Entry your password: ')
check_pass(user, pwd)
```

 ![img](https://img2018.cnblogs.com/blog/1681552/201905/1681552-20190509145031816-311196078.png)

**打开pycharm的terminal：**

![img](https://img2018.cnblogs.com/blog/1681552/201905/1681552-20190509145114425-1625091600.png)
