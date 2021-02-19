# Java-数组

## 数组

### 基本介绍

> **引入数组——**
>
> 一个养鸡场有6只鸡，他们的体重分别是3kg，5kg，1kg，3.4kg，2kg，50kg。请问六只鸡的总体重？评价体重？

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

### 数组的使用

#### 1. 动态初始化

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

#### 2. 动态初始化

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

#### 3. 静态初始化

初始化数组语法：
数据类型 数组名[] = {元素值, 元素值, 元素值...}

int a[] = {1, 2, 3, 4, 5, 6, 77};

上门的用法相当于：int a[] = new int[7]; a[0] = 1; a[1] = 2; a[2] = 3; a[3] = 4; a[4] = 5; a[5] = 6; a[6] = 77;

### 使用细节

1. 数组是多个相同类型数据的组合，实现对这些数据的同一管理

2. 数组中的元素可以是任何数据类型，包括基本类型和引用类型，但是不能混用

3. 数组创建后，如果没有赋值，有默认值

   int 0，short 0，byte 0，long 0，float 0.0， double 0.0，char \u0000，boolean false，String null

4. 使用数组的步骤：1. 声明数组并开辟空间；2. 给数组的各个元素赋值；3. 使用数组

5. 数组的下标是从 0 开始的

6. 数组下标必须在指定范围内使用，否则报：下标越界异常

   比如：int[] arr = new int[5]; 则有效下标为0~4

7. 数组属于引用类型，数值型数据是对象(Object)

```
public class ArrayDetail {
	public static void main(String[] args) {
		// 使用细节

		// 1. 数组是多个相同类型数据的组合，实现对这些数据的同一管理
		// int[] arr1 = {1, 2, 3, 4, 5, 6.6}; // double -> int

		// 2. 数组中的元素可以是任何数据类型，包括基本类型和引用类型，但是不能混用
		String[] arr3 = {"北京", "asd", "123"};

		// 3. 数组创建后，如果没有赋值，有默认值
		// int 0，short 0，byte 0，long 0，
		// float 0.0， double 0.0，char \u0000，
		// boolean false，String null
		short[] arr4 = new short[3];
		for (int i = 0; i < arr4.length; i++) {
			System.out.println(arr4[i]);
		}

		// 6. 数组下标必须在指定范围内使用，否则报：下标越界异常
		// 比如：int[] arr = new int[5]; 则有效下标为0~4
		int[] arr = new int[5];
		System.out.println(arr[5]); // 下标越界
	}
}
```

### 课堂练习

```
public class ArrayExercise01 {
	public static void main(String[] args) {
		// 创建一个char类型的26个元素的数组，放置'A'~'Z'
		// 使用for循环把所有元素打印出来
		// char类型： 'A' + 1 -> 'B'
		char[] chars = new char[26];
		for (int i = 0; i < chars.length; i++) {
			chars[i] = (char)('A' + i); // 'A' + i为int，需要强制转换
		}
		for (int i = 0; i < chars.length; i++) {
			System.out.print(chars[i] + " ");
		}
	}
}
```

```
public class ArrayExercise02 {
	public static void main(String[] args) {
		/*
		请求出一个数组int[]的最大值 {4, -1, 9, 10, 23}，并得到对应下标
		
		思路分析：
		1. 定义一个int数组 int[] arr = {4, -1, 9, 10, 23};
		2. 假定 max = arr[0] 是最大值，maxIndex=0;
		3. 从下标 1 开始遍历arr，如果max < 当前元素，则重新赋值 max = 当前元素，maxIndex=当前元素下标
		4. 遍历完数组后，即可得出最大值和下标
		*/
		int[] nums = {4, -1, 9, 10, 23};
		int max = nums[0]; // 假定第一个元素是最大值
		int maxIndex = 0; // 下标
		for (int i = 1; i < nums.length; i++) {
			if (max < nums[i]) { // 如果max < 当前元素
				max = nums[i]; // 把max 设置成当前元素
				maxIndex = i;
			}
		}
		System.out.println(max);
		System.out.println(maxIndex);
	}
}
```

### 数组赋值机制

1. 基本数据类型赋值，这个值就是具体的数据，而且互相不影响

   int n1 = 2; int n2 = n1;

2. 数组在默认情况下是引用传递，赋的值是地址

   int[] arr1 = {1,2,3};

   int[] arr2 = arr1;

```
public class ArrayAssign {
	public static void main(String[] args) {
		// 基本数据类型赋值，这个值就是具体的数据，而且互相不影响
		// n2 的变化，不会影响到 n1 的值
		int n1 = 10;
		int n2 = n1;
		n2 = 80;
		System.out.println("n1=" + n1); // 10
		System.out.println("n2=" + n2); // 80

		// 数组在默认情况下是引用传递，赋的值是地址
		// arr2 的变化会影响到 arr1
		int[] arr1 = {1, 2, 3};
		int[] arr2 = arr1; // 把arr1赋给arr2
		arr2[0] = 10;

		System.out.println("===arr1的元素====");
		for (int i = 0; i < arr1.length; i++) {
			System.out.println(arr1[i]);
		}
	}
}
```

![image-20210219192935538](https://gitee.com/luoxian1011/pictures/raw/master/image-20210219192935538.png)

### 数组拷贝

编写代码：实现数组拷贝（内容复制）

将int[] arr1 = {10, 20, 30}; 拷贝到 arr2 数组，要求数据空间是独立的

```
public class ArrayCopy {
	public static void main(String[] args) {
		// 将int[] arr1 = {10, 20, 30}; 拷贝到 arr2 数组，
		// 要求数据空间是独立的
		int[] arr1 = {10, 20, 30};

		// 创建新数组arr2，开辟新的数据空间
		// 大小等于arr1.length
		int[] arr2 = new int[arr1.length];

		// 遍历arr1，把每个元素拷贝到arr2对应的元素
		for (int i = 0; i < arr1.length; i++) {
			arr2[i] = arr1[i];
		}

		// 修改arr2的元素，不会影响arr1的值
		arr2[0] = 100;

		// 输出arr1
		System.out.println("arr1的元素：");
		for (int i = 0; i < arr1.length; i++) {
			System.out.println(arr1[i]); // 10, 20, 30
		}

		// 输出arr2
		System.out.println("arr2的元素：");
		for (int i = 0; i < arr2.length; i++) {
			System.out.println(arr2[i]); // 100, 20, 30
		}
	}
}
```

![image-20210219194931552](https://gitee.com/luoxian1011/pictures/raw/master/image-20210219194931552.png)