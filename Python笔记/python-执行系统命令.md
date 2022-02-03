# python-执行系统命令
- https://www.cnblogs.com/Zzbj/p/14141428.html

[toc]

```
# #!/usr/bin/env python
# -*- coding: utf-8 -*-

import os
import subprocess
from threading import Timer
```

## 示例代码


```
import logging
import subprocess
import timeit
from threading import Timer, Thread
```

```
# 定义日志输出级别与格式
logging.basicConfig(level=logging.INFO,
                    format='%(asctime)s - %(pathname)s[line:%(lineno)d] - %(levelname)s: %(message)s')
                    
                    
# 执行系统命令
def run_cmd(_cmd):
    _RunCmd = subprocess.Popen(_cmd, shell=True, close_fds=True, stdin=subprocess.PIPE, stdout=subprocess.PIPE,
                               stderr=subprocess.PIPE)
    _RunCmdStdout, _RunCmdStderr = _RunCmd.communicate()
    _RunCmdReturn = _RunCmd.wait()
    # logging.debug('Run Cmd: [{}][Return {}]'.format(_cmd, _RunCmdReturn))
    return _RunCmdReturn, _RunCmdStdout, _RunCmdStderr
    # stdout 为多行时，使用 run_cmd(cmd)[1].splitlines()
    # 命令中包含大括号{}时，使用连续两个大括号{{}}表示大括号{}本身: cmd = "df -h {mount_point} | grep / | awk '{{print $5}}'".format(mount_point=mount_point)
                    
```
```
# 执行系统命令（定义超时时间）
def run_cmd(_cmd, _timeout_sec=60):
    _RunCmdReturn, _RunCmdStdout, _RunCmdStderr = 1, 0, 0
    _RunCmd = subprocess.Popen(_cmd, shell=True, close_fds=True, stdin=subprocess.PIPE, stdout=subprocess.PIPE,
                               stderr=subprocess.PIPE)
    _cmd_timer = Timer(_timeout_sec, _RunCmd.kill)
    try:
        _cmd_timer.start()
        _RunCmdStdout, _RunCmdStderr = _RunCmd.communicate()
        _RunCmdReturn = _RunCmd.wait()
    finally:
        # logging.debug('Run Cmd: [{}][Return {}]'.format(_cmd, _RunCmdReturn))
        _cmd_timer.cancel()
        return _RunCmdReturn, _RunCmdStdout, _RunCmdStderr


# 输出程序运行时间（装饰器）
def run_time(main_fun):
    def wrapper():
        logging.info('Running...')
        start = timeit.default_timer()
        main_fun()
        end = timeit.default_timer()
        logging.info('Running time: %s Seconds' % (end - start))

    return wrapper
    
    
# 多进程类，获取运行函数的 返回值
class MyThread(Thread):
    def __init__(self, target, args=()):
        super(MyThread, self).__init__()
        self.result = None
        self.target = target
        self.args = args

    def run(self):
        self.result = self.target(*self.args)
        # 在执行函数的同时，把结果赋值给result, 然后通过get_result函数获取返回的结果

    def get_result(self):
        try:
            return self.result
        except Exception as e:
            logging.error(e)
            return None
    

# 对远程主机批量执行系统命令（免密环境）
def mul_remote_cmd(_ips, _cmd):
    _Result = {'Return': {}, 'Stdout': {}, 'Stderr': {}}
    _threads = []
    for _each_ip in _ips:
        _t = MyThread(target=run_cmd, args=('ssh {} "{}"'.format(_each_ip, _cmd),))
        _t.start()
        _threads.append(_t)
    for _each_ip, _t in zip(_ips, _threads):
        _t.join()
        _Result['Return'][_each_ip], _Result['Stdout'][_each_ip], _Result['Stderr'][_each_ip] = _t.get_result()
    return _Result
    # 返回{'Return': {ip:code, ip:code...}, 'Stdout': {ip:std, ip:std...}, 'Stderr': {}}
    # 取出返回的sdt：mul_remote_cmd(ips , _cmd)['Stdout'].values()

```




## 方法一：os.system()

```
# 返回值：返回对应状态码，且状态码只会有0(成功)、1、2。
# 其它说明：os.system()的返回值并不是执行程序的返回结果。而是一个16位的数，它的高位才是返回码。也就是说os.system()执行返回256即 0×0100，返回码应该是其高位0×01即1。所以要获取它的状态码的话，需要通过>>8移位获取。
def adb_shell(cmd):
    exit_code = os.system(cmd)
    return exit_code>>8
    # os.system(cmd)命令会直接把结果输出，所以在不对状态码进行分析处理的情况下，一般直接调用即可
    # os.system(cmd)
```



## 方法二：os.popen()

```
# 返回值：返回脚本命令输出的内容
# 其它说明：os.popen()可以实现一个“管道”，从这个命令获取的值可以继续被调用。而os.system不同，它只是调用，调用完后自身退出，执行成功直接返回个0。
def adb_shell(cmd):
    result = os.popen(cmd).read()
    return result
```



## 方法三：subprocess.Popen()

```
# 返回值：Popen类的构造函数，返回结果为subprocess.Popen对象，脚本命令的执行结果可以通过stdout.read()获取。
def adb_shell(cmd):
    # 执行cmd命令，如果成功，返回(0, 'xxx')；如果失败，返回(1, 'xxx')
    res = subprocess.Popen(cmd, shell=True, stdin=subprocess.PIPE, stdout=subprocess.PIPE, stderr=subprocess.PIPE) # 使用管道
    result = res.stdout.read()  # 获取输出结果
    res.wait()  # 等待命令执行完成
    res.stdout.close() # 关闭标准输出
    return result
```



## 方法四：subprocess.getstatusoutput()

```
# 返回值：返回是一个元组，如果成功，返回(0, 'xxx')；如果失败，返回(1, 'xxx')
def adb_shell(cmd):
    result = subprocess.getstatusoutput(cmd)
    return result


cmd = 'adb shell dumpsys activity | grep "Run #"'
print(adb_shell(cmd))
```
