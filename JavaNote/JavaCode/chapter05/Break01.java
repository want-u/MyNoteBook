public class Break01 {
	// break案例
	public static void main(String[] args) {
		for (int i = 0; i < 10; i++) {
			if (i == 3) {
				break; // 当i为3时，跳出循环
			}
			System.out.println("ok" + i);
		}
		System.out.println("跳出for循环，继续...");
	}
}