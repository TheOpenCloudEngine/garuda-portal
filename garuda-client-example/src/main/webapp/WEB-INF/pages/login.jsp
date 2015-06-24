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

    <style type="text/css">
        .form-style-2 {
            max-width: 500px;
            padding: 20px 12px 10px 20px;
            font: 13px Arial, Helvetica, sans-serif;
        }

        .form-style-2-heading {
            font-weight: bold;
            font-style: italic;
            border-bottom: 2px solid #ddd;
            margin-bottom: 20px;
            font-size: 15px;
            padding-bottom: 3px;
        }

        .form-style-2 label {
            display: block;
            margin: 0px 0px 15px 0px;
        }

        .form-style-2 label > span {
            width: 180px;
            font-weight: bold;
            float: left;
            padding-top: 8px;
            padding-right: 5px;
        }

        .form-style-2 span.required {
            color: red;
        }

        .form-style-2 .tel-number-field {
            width: 40px;
            text-align: center;
        }

        .form-style-2 input.input-field {
            width: 48%;

        }

        .form-style-2 input.input-field,
        .form-style-2 .tel-number-field,
        .form-style-2 .textarea-field,
        .form-style-2 .select-field {
            box-sizing: border-box;
            -webkit-box-sizing: border-box;
            -moz-box-sizing: border-box;
            border: 1px solid #C2C2C2;
            box-shadow: 1px 1px 4px #EBEBEB;
            -moz-box-shadow: 1px 1px 4px #EBEBEB;
            -webkit-box-shadow: 1px 1px 4px #EBEBEB;
            border-radius: 3px;
            -webkit-border-radius: 3px;
            -moz-border-radius: 3px;
            padding: 7px;
            outline: none;
        }

        .form-style-2 .input-field:focus,
        .form-style-2 .tel-number-field:focus,
        .form-style-2 .textarea-field:focus,
        .form-style-2 .select-field:focus {
            border: 1px solid #0C0;
        }

        .form-style-2 .textarea-field {
            height: 100px;
            width: 55%;
        }

        .form-style-2 input[type=submit],
        .form-style-2 input[type=button] {
            border: none;
            padding: 8px 15px 8px 15px;
            background: #FF8500;
            color: #fff;
            box-shadow: 1px 1px 4px #DADADA;
            -moz-box-shadow: 1px 1px 4px #DADADA;
            -webkit-box-shadow: 1px 1px 4px #DADADA;
            border-radius: 3px;
            -webkit-border-radius: 3px;
            -moz-border-radius: 3px;
        }

        .form-style-2 input[type=submit]:hover,
        .form-style-2 input[type=button]:hover {
            background: #EA7B00;
            color: #fff;
        }
    </style>
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
                <button type="button" id="subscribe" class="btn btn-default">회원가입</button>
                <button type="button" id="login" class="btn btn-default">로그인</button>
            </div>
        </div>
    </form>
</body>
</html>