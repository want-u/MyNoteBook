public class Char02 {
	public static void main(String[] args) {
		// Java中，char 的本质是一个整数，默认输出时，是Unicode码对应的字符
		// 要输出对应的数字，可以(int)字符
		char c1 = 97;
		System.out.println(c1); // 输出a

		char c2 = 'a';
		System.out.println((int)c2); // 输出'a'对应的数字

		char c3 = 38889;
		System.out.println(c3); // 输出韩

		char c4 = '韩';
		System.out.println((int)c4); // 输出韩

		// char 类型是可以进行运算的，相当于一个整数，因为它都对应有Unicode码。
		System.out.println('a' + 10); // 输出107

		//小测试
		char c5 = 'b' + 1; // 98 + 1 = 99
		System.out.println((int)c5); // 输出99
		System.out.println(c5); // 输出99 -> 对应的字符 -> 编码表Ascii -> c

	}
}