<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<link type="text/css" href="<s:url value="/css/style.css"/>" rel="stylesheet" />
<script type="text/javascript" src="<s:url value="/js/compliance/compliance.js"/>"></script>
<meta   http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<SCRIPT type="text/javascript">
function KR(taskId) 
{
	var taskId=$("#taskName12").val();
	
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/Compliance/compl_task_page/ViewCompletionTipKRAction.action?dataId="+taskId+"&dataFor=KRName",
	    success : function(data) 
	    {
		$("#openKRDialog").dialog({title: 'KR Name',width: 300,height: 200});
		$("#openKRDialog").dialog('open');
		$("#openKRDialog").html(data);
		},
	    error: function()
	    {
	        alert("error");
	    }
	 });
}
$.subscribe('complete', function(event,data)
 {   setTimeout(function(){ $("#foldeffect").fadeIn(); }, 10);
	 setTimeout(function(){ $("#foldeffect").fadeOut(); }, 4000);
	 setTimeout(function(){ $("#takeActionGrid").dialog('close'); }, 800);
	 
	 /* var departId=$("#departId").val();
	 var data4=$("#data4").val();
	 var frequency=$("#frequency").val();
	 var totalOrMissed=$("#totalOrMissed").val();
	 var status=$("#status").val(); */
	 
	/*  if(data4=='complianceStatus')
	 {
	 	$("#complianceDialog").dialog('close');
	 	showAllComplDetails(departId,status);
	 }
	 else if(data4=='mgmtDashboard')
	 {
	 	$("#complianceDialog").dialog('close');
	 	showComplDetails(departId,frequency,totalOrMissed);
	 }
	 else if(data4=='ageingDashboard')
	 {
	 	$("#complianceDialog").dialog('close');
	 	showAgeingComplDetails(departId,frequency,totalOrMissed);
	 } */
	 /* else
	 {
	 	$("#takeActionGrid").dialog('close');
	 	setTimeout(function(){ $("#takeActionGrid").dialog('close'); });
	 	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
			$.ajax({
			    type : "post",
			    url : "view/Over2Cloud/Compliance/compliance_pages/logedUserComplDashboard.action",
			    success : function(subdeptdata) {
		       $("#"+"data_part").html(subdeptdata);
			},
			   error: function() {
		            alert("error");
		        }
			 });
	 } */
 });
function addInKR()
{
	
	$('#openKRDialog').dialog('open');
	$('#openKRDialog').dialog({height: '450',width:'1250',position:'center'});
	$("#krview").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/KRLibraryOver2Cloud/beforeKRUpload.action?dataFrom=Other",
	    success : function(data) 
	    {
			$("#"+"krview").html(data);
	    },
	    error: function() 
	    {
            alert("error");
        }
	 });
}
function showHide(statusId)
{
	
    if(statusId=='Snooze')
	{
    	//document.getElementById("snoozeDiv").style.display="block";
    	$("#snoozeDiv1").show();
    	document.getElementById("reScheduleDiv").style.display="none"; 
    	document.getElementById("documentDiv").style.display="none"; 
	}
    else if(statusId=='Reschedule')
    {
    	document.getElementById("reScheduleDiv").style.display="block"; 
    	document.getElementById("snoozeDiv1").style.display="none";
    	document.getElementById("documentDiv").style.display="none"; 

    }
    else
    {
    	document.getElementById("reScheduleDiv").style.display="none"; 
    	document.getElementById("snoozeDiv1").style.display="none";
    	document.getElementById("documentDiv").style.display="block"; 
    }
}
function fromKRAdd()
{
	$("#result").load("<%=request.getContextPath()%>/view/Over2Cloud/DAROver2Cloud/beforeFromKR.action?dataFor=attach");
}
</SCRIPT>
<title>Operation Task</title>
</head>
<body>
<sj:dialog
          id="openKRDialog"
          showEffect="slide" 
    	  hideEffect="explode" 
    	  openTopics="openEffectDialog"
    	  closeTopics="closeEffectDialog"
          autoOpen="false"
          title="KR Upload"
          modal="true"
          width="1200"
          height="450"
          draggable="true"
    	  resizable="true"
          >
          <div id="krview"></div>
