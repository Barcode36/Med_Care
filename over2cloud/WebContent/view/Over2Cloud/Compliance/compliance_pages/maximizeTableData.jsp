<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<center>
	<s:if test="maximizeDivBlock=='1stTableDta'">
	<table border="1" width="100%" align="center">
		<tbody>
		
		 <tr bgcolor="#7D89E3">
			<td  align="center" width="20%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Department</b></font></td>
			<td  align="center" width="20%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Pending</b></font></td>
			<td  align="center" width="20%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Missed</b></font></td>
			<td  align="center" width="20%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Done</b></font></td>
		    <td  align="center" width="25%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Snooze</b></font></td>
		 </tr>
		
		<s:iterator id="totalCompl"  status="status" value="%{totalComplDashboard.complList}" >
		 <tr bgcolor="#BABFE0">
			<td align="center" width="20%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;" href="#"><s:property value="#totalCompl.departName" /></a></font></td>
			
			<td align="center" width="20%" onclick="showAllComplDetails('<s:property value="#totalCompl.departId"/>','Pending','<s:property value="#totalCompl.compId"/>');"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="#totalCompl.pending"/></a></font></td>
			
			<td align="center" width="20%" onclick="showAllComplDetails('<s:property value="#totalCompl.departId"/>','Missed','<s:property value="#totalCompl.compId"/>');"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;" href="#"><s:property value="#totalCompl.missed"/></a></font></td>
			
			<td align="center" width="20%" onclick="showAllComplDetails('<s:property value="#totalCompl.departId"/>','Done','<s:property value="#totalCompl.compId"/>');"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="#totalCompl.done"/></a></font></td>
			
			<s:if test="#totalCompl.snooze==0">
			<td align="center" width="25%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="#totalCompl.snooze"/></a></font></td>
			</s:if>
			<s:else>
			<td align="center" width="25%" onclick="showAllComplDetails('<s:property value="#totalCompl.departId"/>','Snooze','<s:property value="#totalCompl.compId"/>');"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="#totalCompl.snooze"/></a></font></td>
			</s:else>
			
		 </tr>
		</s:iterator>
		<tr bgcolor="#BABFE0">
			<td align="center" width="20%" height="40%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2">Total </font></td>
			<td align="center" width="20%" height="40%" onclick="showAllComplDetails('All','Pending','<s:property value="allCompId1st"/>');"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><s:property value="totalPending"/></font></td>
			
			<td align="center" width="20%" height="40%" onclick="showAllComplDetails('All','Missed','<s:property value="allCompId1st"/>');"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><s:property value="totalMissed"/></font></td>
			
			<td align="center" width="20%" height="40%" onclick="showAllComplDetails('All','Done','<s:property value="allCompId1st"/>');"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><s:property value="totalDone"/></font></td>
			<s:if test="totalSnooze==0">
				<td align="center" width="25%" height="40%" ><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="totalSnooze"/></a></font></td>
			</s:if>
			<s:else>
				<td align="center" width="25%" height="40%" onclick="showAllComplDetails('All','Snooze','<s:property value="allCompId1st"/>');"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="totalSnooze"/></a></font></td>
			</s:else>
		 </tr>
		</tbody>
	</table>
	</s:if>
	
	<s:elseif test="maximizeDivBlock=='2ndDataBlockDiv'">
	<table border="1" width="100%" align="center">
	<tbody>
	
	 <tr bgcolor="#27C490">
		<td align="center" rowspan="2" width="20%"><font face="Arial, Helvetica, sans-serif" color="#ffffff" size="2"><b>Department</b></font></td>
		<td colspan="2" align="center" width="20%"><font face="Arial, Helvetica, sans-serif" color="#ffffff" size="2"><b>Yearly</b></font></td>
		<td colspan="2" align="center" width="20%"><font face="Arial, Helvetica, sans-serif" color="#ffffff" size="2"><b>Monthly</b></font></td>
		<td colspan="2" align="center" width="20%"><font face="Arial, Helvetica, sans-serif" color="#ffffff" size="2"><b>Weekly</b></font></td>
	 </tr>
	
	 <tr bgcolor="#27C490">
		<td align="center" width="12%"><font face="Arial, Helvetica, sans-serif" color="#ffffff" size="1"><b>Total</b></font></td>
		<td align="center" width="12%"><font face="Arial, Helvetica, sans-serif" color="#ffffff" size="1"><b>Missed</b></font></td>
		
		<td align="center" width="12%"><font face="Arial, Helvetica, sans-serif" color="#ffffff" size="1"><b>Total</b></font></td>
		<td align="center" width="12%"><font face="Arial, Helvetica, sans-serif" color="#ffffff" size="1"><b>Missed</b></font></td>
		
		<td align="center" width="12%"><font face="Arial, Helvetica, sans-serif" color="#ffffff" size="1"><b>Total</b></font></td>
		<td align="center" width="12%"><font face="Arial, Helvetica, sans-serif" color="#ffffff" size="1"><b>Missed</b></font></td>
	 </tr>
	
	<s:iterator id="pendingCompl"  status="status" value="%{complBeanForDashboard.complList}" >
	 <tr bgcolor="#D3EBE3">
		<td align="center" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;" href="#"><s:property value="#pendingCompl.departName" /></a></font></td>
		<td align="center" width="12%" onclick="showComplDetails('<s:property value="#pendingCompl.departId"/>','Y','Total','<s:property value="#pendingCompl.compId"/>');"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="#pendingCompl.annualTotal"/></a></font></td>
		<td align="center" width="12%" onclick="showComplDetails('<s:property value="#pendingCompl.departId" />','Y','Missed','<s:property value="#pendingCompl.compId"/>');"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="#pendingCompl.annualMissed"/></a></font></td>
		
		<td align="center" width="12%" onclick="showComplDetails('<s:property value="#pendingCompl.departId"/>','M','Total','<s:property value="#pendingCompl.compId"/>');"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="#pendingCompl.monthlyTotal"/></a></font></td>
		<td align="center" width="12%" onclick="showComplDetails('<s:property value="#pendingCompl.departId"/>','M','Missed','<s:property value="#pendingCompl.compId"/>');"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="#pendingCompl.monthlyMissed"/></a></font></td>
		
		<td align="center" width="12%" onclick="showComplDetails('<s:property value="#pendingCompl.departId"/>','W','Total','<s:property value="#pendingCompl.compId"/>');"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="#pendingCompl.weeklyTotal"/></a></font></td>
		<td align="center" width="12%" onclick="showComplDetails('<s:property value="#pendingCompl.departId"/>','W','Missed','<s:property value="#pendingCompl.compId"/>');"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="#pendingCompl.weeklyMissed"/></a></font></td>
	 </tr>
	</s:iterator>
	<tr bgcolor="#D3EBE3">
		<td align="center" width="20%" height="40%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2">Total</font></td>
		<td align="center" width="12%" height="40%" onclick="showComplDetails('All','Y','Total','<s:property value="allCompId"/>');"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="allYearlyTotal"/></a></font></td>
		<td align="center" width="12%" height="40%" onclick="showComplDetails('All','Y','Missed','<s:property value="allCompId"/>');"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="allYearlyMissedTotal"/></a></font></td>
		
		<td align="center" width="12%" height="40%" onclick="showComplDetails('All','M','Total','<s:property value="allCompId"/>');"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="allMonthlyTotal"/></a></font></td>
		<td align="center" width="12%" height="40%" onclick="showComplDetails('All','M','Missed','<s:property value="allCompId"/>');"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="allMonthlyMissedTotal"/></a></font></td>
		
		<td align="center" width="12%" height="40%" onclick="showComplDetails('All','W','Total','<s:property value="allCompId"/>');"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="allWeeklyTotal"/></a></font></td>
		<td align="center" width="12%" height="40%" onclick="showComplDetails('All','W','Missed','<s:property value="allCompId"/>');"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="allWeeklyMissedTotal"/></a></font></td>
	 </tr>
	</tbody>
	</table>
	</s:elseif>
	
	<s:elseif test="maximizeDivBlock=='3rdDataBlockDiv'">
	<table border="1" width="100%" align="center">
	<tbody>
	
	 <tr bgcolor="#EB8B05">
		<td  align="center" width="25%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#ffffff" size="2"><b>Department</b></font></td>
		<td  align="center" width="25%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#ffffff" size="2"><b>This&nbsp;Week</b></font></td>
		<td  align="center" width="25%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#ffffff" size="2"><b>This&nbsp;Month</b></font></td>
		<td  align="center" width="25%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#ffffff" size="2"><b>This&nbsp;Year</b></font></td>
	 </tr>
	
	<s:iterator id="pendingAgeingCompl"  status="status" value="%{complageingDashboard.complList}" >
	 <tr bgcolor="#F0E0C9">
		<td align="center" width="25%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;" href="#"><s:property value="#pendingAgeingCompl.departName" /></a></font></td>
		<td align="center" width="25%" onclick="showAgeingComplDetails('<s:property value="#pendingAgeingCompl.departId"/>','W','Total','<s:property value="#pendingAgeingCompl.compId"/>');"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="#pendingAgeingCompl.weeklyTotal"/></a></font></td>
		<td align="center" width="25%" onclick="showAgeingComplDetails('<s:property value="#pendingAgeingCompl.departId"/>','M','Total','<s:property value="#pendingAgeingCompl.compId"/>');"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;" href="#"><s:property value="#pendingAgeingCompl.monthlyTotal"/></a></font></td>
		<td align="center" width="25%" onclick="showAgeingComplDetails('<s:property value="#pendingAgeingCompl.departId"/>','Y','Total','<s:property value="#pendingAgeingCompl.compId"/>');"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="#pendingAgeingCompl.annualTotal"/></a></font></td>
	 </tr>
	</s:iterator>
	
	<tr bgcolor="#F0E0C9">
		<td align="center" width="25%" height="40%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2">Total</font></td>
		<td align="center" width="25%" height="40%" onclick="showAgeingComplDetails('All','W','Total','<s:property value="allAgeingCompId"/>');"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="allAgeingWeeklyTotal"/></a></font></td>
		<td align="center" width="25%" height="40%" onclick="showAgeingComplDetails('All','M','Total','<s:property value="allAgeingCompId"/>');"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="allAgeingMonthlyTotal"/></a></font></td>
		<td align="center" width="25%" height="40%" onclick="showAgeingComplDetails('All','Y','Total','<s:property value="allAgeingCompId"/>');"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="allAgeingYearlyTotal"/></a></font></td>
	 </tr>
	</tbody>
	</table>
	</s:elseif>
	
	<s:elseif test="maximizeDivBlock=='4thDataBlockDiv'">
	<table border="1" width="100%" align="center">
	<tbody>
	
	 <tr bgcolor="#F2506B">
		<td  align="center" width="25%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#ffffff" size="2"><b>Department</b></font></td>
		<td  align="center" width="25%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#ffffff" size="2"><b>Done</b></font></td>
		<td  align="center" width="25%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#ffffff" size="2"><b>Pending</b></font></td>
	 </tr>
	
	<s:iterator id="lastMonthCompl"  status="status" value="%{previousMonthComplDashboard.complList}" >
	 <tr bgcolor="#EDDFE1">
		<td align="center" width="25%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;" href="#"><s:property value="#lastMonthCompl.departName" /></a></font></td>
		<td align="center" width="25%" onclick="lastMonthCompDetail('<s:property value="#lastMonthCompl.departId"/>','Done','Last Month Compliance','<s:property value="#lastMonthCompl.compId"/>');"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="#lastMonthCompl.done"/></a></font></td>
		<td align="center" width="25%" onclick="lastMonthCompDetail('<s:property value="#lastMonthCompl.departId"/>','Pending','Last Month Compliance','<s:property value="#lastMonthCompl.compId"/>');"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;" href="#"><s:property value="#lastMonthCompl.pending"/></a></font></td>
	 </tr>
	</s:iterator>
	
	<tr bgcolor="#EDDFE1">
		<td align="center" width="25%" height="40%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2">Total</font></td>
		<td align="center" width="25%" height="40%" onclick="lastMonthCompDetail('All','Done','Last Month Compliance','<s:property value="allCompId4th"/>');"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="lastMonthDoneTotal"/></a></font></td>
		<td align="center" width="25%" height="40%" onclick="lastMonthCompDetail('All','Pending','Last Month Compliance','<s:property value="allCompId4th"/>');"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="lastMonthPendingTotal"/></a></font></td>
	 </tr>
	</tbody>
	</table>
	</s:elseif>
	
	<s:elseif test="maximizeDivBlock=='6thDataBlockDiv'">
	<table border="1" width="100%" align="center">
	<tbody>
	
	 <tr bgcolor="#B27ACF">
	<td  align="center" width="25%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#ffffff" size="2"><b>Department</b></font></td>
	<td  align="center" width="15%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#ffffff" size="2"><b>Level&nbsp;1</b></font></td>
	<td  align="center" width="15%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#ffffff" size="2"><b>Level&nbsp;2</b></font></td>
	<td  align="center" width="15%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#ffffff" size="2"><b>Level&nbsp;3</b></font></td>
	<td  align="center" width="15%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#ffffff" size="2"><b>Level&nbsp;4</b></font></td>
	<td  align="center" width="15%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#ffffff" size="2"><b>Level&nbsp;5</b></font></td>
 </tr>

