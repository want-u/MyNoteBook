## Java-注意事项

Java开发注意事项和细节说明

1. Java源文件以 .java为扩展名。源文件的基本组成部分是类（ class），如本类中的 Hello类
2. Java应用程序的执行入口是main()方法。它有固定的书写格式：
public static void main(String[] args) {...}
3. Java语言严格区分大小写。
4. Java方法由一条条语句构成，每个语句以“;"结束。
5. 大括号都是成对出现的，缺一不可。【习惯，先写{}再写代码】
6. 一个源文件中最多只能有一个 public类。其它类的个数不限。
7. 如果源文件包含一个 public类，则文件名必须按该类名命名！
8. 一个源文件中最多只能有一个 public类。其它类的个数不限，也可以将main方法写在非public类中，然后指定运行非 public类，这样入口方法就是非 public的main方法

## Java-代码规范

1. 类、方法的注释，要以 javadoc的方式来写。
2. 非 Java doc的注释，往往是给代码的维护者看的，着重告述读者为什么这样写，如何修改，注意什么问题等

3. 使用tab操作，实现缩进默认整体向右边移动，时候用 shift+tab整体向左移
4. 运算符和=两边习惯性各加一个空格。比如：2 + 4 * 5 + 345 - 89
5. 源文件使用utf-8编码
6. 行宪度不要超过80字符
7. 代码编写次行风格和行尾风格（！）

![image-20210204110335165](https://gitee.com/luoxian1011/pictures/raw/master/image-20210204110335165.png)

## Java-注释

1. 单行注释

   ```
   //单行注释
   ```

2. 多行注释

   ```
   /*
   多行注释
   多行注释
   */
   ```

3. 文档注释（固定标签）

   ```
   /**
    * 文档注释
    * @author luoxian
    * @version 1.0
    */
   ```

## Java-学习方法

![image-20210204152631467](https://gitee.com/luoxian1011/pictures/raw/master/image-20210204152631467.png)

## Java-API文档

1. API （Application Programming Interface，应用程序编程接口）是Java提供的基本编程接口（Java提供的类还有相关的方法）。中文在线文档：https://www.matools.com/api/java8

2. Java语言提供了大量的基础类，因此Oracle公司也为这些基础类提供了相应的API文档，用于告诉开发者如何使用这些类，以及这些类里包含的方法

3. Java类的组织形式

   ![image-20210205150716866](https://gitee.com/luoxian1011/pictures/raw/master/image-20210205150716866.png)

4. 举例说明如何使用：ArrayList类有哪些方法：

   1. 按照：包 -> 类 -> 方法
   2. 直接索引：Math

