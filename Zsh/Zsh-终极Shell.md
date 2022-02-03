# Zsh-终极Shell
官网：https://ohmyz.sh/

## 一、安装zsh
    
```
# 使用yum安装zsh
yum -y install zsh

# 切换shell为zsh
chsh -s /bin/zsh

# 最后重启，或直接执行zsh
```

## 二、安装oh my zsh

```
# 安装git工具
yum -y install git

# 安装oh my zsh
wget https://github.com/robbyrussell/oh-my-zsh/raw/master/tools/install.sh -O - | sh
```

## 三、主题选择

```
# 修改配置文件
vim ~/.zshrc
    # 将ZSH_THEME改成bira
    # avit ys
    ZSH_THEME="bira"
    # agnoster

# 更新配置
source ~/.zshrc   
```

```
# zsh主题提示符：
vim /root/.oh-my-zsh/themes/bira.zsh-theme
# 提示符修改
PROMPT="
╭─${user_host}${current_dir}${rvm_ruby}${git_branch}${venv_prompt}
╰─%B${user_symbol}%b "
RPROMPT="%B${return_code}%b"

# 添加时间显示
PROMPT="
╭─${user_host}${current_dir}${rvm_ruby}${git_branch}${venv_prompt} %{$fg[white]%}[%*]%{$reset_color%}
╰─%B${user_symbol}%b "

# 小箭头
PROMPT="
${user_host}${current_dir}${rvm_ruby}${git_branch}${venv_prompt} %{$fg[white]%}[%*]%{$reset_color%}
%{$fg_bold[green]%}➜ %B${user_symbol}%b "

# PS1变量修改，在.bashrc中追加【\t代表时间】
vi ~/.bashrc

PS1="
╭─\[\e[31;1m\]\u@\h\e[m \[\e[34;1m\]\w\e[m    \t
╰─\$ "

PS1="
\[\e[31;1m\]\u@\h\e[m \[\e[34;1m\]\w\e[m    \[\e[36;1m\]\t\e[m
➜ "

PS1 可以支持以下这些选项：
\d：显示曰期，格式为"星期 月 日"。
\H：显示完整的主机名。如默认主机名"localhost.localdomain"。
\h：显示简写的主机名。如默认主机名"localhost"。
\t：显示 24 小时制时间，格式为"HH:MM:SS"。
\T：显示 12 小时制时间，格式为"HH:MM:SS"。
\A：显示 24 小时制时间，格式为"HH:MM"。
\@：显示 12 小时制时间，格式为"HH:MM am/pm"。
\u：显示当前用户名。
\v：显示 Bash 的版本信息。
\w：显示当前所在目录的完整名称。
\W：显示当前所在目录的最后一个目录。
\#：执行的第几条命令。
\$：提示符。如果是 root 用户，则会显示提示符为"#"；如果是普通用户，则会显示提示符为"$"。
```


## 四、自动提示插件zsh-autosuggestions【推荐】

    使用以下命令安装
    git clone git://github.com/zsh-users/zsh-autosuggestions $ZSH_CUSTOM/plugins/zsh-autosuggestions
    # 在~/.zshrc加入插件名，插件名以空格隔开
    plugins=(git zsh-autosuggestions extract)
    # 更新配置
    source ~/.zshrc 



## 五、命令高亮 正确路径自带下划线【推荐】

```
# 下载zsh-syntax-highlighting
git clone git://github.com/jimmijj/zsh-syntax-highlighting ~/.oh-my-zsh/custom/plugins/zsh-syntax-highlighting

# 在~/.zshrc加入插件名，插件名以空格隔开
plugins=(git zsh-syntax-highlighting zsh-autosuggestions extract)


# 更新配置
source ~/.zshrc 

```

## 六、快速切换路径

```
# 下载autojump
git clone git://github.com/joelthelion/autojump.git

# 进入autojump执行
./install.py 

# 在～/.zshrc文件中加入此句
[[ -s ~/.autojump/etc/profile.d/autojump.sh ]] && . ~/.autojump/etc/profile.d/autojump.sh

# 更新配置
source ~/.zshrc 

>>>>>如果你之前打开过~/.oh-my-zsh/themes目录
>>>>>现在只需敲入j themes就可以快速切换到~/.oh-my-zsh/themes目录
```

## 七、自动补齐插件【不推荐】

```
# 下载incr.zsh
wget http://mimosa-pudica.net/src/incr-0.2.zsh   

# 将此插件放到oh-my-zsh目录的插件库下
mkdir .oh-my-zsh/plugins/incr
mv incr-0.2.zsh .oh-my-zsh/plugins/incr

# 在~/.zshrc文件末尾加上
source ~/.oh-my-zsh/plugins/incr/incr*.zsh

# 更新配置
source ~/.zshrc   

# 与vim冲突解决
rm -rf ~/.zcompdump*
zsh
```


## 八、更新zsh

    git config --global user.email "1846016614@qq.com"
    git config --global user.name "luoxian"
    cd ~/.oh-my-zsh
    git status
    git stash
    upgrade_oh_my_zsh【或者：omz update】
    git stash pop
    
    查看zsh版本：zsh --version