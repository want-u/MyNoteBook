public class ArithmeticOperatorExercise01 {
	public static void main(String[] args) {
		// int i = 1; // i => 1
		// （临时变量规则）
		// 1. temp=i; 
		// 2. i=i+1; 
		// 3. i = temp
		// i = i ++; 
		// System.out.println(i); // 1

		// int j = 1; // i => 1
		// j = ++j; // （临时变量规则）1. i=i+1; 2. temp=i; 3. i = temp
		// System.out.println(j); // 2

		int i1 = 10;
		int i2 = 20;
		int i = i1++; // i = i1 => 10; i1 = i1 + 1
		System.out.print("i=" + i); // 10
		System.out.println(" i2=" + i2); // 20
		i = --i2; // i2 = i2 - 1; i = i2 => 19
		System.out.print("i=" + i); // 19
		System.out.println(" i2=" + i2); // 19
	}
}