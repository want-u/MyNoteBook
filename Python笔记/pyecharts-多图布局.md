# pyecharts-多图布局
https://blog.csdn.net/qq_42374697/article/details/105943507
[toc]

## Page-竖排布局


```
# coding:utf-8
from pyecharts.charts import Bar, Page
from pyecharts import options as opts
import os
if __name__ == '__main__':
    # pyecharts=1.8.1
    # 安装 python -m pip install pyecharts
    page = Page(layout=Page.DraggablePageLayout)
    bar = Bar()
    bar.add_xaxis(["衬衫", "毛衣", "领带", "裤子", "风衣", "高跟鞋", "袜子"])
    bar.add_yaxis("商家A", [114, 55, 27, 101, 125, 27, 105])
    bar.add_yaxis("商家B", [57, 134, 137, 129, 145, 60, 49])
    bar.add_yaxis("商家C", [57, 134, 137, 129, 145, 60, 49])
    bar.set_global_opts(title_opts=opts.TitleOpts(title="某商场销售情况1"))
    bar1 = Bar()
    bar1.add_xaxis(["衬衫", "毛衣", "领带", "裤子", "风衣", "高跟鞋", "袜子"])
    bar1.add_yaxis("商家A", [114, 55, 27, 101, 125, 27, 105])
    bar1.add_yaxis("商家B", [57, 134, 137, 129, 145, 60, 49])
    bar1.add_yaxis("商家C", [57, 134, 137, 129, 145, 60, 49])
    bar1.set_global_opts(title_opts=opts.TitleOpts(title="某商场销售情况2"))
    # 将柱状图bar和bar1添加到page页面中，这样就可以将两个图标绘制成一个html中了
    page.add(bar, bar1)
    page.render("test.html")
    os.system("test.html")
```
![多个表格绘制到一个页面上的效果图](https://gitee.com/luoxian1011/pictures/raw/master/20200812183321191.png)

## Grid-并行多图

具体请参见：[Grid并行多图](http://pyecharts.org/#/zh-cn/composite_charts?id=grid：并行多图)

### （1）水平放置

![在这里插入图片描述](https://img-blog.csdnimg.cn/20200508094356814.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQyMzc0Njk3,size_16,color_FFFFFF,t_70#pic_center)

```python
from pyecharts import options as opts
from pyecharts.charts import Grid, Line, Scatter
from pyecharts.faker import Faker
from pyecharts.globals import ThemeType

# 散点图
scatter = (
    Scatter()
    .add_xaxis(Faker.choose()) # x轴
    .add_yaxis("商家A", Faker.values()) 
    .add_yaxis("商家B", Faker.values())
    # 全局配置项
    .set_global_opts(
        title_opts=opts.TitleOpts(title="Grid-Scatter"),
        # 设置图例位置
        legend_opts=opts.LegendOpts(pos_left="20%"),
    )
)

# 折线图
line = (
    Line()
    .add_xaxis(Faker.choose()) # x轴
    .add_yaxis("商家A", Faker.values())
    .add_yaxis("商家B", Faker.values())
    .set_global_opts(
        title_opts=opts.TitleOpts(title="Grid-Line", pos_right="5%"),
        legend_opts=opts.LegendOpts(pos_right="20%"),
    )
)

# Grid并行多图
grid = (
    Grid()
    
    .add(
        scatter, # 图表实例，仅 `Chart` 类或者其子类
        
        # 直角坐标系网格配置项
        # grid 组件离容器左侧的距离。
        # left 的值可以是像 20 这样的具体像素值，可以是像 '20%' 这样相对于容器高宽的百分比，
        # 也可以是 'left', 'center', 'right'。
        # 如果 left 的值为'left', 'center', 'right'，组件会根据相应的位置自动对齐。
        grid_opts=opts.GridOpts(pos_left="55%"))
    
    
    .add(
        line, # 图表实例，仅 `Chart` 类或者其子类
        
        # grid 组件离容器右侧的距离。
        # right 的值可以是像 20 这样的具体像素值，可以是像 '20%' 这样相对于容器高宽的百分比。
        grid_opts=opts.GridOpts(pos_right="55%"))
    .render("grid_horizontal.html")
)

12345678910111213141516171819202122232425262728293031323334353637383940414243444546474849505152535455
```

### （2）竖直放置

![在这里插入图片描述](https://img-blog.csdnimg.cn/20200508095956268.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQyMzc0Njk3,size_16,color_FFFFFF,t_70#pic_center)

```python
from pyecharts import options as opts
from pyecharts.charts import Bar, Grid, Line
from pyecharts.faker import Faker
from pyecharts.globals import ThemeType

# 条形图
bar = (
    Bar()
    .add_xaxis(Faker.choose())
    .add_yaxis("商家A", Faker.values())
    .add_yaxis("商家B", Faker.values())
    .set_global_opts(title_opts=opts.TitleOpts(title="Grid-Bar"))
)

# 折线图
line = (
    Line()
    .add_xaxis(Faker.choose())
    .add_yaxis("商家A", Faker.values())
    .add_yaxis("商家B", Faker.values())
    .set_global_opts(
        # 标题位置
        title_opts=opts.TitleOpts(title="Grid-Line", pos_top="48%"),
        # 图例位置
        legend_opts=opts.LegendOpts(pos_top="48%"),
    )
)

grid = (
    Grid(init_opts=opts.InitOpts(theme=ThemeType.INFOGRAPHIC))
    # 通过设置图形相对位置，来调整是整个并行图是竖直放置，还是水平放置
    .add(bar, grid_opts=opts.GridOpts(pos_bottom="60%"))
    .add(line, grid_opts=opts.GridOpts(pos_top="60%"))
    .render("grid_vertical.html")
)

123456789101112131415161718192021222324252627282930313233343536
```

### （3）Grid_geo_bar

![在这里插入图片描述](https://img-blog.csdnimg.cn/20200508095228712.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQyMzc0Njk3,size_16,color_FFFFFF,t_70#pic_center)

```python
from pyecharts import options as opts
from pyecharts.charts import Bar, Geo, Grid
from pyecharts.faker import Faker

# 条形图
bar = (
    Bar()
    .add_xaxis(Faker.choose())
    .add_yaxis("商家A", Faker.values())
    .add_yaxis("商家B", Faker.values())
    # 图例位置
    .set_global_opts(legend_opts=opts.LegendOpts(pos_left="20%"))
)


# 动态热力地图
geo = (
    Geo()
    .add_schema(maptype="china")
    .add("geo", [list(z) for z in zip(Faker.provinces, Faker.values())])
    # 标签
    .set_series_opts(label_opts=opts.LabelOpts(is_show=False))
    .set_global_opts(
        # 映射
        visualmap_opts=opts.VisualMapOpts(),
        # 主题
        title_opts=opts.TitleOpts(title="Grid-Geo-Bar"),
    )
)

# Grid并行多图
grid = (
    Grid()
    # 添加条形图  并设置位置
    .add(bar, grid_opts=opts.GridOpts(pos_top="50%", pos_right="75%"))
    
    # 添加地图
    .add(geo, grid_opts=opts.GridOpts(pos_left="70%"))
    .render("grid_geo_bar.html")
)

1234567891011121314151617181920212223242526272829303132333435363738394041
```

### （4）重叠（组合）图

![在这里插入图片描述](https://img-blog.csdnimg.cn/20200506085742251.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQyMzc0Njk3,size_16,color_FFFFFF,t_70#pic_center)

```python
from pyecharts import options as opts
from pyecharts.charts import Bar, Line
from pyecharts.faker import Faker

# 数据
v1 = [2.0, 4.9, 7.0, 23.2, 25.6, 76.7, 135.6, 162.2, 32.6, 20.0, 6.4, 3.3]
v2 = [2.6, 5.9, 9.0, 26.4, 28.7, 70.7, 175.6, 182.2, 48.7, 18.8, 6.0, 2.3]
v3 = [2.0, 2.2, 3.3, 4.5, 6.3, 10.2, 20.3, 23.4, 23.0, 16.5, 12.0, 6.2]

# 条形图
bar = (
    Bar()
    .add_xaxis(Faker.months)  # ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月']
    # 默认轴索引都为0
    .add_yaxis("蒸发量", v1)
    .add_yaxis("降水量", v2)
    
    # 提前配置 折线图 的 y 轴
    .extend_axis(
        # 对新增y轴设置，轴标签为 摄氏度 间隔为5°C
        yaxis=opts.AxisOpts(
            axislabel_opts=opts.LabelOpts(formatter="{value} °C"), interval=5
        )
    )
    
    # 系列配置项   不显示标签
    .set_series_opts(label_opts=opts.LabelOpts(is_show=False))
    
    # 全局配置项
    .set_global_opts(
        # 标题
        title_opts=opts.TitleOpts(title="Overlap-bar+line"),
        # 对条形图的 y 轴进行设置
        yaxis_opts=opts.AxisOpts(axislabel_opts=opts.LabelOpts(formatter="{value} ml")),
    )
)

# 折线图
line = (
        Line()
        .add_xaxis(Faker.months) # x轴
        .add_yaxis(
            "平均温度", 
            v3, 
            yaxis_index=1  # 上面的条形图默认索引为0，这里设置折线图y 轴索引为1
            )
        )

bar.overlap(line)  # 将折线图重叠到条形图
bar.render("overlap_bar_line.html")

123456789101112131415161718192021222324252627282930313233343536373839404142434445464748495051
```

### （5）多y轴

- 所有轴的索引初始值都是0，在多个轴的情况下，按照添加顺序进行排序

![在这里插入图片描述](https://img-blog.csdnimg.cn/20200508104303646.gif#pic_center)

```python
from pyecharts import options as opts
from pyecharts.charts import Bar, Grid, Line

# x 轴数据
x_data = ["{}月".format(i) for i in range(1, 13)]
# ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月']


# 条形图
bar = (
    Bar()
    .add_xaxis(x_data)
    
    # y1
    .add_yaxis(
        "蒸发量",# 系列名称
        [2.0, 4.9, 7.0, 23.2, 25.6, 76.7, 135.6, 162.2, 32.6, 20.0, 6.4, 3.3], # 系列数据项
        # 添加第一个轴，索引为0,（默认也是0）
        yaxis_index=0,
        
        color="#d14a61", # 系列 label 颜色
    )
    
    
    # y2
    .add_yaxis(
        "降水量", # 系列名称
        [2.6, 5.9, 9.0, 26.4, 28.7, 70.7, 175.6, 182.2, 48.7, 18.8, 6.0, 2.3], # 系列数据项
        # 添加第二个轴，索引设为1
        yaxis_index=1,
        color="#5793f3", # 系列 label 颜色
    )
    
    
    # 对 y1 轴进行更加细致的配置
    .extend_axis(
        yaxis=opts.AxisOpts(
            name="蒸发量", # 坐标轴名称
            type_="value", # 坐标轴类型  'value': 数值轴，适用于连续数据。
            min_=0,  # 坐标轴刻度最小值
            max_=250,  # 坐标轴刻度最大值
            position="right",  # 轴的位置  右侧
            # 轴线配置
            axisline_opts=opts.AxisLineOpts(
                # 轴线颜色
                linestyle_opts=opts.LineStyleOpts(color="#d14a61")
            ),
            # 轴标签显示格式
            axislabel_opts=opts.LabelOpts(formatter="{value} ml"),
        )
    )
    
    # 提前配置 折线图 的 y 轴
    .extend_axis(
        yaxis=opts.AxisOpts(
            type_="value",
            name="温度",
            min_=0,
            max_=25,
            position="left",
            axisline_opts=opts.AxisLineOpts(
                linestyle_opts=opts.LineStyleOpts(color="#675bba")
            ),
            axislabel_opts=opts.LabelOpts(formatter="{value} °C"),
            # 分割线配置
            splitline_opts=opts.SplitLineOpts(
                # 显示分割线
                is_show=True, 
                # 线样式配置 opacity 图形透明度。支持从 0 到 1 的数字，为 0 时不绘制该图形。
                linestyle_opts=opts.LineStyleOpts(opacity=1)
            ),
        )
    )
    
    
    .set_global_opts(
        # 对 y2 轴进行更加细致的配置
        yaxis_opts=opts.AxisOpts(
            name="降水量",
            min_=0,
            max_=250,
            position="right",
            offset=80, # Y 轴相对于默认位置的偏移，在相同的 position 上有多个 Y 轴的时候有用。
            axisline_opts=opts.AxisLineOpts(
                linestyle_opts=opts.LineStyleOpts(color="#5793f3")
            ),
            axislabel_opts=opts.LabelOpts(formatter="{value} ml"),
        ),
        title_opts=opts.TitleOpts(title="Grid-多 Y 轴示例"),
        # 提示框配置
        tooltip_opts=opts.TooltipOpts(trigger="axis", axis_pointer_type="cross"),
    )
)


# 折线图
line = (
    Line()
    .add_xaxis(x_data)
    .add_yaxis(
        "平均温度",
        [2.0, 2.2, 3.3, 4.5, 6.3, 10.2, 20.3, 23.4, 23.0, 16.5, 12.0, 6.2],
        # 折线图对应的y轴索引为2
        yaxis_index=2,
        color="#675bba",
        label_opts=opts.LabelOpts(is_show=False),
    )
)


bar.overlap(line) # 使用overlap(重叠)组件，把折线图重叠在条形图中

# 并行多图
grid = Grid()
grid.add(
    bar, # 图表实例，仅 `Chart` 类或者其子类
    
    # 直角坐标系网格配置项
    opts.GridOpts(
        # grid 组件离容器距离
        pos_left="5%",
        pos_right="20%"), 
        # 是否由自己控制 Axis 索引
        is_control_axis_index=True
        
        )
grid.render("grid_multi_yaxis.html")

123456789101112131415161718192021222324252627282930313233343536373839404142434445464748495051525354555657585960616263646566676869707172737475767778798081828384858687888990919293949596979899100101102103104105106107108109110111112113114115116117118119120121122123124125126127128
```

### （6）多 X/Y 轴

![在这里插入图片描述](https://img-blog.csdnimg.cn/20200508110624330.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQyMzc0Njk3,size_16,color_FFFFFF,t_70)

```python
from pyecharts import options as opts
from pyecharts.charts import Bar, Grid, Line

bar = (
    Bar()
    .add_xaxis(["{}月".format(i) for i in range(1, 13)])
    .add_yaxis(
        "蒸发量",
        [2.0, 4.9, 7.0, 23.2, 25.6, 76.7, 135.6, 162.2, 32.6, 20.0, 6.4, 3.3],
        yaxis_index=0,
        color="#d14a61",
    )
    .add_yaxis(
        "降水量",
        [2.6, 5.9, 9.0, 26.4, 28.7, 70.7, 175.6, 182.2, 48.7, 18.8, 6.0, 2.3],
        yaxis_index=1,
        color="#5793f3",
    )
    .extend_axis(
        yaxis=opts.AxisOpts(
            name="蒸发量",
            type_="value",
            min_=0,
            max_=250,
            position="right",
            axisline_opts=opts.AxisLineOpts(
                linestyle_opts=opts.LineStyleOpts(color="#d14a61")
            ),
            axislabel_opts=opts.LabelOpts(formatter="{value} ml"),
        )
    )
    .extend_axis(
        yaxis=opts.AxisOpts(
            type_="value",
            name="温度",
            min_=0,
            max_=25,
            position="left",
            axisline_opts=opts.AxisLineOpts(
                linestyle_opts=opts.LineStyleOpts(color="#675bba")
            ),
            axislabel_opts=opts.LabelOpts(formatter="{value} °C"),
            splitline_opts=opts.SplitLineOpts(
                is_show=True, linestyle_opts=opts.LineStyleOpts(opacity=1)
            ),
        )
    )
    .set_global_opts(
        yaxis_opts=opts.AxisOpts(
            name="降水量",
            min_=0,
            max_=250,
            position="right",
            offset=80,
            axisline_opts=opts.AxisLineOpts(
                linestyle_opts=opts.LineStyleOpts(color="#5793f3")
            ),
            axislabel_opts=opts.LabelOpts(formatter="{value} ml"),
        ),
        title_opts=opts.TitleOpts(title="Grid-Overlap-多 X/Y 轴示例"),
        tooltip_opts=opts.TooltipOpts(trigger="axis", axis_pointer_type="cross"),
        legend_opts=opts.LegendOpts(pos_left="25%"),
    )
)

line = (
    Line()
    .add_xaxis(["{}月".format(i) for i in range(1, 13)])
    .add_yaxis(
        "平均温度",
        [2.0, 2.2, 3.3, 4.5, 6.3, 10.2, 20.3, 23.4, 23.0, 16.5, 12.0, 6.2],
        yaxis_index=2,
        color="#675bba",
        label_opts=opts.LabelOpts(is_show=False),
    )
)

# 上面的代码和上个例子  都是差不多的
bar1 = (
    Bar()
    .add_xaxis(["{}月".format(i) for i in range(1, 13)])
    .add_yaxis(
        "蒸发量 1",
        [2.0, 4.9, 7.0, 23.2, 25.6, 76.7, 135.6, 162.2, 32.6, 20.0, 6.4, 3.3],
        color="#d14a61",
        xaxis_index=1,
        # y轴索引，继上面的 设为3
        yaxis_index=3,
    )
    .add_yaxis(
        "降水量 2",
        [2.6, 5.9, 9.0, 26.4, 28.7, 70.7, 175.6, 182.2, 48.7, 18.8, 6.0, 2.3],
        color="#5793f3",
        xaxis_index=1,
        yaxis_index=3,
    )
    .extend_axis(
        yaxis=opts.AxisOpts(
            name="蒸发量",
            type_="value",
            min_=0,
            max_=250,
            position="right",
            axisline_opts=opts.AxisLineOpts(
                linestyle_opts=opts.LineStyleOpts(color="#d14a61")
            ),
            axislabel_opts=opts.LabelOpts(formatter="{value} ml"),
        )
    )
    .extend_axis(
        yaxis=opts.AxisOpts(
            type_="value",
            name="温度",
            min_=0,
            max_=25,
            position="left",
            axisline_opts=opts.AxisLineOpts(
                linestyle_opts=opts.LineStyleOpts(color="#675bba")
            ),
            axislabel_opts=opts.LabelOpts(formatter="{value} °C"),
            splitline_opts=opts.SplitLineOpts(
                is_show=True, linestyle_opts=opts.LineStyleOpts(opacity=1)
            ),
        )
    )
    .set_global_opts(
        xaxis_opts=opts.AxisOpts(grid_index=1),
        yaxis_opts=opts.AxisOpts(
            name="降水量",
            min_=0,
            max_=250,
            position="right",
            offset=80,
            # 直角坐标系网格索引
            grid_index=1,
            axisline_opts=opts.AxisLineOpts(
                linestyle_opts=opts.LineStyleOpts(color="#5793f3")
            ),
            axislabel_opts=opts.LabelOpts(formatter="{value} ml"),
        ),
        tooltip_opts=opts.TooltipOpts(trigger="axis", axis_pointer_type="cross"),
        legend_opts=opts.LegendOpts(pos_left="65%"),
    )
)

line1 = (
    Line()
    .add_xaxis(["{}月".format(i) for i in range(1, 13)])
    .add_yaxis(
        "平均温度 1",
        [2.0, 2.2, 3.3, 4.5, 6.3, 10.2, 20.3, 23.4, 23.0, 16.5, 12.0, 6.2],
        color="#675bba",
        label_opts=opts.LabelOpts(is_show=False),
        # 新增了一个 x轴的索引，上面的line中x轴的索引为0
        xaxis_index=1,
         
        yaxis_index=5,
    )
)

overlap_1 = bar.overlap(line)
overlap_2 = bar1.overlap(line1)


# 使用grid将overlap_1和overlap_2进行水平并行放置
grid = (
        # 初始化
    Grid(init_opts=opts.InitOpts(width="1200px", height="800px"))
    .add(
        overlap_1, grid_opts=opts.GridOpts(pos_right="58%"), 
        # 是否由自己控制 Axis 索引
        is_control_axis_index=True
    )
    .add(overlap_2, grid_opts=opts.GridOpts(pos_left="58%"), is_control_axis_index=True)
    .render("grid_overlap_multi_xy_axis.html")
)
```