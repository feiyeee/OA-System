# OA-System
An Office Automation System. I save here for learning Javaweb.

使用纯Servlet搭建OA系统，没有使用任何框架，仅作为初学Javaweb使用。

在使用代码前，先准备好数据库表（SQL脚本），代码中的数据库名为`Javaweb`
```SQL
create database if not exists Javaweb;
use `Javaweb`;
# 部门表
drop table if exists dept;
create table dept(
  deptno int primary key,
  dname varchar(255),
  loc varchar(255)
);
insert into dept(deptno, dname, loc) values(10, '销售部', '北京');
insert into dept(deptno, dname, loc) values(20, '研发部', '上海');
insert into dept(deptno, dname, loc) values(30, '技术部', '广州');
insert into dept(deptno, dname, loc) values(40, '媒体部', '深圳');
commit;
select * from dept;
```
