<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>

<s:if test="complDetails.size()>0">
 <table width="100%" border="1" style="border-collapse: collapse;">
        
        <tr bgcolor="#D8D8D8" style="height: 25px">
          <s:iterator value="%{complDetails}" status="status" >
            <s:if test="%{key=='Department' || key=='Task Type'}">
                    <td align="left" width="15%" ><strong><s:property value="%{key}"/>:</strong></td>
                    <td align="left" width="30%"> <s:property value="%{value}"/></td>
            </s:if>
        </s:iterator>
        </tr>
        <tr style="height: 25px">
         <s:iterator value="%{complDetails}" status="status" >
            <s:if test="%{key=='Document 1' || key=='Document 2'}">
                    <td align="left" width="15%"><strong><s:property value="%{key}"/>:</strong></td>
                    <td align="left" width="30%" ><a href='view/Over2Cloud/Compliance/compliance_pages/DocDownload.action?dataFor=<s:property value="%{key}"/>&docName=<s:property value="%{complID}"/>'><s:property value="%{value}"/></a></td>
            </s:if>
        </s:iterator>
       </tr>
       <tr bgcolor="#D8D8D8" style="height: 25px">
         <s:iterator value="%{complDetails}" status="status" >
           <s:if test="%{key=='Alloted By' || key=='Alloted On'}">
               
                   <td align="left" width="15%"><strong><s:property value="%{key}"/>:</strong></td>
                   <td align="left" width="30%" > <s:property value="%{value}"/></td>
               
           </s:if>
       </s:iterator>
       </tr>
        <tr style="height: 25px">
         <s:iterator value="%{complDetails}" status="status" >
            <s:if test="%{key=='Alloted To' || key=='Budgeted'}">
                    <td align="left" width="15%"><strong><s:property value="%{key}"/>:</strong></td>
                    <td align="left" width="30%" > <s:property value="%{value}"/></td>
            </s:if>
        </s:iterator>
    </tr>
            <tr  bgcolor="#D8D8D8" style="height: 25px">
          <s:iterator value="%{complDetails}" status="status" >
            <s:if test="%{key=='Frequency' || key=='Next Due Date'}">
                
                    <td align="left" width="15%"><strong><s:property value="%{key}"/>:</strong></td>
                    <td align="left" width="30%" > <s:property value="%{value}"/></td>
                
            </s:if>
        </s:iterator>
        </tr>
        <tr style="height: 25px" >
         <s:iterator value="%{complDetails}" status="status" >
            <s:if test="%{key=='Current Status' || key=='Current Level'}">
                    <td align="left" width="15%"><strong><s:property value="%{key}"/>:</strong></td>
                    <td align="left" width="30%" > <s:property value="%{value}"/></td>
            </s:if>
        </s:iterator>
    </tr>
</table> 
</s:if>

<s:if test="checkListDetails.size()>0">
<table width="100%" border="1" style="border-collapse: collapse;">
<tr >
<td>
<center><b>Check List </b></center>
<table width="100%" border="1" style="border-collapse: collapse;">
 <s:iterator value="%{checkListDetails}" status="status" >
 <s:if test="#status.odd">
            <tr bgcolor="#D8D8D8" style="height: 25px">
            <td><s:property value="%{key}"/>.</td>
            <td><s:property value="%{value}"/></td>
            </tr>
  </s:if>
  <s:else>
   <tr bgcolor="#FFFFFF" style="height: 25px">
            <td><s:property value="%{key}"/>.</td>
            <td><s:property value="%{value}"/></td>
            </tr>
  
  </s:else>
        </s:iterator>
</table>
</td>
</tr>
</table>
</s:if>

