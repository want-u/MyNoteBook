# Java-包

## 1. 应用场景

现在有两个程序员共同开发一个Java项目，程序员xiaoming希望定义一个类取名Dog，程序员xiaoqiang也想定义一个类也叫Dog。两个程序员为此还吵了起来，怎么办？ ==> 包

## 2. 包的三大作用

1. 区分相同名字的类
2. 当类很多时，可以很好的管理类 [看Java API文档或源码]
3. 控制访问范围

## 3. 包的基本语法

```
package com.hspedu;
```

说明：

1. package 关键字，表示打包
2. com.hspedu 表示包名

## 4. 包原理

### 4.1 包的本质（原理）

包的本质 实际上就是创建不同的文件夹/目录来保存类文件

![image-20210815222845879](https://gitee.com/luoxian1011/pictures/raw/master/image-20210815222845879.png)

### 4.2 包的快速入门

使用打包技术，不同的包下创建Dog类

```
package com.demo;

import com.xiaoming.Dog;

public class Test {
    public static void main(String[] args) {
        Dog dog = new Dog();
        System.out.println(dog); // com.xiaoming.Dog@1b6d3586
        com.xiaoqiang.Dog dog1 = new com.xiaoqiang.Dog();
        System.out.println(dog1); // com.xiaoqiang.Dog@4554617c
    }
}
```

![image-20210815224424774](https://gitee.com/luoxian1011/pictures/raw/master/image-20210815224424774.png)