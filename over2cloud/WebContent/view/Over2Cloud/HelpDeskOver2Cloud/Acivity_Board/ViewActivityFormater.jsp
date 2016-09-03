<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<SCRIPT type="text/javascript">
function ResendSMS()
{
	var resendFeedId = $("#resend_id").val();
 	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Lodge_Feedback/resendSMSToSelectedEmp.action?SMSResend_feedId="+resendFeedId,
	    success : function(feeddraft) {
	    	$("#takeActionGrid").dialog('close');
	    	onloadData();
	},
	   error: function() {
            alert("error");
        }
	 });

}


function CancelResendSMS()
{
	
	$("#takeActionGrid").dialog('close');
	

}

</SCRIPT>

</head>
<body>
<s:hidden id="resend_id" value="%{SMSResend_feedId}"/>
 
	<center>
		<s:if test="mainTable=='employee_basic' && formaterOn=='feed_by'">
		<br>
			<table border="1" width="100%" style="border-collapse: collapse;">
				<s:iterator value="dataMap" status="status">
					<s:if test="#status.odd">
						<tr bgcolor="#D8D8D8" style="height: 25px">
							<td width="10%"><s:property value="%{key}"/>:</td>
							<td width="10%"><s:property value="%{value}"/></td>
						</tr>
					</s:if>
					<s:elseif test="#status.even">
						<tr style="height: 23px">
							<td width="10%"><s:property value="%{key}"/>:</td>
							<td width="10%"><s:property value="%{value}"/></td>
						</tr>
					</s:elseif>
				</s:iterator>
			</table>
		</s:if>
		
		<s:if test="mainTable=='employee_basic' && formaterOn=='allot_to'">
		<br>
			<table border="1" width="100%" style="border-collapse: collapse;">
				<s:iterator value="dataMap" status="status">
					<s:if test="#status.odd">
						<tr bgcolor="#D8D8D8" style="height: 25px">
							<td width="10%"><s:property value="%{key}"/>:</td>
							<td width="10%"><s:property value="%{value}"/></td>
						</tr>
					</s:if>
					<s:elseif test="#status.even">
						<tr style="height: 23px">
							<td width="10%"><s:property value="%{key}"/>:</td>
							<td width="10%"><s:property value="%{value}"/></td>
						</tr>
					</s:elseif>
				</s:iterator>
			</table>
		</s:if>
		
		<s:if test="mainTable=='employee_basic' && formaterOn=='lodgeMode'">
		<br>
			<table border="1" width="100%" style="border-collapse: collapse;">
				<s:iterator value="dataMap" status="status">
					<s:if test="#status.odd">
						<tr bgcolor="#D8D8D8" style="height: 25px">
							<td width="10%"><s:property value="%{key}"/>:</td>
							<td width="10%"><s:property value="%{value}"/></td>
						</tr>
					</s:if>
					<s:elseif test="#status.even">
						<tr style="height: 23px">
							<td width="10%"><s:property value="%{key}"/>:</td>
							<td width="10%"><s:property value="%{value}"/></td>
						</tr>
					</s:elseif>
				</s:iterator>
			</table>
		</s:if>
		
		<s:if test="mainTable=='feedback_status' && formaterOn=='status'">
	  	<s:iterator value="%{list}" id="temp">
		    	<s:if test="#temp.status== 'Register'" >
			 		
					<center><b><i><s:property value="#temp.status"/></i></b></center>
					<table border="1" width="100%" style="border-collapse: collapse;">
			 			<tr bgcolor="#D8D8D8" style="height: 25px">
							<td width="10%">Register At:</td>
							<td width="10%"><s:property value="#temp.action_date"/>&nbsp;&nbsp;&nbsp;<s:property value="#temp.action_time"/></td>
						</tr>
				 		  
				   		<tr   style="height: 25px">
							<td width="10%">Register By:</td>
							<td width="10%"><s:property value="#temp.feed_by"/></td>
						</tr>
						<tr  bgcolor="#D8D8D8"  style="height: 25px">
							<td width="10%">Alloted To:</td>
							<td width="10%"><s:property value="#temp.allotto"/></td>
						</tr>
						  
				 
			</table>
			</s:if>
			   	<s:if test="#temp.status== 'Resolved'" >
			 		
					<center><b><i><s:property value="#temp.status"/></i></b></center>
					<table border="1" width="100%" style="border-collapse: collapse;">
			 			<tr bgcolor="#D8D8D8" style="height: 25px">
							<td width="10%">Resolve At:</td>
							<td width="10%"><s:property value="#temp.action_date"/>&nbsp;&nbsp;&nbsp;<s:property value="#temp.action_time"/></td>
						</tr>
				 		  <tr style="height: 23px">
							<td width="10%">Total Breakdown:</td>
							<td width="10%"><s:property value="#temp.action_duration"/></td>
						</tr>
				   		<tr bgcolor="#D8D8D8" style="height: 25px">
							<td width="10%">Resolved By:</td>
							<td width="10%"><s:property value="#temp.actionby"/></td>
						</tr>
						  <tr style="height: 23px">
							<td width="10%">Remarks:</td>
							<td width="10%"><s:property value="#temp.action_remark"/></td>
						</tr>
				 
			</table>
			</s:if>
			
			
			
			<s:if test="#temp.status== 'Snooze'" >
			 		
					<center><b><i>Parked</i></b></center>
					<table border="1" width="100%" style="border-collapse: collapse;">
			 			<tr bgcolor="#D8D8D8" style="height: 25px">
							<td width="10%">Parked At:</td>
							<td width="10%"><s:property value="#temp.action_date"/>&nbsp;&nbsp;&nbsp;<s:property value="#temp.action_time"/></td>
						</tr>
				 		  <tr style="height: 23px">
							<td width="10%">Parked Upto:</td>
							<td width="10%"><s:property value="#temp.sn_upto_date"/>&nbsp;&nbsp;&nbsp;<s:property value="#temp.sn_upto_time"/></td>
						</tr>
				   		<tr bgcolor="#D8D8D8" style="height: 25px">
							<td width="10%">Duration:</td>
							<td width="10%"><s:property value="#temp.action_duration"/></td>
						</tr>
						  <tr style="height: 23px">
							<td width="10%">Parked By:</td>
							<td width="10%"><s:property value="#temp.actionby"/></td>
						</tr>
						<tr bgcolor="#D8D8D8" style="height: 25px">
							<td width="10%">Reason:</td>
							<td width="10%"><s:property value="#temp.tasktype"/></td>
						</tr>
						 <%--  <tr style="height: 23px">
							<td width="10%">Total time:</td>
							<td width="10%"><s:property value="#temp.total_time"/></td>
						</tr>
				 		<tr bgcolor="#D8D8D8" style="height: 25px">
							<td width="10%">Working time:</td>
							<td width="10%"><s:property value="#temp.working_time"/></td>
						</tr> --%>
			</table>
			</s:if>
			
			
			<s:if test="#temp.status== 'High Priority'" >
			 		
					<center><b><i>High Priority</i></b></center>
					<table border="1" width="100%" style="border-collapse: collapse;">
			 			<tr bgcolor="#D8D8D8" style="height: 25px">
							<td width="10%">High Priority At:</td>
							<td width="10%"><s:property value="#temp.action_date"/>&nbsp;&nbsp;&nbsp;<s:property value="#temp.action_time"/></td>
						</tr>
				 		 
				   		 
						  <tr  style="height: 23px">
							<td width="10%">High Priority By:</td>
							<td width="10%"><s:property value="#temp.actionby"/></td>
						</tr>
						<tr  bgcolor="#D8D8D8"  style="height: 25px">
							<td width="10%">Reason:</td>
							<td width="10%"><s:property value="#temp.tasktype"/></td>
						</tr>
						<%--   <tr   style="height: 23px">
							<td width="10%">Total time:</td>
							<td width="10%"><s:property value="#temp.total_time"/></td>
						</tr> --%>
				 		<%-- <tr bgcolor="#D8D8D8"  style="height: 25px">
							<td width="10%">Working time:</td>
							<td width="10%"><s:property value="#temp.working_time"/></td>
						</tr> --%>
			</table>
			</s:if>
			
			<s:if test="#temp.status== 'Re-Opened'" >
			 		
					<center><b><i>Re-Opened</i></b></center>
					<table border="1" width="100%" style="border-collapse: collapse;">
			 			<tr bgcolor="#D8D8D8" style="height: 25px">
							<td width="10%">Re-Opened At:</td>
							<td width="10%"><s:property value="#temp.action_date"/>&nbsp;&nbsp;&nbsp;<s:property value="#temp.action_time"/></td>
						</tr>
				 		<tr   style="height: 23px">
							<td width="10%">Re-opened By:</td>
							<td width="10%"><s:property value="#temp.actionby"/></td>
						</tr>
						<tr  bgcolor="#D8D8D8" style="height: 25px">
							<td width="10%">Reason:</td>
							<td width="10%"><s:property value="#temp.tasktype"/></td>
						</tr>
						  <tr  style="height: 23px">
							<td width="10%">Re-alloted to:</td>
							<td width="10%"><s:property value="#temp.allotto"/></td>
						</tr>
				 		 
			</table>
			</s:if>
			
			<s:if test="#temp.status== 'Ignore'" >
			 		
					<center><b><i>Ignore</i></b></center>
					<table border="1" width="100%" style="border-collapse: collapse;">
			 			<tr bgcolor="#D8D8D8" style="height: 25px">
							<td width="10%">Ignore At:</td>
							<td width="10%"><s:property value="#temp.action_date"/>&nbsp;&nbsp;&nbsp;<s:property value="#temp.action_time"/></td>
						</tr>
				 		<tr   style="height: 23px">
							<td width="10%">Ignore By:</td>
							<td width="10%"><s:property value="#temp.actionby"/></td>
						</tr>
						<tr  bgcolor="#D8D8D8" style="height: 25px">
							<td width="10%">Reason:</td>
							<td width="10%"><s:property value="#temp.tasktype"/></td>
						</tr>
						   
				 		  <tr style="height: 23px">
							<td width="10%">Remarks:</td>
							<td width="10%"><s:property value="#temp.action_remark"/></td>
						</tr>
			</table>
			</s:if>
			
			
			
			<s:if test="#temp.status== 'Re-assign'" >
			 		
					<center><b><i>Re-assign</i></b></center>
					<table border="1" width="100%" style="border-collapse: collapse;">
			 			<tr bgcolor="#D8D8D8" style="height: 25px">
							<td width="10%">Re-assign At:</td>
							<td width="10%"><s:property value="#temp.action_date"/>&nbsp;&nbsp;&nbsp;<s:property value="#temp.action_time"/></td>
						</tr>
				 		<tr   style="height: 23px">
							<td width="10%">Re-assign By:</td>
							<td width="10%"><s:property value="#temp.actionby"/></td>
						</tr>
						<tr  bgcolor="#D8D8D8" style="height: 25px">
							<td width="10%">Reason:</td>
							<td width="10%"><s:property value="#temp.tasktype"/></td>
						</tr>
						  <tr  style="height: 23px">
							<td width="10%">Re-assign to:</td>
							<td width="10%"><s:property value="#temp.previous_allotto"/></td>
						</tr>
				 		 
			</table>
			</s:if>
			
			<s:if test="#temp.status== 'Escalated'" >
			 		
					<center><b><i>Escalated</i></b></center>
					<table border="1" width="100%" style="border-collapse: collapse;">
			 			<tr bgcolor="#D8D8D8" style="height: 25px">
							<td width="10%">Escalated At:</td>
							<td width="10%"><s:property value="#temp.action_date"/>&nbsp;&nbsp;&nbsp;<s:property value="#temp.action_time"/></td>
						</tr>
				 		<tr   style="height: 23px">
							<td width="10%">Escalated To:</td>
							<td width="10%"><s:property value="#temp.action_remark"/></td>
						</tr>
 		</table>
			</s:if>
			
		 	</s:iterator> 
			 
		<br>
		<br>
		<br>
		<br>
		<br>
		<s:if test="%{list.size()==0}">
		<center><b><i><font color="#D8D8D8" size="5">There are no data</font></i></b></center>
		</s:if>
	 
		</s:if>
		 <s:if test="mainTable=='feedback_status' && formaterOn=='TAT'">
			<table border="1" width="100%" style="border-collapse: collapse;">
					<tr bgcolor="#D8D8D8" style="height: 25px">
						<s:iterator value="dataMap" status="status">
							<s:if test="key=='Status' || key=='Level'">
					 			<td width="25%"><b><s:property value="%{key}"/>:</b></td>
								<td width="25%"><s:property value="%{value}"/></td>
						 	</s:if>
					 	</s:iterator>
					</tr>
					 	
					<tr style="height: 25px" >
						<s:iterator value="dataMap" status="status">
							<s:if test="key=='Open Date & Time'">
						 		<td width="25%"  ><b><s:property value="%{key}"/>:</b></td>
								<td width="25%" colspan="3"><s:property value="%{value}"/></td>
						 	</s:if>
					  	</s:iterator>
					</tr>
					 	
					 
					<tr bgcolor="#D8D8D8" style="height: 25px">
						<s:iterator value="dataMap" status="status">
							<s:if test="key=='Addressing Date & Time' || key=='Resolution Date & Time'">
						 		<td width="25%"><b><s:property value="%{key}"/>:</b></td>
								<td width="25%"><s:property value="%{value}"/></td>
						 	</s:if>
					  </s:iterator>
					</tr>
					 	
					 	
					 <tr style="height: 25px">
						<s:iterator value="dataMap" status="status">
							<s:if test="key=='L-2 Escalation Date & Time' ||key=='L-2 Escaltion To'">
						 		<td width="25%"><b><s:property value="%{key}"/>:</b></td>
								<td width="25%"><s:property value="%{value}"/></td>
						 	</s:if>
					  </s:iterator>
					</tr>
					 	
					<tr bgcolor="#D8D8D8" style="height: 25px">
						<s:iterator value="dataMap" status="status">
							<s:if test="key=='L-3 Escalation Date & Time' || key=='L-3 Escaltion To'">
						 		<td width="25%"><b><s:property value="%{key}"/>:</b></td>
								<td width="25%"><s:property value="%{value}"/></td>
							</s:if>
					 
						</s:iterator>
					</tr>
					
			</table>
		</s:if>
		 <s:if test="formaterOn=='smsCode'">
			 <table border="1" width="100%" style="border-collapse: collapse;">
				<s:iterator value="dataMap" status="status">
					<s:if test="#status.odd">
						<tr bgcolor="#D8D8D8" style="height: 25px">
							<td width="10%"><s:property value="%{key}"/>:</td>
							<td width="10%"><s:property value="%{value}"/></td>
						</tr>
					</s:if>
					<s:elseif test="#status.even">
						<tr style="height: 23px">
							<td width="10%"><s:property value="%{key}"/>:</td>
							<td width="10%"><s:property value="%{value}"/></td>
						</tr>
					</s:elseif>
				</s:iterator>
			</table>
			</s:if>
			
			
			<s:if test="formaterOn=='smsResend'">
			 	 <table border="1" width="100%" style="border-collapse: collapse;">
				 	 		<tr bgcolor="#D8D8D8" style="height: 25px">
								<td width="100%"><b><font color="black" size="2">Are You Sure You Want To Resend SMS?</font></b></td>
								
					  			<td width="50%"><sj:a  button="true" cssClass="button"   title="Yes"   onClick=" ResendSMS();" >Yes</sj:a><sj:a  button="true" cssClass="button"   title="No"   onClick="CancelResendSMS(); " >No</sj:a></td>
			     	     
					 		</tr>
				 	 
			</table>
			</s:if>
		 <s:if test="mainTable=='feedback_status' && formaterOn=='TAT'">
			<table border="1" width="100%" style="border-collapse: collapse;">
				<s:iterator value="dataMap" status="status">
					<s:if test="#status.odd">
						<tr bgcolor="#D8D8D8" style="height: 25px">
							<td width="10%"><s:property value="%{key}"/>:</td>
							<td width="10%"><s:property value="%{value}"/></td>
						</tr>
					</s:if>
					<s:elseif test="#status.even">
						<tr style="height: 23px">
							<td width="10%"><s:property value="%{key}"/>:</td>
							<td width="10%"><s:property value="%{value}"/></td>
						</tr>
					</s:elseif>
				</s:iterator>
			</table>
		</s:if>
		 
		<s:if test="mainTable=='feedback_status' && formaterOn=='closeMode'">
		<s:form name="editData" id="editData" action="editSMSModeData" theme="css_xhtml"  method="post" enctype="multipart/form-data">
			<s:hidden name="complianId" value="%{complianId}"/>
			
			<table border="1" width="100%" style="border-collapse: collapse;">
	   
				<tr bgcolor="#D8D8D8" style="height: 25px">
					<s:iterator value="dataMap">
						<s:if test="key=='From Department' || key=='Feedback By' || key=='Mobile No.'">
							<td width="10%"><b><s:property value="%{key}"/>:<b></td>
							<td width="10%"><s:property value="%{value}"/></td>
						</s:if>
					</s:iterator>
				</tr>
				
				<tr  style="height: 25px">
					<s:iterator value="dataMap">
						<s:if test="key=='To Department' || key=='Allotted To' || key=='Mobile No'">
							<td width="10%"><b><s:property value="%{key}"/>: <b></td>
							<td width="10%"><s:property value="%{value}"/></td>
						</s:if>
					</s:iterator>
				</tr>
				
				<tr bgcolor="#D8D8D8" style="height: 23px">
					<s:iterator value="dataMap">
						<s:if test="key=='Category' || key=='Sub-Category' || key=='Brief'">
							<td width="10%"><b><s:property value="%{key}"/>: <b></td>
							<td width="10%"><s:property value="%{value}"/></td>
						</s:if>
					</s:iterator>
				</tr>
				
				<tr style="height: 25px">
					<s:iterator value="dataMap">
						<s:if test="key == 'Feedback Type' || key=='Current Status' || key == 'Current Level'">
							<td width="10%"><b><s:property value="%{key}"/>: <b></td>
							<td width="10%"><s:property value="%{value}"/></td>
						</s:if>
					</s:iterator>
				</tr>
				
				<tr bgcolor="#D8D8D8" style="height: 25px">
					<s:iterator value="dataMap">
						<s:if test="key == 'Resolved By' || key=='Resolved On' || key=='Resolve Duration'">
							<td width="10%"><b><s:property value="%{key}"/>: <b></td>
							<td width="10%"><s:property value="%{value}"/></td>
						</s:if>
					</s:iterator>
				</tr>
				
				 
		</table>
			
			<s:iterator value="editableDataMap" status="status" begin="0" end="0">
			  <div class="newColumn">
			       <div class="leftColumn"><s:property value="%{key}"/>:</div>
			            <div class="rightColumn">
				           <s:textfield name="resolutionDetail"  placeholder="Enter Data" value="%{value}" cssClass="textField" />
			            </div>
	            </div>
			</s:iterator>
		
		   <div class="clear"></div>
		   <div class="fields" align="center">
		   <center><img id="indicator1" src="<s:url value="/images/ajax-loader_small.gif"/>" alt="Loading..." style="display:none"/></center>
		   <ul><li class="submit">
		   <div class="type-button">
		   <div id="bt" style="display: block;">
		   <sj:submit 
			           targets="target1" 
			           clearForm="true"
			           value="  Save  " 
			           effect="highlight"
			           effectOptions="{ color : '#222222'}"
			           effectDuration="5000"
			           button="true"
			           cssStyle="margin-left: -30px;"
					   />
		   </div>
		   </div>
		   </li>
		   </ul>
		   </div>
		   <div id="target1"></div>
		
		</s:form>
		</s:if>
	</center>
</body>
</html>