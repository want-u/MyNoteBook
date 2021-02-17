public class Homework04 {
	public static void main(String[] args) {
		// 判断一个整数是否是水仙花数，所谓水仙花数
		// 是指:一个3位数，其各个位数上数字的立方和等于其本身
		// 例如：153 = 1*1*1 + 3*3*3 + 5*5*5
		int num = 257;
		int num1 = num / 100;
		int num2 = num % 100 / 10;
		int num3 = num % 10;
		System.out.println(num1);
		System.out.println(num2);
		System.out.println(num3);

		int sum = num1 * num1 * num1 + num2 * num2 * num2 + num3 * num3 * num3;
		if (num == sum) {
			System.out.println(sum);
			System.out.println(num + "水仙花数");
		} else {
			System.out.println(num + "不是水仙花数");
		}
	}
}