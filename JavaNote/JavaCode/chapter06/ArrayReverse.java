public class ArrayReverse {
	public static void main(String[] args) {
		/*
		数组的反转
		arr {11, 22, 33, 44, 55, 66} ==>  {66, 55, 44, 33, 22, 11}
		
		1. 将arr[0] 和arr[5] 调换
		2. 将arr[1] 和arr[4] 调换
		3. 将arr[2] 和arr[3] 调换
		4. 一共需要调换3次 = arr.length / 2
		5. 若为奇数个，中间元素无须更改
		6. 每次交换时，对应的下标是 arr[i] 和 arr[arr.length - 1 - i]
		*/

		// 定义数组
		int[] arr = {11, 22, 33, 44, 55, 66};

		int len = arr.length; // 数组的长度
		int temp; // 声明临时变量

		// 进行数组反转
		for (int i = 0; i < len / 2; i++) {
			temp = arr[i]; // 保存arr[i]的值
			arr[i] = arr[len - 1 - i];
			arr[len - 1 - i] = temp;
		}

		// 输出数组
		for (int i = 0; i < arr.length; i++) {
			System.out.print(arr[i] + " "); // 66 55 44 33 22 11
		}
	}
}