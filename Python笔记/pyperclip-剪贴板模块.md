# pyperclip-操作剪贴板模块
https://zhuanlan.zhihu.com/p/64678888

首先我们要介绍一个操作剪贴板的模块——pyperclip

安装：pip instasll pyperclip

这个模块有两个函数：


```
copy(), 向剪贴板发送文本
paste(), 从剪贴板接收文本
```


首先用copy函数来发送文本到剪贴板（即复制内容），然后把从剪贴板接收的内容（即粘贴内容），打印出来

这时候你用ctrl+v粘贴，出来的文本也是“Python从放弃到真香”



如果你不用copy方法，直接用paste的话，会打印出你剪贴板的文本，也就是你上一次复制的内容。

总结一下，pyperclip的用法就是和剪贴板相关联（仅限文本），copy了什么内容，就会原封不动的paste什么内容。

比如说我要把复制的文本全部倒过来粘贴，怎么实现呢？


```
import pyperclip

s = pyperclip.paste()
s_ = s[::-1]
pyperclip.copy(s_)
```
