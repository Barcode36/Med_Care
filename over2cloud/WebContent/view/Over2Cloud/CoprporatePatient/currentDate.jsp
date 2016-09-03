<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
		<s:if test="%{fetchDate=='today'}">
			<s:textfield id="preferred_time" name="preferred_time" value="%{today}" cssClass="textField" theme="simple" placeHolder="Enter Date and Time"   />
		</s:if>
		<s:elseif test="%{fetchDate=='tomorrow'}">
			<s:textfield id="preferred_time" name="preferred_time" value="%{tomorrow}" cssClass="textField" theme="simple" placeHolder="Enter Date and Time"   />
		</s:elseif>
</body>
</html>