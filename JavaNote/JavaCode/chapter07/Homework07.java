public class Homework07 {
	public static void main(String[] args) {
		Dog dog = new Dog("小白", "黑色", 3);
		dog.show();
	}
}

// 设计一个Dog类，有名字、颜色和年龄属性
// 定义输出方法show()显示其信息。并创建对象测试
class Dog {
	String name;
	String color;
	int age;

	public Dog(String name, String color, int age) {
		this.name = name;
		this.color = color;
		this.age = age;
	}

	public void show() {
		System.out.println(name + "信息如下：");
		System.out.println("color=" + color + " age=" + age);
	}
}