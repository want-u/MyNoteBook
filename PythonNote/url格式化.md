# url格式化

我们在爬虫时，url链接中含有中文时，会自动被编码为特殊符号，因此我们需要对中文进行编码操作，同时也有可能涉及到对url编码后的中文进行解码操作。



```
from urllib.parse import quote
import  string
 
url = r'http://www.xxxx.com/name=中文'
url = quote(url, safe = string.printable)   # safe表示可以忽略的字符
print(url)
```

