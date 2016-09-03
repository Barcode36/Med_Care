<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true"  jquerytheme="mytheme" customBasepath="%{contextz}"/>
<link type="text/css" href="<s:url value="/css/style.css"/>" rel="stylesheet" />
<script type="text/javascript" src="<s:url value="/js/compliance/ComplianceEdit.js"/>"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script type="text/javascript">
$.subscribe('makeEffect', function(event,data)
		  {
			 setTimeout(function(){ $("#complTarget").fadeIn(); }, 10);
			 setTimeout(function(){ $("#complTarget").fadeOut(); }, 400);
		  });
function selectAllotTo()
{
	if($('#reminder_forremToOther').is(':checked') || $('#reminder_forremToBoth').is(':checked'))
	{
		$("#showAllotToDiv").show();
		var preSelectedId = $("#selectCont").val().split(",");
		$("#emp_id").val(preSelectedId);
	}
	else
	{
		$("#showAllotToDiv").hide();
	}
	
	var escValue =  $("#escalation").val();
	if(escValue=="Y")
	{
		var l1PreSelectedId = $("#selectEscCont1").val().split(",");
		$("#l2emp_id").val(l1PreSelectedId);
		var l2PreSelectedId = $("#selectEscCont2").val().split(",");
		$("#l3emp_id").val(l2PreSelectedId);
		var l3PreSelectedId = $("#selectEscCont3").val().split(",");
		$("#l4emp_id").val(l3PreSelectedId);
		var l4PreSelectedId = $("#selectEscCont4").val().split(",");
		$("#l5emp_id").val(l4PreSelectedId);
		
	}
	else
	{
		$("#showEscDiv").hide();
	}
}

function hideEsc()
{
	var escValue =  $("#escalation").val();
	if(escValue=="Y")
	{
		$("#showEscDiv").show();
	}
	else
	{
		$("#showEscDiv").hide();
	}
}

selectAllotTo();
</script>
</head>
<body>

<div class="clear"></div>
<div class="middle-content">
<div class="list-icon">
	 <div class="head">
	 Operation Task</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div>
	 <div class="head">Edit</div> 
</div>
<div class="clear"></div>
<div class="listviewBorder" style="margin-top: 10px;width: 97%;margin-left: 20px;" align="center">
<div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
<div class="pwie">
	<table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%">
    <tbody>
    <tr>
      <td>
      <table class="floatL" border="0" cellpadding="0" cellspacing="0">
        <tbody>
        <tr>
        <td class="pL10"> 
         </td></tr></tbody>
      </table>
      </td>
      <td class="alignright printTitle">
      <sj:a id="sendActivity"  cssClass="button" button="true" title="Activity Board"  onclick="activityBoard();">Activity Board</sj:a>
     <sj:a id="sendButton111" cssClass="button" button="true" title="Download"  onclick="configureCompliance();">Configure Task</sj:a>
      </td>
    </tr>
    </tbody>
    </table>
</div>
</div>
</div>
<div style="overflow-x:hidden; height:450px; width: 96%; margin: 1%; border: 1px solid #e4e4e5; -webkit-border-radius: 6px; -moz-border-radius: 6px; border-radius: 6px; -webkit-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); -moz-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); box-shadow: 0 1px 4px rgba(0, 0, 0, .10); padding: 1%;">

