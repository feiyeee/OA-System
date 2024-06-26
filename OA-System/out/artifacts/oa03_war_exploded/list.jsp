<%@ page import="com.feiye.oa.bean.Dept" %>
<%@ page import="java.util.List" %>
<%@page contentType="text/html;charset=UTF-8"%>
<%--<%@page import="java.util.List, com.feiye.oa.bean.Dept"%>--%>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>部门列表页面</title>
	</head>
	<body>
		<%--显示一个用户名--%>
		<h3>欢迎<%=session.getAttribute("username")%></h3>
		<a href="<%=request.getContextPath()%>/user/exit">[退出系统]</a>

		<script type="text/javascript">
			function del(deptno) {
				// 弹出确认框，点击确认，返回true；点击取消，返回false
				var ok = window.confirm("是否确认删除？");
				if(ok) {
					// 向服务器发送请求，进行删除数据操作
					// alert("正在删除数据，请稍后......")
					document.location.href = "<%=request.getContextPath()%>/dept/delete?deptno=" + deptno;
				}
			}
		</script>
		
		<h1 align="center">部门列表</h1>
		<hr />
		<table border="1px" align="center" width="50%">
			<tr>
				<th>序号</th>
				<th>部门编号</th>
				<th>部门名称</th>
				<th>操作</th>
			</tr>

			<%
				// 从request域中取出集合
				List<Dept> deptList = (List<Dept>)request.getAttribute("deptList");
				// 循环遍历
				int i = 0;
				for(Dept dept:deptList) {
					/*// 在后台输出
					System.out.println(dept.getDname());*/

					// 将部门名输出到浏览器
					/*out.write(dept.getDname());*/
			%>
			<tr>
				<td><%=++i%></td>
				<td><%=dept.getDeptno()%></td>
				<td><%=dept.getDname()%></td>
				<td>
					<!-- javascript:void(0) 表示仍然保留住超链接的样子 -->
					<!-- 点击此超链接之后，不进行页面的跳转 -->
					<!-- 用户点击此超链接时，执行一段JS代码，不跳转页面 -->
					<a href="javascript:void(0)" onclick="del(<%=dept.getDeptno()%>)">删除</a>
					<a href="<%=request.getContextPath()%>/dept/detail?f=edit&deptno=<%=dept.getDeptno()%>">修改</a>
					<a href="<%=request.getContextPath()%>/dept/detail?f=detail&deptno=<%=dept.getDeptno()%>">详情</a>
				</td>
			</tr>

			<%
				}
			%>

		</table>
		
		<hr />
		<a href="<%=request.getContextPath()%>/add.jsp">新增部门</a>
	</body>
</html>