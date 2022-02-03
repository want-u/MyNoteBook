# mysqldump备份
自动全部备份+增量备份
## 1.全备脚本
    [root@s1 ~]# vim DBFullyBack.sh

```
#!/bin/bash
# use mysqldump to Fully backup mysql data per week!
BakDir=/home/mysql/backup
LogFile=/home/mysql/backup/bak.log
Date=` date +%Y%m%d`
Begin=` date +"%Y年%m月%d日 %H:%M:%S"`
DumpFile=$Date.sql
GZDumpFile=$Date.sql.tgz

[ ! -d $BakDir ] && mkdir -p $BakDir
cd  $BakDir
/usr/local/mysql/bin/mysqldump  -uroot -pwanan1414 --quick --all-databases --flush-logs --delete-master-logs --single-transaction > $DumpFile
/bin/tar  czvf $GZDumpFile $DumpFile
/bin/rm  $DumpFile
echo  开始:$Begin 结束:$Begin  $GZDumpFile succ >> $LogFile
[ ! -d $BakDir/daily ] && mkdir $BakDir/daily
rm -f $BakDir/daily/*
```

## 2.增量脚本
    [root@s1 ~]# vim DBDailyBack.sh

```

#!/bin/bash
# use cp to backup mysql data everyday!
BakDir=/home/mysql/backup/daily
BinDir=/usr/local/mysql/data
LogFile=/home/mysql/backup/bak.log
BinFile=/usr/local/mysql/data/mysql_bin.index
[ ! -d $BakDir ] && mkdir -p $BakDir
/usr/local/mysql/bin/mysqladmin  -uroot -pwanan1414 flush-logs
#这个是用于产生新的mysql-bin.00000*文件
Counter=` wc  -l $BinFile | awk  '{print $1}' `
NextNum=0
#这个for循环用于比对$Counter,$NextNum这两个值来确定文件是不是存在或最新的。
for file in ` cat  $BinFile`
do
         base=` basename  $file `
         #basename用于截取mysql-bin.00000*文件名，去掉./mysql-bin.000005前面的./
         NextNum=` expr $NextNum + 1`
         if  [ $NextNum -eq $Counter ]
         then
                 echo  $base skip!  >> $LogFile
         else
                 dest=$BakDir/$base
                 if ( test  -e $dest)
                 #test -e用于检测目标文件是否存在，存在就写exist!到$LogFile去。
                 then
                         echo   $base exist! >> $LogFile
                 else
                         cp  $BinDir/$base $BakDir
                         echo  $base copying >> $LogFile
                 fi
         fi
done
echo  ` date +"%Y年%m月%d日 %H:%M:%S" ` $Next Bakup succ! >> $LogFile
```



## 3.做定时任务
    [root@s1 ~]# crontab -e



```
#每个星期日凌晨3:00执行完全备份脚本
0 3 * * 0  /root/DBFullyBack.sh >/dev/null  2>&1
#周一到周六凌晨3:00做增量备份
0 3 * * 1-6  /root/DBDailyBack.sh >/dev/null  2>&1
```

