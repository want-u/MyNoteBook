public class VarParameter01 {
	public static void main(String[] args) {
		HspMethod m = new HspMethod();
		System.out.println(m.sum(1, 4, 7)); // 12
		System.out.println(m.sum(1, 4)); // 5
	}
}

// 要求：可以计算2个数的和，3个数的和，4，5...
class HspMethod {
	// 可以使用方法重载
	// public int sum(int n1, int n2) { // 2个数的和
	// 	return n1 + n2;
	// }
	// public int sum(int n1, int n2, int n3) { // 3个数的和
	// 	return n1 + n2 + n3;
	// }
	// public int sum(int n1, int n2, int n3, int n4) { // 4个数的和
	// 	return n1 + n2 + n3 + n4;
	// }
	// ......
	// 上面的三个方法名称相同，功能相同，参数个数不同 -> 使用可变参数优化

	// 1. int... 表示接收的是可变参数，类型为int，即可以接收0-多个int
	// 2. 使用可变参数时，可以当作数组使用，即nums当作数组
	// 3. 遍历 nums 求和
	public int sum(int... nums) {
		// System.out.println("接收的参数个数=" + nums.length);
		int res = 0;
		for (int i = 0; i < nums.length; i++) {
			res += nums[i];
		}
		return res;
	}
}