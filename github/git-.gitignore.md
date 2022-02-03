# git-.gitignore

https://www.cnblogs.com/yuqing6/p/6739607.html

[toc]

在使用git对项目进行版本管理的时候，我们总有一些不需要提交到版本库里的文件和文件夹，这个时候我们就需要让git自动忽略掉一下文件。

## **使用.gitignore忽略文件**

为了让git忽略指定的文件和文件夹，我们需要在项目的根目录当中创建*.gitignore*文件，使用.gitignore文件的方式很简单，在.gitignore文件当中，一行代表一条忽略规则，如果是一个带“.”这种有后缀的字符串那么git就会忽略这个文件。“*”表示的就是选中所有，如果没有“.”就表示一个文件夹。下面举个例子。

![img](https://images2015.cnblogs.com/blog/1055753/201704/1055753-20170420160942102-1144976135.png)

比如我的项目想忽略掉.*idea*这个文件夹，直接输入：

```
.idea/
```

如果想忽略掉所有的后缀为json的文件，那么就输入：

```
*.json
```

**注意：.gitignore只能忽略那些原来没有被track的文件，如果某些文件已经被纳入了版本管理中，则修改.gitignore是无效的。**

 

## **删除文件的追踪**

 *.gitignore* 文件的用途，只能作用于 *Untracked Files*，也就是那些从来没有被 Git 记录过的文件（自添加以后，从未 add 及 commit 过的文件）。

对于已经提交过文件，想要让ignore生效， 也是有办法的：

1. 使用git rm --cached从 Git 的数据库中删除对于该文件的追踪；
2. 把对应的规则写入 *.gitignore*，让忽略真正生效；
3. 提交＋推送。

只有这样做，所有的团队成员才会保持一致而不会有后遗症，也只有这样做，其他的团队成员根本不需要做额外的工作来维持对一个文件的改变忽略。

最后有一点需要注意的，`git rm --cached` 删除的是追踪状态，而不是物理文件；如果你真的是彻底不想要了，你也可以直接 `rm --> `忽略  --> 提交+推送。

举个例子， 比如要删除.idea文件下的workspace.xml

```
git rm --cached .idea/workspace.xml
```

![img](https://images2015.cnblogs.com/blog/1055753/201704/1055753-20170420162730571-474852702.png)

这样就可以删除掉workspace.xml的文件跟踪了， .*gitignore内的忽略规则就会*真正生效。

 

## **暂时忽略某个文件的修改**

开发过程中可能还会遇到这样的情况，某个文件没有修改好，但是又要提交代码， 想这次忽略这个文件，下一次提交时再去提交它。

```
git update-index --assume-unchanged
```

1.  `git update-index --assume-unchanged`，这样 Git 暂时忽略你对文件做的修改；
2. 当你的工作告一段落决定可以提交的时候，重置该标识：`git update-index --no-assume-unchanged`，于是 Git 只需要做一次更新，这是完全可以接受的了；
3. 提交并推送代码到远程库

 