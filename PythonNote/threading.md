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
## 5. 生产与消费-condition
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
