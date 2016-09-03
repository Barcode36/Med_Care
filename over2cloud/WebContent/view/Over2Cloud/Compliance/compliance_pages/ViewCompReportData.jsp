<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<s:url id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}" />
<link type="text/css" href="<s:url value="/css/style.css"/>" rel="stylesheet" />
<script type="text/javascript" src="<s:url value="/js/compliance/compliance.js"/>"></script>
<SCRIPT type="text/javascript">
function downloadDoc(cellvalue, options, rowObject) 
{
	var context_path = '<%=request.getContextPath()%>';
	if(rowObject.document_config_1=='NA' && rowObject.document_config_2=='NA' && rowObject.document_takeaction=='NA' && rowObject.document_action_2=='NA' && rowObject.document_action_1=='NA')
	{
		return "<img src='"+ context_path +"/images/noDoc.jpg' alt='No Record'/>";
	}
	else
	{
		return "<a href='#' title='Download' onClick='complianceDownloadAction("+rowObject.id+")'><img src='"+ context_path +"/images/docDownlaod.jpg' alt='Download'></a>";
	}
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
function complianceDownloadAction(compReportId) 
{
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/Compliance/compliance_pages/docDownloadAction.action?compReportId="+compReportId,
	    success : function(data) 
	    {
			$("#downloadLoc").html(data);
		},
	    error: function()
	    {
	        alert("error");
	    }
	 });
}
function downloadReport()
{
	var sel_id;
	sel_id = $("#gridCompReport").jqGrid('getGridParam','selarrrow');
	if(sel_id=="")
	{
	     alert("Please select atleast one check box...");        
	     return false;
	}
	else
	{
		$("#takeActionGrid").dialog({title: 'Check Column',width: 350,height: 600});
		$("#takeActionGrid").dialog('open');
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/Compliance/compliance_pages/sendReportMailConfirmAction.action?selectedId="+sel_id,
		    success : function(data) 
		    {
	 			$("#takeActionGrid").html(data);
			},
		   error: function() {
		        alert("error");
		    }
		 });
	}	
}
$.subscribe('getPrintData', function(event,data) 
		{
	var  id = $("#gridCompReport").jqGrid('getGridParam','selarrrow');
		if(id=="")
	    {
	      alert("Please select atleast one check box...");        
	      return false;
	    }
		else
		{
		 	var deptName = jQuery("#gridCompReport").jqGrid('getCell',id,'dept.deptName');
		 	var taskType = jQuery("#gridCompReport").jqGrid('getCell',id,'ctyp.taskType');
		 	var actionBy = jQuery("#gridCompReport").jqGrid('getCell',id,'userAC.name');
		 	var actionTakenDate = jQuery("#gridCompReport").jqGrid('getCell',id,'compReport.actionTakenDate');
			var taskName = jQuery("#gridCompReport").jqGrid('getCell',id,'ctn.taskName');
			var frequency = jQuery("#gridCompReport").jqGrid('getCell',id,'comp.frequency');
			var dueDate = jQuery("#gridCompReport").jqGrid('getCell',id,'compReport.dueDate');
			var dueTime = jQuery("#gridCompReport").jqGrid('getCell',id,'compReport.dueTime');
			var reminderFor = jQuery("#gridCompReport").jqGrid('getCell',id,'comp.reminder_for');
			var status = jQuery("#gridCompReport").jqGrid('getCell',id,'compReport.actionTaken');
			$("#printSelect").load("<%=request.getContextPath()%>/view/Over2Cloud/Compliance/compliance_pages/printCompInfo?deptName="+deptName.split(" ").join("%20")+"&taskName="+taskName.split(" ").join("%20")+"&frequency="+frequency.split(" ").join("%20")+"&dueDate="+dueDate.split(" ").join("%20")+"&reminderFor="+reminderFor.split(" ").join("%20")+"&status="+status.split(" ").join("%20")+"&taskType="+taskType.split(" ").join("%20")+"&actionBy="+actionBy.split(" ").join("%20")+"&actionTakenDate="+actionTakenDate.split(" ").join("%20")+"&dueTime="+dueTime.split(" ").join("%20"));
			$("#printSelect").dialog('open');
		}
});

