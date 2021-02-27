public class MethodDetail {
	public static void main(String[] args) {
		// 在实际工作中，方法都应有具体含义
		AA a = new AA();
		int[] res = a.getSumAndSub(1, 4);
		System.out.println("sum=" + res[0] + " sub=" + res[1]);
	}
}


class AA {
	// 一个方法最多有一个返回值 
	// [思考：如何返回多个结果 返回数组即可]
	public int[] getSumAndSub(int n1, int n2) {
		int[] resArr = {n1 + n2, n1 - n2};
		return resArr;
	}

	// 返回类型可以为任意类型，包含基本类型和引用类型 (数组，对象)
	// 如上方法，返回数组

	// 如果方法要求有**返回数据类型**，则方法体中最后的执行语句必须为 **return 值;** 
	// 而且要求返回值类型必须和return 的值类型一致或兼容
	public double f2() {
		double d1 = 1.1 * 3;
		int n = 100;
		// return d1; // ok
		return n; // ok int -> double
	}
	// 如果方法是void，则方法体中可以没有return语句
	// 或 只写 return;
	public void f3() {
		System.out.println("hello world");
		return; // 也可以不写
	}
}