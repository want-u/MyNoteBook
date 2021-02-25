# Java-运算符

## 运算符介绍

运算符是一种特殊的符号，用以表示数据的运算、赋值和比较等。

1. 算术运算符
2. 赋值运算符
3. 关系运算符（比较运算符）
4. 逻辑运算符
5. 位运算符（需要二进制基础）
6. 三元运算符

## 算术运算符

### 基本介绍

算术运算符是对数值类型的变量进行运算的，在Java程序中使用的非常多

![image-20210206201132189](https://gitee.com/luoxian1011/pictures/raw/master/image-20210206201132189.png)

### 案例演示

1. +，-，*，/，%，++，--，重点是：/，%，++

2. 自增：++

   作为独立的语句使用时，都等价于：i = i + 1;

   做为表达式使用时，\++i 先自增后赋值；i\++ 先赋值后自增

3. -- 原理与 ++ 相同

```
public class ArithmeticOperator {
	public static void main(String[] args) {
		// /：除法
		System.out.println(10 / 4); // 数学角度：2.5， Java：2[int精度]
		System.out.println(10.0 / 4); // Java：2.5
		double d = 10 / 4; // Java：10 / 4 = 2，2=> 2.0
		System.out.println(d); // Java：2.5

		// % 取模，取余
		// 在Java中，% 的本质：a % b = a - a / b * b
		System.out.println(10 % 3); // 1
		// -10 % 3 => -10 - (-10) / 3 * 3 => -10 - (-9) = -1
		System.out.println(-10 % 3); // -1
		System.out.println(10 % -3); // 1
		System.out.println(-10 % -3); // -1

		// ++ 自增的独立使用
		int i = 10;
		i++; // 自增，等价于：i = i + 1 => i = 11
		++i; // 自增，等价于：i = i + 1 => i = 12
		System.out.println(i);
		// ++ 自增的表达式使用
		// ++i 先自增后赋值；i++ 先赋值后自增
		int j = 8;
		int k = j++; // 等价于：k = j = 8; j = j + 1 = 9;
		int w = ++j; // 等价于：j = j + 1 = 10; w = j = 10;
		System.out.println("j=" + j + " k=" + k + " w=" + w); // j=10 k=8 w=10
	}
}
```

### 面试题目

问：结果是多少，为什么

1. ```
   int i = 1; // i => 1
   i = i++; 
   // （临时变量规则）
   // 1. temp = i; 
   // 2. i = i + 1; 
   // 3. i = temp
   System.out.println(i); // 1
   ```
   
2. ```
   int i = 1; // i => 1
   i = ++i; 
   // （临时变量规则）1. i=i+1; 2. temp=i; 3. i = temp
   System.out.println(i); // 2
   ```

### 课堂练习

>  内存分析法：![image-20210206211949652](https://gitee.com/luoxian1011/pictures/raw/master/image-20210206211949652.png)

```
public class ArithmeticOperatorExercise01 {
	public static void main(String[] args) {
		// 练习1
		int i1 = 10;
		int i2 = 20;
		int i = i1++; // i = i1 => 10; i1 = i1 + 1
		System.out.print("i=" + i); // 10
		System.out.println(" i2=" + i2); // 20
		i = --i2; // i2 = i2 - 1; i = i2 => 19
		System.out.print("i=" + i); // 19
		System.out.println(" i2=" + i2); // 19
	}
}
```

```
public class ArithmeticOperatorExercise02 {
	public static void main(String[] args) {
		/*
		1. 需求：
			假如还有59天放假，问：合xx个星期xx天
		2. 思路分析
			1)使用 int days 保存天数;
			2)一个星期7天；
			  星期数 int weeks = days / 7;
			  零几天 int leftDays = days % 7;
			3)输出
		3. 走代码
		*/
		int days = 59;
		int weeks = days / 7;
		int leftDays = days % 7;
		System.out.println(days + "天 合：" 
		+ weeks + "星期，零" + leftDays + "天"); // 59天 合：8星期，零3天

		/*
		1. 需求：
			定义一个变量保存华氏温度，华氏温度转换摄氏度温度的公式为：5/9*(华氏温度-100)，请求出华氏温度 234.6 对应的摄氏温度
		2. 思路分析：
			1)使用 double huaShi 保存华氏温度;
			2)套用数学公式 5/9*(华氏温度-100)
			3)输出
		*/
		double huaShi = 1234.6;
		// double sheShi = 5 / 9 * (huaShi - 100);
		// 使用数学公式时要考虑 Java的特性：5 / 9 = 0 ！
		double sheShi = 5.0 / 9 * (huaShi - 100);
		System.out.println("华氏温度" + huaShi + " 对应的摄氏温度" + sheShi);
	}
}
```

## 关系运算符

### 基本介绍

1. 关系运算符的结果都是 boolean 型，也就是要么是 true，要么 flase
2. 关系表达式 经常用在 if 结构的条件中或循环结构的条件中

![image-20210207111506464](https://gitee.com/luoxian1011/pictures/raw/master/image-20210207111506464.png)

### 案例演示

1. 关系运算符的结果都是 boolean 型，也就是要么是 true，要么 flase
2. 关系运算符组成的表达式，我们称为 **关系表达式** 。a > b;
3. 比较运算符 "==" 不能误写成 "="

```
public class RelationalOperator {
	public static void main(String[] args) {
		// 关系运算符的使用
		// 开发中，不可以使用 a, b, a1, n1 ...无意义变量名
		int a = 9;
		int b = 8;
		System.out.println(a > b); // true
		System.out.println(a >= b); // true
		System.out.println(a <= b); // false
		System.out.println(a < b); // false
		System.out.println(a == b); // false
		System.out.println(a != b); // true
		boolean flag = a > b;
		System.out.println(flag); // true
	}
}
```

## 逻辑运算符

### 基本介绍

用于连接多个条件（多个关系表达式），最终的结果是一个 boolean 。

![image-20210207113740279](https://gitee.com/luoxian1011/pictures/raw/master/image-20210207113740279.png)

### 短路与逻辑与

&& 和 & 的基本规则

| 名称     | 语法         | 特点                                    |
| -------- | ------------ | --------------------------------------- |
| &&短路与 | 条件1&&条件2 | 两个条件都为true，结果为true，否则false |
| &逻辑与  | 条件1&条件2  | 两个条件都为true，结果为true，否则false |

&& 和 & 的使用区别：

1. &&短路与：如果第一个条件为false，则第二个条件不会判断，最终结果为false，效率高
2. &逻辑与：不管第一个条件是否为false，第二个条件都要判断，效率低
3. **开发中，我们使用的基本都是短路与&&，效率高**

```
public class LogicOperator01 {
	public static void main(String[] args) {
		// &&短路与 和 &逻辑与的演示
		int age = 50;
		if (age > 20 && age < 90) {
			System.out.println("ok 100"); // 条件为true，会输出
		}
		if (age > 20 & age < 90) {
			System.out.println("ok 200"); // 条件为true，会输出
		}

		// &&短路与 和 &逻辑与的区别
		// 1. &&短路与：如果第一个条件为false，则第二个条件不会判断，最终结果为false，效率高
		// 2. &逻辑与：不管第一个条件是否为false，第二个条件都要判断，效率低
		int a = 4;
		int b = 9;
		if (a < 1 && b++ < 50) { // a<1 => false; 则不会执行b++
			System.out.println("ok 300");
		}
		System.out.println("a=" + a + " b=" + b); // a=4 b=9
	}
}
```

### 短路或逻辑或

|| 和 | 的基本规则

| 名称        | 语法             | 特点                                            |
| ----------- | ---------------- | ----------------------------------------------- |
| \|\| 短路或 | 条件1 \|\| 条件2 | 两个条件中只要有一个true，结果为true，否则false |
| \| 逻辑或   | 条件1 \| 条件2   | 两个条件中只要有一个true，结果为true，否则false |

|| 和 | 的使用区别：

1. || 短路或 ：如果第一个条件为true，则第二个条件不会判断，最终结果为false，效率高
2. | 逻辑或 ：不管第一个条件是否为true，第二个条件都要判断，效率低
3. **开发中，我们使用的基本都是短路或 || ，效率高**

```
public class LogicOperator02 {
	public static void main(String[] args) {
		// ||短路或 和 |逻辑或 的演示
		int age = 50;
		if (age > 20 || age < 30) {
			System.out.println("ok 100"); // 条件为true，会输出
		}
		if (age > 20 | age < 30) {
			System.out.println("ok 200"); // 条件为true，会输出
		}

		// ||短路或 和 |逻辑或 的区别
		// 1. || 短路或 ：如果第一个条件为true，则第二个条件不会判断，最终结果为false，效率高
		// 2. | 逻辑或 ：不管第一个条件是否为true，第二个条件都要判断，效率低
		int a = 4;
		int b = 9;
		if (a > 1 || ++b > 4) { // 若使用|，则++b执行 => b=10
			System.out.println("ok 300"); // 条件为true，会输出
		}
		System.out.println(b); // 9
	}
}
```

### 逻辑非和异或

! 和 ^ 的基本规则

| 名称       | 语法   | 特点                                    |
| ---------- | ------ | --------------------------------------- |
| ! 非(取反) | ! 条件 | 如果条件本身成立，结果为true，否则false |
| ^ 逻辑异或 | a ^ b  | 当a和b不同时，结果为true，否则false     |

```
public class InverseOperator {
	public static void main(String[] args) {
		// ! 和 ^ 的演示

		// ! 非运算，取反
		System.out.println(!(2 > 1)); // false

		// ^ 异或，不同为true
		System.out.println((4 < 1) ^ (6 > 3)); // true
		System.out.println((4 >1) ^ (6 > 3)); // false
	}
}
```

### 课堂练习

![image-20210207125755349](https://gitee.com/luoxian1011/pictures/raw/master/image-20210207125755349.png)

![image-20210207130728100](https://gitee.com/luoxian1011/pictures/raw/master/image-20210207130728100.png)

```
public class Test {
	public static void main(String[] args) {
		// 逻辑运算符练习

		// int x = 5;
		// int y = 5;
		// if (x++ == 6 & ++y == 6) {
		// 	x = 11;
		// }
		// // x++ == 6：先比较x == 6(false => 不执行x=11); 再自增x = x + 1;
		// // ++y == 6：先自增y = y + 1; 再比较y == 6;(true)
		// System.out.println("x=" + x + ",y=" + y);
		// // x=6,y=6

		// int x = 5, y = 5;
		// if (x++ == 6 && ++y == 6) {
		// 	x = 11;
		// }
		// // x++ == 6：先比较x == 6(false => 不执行x=11); 再自增x = x + 1;
		// System.out.println("x=" + x + ",y=" + y);
		// // x=6,y=5

		// int x = 5, y = 5;
		// if (x++ == 5 | ++y == 5) {
		// 	x = 11;
		// }
		// // x++ == 5：先比较x == 5(true); 再自增x = x + 1;  => 执行x=11
		// // ++y == 5：先自增y = y + 1; 再比较y == 5;(false)
		// System.out.println("x=" + x + ",y=" + y);
		// // x=11,y=6

		// int x = 5, y = 5;
		// if (x++ == 5 || ++y == 5) {
		// 	x = 11;
		// }
		// // x++ == 5：先比较x == 5(true); 再自增x = x + 1;  => 执行x=11
		// System.out.println("x=" + x + ",y=" + y);
		// // x=11,y=5

		boolean x = true, y = false;
		short z = 46;
		if ((z++ == 46) && (y = true)) z++;
		// z++ == 46 => true;  z=47
		// y = true：y赋值为true => true; 执行z++ z=48
		if ((x = false) || (++z == 49)) z++;
		// x = false => false;
		// ++z == 49：z=49 => true; 执行z++ z=50 
		System.out.println(z); // 50
	}
}
```

## 赋值运算符

### 基本介绍

赋值运算符就是将某个运算后的值，赋给指定的变量

赋值运算符的分类：

1. 基本赋值运算符：= （int a = 10;）

2. 复合赋值运算符：+=，-=，*=，/=，%=等

   a += b; // 等价与：a = a + b;

   a -= b; // 等价与：a = a - b;

### 案例演示

赋值运算符的基本使用：

```
public class AssignOperator {
	public static void main(String[] args) {
		// 演示赋值运算符的使用
		int n1 = 10;
		n1 += 4; // n1 = n1 + 4;
		System.out.println(n1); // 输出14;
	}
}
```

### 使用细节

赋值运算符的特点：

1. 运算顺序从右到左 int num = a + b + c;

2. 赋值运算符的左边只能是变量，右边可以是变量、表达式、常量值

   如：int num = 20; int num2 = 17 * 34 - 9; int num3 = a;

3. 复合赋值运算符等价与下面的效果

   如：a += 3;  => a = a + 3;

4. 复合赋值运算符会进行类型转换

   如：byte b = 2; b += 3; b++;

```
public class AssignOperator {
	public static void main(String[] args) {
		// 演示赋值运算符的使用
		int n1 = 10;
		n1 += 4; // n1 = n1 + 4;
		System.out.println(n1); // 输出14;

		// 复合赋值运算符
		byte b = 3;
		b += 2; // 等价于：b = (byte)(b + 2);
		b++;	// 等价于：b = (byte)(b + 1);
	}
}public class AssignOperator {
	public static void main(String[] args) {
		// 复合赋值运算符
		byte b = 3;
		b += 2; // 等价于：b = (byte)(b + 2);
		b++;	// 等价于：b = (byte)(b + 1);
	}
}
```

## 三元运算符

### 基本介绍

基本语法：

条件表达式 ? 表达式1 : 表达式2;

运算规则：

1. 如果条件表达式为true，运算后的结果是 表达式1;
2. 如果条件表达式为false，运算后的结果是 表达式2;

口诀：[一灯大师：一真大师]

```
public class TernaryOperator {
	public static void main(String[] args) {
		// 三元运算符的使用
		int a = 10, b = 99;
		// 解读：
		// 1. a > b => false，a++ 不执行
		// 2. 返回 b--; => 先赋值后自增
		// 3. result = 99; b = 98
		// int result1 = a > b ? a++ : b--;
		// System.out.println(result1); // 99

		int result2 = a < b ? a++ : b--;
		System.out.println("result2=" + result2 + " a=" + a + " b=" + b); 
		// result2=10 a=11 b=99
	}
}
```

### 使用细节

1. 表达式1 和 表达式2 要为可以赋值给接收变量的类型（或可以自动转换）

2. 三元运算符可以转成 if -- else 语句

   int result;
   if (a > b) result = a;
   else result = b;

```
public class TernaryOperatorDetail {
	public static void main(String[] args) {
		// 三元运算符的细节
		// 表达式1 和 表达式2 要为可以赋值给接收变量的类型
		//（或可以自动转换 或强制转换）
		int a = 3;
		int b = 8;
		int c = a > b ? a : b; // ok
		// int d = a > b ? 1.1 : 2.2; // 错误：不兼容类型
		int d = a > b ? (int)1.1 : (int)2.2; // ok
	}
}
```

### 课堂练习

```
public class TernaryOperatorExercise {
	public static void main(String[] args) {
		// 案例：实现三个数的最大值
		int n1 = 55;
		int n2 = 33;
		int n3 = 123;
		// 思路：
		// 1. 先得到 n1 和 n2 中的最大值，存为max1
		// 2. 再求出 max1 和 n3 的最大值
		int max1 = n1 > n2 ? n1 : n2;
		int max2 = max1 > n3 ? max1 : n3;
		System.out.println(max2);

		// 使用一句话实现
		int max3 = (n1 > n2 ? n1 : n2) > n3 ?
		 (n1 > n2 ? n1 : n2) : n3;
		System.out.println(max3);
	}
}
```

## 运算符优先级

### 基本介绍

1. 运算符有不同的优先级，所谓优先级就是表达式运算中的运算顺序。如下图：上一行运算符总优先于下一行

   ![image-20210207190731024](https://gitee.com/luoxian1011/pictures/raw/master/image-20210207190731024.png)

2. 只有单目运算符、赋值运算符是从右向左运算的。

### 梳理小结

（用多了就熟了）

1. () , {} 等
2. 单目运行 ++ --
3. 算术运算符
4. 位移运算符
5. 比较运算符
6. 逻辑运算符
7. 三元运算符
8. 赋值运算符

## 本章作业

1. 计算下列表达式的结果

   10 / 3 = 3; 10 / 5 = 2; 10 % 2 = 0; -10.5 % 3= -1.5

   // a % b，当a是小数时，公式：a - (int)a / b * b

   // -10.5 % 3 = -10.5 - (-10) / 3 * 3 = -10.5 + 9 = -1.5

   // -10.4 % 3 => -1.4000000000000004 [小数运算都是近似值]

2. 说出下面代码的结果

   int i = 66;

   System.out.println(++i + i); // i = i+1 => i = 67 => 134

3. 在Java中，以下赋值语句正确的是（CD）

   A) int num1 = int("18"); // 错误：应该Integer.parseInt("18");

   B) int num2 = 18.0; // 错误：double -> int

   C) double num3 = 3d; // ok，3d为double

   D) double num4 = 8; //ok 自动提升

   E) int i = 48; char ch = i+1; // 错误：int -> char

   F) byte b = 19; short s = b + 2; // 错误：int -> short

4. 写出将String 转成double的语句，以及char类型转成String的语句

   String s = "18.8";

   doubel d = Double.parseDouble(s);

   char c = '韩';

   String s2 = c + "";