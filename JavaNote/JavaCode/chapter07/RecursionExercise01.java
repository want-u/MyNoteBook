public class RecursionExercise01 {
	public static void main(String[] args) {
		// int n = -7;
		// T t1 = new T();
		// int res = t1.fibonacci(n);
		// if (res != -1) {
		// 	System.out.println("当n=" + n + " res=" + res);
		// } else {
		// 	System.out.println("需要输入的n>=1");
		// }
		int day = 1;
		T t1 = new T();
		int peachNum = t1.peach(day);
		if (peachNum != -1) {
			System.out.println("day=" + day + " peach=" + peachNum );
		} else {
			System.out.println("day 需要在1到10之间");
		}
	}
}


class T {
	/*
	请使用递归的方式求出斐波那契数1，1，2，3，5，8，13...
	给你一个整数n，求出第n个斐波那契数
	思路:
	1. 当n = 1 时，斐波那契数为 1
	2. 当n = 2 时，斐波那契数为 1
	3. 当n >= 3 时，斐波那契数为 前俩个数的和
	*/
	public int fibonacci(int n) {
		if (n >= 1) {
			if (n == 1 || n == 2) {
				return 1;
			} else {
				return fibonacci(n-1) + fibonacci(n-2);
			}
		} else {
			return -1;
		}

	}

	/*
	猴子吃桃问题：有一堆桃子，猴子第一天吃了其中的一半，并且多吃了一个！
	以后每天猴子都吃其中的一半，然后再多吃一个。
	当到第10天时，想再吃时(还没吃)，发现只有1个桃子。
	问题：最初共有多少个桃子？
	思路分析：
	1. day=10 ，peach=1
	2. day=9 ，peach= (day10 + 1) * 2 = 4
	...
	*/
	public int peach(int day) {
		if (day == 10) {
			return 1;
		} else if (day <= 10 && day >= 1) {
			return (peach(day + 1) + 1) * 2;
		} else {
			return -1;
		}
	} 
}