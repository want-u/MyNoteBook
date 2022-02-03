# parsel-css选择器用法
[toc]

## parsel库的基本使用
parsel是一个python的第三方库，相当于css选择器+xpath+re。

parsel由scrapy团队开发，是将scrapy中的parsel独立抽取出来的，可以轻松解析html，xml内容，获取需要的数据。

相比于BeautifulSoup，xpath，parsel效率更高，使用更简单。

### 功能
parsel是一个库，支持三大功能

- .css()
- .xpath()
- .re()

### 安装

```
pip install parsel

直接pip安装下载速度可能比较慢，推荐使用豆瓣源

pip install parsel -i http://pypi.douban.com/simple/
```


### 导入库

    import parsel  
    import requests
    
### 语法使用

    url = 'http://www.baidu.com'
    response = requests.get(url)
    selector = parsel.Selector(response.text)
    
## CSS选择器
无论是使用css选择器，还是xpath，re，都需要先创建一个parsel.Selector对象


```
from parsel import Selector

# html 可以是请求某个网页的源码，也可以是html，xml格式的字符串
selector = Selector(html)
```

创建Selector对象之后就可以开始使用了


```
tags = selector.css('.content')

# 我们平时使用的css中，对某一个标签进行修饰时，使用的是 .class_attr
# 在这里也是如此
# .content 就是指查询所有 class 为 content 的标签
```


查询的结果是一个特殊的对象，不能直接得到需要的数据

将css()函数查询到的结果转换为字符串或者列表，需要使用一个函数

- getall() 是将css() 查询到的结果转换为python的列表
- get() 是将css() 查询到的第一个结果转换为str类型    

以下面的html格式的字符串为例

```
html = """
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>标签选择器</title>
</head>

<body>
    <p>css标签选择器的介绍</p>
    <p class='p'>标签选择器、类选择器、ID选择器</p>
    
    <a href="https://www.baidu.com" title="百度搜索">百度一下</a>
    
    <span> 我是一个span标签</span>
    
    <div id="map">
        <p class="content">早上祝您端午节快乐！</p>
    </div>
    
    <span id="map">
        <p class="content">晚上祝您端午节快乐！</p>
    </span>
    <span id="text">你好，我是一行文字</span>
</body>

</html>
"""
```
css选择器的使用方法和我们平时使用的css几乎相同，主要分为：

- 标签选择器
- class选择器
- id选择器
- 属性提取器
- 属性选择器
- 混合选择器

### 标签选择器

```
# 选取所有的p标签
p_tags = selector.css('p')

print(p_tags.get())
# 结果
# <p>css标签选择器的介绍</p>

print(p_tags.getall())
# 结果
# ['<p>css标签选择器的介绍</p>',
# '<p class="p">标签选择器、类选择器、ID选择器</p>',
# '<p>我是 div &gt; p 标签的文字，我是div 标签的儿子标签</p>',
# '<p class="content">早上祝您端午节快乐！</p>',
# '<p>我是div &gt; span &gt; p 标签中的文字，我是div 标签的孙子标签</p>',
# '<p class="content">晚上祝您端午节快乐！</p>']
```

### class选择器

```
# 选择所有class为content的标签
# tags = selector.css('.content.info')  # 选择多个属性
tags = selector.css('.content')

print(type(tags.get()))
# <class 'str'>

print(tags.get())
# <p class="content">早上祝您端午节快乐！</p>

print(type(tags.getall()))
# <class 'list'>

print(tags.getall())
# ['<p class="content">早上祝您端午节快乐！</p>', '<p class="content">晚上祝您端午节快乐！</p>']
```

### id选择器

```
# 选取id为text的所有标签
text = selector.css('#text')

print(text.get())
# <span id="text">你好，我是一行文字</span>
```
### 属性提取器

```
# 属性提取器
# 分别提取a标签中的href和title属性值
href_value = selector.css('a::attr(href)').get()
print(href_value)
# https://www.baidu.com

title_value = selector.css('a::attr(title)').get()
print(title_value)
# 百度搜索
```
### 属性选择器

