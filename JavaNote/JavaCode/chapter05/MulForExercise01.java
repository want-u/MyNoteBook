import java.util.Scanner;
public class MulForExercise01 {
	public static void main(String[] args) {
		/*
		案例1：
		统计3个班成绩情况，每个班有5个学生
		求出各个班的平均分和所有班级的平均分[成绩键盘输入]
		统计三个班及格的人数
		思路：
		1. 三个班：外层循环3次，i表示班级
		2. 每个班有5个学生，内层循环5次，j表示学生
		3. 创建Scanner对象，接收分数
		4. 定义各个班的总分为sum，平均分为sum / 5
		5. 定义全部班级总分为total，平均分为 total / 15
		6. 定义及格人数为passNum， score >= 60 时增加 
		*/

		
		Scanner myScanner = new Scanner(System.in); // 创建Scanner对象
		double total = 0; // 定义全部班级总分
		int passNum = 0; // 定义及格人数

		int classNum = 3; // 班级数
		int stuNum = 5; // 学生数

		// i表示班级
		for (int i = 1; i <= classNum; i++) { // 各个班的总分为sum
			
			double sum = 0;
			for (int j = 1; j <= stuNum; j++) { // j表示学生
				System.out.println("输入第" + i + "班的第" + j + "个同学的成绩：");
				double score = myScanner.nextDouble(); // 接收分数
				if (score >= 60) { // 判断是否及格
					passNum++;
				}
				System.out.println("成绩为：" + score);
				sum += score; // 统计班级总分
			}
			System.out.println(i + "班的平均分=" + (sum / stuNum));
			total += sum; // 统计全部总分
		}
		System.out.println("所有班级的平均分=" + (total / (classNum*stuNum)));
		System.out.println("及格人数=" + passNum);
	}
}