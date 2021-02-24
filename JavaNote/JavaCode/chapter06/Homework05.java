public class Homework05 {
	public static void main(String[] args) {
		/*
		随机生成10个整数(1~100)保存到数组，并倒序打印
		求平均值、最大值和最大值的下标
		并查找里面是否有8
		随机值：(int)(Math.random() * 100 + 1)
		*/
		int[] arr = new int[10];
		double sum = 0; // 累积和求平均值

		// 随机生成10个整数(1~100)保存到数组
		for (int i = 0; i < arr.length; i++) {
			arr[i] = (int)(Math.random() * 100 + 1);
			sum += arr[i];
		}

		int max = arr[0]; // 初始化最大值
		int maxIndex = -1; // 初始化最大值索引
		int findNum = 8; // 查找的数值
		int findIndex = -1; // 查找的数值的下标

		System.out.println("====数组的情况====");
		for (int i = 0; i < arr.length; i++) {
			System.out.print(arr[i] + " ");
		}

		// 倒序打印数组
		System.out.println("\n====倒序打印数组====");
		for (int i = arr.length - 1; i >= 0; i--) {
			System.out.print(arr[i] + " ");
			// 循环完成，得到最大值和索引
			if (max < arr[i]) {
				max = arr[i];
				maxIndex = i;
			}
			// 判断是否有findNum
			if (arr[i] == findNum) {
				findIndex = i;
			}
		}

		// 输出
		System.out.println("\navg=" + (sum/arr.length));
		System.out.println("max=" + max);
		System.out.println("maxIndex=" + maxIndex);

		if (findIndex != -1) {
			System.out.println("有" + findNum + " findIndex=" + findIndex);
		} else {
			System.out.println("没有" + findNum);
		}
	}
}