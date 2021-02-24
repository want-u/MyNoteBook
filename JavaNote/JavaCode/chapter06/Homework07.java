public class Homework07 {
	public static void main(String[] args) {
		// 写出冒泡排序:从小到大
		int[] arr = {1, 55, 2, 9, 7};
		int temp;

		for (int i = 0; i < arr.length - 1; i++) {
			for (int j = 0; j < arr.length - 1 - i; j++) {
				// 若顺序要从大到小
				// 则条件是：arr[j] < arr[j + 1]
				if (arr[j] > arr[j + 1]) {
					temp = arr[j];
					arr[j] = arr[j + 1];
					arr[j + 1] = temp;
				}
			}
		}

		// 输出
		for (int i = 0; i < arr.length; i++) {
			System.out.print(arr[i] + " ");
		}
	}
}