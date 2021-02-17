public class Homework07 {
	public static void main(String[] args) {
		// 输出小写的a-z以及大写的Z-A
		// a~z 97~122
		// Z~A 90~65
		// for (int i = 97; i <= 122; i++) {
		// 	System.out.print((char)i);
		// }
		// for (int i = 90; i >= 65; i--) {
		// 	System.out.print((char)i);
		// }
		for (char c1 = 'a'; c1 <= 'z'; c1++) {
			System.out.print(c1);
		}
		for (char c1 = 'Z'; c1 >= 'A'; c1--) {
			System.out.print(c1);
		}
	}
}