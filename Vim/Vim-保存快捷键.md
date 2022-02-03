# Vim-保存快捷键

https://blog.csdn.net/deng8235615/article/details/101245593

[toc]


```
" 定义<leader>键
" let mapleader = " "      
"""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""
" 按 空格 + w 保存的快捷键
"""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""
nmap <Leader>w :w!<CR>
vmap <Leader>w <C-C>:w!<CR>
imap <Leader>w <Esc>:w!<CR>

"""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""
```


用vim正撸代码撸的飞起，突然Xshell就掉线了，真是太蛋疼了。

于是开始怀念起vs下撸代码时随时随地ctrl+s保存的快捷键，百度了一下，网上的vim实现ctrl+s保存的快捷键设置都有问题，自己捣鼓了半天才搞定，在这里记录一下。

 

首先要禁止 "CTRL-S：挂起终端， "CTRL-Q：恢复终端 "这些键盘映射，

在用户主目录下的.bashrc中加入stty -ixon   如下图：


![img](https://images0.cnblogs.com/blog2015/750208/201506/121845215518768.png)

然后修改vim中的按键映射，在 ~/.vimrc中加入

```
nmap <C-S> :w!<CR>i
vmap <C-S> <C-C>:w!<CR>
imap <C-S> <Esc>:w!<CR>i
```

如下图：

![img](https://images0.cnblogs.com/blog2015/750208/201506/121900177694202.png)

就可以实现在vim的命令模式和插入模式下，Ctrl+s保存文件

