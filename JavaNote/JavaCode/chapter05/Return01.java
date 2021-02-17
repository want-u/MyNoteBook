public class Return01 {
	public static void main(String[] args) {
		// return
		for (int i = 1; i <= 5; i++) {
			if (i == 3) {
				System.out.println("韩顺平教育" + i);
				return; // continue; break;
			}
			System.out.println("hello world");
		}
		System.out.println("go on ..");
	}
}
	
