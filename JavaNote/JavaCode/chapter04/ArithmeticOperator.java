public class ArithmeticOperator {
	public static void main(String[] args) {
		// /：除法
		System.out.println(10 / 4); // 数学角度：2.5， Java：2[int精度]
		System.out.println(10.0 / 4); // Java：2.5
		double d = 10 / 4; // Java：10 / 4 = 2，2=> 2.0
		System.out.println(d); // Java：2.5

		// % 取模，取余
		// 在Java中，% 的本质：a % b = a - a / b * b
		System.out.println(10 % 3); // 1
		// -10 % 3 => -10 - (-10) / 3 * 3 => -10 - (-9) = -1
		System.out.println(-10 % 3); // -1
		System.out.println(10 % -3); // 1
		System.out.println(-10 % -3); // -1

		// ++ 自增的独立使用
		int i = 10;
		i++; // 自增，等价于：i = i + 1 => i = 11
		++i; // 自增，等价于：i = i + 1 => i = 12
		System.out.println(i);
		// ++ 自增的表达式使用
		int j = 8;
		int k = j++; // 等价于：k = j = 8; j = j + 1 = 9;
		int w = ++j; // 等价于：j = j + 1 = 10; w = j = 10;
		System.out.println("j=" + j + " k=" + k + " w=" + w); // j=10 k=8 w=10
	}
}