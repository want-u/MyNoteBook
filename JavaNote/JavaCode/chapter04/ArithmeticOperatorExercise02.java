public class ArithmeticOperatorExercise02 {
	public static void main(String[] args) {
		/*
		1. 需求：
			假如还有59天放假，问：合xx个星期xx天
		2. 思路分析
			1)使用 int days 保存天数;
			2)一个星期7天；
			  星期数 int weeks = days / 7;
			  零几天 int leftDays = days % 7;
			3)输出
		3. 走代码
		*/
		int days = 59;
		int weeks = days / 7;
		int leftDays = days % 7;
		System.out.println(days + "天 合：" 
		+ weeks + "星期，零" + leftDays + "天"); // 59天 合：8星期，零3天

		/*
		1. 需求：
			定义一个变量保存华氏温度，华氏温度转换摄氏度温度的公式为：5/9*(华氏温度-100)，请求出华氏温度 234.6 对应的摄氏温度
		2. 思路分析：
			1)使用 double huaShi 保存华氏温度;
			2)套用数学公式
			3)输出
		*/
		double huaShi = 1234.6;
		// double sheShi = 5 / 9 * (huaShi - 100);
		// 使用数学公式时要考虑 Java的特性：5 / 9 = 0 ！
		double sheShi = 5.0 / 9 * (huaShi - 100);
		System.out.println("华氏温度" + huaShi + " 对应的摄氏温度" + sheShi);
	}
}