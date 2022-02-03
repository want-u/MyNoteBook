# Mysql导入csv文件

（注意：需要修改对应的路径，表名，分割符）

- 登录mysql，进入数据库；
- 进入到对应的表里面，输入以下内容：


```
含有中文数据：
load data infile 'E:/qzkh.csv'
into table dmpa_test character set gb2312
fields terminated by ',' optionally enclosed by '"' escaped by '"'
lines terminated by '\r\n';
```


 

```
不含中文数据：
load data infile 'E:/qzkh.csv'
into table dmpa_test
fields terminated by ',' optionally enclosed by '"' escaped by '"'
lines terminated by '\r\n';
```

