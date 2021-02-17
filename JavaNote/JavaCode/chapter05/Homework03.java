public class Homework03 {
	public static void main(String[] args) {
		// 判断一个年份是否为闰年
		int year = 2000;
		if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
			System.out.println(year + "是闰年");
		} else {
			System.out.println(year + "不是闰年");
		}
	}
}