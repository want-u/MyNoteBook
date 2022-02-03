# Kibana-查询语言（KQL）

- [Kibana查询语言（KQL） - 寻觅beyond - 博客园 (cnblogs.com)](https://www.cnblogs.com/-beyond/p/14159002.html)
- [日志查询神器 Kibana简单使用 - 简书 (jianshu.com)](https://www.jianshu.com/p/9d2316693a1e)
- [Query string query | Elasticsearch Guide  7.17 | Elastic](https://www.elastic.co/guide/en/elasticsearch/reference/current/query-dsl-query-string-query.html#query-string-syntax)
- [kibana data table怎么去取某个字段的平均值. - Elastic 中文社区 (elasticsearch.cn)](https://elasticsearch.cn/question/4509)

[toc]

## 仪表盘

![image-20220203232644848](https://gitee.com/luoxian1011/pictures/raw/master//image-20220203232644848.png)

## 优化查询

顶部的搜索栏采用了 Elasticsearch 对 Lucene 查询字符串语法的支持技术。

以下是一些示例，说明如何搜索已解析成若干字段的 Web 服务器日志。


```
查找任意字段包含数字 200 的请求
200

在状态字段中查找 200
status:200

查找所有介于 400-499 之间的状态代码
status:[400 TO 499]


您可以通过布尔操作符 AND 、 OR 和 NOT 来指定更多的搜索条件
(注意：这AND、OR、NOT必须大写)。

查找状态代码 400-499 以及扩展名 php
status:[400 TO 499] AND extension:PHP

查找状态代码 400-499 以及扩展名 php 或 html
status:[400 TO 499] AND (extension:php OR extension:html)
```
## 范围查询

```
request_time:>1
request_time: [1 TO *]

status:>300
status: [300 TO *]
```
![image-20220203185206850](https://gitee.com/luoxian1011/pictures/raw/master//image-20220203185206850.png)
```
具有一侧无界的范围可以使用以下语法：

age:>10
age:>=10
age:<10
age:<=10


要将上限和下限与简化的语法相结合，您需要使用运算符连接两个子句：AND

age:(>=10 AND <20)
age:(+>=10 +<20)
```
## 聚合查询

```
您可以使用“数据表”作为可视化表单。

选择“平均值”作为指标，然后选择request_time（如您的具体问题 - 需要是 integer 或 float）作为字段。
```

### status 总计分布

![image-20220203190445204](https://gitee.com/luoxian1011/pictures/raw/master//image-20220203190445204.png)

### request_time 每小时请求时间平均值

![image-20220203192405655](https://gitee.com/luoxian1011/pictures/raw/master//image-20220203192405655.png)


### request_time 每小时请求时间最大值

![image-20220203192056208](https://gitee.com/luoxian1011/pictures/raw/master//image-20220203192056208.png)

### body_bytes_sent 每小时流量大小

![image-20220203191902868](https://gitee.com/luoxian1011/pictures/raw/master//image-20220203191902868.png)

### PV/UV

### PV/UV 趋势

## 测试访问代码

```
vim /mnt/d/PythonProject/logParse/demo.py

#########################################################################
# File Name: demo.py
# Author: Longfei
# Blog: https://www.cnblogs.com/luoxian1011
# Created Time: Thu Feb  3 16:37:18 2022
#########################################################################
#!/usr/bin/env python3
import requests
import random
import time

# url = 'http://localhost/test.php'
urls= ['http://localhost', 'http://localhost/test.php', 'http://localhost/testdb.php', 'http://localhost/test_404_page']

while True:
    f = random.randint(30, 100)
    for i in range(f):
        r = requests.get(random.choice(urls))
        print(r, r.url)

    print('sleep: {}s'.format(f))
    time.sleep(f)


```
