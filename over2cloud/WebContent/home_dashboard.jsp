<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjc" uri="/struts-jquery-chart-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<s:url id="contextz" value="/template/theme" />
<s:url var="chartDataUrl" action="json-chart-data"/>
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript" src="<s:url value="/js/jquery.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/hdmDashboard.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/dashboard//dashboard.js"/>"></script>

<title>HDM Dashboard</title>
<script type="text/javascript">
function ShowTicketData(id,status_for)
 {
	if(status_for=='T')
	{
		$("#ticketDialog").dialog({title: 'Ticket Detail'});
	}
	else
	{
		$("#ticketDialog").dialog({title: 'Feedback By Detail'});
	}
   $.ajax( {
	 
	type :"post",
	url :"view/Over2Cloud/HelpDeskOver2Cloud/Dashboard_Pages/ticketInfo.action?ticket_id="+id+"&status_for="+status_for,
	success : function(ticketdata) {
	$("#ticketsInfo").html(ticketdata);
	},
	error : function() {
		alert("error");
	}
});
$("#ticketDialog").dialog('open');
 }
</script>
</head>
<body>
<sj:dialog 
    	id="ticketDialog" 
    	autoOpen="false" 
    	modal="true" 
    	showEffect="slide" 
    	hideEffect="explode" 
    	position="['center','top']"
    >
    <center><div id="ticketsInfo"></div></center>
    </sj:dialog>
    
    <center>
<sj:dialog 
            id="confirmEscalationDialog" 
            autoOpen="false"  
            closeOnEscape="true" 
            modal="true" 
            title="Action On Tickets" 
            width="900" 
            height="350" 
            showEffect="slide" 
            hideEffect="explode" >
            <div id="confirmingEscalation"></div>
</sj:dialog>
</center>
<div class="middle-content">

<div class="contentdiv-small" style="overflow: hidden;" id="hod_dash">
<div class="headding">Ticket Score Sheet For HOD</div>
<div class="clear"></div>
<div style="height: auto; margin-bottom: 10px;">
<table border="1" width="100%" align="center">
    <tr>
		<td align="center" width="40%" bgcolor="#4EC2D9" style=" color:#ffffff; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;"><b>&nbsp;Sub Dept</b></td>
		<td align="center" width="15%" bgcolor="#4EC2D9" style=" color:#ffffff; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;"><b>&nbsp;PN</b></td>
		<td align="center" width="15%" bgcolor="#4EC2D9" style=" color:#ffffff; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;"><b>&nbsp;HP</b></td>
		<td align="center" width="15%" bgcolor="#4EC2D9" style=" color:#ffffff; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;"><b>&nbsp;SN</b></td>
		<td align="center" width="15%" bgcolor="#4EC2D9" style=" color:#ffffff; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;"><b>&nbsp;RS</b></td>
	</tr>
</table>
<s:iterator id="rsCompl"  status="status" value="%{subdeptdashObj.dashList}" >
	<table border="1" width="100%" align="center">
 	<tr>
 	    <td align="left" width="40%"   bgcolor="#CAE3E8" style=" color:#125EA9; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;"><b><s:property value="#rsCompl.deptName"/></b></td>
		<td align="center" width="15%" bgcolor="#CAE3E8" style=" color:#125EA9; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;" onclick="getData('<s:property value="#rsCompl.id"/>','Pending','H');"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.pc"/></b></a></td>
		<td align="center" width="15%" bgcolor="#CAE3E8" style=" color:#125EA9; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;" onclick="getData('<s:property value="#rsCompl.id"/>','High Priority','H');"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.hpc"/></b></a></td>
		<td align="center" width="15%" bgcolor="#CAE3E8" style=" color:#125EA9; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;" onclick="getData('<s:property value="#rsCompl.id"/>','Snooze','H');"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.sc"/></b></a></td>
		<td align="center" width="15%" bgcolor="#CAE3E8" style=" color:#125EA9; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;" onclick="getData('<s:property value="#rsCompl.id"/>','Resolved','H');"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.rc"/></b></a></td>
 	</tr>
 	</table>
	</s:iterator>
