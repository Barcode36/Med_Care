<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

<table border="1" width="100%" align="center">
    <tr>
		<td align="center" width="70%"   bgcolor="#F64D54" style=" color:#ffffff; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;"><b>&nbsp; Category</b></td>
		<td align="center" width="30%" bgcolor="#F64D54" style=" color:#ffffff; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;"><b>&nbsp;Counter</b></td>
	</tr>
</table>

<s:iterator id="rsCompl1dfr4245"  status="status" value="%{catgCountList}" >
	<table border="1" width="100%" align="center">
 	<tr>
 	    <td align="left" width="70%"   bgcolor="#EDDBDA" style=" color:#125EA9; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;"><b><s:property value="%{feedback_catg}"/></b></td>
		<td align="center" width="30%" bgcolor="#EDDBDA" style=" color:#125EA9; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;" onclick="getCategoryData('<s:property value="%{id}"/>');"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="%{counter}"/></b></a></td>
 	</tr>
 	</table>
	</s:iterator>
</body></html>