<s:iterator id="levelWiseCompl"  status="status" value="%{escLevelCompDetailObj.complList}" >
 <tr bgcolor="#E2DAE6">
	<td align="center" width="25%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;" href="#"><s:property value="#levelWiseCompl.departName" /></a></font></td>
	<td align="center" width="15%" onclick="lastMonthCompDetail('<s:property value="#levelWiseCompl.departId"/>','Level1','Level wise Compliance');"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="#levelWiseCompl.esc1Count"/></a></font></td>
	<td align="center" width="15%" onclick="lastMonthCompDetail('<s:property value="#levelWiseCompl.departId"/>','Level2','Level wise Compliance');"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;" href="#"><s:property value="#levelWiseCompl.esc2Count"/></a></font></td>
	<td align="center" width="15%" onclick="lastMonthCompDetail('<s:property value="#levelWiseCompl.departId"/>','Level3','Level wise Compliance');"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="#levelWiseCompl.esc3Count"/></a></font></td>
	<td align="center" width="15%" onclick="lastMonthCompDetail('<s:property value="#levelWiseCompl.departId"/>','Level4','Level wise Compliance');"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;" href="#"><s:property value="#levelWiseCompl.esc4Count"/></a></font></td>
	<td align="center" width="15%" onclick="lastMonthCompDetail('<s:property value="#levelWiseCompl.departId"/>','Level5','Level wise Compliance');"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;" href="#"><s:property value="#levelWiseCompl.esc5Count"/></a></font></td>
 </tr>
