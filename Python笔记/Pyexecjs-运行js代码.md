# Pyexecjs-运行js代码

https://zhuanlan.zhihu.com/p/193117566

[toc]

## 配置环境

**node.js**

下载地址：

[https://nodejs.org/en/download/](https://link.zhihu.com/?target=https%3A//nodejs.org/en/download/)

选好版本直接下就好了，它会自动加入系统环境的

检查Node.js版本，出现版本号就说明配置好了



![img](https://pic2.zhimg.com/80/v2-f16342c59871be3f558c860365284a31_1440w.jpg)

**Pyexecjs**

```text
pip install pyexecjs
```



## 基本使用

先检查一下使用的引擎是否为node.js

```python3
import execjs

print(execjs.get().name)
运行结果：
Node.js (V8)
```



Pyexecjs运行js代码有两种方法

### **一、eval()**

eval()可以直接执行js代码

```text
import execjs

print(execjs.eval("a = new Array(1, 2, 3)"))
运行结果：
[1, 2, 3]
```



### **二、compile()**

代码量多的话就推荐用这个方法，先将js代码写入一个文件中，需要的时候读取执行即可。

创建js_text.js文件，写入如下代码：

```js
function a(str) {
    return str;
}
```



python代码：

```python3
import execjs

with open('js_text.js', 'r', encoding='utf-8') as f:
    jstext = f.read()

ctx = execjs.compile(jstext)
a = '123456'
result = ctx.call('a', a)
print(result)
运行结果：
123456
```

先调用compile编译js代码，再调用call方法进行执行，call的第一个参数的js代码中的函数名，第二个参数是该函数需要的参数（如果有多个参数，直接逗号写下一个参数即可）。



## 常见的问题

js代码返回的字符串如果有特殊字符的话可能会出错。

解决方法就是先将字符串进行base64编码后再返回。

```js
function a(str) {
    return new Buffer(str).toString("base64");
}
```

有了这个方法你就可以不用重写代码了，直接扣js代码运行即可，扣代码有时会有些变量未声明，在js代码中查找补全即可。