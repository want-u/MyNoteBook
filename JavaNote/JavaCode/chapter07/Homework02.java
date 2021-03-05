public class Homework02 {
	public static void main(String[] args) {
		String[] strs = {"tom", "jack"};
		
		A02 a = new A02();
		System.out.println("查找的index=" + a.find("tom", strs));
		System.out.println("查找的index=" + a.find("tomm", strs));
	}
}

// 编写类A02，定义方法find
// 实现查找某字符串数组中的元素查找，并返回索引
// 如果找不到，返回-1
class A02 {
	public int find(String findStr, String[] strs) {
		for (int i = 0; i < strs.length; i++) {
			if (findStr.equals(strs[i])) {
				return i;
			}
		}
		// 如果没有，就返回-1
		return -1;
	}
}