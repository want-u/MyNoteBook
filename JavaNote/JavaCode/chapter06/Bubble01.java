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