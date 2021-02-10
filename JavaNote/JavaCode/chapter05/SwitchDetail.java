public class SwitchDetail {
	public static void main(String[] args) {
		// 1. 表达式数据类型，应和case后的常量类型一致
		//    或者是可以自动转成可以相互比较的类型，比如输入的是字符，而常量是int
		char c1 = 'a';
		switch (c1) {
			case 97 :
				System.out.println("aaa");
				break;
			case 'b' :
				System.out.println("bbb");
				break;
			default :
				System.out.println("...");
		}

		// 2. switch (表达式) 中的表达式的返回值必须是：
		//（byte，short，int，char，enum，String）

		// double num1 = 1.1;
		// switch (num1) { // 错误
		// 	case 1.1 :
		// 		System.out.println("aaa");
		// 		break;
		// 	case 2.2 :
		// 		System.out.println("bbb");
		// 		break;
		// 	default :
		// 		System.out.println("...");
		// }

		// 3. case子句中的值必须是常量，而不能是变量
		
		// char c2 = 'b';
		// switch (c1) {
		// 	case 'a' :
		// 		System.out.println("aaa");
		// 		break;
		// 	case c2 :
		// 		System.out.println("bbb");
		// 		break;
		// 	default :
		// 		System.out.println("...");
		// }

		// 4. default子句是可选的，当没有匹配的case时，执行default
		switch (c1) {
			case 97 :
				System.out.println("aaa");
				break;
			case 'b' :
				System.out.println("bbb");
				break;
		}
		// 5. break语句用来跳出switch，如果没有写break
		//    程序会顺序执行到switch结尾，除非遇到break;
		switch (c1) {
			case 97 :
				System.out.println("aaa");
				// break;
			case 'b' :
				System.out.println("bbb");
				// break;
			default :
				System.out.println("...");
		}
	}
}