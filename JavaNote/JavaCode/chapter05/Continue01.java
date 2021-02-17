public class Continue01 {
	public static void main(String[] args) {
		// continue快速入门
		int i = 1;
		while (i <= 4) {
			i++;
			if (i == 2) {
				continue;
			}
			System.out.println("i=" + i);
		}
	}
}