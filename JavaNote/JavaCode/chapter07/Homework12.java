public class Homework12 {
	public static void main(String[] args) {
		Employee e = new Employee("bai", '男', 18, "it", 9999.9);
	}
}

/*
创建一个Employee类，属性有(名字，性别，年龄，职位，薪水)，提供3个构造器，可以初始化

1. (名字，性别，年龄，职位，薪水)
2. (名字，性别，年龄)
3. (职位，薪水)

要求充分复用构造器
*/
class Employee {
	String name;
	char gender;
	int age;
	String job;
	double salary;

	// 职位，薪水
	public Employee(String job, double salary) {
		this.job = job;
		this.salary = salary;
	}

	// 名字，性别，年龄
	public Employee(String name, char gender, int age) {
		this.name = name;
		this.gender = gender;
		this.age = age;
	}

	// 名字，性别，年龄，职位，薪水
	public Employee(String name, char gender, int age, String job, double salary) {
		this(name, gender, age); // 复用构造器
		this.job = job;
		this.salary = salary;
	}
}