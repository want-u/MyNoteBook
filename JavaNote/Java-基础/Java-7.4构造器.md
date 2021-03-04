## Java-构造器

### 引入

看一个需求：

前面我们在创建人类的对象时，是先把一个对象创建好后，再给他的年龄和姓名属性赋值

如果现在我要求，在创建人类的对象时，就直接指定这个对象的年龄和姓名，该怎么做？这时就可以使用构造器

### 基本介绍

构造方法又叫构造器(constructor)，是类的一种特殊的方法，它的主要作用是完成**对新对象的初始化**。

基本语法：

```
[修饰符] 方法名(形参列表) {
	方法体;
}
```

1. 构造器的修饰符可以默认
2. 构造器没有返回值
3. 方法名 和 类名 必须一样
4. 参数列表 和 成员方法 一样的规则
5. 在创建对象时，系统会自动的调用该类的构造器完成对象的初始化

### 快速入门

现在我们就用构造方法来完成对刚才提出的问题：

在创建人类的对象时，就直接指定这个对象的年龄和姓名

```
public class Constructor01 {
	public static void main(String[] args) {
		Person p1 = new Person("jack", 99);
		System.out.println("p1的属性如下");
		System.out.println("name=" + p1.name);
		System.out.println("age=" + p1.age);
	}
}

// 在创建人类的对象时，就直接指定这个对象的年龄和姓名
class Person {
	String name;
	int age;

	public Person(String pName, int pAge) {
		System.out.println("构造器被调取~完成对象的属性初始化");
		name = pName;
		age = pAge;
	}
}
```

### 使用细节

1. 一个类可以定义多个不同的构造器，即构造器重载

   比如：可以再给Person类定义一个构造器，用来创建对象的时候，只指定人名，不需要指定年龄

2. 构造器名和类名要相同

3. 构造器没有返回值

4. 构造器是完成对象的初始化，并不是创建对象

5. 在创建对象时，相同自动的调用该类的构造方法

6. 如果程序员没有定义构造方法，相同会自动给类生成一个默认无参构造方法(也叫默认构造方法)，比如Dog(){}，使用javap指令反编译看看

7. 一旦定义了自己的构造器，默认的构造器就被覆盖了，就不能再使用默认的无参构造器，除非显示的定义以下，即 Dog(){};

```
public class ConstructorDetail {
	public static void main(String[] args) {
		Person p1 = new Person("jack", 100);
		Person p2 = new Person("milan");

		Dog dog = new Dog(); // 使用的是默认无参构造器
	}
}

class Dog {
	// 如果程序员没有定义构造方法，相同会自动给类生成一个默认无参构造方法(也叫默认构造方法)
	// 比如Dog(){}，使用javap指令反编译看看
	/*
		默认构造器
		Dog(){
	
		};

		反编译：javap 类名
		cmd中运行：javap Dog
		
		Compiled from "ConstructorDetail.java"
		class Dog {
		  Dog();
		}

	*/

	// 一旦定义了自己的构造器，默认的构造器就被覆盖了，就不能再使用默认的无参构造器
	// 除非显示的定义以下，即 Dog(){};
	Dog(){};
	
	public Dog(String dName) {

	}
}

class Person {
	String name;
	int age;
	// 第一个构造器
	public Person(String pName, int pAge) {
		name = pName;
		age = pAge;
	}
	// 第二个构造器
	public Person(String pName) {
		name = pName;
	}
}
```

