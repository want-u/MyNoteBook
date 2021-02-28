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

   ```
   Cat cat;
   cat = new Cat();
   ```

2. 直接创建

   ```
   Cat cat = new Cat();
   ```

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

```
Person p = new Person();
p.name = "jack";
p.age = 10;
```

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

## 方法细节

### 访问修饰符

作用是控制 方法使用的范围

共有四种：[public，protected，默认，private]

如果不写即默认访问

### 返回数据类型

1. 一个方法最多有一个返回值 [思考：如何返回多个结果 返回数组即可]
2. 返回类型可以为任意类型，包含基本类型和引用类型 (数组，对象)
3. 如果方法要求有**返回数据类型**，则方法体中最后的执行语句必须为 **return 值;** 而且要求返回值类型必须和return 的值类型一致或兼容
4. 如果方法是void，则方法体中可以没有return语句，或 只写 return;

```
public class MethodDetail {
	public static void main(String[] args) {
		// 在实际工作中，方法都应有具体含义
		AA a = new AA();
		int[] res = a.getSumAndSub(1, 4);
		System.out.println("sum=" + res[0] + " sub=" + res[1]);
	}
}


class AA {
	// 一个方法最多有一个返回值 
	// [思考：如何返回多个结果 返回数组即可]
	public int[] getSumAndSub(int n1, int n2) {
		int[] resArr = {n1 + n2, n1 - n2};
		return resArr;
	}

	// 返回类型可以为任意类型，包含基本类型和引用类型 (数组，对象)
	// 如上方法，返回数组

	// 如果方法要求有**返回数据类型**，则方法体中最后的执行语句必须为 **return 值;** 
	// 而且要求返回值类型必须和return 的值类型一致或兼容
	public double f2() {
		double d1 = 1.1 * 3;
		int n = 100;
		// return d1; // ok
		return n; // ok int -> double
	}
	// 如果方法是void，则方法体中可以没有return语句
	// 或 只写 return;
	public void f3() {
		System.out.println("hello world");
		return; // 也可以不写
	}
}
```

### 方法名

遵循驼峰命名法，最好见名知义，表达出该功能的意思即可

比如：得到两个数的和 getSum ，开发中按照规范



### 形参列表

1. 一个方法可以有0个参数，也可以有多个参数，中间用逗号隔开

   比如：getSum(int n1, int n2);

2. 参数类型可以为任意类型，包含基本类型和引用类型

   比如：printArr(int\[][] map)

3. 调用带参数的方法时，一定对应着参数列表传入相同类型或兼容类型的参数

4. 方法定义时的参数称为形式参数，简称形参；方法调用时传入的参数称为实际参数，简称实参

   实参和形参的类型要一致或兼容，个数、顺序必须一致

### 方法体

里面写完成功能的具体的语句

可以为输入、输出、变量、运算、分支、循环、方法调用

但里面不能再定义方法！即：方法不能嵌套定义！

### 方法调用细节

1. 同一个类中的方法调用：直接调用即可。比如：print(参数)

2. 跨类中的方法A类调用B类方法：需要通过对象名调用。

   比如：对象名.方法名(参数)

3. 特别说明：跨类的方法调用和方法的访问修饰符相关，后面再细说

```
public class MethodDetail02 {
	public static void main(String[] args) {
		A a = new A();
		a.sayOk();
		a.m1();
	}
}


class A {
	// 同一个类中的方法调用：直接调用即可。比如：print(参数)
	public void print(int n) {
		System.out.println("print方法被调用 n=" + n);
	}

	public void sayOk() {
		print(10);
		System.out.println("sayOk 继续...");
	}

	// 跨类中的方法A类调用B类方法：需要通过对象名调用。
	public void m1() {
		// 创建B对象，然后调用方法
		System.out.println("m1被调用");
		B b = new B();
		b.hi();
		System.out.println("m1继续...");
	}
}

class B {
	public void hi() {
		System.out.println("hi方法被调用");
	}
}
```

### 课堂练习

1. 编写类AA，有一个方法：判断一个数是奇数还是偶数，返回boolean

2. 根据行、列、字符打印 对应行数和列数的字符
   比如：行4，列4，字符#，则打印为：

   ####
   ####
   ####
   ####

```
public class MethodExercise01 {
	public static void main(String[] args) {
		AA a = new AA();
		// 1.
		if (a.isOdd(1)) {
			System.out.println("是奇数");
		} else {
			System.out.println("是偶数");
		}
		// 2.
		a.print(4, 4, '#');

	}
}

// 编写类AA，有一个方法：判断一个数是奇数还是偶数，返回boolean
class AA {
	// 1. 方法的返回类型 boolean
	// 2. 方法的名字 isOdd
	// 3. 方法的形参 (int n)
	// 4. 方法体 判断
	public boolean isOdd(int n) {
		// if (n % 2 != 0) {
		// 	return true;
		// } else {
		// 	return false;
		// }

		// return n % 2 != 0 ? true; false;
		return n % 2 != 0;
	}
	
	/*
	根据行、列、字符打印 对应行数和列数的字符
	比如：行4，列4，字符#，则打印为：

	####
	####
	####
	####

	1. 方法返回类型void
	2. 方法名 print
	3. 方法参数(int row, int col, char c)
	4. 方法体 循环
	*/
	public void print(int row, int col, char c) {
		for (int i = 1; i <= row; i++) {
			for (int j = 1; j <= col; j++) {
				System.out.print(c);
			}
			System.out.println();
		}
	}

}
```

