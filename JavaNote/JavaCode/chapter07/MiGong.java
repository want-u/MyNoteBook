public class MiGong {
	public static void main(String[] args) {
		/*
		 迷宫问题
		1 1 1 1 1 1 1 
		1 0 0 0 0 0 1 
		1 0 0 0 0 0 1 
		1 1 1 0 0 0 1 
		1 0 0 0 0 0 1 
		1 0 0 0 0 0 1 
		1 0 0 0 0 0 1 
		1 1 1 1 1 1 1 
		 1. 先创建迷宫，用二维数组表示 int[][] map = new int[8][7];
		 2. 规定map数组的元素值： 0 表示 可以走 1 表示障碍物
		*/
		 int[][] map = new int[8][7];
		 // 3. 将最上面一行和最下面一行设为1
		 for (int i = 0; i < 7; i++) {
		 	map[0][i] = 1;
		 	map[7][i] = 1;
		 }
		 // 4. 将最左面一行和最右面一行设为1
 		 for (int i = 0; i < 8; i++) {
		 	map[i][0] = 1;
		 	map[i][6] = 1;
		 }
		 // 5. 设置其他障碍
		 map[3][1] = 1;
		 map[3][2] = 1;
		 map[2][2] = 1; // 测试回溯：即当右一步走死时，返回上一步继续测试下一个方向
		 // 输出迷宫
		 System.out.println("====当前迷宫情况====");
		 for (int i = 0; i < map.length; i++) {
		 	for (int j = 0; j < map[i].length; j++) {
		 		System.out.print(map[i][j] + " ");
		 	}
		 	System.out.println(); 
		 }

		 // 调用findWay
		 T t1 = new T();
		 t1.findWay(map, 1, 1);

 		 // 输出迷宫
		 System.out.println("====寻路迷宫情况====");
		 for (int i = 0; i < map.length; i++) {
		 	for (int j = 0; j < map[i].length; j++) {
		 		System.out.print(map[i][j] + " ");
		 	}
		 	System.out.println(); 
		 }
	}
}

class T {
	/*
	使用递归回溯的思想来解决老鼠出迷宫
	1. 规定迷宫数组的值
	   1：障碍物 2：可以走 3：走过且走不通
	2. 定义一个方法findWay，返回一个表示能否走通的布尔值
	3. 传入迷宫数组map，和坐标i(行)j(列)
	4.坐标初始化为1,1，终止与6,5
	5. 找路策略 下 -> 右 -> 上 -> 左
	*/
	public boolean findWay(int[][] map, int i, int j) {
		// 当坐标为6，5时，说明已经到出口
		if (map[6][5] == 2) {
			return true;
		} else {
			
			if (map[i][j] == 0) { // 当这个位置可以走时

				map[i][j] = 2; // 先将当前位置标记为2

				// 判断周围是否能够走，不能则不通
				if (findWay(map, i + 1, j)) { // 向下 i + 1
					return true;
				} else if (findWay(map, i, j + 1)) { // 向右 j + 1
					return true;
				} else if (findWay(map, i - 1, j)) { // 向上 i - 1
					return true;
				} else if (findWay(map, i, j - 1)) { // 向左 j - 1
					return true;
				} else { // 如果都为false，则表示次路不通
					map[i][j] = 3;
					return false;
				}
			} else { // 当标记为1，2，3时，表示不通
				// 1：障碍物
				// 2：已经走过的位置
				// 3. 不通的路
				return false;
			}
		}
	}
}