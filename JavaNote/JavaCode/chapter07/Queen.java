/**
 * 递归法解决八皇后问题
 */
public class Queen {
    private final static int max = 8;
    private static int array[] = new int[max];
    private static int count = 0;
    public static void main(String []args){
        //定义一个一位数组表示八皇后的棋盘(第n个代表第n行，值代表第n行的第m列)

        check(0);
        System.out.printf("总共有%d种解法\n",count);
    }

    /**
     * 放置第n个皇后
     * @param n
     * @return
     */
    private static void check(int n){
        if(n == max){
            print(array);
            return;
        }
        for(int i=0; i<max; i++){
            array[n] = i;
            if(judge(n)){
                check(n+1);
            }
        }
    }
    /**
     * 判断第n个皇后是否与之前的冲突
     * @param n
     * @return
     */
    private static boolean judge(int n){
        for(int i=0; i<n; i++){
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