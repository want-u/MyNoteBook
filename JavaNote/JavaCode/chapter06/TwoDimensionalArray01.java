
public class TwoDimensionalArray01 {
	public static void main(String[] args) {
		/*
		请用二维数组输出如下图形：
		0 0 0 0 0 0
		0 0 1 0 0 0
		0 2 0 3 0 0
		0 0 0 0 0 0
		*/

		//1. 从定义上看 int[][]
		//2. 可以这样理解，原来的一维数组的每一个元素是一维数组，就构成二维数组
		int[][] arr = {{0, 0, 0, 0, 0, 0}, 
					   {0, 0, 1, 0, 0, 0},
					   {0, 2, 0, 3, 0, 0}, 
					   {0, 0, 0, 0, 0, 0}};

		// 输出二维图形
		for (int i = 0; i < arr.length; i++) {
			// 遍历二维数组的每一个元素(数组)
			// 1. arr[i] 表示二维数组的第 (i+1) 个元素
			// 2. arr[i].length 得到对应的 每一个数组的长度
			for (int j = 0; j < arr[i].length; j++) {
				System.out.print(arr[i][j] + " "); // 输出一维数组
			}
			System.out.println(); // 换行
		}
	}
}