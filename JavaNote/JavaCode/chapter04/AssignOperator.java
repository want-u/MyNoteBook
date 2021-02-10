public class AssignOperator {
	public static void main(String[] args) {
		// 演示赋值运算符的使用
		int n1 = 10;
		n1 += 4; // n1 = n1 + 4;
		System.out.println(n1); // 输出14;

		// 复合赋值运算符
		byte b = 3;
		b += 2; // 等价于：b = (byte)(b + 2);
		b++;	// 等价于：b = (byte)(b + 1);
	}
}