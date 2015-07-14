<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" import="org.oce.garuda.multitenancy.*,org.springframework.web.context.*" %>
<%@ page import="org.springframework.web.context.support.WebApplicationContextUtils" %>
<!DOCTYPE>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">

    <title>Self Service Screen</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>

    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">

    <!-- Latest compiled and minified JavaScript -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>

    <%
        ServletContext srvCtx = request.getServletContext();

        WebApplicationContext appContext =  WebApplicationContextUtils.getWebApplicationContext(srvCtx);

        TenantSpecificUtil tenantSpecificUtil = (TenantSpecificUtil)appContext.getBean("tenantSpecificUtil");

        String tenantId = TenantContext.getThreadLocalInstance().getTenantId();
    %>

    <script type="text/javascript">
        $(document).ready(function () {
            $("#selfService").click(function () {
                window.open("<%=tenantSpecificUtil.getSelfServicePortalURL() %>",'Self Service','height=900,width=900');
            });

        });
    </script>
</head>
<body>


<div class="text-right">
    <button type="button" class="btn btn-primary btn-lg" id="selfService">
        Self Service
    </button>
</div>

<img src="<%=tenantSpecificUtil.getIamageURL("companyLogo")%>"
     class="img-rounded" width="200px" height="100px">

<h1>Company name is <%=tenantSpecificUtil.getMetadata("companyName")%>.</h1>

</body>
</html>