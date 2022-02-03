# Nginx高可用脚本

```
#!/bin/bash
#监控nginx进程
listening="nginx"
vip='172.17.100.100'
start_vip='ifconfig eth0:0 172.17.100.100/24'
stop_vip='ifconfig eth0:0 down'
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
CheckVip(){
    VIP_NUM=`ifconfig |grep $vip |wc -l`
    if [ $VIP_NUM-eq 0 ];then
        return 0   
    else
        return 1   
    fi
}

while true;do
    CheckProcess
    CheckQQ_RET=$?
    if [ $CheckQQ_RET -eq 0 ];then
        CheckVip
        Check_Vip=$?
        if [ $Check_Vip -eq 0 ];then
          $($start_vip)
          ssh $($stop_vip)
        fi
    
    else
        $($stop_vip)
        ssh $($start_vip)
    
    fi
    
    sleep 1

done
```

