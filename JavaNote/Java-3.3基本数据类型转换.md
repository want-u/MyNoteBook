# Java-基本数据类型转换

## 自动类型转换

### 基本介绍

当java程序在进行赋值或者运算时，精度小的类型自动转换为精度大的数据类型，这个就是**自动类型转换**。

数据类型按精度（容量）大小排序为：

![image-20210205232736351](https://gitee.com/luoxian1011/pictures/raw/master/image-20210205232736351.png)

```
public class AutoConvert {
	public static void main(String[] args) {
		// 自动类型转换
		int n1 = 'a'; 	// ok: char -> int
		double d1 = 80; // ok: int -> double 
		System.out.println(n1); // 输出97
		System.out.println(d1); // 输出80.0
	}
}
```

### 使用细节

1. 有多种类型的数据混合运算时，系统首先自动将所有数据转换成容量最大的那种数据类型，然后再进行计算。
2. 当我们把精度大的数据类型赋值给精度小的数据类型时会报错，反之就会进行自动类型转换
3. （byte ，short）和 char 之间不会相互自动转换
4. byte，short，char 他们三者可以计算，在计算时首先转换为 int 类型
5. boolean 不参与转换
6. 自动提升原则：表达式结果的类型自动提升为 操作数中最大的类型

```
public class AutoConvert02 {
	public static void main(String[] args) {
		// 有多种类型的数据混合运算时
		// 系统首先自动将所有数据转换成容量最大的那种数据类型，然后再进行计算
		int n1 = 10; // ok
		// float f1 = n1 + 1.1; // 错误：n1 + 1.1 => 结果类型是double
		double d1 = n1 + 1.1; // ok 
		float f2 = n1 + 1.1F; // ok

		// 当我们把精度大的数据类型赋值给精度小的数据类型时会报错
		// 反之就会进行自动类型转换
		// int n2 = 1.1; // 错误：1.1为double

		// （byte ，short）和 char 之间不会相互自动转换
		// 当把具体数值赋给byte时，首先判断该数是否在byte范围内，如果是就可以
		byte b1 = 10; // ok -128 ~ 127
		int n2 = 1;
		// byte b2 = n2; // 错误：如果是变量赋值，则直接判断类型，n2为int
		// char c1 = b1; // 错误：byte不能自动转成 char

		// byte，short，char 他们三者可以计算，在计算时首先转换为 int 类型
		byte b2 = 1;
		byte b3 = 2;
		short s1 = 1;
		// short s2 = b2 + s1; // 错误：b2 + s1 => int
		int s2 = b2 + s1; // ok
		// byte b4 = b2 + b3;  // 错误：b2 + b3 => int

		// boolean 不参与转换
		boolean pass = true;
		// int n3 = pass; // 错误

		// 自动提升原则：表达式结果的类型自动提升为 操作数中最大的类型
		byte b5 = 1;
		short s3 = 2;
		int n4 = 3;
		double n5 = 1.1;

		double n6 = b5 + s3 + n4 + n5; // 动提升为double


	}
}
```

## 强制类型转换

### 基本介绍

自动类型转换的逆过程，将容量大的数据类型转换为容量小的数据类型。使用时要加上强制转换符 **()**， 但可能造成精度降低或溢出，格外要注意，如：(int) (byte) (char)

```
public class ForceConvert01 {
	public static void main(String[] args) {
		// 强制类型转换
		int n1 = (int)1.9;
		System.out.println(n1); // 输出1，精度丢失

		int n2 = 2000;
		byte b1 = (byte)n2;
		System.out.println(b1); // 输出-48，数据溢出
	}
}
```

### 使用细节

1. 当进行数据的从小到大 大——> 小，就需要使用到强制转换
2. 强制符号只针对于最近的操作数有效，往往会使用小括号提升优先级
3. char 类型可以保存 int 的常量值，但不能保存 int 的变量值，需要强转
4. byte ，short 和 char 类型在进行运算时，当作 int 类型处理

```
public class ForceConvert02 {
	public static void main(String[] args) {
		// 强制符号只针对于最近的操作数有效，往往会使用小括号提升优先级
		// int x = (int)10 * 3.5 + 6 * 1.5; // 错误：double -> int
		int x = (int)(10 * 3.5 + 6 * 1.5); // (int)44.0 -> 44
		System.out.println(x); // 44

		char c1 = 100; // ok
		int m = 100; // ok
		// char c2 = m; // 错误
		char c3 = (char)m; // ok
		System.out.println(c3); // 输出100对应的字符d
	}
}
```

练习题：

1. short s = 12; // ok

   s = s - 9; // 错误：int -> short 

2. byte b = 10; // ok

   b = b + 11;

   b = (byte)(b + 11); // ok

3. char c = 'a'; // ok

   int i = 16; // ok

   float d = .314F; // ok

   double result = c + i + d; // ok

4. byte b = 16; // ok

   short s = 14; // ok

   short t = s + b; // 错误：int -> short

## 与String类型的转换

### 基本介绍

在程序开发中，我们经常需要将基本数据类型转换为String类型，或者将String类型转换为基本数据类型

1. 基本数据类型转换为String类型

   语法：将基本类型的值 + "" 即可

2. String类型转换为基本数据类型

   语法：通过基本类型的包装类调用parseXX方法即可

```
public class StringToBasic {
	public static void main(String[] args) {
		// 基本数据类型转换为String类型
		int n1 = 100;
		float f1 = 1.1F;
		double d1 = 4.5;
		boolean b1 = true;
		String s1 = n1 + "";
		String s2 = f1 + "";
		String s3 = d1 + "";
		String s4 = b1 + "";
		System.out.println(s1 + " " + s2 + " " + s3 +" " + s4);

		// String类型转换为基本数据类型
		String s5 = "123";
		byte num1 = Byte.parseByte(s5);
		short num2 = Short.parseShort(s5);
		int num3 = Integer.parseInt(s5);
		long num4 = Long.parseLong(s5);
		float num5 = Float.parseFloat(s5);
		double num6 = Double.parseDouble(s5);
		boolean b = Boolean.parseBoolean("true");

		// 将字符串转换成字符char：含义时把字符串的第一个字符得到
		// s5.charAt(0)：得到s5字符串的第一个字符
		System.out.println(s5.charAt(0));
	}
}
```

### 使用细节

1. 在将String类型转成基本数据类型时，要确保String类型能够转成有效的数据，比如可以把 "123" 转成一个整数，但不可以把 "hello" 转成一个整数
2. 如果格式不正确，在编译时可以通过，但在运行时会抛出异常，程序终止

```
public class StringToBasicDetail {
	public static void main(String[] args) {
		String s = "hello";
		int n1 = Integer.parseInt(s);
		System.out.println(n1);
		// 运行报错：Exception in thread "main" java.lang.NumberFormatException: For input string: "hello"
	}
}
```

## 本章作业

```
public class Homework01 {
	public static void main(String[] args) {
		int n1;
		n1 = 13;
		int n2;
		n2 = 17;
		int n3;
		n3 = n1 + n2;
		System.out.println(n3); // 30

		int n4 = 38;
		int n5 = n4 - n3;
		System.out.println(n5); // 8
	}
}
```

```
public class Homework02 {
	public static void main(String[] args) {
		// 使用char类型，分别保存 \n \t \r \\ 1 2 3等字符，并输出
		char c1 = '\n'; // 换行
		char c2 = '\t'; // 制表位
		char c3 = '\r'; // 回车
		char c4 = '\\'; // 输出/
		char c5 = '1'; // 1
		char c6 = '2'; // 2
		char c7 = '3'; // 3

		System.out.println(c1);
		System.out.println(c2);
		System.out.println(c3);
		System.out.println(c4);
		System.out.println(c5);
		System.out.println(c6);
		System.out.println(c7);
	}
}
```

```
public class Homework03 {
	public static void main(String[] args) {
		// 保存两本书名，用 + 拼接
		String book1 = "天龙八部";
		String book2 = "笑傲江湖";
		// 字符串 + 字符串 = 字符串
		System.out.println(book1 + book2); // 天龙八部笑傲江湖

		// 保存两个性别，用 + 拼接
		char c1 = '男';
		char c2 = '女';
		// char + char = int ；char的本质是个整数
		System.out.println(c1 + c2); // 52906

		// 保存两本书的价格，用 + 拼接
		double price1 = 123.56;
		double price2 = 100.11;
		// double + double = double
		System.out.println(price1 + price2); // 223.67000000000002
	}
}
```

```
public class Homework04 {
	public static void main(String[] args) {
		/*
		输出：
			姓名		年龄		成绩		性别		爱好
			xx		xx		xx		xx		xx
		*/
		// 定义变量
		String name = "jack";
		int age = 18;
		double score = 99.5;
		char sex = '男';
		String robby = "打篮球";
		// 输出
		System.out.println("姓名\t年龄\t成绩\t性别\n" +
			name + '\t' + age + '\t' + score + '\t' + sex + '\t' + robby); 
	}
}
```

