# GitHub Actions

大家知道，持续集成由很多操作组成，比如抓取代码、运行测试、登录远程服务器，发布到第三方服务等等。GitHub 把这些操作就称为 actions。


## 1. 基本概念

    （1）workflow （工作流程）：持续集成一次运行的过程，就是一个 workflow。
    （2）job （任务）：一个 workflow 由一个或多个 jobs 构成，含义是一次持续集成的运行，可以完成多个任务。
    （3）step（步骤）：每个 job 由多个 step 构成，一步步完成。
    （4）action （动作）：每个 step 可以依次执行一个或多个命令（action）。

## 2. workflow 文件

GitHub Actions 的配置文件叫做 workflow 文件，存放在代码仓库的.github/workflows目录。

### 2.1 workflow

- 采用 YAML 格式，文件名可以任意取，但是后缀名统一为.yml
- 比如foo.yml。一个库可以有多个 workflow 文件。
- GitHub 只要发现.github/workflows目录里面有.yml文件，就会自动运行该文件。

## 2.2 配置

字段非常多，详见官方文档。下面是一些基本字段。


```
name: Python application  # name字段是 workflow 的名称

on:  # on字段指定触发 workflow 的条件，通常是某些事件。
  push:
    branches: [ main ]
  pull_request:
    branches: [ main ]
  schedule:
    - cron: '1 0 * * *'

jobs:
  build:

    runs-on: ubuntu-latest  # runs-on字段指定运行所需要的虚拟机环境

    steps:  # steps字段指定每个 Job 的运行步骤，可以包含一个或多个步骤
    - uses: actions/checkout@v2
    - name: Set up Python 3.8
      uses: actions/setup-python@v2
      with:
        python-version: 3.8
    - name: Install dependencies
      run: |
        python -m pip install --upgrade pip
        if [ -f requirements.txt ]; then pip install -r requirements.txt; fi
    - name: go python script
      run: |
        user='${{ secrets.USER }}'
        pwd='${{ secrets.PWD }}'
        echo "begin to run python script"
        python hi.py $user $pwd
        echo "Script Done."
```


## 3. Secrets私密变量

调用Secrets：

    run: |
      user='${{ secrets.USER }}'
      pwd='${{ secrets.PWD }}' 
      python hi.py $user $pwd
      echo "Script Done."


## 4. 实例
https://github.com/want-u/hi

    .github/workflows  Update main.yml          # 工作流程，计划
    README.md          Initial commit           # 说明文档
    hi.py              Update hi.py             # Python脚本
    requirements.txt   Create requirements.txt  # pip依赖清单


1. 设置secrets变量
    - USER = '邮箱'
    - PWD = '授权码'

2. 编写python库清单

```
# requirements.txt
requests
```


3. 编写Python脚本

```
# -*- coding: utf-8 -*-
# @Date    : 2020/4/23 16:07
# Software : PyCharm
import smtplib
import requests
import email
import sys
from email.mime.text import MIMEText
from email.mime.image import MIMEImage
from email.mime.multipart import MIMEMultipart

USER = sys.argv[1]
PWD = sys.argv[2]

def get_mes():
    url = 'http://open.iciba.com/dsapi/'
    r = requests.get(url)
    data = r.json()
    en_content = data.get('content')
    zh_content = data.get('note')
    message = zh_content + '\n' + en_content
    return message

msg = get_mes()
print(msg)

HOST = 'smtp.qq.com'
SUBJECT = '嘿！今天也是幸运的一天~'
FROM = USER
TO = USER
message = MIMEMultipart('related')
message_html = MIMEText(f"{msg}", 'plain', 'utf-8')
message.attach(message_html)
message['From'] = FROM
message['To'] = TO
message['Subject'] = SUBJECT
email_client = smtplib.SMTP_SSL(HOST)
email_client.connect(HOST, '465')
result = email_client.login(FROM, PWD)
email_client.sendmail(from_addr=FROM, to_addrs=TO.split(','), msg=message.as_string())
email_client.close()
print("Done.")
```
4. 编写Actions

```
name: Python application

on:
  push:
    branches: [ main ]
  pull_request:
    branches: [ main ]
  schedule:
    - cron: '1 0 * * *'

jobs:
  build:

    runs-on: ubuntu-latest

    steps:
    - uses: actions/checkout@v2
    - name: Set up Python 3.8
      uses: actions/setup-python@v2
      with:
        python-version: 3.8
    - name: Install dependencies
      run: |
        python -m pip install --upgrade pip
        if [ -f requirements.txt ]; then pip install -r requirements.txt; fi
    - name: go python script
      run: |
        user='${{ secrets.USER }}'
        pwd='${{ secrets.PWD }}'
        echo "begin to run python script"
        python hi.py $user $pwd
        echo "Script Done."
```


```
BiliBili cookie
# USERID 20476753
# SESSDATA fe4b8d1c%2C1624766850%2C603d0*c1
# BILIJCT b732fbba449324268393a50211a56055
```
