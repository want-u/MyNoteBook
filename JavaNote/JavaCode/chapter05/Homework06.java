public class Homework06 {
	public static void main(String[] args) {
		// 输出1~100之间的 不能被5整除的数，每5个一行
		int count = 0;
		for (int i = 1; i <= 100; i++) {
			if (i % 5 != 0) {
				System.out.print(i + "\t");
				count++;
				if (count % 5 == 0) {
					System.out.println();
				}
			}
		}
	}
}