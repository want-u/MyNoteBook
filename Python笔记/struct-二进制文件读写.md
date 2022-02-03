# struct-二进制文件读写

https://blog.csdn.net/whatday/article/details/100559721

python有时需要处理二进制数据，例如 存取文件，socket操作时.这时候，可以使用python的struct模块来完成.可以用struct来处理c语言中的结构体.

struct模块中最重要的三个函数是pack(), unpack(), calcsize


```
# 按照给定的格式(fmt)，把数据封装成字符串(实际上是类似于c结构体的字节流)
pack(fmt, v1, v2, ...) 
 
# 按照给定的格式(fmt)解析字节流string，返回解析出来的tuple
unpack(fmt,string)      
 
# 计算给定的格式(fmt)占用多少字节的内存
calcsize(fmt)
```

```
# -*- coding: utf-8 -*-
# @Author  : LuoXian
# @Date    : 2021/11/14 21:51
# Software : PyCharm
# version： Python 3.8
# @File    : parse_log.py
# 常用笔记：使用迭代器读取文件

import re
import time
import struct


def chunked_file_reader(file, block_size=102400):
    """生成器函数：分块读取文件内容，使用 iter 函数
    """
    # 循环将不断返回 file.read(block_size) 调用结果，直到其为 '' 时终止
    for chunk in iter(lambda: file.read(block_size), b''):
        yield chunk


def return_chunk(file_name):
    """使用迭代器读取文件获得MD5
    """
    with open(file_name, 'rb') as fp:
        for chunk in chunked_file_reader(fp):
            yield chunk


def test(chunks):
    trs = b''
    re_comp = re.compile(r'(\(.*?何龙飞.*?)\n'.encode())
    # # struct模块一般用来通信转换为字节流之用，用在此处，100w行解析20个域，可以压在1秒
    for chunk in chunks:
        try:
            trs = struct.unpack('102400s', chunk)
        except:
            awf = str(len(chunk)) + 's'
            trs = struct.unpack(awf, chunk)
        info = re_comp.findall(trs[0])
        if info:
            for i in info:
                print(i.decode())
    # print("结果=", trs)
    # with open(file_name, 'rb') as f:
    #     a = f.read()
    #     info = re_comp.findall(a)
    #     if info:
    #         for i in info:
    #             print(i.decode())


if __name__ == '__main__':
    ticks = time.perf_counter()
    file_name = 'out.log'
    chunks_data = return_chunk(file_name)
    test(chunks_data)
    print("共计耗时%16.6f秒" % (time.perf_counter() - ticks))

```
