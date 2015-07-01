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

    <!-- Latest compiled and minified JavaScript -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>

    <script type="text/javascript">
        $(document).ready(function () {
            $("#logout").click(function () {
                $(location).attr('href', "logout");
            });

            $("#saveChange").click(function () {
                alert("Save Changed");
            });
        });
    </script>
</head>
<body>
<div class="text-right">
    <button type="button" id="logout" class="btn btn-default btn-lg">logout</button>

    <!-- Button trigger modal -->
    <button type="button" class="btn btn-primary btn-lg" data-toggle="modal" data-target="#myModal">
        Self Service
    </button>

</div>

<img src="${domain}/services/metadata/app/${appId}/tenant/${user.tenantId}/image/companyLogo" class="img-rounded">

<frame src="${domain}/services/metadata/app/${appId}/tenant/${user.tenantId}/text/companyName" frameborder="1"
       name="header" title="Header window">

<!-- Modal -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document" style="width:1000px;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">Self Service</h4>
            </div>
            <div class="modal-body">
                <iframe frameborder="0" src="http://www.baidu.com/" height="700px" width="900px"></iframe>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                <button type="button" class="btn btn-primary" data-dismiss="modal" id="saveChange">Save changes</button>
            </div>
        </div>
    </div>
</div>
</body>
</html>