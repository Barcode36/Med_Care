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
    <s:if test="hodFlag">
		<td align="center" bgcolor="#138F6A" style=" color:#ffffff; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;"><b>&nbsp;Time</b></td>
	</s:if>
	<s:elseif test="mgmtFlag">
	<td align="center"bgcolor="#138F6A" style=" color:#ffffff; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;"><b>&nbsp;Time</b></td>
	</s:elseif>
	<s:elseif test="normalFlag">
	<td align="center" bgcolor="#138F6A" style=" color:#ffffff; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;"><b>&nbsp;Time</b></td>
	</s:elseif>
		<td align="center"  bgcolor="#138F6A" style=" color:#ffffff; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;"><b>&nbsp;Pending</b></td>
		<td align="center"  bgcolor="#138F6A" style=" color:#ffffff; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;"><b>&nbsp;Park</b></td>
		<td align="center" bgcolor="#138F6A" style=" color:#ffffff; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;"><b>&nbsp;Resolve</b></td>
		<td align="center"  bgcolor="#138F6A" style=" color:#ffffff; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;"><b>&nbsp;Ignore</b></td>
		<td align="center"  bgcolor="#138F6A" style=" color:#ffffff; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;"><b>&nbsp;Re-Assign</b></td>
		<td align="center" bgcolor="#138F6A" style=" color:#ffffff; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;"><b>&nbsp;Re-Open</b></td>
		<td align="center" bgcolor="#138F6A" style=" color:#ffffff; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;"><b>&nbsp;Total</b></td>
	</tr>


<s:iterator id="rsCompl"  status="status" value="%{dashObj.dashList}" >
	 	<tr>
	 	   
	 	    <s:if test="#rsCompl.time=='Total'">
	 	  <td align="left"      bgcolor="#C3E8D1" style="color:#004276;background:#C2C2CC"><b><s:property value="#rsCompl.time"/></b></td>
	 	    <td align="center"   bgcolor="#C3E8D1" style="color:#004276;background:#C2C2CC" onclick="getData('','Pending','TD','dataFor','<s:property value="#rsCompl.id"/>');"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.pct"/></b></a></td>
			<td align="center"   bgcolor="#C3E8D1" style="color:#004276;background:#C2C2CC" onclick="getData('','Snooze','TD','dataFor','<s:property value="#rsCompl.id"/>');"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.sct"/></b></a></td>
			<td align="center"   bgcolor="#C3E8D1" style="color:#004276;background:#C2C2CC" onclick="getData('','Resolved','TD','dataFor','<s:property value="#rsCompl.id"/>');"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.rct"/></b></a></td>
			<td align="center"   bgcolor="#C3E8D1" style="color:#004276;background:#C2C2CC" onclick="getData('','Ignore','TD','dataFor','<s:property value="#rsCompl.id"/>');"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.ignt"/></b></a></td>
			<td align="center"    bgcolor="#C3E8D1" style="color:#004276;background:#C2C2CC " onclick="getData('','Re-assigned','TD','dataFor','<s:property value="#rsCompl.id"/>');"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.ract"/></b></a></td>
			<td align="center"   bgcolor="#C3E8D1" style="color:#004276;background:#C2C2CC " onclick="getData('','Re-opened','TD','dataFor','<s:property value="#rsCompl.id"/>');"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.reopct"/></b></a></td>
		    <td align="center"   bgcolor="#C3E8D1" style="color:#004276;background:#C2C2CC " onclick="getData('','All','TD','dataFor','<s:property value="#rsCompl.id"/>');"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.grand"/></b></a></td>
		    </s:if>
		    <s:else>
		     <td align="left"      bgcolor="#C3E8D1" style=" color:#125EA9; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;"><b><s:property value="#rsCompl.time"/></b></td>
	 	    <td align="center"   bgcolor="#C3E8D1" style=" color:#125EA9; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;" onclick="getData('','Pending','TD','dataFor','<s:property value="#rsCompl.id"/>');"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.pc"/></b></a></td>
			<td align="center"   bgcolor="#C3E8D1" style=" color:#125EA9; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;" onclick="getData('','Snooze','TD','dataFor','<s:property value="#rsCompl.id"/>');"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.sc"/></b></a></td>
			<td align="center"   bgcolor="#C3E8D1" style=" color:#125EA9; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;" onclick="getData('','Resolved','TD','dataFor','<s:property value="#rsCompl.id"/>');"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.rc"/></b></a></td>
			<td align="center"   bgcolor="#C3E8D1" style=" color:#125EA9; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;" onclick="getData('','Ignore','TD','dataFor','<s:property value="#rsCompl.id"/>');"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.igc"/></b></a></td>
			<td align="center"    bgcolor="#C3E8D1" style=" color:#125EA9; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;" onclick="getData('','Re-assigned','TD','dataFor','<s:property value="#rsCompl.id"/>');"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.rac"/></b></a></td>
			<td align="center"   bgcolor="#C3E8D1" style=" color:#125EA9; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;" onclick="getData('','Re-opened','TD','dataFor','<s:property value="#rsCompl.id"/>');"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.reopc"/></b></a></td>
			<td align="center"   bgcolor="#C3E8D1" style="color:#004276;background:#C2C2CC; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;" onclick="getData('','All','TD','dataFor','<s:property value="#rsCompl.id"/>');"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.total"/></b></a></td>
			</s:else>
	 	</tr>
 	
</s:iterator>
</table>

</body>
