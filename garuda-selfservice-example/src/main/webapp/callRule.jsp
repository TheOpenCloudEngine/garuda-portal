<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" import="org.oce.garuda.multitenancy.*,org.springframework.web.context.*" %>
<%@ page import="org.springframework.web.context.support.WebApplicationContextUtils" %>
<%@ page import="sample.Main" %>
<!DOCTYPE>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">

    <title>Call Rule Result</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>

    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">

    <!-- Latest compiled and minified JavaScript -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>

    <%
        ServletContext srvCtx = request.getServletContext();

        WebApplicationContext appContext =  WebApplicationContextUtils.getWebApplicationContext(srvCtx);

        Main main = (Main)appContext.getBean(Main.class);

        String argument1 = request.getParameter("argument1");

        String result = main.callRule(argument1);
    %>


</head>
<body>


<h1>Result is <%=result%>.</h1>

</body>
</html>
