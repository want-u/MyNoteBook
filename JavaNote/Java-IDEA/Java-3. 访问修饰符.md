# Java-3. 访问修饰符

## 1. 基本介绍

Java提供四种访问控制修饰符号，用于控制方法和属性（成员变量）的访问权限（范围）：

1. 公开级别：用 public 修饰，对外公开
2. 受保护级别：用 protected 修饰，对子类和同一个包中的类公开
3. 默认级别：没有修饰符号，向同一个包的类公开
4. 私有级别：用 private 修饰，只有类本身可以访问，不对外公开

## 2. 四种访问修饰符的访问范围

| 访问级别 | 访问控制修饰符 | 同类 | 同包 | 子类 | 不同包 |
| -------- | -------------- | ---- | ---- | ---- | ------ |
| 公开     | public         | √    | √    | √    | √      |
| 受保护   | protected      | √    | √    | √    | ×      |
| 默认     | 没有修饰符     | √    | √    | ×    | ×      |
| 私有     | private        | √    | ×    | ×    | ×      |

## 3. 使用的注意事项

1. 修饰符可以用来修饰类中的属性、成员方法和类
2. 只有默认的和public才能修饰类！并且遵循上述访问权限的特点
3. 因为没有学习继承，关于在子类中的访问权限，回头再说
4. 成员方法的访问规则和属性完全一样

//com.hspedu.modifier：需要很多文件来说明（A类，Test类）

```
package com.hspedu.modifier;

public class Test {
    public static void main(String[] args) {
        A a = new A();
        a.m1();

        //在同一包中，可以访问public protected 默认 修饰属性和方法，不能访问 private 修饰的属性或方法
        System.out.println("n1=" + a.n1 + " n2=" + a.n2 + " n3=" + a.n3);
        a.m1();
        a.m2();
        a.m3();
        //a.m4(); 'm4()' 在 'com.hspedu.modifier.A' 中具有 private 访问权限
    }
}

//只有默认的和public才能修饰类！
class Tiger {}
```

```
package com.hspedu.modifier;

public class Test {
    public static void main(String[] args) {
        A a = new A();
        a.m1();

        //在同一包中，可以访问public protected 默认 修饰属性和方法，不能访问 private 修饰的属性或方法
        System.out.println("n1=" + a.n1 + " n2=" + a.n2 + " n3=" + a.n3);
        a.m1();
        a.m2();
        a.m3();
        //a.m4(); 'm4()' 在 'com.hspedu.modifier.A' 中具有 private 访问权限
    }
}
```

```
package com.hspedu.pkg;

import com.hspedu.modifier.A;

public class Test {
    public static void main(String[] args) {
        A a = new A();
        //在不同包中，可以访问public，不能访问 protected 默认 和 private 修饰属性和方法
        System.out.println("n1=" + a.n1);
        a.m1();
        //a.m2(); 'm2()' 在 'com.hspedu.modifier.A' 中具有 protected 访问权限
    }
}
```

