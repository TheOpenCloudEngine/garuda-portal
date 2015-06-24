<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">

    <title>Insert title here</title>

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>

    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">

    <script type="text/javascript">
        $(document).ready(function () {
            $("#login").click(function () {
                $.ajax({
                    url: 'loginCheck',
                    type: 'POST',
                    cache: false,
                    data: {
                        userId: $("#userId").val(),
                        password: $("#password").val()
                    },
                    success: function (data) {
                        if (data == 'SUCCESS')
                            $(location).attr('href', "main");
                        else
                            alert(data);
                        console.log(data);
                    },
                    error: function (jqXHR, textStatus, errorThrown) {
                        var jsonResponse = $.parseJSON(jqXHR.responseText);
                        alert(jsonResponse.return_msgs[0]);
                    }
                });
            });
            $("#subscribe").click(function () {
                $(location).attr('href', "signUpStep1");
            });
        });
    </script>
</head>
<body>
    <h2 style="margin-left: 40px;">사용자 로그인</h2><br/><br/>

    <form class="form-horizontal" style="width:40%;">
        <div class="form-group">
            <label for="inputUserID" class="col-sm-2 control-label">User ID</label>
            <div class="col-sm-10">
                <input type="text" class="form-control" id="userId" placeholder="User ID">
            </div>
        </div>

        <div class="form-group">
            <label for="inputPassword" class="col-sm-2 control-label">Password</label>
            <div class="col-sm-10">
                <input type="password" class="form-control" id="password" placeholder="Password">
            </div>
        </div>

        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <button type="button" id="subscribe" class="btn btn-success">회원가입</button>
                <button type="button" id="login" class="btn btn-default">로그인</button>
            </div>
        </div>
    </form>
</body>
</html>