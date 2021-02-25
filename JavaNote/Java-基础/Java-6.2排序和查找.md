# Java-排序和查找

## 排序

### 基本介绍

排序是将一群数据，依指定的顺序进行排列的过程

排序的分类：

1. 内部排序

   指将需要处理的所有数据都加载到内存中进行排序，包括(交换式排序法、选择式排序法 和 插入式排序法)

2. 外部排序法

   数据量过大，无法全部加载到内存中，需要借助外部存储进行排序。包括(合并排序法 和 直接合并排序法)

### 冒泡排序法

冒泡排序 (Bubble Sorting) 的基本思想是：

通过对 待排序 序列从后向前(从下标较大的元素开始)，依次比较相邻元素的值，若发现逆序则交换，使值较大的元素逐渐从前移向后部，就像水底下的气泡一样逐渐向上冒

### 冒泡排序案例

下面我们举一个具体的案例来说明冒泡法：

我们将五个无序：24，69，80，57，13使用冒泡排序法将其排成一个**从小到大**的有序数列

![image-20210222120156893](https://gitee.com/luoxian1011/pictures/raw/master/image-20210222120156893.png)

```
public class Bubble01 {
	public static void main(String[] args) {
		// 我们将五个无序：24，69，80，57，13
		// 使用冒泡排序法将其排成一个**从小到大**的有序数列
		int[] arr = {-1, 1, 111, 24, 69, 80, 111, 112, 113, 114, 116};

		int temp; // 临时变量
		// boolean flag = false; // 初始化flag

		for (int i = 0; i < arr.length - 1; i++) {
			for (int j = 0; j < arr.length - i - 1; j++) {
				if (arr[j] > arr[j + 1]) {
					temp = arr[j];
					arr[j] = arr[j + 1];
					arr[j + 1] = temp;
					// flag = true; // 有交换动作的flag
				}
			}
			// if (! flag) { // 当循环后，flag依然为false，则无需排序
			// 	System.out.println("无需排序");
			// 	break;
			// }
			System.out.println("\n====冒泡排序第" + (i+1) + "轮====");
			for (int j = 0; j < arr.length; j++) {
				System.out.print(arr[j] + " ");
			}

	
		}
	}
}
```

## 查找

### 基本介绍

在Java中，我们常用的查找有两种：

1. 顺序查找
2. 二分查找【二分法】

### 案例演示

1. 有一个数列[数组]：白眉鹰王、金毛狮王、紫衫龙王、青翼蝠王

   从键盘中输入一个名称，判断数列中是否存在

   要求：如果找到了，就提示找到，并给出下标值

```
import java.util.Scanner;
public class SeqSearch {
	public static void main(String[] args) {
		/*
		有一个数列[数组]：白眉鹰王、金毛狮王、紫衫龙王、青翼蝠王
		从键盘中输入一个名称，判断数列中是否存在
		要求：如果找到了，就提示找到，并给出下标值

		思路：
		1. 定义一个字符串数组
		2. 接收用户输入，变历数组，逐一比较
		   有则提示信息，并退出
		*/
		
		// 定义一个字符串数组
		String[] names = {"白眉鹰王", "金毛狮王", "紫衫龙王", "青翼蝠王"};
		Scanner myScanner = new Scanner(System.in);
		int index = -1; // 初始化索引

		// 接收输入
		System.out.println("请输入名字：");
		String findName = myScanner.next();

		// 变历数组，逐一比较
		for (int i = 0; i < names.length; i++) {
			// 字符串比较 equals方法
			if (findName.equals(names[i])) {
				System.out.println("找到" + findName);
				System.out.println("下标为：" + i);
				index = i;
				break;
			}
		}

		// 如果没找到，index则仍为-1
		if (index == -1) {
			System.out.println("没找到" + findName);
		}

	}
}
```

2. 请对一个**有序数组**进行**二分查找** {1, 8, 10, 89, 1000, 1234}

   输入一个数看该数组是否存在此数

```
public class Search02 {
	public static void main(String[] args) {
		/*
		int[] arr = {1, 8, 10, 89, 99, 1000, 1234};
		使用二分查找：10

		1. left:0  right:6  mid: (0 + 6) / 2 = 3
		   arr[3]: 89 > 8; // 向左继续查，right=mid - 1

		2. left:0  right:2  mid: (0 + 2) / 2 = 1
		   arr[3]: 8 < 10; // 向右继续查，left=mid + 1

		3. left:2  right:3  mid: (2 + 3) / 2 = 2
		   arr[3]: 10 == 10; // 找到
		*/
		int[] arr = {1, 8, 10, 89, 99, 1000, 1234};
		int num = 99;

		// 定义变量，表示查找数组范围的最左侧，先从0索引开始
		int left = 0; 
		// 定义变量，表示查找数组范围的最右侧，先从最大索引开始
		int right = arr.length - 1;
		// 定义变量，表示查找范围的中间值
		int mid;

		boolean flag = false;
		
		while (left <= right) {
		  // 中间索引 = (左侧  + 右侧) / 2
		  // mid = (left + right) / 2; 
		  // 为了提高效率，我们可以用位运算代替除以运算
		  mid = (left + right) >> 1;
		  // System.out.println("mid=" + mid);
		  if (arr[mid] > num) {
		      //如果中间元素大于要查找元素，则在中间元素的左侧去找正确元素，最右侧变为mid - 1
		      right = mid - 1;
		  } else if (arr[mid] < num) {
		      //如果中间元素小于要查找元素，则在中间元素的右侧去找正确元素，最左侧变为mid + 1
		      left = mid + 1;
		  } else {
		      // 如果不大不小，那么就正好是找到了，返回找到的索引
			  System.out.println("找到了，下标为" + mid);
			  flag = true;
			  break;
		  }
		}
		// 当查找范围的最左侧和最右侧重叠后还没有找到元素，则返回-1表示没有找到
		if (flag == false) {
			System.out.println("未找到");
		}
	}
}
```

