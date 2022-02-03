# SQLite-轻型的数据库

- https://www.cnblogs.com/desireyang/p/12102143.html
- https://blog.csdn.net/qq_40707462/article/details/105685933

[toc]

# SQLite

SQLite，是一款**轻型的数据库**，是**遵守ACID的关系型数据库管理系统**，它包含在一个相对小的C库中。它是D.RichardHipp建立的公有领域项目。它的设计目标是嵌入式的，而且已经在很多嵌入式产品中使用了它，它占用资源非常的低，在嵌入式设备中，可能只需要几百K的内存就够了。它能够支持Windows/Linux/Unix等等主流的操作系统，同时能够跟很多程序语言相结合，比如 Tcl、C#、PHP、Java等，还有ODBC接口，同样比起Mysql、PostgreSQL这两款开源的世界著名数据库管理系统来讲，它的处理速度比他们都快。SQLite第一个Alpha版本诞生于2000年5月。 至2019年已经有19个年头，SQLite也迎来了一个版本 SQLite 3已经发布。[[摘自SQLite百度百科](https://baike.baidu.com/item/SQLite/375020?fr=aladdin)]

## 功能特性[#](https://www.cnblogs.com/desireyang/p/12102143.html#1912286191)

- ACID事务
- 零配置 – 无需安装和管理配置
- 支持数据库大小至2TB
- 比一些流行的数据库在大部分普通数据库操作要快
- 独立: 没有额外依赖
- 支持多种开发语言，C, C++, PHP, Perl, Java, C#,Python, Ruby等

## 数据类型[#](https://www.cnblogs.com/desireyang/p/12102143.html#3962674983)

- Typelessness(无类型)
- 支持常用的数据库类型

## 使用Python3操作SQLite[#](https://www.cnblogs.com/desireyang/p/12102143.html#263716188)

###### python2.5以后的安装包已经自带SQLite3的软件包了，所以直接导入使用即可。

```python
Copyimport sqlite3
```

### 第一步：连接数据库（如果数据库不存在就会创建新的数据库）[#](https://www.cnblogs.com/desireyang/p/12102143.html#583940922)

```python
Copy# 可以指定创建数据库的路径，比如可以写成sqlite3.connect(r"E:\DEMO.db")
con = sqlite3.connect("DEMO.db")
```

### 第二步：创建游标[#](https://www.cnblogs.com/desireyang/p/12102143.html#3114932558)

```python
Copycur = con.cursor()
```

### 第三步：CURD操作[#](https://www.cnblogs.com/desireyang/p/12102143.html#2108210540)

#### (一) 创建表[#](https://www.cnblogs.com/desireyang/p/12102143.html#595825351)

```python
Copysql = "CREATE TABLE IF NOT EXISTS test(id INTEGER PRIMARY KEY,name TEXT,age INTEGER)"
cur.execute(sql)
```

###### 创建完数据库和表后的结构示意图：

