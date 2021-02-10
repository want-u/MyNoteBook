# Java-数据类型

![image-20210204213546079](https://gitee.com/luoxian1011/pictures/raw/master/image-20210204213546079.png)

1. java数据类型分为两大类：基本数据类型，引用数据类型
2. 基本数据类型有8种：
   1. 数值型：byte，short，int，long，float，double
   2. 字符型：char
   3. 布尔型：boolean
3. 引用类型：类，接口，数组

## 整数类型

### 基本介绍

java的整数类型就是用于存放整数值的，比如 12，20，3456 等等

![image-20210204220103133](https://gitee.com/luoxian1011/pictures/raw/master/image-20210204220103133.png)

```
public class Int01 {
	public static void main(String[] args) {
		// 整数类型
		// 2^63 = 9,223,372,036,854,775,808
		byte n1 = 10; 	// 1个字节
		short n2 = 10; 	// 2个字节
		int n3 = 10; 	// 4个字节
		long n4 = 10; 	// 8个字节
	}
}
```

### 使用细节

1. Java个整数类型有固定的范围和字段长度，不受具体OS影响，已保证java程序的可移植性
2. Java的整型常量（具体值）默认为 int 型，声明 long 型常量须后加 'l' 或 'L'
3. Java程序中变量常声明为 int 型，除非不足以表示大数，才使用 long
4. bit：计算机中的最小存储单位。byte：计算机中基本存储单元；1byte = 8bit

```
public class Int02 {
	public static void main(String[] args) {
		// 整型常量默认为int型，声明long型常量须后加'l'或'L'
		// int n2 = 1L; 	// 这是错误写法
		int n1 = 1; 	// 4个字节
		long n2 = 1L; 	// 8个字节
	}
}
```

## 浮点类型

### 基本介绍

Java的浮点类型可以表示一个小数，比如 123.4 5.6 0.23 等等

![image-20210205113331539](https://gitee.com/luoxian1011/pictures/raw/master/image-20210205113331539.png)

1. 关于浮点数在机器中存放形式的简单说明：浮点数 = 符号位 + 指数位 + 尾数位
2. 尾数位可能丢失，造成精度损失（可以说小数都是近似值）

### 使用细节

1. Java个浮点类型有固定的范围和字段长度，不受具体OS影响：[float 4字节；double 8字节]
2. Java的整型常量（具体值）默认为 double 型，声明 float 型常量须后加 'f' 或 'F'
3. 浮点型常量有两种表示形式：
   1. 十进制形式：5.12 512.0F .512（可以省略0，但必须有小数点）
   2. 科学计数法：5.12e2 5.12E-2（5.12e2 表示5.12 * 10^2；5.12E-2 表示5.12 * 10^-2）
4. 通常情况下，应该使用 double，它比 float 更精确
5. 浮点数的陷阱：2.7 与 8.1 / 3 比较

```
public class Float01 {
	public static void main(String[] args) {
		// Java的整型常量（具体值）默认为 double 型，声明 float 型常量须后加 'f' 或 'F'
		// float n1 = 1.1; // 错误: 不兼容的类型: 从double转换到float可能会有损失
		float n2 = 1.1F;
		double n3 = 1.1;
		double n4 = 1.1F;

		// 十进制形式：5.12 512.0F .512（可以省略0，但必须有小数点）
		double n5 = .123; // 0.123
		System.out.println(n5);
		// 科学计数法：5.12e2 5.12E-2（5.12e2 表示5.12 * 10^2；5.12E-2 表示5.12 * 10^-2）
		System.out.println(5.12e2); // 512.0
		System.out.println(5.12e-2); // 0.0512

		// 通常情况下，应该使用 double，它比 float 更精确
		double n6 = 2.1234567891;
		float n7 = 2.1234567891F;
		System.out.println(n6); // 2.1234567891
		System.out.println(n7); // 2.1234567

		// 浮点数的陷阱：2.7 与 8.1 / 3 比较
		double n8 = 2.7;
		double n9 = 8.1 / 3;
		System.out.println(n8); // 2.7
		System.out.println(n9); // 2.6999999999999997
		// 得出一个使用点：对运算结果时小数的进行相等判断时，要小心
		// 应该是两个数的差值的绝对值，在某个精度范围去判断
		if (n8 == n9) {
			System.out.println("假相等"); // 此判断不会输出
		}
		// 正确写法：Math.abs(n8 - n9)表示两个数的差值的绝对值
		if (Math.abs(n8 - n9) < 1e-10) {
			System.out.println("真相等"); // 此判断输出
		}

	}
}
```

## 字符类型

### 基本介绍

字符类型可以表示**单个字符**，字符类型是 char ， char 是两个字节（可以存放汉字），多个字符我们使用 String 。

```
public class Char01 {
	public static void main(String[] args) {
		char c1 = 'a';
		char c2 = '\t';
		char c3 = '韩';
		char c4 = 97; // 说明：字符类型可以直接存放一个数字

		System.out.println(c1);
		System.out.println(c2);
		System.out.println(c3);
		System.out.println(c4); // 当输出c4时，会输出数字对应的Unicode编码的字符，即a

	}
}
```

### 使用细节

1. 字符常量是用单引号（''）括起来的单个字符。例如：char c1 = 'a'; char c2 = '中'; char c3 = '9';
2. Java中还允许使用转义字符 '\\' 来将其后的字符转变为特殊字符常量。例如：char c3 = '\n';
3. Java中，char 的本质是一个整数，在输出时，是Unicode码对应的字符
4. 可以直接给 char 赋一个整数，然后输出时，会按照对应的Unicode字符输出 [97 --> a ]
5. char 类型是可以进行运算的，相当于一个整数，因为它都对应有Unicode码。

```
public class Char02 {
	public static void main(String[] args) {
		// Java中，char 的本质是一个整数，默认输出时，是Unicode码对应的字符
		// 要输出对应的数字，可以(int)字符
		char c1 = 97;
		System.out.println(c1); // 输出a

		char c2 = 'a';
		System.out.println((int)c2); // 输出'a'对应的数字

		char c3 = 38889;
		System.out.println(c3); // 输出韩

		char c4 = '韩';
		System.out.println((int)c4); // 输出韩

		// char 类型是可以进行运算的，相当于一个整数，因为它都对应有Unicode码。
		System.out.println('a' + 10); // 输出107

		//小测试
		char c5 = 'b' + 1; // 98 + 1 = 99
		System.out.println((int)c5); // 输出99
		System.out.println(c5); // 输出99 -> 对应的字符 -> 编码表Ascii -> c

	}
}
```

### 本质探讨

1. 字符型存储到计算机中，需要将字符对应的码值（整数）找出来，比如 'a'
   1. 存储：'a' ==> 码值 97 ==> 二进制 110 0001 ==> 存储
   2. 读取：二进制 110 0001  ==> 码值 97 ==> 'a' ==> 显示

2. 字符和码值的对应关系是通过字符编码表决定的

   ![image-20210205165359707](https://gitee.com/luoxian1011/pictures/raw/master/image-20210205165359707.png)

## 布尔类型

### 基本介绍

1. 布尔类型也叫 boolean 类型，boolean 类型数据只允许取值 true 和 false，无 null
2. boolean 类型占1字节
3. boolean 类型适于逻辑运算，一般用于程序流程控制：
   1. if 条件判断语句
   2. while 循环控制语句
   3. do-while 循环控制语句
   4. for 循环控制语句

```
public class Boolean01 {
	public static void main(String[] args) {
		// 判断成绩是否通过案例
		// 定义一个布尔变量
		boolean isPass = true;
		if (isPass == true) {
			System.out.println("考试通过，恭喜");
		} else {
			System.out.println("考试未通过，下次努力");
		}
	}
}
```

