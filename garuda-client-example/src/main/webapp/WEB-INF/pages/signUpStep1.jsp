<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">

<title>Insert title here</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.js"></script>

<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">

<script type="text/javascript">
function subscribe(element){
	$(location).attr('href',"signUpStep2?planId=" + $(element).attr("id"));
}

$(document).ready(function() {
	// 플랜 목록 가져오기
	$.ajax({
		url  : '${domain}/services/app/${appId}/plan',
		type : 'GET',
		async: false,
		cache: false,
		success : function(data){
			var html = "<table class='table table-striped'>";
			html += "<td><table class='table table-bordered'>";
			html += "<tr><td>Name</td></tr>";
			html += "<tr><td>Description</td></tr>";
			html += "<tr><td>OneTimeServiceList</td></tr>";
			html += "<tr><td>RecurringServiceAndRateList</td></tr>";
			html += "<tr><td>UsageServiceAndRateList</td></tr>";
			html += "<tr><td>Price</td></tr>";
			html += "<tr><td>Subcribe</td></tr>";
			html += "</table></td>";
			$.each( data, function( key, value ) {
				html += "<td><table class='table table-bordered'>";
				html += "<tr><td>" + value.name + "</td></tr>";
				html += "<tr><td>" + value.description + "</td></tr>";

				var price = 0.0;
				var serviceList= "*";
				$.each( value.oneTimeServiceAndRateList, function( k, v ) {
					price += parseFloat(v.price); 
					serviceList +=v.service.name + "(contractDuration = " + v.contractDuration + ")<br/>";
				});
				html += "<tr><td>" + serviceList + "</td></tr>";

				serviceList= "*";
				$.each( value.recurringServiceAndRateList, function( k, v ) {
					price += parseFloat(v.price);
					serviceList +=v.service.name + "( quota = " + v.quota + ", overagePrice = " + v.overagePrice + ")<br/>";
				});
				html += "<tr><td>" + serviceList + "</td></tr>";

				serviceList= "*";
				$.each( value.usageServiceAndRateList, function( k, v ) {
					price += parseFloat(v.price);
					serviceList +=v.service.name + "(" + v.minQuantity + "~" + v.maxQuantity + ")<br/>";
				});
				html += "<tr><td>" + serviceList + "</td></tr>";

				html += "<tr><td>" + price + "</td></tr>";
				html += "<tr><td><input class='btn btn-primary' type='button' id='"+value.id+"' value='Subscribe'onclick='subscribe(this)'/></td></tr>";
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
	<div class="CSSTableGenerator" id="planDiv"></div>
</body>
</html>