public class Method02 {
	public static void main(String[] args) {
		// 请遍历一个数组，输出数组的各个元素值。
		int[][] map = {{0,0,0}, {1,1,1}, {2,2,2}};

		//使用成员方法输出
		// 1. 实例化MyTools
		MyTools tool = new MyTools();
		// 2. 调用方法
		tool.printArr(map);

		tool.printArr(map);
		
		tool.printArr(map);

	}
}


class MyTools {
	public void printArr(int[][] map) {
		System.out.println("======");
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				System.out.print(map[i][j] + " ");
			}
			System.out.println();
		}
	}
}


