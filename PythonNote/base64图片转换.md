## base64图片转换
[toc]
#### 将图片转化为base64格式

    def change_img_as_base64(img_name):
        with open(img_name, 'rb') as f:
            return base64.b64encode(f.read())
            
#### 将base64转化为图片格式

    def change_base64_as_img(bs64):
        img_data = base64.b64decode(bs64)
        #uuid随机生成的不重复字符串
        img_path = str(Utils.UUID()) + '.jpg'
        with open(img_path, 'wb') as f:
            f.write(img_data)
        return img_path