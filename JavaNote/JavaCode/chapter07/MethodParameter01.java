public class MethodParameter01 {
	public static void main(String[] args) {
		int a = 10, b = 20;
		// 创建AA对象
		AA obj = new AA();
		obj.swap(a, b);
		System.out.println("main a=" + a + " b=" + b); // a=10,b=20
	}
}

class AA {
	public void swap(int a, int b) {
		System.out.println("交换前 a=" + a + " b=" + b); // a=10,b=20
		int tmp = a;
		a = b;
		b= tmp;
		System.out.println("交换后 a=" + a + " b=" + b); // a=20,b=10
	}
}