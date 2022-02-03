# Xpath-lxml库模块

[toc]

XPath在[Python](http://lib.csdn.net/base/11)的爬虫学习中，起着举足轻重的地位，对比正则表达式 re两者可以完成同样的工作，实现的功能也差不多，但XPath明显比re具有优势，在网页分析上使re退居二线。

**XPath介绍：** 
是什么？ 全称为**XML Path Language** 一种小型的**查询语言** 
说道XPath是门语言，不得不说它所具备的优点： 
1） 可在XML中查找信息 
2） 支持HTML的查找 
3） 通过元素和属性进行导航


### 使用xpath提取网页信息

注：获取xpath路径（浏览器F12复制 或 使用插件XPath Helper调试）

```python
# 安装lxml   在终端使用命令安装 pip3 install lxml  
from lxml import etree  # 导入etree
selector = etree.HTML(html)  # 将html转换成Element对象
```

### Xpath路径表达式：

——*返回节点对象*

| nodename(节点名) | 选取此节点的所有子节点 |
| :--------------- | :--------------------- |
| /                | 从根节点选取           |
| //               | 选取任意位置的该节点   |
| .                | 选取当前节点           |
| …                | 选取当前节点的父节点   |
| @                | 选取有属性的节点       |

### 选取未知节点：

| *    | 匹配任何节点       |
| :--- | :----------------- |
| @*   | 匹配任何带属性节点 |

### 提取标签的属性值：

*——返回标签某属性的值（如href）*

/@属性



```
		a = selector.xpath('//a/@href')
```

### 提取标签下文本：

/text()



```
		   msg = selector.xpath('//title/text()')
```

### 提取标签下的所有自节点



```
  加  //  试试吧
```