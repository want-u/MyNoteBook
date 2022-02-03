## CSV文件的读写
[toc]
#### 导入库
    
    import csv

#### csv读取：列表读取

    def csv_read_list():
        with open('csv_test.csv', 'r', encoding='utf-8') as f:
            # 返回一个reader生成器， 每一条数据为一个列表
            reader = csv.reader(f)
            # 调用next函数获取第一行的表头
            title = next(reader)
            print(title)
            # 表格信息，单列使用下标获取
            for i in reader:
                print(i)

#### csv写入：列表写入

    def csv_write_list():
        headers = ['name', 'age', 'height']
        data1 = ['张三', 18, 180]
        data2 = ['da张三', 12, 170]
        with open('csv_test.csv', 'w', encoding='utf-8', newline='') as f:
            # 创建writer对象
            writer = csv.writer(f)
            # 写入表头
            writer.writerow(headers)
            # 写入数据， 写入多行使用writerows方法
            writer.writerow(data1)
            writer.writerow(data2)

#### csv读取：字典读取

    def csv_read_dict():
        with open('csv_test.csv', 'r', encoding='utf-8') as f:
            # 返回一个reader生成器， 每一条数据为一个字典
            reader = csv.DictReader(f)
            # 表格信息，单列使用key获取
            for i in reader:
                print(i)

#### csv写入：字典写入

    def csv_write_dict():
        headers = ['name', 'age', 'height']
        data1 = {'name': '张三', 'age': 18, 'height': 180}
        data2 = {'name': '张da三', 'age': 12, 'height': 170}
        with open('csv_test.csv', 'w', encoding='utf-8', newline='') as f:
            # 创建writer对象
            writer = csv.DictWriter(f, headers)
            # 写入表头
            writer.writeheader()
            # 写入数据， 写入多行使用writerows方法
            writer.writerow(data1)
            writer.writerow(data2)