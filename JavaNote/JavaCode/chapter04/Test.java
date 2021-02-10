public class Test {
	public static void main(String[] args) {
		// 逻辑运算符练习

		// int x = 5;
		// int y = 5;
		// if (x++ == 6 & ++y == 6) {
		// 	x = 11;
		// }
		// // x++ == 6：先比较x == 6(false => 不执行x=11); 再自增x = x + 1;
		// // ++y == 6：先自增y = y + 1; 再比较y == 6;(true)
		// System.out.println("x=" + x + ",y=" + y);
		// // x=6,y=6

		// int x = 5, y = 5;
		// if (x++ == 6 && ++y == 6) {
		// 	x = 11;
		// }
		// // x++ == 6：先比较x == 6(false => 不执行x=11); 再自增x = x + 1;
		// System.out.println("x=" + x + ",y=" + y);
		// // x=6,y=5

		// int x = 5, y = 5;
		// if (x++ == 5 | ++y == 5) {
		// 	x = 11;
		// }
		// // x++ == 5：先比较x == 5(true); 再自增x = x + 1;  => 执行x=11
		// // ++y == 5：先自增y = y + 1; 再比较y == 5;(false)
		// System.out.println("x=" + x + ",y=" + y);
		// // x=11,y=6

		// int x = 5, y = 5;
		// if (x++ == 5 || ++y == 5) {
		// 	x = 11;
		// }
		// // x++ == 5：先比较x == 5(true); 再自增x = x + 1;  => 执行x=11
		// System.out.println("x=" + x + ",y=" + y);
		// // x=11,y=5

		boolean x = true, y = false;
		short z = 46;
		if ((z++ == 46) && (y = true)) z++;
		// z++ == 46 => true;  z=47
		// y = true：y赋值为true => true; 执行z++ z=48
		if ((x = false) || (++z == 49)) z++;
		// x = false => false;
		// ++z == 49：z=49 => true; 执行z++ z=50 
		System.out.println(z); // 50
	}
}