# Kali-网络配置

## 1. ipconfig

```
说一下内网ip ，如果你的网关是  `192.168.1.1`

那么你的内网ip是 `192.168.1.XXX ` , 前三个数字都一样，最后一个数字不
一样，一台机子分一个数字。
```



## 2. 正式开始配置

### 2.1 打开终端  输入命令 

```
#意思是用vim打开文件，然后编辑IP地址。
vi /etc/network/interfaces   
```

### 2.2 进入编辑模式


```
先按一下 i 键  编辑好了再单击Esc 键，再按一下 ： + q 完毕。

修改 /etc/resolv.conf 文件

重启网卡
```


### 2.3 在终端下输入

```
/etc/init.d/networking restart 
```

回车，进行重启网卡
进行这一步之后，kali的右上角会提示网络已断开，并会显示一个红色的X。如果等了1分钟X还没去掉，请重启，做好失败的准备。



## 3. 重启Kali


