public class ObjectUse {
	public static void main(String[] args) {
		// 使用OOP面向对象解决
		// 实例化一只猫[创建一只猫对象]
		// 1. new Cat() 创建一只猫
		// 2，Cat cat1 = new Cat(); 把创建的猫赋给 cat1
		// 3. cat1 就是一个对象
		Cat cat1 = new Cat();
		cat1.name = "小白";
		cat1.age = 3;
		cat1.color = "白色";
		// 创建第二只猫
		Cat cat2 = new Cat();
		cat2.name = "小花";
		cat2.age = 100;
		cat2.color = "花色";

		// 怎么使用
		System.out.println("第1只猫的信息：" 
			+ cat1.name + " " + cat1.age + " " + cat1.color);
		System.out.println("第2只猫的信息：" 
			+ cat2.name + " " + cat2.age + " " + cat2.color);
	}
}


// 使用面向对象的方式解决养猫问题
// 定义一个猫类 Cat => 自定义的数据类型
class Cat {
	// 属性
	String name; // 名字
	int age; // 年龄
	String color; // 颜色
}