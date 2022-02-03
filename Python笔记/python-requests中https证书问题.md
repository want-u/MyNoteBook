# python-requests中https证书问题

https://blog.csdn.net/wdlnancy/article/details/87092178

[toc]

## 1. 忽略SSL证书方法

因登陆的是https网站，需要使用SSL证书，但若无证书进行连接的话，可增加verify=False方法忽略证书验证，但这样会出现如下告警：


```
D:\Python35\lib\site-packages\urllib3\connectionpool.py:847: InsecureRequestWarning: Unverified HTTPS request is being made. Adding certificate verification is strongly advised. See: https://urllib3.readthedocs.io/en/latest/advanced-usage.html#ssl-warnings
  InsecureRequestWarning)
```


故在代码上方增加忽略安全告警的代码：


```
requests.packages.urllib3.disable_warnings()
```

## 2. 请求中带上证书地址
（以浏览器为客户端解释SSL证书如何工作的参考文档：https://www.wosign.com/FAQ/how_browser_check_SSL.htm）

从浏览器中获取浏览器的证书地址，然后在请求体中加上证书路径，使用证书的最大问题是证书在哪儿弄？，经过探索得到解决：通过浏览器查看网页证书，然后另存为本地某路径

参考：https://jingyan.baidu.com/article/20095761903725cb0621b44f.html

但注意导出格式要选择Base64编码的选项，如保存到本地：


```
C:/Users/user/Downloads/github3.cer
```

代码修改为：


```
 r= requests.get(build_uri('user'),auth=('github账号','密码'),proxies=proxies,verify="C:/Users/user/Downloads/github3.cer")

```

## 3. github是需要用户登陆后才能继续进行操作的

故必须在get请求中设置auth信息，若不设置，如下方，则会报401错误：

![image](https://img-blog.csdnimg.cn/20190212153108346.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dkbG5hbmN5,size_16,color_FFFFFF,t_70)


```
# coding=utf-8
__author__ = 'w00*'
import requests
import json
import ssl
 
#忽略安全警告
requests.packages.urllib3.disable_warnings()
URL = "https://api.github.com"
proxies = {"http":"http://账号:密码@proxycn2.huawei.com:8080","https":"http://账号:密码@proxycn2.huawei.com:8080"}
context = ssl._create_unverified_context()
def build_uri(endpoint):
    return '/'.join([URL,endpoint]) #主要作用是拼接接口请求地址
 
def better_output(json_str):
    return json.dumps(json.loads(json_str),indent=4) #采用json里面提供方法打印出来
 
def request_method():
    # r = requests.get(build_uri('user'),auth=('wdlnancysw','wdlsw080705'),proxies=proxies)
    # r= requests.get(build_uri('user'),auth=('Github账号','密码'),proxies=proxies,verify=False)
    r= requests.get(build_uri('user'),proxies=proxies,verify=False)
    print (r.status_code)
    print (better_output(r.text)) #调用json更好格式输出
 
if __name__=='__main__':
    request_method()
```
