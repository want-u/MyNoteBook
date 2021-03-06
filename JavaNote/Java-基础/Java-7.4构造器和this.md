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

## 本章作业

1. 编写类A01，定义方法max，实现求某个double数组的最大值，并返回

   **返回值为Double（包装类）时，可以返回double或null**

```
public class Homework01 {
	public static void main(String[] args) {
		double[] arr = {1.1, 2.2};
		// double[] arr = null;
		// double[] arr = {};
		A01 a = new A01();
		Double res = a.max(arr);
		if (res != null) {
			System.out.println("arr的最大值=" + a.max(arr));
		} else {
			System.out.println("arr的输入有误");
		}
	}
}


// 编写类A01，定义方法max，实现求某个double数组的最大值，并返回
class A01 {
	public Double max(double[] arr) {
		// 先判断arr是否为null，再判断数组长度
		if (arr != null && arr.length > 0) {
			
			// 保证arr至少有一个元素
			double max = arr[0];
			for (int i = 1; i < arr.length; i++) {
				if (max < arr[i]) {
					max = arr[i];
				}
			}
			return max; // double
		}
		return null; // 使Double对象置空
	}
}
```

2. 编写类A02，定义方法find，实现查找某字符串是否在数组中，并返回索引，如果找不到，返回-1

```
public class Homework02 {
	public static void main(String[] args) {
		String[] strs = {"tom", "jack"};
		
		A02 a = new A02();
		System.out.println("查找的index=" + a.find("tom", strs));
		System.out.println("查找的index=" + a.find("tomm", strs));
	}
}

// 编写类A02，定义方法find
// 实现查找某字符串数组中的元素查找，并返回索引
// 如果找不到，返回-1
class A02 {
	public int find(String findStr, String[] strs) {
		for (int i = 0; i < strs.length; i++) {
			if (findStr.equals(strs[i])) {
				return i;
			}
		}
		// 如果没有，就返回-1
		return -1;
	}
}
```

3. 编写类Book，定义方法updatePrice，实现更改某本书的价格

   具体：如果价格>150，则更改为150，如果价格>100，则更改为100，否则不变

```
public class Homework03 {
	public static void main(String[] args) {
		Book book1 = new Book("红楼", 199.9);
		book1.info();
		book1.updatePrice();
		book1.info();
	}
}

/*
编写类Book，定义方法updatePrice，实现更改某本书的价格
具体：如果价格>150，则更改为150，如果价格>100，则更改为100，否则不变
分析
1. 类名：Book
2. 属性：name，price
3. 方法名：updatePrice
4. 形参：()
5. 返回值：void
6. 提供一个构造器
*/
class Book {
	String name;
	double price;
	public Book(String name, double price) {
		this.name = name;
		this.price = price;
	}

	public void updatePrice() {
		if (this.price > 150) {
			this.price = 150;
		} else if (this.price > 100) {
			this.price = 100;
		}
	}

	// 输出book信息
	public void info() {
		System.out.println(this.name + ":" + this.price);
	}
}
```

4. 编写类A03，实现数组的复制copyArr，输入旧数组，返回一个新数组，元素和旧数组一样

```
public class Homework04 {
	public static void main(String[] args) {
		int[] oldArr = {1, 2, 4};

		A03 a = new A03();
		int[] newArr = a.copyArr(oldArr);

		// 输出数组
		System.out.println("====复制的新数组情况====");
		for (int i = 0 ; i < newArr.length; i++) {
			System.out.print(newArr[i] + " ");
		}
	}
}

// 编写类A03，实现数组的复制copyArr
// 输入旧数组，返回一个新数组，元素和旧数组一样
class A03 {
	public int[] copyArr(int[] oldArr) {
		int[] newArr = new int[oldArr.length];
		for (int i = 0; i < oldArr.length; i++) {
			newArr[i] = oldArr[i];
		}
		return newArr;
	}
}
```

5. 定义一个圆类Circle，定义属性：半径，提供显示圆周长功能的方法，提供显示圆面积的方法

```
public class Homework05 {
	public static void main(String[] args) {
		Circle c1 = new Circle(3);
		System.out.println("周长=" + c1.len());
		System.out.println("面积=" + c1.area());
	}
}

// 定义一个圆类Circle，定义属性：半径
// 提供显示圆周长功能的方法，提供显示圆面积的方法
class Circle {
	double radius; // 半径

	public Circle(double radius) {
		this.radius = radius;
	}

	public double len() { // 周长
		return 2 * Math.PI * radius;
	}

	public double area() { // 面积
		return Math.PI * radius * radius;
	}
}
```

