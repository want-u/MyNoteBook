public class ArrayReverse02 {
	public static void main(String[] args) {
		// 数组反转：逆序赋值法
		// 思路：
		// 1. 先创建一个新的数组arr2，大小 = arr.length
		// 2. 逆序遍历arr，将每个元素拷贝到arr2中（顺序拷贝）
		// 3. 建议增加一个循环变量j -> 0 -> 5

		int[] arr = {11, 22, 33, 44, 55, 66};

		// 创建一个新数组，长度与arr相同
		int[] arrNew = new int[arr.length];

		// 逆序遍历arr
		for (int i = arr.length - 1, j = 0; i >= 0; i--, j++) {
			arrNew[j] = arr[i];
		}

		// 将arr 指向arrNew 的数据空间
		// 此时arr原来的数据空间就没有变量引用，会被当做垃圾销毁
		arr = arrNew;

		// 输出数组
		for (int i = 0; i < arr.length; i++) {
			System.out.print(arr[i] + " ");
		}
	}
}