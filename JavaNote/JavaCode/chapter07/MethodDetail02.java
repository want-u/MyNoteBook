public class MethodDetail02 {
	public static void main(String[] args) {
		A a = new A();
		a.sayOk();
		a.m1();
	}
}


class A {
	// 同一个类中的方法调用：直接调用即可。比如：print(参数)
	public void print(int n) {
		System.out.println("print方法被调用 n=" + n);
	}

	public void sayOk() {
		print(10);
		System.out.println("sayOk 继续...");
	}

	// 跨类中的方法A类调用B类方法：需要通过对象名调用。
	public void m1() {
		// 创建B对象，然后调用方法
		System.out.println("m1被调用");
		B b = new B();
		b.hi();
		System.out.println("m1继续...");
	}
}

class B {
	public void hi() {
		System.out.println("hi方法被调用");
	}
}