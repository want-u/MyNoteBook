# python-转化请求头


```
import re
 
headers = """
 
 
 
 
 
 
 
Accept: */*
Accept-Encoding: gzip, deflate, br
Accept-Language: zh-CN,zh;q=0.9
Connection: keep-alive
Host: movie.douban.com
Referer: https://movie.douban.com/subject/27010768/comments?start=420&limit=20&sort=new_score&status=P
Sec-Fetch-Mode: cors
Sec-Fetch-Site: same-origin
User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.130 Safari/537.36
X-Requested-With: XMLHttpRequest
 
"""
 
 
 
pattern = '^(.*?): (.*?)$'
#               1   2
for line in headers.splitlines(): # 反向引用
    print(re.sub(pattern, '\'\\1\': \'\\2\',',  line))
```
