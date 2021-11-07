# 谷歌浏览器 发送POST/GET请求
[toc]
    打开chrome 开发者工具
    登录你访问的系统，复制你后台系统提供的url
    在Console中输入如下代码 可发送http post/get请求

#### POST

###### 方法一：
```
var url = "/dict/test";
var params = {advertiserUid: 1232131, advertiserWeiboNickname: "18"};
var xhr = new XMLHttpRequest();
xhr.open("POST", url, true);
xhr.setRequestHeader("Content-Type", "application/json");
xhr.onload = function (e) {
  if (xhr.readyState === 4) {
    if (xhr.status === 200) {
      console.log(xhr.responseText);
    } else {
      console.error(xhr.statusText);
    }
  }
};
xhr.onerror = function (e) {
  console.error(xhr.statusText);
};
xhr.send(JSON.stringify(params));
```

###### 方法二：
```
var url = "/dict/test";
var params = "score=5&abc=6";
var xhr = new XMLHttpRequest();
xhr.open("POST", url, true);
xhr.setRequestHeader("Content-type","application/x-www-form-urlencoded"); 
xhr.onload = function (e) {
  if (xhr.readyState === 4) {
    if (xhr.status === 200) {
      console.log(xhr.responseText);
    } else {
      console.error(xhr.statusText);
    }
  }
};
xhr.onerror = function (e) {
  console.error(xhr.statusText);
};
xhr.send(params);
```

#### GET


```
var url = "/v1/query/listDicts?types=userType,userStatus";
var xhr = new XMLHttpRequest();
xhr.open("GET", url, true);
xhr.onload = function (e) {
  if (xhr.readyState === 4) {
    if (xhr.status === 200) {
      console.log(xhr.responseText);
    } else {
      console.error(xhr.statusText);
    }
  }
};
xhr.onerror = function (e) {
  console.error(xhr.statusText);
};
xhr.send(null);
```
