# git-项目同时推送至github和gitee

https://www.jianshu.com/p/747e2bb71775

[toc]

## 前言

github用来管理版本存储代码很是合适，但是“墙”的存在常常让开发者变得抓狂，于是国内推出了gitee，也就是码云，操作习惯和git没有任何不同，是不是很爽？

那么，git能否支持一个项目同时推送到gitee和github呢？
 答案是YES
 

```shell
❯ sudo cat .git/config

[core]
        repositoryformatversion = 0
        filemode = true
        bare = false
        logallrefupdates = true
[remote "origin"]
        url = git@gitee.com:luoxian1011/let.git
        url = git@github.com:want-u/let.git
        fetch = +refs/heads/*:refs/remotes/origin/*
[branch "master"]
        remote = origin
        merge = refs/heads/master
```
```shell
❯ sudo cat .gitignore

elk/elasticsearch/logs/
elk/elasticsearch/data/

```


## 配置1

打开每个项目的.git文件夹下的config文件（windows请务必打开隐藏文件展示，打开姿势自行百度）

而后我们在文件中新增以下的配置



```ruby
[remote "gitee"]
    url = 你的gitee项目地址        
    fetch = +refs/heads/*:refs/remotes/gitee/*
    tagopt = --no-tags
```

保存完成。

或者命令行

```csharp
 git remote add gitee  你的gitee项目地址 
```

此时我们执行命令

```undefined
git remote 
```

即可看到两个仓库

需要注意，此时推送代码时需要对两个仓库分别执行一次push命令

```undefined
git push gitRepo
git push giteeRepo
```

那么有没有一种方式可以一次push同时推送多个仓库？请往下看。



## 配置2

我们可以更改.git下的config文件为

```ruby
[remote "origin"]
    url = 原有的git项目地址
    fetch = +refs/heads/*:refs/remotes/origin/*
    url = 新增的gitee项目地址
```

保存即可。

或者git命令行:

```csharp
git remote set-url --add origin 你的gitee项目地址
```

此时，只需执行一次push即可同时推送多个仓库。

```undefined
git push
```

## 对比

以上两种配置乍一看，配置2好像比配置1方便很多，至少少了一次push不是？！

其实未必，两种配置的不同还体现在pll代码上，配置1可以选择任一仓库进行pll，而配置2缺默认只能从config中的第一个url内的仓库pull代码。
 总之，pull代码的话配置1更方便，push代码的话配置2更方便。至于用哪种，各位dever自选。

