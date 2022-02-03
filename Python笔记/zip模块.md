# zip模块

https://www.runoob.com/python/python-func-zip.html

[toc]

## Python zip() 函数

### 描述

**zip()** 函数用于将可迭代的对象作为参数，将对象中对应的元素打包成一个个元组，然后返回由这些元组组成的列表。

如果各个迭代器的元素个数不一致，则返回列表长度与最短的对象相同，利用 * 号操作符，可以将元组解压为列表。

> zip 方法在 Python 2 和 Python 3 中的不同：在 Python 3.x 中为了减少内存，zip() 返回的是一个对象。如需展示列表，需手动 list() 转换。
>
> 如果需要了解 Python3 的应用，可以参考 [Python3 zip()](https://www.runoob.com/python3/python3-func-zip.html)。

### 语法

zip 语法：

```
zip([iterable, ...])
```

参数说明：

- iterabl -- 一个或多个迭代器;

### 返回值

返回元组列表。

### 实例

以下实例展示了 zip 的使用方法：

```
>>>a = [1,2,3]
>>> b = [4,5,6]
>>> c = [4,5,6,7,8]
>>> zipped = zip(a,b)     # 打包为元组的列表
[(1, 4), (2, 5), (3, 6)]
>>> zip(a,c)              # 元素个数与最短的列表一致
[(1, 4), (2, 5), (3, 6)]
>>> zip(*zipped)          # 与 zip 相反，*zipped 可理解为解压，返回二维矩阵式
[(1, 2, 3), (4, 5, 6)]
```

