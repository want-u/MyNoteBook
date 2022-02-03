# Vim-粘贴取消自动缩进

https://www.cnblogs.com/Template/p/9345565.html

[toc]

# Vim 复制粘贴探秘

Vim 作为最好用的文本编辑器之一，使用vim来编文档，写代码实在是很惬意的事情。每当学会了vim的一个新功能，就会很大地提高工作效率。有人使用vim几 十年，还没有完全掌握vim的功能，这也说明了vim的强大。而这样何尝不是一件好事呢，只要有的学习，就有的提高。

最近使用Vim来写博客，发现在Vim中粘贴Python代码后，缩进就全乱了。仔细研究了以下，原来是自动缩进的缘故，于是做如下设置：

```
:set noai nosi 
```

取消了自动缩进和智能缩进，这样粘贴就不会错行了。但在有的vim中不行，还是排版错乱。

后来发现了更好用的设置：

```
:set paste 
```

进入paste模式以后，可以在插入模式下粘贴内容，不会有任何变形。这个真是灰常好用，情不自禁看了一下帮助，发现它做了这么多事：

- textwidth设置为0
- wrapmargin设置为0
- set noai
- set nosi
- softtabstop设置为0
- revins重置
- ruler重置
- showmatch重置
- formatoptions使用空值

下面的选项值不变，但却被禁用：

- lisp
- indentexpr
- cindent

怪不得之前只设置noai和nosi不行，原来与这么多因素有关！

但这样还是比较麻烦的，每次要粘贴的话，先set paste，然后粘贴，然后再set nopaste。有没有更方便的呢？你可能想到了，使用键盘映射呀，对。我们可以这样设置：:

```
:map <F10> :set paste<CR> 
:map <F11> :set nopaste<CR> 
```

这样在粘贴前按F10键启动paste模式，粘贴后按F11取消paste模式即可。其实，paste有一个切换paste开关的选项，这就是pastetoggle。通过它可以绑定快捷键来激活/取消 paste模式。比如：:

```
:set pastetoggle=<F11> 
```

这样减少了一个快捷键的占用，使用起来也更方便一些。