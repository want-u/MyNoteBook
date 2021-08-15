# Java-Maven

## 1. 安装 Maven 核心程序

### 1.1 下载地址

http://maven.apache.org/

### 1.2 检查JAVA_HOME 环境变量

Maven 是使用 Java 开发的，所以必须知道当前
系统环境中 JDK 的安装目录。

```
C:\Users\luoxian>echo %JAVA_HOME%
D:\JAVA\JDK1.8
```

### 1.3 解压 Maven 的核心程序

将 apache-maven-3.6.3-bin.zip 解压到一个非中文无空格的目录下。例如：

```
D:\JAVA\apache-maven-3.8.2
```

### 1.4 配置环境变量

|  系统变量  |             值             |
| :--------: | :------------------------: |
| MAVEN_HOME | D:\JAVA\apache-maven-3.8.2 |
|    Path    |      %MAVEN_HOME%\bin      |



### 1.5 查看 Maven 版本信息

```
C:\Users\luoxian>mvn -v
Apache Maven 3.8.2 (ea98e05a04480131370aa0c110b8c54cf726c06f)
Maven home: D:\JAVA\apache-maven-3.8.2
Java version: 1.8.0_281, vendor: Oracle Corporation, runtime: D:\JAVA\JDK1.8\jre
Default locale: zh_CN, platform encoding: GBK
OS name: "windows 10", version: "10.0", arch: "amd64", family: "windows"
```

## 2. Maven核心配置文件

### 2.1 配置本地仓库

1) Maven 的核心程序并不包含具体功能，仅负责宏观调度。具体功能由插件来完成。

Maven 核心程序会到本地仓库中查找插件。如果本地仓库中没有就会从远程中央仓库下载。此时如果不能上网则无法执行 Maven 的具体功能。为了解决这个问题，我们可以将 Maven 的本地仓库指向一个在联网情况下下载好的目录。

2) Maven默认的本地仓库：~\.m2\repository目录

3) 找到 Maven 的核心配置文件 settings.xml 文件：

```
解压目录 D:\JAVA\apache-maven-3.8.2\conf\settings.xml
```

4) 设置方式

```
  <localRepository>D:\JAVA\apache-maven-3.8.2\repository</localRepository>
```

### 2.2 配置阿里云镜像

为了下载 jar 包方便，在 Maven 的核心配置文件 settings.xml 文件的<mirrors></mirrors>标签里面配置以下标签：

```
	<mirror>
		<id>alimaven</id>
		<name>aliyun maven</name>
		<url>http://maven.aliyun.com/nexus/content/groups/public/</url>
		<mirrorOf>central</mirrorOf>
	</mirror>
```

### 2.3 配置全局编译jdk版本

```
    <profile>
      <id>jdk-1.8</id>
      <activation>
        <activeByDefault>true</activeByDefault>
        <jdk>1.8</jdk>
      </activation>
      <properties>
        <maven.compiler.source>1.8</maven.compiler.source>
        <maven.compiler.target>1.8</maven.compiler.target>
        <maven.compiler.compilerVersion>1.8</maven.compiler.compilerVersion>
      </properties>
    </profile>
```

## 3. 常常鲜，体验

### 3.1 Maven标准目录

```
src
	|--main
		|--java			源代码目录
		|--resources	资源目录
	|--test
		|--java			测试代码目录
		|--resources	测试资源目录
	|--target
		|--classes 		编译后的class文件目录
		|--test-classes 编译后的测试class文件目录
pom.xml					Maven工程配置文件
```

这是大部分Maven工程的目录结构，在这个基础上可以合理地增删目录。

pom.xml的基本要求：

```
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>org.example</groupId>
    <artifactId>test_maven</artifactId>
    <version>1.0-SNAPSHOT</version>


</project>
```

### 3.2 写个Hello.java

```
public class Hello{
	public static void main(String args[]){
		System.out.println("Hello maven!");
	}
}
```

可以再 resources文件夹下新建 db.properties配置文件，或随便帮一个文件;

```
创建一个index.html
```

### 3.3 CMD编译运行

在项目根目录D:\JAVA\IdeaProjects\test_maven>

```
1. 编译java文件
mvn compile
```

结果，并生成target目录；我们编译的class文件和resources中的配置文件都放在了一起

```
2. 执行main方法
mvn exec:java -Dexec.mainClass="Hello"
```

```
3. 执行 mvn package
观察target中已经有了我们打包好的jar包
```

### 3.4 Maven 常用命令

| 常用命令    | 中文含义 | 说明                                         |
| ----------- | -------- | -------------------------------------------- |
| mvn clean   | 清理     | 这个命令可以用来清理已经编译好的文件         |
| mvn compile | 编译     | 将 Java 代码编译成 Class 文件                |
| mvn test    | 测试     | 项目测试                                     |
| mvn package | 打包     | 根据用户的配置，将项目打成 jar 包或者 war 包 |
| mvn install | 安装     | 手动向本地仓库安装一个 jar                   |
| mvn deploy  | 上传     | 将 jar 上传到私服                            |

## 4. 在 Idea 中使用 Maven

Idea 中也自带 Maven 插件，而且我们也可以给自带的 Maven 插件进行配置，所以我们可以使用自带的 Maven，也可以使用我们安装的 Maven 核心程序

### 4.1 配置我们自己安装的 Maven

点击工具栏中的 其他设置 - 新项目的设置

![image-20210815180704872](https://gitee.com/luoxian1011/pictures/raw/master/image-20210815180704872.png)

![image-20210815174340952](https://gitee.com/luoxian1011/pictures/raw/master/image-20210815174340952.png)

点击 OK 保存即可

### 4.2 创建 Java 工程

1) 点击 File→New→Module…（如果之前没有 Project 选 Project）→Maven

![image-20210815182009579](https://gitee.com/luoxian1011/pictures/raw/master/image-20210815182009579.png)

2) 点击 Next，自定义名称

3) 确认配置后，点击 Finish 即可创建成功

4) 运行代码

等待maven依赖导入完成后，即可直接运行java程序

![image-20210815182243393](https://gitee.com/luoxian1011/pictures/raw/master/image-20210815182243393.png)

5) Maven工具栏

![image-20210815182309393](https://gitee.com/luoxian1011/pictures/raw/master/image-20210815182309393.png)

### 4.3 在 Idea 中导入 Maven 项目

下载maven示例工程：[快速入门 - 对象存储 OSS - 阿里云 (aliyun.com)](https://help.aliyun.com/document_detail/32011.html?spm=a2c4g.11186623.6.928.243d5338KAG4iv)

#### 4.3.1 单独导入项目

1) 关闭现有项目

![image-20210815183353391](https://gitee.com/luoxian1011/pictures/raw/master/image-20210815183353391.png)

2) 打开或导入

![image-20210815183434512](https://gitee.com/luoxian1011/pictures/raw/master/image-20210815183434512.png)

![image-20210815183500565](https://gitee.com/luoxian1011/pictures/raw/master/image-20210815183500565.png)

![image-20210815183524538](https://gitee.com/luoxian1011/pictures/raw/master/image-20210815183524538.png)

#### 4.3.2 导入多项目

1) 点击 Project Structure（项目结构）

![image-20210815182710132](https://gitee.com/luoxian1011/pictures/raw/master/image-20210815182710132.png)

2) 点击 Modules→➕→Import Module

![image-20210815182957211](https://gitee.com/luoxian1011/pictures/raw/master/image-20210815182957211.png)

![image-20210815183023695](https://gitee.com/luoxian1011/pictures/raw/master/image-20210815183023695.png)