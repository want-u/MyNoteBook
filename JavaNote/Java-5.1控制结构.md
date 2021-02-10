# Java-控制结构

在程序中，程序运行的流程控制觉得程序是如何执行的，是我们必须掌握的，主要有顺序控制，分支控制和循环控制

1. 顺序控制
2. 分支控制（if，else，switch）
3. 循环控制（for，while，dowhile，多重循环）
4. break
5. continue
6. return

## 顺序控制

### 基本介绍

程序从上到下逐行的执行，中间没有任何判断和跳转

### 注意事项

Java中定义变量时采用合法的 **向前引用** ：

```
public class Test1 {
	int num1 = 12;
	int num2 = num1 + 1; // ok
}

public class Test2 {
	int num2 = num1 + 1; // 错误
    int num1 = 12; 
}
```

## if 分支控制

### 单分支

基本语法：

> if (条件表达式) {
> 	执行代码块;（可以有多条语句）
> }

说明：

- 当条件表达式为true时，就会执行{}的代码
- 当为false，就不执行
- 特别说明，如果{}只有一条语句，可以不写{}，但建议写上

![image-20210210110728080](https://gitee.com/luoxian1011/pictures/raw/master/image-20210210110728080.png)

```
import java.util.Scanner;
public class If01 {
	public static void main(String[] args) {
		/*
		编写一个程序，可以输入人的年龄，如果大于18岁，
		则输出 "你年龄大于18，要对自己的行为负责"

		思路分析：
		1. 接收输入的年龄，应该定义一个Scanner对象
		2. 把年龄保存到变量 int age
		3. 使用if判断，输出信息
		*/
		Scanner myScanner = new Scanner(System.in);
		System.out.println("请输入年龄:");
		int age = myScanner.nextInt();
		if (age > 18) {
			System.out.println("你年龄大于18，要对自己的行为负责");
		}
		System.out.println("程序继续...");
	}
}
```

### 双分支

基本语法：

> if (条件表达式) {
> 	执行代码块1;
> } else {
> 	执行代码块2;
> }

说明：

- 当条件表达式为true时，就会执行代码块1
- 否则执行代码块2
- 特别说明，如果{}只有一条语句，可以不写{}，但建议写上

![image-20210210112233806](https://gitee.com/luoxian1011/pictures/raw/master/image-20210210112233806.png)

```
import java.util.Scanner;
public class If02 {
	public static void main(String[] args) {
		/*
		编写一个程序，可以输入人的年龄，如果大于18岁，
		则输出 "你年龄大于18，要对自己的行为负责"
		否则输出 "你的年龄不大，这次放过你了"

		思路分析：
		1. 接收输入的年龄，应该定义一个Scanner对象
		2. 把年龄保存到变量 int age
		3. 使用if-else判断，输出信息
		*/
		Scanner myScanner = new Scanner(System.in);
		System.out.println("请输入年龄:");
		int age = myScanner.nextInt();
		if (age > 18) {
			System.out.println("你年龄大于18，要对自己的行为负责");
		} else {
			System.out.println("你的年龄不大，这次放过你了");
		}
		System.out.println("程序继续...");
	}
}
```

```
// 练习 IfExercise01
public class IfExercise01 {
	public static void main(String[] args) {
		// 声明2个double变量并赋值
		// 判断第一个数大于10.0，且第二个数小于20.0
		// 打印两数之和
		double d1 = 11.0;
		double d2 = 12.0;
		if (d1 > 10.0 && d2 < 20.0) {
			System.out.println(d1 + d2);
		}

		// 定义两个变量int，判断二者的和
		// 是否能被3又能被5整除，打印提示信息
		int n1 = 11;
		int n2 = 4;
		int sum = n1 + n2;
		if (sum % 3 == 0 && sum % 5 == 0) {
			System.out.println(n1 + "+" + n2 + "=" +sum + " 能被3又能被5整除");
		} else {
			System.out.println(n1 + "+" + n2 + "=" +sum + " 不能");
		}

		// 判断一个年份是否是闰年，闰年的条件是符合下面二者之一：
		// 1. 年份能被4整除，但不能被100 整除
		// 2. 能被400 整除
		int year = 2008;
		if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
			System.out.println(year + "闰年");
		} else {
			System.out.println(year + "不是闰年");
		}
	}
}
```

### 多分支

基本语法：

> if (条件表达式1) {
> 	执行代码块1;
> } else if (条件表达式2) {
> 	执行代码块2;
> }
> ......
> else {
> 	执行代码块n;
> }

说明：

1. 当条件表达式1成立时，即执行代码块1
2. 如果表达式1不成立，才去判断表达式2是否成立
3. 如果表达式2成立，就执行代码块2
4. 以此类推，如果所有的表达式都不成立，则执行else代码块
5. 注意，分支控制只能有一个执行入口
6. 多分支 可以没有else，如果所有表达式都不成立，则没有执行入口

```
// 案例演示1
import java.util.Scanner;
public class If03 {
	public static void main(String[] args) {
		/*
		输入保国同志的芝麻信用分：
		1. 信用分为100时，输出信用极好
		2. 信用分为(80,99]时，输出信用优秀
		3. 信用分为[60,80]时，输出信用一般
		4. 其他情况，输出信用不及格
		5. 键盘接收保国的芝麻信用分
		*/
		// 创建Scanner对象
		Scanner myScanner = new Scanner(System.in);
		System.out.println("输入保国同志的芝麻信用分（1-100）：");
		// 接收信用分
		int grade = myScanner.nextInt();
		// 判断是否在1-100之间
		if (grade >= 1 && grade <= 100) {
			// 判断信用级别
			if (grade == 100) {
				System.out.println("信用极好");
			} else if (grade > 80 && grade <= 99) {
				System.out.println("信用优秀");
			} else if (grade >= 60 && grade <= 80) {
				System.out.println("信用一般");
			} else {
				System.out.println("信用不及格");
			}
		} else {
		System.out.println("输入需要在1-100之间，程序结束...");
		}
	}
}
```

```
// 案例演示2
boolean b = true;
if (b == false) { // 如果写为if (b = false) => 输出c
	System.out.println("a");
} else if (b) {
	System.out.println("b");
} else if (!b) {
	System.out.println("c");
} else {
	System.out.println("d");
} // 输出b
```

### 嵌套分支

在一个分支结构中又完整的嵌套了另一个完整的分支结构，里面的分支结构称为内层分支，外面的分支结构称为外层分支。建议不要超过3层

基本语法：

> if () {
> 	if () {
>		// if - else...
> 	} else {
> 		// if - else...
> 	}
> }

```
import java.util.Scanner;
public class NestedIf {
	public static void main(String[] args) {
		/*
		参加歌手比赛，初赛成绩大于8.0进入决赛，否则淘汰
		根据性别提示进入男/女子组
		提示： 
		double score; char gender;
		接收字符： char gender = scanner.next().charAt(0);

		思路分析：
		1. 创建Scanner对象，接收用户输入
		2. 接收 成绩 double score;
		3. 使用if-else 判断初赛成绩，大于8进决赛，否则淘汰
		4. 进入决赛，接收性别char gender;使用多分支判断输出
		*/
		Scanner myScanner = new Scanner(System.in);
		System.out.println("请输入歌手的初赛成绩（小于8淘汰）：");
		double score = myScanner.nextDouble();
		if (score > 8.0) {
			//接收性别
			System.out.println("进入决赛，请输入性别分组（男/女）：");
			char gender = myScanner.next().charAt(0);
			if (gender == '男') {
				System.out.println("进入男子组");
			} else if (gender == '女') {
				System.out.println("进入女子组");
			} else {
				System.out.println("性别输入错误！");
			}
		} else {
			System.out.println("淘汰!");
		}
	}
}
```

```
import java.util.Scanner;
public class NestedIf02 {
	public static void main(String[] args) {
		// 出票系统：根据淡旺季的月份和年龄，打印票价
		// 4-10 旺季：
		//		成人（18-60）：60
		// 		儿童（<18）：半价
		// 		老人（>60）：1/3
		// 淡季：
		// 		成人：40
		// 		其他：20
		Scanner myScanner = new Scanner(System.in);
		System.out.print("请输入月份： ");
		int month = myScanner.nextInt();
		System.out.print("请输入年龄： ");
		int age = myScanner.nextInt();
		if (month >= 4 && month <= 10) {
			// 旺季
			if (age >= 18 && age <= 60) {
				System.out.print("60");
			} else if (age < 18) {
				System.out.print("30");
			} else {
				System.out.print("20");
			}
		} else {
			//  淡季
			if (age >= 18 && age <= 60) {
				System.out.print("40");
			} else {
				System.out.print("20");
			}
		}
	}
}
```

## switch 分支控制

### 基本语法

> switch (表达式) {
> 	case 常量1 : // 当...
> 	语句块1;
> 	break;
> 	case 常量2 : // 当...
> 	语句块2;
> 	break;
> 	...
> 	case 常量n : // 当...
> 	语句块n;
> 	break;
> 	default :
> 	default语句块;
> 	break;
> }

1. switch 关键字，表示switch分支
2. 表达式 对应一个值
3. case 常量1：当表达式的值等于常量1，就执行语句块1
4. break：表示退出switch，如果case匹配成功后，语句块没有break，会造成穿透，不再判断之后的常量，直接执行之后所有的语句块
5. 如果和case 常量1匹配，就执行语句块1，否则继续匹配case 常量2
6. 如果一个都没有匹配上，执行default

![image-20210210211150671](https://gitee.com/luoxian1011/pictures/raw/master/image-20210210211150671.png)

### 快速入门

```
// switch 快速入门
import java.util.Scanner;
public class Switch01 {
	public static void main(String[] args) {
		// 接收一个字符，比如：a, b, c, d, e, f, g
		// a 表示星期一，b表示星期二...
		Scanner myScanner = new Scanner(System.in);
		System.out.println("请输入一个字符（a-g）：");
		char day = myScanner.next().charAt(0);

		switch (day) {
			case 'a' :
				System.out.println("今天星期一，猴子穿新衣");
				break;
			case 'b' :
				System.out.println("今天星期二，猴子当小二");
				break;
			case 'c' :
				System.out.println("今天星期三，猴子爬雪山");
				break;
			default :
				System.out.println("猴子累了...");
		}
		System.out.println("程序继续...");
	}
}
```

### 使用细节

1. 表达式数据类型，应和case后的常量类型一致，或者是可以自动转成可以相互比较的类型，比如输入的是字符，而常量是int
2. switch (表达式) 中的表达式的返回值必须是：（byte，short，int，char，enum，String）
3. case子句中的值必须是常量，而不能是变量
4. default子句是可选的，当没有匹配的case时，执行default
5. break语句用来跳出switch，如果没有写break，程序会顺序执行到switch结尾，除非遇到break;

```
import java.util.Scanner;
public class SwitchExercise {
	public static void main(String[] args) {
		// 使用switch把小写类型的char 转成大写[键盘输入]
		// 只转换 abcde，其他输出other
		// Scanner myScanner = new Scanner(System.in);
		// System.out.println("输入一个字符[a-z]，输出其大写：");
		// char c1 = myScanner.next().charAt(0);
		// switch (c1) {
		// 	case 'a' :
		// 		System.out.println('A');
		// 		break;
		// 	case 'b' :
		// 		System.out.println('B');
		// 		break;
		// 	case 'c' :
		// 		System.out.println('C');
		// 		break;
		// 	case 'd' :
		// 		System.out.println('D');
		// 		break;
		// 	case 'e' :
		// 		System.out.println('E');
		// 		break;
		// 	default :
		// 		System.out.println("other");
	}
}
```

```
		// 对学生成绩大于60分的，输出“合格”。低于60输出“不合格” 【提示 成绩/60】
		// 思路分析：
		// 1. 这道题，可以使用if分支完成，但要求使用switch
		// 2. 需要进行一个转换：
		//		如果成绩在[60,100], (int)(成绩/60) = 1;
		// 		如果成绩在[0,60), (int)(成绩/60) = 0;
		double score = 8.5;
		if (score >= 0 && score <=100) {
			// 判断是否合格
			switch ((int)(score / 60)) {
				case 1 :
					System.out.println("合格");
					break;
				case 0 :
					System.out.println("不合格");
					break;
			}
		} else {
			System.out.println("成绩输入有误");
		}
```

```
		// 用户指定月份，打印所属的季节 
		System.out.println("输入月份：");
		int month = myScanner.nextInt();
		switch (month) {
			case 3 :
			case 4 :
			case 5 :
				System.out.println("春季");
				break;
			case 6 :
			case 7 :
			case 8 :
				System.out.println("夏季");
				break;
			case 9 :
			case 10 :
			case 11 :
				System.out.println("秋季");
				break;
			case 12 :
			case 1 :
			case 2 :
				System.out.println("冬季");
			default :
				System.out.println("月份输入有误");
		}
```

### 与if的比较

1. 如果判断的具体数值不多，而且符合byte，short，int，char，enum，String这6中类型，推荐使用switch
2. 其他情况：对区间的判断，对结果为boolean类型的判断，使用if，if的使用范围更广