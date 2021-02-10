public class Homework03 {
	public static void main(String[] args) {
		// 保存两本书名，用 + 拼接
		String book1 = "天龙八部";
		String book2 = "笑傲江湖";
		// 字符串 + 字符串 = 字符串
		System.out.println(book1 + book2); // 天龙八部笑傲江湖

		// 保存两个性别，用 + 拼接
		char c1 = '男';
		char c2 = '女';
		// char + char = int ；char的本质是个整数
		System.out.println(c1 + c2); // 52906

		// 保存两本书的价格，用 + 拼接
		double price1 = 123.56;
		double price2 = 100.11;
		// double + double = double
		System.out.println(price1 + price2); // 223.67000000000002
	}
}