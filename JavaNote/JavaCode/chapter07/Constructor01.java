public class Constructor01 {
	public static void main(String[] args) {
		Person p1 = new Person("jack", 99);
		System.out.println("p1的属性如下");
		System.out.println("name=" + p1.name);
		System.out.println("age=" + p1.age);
	}
}

// 在创建人类的对象时，就直接指定这个对象的年龄和姓名
class Person {
	String name;
	int age;

	public Person(String pName, int pAge) {
		System.out.println("构造器被调取~完成对象的属性初始化");
		name = pName;
		age = pAge;
	}
}