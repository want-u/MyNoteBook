public class PropertiesDetail {
	public static void main(String[] args) {
		// 创建Person对象
		// p1 是对象名(对象引用)
		// new Person() 创建的对象空间(数据) 才是真正的对象
		Person p1 = new Person();

		// 对象的属性默认值
		System.out.println("这个人的信息：");
		System.out.println("name=" + p1.name + " age=" + p1.age
			+ " salary=" + p1.salary + " isPass=" + p1.isPass);
	}
		// name=null age=0 salary=0.0 isPass=false
}


class Person {
	// 定义四个属性
	String name;
	int age;
	double salary;
	boolean isPass;
}