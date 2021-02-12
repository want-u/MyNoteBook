public class While01 {
	public static void main(String[] args) {
		// while 循环实例

		// 输出10句："你好，韩顺平教育"

		int i = 1; // 循环变量初始化
		while (i <= 10) {
			System.out.println("你好，韩顺平教育" + i);
			i++;
		}
		System.out.println("退出while，继续...");
	}
}