public class VarParameterExercise {
	public static void main(String[] args) {
		HspMethod m = new HspMethod();
		System.out.println(m.showScore("小明", 60, 70));
		System.out.println(m.showScore("小花", 60, 70, 80));
		System.out.println(m.showScore("小加", 60, 70, 80, 90, 100));
	}
}

class HspMethod {
	// 成绩使用double接收
	// 返回字符串
	public String showScore(String name, double... scores) {
		double res = 0;
		for (int i = 0; i < scores.length; i++) {
			res += scores[i];
		}
		return name + "有" + scores.length +"门课总分=" + res;
	}
}