# diff命令

https://blog.csdn.net/gjggj/article/details/97495102

**diff用来比较两个文件**

diff分析两个文件，并输出两个文件的不同的行。

diff的输出结果表明需要对一个文件做怎样的操作之后才能与第二个文件相匹配

【或者这么理解：与第一个文件相比，第二个文件发生了那些变化】【git
diff的结果输出也是如此】。

diff并不会改变文件的内容，但是diff可以输出一个ed脚本来应用这些改变。

---

现在让我们来看一下diff是如何工作的，假设有两个文件：

```
//file1.txt
I need to buy apples.
I need to run the laundry.
I need to wash the dog.
I need to get the car detailed.

//file2.txt
I need to buy apples.
I need to do the laundry.
I need to wash the car.
I need to get the dog detailed.

我们使用diff比较他们的不同：
diff file1.txt file2.txt
```

输出如下结果：

```
    2,4c2,4
    < I need to run the laundry.
    < I need to wash the dog.

< I need to get the car detailed.
---
> I need to do the laundry.
> I need to wash the car.
> I need to get the dog detailed.
```

我们来说明一下该输出结果的含义：

要明白diff比较结果的含义，我们必须牢记一点，diff描述两个文件不同的方式是告诉我们怎么样改变第一个文件之后与第二个文件匹配。

我们看看上面的比较结果中的第一行
2,4c2,4
- 前面的数字2,4表示第一个文件中的行
- 中间有一个字母c表示需要在第一个文件上做的操作(a=add,c=change,d=delete)
- 后面的数字2,4表示第二个文件中的行。

2,4c2,4

表示：
- 第一个文件中的第[2,4]行(注意这是一个闭合区间，包括第2行和第4行)需要做出修改才能与第二个文件中的[2,4]行相匹配。
- 接下来的内容则告诉我们需要修改的地方，前面带 < 的部分表示左边文件的第[2,4]行的内容
- 而带> 的部分表示右边文件的第[2,4]行的内容
- 中间的 — 则是两个文件内容的分隔符号。