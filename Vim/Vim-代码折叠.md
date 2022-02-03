# Vim-代码折叠

https://www.jianshu.com/p/962ce9bc31f3

[toc]

Vim 可以使用缩进指导折叠：



```bash
set foldmethod=indent
```

常用的打开和关闭折叠的快捷键有：

1. `zo` 打开一个折叠。
2. `zc` 关闭一个折叠。
3. `za` 当关闭时打开，当打开时关闭。
4. `zo`、`zc`、`za` 对应 `zO`、`zC` 和 `zA`；以递归形式改变折叠状态。
5. `zR` 打开全部折叠。
6. `zM` 关闭全部折叠。