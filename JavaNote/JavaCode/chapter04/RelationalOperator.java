public class RelationalOperator {
	public static void main(String[] args) {
		// 关系运算符的使用
		// 开发中，不可以使用 a, b, a1, n1 ...无意义变量名
		int a = 9;
		int b = 8;
		System.out.println(a > b); // true
		System.out.println(a >= b); // true
		System.out.println(a <= b); // false
		System.out.println(a < b); // false
		System.out.println(a == b); // false
		System.out.println(a != b); // true
		boolean flag = a > b;
		System.out.println(flag); // true
	}
}