public class ArrayExercise01 {
	public static void main(String[] args) {
		// 创建一个char类型的26个元素的数组，放置'A'~'Z'
		// 使用for循环把所有元素打印出来
		// char类型： 'A' + 1 -> 'B'
		char[] chars = new char[26];
		for (int i = 0; i < chars.length; i++) {
			chars[i] = (char)('A' + i); // 'A' + i为int，需要强制转换
		}
		for (int i = 0; i < chars.length; i++) {
			System.out.print(chars[i] + " ");
		}
	}
}