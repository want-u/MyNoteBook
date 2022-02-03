# pandas iteration遍历

http://c.biancheng.net/pandas/iteration.html

[toc]

遍历是众多编程语言中必备的一种操作，比如 Python 语言通过 for 循环来遍历列表结构。那么 Pandas 是如何遍历 Series 和 DataFrame 结构呢？我们应该明确，它们的数据结构类型不同的，遍历的方法必然会存在差异。对于 Series 而言，您可以把它当做一维数组进行遍历操作；而像 DataFrame 这种二维数据表结构，则类似于遍历 Python 字典。

在 Pandas 中同样也是使用 for 循环进行遍历。通过`for`遍历后，Series 可直接获取相应的 value，而 DataFrame 则会获取列标签。示例如下：

```
import pandas as pd
import numpy as np
N=20
df = pd.DataFrame({
   'A': pd.date_range(start='2016-01-01',periods=N,freq='D'),
   'x': np.linspace(0,stop=N-1,num=N),
   'y': np.random.rand(N),
   'C': np.random.choice(['Low','Medium','High'],N).tolist(),
   'D': np.random.normal(100, 10, size=(N)).tolist()
   })
print(df)
for col in df:
   print (col)
```

输出结果：

```
A
x
y
C
D
```

## 内置迭代方法

如果想要遍历 DataFrame 的每一行，我们下列函数：

- 1) iteritems()：以键值对 (key,value) 的形式遍历；
- 2) iterrows()：以 (row_index,row) 的形式遍历行;
- 3) itertuples()：使用已命名元组的方式对行遍历。


下面对上述函数做简单的介绍：

#### 1) iteritems()

以键值对的形式遍历 DataFrame 对象，以列标签为键，以对应列的元素为值。

```
import pandas as pd
import numpy as np
df = pd.DataFrame(np.random.randn(4,3),columns=['col1','col2','col3'])
for key,value in df.iteritems():
   print (key,value)
```

输出结果：

```
col1 
0    0.561693
1    0.537196
2    0.882564
3    1.063245
Name: col1, dtype: float64
col2 
0   -0.115913
1   -0.526211
2   -1.232818
3   -0.313741
Name: col2, dtype: float64
col3 
0    0.103138
1   -0.655187
2   -0.101757
3    1.505089
Name: col3, dtype: float64
```

#### 2) iterrows()

该方法按行遍历，返回一个迭代器，以行索引标签为键，以每一行数据为值。示例如下：

```
import pandas as pd
import numpy as np
df = pd.DataFrame(np.random.randn(3,3),columns = ['col1','col2','col3'])
print(df)
for row_index,row in df.iterrows():
    print (row_index,row)
```


输出结果：

```
       col1      col2      col3
0 -0.319301  0.205636  0.247029
1  0.673788  0.874376  1.286151
2  0.853439  0.543066 -1.759512

0
col1   -0.319301
col2    0.205636
col3    0.247029
Name: 0, dtype: float64
1
col1    0.673788
col2    0.874376
col3    1.286151
Name: 1, dtype: float64
2
col1    0.853439
col2    0.543066
col3   -1.759512
Name: 2, dtype: float64
```

注意：iterrows() 遍历行，其中 0,1,2 是行索引而 col1,col2,col3 是列索引。

#### 3) itertuples

itertuples() 同样将返回一个迭代器，该方法会把 DataFrame 的每一行生成一个元组，示例如下：

```
import pandas as pd
import numpy as np
df = pd.DataFrame(np.random.rand(3,3),columns = ['c1','c2','c3'])
for row in df.itertuples():
    print(row)
```

输出结果：

```
Pandas(Index=0, c1=0.253902385555437, c2=0.9846386610838339, c3=0.8814786409138894)
Pandas(Index=1, c1=0.018667367298908943, c2=0.5954745800963542, c3=0.04614488622991075)
Pandas(Index=2, c1=0.3066297875412092, c2=0.17984210928723543, c3=0.8573031941082285)
```

## 迭代返回副本

迭代器返回的是原对象的副本，所以，如果在迭代过程中修改元素值，不会影响原对象，这一点需要大家注意。

看一组简单的示例：

```
import pandas as pd
import numpy as np
df = pd.DataFrame(np.random.randn(3,3),columns = ['col1','col2','col3'])
for index, row in df.iterrows():
   row['a'] = 15
print (df)
```

输出结果：

```
       col1      col2      col3
0  1.601068 -0.098414 -1.744270
1 -0.432969 -0.233424  0.340330
2 -0.062910  1.413592  0.066311
```

由上述示例可见，原对象`df`没有受到任何影响。

