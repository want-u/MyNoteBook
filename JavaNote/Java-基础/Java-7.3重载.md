## Java-重载

### 基本介绍

Java中允许同一个类中，多个同名方法的存在，但要求形参列表不一致

比如：System.out.println(); out是PrintStream类型

重载的好处：

1. 减轻了起名的麻烦
2. 减轻了记名的麻烦

```
System.out.println(100);
System.out.println("hello world");
System.out.println('A');
System.out.println(1.1);
System.out.println(true);
```

### 快速入门

案例：类：MyCalculator 方法：calculator

calculator(int n1, int n2)

calculator(int n1, double n2)

calculator(double n1, int n2)

calculator(int n1, int n2, int n3)

```
public class OverLoad01 {
	public static void main(String[] args) {
		MyCalculator mc = new MyCalculator();
		System.out.println(mc.calculator(1, 9));
		System.out.println(mc.calculator(1, 9.1));
		System.out.println(mc.calculator(1.2, 9));
		System.out.println(mc.calculator(1, 9, 1));
	}
}

class MyCalculator {
	public int calculator(int n1, int n2) {
		return n1 + n2;
	}
	public double calculator(int n1, double n2) {
		return n1 + n2;
	}
	public double calculator(double n1, int n2) {
		return n1 + n2;
	}
	public int calculator(int n1, int n2, int n3) {
		return n1 + n2 + n3;
	}
}
```

### 使用细节

1. 方法名：必须相同
2. 形参列表：必须不同（形参的类型或个数或顺序，至少有一样不同，参数名无要求）
3. 返回类型：无要求

```
	public int calculator(int n1, int n2) {
		return n1 + n2;
	}
	// 构成重载
	public double calculator(int n1, double n2) {
		return n1 + n2;
	}
	// 形参名不同，不构成重载，会报错[方法重复]
	public double calculator(int a1, int a2) {
		return a1 + a2;
	}
	// 返回类型不同，不构成重载，会报错[方法重复]
	public void calculator(double n1, int n2) {
		int res = n1 + n2;
	}
```

### 课堂练习

0. 判断题：

```
与void show(int a, char b, double c) {} 构成重载的有：[_bcde_]
a)void show(int x, char y, double z) {}
b)int show(int a, double b, char c) {}
c)void show(int a, double b, char c) {}
d)boolean show(int a, char b) {}
e)void show(double c) {}
f)double show(int x, char y, double z) {}
g)void shows() {}
```

1. 编写程序：类Methods中定义三个重载方法并调用。方法名为m。三个方法分别接收一个int参数、两个int参数、一个字符串参数。分别执行平方运算并输出结果，相乘并输出结果，输出字符串信息。在主类的main()方法中分别用参数区别调用三个方法

2. 在Methods类，定义三个重载方法max()，第一个方法，返回两个int值中的最大值，第二个方法，返回两个double值中的最大值，第三个方法，返回三个double值中的最大值，分别调用三个方法

```
public class OverLoadExercise {
	public static void main(String[] args) {
		// 调用重载方法
		Methods methods = new Methods();
		methods.m(9);
		methods.m(9, 2);
		methods.m("hello world");
		methods.m("====");
		System.out.println(methods.max(1,4));
		System.out.println(methods.max(1.1,4.4));
		System.out.println(methods.max(1.1,4.4,6));
	}
}

class Methods {
	// 1
	public void m(int n) {
		System.out.println(n * n);
	}
	public void m(int n1, int n2) {
		System.out.println(n1 * n2);
	}
	public void m(String s) {
		System.out.println(s);
	}

	// 2
	public int max(int n1, int n2) {
		return n1 > n2 ? n1 : n2;
	}
	public double max(double n1, double n2) {
		return n1 > n2 ? n1 : n2;
	}
	public double max(double n1, double n2, double n3) {
		// 先求出n1和n2的最大值
		double max1 = n1 > n2 ? n1 : n2;
		return max1 > n3 ? max1 : n3;
	}
}
```

## Java-可变参数

### 基本介绍

Java允许将同一个类中 多个同名 同功能 但 参数个数不同 的方法，封装成一个方法

基本语法：

```
访问修饰符 返回类型 方法名(数据类型... 形参名) {
...
}
```

### 快速入门

案例：创建一个类 HspMethod，方法 sum

要求：可以计算2个数的和，3个数的和，4，5...

```
public class VarParameter01 {
	public static void main(String[] args) {
		HspMethod m = new HspMethod();
		System.out.println(m.sum(1, 4, 7)); // 12
		System.out.println(m.sum(1, 4)); // 5
	}
}

// 要求：可以计算2个数的和，3个数的和，4，5...
class HspMethod {
	// 可以使用方法重载
	// public int sum(int n1, int n2) { // 2个数的和
	// 	return n1 + n2;
	// }
	// public int sum(int n1, int n2, int n3) { // 3个数的和
	// 	return n1 + n2 + n3;
	// }
	// public int sum(int n1, int n2, int n3, int n4) { // 4个数的和
	// 	return n1 + n2 + n3 + n4;
	// }
	// ......
	// 上面的三个方法名称相同，功能相同，参数个数不同 -> 使用可变参数优化

	// 1. int... 表示接收的是可变参数，类型为int，即可以接收0-多个int
	// 2. 使用可变参数时，可以当作数组使用，即nums当作数组
	// 3. 遍历 nums 求和
	public int sum(int... nums) {
		// System.out.println("接收的参数个数=" + nums.length);
		int res = 0;
		for (int i = 0; i < nums.length; i++) {
			res += nums[i];
		}
		return res;
	}
}
```

