<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">

<title>Insert title here</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
<style type="text/css">


</style>
<script type="text/javascript">
function subscribe(element){
	$(location).attr('href',"subscribe2?planId=" + $(element).attr("id"));
}

$(document).ready(function() {
	// 플랜 목록 가져오기
	$.ajax({
		url  : '${domain}/services/app/${appId}/plan',
		type : 'GET',
		async: false,
		cache: false,
		success : function(data){
			var html = "<table>";
			html += "<td><table>";
			html += "<tr><td>Name</td></tr>";
			html += "<tr><td>Description</td></tr>";
			html += "<tr><td>Price</td></tr>";
			html += "<tr><td>ServiceList</td></tr>";
			html += "<tr><td>Subcribe</td></tr>";
			html += "</table></td>";
			$.each( data, function( key, value ) {
				html += "<td><table>";
				html += "<tr><td>" + value.name + "</td></tr>";
				html += "<tr><td>" + value.description + "</td></tr>";
				var price = 0.0;
				var serviceList= "";
				$.each( value.serviceAndRates, function( k, v ) {
					price += parseFloat(v.price); 
					serviceList +=v.service.name + "(" + v.minQuantity + "~" + v.maxQuantity + ")<br/>";
				});
				
				html += "<tr><td>" + price + "</td></tr>";
				html += "<tr><td>" + serviceList + "</td></tr>";
				html += "<tr><td><input type='button' id='"+value.id+"' value='Subscribe'onclick='subscribe(this)'/></td></tr>";
				html += "</table></td>";

			});
			html += "</table>";
			$("#planDiv").html(html);
		},
		error   : function(jqXHR, textStatus, errorThrown){
			var jsonResponse = $.parseJSON(jqXHR.responseText);
			alert(jsonResponse);
		}
	});
});
</script>
</head>
<body>
	<div class="CSSTableGenerator" id="planDiv">
	
	</div>
</body>
</html>