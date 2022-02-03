# 让Make编译速度加快（Make -j解析）

https://www.jianshu.com/p/bec38eb47c43

[toc]

## 1.加上 参数 - j[number] 来提升速度

> -j 就是编译机器的内核线程数

> linux 下输入nproc返回数字是你的机器的线程数

> mac 下 输入 sysctl -n hw.ncpu 查看本机机器线程数

> 假设你明确知道你的CPU是8核16线程的，你完全可以直接用make -j16

## 2.列举



```shell
make -j16
```

## linux（自动计算线程数）



```go
make -j$(nproc)
```

## mac（自动计算线程数）



```shell
make -j$(sysctl -n hw.ncpu)
```

