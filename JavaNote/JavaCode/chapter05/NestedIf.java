import java.util.Scanner;
public class NestedIf {
	public static void main(String[] args) {
		/*
		参加歌手比赛，初赛成绩大于8.0进入决赛，否则淘汰
		根据性别提示进入男/女子组
		提示： 
		double score; char gender;
		接收字符： char gender = scanner.next().charAt(0);

		思路分析：
		1. 创建Scanner对象，接收用户输入
		2. 接收 成绩 double score;
		3. 使用if-else 判断初赛成绩，大于8进决赛，否则淘汰
		4. 进入决赛，接收性别char gender;使用多分支判断输出
		*/
		Scanner myScanner = new Scanner(System.in);
		System.out.println("请输入歌手的初赛成绩（小于8淘汰）：");
		double score = myScanner.nextDouble();
		if (score > 8.0) {
			//接收性别
			System.out.println("进入决赛，请输入性别分组（男/女）：");
			char gender = myScanner.next().charAt(0);
			if (gender == '男') {
				System.out.println("进入男子组");
			} else if (gender == '女') {
				System.out.println("进入女子组");
			} else {
				System.out.println("性别输入错误！");
			}
		} else {
			System.out.println("淘汰!");
		}
	}
}