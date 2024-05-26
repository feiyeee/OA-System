# OA-System
An Office Automation System. I save here for learning Javaweb.

使用纯Servlet搭建OA系统，没有使用任何框架，系统极脆弱，仅作为初学Javaweb使用。

项目模块说明：
1. `oa`：使用纯Servlet搭建OA系统，进行单表的CRUD操作，在后端程序中写前端代码；
2. `oa02`：使用Servlet + JSP 改造OA系统
3. `oa03`：使用session增加OA系统的安全登录、退出功能
4. `oa04`：使用Cookie实现十天内免登录功能，利用Servlet + JSP + EL表达式 + JSTL标签改造OA系统
5. `oa05`：最终系统，添加Filter过滤器，利用session记录网站实时在线用户个数



数据库表（SQL脚本），数据库名为`Javaweb`
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

# 用户表
DROP TABLE IF EXISTS t_user;
CREATE table t_user(
	id INT PRIMARY KEY auto_increment,
	username VARCHAR(255),
	password VARCHAR(255)
);
INSERT INTO t_user(username, password) VALUES('admin', '123456');
INSERT INTO t_user(username, password) VALUES('zhangsan', '123456');
commit;
SELECT * FROM t_user;
```



版本说明：

* JDK17
* IDEA 2022.1.4
* MySQL 8.0.27
* Tomcat 10.0.12
