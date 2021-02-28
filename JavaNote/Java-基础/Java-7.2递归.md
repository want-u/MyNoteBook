## Java-递归

### 基本介绍

简单的说: 递归就是方法自己调用自己,每次调用时传入不同的变量.

递归有助于编程者解决复杂问题,同时可以让代码变 得简洁

### 递归作用

递归能解决什么问题？

1. 各种数学问题：8皇后问题，汉诺塔，阶乘问题，迷宫问题，球和篮子问题 [回溯算法]
2. 各种算法中也会使用到递归，比如快排，归并排序，二分查找，分治算法等
3. 将用栈解决的问题 -> 递归代码比较简洁

### 递归执行机制

递归举例:

列举两个小案例，帮助大家理解递归调用机制

```
public class Recursion01 {
	public static void main(String[] args) {
		T t1 = new T();
		// 调用test方法
		t1.test(4); // n=2 n=3 n=4
		// 调用factorial
		int res = t1.factorial(5); 
		System.out.println("res=" + res); // 120
	}
}

class T {
	// 1. 递归打印
	public void test(int n) {
		if (n > 2) {
			test(n - 1);
		}
		System.out.println("n=" + n);
	}
	// 2. 阶乘问题
	public int factorial(int n) {
		if (n == 1) {
			return 1;
		} else {
			return factorial(n - 1) * n;
		}
	}
 }
```

1. 打印问题：

![image-20210228221528294](https://gitee.com/luoxian1011/pictures/raw/master/image-20210228221528294.png)

2. 阶乘问题：

![image-20210228222809337](https://gitee.com/luoxian1011/pictures/raw/master/image-20210228222809337.png)

### 递归重要规则

1. 执行一个方法时，就创建一个新的受保护的独立空间(栈空间)
2. 方法的局部变量是独立的，不会互相影响，比如n变量
3. 如果方法中使用的是引用类型变量(如数组，对象)，就会共享该引用类型的数据
4. 递归必须向退出递归的条件逼近，否则就是无限递归，出现StackOverfolowError，死龟了:)
5. 当一个方法执行完毕，或者遇到return，就会返回，遵守谁调用，就将结果返回给谁，同时当方法执行完毕或者返回时，该方法也就执行完毕

