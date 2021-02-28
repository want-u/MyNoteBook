public class MethodExercise02 {
	public static void main(String[] args) {
		// 对象的拷贝
		Person p = new Person();
		p.name = "milan";
		p.age = 100;
		// 创建一个tools对象
		MyTools tools = new MyTools();
		Person p2 = tools.copyPerson(p);
		System.out.println("p name=" + p.name + " age=" + p.age);
		System.out.println("p2 name=" + p2.name + " age=" + p2.age);
		// 比较两个对象，判断是否是同一个对象
		System.out.println(p == p2); // false
	}
}

/*
	编写一个方法copyPerson，可以赋值一个Person对象，返回复制的对象。
	克隆对象：要求得到的新对象和原来的对象是两个独立的对象，只是属性相同
	思路:
	1. 方法返回类型 Person
	2. 方法名 copyPerson
	3. 方法参数(Person p)
	4. 方法体 创建一个新对象，并赋值属性
*/
class Person {
	String name;
	int age;
}


class MyTools {
	public Person copyPerson(Person p) {
		Person p2 = new Person();
		p2.name = p.name; // 把原来对象的名字赋给p2
		p2.age = p.age; // 把原来对象的年龄赋给p2
		return p2;
	}
}