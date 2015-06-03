<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
<script type="text/javascript">
$(document).ready(function() {
	$("#billProcess").click(function(){
			$.ajax({
				url  : 'http://localhost:8080/uengine-web/services/app/hello/plan',
				type : 'GET',
				cache: false,
				success : function(data){
					alert(data);
					console.log(data);
				},
				error   : function(jqXHR, textStatus, errorThrown){
					var jsonResponse = $.parseJSON(jqXHR.responseText);
					alert(jsonResponse);
				}
			});
	});
});
</script>
</head>
<body>
	Hello World~~
	<br />
	<br />
	<button id="billProcess">Data Source Process</button>
</body>
</html>