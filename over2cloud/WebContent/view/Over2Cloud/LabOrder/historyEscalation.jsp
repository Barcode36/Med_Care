<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<s:if test="%{esc_list.size>0}">
<table class="CSSTableGenerator"> 
		 	<tr>
		     	<td colspan="3"><b>Escalation</b></td>
	 		</tr>
	 	<tr>
		 
				 <td style="background-color: lavender;color: firebrick;"><b>Level</b></td>
				 <td style="background-color: lavender;color: firebrick;"><b>Escalate To</b></td>
			   	 <td style="background-color: lavender;color: firebrick;"><b>Action At</b></td>
  				 
  				 
	 	</tr>
		
		<s:iterator id="rsCompl"  status="status" value="%{esc_list}" >
			 	 	<tr>
				 	 	<td><b><s:property value="#rsCompl.level"/></b></td> 
				 	 	<td><b><a href="#" ><s:property value="#rsCompl.history_id"/></a></b></td> 
				 	   	<td><b><s:property value="#rsCompl.actionDate"/>&nbsp;&nbsp;&nbsp;<s:property value="#rsCompl.actionTime"/></b></td>  
	 				</tr>
		</s:iterator>
 
</table>
</s:if>
<s:else>
		
		<center><h2 style='color:blue;'>No Escalation Found</h2></center>
</s:else>
</body>
</html>