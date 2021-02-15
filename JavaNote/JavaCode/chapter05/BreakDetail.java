public class BreakDetail {
	public static void main(String[] args) {
		// break 使用细节
		abc1:
		for (int j = 0; j < 4; j++) {
		abc2:
			for (int i = 0; i < 10; i++) {
					if (i == 2) {
						// break; // 等价于 break abc2;
						break abc1;
					}
					System.out.println("i=" + i);
				}	
		}
	}
}