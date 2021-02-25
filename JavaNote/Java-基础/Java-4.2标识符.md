## Java-标识符

### 基本概念

1. Java对各种变量、方法和类等命名时使用的字符序列称为标识符
2. 凡是自己可以起名字的地方 都叫标识符：int num1 = 90; // num1就是标识符

### 命名规则

1. 由26个英文字母大小写，0到9，_或$ 组成
2. 数字不可以开头：[错误写法 int 3ab = 1;]
3. 不可以使用关键字和保留字，但能包含关键字和保留字
4. Java中严格区分大小写，长度无限制
5. 标识符不能包含空格：[错误写法 int a b = 1;]

### 课堂练习

判断下面的变量名是否正确：

- [x] hsp
- [x] hsp12
- [ ] 1hsp // 错误：数字不能起头
- [ ] h-s // 错误：不能有减号
- [ ] x h // 错误：不能有空格
- [x] h$4
- [ ] class // 错误：class 是关键字
- [ ] int // 错误：int 是关键字
- [ ] double // 错误：double 是关键字
- [ ] public // 错误：public 是关键字
- [ ] static // 错误：static 是关键字
- [ ] goto // 错误：goto是保留字
- [x] stu_name

### 命名规范

标识符命名规范 [更加专业]

1. 包名：多单词组成时，所有字母都小写：aaa.bbb.ccc // 如：com.hsp.crm
2. 类名、接口名：多单词组成时，所有单词的首字母大写：XxxYyyZzz [大驼峰]
   比如：TankShotGame
3. 变量名、方法名：多单词组成时，第一个单词首字母小写，第二个单词开始每个单词首字母大写：xxxYyyZzz [小驼峰，简称 驼峰法]
   比如：tankShotGame
4. 常量名：所有字母都大写。多单词时每个单词用下划线连接：XXX_YYY_ZZZ
   比如：定义一个所得税率 TAX_RATE

## Java-关键字

定义：被Java语言赋予了特殊含义，用作专门用途的字符串（单词）

特点：关键字中所有字母都为小写

![image-20210207221659303](https://gitee.com/luoxian1011/pictures/raw/master/image-20210207221659303.png)

![image-20210207221719929](https://gitee.com/luoxian1011/pictures/raw/master/image-20210207221719929.png)

## Java-保留字

现有Java版本尚未使用，但以后版本可能会作为关键字使用。

自己命名标识符时要避免使用这些保留字

byValue、cast、future、generic、inner、operator、outer、reset、var、goto、const