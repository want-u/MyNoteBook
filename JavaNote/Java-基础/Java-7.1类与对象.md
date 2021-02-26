# Java-类与对象

## 引出

**现有技术解决**

一个养猫猫的问题：

张老太养了两只猫猫:一只名字叫小白,今年 3 岁,白色。还有一只叫小花,今年 100 岁,花色。请编写一个程序，当用户 输入小猫的名字时，就显示该猫的名字，年龄，颜色。如果用户输入的小猫名错误，则显示 张老太没有这只猫猫。

```
public class Object01 {
	public static void main(String[] args) {
		/*
		张老太养了两只猫猫:
		一只名字叫小白,今年 3 岁,白色。
		还有一只叫小花,今年 100 岁,花色。
		请编写一个程序，当用户 输入小猫的名字时，就显示该猫的名字，年龄，颜色。
		如果用户输入的小猫名错误，则显示 张老太没有这只猫猫。
		*/

		// 使用单独的变量解决 => 不利于数据的管理：将一只猫的信息拆解了
		// 第一只猫
		String cat1Name = "小白";
		int cat1Age = 3;
		String cat1Color = "白色";
		// 第二只猫
		String cat2Name = "小花";
		int cat2Age = 100;
		String cat2Color = "花色";

		// 数组 => 1. 数据类型体现不出来
		// 		   2. 只能通过下标获取，变量名和内容对应不明确
		//		   3. 不能体现猫的行为 
		String[] cat1 = {"小白", "3", "白色"};
		String[] cat2 = {"小花", "100", "花色"};
	}
}
```

**现有技术缺点**

不利于数据的管理，且效率低 => 由此引出我们的新知识 类与对象

Java设计者 引入 类与对象(OOP)，根本原因就是：

现有的技术，不能完美的解决新的需求

## 概述

