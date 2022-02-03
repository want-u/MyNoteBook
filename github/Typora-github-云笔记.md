# Typora-github-云笔记

[toc]

## 1. Typora介绍

![img](https://www.typora.io/img/windows/screen2.png)

    Typora 是一款支持实时预览的 Markdown 文本编辑器。
    
    它有 OS X、Windows、Linux 三个平台的版本，并且由于仍在测试中，是完全免费的。
    
    下载地址：https://www.typora.io/

关于 Markdown：

Markdown 是用来编写结构化文档的一种纯文本格式，它使我们在双手不离开键盘的情况下，可以对文本进行一定程度的格式排版。

### 1.1 Typora安装和设置

下载地址：https://www.typora.io/#windows

主题地址：https://github.com/want-u/MyNoteBook/tree/master/.git_push/My-Typora-Themes

- 【文件-偏好设置-外观-一体化】
- 【文件-偏好设置-编辑器-拼写检查-不使用拼写检查】
- 【文件-偏好设置-编辑器-复制默认行为-取消】
- 【文件-偏好设置-Markdown-显示行号】

### 1.2 图像设置

![image-20200815204132133](https://raw.githubusercontent.com/yusenyi123/pictures1/master/imgs/20200827135505.png)

在这里设置插入图片的保存规则，选相对路径，在你的笔记目录下建一个文件夹专门存放图片，然后把放图片那个目录设置隐藏，这样在下面菜单中就不会显示了，都是我们的笔记看着很清爽
==图像设置这样做的目的是：为了让我们这托管的网站查看我们的笔记的时候也能看到笔记中的图片==



## 2. git安装和设置环境变量

Git安装：https://git-scm.com/downloads

==如果我们单纯用来做笔记本，我们就下载便携版本的git,下载完解压就能用==

==把git解压目录下的cmd文件夹路径添加到系统变量中==

### 2.1 生成ssh公钥

使用ssh公钥不需要每次向github提交都输入密码


```
git config --global user.email "1846016614@qq.com"
git config --global user.name "jianyue"

# 生成密钥文件
ssh-keygen -t rsa -C "sshkey"
```

#### 2.2 添加公钥

将ssh公钥添加到github的sshkeys中
- 右上角 Settings
- SSH and GPG keys
- 克隆已有仓库
git clone git@github.com:want-u/MyNoteBook.git

#### 2.3 新建仓库

在github中新建仓库，获取仓库的ssh链接

#### 2.4 初始化和远程同步


```
在笔记文件夹目录下 打开git bash 输入下列命令初始化本地仓库
git init 
将笔记本文件夹下所有文件进行跟踪，提交所有变化

git add -A  提交所有变化

其他命令：
git add -u  提交被修改(modified)和被删除(deleted)文件，不包括新文件(new)

git add .  提交新文件(new)和被修改(modified)文件，不包括被删除(deleted)文件
生成一个本地库版本

git commit -m "commit"
将本地库版本推送到github中

git push   git@github.com:yusenyi123/notebook.git  master

git push  git@gitee.com:yusenyi/notebook.git master


git remote add origin sqt@180.169.33.117:repositores/ZSKPad.git

git remote －v 查看远程origin
git remote rm origin 删除远程origin

git push  origin master
```

```
D:
cd D:\MyNoteBook
git add -A && git commit -m "commit" && git pull origin master && git push origin master
```



## 3. PicGo+GitHub 

**打造最稳定可靠的免费图床**

### 3.1 PicGo介绍

​	PicGo是一款图片上传工具，目前支持SM.MS图床、腾讯云COS、微博图床、GitHub图床、七牛图床、Imgur图床、阿里云OSS、又拍云图床，未来将支持更多图床。
​	而这里边，SM.MS和Imgur有免费版也有收费版，腾讯云、七牛、阿里云、又拍云都是收费的，微博图床据说已经挂了，那么，也就剩下GitHub安全、免费又可靠了。
​	所以我们可以将本地的文件，或者剪切板上截图发送到图床并生成在线图片链接，再贴到我们的博文里，不再占用我们服务器的存储和带宽啦。

PicGo地址：https://github.com/Molunerfinn/PicGo

### 3.2 GitHub图床

1. 创建GitHub图床之前，需要注册/登陆GitHub账号
2. 创建Repository
点击"New repository"按钮

3. 生成一个Token用于操作GitHub repository
回到主页，点击"Settings"按钮
4. 进入页面后，点击"Developer settings"按钮
5. 点击"Personal access tokens"按钮
6. 填写描述，选择"repo"权限，然后点击"Generate token"按钮
7. ![使用 PicGo+GitHub 打造最稳定可靠的免费图床](https://raw.githubusercontent.com/flighty/images/master/note-repo.jpg)
8. 注：创建成功后，会生成一串token，这串token之后不会再显示，所以第一次看到的时候，可以建个文本文件好好保存，忘记了只有重新生成，每次都不一样。

### 3.3 配置PicGo

1. 下载安装并运行PicGo

   https://github.com/Molunerfinn/PicGo/releases/

2. 配置图床
    之后按照这个配置格式配置即可！

  ![使用 PicGo+GitHub 打造最稳定可靠的免费图床](https://raw.githubusercontent.com/flighty/images/master/PicGo%20set.jpg)

  **第一行设定仓库名按照“账户名/仓库名的格式填写”，比如我的是：flighty/img**
  **第二行分支名统一填写“master”**
  **第三行将之前的Token粘贴在这里**
  **第四行留空**
  **第五行自定义域名**

  自定义域名作用是在上传图片后成功后，PicGo会将“自定义域名+上传的图片名”生成的访问链接，放到剪切板上

  自定义域名需要按照这样去填写：https://raw.githubusercontent.com/账户名/仓库名/master

  比如我的是：https://raw.githubusercontent.com/flighty/images/master

### 3.4 快捷键及相关配置

支持快捷键command+shift+p（macOS）或者control+shift+p（windows\linux）用以支持快捷上传剪贴板里的图片（第一张）。
  PicGo支持自定义快捷键，使用方法见配置手册。
  注：可以将快捷键设置为ctrl+shift+c

总结

​	将上面的步骤都设置好，每次截图之后，都可以按一下ctrl+shift+c，这样就会将剪切板上面的截图转化为在线网络图片链接，简直就是爽的不要不要的，关键是背靠 GitHub 和微软，比自建服务器都稳！



## 4. Gitee图床

https://blog.csdn.net/qq_42127861/article/details/119414585

- 下载插件，在插件设置里面搜索gitee，然后安装如下的插件：gitee-uploader 1.1.2

- 跳转安装node.js：https://nodejs.org/en/

- gitee创建仓库：
- owner：luoxian1011
- repo：luoxian1011/pictures
- branch：master
- 添加私人令牌 [右上角进入设置之后，一直向下拉，找到 私人令牌token：58cc18655510bf8fa7a1dd532e1956fc]
- path：/ 或指定路径
- PicGo安装gitee upload插件 [须有node.js环境]
- 配置PicGo中的gitee图床参数
- typora图片设置中：开启自动上传


![image-20201219033057608](https://gitee.com/luoxian1011/pictures/raw/master/image-20201219033057608.png)


## 5. sm.ms图床

    luoxian
    
    smms_secret_token：S7YnlHgz9xrTK81pTQT4hmEpDvWQNy77
    
## 6. gitbook

注册：https://www.gitbook.com/

创建空间

Integrations【集成】 —— github【√】 —— 将github中的项目同步到gitbook