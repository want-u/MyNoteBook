public class This01 {
	public static void main(String[] args) {
		Dog dog1 = new Dog("haha", 3);
		// 输出dog1对象的hashCode，与this一致
		System.out.println("dog1 hashCode=" + dog1.hashCode());
		Dog dog2 = new Dog("xiaohei", 2);
		System.out.println("dog2 hashCode=" + dog2.hashCode());

	}
}

class Dog {
	String name;
	int age;

	public Dog(String name, int age) { 
		// this.name 就是当前对象的属性name
		this.name = name;
		// this.age 就是当前对象的属性age
		this.age = age;
		// 输出this 的hashCode
		System.out.println("this hashCode=" + this.hashCode());
	}

	public void info() {
		System.out.println(name + "\t" + age);
	}
}