# Mysql事务

#### TCL
    Transaction Control Language 事务控制语言
    
#### 事务
        一个或一组sql语句组成一个执行单元，这个单元要么都执行，要么都不执行
        
    案例：转账
    张三丰  1000
    郭襄    1000
    
    查看存储引擎：show engines;
        默认为InnoDB（支持事务）
        myisam、memory等不支持事务
        
#### 事务的ACID属性
    1.原子性：一个事务不可再分割，事务中的操作要么都发生，要么都不发生
    
    2.一致性：一个事务会使数据库从一个一致状态转换到另一个一致状态
    
    3.隔离性：一个事务的执行不受其他事务的干扰

    4.持久性：一个事务一旦被提交，对数据库的改变是永久性的
    
#### 事务的创建
    show variables like 'autocommit';//默认自动提交
    
    隐式事务：insert，update，delete语句
    显示事务
        事务有明显的开启和结束的标记
        前提是先设置自动提交功能为禁用
        set autocommit=0;//当前会话禁用
        
    步骤1：开启事务
        set autocommit=0;
        start transaction;可选
    步骤2：事务中的sql语句（select，insert，update，delete）
        语句1；
        语句2；
    步骤3：结束事务
        commit;提交事务
        rollback;回滚事务
        
#### 事务演示
    drop table if exists account;
    create table account(
        id int primary key auto_increment,
        username varchar(50),
        balance double);
    ALTER TABLE account CONVERT TO CHARACTER SET utf8mb4;
    insert into account(username,balance) values ('张三丰',1000),('郭襄',1000);
    
    #开始事务
    set autocommit=0;
    begin;
    #编写事务语句
    update account set balance=500 where username='张三丰';
    update account set balance=1500 where username='郭襄';
    #结束事务
    commit;
    #rollback;
    select * from account;
    
#### 事务并发问题    

    ##### 脏读：读取到未提交的字段
    
    ##### 不可重复读：重复查看到的数据不一样
    
    ##### 幻读：重复查询多出N行数据（插入操作）
    
#### 事务隔离级别

    查看当前隔离级别：select @@tx_isolation;
    设置当前隔离级别：set session transaction isolation level 隔离级别;
    
##### 一、读未提交：set session transaction isolation level read uncommitted;
    
    改变字符集：set names gbk;
    
    1.    
        begin;
        update account set username='john' where id=1;
        //去另一个Session查看
        //rollback;
    2.
        begin;
        select * from account;
        commit;
    
##### 二、读已提交：set session transaction isolation level read committed;    
    1.
        begin;
        update account set username='张飞' where id=1;
        //去另一个Session查看
        //commit;
    2.
        begin;
        select * from account;
        commit;
        
##### 三、可重复读：set session transaction isolation level repeatable read;    
    1.
        begin;
        update account set username='刘备' where id=1;
        commit;
        
    2.
        begin;
        select * from account;
        commit;
        select * from account;
        
        
    幻读：
    1.
        begin;
        select * from account;
        //去另一个Session插入
        update account set username='mmmm';
        select * from account;
        //多出一行的修改
        commit;
        
    2.
        begin;
        insert into account values (null,'关羽',1000);
        commit;
        
        
##### 四、串行化（性能最差）：set session transaction isolation level serializable;   
    1.
        begin;
        select * from account;
        //去另一个Session插入
        update account set username='wwwww';
        commit;
    2.
        begin;
        insert into account values (null,'关羽',1000);
        //堵塞操作
        commit;