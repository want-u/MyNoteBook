# pandas-value_counts计数

https://www.cnblogs.com/keye/p/9664414.html

[toc]

Python是进行数据分析的一种出色语言，主要是因为以数据为中心的python软件包具有奇妙的生态系统。 Pandas是其中的一种，使导入和分析数据更加容易。

Pandas `**Index.value_counts()**`函数返回包含唯一值计数的对象。结果对象将按降序排列，因此第一个元素是最frequently-occurring元素。默认情况下不包括NA值。

## 说明
```
用法： 
Index.value_counts(normalize=False, sort=True, ascending=False, 
				   bins=None, dropna=True)
```

**参数：**
**normalize:**如果为True，则返回的对象将包含唯一值的相对频率。
**sort:**按值排序
**ascending:**升序排列
**bins:**而不是对值进行计数，而是将它们分组到half-open箱中，这是pd.cut的一种便利，仅适用于数字数据
**dropna:**不包括NaN计数。

**返回：**数量：系列



## 示例

**范例1：**采用`Index.value_counts()`函数计算给定索引中唯一值的数量。

```
# importing pandas as pd 
import pandas as pd 
  
# Creating the index 
idx = pd.Index(['Harry', 'Mike', 'Arther', 'Nick', 
                'Harry', 'Arther'], name ='Student') 
  
# Print the Index 
print(idx)
```

**输出：**
![img](https://vimsky.com/wp-content/uploads/2020/02/249eb21992351069eec310324ec2c863.jpg)

让我们找到索引中所有唯一值的计数。

```
# find the count of unique values in the index 
idx.value_counts()
```

**输出：**
![img](https://vimsky.com/wp-content/uploads/2020/02/70c7bc1267c4f92d18fac43b5f4fbced.jpg)
该函数已返回给定索引中所有唯一值的计数。请注意，函数返回的对象包含值的降序出现。

**范例2：**采用`Index.value_counts()`函数查找给定索引中所有唯一值的计数。

```
# importing pandas as pd 
import pandas as pd 
  
# Creating the index 
idx = pd.Index([21, 10, 30, 40, 50, 10, 50]) 
  
# Print the Index 
print(idx)
```

**输出：**
![img](https://vimsky.com/wp-content/uploads/2020/02/63485117ac2f5abd2ce61f68b0e59d21.jpg)

让我们计算一下索引中所有唯一值的出现。

```
# for finding the count of all  
# unique values in the index. 
idx.value_counts()
```

**输出：**
![img](https://vimsky.com/wp-content/uploads/2020/02/01170df5302a6cb6e2d0504357559ab6.jpg)
该函数已返回索引中所有唯一值的计数。


## 转化


```
# importing pandas as pd 
import pandas as pd 
  
# Creating the index 
idx = pd.Index([21, 10, 30, 40, 50, 10, 50]) 

idx.value_counts()

dict(idx.value_counts())
```
