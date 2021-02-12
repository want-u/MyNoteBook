public class WhileExercise {
	public static void main(String[] args) {
		// 打印1~100之间所有能被3整除的数
		int i = 1;
		int end = 100;
		int t = 3;
		while (i <= end) {
			if (i % t == 0) {
				System.out.println("i=" + i);
			}
			i++;
		}

		System.out.println("==========");
		
		// 打印40~200之间所有偶数
		int j = 40;
		while (j <= 200) {
			if (j % 2 == 0) {
				System.out.println("j=" + j);
			}
			j++;
		}
	}
}