![image-20210225205130113](https://gitee.com/luoxian1011/pictures/raw/master/image-20210225205130113.png)

![image-20210225205052899](https://gitee.com/luoxian1011/pictures/raw/master/image-20210225205052899.png)

## 快速入门

使用面向对象的方式解决养猫问题

```
public class ObjectUse {
	public static void main(String[] args) {
		// 使用OOP面向对象解决
		// 实例化一只猫[创建一只猫对象]
		// 1. new Cat() 创建一只猫
		// 2，Cat cat1 = new Cat(); 把创建的猫赋给 cat1
		// 3. cat1 就是一个对象
		Cat cat1 = new Cat();
		cat1.name = "小白";
		cat1.age = 3;
		cat1.color = "白色";
		// 创建第二只猫
		Cat cat2 = new Cat();
		cat2.name = "小花";
		cat2.age = 100;
		cat2.color = "花色";

		// 怎么使用
		System.out.println("第1只猫的信息：" 
			+ cat1.name + " " + cat1.age + " " + cat1.color);
		System.out.println("第2只猫的信息：" 
			+ cat2.name + " " + cat2.age + " " + cat2.color);
	}
}


// 使用面向对象的方式解决养猫问题
// 定义一个猫类 Cat => 自定义的数据类型
class Cat {
	// 属性
	String name; // 名字
	int age; // 年龄
	String color; // 颜色
}
```

类和对象的区别和联系：

1. 类是抽象的，概念的，代表一类事物，比如人类，猫类...它就是一种数据类型
2. 对象是具体的，实际的，代表一个具体事物，即是实例
3. 类是对象的模板，对象是类的个体，对应一个实例

![image-20210225222657917](https://gitee.com/luoxian1011/pictures/raw/master/image-20210225222657917.png)

## 属性

### 属性概念

属性 / 成员变量 基本介绍

1. 从概念或叫法上看：成员变量 = 属性 = field字段

   即成员变量是用来表示属性的

2. 属性是类的一个组成部分，一般是基本数据类型，也可以是引用类型(对象，数组)

```
public class Object02 {
	public static void main(String[] args) {
		
	}
}


class Car {
	String name; // 属性，成员变量，字段field
	double price;
	String color;
	String[] master; // 属性可以是基本数据类型，也可以是引用类型(对象，数组)
}
```

### 属性细节

1. 属性的定义语法同变量，示例：访问修饰符 属性类型 属性名;

   访问修饰符：控制属性的访问范围

   有四种访问修饰符 public，proctected，默认和private，后面详讲

2. 属性的定义类型可以为任意类型，包含基本类型和引用类型

3. 属性如果不赋值，会有默认值，规则和数组一致

   int 0，short 0，byte 0，long 0，float 0.0， double 0.0，char \u0000，boolean false，String null

```
public class PropertiesDetail {
	public static void main(String[] args) {
		// 创建Person对象
		// p1 是对象名(对象引用)
		// new Person() 创建的对象空间(数据) 才是真正的对象
		Person p1 = new Person();

		// 对象的属性默认值
		System.out.println("这个人的信息：");
		System.out.println("name=" + p1.name + " age=" + p1.age
			+ " salary=" + p1.salary + " isPass=" + p1.isPass);
	}
		// name=null age=0 salary=0.0 isPass=false
}


class Person {
	// 定义四个属性
	String name;
	int age;
	double salary;
	boolean isPass;
}
```

## 对象

### 创建对象

1. 先声明，再创建

   Cat cat;

   cat = new Cat();

2. 直接创建

   Cat cat = new Cat();

### 访问属性

基本语法：对象名.属性名

例如：cat.name; cat.age; cat.color

### 内存分配机制

类和对象的内存分配机制

1. 栈：一般存放基本数据类型(局部变量)
2. 堆：存放对象(Cat cat，数组等)
3. 方法区：常量池(常量，比如字符串)，类加载信息
4. 示意图 [Person (name, age)]

![image-20210226121512091](https://gitee.com/luoxian1011/pictures/raw/master/image-20210226121512091.png)

```
public class Object03 {
	public static void main(String[] args) {
		Person p1 = new Person();
		p1.age = 10;
		p1.name = "小明";
		Person p2 = p1; // 把p1赋给了p2
		System.out.println(p2.age); // 10
	}
}


class Person {
	String name;
	int age;
}
```

### 对象创建过程

Java创建对象的流程简单分析：

> Person p = new Person();
> p.name = "jack";
> p.age = 10;

1. 先加载Person类信息(属性和方法信息，只会加载一次)
2. 在堆中分配空间，进行默认初始化
3. 把地址赋给p，p就指向对象
4. 进行指定初始化，比如 p.name = "jack"; p.age = 10;

---

看一个练习题，并分析画出内存布局图，进行分析

![image-20210226123412178](https://gitee.com/luoxian1011/pictures/raw/master/image-20210226123412178.png)

## 成员方法

### 基本介绍

在某些情况下，我们需要定义成员方法(简称方法)。

比如人类：除了有一些属性外(年龄，姓名...)，还有一些行为：说话、跑步...

这时就要成员方法才能完成

### 快速入门

1. 添加 speak 成员方法,输出 “我是一个好人” 
2. 添加 cal01 成员方法,可以计算从 1+..+1000 的结果 
3. 添加 cal02 成员方法,该方法可以接收一个数 n，计算从 1+..+n 的结果 
4. 添加 getSum 成员方法,可以计算两个数的和

```
public class Method01 {
	public static void main(String[] args) {
		// 1. 方法写好后，如果不去调用，不会执行
		// 2. 先创建对象，然后调用方法即可
		Person p1 = new Person();
		p1.speak(); // 调用speak方法
		p1.cal01(); // 调用cal01方法
		p1.cal02(5); // 调用cal02方法，同时n = 5
		p1.cal02(10); // 调用cal02方法，同时n = 10
		// 调用getSum方法，同时num1 = 10, num2 = 20
		// 把方法getSum返回的值，赋给变量 returnRes
		int returnRes = p1.getSum(10, 20);
		System.out.println("getSum返回的值=" + returnRes);
	}
}


class Person {
	String name;
	int age;

	// 方法(成员方法)
	// 添加speak 成员方法，输出"我是一个好人"
	// 1. public 表示方法是公开的
	// 2. void   表示方法没有返回值
	// 3. speak()：speak是方法名，()是形参列表，当前为空
	// 4. {}     表示方法体，需要执行的代码

	public void speak() {
		System.out.println("我是一个好人");
	}

	// 添加 cal01 成员方法,可以计算从 1+..+1000 的结果
	public void cal01() {
		// 循环完成
		int res = 0;
		for (int i = 1; i <= 1000; i++) {
			res += i;
		}
		System.out.println("cal01 res=" + res);
	}

	// 添加 cal02 成员方法,该方法可以接收一个数 n，计算从 1+..+n 的结果
	// 解读：(int n) 形参列表，表示当前有一个形参n，可以接收输入
	public void cal02(int n) {
		// 循环完成
		int res = 0;
		for (int i = 1; i <= n; i++) {
			res += i;
		}
		System.out.println("cal02 res=" + res);
	}

	// 添加 getSum 成员方法,可以计算两个数的和
	// 1. public 表示方法是公开的
	// 2. int：表示方法执行后，返回一个int值
	// 3. getSum 方法名
	// 4. (int num1, int num2) 形参列表，2个形参，可以接收传入两个数
	// 5. return res; 表示把res的值 ，返回
	public int getSum(int num1, int num2) {
		int res = num1 + num2;
		return res;
	}
}
```

### 调用机制

方法的调用机制原理：[重要]

![image-20210226154626308](https://gitee.com/luoxian1011/pictures/raw/master/image-20210226154626308.png)

### 方法的好处

为什么需要成员方法？看一个需求：

请遍历一个数组，输出数组的各个元素值。

解决思路1：传统方式，使用单个for循环，将数组输出

解决思路2：定义一个类 MyTools，写一个成员方法，调用方法实现

==**成员方法的好处：**==

1. 提高代码的复用性
2. 可以将实现的细节封装，然后供其他用户来调用即可

```
public class Method02 {
	public static void main(String[] args) {
		// 请遍历一个数组，输出数组的各个元素值。
		int[][] map = {{0,0,0}, {1,1,1}, {2,2,2}};

		//使用成员方法输出
		// 1. 实例化MyTools
		MyTools tool = new MyTools();
		// 2. 调用方法
		tool.printArr(map);

		tool.printArr(map);
		
		tool.printArr(map);

	}
}


class MyTools {
	public void printArr(int[][] map) {
		System.out.println("======");
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				System.out.print(map[i][j] + " ");
			}
			System.out.println();
		}
	}
}
```

### 方法的定义

成员方法的定义：

```
public 返回数据类型 方法名(形参列表) { // 方法体
	语句;
	retrun 返回值;
}
```

1. 形参列表：表示成员方法输入 cal(int n), getSum(int num1, int num2)
2. 返回数据类型：表示成员方法输出，void 表示没有返回值
3. 方法主体：表示为了实现某一功能的代码块
4. return 语句不是必须的

