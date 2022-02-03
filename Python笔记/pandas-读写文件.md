# pandas-读写文件

- http://c.biancheng.net/pandas/
- https://jishuin.proginn.com/p/763bfbd6a4af

[toc]

Pandas 库是一个免费、开源的第三方 Python 库，是 Python 数据分析必不可少的工具之一，它为 Python 数据分析提供了高性能，且易于使用的数据结构，即 Series 和 DataFrame。Pandas 自诞生后被应用于众多的领域，比如金融、统计学、社会科学、建筑工程等。

Pandas 库基于 Python NumPy 库开发而来，因此，它可以与 Python 的科学计算库配合使用。Pandas 提供了两种数据结构，分别是 Series（一维数组结构）与 DataFrame（二维数组结构），这两种数据结构极大地增强的了 Pandas 的数据分析能力。在本套教程中，我们将学习 Python Pandas 的各种方法、特性以及如何在实践中运用它们。

## Excel读写

### 读取excel文件：read_excel()

如果您想读取 Excel 表格中的数据，可以使用 read_excel() 方法，其语法格式如下：

```
pd.read_excel(io, sheet_name=0, header=0, names=None, index_col=None,
              usecols=None, squeeze=False,dtype=None, engine=None,
              converters=None, true_values=None, false_values=None,
              skiprows=None, nrows=None, na_values=None, parse_dates=False,
              date_parser=None, thousands=None, comment=None, skipfooter=0,
              convert_float=True, **kwds)
```

下表对常用参数做了说明：

| 参数名称        | 说明                                                         |
| --------------- | ------------------------------------------------------------ |
| io              | 表示 Excel 文件的存储路径。                                  |
| sheet_name      | 要读取的工作表名称。默认值是0，表示读取第一个sheet。         |
| na_values       | 可以填充这些异常符号为缺失值。例如：na_values='#N'           |
| keep_default_na | 默认为True，缺失值表示为NaN；当为False时显示原始空值         |
| header          | 指定作为列名的行，默认0，即取第一行的值为列名；若数据不包含列名，则设定 header = None。<br />若将其设置 为 header=2，则表示将前两行作为多重索引。 |
| names           | 一般适用于Excel缺少列名，或者需要重新定义列名的情况；names的长度必须等于Excel表格列的长度，否则会报错。 |
| index_col       | 用做行索引的列，可以是工作表的列名称，如 index_col = '列名'，也可以是整数或者列表。 |
| usecols         | int或list类型，默认为None，表示需要读取所有列。              |
| squeeze         | boolean，默认为False，如果解析的数据只包含一列，则返回一个Series。 |
| converters      | 规定每一列的数据类型。                                       |
| skiprows        | 接受一个列表，表示跳过指定行数的数据，从头部第一行开始。     |
| nrows           | 需要读取的行数。                                             |
| skipfooter      | 接受一个列表，省略指定行数的数据，从尾部最后一行开始。       |

读取excel文件示例：
```
# pd.read_excel(file_name): 读取excel表格
#  - sheet_name  默认值是0，表示读取第一个sheet。
#  - keep_default_na=False   显示原始空值
import pandas as pd

df = pd.read_excel(file, sheet_name=sheet_name, keep_default_na=False)
```

### 写入excel文件：to_excel()

通过 to_excel() 函数可以将 Dataframe 中的数据写入到 Excel 文件。

如果想要把单个对象写入 Excel 文件，那么必须指定目标文件名；如果想要写入到多张工作表中，则需要创建一个带有目标文件名的`ExcelWriter`对象，并通过`sheet_name`参数依次指定工作表的名称。

to_ecxel() 语法格式如下：

```
DataFrame.to_excel(excel_writer, sheet_name='Sheet1', na_rep='', float_format=None,
                  columns=None, header=True, index=True, index_label=None, startrow=0,
                  startcol=0, engine=None, merge_cells=True, encoding=None,
                  inf_rep='inf', verbose=True, freeze_panes=None)  
```

下表列出函数的常用参数项，如下表所示：

