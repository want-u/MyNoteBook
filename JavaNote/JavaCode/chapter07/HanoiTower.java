public class HanoiTower {
	public static void main(String[] args) {
		// 汉诺塔
		Tower tower = new Tower();
		tower.move(5, 'A', 'B', 'C');
	}	
}
	
class Tower {
	//方法
	// num 表示要移动的个数
	// a，b，c 表示 A塔，B塔，C塔
	public void move(int num, char a, char b, char c) {
		// 如果只有一个盘 num = 1
		if (num == 1) {
			System.out.println(a + "->" + c);
		} else {
			// 如果右多个盘，就可以看成两个，最下面的 和 上面所有
			// 1. 先移动上面所有的盘到 b，需要借助 c
			move(num - 1, a, c, b);
			// 2. 把最下面的盘，移动到 c
			System.out.println(a + "->" + c);
			// 3. 再把b 塔的所有盘，移动到c，需要借助 a
			move(num - 1, b, a, c);
		}
	}
}