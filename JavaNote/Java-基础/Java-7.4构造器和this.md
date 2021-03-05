## Java-构造器

### 引出

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

### 对象创建流程

![image-20210305132502581](https://gitee.com/luoxian1011/pictures/raw/master/image-20210305132502581.png)

流程分析（面试题）

1. 加载Person类信息(Person.class) ，只会加载一次
2. 在堆中分配对象空间(地址)
3. 完成对象初始化
   1. 默认初始化 age=0 name=null
   2. 显示初始化 age=90 name=null
   3. 构造器初始化 age=20 name=小倩
4. 将对象在堆中的地址，返回给p(p 是对象名 或者叫对象的引用)

## Java-this

### 引出

看以下代码，并分析问题

```
public class This01 {
	public static void main(String[] args) {
		Dog dog1 = new Dog("haha", 3);
		dog1.info(); // 此时属性输出默认值
	}
}

class Dog {
	String name;
	int age;

	// public Dog(String dName, int dAge) {
	// 	name = dName;
	// 	age = dAge;
	// }
	// 如果我们构造器的形参，能够直接写出属性名，就更好了
	// 但是，根据变量的作用域规则
	// 构造器的name和age就是局部变量了，而不是属性
	// 引出 this 关键字解决
	public Dog(String name, int age) { 
		name = name;
		age = age;
	}

	public void info() {
		System.out.println(name + "\t" + age);
	}
}
```

### 基本介绍

Java虚拟机会给每个对象分配 this，代表当前的对象。[上帝创世界的小故事]

使用 this 解决前面变量命名的问题

```
public class This01 {
	public static void main(String[] args) {
		Dog dog1 = new Dog("haha", 3);
		dog1.info(); // 此时属性输出haha 3
	}
}

class Dog {
	String name;
	int age;

	// 引出 this 关键字解决
	public Dog(String name, int age) { 
		// this.name 就是当前对象的属性name
		this.name = name;
		// this.age 就是当前对象的属性age
		this.age = age;
	}

	public void info() {
		System.out.println(name + "\t" + age);
	}
}
```

### 快速入门

**简单的说，哪个对象调用，this就代表哪个对象**

[可以理解为，this是对象的一个隐藏属性，指向对象地址]

hashCode：是对象内存地址转换的一串整数，可以理解为对象地址

![image-20210305151601888](https://gitee.com/luoxian1011/pictures/raw/master/image-20210305151601888.png)

```
public class This01 {
	public static void main(String[] args) {
		Dog dog1 = new Dog("haha", 3);
		// 输出dog1对象的hashCode，与this一致
		System.out.println("dog1 hashCode=" + dog1.hashCode());
		Dog dog2 = new Dog("xiaohei", 2);
		System.out.println("dog2 hashCode=" + dog2.hashCode());

	}
}

class Dog {
	String name;
	int age;

	public Dog(String name, int age) { 
		// this.name 就是当前对象的属性name
		this.name = name;
		// this.age 就是当前对象的属性age
		this.age = age;
		// 输出this 的hashCode
		System.out.println("this hashCode=" + this.hashCode());
	}

	public void info() {
		System.out.println(name + "\t" + age);
	}
}
```

### 使用细节

1. this 关键字可以用来访问本类的属性、方法、构造器

2. this 用于区分当前类的属性和局部变量

3. 访问成员方法的语法： this.方法名(参数列表);

4. 访问构造器语法： this(参数列表);

   注意只能在构造器中使用，且必须放在第一条语句，即只能在构造器访问另一个构造器

5. this 不能在类定义的外部使用，只能在类定义的方法中使用

```
public class ThisDetail {
	public static void main(String[] args) {
		T t1 = new T();
		// t1.f1();
		t1.f2();
	}
}

 class T {
 	String name = "jack";
 	int age = 100;

 	// 访问构造器语法： this(参数列表);
 	public T() {
 		// 在这里访问T(String name, int age)
 		this("xiaohei", 99);
 		System.out.println("T() ...");
 	}

 	public T(String name, int age) {
 		System.out.println("T(String name, int age)..");
 	}

 	// this 用于区分当前类的属性和局部变量
 	public void f1() {
 		System.out.println("f1()...");
 		String name = "toto";
 		System.out.println("name=" + name + " age=" + age); // toto 1000
 		System.out.println("name=" + this.name + " age=" + this.age); // jack 100
 	}

 	// 访问成员方法的语法： this.方法名(参数列表);
 	public void f2() {
 		System.out.println("f2()...");
 		// 第一种方法
 		f1();
 		// 第二种方法
 		this.f1();
 	}

 }
```

### 课堂练习

定义Person类，里面有name、age属性，并提供compareTo比较方法，用于判断是否和另一个人相等，提供测试类TestPerson用于测试，名字和年龄完全一样，就返回true，否则返回false

```
public class TestPerson {
	public static void main(String[] args) {
		Person p1 = new Person("mary", 20);
		Person p2 = new Person("mary", 20);
		System.out.println("p1和p2比较的结果：" + p1.compareTo(p2));
	}
}

/*
定义Person类，里面有name、age属性
并提供compareTo比较方法，用于判断是否和另一个人相等
名字和年龄完全一样，就返回true，否则返回false
*/
class Person {
	String name;
	int age;

	// 构造器
	public Person(String name, int age) {
		this.name = name;
		this.age = age;
	}

	// compareTo比较方法
	public boolean compareTo(Person p) {
		// if (this.name.equals(p.name) && this.age == p.age) {
		// 	return true;
		// } else {
		// 	return false;
		// }
		return this.name.equals(p.name) && this.age == p.age;
	}
}
```

