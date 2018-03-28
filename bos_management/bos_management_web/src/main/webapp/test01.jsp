<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<shiro:authenticated>
		您已经登录
	</shiro:authenticated>
	<hr>
	<shiro:hasPermission name="courier_pageQuery11">
		你有查看页快递员的权限
	</shiro:hasPermission>
	<hr>
	<shiro:hasRole name="1">
		你的角色是1
	</shiro:hasRole>
</body>
</html>