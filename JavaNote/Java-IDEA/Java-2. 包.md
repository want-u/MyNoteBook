# Java-2.1 包

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

## 4. 包的原理

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

## 5. 包的命名

### 5.1 命名规则

只能包含数字、字母、下划线、小圆点，但是不能用数字开头，不能是关键字或保留字

```
demo.class.exec1 	//错误 class是关键字
demo.12a 			//错误 12a是数字开头
demo.ab12.oa 		//对
```

### 5.2 命名规范

一般是小写字母 + 小圆点

com.公司名.项目名.业务模块名

比如：com.hspedu.oa.model; com.hspedu.oa.controller;

举例：

com.sina.crm.user  	//用户模块

com.sina.crm.order 	//订单模块

com.sina.crm.user  	//工具模块

## 6. 常用的包

一个包下，包含很多的类，Java中常用的包有：

java.lang.*	//lang包是基本包，默认引入，不需要再引入

java.util.*	//util包，系统提供的工具包，工具类，使用Scanner

java.net.*	//网络包，网络开发

java.awt.*	//java的界面开发，GUI

## 7. 包的引入

com.hspedu.pkg: Import01.java

语法：import 包

我们引入一个包的主要目的是要使用该包下的类

比如 

- import java.util.Scanner; 就只是引入一个类Scanner
- import java.util.*; 就是将java.util 包所有都引入

案例：使用系统提供 Arrays 完成数组排序

```
package com.hspedu.pkg;

//建议：我们需要使用到哪个类，就导入哪个类即可，不建议使用 * 导入
import java.util.Arrays;
import java.util.Scanner;

public class Import01 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int[] arr = {-1, 99, 12, 33};
        Arrays.sort(arr);
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + "\t");
        }
    }
}
```

## 注意事项和使用细节

PkgDetail.java

1. package 的作用是声明当前类所在的包，需要放在类的最上面，一个类中最多只有一句package
2. import指令 位置放在package的下面，在类定义前面，可以有多句没有顺序要求

```
//一个类中最多只有一句package
package com.hspedu.pkg;

//import指令 位置放在package的下面，在类定义前面，可以有多句没有顺序要求
import java.util.Arrays;
import java.util.Scanner;

//类定义
public class Import01 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int[] arr = {-1, 99, 12, 33};
        Arrays.sort(arr);
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + "\t");
        }
    }
}
```