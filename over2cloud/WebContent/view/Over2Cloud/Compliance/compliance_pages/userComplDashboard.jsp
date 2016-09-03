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
function showComplDetails(deptId,status)
{
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/Compliance/compliance_pages/userDashboardData.action?departId="+deptId+"&status="+status,
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
<c:set var="lDE_deptID" value="${departId}" />
<c:set var="lDE_tc" value="${departName}" />
<c:set var="lp_tc" value="${weekly_pending}" />
<c:set var="lsn_tc" value="${monthly_pending}" />
<c:set var="lhp_tc" value="${yearly_pending}" /> 
<div class="page_title"><h1>Operation Task>>User's Dashboard</h1></div>
<div class="container_block">
<div style=" float:left; padding:20px 5%; width:90%;">

<table border="1" width="100%" align="center">

      <tr>
		<td width="20%" align="center"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><b>&nbsp;Department</b></font></td>
		<td width="20%" align="center"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><b>&nbsp;Due(1W)</b></font></td>
		<td width="20%" align="center"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><b>&nbsp;Due(1M)</b></font></td>
		<td width="20%" align="center"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><b>&nbsp;Due(1Y)</b></font></td>
	 </tr>
	
	 <tr>
		<td width="20%" align="center"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><b>&nbsp;<c:out value="${lDE_tc}" /></b></font></td>
		<td width="20%" align="center"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;" onclick="showComplDetails('<c:out value="${lDE_deptID}" />','W');" href="#"><b>&nbsp;<c:out value="${weekly_pending}" /></b></a></font></td>
	    <td width="20%" align="center"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;" onclick="showComplDetails('<c:out value="${lDE_deptID}" />','M');" href="#"><b>&nbsp;<c:out value="${monthly_pending}" /></b></a></font></td>
		<td width="20%" align="center"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;" onclick="showComplDetails('<c:out value="${lDE_deptID}" />','Y');" href="#"><b>&nbsp;<c:out value="${yearly_pending}" /></b></a></font></td>
	</tr>
	
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
