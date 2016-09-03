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
<s:url id="fileDownload" action="downloadBankDetails" ></s:url>
 <s:property value="%{empDocument}"/>
 
 
 
<s:if test="%{empBasicFullViewMap != null && empBasicFullViewMap.size > 0}">
	<table width="100%">
	<tr style="background-color: #66CCFF"><td colspan="4" align="Center"><STRONG>Bank Details</STRONG>
	<s:iterator value="%{empBasicFullViewMap}">
		<tr  style="background-color: #C2EBFF">
		<td>
		<s:property value="%{value}"/> &nbsp;&nbsp;
		<a href="<%=request.getContextPath()%>/view/Over2Cloud/hr/downloadBankDetails.action?fileName=<s:property value="%{value}"/>">
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