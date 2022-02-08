# PhpMyAdmin

下载地址：
    
- https://files.phpmyadmin.net/phpMyAdmin/5.0.4/phpMyAdmin-5.0.4-all-languages.zip [php7.1]
- https://files.phpmyadmin.net/phpMyAdmin/4.4.12/phpMyAdmin-4.4.12-all-languages.zip 

放到网站目录下


```
mv phpMyAdmin-5.0.4-all-languages/* .
mv config.sample.inc.php config.inc.php

vim config.inc.php
    添加http认证，修改数据库host
    /* Authentication type */
    $cfg['Servers'][$i]['auth_type'] = 'cookie';
    $cfg['Servers'][$i]['auth_type'] = 'http';
    /* Server parameters */
    $cfg['Servers'][$i]['host'] = '127.0.0.1';

mkdir /wordpress/mydata/tmp/ -p
chown -R www.www *     
```
   
   
