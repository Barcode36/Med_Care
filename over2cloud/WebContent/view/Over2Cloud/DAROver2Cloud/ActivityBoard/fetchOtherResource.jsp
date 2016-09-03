<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<script type="text/javascript">
function onLoad()
{
	//$("#dateDiv").html($("#fromDate").val());
	//$("#dateVal").val($("#fromDate").val());
}
onLoad();
</script>
<body>
<s:if test="alreadyFree.size()>0">
<center><b>Already Free Resources</b></center>
<table border="1" width="100%" align="center">
<tbody>

 <tr bgcolor="#D8D8D8">
	<td  align="center" width="10%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b>Name</b></font></td>
	<td  align="center" width="10%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b>Task Name</b></font></td>
	<td  align="center" width="10%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b>Task Brief</b></font></td>
	<td  align="center" width="10%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b>Alloted By</b></font></td>
	<td  align="center" width="10%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b>Dates</b></font></td>
	<td  align="center" width="10%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b>Status</b></font></td>
	<td  align="center" width="10%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b>Today Activity</b></font></td>
	<td  align="center" width="10%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b>Functional Review</b></font></td>
	<td  align="center" width="10%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b>Technical Review</b></font></td>
	<td  align="center" width="10%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b>Tomorrow Task</b></font></td>
 </tr>

<s:iterator id="levelWiseCompl"  status="status" value="%{alreadyFree}" >
<s:if test="#status.odd">
<tr>
	    <td align="center" width="10%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;" href="#"><s:property value="%{allotedtoo}" /></a></font></td>
		<td align="center" width="10%" ><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="#levelWiseCompl.tasknameee"/></a></font></td>
		<td align="center" width="10%" ><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;" href="#"><s:property value="#levelWiseCompl.specificTask"/></a></font></td>
		<td align="center" width="10%" ><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="#levelWiseCompl.allotedbyy"/></a></font></td>
		<td align="center" width="10%" ><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;" href="#"><s:property value="#levelWiseCompl.initiondate"/> to <s:property value="#levelWiseCompl.comlpetiondate"/></a></font></td>
		<td align="center" width="10%" ><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;" href="#"><s:property value="#levelWiseCompl.statuss"/>, <s:property value="#levelWiseCompl.compercentage"/></a></font></td>
	    <td align="center" width="10%" ><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="#levelWiseCompl.today"/></a></font></td>
		<td align="center" width="10%" ><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;" href="#"><s:property value="#levelWiseCompl.functReviewBy"/>, <s:property value="#levelWiseCompl.functonalDate"/>, <s:property value="#levelWiseCompl.reviewStatus"/></a></font></td>
		<td align="center" width="10%" ><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="#levelWiseCompl.techReviewBy"/>, <s:property value="#levelWiseCompl.technicalDate"/>, <s:property value="#levelWiseCompl.reviewStatus"/></a></font></td>
		<td align="center" width="10%" ><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;" href="#"><s:property value="#levelWiseCompl.tommorow"/></a></font></td>
 </tr>
</s:if>
<s:else>
<tr bgcolor="#D8D8D8">
	    <td align="center" width="10%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;" href="#"><s:property value="#levelWiseCompl.allotedtoo" /></a></font></td>
		<td align="center" width="10%" ><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="#levelWiseCompl.tasknameee"/></a></font></td>
		<td align="center" width="10%" ><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;" href="#"><s:property value="#levelWiseCompl.specificTask"/></a></font></td>
		<td align="center" width="10%" ><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="#levelWiseCompl.allotedbyy"/></a></font></td>
		<td align="center" width="10%" ><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;" href="#"><s:property value="#levelWiseCompl.initiondate"/> to <s:property value="#levelWiseCompl.comlpetiondate"/></a></font></td>
		<td align="center" width="10%" ><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;" href="#"><s:property value="#levelWiseCompl.statuss"/>, <s:property value="#levelWiseCompl.compercentage"/></a></font></td>
	    <td align="center" width="10%" ><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="#levelWiseCompl.today"/></a></font></td>
		<td align="center" width="10%" ><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;" href="#"><s:property value="#levelWiseCompl.functReviewBy"/>, <s:property value="#levelWiseCompl.functonalDate"/>, <s:property value="#levelWiseCompl.reviewStatus"/></a></font></td>
		<td align="center" width="10%" ><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="#levelWiseCompl.techReviewBy"/>, <s:property value="#levelWiseCompl.technicalDate"/>, <s:property value="#levelWiseCompl.reviewStatus"/></a></font></td>
		<td align="center" width="10%" ><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;" href="#"><s:property value="#levelWiseCompl.tommorow"/></a></font></td>
 </tr>

