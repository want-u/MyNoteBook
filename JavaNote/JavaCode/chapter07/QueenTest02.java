public class QueenTest02 {
	static int max = 8;
	static int[] array = new int[max];
	static int count = 0;

	public static void main(String[] args) {
		check(0);
		System.out.println(count);
	}

	public static void check(int n) {
		if (n == max) {
			print(array);
			return;
		} else {
			for (int i = 0; i < max; i++) {
				array[n] = i;
				if (secure(n)) {
					check(n + 1);
				}
			}
		}
	}

	public static boolean secure(int n) {
		for (int i = 0; i < n; i++) {
			if (array[n] == array[i] || Math.abs(n - i) == Math.abs(array[n] - array[i])) {
				return false;
			}
		}
		return true;
	}

	public static void print(int[] array) {
		for (int i = 0; i < array.length; i++) {
			System.out.print(array[i] + " ");
		}
		count++;
		System.out.println();
	}
}

