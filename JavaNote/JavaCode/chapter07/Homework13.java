public class Homework13 {
	public static void main(String[] args) {
		Circle c = new Circle();
		PassObject p = new PassObject();
		p.printArea(c, 5);
	}
}

class Circle {
	double radius; // 半径

	public double findArea(double radius) {
		return Math.PI * radius * radius;
	}

	// 添加方法setRadius，修改对象的半径值
	public void setRadius(double radius) {
		this.radius = radius;
	}
}

class PassObject {
	public void printArea(Circle c, int times) {
		System.out.println("Radius\tArea");
		for (int i = 1; i <= times; i++) {
			// 修改c对象的半径值
			c.setRadius(i);
			System.out.println((double)i + "\t" + c.findArea(i));
		}
	}
}