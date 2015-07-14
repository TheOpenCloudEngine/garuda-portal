<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8" import="org.oce.garuda.multitenancy.*,org.springframework.web.context.*" %>
<%@ page import="org.springframework.web.context.support.WebApplicationContextUtils" %><!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">

<title>Insert title here</title>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>

<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">

</head>
<body>

<%
	ServletContext srvCtx = request.getServletContext();

	WebApplicationContext appContext =  WebApplicationContextUtils.getWebApplicationContext(srvCtx);

	TenantSpecificUtil tenantSpecificUtil = (TenantSpecificUtil)appContext.getBean("tenantSpecificUtil");

	String tenantId = (String)request.getParameter("tenantId");
%>

	<h2 style="margin-left: 40px;">Tenant 가입이 성공되였습니다.</h2><br/><br/>

	<a href="http://<%=tenantId%>.garuda.com:9090/main.jsp"
	   class="btn btn-default btn-lg active" role="button">Tenant Self Service Page</a>

	<h3>http://<%=tenantId%>.garuda.com:9090/main.jsp</h3>
</body>
</html>