<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">

<title>Insert title here</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
<style type="text/css">
.form-style-2{
    max-width: 500px;
    padding: 20px 12px 10px 20px;
    font: 13px Arial, Helvetica, sans-serif;
}
.form-style-2-heading{
    font-weight: bold;
    font-style: italic;
    border-bottom: 2px solid #ddd;
    margin-bottom: 20px;
    font-size: 15px;
    padding-bottom: 3px;
}
.form-style-2 label{
    display: block;
    margin: 0px 0px 15px 0px;
}
.form-style-2 label > span{
    width: 180px;
    font-weight: bold;
    float: left;
    padding-top: 8px;
    padding-right: 5px;
}
.form-style-2 span.required{
    color:red;
}
.form-style-2 .tel-number-field{
    width: 40px;
    text-align: center;
}
.form-style-2 input.input-field{
    width: 48%;
   
}

.form-style-2 input.input-field,
.form-style-2 .tel-number-field,
.form-style-2 .textarea-field,
 .form-style-2 .select-field{
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
.form-style-2 .select-field:focus{
    border: 1px solid #0C0;
}
.form-style-2 .textarea-field{
    height:100px;
    width: 55%;
}
.form-style-2 input[type=submit],
.form-style-2 input[type=button]{
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
.form-style-2 input[type=button]:hover{
    background: #EA7B00;
    color: #fff;
}


</style>
<script type="text/javascript">
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
				html += "<tr><td><input type='radio' id='planSubscribeRadio' name='planSubscribeRadio' value='" + value.id + "'>Subscribe</td></tr>";
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
	
	$("#subscribe").click(function(){
		var selectedVal = $('input[name=planSubscribeRadio]:checked').val();
		if($("#password").val() != $("#passwordConfirm").val()){
			alert("Passwords are different!");
		}else if(selectedVal == undefined || selectedVal == null){
			alert("Please select plan");
		}else{
			alert(selectedVal);
			$.ajax({
				url  : 'signin',
				type : 'POST',
				cache: false,
				data :{
					tenantId: $("#tenantId").val(),
					tenantName: $("#tenantName").val(),
					userId: $("#userId").val(),
					userName: $("#userName").val(),
					password: $("#password").val(),
					planId: selectedVal
				},
				success : function(data){
					if(data == '200')
						$(location).attr('href',"login");
					else
						alert(data);
					console.log(data);
				},
				error   : function(jqXHR, textStatus, errorThrown){
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
	
	<div class="form-style-2">
		<div class="form-style-2-heading">Tenant 가입</div>
	<form>
		<label for="field1">
			<span>Tenant ID <span class="required">*</span></span>
			<input type="text" id="tenantId" name="tenantId" class="input-field" />
		</label> 
		
		<label for="field2">
			<span>Tenant Name <span class="required">*</span></span>
			<input type="text" id="tenantName" name="tenantName" class="input-field" />
		</label> 
		
		<label for="field3">
			<span>User ID <span class="required">*</span></span>
			<input type="text" id="userId" name="userId" class="input-field" />
		</label> 
		
		<label for="field4">
			<span>User Name <span class="required">*</span></span>
			<input type="text" id="userName" name="userName" class="input-field" />
		</label> 
		
		<label for="field5">
			<span>Password <span class="required">*</span></span>
			<input type="password" id="password" name="password" class="input-field" />
		</label> 
		
		<label for="field6">
			<span>Confirm Password <span class="required">*</span></span>
			<input type="password" id="passwordConfirm" name="passwordConfirm" class="input-field" />
		</label> 
		<div class="form-style-2">
			<div class="form-style-2-heading">App 플랜 목록</div>
			<div class="CSSTableGenerator" id="planDiv">
                
            </div>
          </div>
          <br/>
		<input type="button" value="회원가입" id="subscribe" name="subscribe">
		</form>
	</div>

            
</body>
</html>