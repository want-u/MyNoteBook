# Java-4. 封装

## 1. 封装介绍

封装（encapsulation）就是把抽象出的数据[==属性==]和对数据的操作[==方法==]封装在一起，数据被保护在内部，程序的其他部分只有通过被授权的操作[==方法==]，才能对数据进行操作

比如：对电视机的操作就是典型的封装

## 2. 封装的理解和好处

1. 隐藏实现细节：方法（连接数据库）<-- 调用（传入参数...）
2. 可以对数据进行验证，保证安全合理

```
Person {name, age}
Person p = new Person();
p.name = "jack";
p.age = 1200;
```

## 3. 封装的实现步骤

三步：

1. 将属性进行私有化 private 【不能直接修改属性】

2. 提供一个公共的(public)set方法，用于对属性判断并赋值

   ```
   public void setXxx(类型 参数名) {//Xxx 表示某个属性
   	//加入数据验证的业务逻辑
   	属性 = 参数名;
   }
   ```

3. 提供一个公共的(public)get方法，用于获取属性的值

   ```
   public 数据类型 getXxx() {//权限判断，Xxx某个属性
   	return xx;
   }
   ```

   

## 4. 快速入门

看案例：

那么在Java中如何实现这种类似的控制呢？

请大家看一个小程序（com.hspedu.encap: Encapsulation01.java），不能随便看人的年龄、工资等隐私，并对设置的年龄进行合理的验证。年龄合理就设置，否则给默认

- 年龄必须在1 ~ 120
- 年龄、工资不能直接查看
- name的长度在 2 ~ 6 字符之间

```
package com.hspedu.encap;

public class Encapsulation01 {
    public static void main(String[] args) {
        Person person = new Person();
        person.setName("jack and tom");
        person.setAge(999);
        person.setSalary(99999);
        System.out.println(person.info());
    }
}

/*
不能随便看人的年龄、工资等隐私，并对设置的年龄进行合理的验证。年龄合理就设置，否则给默认

- 年龄必须在1 ~ 120
- 年龄、工资不能直接查看
- name的长度在 2 ~ 6 字符之间
 */
class Person {
    private String name;
    private int age;
    private double salary;

    //    public void setName(String name) {
//        this.name = name;
//    }
//通过快捷键 Alt + Insert 调出
    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name.length() >= 2 && name.length() <= 6) {//name的长度在 2 ~ 6 字符之间
            this.name = name;
        } else {
            System.out.println("name的长度在 2 ~ 6 字符之间, 默认为None");
            this.name = "None";
        }
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        if (age >= 1 && age <= 120) {//年龄必须在1 ~ 120
            this.age = age;
        } else {//给出默认年龄 0
            System.out.println("年龄必须在1 ~ 120, 给出默认年龄 0");
            this.age = 0;
        }
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    //返回属性信息
    public String info() {
        return "属性信息为 name=" + name + " age=" + age + " salary=" + salary;
    }
}
```

## 5. 将构造器和setXxx结合

```
    //将构造器和setXxx结合
    public Person(String name, int age, double salary) {
        this.setName(name);
        this.setAge(age);
        this.setSalary(salary);
    }
```

```
package com.hspedu.encap;

public class Encapsulation01 {
    public static void main(String[] args) {
        Person person = new Person();
        person.setName("jack and tom");
        person.setAge(999);
        person.setSalary(99999);
        System.out.println(person.info());

        //直接使用构造器指定属性
        Person smith = new Person("smith", 199, 8888);
        System.out.println(smith.info());
    }
}

/*
不能随便看人的年龄、工资等隐私，并对设置的年龄进行合理的验证。年龄合理就设置，否则给默认

- 年龄必须在1 ~ 120
- 年龄、工资不能直接查看
- name的长度在 2 ~ 6 字符之间
 */
class Person {
    private String name;
    private int age;
    private double salary;

    public Person() {
    }

//    public Person(String name, int age, double salary) {
//        this.name = name;
//        this.age = age;
//        this.salary = salary;
//    }

    //将构造器和setXxx结合
    public Person(String name, int age, double salary) {
        this.setName(name);
        this.setAge(age);
        this.setSalary(salary);
    }

    //    public void setName(String name) {
//        this.name = name;
//    }
//通过快捷键 Alt + Insert 调出
    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name.length() >= 2 && name.length() <= 6) {//name的长度在 2 ~ 6 字符之间
            this.name = name;
        } else {
            System.out.println("name的长度在 2 ~ 6 字符之间, 默认为None");
            this.name = "None";
        }
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        if (age >= 1 && age <= 120) {//年龄必须在1 ~ 120
            this.age = age;
        } else {//给出默认年龄 0
            System.out.println("年龄必须在1 ~ 120, 给出默认年龄 0");
            this.age = 0;
        }
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    //返回属性信息
    public String info() {
        return "属性信息为 name=" + name + " age=" + age + " salary=" + salary;
    }
}
```

## 课堂练习

com.hspedu.encap: AccountTest.java 和 Account.java

创建程序，在其中定义两个类：AccountTest 和 Account 体会Java的封装性

1. Account类要求具有属性：姓名（长度2/3/4位）、余额（必须>20）、密码（必须是六位），如果不满足，则给出提示信息，并给默认值
2. 通过setXxx的方法给Account的属性赋值
3. 在AccountTest中测试

提示知识点：

String name="";

int len = name.length;

```
package com.hspedu.encap;

public class Account {
    //1. Account类要求具有属性：姓名（长度2/3/4位）、余额（必须>20）、密码（必须是六位），如果不满足，则给出提示信息，并给默认值
    //2. 通过setXxx的方法给Account的属性赋值

    //为了封装，将属性设为private
    private String name;
    private double salary;
    private String password;

    //提供两个构造器
    public Account() {
    }

    public Account(String name, double salary, String password) {
        this.setName(name);
        this.setSalary(salary);
        this.setPassword(password);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        //姓名（长度2/3/4位）
        if (name.length() >=2 && name.length() <= 4) {
            this.name = name;
        } else {
            System.out.println("姓名（长度2/3/4位）,赋予默认值 Nobody");
            this.name = "Nobody";
        }
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        //余额（必须>20）
        if (salary > 20) {
            this.salary = salary;
        } else {
            System.out.println("余额（必须>20）, 赋予默认值 0");
        }
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        //密码（必须是六位）
        if (password.length() == 6) {
            this.password = password;
        } else {
            System.out.println("密码（必须是六位）,赋予默认值 000000");
        }
    }

    //显示账号信息
    public String getInfo() {
        //可以增加权限校验获取密码
        return "账号信息 name=" + name + " salary=" + salary + " password=" + password;
    }
}
```

```
package com.hspedu.encap;

public class AccountTest {
    public static void main(String[] args) {
        Account account = new Account("johnppppppppp", 9, "qwepasd");
        System.out.println(account.getInfo());
    }
}

```

