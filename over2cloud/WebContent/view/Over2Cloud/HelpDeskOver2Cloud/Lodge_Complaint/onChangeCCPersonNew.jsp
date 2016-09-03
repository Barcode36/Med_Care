<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="js/multiselect/jquery-ui.css" rel="stylesheet" type="text/css" />
<link href="js/multiselect/jquery.multiselect.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<s:url value="/js/multiselect/jquery-ui.min.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/multiselect/jquery.multiselect.js"/>"></script>

<title>Insert title here</title>
<script type="text/javascript">
$(document).ready(function()
	{
		$("#ccContactPerson").multiselect({
		   show: ["bounce", 200],
		   hide: ["explode", 1000]
		});
	});
</script>
</head>
<body>

		<div class="newColumn">
			<div class="leftColumn1" style="margin-left: -11px; margin-top:8px;width:90px">CC Contact:</div>
			<div class="rightColumn1" style="margin-left: 98px;margin-top:-37px">
			  <s:select
			id="ccContactPerson"
			name="ccContactPerson"
			list="ccPerson"
			cssClass="select"
		    cssStyle="width:28%"
		    multiple="true"
		    
		    ></s:select></div>
</div>
</body>
</html>