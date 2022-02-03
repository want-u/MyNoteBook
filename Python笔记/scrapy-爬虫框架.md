# scrapy-爬虫框架

[toc]

### 1. scrapy框架介绍

— — python使用的最广泛的爬虫框架。

### 2. 创建项目：终端cmd下创建

输入命令：scrapy startproject [项目名qsbk]
生成目录结构：
- 1、scrapy.cfg:项目配置文件
- 2、items.py :定义需要爬去的字段
- 3、middlewares.py：中间件
- 4、pipelines.py:数据的处理和保存 ， item为爬去的数据
- 5、settings:配置文件
- 6、spiders：爬虫文件

### 3. 创建爬虫：

输入命令：cd qsbk # **进入项目路径**

输入命令：scrapy genspider qsbk_spider “qiushibaike.com” # **scrapy genspider [爬虫名] [域名限定]**

爬虫文件存放在 spiders目录中


### 4. settings设置：



```
            ROBOTSTXT_OBEY=False                 关闭爬虫协议
            DEFAULT_REQUEST_HEADERS              请求头添加user-agent
            ITEM_PIPELINES                       激活管道文件
            #DOWNLOAD_DELAY = 3                  下载延时3秒
```

### 5. items（数据模型）：



```
            定义数据模型：name = scrapy.Field()
            例如：            content = scrapy.Field()
            
            导入到spider：from [项目名 ]      import [item名 ]
            例如：            from bilibili.items import BilibiliItem

			将数据添加到字典：item = CarItem( title=title,  image_urls=image_url)
```

### 6. parse（解析提取数据）：



```
            xpath方式 —— ——  reponse.xpath('//a/text()').get()
                        	reponse.xpath('//a/text()').getall()				# 选取多个标签
                        
            css方式（可以使用extract()  或get()方法） 
            	     —— ——  response.css('h1::text').extract()  /get()
                        	response.css('h1.title.content::text').extract() 	# 选取多个标签

            正则方式	 —— ——  reponse.xpath('.').re('正则表达式')
                        
            yeild # 函数最后返回item数据
```

### 7. 多页提取：



```
            # 在parse中找到下一页next_url ， 然后yield请求： yield scrapy.Request('https://www.bilibili.com/ranking/origin/0/0/3', callback=self.parse)
            if not next_url:
                return 
            else:
                yield scrapy.Request(next_url, callback=parse)
```

### 8. piplines（管道文件，负责存储数据）:



```
		# 需在settings中开启管道配置选项
		ITEM_PIPELINES = {
		   'bilibili.pipelines.BilibiliPipeline': 300,
		}
```

### 8. scrapy shell( 方便数据提取测试代码 )：



```
            如果虚拟环境要先进入虚拟环境  workon crawl-env
            进入项目目录  cd [项目]
            scrapy shell [url]
            然后执行测试语句
```

### 9. 运行爬虫
运行爬虫（命令方式）：scrapy crawl (爬虫名)

运行爬虫（cmdline方式）：

```
# 在项目目录下创建一个运行代码

# -*- coding: utf-8 -*-

from scrapy import cmdline

cmdline.execute("scrapy crawl baidu".split())
```