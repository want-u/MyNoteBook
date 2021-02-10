import java.util.Scanner;
public class If02 {
	public static void main(String[] args) {
		/*
		编写一个程序，可以输入人的年龄，如果大于18岁，
		则输出 "你年龄大于18，要对自己的行为负责"
		否则输出 "你的年龄不大，这次放过你了"

		思路分析：
		1. 接收输入的年龄，应该定义一个Scanner对象
		2. 把年龄保存到变量 int age
		3. 使用if-else判断，输出信息
		*/
		Scanner myScanner = new Scanner(System.in);
		System.out.println("请输入年龄:");
		int age = myScanner.nextInt();
		if (age > 18) {
			System.out.println("你年龄大于18，要对自己的行为负责");
		} else {
			System.out.println("你的年龄不大，这次放过你了");
		}
		System.out.println("程序继续...");
	}
}