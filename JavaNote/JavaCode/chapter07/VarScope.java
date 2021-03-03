public class VarScope {
	public static void main(String[] args) {
		
	}
}

class Cat {
	// 全局变量：也就是属性，作用域为整个类体 Cat类：cry eat等方法使用属性
	// 属性在定义时，可以直接赋值
	int age = 10;

	// 全局变量(属性)可以不赋值，直接使用，因为有默认值
	double weight; // 默认是0.0

	public void hi() {
		// 局部变量必须赋值后，才能使用，因为没有默认值
		int num = 1;
		System.out.println("weight=" + weight);
		System.out.println("num=" + num);
	}

	public void cry() {
		// 局部变量：也就是除了属性之外的其他变量，作用域为定义它的代码块中
		// n 和 name 就是局部变量
		int n = 10;
		String name = "jack";
		System.out.println("age=" + age);
	}

	public void eat() {
		System.out.println("age=" + age);
	}
}