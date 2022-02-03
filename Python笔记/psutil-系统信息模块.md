# psutil-系统信息模块


```
import psutil  # 载入psutil模块
import smtplib  # 载入smtplib模块
from email.mime.text import MIMEText
from email.header import Header

# 定义检查的服务名称和端口
services = {
    'nginx': 80,
    'php': 9000,
    'mysql': 3306,
    'redis': 6379,
    'zabbix': 10050,
}

# 获取cpu信息
def cpu_info():  # 定义CPU使用率函数
    cpu = '%.2f%%' % psutil.cpu_percent(1)  # 截取1秒内cpu的使用率。
    return cpu
    # %.2f表示输出浮点数并保留两位小数。%%表示直接输出一个%。

# 获取内存信息
def mem_info():  # 定义内存使用率
    mem = psutil.virtual_memory()
    mem_per = '%.2f%%' % mem[2]
    mem_total = str(int(mem[0] / 1024 / 1024)) + 'M'
    mem_used = str(int(mem[3] / 1024 / 1024)) + 'M'
    info = {
        'mem_per': mem_per,
        'mem_total': mem_total,
        'mem_used': mem_used
    }  # 建立字典，方便后边调用值
    return info

# 获取硬盘信息
def disk_info():
    all_disk = psutil.disk_usage('/')
    disk_per = '%.2f%%' % all_disk[3]
    disk_total = str(int(all_disk[0] / 1024 / 1024 / 1024)) + 'Gb'
    disk_used = str(int(all_disk[1] / 1024 / 1024 / 1024)) + 'Gb'
    info = {
        'disk_per': disk_per,
        'disk_total': disk_total,
        'disk_used': disk_used,
    }
    return info

# 获取网卡信息
def network_info():
    network = psutil.net_io_counters()
    network_sent = str(int(network[0] / 1024 / 1024)) + 'Mb'
    network_resv = str(int(network[1] / 1024 / 1024)) + 'Mb'
    info = {
        'network_sent': network_sent,
        'network_resv': network_resv
    }
    return info

# 获取服务端口信息
def port_check():
    info = dict.fromkeys(services.keys(), 'Off!')
    for i in services.items():
        for j in psutil.net_connections():
            if j.status == 'LISTEN' and j.laddr.port == i[1]:
                info[i[0]] = 'ok'
    return info

# 放送报警邮件
def send_mail(message):  # 定义发邮件函数
    a = ['1846016614@qq.com', ]  # 定义一个列表a为接收方
    sender = '1846016614@qq.com'  # 发送方
    for i in a:
        receiver = i
        subject = '报警'
        username = '1846016614@qq.com'  # 发送方使用的账户名
        password = 'ddysyvabofocehhb'  # 发送方的授权码密码
        msg = MIMEText(message, 'plain', 'utf-8')
        msg['Subject'] = Header(subject, 'utf-8')
        msg['From'] = sender
        msg['To'] = receiver
        smtp = smtplib.SMTP_SSL('smtp.qq.com')
        smtp.ehlo('smtp.qq.com')  # 邮件服务器
        smtp.login(username, password)
        smtp.sendmail(sender, receiver, msg.as_string())
    smtp.quit()
    # 接收方只有一个用户时，网易、QQ都好使；接收方为多用户时，QQ好使


def main():  # 主函数
    cpu = cpu_info()
    mem = mem_info()
    disk = disk_info()
    network = network_info()
    port = port_check()
    port_result = '    ------------------------------\n    服务状态：\n'
    for each in port.items():
        port_result += f'\t{each[0]}：\t\t{each[1]}\n'
    info = port_result + f'''
    ------------------------------
    CPU使用率：\t\t{cpu}
    ------------------------------
    内存使用率：\t{mem.get('mem_per')}
    内存总容量：\t{mem.get('mem_total')}
    已使用内存：\t{mem.get('mem_used')}
    ------------------------------
    磁盘使用率：\t{disk.get('disk_per')}
    磁盘总容量：\t{disk.get('disk_total')}
    已使用磁盘：\t{disk.get('disk_used')}
    ------------------------------
    网卡发送流量：\t{network.get('network_sent')}
    网卡接收流量：\t{network.get('network_resv')}
    ------------------------------

    '''
    flag = 0
    services_msg = ''
    for i in port.items():
        if not i[1]:
            flag = 1
            services_msg += i[0] + '报错\n'
    if flag:
        info = services_msg + '\n' + info
        # 根据需求进行阈值设置
        # send_mail(info) 
    elif int(cpu[:-4]) > 80:
        info = 'CPU过载\n' + info
        # 根据需求进行阈值设置
        # send_mail(info)  
    elif int(mem.get('mem_per')[:-4]) > 80:
        info = '内存过载\n' + info
        # 根据需求进行阈值设置
        # send_mail(info)  
    print(info)


if __name__ == '__main__':
    main()



```
