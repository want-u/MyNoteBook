# Java-IDEA入门

## IDE 集成开发环境

### IDEA 介绍

1.  IDEA 全称 IntelliJ IDEA
2.  在业界被公认为最好的 Java 开发工具
3.  IDEA 是 JetBrains 公司的产品，总部位于捷克的首都布拉格
4.  除了支持 Java 开发，还支持 HTML，CSS，PHP，MySQL，Python 等

### Eclipse介绍

1. Eclipse 是一个开放源代码的、基于 Java 的可扩展开发平台。
2. 最初是由 IBM 公司耗资 3000 万美金开发的下一代 IDE 开发环境
3. 2001 年 11 月贡献给开源社区

Eclipse 是目前最优秀的 Java 开发 IDE 之一

### IDEA的安装

1. 官网: https://www.jetbrains.com/
2. IDEA 下载后，就可以开始安装。
   1. 选择安装位置，这里安装在/D/JAVA/IDEA目录下
   2. 勾选64位启动器，安装即可

## IDEA

### 基本介绍

使用 IDEA 创建 Java 项目(project)，看看 IDEA 是如何使用的，IDEA 是以项目的概念，来管理我们的 java 源码的

1. 创建一个 java 项目 - hello

```
public class Hello {
	public static void main(String[] args) {
		System.out.println("hello, idea, 你好北京~~");
	}
}
```

2. 使用 IDEA 开发一个 java 项目 testpro01，创建一个类 MyTools, 编写一个方法，可以完成对 int 数组冒泡排序的功能

```
public class ArrayTest {
    public static void main(String[] args) {
        int[] arr = {1, 99, 8, -1, 5};
        MyTools myTools = new MyTools();
        myTools.bubble(arr);
    }
}


// 创建一个MyTools类，编写一个方法实现冒泡排序的功能
class MyTools {
    public void bubble(int[] arr) {
        int tmp;
        for (int i = 0; i < arr.length - 1; i++) { // 外层为arr.length - 1
            for (int j = 0; j < arr.length - i - 1; j++) { // 内层为arr.length - i - 1
                if (arr[j] > arr[j + 1]) { // 将大数放在后面
                    tmp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = tmp;
                }
            }
        }
        // 输出排序后的数组
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
    }
}
```



### 使用技巧

file -> settings -> plug-in

下载插件 [汉化插件 Chinese Language]

### IDEA 常用快捷键

1. 删除当前行, 默认是 ctrl + Y 自己配置 ctrl + d
2. 复制当前行, 自己配置 ctrl + alt + 向下光标
3. 补全代码 alt + /
4. 添加注释和取消注释 ctrl + / 【第一次是添加注释，第二次是取消注释】
5. 导入该行需要的类 先配置 auto import , 然后使用 alt+enter 即可
6. 快速格式化代码 ctrl + alt + L
7. 快速运行程序 自己定义 alt + R
8. 生成构造器等 alt + insert [提高开发效率]
9. 查看一个类的层级关系 ctrl + H [学习继承后，非常有用]
10. 将光标放在一个方法上，输入 ctrl + B , 可以定位到方法 [学继承后，非常有用]
11. 自动的分配变量名 , 通过 在后面假 .var [老师最喜欢的]
12. 还有很多其它的快捷键... 

### 模板/自定义模板

file -> settings -> editor -> Live templates -> 

查看有哪些模板快捷键/可以自己增加模板

模板可以高效的完成开发

```
main # main方法
sout # 输出
fori # for循环
```

![image-20210815221408222](https://gitee.com/luoxian1011/pictures/raw/master/image-20210815221408222.png)