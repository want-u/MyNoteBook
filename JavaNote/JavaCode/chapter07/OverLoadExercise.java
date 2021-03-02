public class OverLoadExercise {
	public static void main(String[] args) {
		// 调用重载方法
		Methods methods = new Methods();
		methods.m(9);
		methods.m(9, 2);
		methods.m("hello world");
		methods.m("====");
		System.out.println(methods.max(1,4));
		System.out.println(methods.max(1.1,4.4));
		System.out.println(methods.max(1.1,4.4,6));
	}
}

class Methods {
	// 1
	public void m(int n) {
		System.out.println(n * n);
	}
	public void m(int n1, int n2) {
		System.out.println(n1 * n2);
	}
	public void m(String s) {
		System.out.println(s);
	}

	// 2
	public int max(int n1, int n2) {
		return n1 > n2 ? n1 : n2;
	}
	public double max(double n1, double n2) {
		return n1 > n2 ? n1 : n2;
	}
	public double max(double n1, double n2, double n3) {
		// 先求出n1和n2的最大值
		double max1 = n1 > n2 ? n1 : n2;
		return max1 > n3 ? max1 : n3;
	}
}