<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
<meta   http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="js/multiselect/jquery.multiselect.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<s:url value="/js/multiselect/jquery-ui.min.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/multiselect/jquery.multiselect.js"/>"></script>

<meta   http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<script type="text/javascript">

		$(document).ready(function()
		 {
		 	$("#register_by").multiselect({
		 	   show: ["", 200],
		 	   hide: ["explode", 1000],
		 	   minWidth: [200]
		 	});
		 });
</script>		

</head>
<body>
	
	<s:select  
		id="register_by" name="register_by" list="%{empList}" multiple= "true" cssStyle="height: 40%px;"
	>
</s:select>
</body>
</html>