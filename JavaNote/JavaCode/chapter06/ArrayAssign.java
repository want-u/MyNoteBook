public class ArrayAssign {
	public static void main(String[] args) {
		// 基本数据类型赋值，这个值就是具体的数据，而且互相不影响
		// n2 的变化，不会影响到 n1 的值
		int n1 = 10;
		int n2 = n1;
		n2 = 80;
		System.out.println("n1=" + n1); // 10
		System.out.println("n2=" + n2); // 80

		// 数组在默认情况下是引用传递，赋的值是地址
		// arr2 的变化会影响到 arr1
		int[] arr1 = {1, 2, 3};
		int[] arr2 = arr1; // 把arr1赋给arr2
		arr2[0] = 10;

		System.out.println("===arr1的元素====");
		for (int i = 0; i < arr1.length; i++) {
			System.out.println(arr1[i]);
		}
	}
}