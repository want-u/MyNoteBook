public class ArrayExercise02 {
	public static void main(String[] args) {
		/*
		请求出一个数组int[]的最大值 {4, -1, 9, 10, 23}，并得到对应下标
		
		思路分析：
		1. 定义一个int数组 int[] arr = {4, -1, 9, 10, 23};
		2. 假定 max = arr[0] 是最大值，maxIndex=0;
		3. 从下标 1 开始遍历arr，如果max < 当前元素，则重新赋值 max = 当前元素，maxIndex=当前元素下标
		4. 遍历完数组后，即可得出最大值和下标
		*/
		int[] nums = {4, -1, 9, 10, 23};
		int max = nums[0]; // 假定第一个元素是最大值
		int maxIndex = 0; // 下标
		for (int i = 1; i < nums.length; i++) {
			if (max < nums[i]) { // 如果max < 当前元素
				max = nums[i]; // 把max 设置成当前元素
				maxIndex = i;
			}
		}
		System.out.println(max);
		System.out.println(maxIndex);
	}
}