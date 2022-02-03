# pwdx-查找程序执行路径

https://www.cnblogs.com/gaoyuechen/p/10466033.html

pwdx
通过PID号查找文件对应的启动目录

在linux 64位 5.4及SunOS 5.10上测试通过

通常的做法：  

```
[root@app1 bin]# ps -ef | grep java  
root      4993 4964 20 Oct22 ?        1-09:03:43 /usr/java/jdk1.6.0_07/bin/java 
-Dprogram.name=run.sh -Duser.timezone=GMT+08:00 -Djava.awt.headless=true 
-Dorg.jboss.mq.il.uil2.useServerHost=true 
-server -Xms3096m -Xmx3096m -Xmn1024m -XX:PermSize=1024m 
-XX:MaxPermSize=1024m -Xss256K -XX:+DisableExplicitGC 
-XX:SurvivorRatio=1 -XX:+UseConcMarkSweepGC -XX:+UseParNewGC 
-XX:+CMSParallelRemarkEnabled -XX:+UseCMSCompactAtFullCollection 
-XX:CMSFullGCsBeforeCompaction=0 -XX:+CMSClassUnloadingEnabled 
-XX:LargePageSizeInBytes=128M -XX:+UseFastAccessorMethods 
-XX:+UseCMSInitiatingOccupancyOnly -XX:CMSInitiatingOccupancyFraction=80 
-XX:SoftRefLRUPolicyMSPerMB=0 -XX:+PrintClassHistogram 
-XX:+PrintGCDetails -XX:+PrintGCTimeStamps -XX:+PrintHeapAtGC 
-Djava.net.preferIPv4Stack=true -Djava.endorsed.dirs=/app/Service/lib/endorsed 
-classpath /app/Service/bin/run.jar:/usr/java/jdk1.6.0_07/lib/tools.jar org.jboss.Main -b app1  
root     31446 29924 0 09:44 pts/2    00:00:00 grep java
```

   
输出目录非常长，要找到目录需要从其中找dirs或用命令过滤。  
   
通过pwdx可直接找到程序的启动路径：  

```
[root@app1 bin]# jps -l  
31480 sun.tools.jps.Jps  
4993 org.jboss.Main  
[root@app1 bin]# jps -l | grep -v jps  
4993 org.jboss.Main  
[root@app1 bin]# pwdx `jps -l | grep -v jps | awk '{print $1}'`  
4993: /app/Service/bin  
[root@app1 bin]#
```
