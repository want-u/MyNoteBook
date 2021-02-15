# Java-跳转控制

## break

### 基本介绍

需求：

随机生成1~100的一个数，直到生产了 97 这个数，看看一共用了几次？

提示使用 (int)(Math.random() * 100) + 1



break语句用于终止某个语句块的执行，一般使用在switch 或者循环中[for，while，do-while]

基本语法：

```
{ 	......
	break;
	......
}
```

![image-20210215215225724](https://gitee.com/luoxian1011/pictures/raw/master/image-20210215215225724.png)

### 快速入门

![image-20210215215801418](https://gitee.com/luoxian1011/pictures/raw/master/image-20210215215801418.png)

```
public class Break01 {
	// break案例
	public static void main(String[] args) {
		for (int i = 0; i < 10; i++) {
			if (i == 3) {
				break; // 当i为3时，跳出循环
			}
			System.out.println("ok" + i);
		}
		System.out.println("跳出for循环，继续...");
	}
}
```

### 使用细节

1. break 语句出现在多层嵌套的语句块中时，可以通过**标签**指明要终止的是那一层语句块

2. 标签的基本使用：

   ```
   label1: { ......
   label2:		{ ......
   label3:			{	......
   					break;
   					......
   				}
   			}
   		}
   ```

   老韩解读：

   1. break 语句可以指定退出哪层
   2. label1 是标签，名字有程序员指定
   3. break 后指定到哪个 label 就退出到哪里
   4. 在实际的开发中，尽量不使用标签，可读性差
   5. 如果没有指定 break，默认退出最近的循环体

   ```
   public class BreakDetail {
   	public static void main(String[] args) {
   		// break 使用细节
   		abc1:
   		for (int j = 0; j < 4; j++) {
   		abc2:
   			for (int i = 0; i < 10; i++) {
   					if (i == 2) {
   						// break; // 等价于 break abc2;
   						break abc1;
   					}
   					System.out.println("i=" + i);
   				}	
   		}
   	}
   }
   ```

   ```
   public class BreakExercise {
   	public static void main(String[] args) {
   		// 1~100以内的数求和，求出 当和第一次大于20的当前数
   		// 【for+break】
   		int sum = 0;
   		for (int i = 1; i <= 100; i++) {
   			sum += i;
   			if (sum > 20) {
   				System.out.println(i);
   				break;
   			}
   		}
   
   	}
   }
   ```

   ```
   import java.util.Scanner;
   public class BreakExercise02 {
   	public static void main(String[] args) {
   		// 实现登陆验证，有三次机会
   		// 如果用户名为"丁真"，密码"666"提示登陆成功
   		// 否则提示还有几次机会
   		// 使用for + break 完成
   		Scanner myScanner = new Scanner(System.in);
   		String name = "";
   		String passwd = "";
   		int chance = 3;
   
   		for (int i = 1; i <= 3; i++) {
   			System.out.println("请输入用户名：");
   			name = myScanner.next();
   			System.out.println("请输入密码：");
   			passwd = myScanner.next();
   			// 说明：字符串内容比较使用方法："name".equals
   			// System.out.println("林黛玉".equals(name)); [推荐，可以避免空指针]
   			// System.out.println(name.equals("林黛玉"));
   			if ("丁真".equals(name) && "666".equals(passwd)) {
   				System.out.println("登陆成功");
   				break;
   			} else {
   				chance--; // 登陆机会减少 1 次
   				System.out.println("失败，还有" + chance + "次机会");
   			}
   		}
   	}
   }
   ```

   