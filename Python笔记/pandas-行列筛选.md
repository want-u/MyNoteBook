# pandas-行列筛选

- https://www.cnblogs.com/treasury-manager/p/13956651.html
- 
[toc]

## pandas行筛选/列筛选（条件筛选/范围筛选）/计算

## 筛选行

### 一、过滤机制 dataframe[ 条件 ]

可以按照下列方法，依据列的值过滤DataFrame处理某些符合条件的行

```
dataframe[ dataframe["colname"] > value ]
dataframe[ dataframe["colname"] < value ]
dataframe[ dataframe["colname"] != value ]
```

### 二、推导过程

```
boolean_array =   dataframe["colname"] > value         ————>   Series type with bool values
dataframe[boolean_array]                               —————>  DataFrame filtered
dataframe[ dataframe["colname"] > value ]              —————>  DataFrame filtered
```

### 三、多条件过滤

```
dataframe[ dataframe["col1"] > val1 & dataframe["col2"] != val2] 
```

### 四、举例

1、从记录中选出所有fault_code列的值在fault_list= [487, 479, 500, 505]这个范围内的记录
`record2=record[record['FAULT_CODE'].isin(fault_list)]`
![img](https://img2020.cnblogs.com/blog/2124386/202011/2124386-20201110232300178-1079884030.png)

要用.isin 而不能用in,用 in以后选出来的值都是True 和False，然后报错：
`ValueError: The truth value of a Series is ambiguous. Use a.empty, a.bool(), a.item(), a.any()`

2、选出所有WTGS_CODE=20004013的记录

```
set=20004013
record= record[record['WTGS_CODE'] == set]
```

![img](https://img2020.cnblogs.com/blog/2124386/202011/2124386-20201110232401136-912339805.png)
要点：
（1）多个条件筛选的时候每个条件都必须加括号。
（2）判断值是否在某一个范围内进行筛选的时候需要使用DataFrame.isin()的isin()函数，而不能使用in。

3、using DataFrame.apply, which applies a function along a given axis,

```
df = pandas.DataFrame(np.random.randn(5, 3), columns=['a', 'b', 'c'])
df: 
          a         b         c
0 -0.001968 -1.877945 -1.515674
1 -0.540628  0.793913 -0.983315
2 -1.313574  1.946410  0.826350
3  0.015763 -0.267860 -2.228350
4  0.563111  1.195459  0.343168

df[df.apply(lambda x: x['b'] > x['c'], axis=1)]
Out: 
          a         b         c
1 -0.540628  0.793913 -0.983315
2 -1.313574  1.946410  0.826350
3  0.015763 -0.267860 -2.228350
4  0.563111  1.195459  0.343168
```

or
mask = df.apply(lambda row: row["col"].val < 100, axis=1)
df[mask]

## 筛选列

### 从DataFrame里选择几个特定的列来组成新的df

```
假设，df有 col1-col20 一共20列，如果要从中选取几列组成新的df：
df = [[col1,col2,col3,col4]]   #注意要用双括号

假设df有两种columns名称， 一个是中文的col1，一个是英文的col2
可以把col1和col2先做成字典（不能有重复的），如下：
col_dict = dict(zip(col1,col2))

use_col = [c1,c2,c3,c4...]  #假设挑出来c1-c4..几列来做过滤，要找对其对应的英文的列名：
use_col_en = []
#对于use_col里每一个i,在字典col_dict中遍历得到相应的value，再添加到新的列表中，就得到了use_col_en
for i in use_col:
    v = col_dict[i]
    use_col_en.append(v)
    
df_new = df[use_col_en]      #使用use_col_en来过滤原表
df_new.columns = use_col      #将列名也替换
```

## Dataframe 计算

### 两个df相加(次序忽略，结果相同）

```
df_new = df1.add(df2,fill_value=0).fillna(0)
```

### 单个df按条件配号

```
import numpy as np
conditions = [c1,c2,c3,c4,c5,c6]      #其中，c1-c6是布尔表达式
values = [1,2,3,4,5,6]
df[column] = np.select(conditions, values)
```

# Pandas提取含有指定字符串的行或列（DataFrame子集）

- https://blog.csdn.net/nixiang_888/article/details/109371043

Pandas提取含有指定字符串的行（完全匹配，部分匹配）
## 1. 概述
接下来，讲解如何采用正则的方式提取DataFrame子集

### 1、完全匹配

```
==
```

### 2、部分匹配

```
str.contains()：包含一个特定的字符串
    - 参数na：缺少值NaN处理
    - 参数case：大小写的处理
    - 参数regex：使用正则表达式模式
str.endswith（）：以特定字符串结尾
str.startswith（）：以特定的字符串开头
str.match（）：匹配正则表达式模式

注：要提取部分匹配的行，可以使用pandas的（str.xxx（））方法，根据指定条件提取的字符串方法。
```

### 3、取反 ~


```
# 匹配 质检结果 列种包含 一级问题分类 值的数据，然后取反
# 即：取 一级问题分类 无异常的所有数据

data = df[~df['质检结果'].str.contains('一级问题分类')]
```


## 2. 示例

```
import pandas as pd

header = ['name', 'age', 'state', 'point']
data = [['Alice', 24, 'NY', 64], ['Bob', 42, 'CA', 92], ['Charlie', 18, 'CA', 70]]

df = pd.DataFrame(data,columns=header)
print(df)
#       name  age state  point
# 0    Alice   24    NY     64
# 1      Bob   42    CA     92
# 2  Charlie   18    CA     70
```
### 2.1 布尔操作符
使用布尔列表（数组）或pandas.Series，只能提取（选择）True行。


```
mask = [True, False, True]  # 第二行False，表示只匹配1、3行
df_mask = df[mask]
print(df_mask)
#       name  age state  point
# 0    Alice   24    NY     64
# 2  Charlie   18    CA     70
```
因此，对于具有字符串元素的列，是否能够获得根据条件的布尔列表就足够了。

### 2.2 完全匹配操作符（==）
如果元素与字符串完全匹配，则使用==获取为True的pandas.Series。


```
print(df['state'] == 'CA')
# 0    False
# 1     True
# 2     True
# Name: state, dtype: bool

print(df[df['state'] == 'CA'])
#       name  age state  point
# 1      Bob   42    CA     92
# 2  Charlie   18    CA     70
```
### 2.3 部分匹配操作符
#### 2.3.1 str.contains()：包含一个特定的字符串
pandas.Series或pandas.index(columns)字符串方法str.contains()允许获取包含特定字符串的pandas.Series.


```
print(df['name'].str.contains('li'))
# 0     True
# 1    False
# 2     True
# Name: name, dtype: bool

print(df[df['name'].str.contains('li')])
#       name  age state  point
# 0    Alice   24    NY     64
# 2  Charlie   18    CA     70
```
请注意，默认情况下，第一个参数中指定的字符串将作为正则表达式模式进行处理，如下所述。

#### 2.3.2 参数na：缺少值NaN处理
如果元素是缺失值NaN，则默认情况下它将返回NaN而不是True或False。因此，使用pandas.Series提取该行是错误的。


```
df_nan = df.copy()
df_nan.iloc[2, 0] = float('nan')
print(df_nan)
#     name  age state  point
# 0  Alice   24    NY     64
# 1    Bob   42    CA     92
# 2    NaN   18    CA     70

print(df_nan['name'].str.contains('li'))
# 0     True
# 1    False
# 2      NaN
# Name: name, dtype: object

# print(df_nan[df_nan['name'].str.contains('li')])
# ValueError: cannot index with vector containing NA / NaN values
```
可以通过str.contains()的参数na来指定替换NaN结果的值。


```
print(df_nan['name'].str.contains('li', na=False))
# 0     True
# 1    False
# 2    False
# Name: name, dtype: bool

print(df_nan['name'].str.contains('li', na=True))
# 0     True
# 1    False
# 2     True
# Name: name, dtype: bool
```
用作条件时，如果na = True，则选择NaN的行，如果na = False，则不选择NaN的行。

#### 2.3.3 参数case：大小写处理
默认情况下，区分大小写。如果参数case为False，则case被忽略。


```
print(df['name'].str.contains('LI'))
# 0    False
# 1    False
# 2    False
# Name: name, dtype: bool

print(df['name'].str.contains('LI', case=False))
# 0     True
# 1    False
# 2     True
# Name: name, dtype: bool
```
#### 2.3.4 参数regex：使用正则表达式模式
使用str.contains()时要记住的一件事是，默认情况下，指定为第一个参数的字符串将作为正则表达式模式进行处理。


```
print(df['name'].str.contains('i.*e'))
# 0     True
# 1    False
# 2     True
# Name: name, dtype: bool
```
如果参数ragex为False，则确定是否包含第一个参数的字符串本身。


```
print(df['name'].str.contains('i.*e', regex=False))
# 0    False
# 1    False
# 2    False
# Name: name, dtype: bool
```
例如，如果要判断是否包含正则表达式的特殊字符，例如? . *，则需要设置regex = False。当然，可以指定一个正则表达式模式，以转义\?等特殊字符。

请注意，默认值可能会导致错误。


```
df_q = df.copy()
df_q.iloc[2, 0] += '?'
print(df_q)
#        name  age state  point
# 0     Alice   24    NY     64
# 1       Bob   42    CA     92
# 2  Charlie?   18    CA     70

# print(df_q['name'].str.contains('?'))
# error: nothing to repeat at position 0

print(df_q['name'].str.contains('?', regex=False))
# 0    False
# 1    False
# 2     True
# Name: name, dtype: bool

print(df_q['name'].str.contains('\?'))
# 0    False
# 1    False
# 2     True
# Name: name, dtype: bool
```
str.contains()等同于re.search()，并且可以在flags参数中指定正则表达式标志。如稍后所述，还有对应于re.match()的str.match()。

#### 2.3.5 str.endswith()：以特定字符串结尾
pandas.Series字符串方法str.endswith()可以获取以特定字符串结尾的pandas.Series。


```
print(df['name'].str.endswith('e'))
# 0     True
# 1    False
# 2     True
# Name: name, dtype: bool

print(df[df['name'].str.endswith('e')])
#       name  age state  point
# 0    Alice   24    NY     64
# 2  Charlie   18    CA     70
```
str.endswith()也有一个参数na。如果要选择缺失值NaN的行，则设置na = True；如果不想选择，则将na = False设置。

没有参数case，因此它始终区分大小写。

另外，第一个参数的字符串不作为正则表达式模式处理。

#### 2.3.6 str.startswith()：以特定的字符串开头
pandas.Series字符串方法str.startswith()可以获取以特定字符串开头的pandas.Series。


```
print(df['name'].str.startswith('B'))
# 0    False
# 1     True
# 2    False
# Name: name, dtype: bool

print(df[df['name'].str.startswith('B')])
#   name  age state  point
# 1  Bob   42    CA     92
```
#### 2.3.7 str.match()：匹配正则表达式模式
pandas.Series字符串方法str.match()可以获取与正则表达式模式匹配的pandas.Series。


```
print(df['name'].str.match('.*i.*e'))
# 0     True
# 1    False
# 2     True
# Name: name, dtype: bool

print(df[df['name'].str.match('.*i.*e')])
#       name  age state  point
# 0    Alice   24    NY     64
# 2  Charlie   18    CA     70
```
如上所述，str.match()对应于re.match()，并确定字符串的开头是否与模式匹配。如果不是一开始就为False。


```
print(df['name'].str.match('.*i'))
# 0     True
# 1    False
# 2     True
# Name: name, dtype: bool

print(df['name'].str.match('i.*e'))
# 0    False
# 1    False
# 2    False
# Name: name, dtype: bool
1234567891011
```
当需要确定是否包括与模式匹配的部分时，不仅在开始时，而且默认使用与上述re.search()等效的re.contains()（regex = True）。

str.match()与str.contains()可以以相同的方式指定参数na，case和flag。

