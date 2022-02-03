# itertools-排列组合

https://www.cnblogs.com/xiao-apple36/p/10861830.html

[toc]

### 吃豆问题

```
import itertools

# 一共有5个豆子，一天吃1或2个，不能连续两天吃2个，问有多少种结果
s = 5
res_list = list()
# 一天吃[1, 2]，列出5天内所有可能的排列
for i in itertools.product([1, 2], repeat=s):
    for index in range(s):
        # 不能连续两天吃2个
        if i[index - 1] == i[index] == 2:
            break
        # 当res_list前面的部分之和等于5时，即为一种可能
        if sum(i[:index + 1]) == s:
            res_list.append(i[:index + 1])

for i in set(res_list):
    print(i)
# # 排序 + 去重
# result = list(set(res_list))
# result.sort(key=res_list.index)
# for i in result:
#     print(i)

```

### 递归实现笛卡尔积
- https://blog.csdn.net/Matrix_cc/article/details/109744803
- https://www.lfhacks.com/tech/python-recursive-generator/

```
# 实现笛卡尔乘积

def combi(seq):
    if not seq:
        yield []
    else:
        for element in seq[0]:
            for rest in combi(seq[1:]):
                yield [element] + rest


n = [[1, 2], [1, 2], [1, 2]]
print(list(combi(n)))
# [[1, 1, 1], [1, 1, 2], [1, 2, 1], [1, 2, 2], [2, 1, 1], [2, 1, 2], [2, 2, 1], [2, 2, 2]]

```

### 回溯法实现笛卡尔积


```
# 这里使用layer的原因是因为防止子列表出现空列表的情况，如果不会出现子列表是空列表的情况，可以在temp的长度等于arr的长度的时候，就把temp添加到结果集合里去
# 参考：https://blog.csdn.net/ylyg050518/article/details/78787432

arr = [['a','b],['c'],['d','e','g']]
res = []
layer = len(arr)
def func(arr,index,temp,layer):
    if len(temp)==layer:
        res.append(temp[:])
    else:
        if not arr[index]:
            layer -= 1
            func(arr, index + 1, temp,layer)
        else:
            for i in range(len(arr[index])):
                temp.append(arr[index][i])
                func(arr,index+1,temp,layer)
                temp.pop()
func(arr,0,[],layer)
print(res)
```



### 笛卡尔积：itertools.product(*iterables[, repeat])

```
import itertools
for i in itertools.product('BCDEF', repeat = 2):
    print(''.join(i),end=",")
print('\n')
 
# 输出 BB BC BD BE BF CB CC CD CE CF DB DC DD DE DF EB EC ED EE EF FB FC FD FE FF
```

　　两个元组进行笛卡尔积：

```
import itertools
a = (1, 2)
b = ('A', 'B', 'C')
c = itertools.product(a,b)
for i in c:
    print(i,end=",")
 
# 输出(1, 'A') (1, 'B') (1, 'C') (2, 'A') (2, 'B') (2, 'C')
```


### 实现：product()函数 返回一个生成器，它的等效代码为


```
def product(*args, **kwds):
    # product('ABCD', 'xy') --> Ax Ay Bx By Cx Cy Dx Dy
    # product(range(2), repeat=3) --> 000 001 010 011 100 101 110 111
    pools = map(tuple, args) * kwds.get('repeat', 1)
    result = [[]]
    for pool in pools:
        result = [x+[y] for x in result for y in pool]
    for prod in result:
        yield tuple(prod)
```


### 排列：itertools.permutations(iterable[, r])

```
import itertools
for i in itertools.permutations('BCD', 2):
    print(''.join(i),end=",")
# 输出 BC BD CB CD DB DC
print('\n')
```


### 组合：itertools.combinations(iterable, r)

```
import itertools
for i in itertools.combinations('BCDEF', 2):
    print(''.join(i),end=" ")
# 输出 BC BD BE BF CD CE CF DE DF EF
print('\n')
```

### 组合（包含自身重复）：itertools.combinations_with_replacement(iterable, r)

```
import itertools
for i in itertools.combinations_with_replacement('ABC', 3):
    print (''.join(i),end=' ')
 
# 输出 AAA AAB AAC ABB ABC ACC BBB BBC BCC CCC
print('\n')
```


### 举例

```
'BCDEF五个字母组合问题'
import itertools
 
print("1个组合：")
for i, val in enumerate(list(itertools.combinations('BCDEF', 1))):
    print("序号：%s   值：%s" % (i + 1, ''.join(val)))
print("2个组合：")
for i, val in enumerate(list(itertools.combinations('BCDEF', 2))):
    print("序号：%s   值：%s" % (i + 1, ''.join(val)))
print("3个组合：")
for i, val in enumerate(list(itertools.combinations('BCDEF', 3))):
    print("序号：%s   值：%s" % (i + 1, ''.join(val)))
print("4个组合：")
for i, val in enumerate(list(itertools.combinations('BCDEF', 4))):
    print("序号：%s   值：%s" % (i + 1, ''.join(val)))
print("5个组合：")
for i, val in enumerate(list(itertools.combinations('BCDEF', 5))):
    print("序号：%s   值：%s" % (i + 1, ''.join(val)))
```

 