[![img](https://img2018.cnblogs.com/blog/1895590/201912/1895590-20191226134612974-484080118.png)](https://img2018.cnblogs.com/blog/1895590/201912/1895590-20191226134612974-484080118.png)

#### (二) 添加数据（需要提交）[#](https://www.cnblogs.com/desireyang/p/12102143.html#3627398380)

```python
Copy# ①：添加单条数据
data = "1,'Desire',5"
cur.execute('INSERT INTO test VALUES (%s)' % data)
# ②：添加单条数据
cur.execute("INSERT INTO test values(?,?,?)", (6, "zgq", 20))
# ③：添加多条数据
cur.executemany('INSERT INTO test VALUES (?,?,?)', [(3, 'name3', 19), (4, 'name4', 26)])
```

###### 操作后的数据库如下图：

[![img](https://img2018.cnblogs.com/blog/1895590/201912/1895590-20191226135122955-1204971198.png)](https://img2018.cnblogs.com/blog/1895590/201912/1895590-20191226135122955-1204971198.png)

#### (三) 更新数据（需要提交）[#](https://www.cnblogs.com/desireyang/p/12102143.html#1448176065)

```python
Copy# 方式一
cur.execute("UPDATE test SET name=? WHERE id=?", ("nihao", 1))
# 方式二
cur.execute("UPDATE test SET name='haha' WHERE id=3")
```

###### 操作后的数据库如下图：

[![img](https://img2018.cnblogs.com/blog/1895590/201912/1895590-20191226135355310-954922607.png)](https://img2018.cnblogs.com/blog/1895590/201912/1895590-20191226135355310-954922607.png)

#### (四) 删除数据（需要提交）[#](https://www.cnblogs.com/desireyang/p/12102143.html#2140935766)

```python
Copy# 方式一
cur.execute("DELETE FROM test WHERE id=?", (1,))
# 方式二
cur.execute("DELETE FROM test WHERE id=3")
```

###### 操作后的数据库如下图：

[![img](https://img2018.cnblogs.com/blog/1895590/201912/1895590-20191226135741614-666000209.png)](https://img2018.cnblogs.com/blog/1895590/201912/1895590-20191226135741614-666000209.png)

#### (五) 查询数据[#](https://www.cnblogs.com/desireyang/p/12102143.html#1263242929)

###### 数据库中的数据如下：

[![img](https://img2018.cnblogs.com/blog/1895590/201912/1895590-20191226140336886-1139590765.png)](https://img2018.cnblogs.com/blog/1895590/201912/1895590-20191226140336886-1139590765.png)

##### 1、查询所有数据

```python
Copycur.execute("select * from Test")
print(cur.fetchall())
```

###### 结果如下：

[![img](https://img2018.cnblogs.com/blog/1895590/201912/1895590-20191226140355990-1748105899.png)](https://img2018.cnblogs.com/blog/1895590/201912/1895590-20191226140355990-1748105899.png)

##### 2、查询第一条数据

```python
Copycur.execute("select * from Test")
print(cur.fetchone())
```

###### 结果如下：

[![img](https://img2018.cnblogs.com/blog/1895590/201912/1895590-20191226140548022-1768564791.png)](https://img2018.cnblogs.com/blog/1895590/201912/1895590-20191226140548022-1768564791.png)

##### 3、查询多条数据

```python
Copyprint(cur.fetchmany(3))
```

###### 结果如下：

[![img](https://img2018.cnblogs.com/blog/1895590/201912/1895590-20191226140711301-1524932239.png)](https://img2018.cnblogs.com/blog/1895590/201912/1895590-20191226140711301-1524932239.png)

### 第四步：事务的提交和回滚[#](https://www.cnblogs.com/desireyang/p/12102143.html#2917059176)

##### 提交

```python
Copycon.commit()
```

##### 回滚

```python
Copycon.rollback()
```

### 第五步：断开会话连接，释放资源[#](https://www.cnblogs.com/desireyang/p/12102143.html#3857226721)

```python
Copy# 关闭游标
cur.close()
# 断开数据库连接
con.close()
```

## 关于SQLite数据类型的Typelessness(无类型)[#](https://www.cnblogs.com/desireyang/p/12102143.html#1225226645)

#### 创建一个无类型的表(创建表的SQL语句字段不加类型即可)[#](https://www.cnblogs.com/desireyang/p/12102143.html#2320993552)

```python
Copysql = "CREATE TABLE IF NOT EXISTS user(clo_1,clo_2,clo_3)"
cur.execute(sql)
```

###### 创建成功后的结构：

[![img](https://img2018.cnblogs.com/blog/1895590/201912/1895590-20191226142711525-1023423298.png)](https://img2018.cnblogs.com/blog/1895590/201912/1895590-20191226142711525-1023423298.png)

#### 添加数据的时候就可以添加任意类型的数据（没有限制）[#](https://www.cnblogs.com/desireyang/p/12102143.html#2827649931)

```python
Copysql = 'INSERT INTO user VALUES (?,?,?)'
data = [(9, 'name3', 19), ('name4', 26, "nihao"), ('nihao', 3, 2.89)]
cur.executemany(sql, data)
con.commit()
```

###### 添加成功后的表内容：

[![img](https://img2018.cnblogs.com/blog/1895590/201912/1895590-20191226143117710-175348911.png)](https://img2018.cnblogs.com/blog/1895590/201912/1895590-20191226143117710-175348911.png)

\-----------------------------------------------------------------------------
***以上便是SQLite在Python中简单的操作，查看表结构可以用[Navicat for SQLite](https://www.navicat.com.cn/products)可视化工具**