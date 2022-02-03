# 优化if-elif语句

使用break，如过关斩将一般，不满足条件直接return结束
```
def print_data(*args):
    if len(args) == 2:
        return "{0:.2f}GB ({1:.2f}TB)".format(args[0], args[1])
    if len(args) == 3:
        print('{0:<20} ====> {1:>13.2f} GB {2:>10.2f} TB'.format(args[0], args[1], args[2]))
        return
    if len(args) == 4:
        print('{0:<20} ====> {1:>13.2f} GB {2:>10.2f} TB ({3:.2f})%'.format(
            args[0], args[1], args[2], (args[1] / args[3] * 100)))
        return

```
