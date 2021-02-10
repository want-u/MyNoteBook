public class LogicOperator01 {
	public static void main(String[] args) {
		// &&短路与 和 &逻辑与的演示
		int age = 50;
		if (age > 20 && age < 90) {
			System.out.println("ok 100"); // 条件为true，会输出
		}
		if (age > 20 & age < 90) {
			System.out.println("ok 200"); // 条件为true，会输出
		}

		// &&短路与 和 &逻辑与的区别
		// 1. &&短路与：如果第一个条件为false，则第二个条件不会判断，最终结果为false，效率高
		// 2. &逻辑与：不管第一个条件是否为false，第二个条件都要判断，效率低
		int a = 4;
		int b = 9;
		if (a < 1 && b++ < 50) { // a<1 => false; 则不会执行b++
			System.out.println("ok 300");
		}
		System.out.println("a=" + a + " b=" + b); // a=4 b=9
	}
}