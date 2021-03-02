/**
 * 递归法解决八皇后问题
 */
public class QueenTest {
	// 定义静态变量
    static int max = 8; // 需要放置皇后的个数
    static int count = 0; // 统计次数

    //定义一个一位数组表示八皇后的棋盘(第n个代表第n行，值代表第n行的第m列)
    static int array[] = new int[max];

    public static void main(String []args){
    	// 调用check方法
        check(0);
        System.out.printf("总共有%d种解法\n",count);
    }

    /**
     * 放置第n个皇后
     * @param n
     * @return
     */
    public static void check(int n){
        if(n == max){ // 当n=8时，表示八皇后放置完成，打印退出
            print(array);
            return;
        }
        for(int i = 0; i < max; i++){ // 遍历每一列i
            array[n] = i; // 将第n行 放置在第i列
            if(judge(n)) { // 判断是否与之前的冲突
                check(n + 1);
            }
        }
    }
    /**
     * 判断第n个皇后是否与之前的冲突
     * 
     * 第一个条件array[i] == array[n]
     * ：因一维数组的值即代表所在行的所在列值，所以如果值相同，则代表在同一列。
     * 
	 * 第二个条件Math.abs(n-i) == Math.abs(array[n] - array[i])
	 * ：n-i表示两个皇后相差几行，array[n]-array[i]表示相差几列，如果相差行等于相差，则这两个皇后能构成一个正方形，即在同一斜线上。
     * @param n
     * @return
     */
    public static boolean judge(int n){
        for(int i = 0; i < n; i++){ // i < n：当n > 0时，开始判断之前的位置是否冲突
            if(array[i] == array[n] || Math.abs(n-i) == Math.abs(array[n] - array[i])){
                return false;
            }
        }
        return true;
    }

    /**
     * 打印数组值
     * @param array
     */
    public static void print(int array[]){
        for (int i = 0; i <max; i++) {
            System.out.print(array[i]+" ");
        }
        count ++ ;
        System.out.println();

    }
}