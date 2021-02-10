import java.util.Scanner;

public class Input {
	public static void main(String[] args) {
		// 演示接收用户的输入
		// Scanner类：表示简单的文本扫描器，在java.util包中
		// 1. 引入/导入 Scanner类所在的包
		// 2. 创建 Scanner 对象，new 创建一个对象
		// 	  myScanner 就是Scanner类的对象
		Scanner myScanner = new Scanner(System.in);
		// 3. 接收用户输入，使用相关的方法
		System.out.println("请输入名字：");
		String name = myScanner.next();
		System.out.println("请输入年龄：");
		int age = myScanner.nextInt();
		System.out.println("请输入薪水：");
		double salary = myScanner.nextDouble();
		System.out.println("======\n用户信息");
		System.out.println("姓名 " + name + " 年龄 " + age + " 薪水 " + salary);
	}
}