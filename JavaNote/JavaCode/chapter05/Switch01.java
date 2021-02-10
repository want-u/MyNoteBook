// switch 快速入门
import java.util.Scanner;
public class Switch01 {
	public static void main(String[] args) {
		// 接收一个字符，比如：a, b, c, d, e, f, g
		// a 表示星期一，b表示星期二...
		Scanner myScanner = new Scanner(System.in);
		System.out.println("请输入一个字符（a-g）：");
		char day = myScanner.next().charAt(0);

		switch (day) {
			case 'a' :
				System.out.println("今天星期一，猴子穿新衣");
				break;
			case 'b' :
				System.out.println("今天星期二，猴子当小二");
				break;
			case 'c' :
				System.out.println("今天星期三，猴子爬雪山");
				break;
			default :
				System.out.println("猴子累了...");
		}
		System.out.println("程序继续...");
	}
}