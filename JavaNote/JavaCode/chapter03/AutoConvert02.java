public class AutoConvert02 {
	public static void main(String[] args) {
		// 有多种类型的数据混合运算时
		// 系统首先自动将所有数据转换成容量最大的那种数据类型，然后再进行计算
		int n1 = 10; // ok
		// float f1 = n1 + 1.1; // 错误：n1 + 1.1 => 结果类型是double
		double d1 = n1 + 1.1; // ok 
		float f2 = n1 + 1.1F; // ok

		// 当我们把精度大的数据类型赋值给精度小的数据类型时会报错
		// 反之就会进行自动类型转换
		// int n2 = 1.1; // 错误：1.1为double

		// （byte ，short）和 char 之间不会相互自动转换
		// 当把具体数值赋给byte时，首先判断该数是否在byte范围内，如果是就可以
		byte b1 = 10; // ok -128 ~ 127
		int n2 = 1;
		// byte b2 = n2; // 错误：如果是变量赋值，则直接判断类型，n2为int
		// char c1 = b1; // 错误：byte不能自动转成 char

		// byte，short，char 他们三者可以计算，在计算时首先转换为 int 类型
		byte b2 = 1;
		byte b3 = 2;
		short s1 = 1;
		// short s2 = b2 + s1; // 错误：b2 + s1 => int
		int s2 = b2 + s1; // ok
		// byte b4 = b2 + b3;  // 错误：b2 + b3 => int

		// boolean 不参与转换
		boolean pass = true;
		// int n3 = pass; // 错误

		// 自动提升原则：表达式结果的类型自动提升为 操作数中最大的类型
		byte b5 = 1;
		short s3 = 2;
		int n4 = 3;
		double n5 = 1.1;

		double n6 = b5 + s3 + n4 + n5; // 动提升为double


	}
}