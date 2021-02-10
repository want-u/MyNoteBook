## Java-环境准备

### 1. 下载JDK

- 官网：https://www.oracle.com/java/technologies/javase-downloads.html
- jdk1.8-win10-64：https://www.oracle.com/java/technologies/javase/javase-jdk8-downloads.html#license-lightbox

### 2. 安装JDK

JDK，JRE，JVM的关系

1. JDK = JRE + java开发工具
2. JRE = JVM + 核心类库

### 3. 配置环境变量


```
右键单击计算机->属性->高级系统设置，选择“环境变量”。

在“系统变量”栏下单击“新建”，创建新的系统环境变量。

点击新建：变量名"JAVA_HOME"，变量值"C:\Program Files\Java\jdk1.8.0_91"，点击确定新建；

编辑->变量名"Path"，在原变量值的最后面加上“;%JAVA_HOME%\bin;%JAVA_HOME%\jre\bin”；

使用java命令确认环境是否配置成功：
cmd：javac
cmd：java
```

### 4. 第一个程序

Java编写步骤

1. 编写Java的源代码
2. javac 编译，得到对应的 .class 字节码文件
3. java 运行，本质就是把 .class 加载到 jvm 运行


```
创建一个HelloCode文件夹，用记事本编写一个“Helloworld”小程序来验证，保存为Helloworld.java，

public class Helloworld{
    public static void main(String[] args) { 
        System.out.println("Hello Helloworld");
    }
}

先输入javac Helloworld.java进行编译，再输入java Helloworld运行。
```

### 5. Sublime配置

- 在JDK的bin目录下新建runJava.bat文件，右键选编辑，复制粘贴如下代码并保存：

```
@echo off
cd %~dp1
echo Compiling %~nx1......
if exist %~n1.class (
del %~n1.class
)
javac -encoding UTF-8 %~nx1
if exist %~n1.class (
echo ------Output------
java %~n1
)
```
- 打开Sublime Text 3
- 依次点击Tool，Build System，New Build System...
- 然后用文本编译器打开，粘贴下面的代码并保存为JavaC.sublime-build，最后关闭文件：

```

{
    "shell_cmd": "runJava.bat \"$file\"",
    "file_regex": "^(...*?):([0-9]*):?([0-9]*)",
    "selector": "source.java",
    "encoding": "GBK"
}
```
- 保存后即可在Sublime Text 3中按Ctrl+B编译Java运行文件。
