<%@taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%
int temp=0,count=0;
%>
<html>
<head>
<link type="text/css" href="<s:url value="/css/style.css"/>" rel="stylesheet" />
<script type="text/javascript" src="<s:url value="/js/compliance/compliance.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/showhide.js"/>"></script>
<meta   http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<SCRIPT type="text/javascript">
$.subscribe('complete', function(event,data)
 {   setTimeout(function(){ $("#foldeffect").fadeIn(); }, 10);
	 setTimeout(function(){ $("#foldeffect").fadeOut(); }, 4000);
	 setTimeout(function(){ $("#takeActionGrid").dialog('close'); }, 800);
	 
	 var departId=$("#departId").val();
	 var data4=$("#data4").val();
	 var frequency=$("#frequency").val();
	 var totalOrMissed=$("#totalOrMissed").val();
	 var status=$("#status").val();
 });

function showHide(statusId)
{
    if(statusId=='Snooze')
	{
    	document.getElementById("snoozeDiv").style.display="block";
    	document.getElementById("reScheduleDiv").style.display="none"; 
    	document.getElementById("documentDiv").style.display="none"; 
	}
    else if(statusId=='Reschedule')
    {
    	document.getElementById("reScheduleDiv").style.display="block"; 
    	document.getElementById("snoozeDiv").style.display="none";
    	document.getElementById("documentDiv").style.display="none"; 

    }
    else
    {
    	document.getElementById("reScheduleDiv").style.display="none"; 
    	document.getElementById("snoozeDiv").style.display="none";
    	document.getElementById("documentDiv").style.display="block"; 
    }
}
</SCRIPT>
<title>Operation Task</title>
</head>
<body>
    <s:form id="takeActionForm" action="takeActionOnCompl" method="post"  name="takeActionForm" enctype="multipart/form-data" theme="css_xhtml">
    <center><div style="float:center; border-color: black; background-color: rgb(255,99,71); color: white;width: 50%; height: 10%; font-size: small; border: 0px solid red; border-radius: 6px;"><div id="errZone" style="float:center; margin-left: 7px"></div></div></center>
    <s:hidden name="complID" id="complID"></s:hidden>
    <s:hidden id="departId" value="%{departId}" />
	<s:hidden id="data4" value="%{data4}" />
	<s:hidden id="frequency" value="%{frequency}" />
	<s:hidden id="totalOrMissed" value="%{totalOrMissed}" />
	<s:hidden id="status" value="%{status}" />
	<s:hidden id="budgetary" name="budgetary" value="%{budgetary}" />
	<center>
	<table border="1" width="50%" align="center">
    <s:iterator value="dataMap" status="status">
    <tr>
     	<%if(temp%2==0){ %>
     	<td><s:property value="%{key}"/>:</td>
     	<td><label id="%{key}"><s:property value="%{value}"/></label></td>
		<%} else {%>
		<td><s:property value="%{key}"/>:</td>
     	<td><label id="%{key}"><s:property value="%{value}"/></label></td>
		<%}
     	if(temp==2)
     	{%>
     		<s:hidden name="dueDate" value="%{value}"/>
     	<%}
     	else if(temp==3)
     	{%>
     		<s:hidden name="dueTime" value="%{value}"/>
     	<%}
			temp++;
		%>
	</tr>
     </s:iterator>
     
    <s:hidden id="nextDueDate" value="%{nextDueDate}" />
     <tr>
     <td>Action Status:</td> 
     <td>
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
     </td> 
 	  </tr>
 	  </table>  
 	    <div id="documentDiv" style="display:none">
 	    <table border="1" width="50%" align="center">
 	    	<tr>
 	    		<td>Document 1:</td>
 	    		<td><s:file name="takeActionDoc" id="takeActionDoc"  cssClass="textField"/></td>
 	    	</tr>
 	    	<tr>
 	    		<td>Document 2:</td>
 	    		<td><s:file name="takeActionDoc1" id="takeActionDoc1"  cssClass="textField"/></td>
 	    	</tr>
 	    	<tr>
 	    		<td>Document 3:</td>
 	    		<td><s:file name="takeActionDoc2" id="takeActionDoc2"  cssClass="textField"/></td>
 	    	</tr>
 	    	<tr>
 	    		<td>Completion Tip:</td>
 	    		<td><s:checkboxlist list="checkListMap" name="checkid"/></td>
 	    	</tr>
 	    	<tr>
 	    		<td>Actual Exp:</td>
 	    		<td><s:textfield name="actualExpenses" id="actualExpenses" placeholder="Enter Expenses"  cssClass="textField"/></td>
 	    	</tr>
 	    </table>
 	    </div>
 	    
 	    
 	    
 	    
 		<div id="snoozeDiv" style="display:none">
 		<table border="1" width="50%" align="center">
 	    	<tr>
 	    		<td>Snooze Date:</td>
 	    		<td><sj:datepicker id="snooze_date" name="snoozeDate" cssClass="textField"size="20" value="today" readonly="true" minDate="1"  yearRange="2013:2050" showOn="focus" displayFormat="dd-mm-yy" Placeholder="Select Snooze Date"/></td>
 	    	</tr>
 	    	<tr>
 	    		<td>Snooze Time:</td>
 	    		<td><sj:datepicker id="snooze_time" name="snoozeTime" placeholder="Enter Snooze Time" showOn="focus" readonly="true" timepicker="true" timepickerOnly="true" timepickerGridHour="4" timepickerGridMinute="10" timepickerStepMinute="10" cssClass="textField"/></td>
 	    	</tr>
 	    </table>
		</div>	
		
		
		<div id="reScheduleDiv" style="display:none">	
		<table border="1" width="50%" align="center">
 	    	<tr>
 	    		<td>Reschedule Date:</td>
 	    		<td><sj:datepicker id="reschuedule_date" name="reschuedule_date" cssClass="textField" size="20" readonly="true" value="today" minDate="0" changeMonth="true"  yearRange="2013:2050" showOn="focus" displayFormat="dd-mm-yy" Placeholder="Select Due Date"/></td>
 	    	</tr>
 	    	<tr>
 	    		<td>Reschedule Time:</td>
 	    		<td><sj:datepicker id="reschuedule_time" name="rescheduleTime" placeholder="Enter Due Time" readonly="true" showOn="focus" timepicker="true" timepickerOnly="true" timepickerGridHour="4" timepickerGridMinute="10" timepickerStepMinute="10" cssClass="textField"/></td>
 	    	</tr>
 	    </table>
	   </div>
	   <div class="clear"></div>
	   <div class="clear"></div>
	   </center>
	   <center>
             <div class="type-button">
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
              </div> 
   </center>
     <sj:div id="foldeffect"  effect="fold">
            <div id="Result"></div>
     </sj:div>
  </s:form>
</body>
</html>