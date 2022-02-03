# PyAutoGUI-自动化手工操作工具

https://www.jianshu.com/p/9c3cce682120

[toc]

**一个外挂代码**

先上一小段外挂示例代码：全部代码见[https://github.com/asweigart/sushigoroundbot](https://links.jianshu.com/go?to=https%3A%2F%2Fgithub.com%2Fasweigart%2Fsushigoroundbot)
外挂代码功能为：
- 1、通过内置图片定位桌面上游戏的左上角位置，
- 2、进而确定需要点击的按钮位置
- 3、依次点击按钮



```dart
input('Move mouse over bot window and press Enter.')
botWindow = pyautogui.position()

# locate the game window
window = pyautogui.locateOnScreen('top_left_corner.png')
if window is None:
    sys.exit('Could not find game on screen. Is the game visible?')

winLeft = window[0]
winTop = window[1]
print('Found game window at:', winLeft, winTop)

# navigate through start screen
pyautogui.click(320 + winLeft, 200 + winTop) # click on Play button
pyautogui.click(300 + winLeft, 380 + winTop) # click on Continue button
pyautogui.click(550 + winLeft, 450 + winTop) # click on Skip
pyautogui.click(300 + winLeft, 380 + winTop) # click on Continue button
pyautogui.click(botWindow) # click back on bot window
```

这段外挂代码用到的python库就是PyAutoGUI.

PyAutoGUI 通过调用不同操作系统的底层库(例如 _normalKeyEvent(key, 'down')，_sendMouseEvent(Quartz.kCGEventOtherMouseDown, x, y, Quartz.kCGMouseButtonCenter) )，在Windows 桌面、macOS和Linux x11 桌面上实现了鼠标和键盘的控制。

你可以利用 PyAutoGUI库开发Python脚本，控制鼠标和键盘来自动执行应用程序的交互。

## **PyAutoGUI 使用**
### 安装



```cshell
pip install pyautogui
# for mac
#sudo pip3 install pyobjc-core
#sudo pip3 install pyobjc
#pip3 install pyautogui

#linux
#sudo apt-get scrot
#sudo apt-get install python-tk
#sudo apt-get install python3-dev
#pip3 install pyautogui
```

### 一、库的引入及配置



```cpp
import pyautogui
pyautogui.FAILSAFE = False  # 是否在鼠标出去的时候抛出异常
通过把pyautogui.PAUSE设置成float或int时间（秒），可以为所有的PyAutoGUI函数增加延迟。
默认延迟时间是0.1秒。在函数循环执行的时候，这样做可以让PyAutoGUI运行的慢一点，非常有用。例如：
In [ ]:
pyautogui.PAUSE = 2.5
```

### 二、定位操作



```python
1、获取屏幕宽高  
screenWidth, screenHeight = pyautogui.size()
屏幕左上开始为(0,0)
2、获取当前鼠标位置
currentMouseX, currentMouseY = pyautogui.position()
onScreen(0, 0)  ，检查是否在屏幕上
3、根据图片定位在图片在屏幕上的中点
x, y = pyautogui.locateCenterOnScreen(r'C:\screenshot.png')

4、 匹配屏幕所有与目标图片的对象，可以用for循环和list()输出
pyautogui.locateAllOnScreen(r'C:\region_screenshot.png')
for pos in pyautogui.locateAllOnScreen(r'C:\\region_screenshot.png'):
  print(pos)
```

### 三、屏幕操作



```python
1、截取部分屏幕
im = pyautogui.screenshot(r'C:\screenshot.png',region=(0, 0, 300, 400)) # 截屏并设置保存图片的位置和名称

2、 匹配屏幕所有与目标图片的对象，可以用for循环和list()输出
pyautogui.locateAllOnScreen(r'C:\region_screenshot.png')
for pos in pyautogui.locateAllOnScreen(r'C:\\region_screenshot.png'):
  print(pos)

3、获取屏幕点色彩属性值
pix = pyautogui.screenshot().getpixel((x, y))  #0,1,2=rgb

4、灰度值匹配
pyautogui.pixelMatchesColor(100, 200, (205, 255, 245), tolerance=10)
```

### 四、鼠标操作



```python
1、鼠标移动
pyautogui.moveTo(100, 150)  #绝对位置 
pyautogui.moveRel(None, 10) #相对位置
pyautogui.moveTo(500, 500, duration=2, tween=pyautogui.easeInOutQuad)  # 2秒带动画移动到500,500

2、鼠标点击
pyautogui.click()  单击
pyautogui.click(x=100, y=200) # 
pyautogui.doubleClick()  双击
pyautogui.rightClick()  单击鼠标右键
pyautogui.middleClick() 单击鼠标中键
pyautogui.click(button='right', clicks=3, interval=0.25) 

pyautogui.click(100, 200, clicks=2, interval=0.1, duration=3.0)  
# 耗时3S，将鼠标移动到100，200的位置，点击2次，两次点击间隔0.1S

3、鼠标拖动
pyautogui.dragTo(x,y[,duration=t)  
pyautogui.dragRel(x,y, duration=0.5)  按住左键移动
4、鼠标滚动
pyautogui.scroll(10)  / -10  # 正数向上/下滚动
pyautogui.hscroll(10)  / -10 #  向右/左滚动 
```

一个鼠标拖拽移动示例。

![img](https://upload-images.jianshu.io/upload_images/3405877-cb021bb4ebbabc0e.png?imageMogr2/auto-orient/strip|imageView2/2/w/365/format/webp)

```swift
import pyautogui,time
screenWidth, screenHeight = pyautogui.size()
pyautogui.moveTo(screenWidth / 2, screenHeight / 2)
distance = 200
while distance > 0:
        pyautogui.dragRel(distance, 0, duration=0.5)   # move right
        distance -= 5
        pyautogui.dragRel(0, distance, duration=0.5)   # move down
        pyautogui.dragRel(-distance, 0, duration=0.5)  # move left
        distance -= 5
        pyautogui.dragRel(0, -distance, duration=0.5)  # move up
```

### 五、键盘操作



```bash
pyautogui.typewrite('Hello world!', interval=0.25) #模拟输入
typewrite(['enter']) 
pyautogui.typewrite(['a','b','left','left','X','Y']) # XYab
pyautogui.press('esc')
pyautogui.keyDown('shift')
pyautogui.press(['left', 'left', 'left', 'left', 'left', 'left']) 连续按键left
pyautogui.keyUp('shift')
pyautogui.hotkey('ctrl', 'c')   # 组合键
```

键盘键说明



```bash
键盘键字符串                        含义
'a','b','c','A','C','1','2','3',    单个字符的键
'!','@','#'等
'enter'                             回车
‘esc'                              ESC键
'shiftleft','shiftright'            左右Shift键
'altleft','altright'                左右Alt键
'ctrlleft','ctrlright'              左右Ctrl键
‘tab'(or '\t')                     Tab键
'backspace','delete'                Backspace键和Delete键
'pageup','pagedown'                 Page Up 和Page Down键
'home','end'                        Home键和End键
'up','down','left','right'          上下左右箭头键
'f1','f2','f3'等                    F1至F12键
'volumemute','volumeup',volumedown' 静音，放大音量和减小音量键
'pause'                             暂停键
'capslock','numlock','scrolllock'   Caps Lock，Num Lock和 Scroll Lock键
'insert'                            Insert键
'printscreen'                       Prtsc或Print Screen键
'winleft','winright'                左右Win键(在windows上)
'command'                           Command键(在OS X上)
'option'                            Option键(在OS X上)
```

另外推荐一个坐标记录工具：KeymouseGo
[https://github.com/taojy123/KeymouseGo/releases](https://links.jianshu.com/go?to=https%3A%2F%2Fgithub.com%2Ftaojy123%2FKeymouseGo%2Freleases)

扩展阅读：
[https://github.com/search?q=%E5%A4%96%E6%8C%82++python](https://links.jianshu.com/go?to=https%3A%2F%2Fgithub.com%2Fsearch%3Fq%3D%E5%A4%96%E6%8C%82%2B%2Bpython)
[https://github.com/search?q=pyautogui](https://links.jianshu.com/go?to=https%3A%2F%2Fgithub.com%2Fsearch%3Fq%3Dpyautogui)