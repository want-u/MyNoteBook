# docker-compose新编排ELK+Filebeat

- [docker-compose编排ELK+Filebeat_u013068184的博客-CSDN博客_docker-compose elk filebeat](https://blog.csdn.net/u013068184/article/details/107433859)
- [ELK - nginx 日志分析及绘图 - hukey - 博客园 (cnblogs.com)](https://www.cnblogs.com/hukey/p/11519612.html)
- [记一次docker-compose 部署 elasticsearch:6.6.2遇到的坑_qq_15256981的博客-CSDN博客](https://blog.csdn.net/qq_15256981/article/details/88605824)

[toc]

ELK+Filebeat主要用于日志系统，主要包括四大组件：Elasticsearch、Logstash、Kibana以及Filebeat，也统称为Elastic Stack。

大体的一个架构流程：

![img](https:////upload-images.jianshu.io/upload_images/17925617-decb123125ea5385.png?imageMogr2/auto-orient/strip|imageView2/2/w/554/format/webp)

下面详细介绍 docker-compose 安装的过程（单机版），经测试可适用`6.8.1`以及`7.8.0`版本！

Git地址：https://gitee.com/luoxian1011/let/tree/master/elk

展示页面：

![image-20220202200121840](https://gitee.com/luoxian1011/pictures/raw/master//image-20220202200121840.png)

## （一）创建相关目录路径

------

先上整体的一个目录结构：

![img](https:////upload-images.jianshu.io/upload_images/17925617-02214634e9c6992e.png?imageMogr2/auto-orient/strip|imageView2/2/w/490/format/webp)

创建一个elk目录：

```shell
mkdir elk && cd elk
```

然后在其下分别创建 elasticsearch、logstash、kibana、filebeat 目录以及各目录相关的需要挂载到容器中的配置文件：

```shell
mkdir -p {elasticsearch,logstash,kibana,filebeat}
```

### 1）elasticsearch 配置

创建 conf、data、logs 文件夹用于挂载容器中的数据卷：

```shell
mkdir -p elasticsearch/conf
```

在 elasticsearch/conf 文件夹下新建一个 elasticsearch.yml 文件：

```shell
vi elasticsearch/conf/elasticsearch.yml
```

编辑并添加以下内容：

```shell
## default configuration in docker
cluster.name: "elasticsearch"     #集群名
network.host: 0.0.0.0             #主机ip
##network.bind_host: 0.0.0.0
##cluster.routing.allocation.disk.threshold_enabled: false
##node.name: es-master
##node.master: true
##node.data: true
http.cors.enabled: true         #允许跨域，集群需要设置
http.cors.allow-origin: "*"     #跨域设置
##http.port: 9200
##transport.tcp.port: 9300
##discovery.zen.minimum_master_nodes: 1
##discovery.zen.ping.unicast.hosts: *.*.*.*:9300,  *.*.*.*:9301,  *.*.*.*:9302
```

说明：

- conf ：用于挂载 elasticsearch 相关配置
- data：用于挂载 elasticsearch 的数据，比如索引；运行一段时间后会存在elasticsearch磁盘占满的问题，所以可自行了解清除数据策略
- logs：用于挂载 elasticsearch 的日志信息

### 2）logstash 配置

创建 logstash/conf文件夹用于挂载容器中的数据卷：

```shell
mkdir -p logstash/conf
```

在 logstash/conf文件夹下分别新建一个 logstash.yml 文件：

```shell
vi logstash/conf/logstash.yml 
```

编辑并添加以下内容：（注意代码中备注）

```shell
## 和kibana的host一样，也需要设置成0.0.0.0才能启动成功
http.host: "0.0.0.0"
## 除了可以使用docker-compose.yml中elasticsearch的容器名如 "http://elasticsearch:9200"（前提是同属于一个docker network，且类型为bridge），也可以直接改成公网ip
xpack.monitoring.elasticsearch.hosts: [ "http://elasticsearch:9200" ]
```

以及新建一个 logstash.conf 文件：

```shell
vi logstash/conf/logstash.conf 
```

编辑并添加以下内容：

- index : 定义索引名称
- stdout {codec => rubydebug} : 打印输出到屏幕
- [fileset] : 模块名，此处即为 nginx ; 在下面 filebeat/modules.d/nginx.yml 中定义
- [tags] : 标签 - 可以用来区分多个目录日志 ；在下面 filebeat/filebeat.yml 中定义
- mutate - convert : 可以设置的转换类型包括："integer"，"float" 和 "string"；若修改类型，在前端需要删除重建索引生效

```shell
##input输入日志  beats用于接收filebeat的插件  codec设置输入日志的格式 port端口为logstash的端口
input {
  beats {
    port => 5044
  }
}

##filter对数据过滤操作
filter {
    if [fileset][name] == "access" {
        json {
            source => "message"
            remove_field => "message"
            remove_field => "@timestamp"
        }
        date {
            match => ["time_local", "ISO8601"]
            target => "@timestamp"
        }
        grok {
            match => { "request" => "%{WORD:method} (?<url>.* )" }
        }
        mutate {
            remove_field => ["event","input","request","offset","prospector","source","type","beat"]
            rename => {"http_user_agent" => "agent"}
            rename => {"upstream_response_time" => "response_time"}
            rename => {"http_x_forwarded_for" => "x_forwarded_for"}
            split => {"x_forwarded_for" => ", "}
            convert => {"request_time" => "float"}
            convert => {"response_time" => "float"}
            convert => {"body_bytes_sent" => "integer"}
        }
    }
    if [fileset][name] == "error" {
        mutate {
            remove_field => ["@timestamp"]
        }
        grok {
            match => {"message" => "(?<datetime>%{YEAR}[./-]%{MONTHNUM}[./-]%{MONTHDAY}[- ]%{TIME}) \[%{LOGLEVEL:severity}\] %{POSINT:pid}#%{NUMBER}: %{GREEDYDATA:errormessage}(?:, client: (?<http_x_forwarded_for>%{IP}|%{HOSTNAME}))(?:, server: %{IPORHOST:domain}?)(?:, request: %{QS:request})?(?:, upstream: (?<upstream>\"%{URI}\"|%{QS}))?(?:, host: %{QS:request_host})?(?:, referrer: \"%{URI:referrer}\")?"}
        }
        date {
            match => ["datetime", "yyyy/MM/dd HH:mm:ss"]
            target => "@timestamp"
        }
        mutate {
            remove_field => ["message","request","http_referer","host","event","input","offset","prospector","source","type","tags","beat"]
        }
    }
}

##output配置输出elasticsearch地址 可配多个 index为elasticsearch的索引，通过在kibana中Create index pattern去匹配
##hosts 的公网ip，也可以填写docker-compose.yml中logstash的容器名如 "http://elasticsearch:9200"（前提是同属于一个docker network，且类型为bridge）
##标签定义于filebeat.yml 
output {
    if "test" in [tags] {      #写入日志到 es
        elasticsearch{
          hosts => ["http://elasticsearch:9200"]
          index => "test-%{+YYYY.MM.dd}"  
        }
        stdout {codec => rubydebug}
    }

    if [fileset][module] == "nginx" {      #写入日志到 es
        elasticsearch{
          hosts => ["http://elasticsearch:9200"]
          index => "logstash-nginx-%{+YYYY.MM.dd}"  
        }
        stdout {codec => rubydebug}
    }
}
```

说明：

- hosts 的公网ip，也可以填写docker-compose.yml中logstash的容器名如 `http://elasticsearch:9200`（前提是同属于一个docker network，且类型为bridge）
- `106.52.202.31` 需要替换成正在配置的服务器公网IP
- logstash.yml：用于挂载 logstash 相关配置（可自定义进行拓展，参考[官网文档](https://links.jianshu.com/go?to=https%3A%2F%2Fwww.elastic.co%2Fguide%2Fen%2Flogstash%2Fcurrent%2Flogstash-settings-file.html)）
- logstash.conf：用于挂载 logstash 日志处理配置文件（可自定义进行拓展，参考[官网文档](https://links.jianshu.com/go?to=https%3A%2F%2Fwww.elastic.co%2Fguide%2Fen%2Flogstash%2Fcurrent%2Fconfiguration.html)）

### 3）kibana 配置

创建 kibana/conf文件夹用于挂载容器中的数据卷：

```shell
mkdir -p kibana/conf
```

在 kibana/conf文件夹下分别新建一个 kibana.yml 文件：

```shell
vi kibana/conf/kibana.yml 
```

编辑并添加以下内容：

```shell
## Default Kibana configuration for docker target
server.name: "kibana"
## 必须设置为0.0.0.0才能访问到elasticsearch
server.host: "0.0.0.0"
## host的公网ip，也可以填写docker-compose.yml中elasticsearch的容器名如 "http://elasticsearch:9200"（前提是同属于一个docker network，且类型为bridge）
elasticsearch.hosts: [ "http://elasticsearch:9200" ]
## 汉化前端页面
i18n.locale: zh-CN
```

说明：

- host的公网ip，也可以填写docker-compose.yml中elasticsearch的容器名如 `http://elasticsearch:9200`（前提是同属于一个docker network，且类型为bridge）
- `106.52.202.31` 需要替换成正在配置的服务器公网IP
- kibana.yml：用于挂载 kibana 相关配置

### 4）filebeat 配置

1、创建 filebeat/conf文件夹用于挂载容器中的数据卷：

```shell
mkdir -p filebeat/modules.d
```

2、在 filebeat/conf文件夹下分别新建一个 filebeat.yml 文件：

```shell
vi filebeat/filebeat.yml 
```

编辑并添加以下内容：

- type: log : 收集多个目录时，可以在 inputs 下定义多个字段
- paths : 要收集的日志文件的路径，容器内的文件路径，所以我们需要挂载
- tags : 区分多个目录日志的标签
- filebeat.config.modules : 定义模块目录

```shell
# ============================== Filebeat inputs ===============================
filebeat.inputs:
- type: log           #输入filebeat的类型 这里设置为log(默认)，即具体路径的日志  另外属性值还有stdin(键盘输入)、kafka、redis，具体可参考官网
  enabled: true       #开启filebeat采集
  ##symlinks: true    #采集软链接文件
  paths:              #配置采集全局路径，后期可根据不同模块去做区分
    - /var/elk/logs/*.log     # 指定需要收集的日志文件的路径(容器内的文件路径，所以我们需要挂载)
  fields:             #可想输出的日志添加额外的信息
    log_type: syslog
  tags: ["test"]

# ------------------------------ Logstash Output -------------------------------
output.logstash:
  hosts: ["logstash:5044"] #发送输出到logstash；host的公网ip，也可以填写docker-compose.yml中logstash的容器名如 "logstash:5044"（前提是同属于一个docker network，且类型为bridge）

# ============================== Filebeat modules ==============================

filebeat.config.modules:
  # Glob pattern for configuration loading
  path: ${path.config}/modules.d/*.yml

  # Set to true to enable config reloading
  reload.enabled: false

  # Period on which files under path should be checked for changes
  #reload.period: 10s

```

说明：

- host的公网ip，也可以填写docker-compose.yml中logstash的容器名如 `logstash:5044`（前提是同属于一个docker network，且类型为bridge）
- filebeat.yml：用于挂载 filebeat 相关配置，一些格式配置（如多行合并输出等）可自行了解，参考官网文档
- 注意paths参数的值，这里配置的是filebeat容器内的一个日志文件路径（自定义），而在服务器部署应用产生的日志路径是我们指定的（比如项目工程用到logback日志包，会在配置文件中指定日志输出路径），因此需要在docker-compose.xml文件中做数据卷挂载才能被filebeat日志采集器采集到并输出到logstash；/var/elk/logs/*.log表示该路径下以.log结尾的文件都会被当成日志采集

3、在filebeat/modules.d文件夹下分别新建一个 nginx.yml 文件：

```shell
vi filebeat/modules.d/nginx.yml
```

filebeat 传输 nginx 日志

- module : 模块名称
- var.paths : 收集日志的路径，用*泛匹配

```shell
- module: nginx
  # Access logs
  access:
    enabled: true

    # Set custom paths for the log files. If left empty,
    # Filebeat will choose the paths depending on your OS.
    var.paths: ["/var/log/nginx/*access.log"]
    

  # Error logs
  error:
    enabled: true

    # Set custom paths for the log files. If left empty,
    # Filebeat will choose the paths depending on your OS.
    var.paths: ["/var/log/nginx/*error.log"]

  # Ingress-nginx controller logs. This is disabled by default. It could be used in Kubernetes environments to parse ingress-nginx logs
  ingress_controller:
    enabled: false

    # Set custom paths for the log files. If left empty,
    # Filebeat will choose the paths depending on your OS.
    #var.paths:

```



## （二）编写docker-compose.yml文件

------

在 elk 目录创建docker-compose.yml编排文件：

```shell
vi docker-compose.yml
```

编辑并添加以下内容（四大组件的镜像版本号应保持一致，避免出现问题）：

- TZ=Asia/Shanghai : 定义容器时区
- TAKE_FILE_OWNERSHIP=true : 就是这货 volumes 挂载权限问题
- filebeat下的volumes : 同时配置挂载需要扫描得日志目录，因为扫描的是filebeat容器内的文件路径
  - 比如此处/var/lib/docker/volumes/lnmp_nginx-log/_data（本地nginx日志目录）

```shell
version: "3.4" #版本号
services:
  ########## elk日志套件(镜像版本最好保持一致) ##########
  elasticsearch:                      #服务名称
    container_name: elasticsearch     #容器名称
    image: docker.elastic.co/elasticsearch/elasticsearch:6.8.1     #使用的镜像 elastisearch：分布式搜索和分析引擎，提供搜索、分析、存储数据三大功能
    restart: on-failure               #重启策略 1)no:默认策略，当docker容器重启时，服务也不重启 2)always:当docker容器重启时，服务也重启 3)on-failure:在容器非正常退出时（退出状态非0），才会重启容器
    ports:                            #避免出现端口映射错误，建议采用字符串格式
      - "9200:9200"
      - "9300:9300"
    environment:                      #环境变量设置 也可在配置文件中设置，environment优先级高
      - TZ=Asia/Shanghai
      - discovery.type=single-node    #单节点设置
      - bootstrap.memory_lock=true    #锁住内存 提高性能
      - "ES_JAVA_OPTS=-Xms512m -Xmx512m"     #设置启动内存大小 默认内存/最大内存
      - TAKE_FILE_OWNERSHIP=true      # 就是这货 volumes 挂载权限问题
    ulimits:
      memlock:
        soft: -1
        hard: -1
    volumes:                         #挂载文件
      - ./elasticsearch/data:/usr/share/elasticsearch/data
      - ./elasticsearch/conf/elasticsearch.yml:/usr/share/elasticsearch/config/elasticsearch.yml
      - ./elasticsearch/logs:/usr/share/elasticsearch/logs

  kibana:
    container_name: kibana
    image: docker.elastic.co/kibana/kibana:6.8.1     #kibana:数据分析可视化平台
    depends_on:
      - elasticsearch
    restart: on-failure
    ports:
      - "5601:5601"
    environment:
      - TZ=Asia/Shanghai
    volumes:
      - ./kibana/conf/kibana.yml:/usr/share/kibana/config/kibana.yml

  logstash:
    container_name: logstash
    image: docker.elastic.co/logstash/logstash:6.8.1 #logstash:日志处理
    command: logstash -f /usr/share/logstash/pipeline/logstash.conf
    depends_on:
      - elasticsearch
    restart: on-failure
    ports:
      - "9600:9600"
      - "5044:5044"
    environment:
      - TZ=Asia/Shanghai
    volumes:                         #logstash.conf日志处理配置文件  格式：#输入 input{}   #分析、过滤 filter{}   #输出 output{}
      - ./logstash/conf/logstash.yml:/usr/share/logstash/config/logstash.yml
      - ./logstash/conf/logstash.conf:/usr/share/logstash/pipeline/logstash.conf

  filebeat:
    container_name: filebeat
    image: docker.elastic.co/beats/filebeat:6.8.1     #filebeat:轻量级的日志文件数据收集器，属于Beats六大日志采集器之一
    depends_on:
      - elasticsearch
      - logstash
      - kibana
    restart: on-failure
    environment:
      - TZ=Asia/Shanghai
    volumes:                         #filebeat.yml配置.log文件的日志输出到logstash
      - ./filebeat/filebeat.yml:/usr/share/filebeat/filebeat.yml
      - ./filebeat/modules.d/nginx.yml:/usr/share/filebeat/modules.d/nginx.yml
      - ./logs:/var/elk/logs         #同时配置挂载需要扫描得日志目录，因为扫描的是filebeat容器内的文件路径
      - /var/lib/docker/volumes/lnmp_nginx-log/_data:/var/log/nginx

```

说明：

- 假如配置environment环境变量，则会覆盖yml文件中的配置，否则就会读取yml文件配置；因此environment优先级大于yml文件



```shell
# 由于这四个容器间需要互相通信，所以我们需要创建一个名称为 elk 的 network：
docker network create elk
# 在不设置 driver 属性时，默认是bridge，可自行查看docker network相关内容

# 查看是否创建成功：
docker network ls
# 在yml文件中不指定网络，会默认创建并使用 bridge的elk_default
```

## （三）启动docker-compose编排

最后启动docker-compose编排：

```shell
docker-compose up -d
```

查看是否启动成功：

```shell
docker-compose ps
```

![img](https:////upload-images.jianshu.io/upload_images/17925617-4d057e72d23e1c81.png?imageMogr2/auto-orient/strip|imageView2/2/w/792/format/webp)

或者：

```shell
docker ps
```

假如启动失败或者想查看启动日志，可执行 logs 相关命令：

```shell
docker-compose logs -f
```

## （四）部署elasticsearch-head[可选]

```
docker pull mobz/elasticsearch-head:5
```

启动服务

```
sudo docker run -d --name elasticsearch-head -p 9100:9100 docker.io/mobz/elasticsearch-head:5
```

浏览器访问：http://localhost:9100/

![image-20220201005615509](https://gitee.com/luoxian1011/pictures/raw/master//image-20220201005615509.png)

## （五）测试 ELK+Filebeat 日志采集以及Kibana搜索

------

启动成功后，我们可以简单测试！

结合docker-compose.yml中 filebeat容器的/media/elk/logs:/var/elk/logs数据卷挂载配置信息，以及 filebeat.yml 文件的 paths 配置信息，我们只要在 /media/elk/logs 路径下创建以.log结尾的日志文件，Filebeat就能读取这些文件中的数据并采集输出到Logstash，然后经Logstash进行过滤等操作，发送到Elasticsearch，最后在Kibana控制台创建索引格式进行条件筛查就能找到我们所需的信息。

在 /media/elk/logs 路径下创建一个日志文件 elk-test.log 文件：

```shell
# 更新日志，才会触发日志收集

echo "==========[log1] TEST LOG INFO ==========" >> logs/elk-test.log
```

模拟输出日志信息到该文件中：

```shell
docker-compose logs >> logs/elk-test.log
```

然后执行

```shell
docker-compose logs -f | grep 'elk-test.log'
```

可看到 Logstash格式化日志信息：

![img](https:////upload-images.jianshu.io/upload_images/17925617-1a3d8c984a32c2d0.png?imageMogr2/auto-orient/strip|imageView2/2/w/1200/format/webp)

之后访问Kibana控制台（ip+映射的端口，默认是5601，第一次访问可能加载较慢）：

http://localhost:5601

创建索引模式：

![img](https:////upload-images.jianshu.io/upload_images/17925617-ec195e03c14f9c74.png?imageMogr2/auto-orient/strip|imageView2/2/w/1200/format/webp)

![img](https:////upload-images.jianshu.io/upload_images/17925617-ec73da8b372a2e3b.png?imageMogr2/auto-orient/strip|imageView2/2/w/1200/format/webp)

说明：第4步中的输入框，应该输入存在索引或索引通配符，索引已在 **logstash.conf** 中定义，所以这里输入 **test** 即可匹配到

然后在 Discover 中就能查找出日志信息（关于怎么使用Kibana，可自行了解）

![img](https:////upload-images.jianshu.io/upload_images/17925617-1f903549e97987e0.png?imageMogr2/auto-orient/strip|imageView2/2/w/1200/format/webp)

以下是6.8.1版本的，在7.8.0版本中，控制台ui略有改变，但步骤是一样的：

![img](https:////upload-images.jianshu.io/upload_images/17925617-df5429ae3d7dc536.png?imageMogr2/auto-orient/strip|imageView2/2/w/1200/format/webp)

## （六）可能会遇到的问题

------

### 1. docker-compose启动时Elasticsearch报错

```shell
elasticsearch    | Error: Could not create the Java Virtual Machine.
elasticsearch    | Error: A fatal exception has occurred. Program will exit.
elasticsearch    | [0.001s][error][logging] Error opening log file 'logs/gc.log': Permission denied
elasticsearch    | [0.001s][error][logging] Initialization of output 'file=logs/gc.log' using options 'filecount=32,filesize=64m' failed.
```

解决办法：因为挂载卷的访问权限不足，所以我们需要给Elasticsearch挂载卷授权，进入到/media/elk/elasticsearch分别给三个挂载卷授权：

```shell
chmod 777 conf data logs
```

### 2. docker-compose启动时发Elasticsearch或Logstash连接失败

类似以下报错信息：

```shell
logstash         | [2020-07-19T05:41:36,488][WARN ][logstash.monitoringextension.pipelineregisterhook] xpack.monitoring.enabled has not been defined, but found elasticsearch configuration. Please explicitly set `xpack.monitoring.enabled: true` in logstash.yml
logstash         | [2020-07-19T05:41:37,517][INFO ][logstash.licensechecker.licensereader] Elasticsearch pool URLs updated {:changes=>{:removed=>[], :added=>[http://0.0.0.0:9200/]}}
logstash         | [2020-07-19T05:41:37,740][WARN ][logstash.licensechecker.licensereader] Attempted to resurrect connection to dead ES instance, but got an error. {:url=>"http://0.0.0.0:9200/", :error_type=>LogStash::Outputs::ElasticSearch::HttpClient::Pool::HostUnreachableError, :error=>"Elasticsearch Unreachable: [http://0.0.0.0:9200/][Manticore::SocketException] Connection refused (Connection refused)"}
logstash         | [2020-07-19T05:41:37,808][WARN ][logstash.licensechecker.licensereader] Marking url as dead. Last error: [LogStash::Outputs::ElasticSearch::HttpClient::Pool::HostUnreachableError] Elasticsearch Unreachable: [http://0.0.0.0:9200/][Manticore::SocketException] Connection refused (Connection refused) {:url=>http://0.0.0.0:9200/, :error_message=>"Elasticsearch Unreachable: [http://0.0.0.0:9200/][Manticore::SocketException] Connection refused (Connection refused)", :error_class=>"LogStash::Outputs::ElasticSearch::HttpClient::Pool::HostUnreachableError"}
logstash         | [2020-07-19T05:41:37,818][ERROR][logstash.licensechecker.licensereader] Unable to retrieve license information from license server {:message=>"Elasticsearch Unreachable: [http://0.0.0.0:9200/][Manticore::SocketException] Connection refused (Connection refused)"}
logstash         | [2020-07-19T05:41:37,879][ERROR][logstash.monitoring.internalpipelinesource] Failed to fetch X-Pack information from Elasticsearch. This is likely due to failure to reach a live Elasticsearch cluster.
```

解决办法：因为是在Ubuntu系统公网上部署，所以查看防火墙端口是否开启，或者假如是阿里云或腾讯云服务器，需要在安全组开放Elasticsearch、Logstash、Kibana相关端口

### 3. docker-compose启动时Elasticsearch报错

```shell
max virtual memory areas vm.max_map_count [65530] is too low, increase to at least [262144]
```

解决办法：因为用户拥有的内存权限太小，至少需要262144；切换到root超管，进入到/etc目录，执行编辑`vim sysctl.conf`，在最末添加属性行`vm.max_map_count=262144`，保存退出后执行加载系统参数命令`sudo sysctl -p`，然后输入命令`sysctl -a|grep vm.max_map_count`即可看到修改后的配置值，最后重新启动即可。

### 4. docker-compose启动时Elasticsearch报错

```shell
java.nio.file.AccessDeniedException: /usr/share/elasticsearch/data/nodes/0/node.lock
```

解决办法：把Elasticsearch的挂载数据卷data路径下的数据删掉。