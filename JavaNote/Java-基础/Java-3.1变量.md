## Java-变量

### 变量的原理

变量是程序的基本组成单位

不论是使用哪种高级程序语言编写程序，变量都是其程序的基本组成单位，比如：

```
//变量有三个基本要素（类型 + 名称 + 值）
public class Test {
	public static void main(String[] args) {
		int a = 1; //定义了一个变量：类型int整型，名称a，值1
		int b = 3; //定义了一个变量：类型int整型，名称b，值3
		b = 89; //把89值赋给b变量
		System.out.println(a); //输出a变量的值
		System.out.println(b); //输出b变量的值
	}
}
```

### 变量概念

概念：

变量相当于内存中一个数据存储空间的表示，你可以把变量看作是一个房间的门牌号，通过门牌号我们可以找到房间，而通过变量名可以访问到变量。

变量使用的基本步骤：

1. 声明变量：`int a;`
2. 赋值：       `a = 60;`
3. 输出：       `System.out.println(a);`

也可以一步到位：[`int a = 60; ` 通常我们是一步完成的]

```
public class Var01 {
	public static void main(String[] args) {
		// 声明变量
		int a;
		a = 100;
		System.out.println(a);
		// 直接赋值
		int b = 800;
		System.out.println(b);
	}
}
```

### 变量快速入门

```
public class Var02 {
	public static void main(String[] args) {
		// 记录个人信息，String双引号，char单引号
		String name = "king";
		int age = 30;
		double score = 88.5;
		char gender = '男';
		// 输出信息
		System.out.println("人的信息是：");
		System.out.println(name);
		System.out.println(age);
		System.out.println(score);
		System.out.println(gender);
	}
}
```

### 变量使用注意事项

1. 变量表示内存中的一个存储区域
2. 该区域有自己的名称和类型
3. 变量必须先声明，后使用，即有顺序
4. 该区域的值可以在同一类型范围内不断变化
5. 变量在同一个作用域内不能重名
6. 变量 = 数据类型 + 变量名 + 值

### 程序中 + 号的使用

1. 当左右两边都是数值型时，则做加法运算
2. 当左右两边有一方为字符串，则做拼接运算
3. 运算顺序：从左到右

```
public class Plus {
	public static void main(String[] args) {
		System.out.println(100 + 98); // 198
		System.out.println("100" + 98); // "10098"
		System.out.println(100 + 3 + "hello"); // "103hello"
		System.out.println("hello" + 100 + 3); // hello1003
	}
}
```

