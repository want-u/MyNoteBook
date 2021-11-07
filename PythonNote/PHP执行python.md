## PHP执行python


```
<?php
header("content-type:text/html;charset=utf-8");
$output = array();
exec("python iqiyi.py", $output);
var_dump( $output);
?>
```
