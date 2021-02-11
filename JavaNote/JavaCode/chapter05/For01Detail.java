public class For01Detail {
	public static void main(String[] args) {
		// for (; 循环判断条件 ;)中的初始化和变量迭代可以放到其他地方，但是两边的分号不能省略
		int i = 1;
		for (; i <= 10 ; ) {
			System.out.println("hello" + i);
			i++;
		}
		System.out.println(i); // i = 11

		// 循环初始值 和 循环变量迭代 可以有多条初始化语句，但要求类型一样，并且中间用逗号隔开
		// int count = 3;
		// for (int i = 0, j = 0; i < count; i++, j += 2) {
		// 	System.out.println("i=" + i + " j=" + j);
		// 输出
		// i=0 j=0
		// i=1 j=2
		// i=2 j=4
		// }

		// 无限循环，可以配合break使用
		for (; ; ) {
			System.out.println(i);
			i++;
		}
	}
}