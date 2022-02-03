# map模块

https://blog.csdn.net/quanlingtu1272/article/details/95482253

[toc]

## 1. map()函数的简介以及语法：

map是[python](https://so.csdn.net/so/search?from=pc_blog_highlight&q=python)内置函数，会根据提供的函数对指定的序列做映射。

map()函数的格式是：

```javascript
map(function,iterable,...)
```

第一个参数接受一个函数名，后面的参数接受一个或多个可迭代的序列，返回的是一个集合。

把函数依次作用在list中的每一个元素上，得到一个新的list并返回。注意，map不改变原list，而是返回一个新list。

## 2. map()函数实例：

```python
del square(x):
    return x ** 2


map(square,[1,2,3,4,5])

# 结果如下:
[1,4,9,16,25]
```

通过使用lambda匿名函数的方法使用map()函数：

```python
map(lambda x, y: x+y,[1,3,5,7,9],[2,4,6,8,10])

# 结果如下：
[3,7,11,15,19]
```

通过lambda函数使返回值是一个元组：

```python
map(lambdax, y : (x**y,x+y),[2,4,6],[3,2,1])

# 结果如下
[(8,5),(16,6),(6,7)]
```

当不传入function时，map()就等同于zip()，将多个列表相同位置的元素归并到一个元组：

```css
map(None,[2,4,6],[3,2,1])

# 结果如下
[(2,3),(4,2),(6,1)]
```

通过map还可以实现类型转换

将元组转换为list：

```python
map(int,(1,2,3))

# 结果如下：
[1,2,3]
```

将字符串转换为list：

```python
map(int,'1234')

# 结果如下：
[1,2,3,4]
```

提取字典中的key，并将结果放在一个list中：

```python
map(int,{1:2,2:3,3:4})

# 结果如下
[1,2,3]
```

 

