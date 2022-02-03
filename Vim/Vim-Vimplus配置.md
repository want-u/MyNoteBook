# Vim-Vimplus配置

- https://www.cnblogs.com/highway-9/p/5984285.html
- https://github.com/junegunn/vim-plug (PlugInstall)

[toc]


![img](https://raw.githubusercontent.com/chxuan/vimplus/master/screenshots/vimplus-logo.png)

# An automatic configuration program for vim

![img](https://raw.githubusercontent.com/chxuan/vimplus/master/screenshots/main.png)

## 安装(github地址：https://github.com/chxuan/vimplus.git, 欢迎star和fork)

  ```bash
  ========Git方式========
  git clone https://gitee.com/luoxian1011/vimplus.git ~/.vimplus
  git clone https://gitee.com/chxuan/YouCompleteMe-clang.git ~/.vim/plugged/YouCompleteMe
  git clone https://gitee.com/luoxian1011/ale.git ~/.vim/plugged/ale
  # git clone https://gitee.com/chxuan/vim82.git ~/vim82
  
  cd ~/.vimplus && bash install.sh
  ```
  
  
  ```bash
  ========本地上传方式========

  # 1、上传vimplus.zip
  # sudo apt-get install unzip
  unzip vimplus.zip -d ~/.vimplus
  cd ~/.vimplus && bash install.sh
  
  # 2、安装语法检查-pyflakes(wsl)
  # https://github.com/dense-analysis/ale/blob/master/supported-tools.md
  # sudo apt-get install python3-pip -y
  # sudo apt-get install flake8 -y
  # pip3 install flake8 -i https://pypi.tuna.tsinghua.edu.cn/simple
  # pip3 install pylint -i https://pypi.tuna.tsinghua.edu.cn/simple
  pip3 install pyflakes -i https://pypi.tuna.tsinghua.edu.cn/simple
  ```
  
  ```
  ####################附注####################################
  # 安装YouCompleteMe自动补全
  cd  ~/.vim/plugged/YouCompleteMe
  ./install.py
  
  # 刷新zsh插件
  sed -i 's/plugins.*/plugins=(git zsh-autosuggestions zsh-syntax-highlighting pip)/g' ~/.zshrc
  source ~/.zshrc
  

  ```
  
```
# 在ubuntu上源代码安装vim
function compile_vim_on_ubuntu()
{
    vim --version | head -1 | grep 'IMproved 8.2' && return
    sudo apt-get install -y libncurses5-dev libncurses5 libgnome2-dev libgnomeui-dev \
        libgtk2.0-dev libatk1.0-dev libbonoboui2-dev \
        libcairo2-dev libx11-dev libxpm-dev libxt-dev python-dev python3-dev ruby-dev lua5.1 lua5.1-dev

    # rm -rf ~/vim82
    git clone https://gitee.com/chxuan/vim82.git ~/vim82
    cd ~/vim82
    ./configure --with-features=huge \
        --enable-multibyte \
        --enable-rubyinterp \
        --enable-pythoninterp \
        --enable-perlinterp \
        --enable-luainterp \
        --enable-gui=gtk2 \
        --enable-cscope \
        --prefix=/usr
    make
    sudo make install
    cd -
}
```

### Mac OS X

- 安装[HomeBrew](https://brew.sh/)

  ```bash
  /usr/bin/ruby -e "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/master/install)"
  ```

- 安装vimplus

  ```bash
  git clone https://github.com/chxuan/vimplus.git ~/.vimplus
  cd ~/.vimplus
  ./install.sh
  ```

### Ubuntu

- 版本要求

  `ubuntu16.04`及其以上系统。

- 安装vimplus(建议在普通用户下安装)

  ```bash
  git clone https://github.com/chxuan/vimplus.git ~/.vimplus
  cd ~/.vimplus
  ./install.sh
  ```

### Centos

- 版本要求

  `centos7`及其以上系统。

- 安装vimplus(建议在普通用户下安装)

  ```bash
  git clone https://github.com/chxuan/vimplus.git ~/.vimplus
  cd ~/.vimplus
  ./install.sh
  ```

### ArchLinux

- 安装vimplus

  ```bash
  git clone https://github.com/chxuan/vimplus.git ~/.vimplus
  cd ~/.vimplus
  ./install.sh
  ```

## 个性化

修改 `~/.vimrc.local` 文件内容，以启用个性化定制，可覆盖 `~/.vimrc` 中的设置。

## 插件列表

| 插件                                                         | 说明                                                         |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
| [cpp-mode](https://github.com/chxuan/cpp-mode)               | 提供生成函数实现、函数声明/实现跳转、.h .cpp切换等功能(I'm author😄) |
| [vim-edit](https://github.com/chxuan/vim-edit)               | 方便的文本编辑插件(I'm author😄)                              |
| [change-colorscheme](https://github.com/chxuan/change-colorscheme) | 随心所欲切换主题(I'm author😄)                                |
| [prepare-code](https://github.com/chxuan/prepare-code)       | 新建文件时，生成预定义代码片段(I'm author😄)                  |
| [vim-buffer](https://github.com/chxuan/vim-buffer)           | vim缓存操作(I'm author😄)                                     |
| [vimplus-startify](https://github.com/chxuan/vimplus-startify) | vimplus开始页面(修改自[mhinz/vim-startify](https://github.com/mhinz/vim-startify)) |
| [tagbar](https://github.com/chxuan/tagbar)                   | 使用[majutsushi/tagbar](https://github.com/majutsushi/tagbar)的v2.3版本，[taglist](https://github.com/vim-scripts/taglist.vim)的替代品，显示类/方法/变量 |
| [vim-plug](https://github.com/junegunn/vim-plug)             | 比[Vundle](https://github.com/VundleVim/Vundle.vim)下载更快的插件管理软件 |
| [YouCompleteMe](https://github.com/Valloric/YouCompleteMe)   | 史上最强大的基于语义的自动补全插件，支持C/C++、C#、Python、PHP等语言 |
| [NerdTree](https://github.com/scrooloose/nerdtree)           | 代码资源管理器                                               |
| [vim-nerdtree-syntax-highlight](https://github.com/tiagofumo/vim-nerdtree-syntax-highlight) | NerdTree文件类型高亮                                         |
| [nerdtree-git-plugin](https://github.com/Xuyuanp/nerdtree-git-plugin) | NerdTree显示git状态                                          |
| [vim-devicons](https://github.com/ryanoasis/vim-devicons)    | 显示文件类型图标                                             |
| [Airline](https://github.com/vim-airline/vim-airline)        | 可以取代[powerline](https://github.com/powerline/powerline)的状态栏美化插件 |
| [auto-pairs](https://github.com/jiangmiao/auto-pairs)        | 自动补全引号、圆括号、花括号等                               |
| [LeaderF](https://github.com/Yggdroot/LeaderF)               | 比[ctrlp](https://github.com/ctrlpvim/ctrlp.vim)更强大的文件的模糊搜索工具 |
| [ack](https://github.com/mileszs/ack.vim)                    | 强大的文本搜索工具                                           |
| [vim-surround](https://github.com/tpope/vim-surround)        | 自动增加、替换配对符的插件                                   |
| [vim-commentary](https://github.com/tpope/vim-commentary)    | 快速注释代码插件                                             |
| [vim-repeat](https://github.com/tpope/vim-repeat)            | 重复上一次操作                                               |
| [vim-endwise](https://github.com/tpope/vim-endwise)          | if/end/endif/endfunction补全                                 |
| [tabular](https://github.com/godlygeek/tabular)              | 代码、注释、表格对齐                                         |
| [vim-easymotion](https://github.com/easymotion/vim-easymotion) | 强大的光标快速移动工具，强大到颠覆你的插件观                 |
| [incsearch.vim](https://github.com/haya14busa/incsearch.vim) | 模糊字符搜索插件                                             |
| [markdown-preview](https://github.com/iamcco/markdown-preview.vim) | markdown实时预览                                             |
| [vim-fugitive](https://github.com/tpope/vim-fugitive)        | 集成Git                                                      |
| [gv](https://github.com/junegunn/gv.vim)                     | 显示git提交记录                                              |
| [vim-slash](https://github.com/junegunn/vim-slash)           | 优化搜索，移动光标后清除高亮                                 |
| [echodoc](https://github.com/Shougo/echodoc.vim)             | 补全函数时在命令栏显示函数签名                               |
| [vim-smooth-scroll](https://github.com/terryma/vim-smooth-scroll) | 让翻页更顺畅                                                 |
| [clever-f.vim](https://github.com/rhysd/clever-f.vim)        | 强化f和F键                                                   |
| [github-complete.vim](https://github.com/rhysd/github-complete.vim) | Emoji🐶补全                                                   |
| [vimcdoc](https://github.com/yianwillis/vimcdoc)             | vim中文文档                                                  |

## 快捷键

以下是部分快捷键，更详细的快捷键请查阅[vimplus帮助文档](https://github.com/chxuan/vimplus/blob/master/help.md)。

| 快捷键       | 说明                                 |
| ------------ | ------------------------------------ |
| `,`          | Leader Key                           |
| `<leader>n`  | 打开/关闭代码资源管理器              |
| `<leader>t`  | 打开/关闭函数列表                    |
| `<leader>a`  | .h .cpp 文件切换                     |
| `<leader>u`  | 转到函数声明                         |
| `<leader>U`  | 转到函数实现                         |
| `<leader>o`  | 打开include文件                      |
| `<leader>y`  | 拷贝函数声明                         |
| `<leader>p`  | 生成函数实现                         |
| `<leader>w`  | 单词跳转                             |
| `<leader>f`  | 搜索~目录下的文件                    |
| `<leader>F`  | 搜索当前目录下的文本                 |
| `<leader>g`  | 显示git仓库提交记录                  |
| `<leader>G`  | 显示当前文件提交记录                 |
| `<leader>gg` | 显示当前文件在某个commit下的完整内容 |
| `<leader>ff` | 语法错误自动修复(FixIt)              |
| `<c-p>`      | 切换到上一个buffer                   |
| `<c-n>`      | 切换到下一个buffer                   |
| `<leader>d`  | 删除当前buffer                       |
| `<leader>D`  | 删除当前buffer外的所有buffer         |
| `vim`        | 运行vim编辑器时,默认启动开始页面     |
| `<F5>`       | 显示语法错误提示窗口                 |
| `<F7>`       | 启用markdown实时预览                 |
| `<F8>`       | 关闭markdown实时预览                 |
| `<F9>`       | 显示上一主题                         |
| `<F10>`      | 显示下一主题                         |
| `<leader>l`  | 按竖线对齐                           |
| `<leader>=`  | 按等号对齐                           |
| `Ya`         | 复制行文本到字母a                    |
| `Da`         | 剪切行文本到字母a                    |
| `Ca`         | 改写行文本到字母a                    |
| `rr`         | 替换文本                             |
| `<leader>r`  | 全局替换，目前只支持单个文件         |
| `gcc`        | 注释代码                             |
| `gcap`       | 注释段落                             |
| `vif`        | 选中函数内容                         |
| `dif`        | 删除函数内容                         |
| `cif`        | 改写函数内容                         |
| `vaf`        | 选中函数内容（包括函数名 花括号）    |
| `daf`        | 删除函数内容（包括函数名 花括号）    |
| `caf`        | 改写函数内容（包括函数名 花括号）    |
| `fa`         | 查找字母a，然后再按f键查找下一个     |
| `<c-x><c-o>` | Emoji🐶补全                           |

## 按需加载插件

为了提高 vim 的启动速度, 我们期望某些插件只有在特定的情况下才被加载. 例如我们希望 `vim-go` 插件只有在编辑 go 类型的文件时才会被打开, 便可以通过如下方法实现:

```
Plug 'https://github.com/fatih/vim-go.git', {'for': 'go'}
```

其中 `for` 后面的文件类型也可以使用列表, 如 `{'for': ['go', 'golang']}` .

同时, 也可以在调用命令的时候加载插件, 如:

```
Plug 'https://github.com/Yggdroot/indentLine', {'on' : 'IndentLinesToggle'}
```

这段代码的意思是只有 `IndentLinesToggle` 被调用时, 对应的插件才会被加载. `on` 后面也可以使用列表.



## Q & A

- **`安装vimplus后Airline等插件有乱码，怎么解决？`**

  linux和mac系统需设置终端字体为`Droid Sans Mono Nerd Font`。

- **`xshell连接远程主机不能使用vim-devicons或乱码。`**

  windows系统安装[Nerd Font](https://github.com/ryanoasis/nerd-fonts)字体后并更改xshell字体即可。

- **`安装vimplus会经常失败，安装了几次都不成功！！！`**

  vimplus安装时需要访问外国网站，由于网络原因，可能会失败，安装成功也要1个多小时，ycm插件有200M左右，下载比较耗时，这里有下载好的[YouCompleteMe.tar.gz](https://pan.baidu.com/s/1i481Eeh)文件，下载后解压到~/.vim/plugged/目录，并进入YouCompleteMe目录执行`./install.py --clang-completer`即可安装。

- **`使用第三方库时怎么让ycm补全第三方库API？`**

  vimplus安装完毕之后，`~`目录下将会生成两个隐藏文件分别是.vimrc和.ycm_extra_conf.py，其中.vimrc是vim的配置文件，.ycm_extra_conf.py是ycm插件的配置文件，当你需要创建一个project时，需要将.ycm_extra_conf.py拷贝到project的顶层目录，通过修改该配置文件里面的`flags`变量来添加你的第三方库路径。

- **`安装vimplus完成后ycm不能够工作！！！`**

  这里的原因可能就有很多了，可能每个人遇到的问题不一样，但`vimplus`尽最大努力不让用户操心，需要注意的是ycm插件只支持`64`位的系统，更多信息请访问[ycm官网](https://github.com/Valloric/YouCompleteMe)。

- **`在aaa用户下安装了vimplus，在bbb用户下不能使用？`**

  目前vimplus是基于用户的，如果你想在其他用户下也能使用vimplus，也需要单独安装。

- **`在Archlinux环境下不能使用ycm怎么办？(缺少libtinfo.so.5)`**

  在Archlinux下可以试着使用pkgfile命令搜索依赖的文件具体在什么包内，目前找到的包含libtinfo.so.5的包是ncurses5-compat-libs(AUR)或者32位的lib32-ncurses5-compat-libs(AUR)，安装后即可正常使用。

- **`以上没有我遇到的问题怎么办？`**

  您可以通过上网找解决方法，或提[Issues](https://github.com/chxuan/vimplus/issues)，也可以通过发邮件方式`787280310@qq.com`一起讨论解决方法。

- **`vimplus用起来真的太棒了，怎么办？`**

  那就麻烦您打赏一颗⭐⭐吧，给予我继续维护的动力。

## 特性展示

- YouCompleteMe

  ![img](https://camo.githubusercontent.com/1f3f922431d5363224b20e99467ff28b04e810e2/687474703a2f2f692e696d6775722e636f6d2f304f50346f6f642e676966)

- cpp-mode

  ![img](https://raw.githubusercontent.com/chxuan/cpp-mode/master/screenshots/cpp-mode.gif)

- LeaderF

  ![img](https://github.com/Yggdroot/Images/blob/master/leaderf/leaderf_1.gif)

- vim-airline

  ![img](https://camo.githubusercontent.com/ba79534309330accd776a8d2a0712f7c4037d7f9/68747470733a2f2f662e636c6f75642e6769746875622e636f6d2f6173736574732f3330363530322f313037323632332f34346332393261302d313439352d313165332d396365362d6463616461336631633533362e676966)

- vim-surround

  ![img](https://camo.githubusercontent.com/1f02cead8bdcf894f26b0006c44068a33a7dc8e5/687474703a2f2f6a6f65646963617374726f2e636f6d2f7374617469632f70696374757265732f737572726f756e645f656e2e676966)

- vim-commentary

  ![img](https://camo.githubusercontent.com/2f5cb5bc9a964b0d9e623b5b3aff0314294ac841/687474703a2f2f6a6f65646963617374726f2e636f6d2f7374617469632f70696374757265732f636f6d6d656e746172795f656e2e676966)

- auto-pairs

  ![img](https://camo.githubusercontent.com/372b34413e710cdbc95c5a5c1f901baf9e77791d/687474703a2f2f6a6f65646963617374726f2e636f6d2f7374617469632f70696374757265732f736d617274696e7075745f656e2e676966)

- vim-easymotion

  ![img](https://camo.githubusercontent.com/d5f800b9602faaeccc2738c302776a8a11797a0e/68747470733a2f2f662e636c6f75642e6769746875622e636f6d2f6173736574732f333739373036322f323033393335392f61386539333864362d383939662d313165332d383738392d3630303235656138333635362e676966)

- vim-devicons

  ![img](https://raw.githubusercontent.com/wiki/ryanoasis/vim-devicons/screenshots/v0.9.x/overall-screenshot.png)

- vim-startify

  ![img](https://raw.githubusercontent.com/mhinz/vim-startify/master/images/startify-menu.png)

- markdown-preview

  ![img](https://cloud.githubusercontent.com/assets/5492542/15363504/839753be-1d4b-11e6-9ac8-def4d7122e8d.gif)

兴趣是最好的老师，我的github地址：https://github.com/chxuan