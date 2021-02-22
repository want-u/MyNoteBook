public class Search02 {
	public static void main(String[] args) {
		int[] arr = {1, 8, 10, 89, 99, 1000, 1234};
		int num = 99;

		// 定义变量，表示查找数组范围的最左侧，先从0索引开始
		int left = 0; 
		// 定义变量，表示查找数组范围的最右侧，先从最大索引开始
		int right = arr.length - 1;
		// 定义变量，表示查找范围的中间值
		int mid;

		boolean flag = false;
		
		while (left <= right) {
		  // 中间索引 = (左侧  + 右侧) / 2
		  // mid = (left + right) / 2; 
		  // 为了提高效率，我们可以用位运算代替除以运算
		  mid = (left + right) >> 1;
		  // System.out.println("mid=" + mid);
		  if (arr[mid] > num) {
		      //如果中间元素大于要查找元素，则在中间元素的左侧去找正确元素，最右侧变为mid - 1
		      right = mid - 1;
		  } else if (arr[mid] < num) {
		      //如果中间元素小于要查找元素，则在中间元素的右侧去找正确元素，最左侧变为mid + 1
		      left = mid + 1;
		  } else {
		      // 如果不大不小，那么就正好是找到了，返回找到的索引
			  System.out.println("找到了，下标为" + mid);
			  flag = true;
			  break;
		  }
		}
		// 当查找范围的最左侧和最右侧重叠后还没有找到元素，则返回-1表示没有找到
		if (flag == false) {
			System.out.println("未找到");
		}
	}
}