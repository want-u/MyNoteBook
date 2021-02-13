import java.util.Scanner;
public class DoWhileExercise02 {
	public static void main(String[] args) {
		// 如果李三不还钱，则老韩将一直使出五连鞭，直到李三说还钱为止
		Scanner myScanner = new Scanner(System.in);
		char answer;
		do {
			System.out.println("老韩问使出五连鞭！！！");
			System.out.println("老韩问：还钱吗？ y/n：");
			answer = myScanner.next().charAt(0);
		} while (answer != 'y');
		System.out.println("李三还钱了 ...");

	}
}