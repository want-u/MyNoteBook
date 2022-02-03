### 将一键提交添加到右键菜单

![](https://raw.githubusercontent.com/want-u/pictures/master/20201218011328.png)

1. 创建 git_push.bat【批处理文件】

```
D:
cd D:\MyNoteBook
git add -A && git commit -m "commit" && git pull origin master && git push origin master

```


2. 创建 git_push.reg【注册表文件】

```
Windows Registry Editor Version 5.00
[HKEY_CLASSES_ROOT\Directory\Background\shell\NoteBook]
@="NoteBook"

[HKEY_CLASSES_ROOT\Directory\Background\shell\NoteBook\command]
@="d:\\MyNoteBook\\.git_push\\git_push.bat"
```


3. 双击运行 git_push.reg ，即可将同步命令添加到右键菜单


4. 双击运行 new_md.reg，即可在右键新建添加md格式文件

```
<<<<<<< HEAD
Windows Registry Editor Version 5.00

[HKEY_CLASSES_ROOT\.md]
@="typora.md"

[HKEY_CLASSES_ROOT\.md\OpenWithProgids]
"Typora.md"=""

[HKEY_CLASSES_ROOT\.md\ShellNew]
=======
Windows Registry Editor Version 5.00

[HKEY_CLASSES_ROOT\.md]
@="typora.md"

[HKEY_CLASSES_ROOT\.md\OpenWithProgids]
"Typora.md"=""

[HKEY_CLASSES_ROOT\.md\ShellNew]
>>>>>>> 55bdf98f2e666f0992ead6e5e4d02be4a33c588b
"NullFile"=""
```

> 附注： 使用 git.exe 进行一键提交，比较钟爱git的小黑框
> 【git_view.reg】

```
Windows Registry Editor Version 5.00
[HKEY_CLASSES_ROOT\Directory\Background\shell\NoteBook]
@="NoteBook"

[HKEY_CLASSES_ROOT\Directory\Background\shell\NoteBook\command]
@="C:\Program Files\Git\git-bash.exe -c "cd /d/MyNoteBook && git add -A && git commit -m "make a commit" && git pull origin master && git push origin master""

```

