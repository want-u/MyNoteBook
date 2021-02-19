public class ArrayCopy {
	public static void main(String[] args) {
		// 将int[] arr1 = {10, 20, 30}; 拷贝到 arr2 数组，
		// 要求数据空间是独立的
		int[] arr1 = {10, 20, 30};

		// 创建新数组arr2，开辟新的数据空间
		// 大小等于arr1.length
		int[] arr2 = new int[arr1.length];

		// 遍历arr1，把每个元素拷贝到arr2对应的元素
		for (int i = 0; i < arr1.length; i++) {
			arr2[i] = arr1[i];
		}

		// 修改arr2的元素，不会影响arr1的值
		arr2[0] = 100;

		// 输出arr1
		System.out.println("arr1的元素：");
		for (int i = 0; i < arr1.length; i++) {
			System.out.println(arr1[i]); // 10, 20, 30
		}

		// 输出arr2
		System.out.println("arr2的元素：");
		for (int i = 0; i < arr2.length; i++) {
			System.out.println(arr2[i]); // 100, 20, 30
		}
	}
}