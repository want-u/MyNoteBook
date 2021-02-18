public class Array01 {
	public static void main(String[] args) {
		/*
		一个养鸡场有6只鸡，他们的体重分别是3kg，5kg，1kg，3.4kg，2kg，50kg。
		请问六只鸡的总体重？评价体重？

		旧思路分析
		1. 定义六个变量double，求和 得到总体重
		2. 平均体重 = 总体重 / 6
		3. 分析旧思路的问题。 6 -> 600
		4. 引出数组
		*/
		// double hen1 = 3;
		// double hen2 = 5;
		// double hen3 = 1;
		// double hen4 = 3.4;
		// double hen5 = 2;
		// double hen6 = 50;

		// double totalWeight = hen1 + hen2 + hen3 + hen4 + hen5 + hen6;
		// System.out.println("总体重=" + totalWeight);
		// System.out.println("平均体重=" + totalWeight / 6);

		// 使用数组解决
		// 1. double[] 表示double类型的数组，数组名 hens
		// 2. {3, 5, 1, 3.4, 2, 50}表示数组的值/元素，依次为第几个元素
		double[] hens = {3, 5, 1, 3.4, 2, 50};
		double totalWeight = 0;

		// 遍历数组得到数组的所有元素，使用for
		// 老韩解读：
		// 1. 可以通过hens[下标] 来访问数组的元素，下标从0开始
		//    第一个元素为hens[0]，第二个为hens[1]...
		// 2. 通过for就可以循环的访问数组的元素
		// 3. 使用一个变量totalWeight 将元素累计
		// 4. 可以通过 数组名.length 得到数组的大小/长度

		for (int i = 0; i < hens.length; i++) {
			totalWeight += hens[i];
		}

		// 输出信息
		System.out.println("总体重=" + totalWeight);
		System.out.println("平均体重=" + totalWeight / hens.length);
	}
}