</s:iterator>

<tr bgcolor="#E2DAE6">
	<td align="center" width="25%" height="40%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2">Total</font></td>
	<td align="center" width="15%" height="40%" onclick="lastMonthCompDetail('All','Level1','Level wise Compliance');"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="esc1Total"/></a></font></td>
	<td align="center" width="15%" height="40%" onclick="lastMonthCompDetail('All','Level2','Level wise Compliance');"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="esc2Total"/></a></font></td>
	<td align="center" width="15%" height="40%" onclick="lastMonthCompDetail('All','Level3','Level wise Compliance');"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="esc3Total"/></a></font></td>
	<td align="center" width="15%" height="40%" onclick="lastMonthCompDetail('All','Level4','Level wise Compliance');"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="esc4Total"/></a></font></td>
	<td align="center" width="15%" height="40%" onclick="lastMonthCompDetail('All','Level5','Level wise Compliance');"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="esc5Total"/></a></font></td>
 </tr>
	</tbody>
	</table>
	</s:elseif>
	<s:elseif test="maximizeDivBlock=='5thMaxData'">
    <table align="center" border="0" height="350px;" width="100%" style="border-style:dotted solid;">
               <tr height="20px">
                   <td valign="bottom">
                       <table height="10px" align="center" border="1" style="border-style:dotted solid;" width="100%">
                        	  <tr bgcolor="#06C0C9" height="17px">
                        			<td align="center" width="40%" bgcolor="#138F6A" style=" color:#ffffff; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;"><b>Task&nbsp;Name</b></td>
									<td align="center" width="25%" bgcolor="#138F6A" style=" color:#ffffff; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;"><b>Due&nbsp;Date</b></td>
									<td align="center" width="20%" bgcolor="#138F6A" style=" color:#ffffff; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;"><b>Status</b></td>
									<td align="center" width="20%" bgcolor="#138F6A" style=" color:#ffffff; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;"><b>Esc&nbsp;Level</b></td>
							  </tr>
                       </table>
                    </td>
               </tr>
               
               <tr>
                   <td valign="top" style="padding-top: 10px" align="left">
                   <marquee onmouseover="this.stop()" onmouseout="this.start()" scrollamount="1" scrolldelay="2" direction="up">
					   <table align="left" border="0"  width="100%" style="border-style:dotted solid;">                        		
                              <s:iterator id="userPendingCompl"  status="status" value="%{userWiseUpcomeingComplList}" >
								<tr bordercolor="#ffffff">
                        			<td align="left" width="40%" bgcolor="#C3E8D1" style=" color:#125EA9; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;"><b><a href="#" onclick="complTakeAction('<s:property value="#userPendingCompl.compId"/>')"><s:property value="#userPendingCompl.taskName"/></a></b></td>
                        			<td align="center" width="25%" bgcolor="#C3E8D1" style=" color:#125EA9; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;"><b><a href="#" onclick="complTakeAction('<s:property value="#userPendingCompl.compId"/>')"><s:property value="#userPendingCompl.dueDate"/></a></b></td>
                        			<td align="center" width="20%" bgcolor="#C3E8D1" style=" color:#125EA9; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;"><b><a href="#" onclick="complTakeAction('<s:property value="#userPendingCompl.compId"/>')"><s:property value="#userPendingCompl.actionStatus"/></a></b></td>
                        			<td align="center" width="20%" bgcolor="#C3E8D1" style=" color:#125EA9; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;"><b><a href="#" onclick="complTakeAction('<s:property value="#userPendingCompl.compId"/>')"><s:property value="#userPendingCompl.currentEscLevel"/></a></b></td>
                        		</tr>
                        	   </s:iterator>
                        </table>
                   </marquee>
                   </td>
               </tr>
    </table>
	</s:elseif>
	<s:elseif test="maximizeDivBlock=='7thDataBlockDiv'">
  <table border="1" width="100%" align="center">
