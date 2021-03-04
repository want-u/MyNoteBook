public class ConstructorExercise {
	public static void main(String[] args) {
		// 1. 无参构造器
		Person p1 = new Person();
		System.out.println("p1 name=" + p1.name + " age=" + p1.age);
		// 2. 带pName和pAge两个参数的构造器
		Person p2 = new Person("tom", 2);
		System.out.println("p2 name=" + p2.name + " age=" + p2.age);

	}
}

// 在Person类中添加两个构造器
// 1. 无参构造器：利用构造器设置所有人的age属性初始值是18
// 2. 带pName和pAge两个参数的构造器
class Person {
	String name;
	int age;
	public Person() {
		age = 18;
	}
	public Person(String pName, int pAge) {
		name = pName;
		age = pAge;
	}
}