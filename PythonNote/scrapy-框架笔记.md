# scrapy-框架笔记
```


# enumerate函数：用于将一个可遍历的数据对象(如列表、元组或字符串)组合为一个索引序列，同时列出数据和数据下标，一般用在 for 循环当中。
"""
 1. 安装scrapy模块:  cmd命令执行
 
                pip install scrapy                   (windows下需要安装pypiwin32 ： pip install pypiwin32)

 2. 创建项目：终端创建
 
                scrapy startproject [项目名qsbk]                 生成目录结构：
                                                                 1、scrapy.cfg:项目配置文件
                                                                 2、items.py :需要爬去的字段
                                                                 3、middlewares.py：中间件
                                                                 4、pipelines.py:数据的处理和保存 ， item为爬去的数据
                                                                 5.settings:
 3. 创建爬虫：
 
                cd qsbk                              # 进入项目路径
                scrapy genspider qsbk_spider "qiushibaike.com"  # scrapy genspider [爬虫名] [域名限定]
     
 4.settings设置：
 
                ROBOTSTXT_OBEY=False                 关闭爬虫协议
                DEFAULT_REQUEST_HEADERS              请求头添加user-agent
                ITEM_PIPELINES                       激活管道文件
                #DOWNLOAD_DELAY = 3                  下载延时3秒
                
 5. items（数据模型）：
                导入到spider：from [项目名 ]      import [item名 ]
                             from bilibili.items import BilibiliItem

                定义数据模型：name = scrapy.Field()

                
 6. parse（解析提取数据）：—— —— （调用内部函数使用yield from self.函数名）
 
                xpath —— —— reponse.xpath('//a/text()').get()
                            reponse.xpath('//a/text()').getall()
                            
                css   —— —— response.css('h1::text').extract()  /get()
                            response.css('h1.title.content::text').extract() # 选取多个标签

                使用正则—— —— reponse.xpath('.').re('正则表达式')
                            
                yeild # 返回数据
                            item = BilibiliItem(name=name)  # 以字典对象的方式传进参数
                            
 7. 多页提取：
                
                在parse中找到下一页next_url  如： yield scrapy.Request('https://www.bilibili.com/ranking/origin/0/0/3', callback=self.parse)
                if not next_url:
                    return 
                else:
                    yield scrapy.Request(next_url, callback=parse)
                
 7. piplines（存储数据）:   

 8. scrapy shell( 方便数据提取测试代码 )：
                进入虚拟环境  workon crawl-env
                进入项目目录  cd [项目]
                scrapy shell [url]
                
 9. 运行scrapy
                在项目目录下，创建运行程序run.py
                from scrapy import cmdline
                cmdline.execute('scrapy crawl bili_spider'.split())
"""

if __name__ == '__main__':
    print('hello')

```