<tbody>

  <tr bgcolor="#04B4AE">
	<td align="center" rowspan="3" width="20%"><font face="Arial, Helvetica, sans-serif" color="#ffffff" size="2"><b>Department</b></font></td>
	<td colspan="6" align="center" width="10%"><font face="Arial, Helvetica, sans-serif" color="#ffffff" size="2"><b>Reminder</b></font></td>
	<td rowspan="2" colspan="2" align="center" width="20%"><font face="Arial, Helvetica, sans-serif" color="#ffffff" size="2"><b>Due Date</b></font></td>
 </tr>
 <tr bgcolor="#04B4AE">
	<td align="center" colspan="2" width="14%"><font face="Arial, Helvetica, sans-serif" color="#ffffff" size="1"><b>1st</b></font></td>
	<td align="center" colspan="2" width="14%"><font face="Arial, Helvetica, sans-serif" color="#ffffff" size="1"><b>2nd</b></font></td>
	<td align="center" colspan="2" width="14%"><font face="Arial, Helvetica, sans-serif" color="#ffffff" size="1"><b>3rd</b></font></td>
 </tr>
 <tr bgcolor="#04B4AE">
	<td align="center"  width="10%"><font face="Arial, Helvetica, sans-serif" color="#ffffff" size="1"><b>Self</b></font></td>
	<td align="center"  width="10%"><font face="Arial, Helvetica, sans-serif" color="#ffffff" size="1"><b>Team</b></font></td>
	
	<td align="center"  width="10%"><font face="Arial, Helvetica, sans-serif" color="#ffffff" size="1"><b>Self</b></font></td>
	<td align="center"  width="10%"><font face="Arial, Helvetica, sans-serif" color="#ffffff" size="1"><b>Team</b></font></td>
	
	<td align="center"  width="10%"><font face="Arial, Helvetica, sans-serif" color="#ffffff" size="1"><b>Self</b></font></td>
	<td align="center"  width="10%"><font face="Arial, Helvetica, sans-serif" color="#ffffff" size="1"><b>Team</b></font></td>
 
    <td align="center"  width="10%"><font face="Arial, Helvetica, sans-serif" color="#ffffff" size="1"><b>Self</b></font></td>
	<td align="center"  width="10%"><font face="Arial, Helvetica, sans-serif" color="#ffffff" size="1"><b>Team</b></font></td>
 
 </tr>

