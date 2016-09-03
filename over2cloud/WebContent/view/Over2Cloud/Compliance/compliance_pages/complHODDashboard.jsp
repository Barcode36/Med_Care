<%@ page language="java" contentType="text/html; charset=ISO-8859-1"  pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s"  uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<%@ taglib prefix="c"  uri="http://java.sun.com/jstl/core_rt" %>
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title></title>
<script type="text/javascript">
function showComplDetails(deptId,status,frequency)
{
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/Compliance/compliance_pages/mgmtDashboardData.action?departId="+deptId+"&status="+status+"&frequency="+frequency,
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
<c:set var="lDE_tc" value="${departName}" />
<c:set var="lp_tc" value="${weekly_pending}" />
<c:set var="lsn_tc" value="${monthly_pending}" />
<c:set var="lhp_tc" value="${yearly_pending}" /> 
<div class="page_title"><h1>Operation Task>>HOD's Dashboard</h1></div>
<div class="container_block"><div style=" float:left; padding:20px 5%; width:90%;">
<table border="1" width="100%" align="center">
    <tr>
		<td align="center" width="33%" style=" color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;"><b>&nbsp;Department</b></td>
		<td align="center" width="20%" style=" color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;"><b>&nbsp;Due(1W)</b></td>
		<td align="center" width="20%" style=" color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;"><b>&nbsp;Due(1M)</b></td>
		<td align="center" width="20%" style=" color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;"><b>&nbsp;Due(1Y)</b></td>
	</tr>
</table>
<s:iterator id="pendingCompl"  status="status" value="%{complBeanForDashboard.complList}" >
<table border="1" width="100%" align="center">
 <tr>
	<td align="center" width="20%" style=" color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;"><a style="cursor: pointer;text-decoration: none;" href="#"><s:property value="#pendingCompl.departName" /></a></td>
	<td align="center" width="12%" style=" color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;"><a style="cursor: pointer;text-decoration: none;" onclick="showComplDetails('<s:property value="#pendingCompl.departId"/>','Pending','recurring');" href="#"><s:property value="#pendingCompl.dueWeekly"/></a></td>
	<td align="center" width="12%" style=" color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;"><a style="cursor: pointer;text-decoration: none;" onclick="showComplDetails('<s:property value="#pendingCompl.departId" />','Pending','oneTime');" href="#"><s:property value="#pendingCompl.dueMonthly"/></a></td>
	<td align="center" width="12%" style=" color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;"><a style="cursor: pointer;text-decoration: none;" onclick="showComplDetails('<s:property value="#pendingCompl.departId" />','Pending','oneTime');" href="#"><s:property value="#pendingCompl.dueYearly"/></a></td>
 </tr>
 </table>
</s:iterator>
</div>
</div>
<sj:dialog 
	 			id				=	"complianceDialog" 
	 			modal			=	"true" 
	 			effect			=	"slide" 
	 			autoOpen		=	"false" 
	 			width			=	"870" 
	 			title			=	"Operation Task" 
	 			overlayColor	=	"#fff" 
	 			overlayOpacity	=	"1.0" 
	 			>
	 			<div id="datadiv"></div>
     		</sj:dialog>
</body>
</html>
