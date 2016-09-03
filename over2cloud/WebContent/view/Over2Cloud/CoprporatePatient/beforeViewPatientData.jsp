<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

<table border="1" width="99%" style="border-collapse: collapse;" align="center">
	   
				<tr bgcolor="#D8D8D8" style="height: 25px">
					<s:iterator value="patienceMap">
						<s:if test="key=='Country' || key=='State' ">
							<td width="10%"><b><s:property value="%{key}"/>: <b></td>
							<td width="10%"><s:property value="%{value}"/></td>
						</s:if>
					</s:iterator>
				</tr>
				
				<tr  style="height: 25px">
					<s:iterator value="patienceMap">
						<s:if test="key=='City' || key=='Mobile No.' ">
							<td width="10%"><b><s:property value="%{key}"/>: <b></td>
							<td width="10%"><s:property value="%{value}"/></td>
						</s:if>
					</s:iterator>
				</tr> 
				
				<tr bgcolor="#D8D8D8" style="height: 25px">
					<s:iterator value="patienceMap">
						<s:if test="key=='Email ID' || key=='Corporate Name' ">
							<td width="10%"><b><s:property value="%{key}"/>: <b></td>
							<td width="10%"><s:property value="%{value}"/></td>
						</s:if>
					</s:iterator>
				</tr>
				
					<tr  style="height: 25px">
					<s:iterator value="patienceMap">
						<s:if test="key=='Age' || key=='Gender' ">
							<td width="10%"><b><s:property value="%{key}"/>: <b></td>
							<td width="10%"><s:property value="%{value}"/></td>
						</s:if>
					</s:iterator>
				</tr>
				
				
				
						
						
				
		
		
		</table>
		
		
		
		
		
</body>
</html>