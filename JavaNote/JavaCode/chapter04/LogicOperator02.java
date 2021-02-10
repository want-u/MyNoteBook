public class LogicOperator02 {
	public static void main(String[] args) {
		// ||短路或 和 |逻辑或 的演示
		int age = 50;
		if (age > 20 || age < 30) {
			System.out.println("ok 100"); // 条件为true，会输出
		}
		if (age > 20 | age < 30) {
			System.out.println("ok 200"); // 条件为true，会输出
		}

		// ||短路或 和 |逻辑或 的区别
		// 1. || 短路或 ：如果第一个条件为true，则第二个条件不会判断，最终结果为false，效率高
		// 2. | 逻辑或 ：不管第一个条件是否为true，第二个条件都要判断，效率低
		int a = 4;
		int b = 9;
		if (a > 1 || ++b > 4) { // 若使用|，则++b执行 => b=10
			System.out.println("ok 300"); // 条件为true，会输出
		}
		System.out.println(b); // 9
		System.out.println((4 < 1) ^ (6 > 3)); // 
	}
}