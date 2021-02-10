public class AutoConvert01 {
	public static void main(String[] args) {
		// 自动类型转换
		int n1 = 'a'; 	// ok: char -> int
		double d1 = 80; // ok: int -> double 
		System.out.println(n1); // 输出97
		System.out.println(d1); // 输出80.0
	}
}