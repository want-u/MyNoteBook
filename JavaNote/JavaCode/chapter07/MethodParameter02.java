public class MethodParameter02 {
	public static void main(String[] args) {
		// 1. 数组测试
		int[] arr = {1, 2, 3};
		// 创建B对象
		B b = new B();
		b.test100(arr);
		System.out.println("main arr数组 ");
		for (int i =0; i < arr.length; i++) {
			System.out.print(arr[i] + " ");
		}
		System.out.println("==========");
		// 2. 对象测试
		Person p = new Person();
		p.name = "jack";
		p.age = 10;
		b.test200(p);
		System.out.println("age=" + p.age); // 10000
		// 当test200方法中 p=null，此时输出age=10
		// 当test200方法中 p = new Person();，此时输出age=10

	}
}

class Person {
	String name;
	int age;
}

class B {
	// B类中写一个方法test200，可以接收一个Person(age,sal)对象
	// 在方法中修改该对象属性，看看原来的对象是否变化？ // 会变化
	public void test200(Person p) {
		// p.age = 10000;
		// 思考1：p=null
		// p=null;
		// 思考2：p = new Person();
		p = new Person();
		p.name = "tom";
		p.age = 999;

	}


	// B类中写一个方法test100，可以接收一个数组
	// 在方法中修改该数组，看看原来的数组是否变化？// 会变化
	public void test100(int[] arr) {
		arr[0] = 100; // 修改元素
		// 遍历数组
		System.out.println("test100 arr数组 ");
		for (int i =0; i < arr.length; i++) {
			System.out.print(arr[i] + " ");
		}
		System.out.println();
	}
}