public class InverseOperator {
	public static void main(String[] args) {
		// ! 和 ^ 的演示

		// ! 非运算，取反
		System.out.println(!(2 > 1)); // false

		// ^ 异或，不同为true
		System.out.println((4 < 1) ^ (6 > 3)); // true
		System.out.println((4 >1) ^ (6 > 3)); // false
	}
}