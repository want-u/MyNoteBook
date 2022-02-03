## Nginx启动脚本


```
# /usr/lib/systemd/system/nginxd.service
[Unit]
Description=nginx - high performance web server
After=network.target remote-fs.target nss-lookup.target

[Service]
Type=forking
ExecStart=/usr/local/nginx1.14/sbin/nginx
ExecReload=/usr/local/nginx1.14/sbin/nginx -s reload
ExecStop=/usr/local/nginx1.14/sbin/nginx -s stop

[Install]
WantedBy=multi-user.target
```


```
#/etc/init.d/nginx 添加执行权
#!/bin/bash
# chkconfig: - 85 15
# description: nginx is a World Wide Web server. It is used to serve
nginx=/usr/local/nginx1.14/sbin/nginx
case $1 in
start)
netstat -anptu|grep nginx
if [ $? -eq 0 ]; then
echo "nginx server is already running"
else
echo "nginx server begin start...."
$nginx

fi
;;
stop)
$nginx -s stop
if [ $? -eq 0 ]; then
echo "nginx server is stop"
else
echo "nginx server stop fail,try again!"
fi
;;
status)
netstat -anptu|grep nginx
if [ $? -eq 0 ]; then
echo "nginx server is running"
else
echo "nginx server is stoped"
fi
;;
restart)
$nginx -s reload
if [ $? -eq 0 ]; then
echo "nginx server is begin restart"
else
echo "nginx server restart fail!"
fi
;;
*)
echo "please enter {start|restart|status|stop}"
;;
esac
```



    