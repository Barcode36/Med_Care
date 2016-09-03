<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>
<body>
<div class="list-icon">
	 <div class="head">Project</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div><div class="head">Activity&nbsp;Board</div> 
</div>
<div class="clear"></div> 
<s:hidden id="contactId"  value="%{contactId}"/>
<div class="contentdiv-small" style="overflow: hidden; width: 95.2%; height: 230px;" >
<%-- <div style="height:auto; margin-bottom:10px;" >
             <div style="margin-left: 40%; float: left;">
				<div style="float: left;">
					<img alt="Previous" onclick="previousNextDayData('taskname','1','backward');" style="cursor: pointer;" src="images/backward.png" title="Previous">
					&nbsp;&nbsp;&nbsp;
				</div>
				<div style="float: left;">	
					 <b>Project Activity Due As On</b> 
					  <b>
					 <div id="dateDiv" style="margin-left: 150px;margin-top: -15px;">
					 		<b><s:property value="%{fromDate}"/></b>
					 </div>
					 </b>
				</div>
				<div id="activityDate" style="color: white; float: left;"></div>
				<div style="float: left;">
					&nbsp;&nbsp;&nbsp;<img alt="Next" onclick="previousNextDayData('taskname','1','forward');" style="cursor: pointer;" src="images/forward.png" title="Next">
				</div>
			</div>
</div> --%>
<div style="height:auto; margin-bottom:10px;" ><B><center>Project Activity Due As On <s:property value="%{fromDate}"/></center></B></div>
<table align="center" border="0" width="100%" style="border-style:dotted solid;">
               <tr >
                   <td valign="bottom">
                       <table  align="center" border="1" style="border-style:solid;" width="100%">
                        	  <tr bgcolor="#FFA500">
                        			<td align="center" width="10%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b>Project Name</b></font></td>
                        			<td align="center" width="10%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b>Task&nbsp;Type</b></font></td>
                        			<td align="center" width="10%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b>Priority</b></font></td>
                        			<td align="center" width="10%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b>Alloted&nbsp;To</b></font></td>
                        			<td align="center" width="10%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b>Alloted&nbsp;By</b></font></td>
                        			<td align="center" width="10%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b>For</b></font></td>
	                                <td align="center" width="10%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b>Offering</b></font></td>
	                                <td align="center" width="10%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b>Initiation&nbsp;Date</b></font></td>
	                                <td align="center" width="0%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b>Completion&nbsp;%</b></font></td>
	                                <td align="center" width="10%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b>Last&nbsp;Activity</b></font></td>
	                                <td align="center" width="10%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b>Due&nbsp;For</b></font></td>
	                                <td align="center" width="10%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b>Take&nbsp;Action</b></font></td>
                        	  </tr>
                       </table>
                    </td>
               </tr>
               <tr>
                   <td valign="top" style="padding-top: 0px" align="center">
					   <table align="center" border="0" width="100%" style="border-style:dotted solid;" >                        		
                             <s:iterator id="projList3"  status="status" value="%{dashboardPojo.detailList}" begin="0" end="%{size}"> 
								<tr bordercolor="#000000" >
                        		<td align="center" width="7%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#projList3.tasknameee" /></b></a></font></td>
                                <td align="center" width="7%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="#projList3.taskType" /></a></font></td>
                                <td align="center" width="7%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="#projList3.priority" /></a></font></td>
                                <td align="center" width="7%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="#projList3.allotedtoo" /></a></font></td>
                                <td align="center" width="7%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="#projList3.allotedbyy" /></a></font></td>
                                <td align="center" width="7%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="#projList3.clientFor" /></a></font></td>
                                <td align="center" width="7%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="#projList3.offeringName" /></a></font></td>
                                <td align="center" width="7%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="#projList3.initiondate" /></a></font></td>
                                <td align="center" width="7%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="#projList3.compercentage" /></a></font></td>
                                <td align="center" width="7%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="#projList3.today" /></a></font></td>
                                <td align="center" width="7%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="#projList3.statuss" /></a></font></td>
                                <td align="center" width="7%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;"  href="#">Action</a></font></td>
                              
                                </tr>
                              
                        	 </s:iterator>
                        	
                        </table>
                   </td>
               </tr>
</table>
</div>
<div class="contentdiv-small" style="overflow: hidden; width: 95.2%; height: 230px;" >
<div style="height:auto; margin-bottom:10px;" ><center><B>Project Activity Missed As On <s:property value="%{fromDate}"/></B></center></div>
<table align="center" border="0" width="100%" style="border-style:dotted solid;">
               <tr >
                   <td valign="bottom">
                       <table  align="center" border="1" style="border-style:solid;" width="100%">
                        	  <tr bgcolor="#ADD8E6">
                        			<td align="center" width="10%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b>Project Name</b></font></td>
                        			<td align="center" width="10%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b>Task&nbsp;Type</b></font></td>
                        			<td align="center" width="10%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b>Priority</b></font></td>
                        			<td align="center" width="10%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b>Alloted&nbsp;To</b></font></td>
                        			<td align="center" width="10%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b>Alloted&nbsp;By</b></font></td>
                        			<td align="center" width="10%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b>For</b></font></td>
	                                <td align="center" width="10%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b>Offering</b></font></td>
	                                <td align="center" width="10%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b>Initiation&nbsp;Date</b></font></td>
	                                <td align="center" width="0%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b>Completion&nbsp;%</b></font></td>
	                                <td align="center" width="10%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b>Last&nbsp;Activity</b></font></td>
	                                <td align="center" width="10%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b>Due&nbsp;For</b></font></td>
	                                <td align="center" width="10%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b>Take&nbsp;Action</b></font></td>
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
                        		<td align="center" width="8%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#projList3.tasknameee" /></b></a></font></td>
                                <td align="center" width="8%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="#projList3.taskType" /></a></font></td>
                                <td align="center" width="8%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="#projList3.priority" /></a></font></td>
                                <td align="center" width="8%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="#projList3.allotedtoo" /></a></font></td>
                                <td align="center" width="8%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="#projList3.allotedbyy" /></a></font></td>
                                <td align="center" width="8%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="#projList3.clientFor" /></a></font></td>
                                <td align="center" width="8%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="#projList3.offeringName" /></a></font></td>
                                <td align="center" width="8%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="#projList3.initiondate" /></a></font></td>
                                <td align="center" width="8%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="#projList3.comlpetiondate" /></a></font></td>
                                <td align="center" width="8%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="#projList3.today" /></a></font></td>
                                <td align="center" width="8%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="#projList3.tommorow" /></a></font></td>
                                <td align="center" width="8%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="#projList3.statuss" /></a></font></td>
                                </tr>
                                <br>
                        	 </s:iterator>
                        </table>
                   </marquee>
                   </td>
               </tr>
</table>
</div>
</body>
</html>