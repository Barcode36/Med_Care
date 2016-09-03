<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%String userRights = session.getAttribute("userRights") == null ? "" : session.getAttribute("userRights").toString(); %>
<html>
<head>


<script type="text/javascript" src="<s:url value="/js/leaveManagement/validation.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/leaveManagement/leave.js"/>"></script>
</head>
<body>
<s:url id="fileDownload" action="download" ></s:url>
 <s:property value="%{empDocument}"/>
 
 
 
<s:if test="%{empProfessionalViewMap != null && empProfessionalViewMap.size > 0}">
	<table width="100%">
	<tr style="background-color: #FFCC66"><td colspan="4" align="Center"><STRONG>Professional Details</STRONG>
	<s:iterator value="%{empProfessionalViewMap}">
		<tr  style="background-color: #FFEBCC">
		<td>
		<s:property value="%{empDocument}"/> &nbsp;&nbsp;
		<a href="<%=request.getContextPath()%>/view/Over2Cloud/hr/download.action?fileName=<s:property value="%{empDocument}"/>">
			<img src="images/docDownlaod.jpg" width="15" height="15" alt="Download" title="Download" />
		</a>
		</td>
		</tr>
	</s:iterator>
	</table>	
	</s:if>	
	
	<s:else>
		<b>No Record Found</b>
	</s:else>


</body>
</html>