6. 编程创建一个Cale计算类，在其中定义2个变量表示两个操作数，定义四个方法实现求和、差、乘、商(要求除数为0要提示)并创建两个对象，分别测试

```
public class Homework06 {
	public static void main(String[] args) {
		// Cale c1 = new Cale(2, 10);
		Cale c1 = new Cale(2, 0);
		System.out.println("和=" + c1.sum());
		System.out.println("差=" + c1.minus());
		System.out.println("乘=" + c1.mul());
		Double res = c1.div();
		if (res != null) {
			System.out.println("除=" + c1.div());
		} else {
			System.out.println("除数不能为0");
		}
	}
}

/*
编程创建一个Cale计算类，在其中定义2个变量表示两个操作数
定义四个方法实现求和、差、乘、商
(要求除数为0要提示)并创建两个对象，分别测试
*/
class Cale {
	double n1;
	double n2;

	public Cale(double n1, double n2) {
		this.n1 = n1;
		this.n2 = n2;
	}

	public double sum() { // 和
		return n1 + n2;
	}

	public double minus() { // 差
		return n1 - n2;
	}

	public double mul() { // 乘
		return n1 * n2;
	}

	public Double div() { // 除
		if (n2 == 0) {
			return null;
		} 
		return n1 / n2;
	}
}
```

7. 设计一个Dog类，有名字、颜色和年龄属性，定义输出方法show()显示其他信息。并创建对象测试

```
public class Homework07 {
	public static void main(String[] args) {
		Dog dog = new Dog("小白", "黑色", 3);
		dog.show();
	}
}

// 设计一个Dog类，有名字、颜色和年龄属性
// 定义输出方法show()显示其信息。并创建对象测试
class Dog {
	String name;
	String color;
	int age;

	public Dog(String name, String color, int age) {
		this.name = name;
		this.color = color;
		this.age = age;
	}

	public void show() {
		System.out.println(name + "信息如下：");
		System.out.println("color=" + color + " age=" + age);
	}
}
```

8. 分析代码输出

输出结果：10 9 10

