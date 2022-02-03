# 读写yaml数据

- https://cloud.tencent.com/developer/article/1516551
- https://geek-docs.com/python/python-tutorial/python-yaml.html
- https://www.cnblogs.com/superhin/p/11503756.html

[toc]

## 数据及配置文件之争

数据及文件通常有三种类型：

- 配置文件型：如ini，conf，properties文件，适合存储简单变量和配置项，最多支持两层，不适合存储多层嵌套数据
- 表格矩阵型：如csv，excel等，适合于存储大量同类数据，不适合存储层级结构的数据
- 多层嵌套型：如XML，HTMl，JSON、YAML，TOML等，适合存储单条或少数多层嵌套数据，不适合存储大量数据

YAML兼容JSON格式，简洁，强大，灵活，可以很方便的构造层级数据并快速转为Python中的字典。


## yaml简介

- YAML（YAML Ain't Markup Language）即一种反标记（XML）语言。强调数据为中心，而非标记。YAML大小写敏感，使用缩进代表层级关系。
- YAML中支持对象Object(对应Python中的字典), 数组Array(对应Python中的列表)以及常量（字符串、数字（int/float），true/false/null）。
- 相比于JSON格式，YAML免除了双引号，逗号，大括号，中括号等，（当然也支持原始的JSON格式），并且支持注释，类型转换，跨行，锚点，引用及插入等等。

### yaml基础语法

- 区分大小写；
- 使用缩进表示层级关系；
- 使用空格键缩进，而非Tab键缩进
- 缩进的空格数目不固定，只需要相同层级的元素左侧对齐；
- 文件中的字符串不需要使用引号标注，但若字符串包含有特殊字符则需用引号标注；
- 注释标识为#
 

### yaml的数据结构

- 对象：使用key: value表示，冒号后面有一个空格，也可以是使用{key: value}（flow流格式）或{"key": "value"}表示
- 数组：使用- value表示，-后面有一个空格，每项一行，也可以使用[value1,value2,value3,...] （flow流格式）或["value1", "value2", "value3", ...]
- 字符串：abc或"abc"
- 数字：123或123.45
- true/false：true/false,TRUE/FALSE,True/False或on/off, ON/OFF, On/Off
- null: null,NULL, Null或~

示例文件demo.yaml:

```
# 注释：示例yaml文件
name: Cactus
age: 18
skills: 
  -
    - Python
    - 3
  -
    - Java
    - 5
has_blog: true
gf: ~
```
相当于以下JSON格式

```
{
  "name": "Cactus",
  "age": 18,
  "skills": [
    [
      "Python",
      3
    ],
    [
      "Java",
      5
    ]
  ],
  "has_blog": true,
  "gf": null
}
```
### 类型转换
使用!!str, !!float等可以将默认类型转为指定类型，如

```
- !!float 3
- !!str 4
- !!str true
```
对应JSON格式

```
[
  3.0,
  "4",
  "true"
]
```
### 多行文本及拼接
- | 保留多行文本（保留换行符）
- \> 将多行拼接为一行

示例：

```
a: |
  我
  喜欢你

b: >
  我
  不喜欢你
  才怪
```
对应JSON格式

```
{
  "a": "我\n喜欢你\n",
  "b": "我 不喜欢你 才怪"
}
```
### 锚点，引用及插入
在-或:后 加上&锚点名为当前字段建立锚点，下面可使用*锚点名引用锚点，或使用<<: *锚点名直接将锚点数据插入到当前的数据中，示例如下：

```
users:
  - &zs
    name: 张三
    password: !!str 123456
  - &ls
    name: 李四
    password: abcdefg

case1:
  login: *zs

case2:
  user:
    <<: *ls
    age: 20
```
对应JSON格式

```
{
  "users": [
    {
      "name": "张三",
      "password": "123456"
    },
    {
      "name": "李四",
      "password": "abcdefg"
    }
  ],
  "case1": {
    "login": {
      "name": "张三",
      "password": "123456"
    }
  },
  "case2": {
    "user": {
      "name": "李四",
      "password": "abcdefg",
      "age": 20
    }
  }
}
```
 
## Python操作YAML文件及字符串

需要安装pyyaml， pip install pyyaml

和JSON文件类似，yaml也提供load和dump两种方法。

