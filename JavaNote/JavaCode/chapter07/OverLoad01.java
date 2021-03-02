public class OverLoad01 {
	public static void main(String[] args) {
		MyCalculator mc = new MyCalculator();
		// System.out.println(mc.calculator(1, 9));
		// System.out.println(mc.calculator(1, 9.1));
		// System.out.println(mc.calculator(1.2, 9));
		// System.out.println(mc.calculator(1, 9, 1));
	}
}

class MyCalculator {
	public int calculator(int n1, int n2) {
		return n1 + n2;
	}
	// 构成重载
	public double calculator(int n1, double n2) {
		return n1 + n2;
	}
	// 形参名不同，不构成重载，会报错[方法重复]
	// public double calculator(int a1, int a2) {
	// 	return a1 + a2;
	// }
	// 返回类型不同，不构成重载，会报错[方法重复]
	// public void calculator(double n1, int n2) {
	// 	int res = n1 + n2;
	// }
	public int calculator(int n1, int n2, int n3) {
		return n1 + n2 + n3;
	}
}