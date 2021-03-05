public class Homework04 {
	public static void main(String[] args) {
		int[] oldArr = {1, 2, 4};

		A03 a = new A03();
		int[] newArr = a.copyArr(oldArr);

		// 输出数组
		System.out.println("====复制的新数组情况====");
		for (int i = 0 ; i < newArr.length; i++) {
			System.out.print(newArr[i] + " ");
		}
	}
}

// 编写类A03，实现数组的复制copyArr
// 输入旧数组，返回一个新数组，元素和旧数组一样
class A03 {
	public int[] copyArr(int[] oldArr) {
		int[] newArr = new int[oldArr.length];
		for (int i = 0; i < oldArr.length; i++) {
			newArr[i] = oldArr[i];
		}
		return newArr;
	}
}