</div>
</div>


<div class="contentdiv-small" style="overflow: hidden;">
<div class="headding">Ticket Status For <s:property value="%{hod_scrolling}"/></div>
<div class="clear"></div>
<div style="height: auto; margin-bottom: 10px;">
<table border="1" width="100%" align="center">
    <tr>
		<td align="center" width="16%" bgcolor="#ED58E1" style=" color:#ffffff; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;"><b>&nbsp;Ticket&nbsp;No</b></td>
		<td align="center" width="30%" bgcolor="#ED58E1" style=" color:#ffffff; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;"><b>&nbsp;Feedback&nbsp;By</b></td>
		<td align="center" width="19%" bgcolor="#ED58E1" style=" color:#ffffff; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;"><b>&nbsp;Date</b></td>
	    <td align="center" width="15%" bgcolor="#ED58E1" style=" color:#ffffff; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;"><b>&nbsp;Time</b></td>
	    <td align="center" width="20%" bgcolor="#ED58E1" style=" color:#ffffff; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;"><b>&nbsp;Status</b></td>
	</tr>
</table>
<marquee onmouseover="this.stop()" onmouseout="this.start()" scrollamount="1" scrolldelay="5" direction="up" height="310" width="100%" style="color: #000000; text-align:left; font-family:Verdana; font-size:8pt">
<s:iterator id="ticketData"  status="status" value="%{ticketsList}" >
	<table  width="100%" align="center">
 	<tr>
 	    <td align="center" width="16%"  style=" color:#125EA9; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;"><a style="cursor: pointer;text-decoration: none;" onclick="ShowTicketData('<s:property value="%{id}"/>','T')" href="#"><b><s:property value="%{ticket_no}"/></b></a></td>
		<td align="center" width="30%"  style=" color:#125EA9; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;" ><a style="cursor: pointer;text-decoration: none;" onclick="ShowTicketData('<s:property value="%{id}"/>','FB')" href="#"><b><s:property value="%{feed_by}"/></b></a></td>
		<td align="center" width="19%"  style=" color:#125EA9; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;" ><b><s:property value="%{open_date}"/></b></td>
 		<td align="center" width="15%"  style=" color:#125EA9; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;" ><b><s:property value="%{open_time}"/></b></td>
 		<td align="center" width="15%"  style=" color:#125EA9; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;" ><b><s:property value="%{status}"/></b></td>
 	</tr>
 	</table>
	</s:iterator>
</marquee>
</div>
</div>

<div class="contentdiv-small" style="overflow: hidden;" id="level">
<div class="headding">Employee wise Ticket Analysis For <s:property value="%{hod_scrolling}"/></div>
<div class="clear"></div>
<div style="height: auto; margin-bottom: 10px;">
<table border="1" width="100%" align="center">
    <tr>
		<td align="center" width="70%"   bgcolor="#828EE8" style=" color:#ffffff; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;"><b>&nbsp; Employee</b></td>
		<td align="center" width="30%" bgcolor="#828EE8" style=" color:#ffffff; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;"><b>&nbsp;Counter</b></td>
	</tr>
</table>
<s:iterator id="rsCompl1dfrgrdfg"  status="status" value="%{empCountList}" >
	<table border="1" width="100%" align="center">
 	<tr>
 	    <td align="left" width="70%"   bgcolor="#CACDE8" style=" color:#125EA9; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;"><b><s:property value="%{feedback_allot_to}"/></b></td>
		<td align="center" width="30%" bgcolor="#CACDE8" style=" color:#125EA9; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;" ><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="%{counter}"/></b></a></td>
 	</tr>
 	</table>
	</s:iterator>
</div>
</div>