| 参数名称     | 描述说明                                                     |
| ------------ | ------------------------------------------------------------ |
| excel_wirter | 文件路径或者 ExcelWrite 对象。                               |
| sheet_name   | 指定要写入数据的工作表名称。                                 |
| na_rep       | 缺失值的表示形式。                                           |
| float_format | 它是一个可选参数，用于格式化浮点数字符串。                   |
| columns      | 指要写入的列。                                               |
| header       | 写出每一列的名称，如果给出的是字符串列表，则表示列的别名。   |
| index        | 表示要写入的索引。                                           |
| index_label  | 引用索引列的列标签。如果未指定，并且 hearder 和 index 均为为 True，则使用索引名称。<br />如果 DataFrame 使用 MultiIndex，则需要给出一个序列。 |
| startrow     | 初始写入的行位置，默认值0。表示引用左上角的行单元格来储存 DataFrame。 |
| startcol     | 初始写入的列位置，默认值0。表示引用左上角的列单元格来储存 DataFrame。 |
| engine       | 它是一个可选参数，用于指定要使用的引擎，可以是 openpyxl 或 xlsxwriter。 |

写入excel文件示例：

```
# 写入excel文件df.to_excel(file_name)
# - sheet_name: str类型的 sheet名
# - index: 默认为True写入索引，设置为False时不写入索引

# 1、写入文件现有sheet
df.to_excel('result-file.xlsx', sheet_name=sheet_name, index=False)

# 2、写入文件（可以创建多sheet写入）
writer = pd.ExcelWriter('result-file.xlsx')
df.to_excel(writer, sheet_name='sheet1', index=False)
df.to_excel(writer, sheet_name='sheet2', index=False)
writer.save()
```

### 数据类型：dtypes

