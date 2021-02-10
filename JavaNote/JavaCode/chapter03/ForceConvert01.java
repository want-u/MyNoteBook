public class ForceConvert01 {
	public static void main(String[] args) {
		// 强制类型转换
		int n1 = (int)1.9;
		System.out.println(n1); // 输出1，精度丢失

		int n2 = 2000;
		byte b1 = (byte)n2;
		System.out.println(b1); // 输出-48，数据溢出
	}
}