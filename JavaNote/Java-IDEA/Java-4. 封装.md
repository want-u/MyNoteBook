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

