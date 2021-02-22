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