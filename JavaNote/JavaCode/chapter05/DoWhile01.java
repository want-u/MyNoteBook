public class DoWhile01 {
	public static void main(String[] args) {
		// do while 实例
		// 输出10句："你好！"
		int i = 1;
		do {
			System.out.println("你好！" + i);
			i++;
		} while (i <= 10);
		System.out.println("退出do .. while, 程序继续");
	}
}