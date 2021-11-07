## Python学习笔记
[toc]
#### Python基础

    《零基础入门学习Python》（小甲鱼）https://www.bilibili.com/video/av27789609
    13年的教程，python3版本基础教学+爬虫基础+GUI+pygame
    
#### 爬虫基础

    【Python网络爬虫与信息提取】.MOOC. 北京理工大学 https://www.bilibili.com/video/av9784617
    requests，beautifulsoup，xpath，re正则，scrapy等爬虫基础，可以一看

#### 爬虫案例

    轻松学Python https://space.bilibili.com/131494610
    爬取短视频，图片，小说，音乐，提取文本，api接口，ip代理池，js解密，数据可视化

    #### Python网络爬虫基础+进阶+Scrapy框架项目实战 https://www.bilibili.com/video/av64147959
    urllib库，requests库，xpath语法，正则，css选择器，selenium自动化，多线程，scrapy框架，json及mysql数据存储，redis分布式爬虫
    
#### 导入自定义包

    第一：当前目录下，创建文件夹lib
    第二：把需要导入的py文件放到lib里面
    # 导入weather.py
    from lib import weather
    # 导入get_weather函数
    from lib.weather import get_weather
    # 别名
    from lib import weather as w