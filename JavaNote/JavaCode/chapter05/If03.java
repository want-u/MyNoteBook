import java.util.Scanner;
public class If03 {
	public static void main(String[] args) {
		/*
		输入保国同志的芝麻信用分：
		1. 信用分为100时，输出信用极好
		2. 信用分为(80,99]时，输出信用优秀
		3. 信用分为[60,80]时，输出信用一般
		4. 其他情况，输出信用不及格
		5. 键盘接收保国的芝麻信用分
		*/
		// 创建Scanner对象
		Scanner myScanner = new Scanner(System.in);
		System.out.println("输入保国同志的芝麻信用分（1-100）：");
		// 接收信用分
		int grade = myScanner.nextInt();
		// 判断是否在1-100之间
		if (grade >= 1 && grade <= 100) {
			// 判断信用级别
			if (grade == 100) {
				System.out.println("信用极好");
			} else if (grade > 80 && grade <= 99) {
				System.out.println("信用优秀");
			} else if (grade >= 60 && grade <= 80) {
				System.out.println("信用一般");
			} else {
				System.out.println("信用不及格");
			}
		} else {
		System.out.println("输入需要在1-100之间，程序结束...");
		}
	}
}