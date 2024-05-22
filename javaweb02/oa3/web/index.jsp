<%@page contentType="text/html;charset=UTF-8"%>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>欢迎使用OA系统</title>
	</head>
	<body>
		<h1>Welcome!!!</h1>
		<%--前端发送请求路径时，如果请求路径是绝对路径，要以 / 开始，加项目名--%>
		<%--以下，oa项目名写死了，我们需要动态获取项目名--%>
		<%--<a href="/oa/list.jsp">查看部门列表</a>--%>
		<%--以下，注意空格问题--%>
		<%--<a href="<%=request.getContextPath()%>/list.jsp">查看部门列表</a>--%>

		<%--执行一个Servlet，查询数据库，收集数据--%>
		<a href="<%=request.getContextPath()%>/dept/list">查看部门列表</a>

		<%--调用方法，动态获取一个应用的根路径--%>
		<%--<%=request.getContextPath()%>	&lt;%&ndash;out.print(request.getContextPath());&ndash;%&gt;--%>
	</body>
</html>