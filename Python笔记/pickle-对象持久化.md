# cPickle/pickle-对象持久化

https://www.cnblogs.com/keye/p/8779626.html

[toc]

## **cPickle模块：**

在python中，一般可以使用pickle类来进行python对象序列化，而cPickle提供了一个更快速简单的接口，如python文档所说：“cPickle - A faster pickle”。

cPickle可以对任意一种类型的python对象进行序列化操作，比如：list, dict,甚至是一个类的对象等。而所谓的序列化，是为了能完整地保存并能够完全可逆的恢复。在cPickle中，主要有4个函数：

### **1. dump**

将python对象序列化保存到本地的文件

```
import cPickle

data = range(1000)
cPickle.dump(data, open("test\\data.pkl", "wb"))
```

dump函数需要指定两个参数，第一个是需要序列化的python对象名称，第二个是本地的文件，需要注意的是，在这里需要使用open函数打开一个文件，并指定“写”操作。

 

### **2. load**

载入本地文件，恢复python对象

```
data = cPickle.load(open("test\\data.pkl", "rb"))
```

使用open函数打开本地的一个文件，并指定“读”操作。

 

### **3. dumps**

将python对象序列化保存到一个字符串变量中

```
data_string = cPickle.dumps(data)
```

 

### **4. loads**

载入字符串，恢复python对象

```
data = cPickle.loads(data_string)
```

 

 

## **pickle与cpickle比较**

pickle完全用python来实现的，cpickle用C来实现的，cpickle的速度要比pickle快好多倍。

 

## **pickle模块：**

### **1. pickle.dump(obj, file, [,protocol])**

- 含义：pickle.dump（对象，文件，[使用协议]）
- 将要持久化的数据“对象”，保存到“文件”中，使用有3种协议，索引0为ASCII，1为旧式二进制，2为新式二进制协议，不同之处在于2要更高效一些。
- 默认dump方法使用0做协议

### **2. pickle.load(file)**

- 含义：pickle.load(文件)，将file中的对象序列化读出。
- 从“文件”中读取字符串，将他们反序列化转换为python的数据对象，可以像操作数据类型的这些方法来操作它们；　　

### **3. pickle.dumps(obj[, protocol])**

- 函数的功能：将obj对象序列化为string形式，而不是存入文件中。
- obj：想要序列化的obj对象。
- protocal：如果该项省略，则默认为0。如果为负值或HIGHEST_PROTOCOL，则使用最高的协议版本。

### **4. pickle.loads(string)**

- 函数的功能：从string中读出序列化前的obj对象。
- string：文件名称。

dump() 与 load() 相比 dumps() 和 loads() 还有另一种能力：dump（）函数能一个接一个地将几个对象序列化存储到同一个文件中，随后调用load（）来以同样的顺序反序列化读出这些对象。

 

实例：http://www.mamicode.com/info-detail-2079993.html