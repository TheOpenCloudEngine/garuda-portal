<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" import="org.oce.garuda.multitenancy.*,org.springframework.web.context.*" %>
<%@ page import="org.springframework.web.context.support.WebApplicationContextUtils" %>
<%@ page import="sample.Main" %>
<!DOCTYPE>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">

    <title>Insert title here</title>

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>

    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">

    <%
        ServletContext srvCtx = request.getServletContext();

        WebApplicationContext appContext = WebApplicationContextUtils.getWebApplicationContext(srvCtx);

        Main main = (Main)appContext.getBean("main");
    %>

    <script type="text/javascript">
        $(document).ready(function () {
            $("#subscribe").click(function () {
                if ($("#password").val() != $("#passwordConfirm").val()) {
                    alert("Passwords are different!");
                } else {
                    $.ajax({
                        url: '<%=main.getTenantSignupURL()%>',
                        type: 'POST',
                        cache: false,
                        dataType: 'text',
                        data: {
                            tenantId: $("#tenantId").val(),
                            tenantName: $("#tenantId").val(),
                            userId: $("#tenantId").val(),
                            userName: $("#tenantId").val(),
                            password: $("#password").val(),
                            planId: '<%=(String)request.getParameter("planId")%>'
                        },
                        success: function (data) {
                            debugger;
                            if (data === 'SUCCESS')
                                $(location).attr('href', "/tenantSignUpSuccess.jsp?tenantId=" + $("#tenantId").val());
                            else
                                alert("FAILED");
                        },
                        error: function (jqXHR, textStatus, errorThrown) {
                            var jsonResponse = $.parseJSON(jqXHR.responseText);
                            alert(jsonResponse);
                        }
                    });
                }
            });
        });
    </script>
</head>
<body>

<h2 style="margin-left: 40px;">Tenant 가입</h2><br/><br/>

<form class="form-horizontal" style="width:50%;">
    <div class="form-group">
        <label for="inputTenantId" class="col-sm-2 control-label">Tenant Id</label>

        <div class="col-sm-10">
            <input type="text" class="form-control" id="tenantId" placeholder="Tenant Id">
        </div>
    </div>
<%--
    <div class="form-group">
        <label for="inputTenantName" class="col-sm-2 control-label">Tenant Name</label>

        <div class="col-sm-10">
            <input type="text" class="form-control" id="tenantName" placeholder="Tenant Name">
        </div>
    </div>

    <div class="form-group">
        <label for="inputUserID" class="col-sm-2 control-label">User ID</label>

        <div class="col-sm-10">
            <input type="text" class="form-control" id="userId" placeholder="User ID">
        </div>
    </div>

    <div class="form-group">
        <label for="inputUserName" class="col-sm-2 control-label">User Name</label>

        <div class="col-sm-10">
            <input type="text" class="form-control" id="userName" placeholder="User Name">
        </div>
    </div>--%>

    <div class="form-group">
        <label for="inputPassword" class="col-sm-2 control-label">Password</label>

        <div class="col-sm-10">
            <input type="password" class="form-control" id="password" placeholder="Password">
        </div>
    </div>

    <div class="form-group">
        <label for="inputPasswordConfirm" class="col-sm-2 control-label">Password Confirm</label>

        <div class="col-sm-10">
            <input type="password" class="form-control" id="passwordConfirm" placeholder="Password Confirm">
        </div>
    </div>

    <div class="form-group">
        <div class="col-sm-offset-2 col-sm-10">
            <button type="button" id="subscribe" class="btn btn-success">회원가입</button>
        </div>
    </div>
</form>
</body>
</html>