<div class="contentdiv-small" style="overflow: hidden";>
<div class="headding">Frequency wise Compliance Status</div>
<div id="2ndDataBlockDiv" style="display: block">
<!--<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="refresh('1stBlock')"><img src="images/refresh-icon.jpg" width="15" height="15" alt="Refresh" title="Refresh" /></a></div>
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="maximizeDetails('2ndDataBlockDiv','Frequency wise Compliance Status')"><img src="images/maximize-icon.jpg" width="15" height="15" alt="Maximize" title="Maximize" /></a></div>
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="showDetailsPie('2ndDataBlockDiv','2ndGraphBlockDiv')"><img src="images/pie_chart_icon.jpg" width="15" height="15" alt="Get Data" title="Get Pie Chart" /></a></div>
--><div class="clear"></div>
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
	<td align="center" width="12%" onclick="showComplDetails('<s:property value="#pendingCompl.departId"/>','Y','Total');"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="#pendingCompl.annualTotal"/></a></font></td>
	<td align="center" width="12%" onclick="showComplDetails('<s:property value="#pendingCompl.departId" />','Y','Missed');"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="#pendingCompl.annualMissed"/></a></font></td>
	
	<td align="center" width="12%" onclick="showComplDetails('<s:property value="#pendingCompl.departId"/>','M','Total');"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="#pendingCompl.monthlyTotal"/></a></font></td>
	<td align="center" width="12%" onclick="showComplDetails('<s:property value="#pendingCompl.departId"/>','M','Missed');"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="#pendingCompl.monthlyMissed"/></a></font></td>
	
	<td align="center" width="12%" onclick="showComplDetails('<s:property value="#pendingCompl.departId"/>','W','Total');"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="#pendingCompl.weeklyTotal"/></a></font></td>
	<td align="center" width="12%" onclick="showComplDetails('<s:property value="#pendingCompl.departId"/>','W','Missed');"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="#pendingCompl.weeklyMissed"/></a></font></td>
 </tr>
</s:iterator>
<tr bgcolor="#D3EBE3">
	<td align="center" width="20%" height="40%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2">Total</td>
	<td align="center" width="12%" height="40%" onclick="showComplDetails('All','Y','Total');"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="allYearlyTotal"/></a></font></td>
	<td align="center" width="12%" height="40%" onclick="showComplDetails('All','Y','Missed');"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="allYearlyMissedTotal"/></a></font></td>
	
	<td align="center" width="12%" height="40%" onclick="showComplDetails('All','M','Total');"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="allMonthlyTotal"/></a></font></td>
	<td align="center" width="12%" height="40%" onclick="showComplDetails('All','M','Missed');"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="allMonthlyMissedTotal"/></a></font></td>
	
	<td align="center" width="12%" height="40%" onclick="showComplDetails('All','W','Total');"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="allWeeklyTotal"/></a></font></td>
	<td align="center" width="12%" height="40%" onclick="showComplDetails('All','W','Missed');"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="allWeeklyMissedTotal"/></a></font></td>
 </tr>
