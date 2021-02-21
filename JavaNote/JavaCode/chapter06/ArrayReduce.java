import java.util.Scanner;
public class ArrayReduce {
	public static void main(String[] args) {
		Scanner myScanner = new Scanner(System.in);

		// 数组缩减
		int[] arr = {1, 2, 3, 4, 5};

		do {
			if (arr.length == 1) {
				System.out.println("不能再缩减");
				break;
			}

			int[] arrNew = new int[arr.length - 1];

			for (int i = 0; i < arrNew.length; i++) {
				arrNew[i] = arr[i];
			}

			arr = arrNew;

			System.out.println("====数组的元素====");
			for (int i = 0; i < arr.length; i++) {
				System.out.print(arr[i] + " ");
			}

			System.out.println("是否继续 y/n");
			if (myScanner.next().charAt(0) == 'n') {
				break;
			}

		} while (true);


	}
}