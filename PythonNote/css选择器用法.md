# css选择器用法
[toc]
#### 导入库

    import parsel  
    import requests
    
## 语法使用

    url = 'http://www.baidu.com'
    response = requests.get(url)
    selector = parsel.Selector(response.text)
    
#### 选择所有元素
    selector.css('*')

#### 1、选择article元素
    html = selector.css('article')
    # get(), getall() , extract_first(), extract() 都可以
    
#### 2、选择id为container的元素
    selector.css('#container')

#### 3、选择所有class包含container的元素
    selector.css('.container')
    
#### 4、选取所有div下所有a元素
    selector.css('div a')

#### 5、提取标签title列表
    title1 = selector.css('title').extract()
    title2 = selector.css('title').extract_first()

#### 6、提取标签p里的文本内容
    text = selector.css('p::text').extract()

#### 7、提取标签div里的所有文本内容
    data = selector.css('div.post-content *::text').extract()

#### 8、提取标签里的URL：标签名::attr(属性名)
    url = selector.css('div.post-content img::attr(src)').extract()
    
#### 9、选取所有拥有title属性的a元素
    a = selector.css('a[title]').getall()

## 拓展语法


#### 选取ul后面的第一个p元素
    selector.css('ul + p')

#### 选取与ul相邻的所有p元素
    selector.css('ul ~ p')

#### 选取下面第二个标签，如果是a的话则选取，不是则不取
    selector.css('a:nth-child(2)')

#### 选取第偶数个a元素
    selector.css('a:nth-child(2n)')

#### 选取第奇数个a元素
    selector.css('a:nth-child(2n+1)') 

#### 选取class为multi-chosen的li的所有a元素
    selector.css('li.multi-chosen > a') 

####  选取所有href属性为https://www.lagou.com/jobs/3537439.html的a元素
    selector.css('a[href=”https://www.lagou.com/jobs/3537439.html”]')

#### 选取所有href属性值中包含www.lagou.com的a元素
    a[href*=”www.lagou.com”]

#### 选取所有href属性值中以http开头的a元素
    a[href^=”http”] 
    
#### 选取所有id为非content-container 的div
    div:not(#content-container) 