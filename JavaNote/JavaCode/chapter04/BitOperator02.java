public class BitOperator02 {
	public static void main(String[] args) {
		// 1 >> 2
		// 00000001 => 00000000
		// 本质： 1 / 2 / 2 = 0
		System.out.println(1 >> 2);
		// 15 >> 2
		// 本质： 15 / 2 / 2 = 3
		System.out.println(15 >> 2);

		// 1 << 2
		// 00000001 => 00000100
		// 本质： 1 * 2 * 2 = 4
		System.out.println(1 << 2);
		// 3 << 2
		// 本质： 2 * 2 * 2 = 12
		System.out.println(3 << 2);
	}
}