<s:if test="doneDetails.size()>0">
<table width="100%" border="1" align="center" style="border-collapse: collapse;">
 <s:iterator id="totalCompl"  status="status" value="%{doneDetails}" >
  					<tr >
	  					<s:if test="%{#totalCompl.actionStatus!='Pending'}">
	  						   <s:if test="%{#totalCompl.actionStatus=='Recurring' || #totalCompl.actionStatus=='Done'}">
	  								<td  align="center"><b>Done</b></td>
  								</s:if>
  								<s:else>
  									<td  align="center"><b><s:property value="#totalCompl.actionStatus" /></b></td>
  								</s:else>
	  					</s:if>
                     </tr>
                     <tr >
                         <td>
                             <table width="100%"  align="center" border="1">
                              <s:if test="%{#totalCompl.actionStatus=='Snooze'}">
	                              <tr bgcolor="#D8D8D8" style="height: 25px;">
	                                     <td  align="center">From Date & Time</td>
	                                     <td  align="center"><s:property value="#totalCompl.actionTakenOn" /></td>
	                                 </tr>
	                                 <tr  style="height: 25px;">
	                                     <td  align="center">To Date & Time</td>
	                                     <td  align="center"><s:property value="#totalCompl.dueDate" /></td>
	                                 </tr>
	                                 <tr bgcolor="#D8D8D8" style="height: 25px;">
	                                     <td  align="center">Reasons</td>
	                                     <td  align="center"><s:property value="#totalCompl.comment" /></td>
	                                 </tr>
	                                  <tr  style="height: 25px;">
	                                     <td  align="center">Snooze By</td>
	                                     <td  align="center"><s:property value="#totalCompl.name" /></td>
	                                 </tr>
	                                  <tr bgcolor="#D8D8D8" style="height: 25px;">
	                                     <td  align="center">Total Snooze</td>
	                                     <td  align="center"><s:property value="#totalCompl.difference" /></td>
	                                 </tr>
                                 </s:if>
                                 <s:if test="%{#totalCompl.actionStatus=='Reschedule'}">
                                 <tr bgcolor="#D8D8D8" style="height: 25px;">
                                     <td  align="center">From Date & Time</td>
                                     <td  align="center"><s:property value="#totalCompl.actionTakenOn" /></td>
                                 </tr>
                                 <tr  style="height: 25px;">
                                     <td  align="center">New Date & Time</td>
                                     <td  align="center"><s:property value="#totalCompl.dueDate" /></td>
                                 </tr>
                                 <tr bgcolor="#D8D8D8" style="height: 25px;">
                                     <td  align="center">Reasons</td>
                                     <td  align="center"><s:property value="#totalCompl.comment" /></td>
                                 </tr>
                                  <tr  style="height: 25px;">
                                     <td  align="center">By</td>
                                     <td  align="center"><s:property value="#totalCompl.name" /></td>
                                 </tr>
                                 </s:if> 
                               <s:if test="%{#totalCompl.actionStatus=='Recurring' || #totalCompl.actionStatus=='Done'}">
                                 <tr bgcolor="#D8D8D8" style="height: 25px;">
                                     <td  align="center">On Date & Time</td>
                                     <td  align="center"><s:property value="#totalCompl.actionTakenOn" /></td>
                                 </tr>
                                 <tr  style="height: 25px;">
                                     <td  align="center">Action By</td>
                                     <td  align="center"><s:property value="#totalCompl.name" /></td>
                                 </tr>
                                 <tr bgcolor="#D8D8D8" style="height: 25px;">
                                     <td  align="center">Comments</td>
                                     <td  align="center"><s:property value="#totalCompl.comment" /></td>
                                 </tr>
                                  <tr  style="height: 25px;">
                                     <td  align="center">Document 1</td>
                                     <td  align="center" href='view/Over2Cloud/Compliance/compliance_pages/DocDownload.action?doneDoc=<s:property value="#totalCompl.id"/>&dataFor="doc1"'><s:property value="#totalCompl.docPath" /></td>
                                 </tr>
                                  <tr bgcolor="#D8D8D8" style="height: 25px;">
                                     <td  align="center">Document 2</td>
                                     <td  align="center" href='view/Over2Cloud/Compliance/compliance_pages/DocDownload.action?doneDoc=<s:property value="#totalCompl.id"/>&dataFor="doc2"'><s:property value="#totalCompl.docPath1" /></td>
                                 </tr>
                                   <tr  style="height: 25px;">
                                     <td  align="center">Document 3</td>
                                     <td  align="center" href='view/Over2Cloud/Compliance/compliance_pages/DocDownload.action?doneDoc=<s:property value="#totalCompl.id"/>&dataFor="doc3"'><s:property value="#totalCompl.docPath2" /></td>
                                 </tr>
                                  <tr bgcolor="#D8D8D8" style="height: 25px;">
                                     <td  align="center">Cost</td>
                                     <td  align="center"><s:property value="#totalCompl.cost" /></td>
                                 </tr>
                                 <tr  style="height: 25px;">
                                     <td  align="center">Total Time</td>
                                     <td  align="center"><s:property value="#totalCompl.difference" /></td>
                                 </tr>
                               </s:if>
                             </table>
                         </td>
                     </tr>
        </s:iterator>
