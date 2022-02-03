# 数据库 · 分页查询

    应用场景：当要显示的数据，一页显示不全，需要分页提交sql请求
    
    语法：
        select 查询列表
        from 表
        【join type join 表2
        on 连接条件
        where 筛选条件
        group by 分组字段
        having 分组后筛选
        order by 排序的字段】
        limit 【offset，】size；
        
        offset：要显示条目的起始索引（从0开始）
        size：要显示的条目个数
        
    特点：
        ①limit语句放在查询语句的最后
        ②公式
            要显示的页数page，每页条目数size
            select 查询列表
            from 表
            limit (page-1)*size size; 
        
    案例1：查询前五条员工信息
    select * from employees limit 0,5;
    select * from employees limit 5;
    
    案例2：查询第11条到25条
    select * from employees limit 10,15;
    
    案例3：有奖金的员工信息，并且工资较高的前十显示
    select * 
    from employees
    where commission_pct is not null
    order by salary desc
    limit 10;
    