<s:form id="EditCompliance" name="EditCompliance" action="EditComplianceAction" namespace="/view/Over2Cloud/Compliance/compliance_pages" theme="simple"  method="post" enctype="multipart/form-data" >
	<s:hidden name="selectedId" value="%{selectedId}"/>
	
	<div class="newColumn">
       <div class="leftColumn">Task Type:&nbsp;</div>
       <div class="rightColumn">
       		<s:textfield value="%{taskType}" disabled="true" cssClass="textField"/>
       </div>
    </div>
    
    <div class="newColumn">
       <div class="leftColumn">Task Name:&nbsp;</div>
       <div class="rightColumn">
       		<s:textfield value="%{taskName}"  disabled="true" cssClass="textField"/>
       </div>
    </div>
    
    <div class="newColumn">
       <div class="leftColumn">Task Brief:&nbsp;</div>
       <div class="rightColumn">
       		<s:textarea value="%{taskBrief}" name="taskBrief" cssClass="textField"/>
       </div>
    </div>
    
    <div class="newColumn">
       <div class="leftColumn">Reminder Message:&nbsp;</div>
       <div class="rightColumn">
       		<s:textarea value="%{msg}" name="msg" cssClass="textField"/>
       </div>
    </div>
    
    <div class="newColumn">
       <div class="leftColumn">Due Date:&nbsp;</div>
       <div class="rightColumn">
       		<sj:datepicker id="dueDate" name="dueDate" onChangeTopics="clearAllDatePicker" readonly="true" cssClass="textField" size="20" value="%{dueDate}" changeMonth="true" changeYear="true"  yearRange="2013:2050" showOn="focus" displayFormat="dd-mm-yy" Placeholder="Select Due Date"/>
       </div>
    </div>
    
    <div class="newColumn">
       <div class="leftColumn">Due Time:&nbsp;</div>
       <div class="rightColumn">
       		<sj:datepicker id="dueTime" name="dueTime" placeholder="Enter Time" showOn="focus" value="%{dueTime}" timepicker="true" timepickerOnly="true" timepickerGridHour="4" timepickerGridMinute="10" timepickerStepMinute="10" cssClass="textField"/>
       </div>
    </div>
    <div class="newColumn">
       <div class="leftColumn">Frequency :&nbsp;</div>
       <div class="rightColumn">
       		 <s:select  
                   id				=		"frequency"
                   name				=		"frequency"
                   list				=		"frequencyMap"
                   cssClass			=		"select"
				   cssStyle			=		"width:82%"
				   onchange         =       "disableReminder(this.value),validateReminder('dueDate','frequency','reminder1')"
                      >
	   		</s:select>
       </div>
    </div>
    
    <s:hidden id="dateDiff" value="0"/>
    <s:textfield id="tset" value="%{remind3rd}"/>
     <s:textfield id="tset1" value="%{remind2nd}"/>
      <s:textfield id="tset2" value="%{remind1st}"/>
    <div id="reminderDiv">
    
    <div class="newColumn">
       <div class="leftColumn">Reminder&nbsp;3:&nbsp;</div>
       <div class="rightColumn">
       		<sj:datepicker id="reminder1" name="reminder1" onChangeTopics="clear2ndDatePicker" readonly="true" cssClass="textField" size="20" value="%{remind3rd}" changeMonth="true" changeYear="true" yearRange="2013:2050" showOn="focus" displayFormat="dd-mm-yy" Placeholder="Select Due Date"/>
       </div>
    </div>
    
    
    <div class="newColumn">
       <div class="leftColumn">Reminder&nbsp;2:&nbsp;</div>
       <div class="rightColumn">
       		<sj:datepicker id="reminder2" name="reminder2" onChangeTopics="clear3rdDatePicker" readonly="true" cssClass="textField" size="20" value="%{remind2nd}" changeMonth="true" changeYear="true" onchange="getTotalday(this.value)" yearRange="2013:2050" showOn="focus" displayFormat="dd-mm-yy" Placeholder="Select Due Date"/>
       </div>
    </div>
    
    
    <div class="newColumn">
       <div class="leftColumn">Reminder&nbsp;1:&nbsp;</div>
       <div class="rightColumn">
       		<sj:datepicker id="reminder3" name="reminder3" readonly="true" cssClass="textField" size="20" value="%{remind1st}" changeMonth="true" changeYear="true" onchange="getTotalday(this.value)" yearRange="2013:2050" showOn="focus" displayFormat="dd-mm-yy" Placeholder="Select Due Date"/>
       </div>
    </div>
    
    </div>
    
    <div class="newColumn">
       <div class="leftColumn">Mode :&nbsp;</div>
       <div class="rightColumn">
       		<s:radio list="viaFrom" name="remindMode" id="remindMode" value="%{remindMode}"/>
       </div>
    </div>
    
    <div class="newColumn">
       <div class="leftColumn">Allot&nbsp;To:&nbsp;</div>
       <div class="rightColumn">
       		<s:radio list="remindToMap" name="reminder_for" id="reminder_for" value="%{remindTo}" onclick="findHiddenDiv(this.value);"/>
       </div>
    </div>
    <s:textfield value="%{remindTo}"></s:textfield>
    <div id="showAllotToDiv">
    
    <div class="newColumn">
       <div class="leftColumn">Employee :&nbsp;</div>
       <div class="rightColumn">
       		<s:select  
                   	id			="emp_id"
                   	name		="emp_id"
                   	list		="emplMap"
                   	multiple	="true"
                   	cssClass	="select"
					cssStyle	="width:82%;height:40%"
             />
       </div>
    </div>
    <s:hidden id="selectCont" value="%{selectedRemind2Cont}"/> 
    <s:hidden id="selectEscCont1" value="%{selectedEsc1Cont}"/> 
    <s:hidden id="selectEscCont2" value="%{selectedEsc2Cont}"/> 
    <s:hidden id="selectEscCont3" value="%{selectedEsc3Cont}"/> 
    <s:hidden id="selectEscCont4" value="%{selectedEsc4Cont}"/> 
   </div>
    
    <div class="newColumn">
       <div class="leftColumn">Escalation :&nbsp;</div>
       <div class="rightColumn">
       		<s:select  
                   	id	= "escalation"
	                name= "escalation"
	                list= "escalationMap"
                    onchange= "hideEsc(this.value);dselectBelowEsc('','','','');"
                    cssClass="select"
					cssStyle="width:82%"
             />
       </div>
    </div>
    
    <div id="showEscDiv">
    
    <div class="clear"></div>
    <div class="newColumn">
       <div class="leftColumn">L2:&nbsp;</div>
       <div class="rightColumn">
       		<s:select  
                   	id			="l2emp_id"
                   	name		="escalation_level_1"
                   	list		="escContMap1"
                   	multiple	="true"
                   	cssClass	="select"
					cssStyle	="width:82%;height:40%"
					onchange	="dselectBelowEsc('l2emp_id','','','')"
             />
       </div>
    </div>
    
    <div class="newColumn">
       <div class="leftColumn">L 2 TAT:&nbsp;</div>
       <div class="rightColumn">
       		<sj:datepicker id="l2EscDuration" name="l1EscDuration" value="%{l1EscDuration}" placeholder="Enter Time" showOn="focus" timepicker="true" timepickerOnly="true" timepickerGridHour="4" timepickerGridMinute="10" timepickerStepMinute="10" cssClass="textField"/>
       </div>
    </div>
    
    <div class="clear"></div>
    <div class="newColumn">
       <div class="leftColumn">L3:&nbsp;</div>
       <div class="rightColumn">
       		<s:select  
                   	id			="l3emp_id"
                   	name		="escalation_level_2"
                   	list		="escContMap2"
                   	multiple	="true"
                   	cssClass	="select"
					cssStyle	="width:82%;height:40%"
					onchange	="dselectBelowEsc('','l3emp_id','','')"
             />
       </div>
    </div>
    
    <div class="newColumn">
       <div class="leftColumn">L 3 TAT:&nbsp;</div>
       <div class="rightColumn">
       		<sj:datepicker id="l3EscDuration" name="l2EscDuration" value="%{l2EscDuration}" placeholder="Enter Time" showOn="focus" timepicker="true" timepickerOnly="true" timepickerGridHour="4" timepickerGridMinute="10" timepickerStepMinute="10" cssClass="textField"/>
       </div>
    </div>
    
    <div class="clear"></div>
    <div class="newColumn">
       <div class="leftColumn">L4:&nbsp;</div>
       <div class="rightColumn">
       		<s:select  
                   	id			="l4emp_id"
                   	name		="escalation_level_3"
                   	list		="escContMap3"
                   	multiple	="true"
                   	cssClass	="select"
					cssStyle	="width:82%;height:40%"
					onchange	="dselectBelowEsc('','','l4emp_id','')"
             />
       </div>
    </div>
    
    <div class="newColumn">
       <div class="leftColumn">L 4 TAT:&nbsp;</div>
       <div class="rightColumn">
       		<sj:datepicker id="l4EscDuration" name="l3EscDuration" value="%{l3EscDuration}" placeholder="Enter Time" showOn="focus" timepicker="true" timepickerOnly="true" timepickerGridHour="4" timepickerGridMinute="10" timepickerStepMinute="10" cssClass="textField"/>
       </div>
    </div>
    
    <div class="clear"></div>
    <div class="newColumn">
       <div class="leftColumn">L5:&nbsp;</div>
       <div class="rightColumn">
       		<s:select  
                   	id			="l5emp_id"
                   	name		="escalation_level_4"
                   	list		="escContMap4"
                   	multiple	="true"
                   	cssClass	="select"
					cssStyle	="width:82%;height:40%"
             />
       </div>
    </div>
    
    <div class="newColumn">
       <div class="leftColumn">L 5 TAT:&nbsp;</div>
       <div class="rightColumn">
       		<sj:datepicker id="l5EscDuration" name="l4EscDuration" value="%{l4EscDuration}" placeholder="Enter Time" showOn="focus" timepicker="true" timepickerOnly="true" timepickerGridHour="4" timepickerGridMinute="10" timepickerStepMinute="10" cssClass="textField"/>
       </div>
    </div>
    
   </div>
    
     <div class="clear"></div>
         <div class="clear"></div>
           <br>
         <div class="fields">
         <center>
		 <ul>
		 <li class="submit" style="background:none;">
		 <div class="type-button">
	     <sj:submit 
         				targets			=	"complTarget" 
         				clearForm		=	"true"
         				value			=	" Save " 
         				button			=	"true"
         				cssClass		=	"submit"
                 		effect			=	"highlight"
                 		effectOptions	=	"{ color : '#222222'}"
                 		effectDuration	=	"5000"
                 		onCompleteTopics=	"makeEffect"
                 		onBeforeTopics  =   "configValidate"
                        
     		  	  />
					 <sj:a 
						button="true" href="#"
						onclick="activityBoard();"
						>
						Back
					</sj:a>
	      </div>
	      </li>
		  </ul></center>
		  <sj:div id="complTarget"  effect="fold"> </sj:div>
		  </div>
          <center>
	 	  <img id="indicator2" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/>
	      </center> 
    
</s:form>
</div>
</div>
</body>
</html>