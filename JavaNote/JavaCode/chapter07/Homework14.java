import java.util.Scanner;
import java.util.Random;


public class Homework14 {
	public static void main(String[] args) {
		Tom tom = new Tom();
		tom.begin();
		tom.showWiner();
	}
}


/*
有个人 Tom 设计他的成员变量，成员方法，可以电脑猜拳
电脑每次都会随机生成0，1，2
0 表示石头 1 表示剪刀 2 表示 布
并要可以显示 Tom 的输赢次数(清单)
*/
class Tom {
	int count;
	Scanner myScanner = new Scanner(System.in);

	int tomNum; // tom出的数
	int comNum; // 电脑出的数
	int result; // 输赢情况，1为赢，0为平，-1为输
	int winCount; // 累计赢的次数，大于零则赢
	// arr[0] = {1, 2, 2, 0}
	// arr[1] = {2, 2, 2, 0}
	// arr[2] = {3, 2, 2, 0}
	int[][] arr = new int[3][4];

	// 游戏规则
	public void game() {
		// 电脑随机012 :Math.random()生成[0-1)
		// int comNum = (int)((Math.random() * 10) % 3);
		Random r = new Random();
		// 生成电脑随机数
		int comNum = r.nextInt(3);
		// System.out.println(comNum);
		arr[count][2] = comNum;

		// 游戏规则
		if (tomNum == 0 && comNum == 1) {
			arr[count][3] = 1;
			winCount += 1;

		} else if (tomNum == 1 && comNum == 2) {
			arr[count][3] = 1;
			winCount += 1;

		} else if (tomNum == 2 && comNum == 0) {
			arr[count][3] = 1;
			winCount += 1;

		} else if (tomNum == comNum) {
			arr[count][3] = 0;
		} else {
			arr[count][3] = -1;
			winCount -= 1;
		}
	}

	// 开始游戏
	public void begin() {
		while (count < 3) {
			arr[count][0] = count + 1;

			System.out.print("猜拳游戏[0 表示石头 1 表示剪刀 2 表示 布]，请输入0-2：");
			// 接收tom的数字
			tomNum = myScanner.nextInt();
			// 限定数字范围为0-2
			if (tomNum >= 0 && tomNum <= 2) {
				
				arr[count][1] = tomNum;
				game();

				System.out.println("次数\ttomNum\tcomNum\t输赢");
				showArrr(arr[count]);
				System.out.println();

				count++;
			} else {
				System.out.println("输入错误...");
			}
		}
	}

	// 输出一维数组
	public void showArrr(int[] array) {
		for (int i = 0; i < array.length; i++) {
			System.out.print(array[i] + "\t");
		}
	}

	// 输出游戏清单
	public void showWiner() {
		System.out.println("游戏数据清单如下：");
		for (int i = 0; i < arr.length; i++) {
			showArrr(arr[i]);
			System.out.println();
		}
		System.out.println("game over...");
		if (winCount > 0) {
			System.out.println("win");
		} else if (winCount == 0){
			System.out.println("no winer...");
		} else {
			System.out.println("lose");
		}
	}
}