</s:else>
</s:iterator>
</tbody>
</table>
</s:if>
<s:else>
<s:if test="%{noDataMsg==null}">
<center><b>There is no Already Free data.</b></center>
</s:if>
</s:else>
<s:if test="todayFree.size()>0">
<center><b>Today Free Resources</b></center>
<table border="1" width="100%" align="center">
<tbody>

 <tr bgcolor="#D8D8D8">
	<td  align="center" width="10%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b>Name</b></font></td>
	<td  align="center" width="10%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b>Task Name</b></font></td>
	<td  align="center" width="10%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b>Task Brief</b></font></td>
	<td  align="center" width="10%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b>Alloted By</b></font></td>
	<td  align="center" width="10%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b>Dates</b></font></td>
	<td  align="center" width="10%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b>Status</b></font></td>
	<td  align="center" width="10%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b>Today Activity</b></font></td>
	<td  align="center" width="10%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b>Functional Review</b></font></td>
	<td  align="center" width="10%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b>Technical Review</b></font></td>
	<td  align="center" width="10%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b>Tomorrow Task</b></font></td>
 </tr>

<s:iterator id="levelWiseCompl"  status="status" value="%{todayFree}" >
<s:if test="#status.odd">
<tr>
	    <td align="center" width="10%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;" href="#"><s:property value="#levelWiseCompl.allotedtoo" /></a></font></td>
		<td align="center" width="10%" ><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="#levelWiseCompl.tasknameee"/></a></font></td>
		<td align="center" width="10%" ><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;" href="#"><s:property value="#levelWiseCompl.specificTask"/></a></font></td>
		<td align="center" width="10%" ><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="#levelWiseCompl.allotedbyy"/></a></font></td>
		<td align="center" width="10%" ><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;" href="#"><s:property value="#levelWiseCompl.initiondate"/> to <s:property value="#levelWiseCompl.comlpetiondate"/></a></font></td>
		<td align="center" width="10%" ><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;" href="#"><s:property value="#levelWiseCompl.statuss"/>, <s:property value="#levelWiseCompl.compercentage"/></a></font></td>
	    <td align="center" width="10%" ><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="#levelWiseCompl.today"/></a></font></td>
		<td align="center" width="10%" ><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;" href="#"><s:property value="#levelWiseCompl.functReviewBy"/>, <s:property value="#levelWiseCompl.functonalDate"/>, <s:property value="#levelWiseCompl.reviewStatus"/></a></font></td>
		<td align="center" width="10%" ><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="#levelWiseCompl.techReviewBy"/>, <s:property value="#levelWiseCompl.technicalDate"/>, <s:property value="#levelWiseCompl.reviewStatus"/></a></font></td>
		<td align="center" width="10%" ><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;" href="#"><s:property value="#levelWiseCompl.tommorow"/></a></font></td>
 </tr>
</s:if>
<s:else>
<tr bgcolor="#D8D8D8">
	    <td align="center" width="10%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;" href="#"><s:property value="#levelWiseCompl.allotedtoo" /></a></font></td>
		<td align="center" width="10%" ><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="#levelWiseCompl.tasknameee"/></a></font></td>
		<td align="center" width="10%" ><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;" href="#"><s:property value="#levelWiseCompl.specificTask"/></a></font></td>
		<td align="center" width="10%" ><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="#levelWiseCompl.allotedbyy"/></a></font></td>
		<td align="center" width="10%" ><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;" href="#"><s:property value="#levelWiseCompl.initiondate"/> to <s:property value="#levelWiseCompl.comlpetiondate"/></a></font></td>
		<td align="center" width="10%" ><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;" href="#"><s:property value="#levelWiseCompl.statuss"/>, <s:property value="#levelWiseCompl.compercentage"/></a></font></td>
	    <td align="center" width="10%" ><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="#levelWiseCompl.today"/></a></font></td>
		<td align="center" width="10%" ><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;" href="#"><s:property value="#levelWiseCompl.functReviewBy"/>, <s:property value="#levelWiseCompl.functonalDate"/>, <s:property value="#levelWiseCompl.reviewStatus"/></a></font></td>
		<td align="center" width="10%" ><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="#levelWiseCompl.techReviewBy"/>, <s:property value="#levelWiseCompl.technicalDate"/>, <s:property value="#levelWiseCompl.reviewStatus"/></a></font></td>
		<td align="center" width="10%" ><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;" href="#"><s:property value="#levelWiseCompl.tommorow"/></a></font></td>
 </tr>

