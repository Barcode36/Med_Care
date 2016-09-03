<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<s:if test="#session['uName']==null || #session['uName']==''">
<jsp:forward page="/view/common_pages/invalidSession.jsp"/>
</s:if>
<%
int count=0;
%>
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<meta   http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link   type="text/css" href="<s:url value="/css/style.css"/>" rel="stylesheet" />
<script type="text/javascript" src="<s:url value="/js/compliance/compliance.js"/>"></script>
<script type="text/javascript">

function showComplDetails(deptId,frequency,totalOrMissed)
{
	$.ajax({
	    type : "post",
	    url : "/cloudapp/view/Over2Cloud/Compliance/compliance_pages/mgmtDashboardData.action?departId="+deptId+"&frequency="+frequency+"&totalOrMissed="+totalOrMissed+"&data4=ageingDashboard",
	    success : function(data) 
	    {
	    	$("#complianceDialog").dialog('open');
			$("#datadiv").html(data);
	},
	   error: function() {
	        alert("error");
	    }
	 });
}
</script>
</head>
<body>
<div class="page_title"><h1>Operation Task >> Ageing Dashboard</h1></div>
<div class="container_block">
<div style=" float:left; padding:20px 5%; width:90%;">
<table border="1" width="100%" align="center" style="border-collapse: collapse; table-layout: fixed;">
<tbody>

 <tr bgcolor="#909090">
	<td  align="center" width="20%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b>Department</b></font></td>
	<td  align="center" width="20%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b>Due of this Week</b></font></td>
	<td  align="center" width="20%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b>Due of this Month</b></font></td>
	<td  align="center" width="20%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b>Due of this Year</b></font></td>
 </tr>

<s:iterator id="pendingCompl"  status="status" value="%{complBeanForDashboard.complList}" >
<% count++; %>
 <tr style="<%if(count%2==0){ %>background-color:#ffffff;<%}else{%>background-color:#F9F9F9;<%} %>">
	<td align="center" width="40%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;" href="#"><s:property value="#pendingCompl.departName" /></a></font></td>
	
	<td align="center" width="40%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;" onclick="showComplDetails('<s:property value="#pendingCompl.departId"/>','W','Total');" href="#"><s:property value="#pendingCompl.weeklyTotal"/></a></font></td>
	
	<td align="center" width="40%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;" onclick="showComplDetails('<s:property value="#pendingCompl.departId"/>','M','Total');" href="#"><s:property value="#pendingCompl.monthlyTotal"/></a></font></td>
	
	<td align="center" width="40%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;" onclick="showComplDetails('<s:property value="#pendingCompl.departId"/>','Y','Total');" href="#"><s:property value="#pendingCompl.annualTotal"/></a></font></td>
	
	
 </tr>
</s:iterator>
<tr bgcolor="#909090">
	<td align="center" width="20%" height="40%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2">Total</td>
	<td align="center" width="12%" height="40%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><s:property value="allWeeklyTotal"/></td>
	
	<td align="center" width="12%" height="40%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><s:property value="allMonthlyTotal"/></td>
	
	<td align="center" width="12%" height="40%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><s:property value="allYearlyTotal"/></td>
 </tr>
</tbody>
</table>
			<sj:dialog 
	 			id				=	"complianceDialog" 
	 			modal			=	"true" 
	 			effect			=	"slide" 
	 			autoOpen		=	"false" 
	 			width			=	"870" 
	 			title			=	"Operation Task Dashboard>>View" 
	 			overlayColor	=	"#fff" 
	 			overlayOpacity	=	"1.0" 
	 			>
	 			<div id="datadiv"></div>
     		</sj:dialog>
     		
    </div>
  </div>
</body>
</html>
