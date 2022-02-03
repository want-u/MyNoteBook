# 判断对象类型-isinstance

https://www.cnblogs.com/Grace-gao/p/11315525.html

## isinstance()函数

是python内置函数，用来判断一个对象是否是一个已知的类型，类似type()。

语法
isinstance(object, classinfo)

参数
- object——实例对象
- classinfo——可以是直接或间接类名、基本类型或者由他们组成的元组。
- 返回值

如果对象类型与参数二的类型（classinfo）相同则返回True，否则返回Fals。

实例:

```
>>> a = 2
>>> isinstance(a, int)
True
>>> isinstance(a, str)
False
>>> isinstance(a, (int, str, bool))
True
>>>
```
## 判断可迭代和迭代器

```
from collections.abc import Iterable
print(isinstance([],Iterable))
print(isinstance( {}, Iterable))
print(isinstance( (), Iterable))
print(isinstance( 'abc', Iterable))
print(isinstance( '100', Iterable))
print(isinstance((x for x in range(10) ), Iterable))
```
