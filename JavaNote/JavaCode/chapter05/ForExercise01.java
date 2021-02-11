public class ForExercise01 {
	public static void main(String[] args) {
		// 完成输出两个数相加为5
		// 化繁为简：
		// 1. 输出 1~5
		// 2. 后面的 + 是 5 - i
		// 先死后活
		// 1. 将5 替换为变量
		int num = 5;
		for (int i = 0; i <=num ;i++ ) {
			System.out.println(i + " + " + (num-i) + " = " + num);
		}
	}
}