</tbody>
</table>
</div>
<div id="2ndGraphBlockDiv" style="display: none">
<!--<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="refresh('1stBlock')"><img src="images/refresh-icon.jpg" width="15" height="15" alt="Refresh" title="Refresh" /></a></div>
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="maximizeDetails('2ndGraphBlockDiv','Frequency wise Compliance Status')"><img src="images/maximize-icon.jpg" width="15" height="15" alt="Maximize" title="Maximize" /></a></div>
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="showDetailsData('2ndDataBlockDiv','2ndGraphBlockDiv')"><img src="images/data.jpg" width="15" height="15" alt="Get Data" title="Get Data of Graph" /></a></div>
	--><div class="clear"></div>
	<sjc:chart
        id="mgmtchartPie"
        cssStyle="width: 405px; height: 190px;"
        pie="true"
        pieLabel="true"
        onClickTopics="getChatInfo"
        onHoverTopics="hover"
        legendShow="false"
    >
    <s:iterator value="%{mgmtCompDetail}">
	    <s:if test="key=='Yearly Total'">
		    <sjc:chartData
		    		label="%{key}"
		    		data="%{value}"
		    		color="green"
		    		/>	
		 </s:if>
		 <s:elseif test="key=='Yearly Missed'">
		 	<sjc:chartData
		    		label="%{key}"
		    		data="%{value}"
		    		color="red"/>
		 </s:elseif>
		 <s:elseif test="key=='Monthly Total'">
		 	<sjc:chartData
		    		label="%{key}"
		    		data="%{value}"
		    		color="#FF8000"/>
		 </s:elseif>
		 <s:elseif test="key=='Monthly Missed'">
		 	<sjc:chartData
		    		label="%{key}"
		    		data="%{value}"
		    		color="#084B8A"/>
		 </s:elseif>
		 <s:elseif test="key=='Weekly Total'">
		 	<sjc:chartData
		    		label="%{key}"
		    		data="%{value}"
		    		color="#F08085"/>
		 </s:elseif>
		 <s:elseif test="key=='Weekly Missed'">
		 	<sjc:chartData
		    		label="%{key}"
		    		data="%{value}"
		    		color="#8A0886"/>
		 </s:elseif>
    	</s:iterator>
    </sjc:chart>
</div>
</div>

<div class="contentdiv-small" style="overflow: hidden;">
<div class="headding">Pending Compliance </div>
<div class="clear"></div>
<table align="center" border="0" width="100%" style="border-style:dotted solid;">
               <tr height="30px">
                   <td valign="bottom">
                       <table height="5px" align="center" border="1" style="border-style:dotted solid;" width="100%">
                        	  <tr bgcolor="#06C0C9">
                        			<th align="left" class="headings1" style="color: #FFFFFF;">Task&nbsp;Name</th>
                        			<th align="left" class="headings1" style="color: #FFFFFF;">Due&nbsp;Date</th>
                        			<th align="center" class="headings1" style="color: #FFFFFF;">Status</th>
                        			<th align="center" class="headings1" style="color: #FFFFFF;">Esc&nbsp;Level</th>
                        	  </tr>
                       </table>
                    </td>
               </tr>
               
               <tr>
                   <td valign="top" style="padding-top: 10px" align="left">
                   <marquee onmouseover="this.stop()" onmouseout="this.start()" scrollamount="1" scrolldelay="2" direction="up">
					   <table align="left" border="0" width="100%" style="border-style:dotted solid;">                        		
                              <s:iterator id="userPendingCompl"  status="status" value="%{userWiseUpcomeingComplList}" >
								<tr bordercolor="#ffffff">
                        			<td align="left" ><b><a href="#" onclick="complTakeAction('<s:property value="#userPendingCompl.compId"/>')"><s:property value="#userPendingCompl.taskName"/></a></b></td>
                        			<td align="left"><b><a href="#" onclick="complTakeAction('<s:property value="#userPendingCompl.compId"/>')"><s:property value="#userPendingCompl.dueDate"/></a></b></td>
                        			<td align="left"><b><a href="#" onclick="complTakeAction('<s:property value="#userPendingCompl.compId"/>')"><s:property value="#userPendingCompl.actionStatus"/></a></b></td>
                        			<td align="left"><b><a href="#" onclick="complTakeAction('<s:property value="#userPendingCompl.compId"/>')"><s:property value="#userPendingCompl.currentEscLevel"/></a></b></td>
                        		</tr>
                        	   </s:iterator>
                        </table>
                   </marquee>
                   </td>
               </tr>
              </table>		
</div>

<div class="contentdiv-small" style="overflow: hidden";>

