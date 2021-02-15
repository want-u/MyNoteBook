import java.util.Scanner;
public class BreakExercise02 {
	public static void main(String[] args) {
		// 实现登陆验证，有三次机会
		// 如果用户名为"丁真"，密码"666"提示登陆成功
		// 否则提示还有几次机会
		// 使用for + break 完成
		Scanner myScanner = new Scanner(System.in);
		String name = "";
		String passwd = "";
		int chance = 3;

		for (int i = 1; i <= 3; i++) {
			System.out.println("请输入用户名：");
			name = myScanner.next();
			System.out.println("请输入密码：");
			passwd = myScanner.next();
			// 说明：字符串内容比较使用方法："name".equals
			// System.out.println("林黛玉".equals(name)); [推荐，可以避免空指针]
			// System.out.println(name.equals("林黛玉"));
			if ("丁真".equals(name) && "666".equals(passwd)) {
				System.out.println("登陆成功");
				break;
			} else {
				chance--; // 登陆机会减少 1 次
				System.out.println("失败，还有" + chance + "次机会");
			}
		}
	}
}