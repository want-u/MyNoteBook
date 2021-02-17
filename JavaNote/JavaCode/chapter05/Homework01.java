public class Homework01 {
	public static void main(String[] args) {
		/*
		某人有100，000元，每经过一次路口，需要缴费，规则如下：
		1. 当现金 > 50000时，每次交 5%
		2. 当现金 <= 50000时，每次交1000
		计算该人可以经过多少次路口【while + break】
		*/
		double money = 100000;
		int count = 0;
		while (true) {

			if (money > 50000) {
				money *= 0.95;
				count++;
				System.out.println("大于50000：" + money);

			} else if (money >= 1000) {
				money -= 1000;
				count++;
				System.out.println("小于50000：" + money);

			} else {
				break;
			}
		}
		System.out.println("没钱了...：" + money);
		System.out.println(count); // 62
	}
}