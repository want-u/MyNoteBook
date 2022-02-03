# threading
https://www.cnblogs.com/hhh5460/p/5178088.html
[toc]
## 1. 多线程
```
# -*- coding: utf-8 -*-
# @Author  : LuoXian
# @Date    : 2020/2/15 17:40
# Software : PyCharm
# version： Python 3.8
# @File    : threading_多线程.py
import threading
import time


def first_thread():
    for i in range(3):
        # 打印当前进程名
        print('第一个函数的', threading.currentThread())
        time.sleep(1)


def second_thread():
    for i in range(3):
        print('第二个函数的', i)
        time.sleep(1)


def main():
    # 创建进程对象 , target= 指定函数名，函数参数使用 args=
    t1 = threading.Thread(target=first_thread)
    t2 = threading.Thread(target=second_thread)
    # 启动进程
    t1.start()
    t2.start()
    # 打印当前总进程数
    print(threading.enumerate())


def print_thread(i):
    print(i)
    time.sleep(1)


def run_thread():
    for i in range(100):
        t = threading.Thread(target=print, args=(i,))
        t.start()


if __name__ == '__main__':
    main()
    # run_thread()

```

```
import threading
def check_proxy(proxy):
    pass
    
def run():
    for i in range(10):
        print('正在执行   >>>>   ', i)
        t = threading.Thread(target=check_proxy, args=(i,))
        t.start()
```
## 2. 类写法

```
# -*- coding: utf-8 -*-
# @Author  : LuoXian
# @Date    : 2020/2/15 17:59
# Software : PyCharm
# version： Python 3.8
# @File    : threading_类写法.py
import threading
import time


class FirstThread(threading.Thread):
    def run(self):
        for i in range(3):
            # 打印当前进程名
            print('第一个函数的', threading.currentThread())
            time.sleep(1)


class SecondThread(threading.Thread):
    def run(self):
        for i in range(3):
            print('第二个函数的', i)
            time.sleep(1)


def main():
    t1 = FirstThread()
    t2 = SecondThread()

    t1.start()
    t2.start()


if __name__ == '__main__':
    main()

```
## 3. 全局变量和线程锁

锁是一种机制，用于保护那些会引起冲突的资源。

```
# -*- coding: utf-8 -*-
# @Author  : LuoXian
# @Date    : 2020/2/15 18:27
# Software : PyCharm
# version： Python 3.8
# @File    : threading_全局变量和线程锁.py
import threading

# 当在函数中对全局变量修改时， 应当加锁
count = 0
# 创建进程锁
thread_lock = threading.Lock()


# 当add_count函数不能在生成下一个进程前执行完成， 会发生数据污染
def add_count():
    # 给当前进程加锁
    thread_lock.acquire()
    global count
    for i in range(1000000):
        count += 1
    print(count)
    # 解锁
    thread_lock.release()


def main():
    for i in range(2):
        t = threading.Thread(target=add_count)
        t.start()


if __name__ == '__main__':
    main()

```

## 4. 获取多线程返回值

https://blog.csdn.net/qq_20663229/article/details/94484526

方法一：通过自定义线程类，继承Thread类，并复写run方法，在run方法中写入执行函数的方式，并把返回值赋值给result；然后通过调用get_result获取每个进程的返回值，代码如下：

```
import threading  
  
  
# 判断值是否为偶数  
def is_even(value):  
    if value % 2 == 0:  
        return True  
    else:  
        return False  
  
  
class MyThread(threading.Thread):  
    def __init__(self, func, args=()):  
        super(MyThread, self).__init__()  
        self.func = func  
        self.args = args  
  
    def run(self):  
        self.result = self.func(*self.args)  # 在执行函数的同时，把结果赋值给result,  
        # 然后通过get_result函数获取返回的结果  
  
    def get_result(self):  
        try:  
            return self.result  
        except Exception as e:  
            return None  
  
result = []  
threads = []  
for i in range(10):  
    t = MyThread(is_even, args=(i,))  
    t.start()  
    threads.append(t)  
for t in threads:  
    t.join()  # 一定执行join,等待子进程执行结束，主进程再往下执行  
    result.append(t.get_result())  
print result  
```

方法二：通过python内置的队列Queue接收子进程的返回值，然后再从中取出，代码如下：


```
import threading
from queue import Queue  # LILO队列


def is_even(value, q):
    if value % 2 == 0:
        q.put(True)
    else:
        q.put(False)


def multithreading():
    q = Queue()
    threads = []
    results = []
    for i in range(10):
        t = threading.Thread(target=is_even, args=(i, q))
        t.start()
        threads.append(t)
    for t in threads:
        t.join()  # 等待子线程结束后，再往后面执行
        results.append(q.get())

    print(results)


multithreading()

```


```
def mul_check(session, week_data):
    q = Queue()
    threads = []
    for each in week_data:
        time.sleep(0.5)
        question_id = each.get('vBizId')
        t = threading.Thread(target=check_tag_and_note, args=(session, question_id, q))
        t.start()
        threads.append((t, question_id))
    for t, question_id in threads:
        t.join()
        logging.info('{}: check {} done'.format(t.name, question_id))
    update_list = [q.get() for _ in range(q.qsize())]
    logging.info('待更新队列: {}'.format(update_list))
    return update_list
```


