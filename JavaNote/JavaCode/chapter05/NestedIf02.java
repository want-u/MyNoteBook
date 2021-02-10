import java.util.Scanner;
public class NestedIf02 {
	public static void main(String[] args) {
		// 出票系统：根据淡旺季的月份和年龄，打印票价
		// 4-10 旺季：
		//		成人（18-60）：60
		// 		儿童（<18）：半价
		// 		老人（>60）：1/3
		// 淡季：
		// 		成人：40
		// 		其他：20
		Scanner myScanner = new Scanner(System.in);
		System.out.print("请输入月份： ");
		int month = myScanner.nextInt();
		System.out.print("请输入年龄： ");
		int age = myScanner.nextInt();
		if (month >= 4 && month <= 10) {
			// 旺季
			if (age >= 18 && age <= 60) {
				System.out.print("60");
			} else if (age < 18) {
				System.out.print("30");
			} else {
				System.out.print("20");
			}
		} else {
			//  淡季
			if (age >= 18 && age <= 60) {
				System.out.print("40");
			} else {
				System.out.print("20");
			}
		}
	}
}