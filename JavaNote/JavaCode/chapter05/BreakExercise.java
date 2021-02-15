public class BreakExercise {
	public static void main(String[] args) {
		// 1~100以内的数求和，求出 当和第一次大于20的当前数
		// 【for+break】
		int sum = 0;
		for (int i = 1; i <= 100; i++) {
			sum += i;
			if (sum > 20) {
				System.out.println(i);
				break;
			}
		}

	}
}