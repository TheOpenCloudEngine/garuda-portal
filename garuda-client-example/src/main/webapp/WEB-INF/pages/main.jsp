<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">

<title>Insert title here</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
<script type="text/javascript">
$(document).ready(function() {
	$("#logout").click(function(){
		$(location).attr('href',"logout");
	});
});

</script>
</head>
<body>
	Login Success!
	<input type="button" value="logout" id="logout" name="logout" />
</body>
</html>