<s:iterator id="totalDueToday"  status="status" value="%{totalDueTodayDashboard.complList}" >
 <tr bgcolor="#E0F8F7">
	<td align="center" width="20%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="1"><a style="cursor: pointer;text-decoration: none;" href="#"><s:property value="#totalDueToday.departName" /></a></font></td>
	
	<s:if test="#totalDueToday.selfRem1==0">
		<td align="center" width="10%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;" href="#"><s:property value="#totalDueToday.selfRem1"/></a></font></td>
	</s:if>
	<s:else>
		<td align="center" width="10%" onclick="showAllTodayCompl('<s:property value="#totalDueToday.departId"/>','selfrem1','<s:property value="#totalDueToday.selfRem1Compliance"/>');"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;" href="#"><s:property value="#totalDueToday.selfRem1"/></a></font></td>
	</s:else>
	
	<s:if test="#totalDueToday.teamRem1==0">
		<td align="center" width="10%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="#totalDueToday.teamRem1"/></a></font></td>
	</s:if>
	<s:else>
		<td align="center" width="10%" onclick="showAllTodayCompl('<s:property value="#totalDueToday.departId"/>','teamrem1','<s:property value="#totalDueToday.teamRem1Compliance"/>');"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="#totalDueToday.teamRem1"/></a></font></td>
	</s:else>
	<s:if test="#totalDueToday.selfRem2==0">
		<td align="center" width="10%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="#totalDueToday.selfRem2"/></a></font></td>
	</s:if>
	<s:else>
		<td align="center" width="10%" onclick="showAllTodayCompl('<s:property value="#totalDueToday.departId"/>','selfrem2','<s:property value="#totalDueToday.selfRem2Compliance"/>');"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="#totalDueToday.selfRem2"/></a></font></td>
	</s:else>
	<s:if test="#totalDueToday.teamRem2==0">
		<td align="center" width="10%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="#totalDueToday.teamRem2"/></a></font></td>
	</s:if>
	<s:else>
		<td align="center" width="10%" onclick="showAllTodayCompl('<s:property value="#totalDueToday.departId"/>','teamrem2','<s:property value="#totalDueToday.teamRem2Compliance"/>');"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="#totalDueToday.teamRem2"/></a></font></td>
	</s:else>
	<s:if test="#totalDueToday.selfRem3==0">
		<td align="center" width="10%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="#totalDueToday.selfRem3"/></a></font></td>
	</s:if>
	<s:else>
		<td align="center" width="10%" onclick="showAllTodayCompl('<s:property value="#totalDueToday.departId"/>','selfrem3','<s:property value="#totalDueToday.selfRem3Compliance"/>');"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="#totalDueToday.selfRem3"/></a></font></td>
	</s:else>
	<s:if test="#totalDueToday.teamRem3==0">
		<td align="center" width="10%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="#totalDueToday.teamRem3"/></a></font></td>
	</s:if>
	<s:else>
		<td align="center" width="10%" onclick="showAllTodayCompl('<s:property value="#totalDueToday.departId"/>','teamrem3','<s:property value="#totalDueToday.teamRem3Compliance"/>');"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="#totalDueToday.teamRem3"/></a></font></td>
	</s:else>
	<s:if test="#totalDueToday.selfDueDate==0">
		<td align="center" width="20%" ><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="#totalDueToday.selfDueDate"/></a></font></td>
	</s:if>
	<s:else>
		<td align="center" width="20%" onclick="showAllTodayCompl('<s:property value="#totalDueToday.departId"/>','selfdueDate','<s:property value="#totalDueToday.selfDueCompliance"/>');"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="#totalDueToday.selfDueDate"/></a></font></td>
	</s:else>
	<s:if test="#totalDueToday.teamDueDate==0">
		<td align="center" width="20%" ><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="#totalDueToday.teamDueDate"/></a></font></td>
	</s:if>
	<s:else>
		<td align="center" width="20%" onclick="showAllTodayCompl('<s:property value="#totalDueToday.departId"/>','teamdueDate','<s:property value="#totalDueToday.teamDueCompliance"/>');"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="#totalDueToday.teamDueDate"/></a></font></td>
	</s:else>
 </tr>
