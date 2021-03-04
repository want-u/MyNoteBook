public class ConstructorDetail {
	public static void main(String[] args) {
		Person p1 = new Person("jack", 100);
		Person p2 = new Person("milan");

		Dog dog = new Dog(); // 使用的是默认无参构造器
	}
}

class Dog {
	// 如果程序员没有定义构造方法，相同会自动给类生成一个默认无参构造方法(也叫默认构造方法)
	// 比如Dog(){}，使用javap指令反编译看看
	/*
		默认构造器
		Dog(){
	
		};

		反编译：javap 类名
		cmd中运行：javap Dog
		
		Compiled from "ConstructorDetail.java"
		class Dog {
		  Dog();
		}

	*/

	// 一旦定义了自己的构造器，默认的构造器就被覆盖了，就不能再使用默认的无参构造器
	// 除非显示的定义以下，即 Dog(){};
	Dog(){};
	
	public Dog(String dName) {

	}
}

class Person {
	String name;
	int age;
	// 第一个构造器
	public Person(String pName, int pAge) {
		name = pName;
		age = pAge;
	}
	// 第二个构造器
	public Person(String pName) {
		name = pName;
	}
}