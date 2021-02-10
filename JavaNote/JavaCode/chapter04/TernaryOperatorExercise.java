public class TernaryOperatorExercise {
	public static void main(String[] args) {
		// 案例：实现三个数的最大值
		int n1 = 55;
		int n2 = 33;
		int n3 = 123;
		// 思路：
		// 1. 先得到 n1 和 n2 中的最大值，存为max1
		// 2. 再求出 max1 和 n3 的最大值
		int max1 = n1 > n2 ? n1 : n2;
		int max2 = max1 > n3 ? max1 : n3;
		System.out.println(max2);

		// 使用一句话实现
		int max3 = (n1 > n2 ? n1 : n2) > n3 ?
		 (n1 > n2 ? n1 : n2) : n3;
		System.out.println(max3);
	}
}