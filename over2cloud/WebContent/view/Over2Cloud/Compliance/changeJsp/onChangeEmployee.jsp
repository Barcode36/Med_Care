<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
<meta   http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>
<body>
	
<s:select  
	id		    ="employee"
	name		="employee"
	list		="empList"
	headerKey	="-1"
	headerValue="Select Employee" 
	multiple   = "true"
	cssClass="textField"
	cssStyle="height: 75px;"
	>
</s:select>
</body>
</html>