public class Test {
	int count = 9;
	public void count1() {
		count = 10;
		System.out.println("count1=" + count);
	}
	public void count2() {
		// 先输出，再自增
		System.out.println("count1=" + count++);
	}
	public static void main(String[] args) {
		// 匿名对象，只能使用一次
		new Test().count1(); // count1=10


		Test t1 = new Test();
		t1.count2(); // count1=9
		t1.count2(); // count1=10
	}
}