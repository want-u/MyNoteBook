public class MethodExercise01 {
	public static void main(String[] args) {
		AA a = new AA();
		// 1.
		if (a.isOdd(1)) {
			System.out.println("是奇数");
		} else {
			System.out.println("是偶数");
		}
		// 2.
		a.print(4, 4, '#');

	}
}

// 编写类AA，有一个方法：判断一个数是奇数还是偶数，返回boolean
class AA {
	// 1. 方法的返回类型 boolean
	// 2. 方法的名字 isOdd
	// 3. 方法的形参 (int n)
	// 4. 方法体 判断
	public boolean isOdd(int n) {
		// if (n % 2 != 0) {
		// 	return true;
		// } else {
		// 	return false;
		// }

		// return n % 2 != 0 ? true; false;
		return n % 2 != 0;
	}
	
	/*
	根据行、列、字符打印 对应行数和列数的字符
	比如：行4，列4，字符#，则打印为：

	####
	####
	####
	####

	1. 方法返回类型void
	2. 方法名 print
	3. 方法参数(int row, int col, char c)
	4. 方法体 循环
	*/
	public void print(int row, int col, char c) {
		for (int i = 1; i <= row; i++) {
			for (int j = 1; j <= col; j++) {
				System.out.print(c);
			}
			System.out.println();
		}
	}

}