</sj:dialog>
    <s:form id="takeActionForm" action="takeActionOnCompl" method="post"  name="takeActionForm" enctype="multipart/form-data" theme="simple">
    <center><div style="float:center; border-color: black; background-color: rgb(255,99,71); color: white;width: 50%; height: 10%; font-size: small; border: 0px solid red; border-radius: 6px;"><div id="errZone" style="float:center; margin-left: 7px"></div></div></center>
    <s:hidden name="complID" id="complID"></s:hidden>
    <s:hidden id="departId" value="%{departId}" />
	<s:hidden id="data4" value="%{data4}" />
	<s:hidden id="frequency" value="%{frequency}" />
	<s:hidden id="totalOrMissed" value="%{totalOrMissed}" />
	<s:hidden id="status" value="%{status}" />
	<s:hidden id="budgetary" name="budgetary" value="%{budgetary}" />
   
   		<table width="100%" border="1" style="border-collapse: collapse;">
   		 <tr bgcolor="#D8D8D8" style="height: 25px;">
     	 <s:iterator value="dataMap" status="status" >
			<s:if test="%{key=='Department' || key=='Task Type' || key=='Task Brief' || key=='Frequency'}">
					<td align="left" width="10%"><strong><s:property value="%{key}"/>:</strong></td>
					<td align="left" width="10%" > <s:property value="%{value}"/></td>
			</s:if>
		</s:iterator>
		</tr>
		<tr style="height: 25px;">
     	 <s:iterator value="dataMap" status="status" >
			<s:if test="%{key=='Due Date' || key=='Current Status' || key=='Current Level' || key=='Next Due'}">
					<td align="left" width="10%"><strong><s:property value="%{key}"/>:</strong></td>
					<td align="left" width="10%" > <s:property value="%{value}"/></td>
			</s:if>
		</s:iterator>
		</tr>
		
		<tr bgcolor="#D8D8D8" style="height: 25px;">
     	 <s:iterator value="dataMap" status="status" >
			<s:if test="%{key=='Reminder' || key=='Next Escalation On' || key=='Next Escalation To' || key=='Alloted To'}">
					<td align="left" width="10%"><strong><s:property value="%{key}"/>:</strong></td>
					<td align="left" width="10%" > <s:property value="%{value}"/></td>
			</s:if>
		</s:iterator>
		</tr>
	<tr style="height: 25px;">
     	 <s:iterator value="dataMap" status="status" >
			<s:if test="%{key=='Budgetary' || key=='Ownership'}">
					<td align="left" width="10%"><strong><s:property value="%{key}"/>:</strong></td>
					<td align="left" width="10%" > <s:property value="%{value}"/></td>
			</s:if>
			<s:if test="%{key=='Document 1' || key=='Document 2'}">
					<td align="left" width="10%"><strong><s:property value="%{key}"/>:</strong></td>
			 		<td align="left" width="30%" ><a href='view/Over2Cloud/Compliance/compliance_pages/DocDownload.action?dataFor=<s:property value="%{key}"/>&docName=<s:property value="%{complID}"/>'><s:property value="%{value}"/></a></td>
			</s:if>
		</s:iterator>
		</tr>
		
   		</table>
   		<br>
   			<s:if test="%{checkListMap.size>0}">
 	    			 <strong>Check List:</strong>
 	    			
 	    			  <div  style="margin-left: 96px;margin-top: -14px;">
 	    			  <s:iterator value="checkListMap" var="var" status="status">
	 	    			 <tr>
	 	    			  <s:checkbox theme="simple"  name="checkid" id="%{#status.count}" value="%{key}" fieldValue="%{key}"/><s:property value="%{value}"/> 
	 	    			  <br>
	 	    			</tr>
 	    			</s:iterator>
 	    			</div> 
 	    	</s:if>
       <br>
    
      <div class="newColumn">
       <div class="leftColumn">KR&nbsp;Name&nbsp;</div>
       <div class="rightColumn">
       <img id="krName" alt="Check List" src="images/share.jpg" height="25" width="50" onclick="KR();">
       </div>
       </div>
    
    
     <s:hidden name="dueDate" value="%{dueDate}"/>
     <s:hidden name="dueTime" value="%{dueTime}"/>
     <s:hidden id="nextDueDate" value="%{nextDueDate}" />
     <s:hidden id="taskName12" value="%{taskName}" />
	    <div class="newColumn">
        <div class="leftColumn">Action Status:</div>
		<div class="rightColumn"><span class="needed"></span>
	    <s:select 
                    cssClass="select"
					cssStyle="width:82%"
                    id="actionTaken"
                    name="actionTaken" 
                    list="takeActionStatus" 
                    headerKey="-1"
                    headerValue="Select Status"
                    onchange="showHide(this.value)"
                    >
        </s:select>
        </div>
        </div>
        <span id="complSpan" class="pIds" style="display: none; ">actionTaken#actionTaken#D#,comments#comments#T#an,</span>
        <span id="snnozeComplSpan" class="snzpIds" style="display: none; ">actionTaken#actionTaken#D#,comments#comments#T#an,snooze_date#snooze_date#date#,snooze_time#snooze_time#time#,</span>
        <span id="snnozeComplSpan" class="reschdpIds" style="display: none; ">actionTaken#actionTaken#D#,comments#comments#T#sc,reschuedule_date#reschuedule_date#date#,reschuedule_time#reschuedule_time#time#,</span>
        <div class="newColumn">
        <div class="leftColumn">Comments:</div>
        <div class="rightColumn"><span class="needed"></span><s:textfield placeholder="Enter Comments" cssClass="textcell" id="comments" name="comments" cssClass="textField" theme="simple"></s:textfield>
        </div>
        </div>
 	    
 	    
 	    <div id="documentDiv" style="display:none">
 					  <div class="newColumn">
	                  <div class="leftColumn">Document 1:</div>
	                  <div class="rightColumn">
 	    			  <s:file name="takeActionDoc" id="takeActionDoc"  cssClass="textField"/></div>
 	    			  </div>
 	    			  
 	    			  <div class="newColumn">
	                  <div class="leftColumn">Document 2:</div>
	                  <div class="rightColumn">
 	    			  <s:file name="takeActionDoc1" id="takeActionDoc1"  cssClass="textField"/></div>
 	    			  </div>
 	    			  
 	    			  <div class="newColumn">
	                  <div class="leftColumn">Document 3:</div>
	                  <div class="rightColumn">
 	    			  <s:file name="takeActionDoc2" id="takeActionDoc2"  cssClass="textField"/></div>
 	    			  </div>
 	    			  
 	    			  <div class="newColumn">
	                  <div class="leftColumn">Actual Exp:</div>
	                  <div class="rightColumn">
 	    			  		<s:textfield name="actualExpenses" id="actualExpenses" placeholder="Enter Expenses"  cssClass="textField"/></div>
 	    			  </div>
 	    			  
 	    </div>
 		<div id="snoozeDiv1" style="display:none">
					  <div class="newColumn">
	                  <div class="leftColumn">Snooze Date:</div>
	                  <div class="rightColumn"><span class="needed"></span>
	                  <sj:datepicker id="snooze_date" name="snoozeDate" cssClass="textField"size="20" value="today" readonly="true" minDate="1"  yearRange="2013:2050" showOn="focus" displayFormat="dd-mm-yy" Placeholder="Select Snooze Date"/>
	                  </div>
	                  </div>
					  <div class="newColumn">
					  <div class="leftColumn">Snooze Time:</div>
	                  <div class="rightColumn"><span class="needed"></span>
	                  <sj:datepicker id="snooze_time" name="snoozeTime" placeholder="Enter Snooze Time" showOn="focus" readonly="true" timepicker="true" timepickerOnly="true" timepickerGridHour="4" timepickerGridMinute="10" timepickerStepMinute="10" cssClass="textField"/>
					  </div>
					  </div>
					  
		</div>	
		<div id="reScheduleDiv" style="display:none">			  
					  <div class="newColumn">
	                  <div class="leftColumn">Reschedule Date:</div>
	                  <div class="rightColumn"><span class="needed"></span>
	                  <sj:datepicker id="reschuedule_date" name="reschuedule_date" cssClass="textField" size="20" readonly="true" value="today" minDate="0" changeMonth="true"  yearRange="2013:2050" showOn="focus" displayFormat="dd-mm-yy" Placeholder="Select Due Date"/>
	                  </div>
	                  </div>
					  <div class="newColumn">
					  <div class="leftColumn">Reschedule Time:</div>
	                  <div class="rightColumn"><span class="needed"></span>
	                  <sj:datepicker id="reschuedule_time" name="rescheduleTime" placeholder="Enter Due Time" readonly="true" showOn="focus" timepicker="true" timepickerOnly="true" timepickerGridHour="4" timepickerGridMinute="10" timepickerStepMinute="10" cssClass="textField"/>
					  </div>
					  </div>
	
	   </div>
	   
	     <sj:a cssStyle="margin-left: 83px;margin-top: 9px;" button="true" href="#" onclick="fromKRAdd();">From KR</sj:a>
		    <sj:a cssStyle="margin-left: 0px;margin-top: 9px;" button="true" href="#" onclick="addInKR();">To KR</sj:a>
		    <br>
	   <div class="clear"></div>
	   <div class="clear"></div>
	    <div id="result"></div>
	    <br>
	     <center>
	   <tr>
	  
	<div id="test">
	          <sj:submit 
                        targets			   	=		"Result" 
                        clearForm			=		"true"
                        value				=		"Save" 
                        effect				=		"highlight"
                        effectOptions		=		"{color:'#222222'}"
                        effectDuration		=		"5000"
						button				=		"true"
                        onCompleteTopics	=		"complete"
                        onBeforeTopics		=		"validateOnTakeAction"
                        />
                        <sj:a 
						button="true" href="#"
						onclick="resetTakeAction('takeActionForm');"
						>
						Reset
					</sj:a>
					 <sj:a 
						button="true" href="#"
						onclick="backTakeAction();"
						>
						Back
					</sj:a>
					<sj:a 
						button="true" href="#"
						onclick="historyView();"
						>
						View History
					</sj:a>
					</div>
		
		 </tr>
		  </center>
		 <br>
		 <br>
		 	 <center> <sj:div id="foldeffect"  effect="fold">
            <div id="Result"></div>
     </sj:div></center>
		
  </s:form>
  <sj:dialog
          id="takeActionGridhistory"
          showEffect="slide" 
    	  hideEffect="explode" 
    	  openTopics="openEffectDialog"
    	  closeTopics="closeEffectDialog"
          autoOpen="false"
          title="Operation Task Action"
          modal="true"
          width="1000"
          height="350"
          draggable="true"
    	  resizable="true"
          >
</sj:dialog>
</body>
<script type="text/javascript">
function backTakeAction()
{
	$("#takeActionGrid").dialog('close');
}
function resetTakeAction(formId)
{
	$('#'+formId).trigger("reset");
}
function historyView()
{
	$("#takeActionGridhistory").dialog('open');
	var id=$("#complID").val();
	$("#takeActionGridhistory").dialog({title: 'Task History  ',width: 850,height: 400});
	$("#takeActionGridhistory").load("<%=request.getContextPath()%>/view/Over2Cloud/Compliance/compliance_pages/viewFullViewFormatter?complID="+id);
	   return false;
}
</script>
</html>