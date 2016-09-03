<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<link type="text/css" href="<s:url value="/css/style.css"/>" rel="stylesheet" />
<script type="text/javascript" src="<s:url value="/js/compliance/compliance.js"/>"></script>
<meta   http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<SCRIPT type="text/javascript">

 function getCompAllotedEmp(deptId)
 {
	 $.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/Compliance/compliance_pages/getEmpData.action?deptId="+deptId,
		    success : function(data) 
		    {
				$("#EmpDiv1").html(data);
			},
		    error: function()
		    {
		        alert("error");
		    }
		 });
 }

 function resetToDate(selectedDate)
 {
 	$.ajax({
 		type :"post",
 		url :"/over2cloud/view/Over2Cloud/Compliance/compliance_pages/getTotalDays.action?selectedDate="+selectedDate,
 		success : function(countDays)
 		{
 			$( "#toDate" ).datepicker( "option", "minDate", countDays);
 	    },
 	    error : function () {
 			alert("Somthing is wrong to get Sub Department");
 		}
 	});
 }
function getData()
{
	var fromDate=$("#fromDate").val();
	var toDate=$("#toDate").val();
	var actionStatus=$("#actionStatus").val();
	var periodSort=$("#periodSort").val();
	var deptId=$("#deptId").val();
	$("#complReportTarget").html("<br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
 		type :"post",
 		url :"/over2cloud/view/Over2Cloud/Compliance/compliance_pages/getOnloadDataAction.action?fromDate="+fromDate+"&toDate="+toDate+"&actionStatus="+actionStatus+"&periodSort="+periodSort+"&deptId="+deptId,
 		success : function(data) 
	    {
			$("#complReportTarget").html(data);
		},
	    error: function()
	    {
	        alert("error");
	    }
 	});
}
getData();
</SCRIPT>
</head>
<body>
<div class="clear"></div>
<div class="middle-content">
<div class="list-icon">
	 <div class="head">
	 Operation Task</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div>
	 <div class="head">Report</div> 
</div>
<div class="clear"></div>
<div style="width: 96%; margin: 1%; border: 1px solid #e4e4e5; -webkit-border-radius: 6px; -moz-border-radius: 6px; border-radius: 6px; -webkit-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); -moz-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); box-shadow: 0 1px 4px rgba(0, 0, 0, .10); padding: 1%;">

    <s:form id="complReportData" name="complReportData" action="complReportDataAction"  theme="simple"  method="post" enctype="multipart/form-data" >
	   <center>
	  		<div style="float:center; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
				<div id="errZone" style="float:center; margin-left: 7px"></div>
	  		</div>
	  </center> 
	  
      <div class="newColumn">
      <div class="leftColumn">From Date:</div>
      <div class="rightColumn">
      <span class="needed"></span>
      <sj:datepicker id="fromDate" name="fromDate" cssClass="textField" size="20" maxDate="0" onchange="resetToDate(this.value)" value="%{fromDate}" readonly="true"   yearRange="2013:2050" showOn="focus" displayFormat="dd-mm-yy" Placeholder="Select From Date"/>
      </div>
      </div>
      <div class="newColumn">
      <div class="leftColumn">To Date:</div>
      <div class="rightColumn">
      <span class="needed"></span>
      <sj:datepicker id="toDate" name="toDate" cssClass="textField" size="20" value="%{toDate}" minDate="%{minCount}" maxDate="0" readonly="true" yearRange="2013:2050" showOn="focus" displayFormat="dd-mm-yy" Placeholder="Select To Date"/>
      </div>
      </div>
	  <s:if test="highLevel">
	  <div class="newColumn">
      <div class="leftColumn">Department Name:</div>
      <div class="rightColumn">
      <span class="needed"></span>
      <s:select  
    	id					=		"deptId"
    	name				=		"deptId"
    	list				=		"deptName"
    	cssClass			=		"select"
		cssStyle			=		"width:82%"
       >
      </s:select>
      </div>
      </div>
	  </s:if>
	  
      <div class="newColumn">
      <div class="leftColumn">Action Status:</div>
      <div class="rightColumn">
      <s:if test="%{mandatory}"><span class="needed"></span></s:if>
      <s:select  
    	id					=		"actionStatus"
    	name				=		"actionStatus"
    	list				=		"#{'All':'All Status','Done':'Done','Pending':'Pending','Snooze':'Snooze','Recurring':'Recurring','Reschedule':'Reschedule'}"
    	cssClass			=		"select"
		cssStyle			=		"width:82%"
       >
      </s:select>
      </div>
      </div>
      
      <div class="newColumn">
      <div class="leftColumn">Period Sorted By:</div>
      <div class="rightColumn">
      <span class="needed"></span>
      <s:select  
    	id					=		"periodSort"
    	name				=		"searchPeriodOn"
    	list				=		"#{'All':'All Period','actionTakenDate':'Action Taken Date','dueDate':'Due Date'}"
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
	                        		targets			 =	"complReportTarget" 
	                        		clearForm		 =	"false"
	                        		value			 =	" Get Data " 
	                        		button			 =	"true"
	                        		cssClass		 =	"submit"
	                        		indicator		 =	"indicator2"
	                        		onBeforeTopics	 =	"validateTaskType"	
	                        		indicator		 =	"indicator2"
	                  />
	         		</div>
	    	 </ul>
   		</div>
   		</center>
   </s:form>
   </div>
    <div id="complReportTarget"></div>
</div>
  
</body>
</html>