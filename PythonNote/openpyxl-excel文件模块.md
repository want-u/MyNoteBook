# openpyxl-excel文件模块


```
import openpyxl
from openpyxl.styles import PatternFill
```

```
def get_product_data(excel_file):
    """
    拿到excel_file表格数据
    :param excel_file: 数据文件
    :return: 数据
    """
    # 获取excel_file数据
    new_wb = openpyxl.load_workbook(excel_file)
    new_sheet = new_wb.worksheets[0]
    # min_row=2 跳过第一行表头
    for row in new_sheet.iter_rows(min_row=2):
        yield row
```

```
def create_excel():
    # 创建新的Excel文件，写入表格表头
    # 表头
    title = """xxx xxx xxx""".split(
        ' ')
    # 创建新的Excel文件
    wb = openpyxl.Workbook()
    # 激活一个sheet
    ws = wb.active
    # 为sheet设置一个title
    ws.title = '周报'
    ws.append([i for i in title])
    return wb, ws
```

```
def check_data(ws):
    """
    质检数据
    :param ws: 分好产品的sheet
    """
    # 质检不合格的行
    check_result = []
    for row in ws:
        for cell in row:
            try:
                if cell.coordinate[0] == 'X' and '异常' in cell.value:
                    # print(cell.coordinate, cell.value)  # X13 ID异常+
                    check_result.append(int(cell.coordinate[1:]))
            except:
                pass

    fill_title = PatternFill(fill_type='solid', fgColor='8DB6CD')
    fill_check = PatternFill(fill_type='solid', fgColor='ff9900')
    # 表头上色 b4a7d6
    for i in range(1, ws.max_column + 1):
        ws.cell(1, i).fill = fill_title

    # 质检上色 FFFF00
    for row in check_result:
        for cell in range(1, ws.max_column + 1):
            ws.cell(row, cell).fill = fill_check
```
