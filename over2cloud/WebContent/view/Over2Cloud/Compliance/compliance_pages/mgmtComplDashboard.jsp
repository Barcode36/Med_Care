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
<STYLE type="text/css">
td:onHover{
background: red;
}
</STYLE>
<script type="text/javascript">

function showComplDetails(deptId,frequency,totalOrMissed)
{
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/Compliance/compliance_pages/mgmtDashboardData.action?departId="+deptId+"&frequency="+frequency+"&totalOrMissed="+totalOrMissed+"&data4=mgmtDashboard",
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

function showAgeingComplDetails(deptId,frequency,totalOrMissed)
{
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/Compliance/compliance_pages/mgmtDashboardData.action?departId="+deptId+"&frequency="+frequency+"&totalOrMissed="+totalOrMissed+"&data4=ageingDashboard",
	    success : function(data) 
	    {
	    	$("#complianceAgeingDialog").dialog('open');
			$("#datadivageing").html(data);
	},
	   error: function() {
	        alert("error");
	    }
	 });
}

</script>
</head>
<body>
<div class="page_title"><h1>Operation Task >> Management's Dashboard</h1></div>
<div class="container_block">
<div style=" float:left; padding:20px 5%; width:90%;">
<div style="height: 125px; overflow: auto;">
<table border="1" width="100%" align="center" style="border-collapse: collapse; table-layout: fixed;">
<tbody>

 <tr bgcolor="#909090">
	<td align="center" rowspan="2" width="20%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b>Department</b></font></td>
	<td colspan="2" align="center" width="25%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b>Yearly</b></font></td>
	<td colspan="2" align="center" width="25%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b>Monthly</b></font></td>
	<td colspan="2" align="center" width="25%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b>Weekly</b></font></td>
 </tr>

 <tr bgcolor="#909090">
	<td align="center" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><b>Total</b></font></td>
	<td align="center" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><b>Missed</b></font></td>
	
	<td align="center" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><b>Total</b></font></td>
	<td align="center" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><b>Missed</b></font></td>
	
	<td align="center" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><b>Total</b></font></td>
	<td align="center" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><b>Missed</b></font></td>
 </tr>

<s:iterator id="pendingCompl"  status="status" value="%{complBeanForDashboard.complList}" >
<% count++; %>
 <tr style="<%if(count%2==0){ %>background-color:#ffffff;<%}else{%>background-color:#F9F9F9;<%} %>">
	<td align="center" width="40%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;" href="#"><s:property value="#pendingCompl.departName" /></a></font></td>
	<td align="center" width="40%" onclick="showComplDetails('<s:property value="#pendingCompl.departId"/>','Y','Total');"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="#pendingCompl.annualTotal"/></a></font></td>
	<td align="center" width="40%" onclick="showComplDetails('<s:property value="#pendingCompl.departId" />','Y','Missed');"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="#pendingCompl.annualMissed"/></a></font></td>
	
	<td align="center" width="40%" onclick="showComplDetails('<s:property value="#pendingCompl.departId"/>','M','Total');"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="#pendingCompl.monthlyTotal"/></a></font></td>
	<td align="center" width="40%" onclick="showComplDetails('<s:property value="#pendingCompl.departId"/>','M','Missed');"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="#pendingCompl.monthlyMissed"/></a></font></td>
	
	<td align="center" width="40%" onclick="showComplDetails('<s:property value="#pendingCompl.departId"/>','W','Total');"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="#pendingCompl.weeklyTotal"/></a></font></td>
	<td align="center" width="40%" onclick="showComplDetails('<s:property value="#pendingCompl.departId"/>','W','Missed');"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="#pendingCompl.weeklyMissed"/></a></font></td>
 </tr>
