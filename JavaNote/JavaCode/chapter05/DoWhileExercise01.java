public class DoWhileExercise01 {
	public static void main(String[] args) {
		// 打印1~100
		// int i = 1;
		// do {
		// 	System.out.println(i);
		// 	i++;
		// } while (i <= 100);

		// 计算1~100的和
		// int i = 1;
		// int sum = 0;
		// do {
		// 	System.out.println(i);
		// 	sum += i;
		// 	i++;
		// } while (i <= 100);
		// System.out.println("sum=" + sum);

		// 统计1~200之间能被5整除但不能被3整除的个数
		int i = 1;
		int count = 0;
		do {
			if (i % 5 == 0 && i % 3 != 0) {
				// System.out.println(i);
				count++;
			}
			i++;
		} while (i <= 200);
		System.out.println("count=" + count);
	}
}