public class Homework14 {
	public static void main(String[] args) {
		Tom tom = new Tom();
		tom.game(2);
	}
}


/*
有个人 Tom 设计他的成员变量，成员方法，可以电脑猜拳
电脑每次都会随机生成0，1，2
0 表示石头 1 表示剪刀 2 表示 布
并要可以显示 Tom 的输赢次数(清单)
*/
class Tom {
	int n;

	public void game(int n) {
		// 电脑随机012
		int computer = (int)((Math.random() * 10 + 1) % 3);
		// System.out.println(computer);

		// 游戏规则
		if (n == 0 && computer == 1) {
			System.out.println("win");
		} else if (n == 1 && computer == 2) {
			System.out.println("win");
		} else if (n == 2 && computer == 0) {
			System.out.println("win");
		} else if (n == computer) {
			System.out.println("...");
		} else {
			System.out.println("lose");
		}
	}
}