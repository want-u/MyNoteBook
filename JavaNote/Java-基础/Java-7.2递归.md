## Java-递归

### 基本介绍

简单的说: 递归就是方法自己调用自己,每次调用时传入不同的变量.

递归有助于编程者解决复杂问题,同时可以让代码变 得简洁

### 递归作用

递归能解决什么问题？

1. 各种数学问题：8皇后问题，汉诺塔，阶乘问题，迷宫问题，球和篮子问题 [回溯算法]
2. 各种算法中也会使用到递归，比如快排，归并排序，二分查找，分治算法等
3. 将用栈解决的问题 -> 递归代码比较简洁

### 递归执行机制

递归举例:

列举两个小案例，帮助大家理解递归调用机制

```
public class Recursion01 {
	public static void main(String[] args) {
		T t1 = new T();
		// 调用test方法
		t1.test(4); // n=2 n=3 n=4
		// 调用factorial
		int res = t1.factorial(5); 
		System.out.println("res=" + res); // 120
	}
}

class T {
	// 1. 递归打印
	public void test(int n) {
		if (n > 2) {
			test(n - 1);
		}
		System.out.println("n=" + n);
	}
	// 2. 阶乘问题
	public int factorial(int n) {
		if (n == 1) {
			return 1;
		} else {
			return factorial(n - 1) * n;
		}
	}
 }
```

1. 打印问题：

