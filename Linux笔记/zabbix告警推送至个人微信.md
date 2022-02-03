# zabbix告警推送至个人微信 

https://www.cnblogs.com/cnsre/p/14783606.html

[toc]

[文章原文](https://cnsre.cn/posts/210518420293)
自从接触`zabbix`后，就一直想着怎么才能把告警推送到个人微信上。有这样的想法主要是个人微信的使用频率远远要比钉钉，企业微信，邮箱，飞书等使用频率要高。比如我，就遇到过在周末的时候，因为没有及时登录钉钉查看`zabbix`告警通知，导致一些告警没来得及处理，对环境产生了影响。
前段时间朋友给推荐了`pushplus`，一开始主要在`jenkins`做构建通知用的，后来就想，能不能使用`zabbix`的告警通知推送到微信上呢？

先介绍下`pushplus`吧,`pushplus` 官方介绍
`pushplus`(推送加)集成了微信、企业微信、钉钉、短信、邮件等实时信息推送平台
你只需要调用简单的API，即可帮助你迅速完成消息的推送，使用简单方便
我们的所做的一切只是为了让推送变的更简单

## pushplus

### 登录官网注册pushplus

[访问官网](https://www.pushplus.plus/)后点击[登录](https://cnsre.cn/),微信扫描即可注册。
![cnsre运维博客|Linux系统运维|自动化运维|云计算|运维监控](https://cdn.jsdelivr.net/gh/zops/ImagesHosting/cnsre/20210518143358.png)

### 获取pushplus Token

注册成功后。点击`一对多`，然后新建一个群组。群组编码作为群组的唯一标示，后续需要使用。群组名称随意填写。
创建成功之后点击群组上的`查看二维码`，将二维码发给需要加入群组的同事。以后推送的消息加入群组的用户都会收的到。在“订阅人”中可以主动的移除不想要的用户。
![cnsre运维博客|Linux系统运维|自动化运维|云计算|运维监控](https://cdn.jsdelivr.net/gh/zops/ImagesHosting/cnsre/20210518144006.png)
然后保存你的token和群组编码。

## zabbix_server端设置

查看[pushplus](http://pushplus.hxtrip.com/message)调用方式.
**附上脚本**
脚本很简单，shell直接调用接口即可



```shell
#!/bin/bash
##########################
# pushplus推送zabbix告警 #
#########################
topic=$1    #topic为你的群组
title=$2    #title你要的标题内容
content=$3  #你要的具体消息内容
url=http://pushplus.hxtrip.com/send #pushplus POST和PUT请求接口
json="{\"token\":  \"$token\", \"title\": \"$title\", \"content\": \"$3\", \"template\": \"html\", \"topic\": \"$topic\"}"
curl -H "Content-Type: application/json" -X POST -d "$json" $url
```

将脚本部署在`alertscripts`目录下，如果你不知道在那，你可以回想一下你钉钉告警，或者是企业微信告警的脚本放在那。如果实在记不起来，那就`find`找下`alertscripts`目录吧

## zabbix web端设置

### 创建报警媒介类型

登录zabbix web端点击`管理`--`报警媒介类型`--`创建媒体类型`
![cnsre运维博客|Linux系统运维|自动化运维|云计算|运维监控](https://cdn.jsdelivr.net/gh/zops/ImagesHosting/cnsre/20210518145612.png)
名称：随意写
类型：选择脚本
脚本名称：按照实际名称填写
脚本参数:



```
{ALERT.SENDTO}
{ALERT.SUBJECT}
{ALERT.MESSAGE}
```

![cnsre运维博客|Linux系统运维|自动化运维|云计算|运维监控](https://cdn.jsdelivr.net/gh/zops/ImagesHosting/cnsre/20210518145823.png)

### 创建动作

点击`配置`--`动作`--`创建动作`
![cnsre运维博客|Linux系统运维|自动化运维|云计算|运维监控](https://cdn.jsdelivr.net/gh/zops/ImagesHosting/cnsre/20210518150020.png)
**动作**
名称：随意写
条件：根据自己的告警需求填写
![cnsre运维博客|Linux系统运维|自动化运维|云计算|运维监控](https://cdn.jsdelivr.net/gh/zops/ImagesHosting/cnsre/20210518150231.png)
**操作**
默认操作步骤持续时间：1h
默认标题：你也可以随意写。也可以用下面的。



```shell
故障{TRIGGER.STATUS},服务器:{HOSTNAME1}发生:{TRIGGER.NAME}故障! 故障通知！！
```

消息内容:和标题一样



```shell
告警主机:{HOSTNAME1}<br>
IP地址：{HOST.CONN} <br>
告警时间:{EVENT.DATE} {EVENT.TIME}(UTC)<br>
告警等级:{TRIGGER.SEVERITY}<br>
告警信息: {TRIGGER.NAME}<br>
告警项目:{TRIGGER.KEY1}<br>
问题详情:{ITEM.NAME}:{ITEM.VALUE}<br>
当前状态:{TRIGGER.STATUS}:{ITEM.VALUE1}<br>
事件ID:{EVENT.ID}<br>
```

![cnsre运维博客|Linux系统运维|自动化运维|云计算|运维监控](https://cdn.jsdelivr.net/gh/zops/ImagesHosting/cnsre/20210518150419.png)
操作步骤--点击`新的`
![cnsre运维博客|Linux系统运维|自动化运维|云计算|运维监控](https://cdn.jsdelivr.net/gh/zops/ImagesHosting/cnsre/20210518150811.png)
选择发送到用户，点击`添加`--`admin`--`选择`
仅送到，选择你刚创建的，最后点击`添加`
![cnsre运维博客|Linux系统运维|自动化运维|云计算|运维监控](https://cdn.jsdelivr.net/gh/zops/ImagesHosting/cnsre/20210518150926.png)
最为为这样
![cnsre运维博客|Linux系统运维|自动化运维|云计算|运维监控](https://cdn.jsdelivr.net/gh/zops/ImagesHosting/cnsre/20210518151320.png)
**恢复操作**
恢复操作和操作一样
![cnsre运维博客|Linux系统运维|自动化运维|云计算|运维监控](https://cdn.jsdelivr.net/gh/zops/ImagesHosting/cnsre/20210518151240.png)
添加完后
![cnsre运维博客|Linux系统运维|自动化运维|云计算|运维监控](https://cdn.jsdelivr.net/gh/zops/ImagesHosting/cnsre/20210518151420.png)

### 创建用户报警媒介

选择`管理`--`用户`--`admin`
![cnsre运维博客|Linux系统运维|自动化运维|云计算|运维监控](https://cdn.jsdelivr.net/gh/zops/ImagesHosting/cnsre/20210518151609.png)
![cnsre运维博客|Linux系统运维|自动化运维|云计算|运维监控](https://cdn.jsdelivr.net/gh/zops/ImagesHosting/cnsre/20210518151743.png)
![cnsre运维博客|Linux系统运维|自动化运维|云计算|运维监控](https://cdn.jsdelivr.net/gh/zops/ImagesHosting/cnsre/20210518151843.png)

## 测试告警

添加完过后，接下来就测试吧。
我这边选择了一个`触发器`调了下告警的值，最后展示下告警以及恢复。
![cnsre运维博客|Linux系统运维|自动化运维|云计算|运维监控](https://cdn.jsdelivr.net/gh/zops/ImagesHosting/cnsre/20210518152153.png)
![cnsre运维博客|Linux系统运维|自动化运维|云计算|运维监控](https://cdn.jsdelivr.net/gh/zops/ImagesHosting/cnsre/20210518152532.png)
![cnsre运维博客|Linux系统运维|自动化运维|云计算|运维监控](https://cdn.jsdelivr.net/gh/zops/ImagesHosting/cnsre/20210518152416.png)
![cnsre运维博客|Linux系统运维|自动化运维|云计算|运维监控](https://cdn.jsdelivr.net/gh/zops/ImagesHosting/cnsre/20210518152553.png)
[文章原文](https://cnsre.cn/posts/210518420293)