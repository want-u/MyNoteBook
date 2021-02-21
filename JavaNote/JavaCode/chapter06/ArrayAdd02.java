import java.util.Scanner;
public class ArrayAdd02 {
	public static void main(String[] args) {
		/*
		要求：实现动态的给数组添加元素效果，实现对数组的扩容
		1. 原始数组使用静态分配 int[] arr = {1, 2, 3};
		2. 增加的元素，直接放在数组的最后 arr = {1, 2, 3, 4};
		3. 用户可以通过如下方法来决定是否添加：
		   添加成功，是否继续？ y/n

		思路分析：
		1. 定义初始数组 int[] arr = {1, 2, 3};
		2. 定义一个新数组 int[] arrNew = new int[arr.length + 1];
		3. 遍历arr数组，依次将arr的元素拷贝到arrNew
		4. 将 4 赋给 arrNew的最后一个元素
		5. 让 arr 指向 arrNew [arr原始数据会被销毁]
		6. 创建一个Scanner接收用户输入
		7. 使用do ... while 循环 + break 控制
		*/
		Scanner myScanner = new Scanner(System.in);

		// 定义数组
		int[] arr = {1, 2, 3};

		do {
			// 新建一个数组，开辟数据空间，大小 = arr.length + 1
			int[] arrNew = new int[arr.length + 1];

			// 遍历arr数组
			for (int i = 0; i < arr.length; i++) {
				arrNew[i] = arr[i];
			}

			// 接收并添加元素
			System.out.println("请输入要添加的元素：");
			arrNew[arrNew.length - 1] = myScanner.nextInt();

			// 将arr指向arrNew的数据空间
			arr = arrNew;

			// 输出数组
			System.out.println("====数组的元素====");
			for (int i = 0; i < arr.length; i++) {
				System.out.print(arr[i] + " ");
			}

			// 判断用户是否继续
			System.out.println("是否继续添加 y/n");
			char key = myScanner.next().charAt(0);
			if (key == 'n') {
				break; // 退出
			}
		} while (true);

	}
}