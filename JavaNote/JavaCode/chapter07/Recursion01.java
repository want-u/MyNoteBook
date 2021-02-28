public class Recursion01 {
	public static void main(String[] args) {
		T t1 = new T();
		// 调用test方法
		t1.test(4); // n=2 n=3 n=4
		// 调用factorial
		int res = t1.factorial(5); 
		System.out.println("res=" + res); // 120
	}
}

class T {
	// 递归打印
	public void test(int n) {
		if (n > 2) {
			test(n - 1);
		}
		System.out.println("n=" + n);
	}
	// 阶乘问题
	public int factorial(int n) {
		if (n == 1) {
			return 1;
		} else {
			return factorial(n - 1) * n;
		}
	}
 }