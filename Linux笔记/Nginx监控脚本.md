# Nginx监控脚本

```
#!/bin/bash
#监控nginx进程
listening="nginx"
CheckProcess(){   
    if [ "$listening" = "" ];then   
        return 1   
    fi

    PROCESS_NUM=`ps -ef | grep "$listening" | grep -v "grep" | wc -l`   
    if [ $PROCESS_NUM -eq 0 ];then
        return 0   
    else
        return 1   
    fi     
}
 
while true; do  
    CheckProcess "$listening"
    CheckQQ_RET=$?
    if [ $CheckQQ_RET -eq 0 ];then   
        # echo "$listening is down! restarting..."
        exec $listening
    fi
    sleep 1
done &
```






```
#!/bin/bash
#原版脚本地址：https://www.csdn.net/gather_25/OtTacg2sMjM4Ny1ibG9n.html
CheckProcess()   
{   
        if [ "$1" = ""];   
        then   
                return 1   
        fi   

    PROCESS_NUM=`ps -ef | grep "$1" | grep -v "grep" | wc -l`   
    if [ $PROCESS_NUM -eq 1 ];   
    then   
        return 0   
    else  
        return 1   
    fi       
}    


while [ 1 ] ; do  
    CheckProcess "./a.out"  
    CheckQQ_RET=$?   
    if [ $CheckQQ_RET -eq 1 ];   
    then   
     killall -9 a.out   
     exec ./a.out &   
    fi   
    sleep 1   
done
```