### 使用细节

1. 可变参数的实参可以为0或者任意多个
2. 可变参数的实参可以为数组
3. 可变参数的本质就是数组
4. 可变参数可以和普通类型的参数一起放在形参列表，但必须保证可变参数在最后
5. 一个形参列表中只能出现一个可变参数

```
public class VarParameterDetail {
	public static void main(String[] args) {
		int[] arr = {1, 2, 5};
		T t1 = new T();
		t1.f1(arr);
	}
}

class T {
	// 2. 可变参数的实参可以为数组
	public void f1(int... nums) {
		System.out.println("长度=" + nums.length);
	}

	// 4. 可变参数可以和普通类型的参数一起放在形参列表
	// 但必须保证可变参数在最后
	// 5. 一个形参列表中只能出现一个可变参数
	public void f2(String str, double... nums) {

	}
}
```

### 课堂练习

有三个方法，分别实现返回姓名和两门课成绩(总分)，返回姓名和三门课成绩(总分)，返回姓名和五门课成绩(总分)。

封装成一个可变参数的方法

类名：HspMethod 方法名：showScore

```
public class VarParameterExercise {
	public static void main(String[] args) {
		HspMethod m = new HspMethod();
		System.out.println(m.showScore("小明", 60, 70));
		System.out.println(m.showScore("小花", 60, 70, 80));
		System.out.println(m.showScore("小加", 60, 70, 80, 90, 100));
	}
}

class HspMethod {
	// 成绩使用double接收
	// 返回字符串
	public String showScore(String name, double... scores) {
		double res = 0;
		for (int i = 0; i < scores.length; i++) {
			res += scores[i];
		}
		return name + "有" + scores.length +"门课总分=" + res;
	}
}
```

## Java-作用域

### 基本介绍

1. 在Java编程中，主要的变量就是属性(成员变量)和局部变量
2. 局部变量一般是指常用方法中定义的变量[例如Cat类：cry]
3. Java中作用域的分类
   1. 全局变量：也就是属性，作用域为整个类体 Cat类：cry eat等方法使用属性
   2. 局部变量：也就是除了属性之外的其他变量，作用域为定义它的代码块中
4. 全局变量(属性)可以不赋值，直接使用，因为有默认值，局部变量必须赋值后，才能使用，因为没有默认值

```
public class VarScope {
	public static void main(String[] args) {
		
	}
}

class Cat {
	// 全局变量：也就是属性，作用域为整个类体 Cat类：cry eat等方法使用属性
	// 属性在定义时，可以直接赋值
	int age = 10;

	// 全局变量(属性)可以不赋值，直接使用，因为有默认值
	double weight; // 默认是0.0

	public void hi() {
		// 局部变量必须赋值后，才能使用，因为没有默认值
		int num = 1;
		System.out.println("weight=" + weight);
		System.out.println("num=" + num);
	}

	public void cry() {
		// 局部变量：也就是除了属性之外的其他变量，作用域为定义它的代码块中
		// n 和 name 就是局部变量
		int n = 10;
		String name = "jack";
		System.out.println("age=" + age);
	}

	public void eat() {
		System.out.println("age=" + age);
	}
}
```

### 使用细节

1. 属性和局部变量可以重名，访问时遵循就近原则
2. 在同一个作用域中，比如在同一个常用方法中，两个局部变量，不能重名
3. 属性生命周期较长，伴随着对象的创建而创建，伴随对象的销毁而销毁；局部变量生命周期较短，伴随着它的代码块的执行而创建，伴随代码块的结束而销毁。即在依次方法调用过程中生效
4. 作用域范围不同
   1. 全局变量/属性：可以被本类使用，或其他类使用（通过对象调用）
   2. 局部变量：只能在本类中对应的方法中使用
5. 修饰符不同
   1. 全局变量/属性：可以加修饰符
   2. 局部变量：不可以加修饰符

```
public class VarScopeDetail {
	public static void main(String[] args) {
		Person p1 = new Person();
		// 属性生命周期较长，伴随着对象的创建而创建，伴随对象的销毁而销毁
		// 局部变量生命周期较短，伴随着它的代码块的执行而创建，伴随代码块的结束而销毁。即在依次方法调用过程中生效
		// p1.say(); // 执行say方法时，say方法的局部变量比如name，会创建，当say执行完毕后销毁
		// 此时，属性仍然可以使用
		T t1 = new T();
		t1.test(); // 第1种 跨类访问对象属性的方式
		t1.test2(p1); // 第2种 跨类访问对象属性的方式

	}
}

class T {
	// 全局变量/属性：可以被本类使用，或其他类使用（通过对象调用）
	public void test() {
		Person p1 = new Person();
		System.out.println(p1.name); // jack
	}
	public void test2(Person p) {
		System.out.println(p.name); // jack
	}
}

class Person {
	// 1. 全局变量/属性：可以加修饰符
	// 2. 局部变量：不可以加修饰符
	public int age = 20;

	String name = "jack";

	public void say() {
		// 属性和局部变量可以重名，访问时遵循就近原则
		String name = "king";
		System.out.println("say() name=" + name); // king
	}

	public void hi() {
		String address = "北京";
		// String address = "上海"; // 错误：重复定义
		String name = "tom";

	}
}
```