![image-20210305220136005](https://gitee.com/luoxian1011/pictures/raw/master/image-20210305220136005.png)

```
public class Test {
	int count = 9;
	public void count1() {
		count = 10;
		System.out.println("count1=" + count);
	}
	public void count2() {
		// 先输出，再自增
		System.out.println("count1=" + count++);
	}
	public static void main(String[] args) {
		// 匿名对象，只能使用一次
		new Test().count1(); // count1=10


		Test t1 = new Test();
		t1.count2(); // count1=9
		t1.count2(); // count1=10
	}
}
```

9. 定义Music类，里面有音乐名name，音乐时长times属性，并由播放play功能和返回本身属性信息的功能方法getinfo

```
public class Homework09 {
	public static void main(String[] args) {
		Music m = new Music("告白气球", 340);
		m.play();
		System.out.println(m.getInfo());

	}
}


// 9. 定义Music类
// 里面有音乐名name，音乐时长times属性
// 并由播放play功能和返回本身属性信息的功能方法getinfo
class Music {
	String name;
	int times;

	public Music(String name, int times) {
		this.name = name;
		this.times = times;
	}

	public void play() {
		System.out.println(name + "正在播放...");
	}

	public String getInfo() {
		return "音乐：" + name + " 时长：" + times + "s";
	}
}
```

10. 分析代码输出

输出结果：101 100 101 101

![image-20210305222251765](https://gitee.com/luoxian1011/pictures/raw/master/image-20210305222251765.png)

11. 在测试方法中，调用method方法，代码如下，编译正确，试写出method方法的定义形式。调用语句为：System.out.println(method(method(10.0, 20.0), 100));

输出：method(method(10.0, 20.0), 100)

method(10.0, 20.0)

```
public double method(double d1, double d2) {

}
```

12. 创建一个Employee类，属性有(名字，性别，年龄，职位，薪水)，提供3个构造器，可以初始化

    1. (名字，性别，年龄，职位，薪水)
    2. (名字，性别，年龄)
    3. (职位，薪水)

    要求充分复用构造器

```
public class Homework12 {
	public static void main(String[] args) {
		Employee e = new Employee("bai", '男', 18, "it", 9999.9);
	}
}

/*
创建一个Employee类，属性有(名字，性别，年龄，职位，薪水)，提供3个构造器，可以初始化

1. (名字，性别，年龄，职位，薪水)
2. (名字，性别，年龄)
3. (职位，薪水)

要求充分复用构造器
*/
class Employee {
	String name;
	char gender;
	int age;
	String job;
	double salary;

	// 职位，薪水
	public Employee(String job, double salary) {
		this.job = job;
		this.salary = salary;
	}

	// 名字，性别，年龄
	public Employee(String name, char gender, int age) {
		this.name = name;
		this.gender = gender;
		this.age = age;
	}

	// 名字，性别，年龄，职位，薪水
	public Employee(String name, char gender, int age, String job, double salary) {
		this(name, gender, age); // 复用构造器
		this.job = job;
		this.salary = salary;
	}
}
```

13. 将对象最为参数传递给方法

![image-20210305225332487](https://gitee.com/luoxian1011/pictures/raw/master/image-20210305225332487.png)

```
public class Homework13 {
	public static void main(String[] args) {
		Circle c = new Circle();
		PassObject p = new PassObject();
		p.printArea(c, 5);
	}
}

class Circle {
	double radius; // 半径

	public double findArea(double radius) {
		return Math.PI * radius * radius;
	}

	// 添加方法setRadius，修改对象的半径值
	public void setRadius(double radius) {
		this.radius = radius;
	}
}

class PassObject {
	public void printArea(Circle c, int times) {
		System.out.println("Radius\tArea");
		for (int i = 1; i <= times; i++) {
			// 修改c对象的半径值
			c.setRadius(i);
			System.out.println((double)i + "\t" + c.findArea(i));
		}
	}
}
```

14. 有个人 Tom 设计他的成员变量，成员方法，可以电脑猜拳

    电脑每次都会随机生成0，1，2

    0 表示石头 1 表示剪刀 2 表示 布

    并要可以显示 Tom 的输赢次数(清单)

```
import java.util.Scanner;
import java.util.Random;


public class Homework14 {
	public static void main(String[] args) {
		Tom tom = new Tom();
		tom.begin();
		tom.showWiner();
	}
}


/*
有个人 Tom 设计他的成员变量，成员方法，可以电脑猜拳
电脑每次都会随机生成0，1，2
0 表示石头 1 表示剪刀 2 表示 布
并要可以显示 Tom 的输赢次数(清单)
*/
class Tom {
	int count;
	Scanner myScanner = new Scanner(System.in);

	int tomNum; // tom出的数
	int comNum; // 电脑出的数
	int result; // 输赢情况，1为赢，0为平，-1为输
	int winCount; // 累计赢的次数，大于零则赢
	// arr[0] = {1, 2, 2, 0}
	// arr[1] = {2, 2, 2, 0}
	// arr[2] = {3, 2, 2, 0}
	int[][] arr = new int[3][4];

	// 游戏规则
	public void game() {
		// 电脑随机012 :Math.random()生成[0-1)
		// int comNum = (int)((Math.random() * 10) % 3);
		Random r = new Random();
		// 生成电脑随机数
		int comNum = r.nextInt(3);
		// System.out.println(comNum);
		arr[count][2] = comNum;

		// 游戏规则
		if (tomNum == 0 && comNum == 1) {
			arr[count][3] = 1;
			winCount += 1;

		} else if (tomNum == 1 && comNum == 2) {
			arr[count][3] = 1;
			winCount += 1;

		} else if (tomNum == 2 && comNum == 0) {
			arr[count][3] = 1;
			winCount += 1;

		} else if (tomNum == comNum) {
			arr[count][3] = 0;
		} else {
			arr[count][3] = -1;
			winCount -= 1;
		}
	}

	// 开始游戏
	public void begin() {
		while (count < 3) {
			arr[count][0] = count + 1;

			System.out.print("猜拳游戏[0 表示石头 1 表示剪刀 2 表示 布]，请输入0-2：");
			// 接收tom的数字
			tomNum = myScanner.nextInt();
			// 限定数字范围为0-2
			if (tomNum >= 0 && tomNum <= 2) {
				
				arr[count][1] = tomNum;
				game();

				System.out.println("次数\ttomNum\tcomNum\t输赢");
				showArrr(arr[count]);
				System.out.println();

				count++;
			} else {
				System.out.println("输入错误...");
			}
		}
	}

	// 输出一维数组
	public void showArrr(int[] array) {
		for (int i = 0; i < array.length; i++) {
			System.out.print(array[i] + "\t");
		}
	}

	// 输出游戏清单
	public void showWiner() {
		System.out.println("游戏数据清单如下：");
		for (int i = 0; i < arr.length; i++) {
			showArrr(arr[i]);
			System.out.println();
		}
		System.out.println("game over...");
		if (winCount > 0) {
			System.out.println("win");
		} else if (winCount == 0){
			System.out.println("no winer...");
		} else {
			System.out.println("lose");
		}
	}
}
```

