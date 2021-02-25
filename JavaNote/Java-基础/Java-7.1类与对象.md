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

