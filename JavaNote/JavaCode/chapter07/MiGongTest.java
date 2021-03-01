public class MiGongTest {
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
	public boolean findWay(int[][] map, int i, int j) {
		if (map[6][5] == 2) {
			return true;
		} else {
			if (map[i][j] == 0) {
				// 可以走
				map[i][j] = 2;

				// 继续走
				if (findWay(map, i + 1, j)) {
					return true;
				} else if (findWay(map, i, j + 1)) {
					return true;
				} else if (findWay(map, i - 1, j)) {
					return true;
				} else if (findWay(map, i, j - 1)) {
					return true;
				} else {
					map[i][j] = 3;
					return false;
				}
			} else {
				// 不可以走
				return false;
			}
		}
	}
}