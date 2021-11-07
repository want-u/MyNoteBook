# TigerVNC安装

## 1. 服务端安装好桌面环境

## 2. 安装tigervnc server
    yum -y install tigervnc-server tigervnc


## 3. 修改配置文件
    cp /lib/systemd/system/vncserver@.service /etc/systemd/system/vncserver@:1.service
    
    vim编辑，这里写你linux用户名
    

```
ExecStartPre=/bin/sh -c '/usr/bin/vncserver -kill %i > /dev/null 2>&1 || :'
ExecStart=/usr/sbin/runuser -l root -c "/usr/bin/vncserver %i"
PIDFile=/root/.vnc/%H%i.pid
ExecStop=/bin/sh -c '/usr/bin/vncserver -kill %i > /dev/null 2>&1 || :'
```

 
## 4. 设置远程密码
    vncpasswd

## 5. 启动vnc服务
    systemctl start vncserver@:1.service


## 6. 查看监听端口，确认是否启动
    netstat -anput

    如监听5901端口

## 7. 客户端远程
    连接：192.168.10.11:5901

## 8. 关闭服务
    vncserver -kill :1
    # 或者systemctl stop vncserver@:1.service


## 9. VNC连接
    VNC是个不错的工具，今天装KVM虚拟机，想用VNC 连接KVM装系统的，结果输入IP：端口后，连上后直接闪退或颜色不正常，可能是ColourLevel的问题
    
    解决方法
    依次点Option-->Advanced-->Expert找到ColourLevel，默认值是pal8，修改为rgb222或full。