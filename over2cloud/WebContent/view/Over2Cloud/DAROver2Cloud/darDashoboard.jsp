<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script type="text/javascript">
function getTaskDetailsInBrief(Taskid,taskname,alltedBy,guidance,status,initiation,completion,technical,functional) 
{
	$("#pendingTakeAction").html("<br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$("#pendingTakeAction").load("<%=request.getContextPath()%>/view/Over2Cloud/DAROver2Cloud/dashboardAction.action?Taskid="+Taskid+"&taskname="+taskname.split(" ").join("%20")+"&allotedBy="+alltedBy.split(" ").join("%20")+"&guidance="+guidance.split(" ").join("%20")+"&status="+status.split(" ").join("%20")+"&initiation="+initiation.split(" ").join("%20")+"&completion="+completion.split(" ").join("%20")+"&technical="+technical.split(" ").join("%20")+"&functional="+functional.split(" ").join("%20")+"");
	$("#pendingTakeAction").dialog('open');
	
}
</script>
</head>
<body>
<sj:dialog
          id="pendingTakeAction"
          showEffect="slide"
          hideEffect="explode"
          autoOpen="false"
          title="Take Action"
          modal="true"
          closeTopics="closeEffectDialog"
          width="1020"
          height="490"
          draggable="true"
    	  resizable="true"
          >
</sj:dialog>
<div class="middle-content">

<div class="contentdiv-small" style="overflow: hidden;height: 154px;">
<div class="clear"></div>
<div style="height:auto; margin-bottom:10px;" id='1stBlock'>
<div style="height:auto; margin-bottom:10px;" ><B>Project Status From <s:property value="%{fromDate}"/> To <s:property value="%{toDate}"/> </B></div>
<table  align="center" border="2" width="100%" style="border-style:solid;">
<tbody>
 <tr bgcolor="#6B6BB2" bordercolor="black" >
    <td align="center" width="25%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Status</b></font></td>
	<td align="center" width="25%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Count</b></font></td>
</tr>
 <tr>
 	<td>
 		<table width="100%" height="100%" border="1">
			  <tr style="background-color:#CCCCFF;">
			    <td align="left" width="25%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#"><b>Ongoing</b></a></font></td>
			  </tr>
			  <tr style="background-color:#CCCCFF;">
			    <td align="left" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#"><b>Snooze</b></a></font></td>
			 </tr>
			  <tr style="background-color:#CCCCFF;">
			    <td align="left" width="25%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#"><b>Completed</b></a></font></td>
			  </tr>
			  <tr style="background-color:#CCCCFF;">
			    <td align="left" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#"><b>Missed</b></a></font></td>
			 </tr>
			 <tr style="background-color:#CCCCFF;">
			    <td align="left" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#"><b>Total</b></a></font></td>
			 </tr>
 		</table>
 	</td>
 	<td>
 		<table width="100%" height="100%" border="1">
 			<s:iterator id="projList1"  status="status" value="%{dashboardPojo.detailList}" >
			 <tr style="background-color:#CCCCFF;">
			    <td align="center" width="25%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#"><b><s:property value="#projList1.pending" /></b></a></font></td>
			 </tr>
			 <tr style="background-color:#CCCCFF;">  
			    <td align="center" width="25%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#"><b><s:property value="#projList1.snooze" /></b></a></font></td>
			 </tr>
			 <tr style="background-color:#CCCCFF;">
			    <td align="center" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#"><b><s:property value="#projList1.tdone" /></b></a></font></td>
			 </tr>
			 <tr style="background-color:#CCCCFF;">
			    <td align="center" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#"><b><s:property value="#projList1.missed" /></b></a></font></td>
			 </tr>
			 <tr style="background-color:#CCCCFF;">
			    <td align="center" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#"><b><s:property value="%{totalStatus}"/></b></a></font></td>
			 </tr>
		</s:iterator>
 		</table>
 	</td>
 </tr>
</tbody>
</table>
</div>
</div>

<div class="contentdiv-small" style="overflow: hidden; width: 62%;height: 154px;" >
<div class="clear"></div>
<div style="height:auto; margin-bottom:10px;" id='1stBlock'>
<div style="height:auto; margin-bottom:10px;" ><B>Project Activity Status</B></div>
<table  align="center" border="2" width="100%" style="border-style:solid;">
<tbody>
 <tr bgcolor="#CCCC00" bordercolor="black" >
 	<td align="center" width="30%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Activity</b></font></td>
 	<td align="center" width="15%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Today</b></font></td>
 	<td align="center" width="15%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Tomorrow</b></font></td>
    <td align="center" width="15%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>This&nbsp;Week</b></font></td>
	<td align="center" width="15%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>This&nbsp;Month</b></font></td>
 </tr>
 <s:iterator id="projList2"  status="status" value="%{dashboardPojo1.detailList}" >
	 <tr style="background-color:#F5F5CC;">
	    <td align="left" width="30%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#"><b><s:property value="#projList2.statuss" /></b></a></font></td>
	    <td align="center" width="15%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#"><b><s:property value="#projList2.today" /></b></a></font></td>
	    <td align="center" width="15%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#"><b><s:property value="#projList2.tommorow" /></b></a></font></td>
	    <td align="center" width="15%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#"><b><s:property value="#projList2.thisWeek" /></b></a></font></td>
	    <td align="center" width="15%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#"><b><s:property value="#projList2.thisMonth"/></b></a></font></td>
	 </tr>
</s:iterator>
</tbody>
</table>
</div>
</div>

<div class="contentdiv-small" style="overflow: hidden; width: 95.2%; height: 340px;" >
<div style="height:auto; margin-bottom:10px;" id='2stBlock'>
<div style="height:auto; margin-bottom:10px;" ><B>Running Projects As On <s:property value="%{toDate}"/></B></div>

<table align="center" border="0" width="100%" style="border-style:dotted solid;">
                        			
               <tr >
                   <td valign="bottom">
                       <table  align="center" border="1" style="border-style:dotted solid;" width="100%">
                        	  <tr bgcolor="#F778A1">
                        			<td align="center" width="12%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Project Name</b></font></td>
                        			<td align="center" width="12%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Task&nbsp;Type</b></font></td>
                        			<td align="center" width="12%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Priority</b></font></td>
                        			<td align="center" width="12%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Alloted&nbsp;To</b></font></td>
                        			<td align="center" width="12%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Alloted&nbsp;By</b></font></td>
                        			<td align="center" width="12%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>For</b></font></td>
	                                <td align="center" width="12%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Offering</b></font></td>
	                                <td align="center" width="12%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Initiation&nbsp;Date</b></font></td>
	                                <td align="center" width="12%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Completion&nbsp;Date</b></font></td>
	                                <td align="center" width="12%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Last&nbsp;Activity</b></font></td>
	                                <td align="center" width="12%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Tomorrow&nbsp;Activity</b></font></td>
	                                <td align="center" width="12%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Current&nbsp;Status</b></font></td>
                        	  </tr>
                       </table>
                    </td>
               </tr>
               <tr>
                   <td valign="top" style="padding-top: 5px" align="center">
                   <marquee onmouseover="this.stop()" onmouseout="this.start()" scrollamount="1" scrolldelay="1" direction="up" >
					   <table align="center" border="0" width="100%" style="border-style:dotted solid;" >                        		
                             <s:iterator id="projList3"  status="status" value="%{dashboardPojo2.detailList}" > 
								<tr bordercolor="#ffffff">
                        		<td align="center" width="8%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" onclick="getTaskDetailsInBrief('<s:property value="#projList1.id" />','<s:property value="#projList1.tasknameee" />','<s:property value="#projList1.allotedbyy" />','<s:property value="#projList1.guidancee" />','<s:property value="#projList1.statuss" />','<s:property value="#projList1.initiondate" />','<s:property value="#projList1.comlpetiondate" />','<s:property value="#projList1.technicalDate" />','<s:property value="#projList1.functonalDate" />');" href="#"><b><s:property value="#projList3.tasknameee" /></b></a></font></td>
                                <td align="center" width="8%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" onclick="getTaskDetailsInBrief('<s:property value="#projList1.id" />','<s:property value="#projList1.tasknameee" />','<s:property value="#projList1.allotedbyy" />','<s:property value="#projList1.guidancee" />','<s:property value="#projList1.statuss" />','<s:property value="#projList1.initiondate" />','<s:property value="#projList1.comlpetiondate" />','<s:property value="#projList1.technicalDate" />','<s:property value="#projList1.functonalDate" />');" href="#"><s:property value="#projList3.taskType" /></a></font></td>
                                <td align="center" width="8%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" onclick="getTaskDetailsInBrief('<s:property value="#projList1.id" />','<s:property value="#projList1.tasknameee" />','<s:property value="#projList1.allotedbyy" />','<s:property value="#projList1.guidancee" />','<s:property value="#projList1.statuss" />','<s:property value="#projList1.initiondate" />','<s:property value="#projList1.comlpetiondate" />','<s:property value="#projList1.technicalDate" />','<s:property value="#projList1.functonalDate" />');" href="#"><s:property value="#projList3.priority" /></a></font></td>
                                <td align="center" width="8%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" onclick="getTaskDetailsInBrief('<s:property value="#projList1.id" />','<s:property value="#projList1.tasknameee" />','<s:property value="#projList1.allotedbyy" />','<s:property value="#projList1.guidancee" />','<s:property value="#projList1.statuss" />','<s:property value="#projList1.initiondate" />','<s:property value="#projList1.comlpetiondate" />','<s:property value="#projList1.technicalDate" />','<s:property value="#projList1.functonalDate" />');" href="#"><s:property value="#projList3.allotedtoo" /></a></font></td>
                                <td align="center" width="8%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" onclick="getTaskDetailsInBrief('<s:property value="#projList1.id" />','<s:property value="#projList1.tasknameee" />','<s:property value="#projList1.allotedbyy" />','<s:property value="#projList1.guidancee" />','<s:property value="#projList1.statuss" />','<s:property value="#projList1.initiondate" />','<s:property value="#projList1.comlpetiondate" />','<s:property value="#projList1.technicalDate" />','<s:property value="#projList1.functonalDate" />');" href="#"><s:property value="#projList3.allotedbyy" /></a></font></td>
                                <td align="center" width="8%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" onclick="getTaskDetailsInBrief('<s:property value="#projList1.id" />','<s:property value="#projList1.tasknameee" />','<s:property value="#projList1.allotedbyy" />','<s:property value="#projList1.guidancee" />','<s:property value="#projList1.statuss" />','<s:property value="#projList1.initiondate" />','<s:property value="#projList1.comlpetiondate" />','<s:property value="#projList1.technicalDate" />','<s:property value="#projList1.functonalDate" />');" href="#"><s:property value="#projList3.clientFor" /></a></font></td>
                                <td align="center" width="8%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" onclick="getTaskDetailsInBrief('<s:property value="#projList1.id" />','<s:property value="#projList1.tasknameee" />','<s:property value="#projList1.allotedbyy" />','<s:property value="#projList1.guidancee" />','<s:property value="#projList1.statuss" />','<s:property value="#projList1.initiondate" />','<s:property value="#projList1.comlpetiondate" />','<s:property value="#projList1.technicalDate" />','<s:property value="#projList1.functonalDate" />');" href="#"><s:property value="#projList3.offeringName" /></a></font></td>
                                <td align="center" width="8%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" onclick="getTaskDetailsInBrief('<s:property value="#projList1.id" />','<s:property value="#projList1.tasknameee" />','<s:property value="#projList1.allotedbyy" />','<s:property value="#projList1.guidancee" />','<s:property value="#projList1.statuss" />','<s:property value="#projList1.initiondate" />','<s:property value="#projList1.comlpetiondate" />','<s:property value="#projList1.technicalDate" />','<s:property value="#projList1.functonalDate" />');" href="#"><s:property value="#projList3.initiondate" /></a></font></td>
                                <td align="center" width="8%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" onclick="getTaskDetailsInBrief('<s:property value="#projList1.id" />','<s:property value="#projList1.tasknameee" />','<s:property value="#projList1.allotedbyy" />','<s:property value="#projList1.guidancee" />','<s:property value="#projList1.statuss" />','<s:property value="#projList1.initiondate" />','<s:property value="#projList1.comlpetiondate" />','<s:property value="#projList1.technicalDate" />','<s:property value="#projList1.functonalDate" />');" href="#"><s:property value="#projList3.comlpetiondate" /></a></font></td>
                                <td align="center" width="8%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" onclick="getTaskDetailsInBrief('<s:property value="#projList1.id" />','<s:property value="#projList1.tasknameee" />','<s:property value="#projList1.allotedbyy" />','<s:property value="#projList1.guidancee" />','<s:property value="#projList1.statuss" />','<s:property value="#projList1.initiondate" />','<s:property value="#projList1.comlpetiondate" />','<s:property value="#projList1.technicalDate" />','<s:property value="#projList1.functonalDate" />');" href="#"><s:property value="#projList3.today" /></a></font></td>
                                <td align="center" width="8%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" onclick="getTaskDetailsInBrief('<s:property value="#projList1.id" />','<s:property value="#projList1.tasknameee" />','<s:property value="#projList1.allotedbyy" />','<s:property value="#projList1.guidancee" />','<s:property value="#projList1.statuss" />','<s:property value="#projList1.initiondate" />','<s:property value="#projList1.comlpetiondate" />','<s:property value="#projList1.technicalDate" />','<s:property value="#projList1.functonalDate" />');" href="#"><s:property value="#projList3.tommorow" /></a></font></td>
                                <td align="center" width="8%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" onclick="getTaskDetailsInBrief('<s:property value="#projList1.id" />','<s:property value="#projList1.tasknameee" />','<s:property value="#projList1.allotedbyy" />','<s:property value="#projList1.guidancee" />','<s:property value="#projList1.statuss" />','<s:property value="#projList1.initiondate" />','<s:property value="#projList1.comlpetiondate" />','<s:property value="#projList1.technicalDate" />','<s:property value="#projList1.functonalDate" />');" href="#"><s:property value="#projList3.statuss" /></a></font></td>
                                </tr>
                                <br>
                        	 </s:iterator>
                        </table>
                   </marquee>
                   </td>
               </tr>
</table>
</div>
</div>
</div>
</body>
</html>