# ps-系统进程排序

【ps aux排序】

## 查看进程占用内存和cpu
    
 
## 按内存升序排列；
    ps aux --sort=+rss

 
## 按内存降序排列；
    ps aux --sort=-rss

 
## 按cpu升序排列；
    ps aux --sort=+%cpu

 
## 为按cpu降序排列。
    ps aux --sort=-%cpu

