### Homework01

```
public class Homework01 {
	//main方法
	public static void main(String[] args) {
		//输出"hello,world"
		System.out.println("hello,world");
	}
}
```

### Homework02

```
public class Homework02 {
	//main方法
	public static void main(String[] args) {
		//将个人的基本信息打印输出，转义字符的使用
		System.out.println("姓名\t性别\t籍贯\t地址\n落仙\t男\t河南\t安阳");
	}
}
```

### Homework03

> JDK，JRE，JVM的关系
>
> 1. JDK = JRE + java开发工具
> 2. JRE = JVM + 核心类库

### Homework04

> 环境变量path配置及其作用
>
> 1. 环境变量的作用是为了在dos的任意目录，可以去使用javac和java命令
> 2. 先配置 JAVA_HOME = 指向JDK安装的主目录
> 3. 编辑path环境变量，增加 %JAVA_HOME%\bin

### Homework05

> Java编写步骤
>
> 1. 编写Java的源代码
> 2. javac 编译，得到对应的 .class 字节码文件
> 3. java 运行，本质就是把 .class 加载到 jvm 运行

### Homework06

> Java编写的7个规范
>
> 1. 类、方法的注释，要以 javadoc 的方式来写。
> 2. 非 Javadoc 的注释，往往是给代码的维护者看的，着重告述读者为什么这样写，如何修改，注意什么问题等
> 3. 使用 tab 操作，实现缩进默认整体向右边移动，时候用 shift + tab 整体向左移
> 4. 运算符和 = 两边习惯性各加一个空格。比如：2 + 4 * 5 + 345 - 89
> 5. 源文件使用 utf-8 编码
> 6. 行宪度不要超过80字符
> 7. 代码编写次行风格和行尾风格（推荐！）

### Homework07

> 初学者jav易犯的错误
>
> 1. 编译或运行时，找不到 javac Hello.java，把文件名或目录找对
> 2. 主类名和文件名不一致，修改时保持一致即可
> 3. 缺少;
> 4. 拼写错误，比如 1 -> L， 0 -> O， void -> viod ，要求写代码时一定要小心 