方法三：通过创建线程池（threadpool）的方式获取返回值，由于该模块属于第三方模块，因此需要先进行安装：pip install threadpool，具体执行代码如下：


```
import threadpool  
  
# 判断值是否为偶数  
def is_even(value):  
    if value % 2 == 0:  
        return True  
    else:  
        return False  
  
  
# 回调函数，接受的参数（请求本身，和请求工作函数执行结果）可以省略  
results = []  
def get_result(request, result):  
     global results  
     results.append(result)  
  
  
# data 设置为长度为10的列表，（列表中每一个数作为参数传递给工作函数运行一次）  
data = range(10)  
# 声明可容纳五个线程的池  
pool = threadpool.ThreadPool(5)  
# 创建线程运行内容请求列表（线程工作函数，线程工作参数列表，回调函数）  
requests = threadpool.makeRequests(is_even, data, get_result)  
# 将每一个线程请求扔进线程池  
[pool.putRequest(req) for req in requests]  
# 等待data被消耗完，所有线程运行结束。  
pool.wait()  
print results  
```
运行以上例子，返回的结果如下：

C:\Python27\python.exe D:/bluekingProject/wd-test/app-publish/test.py  

[True, False, True, False, True, False, True, False, True, False]  
 



## 4. 生产与消费-Lock

```
# -*- coding: utf-8 -*-
# @Author  : LuoXian
# @Date    : 2020/2/15 19:07
# Software : PyCharm
# version： Python 3.8
# @File    : threading_生产与消费.py
import threading
import random
import time

gMoney = 1000
gTime = 0
gTotal_time = 10
gLock = threading.Lock()


class Producer(threading.Thread):
    def run(self):
        global gMoney
        global gTime
        # 加锁解锁非常消耗资源
        while True:
            gLock.acquire()
            if gTime >= gTotal_time:
                gLock.release()
                break
            money = random.randint(100, 1000)
            gMoney += money
            gTime += 1
            print(f'{threading.current_thread()}生产了{money}, 剩余{gMoney}')
            gLock.release()
            time.sleep(1)


class Consumer(threading.Thread):
    def run(self):
        global gMoney
        while True:
            gLock.acquire()
            money = random.randint(100, 1000)
            if gMoney > money:
                gMoney -= money
                print(f'{threading.current_thread()}消费了{money}, 剩余{gMoney}')
            else:
                if gTime >= gTotal_time:
                    gLock.release()
                    break
                print(f'{threading.current_thread()}想要消费{money}, 剩余{gMoney}， 不足！！！')
            gLock.release()
            time.sleep(1)


def main():
    for i in range(3):
        t = Consumer(name=f'消费者{i}')
        t.start()

    for i in range(3):
        t = Producer(name=f'生产者{i}')
        t.start()


if __name__ == '__main__':
    main()

```
## 5. 生产与消费-Condition
threading提供的Condition对象提供了对复杂线程同步问题的支持。

Condition被称为条件变量，除了提供与Lock类似的acquire和release方法外，还提供了wait和notify方法。

使用Condition的主要方式为：

线程首先acquire一个条件变量，然后判断一些条件。如果条件不满足则wait；如果条件满足，进行一些 处理改变条件后，通过notify方法通知其他线程，其他处于wait状态的线程接到通知后会重新判断条件。不断的重复这一过程，从而解决复杂的同步问 题。
```
# -*- coding: utf-8 -*-
# @Author  : LuoXian
# @Date    : 2020/2/15 19:50
# Software : PyCharm
# version： Python 3.8
# @File    : threading_condition生产消费.py
import threading
import random
import time

gMoney = 1000
gTime = 0
gTotal_time = 10
gCondition = threading.Condition()


class Producer(threading.Thread):
    def run(self):
        global gMoney
        global gTime
        # 加锁解锁非常消耗资源
        while True:
            gCondition.acquire()
            if gTime >= gTotal_time:
                gCondition.release()
                break
            money = random.randint(100, 1000)
            gMoney += money
            gTime += 1
            print(f'{threading.current_thread()}生产了{money}, 剩余{gMoney}')
            # 通知等待的进程
            gCondition.notify_all()
            gCondition.release()
            time.sleep(1)


class Consumer(threading.Thread):
    def run(self):
        global gMoney
        while True:
            gCondition.acquire()
            money = random.randint(100, 1000)
            while gMoney < money:
                if gTime >= gTotal_time:
                    gCondition.release()
                    return
                print(f'{threading.current_thread()}想要消费{money}, 剩余{gMoney}, 不足！！！')
                # 进程进入等待
                gCondition.wait()
            gMoney -= money
            print(f'{threading.current_thread()}消费了{money}, 剩余{gMoney}')
            gCondition.release()
            time.sleep(1)


def main():
    for i in range(3):
        t = Consumer(name=f'消费者{i}')
        t.start()

    for i in range(3):
        t = Producer(name=f'生产者{i}')
        t.start()


if __name__ == '__main__':
    main()

```