- yaml.load()或yaml.safe_load(YAML字符串或文件句柄)：yaml -> 字典，如yaml中有中文，需要使用 字符串.encode('utf-8')或打开文件时指定encoding='utf-8'
- yaml.dump(字典)：默认为flow流格式，即字典{b': {'c': 3, 'd': 4}}，会被转为b: {c: 3, d: 4}形式，可以使用default_flow_style=False关闭流模式

由于yaml.load()支持原生Python对象，不安全，建议使用yaml.safe_load()


示例文件：demo.yaml


```
# 我们有几个标量值
raincoat: 1
coins: 5
books: 23
spectacles: 2
chairs: 12
pens: 6
```

```
# 我们在data.yaml中有两个文档。 文件用---分隔
cities:
  - Bratislava
  - Kosice
  - Trnava
  - Moldava
  - Trencin
---
companies:
  - Eset
  - Slovnaft
  - Duslo Sala
  - Matador Puchov
```


```
# config.yaml
config:
  # 使用-分隔用例，则yaml读取到的数据类型为列表
  - model: 注册模块
    title: 注册成功
    url: http://api.nnzhp.cn/api/user/user_reg
    method: POST
    data:
      username: yingcr8
      pwd: Ace123456
      cpwd: Ace123456
    check:
      error_code: 0
      msg: 注册成功！
  - model: 注册模块
    title: 用户名长度小于6位，注册失败
    url: http://api.nnzhp.cn/api/user/user_reg
    method: POST
    data:
      username: yingc
      pwd: Ace123456
      cpwd: Ace123456
    check:
      error_code: 3002
```

### PyYAML

#### Python YAML 读取

```
# pip install PyYAML
import yaml


def get_yaml_data(yaml_file):
    with open(yaml_file, encoding='utf-8')as file:
        print("*****转换yaml数据为字典或列表*****")
        # 设置Loader=yaml.FullLoader忽略YAMLLoadWarning警告
        data = yaml.load(file.read(), Loader=yaml.Loader)
        print(data)
        print(type(data))
        print(data.get('config'))  # 类型为字典 <class 'dict'>　# print(data[0]["model"]) # 若类型为列表 <class 'list'>

```

```
# 使用三个短横杠 --- 在一个文件中保存多个Yaml文档内容
# 所以Yaml的方法额外多出了load_all dump_all两种方法
def get_yaml_data(yaml_file):
    with open(yaml_file, encoding='utf-8')as file:
        print("*****转换yaml数据为字典或列表*****")
        # 设置Loader=yaml.FullLoader忽略YAMLLoadWarning警告
        data = yaml.load_all(file.read(), Loader=yaml.FullLoader)
        for i in data:
            print(i)
            print(type(i))
            # 类型为字典 <class 'dict'>　# print(data[0]["model"]) # 若类型为列表 <class 'list'>
```
#### Python YAML 转储

```
#!/usr/bin/env python3
# dump()方法将 Python 对象序列化为 YAML 流。

import yaml

users = [{'name': 'John Doe', 'occupation': 'gardener'},
         {'name': 'Lucy Black', 'occupation': 'teacher'}]

print(yaml.dump(users))
# 在示例中，我们有一个词典列表。 我们使用dump()方法将列表序列化为 YAML 格式。

$ python dumping.py
- name: John Doe
  occupation: gardener
- name: Lucy Black
  occupation: teacher
```

#### Python YAML 写入

```
#!/usr/bin/env python3
# 以下示例将 Python 数据写入 YAML 文件。

import yaml

users = [{'name': 'John Doe', 'occupation': 'gardener'},
         {'name': 'Lucy Black', 'occupation': 'teacher'}]

with open('users.yaml', 'w') as f:

    data = yaml.dump(users, f, default_flow_style=False)
    # 我们使用dump()方法写入数据。 第一个参数是数据，第二个参数是文件对象。
    # default_flow_style=False：写入文件，不使用flow流格式

```

```
# yaml.dump_all()将多个段输出到一个文件中
import yaml

obj1 = {"name": "James", "age": 20}
obj2 = ["Lily", 19]

with open(r'E:\AutomaticTest\Test_Framework\config\config.yml', 'w') as f:
    yaml.dump_all([obj1, obj2], f)
```

#### Python YAML 排序键

```
#!/usr/bin/env python3
# 我们可以使用dump's sort_keys参数对键进行排序。

import yaml

with open('items.yaml') as f:

    data = yaml.load(f, Loader=yaml.FullLoader)
    print(data)

    sorted = yaml.dump(data, sort_keys=True)
    print(sorted)
    
# 该示例从items.yaml文件中读取数据，并通过 YAML 输出中的键对数据进行排序。
$ python sort_keys.py
{'raincoat': 1, 'coins': 5, 'books': 23, 'spectacles': 2, 'chairs': 12, 'pens': 6}
books: 23
chairs: 12
coins: 5
pens: 6
raincoat: 1
spectacles: 2

```