function openDialog()
{
	var sel_id;
	sel_id = $("#gridCompReport").jqGrid('getGridParam','selarrrow');
	
	if(sel_id=="")
	{
	     alert("Please select atleast one check box...");        
	     return false;
	}
	else
	{
		$("#takeActionGrid").dialog({title: 'Send Mail ',width: 950,height: 250});
		$("#takeActionGrid").dialog('open');
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/Compliance/compliance_pages/beforeSendMail.action?selectedId="+sel_id+"&actionName=sendReportMailConfirmAction",
		    success : function(data) 
		    {
	 			$("#takeActionGrid").html(data);
			},
		   error: function() {
		        alert("error");
		    }
		 });
	}
}
function searchRow()
{
	 $("#gridCompReport").jqGrid('searchGrid', {sopt:['eq','cn']} );
}
function reloadRow()
{
	var grid = $("#gridCompReport");
	grid.trigger("reloadGrid",[{current:true}]);
	getSearchData();
}
function getData(valueFor)
{
	var fromDate=$("#fromDate").val();
	var toDate=$("#toDate").val();
	var actionStatus;
	var periodSort;
	var deptId;
	var freque;
	if(valueFor=='Refresh')
	{
		 actionStatus="All";
		 periodSort="All";
		 deptId="All";
		 freque ="All";
	}
	else
	{
		 actionStatus=$("#actionStatus").val();
		 periodSort=$("#periodSort").val();
		 deptId=$("#deptId").val();
		 freque =$("#frequency").val();
	}
	$("#complReportTarget").html("<br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
 		type :"post",
 		url :"/over2cloud/view/Over2Cloud/Compliance/compliance_pages/getOnloadData.action?fromDate="+fromDate+"&toDate="+toDate+"&actionStatus="+actionStatus+"&searchPeriodOn="+periodSort+"&deptId="+deptId+"&frequency="+freque,
 		success : function(data) 
	    {
			$("#reportData").html(data);
		},
	    error: function()
	    {
	        alert("error");
	    }
 	});
}
function getSearchData()
{
	var fromDate=$("#fromDate").val();
	var toDate=$("#toDate").val();
	var actionStatus=$("#actionStatus").val();
	var periodSort=$("#periodSort").val();
	var deptId=$("#deptId").val();
	var freque =$("#frequency").val();
	$("#complReportTarget").html("<br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
 		type :"post",
 		url :"/over2cloud/view/Over2Cloud/Compliance/compliance_pages/getSearchData.action?fromDate="+fromDate+"&toDate="+toDate+"&actionStatus="+actionStatus+"&searchPeriodOn="+periodSort+"&deptId="+deptId+"&frequency="+freque,
 		success : function(data) 
	    {
			$("#reportData").html(data);
		},
	    error: function()
	    {
	        alert("error");
	    }
 	});
	
}
function ViewCompletionTip(cellvalue, options, rowObject) 
{
	var context_path = '<%=request.getContextPath()%>';
	if(cellvalue=="NA")
	{
		return "<a href='#' title='Download' onClick='CompletionTip("+rowObject.id+")'>No Data</a>";
	}
	else
	{
		return "<a href='#' title='Download' onClick='CompletionTip("+rowObject.id+")'>View</a>";
	}
}

function CompletionTip(taskId) 
{
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/Compliance/compl_task_page/ViewCompletionTipKRAction.action?dataId="+taskId+"&dataFor=CompletionTip&module=Report",
	    success : function(data) 
	    {
			$("#takeActionGrid").dialog({title: 'Completion Tip',width: 300,height: 200});
			$("#takeActionGrid").dialog('open');
			$("#takeActionGrid").html(data);
		},
	    error: function()
	    {
	        alert("error");
	    }
	 });
}

getData('Normal');
</SCRIPT>
</head>
<body>
<sj:dialog id="printSelect" title="Print Ticket" autoOpen="false"  modal="true" height="320" width="700" showEffect="drop"></sj:dialog>
<sj:dialog
          id="takeActionGrid"
          showEffect="slide"
          hideEffect="explode"
          autoOpen="false"
          title="Take Action on Operation Task"
          modal="true"
          closeTopics="closeEffectDialog"
          width="1000"
          height="400"
          >
</sj:dialog>
<center>
</center>
<div class="middle-content">
<div class="list-icon">
	 <div class="head">
	 Operation Task</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div>
	 <div class="head">Report</div> 
