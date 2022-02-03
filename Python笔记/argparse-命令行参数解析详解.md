# python argparse：命令行参数解析详解
- https://www.cnblogs.com/gmpy/p/11796416.html
- https://geek-docs.com/python/python-tutorial/python-argparse.html
- https://blog.csdn.net/Drievn/article/details/70821188

[toc]

## 1. 简介[#](https://www.cnblogs.com/gmpy/p/11796416.html#2476098359)

本文介绍的是***[argparse](https://docs.python.org/3/library/argparse.html)***模块的基本使用方法，尤其详细介绍**add_argument**内建方法各个参数的使用及其效果。

本文翻译自[argparse的官方说明](https://docs.python.org/3/library/argparse.html)，并加上一些笔者的理解

```Python
# 解析命令行参数
parser = argparse.ArgumentParser(description='./sumDiskFile.py: for disk data')
parser.add_argument('-d', '--dest_dir', type=str, default='/',
                    help='exp: python sumDiskFile.py -d /var/')
args = parser.parse_args()

# args.xxx 获取命令行参数
dest_path = args.dest_dir
```

```Python
import argparse

parser = argparse.ArgumentParser(description='Process some integers.')
parser.add_argument('integers', metavar='N', type=int, nargs='+',
                    help='an integer for the accumulator')
parser.add_argument('--sum', dest='accumulate', action='store_const',
                    const=sum, default=max,
                    help='sum the integers (default: find the max)')

args = parser.parse_args()
print(args.accumulte)
#args.integers

```

如上示例，*argparse*模块的基本使用包含5个步骤：

1. import模块：*import argparse*
2. 获解析器对象：*argparse.ArgumentParser*
3. 利用解析器对象内建方法，添加参数解析规则：*add_argument*
4. 利用解析器对象内建方法，开始解析参数，获取解析结果对象：*parse_args*
5. 从解析结果对象中，获取参数值：*parse_args*

## 2. 解析器类(ArgumentParser)[#](https://www.cnblogs.com/gmpy/p/11796416.html#122656606)

在第2步中，我们通过`ArgumentParser()`函数的调用获取了解析器对象`ArgumentParser`。

在了解解析器对象的各个成员之前，我们先对一段正常的说明文本进行区间划分

```
# usage字段
usage: 程序名 [-h|--help] .....

# Description字段
程序功能描述

# 位置参数说明（必选）
positional arguments:
 ...
 
# 可选参数说明
optional arguments:
...

# 补充说明字段
...
```

例如

```
usage: PROG [-h] [--foo [FOO]] bar [bar ...]

bar help

positional arguments:
 bar          bar help

optional arguments:
 -h, --help   show this help message and exit
 --foo [FOO]  foo help
 
 And that's how you'd foo a bar
```

*关于位置参数与可选参数的理解，参考下一章节：[添加参数解析规则](https://www.cnblogs.com/gmpy/p/11796416.html#关键字name-or-flags)*

在上述的区间划的认识下，我们再来看看解析器对象的成员及其功能

|         名字          |   默认值    | 功能                                       |
| :-------------------: | :---------: | :----------------------------------------- |
|         prog          | sys.argv[0] | `-h`时显示的程序名                         |
|         usage         |      -      | usage字段描述                              |
|      description      |    None     | description字段描述                        |
|        epilog         |    None     | 补充字段描述                               |
|        parents        |    None     | 从父(公共)解析器中继承所有的参数选项       |
|    formatter_class    |    None     | 定制说明文本的显示风格                     |
|     prefix_class      |      -      | 定制前缀字符，例如前缀**"-b"**改为**“+b"** |
|       add_help        |    True     | 是否使能显示参数 `-h --help`               |
|     allow_abbrev      |    True     | 是否支持长参数                             |
| fromfile_prefix_chars |    None     | 略                                         |
|   argument_default    |    None     | 略                                         |
|   conflict_handler    |    None     | 略                                         |

比较常用的是***description***，例如：

```
parser = argparse.ArgumentParser(description='Process some integers.')
```

或者

```
parser = argparse.ArgumentParser()
parser.descritpioin="Process some integers."
```

## 3. 添加参数解析规则(add_argument)[#](https://www.cnblogs.com/gmpy/p/11796416.html#3195818192)

***add_argument***是解析器类***ArgumentParser***的内建方法，用于向解析器添加参数解析规则

```
ArgumentParser.add_argument(name or flags...[, action][, nargs][, const][, default][, type]
		[, choices][, required][, help][, metavar][, dest])
```

内建方法支持以下的**关键字**，我们会对每一个关键字及其效果做进一步说明

| 关键字                                                       | 简介                                                 |
| :----------------------------------------------------------- | :--------------------------------------------------- |
| [name or flags](https://www.cnblogs.com/gmpy/p/11796416.html#关键字name-or-flags) | 参数名或者"-/--"开头的选项，例如`foo`或者`-f, --foo` |
| [action](https://www.cnblogs.com/gmpy/p/11796416.html#关键字action) | 匹配到选项后的行为                                   |
| [nargs](https://www.cnblogs.com/gmpy/p/11796416.html#关键字nargs) | 选项跟随的参数个数                                   |
| [const](https://www.cnblogs.com/gmpy/p/11796416.html#关键字const) | 在某些`action`和`nargs`下，使用的固定值              |
| [default](https://www.cnblogs.com/gmpy/p/11796416.html#关键字default) | 默认值                                               |
| [type](https://www.cnblogs.com/gmpy/p/11796416.html#关键字type) | 参数类型                                             |
| [choices](https://www.cnblogs.com/gmpy/p/11796416.html#关键字choices) | 可选的参数值范围                                     |
| [required](https://www.cnblogs.com/gmpy/p/11796416.html#关键字required) | 选项必选or可选                                       |
| [help](https://www.cnblogs.com/gmpy/p/11796416.html#关键字help) | 参数描述                                             |
| [metavar](https://www.cnblogs.com/gmpy/p/11796416.html#关键字metavar) | 使用说明中显示的参数名                               |
| [dest](https://www.cnblogs.com/gmpy/p/11796416.html#关键字dest) | 选项最终在解析结果对象中的名字                       |

### 3.1 关键字name or flags[#](https://www.cnblogs.com/gmpy/p/11796416.html#4067797408)

**关键字name**是什么？**flags**又是什么？两者有什么差别？

**name表示参数名，其赋值与位置顺序相关，因此也叫位置参数名，命令行中必须赋值**

**flags表示`-|--`开头的参数名，命令行中可选参数**

例如：

```
#可选的flags参数
parser.add_argument('-f', '--foo')
#必选的name位置参数
parser.add_argument('bar0')
parser.add_argument('bar1')
```

这里假设`--foo`需要带1个参数，那么

```
prog arg1 --foo arg2 arg3
```

- arg1 是位置参数bar0的值
- arg2 是可选参数'-f, --foo'的值
- arg3 是位置参数bar1的值

换句话来说，在输入的命令行中，除去所有`-|--`开头的参数及其带上的参数值之外，剩下的参数都为位置参数，其顺序依次对应使用*add_argument*注册的位置参数顺序。

**在命令行调用中，位置参数必须赋值，即每个必选参数都要有赋值；而可选参数`(-|--)`可根据需求选择**

### 3.2 关键字action[#](https://www.cnblogs.com/gmpy/p/11796416.html#244161658)

功能如其名，**关键字action**控制匹配到命令行选项后的行为。参数解析后不是保存值就好了？我们继续看看。

**关键字action**只支持以下值，我们稍后再详细讲解每一个值的含义：

| 值           | 含义                                                     |
| :----------- | :------------------------------------------------------- |
| store        | 保存参数值                                               |
| store_const  | 与**关键字const**配合使用，保存**关键字const**的值       |
| store_true   | 保存值为*True*                                           |
| store_false  | 保存值为*False*                                          |
| append       | 保存多次选项值为列表                                     |
| append_const | 与**关键字const**配合使用，保存**关键字const**的值为列表 |
| const        | 保存选项的出现次数                                       |
| help         | 效果等效于`-h`和 `--help`                                |
| version      | 打印版本                                                 |

#### 3.2.1 store[#](https://www.cnblogs.com/gmpy/p/11796416.html#2843221963)

保存参数值，这也是默认的行为，我们看个例子：

```python
>>> parser = argparse.ArgumentParser()
>>> parser.add_argument('--foo')
>>> parser.parse_args('--foo 1'.split())
Namespace(foo='1')
```

在`parse_args()`之后，`--foo`选项的值就保存到了`parse_args()`的解析结果对象中。

我们可以这样获取解析结果对象的值：

```python
args = parser.parse_args('--foo 1'.split())
print(args.foo)
```

总结来说，**选项名成为了解析结果对象的成员，而选项对应的值则成了成员的值**

到这里，我们存在2个疑惑：

1. 当同时支持`-f`和`--foo`时，生成的成员名是什么呢？能自定义名字么？
2. 当`PROG -f`这样不需要带参数的选项，存储的成员值是什么样的呢？

不用急，我们继续往下看

#### 3.2.2 store_const[#](https://www.cnblogs.com/gmpy/p/11796416.html#2103056749)

在匹配到选项后，存储[**关键字const**](https://www.cnblogs.com/gmpy/p/11796416.html#关键字const)的值，常用于命令行中不带参数的选项。例如：

```python
>>> parser = argparse.ArgumentParser()
>>> parser.add_argument('--foo', action='store_const', const=42)
>>> parser.parse_args(['--foo'])
Namespace(foo=42)
```

从上面的例子，我们可以看到，在匹配到`--foo`后，对应成员foo的保存为了`const参数`的值。

但更多时候，我们不带参数的选项更多只是表示布尔型的开关，这时候我们可用`store_true`或者`store_false`

#### 3.2.3 store_true 和 store_false[#](https://www.cnblogs.com/gmpy/p/11796416.html#776140246)

`store_true`和`store_false`是一种特殊的`store_const`，存储的值类型为布尔型`True`或`False`。例如：

```python
>>> parser = argparse.ArgumentParser()
>>> parser.add_argument('--foo', action='store_true')
>>> parser.add_argument('--bar', action='store_false')
>>> parser.add_argument('--baz', action='store_false')
>>> parser.parse_args('--foo --bar'.split())
Namespace(foo=True, bar=False, baz=True)
```

示例中有3个布尔型“开关”，可以发现有以下特点：

1. `store_true`在匹配到命令选项后，保存为`True`；相对的，`store_false`保存为`False`
2. 当命令行中没匹配到开关后，例如示例中的`baz`，则保存为`store_false`的相反值`True`

#### 3.2.4 append[#](https://www.cnblogs.com/gmpy/p/11796416.html#4045350020)

把所有值保存为一个列表，常用于支持多次选项的情况，例如：

```python
>>> parser = argparse.ArgumentParser()
>>> parser.add_argument('--foo', action='append')
>>> parser.parse_args('--foo 1 --foo 2'.split())
Namespace(foo=['1', '2'])
```

#### 3.2.5 append_const[#](https://www.cnblogs.com/gmpy/p/11796416.html#2621288067)

与`store_const`非常相似，只是把值存储为一个列表。此时如果没有指定**关键字const**，则默认为`None`。`append_const`常用于多个不同选项值需要存储到相同成员列表的情况。例如：

```python
>>> parser = argparse.ArgumentParser()
>>> parser.add_argument('--str', dest='types', action='append_const', const=str)
>>> parser.add_argument('--int', dest='types', action='append_const', const=int)
>>> parser.parse_args('--str --int'.split())
Namespace(types=[<class 'str'>, <class 'int'>])
```

理解上文需要知道以下前提：

1. [**关键字dest**](https://www.cnblogs.com/gmpy/p/11796416.html#关键字dest)用于指定成员名
2. 参数的值可以是各种对象，包括类型对象，即示例中的**str类型**和**int类型**

在示例中，`--str`的常量值是***str类型***，以列表形式保存到**types成员**；`--int`的常量值是***int类型***，以列表形式保存到**types成员**

#### 3.2.6 count[#](https://www.cnblogs.com/gmpy/p/11796416.html#3076375404)

如果我们只需要统计**选项**出现的次数，此处可以用`count`，例如：

```python
>>> parser = argparse.ArgumentParser()
>>> parser.add_argument('--verbose', '-v', action='count')
>>> parser.parse_args(['-vvv'])
Namespace(verbose=3)
```

#### 3.2.7 help[#](https://www.cnblogs.com/gmpy/p/11796416.html#2730350691)

打印帮助信息，功能等效于`-h|--help`

#### 3.2.8 version[#](https://www.cnblogs.com/gmpy/p/11796416.html#46148814)

打印版本信息，需要配合**关键字version**使用，例如：

```python
>>> import argparse
>>> parser = argparse.ArgumentParser(prog='PROG')
>>> parser.add_argument('--version', action='version', version='%(prog)s 2.0')
>>> parser.parse_args(['--version'])
PROG 2.0
```

### 3.3 关键字nargs[#](https://www.cnblogs.com/gmpy/p/11796416.html#2635837190)

**关键字nargs**是*number argumemts*的缩写，表示选项有多少个参数，其支持以下值：

|         值         | 含义              |
| :----------------: | :---------------- |
|   N (an integer)   | 收集N个参数到列表 |
|        '?'         | 无参数或单个参数  |
|        '*'         | 大于等于0个参数   |
|        '+'         | 大于等于1个参数   |
| argparse.REMAINDER | 只收集不解析      |

当没有指定**关键字nargs**时，其实际的值取决于**关键字action**，例如当`action = "store"`时，默认获取1个参数，当`action = "store_true"`时，选项不需要带参数。

#### 3.3.1 N (an integer)[#](https://www.cnblogs.com/gmpy/p/11796416.html#2107116361)

此处的`N`，表示一个整型数字，含义为选项需要提供N个参数。例如：

```python
>>> parser = argparse.ArgumentParser()
>>> parser.add_argument('--foo', nargs=2)
>>> parser.add_argument('bar', nargs=1)
>>> parser.parse_args('c --foo a b'.split())
Namespace(bar=['c'], foo=['a', 'b'])
```

需要注意的是，当`nargs = 1`时，其并不等效于**关键字nargs**的默认情况。例如：

```python
>>> parser = argparse.ArgumentParser()
>>> parser.add_argument('bar0', nargs=1)
>>> parser.add_argument('bar1')
>>> parser.parse_args('a b'.split())
Namespace(bar0=['a'], bar1='b')
```

可以发现，`nargs = 1`时，例如bar0，其值是一个列表，一个只有1个元素的列表；而默认情况下，就是一个元素，官方称为`item`。

#### 3.3.2 '?'[#](https://www.cnblogs.com/gmpy/p/11796416.html#3983361694)

`nargs='?'`可以实现3种场景：

1. 输入的命令行中，选项带参数时，值为附带的参数
2. 输入的命令行中，没有改选项时，值为**关键字default**的值
3. 输入的命令行中，有选项但没带参数时，值为**关键字const**的值（只适用于可选选项(flag)）

例如：

```python
>>> parser = argparse.ArgumentParser()
>>> parser.add_argument('--foo', nargs='?', const='c', default='d')
>>> parser.add_argument('bar', nargs='?', default='d')
>>> parser.parse_args(['XX', '--foo', 'YY'])
Namespace(bar='XX', foo='YY')
>>> parser.parse_args(['XX', '--foo'])
Namespace(bar='XX', foo='c')
>>> parser.parse_args([])
Namespace(bar='d', foo='d')
```

一个更常用的场景，是实现可选的输入输出文件，例如：

```python
>>> parser = argparse.ArgumentParser()
>>> parser.add_argument('infile', nargs='?', type=argparse.FileType('r'),
...                     default=sys.stdin)
>>> parser.add_argument('outfile', nargs='?', type=argparse.FileType('w'),
...                     default=sys.stdout)
>>> parser.parse_args(['input.txt', 'output.txt'])
Namespace(infile=<_io.TextIOWrapper name='input.txt' encoding='UTF-8'>,
          outfile=<_io.TextIOWrapper name='output.txt' encoding='UTF-8'>)
>>> parser.parse_args([])
Namespace(infile=<_io.TextIOWrapper name='<stdin>' encoding='UTF-8'>,
          outfile=<_io.TextIOWrapper name='<stdout>' encoding='UTF-8'>)
```

上述例子中，如果命令行没有指定input，则使用标准输入stdin；如果命令行没有指定output，则使用标准输出stdout

#### 3.3.3 '*'[#](https://www.cnblogs.com/gmpy/p/11796416.html#1513399385)

`nargs=2`会限制一定要有2个参数，如果需要任意多个参数呢？我们可以用`nargs='*'`，例如：

```python
>>> parser = argparse.ArgumentParser()
>>> parser.add_argument('--foo', nargs='*')
>>> parser.add_argument('--bar', nargs='*')
>>> parser.add_argument('baz', nargs='*')
>>> parser.parse_args('a b --foo x y --bar 1 2'.split())
Namespace(bar=['1', '2'], baz=['a', 'b'], foo=['x', 'y'])
```

与`nargs=N`相似，最终的值是列表类型。

#### 3.3.4 '+'[#](https://www.cnblogs.com/gmpy/p/11796416.html#1693491135)

`nargs='+'`与`nargs='*'`从功能上非常相似，唯一不同的地方在于，`nargs='+'`要求至少要有1个参数，否则会报错。

`'?'`，`'+'`与`'*'`的定义与正则表达式中的`？`，`+`和`*`非常相似

在正则表达式中，

- ?：表示0或1个字符
- +：表示大于等于1个字符
- *：表示大于等于0个字符

我们看个例子：

```python
>>> parser = argparse.ArgumentParser(prog='PROG')
>>> parser.add_argument('foo', nargs='+')
>>> parser.parse_args(['a', 'b'])
Namespace(foo=['a', 'b'])
>>> parser.parse_args([])
usage: PROG [-h] foo [foo ...]
PROG: error: the following arguments are required: foo
```

#### 3.3.5 argparse.REMAINDER[#](https://www.cnblogs.com/gmpy/p/11796416.html#4057565195)

`nargs=argparse.REMAINDER`常用于收集参数后传递给其他的命令行解析工具，其不会解析`-|--`，只是收集所有选项到列表。

例如：

```python
>>> parser = argparse.ArgumentParser(prog='PROG')
>>> parser.add_argument('--foo')
>>> parser.add_argument('command')
>>> parser.add_argument('args', nargs=argparse.REMAINDER)
>>> print(parser.parse_args('--foo B cmd --arg1 XX ZZ'.split()))
Namespace(args=['--arg1', 'XX', 'ZZ'], command='cmd', foo='B')
```

上例中，**argparse**没有解析`args`选项的`--arg1`，而是全部收集到了一个列表

### 3.4 关键字const[#](https://www.cnblogs.com/gmpy/p/11796416.html#3093950347)

在**关键字acton**和**关键字nargs**中，其实已经涉及了**关键字const**的所有功能。

**关键字const**只是存储一个常量值，在以下两种情况下才会使用：

1. [`action='store_const'`](https://www.cnblogs.com/gmpy/p/11796416.html#store_const)或者[`action='append_const'`](https://www.cnblogs.com/gmpy/p/11796416.html#sppend_const)
2. [`nargs='?'`](https://www.cnblogs.com/gmpy/p/11796416.html#'?')

当使用`action='store_const'`和`action='append_const'`时，**关键字const**必须提供，对其他的**action关键字**时，默认值为`None`

### 3.5 关键字default[#](https://www.cnblogs.com/gmpy/p/11796416.html#4249287770)

有时候，选项不带参数，或者命令行没对应选项，这时候就可以使用默认值，而**关键字default**存储的就是默认值。默认情况下，**关键字default**的值为`None`。例如：

```python
>>> parser = argparse.ArgumentParser()
>>> parser.add_argument('--foo', default=42)
>>> parser.parse_args(['--foo', '2'])
Namespace(foo='2')
>>> parser.parse_args([])
Namespace(foo=42)
```

如果**关键字default**赋值的是字符串，而**关键字type**有指定参数类型，那么就会把字符串转为**关键字type**指定的类型，例如：

```python
>>> parser = argparse.ArgumentParser()
>>> parser.add_argument('--length', default='10', type=int)
>>> parser.add_argument('--width', default=10.5, type=int)
>>> parser.parse_args()
Namespace(length=10, width=10.5)
```

如果[**关键字nargs**](https://www.cnblogs.com/gmpy/p/11796416.html#关键字nargs)为`?`或者`*`，那么default的值会在命令行没有参数时使用，例如：

```python
>>> parser = argparse.ArgumentParser()
>>> parser.add_argument('foo', nargs='?', default=42)
>>> parser.parse_args(['a'])
Namespace(foo='a')
>>> parser.parse_args([])
Namespace(foo=42)
```

**关键字default**也提供一种特殊用法：`default=argparse.SUPPRESS`。在这种情况下，如果命令行并没有匹配的选项，那么并不会在**解析结果对象**中添加选项对应的成员，例如：

```python
>>> parser = argparse.ArgumentParser()
>>> parser.add_argument('--foo', default=argparse.SUPPRESS)
>>> parser.parse_args([])
Namespace()
>>> parser.parse_args(['--foo', '1'])
Namespace(foo='1')
```

### 3.6 关键字type[#](https://www.cnblogs.com/gmpy/p/11796416.html#183950447)

默认情况下，**argparse**解析的参数默认为**字符串**类型，当然也可以通过**关键字type**指定其他任何类型，例如`float`，`int`，甚至是文件类型`file`， 例如：

```python
>>> parser = argparse.ArgumentParser()
>>> parser.add_argument('foo', type=int)
>>> parser.add_argument('bar', type=open)
>>> parser.parse_args('2 temp.txt'.split())
Namespace(bar=<_io.TextIOWrapper name='temp.txt' encoding='UTF-8'>, foo=2)
```

如果**关键字type**指定的是文件类型，我们还可以通过```FileType('w')以可写形式打开文件，例如：

```python
>>> parser = argparse.ArgumentParser()
>>> parser.add_argument('bar', type=argparse.FileType('w'))
>>> parser.parse_args(['out.txt'])
Namespace(bar=<_io.TextIOWrapper name='out.txt' encoding='UTF-8'>)
```

**关键字type**甚至能指定为函数，经过函数处理后的返回值作为参数值，例如：

```python
>>> def perfect_square(string):
...     value = int(string)
...     sqrt = math.sqrt(value)
...     if sqrt != int(sqrt):
...         msg = "%r is not a perfect square" % string
...         raise argparse.ArgumentTypeError(msg)
...     return value
...
>>> parser = argparse.ArgumentParser(prog='PROG')
>>> parser.add_argument('foo', type=perfect_square)
>>> parser.parse_args(['9'])
Namespace(foo=9)
>>> parser.parse_args(['7'])
usage: PROG [-h] foo
PROG: error: argument foo: '7' is not a perfect square
```

### 3.7 关键字choices[#](https://www.cnblogs.com/gmpy/p/11796416.html#1010216434)

当我们需要限制选项的值范围，我们可以用**关键字choices**。**关键字choices**限定了参数值的可选列表，如果命令行提供的参数值不在列表中，则会报错，例如：

```python
>>> parser = argparse.ArgumentParser(prog='game.py')
>>> parser.add_argument('move', choices=['rock', 'paper', 'scissors'])
>>> parser.parse_args(['rock'])
Namespace(move='rock')
>>> parser.parse_args(['fire'])
usage: game.py [-h] {rock,paper,scissors}
game.py: error: argument move: invalid choice: 'fire' (choose from 'rock',
'paper', 'scissors')
```

当然，需要注意的是，**关键字choice**的值必须符合**关键字type**指定的类型。

### 3.8 关键字required[#](https://www.cnblogs.com/gmpy/p/11796416.html#2163854771)

默认情况下，`-f`和`--foo`都是可选的，但如果需要改为**必选**，可以使用**关键字required**，例如：

```python
>>> parser = argparse.ArgumentParser()
>>> parser.add_argument('--foo', required=True)
>>> parser.parse_args(['--foo', 'BAR'])
Namespace(foo='BAR')
>>> parser.parse_args([])
usage: argparse.py [-h] [--foo FOO]
argparse.py: error: option --foo is required
```

### 3.9 关键字help[#](https://www.cnblogs.com/gmpy/p/11796416.html#1465453742)

**关键字help**是选项的说明，在`-h`或者`--help`时会显示出来，例如：

```python
>>> parser = argparse.ArgumentParser(prog='frobble')
>>> parser.add_argument('--foo', action='store_true',
...                     help='foo the bars before frobbling')
>>> parser.add_argument('bar', nargs='+',
...                     help='one of the bars to be frobbled')
>>> parser.parse_args(['-h'])
usage: frobble [-h] [--foo] bar [bar ...]

positional arguments:
 bar     one of the bars to be frobbled

optional arguments:
 -h, --help  show this help message and exit
 --foo   foo the bars before frobbling
```

当然，**关键字help**也支持格式化显示，`%(prog)s`和大部分**add_argument()**的关键字，包括`%(default)s`，`%(type)s`，等等，例如：

```python
>>> parser = argparse.ArgumentParser(prog='frobble')
>>> parser.add_argument('bar', nargs='?', type=int, default=42,
...                     help='the bar to %(prog)s (default: %(default)s)')
>>> parser.print_help()
usage: frobble [-h] [bar]

positional arguments:
bar     the bar to frobble (default: 42)

optional arguments:
-h, --help  show this help message and exit
```

格式为`%(keyword)s`，如果需要显示`%`，就需要使用`%%`

还存在一种特殊情况，假设我们不希望参数显示在*--help*中，我们可以用：**argparse.SUPPRESS**，例如：

```python
>>> parser = argparse.ArgumentParser(prog='frobble')
>>> parser.add_argument('--foo', help=argparse.SUPPRESS)
>>> parser.print_help()
usage: frobble [-h]

optional arguments:
 -h, --help  show this help message and exit
```

### 3.10 关键字dest[#](https://www.cnblogs.com/gmpy/p/11796416.html#381107755)

**argparse**会把解析的结果保存成*解析结果对象*的属性，但是，属性名是什么呢？例如，`parser.add_argument(’-f', '--foo')`，解析结果是保存在属性`f`中还是`foo`中？**关键字dest**就是用于定制属性名的。

我们先了解默认情况下，属性名是什么？

**对位置参数而言，关键字dest默认为第一个参数名**，例如：

```python
>>> parser = argparse.ArgumentParser()
>>> parser.add_argument('bar')
>>> parser.parse_args(['XXX'])
Namespace(bar='XXX')
```

**对可选参数而言，关键字dest首选第一个出现的长参数名。如果没有长参数，则选择第一个短参数名。不管选择的是长参数还是短参数，都会把`-|---`给去掉，同时把名字中的`-`符号替换为`_`，以符合python的变量命名规则**，例如：

```python
>>> parser = argparse.ArgumentParser()
>>> parser.add_argument('-f', '--foo-bar', '--foo')
>>> parser.add_argument('-x', '-y')
>>> parser.parse_args('-f 1 -x 2'.split())
Namespace(foo_bar='1', x='2')
>>> parser.parse_args('--foo 1 -y 2'.split())
Namespace(foo_bar='1', x='2')
```

我们再看看，如果定制属性名有什么效果？

```python
>>> parser = argparse.ArgumentParser()
>>> parser.add_argument('--foo', dest='bar')
>>> parser.parse_args('--foo XXX'.split())
Namespace(bar='XXX')
```

### 3.11 关键字metavar[#](https://www.cnblogs.com/gmpy/p/11796416.html#45554549)

在执行`-h|--help`，显示的帮助信息中，如何定制选项带的参数名呢？例如：

```
-t T  	loop times
```

我希望修改显示的`T`为`TIMES`，更直观。这时候我们就可以使用**关键字metavar**了。

在默认情况下，对**位置参数**，则会直接显示参数名，对**可选参数**，则会转为大写。例如：

```python
>>> parser = argparse.ArgumentParser()
>>> parser.add_argument('--foo')
>>> parser.add_argument('bar')
>>> parser.parse_args('X --foo Y'.split())
Namespace(bar='X', foo='Y')
>>> parser.print_help()
usage:  [-h] [--foo FOO] bar

positional arguments:
 bar

optional arguments:
 -h, --help  show this help message and exit
 --foo FOO
```

上例中，**位置参数bar**直接显示为*bar*，而**可选参数--foo**带的参数名就转大写显示*FOO*。

如果我们定制显示的参数名，该怎么做呢？

```python
>>> parser = argparse.ArgumentParser()
>>> parser.add_argument('--foo', metavar='YYY')
>>> parser.add_argument('bar', metavar='XXX')
>>> parser.parse_args('X --foo Y'.split())
Namespace(bar='X', foo='Y')
>>> parser.print_help()
usage:  [-h] [--foo YYY] XXX

positional arguments:
XXX

optional arguments:
-h, --help  show this help message and exit
--foo YYY
```

当然，还存在一种特殊情况，就是有多个参数`nargs=N`，这时候**关键字metavar**可以以列表形式提供啦，例如：

```python
>>> parser = argparse.ArgumentParser(prog='PROG')
>>> parser.add_argument('-x', nargs=2)
>>> parser.add_argument('--foo', nargs=2, metavar=('bar', 'baz'))
>>> parser.print_help()
usage: PROG [-h] [-x X X] [--foo bar baz]

optional arguments:
-h, --help     show this help message and exit
-x X X
--foo bar baz
```

最后，有一点要注意的时，**关键字metavar**与**关键字dest**不一样的地方在于，**关键字metavar仅仅只影响-h|--help的显示效果，关键dest则同时影响解析结果属性名**

## 4. 获取解析结果[#](https://www.cnblogs.com/gmpy/p/11796416.html#28268116)

在[*action='store'*](https://www.cnblogs.com/gmpy/p/11796416.html#store)中提到：**选项名成为了解析结果对象的成员，而选项对应的值则成了成员的值**，所以如果我们需要获取解析后的结果，直接使用解析结果的成员值就好了。例如：

```python
>>> parser = argparse.ArgumentParser(prog='PROG')
>>> parser.add_argument('-x', dest='xyz')
>>> parser.add_argument('--foo', nargs=2, metavar=('bar', 'baz'))
>>> args = parser.parse_args("--foo a b -x c".split())
>>> print(args.foo)
>>> ['a', 'b']
>>> print(args.xyz)
>>> c
```