</s:else>
</s:iterator>
</tbody>
</table>
</s:if>
<s:else>
<s:if test="%{noDataMsg==null}">
<br>
<center><b>There is no Today Free data.</b></center>
</s:if>
</s:else>
<s:if test="tommorowFree.size()>0">
<center><b>Tomorrow Free Resources</b></center>
<table border="1" width="100%" align="center">
<tbody>

 <tr bgcolor="#D8D8D8">
	<td  align="center" width="10%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b>Name</b></font></td>
	<td  align="center" width="10%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b>Task Name</b></font></td>
	<td  align="center" width="10%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b>Task Brief</b></font></td>
	<td  align="center" width="10%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b>Alloted By</b></font></td>
	<td  align="center" width="10%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b>Dates</b></font></td>
	<td  align="center" width="10%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b>Status</b></font></td>
	<td  align="center" width="10%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b>Today Activity</b></font></td>
	<td  align="center" width="10%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b>Functional Review</b></font></td>
	<td  align="center" width="10%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b>Technical Review</b></font></td>
	<td  align="center" width="10%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b>Tomorrow Task</b></font></td>
 </tr>

<s:iterator id="levelWiseCompl"  status="status" value="%{tommorowFree}" >
 <s:if test="#status.odd">
<tr>
	    <td align="center" width="10%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;" href="#"><s:property value="#levelWiseCompl.allotedtoo" /></a></font></td>
		<td align="center" width="10%" ><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="#levelWiseCompl.tasknameee"/></a></font></td>
		<td align="center" width="10%" ><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;" href="#"><s:property value="#levelWiseCompl.specificTask"/></a></font></td>
		<td align="center" width="10%" ><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="#levelWiseCompl.allotedbyy"/></a></font></td>
		<td align="center" width="10%" ><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;" href="#"><s:property value="#levelWiseCompl.initiondate"/> to <s:property value="#levelWiseCompl.comlpetiondate"/></a></font></td>
		<td align="center" width="10%" ><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;" href="#"><s:property value="#levelWiseCompl.statuss"/>, <s:property value="#levelWiseCompl.compercentage"/></a></font></td>
	    <td align="center" width="10%" ><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="#levelWiseCompl.today"/></a></font></td>
		<td align="center" width="10%" ><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;" href="#"><s:property value="#levelWiseCompl.functReviewBy"/>, <s:property value="#levelWiseCompl.functonalDate"/>, <s:property value="#levelWiseCompl.reviewStatus"/></a></font></td>
		<td align="center" width="10%" ><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="#levelWiseCompl.techReviewBy"/>, <s:property value="#levelWiseCompl.technicalDate"/>, <s:property value="#levelWiseCompl.reviewStatus"/></a></font></td>
		<td align="center" width="10%" ><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;" href="#"><s:property value="#levelWiseCompl.tommorow"/></a></font></td>
 </tr>
</s:if>
<s:else>
<tr bgcolor="#D8D8D8">
	    <td align="center" width="10%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;" href="#"><s:property value="#levelWiseCompl.allotedtoo" /></a></font></td>
		<td align="center" width="10%" ><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="#levelWiseCompl.tasknameee"/></a></font></td>
		<td align="center" width="10%" ><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;" href="#"><s:property value="#levelWiseCompl.specificTask"/></a></font></td>
		<td align="center" width="10%" ><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="#levelWiseCompl.allotedbyy"/></a></font></td>
		<td align="center" width="10%" ><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;" href="#"><s:property value="#levelWiseCompl.initiondate"/> to <s:property value="#levelWiseCompl.comlpetiondate"/></a></font></td>
		<td align="center" width="10%" ><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;" href="#"><s:property value="#levelWiseCompl.statuss"/>, <s:property value="#levelWiseCompl.compercentage"/></a></font></td>
	    <td align="center" width="10%" ><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="#levelWiseCompl.today"/></a></font></td>
		<td align="center" width="10%" ><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;" href="#"><s:property value="#levelWiseCompl.functReviewBy"/>, <s:property value="#levelWiseCompl.functonalDate"/>, <s:property value="#levelWiseCompl.reviewStatus"/></a></font></td>
		<td align="center" width="10%" ><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="#levelWiseCompl.techReviewBy"/>, <s:property value="#levelWiseCompl.technicalDate"/>, <s:property value="#levelWiseCompl.reviewStatus"/></a></font></td>
		<td align="center" width="10%" ><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;" href="#"><s:property value="#levelWiseCompl.tommorow"/></a></font></td>
 </tr>

</s:else>
</s:iterator>
</tbody>
</table>
</s:if>
<s:else>
<s:if test="%{noDataMsg==null}">
<br>
<center><b>There is no Tomorrow Free data.</b></center>
</s:if>

</s:else>
<center><b><s:property value="%{noDataMsg}"/></b></center>
</body>
</html>