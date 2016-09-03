<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib  prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script type="text/javascript" src="<s:url value="/js/helpdesk/feedback.js"/>"></script>
<script>
$(document).ready(function(){
	$("#openDate").text($("#registerDate").text());
});
function printDiv(divName) {
    var printContents = document.getElementById(divName).innerHTML;
    $("#printSelect").dialog('close');
    var myWindow = window.open("","myWindow","width=900,height=600"); 
    myWindow.document.write(printContents);
    myWindow.moveTo(300,200); 
    myWindow.print();
    myWindow.close();
}

function  CancelPrint()
{
   $("#printSelect").dialog('close');
}

</script>

</head>
<body>
<c:set var="open_date" value="${fstatus.open_date}" />
<c:set var="open_time" value="${fstatus.open_time}" />
<c:set var="ticketNo" value="${fstatus.ticket_no}" /> 
<c:set var="feedBackTo" value="${fstatus.empName}" /> 
<c:set var="type" value="${fstatus.feedtype}" /> 
<c:set var="feedBackToDept" value="${fstatus.feedback_to_dept}"/>
<c:set var="subCat" value="${fstatus.feedback_subcatg}"/>
<c:set var="feedCat" value="${fstatus.feedback_catg}"/>
<c:set var="feedType" value="${fstatus.feedtype}"/>

<c:set var="feedbackBy" value="${fstatus.feed_by}"/>
<c:set var="status" value="${fstatus.status}"/>
<c:set var="location" value="${fstatus.location}" />
<c:set var="orgName" value="${orgName}" />
<c:set var="address" value="${address}" />
<c:set var="city" value="${city}" />
<c:set var="pincode" value="${pincode}" />
<div id="printDiv">
<table cellpadding="0" border="0" align="center" cellspacing="0" style="border-collapse: collapse" width="100%">
<tr>
   <td width="10%" align="left"><img src="<s:property value="%{orgImgPath}" />"  width="150" height="50" /></td>
   <td width="90%" align="left">
      <table cellpadding="0" border="0" align="center" cellspacing="0" style="border-collapse: collapse" width="100%">
      <tr>
          <td width="50%" align="center"><font face="Arial, Helvetica, sans-serif" color="#000000" size="3"><b><u>Call Detail Report</u></b></font></td>
      </tr>
      <tr>
          <td width="50%" align="center"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b><u>Medanta Medicity</u></b></font></td>
     </tr>
	</table>
   </td>
</tr>
</table>
<hr />
<font face="Arial, Helvetica, sans-serif" color="#000000" size="1.5"><b>Call No. &nbsp;-&nbsp;<c:out value="${ticketNo}" /></b></font>
<br>
<table border="1" width="100%" align="center" style="border-collapse: collapse;">
    <tr  bgcolor="#D8D8D8" style="height: 25px">
		<td width="10%">
			<font face="Arial, Helvetica, sans-serif" color="#000000" size="1.5"><b>&nbsp;Date&nbsp;&nbsp;Time:&nbsp;</b></font>
		</td>
		<td width="10%">
			<font id="openDate" face="Arial, Helvetica, sans-serif" color="#000000" size="1">&nbsp;<c:out value="${open_date}" />&nbsp;<c:out value="${open_time}" /></font>
		</td>
		<td width="10%">
			<font face="Arial, Helvetica, sans-serif" color="#000000" size="1.5"><b>&nbsp;Requested By:&nbsp;</b></font>
		</td>
		<td width="10%">
			<font face="Arial, Helvetica, sans-serif" color="#000000" size="1">&nbsp;<c:out value="${feedbackBy}" /></font>
		</td>
		<td width="10%">
			<font face="Arial, Helvetica, sans-serif" color="#000000" size="1.5"><b>&nbsp;Status:&nbsp;</b></font>
		</td>
		<td width="10%">
			<font face="Arial, Helvetica, sans-serif" color="#000000" size="1">&nbsp;<c:out value="${status}" /></font>
		</td>
	</tr>

	<tr style="height: 25px">
		<td width="10%">
			<font face="Arial, Helvetica, sans-serif" color="#000000" size="1.5"><b>&nbsp;To Department:&nbsp;</b></font>
		</td>
		<td width="10%">
			<font face="Arial, Helvetica, sans-serif" color="#000000" size="1"> &nbsp;<c:out value="${feedBackToDept}" /> </font>
		</td>
		<td width="10%">
			<font face="Arial, Helvetica, sans-serif" color="#000000" size="1.5"><b>&nbsp;Location:&nbsp;</b></font>
		</td>
		<td width="10%">
			<font face="Arial, Helvetica, sans-serif" color="#000000" size="1"> &nbsp;<c:out value="${location}" /> </font>
		</td>
		<td width="10%">
			<font face="Arial, Helvetica, sans-serif" color="#000000" size="1.5"><b>&nbsp;Assign To:&nbsp;</b></font>
		</td>
		<td width="10%">
			<font face="Arial, Helvetica, sans-serif" color="#000000" size="1"> &nbsp;<c:out value="${feedBackTo}" /> </font>
		</td>
	</tr>
	
	<tr  bgcolor="#D8D8D8" style="height: 25px">
		<td width="10%">
			<font face="Arial, Helvetica, sans-serif" color="#000000" size="1.5"><b>&nbsp;Description:&nbsp;</b></font>
		</td>
		<td width="10%">
			<font face="Arial, Helvetica, sans-serif" color="#000000" size="1"> &nbsp;<c:out value="${subCat}" /> </font>
		</td>
		<td width="10%">
			<font face="Arial, Helvetica, sans-serif" color="#000000" size="1.5"><b>&nbsp;Category:&nbsp;</b></font>
		</td>
		<td width="10%">
			<font face="Arial, Helvetica, sans-serif" color="#000000" size="1"> &nbsp;<c:out value="${feedCat}" /> </font>
		</td>
		<td width="10%">
			<font face="Arial, Helvetica, sans-serif" color="#000000" size="1.5"><b>&nbsp;Call Type:&nbsp;</b></font>
		</td>
		<td width="10%">
			<font face="Arial, Helvetica, sans-serif" color="#000000" size="1"> &nbsp;<c:out value="${feedType}" /></font>
		</td>
	</tr>
