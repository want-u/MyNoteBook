# Java-多维数组

## 二维数组

### 应用场景

比如我们开发一个五子棋游戏，棋盘就是需要二维数组来表示

### 快速入门

请用二维数组输出如下图形：

```
0 0 0 0 0 0
0 0 1 0 0 0
0 2 0 3 0 0
0 0 0 0 0 0
```

```

public class TwoDimensionalArray01 {
	public static void main(String[] args) {
		/*
		请用二维数组输出如下图形：
		0 0 0 0 0 0
		0 0 1 0 0 0
		0 2 0 3 0 0
		0 0 0 0 0 0
		*/

		//1. 从定义上看 int[][]
		//2. 可以这样理解，原来的一维数组的每一个元素是一维数组，就构成二维数组
		int[][] arr = {{0, 0, 0, 0, 0, 0}, 
					   {0, 0, 1, 0, 0, 0},
					   {0, 2, 0, 3, 0, 0}, 
					   {0, 0, 0, 0, 0, 0}};

		// 输出二维图形
		for (int i = 0; i < arr.length; i++) {
			// 遍历二维数组的每一个元素(数组)
			// 1. arr[i] 表示二维数组的第 (i+1) 个元素
			// 2. arr[i].length 得到对应的 每一个数组的长度
			for (int j = 0; j < arr[i].length; j++) {
				System.out.print(arr[i][j] + " "); // 输出一维数组
			}
			System.out.println(); // 换行
		}
	}
}
```

### 内存布局

