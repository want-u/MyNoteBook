# Java-数组

## 数组

### 引入数组

一个养鸡场有6只鸡，他们的体重分别是3kg，5kg，1kg，3.4kg，2kg，50kg。请问六只鸡的总体重？评价体重？

### 基本介绍

数组可以存放多个同一类型的数据。数组也是一种数据类型，是引用类型

即：数组就是一组数据

### 快速入门

```
public class Array01 {
	public static void main(String[] args) {
		/*
		一个养鸡场有6只鸡，他们的体重分别是3kg，5kg，1kg，3.4kg，2kg，50kg。
		请问六只鸡的总体重？评价体重？

		旧思路分析
		1. 定义六个变量double，求和 得到总体重
		2. 平均体重 = 总体重 / 6
		3. 分析旧思路的问题。 6 -> 600
		4. 引出数组
		*/
		// double hen1 = 3;
		// double hen2 = 5;
		// double hen3 = 1;
		// double hen4 = 3.4;
		// double hen5 = 2;
		// double hen6 = 50;

		// double totalWeight = hen1 + hen2 + hen3 + hen4 + hen5 + hen6;
		// System.out.println("总体重=" + totalWeight);
		// System.out.println("平均体重=" + totalWeight / 6);

		// 使用数组解决
		// 1. double[] 表示double类型的数组，数组名 hens
		// 2. {3, 5, 1, 3.4, 2, 50}表示数组的值/元素，依次为第几个元素
		double[] hens = {3, 5, 1, 3.4, 2, 50};
		double totalWeight = 0;

		// 遍历数组得到数组的所有元素，使用for
		// 老韩解读：
		// 1. 可以通过hens[下标] 来访问数组的元素，下标从0开始
		//    第一个元素为hens[0]，第二个为hens[1]...
		// 2. 通过for就可以循环的访问数组的元素
		// 3. 使用一个变量totalWeight 将元素累计
		// 4. 可以通过 数组名.length 得到数组的大小/长度

		for (int i = 0; i < hens.length; i++) {
			totalWeight += hens[i];
		}

		// 输出信息
		System.out.println("总体重=" + totalWeight);
		System.out.println("平均体重=" + totalWeight / hens.length);
	}
}
```

## 数组的使用

### 1. 动态初始化

**数组的定义：**

数据类型 数组名[] = new 数据类型[大小] 

// 数据类型[] 数组名 = new 数据类型[大小] // 与之等同

int a[] = new int[5]; // 创建了一个数组，名字为a，存放5个int

![image-20210218191807908](https://gitee.com/luoxian1011/pictures/raw/master/image-20210218191807908.png)

**数组的引用(使用)：**

数组名[下标/索引/index]

比如：你要使用a数组的第3个数 a[2]

```
import java.util.Scanner;

public class Array02 {
	public static void main(String[] args) {
		// 演示 数据类型 数组名[] = new 数据类型[大小] 
		// 循环输入5个成绩，保存到double数组，并输出

		// 1. 创建一个double数组，大小为5
		double scores[] = new double[5];

		// 2. 循环输入
		Scanner myScanner = new Scanner(System.in);
		for (int i = 0; i < scores.length; i++) {
			System.out.println("请输入第" + (i+1) + "个元素的值");
			scores[i] = myScanner.nextDouble();
		}

		// 3. 遍历输出数组
		for (int i = 0; i < scores.length; i++) {
			System.out.println("第" + (i+1) + "个元素的值=" + scores[i]);
		}
	}
}
```

### 2. 动态初始化

- 先声明数组

  语法：数据类型 数组名[]; // 也可以 数据类型[] 数组名;

  int a[]; 或者 int[] a;

- 再创建数组

  语法：数组名 = new 数据类型[大小];

  a = new int[10];

```
double scores[]; // 声明数组，这时scores 是 null
scores = new double[5]; // 分配内存空间，可以存放数据
```

### 3. 静态初始化

初始化数组语法：
数据类型 数组名[] = {元素值, 元素值, 元素值...}

int a[] = {1, 2, 3, 4, 5, 6, 77};

上门的用法相当于：int a[] = new int[7]; a[0] = 1; a[1] = 2; a[2] = 3; a[3] = 4; a[4] = 5; a[5] = 6; a[6] = 77;