```
# 属性选择器就是提取出标签中的文字
# 提取文字
string = selector.css('#text::text').get()

print(string)
# 你好，我是一行文字

```
### 类选择器和id选择器搭配使用


```
# id为map的标签内的p标签，且p标签的class为content，提取出该标签的文字
content = selector.css('#map p.content::text').getall()
print(content)
# ['早上祝您端午节快乐！', '晚上祝您端午节快乐！']

# id为map的span标签，该标签内的p标签，且该p标签的class为content，提取出该标签的文字
content2 = selector.css('span#map p.content::text').getall()
print(content2)
# ['晚上祝您端午节快乐！']

# id为map的标签，该标签内class为content的标签，提取出该标签的文字
content3 = selector.css('#map .content::text').getall()
print(content3)
# ['早上祝您端午节快乐！', '晚上祝您端午节快乐！']
```

### 子选择器和子孙选择器

```
# 子选择器和孙子选择器
son_tags = selector.css('#map > p')
pprint(son_tags.getall())
# ['<p>我是 div &gt; p 标签的文字，我是div 标签的儿子标签</p>',
# '<p class="content">早上祝您端午节快乐！</p>',
# '<p class="content">晚上祝您端午节快乐！</p>']

sunzi_tags = selector.css('#map p')
pprint(sunzi_tags.getall())
# ['<p>我是 div &gt; p 标签的文字，我是div 标签的儿子标签</p>',
# '<p class="content">早上祝您端午节快乐！</p>',
# '<p>我是div &gt; span &gt; p 标签中的文字，我是div 标签的孙子标签</p>',
# '<p class="content">晚上祝您端午节快乐！</p>']
```

```
#map > p 是选择 id为map 的标签儿子标签的 p标签，只检测 id为map 的标签内的第一层

#map p 是选择 id为map 的标签所有子孙标签中的p标签，检测 id为map 的标签内的所有标签

可以看到，#map p 可以查询到孙子标签中的p标签，而 #map > p 只能查询子标签中的p标签

```

## 我的示例

### 选择所有元素
    selector.css('*')

### 1、选择article元素
    html = selector.css('article')
    # get(), getall() , extract_first(), extract() 都可以
    
### 2、选择id为container的元素
    selector.css('#container')

### 3、选择所有class包含container的元素
    selector.css('.container')
    
### 4、选取所有div下所有a元素
    selector.css('div a')

### 5、提取标签title列表
    title1 = selector.css('title').extract()
    title2 = selector.css('title').extract_first()

### 6、提取标签p里的文本内容
    text = selector.css('p::text').extract()

### 7、提取标签div里的所有文本内容
    data = selector.css('div.post-content *::text').extract()

### 8、提取标签里的URL：标签名::attr(属性名)
    url = selector.css('div.post-content img::attr(src)').extract()
    
### 9、选取所有拥有title属性的a元素
    a = selector.css('a[title]').getall()

## 拓展语法


### 选取ul后面的第一个p元素
    selector.css('ul + p')

### 选取与ul相邻的所有p元素
    selector.css('ul ~ p')

### 选取下面第二个标签，如果是a的话则选取，不是则不取
    selector.css('a:nth-child(2)')

### 选取第偶数个a元素
    selector.css('a:nth-child(2n)')

### 选取第奇数个a元素
    selector.css('a:nth-child(2n+1)') 

### 选取class为multi-chosen的li的所有a元素
    selector.css('li.multi-chosen > a') 

###  选取所有href属性为https://www.lagou.com/jobs/3537439.html的a元素
    selector.css('a[href=”https://www.lagou.com/jobs/3537439.html”]')

### 选取所有href属性值中包含www.lagou.com的a元素
    a[href*=”www.lagou.com”]

### 选取所有href属性值中以http开头的a元素
    a[href^=”http”] 
    
### 选取所有id为非content-container 的div
    div:not(#content-container) 