## 方法传参机制

方法的传参机制对我们今后的编程**非常重要**，一定要搞的清清楚楚明明白白：

### 基本数据类型

**基本数据类型的传参机制**

基本数据类型，传递的是值(值拷贝)，形参的任何改变不影响实参！

案例分析：

```
public class MethodParameter01 {
	public static void main(String[] args) {
		int a = 10, b = 20;
		// 创建AA对象
		AA obj = new AA();
		obj.swap(a, b);
		System.out.println("main a=" + a + " b=" + b); // a=10,b=20
	}
}

class AA {
	public void swap(int a, int b) {
		System.out.println("交换前 a=" + a + " b=" + b); // a=10,b=20
		int tmp = a;
		a = b;
		b= tmp;
		System.out.println("交换后 a=" + a + " b=" + b); // a=20,b=10
	}
}
```

![image-20210228134651080](https://gitee.com/luoxian1011/pictures/raw/master/image-20210228134651080.png)

### 引用数据类型

**引用数据类型的传参机制**

案例：

B类中写一个方法test100，可以接收一个数组，在方法中修改该数组，看看原来的数组是否变化？// 会变化

B类中写一个方法test200，可以接收一个Person(age,sal)对象，在方法中修改该对象属性，看看原来的对象是否变化？ // 会变化

```
public class MethodParameter02 {
	public static void main(String[] args) {
		// 1. 数组测试
		int[] arr = {1, 2, 3};
		// 创建B对象
		B b = new B();
		b.test100(arr);
		System.out.println("main arr数组 ");
		for (int i =0; i < arr.length; i++) {
			System.out.print(arr[i] + " ");
		}
		System.out.println("==========");
		// 2. 对象测试
		Person p = new Person();
		p.name = "jack";
		p.age = 10;
		b.test200(p);
		System.out.println("age=" + p.age); // 10000
		// 当test200方法中 p=null，此时输出age=10
		// 当test200方法中 p = new Person();，此时输出age=10

	}
}

class Person {
	String name;
	int age;
}

class B {
	// B类中写一个方法test200，可以接收一个Person(age,sal)对象
	// 在方法中修改该对象属性，看看原来的对象是否变化？ // 会变化
	public void test200(Person p) {
		// p.age = 10000;
		// 思考1：p=null
		// p=null;
		// 思考2：p = new Person();
		p = new Person();
		p.name = "tom";
		p.age = 999;

	}


	// B类中写一个方法test100，可以接收一个数组
	// 在方法中修改该数组，看看原来的数组是否变化？// 会变化
	public void test100(int[] arr) {
		arr[0] = 100; // 修改元素
		// 遍历数组
		System.out.println("test100 arr数组 ");
		for (int i =0; i < arr.length; i++) {
			System.out.print(arr[i] + " ");
		}
		System.out.println();
	}
}
```

![image-20210228141007833](https://gitee.com/luoxian1011/pictures/raw/master/image-20210228141007833.png)

![image-20210228142054377](https://gitee.com/luoxian1011/pictures/raw/master/image-20210228142054377.png)

![image-20210228142713348](https://gitee.com/luoxian1011/pictures/raw/master/image-20210228142713348.png)

![image-20210228143553504](https://gitee.com/luoxian1011/pictures/raw/master/image-20210228143553504.png)

## 对象的拷贝

编写一个方法copyPerson，可以赋值一个Person对象，返回复制的对象。
克隆对象：要求得到的新对象和原来的对象是两个独立的对象，只是属性相同

```
public class MethodExercise02 {
	public static void main(String[] args) {
		// 对象的拷贝
		Person p = new Person();
		p.name = "milan";
		p.age = 100;
		// 创建一个tools对象
		MyTools tools = new MyTools();
		Person p2 = tools.copyPerson(p);
		System.out.println("p name=" + p.name + " age=" + p.age);
		System.out.println("p2 name=" + p2.name + " age=" + p2.age);
		// 比较两个对象，判断是否是同一个对象
		System.out.println(p == p2); // false
	}
}

/*
	编写一个方法copyPerson，可以赋值一个Person对象，返回复制的对象。
	克隆对象：要求得到的新对象和原来的对象是两个独立的对象，只是属性相同
	思路:
	1. 方法返回类型 Person
	2. 方法名 copyPerson
	3. 方法参数(Person p)
	4. 方法体 创建一个新对象，并赋值属性
*/
class Person {
	String name;
	int age;
}


class MyTools {
	public Person copyPerson(Person p) {
		Person p2 = new Person();
		p2.name = p.name; // 把原来对象的名字赋给p2
		p2.age = p.age; // 把原来对象的年龄赋给p2
		return p2;
	}
}
```

![image-20210228160055890](https://gitee.com/luoxian1011/pictures/raw/master/image-20210228160055890.png)