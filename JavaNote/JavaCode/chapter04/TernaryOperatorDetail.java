public class TernaryOperatorDetail {
	public static void main(String[] args) {
		// 三元运算符的细节
		// 表达式1 和 表达式2 要为可以赋值给接收变量的类型
		//（或可以自动转换 或强制转换）
		int a = 3;
		int b = 8;
		int c = a > b ? a : b; // ok
		// int d = a > b ? 1.1 : 2.2; // 错误：不兼容类型
		int d = a > b ? (int)1.1 : (int)2.2; // ok

		int result;
		if (a > b) result = a;
		else result = b;
	}
}