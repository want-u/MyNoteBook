# docker-compose设置时区

https://blog.csdn.net/liuwei0376/article/details/95061844

[toc]

docker中如果对时区不加限制，默认会采用格林尼治时间（GMT），这给日常程序部署、日志查看、错误调试等带来了诸多麻烦与困扰。
每次都需要将event发生的显示时间+8个时区，手工换算成北京时间，想想都令人抓狂。
强调: 实验的docker-compose为2.2版本,其他版本未做相应测试, 请谨慎判别.

## Dockerfile中配置时区
在Dockerfile中，可以通过如下方式添加中国时区：

```
FROM docker.io/centos
MAINTAINER DAVID

# 使用RUN命令设置时区
RUN cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && echo 'Asia/Shanghai'>/etc/timezone
```


## docker-compose中配置时区
但如果docker程序是以docker-compose.yaml构建，那么时区这块需要如何设置呢？

经过实测，如下两种方式都可行。

方式一：

```
environment:
  - SET_CONTAINER_TIMEZONE=true
  - CONTAINER_TIMEZONE=Asia/Shanghai
```

方式二：

```
environment:
  - TZ=Asia/Shanghai
```

