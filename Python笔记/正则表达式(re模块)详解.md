# 正则表达式(re模块)详解

https://blog.csdn.net/guo_qingxia/article/details/113979135

[toc]

在[Python](https://so.csdn.net/so/search?from=pc_blog_highlight&q=Python)中需要通过正则表达式对字符串进⾏匹配的时候，可以使⽤⼀个python自带的模块，名字为re。

正则表达式的大致匹配过程是：
1.依次拿出表达式和文本中的字符比较，
2.如果每一个字符都能匹配，则匹配成功；一旦有匹配不成功的字符则匹配失败。
3.如果表达式中有量词或边界，这个过程会稍微有一些不同。

r：在带有 `'r'` 前缀的字符串字面值中，反斜杠不必做任何特殊处理。 因此 `r"\n"` 表示包含 `'\'` 和 `'n'` 两个字符的字符串，而 `"\n"` 则表示只包含一个换行符的字符串。

re模块的使用：import re

## **re.match函数**

语法：re.match(pattern, string, flags=0)

| pattern | 匹配的正则表达式                                             |
| ------- | ------------------------------------------------------------ |
| string  | 要匹配的字符串                                               |
| flags   | 标志位，用于控制正则表达式的匹配方式，如：是否区分大小写，多行匹配等等。<br />**re.I** 忽略大小写<br />**re.L** 表示特殊字符集 \w, \W, \b, \B, \s, \S 依赖于当前环境<br />**re.M** 多行模式<br />**re.S** 即为 . 并且包括换行符在内的任意字符（. 不包括换行符）<br />**re.U** 表示特殊字符集 \w, \W, \b, \B, \d, \D, \s, \S 依赖于 Unicode 字符属性数据库<br />**re.X** 为了增加可读性，忽略空格和 # 后面的注释 |

尝试从字符串的起始位置匹配一个模式，如果不是起始位置匹配成功的话，match()就返回none。匹配成功re.match方法返回一个匹配的对象。

如果上⼀步匹配到数据的话，可以使⽤group⽅法来提取数据。以使用group(num) 或 groups() 匹配对象函数来获取匹配表达式。

group()用来提出分组截获的字符串**，**（）用来分组，group() 同group（0）就是匹配正则表达式整体结果，group(1) 列出第一个括号匹配部分，group(2) 列出第二个括号匹配部分，group(3) 列出第三个括号匹配部分。没有匹配成功的，re.search()返回None。

举例：

```python
>>> import re
>>> result = re.match("itcast","itcast.cn")
>>> result.group()
'itcast'
```

从string头开始匹配pattern完全可以匹配，pattern匹配结束，同时匹配终止，后面的.cn不再匹配，返回匹配成功的信息。

### **匹配单个字符**

| 字符 | 功能                                | 位置                  |
| ---- | ----------------------------------- | --------------------- |
| .    | 匹配任意1个字符（除了\n）           |                       |
| [ ]  | 匹配[ ]中列举的字符                 |                       |
| \d   | 匹配数字，即0-9                     | 可以写在字符集[...]中 |
| \D   | 匹配⾮数字，即不是数字              | 可以写在字符集[...]中 |
| \s   | 匹配空⽩，即空格，tab键             | 可以写在字符集[...]中 |
| \S   | 匹配⾮空⽩字符                      | 可以写在字符集[...]中 |
| \w   | 匹配单词字符，即a-z、A-Z、0-9、_    | 可以写在字符集[...]中 |
| \W   | 匹配⾮单词字符                      | 可以写在字符集[...]中 |
| \w   | \w 匹配单词字符，即a-z、A-Z、0-9、_ |                       |
| \W   | 匹配⾮单词字符                      |                       |

[...]字符集，对应的位置可以是字符集中任意字符。字符集中的字符可以逐个列出，也可以给出范围，比如[abc]和[a-c]，第一个字符如果是^表示取反。所有特殊字符（比如"]""-""^"）在字符集中都失去原来的含义，如要使用可把"]""-"放在第一个字符，"^"放在非第一个字符。

举例：

```swift
import re
ret = re.match(".","M")
print(ret.group())
ret = re.match("t.o","too")
print(ret.group())
ret = re.match("t.o","two")
print(ret.group())

# 如果hello的⾸字符⼩写，那么正则表达式需要⼩写的h
ret = re.match("h","hello Python")
print(ret.group())

# 如果hello的⾸字符⼤写，那么正则表达式需要⼤写的H
ret = re.match("H","Hello Python")
print(ret.group())

# ⼤⼩写h都可以的情况
ret = re.match("[hH]","hello Python")
print(ret.group())
ret = re.match("[hH]","Hello Python")
print(ret.group())
ret = re.match("[hH]ello Python","Hello Python")
print(ret.group())

# 匹配0到9的多种写法
ret = re.match("[0123456789]Hello Python","7Hello Python")
print(ret.group())
ret = re.match("[0-9]Hello Python","7Hello Python")
print(ret.group())

# 匹配0到3和5-9
ret = re.match("[0-35-9]Hello Python","7Hello Python")
print(ret.group())
ret = re.match("[0-35-9]Hello Python","4Hello Python")
#print(ret.group())
ret = re.match("嫦娥\d号","嫦娥1号发射成功")
print(ret.group())
ret = re.match("嫦娥\d号","嫦娥2号发射成功")
print(ret.group())
```

结果：

```
M
too
two
h
H
h
H
Hello Python
7Hello Python
7Hello Python
7Hello Python
嫦娥1号
嫦娥2号
```



### **匹配多个字符**

| 字符  | 功能                                                         | 位置                | 表达式实例 | 完整匹配的字符串 |
| ----- | ------------------------------------------------------------ | ------------------- | ---------- | ---------------- |
| *     | 匹配前⼀个字符出现0次或者⽆限次，即可有可⽆                  | 用在字符或(...)之后 | abc*       | abccc            |
| +     | 匹配前⼀个字符出现1次或者⽆限次，即⾄少有1次                 | 用在字符或(...)之后 | abc+       | abccc            |
| ?     | 匹配前⼀个字符出现1次或者0次，即要么有1次，要么没有          | 用在字符或(...)之后 | abc?       | ab,abc           |
| {m}   | 匹配前⼀个字符出现m次                                        | 用在字符或(...)之后 | ab{2}c     | abbc             |
| {m,n} | 匹配前⼀个字符出现从m到n次，若省略m，则匹配0到n次，若省略n，则匹配m到无限次 | 用在字符或(...)之后 | ab{1,2}c   | abc,abbc         |

举例：

```perl
import re

#：匹配出，⼀个字符串第⼀个字⺟为⼤写字符，后⾯都是⼩写字⺟并且这些⼩写字⺟可有可⽆
ret = re.match("[A-Z][a-z]*","M")
print(ret.group())
ret = re.match("[A-Z][a-z]*","MnnM")
print(ret.group())
ret = re.match("[A-Z][a-z]*","Aabcdef")
print(ret.group())

#匹配出，变量名是否有效
names = ["name1", "_name", "2_name", "__name__"]
for name in names:
    ret = re.match("[a-zA-Z_]+[\w]*",name)
    if ret:
        print("变量名 %s 符合要求" % ret.group())
    else:
        print("变量名 %s ⾮法" % name)
        
#匹配出，0到99之间的数字
ret = re.match("[1-9]?[0-9]","7")
print(ret.group())
ret = re.match("[1-9]?\d","33")
print(ret.group())

# 这个结果并不是想要的，利⽤$才能解决
ret = re.match("[1-9]?\d","09")
print(ret.group())
ret = re.match("[a-zA-Z0-9_]{6}","12a3g45678")
print(ret.group())

#匹配出，8到20位的密码，可以是⼤⼩写英⽂字⺟、数字、下划线
ret = re.match("[a-zA-Z0-9_]{8,20}","1ad12f23s34455ff66")
print(ret.group())
```

结果：

```
M
Mnn
Aabcdef
变量名 name1 符合要求
变量名 _name 符合要求
变量名 2_name ⾮法
变量名 __name__ 符合要求
7
33
0
12a3g4
1ad12f23s34455ff66
```



### **匹配开头结尾**

| 字符 | 功能           |
| ---- | -------------- |
| ^    | 匹配字符串开头 |
| $    | 匹配字符串结尾 |

举例：匹配163.com的邮箱地址

```perl
import re

email_list = ["xiaoWang@163.com", "xiaoWang@163.comheihei", ".com.xiaowang@qq.com"]
for email in email_list:
    ret = re.match("[\w]{4,20}@163\.com$", email)
    if ret:
        print("%s 是符合规定的邮件地址,匹配后的结果是:%s" % (email, ret.group()))
    else:
        print("%s 不符合要求" % email)
```

结果：

```
xiaoWang@163.com 是符合规定的邮件地址,匹配后的结果是:xiaoWang@163.com
xiaoWang@163.comheihei 不符合要求
.com.xiaowang@qq.com 不符合要求
```



### **匹配分组**

| 字符       | 功能                                                         |
| ---------- | ------------------------------------------------------------ |
| \|         | 匹配左右任意⼀个表达式                                       |
| (ab)       | 将括号中字符作为⼀个分组                                     |
| \num       | 引⽤分组num匹配到的字符串                                    |
| (?P<name>) | 分组起别名，匹配到的子串组在外部是通过定义的 *name* 来获取的 |
| (?P=name)  | 引⽤别名为name分组匹配到的字符串                             |

举例：|

```python
#匹配出0-100之间的数字
import re

ret = re.match("[1-9]?\d$|100","8")
print(ret.group()) # 8
ret = re.match("[1-9]?\d$|100","78")
print(ret.group()) # 78
ret = re.match("[1-9]?\d$|100","08")

# print(ret.group()) # 不是0-100之间
ret = re.match("[1-9]?\d$|100","100")
print(ret.group()) # 100
```

举例：()

```perl
#需求：匹配出163、126、qq邮箱

ret = re.match("\w{4,20}@163\.com", "test@163.com")
print(ret.group()) # test@163.com
ret = re.match("\w{4,20}@(163|126|qq)\.com", "test@126.com")
print(ret.group()) # test@126.com
ret = re.match("\w{4,20}@(163|126|qq)\.com", "test@qq.com")
print(ret.group()) # test@qq.com
ret = re.match("\w{4,20}@(163|126|qq)\.com", "test@gmail.com")
if ret:
    print(ret.group())
else:
    print("不是163、126、qq邮箱") # 不是163、126、qq邮箱

#不是以4、7结尾的⼿机号码(11位)
tels = ["13100001234", "18912344321", "10086", "18800007777"]
for tel in tels:
    ret = re.match("1\d{9}[0-35-68-9]", tel)
    if ret:
        print(ret.group())
    else:
        print("%s 不是想要的⼿机号" % tel)

#提取区号和电话号码
ret = re.match("([^-]*)-(\d+)","010-12345678")
print(ret.group())
print(ret.group(1))
print(ret.group(2))
```

举例：\number

匹配数字代表的组合。每个括号是一个组合，组合从1开始编号。比如 `(.+) \1` 匹配 `'the the'` 或者 `'55 55'`, 但不会匹配 `'thethe'` (注意组合后面的空格)。这个特殊序列只能用于匹配前面99个组合。如果 *number* 的第一个数位是0， 或者 *number* 是三个八进制数，它将不会被看作是一个组合，而是八进制的数字值。在 `'['` 和 `']'` 字符集合内，任何数字转义都被看作是字符。

```html
例子1：匹配出 <html>hh</html>
```

\1,...,\9，匹配第n个分组的内容。如例子所示，指匹配第一个分组的内容。

```python
import re
# 正确的理解思路：如果在第⼀对<>中是什么，按理说在后⾯的那对<>中就应该是什么。通过引⽤分组中匹配到的数据即可，但是要注意是元字符串，即类似 r""这种格式。
ret = re.match(r"<([a-zA-Z]*)>\w*</\1>", "<html>hh</html>")

# 因为2对<>中的数据不⼀致，所以没有匹配出来
test_label = ["<html>hh</html>","<html>hh</htmlbalabala>"]
for label in test_label:
    ret = re.match(r"<([a-zA-Z]*)>\w*</\1>", label)
    if ret:
        print("%s 这是一对正确的标签" % ret.group())
    else:
        print("%s 这是⼀对不正确的标签" % label)
```

  结果：

```
<html>hh</html> 这是一对正确的标签
<html>hh</htmlbalabala> 这是⼀对不正确的标签
```

  例子2：匹配出 <html><h1>www.itcast.cn</h1></html>

```python
import re
labels = ["<html><h1>www.itcast.cn</h1></html>", "<html><h1>www.itcast.cn</h2></html>"]
for label in labels:
    ret = re.match(r"<(\w*)><(\w*)>.*</\2></\1>", label)
    if ret:
        print("%s 是符合要求的标签" % ret.group())
    else:
        print("%s 不符合要求" % label)
```

  结果：

```
<html><h1>www.itcast.cn</h1></html> 是符合要求的标签
<html><h1>www.itcast.cn</h2></html> 不符合要求
```


举例：(?P<name>) (?P=name)

一个用于标记，一个用于在同一个正则表达式中复用

```python
import re
ret = re.match(r"<(?P<name1>\w*)><(?P<name2>\w*)>.*</(?P=name2)></(?P=name1)>","<html><h1>www.itcast.cn</h1></html>")
ret.group()
ret = re.match(r"<(?P<name1>\w*)><(?P<name2>\w*)>.*</(?P=name2)></(?P=name1)>","<html><h1>www.itcast.cn</h2></html>")
#ret.group()
```

## re.compile 函数

compile 函数用于编译正则表达式，生成一个正则表达式（ Pattern ）对象，供 match() 和 search() 这两个函数使用。

```html
prog = re.compile(pattern)
result = prog.match(string)
```

等价于

```html
result = re.match(pattern, string)
```

举例：

```python
>>>import re
>>> pattern = re.compile(r'\d+')   
m = pattern.match('one12twothree34four', 3, 10) # 从'1'的位置开始匹配，正好匹配
>>> print m                                         # 返回一个 Match 对象
<_sre.SRE_Match object at 0x10a42aac0>
>>> m.group(0)   # 可省略 0
'12'
>>> m.start(0)   # 可省略 0
3
>>> m.end(0)     # 可省略 0
5
>>> m.span(0)    # 可省略 0
(3, 5)
```

在上面，当匹配成功时返回一个 Match 对象，其中：

- `group([group1, …])` 方法用于获得一个或多个分组匹配的字符串，当要获得整个匹配的子串时，可直接使用 `group()` 或 `group(0)`；
- `start([group])` 方法用于获取分组匹配的子串在整个字符串中的起始位置（子串第一个字符的索引），参数默认值为 0；
- `end([group])` 方法用于获取分组匹配的子串在整个字符串中的结束位置（子串最后一个字符的索引+1），参数默认值为 0；
- `span([group])` 方法返回 `(start(group), end(group))`

## re.search函数

re.search 扫描整个字符串并返回第一个成功的匹配，如果没有匹配，就返回一个 `None`。

re.match与re.search的区别：re.match只匹配字符串的开始，如果字符串开始不符合正则表达式，则匹配失败，函数返回None；而re.search匹配整个字符串，直到找到一个匹配

举例：

```python
import re
ret = re.search(r"\d+", "阅读次数为9999")
print(ret.group())
```

结果：

```
9999
```



## re.findall函数

在字符串中找到正则表达式所匹配的所有子串，并返回一个列表，如果没有找到匹配的，则返回空列表。注意**：** match 和 search 是匹配一次 findall 匹配所有。

举例：

```python
import re
ret = re.findall(r"\d+", "python = 9999, c = 7890, c++ = 12345")
print(ret)
```

结果：

```
['9999', '7890', '12345']
```



## re.finditer函数

和 findall 类似，在字符串中找到正则表达式所匹配的所有子串，并把它们作为一个迭代器返回。

```python
import re
it = re.finditer(r"\d+", "12a32bc43jf3")
for match in it:
    print(match.group())
```

结果：

```
12
32
43
3
```



## re.sub函数

sub是substitute的所写，表示替换，将匹配到的数据进⾏替换。

语法：re.sub(pattern, repl, string, count=0, flags=0)

| 参数    | 描述                                                         |
| ------- | ------------------------------------------------------------ |
| pattern | 必选，表示正则中的模式字符串                                 |
| repl    | 必选，就是replacement，要替换的字符串，也可为一个函数        |
| string  | 必选，被替换的那个string字符串                               |
| count   | 可选参数，*count* 是要替换的最大次数，必须是非负整数。如果省略这个参数或设为 0，所有的匹配都会被替换 |
| flag    | 可选参数，标志位，用于控制正则表达式的匹配方式，如：是否区分大小写，多行匹配等等。 |

举例：将匹配到的阅读次数加1

方法一：

```python
import re
ret = re.sub(r"\d+", '998', "python = 997")
print(ret)
```

结果：

```
python = 998
```

方法二：

```python
import re
def add(temp):
    #int（）参数必须是字符串，类似字节的对象或数字，而不是“re.Match”
    strNum = temp.group()
    num = int(strNum) + 1
    return str(num)
ret = re.sub(r"\d+", add, "python = 997")
print(ret)
ret = re.sub(r"\d+", add, "python = 99")
print(ret)
```

结果;

```
python = 998
python = 100
```



## re.subn函数

行为与`sub()`相同，但是返回一个元组 `(字符串, 替换次数)`。

re.subn(pattern, repl, string[, count])

返回：(sub(repl, string[, count]), 替换次数)

```python
import re
pattern = re.compile(r'(\w+) (\w+)')
s = 'i say, hello world!'
print(re.subn(pattern, r'\2 \1', s))
def func(m):
    return m.group(1).title() + ' ' + m.group(2).title()
print(re.subn(pattern, func, s))
### output ###
# ('say i, world hello!', 2)
# ('I Say, Hello World!', 2)
```

## re.split函数

根据匹配进⾏切割字符串，并返回⼀个列表。

`re.``split`(*pattern*, *string*, *maxsplit=0*, *flags=0*)

| 参数     | 描述                                                |
| -------- | --------------------------------------------------- |
| pattern  | 匹配的正则表达式                                    |
| string   | 要匹配的字符串                                      |
| maxsplit | 分隔次数，maxsplit=1 分隔一次，默认为 0，不限制次数 |

举例：

```python
import re
ret = re.split(r":| ","info:xiaoZhang 33 shandong")
print(ret)
```

结果：

```
['info', 'xiaoZhang', '33', 'shandong']
```



## python贪婪和⾮贪婪

Python⾥数量词默认是贪婪的（在少数语⾔⾥也可能是默认⾮贪婪），总是尝试匹配尽可能多的字符；⾮贪婪则相反，总是尝试匹配尽可能少的字符。

例如：正则表达式”ab*”如果用于查找”abbbc”，将找到”abbb”。而如果使用非贪婪的数量词”ab*?”，将找到”a”。

注：我们一般使用非贪婪模式来提取。

在"*","?","+","{m,n}"后⾯加上？，使贪婪变成⾮贪婪。

举例1：

```lua
import re
s="This is a number 234-235-22-423"
#正则表达式模式中使⽤到通配字，那它在从左到右的顺序求值时，会尽量“抓取”满⾜匹配最⻓字符串，在我们上⾯的例⼦⾥⾯，“.+”会从字符串的启始处抓取满⾜模式的最⻓字符，其中包括我们想得到的第⼀个整型字段的中的⼤部分，“\d+”只需⼀位字符就可以匹配，所以它匹配了数字“4”，⽽“.+”则匹配了从字符串起始到这个第⼀位数字4之前的所有字符
r=re.match(".+(\d+-\d+-\d+-\d+)",s)
print(r.group(1))
#⾮贪婪操作符“？”，这个操作符可以⽤在"*","+","?"的后⾯，要求正则匹配的越少越好
r=re.match(".+?(\d+-\d+-\d+-\d+)",s)
print(r.group(1))
```

结果：

```
4-235-22-423
234-235-22-423
```

举例2：

```python
>>> re.match(r"aa(\d+)","aa2343ddd").group(1)
'2343'
>>> re.match(r"aa(\d+?)","aa2343ddd").group(1)
'2'
>>> re.match(r"aa(\d+)ddd","aa2343ddd").group(1)
'2343'
>>> re.match(r"aa(\d+?)ddd","aa2343ddd").group(1)
'2343'
```

举例3：提取图片地址

```python
import re
test_str="<img data-original=https://rpic.douyucdn.cn/appCovers/2016/11/13/1213973.jpg>"
ret = re.search(r"https://.*?.jpg", test_str)
print(ret.group())
```

结果：https://rpic.douyucdn.cn/appCovers/2016/11/13/1213973.jpg

## r的作⽤

与大多数编程语言相同，正则表达式里使用”\”作为转义字符，这就可能造成反斜杠困扰。假如你需要匹配文本中的字符”\”，那么使用编程语言表示的正则表达式里将需要4个反斜杠”\\\\”：前两个和后两个分别用于在编程语言里转义成反斜杠，转换成两个反斜杠后再在正则表达式里转义成一个反斜杠。Python里的原生字符串很好地解决了这个问题，Python中字符串前⾯加上 r 表示原⽣字符串。

```python
import re
mm = "c:\\a\\b\\c"
print(mm)#c:\a\b\c
ret = re.match("c:\\\\",mm).group()
print(ret)#c:\
ret = re.match("c:\\\\a",mm).group()
print(ret)#c:\a
ret = re.match(r"c:\\a",mm).group()
print(ret)#c:\a
ret = re.match(r"c:\a",mm).group()
print(ret)#AttributeError: 'NoneType' object has no attribute 'group'
```

 

