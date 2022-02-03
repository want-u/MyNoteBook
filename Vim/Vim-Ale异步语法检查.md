# Vim-Ale异步语法检查

- https://www.cnblogs.com/wudongwei/p/9083546.html
- https://blog.csdn.net/demorngel/article/details/69052789

[toc]

## 注意

在设置let g:ale_sign_error = '✗'和let g:ale_sign_warning = '⚡'这些时，可能vim不让你保存，提示fenc这个东西。
所以，为了保险起见，你最好在脚本里写入

```
set fenc= 
```

在注意，fenc是等于空格的，不要自以为是把=后面的空格去掉（我就是一开始这样了）。这让它置空。

## 安装pyflakes(默认pylint)

```
pip install pyflakes -i https://pypi.tuna.tsinghua.edu.cn/simple
```


## 克隆ale

```
git clone https://github.com/w0rp/ale.git ~/.vim/plugged/ale
```

## vim ~/.vimrc
```vim
"异步语法检查 PlugInstall
Plug 'w0rp/ale'

" ale-setting {{{
let g:ale_set_highlights = 0
"自定义error和warning图标
let g:ale_sign_error = '✗'
let g:ale_sign_warning = '⚡'
"在vim自带的状态栏中整合ale
let g:ale_statusline_format = ['✗ %d', '⚡ %d', '✔ OK']
"显示Linter名称,出错或警告等相关信息
let g:ale_echo_msg_error_str = 'E'
let g:ale_echo_msg_warning_str = 'W'
let g:ale_echo_msg_format = '[%linter%] %s [%severity%]'
"打开文件时进行检查
let g:ale_lint_on_enter = 1

"普通模式下，sp前往上一个错误或警告，sn前往下一个错误或警告
nmap sp <Plug>(ale_previous_wrap)
nmap sn <Plug>(ale_next_wrap)
"<Leader>s触发/关闭语法检查
nmap <Leader>s :ALEToggle<CR>
"<Leader>d查看错误或警告的详细信息
nmap <Leader>d :ALEDetail<CR>
"对python使用pyflakes进行语法检查
"\   'python': ['pylint'],
let g:ale_linters = {
\   'python': ['pyflakes'],
\}
" }}}
```


```
"使用clang对c和c++进行语法检查，对python使用pylint进行语法检查
let g:ale_linters = {
\   'c++': ['clang'],
\   'c': ['clang'],
\   'python': ['pyflakes'],
\}
```