</s:iterator>
<tr bgcolor="#909090">
	<td align="center" width="20%" height="40%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2">Total</td>
	<td align="center" width="12%" height="40%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><s:property value="allYearlyTotal"/></td>
	<td align="center" width="12%" height="40%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><s:property value="allYearlyMissedTotal"/></td>
	
	<td align="center" width="12%" height="40%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><s:property value="allMonthlyTotal"/></td>
	<td align="center" width="12%" height="40%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><s:property value="allMonthlyMissedTotal"/></td>
	
	<td align="center" width="12%" height="40%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><s:property value="allWeeklyTotal"/></td>
	<td align="center" width="12%" height="40%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><s:property value="allWeeklyMissedTotal"/></td>
 </tr>
</tbody>
</table>
</div>
</div>
</div>

<div class="page_title"><h1>Operation Task >> Ageing Dashboard</h1></div>
<div class="container_block">
<div style=" float:left; padding:20px 5%; width:90%;">
<div style="height: 200px; overflow: auto;">
<table border="1" width="100%" align="center" style="border-collapse: collapse; table-layout: fixed;">
<tbody>

 <tr bgcolor="#909090">
	<td  align="center" width="20%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b>Department</b></font></td>
	<td  align="center" width="20%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b>Due of this Week</b></font></td>
	<td  align="center" width="20%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b>Due of this Month</b></font></td>
	<td  align="center" width="20%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b>Due of this Year</b></font></td>
 </tr>

<s:iterator id="pendingAgeingCompl"  status="status" value="%{complageingDashboard.complList}" >
<% count++; %>
 <tr style="<%if(count%2==0){ %>background-color:#ffffff;<%}else{%>background-color:#F9F9F9;<%} %>">
	<td align="center" width="40%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;" href="#"><s:property value="#pendingAgeingCompl.departName" /></a></font></td>
	
	<td align="center" width="40%" onclick="showAgeingComplDetails('<s:property value="#pendingAgeingCompl.departId"/>','W','Total');"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="#pendingAgeingCompl.weeklyTotal"/></a></font></td>
	
	<td align="center" width="40%" onclick="showAgeingComplDetails('<s:property value="#pendingAgeingCompl.departId"/>','M','Total');"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;" href="#"><s:property value="#pendingAgeingCompl.monthlyTotal"/></a></font></td>
	
	<td align="center" width="40%" onclick="showAgeingComplDetails('<s:property value="#pendingAgeingCompl.departId"/>','Y','Total');"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="#pendingAgeingCompl.annualTotal"/></a></font></td>
	
	
 </tr>
</s:iterator>
<tr bgcolor="#909090">
	<td align="center" width="20%" height="40%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2">Total</td>
	<td align="center" width="12%" height="40%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><s:property value="allAgeingWeeklyTotal"/></td>
	
	<td align="center" width="12%" height="40%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><s:property value="allAgeingMonthlyTotal"/></td>
	
	<td align="center" width="12%" height="40%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><s:property value="allAgeingYearlyTotal"/></td>
 </tr>
</tbody>
</table>
</div>
</div>
</div>
			<sj:dialog 
	 			id				=	"complianceDialog" 
	 			modal			=	"true" 
	 			effect			=	"slide" 
	 			autoOpen		=	"false" 
	 			width			=	"1000" 
	 			title			=	"Operation Task Management Dashboard  >>  View" 
	 			overlayColor	=	"#fff" 
	 			overlayOpacity	=	"1.0" 
	 			position="['center','center']"
	 			>
	 			<div id="datadiv"></div>
     		</sj:dialog>
     		
     		<sj:dialog 
	 			id				=	"complianceAgeingDialog" 
	 			modal			=	"true" 
	 			effect			=	"slide" 
	 			autoOpen		=	"false" 
	 			width			=	"1000" 
	 			title			=	"Operation Task Ageing Dashboard  >>  View" 
	 			overlayColor	=	"#fff" 
	 			overlayOpacity	=	"1.0" 
	 			position="['center','center']"
	 			>
	 			<div id="datadivageing"></div>
     		</sj:dialog>
     		
</body>
</html>
