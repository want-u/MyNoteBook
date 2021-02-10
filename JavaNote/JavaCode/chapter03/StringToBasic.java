public class StringToBasic {
	public static void main(String[] args) {
		// 基本数据类型转换为String类型
		int n1 = 100;
		float f1 = 1.1F;
		double d1 = 4.5;
		boolean b1 = true;
		String s1 = n1 + "";
		String s2 = f1 + "";
		String s3 = d1 + "";
		String s4 = b1 + "";
		System.out.println(s1 + " " + s2 + " " + s3 +" " + s4);

		// String类型转换为基本数据类型
		String s5 = "123";
		byte num1 = Byte.parseByte(s5);
		short num2 = Short.parseShort(s5);
		int num3 = Integer.parseInt(s5);
		long num4 = Long.parseLong(s5);
		float num5 = Float.parseFloat(s5);
		double num6 = Double.parseDouble(s5);
		boolean b = Boolean.parseBoolean("true");

		// 将字符串转换成字符char：含义时把字符串的第一个字符得到
		// s5.charAt(0)：得到s5字符串的第一个字符
		System.out.println(s5.charAt(0));

	}
}