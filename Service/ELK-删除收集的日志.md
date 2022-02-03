# 删除ELK收集的日志

https://www.cnblogs.com/A121/p/12029235.html

[toc]

## 查看ES索引信息：

http://localhost:9200/_cat/indices?v

![img](https://img2018.cnblogs.com/i-beta/1413093/201912/1413093-20191212150101025-1248654246.jpg)

##  删除日志：


```

curl -XDELETE "FH-UMP-ELK-VIP:9200/*-ump-2019.06.25"

curl -XDELETE "FH-UMP-ELK-VIP:9200/*-ES名称"
```

```
curl -XDELETE "localhost:9200/*"
```


## 日志真实数据存放的目录：


```
ll /media/elk/elasticsearch/data/nodes/0/indices
```


![img](https://img2018.cnblogs.com/i-beta/1413093/201912/1413093-20191212150324177-443295741.jpg)