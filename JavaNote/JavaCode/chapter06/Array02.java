import java.util.Scanner;

public class Array02 {
	public static void main(String[] args) {
		// 演示 数据类型 数组名[] = new 数据类型[大小] 
		// 循环输入5个成绩，保存到double数组，并输出

		// 1. 创建一个double数组，大小为5
		// (1) 第一种动态分配的方式
		// double scores[] = new double[5];
		// (2) 第一种动态分配的方式
		double scores[]; // 声明数组，这时scores 是 null
		scores = new double[5]; // 分配内存空间，可以存放数据

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