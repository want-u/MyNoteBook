# selenium-Edge

最近更新的Edge浏览器有着谷歌内核，而且可以在无插件情况下访问拓展商店，可以作为谷歌浏览器的替代品

将msedgedriver.exe放在python.exe同目录下
driver_url为下载的msedgedriver.exe所在位置

Egde浏览器下载链接：
https://www.microsoft.com/zh-cn/edge?form=MI13F4&OCID=MI13F4

msedgedriver.exe下载链接：(看清版本号)
https://developer.microsoft.com/en-us/microsoft-edge/tools/webdriver/


```
from selenium import webdriver
driver_url = r"C:\Program Files (x86)\Microsoft\Edge Beta\Application\msedgedriver.exe"
browser = webdriver.Edge(executable_path=driver_url)
browser.get("http://www.baidu.com")
```


