# rm替换为mv

linux上使用rm很危险，刚刚不小心删除了一个还有用的文件。
因此我们可以把rm指令替换为mv，执行后把要删除的文件保留到一个目录下，定期清理就好了。
把下面的代码粘贴到你的.bashrc或.zshrc下。注意root下的也要粘贴。	


```
# for rm {{
# mkdir ~/.delete, when rm somethings ,mv them to here
if [ ! -d $HOME/.delete ]
then
    mkdir $HOME/.delete
fi
unDoRm() {
  mv -i $HOME/.delete/$@ ./
}
toBackup()
{
    for thing in $@
    do
        echo $thing | grep '^-' > /dev/null
        if [ ! $? = 0 ]
        then
            mv $thing $HOME/.delete
            echo mv $thing to ~/.delete, you can backup them
        fi
    done

}
cleanDelete()
{
    echo 'clear backup files?[y/N]'
    read confirm
    [ $confirm = 'y' ] || [ $confirm = 'Y' ]  && /usr/bin/rm -rf $HOME/.delete/*
}
# rm somethings
alias rm=toBackup
# see what in~/.delete now
alias lsdel='ls $HOME/.delete'
# undo
alias unrm=unDoRm
# clean ~/.delete
alias cleandel=cleanDelete
# }}
```

> 修改版
```
# for rm {{
# mkdir ~/.delete, when rm somethings ,mv them to here
if [ ! -d /tmp/.delete ]
then
    mkdir /tmp/.delete
fi
unDoRm() {
  mv -i /tmp/.delete/$@ ./
}
toBackup()
{
    for thing in $@
    do
        echo $thing | grep '^-' > /dev/null
        if [ ! $? = 0 ]
        then
            mv $thing /tmp/.delete
            echo mv $thing to ~/.delete, you can backup them
        fi
    done

}
cleanDelete()
{
    echo 'clear backup files?[y/N]'
    read confirm
    [ $confirm = 'y' ] || [ $confirm = 'Y' ]  && /usr/bin/rm -rf /tmp/.delete/*
}
# rm somethings
alias rm=toBackup
# see what in~/.delete now
alias lsdel='ls /tmp/.delete'
# undo
alias unrm=unDoRm
# clean ~/.delete
alias cleandel=cleanDelete
# }}
```
