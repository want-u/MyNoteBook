## pip国内源

#### Linux下
    修改 ~/.pip/pip.conf (没有就创建一个文件夹及文件。文件夹要加“.”，表示是隐藏文件夹)
    内容如下：
    
    [global]
    index-url = https://pypi.tuna.tsinghua.edu.cn/simple
    [install]
    trusted-host = https://pypi.tuna.tsinghua.edu.cn
    
#### windows下
    直接在user目录中创建一个pip目录，如：C:\Users\xx\pip，然后新建文件pip.ini，即 %HOMEPATH%\pip\pip.ini，在pip.ini文件中输入以下内容（以豆瓣镜像为例）：
    
    [global]
    index-url = http://pypi.douban.com/simple
    [install]
    trusted-host = pypi.douban.com