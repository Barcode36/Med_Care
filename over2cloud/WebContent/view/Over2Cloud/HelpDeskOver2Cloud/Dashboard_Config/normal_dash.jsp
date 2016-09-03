<%@ page language="java" contentType="text/html; charset=ISO-8859-1"  pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s"  uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="c"  uri="http://java.sun.com/jstl/core_rt" %>
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title></title>
<SCRIPT type="text/javascript">
function getData(status)
{
	  $("#confirmEscalationDialog").dialog('open');
	$.ajax({
	    type : "post",
	    url : "/cloudapp/view/Over2Cloud/HelpDeskOver2Cloud/Lodge_Feedback/beforeFeedAction.action?feedStatus="+status+"&callFrom=dash",
	    success : function(subdeptdata) {
       $("#confirmingEscalation").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });
}
</SCRIPT>
</head>
<body>
<c:set var="p_tc" value="${pending}" />
<c:set var="sn_tc" value="${snooze}" />
<c:set var="hp_tc" value="${hp}" /> 
<c:set var="ig_tc" value="${ignore}" /> 
<c:set var="rs_tc" value="${resolved}"/>
<div class="page_title"><h1>Feedback >> Dashboard</h1></div>
<div class="container_block"><div style=" float:left; padding:20px 5%; width:90%;">
<div style="height: 300px; overflow: auto;">
<table border="1" width="100%" align="center">
    <tr>
		<td width="20%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><b>&nbsp;Pending</b></font></td>
		<td width="20%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><b>&nbsp;Snooze</b></font></td>
		<td width="20%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><b>&nbsp;High Priority</b></font></td>
		<td width="20%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><b>&nbsp;Ignore</b></font></td>
		<td width="20%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><b>&nbsp;Resolved</b></font></td>
	</tr>
    <tr>
		<td width="20%" onclick="getData('Pending');"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><b>&nbsp;<c:out value="${p_tc}" /></b></font></td>
		<td width="20%" onclick="getData('Snooze');"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><b>&nbsp;<c:out value="${sn_tc}" /></b></font></td>
	    <td width="20%" onclick="getData('High Priority');"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><b>&nbsp;<c:out value="${hp_tc}" /></b></font></td>
		<td width="20%" onclick="getData('Ignore');"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><b>&nbsp;<c:out value="${ig_tc}" /></b></font></td>
 	    <td width="20%" onclick="getData('Resolved');"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><b>&nbsp;<c:out value="${rs_tc}" /></b></font></td>
	</tr>
</table>
</div>
</div>
</div>
 <center>
			<sj:dialog id="confirmEscalationDialog" autoOpen="false"  closeOnEscape="true" modal="true" title="Confirm Feedback Via Call" minWidth="1050" maxWidth="1050" height="550" showEffect="slide" hideEffect="explode" >
                     <div id="confirmingEscalation"></div>
            </sj:dialog>
 </center>
</body>
</html>