<div class="headding">Compliance Ageing</div>
<div id="3rdDataBlockDiv" style="display: block">
<!--<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="refresh('1stBlock')"><img src="images/refresh-icon.jpg" width="15" height="15" alt="Refresh" title="Refresh" /></a></div>
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="showDetails('1stBlock')"><img src="images/maximize-icon.jpg" width="15" height="15" alt="Maximize" title="Maximize" /></a></div>
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="showDetailsPie('3rdDataBlockDiv','3rdGraphBlockDiv')"><img src="images/pie_chart_icon.jpg" width="15" height="15" alt="Get Data" title="Get Pie Chart" /></a></div>
--><div class="clear"></div>
<table border="1" width="100%" align="center">
<tbody>

 <tr bgcolor="#EB8B05">
	<td  align="center" width="25%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#ffffff" size="2"><b>Department</b></font></td>
	<td  align="center" width="25%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#ffffff" size="2"><b>This Week</b></font></td>
	<td  align="center" width="25%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#ffffff" size="2"><b>This Month</b></font></td>
	<td  align="center" width="25%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#ffffff" size="2"><b>This Year</b></font></td>
 </tr>

<s:iterator id="pendingAgeingCompl"  status="status" value="%{complageingDashboard.complList}" >
 <tr bgcolor="#F0E0C9">
	<td align="center" width="25%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;" href="#"><s:property value="#pendingAgeingCompl.departName" /></a></font></td>
	<td align="center" width="25%" onclick="showAgeingComplDetails('<s:property value="#pendingAgeingCompl.departId"/>','W','Total');"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="#pendingAgeingCompl.weeklyTotal"/></a></font></td>
	<td align="center" width="25%" onclick="showAgeingComplDetails('<s:property value="#pendingAgeingCompl.departId"/>','M','Total');"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;" href="#"><s:property value="#pendingAgeingCompl.monthlyTotal"/></a></font></td>
	<td align="center" width="25%" onclick="showAgeingComplDetails('<s:property value="#pendingAgeingCompl.departId"/>','Y','Total');"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="#pendingAgeingCompl.annualTotal"/></a></font></td>
 </tr>
</s:iterator>

<tr bgcolor="#F0E0C9">
	<td align="center" width="25%" height="40%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2">Total</td>
	<td align="center" width="25%" height="40%" onclick="showAgeingComplDetails('All','W','Total');"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="allAgeingWeeklyTotal"/></a></font></td>
	<td align="center" width="25%" height="40%" onclick="showAgeingComplDetails('All','M','Total');"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="allAgeingMonthlyTotal"/></a></font></td>
	<td align="center" width="25%" height="40%" onclick="showAgeingComplDetails('All','Y','Total');"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="allAgeingYearlyTotal"/></a></font></td>
 </tr>
</tbody>
</table>
</div>

<div id="3rdGraphBlockDiv" style="display: none">
<!--<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="refresh('1stBlock')"><img src="images/refresh-icon.jpg" width="15" height="15" alt="Refresh" title="Refresh" /></a></div>
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="showDetails('1stBlock')"><img src="images/maximize-icon.jpg" width="15" height="15" alt="Maximize" title="Maximize" /></a></div>
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="showDetailsData('3rdDataBlockDiv','3rdGraphBlockDiv')"><img src="images/data.jpg" width="15" height="15" alt="Get Data" title="Get Data of Graph" /></a></div>
	--><div class="clear"></div>
	<sjc:chart
        id="ageingchartPie"
        cssStyle="width: 405px; height: 190px;"
        pie="true"
        pieLabel="true"
        onClickTopics="getChatInfo"
        legendShow="false"
    >
    <s:iterator value="%{ageingCompDetail}">
	    <s:if test="key=='This Year'">
		    <sjc:chartData
		    		label="%{key}"
		    		data="%{value}"
		    		color="orange"
		    		/>	
		    		
		 </s:if>
		 <s:elseif test="key=='This Month'">
		 	<sjc:chartData
		    		label="%{key}"
		    		data="%{value}"
		    		color="#9FF781"/>
		 </s:elseif>
		 <s:elseif test="key=='This Week'">
		 	<sjc:chartData
		    		label="%{key}"
		    		data="%{value}"
		    		color="brown"/>
		 </s:elseif>
    	</s:iterator>
    </sjc:chart>
</div>

</div>
<!-- Alerts & Update Div End -->
</div>
</body>
</html>