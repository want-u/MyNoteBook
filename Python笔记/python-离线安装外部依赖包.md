# python-离线安装外部依赖包

https://www.cnblogs.com/JonaLin/p/12161557.html

[toc]



## 方式一：下载依赖包

下载依赖包：[PyPI · The Python Package Index](https://pypi.org/)

[![img](https://img2018.cnblogs.com/i-beta/1472972/202001/1472972-20200108190044338-1652405139.png)](https://img2018.cnblogs.com/i-beta/1472972/202001/1472972-20200108190044338-1652405139.png)


```
# python2 对应的 prettytable-0.7.2.tar.gz，https://pypi.org/search/?q=prettytable 没找到低版本
# 方式一：https://pypi.python.org/packages/source/P/PrettyTable/prettytable-0.7.2.tar.gz
# 方式二：https://mirrors.bfsu.edu.cn/pypi/web/simple/prettytable # 中科大镜像源
# https://mirrors.bfsu.edu.cn/pypi/web/packages/e0/a1/36203205f77ccf98f3c6cf17cf068c972e6458d7e58509ca66da949ca347/prettytable-0.7.2.tar.gz#sha256=2d5460dc9db74a32bcc8f9f67de68b2c4f4d2f01fa3bd518764c69156d9cacd9

# 将prettytable-0.7.2.tar.gz包下载到程序目录后解压

方式一：正常导入 
from prettytable import PrettyTable

方式二：如果有问题的话，可以将需要的py文件拿到当前路径
from prettytable.prettytable import PrettyTable
```


### 安装单个文件包

把下载好的包放在你要安装的目录下 E:\Python35\Scripts： xlrd-1.2.0-py2.py3-none-any.whl

进入该目录 cmd


```
pip install --no-index --find-links=/Python35/ xlrd-1.2.0-py2.py3-none-any.whl
```


执行成功如下

[![img](https://img2018.cnblogs.com/i-beta/1472972/202001/1472972-20200108184850222-635768162.png)](https://img2018.cnblogs.com/i-beta/1472972/202001/1472972-20200108184850222-635768162.png)

### 批量安装

1.制作requirement.txt


```
pip freeze > requirement.txt
```


内网安装外部依赖包办法：

例如:安装pytest包得时候会顺带安装pytest依赖包

离线下载安装包

```
下载单个离线包 - pip download -d your_offline_packages <package_name>
批量下载离线包 - pip download -d your_offline_packages -r requirements.txt
```

离线安装

```
安装单个离线包 - pip install --no-index --find-links=/**your_offline_packages**/ **package_name**
批量安装离线包 - pip install --no-index --find-links=/your_offline_packages/ -r requirements.txt
```


[![img](https://img2018.cnblogs.com/blog/854415/201811/854415-20181101140924464-1943747260.png)](https://img2018.cnblogs.com/blog/854415/201811/854415-20181101140924464-1943747260.png)

[![img](https://img2018.cnblogs.com/blog/854415/201811/854415-20181101140936112-455328727.png)](https://img2018.cnblogs.com/blog/854415/201811/854415-20181101140936112-455328727.png)

批量安装：

就是把需要的包放置在requirements.tes文件里


```
批量安装离线包 - pip install --no-index --find-links=/**your_offline_packages**/ -r requirements.txt
```


[![img](https://img2018.cnblogs.com/blog/854415/201811/854415-20181114143954527-1618232950.png)](https://img2018.cnblogs.com/blog/854415/201811/854415-20181114143954527-1618232950.png)

requirements文件内容：

[![img](https://img2018.cnblogs.com/blog/854415/201811/854415-20181114144049305-1088896429.png)](https://img2018.cnblogs.com/blog/854415/201811/854415-20181114144049305-1088896429.png)

### 如何查看pandas版本

**各个pandas版本的使用方法有一些差异，在使用pandas时需要查看pandas的版本号，我们可以在python里导入pandas包，使用show_versions()函数，就可以看到当前导入的pandas版本号。**

```
In [4]: import pandas as pd

In [5]: pd.show_versions()

INSTALLED VERSIONS
——————
commit: None
python: 3.5.2.final.0
python-bits: 64
OS: Linux
OS-release: 2.6.32-573.el6.x86_64
machine: x86_64
processor: x86_64
byteorder: little
LC_ALL: None
LANG: en_US.UTF-8
LOCALE: en_US.UTF-8

pandas: 0.22.0
pytest: 2.9.2
pip: 9.0.1
setuptools: 23.0.0
Cython: 0.24
numpy: 1.14.0
scipy: 0.19.1
pyarrow: None
xarray: None
IPython: 4.2.0
sphinx: 1.4.1
patsy: 0.4.1
dateutil: 2.6.1
pytz: 2017.3
blosc: None
bottleneck: 1.2.1
tables: 3.2.2
numexpr: 2.4.6
feather: None
matplotlib: 1.5.1
openpyxl: 2.3.2
xlrd: 1.0.0
xlwt: 1.1.2
xlsxwriter: 0.9.2
lxml: 3.6.0
bs4: 4.4.1
html5lib: None
sqlalchemy: 1.0.13
pymysql: 0.7.9.None
psycopg2: None
jinja2: 2.8
s3fs: None
fastparquet: None
pandas_gbq: None
pandas_datareader: 0.5.0

也可以使用：
In [6]: pd.__version__
Out[6]: ‘0.22.0’
可以看到pandas的版本是0.22.0，如果我们需要帮助，就查这个版本的文档

参考文档：http://pandas.pydata.org/pandas-docs/stable/
```

## 方式二：pip download

1、运用pip的基本功能download，具体格式如下


```
pip download -d ./path pyinstaller -i https://pypi.mirrors.ustc.edu.cn/simple/
# <-d ./path>的意思是将下载的文件存放到当前目录下的path文件夹里面，<-i url>的意思是从中科大镜像源下载文件。
```
2、使用本地索引依赖包

```
# pip提供了可以在本地目录搜索依赖包的选项，这样安装的好处是以前安装过的依赖包不会更新，避免出现升级带来的bug。

pip install --no-index --find-links=C:\Users\path\ pyinstaller

```


## 方式三：pip install

1. 先在联网环境上下载所需要的库

```
pip install influxdb-python
```
2. 打包已安装的包

在C:Python27目录下新建packages文件夹用来存储下载下来的所需安装包。


```
pip list #查看安装的包
pip freeze >requirements.txt
pip install --download C:\Python27\packages -r requirements.txt
```


在执行download之前还可以编辑requirements.txt，只保留本次安装所需要的依赖库。

3. 离线情况安装打包好的包

将packages文件夹和requirement.txt拷贝至离线机器上目录下，packages文件夹放在C:Python27下。

requirements.txt放在执行路径下（pip.exe所在路径，若已添加环境变量则可随意放置）


使用命令安装依赖文件

```
pip install --no-index --find-links=c:\Python27\packages -r requirements.txt
```


