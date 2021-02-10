public class TernaryOperator {
	public static void main(String[] args) {
		// 三元运算符的使用
		int a = 10, b = 99;
		// 解读：
		// 1. a > b => false，a++ 不执行
		// 2. 返回 b--; => 先赋值后自增
		// 3. result = 99; b = 98
		// int result1 = a > b ? a++ : b--;
		// System.out.println(result1); // 99

		int result2 = a < b ? a++ : b--;
		System.out.println("result2=" + result2 + " a=" + a + " b=" + b); 
		// result2=10 a=11 b=99
	}
}