public class MulForExercise02 {
	public static void main(String[] args) {
		// 9 9 乘法表
		for (int i = 1; i <= 9; i++) {
			for (int j = 1; j <= i; j++) {
				System.out.print(j + " * " + i + " = " + j*i + "  ");
			}
			System.out.println();
		}
	}
}