# nginx-日志分析

- https://cloud.tencent.com/developer/article/1444720
- https://developer.aliyun.com/article/90906
- https://www.cnblogs.com/ssgeek/p/12119657.html

## 思路
1. 前端机access日志，确认前段机
2. kvclient日志，确认后端机
3. kv日志全过滤

## 代码

以kv版本划分个sr日志路径

```
kv_84 = {
    sr: log_path
}
kv_86 = {
    sr: log_path
}
```


## 命令行

```
❯ tail -1 access.log
{"remote_addr":"172.18.0.1","domain":"localhost","http_x_forwarded_for":"-","time_local":"2022-02-04T00:09:11+08:00","request":"GET / HTTP/1.1","request_body":"-","status":200,"body_bytes_sent":"15","http_referer":"-","upstream_response_time":"-","request_time":"0.000","http_user_agent":"python-requests/2.27.1","upstream_addr":"-","upstream_status":"-"}

```


```
# 统计status 出现的次数
❯ tail -1000 access.log | awk -F, '{print $7}' | awk -F ':' '{print $2}' | sort | uniq -c | sort -r
    763 200
    237 404

# 统计请求的URL
❯ tail -1000 access.log | awk -F, '{print $5}' | awk -F '"' '{print $4}' | sort | uniq -c | sort -r
    267 "GET /test.php HTTP/1.1"
    258 "GET / HTTP/1.1"
    238 "GET /testdb.php HTTP/1.1"
    237 "GET /test_404_page HTTP/1.1"

# 统计请求的IP地址
❯ tail -1000 access.log | awk -F, '{print $1}' | awk -F '"' '{print $4}' | sort | uniq -c | sort -r
   1000 "172.18.0.1"

# 统计请求时间前十
❯ tail -1000 access.log | awk -F, '{print $11}' | awk -F '"' '{print $4}' | sort -rn | head
0.003
0.003
0.003
0.003
0.003
0.003
0.003
0.003
0.003
0.003

```
