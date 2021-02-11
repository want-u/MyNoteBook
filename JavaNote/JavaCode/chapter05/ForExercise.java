public class ForExercise {
	public static void main(String[] args) {
		/*
		打印1~100之间所有是9的倍数的整数，统计个数及总和
		编程思想：[化繁为简，先死后活]
		1. 化繁为简：即将复杂的需求，拆解成简单的需求，逐步完成
		2. 先死后活：先考虑固定的值，然后转成可以灵活变化的值
		思路分析
		1. 输出 1-100 的值
		2. 在输出的过程，进行过滤，只输出9的倍数 i % 9 == 0
		3. 统计个数，定义变量 int count = 0; 当条件满足时 count++;
		4. 总和，定义变量 int sum = 0; 当条件满足时 sum += i;
		*/
		int start = 1; // 开始值
		int end = 100; // 结束值
		int t = 9;     // 倍数值

		int count = 0; // 统计个数
		int sum = 0;   // 统计总和
		for (int i = start; i <= end ; i++) {
			if (i % t == 0) {
				count++;
				sum += i;
				System.out.println("i=" + i);
			}
		}
		System.out.println("count=" + count);
		System.out.println("sum=" + sum);
	}
}