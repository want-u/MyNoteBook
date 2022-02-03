# Tomcat多实例管理脚本

```
#!/bin/bash
case $1 in
'-h')
	echo "/root/tomcats.sh [start|stop|version]"
	exit 1
	;;
*)
	/usr/local/tomcat8/bin/catalina.sh $1
	/usr/local/tomcat8_2/bin/catalina.sh $1
	/usr/local/tomcat8_3/bin/catalina.sh $1
	/usr/local/tomcat8_4/bin/catalina.sh $1
	;;
esac
```