</table>
<br />
  <hr />
  <center><b><i>History</i></b></center>
					<table border="1" width="100%" style="border-collapse: collapse;">
			 			<tr bgcolor="#D8D8D8" style="height: 25px">
							<td width="15%">
							<font face="Arial, Helvetica, sans-serif" color="#000000" size="1.5"><b>&nbsp;Call Logged Date and Time</b></font>
							</td>
							<td width="10%">
							<font face="Arial, Helvetica, sans-serif" color="#000000" size="1.5"><b>&nbsp;Action</b></font>
							</td>
							<td width="15%">
							<font face="Arial, Helvetica, sans-serif" color="#000000" size="1.5"><b>&nbsp;Reason</b></font>
							</td>
							<td width="10%">
							<font face="Arial, Helvetica, sans-serif" color="#000000" size="1.5"><b>&nbsp;Status</b></font>
							</td>
							<td width="10%">
							<font face="Arial, Helvetica, sans-serif" color="#000000" size="1.5"><b>&nbsp;Staff</b></font>
							</td>
							<td width="15%">
							<font face="Arial, Helvetica, sans-serif" color="#000000" size="1.5"><b>&nbsp;Remarks</b></font>
							</td>
							<td width="10%">
							<font face="Arial, Helvetica, sans-serif" color="#000000" size="1.5"><b>&nbsp;User Name</b></font>
							</td>
						</tr>
  						<s:iterator value="%{cycleList}" id="temp" status="status">
  						<s:if test="#status.odd">
		   			 		<tr   style="height: 25px">
		   			 		</s:if>
		   			 		<s:if test="#status.even">
		   			 		<tr bgcolor="#D8D8D8" style="height: 25px">
		   			 		</s:if>
							<td width="15%">
							<s:if test="#temp.status=='Register'">
							
							<font id="registerDate" face="Arial, Helvetica, sans-serif" color="#000000" size="1"><s:property value="#temp.action_date"/>&nbsp;&nbsp;&nbsp;<s:property value="#temp.action_time"/></b></font>
							</s:if>
							<s:else>
							<font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><s:property value="#temp.action_date"/>&nbsp;&nbsp;&nbsp;<s:property value="#temp.action_time"/></b></font>
							</s:else>
							
							</td>
							<td width="10%">
							<font   face="Arial, Helvetica, sans-serif" color="#000000" size="1"  ><s:property value="#temp.status"/></font>
							</td>
							<td width="15%">
							<font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><s:property value="#temp.tasktype"/></font>
							</td>
							<td width="10%">
							<font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><s:property value="#temp.statusmode"/></font>
							</td>
							<td width="10%">
							
							<s:if test="#temp.status=='Re-assign'">
							
							<font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><s:property value="#temp.previous_allotto"/></font>
							</s:if>
							<s:else>
							<font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><s:property value="#temp.allotto"/></font>
							</s:else>
							
						 	</td>
							<td width="15%">
							<font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><s:property value="#temp.action_remark"/></font>
							</td>
							<td width="10%">
							<s:if test="#temp.status=='Register'">
							<font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><s:property value="#temp.feed_by"/></font>
							</s:if>
							<s:else>
								<font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><s:property value="#temp.actionby"/></font>
							</s:else>
						 	</td>
						</tr>
						 
				 		 
			
		 	</s:iterator> 
		 	</table>
      <table align="center" width="100%">
         <tr>
          <td width="100%" align="center"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"></font></td> 
        </tr>
	</table>
	</div>
	<br>
	<div class="type-button">
       		<center>
              <sj:submit 
                        clearForm="true"
                        value="  Print  " 
                        button="true"
                        onclick="printDiv('printDiv')"
                        />
              <sj:submit 
                        clearForm="true"
                        value="  Cancel  " 
                        button="true"
                        onclick="CancelPrint()"
                        />          
              </center>
              </div> 
	
</body>
</html>

