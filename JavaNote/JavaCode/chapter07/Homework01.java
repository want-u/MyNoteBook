public class Homework01 {
	public static void main(String[] args) {
		double[] arr = {1.1, 2.2};
		// double[] arr = null;
		// double[] arr = {};
		A01 a = new A01();
		Double res = a.max(arr);
		if (res != null) {
			System.out.println("arr的最大值=" + a.max(arr));
		} else {
			System.out.println("arr的输入有误");
		}
	}
}


// 编写类A01，定义方法max，实现求某个double数组的最大值，并返回
class A01 {
	public Double max(double[] arr) {
		// 先判断arr是否为null，再判断数组长度
		if (arr != null && arr.length > 0) {
			
			// 保证arr至少有一个元素
			double max = arr[0];
			for (int i = 1; i < arr.length; i++) {
				if (max < arr[i]) {
					max = arr[i];
				}
			}
			return max; // double
		}
		return null; // 使Double对象置空
	}
}