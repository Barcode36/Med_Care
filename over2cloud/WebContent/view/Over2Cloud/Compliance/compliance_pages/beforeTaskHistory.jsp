<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<s:url id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<link type="text/css" href="<s:url value="/css/style.css"/>" rel="stylesheet" />
<script type="text/javascript" src="<s:url value="/js/compliance/compliance.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/helpdesk/common.js"/>"></script>
<script type="text/javascript">
function resetTaskName(formId)
{
    $('#'+formId).trigger("reset");
}
</script>
</head>
<body>
<div class="clear"></div>
<div class="middle-content">
<div class="list-icon">
	 <div class="head">
	 Operation Task</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div>
	 <div class="head">Task History</div> 
</div>
<div class="clear"></div>
<div style="width: 96%; margin: 1%; border: 1px solid #e4e4e5; -webkit-border-radius: 6px; -moz-border-radius: 6px; border-radius: 6px; -webkit-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); -moz-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); box-shadow: 0 1px 4px rgba(0, 0, 0, .10); padding: 1%;">

    <s:form id="complTaskHistory" name="complTaskHistory" action="complTaskHistoryAction"  theme="simple"  method="post" enctype="multipart/form-data" >
	   <center>
	  		<div style="float:center; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
				<div id="errZone" style="float:center; margin-left: 7px"></div>
	  		</div>
	  </center> 
      <div class="newColumn">
      <div class="leftColumn">From Date:</div>
      <div class="rightColumn">
      <sj:datepicker id="fromDate" name="fromDate" cssClass="textField" size="20"  value="%{fromDate}" readonly="true"   yearRange="2013:2050" showOn="focus" displayFormat="dd-mm-yy" Placeholder="Select From Date"/>
      </div>
      </div>
      <div class="newColumn">
      <div class="leftColumn">To Date:</div>
      <div class="rightColumn">
      <sj:datepicker id="toDate" name="toDate" cssClass="textField" size="20" value="%{toDate}"  readonly="true" yearRange="2013:2050" showOn="focus" displayFormat="dd-mm-yy" Placeholder="Select To Date"/>
      </div>
      </div>
      
      <div class="newColumn">
      <div class="leftColumn">Department:</div>
      <div class="rightColumn">
     <span class="needed"></span>
      <s:select  
    	id					=		"depName"
    	name				=		"depName"
    	headerKey			=		"-1"
        headerValue			=		"Select Department"
    	list				=		"deptName"
    	cssClass			=		"select"
		cssStyle			=		"width:82%"
		 onchange="commonSelectAjaxCall('compl_task_type','id','taskType','departName',this.value,'','','taskType','ASC','taskType','Select Task Type');"
       >
      </s:select>
      </div>
      </div>
      
      
	   <span id="historySpan" class="pIds" style="display: none; ">taskType#Task Type#D#,taskName#Task Name#D#,</span>
      <div class="newColumn">
      <div class="leftColumn">Task Type:</div>
      <div class="rightColumn">
     <span class="needed"></span>
      <s:select  
    	id					=		"taskType"
    	name				=		"taskType"
    	headerKey			=		"-1"
        headerValue			=		"Select Task Type"
    	list				=		"taskTypeMap"
    	cssClass			=		"select"
		cssStyle			=		"width:82%"
		onchange 			=		"getTaskNameDetails(this.value,'depName','taskName');"
       >
      </s:select>
      </div>
      </div>
      
      <div class="newColumn">
      <div class="leftColumn">Task Name:</div>
      <div class="rightColumn">
      <span class="needed"></span>
      <s:select  
    	id					=		"taskName"
		name				=		"taskName"
        list				=		"{'No Data'}"
        headerKey			=		"-1"
        headerValue			=		"Select Task Name" 
        cssClass			=		"select"
		cssStyle			=		"width:82%"
       >
      </s:select>
      </div>
      </div>
         
		<center>
	 	 	<img id="indicator2" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/>
		</center>
		<div class="clear"></div>
		<div class="clear"></div>
		<center>	
	 	<div class="fields">
			<ul>
		  		<li class="submit" style="background: none;">
			 		<div class="type-button">
	            		<sj:submit 
	                        		targets			 =	"complTaskTarget" 
	                        		clearForm		 =	"false"
	                        		value			 =	" View History " 
	                        		button			 =	"true"
	                        		cssClass		 =	"submit"
	                        		indicator		 =	"indicator2"
	                        		onBeforeTopics	 =	"validateTaskHistory"	
	                  />
	                    <sj:a 
                        button="true" href="#"
                        onclick="resetTaskName('complTaskHistory');resetColor('.pIds');"
                        >
                        Reset
                    </sj:a>
	                  
	         		</div>
	    	 </ul>
   		</div>
   		</center>
   </s:form>
   </div>
   <sj:dialog
          id="taskHistoryDialog"
          showEffect="slide" 
    	  hideEffect="explode" 
    	  openTopics="openEffectDialog"
    	  closeTopics="closeEffectDialog"
          autoOpen="false"
          title="Task History"
          modal="true"
          width="1128"
          height="400"
          draggable="true"
    	  resizable="true"
          >
          <div id="complTaskTarget"></div>
</sj:dialog>
    
</div>
  
</body>
</html>