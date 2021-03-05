public class ThisDetail {
	public static void main(String[] args) {
		T t1 = new T();
		// t1.f1();
		t1.f2();
	}
}

 class T {
 	String name = "jack";
 	int age = 100;

 	// 访问构造器语法： this(参数列表);
 	public T() {
 		// 在这里访问T(String name, int age)
 		this("xiaohei", 99);
 		System.out.println("T() ...");
 	}

 	public T(String name, int age) {
 		System.out.println("T(String name, int age)..");
 	}

 	// this 用于区分当前类的属性和局部变量
 	public void f1() {
 		System.out.println("f1()...");
 		String name = "toto";
 		System.out.println("name=" + name + " age=" + age); // toto 1000
 		System.out.println("name=" + this.name + " age=" + this.age); // jack 100
 	}

 	// 访问成员方法的语法： this.方法名(参数列表);
 	public void f2() {
 		System.out.println("f2()...");
 		// 第一种方法
 		f1();
 		// 第二种方法
 		this.f1();
 	}

 }