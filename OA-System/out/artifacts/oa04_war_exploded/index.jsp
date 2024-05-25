<%@page contentType="text/html;charset=UTF-8"%>
<%--访问jsp时不生成session对象--%>
<%@page session="false"%>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>欢迎使用OA系统</title>
	</head>
	<body>
		<h1>LOGIN PAGE</h1>
		<hr>
		<form action="${pageContext.request.contextPath}/user/login" method="post">
			username: <input type="text" name="username"><br>
			password: <input type="password" name="password"><br>
			<input type="checkbox" name="f" value="1">十天内免登录<br>
			<input type="submit" value="login">
		</form>
	</body>
</html>
