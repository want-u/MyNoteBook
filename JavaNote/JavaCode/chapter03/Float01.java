public class Float01 {
	public static void main(String[] args) {
		// Java的整型常量（具体值）默认为 double 型，声明 float 型常量须后加 'f' 或 'F'
		// float n1 = 1.1; // 错误: 不兼容的类型: 从double转换到float可能会有损失
		float n2 = 1.1F;
		double n3 = 1.1;
		double n4 = 1.1F;

		// 十进制形式：5.12 512.0F .512（可以省略0，但必须有小数点）
		double n5 = .123; // 0.123
		System.out.println(n5);
		// 科学计数法：5.12e2 5.12E-2（5.12e2 表示5.12 * 10^2；5.12E-2 表示5.12 * 10^-2）
		System.out.println(5.12e2); // 512.0
		System.out.println(5.12e-2); // 0.0512

		// 通常情况下，应该使用 double，它比 float 更精确
		double n6 = 2.1234567891;
		float n7 = 2.1234567891F;
		System.out.println(n6); // 2.1234567891
		System.out.println(n7); // 2.1234567

		// 浮点数的陷阱：2.7 与 8.1 / 3 比较
		double n8 = 2.7;
		double n9 = 8.1 / 3;
		System.out.println(n8); // 2.7
		System.out.println(n9); // 2.6999999999999997
		// 得出一个使用点：对运算结果时小数的进行相等判断时，要小心
		// 应该是两个数的差值的绝对值，在某个精度范围去判断
		if (n8 == n9) {
			System.out.println("假相等"); // 此判断不会输出
		}
		// 正确写法：Math.abs(n8 - n9)表示两个数的差值的绝对值
		if (Math.abs(n8 - n9) < 1e-10) {
			System.out.println("真相等"); // 此判断输出
		}

	}
}