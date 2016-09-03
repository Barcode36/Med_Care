<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script type="text/javascript">



</script>
</head>
<body>
<div>

			

			
			
			
			<table border="1" width="100%" style="border-collapse: collapse;">
	   
				<tr bgcolor="#D8D8D8" style="height: 25px">
					<s:iterator value="detailsCons">
						<s:if test="key=='Name' || key=='Speciality' ">
							<td width="15%"><b><s:property value="%{key}"/>: <b></td>
							<td width="15%"><s:property value="%{value}"/></td>
						</s:if>
					</s:iterator>
				</tr>
				
				<tr  style="height: 25px">
					<s:iterator value="detailsCons">
						<s:if test="key=='Communication Name' || key=='Pancard' ">
							<td width="15%"><b><s:property value="%{key}"/>: <b></td>
							<td width="15%"><s:property value="%{value}"/></td>
						</s:if>
					</s:iterator>
				</tr>
				
				<tr bgcolor="#D8D8D8" style="height: 23px">
					<s:iterator value="detailsCons">
						<s:if test="key=='Address' || key=='Location'">
							<td width="15%"><b><s:property value="%{key}"/>: <b></td>
							<td width="15%"><s:property value="%{value}"/></td>
						</s:if>
					</s:iterator>
				</tr>
				
				<tr style="height: 25px">
				 
					<s:iterator value="detailsCons">
						<s:if test="key == 'Mobile 1:' ||  key == 'Email Id 1:'">
							<td width="15%"><b><s:property value="%{key}"/> <b></td>
							<td width="15%"><s:property value="%{value}"/></td>
						</s:if>
						
					</s:iterator>
					
				</tr>
				
			  <tr bgcolor="#D8D8D8" style="height: 25px">
					<s:iterator value="detailsCons">
						<s:if test="key == 'Mobile 2:' || key=='Email Id 2:' ">
							<td width="15%"><b><s:property value="%{key}"/> <b></td>
							<td width="15%"><s:property value="%{value}"/></td>
						</s:if>
					</s:iterator>
				</tr>    
				<tr style="height: 25px">
				 
					<s:iterator value="detailsCons">
						<s:if test="key == 'Mobile 3:' ||  key == 'Email Id 3:'">
							<td width="15%"><b><s:property value="%{key}"/> <b></td>
							<td width="15%"><s:property value="%{value}"/></td>
						</s:if>
						
					</s:iterator>
					
				</tr>
				
			  <tr bgcolor="#D8D8D8" style="height: 25px">
					<s:iterator value="detailsCons">
						<s:if test="key == 'Mobile 4:' || key=='Email Id 4:' ">
							<td width="10%"><b><s:property value="%{key}"/> <b></td>
							<td width="10%"><s:property value="%{value}"/></td>
						</s:if>
					</s:iterator>
				</tr>   	
					<tr style="height: 25px">
				 
					<s:iterator value="detailsCons">
						<s:if test="key == 'Mobile 5:' ||  key == 'Email Id 5:'">
							<td width="15%"><b><s:property value="%{key}"/> <b></td>
							<td width="15%"><s:property value="%{value}"/></td>
						</s:if>
						
					</s:iterator>
				
				</tr>
				
				 
		</table>
			
			</div>
	
	<div id="datapart1"></div>		
</body>
</html>