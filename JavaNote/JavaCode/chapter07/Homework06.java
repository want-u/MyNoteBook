public class Homework06 {
	public static void main(String[] args) {
		// Cale c1 = new Cale(2, 10);
		Cale c1 = new Cale(2, 0);
		System.out.println("和=" + c1.sum());
		System.out.println("差=" + c1.minus());
		System.out.println("乘=" + c1.mul());
		Double res = c1.div();
		if (res != null) {
			System.out.println("除=" + c1.div());
		} else {
			System.out.println("除数不能为0");
		}
	}
}

/*
编程创建一个Cale计算类，在其中定义2个变量表示两个操作数
定义四个方法实现求和、差、乘、商
(要求除数为0要提示)并创建两个对象，分别测试
*/
class Cale {
	double n1;
	double n2;

	public Cale(double n1, double n2) {
		this.n1 = n1;
		this.n2 = n2;
	}

	public double sum() { // 和
		return n1 + n2;
	}

	public double minus() { // 差
		return n1 - n2;
	}

	public double mul() { // 乘
		return n1 * n2;
	}

	public Double div() { // 除
		if (n2 == 0) {
			return null;
		} 
		return n1 / n2;
	}
}