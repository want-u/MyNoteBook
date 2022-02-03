# Json模块

- https://www.runoob.com/python/python-json.html
- https://www.cnblogs.com/tizer/p/11067098.html

[toc]

使用 JSON 函数需要导入 json 库：import json。

## json.dumps：Python对象 => JSON字符串
json.dumps 用于将 Python 对象编码成 JSON 字符串。

语法：
```
json.dumps(obj, skipkeys=False, ensure_ascii=True, check_circular=True, allow_nan=True, cls=None, indent=None, separators=None, encoding="utf-8", default=None, sort_keys=False, **kw)
```
实例：
以下实例将数组编码为 JSON 格式数据：

```python
#!/usr/bin/python
import json

data = [ { 'a' : 1, 'b' : 2, 'c' : 3, 'd' : 4, 'e' : 5 } ]

data2 = json.dumps(data)
print(data2)

# 以上代码执行结果为：
[{"a": 1, "c": 3, "b": 2, "e": 5, "d": 4}]
```

JSON 数据格式化输出：

```python
#!/usr/bin/python
import json

data_dict = [ { 'a' : 1, 'b' : 2, 'c' : 3, 'd' : 4, 'e' : 5 } ]

data = json.dumps(data_dict, sort_keys=True, indent=2, ensure_ascii=False)
print(data)

# 以上代码执行结果为：
{
    "a": "Runoob",
    "b": 7
}
```

## json.loads：JSON字符串 => Python对象
json.loads 用于解码 JSON 数据。该函数返回 Python 字段的数据类型。

语法：

```
json.loads(s[, encoding[, cls[, object_hook[, parse_float[, parse_int[, parse_constant[, object_pairs_hook[, **kw]]]]]]]])
```
实例：

```
#!/usr/bin/python
import json

jsonData = '{"a":1,"b":2,"c":3,"d":4,"e":5}';

text = json.loads(jsonData)
print(text)

# 以上代码执行结果为：
{u'a': 1, u'c': 3, u'b': 2, u'e': 5, u'd': 4}
```

## json.dump：存储JSON

```
import json

names = ['joker','joe','nacy','timi']

filename='names.json'
with open(filename,'w') as file_obj:
    json.dump(names,file_obj)
```

## json.load：读取JSON

```
import json

filename='names.json'
with open(filename) as file_obj:
    names = json.load(file_obj)
    
print(names)
```



## 示例代码
```
import json

data = {
    "error_code": 0,  # 要使用双引号，如果是单引号则运行时会报错，可以上网做在线json格式校验
    "stu_info": [
        {
            "id": 309,
            "name": "小白",
            "sex": "男",
        },
        {
            "id": 310,
            "name": "小黑",
            "sex": "男",
        }
    ]
}
# # 将 字典 转化为 json数据
json_data = json.dumps(data, ensure_ascii=False)
print(json_data)
print(type(json_data))
#
# # 将json数据 转化为 字典
dict_data = json.loads(json_data)
print(dict_data)
print(type(dict_data))

# 将 字典 保存为 json文件
with open('json_text.json', 'w', encoding='utf-8') as f:
    json.dump(data, f, ensure_ascii=False)

# 读取 json文件
with open('json_text.json', encoding='utf-8') as f:
    dict_data = json.load(f)
    print(dict_data)
    print(type(dict_data))

```




