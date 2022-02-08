# WSL2-ubuntu-开启messages日志文件

## 高版本ubuntu系统默认没有 `/var/log/messages`

因为在 `/etc/rsyslog.d/50-default.conf` 文件中，将其注释掉了。如下图

```
vi /etc/rsyslog.d/50-default.conf
```

![image-20220207231248717](https://gitee.com/luoxian1011/pictures/raw/master//image-20220207231248717.png)



## 需修改该配置文件，将注释放开



![img](https:////upload-images.jianshu.io/upload_images/18714459-fe8bcb5f4e0d61d1.png?imageMogr2/auto-orient/strip|imageView2/2/w/724/format/webp)

```
*.=info;*.=notice;*.=warn;\
    auth,authpriv.none;\
    cron,daemon.none;\
    mail,news.none      -/var/log/messages
```



## 然后重启rsyslog服务即可

```css
systemctl restart rsyslog.service
```

进入 /var/log/ 目录下就可以看到 messages 文件

```
tail /var/log/messages
```