![image-20210223105601980](https://gitee.com/luoxian1011/pictures/raw/master/image-20210223105601980.png)

### 使用方式

#### 1. 动态初始化

1. 语法：类型\[][] 数组名 = new 类型 \[大小][大小] 
2. 比如：int arr\[][] = new int\[2][3]

```
int[][] arr = new int[2][3]
```

#### 2. 动态初始化

1. 先声明：类型\[][] 数组名;
2. 再定义(开辟空间)：数组名 = new int\[2][3];
3. 赋值(有默认值，比如 int 类型的就是 0 )

```
int[][] arr; // 声明二维数组 或int arr[][];
arr = new int[2][3] // 再开辟空间
```

#### 3. 动态初始化-列数不定

需求：动态创建下面的二维数组，并输出

![image-20210223230500269](https://gitee.com/luoxian1011/pictures/raw/master/image-20210223230500269.png)

```
public class TwoDimensionalArray02 {
	public static void main(String[] args) {
		/*
		需求：动态创建下面的二维数组，并输出
		i = 0: 1
		i = 1: 2 2
		i = 2: 3 3 3
		有三个一维数组，每个一维数组的元素是不一样的 
		*/

		// 创建二维数组，有3个一维数组，但是每个一维数组还没有开辟数据空间
		int[][] arr = new int[3][];

		for (int i = 0; i < arr.length; i++) {
			// 给每一个一维数组开辟空间 new
			// 如果没有给一维数组 new，那么arr[i] 就是null
			arr[i] = new int[i + 1];

			// 遍历一维数组，并给每个元素赋值
			for (int j = 0; j < arr[i].length; j++) {
				arr[i][j] = i + 1; // 赋值
			}
		}

		// 输出二维数组
		for (int i = 0; i < arr.length; i++) {
			// 输出每个一维数组
			for (int j = 0; j < arr[i].length; j++) {
				System.out.print(arr[i][j] + " ");
			}
			System.out.println(); // 换行
		}
	}
}
```

#### 4. 静态初始化

1. 定义：类型 数组名\[][] = {{值1, 值2...}, {值1, 值2...}, {值1, 值2...}};
2. 使用即可 [ 固定方式访问 ]

比如：

int\[][] arr = {{1,1,1}, {8,8,9}, {100}};

解读：

1. 定义了一个二维数组 arr
2. arr有三个元素 (每个元素都是一维数组)
3. 第一个一维数组有3个元素，第二个一维数组有3个元素，第三个一维数组有1个元素( 一个元素也必须是数组类型 )

### 课堂练习

案例：

int\[][] arr = {{4,6}, {1,4,5,7}, {-2}}; 遍历该数组，并得到和

```
public class TwoDimensionalArray05 {
	public static void main(String[] args) {
		// int[][] arr = {{4,6}, {1,4,5,7}, {-2}}; 
		// 遍历该数组，并得到和
		int[][] arr = {{4,6}, {1,4,5,7}, {-2}};
		int sum = 0;

		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr[i].length; j++) {
				sum += arr[i][j];
			}
		}
		System.out.println("sum=" + sum);

	}
}
```

### 杨辉三角

使用二维数组打印一个10行的杨辉三角

```
1
1 1
1 2 1
1 3 3 1
1 4 6 4 1
1 5 10 10 5 1
```

提示：

1. 第一行有1个元素，第n行有n个元素

2. 每一行的第一个元素和最后一个元素都是1

3. 从第三行开始，对于非一个个元素个最后一个元素的值arr\[i][j]

   arr\[i][j] = arr\[i-1][j] + arr\[i-1][j-1]

```
public class YangHui {
	public static void main(String[] args) {
		/*
		使用二维数组打印一个10行的杨辉三角
		1
		1 1
		1 2 1
		1 3 3 1
		1 4 6 4 1
		1 5 10 10 5 1
		
		1. 第一行有1个元素，第n行有n个元素
		2. 每一行的第一个元素和最后一个元素都是1
		3. 从第三行开始，对于非一个个元素个最后一个元素的值arr[i][j]
		   arr[i][j] = arr[i-1][j] + arr[i-1][j-1]
		*/
		int[][] arr = new int[10][];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = new int[i+1];

			for (int j = 0; j < arr[i].length; j++) {
				if (j == 0 || j == arr[i].length - 1) {
					arr[i][j] = 1;
				} else {
					arr[i][j] = arr[i-1][j] + arr[i-1][j-1];
				}
			}
		}

		// 输出
		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr[i].length; j++) {
				System.out.print(arr[i][j] + " ");
			}
			System.out.println();
		}
	}
}
```

### 使用细节

1. 一维数组的声明方式：

   int[] x 或者 int x[]

2. 二维数组的声明方式：

   int\[][] y 或者 int[] y[] 或者 int y\[][]

3. 二维数组实际上是由多个一维数组组成的，它的各个一维数组的长度可以相同，也可以不相同。

   比如：map\[][] 是一个二维数组; int map\[][] = {{1,2}, {3,4,5}};

   由map[0] 是一个含有两个元素的一维数组，map[1]是一个含有三个元素的一维数组构成，我们也称为列数不等的二维数组

```
// 练习
声明：int[] x,y[]; 以下选项允许通过编译的是(be):
// int[] x; int[][] y;
a) x[0] = y; //错误 int[][] -> int
b) y[0] = x; // ok int[] -> int[]
c) y[0][0] = x; //错误 int[] -> int
d) x[0][0] = y; //错误 x[0][0]错误写法
e) y[0][0] = x[0]; // ok int -> int
f) x = y; // 错误 int[][] -> int[]
```

## 本章作业

```
1. 下面数组定义正确的有__BD__。
A. String strs[] = {'a', 'b', 'c'}; // error:char -> String
B. String[] strs = {"a", "b", "c"}; // ok
C. String[] strs = new String{"a", "b", "c"}; // error
D. String strs[] = new String[]{"a", "b", "c"}; // ok
E. String[] strs = new String[3]{"a", "b", "c"}; // error 编译不通过
```

```
2. 写出结果

String foo = "blue";
boolean[] bar = new boolean[2]; // 默认值为false
if (bar[0]) {
	foo = "green";
}
System.out.println(foo); // 输出blue
```

```
3. 以下Java代码的输出结果为：1,3,5,7
int num = 1;
while (num < 10) {
	System.out.println(num);
	if (num > 5) {
		break;
	}
	num += 2;
}
```

```
public class Homework04 {
	public static void main(String[] args) {
		/*
		4. 已知有个升序的数组，要求插入一个元素，该数组顺序依然是升序
		比如：[10, 12, 45, 90] 添加23 ,得到[10, 12, 23, 45, 90]

		思路：
		1. 定义一个新数组 int[] arrNew = new int[arr.length + 1];
		2. 遍历arr数组
			arr[i] < insertNum时，arrNew[i] = arr[i]，使用index记录当前i
			否则，arrNew[i + 1] = arr[i]
			空出需要插入的空间
		3. arr[index + 1] = insertNum
		4. 测试边界：插入2和2222试试
		*/
		int[] arr = {10, 12, 45, 90};
		int insertNum = 223;
		int index = -1;

		int[] arrNew = new int[arr.length + 1];

		for (int i = 0; i < arr.length; i++) {
			if (arr[i] < insertNum) {
				arrNew[i] = arr[i];
				// 记录比insertNum小的索引
				// 循环完成后，index记录(插入值索引 - 1)的索引
				index = i;
			} else {
				arrNew[i + 1] = arr[i];
			}
		}
		arrNew[index + 1] = insertNum;
		arr = arrNew;

		System.out.println("====插入后的数组元素====");
		for (int i = 0; i < arr.length; i++) {
			System.out.print(arr[i] + " ");
		}
	}
}
```

![image-20210224161433360](https://gitee.com/luoxian1011/pictures/raw/master/image-20210224161433360.png)

```
public class Homework05 {
	public static void main(String[] args) {
		/*
		随机生成10个整数(1~100)保存到数组，并倒序打印
		求平均值、最大值和最大值的下标
		并查找里面是否有8
		随机值：(int)(Math.random() * 100 + 1)
		*/
		int[] arr = new int[10];
		double sum = 0; // 累积和求平均值

		// 随机生成10个整数(1~100)保存到数组
		for (int i = 0; i < arr.length; i++) {
			arr[i] = (int)(Math.random() * 100 + 1);
			sum += arr[i];
		}

		int max = arr[0]; // 初始化最大值
		int maxIndex = -1; // 初始化最大值索引
		int findNum = 8; // 查找的数值
		int findIndex = -1; // 查找的数值的下标

		System.out.println("====数组的情况====");
		for (int i = 0; i < arr.length; i++) {
			System.out.print(arr[i] + " ");
		}

		// 倒序打印数组
		System.out.println("\n====倒序打印数组====");
		for (int i = arr.length - 1; i >= 0; i--) {
			System.out.print(arr[i] + " ");
			// 循环完成，得到最大值和索引
			if (max < arr[i]) {
				max = arr[i];
				maxIndex = i;
			}
			// 判断是否有findNum
			if (arr[i] == findNum) {
				findIndex = i;
			}
		}

		// 输出
		System.out.println("\navg=" + (sum/arr.length));
		System.out.println("max=" + max);
		System.out.println("maxIndex=" + maxIndex);

		if (findIndex != -1) {
			System.out.println("有" + findNum + " findIndex=" + findIndex);
		} else {
			System.out.println("没有" + findNum);
		}
	}
}
```

![](https://gitee.com/luoxian1011/pictures/raw/master/image-20210224165737861.png)

```
public class Homework07 {
	public static void main(String[] args) {
		// 写出冒泡排序:从小到大
		int[] arr = {1, 55, 2, 9, 7};
		int temp;

		for (int i = 0; i < arr.length - 1; i++) {
			for (int j = 0; j < arr.length - 1 - i; j++) {
				// 若顺序要从大到小
				// 则条件是：arr[j] < arr[j + 1]
				if (arr[j] > arr[j + 1]) {
					temp = arr[j];
					arr[j] = arr[j + 1];
					arr[j + 1] = temp;
				}
			}
		}

		// 输出
		for (int i = 0; i < arr.length; i++) {
			System.out.print(arr[i] + " ");
		}
	}
}
```

