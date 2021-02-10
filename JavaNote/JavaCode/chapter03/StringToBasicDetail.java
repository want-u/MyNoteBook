public class StringToBasicDetail {
	public static void main(String[] args) {
		String s = "hello";
		int n1 = Integer.parseInt(s);
		System.out.println(n1);
		// Exception in thread "main" java.lang.NumberFormatException: For input string: "hello"
	}
}