https://www.cnblogs.com/onemorepoint/p/9404753.html
![image](http://ww1.sinaimg.cn/large/9ebd4c2bgy1fto9860xy9j20sl0bfgrk.jpg)

```
# dtype设置指定列类型
df = pd.read_excel(file, sheet_name=sheet_name, keep_default_na=False, dtype={'提交时间': str, '结束时间': str})

# 查看数据类型
df.dtypes

```

### 去重函数：drop_duplicates()
http://c.biancheng.net/pandas/drop-duplicate.html

drop_duplicates()函数的语法格式如下：

```
df.drop_duplicates(subset=['A','B','C'],keep='first',inplace=True)
```

参数说明如下：

- subset：表示要进去重的列名，默认为 None。
- keep：有三个可选参数，分别是 first、last、False，默认为 first，表示只保留第一次出现的重复项，删除其余重复项，last 表示只保留最后一次出现的重复项，False 则表示删除所有重复项。
- inplace：布尔值参数，默认为 False 表示删除重复项后返回一个副本，若为 Ture 则表示直接在原数据上删除重复项。


```
# 默认保留第一次出现的重复项
import pandas as pd
data={
  
    'A':[1,0,1,1],
    'B':[0,2,5,0],
    'C':[4,0,4,4],
    'D':[1,0,1,1]
}
df=pd.DataFrame(data=data)
#默认保留第一次出现的重复项
df.drop_duplicates()

#keep=False删除所有重复项
df.drop_duplicates(keep=False)

#根据指定列标签去重
df.drop_duplicates(['B'],keep=False)

#指定多列同时去重；last只保留最后一个重复项
df.drop_duplicates(['Age','Group ID'],keep='last')

```

### 重置索引：reset_index

```
#重置索引，从0重新开始
df.reset_index(drop=True)
```



## DataFrame结构

DataFrame 一个表格型的数据结构，既有行标签（index），又有列标签（columns），它也被称异构数据表，所谓异构，指的是表格中每列的数据类型可以不同，比如可以是字符串、整型或者浮点型等。其结构图示意图，如下所示：



![Dataframe结构示意图](https://gitee.com/luoxian1011/pictures/raw/master/154931A54-0.gif)


表格中展示了某个销售团队个人信息和绩效评级（rating）的相关数据。数据以行和列形式来表示，其中每一列表示一个属性，而每一行表示一个条目的信息。

下表展示了上述表格中每一列标签所描述数据的数据类型，如下所示：



| Column | Type    |
| ------ | ------- |
| name   | String  |
| age    | integer |
| gender | String  |
| rating | Float   |

DataFrame 的每一行数据都可以看成一个 Series 结构，只不过，DataFrame 为这些行中每个数据值增加了一个列标签。因此 DataFrame 其实是从 Series 的基础上演变而来。在数据分析任务中 DataFrame 的应用非常广泛，因为它描述数据的更为清晰、直观。

通过示例对 DataFrame 结构做进一步讲解。 下面展示了一张学生成绩表，如下所示：

![pandas dataframe结构](https://gitee.com/luoxian1011/pictures/raw/master/1549314F4-1.gif)

DataFrame 结构类似于 Execl 的表格型，表格中列标签的含义如下所示：

- Regd.No：表示登记的序列号
- Name：学生姓名
- Marks：学生分数

同 Series 一样，DataFrame 自带行标签索引，默认为“隐式索引”即从 0 开始依次递增，行标签与 DataFrame 中的数据项一一对应。上述表格的行标签从 0 到 5，共记录了 5 条数据（图中将行标签省略）。当然你也可以用“显式索引”的方式来设置行标签。

下面对 DataFrame 数据结构的特点做简单地总结，如下所示：

- DataFrame 每一列的标签值允许使用不同的数据类型；
- DataFrame 是表格型的数据结构，具有行和列；
- DataFrame 中的每个数据值都可以被修改。
- DataFrame 结构的行数、列数允许增加或者删除；
- DataFrame 有两个方向的标签轴，分别是行标签和列标签；
- DataFrame 可以对行和列执行算术运算。

## 创建DataFrame对象

创建 DataFrame 对象的语法格式如下：

```
import pandas as pd
pd.DataFrame( data, index, columns, dtype, copy)
```

参数说明：

| 参数名称 | 说明                                                         |
| -------- | ------------------------------------------------------------ |
| data     | 输入的数据，可以是 ndarray，series，list，dict，标量以及一个 DataFrame。 |
| index    | 行标签，如果没有传递 index 值，则默认行标签是 np.arange(n)，n 代表 data 的元素个数。 |
| columns  | 列标签，如果没有传递 columns 值，则默认列标签是 np.arange(n)。 |
| dtype    | dtype表示每一列的数据类型。                                  |
| copy     | 默认为 False，表示复制数据 data。                            |


Pandas 提供了多种创建 DataFrame 对象的方式，主要包含以下五种，分别进行介绍。

### 1) 创建空的DataFrame对象

使用下列方式创建一个空的 DataFrame，这是 DataFrame 最基本的创建方法。

```
import pandas as pd
df = pd.DataFrame()print(df)
```

输出结果如下：

```
Empty DataFrame
Columns: []
Index: []
```

### 2) 列表创建DataFame对象

可以使用单一列表或嵌套列表来创建一个 DataFrame。

示例 1，单一列表创建 DataFrame：

```
import pandas as pd
data = [1,2,3,4,5]
df = pd.DataFrame(data)
print(df)
```

输出如下：

```
     0
0    1
1    2
2    3
3    4
4    5
```

示例 2，使用嵌套列表创建 DataFrame 对象：

```
import pandas as pd
data = [['Alex',10],['Bob',12],['Clarke',13]]
df = pd.DataFrame(data,columns=['Name','Age'])
print(df)
```

输出结果：

```
      Name      Age
0     Alex      10
1     Bob       12
2     Clarke    13
```

示例 3，指定数值元素的数据类型为 float：

```
import pandas as pd
data = [['Alex',10],['Bob',12],['Clarke',13]]
df = pd.DataFrame(data,columns=['Name','Age'],dtype=float)
print(df)
```

输出结果：

```
      Name     Age
0     Alex     10.0
1     Bob      12.0
2     Clarke   13.0
```

### 3) 字典嵌套列表创建

data 字典中，键对应的值的元素长度必须相同（也就是列表长度相同）。如果传递了索引，那么索引的长度应该等于数组的长度；如果没有传递索引，那么默认情况下，索引将是 range(n)，其中 n 代表数组长度。

示例 4：

```
import pandas as pd
data = {'Name':['Tom', 'Jack', 'Steve', 'Ricky'],'Age':[28,34,29,42]}
df = pd.DataFrame(data)
print(df)
```

输出结果：

```
      Age      Name
0     28        Tom
1     34       Jack
2     29      Steve
3     42      Ricky
```

注意：这里使用了默认行标签，也就是 range(n)。它生成了 0,1,2,3，并分别对应了列表中的每个元素值。

示例 5，现在给上述示例 4 添加自定义的行标签：

```
import pandas as pd
data = {'Name':['Tom', 'Jack', 'Steve', 'Ricky'],'Age':[28,34,29,42]}
df = pd.DataFrame(data, index=['rank1','rank2','rank3','rank4'])
print(df)
```

输出结果如下：

```
         Age    Name
rank1    28      Tom
rank2    34     Jack
rank3    29    Steve
rank4    42    Ricky
```

注意：index 参数为每行分配了一个索引。

### 4) 添加数据行

使用 append() 函数，可以将新的数据行添加到 DataFrame 中，该函数会在行末追加数据行。示例如下：

```
import pandas as pd
df = pd.DataFrame([[1, 2], [3, 4]], columns = ['a','b'])
df2 = pd.DataFrame([[5, 6], [7, 8]], columns = ['a','b'])
#在行末追加新数据行
df = df.append(df2)
print(df)
```

输出结果：

```
   a  b
0  1  2
1  3  4
0  5  6
1  7  8
```

### 5) 删除数据行

您可以使用行索引标签，从 DataFrame 中删除某一行数据。如果索引标签存在重复，那么它们将被一起删除。示例如下：

```
import pandas as pd
df = pd.DataFrame([[1, 2], [3, 4]], columns = ['a','b'])
df2 = pd.DataFrame([[5, 6], [7, 8]], columns = ['a','b'])
df = df.append(df2)
print(df)
#注意此处调用了drop()方法
df = df.drop(0)
print (df)
```

输出结果：

```
执行drop(0)前：
   a  b
0  1  2
1  3  4
0  5  6
1  7  8

执行drop(0)后：
  a b
1 3 4
1 7 8
```

在上述的示例中，默认使用 range(2) 生成了行索引，并通过 drop(0) 同时删除了两行数据。

## 常用属性和方法汇总

DataFrame 的属性和方法，与 Series 相差无几，如下所示：



| 名称    | 属性&方法描述                                            |
| ------- | -------------------------------------------------------- |
| T       | 行和列转置。                                             |
| axes    | 返回一个仅以行轴标签和列轴标签为成员的列表。             |
| dtypes  | 返回每列数据的数据类型。                                 |
| empty   | DataFrame中没有数据或者任意坐标轴的长度为0，则返回True。 |
| ndim    | 轴的数量，也指数组的维数。                               |
| shape   | 返回一个元组，表示了 DataFrame 维度。                    |
| size    | DataFrame中的元素数量。                                  |
| values  | 使用 numpy 数组表示 DataFrame 中的元素值。               |
| head()  | 返回前 n 行数据。                                        |
| tail()  | 返回后 n 行数据。                                        |
| shift() | 将行或列移动指定的步幅长度                               |

下面对 DataFrame 常用属性进行演示，首先我们创建一个 DataFrame 对象，示例如下：

```
import pandas as pdimport numpy as npd = {'Name':pd.Series(['c语言中文网','编程帮',"百度",'360搜索','谷歌','微学苑','Bing搜索']),   'years':pd.Series([5,6,15,28,3,19,23]),   'Rating':pd.Series([4.23,3.24,3.98,2.56,3.20,4.6,3.8])}#构建DataFramedf = pd.DataFrame(d)#输出seriesprint(df)
```

输出结果：

```
输出 series 数据:
     Name  years  Rating
0  c语言中文网    5    4.23
1     编程帮     6    3.24
2      百度     15    3.98
3   360搜索     28    2.56
4      谷歌     3     3.20
5     微学苑    19    4.60
6  Bing搜索     23    3.80
```

### 1) T（Transpose）转置

返回 DataFrame 的转置，也就是把行和列进行交换。

```
import pandas as pd
import numpy as np
d = {'Name':pd.Series(['c语言中文网','编程帮',"百度",'360搜索','谷歌','微学苑','Bing搜索']),
   'years':pd.Series([5,6,15,28,3,19,23]),
   'Rating':pd.Series([4.23,3.24,3.98,2.56,3.20,4.6,3.8])}
#构建DataFrame
df = pd.DataFrame(d)
#输出DataFrame的转置
print(df.T)
```

输出结果：

```
Our data series is:
             0         1      2      3       4    5       6
Name    c语言中文网   编程帮    百度  360搜索   谷歌  微学苑  Bing搜索
years        5        6      15      28      3     19      23
Rating    4.23       3.24    3.98    2.56    3.2   4.6     3.8
```

### 2) axes

返回一个行标签、列标签组成的列表。

```
import pandas as pd
import numpy as np
d = {'Name':pd.Series(['c语言中文网','编程帮',"百度",'360搜索','谷歌','微学苑','Bing搜索']),
   'years':pd.Series([5,6,15,28,3,19,23]),
   'Rating':pd.Series([4.23,3.24,3.98,2.56,3.20,4.6,3.8])}
#构建DataFrame
df = pd.DataFrame(d)
#输出行、列标签
print(df.axes)
```

输出结果：

```
[RangeIndex(start=0, stop=7, step=1), Index(['Name', 'years', 'Rating'], dtype='object')]
```

### 3) dtypes

返回每一列的数据类型。示例如下：

```
import pandas as pd
import numpy as np
d = {'Name':pd.Series(['c语言中文网','编程帮',"百度",'360搜索','谷歌','微学苑','Bing搜索']),
   'years':pd.Series([5,6,15,28,3,19,23]),
   'Rating':pd.Series([4.23,3.24,3.98,2.56,3.20,4.6,3.8])}
#构建DataFrame
df = pd.DataFrame(d)
#输出行、列标签
print(df.dtypes)
```

输出结果：

```
Name       object
years       int64
Rating     float64
dtype:     object
```

### 4) empty

返回一个布尔值，判断输出的数据对象是否为空，若为 True 表示对象为空。

```
import pandas as pd
import numpy as np
d = {'Name':pd.Series(['c语言中文网','编程帮',"百度",'360搜索','谷歌','微学苑','Bing搜索']),
   'years':pd.Series([5,6,15,28,3,19,23]),
   'Rating':pd.Series([4.23,3.24,3.98,2.56,3.20,4.6,3.8])}
#构建DataFrame
df = pd.DataFrame(d)
#判断输入数据是否为空
print(df.empty)
```

输出结果：

```
判断输入对象是否为空：
False
```

### 5) ndim

返回数据对象的维数。DataFrame 是一个二维数据结构。

```
import pandas as pd
import numpy as np
d = {'Name':pd.Series(['c语言中文网','编程帮',"百度",'360搜索','谷歌','微学苑','Bing搜索']),
   'years':pd.Series([5,6,15,28,3,19,23]),
   'Rating':pd.Series([4.23,3.24,3.98,2.56,3.20,4.6,3.8])}
#构建DataFrame
df = pd.DataFrame(d)
#DataFrame的维度
print(df.ndim)
```

输出结果：

2

### 6) shape

返回一个代表 DataFrame 维度的元组。返回值元组 (a,b)，其中 a 表示行数，b 表示列数。

```
import pandas as pd
import numpy as np
d = {'Name':pd.Series(['c语言中文网','编程帮',"百度",'360搜索','谷歌','微学苑','Bing搜索']),
   'years':pd.Series([5,6,15,28,3,19,23]),
   'Rating':pd.Series([4.23,3.24,3.98,2.56,3.20,4.6,3.8])}
#构建DataFrame
df = pd.DataFrame(d)
#DataFrame的形状
print(df.shape)
```

输出结果：

(7, 3)

### 7) size

返回 DataFrame 中的元素数量。示例如下：

```
import pandas as pd
import numpy as np
d = {'Name':pd.Series(['c语言中文网','编程帮',"百度",'360搜索','谷歌','微学苑','Bing搜索']),
   'years':pd.Series([5,6,15,28,3,19,23]),
   'Rating':pd.Series([4.23,3.24,3.98,2.56,3.20,4.6,3.8])}
#构建DataFrame
df = pd.DataFrame(d)
#DataFrame的中元素个数
print(df.size)
```

输出结果：

21

### 8) values

以 ndarray 数组的形式返回 DataFrame 中的数据。

```
import pandas as pd
import numpy as np
d = {'Name':pd.Series(['c语言中文网','编程帮',"百度",'360搜索','谷歌','微学苑','Bing搜索']),
   'years':pd.Series([5,6,15,28,3,19,23]),
   'Rating':pd.Series([4.23,3.24,3.98,2.56,3.20,4.6,3.8])}
#构建DataFrame
df = pd.DataFrame(d)
#DataFrame的数据
print(df.values)
```

输出结果：

```
[['c语言中文网' 5 4.23]
['编程帮' 6 3.24]
['百度' 15 3.98]
['360搜索' 28 2.56]
['谷歌' 3 3.2]
['微学苑' 19 4.6]
['Bing搜索' 23 3.8]]
```

### 9) head()&tail()查看数据

如果想要查看 DataFrame 的一部分数据，可以使用 head() 或者 tail() 方法。其中 head() 返回前 n 行数据，默认显示前 5 行数据。示例如下：

```
import pandas as pd
import numpy as np
d = {'Name':pd.Series(['c语言中文网','编程帮',"百度",'360搜索','谷歌','微学苑','Bing搜索']),
   'years':pd.Series([5,6,15,28,3,19,23]),
   'Rating':pd.Series([4.23,3.24,3.98,2.56,3.20,4.6,3.8])}
#构建DataFrame
df = pd.DataFrame(d)
#获取前3行数据
print(df.head(3))
```

输出结果：

```
     Name       years   Rating
0   c语言中文网      5     4.23
1    编程帮         6     3.24
2    百度          15     3.98
```

tail() 返回后 n 行数据，示例如下：

```
import pandas as pd
import numpy as np
d = {'Name':pd.Series(['c语言中文网','编程帮',"百度",'360搜索','谷歌','微学苑','Bing搜索']),
   'years':pd.Series([5,6,15,28,3,19,23]),
   'Rating':pd.Series([4.23,3.24,3.98,2.56,3.20,4.6,3.8])}
#构建DataFrame
df = pd.DataFrame(d)
#获取后2行数据
print(df.tail(2))
```

输出结果：

```
      Name     years   Rating
5     微学苑      19     4.6
6    Bing搜索     23     3.8
```

### 10) shift()移动行或列

如果您想要移动 DataFrame 中的某一行/列，可以使用 shift() 函数实现。它提供了一个`periods`参数，该参数表示在特定的轴上移动指定的步幅。

shif() 函数的语法格式如下：

```
DataFrame.shift(periods=1, freq=None, axis=0)  
```

参数说明如下：



| 参数名称   | 说明                                                         |
| ---------- | ------------------------------------------------------------ |
| peroids    | 类型为int，表示移动的幅度，可以是正数，也可以是负数，默认值为1。 |
| freq       | 日期偏移量，默认值为None，适用于时间序。取值为符合时间规则的字符串。 |
| axis       | 如果是 0 或者 "index" 表示上下移动，如果是 1 或者 "columns" 则会左右移动。 |
| fill_value | 该参数用来填充缺失值。                                       |


该函数的返回值是移动后的 DataFrame 副本。下面看一组简单的实例：

```
import pandas as pd 
info= pd.DataFrame({'a_data': [40, 28, 39, 32, 18], 
'b_data': [20, 37, 41, 35, 45], 
'c_data': [22, 17, 11, 25, 15]}) 
#移动幅度为3
info.shift(periods=3)  
```

输出结果：

```
   a_data  b_data  c_data
0     NaN     NaN     NaN
1     NaN     NaN     NaN
2     NaN     NaN     NaN
3    40.0    20.0    22.0
4    28.0    37.0    17.0
```

下面使用 fill_value 参数填充 DataFrame 中的缺失值，如下所示：

```
import pandas as pd 
info= pd.DataFrame({'a_data': [40, 28, 39, 32, 18], 
'b_data': [20, 37, 41, 35, 45], 
'c_data': [22, 17, 11, 25, 15]}) 
#移动幅度为3
print(info.shift(periods=3))
#将缺失值和原数值替换为52
info.shift(periods=3,axis=1,fill_value= 52)  
```

输出结果：

```
原输出结果：
   a_data  b_data  c_data
0     NaN     NaN     NaN
1     NaN     NaN     NaN
2     NaN     NaN     NaN
3    40.0    20.0    22.0
4    28.0    37.0    17.0

替换后输出：
   a_data  b_data  c_data
0      52      52      52
1      52      52      52
2      52      52      52
3      52      52      52
4      52      52      52
```

注意：fill_value 参数不仅可以填充缺失值，还也可以对原数据进行替换。

