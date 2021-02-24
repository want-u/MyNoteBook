public class Homework04 {
	public static void main(String[] args) {
		/*
		4. 已知有个升序的数组，要求插入一个元素，该数组顺序依然是升序
		比如：[10, 12, 45, 90] 添加23 ,得到[10, 12, 23, 45, 90]

		思路：
		1. 定义一个新数组 int[] arrNew = new int[arr.length + 1];
		2. 遍历arr数组
			arr[i] < insertNum时，arrNew[i] = arr[i]，使用index记录当前i
			否则，arrNew[i + 1] = arr[i]
		3. arr[index + 1] = insertNum
		4. 测试边界：插入2和2222试试
		*/
		int[] arr = {10, 12, 45, 90};
		int insertNum = 223;
		int index = -1;

		int[] arrNew = new int[arr.length + 1];

		for (int i = 0; i < arr.length; i++) {
			if (arr[i] < insertNum) {
				arrNew[i] = arr[i];
				// 记录比insertNum小的索引
				// 循环完成后，index记录(插入值索引 - 1)的索引
				index = i;
			} else {
				arrNew[i + 1] = arr[i];
			}
		}
		arrNew[index + 1] = insertNum;
		arr = arrNew;

		System.out.println("====数组元素====");
		for (int i = 0; i < arr.length; i++) {
			System.out.print(arr[i] + " ");
		}
	}
}