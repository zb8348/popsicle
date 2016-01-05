<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="org.slf4j.Logger,org.slf4j.LoggerFactory" %>
<%response.setStatus(200);%>
<%
	Throwable ex = null;
	if (request.getAttribute("javax.servlet.error.exception") != null)
		ex = (Throwable) request.getAttribute("javax.servlet.error.exception");

	//记录日志
	Logger logger = LoggerFactory.getLogger("page/error/error.jsp");
	logger.error(ex.getMessage(), ex);
%>
<!DOCTYPE html>
<html>
<head>
	<title>ERROR - 系统内部错误</title>
	<meta http-equiv="content-type" content="text/html;charset=utf-8">
</head>

<body>
	<h2>错误：</h2>
	<p>
		<span><%=ex.getMessage()%></span>
	</p>
	<p><a href='<c:url value="/"/>'>返回首页</a></p>
</body>
</html>