</div>
<div class="clear"></div>
<center><div id="downloadLoc"></div></center>
<div class="listviewBorder" style="margin-top: 10px;width: 97%;margin-left: 20px;" align="center">
<div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
	<div class="pwie">
		<table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%">
		<tbody><tr></tr><tr><td></td></tr><tr><td> 
			<table class="floatL" border="0" cellpadding="0" cellspacing="0">
		<tbody>
		<tr>
		<td class="pL10"> 
			 <sj:a id="searchButton"  title="Search" buttonIcon="ui-icon-search" cssStyle="height:25px; width:32px"  cssClass="button" button="true"  onclick="searchRow()"></sj:a>
			 <sj:a id="refButton"  title="Refresh" buttonIcon="ui-icon-refresh" cssStyle="height:25px; width:32px"  cssClass="button" button="true"  onclick="getData('Refresh');"></sj:a>
		     
		     <sj:datepicker cssStyle="height: 16px; width: 60px;" cssClass="button" id="fromDate" name="fromDate" size="20" maxDate="0" onchange="resetToDate(this.value)" value="%{fromDate}" readonly="true"   yearRange="2013:2050" showOn="focus" displayFormat="dd-mm-yy" Placeholder="Select From Date"/>
		     <sj:datepicker cssStyle="height: 16px; width: 60px;" cssClass="button" id="toDate" name="toDate" size="20" value="%{toDate}" minDate="%{minCount}"  readonly="true" yearRange="2013:2050" showOn="focus" displayFormat="dd-mm-yy" Placeholder="Select To Date"/>
		     <s:select  
	    		id					=		"periodSort"
	    		name				=		"searchPeriodOn"
	    		list				=		"#{'All':'Both Date','actionTakenDate':'Action Taken Date','dueDate':'Due Date'}"
	      		theme				=		"simple"
	      		cssClass			=		"button"
	      		cssStyle			=		"height: 28px;margin-top: 3px;margin-left: 3px;width: 110px;"
	      	 >
	      	 </s:select>
		     <s:select  
		    	id					=		"deptId"
		    	name				=		"deptId"
		    	list				=		"deptName"
      		    theme  				=		"simple"
      		    cssClass			=		"button"
      		    cssStyle			=		"height: 28px;width: 110px;"
      		 >
     	     </s:select>
     	    </td>
		
     	    <td class="pL10"> 
     	    <s:select  
	    	id					=		"actionStatus"
	    	name				=		"actionStatus"
	    	list				=		"#{'All':'All Status','Done':'Done','Pending':'Pending','Snooze':'Snooze','Recurring':'Recurring','Reschedule':'Reschedule'}"
	      	theme				=		"simple"
	      	cssClass			=		"button"
	      	cssStyle			=		"height: 28px;margin-top: 3px;margin-left: -7px;width: 110px;"
	       >
	      </s:select>
	     	</td> 
	      
	      <s:select  
	    	id					=		"frequency"
	    	name				=		"frequency"
	    	list				=		"#{'All':'All Frequency','OT':'One-Time','D':'Daily','W':'Weekly','M':'Monthly','BM':'Bi-Monthly','Q':'Quaterly','HY':'Half Yearly','Y':'Yearly'}"
	      	theme				=		"simple"
	      	cssClass			=		"button"
	      	cssStyle			=		"height: 28px;margin-top: 3px;margin-left: 3px;width: 110px;"
	       >
	      </s:select>
		   <sj:a  button="true" cssClass="button" cssStyle="height: 27px;margin-left: 4px;" onclick="getSearchData('fromDate','toDate','deptId','actionStatus','periodSort','frequency');">OK</sj:a>
		</tr>
		</tbody>
		</table>
		<td class="alignright printTitle">
			<sj:a id="sendButton111Download" cssStyle="height:25px; width:32px"  buttonIcon="ui-icon-arrowthickstop-1-s" cssClass="button" title="Download" button="true"  onclick="downloadReport();"></sj:a>
			<sj:a  button="true" cssStyle="height:25px; width:32px"  cssClass="button" buttonIcon="ui-icon-print" title="Print" onClickTopics="getPrintData"></sj:a>
		    <sj:a id="sendMailButton"  cssClass="button" buttonIcon="ui-icon-mail-closed" button="true" cssStyle="height:25px; width:32px" title="Send Mail" onclick="openDialog();"></sj:a>
		</td>
		</tr>

</tbody></table></div>
</div>
<div id ="reportData"></div>
<br>
<br>
</div>
</div>
</body>

</html>
