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