</table>

</s:if>
<s:if test="workingmap.size()>0">
<br>
<center>
<table width="50%" border="1" style="border-collapse: collapse;">
          <s:iterator value="%{workingmap}" status="status">
            <s:if test="#status.odd">
                <tr  bgcolor="#D8D8D8" style="height: 25px;">
                 <td align="left" width="25%"><strong><s:property value="%{key}"/>:</strong></td>
                <td align="left" width="25%"><s:property value="%{value}"/></td>
                </tr>
            </s:if>
               <s:else>
                 <tr style="height: 25px;" >
                 <td align="left" width="25%"><strong><s:property value="%{key}"/>:</strong></td>
                <td align="left" width="25%"><s:property value="%{value}"/></td>
                </tr>
               
               </s:else>
           
        </s:iterator>
</table>

</center>
</s:if>

<s:if test="statusDetails.size()>0">
<table width="100%" border="1" style="border-collapse: collapse;">
          <s:iterator value="%{statusDetails}" status="status">
            <s:if test="#status.odd">
                <tr  bgcolor="#D8D8D8" style="height: 25px;">
            </s:if>
                <td align="left" width="25%"><strong><s:property value="%{key}"/>:</strong></td>
                <td align="left" width="25%"><s:property value="%{value}"/></td>
            <s:if test="#status.even">
            </s:if>
        </s:iterator>
</table>
</s:if>
<s:if test="escalationDetails.size()>0">
<center><b>Expected TAT & Escalations </b></center>
<table width="100%" border="1" style="border-collapse:separate;">

            <tr bgcolor="#D8D8D8" style="height: 25px;">
          <s:iterator value="%{escalationDetails}" status="status" >
            <s:if test="%{key=='L-2 Escalate Date & Time' || key=='L-2 Escalate To'}">
                
                    <td align="left" width="25%"><strong><s:property value="%{key}"/>:</strong></td>
                    <td align="left" width="25%" > <s:property value="%{value}"/></td>
                
            </s:if>
        </s:iterator>
        </tr>
     <tr style="height: 25px;">
         <s:iterator value="%{escalationDetails}" status="status" >
            <s:if test="%{key=='L-3 Escalate Date & Time' || key=='L-3 Escalate To'}">
                    <td align="left" width="25%"><strong><s:property value="%{key}"/>:</strong></td>
                    <td align="left" width="25%" > <s:property value="%{value}"/></td>
            </s:if>
        </s:iterator>
    </tr>
            <tr bgcolor="#D8D8D8" style="height: 25px;">
          <s:iterator value="%{escalationDetails}" status="status" >
            <s:if test="%{key=='L-4 Escalate Date & Time' || key=='L-4 Escalate To'}">
                
                    <td align="left" width="25%"><strong><s:property value="%{key}"/>:</strong></td>
                    <td align="left" width="25%" > <s:property value="%{value}"/></td>
                
            </s:if>
        </s:iterator>
        </tr>
        <tr style="height: 25px;">
         <s:iterator value="%{escalationDetails}" status="status" >
            <s:if test="%{key=='L-5 Escalate Date & Time' || key=='L-5 Escalate To'}">
                    <td align="left" width="25%"><strong><s:property value="%{key}"/>:</strong></td>
                    <td align="left" width="25%" > <s:property value="%{value}"/></td>
            </s:if>
        </s:iterator>
    </tr>
