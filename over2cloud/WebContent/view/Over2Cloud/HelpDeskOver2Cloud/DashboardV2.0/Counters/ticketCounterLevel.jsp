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
		<td align="center" width="38%" bgcolor="#25B0C4" style=" color:#ffffff; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;"><b>&nbsp; Level</b></td>
		<td align="center" width="14%" bgcolor="#25B0C4" style=" color:#ffffff; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;"><b>&nbsp;PN</b></td>
		<td align="center" width="15%" bgcolor="#25B0C4" style=" color:#ffffff; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;"><b>&nbsp;HP</b></td>
		<td align="center" width="14%" bgcolor="#25B0C4" style=" color:#ffffff; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;"><b>&nbsp;SN</b></td>
		<td align="center" width="19%" bgcolor="#25B0C4" style=" color:#ffffff; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;"><b>&nbsp;RS</b></td>
	</tr>
</table>
<div style="overflow:auto; height:200px;">
<s:iterator id="rsCompl1dfrgcvxfzcvzdf"  status="status" value="%{leveldashObj.dashList}" >
	<table border="1" width="100%" align="center">
 	<tr>
 	    <td align="left" width="38%"   bgcolor="#D5EAED" style=" color:#125EA9; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;"><b><s:property value="#rsCompl1dfrgcvxfzcvzdf.level"/></b></td>
		<td align="center" width="14%" bgcolor="#D5EAED" style=" color:#125EA9; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;" onclick="getData('<s:property value="#rsCompl.id"/>','Pending','L','dataFor','<s:property value="#rsCompl1dfrgcvxfzcvzdf.level"/>');"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl1dfrgcvxfzcvzdf.pc"/></b></a></td>
		<td align="center" width="15%" bgcolor="#D5EAED" style=" color:#125EA9; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;" onclick="getData('<s:property value="#rsCompl.id"/>','High Priority','L','dataFor','<s:property value="#rsCompl1dfrgcvxfzcvzdf.level"/>');"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl1dfrgcvxfzcvzdf.hpc"/></b></a></td>
		<td align="center" width="14%" bgcolor="#D5EAED" style=" color:#125EA9; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;" onclick="getData('<s:property value="#rsCompl.id"/>','Snooze','L','dataFor','<s:property value="#rsCompl1dfrgcvxfzcvzdf.level"/>');"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl1dfrgcvxfzcvzdf.sc"/></b></a></td>
		<td align="center" width="19%" bgcolor="#D5EAED" style=" color:#125EA9; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;" onclick="getData('<s:property value="#rsCompl.id"/>','Resolved','L','dataFor','<s:property value="#rsCompl1dfrgcvxfzcvzdf.level"/>');"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl1dfrgcvxfzcvzdf.rc"/></b></a></td>
 	</tr>
 	</table>
	</s:iterator>
	</div>
	</body></html>