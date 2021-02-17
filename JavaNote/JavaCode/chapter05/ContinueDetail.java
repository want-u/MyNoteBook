public class ContinueDetail {
	public static void main(String[] args) {
		// 细节使用案例
		label1:
		for (int j = 1; j <= 2; j++) {
			label2:
			for (int i = 1; i <= 4; i ++) {
				if (i == 2) {
					// continue;
					// continue label2;
					continue label1; // 输出两组[1 ]
				}
				System.out.println("i = " + i); // 输出两组[1 3 4]
			}
		}
	}
}