![image-20210228221528294](https://gitee.com/luoxian1011/pictures/raw/master/image-20210228221528294.png)

2. 阶乘问题：

![image-20210228222809337](https://gitee.com/luoxian1011/pictures/raw/master/image-20210228222809337.png)

### 递归重要规则

1. 执行一个方法时，就创建一个新的受保护的独立空间(栈空间)
2. 方法的局部变量是独立的，不会互相影响，比如n变量
3. 如果方法中使用的是引用类型变量(如数组，对象)，就会共享该引用类型的数据
4. 递归必须向退出递归的条件逼近，否则就是无限递归，出现StackOverfolowError，死龟了:)
5. 当一个方法执行完毕，或者遇到return，就会返回，遵守谁调用，就将结果返回给谁，同时当方法执行完毕或者返回时，该方法也就执行完毕

### 课堂练习

1. 请使用递归的方式求出斐波那契数1，1，2，3，5，8，13...

   给你一个整数n，求出第n个斐波那契数

2. 猴子吃桃问题：有一堆桃子，猴子第一天吃了其中的一半，并且多吃了一个！以后每天猴子都吃其中的一半，然后再多吃一个。当到第10天时，想再吃时(还没吃)，发现只有1个桃子。

   问题：最初共有多少个桃子？

```
public class RecursionExercise01 {
	public static void main(String[] args) {
		// int n = -7;
		// T t1 = new T();
		// int res = t1.fibonacci(n);
		// if (res != -1) {
		// 	System.out.println("当n=" + n + " res=" + res);
		// } else {
		// 	System.out.println("需要输入的n>=1");
		// }
		int day = 1;
		T t1 = new T();
		int peachNum = t1.peach(day);
		if (peachNum != -1) {
			System.out.println("day=" + day + " peach=" + peachNum );
		} else {
			System.out.println("day 需要在1到10之间");
		}
	}
}


class T {
	/*
	请使用递归的方式求出斐波那契数1，1，2，3，5，8，13...
	给你一个整数n，求出第n个斐波那契数
	思路:
	1. 当n = 1 时，斐波那契数为 1
	2. 当n = 2 时，斐波那契数为 1
	3. 当n >= 3 时，斐波那契数为 前俩个数的和
	*/
	public int fibonacci(int n) {
		if (n >= 1) {
			if (n == 1 || n == 2) {
				return 1;
			} else {
				return fibonacci(n-1) + fibonacci(n-2);
			}
		} else {
			return -1;
		}

	}

	/*
	猴子吃桃问题：有一堆桃子，猴子第一天吃了其中的一半，并且多吃了一个！
	以后每天猴子都吃其中的一半，然后再多吃一个。
	当到第10天时，想再吃时(还没吃)，发现只有1个桃子。
	问题：最初共有多少个桃子？
	思路分析：
	1. day=10 ，peach=1
	2. day=9 ，peach= (day10 + 1) * 2 = 4
	...
	*/
	public int peach(int day) {
		if (day == 10) {
			return 1;
		} else if (day <= 10 && day >= 1) {
			return (peach(day + 1) + 1) * 2;
		} else {
			return -1;
		}
	} 
}
```

### 迷宫问题

递归调用应用实例-迷宫问题

![image-20210301184002841](https://gitee.com/luoxian1011/pictures/raw/master/image-20210301184002841.png)

1. 小球得到的路径，和程序员设置的找路策略有关：找路的上下左右的顺序
2. 再得到小球路径时，可以先使用(上下左右)，再改成(上右下左)，看看路径是不是变化
3. 测试回溯现象
4. 扩展思考：如何求出最短路径？

```
public class MiGong {
	public static void main(String[] args) {
		/*
		 迷宫问题
		1 1 1 1 1 1 1 
		1 0 0 0 0 0 1 
		1 0 0 0 0 0 1 
		1 1 1 0 0 0 1 
		1 0 0 0 0 0 1 
		1 0 0 0 0 0 1 
		1 0 0 0 0 0 1 
		1 1 1 1 1 1 1 
		 1. 先创建迷宫，用二维数组表示 int[][] map = new int[8][7];
		 2. 规定map数组的元素值： 0 表示 可以走 1 表示障碍物
		*/
		 int[][] map = new int[8][7];
		 // 3. 将最上面一行和最下面一行设为1
		 for (int i = 0; i < 7; i++) {
		 	map[0][i] = 1;
		 	map[7][i] = 1;
		 }
		 // 4. 将最左面一行和最右面一行设为1
 		 for (int i = 0; i < 8; i++) {
		 	map[i][0] = 1;
		 	map[i][6] = 1;
		 }
		 // 5. 设置其他障碍
		 map[3][1] = 1;
		 map[3][2] = 1;
		 // 测试回溯：即当右一步走死时，返回上一步继续测试下一个方向
		 // map[2][2] = 1; 
		 // 输出迷宫
		 System.out.println("====当前迷宫情况====");
		 for (int i = 0; i < map.length; i++) {
		 	for (int j = 0; j < map[i].length; j++) {
		 		System.out.print(map[i][j] + " ");
		 	}
		 	System.out.println(); 
		 }

		 // 调用findWay
		 T t1 = new T();
		 t1.findWay(map, 1, 1);

 		 // 输出迷宫
		 System.out.println("====寻路迷宫情况====");
		 for (int i = 0; i < map.length; i++) {
		 	for (int j = 0; j < map[i].length; j++) {
		 		System.out.print(map[i][j] + " ");
		 	}
		 	System.out.println(); 
		 }
	}
}

class T {
	/*
	使用递归回溯的思想来解决老鼠出迷宫
	1. 规定迷宫数组的值
	   1：障碍物 2：可以走 3：走过且走不通
	2. 定义一个方法findWay，返回一个表示能否走通的布尔值
	3. 传入迷宫数组map，和坐标i(行)j(列)
	4.坐标初始化为1,1，终止与6,5
	5. 找路策略 下 -> 右 -> 上 -> 左
	*/
	public boolean findWay(int[][] map, int i, int j) {
		// 当坐标为6，5时，说明已经到出口
		if (map[6][5] == 2) {
			return true;
		} else {
			
			if (map[i][j] == 0) { // 当这个位置可以走时

				map[i][j] = 2; // 先将当前位置标记为2

				// 判断周围是否能够走，不能则不通
				if (findWay(map, i + 1, j)) { // 向下 i + 1
					return true;
				} else if (findWay(map, i, j + 1)) { // 向右 j + 1
					return true;
				} else if (findWay(map, i - 1, j)) { // 向上 i - 1
					return true;
				} else if (findWay(map, i, j - 1)) { // 向左 j - 1
					return true;
				} else { // 如果都为false，则表示次路不通
					map[i][j] = 3;
					return false;
				}
			} else { // 当标记为1，2，3时，表示不通
				// 1：障碍物
				// 2：已经走过的位置
				// 3. 不通的路
				return false;
			}
		}
	}
}
```

