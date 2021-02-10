public class IfExercise01 {
	public static void main(String[] args) {
		// 声明2个double变量并赋值
		// 判断第一个数大于10.0，且第二个数小于20.0
		// 打印两数之和
		double d1 = 11.0;
		double d2 = 12.0;
		if (d1 > 10.0 && d2 < 20.0) {
			System.out.println(d1 + d2);
		}

		// 定义两个变量int，判断二者的和
		// 是否能被3又能被5整除，打印提示信息
		int n1 = 11;
		int n2 = 4;
		int sum = n1 + n2;
		if (sum % 3 == 0 && sum % 5 == 0) {
			System.out.println(n1 + "+" + n2 + "=" +sum + " 能被3又能被5整除");
		} else {
			System.out.println(n1 + "+" + n2 + "=" +sum + " 不能");
		}

		// 判断一个年份是否是闰年，闰年的条件是符合下面二者之一：
		// 1. 年份能被4整除，但不能被100 整除
		// 2. 能被400 整除
		int year = 2008;
		if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
			System.out.println(year + "闰年");
		} else {
			System.out.println(year + "不是闰年");
		}
	}
}