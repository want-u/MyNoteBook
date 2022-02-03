# Filebeat-采集多个目录日志

https://blog.csdn.net/vip100549/article/details/79657574

[toc]

## 配置filebeat

为了lostash能区分不同目录发过来的日志，我们使用 tags属性

修改filebeat的配置文件 filebeat.yml

修改为如下配置，开启2个prospectors，收集2个目录下的日志，同样支持这样的写法：/var/log/*/*.log

```
vi filebeat/conf/filebeat.yml 
```


```
filebeat.inputs:
- type: log
  # Change to true to enable this prospector configuration.
  enabled: true
  # Paths that should be crawled and fetched. Glob based paths.
  paths:
    - /var/elk/logs1/*.log
  tags: ["log1"]
  
- type: log
  enabled: true
  paths:
    - /var/elk/logs2/*.log
  tags: ["log2"]

# 同样添加输出到lostash的配置
output.logstash:
  # The Logstash hosts
  hosts: ["logstash:5044"]
```

## 配置lostash


```
vi logstash/conf/logstash.conf
```

编辑并添加以下内容：

```
##input输入日志  beats用于接收filebeat的插件  codec设置输入日志的格式 port端口为logstash的端口
input {
  beats {
    port => 5044
  }
}

##filter对数据过滤操作
filter {
}

##output配置输出elasticsearch地址 可配多个 index为elasticsearch的索引，通过在kibana中Create index pattern去匹配
##hosts 的公网ip，也可以填写docker-compose.yml中logstash的容器名如 "http://elasticsearch:9200"（前提是同属于一个docker network，且类型为bridge）
output {
    if "log1" in [tags] {      #写入iislog日志到 es
        elasticsearch{
          hosts => ["http://elasticsearch:9200"]
          index => "log1-%{+YYYY.MM.dd}"  
        }
        stdout {codec => rubydebug}
    }
    if "log2" in [tags] {      #写入iislog日志到 es
        elasticsearch{
          hosts => ["http://elasticsearch:9200"]
          index => "log2-%{+YYYY.MM.dd}"  
        }
        stdout {codec => rubydebug}
    }
}
```

## redis的使用配置

### 修改前端lostash 的 shipper 配置文件

开启beat的收集端口，发送到redis

判断tags中，是否含有我们在filebeat配置中的根据目录设定的关键字，放入redis中


```
# redis的使用配置，没有则忽略
input{
        beats {
            port => 5044
        }
}
output{
    if "iislog1" in [tags]{    #写入iislog日志到redis
    redis  {
                host => "172.16.1.176"
                port => "6379"
                db => "3"
                data_type => "list"
                key => "iislog1"
        }
    }
    if "iislog2" in [tags]{    #写入iislog日志到redis
    redis  {
                host => "172.16.1.176"
                port => "6379"
                db => "3"
                data_type => "list"
                key => "iislog2"
        }
    }
}
```

### 修改后端 lostash 的 indexer 配置文件


```
input{
        redis  {                
                host => "172.16.1.176"
                port => "6379"
                db => "3"
                data_type => "list"
                key => "iislog1"
                type => "iislog1"
        }
        redis  {                
                host => "172.16.1.176"
                port => "6379"
                db => "3"
                data_type => "list"
                key => "iislog2"
                type => "iislog2"
        }
}
output{
    if [type] == "iislog1" {      #写入iislog日志到 es
         elasticsearch{
          hosts => ["172.16.1.176:9200"]
          index => "iislog1-%{+YYYY.MM.dd}"  
        }
    }
    if [type] == "iislog2" {      #写入iislog日志到 es
         elasticsearch{
          hosts => ["172.16.1.176:9200"]
          index => "iislog2-%{+YYYY.MM.dd}"  
        }
    }
}
```

## 测试效果

启动ELK ，进入kibana，然后手动写入一些日志，或者产生一些日志，可以看到

![这里写图片描述](https://i.loli.net/2018/03/23/5ab46a0b26e25.png)

kibana中 看到tags区分了不同目录的日志，同样tags也可以在lostash 的filter plugin 中区分不同目录的日志做不同的操作