</s:iterator>
<tr bgcolor="#E0F8F7">
	<td align="center" width="8%" height="40%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2">Total </font></td>
	
	
	<s:if test="totalSelfRem1==0">
		<td align="center" width="10%" height="40%" ><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="totalSelfRem1"/></a></font></td>
	</s:if>
	<s:else>
		<td align="center" width="10%" height="40%" onclick="showAllTodayCompl('All','selfrem1','<s:property value="allselfRem1Compliance"/>');"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="totalSelfRem1"/></a></font></td>
	</s:else>
	<s:if test="totalTeamRem1==0">
		<td align="center" width="10%" height="40%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="totalTeamRem1"/></a></font></td>
	</s:if>
	<s:else>
		<td align="center" width="10%" height="40%" onclick="showAllTodayCompl('All','teamrem1','<s:property value="allteamRem1Compliance"/>');"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="totalTeamRem1"/></a></font></td>
	</s:else>
	<s:if test="totalSelfRem2==0">
		<td align="center" width="10%" height="40%" ><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="totalSelfRem2"/></a></font></td>
	</s:if>
	<s:else>
		<td align="center" width="10%" height="40%" onclick="showAllTodayCompl('All','selfrem2','<s:property value="allselfRem2Compliance"/>');"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="totalSelfRem2"/></a></font></td>
	</s:else>
	<s:if test="totalTeamRem2==0">
		<td align="center" width="10%" height="40%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="totalTeamRem2"/></a></font></td>
	</s:if>
	<s:else>
		<td align="center" width="10%" height="40%" onclick="showAllTodayCompl('All','teamrem2','<s:property value="allteamRem2Compliance"/>');"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="totalTeamRem2"/></a></font></td>
	</s:else>
	<s:if test="totalSelfRem3==0">
		<td align="center" width="10%" height="40%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="totalSelfRem3"/></a></font></td>
	</s:if>
	<s:else>
		<td align="center" width="10%" height="40%" onclick="showAllTodayCompl('All','selfrem3','<s:property value="allselfRem3Compliance"/>');"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="totalSelfRem3"/></a></font></td>
	</s:else>
	<s:if test="totalTeamRem3==0">
		<td align="center" width="10%" height="40%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="totalTeamRem3"/></a></font></td>
	</s:if>
	<s:else>
		<td align="center" width="10%" height="40%" onclick="showAllTodayCompl('All','teamrem3','<s:property value="allteamRem3Compliance"/>');"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="totalTeamRem3"/></a></font></td>
	</s:else>
	<s:if test="totalSelfDue==0">
		<td align="center" width="20%" height="30%" ><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="totalSelfDue"/></a></font></td>
	</s:if>
	<s:else>
		<td align="center" width="20%" height="30%" onclick="showAllTodayCompl('All','selfdue','<s:property value="allselfDueCompliance"/>');"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="totalSelfDue"/></a></font></td>
	</s:else>
	<s:if test="totalTeamDue==0">
		<td align="center" width="20%" height="30%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="totalTeamDue"/></a></font></td>
	</s:if>
	<s:else>
		<td align="center" width="20%" height="30%" onclick="showAllTodayCompl('All','teamdue','<s:property value="allteamDueCompliance"/>');"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="totalTeamDue"/></a></font></td>
	</s:else>
 </tr>
</tbody>
</table>
	</s:elseif>
	
	
</center>
</body>
</html>
