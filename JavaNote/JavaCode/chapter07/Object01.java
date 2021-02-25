public class Object01 {
	public static void main(String[] args) {
		/*
		张老太养了两只猫猫:
		一只名字叫小白,今年 3 岁,白色。
		还有一只叫小花,今年 100 岁,花色。
		请编写一个程序，当用户 输入小猫的名字时，就显示该猫的名字，年龄，颜色。
		如果用户输入的小猫名错误，则显示 张老太没有这只猫猫。
		*/

		// 使用单独的变量解决 => 不利于数据的管理：将一只猫的信息拆解了
		// 第一只猫
		String cat1Name = "小白";
		int cat1Age = 3;
		String cat1Color = "白色";
		// 第二只猫
		String cat2Name = "小花";
		int cat2Age = 100;
		String cat2Color = "花色";

		// 数组 => 1. 数据类型体现不出来
		// 		   2. 只能通过下标获取，变量名和内容对应不明确
		//		   3. 不能体现猫的行为 
		String[] cat1 = {"小白", "3", "白色"};
		String[] cat2 = {"小花", "100", "花色"};
	}
}