</table>
</s:if>
<s:if test="allotDetails.size()>0">
<table width="100%" border="0" style="border-collapse: collapse;">
 <s:iterator value="%{allotDetails}" id="totalCompl">
        <tr>
            <td>
                <table width="100%"  align="center" border="1" style="border-collapse: collapse;">
                    <tr bgcolor="#D8D8D8" style="height: 25px;">
                        <td align="left" width="12%"><strong>Name:</strong></td>
                        <td  align="center"><s:property value="#totalCompl.remindTo" /></td>
                    </tr>
                    <tr style="height: 25px;">
                        <td align="left" width="12%"><strong>Mobile No:</strong></td>
                        <td  align="center"><s:property value="#totalCompl.mobNo" /></td>
                    </tr>
                    <tr bgcolor="#D8D8D8" style="height: 25px;">
                        <td align="left" width="12%"><strong>Email Id:</strong></td>
                        <td  align="center"><s:property value="#totalCompl.emailId" /></td>
                    </tr>
                    <tr style="height: 25px;">
                        <td align="left" width="12%"><strong>Department:</strong></td>
                        <td  align="center"><s:property value="#totalCompl.departName" /></td>
                    </tr>
                </table>
            </td>
        </tr>
</s:iterator>
</table>
</s:if>

<s:if test="allotByDetails.size()>0">
<table width="100%" border="1" style="border-collapse: collapse;">
 <s:iterator value="%{allotByDetails}" id="totalCompl">
          <tr>
            <td>
                <table width="100%"  align="center" border="1" style="border-collapse: collapse;">
                    <tr bgcolor="#D8D8D8" style="height: 25px;">
                        <td align="left" width="12%"><strong>Name:</strong></td>
                        <td  align="center"><s:property value="#totalCompl.remindTo" /></td>
                    </tr>
                    <tr style="height: 25px;">
                        <td align="left" width="12%"><strong>Mobile No:</strong></td>
                        <td  align="center"><s:property value="#totalCompl.mobNo" /></td>
                    </tr>
                    <tr bgcolor="#D8D8D8" style="height: 25px;">
                        <td align="left" width="12%"><strong>Email Id:</strong></td>
                        <td  align="center"><s:property value="#totalCompl.emailId" /></td>
                    </tr>
                    <tr style="height: 25px;">
                        <td align="left" width="12%"><strong>Department:</strong></td>
                        <td  align="center"><s:property value="#totalCompl.departName" /></td>
                    </tr>
                </table>
            </td>
        </tr>
        </s:iterator>
</table>
</s:if>
<s:if test="reminderDetails.size()>0">
<table width="100%" border="1" style="border-collapse: collapse;">
 <s:iterator value="%{reminderDetails}" status="status">
     <s:if test="#status.odd">
                <tr  bgcolor="#D8D8D8" style="height: 25px;">
                <td><s:property value="%{key}"/>:</td>
            <td><s:property value="%{value}"/></td>
            </tr>
            </s:if>
            <s:if test="#status.even">
            <tr style="height: 25px;">
                <td><s:property value="%{key}"/>:</td>
            <td><s:property value="%{value}"/></td>
            </tr>
            </s:if>
            
            
        </s:iterator>
</table>
</s:if>
<s:if test="budgetDetails.size()>0">
<table width="50%" border="1" align="center" style="border-collapse: collapse;">
 <s:iterator value="%{budgetDetails}" id="totalCompl" >
                     <tr bgcolor="#E86680" style="height: 25px;">
                         <td  align="center"><b>As on <s:property value="#totalCompl.actionTakenOn" /></b></td>
                     </tr>
                     <tr style="height: 25px;">
                         <td>
                             <table width="100%"  align="center" border="1">
                                 <tr bgcolor="#F8D0D8">
                                     <td  align="center">Budgeted</td>
                                     <td  align="center"><s:property value="#totalCompl.budgeted" /></td>
                                 </tr>
                                 <tr bgcolor="#F8D0D8">
                                     <td  align="center">Actual</td>
                                     <td  align="center"><s:property value="#totalCompl.actual" /></td>
                                 </tr>
                                 <tr bgcolor="#F8D0D8">
                                     <td  align="center">Difference</td>
                                     <td  align="center"><s:property value="#totalCompl.difference" /></td>
                                 </tr>
                             </table>
                         </td>
                     </tr>
        </s:iterator>
</table>
</s:if>

</body>
</html>