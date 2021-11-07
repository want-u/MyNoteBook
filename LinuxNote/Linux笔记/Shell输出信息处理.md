# Shell输出信息处理

https://bbs.csdn.net/topics/90286047

将shell命令的输出信息同时显示在屏幕和保存到日志文件中

```
1>直接覆盖日志文件
ls -l | tee ./t.log

2>将输出内容附加到日志文件
ls -l | tee -a ./t.log
```
