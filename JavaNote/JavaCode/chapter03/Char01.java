public class Char01 {
	public static void main(String[] args) {
		char c1 = 'a';
		char c2 = '\t';
		char c3 = '韩';
		char c4 = 97; // 说明：字符类型可以直接存放一个数字

		System.out.println(c1);
		System.out.println(c2);
		System.out.println(c3);
		System.out.println(c4); // 当输出c4时，会输出数字对应的Unicode编码的字符，即a

	}
}