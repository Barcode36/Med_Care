<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<script type="text/javascript">
function savetime(){
	var lvl=$("#levelid").html();
	if($("#seltime").val()==""){
		alert("Please Select Time");
	}else{
	$("#timerDialog").dialog('close');
	if(lvl=="Level 1"){
		$("#seltimedisp1").html('<b>'+$("#seltime").val().trim()+'</b>');	
	}
	
	if(lvl=="Level 2"){
		$("#seltimedisp2").html('<b>'+$("#seltime").val().trim()+'</b>');	
	}
	if(lvl=="Level 3"){
		$("#seltimedisp3").html('<b>'+$("#seltime").val().trim()+'</b>');	
	}
	if(lvl=="Level 4"){
		$("#seltimedisp4").html('<b>'+$("#seltime").val().trim()+'</b>');	
	}
	if(lvl=="Level 5"){
		$("#seltimedisp5").html('<b>'+$("#seltime").val().trim()+'</b>');	
	}
	
	}
}

</script>

<meta   http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

</head>
<body>

<sj:datepicker id="seltime"  placeholder="Enter Time" showOn="focus" timepicker="true" timepickerOnly="true" timepickerGridHour="4" timepickerGridMinute="10" timepickerStepMinute="10" cssClass="textField"/>
<sj:a cssClass="button"  onclick="savetime();" href="#">Save</sj:a>
</body>
</html>