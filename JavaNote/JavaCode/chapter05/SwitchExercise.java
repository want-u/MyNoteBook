import java.util.Scanner;
public class SwitchExercise {
	public static void main(String[] args) {
		// 使用switch把小写类型的char 转成大写[键盘输入]
		// 只转换 abcde，其他输出other
		Scanner myScanner = new Scanner(System.in);
		// System.out.println("输入一个字符[a-z]，输出其大写：");
		// char c1 = myScanner.next().charAt(0);
		// switch (c1) {
		// 	case 'a' :
		// 		System.out.println('A');
		// 		break;
		// 	case 'b' :
		// 		System.out.println('B');
		// 		break;
		// 	case 'c' :
		// 		System.out.println('C');
		// 		break;
		// 	case 'd' :
		// 		System.out.println('D');
		// 		break;
		// 	case 'e' :
		// 		System.out.println('E');
		// 		break;
		// 	default :
		// 		System.out.println("other");

		// 对学生成绩大于60分的，输出“合格”。低于60输出“不合格” 【提示 成绩/60】
		// 思路分析：
		// 1. 这道题，可以使用if分支完成，但要求使用switch
		// 2. 需要进行一个转换：
		//		如果成绩在[60,100], (int)(成绩/60) = 1;
		// 		如果成绩在[0,60), (int)(成绩/60) = 0;
		// double score = 8.5;
		// if (score >= 0 && score <=100) {
		// 	// 判断是否合格
		// 	switch ((int)(score / 60)) {
		// 		case 1 :
		// 			System.out.println("合格");
		// 			break;
		// 		case 0 :
		// 			System.out.println("不合格");
		// 			break;
		// 	}
		// } else {
		// 	System.out.println("成绩输入有误");
		// }

		// 用户指定月份，打印所属的季节 
		System.out.println("输入月份：");
		int month = myScanner.nextInt();
		switch (month) {
			case 3 :
			case 4 :
			case 5 :
				System.out.println("春季");
				break;
			case 6 :
			case 7 :
			case 8 :
				System.out.println("夏季");
				break;
			case 9 :
			case 10 :
			case 11 :
				System.out.println("秋季");
				break;
			case 12 :
			case 1 :
			case 2 :
				System.out.println("冬季");
			default :
				System.out.println("月份输入有误");
		}
	}
}