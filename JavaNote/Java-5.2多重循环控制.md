## Java-多重循环控制

### 基本介绍

1. 将一个循环放在另一个循环体内，就形成了嵌套循环。其中 for，while，do...while 均可以作为外层循环和内层循环。[建议不超过3层，可读性差]

2. 实质上，嵌套循环就是把内层循环当成外层循环的循环体。当只有内存循环的循环条件为false时，才会完全跳出内层循环，借书外层的当此循环，开始下一次的循环

3. 设外层循环的次数为m次，内层为n次，则内层循环体实际上需要执行m*n次

   for ( int i = 1; i <= 7; i++) { // 第一层循环 7

   ​	for ( int j = 1; j <= 2; j++) { // 第二层循环 2

   ​		System.out.println("ok~~");	// 7 * 2 = 14

   ​	}

   }

   ![image-20210214143946712](https://gitee.com/luoxian1011/pictures/raw/master/image-20210214143946712.png)

```
public class MulFor {
	public static void main(String[] args) {
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 3; j++ ) {
				System.out.println("i=" + i + " j=" + j);
			}
		}
	}
}
```

### 应用实例

```
import java.util.Scanner;
public class MulForExercise01 {
	public static void main(String[] args) {
		/*
		案例1：
		统计3个班成绩情况，每个班有5个学生
		求出各个班的平均分和所有班级的平均分[成绩键盘输入]
		统计三个班及格的人数
		思路：
		1. 三个班：外层循环3次，i表示班级
		2. 每个班有5个学生，内层循环5次，j表示学生
		3. 创建Scanner对象，接收分数
		4. 定义各个班的总分为sum，平均分为sum / 5
		5. 定义全部班级总分为total，平均分为 total / 15
		6. 定义及格人数为passNum， score >= 60 时增加 
		7. 优化【效率，可读性，结构】
		*/

		
		Scanner myScanner = new Scanner(System.in); // 创建Scanner对象
		double total = 0; // 定义全部班级总分
		int passNum = 0; // 定义及格人数

		int classNum = 3; // 班级数
		int stuNum = 5; // 学生数

		// i表示班级
		for (int i = 1; i <= classNum; i++) { // 各个班的总分为sum
			
			double sum = 0;
			for (int j = 1; j <= stuNum; j++) { // j表示学生
				System.out.println("输入第" + i + "班的第" + j + "个同学的成绩：");
				double score = myScanner.nextDouble(); // 接收分数
				if (score >= 60) { // 判断是否及格
					passNum++;
				}
				System.out.println("成绩为：" + score);
				sum += score; // 统计班级总分
			}
			System.out.println(i + "班的平均分=" + (sum / stuNum));
			total += sum; // 统计全部总分
		}
		System.out.println("所有班级的平均分=" + (total / (classNum*stuNum)));
		System.out.println("及格人数=" + passNum);
	}
}
```

```
// 9 9 乘法表
public class MulForExercise02 {
	public static void main(String[] args) {
		// 9 9 乘法表
		for (int i = 1; i <= 9; i++) {
			for (int j = 1; j <= i; j++) {
				System.out.print(j + " * " + i + " = " + j*i + "  ");
			}
			System.out.println();
		}
	}
}
```

