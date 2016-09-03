<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%String userRights = session.getAttribute("userRights") == null ? "" : session.getAttribute("userRights").toString();%>
<%String validApp = session.getAttribute("validApp") == null ? "" : session.getAttribute("validApp").toString();
%>

<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script type="text/javascript" src="<s:url value="/js/helpdesk/feedback.js"/>"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<STYLE>
.textfieldbgcolr {
    background-color: #E8E8E8;
}
.ui-autocomplete-input {

width:130px;
height:26px;
margin-top:10px;
}
.ui-autocomplete { 
height: 200px; 
overflow-y: 
scroll; 
overflow-x: hidden;
}


</style>
<title>Insert title here</title>
<SCRIPT type="text/javascript">

function downloadFeedStatus()
{
 	var sel_id;
 	sel_id = $("#gridedittable").jqGrid('getGridParam','selrow');
 	//var fromDept = $("#fromDept1").val();
	var feedStatus = $("#feedStatus").val();
	var minDateValue = $("#minDateValue").val();
	var maxDateValue = $("#maxDateValue").val();
	var taskType = $("#taskType").val();
	//var lodgeMode = $("#lodgeMode").val();
	//var closeMode = $("#closeMode").val();
	var escLevel = $("#escLevel").val();
	var escTAT = $("#escTAT").val();
	//var severityLevel = $("#severityLevel").val();
	$("#takeActionGrid").dialog({title: 'Check Column',width: 350,height: 600});
	$("#takeActionGrid").dialog('open');
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Activity_Board/feedbackStatusDownload.action?fromDepart=all&feedStatus="+feedStatus+"&minDateValue="+minDateValue+"&maxDateValue="+maxDateValue+"&taskType="+taskType+"&lodgeMode=All&closeMode=All&escLevel="+escLevel+"&escTAT="+escTAT+"&severityLevel=All",
	 	
	    success : function(data) 
	    {
 			$("#takeActionGrid").html(data);
		},
	   error: function() {
	        alert("error");
	    }
	 });
}


function getOnlinePage()
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Lodge_Feedback/beforeFeedViaOnline.action?feedStatus=online&dataFor=HDM",
	    success : function(feeddraft) {
       $("#"+"data_part").html(feeddraft);
	},
	   error: function() {
            alert("error");
        }
	 });
}


function viewHistory(cellvalue, options, rowObject) 
{
	return "<a href='#' title='View Data' onClick='viewHistoryOnClick("+rowObject.id+")'><font color='blue'>"+cellvalue+"</font></a>";
}

function viewHistoryOnClick(id) 
{
	var feedbackBy = jQuery("#gridedittable").jqGrid('getCell',id,'feedby');
	var feedByDept = jQuery("#gridedittable").jqGrid('getCell',id,'bydept');
	var feedToDept = jQuery("#gridedittable").jqGrid('getCell',id,'todept');
	var feedCategory = jQuery("#gridedittable").jqGrid('getCell',id,'category');
	var Location = jQuery("#gridedittable").jqGrid('getCell',id,'location');
	$("#takeActionGrid").dialog('open');
	$("#takeActionGrid").html("<br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$("#takeActionGrid").dialog({title: 'Feedback History for location '+Location,width: 1200,height: 400});
  $.ajax
	({
		type : "post",
		url  : "view/Over2Cloud/HelpDeskOver2Cloud/Activity_Board/beforeViewActivityHistoryData?id="+id,
		success : function(data)
		{
			$("#takeActionGrid").html(data);
		},
		error : function()
		{
			alert("Error on data fetch");
		} 
	});
}

function feedByDetail(cellvalue, options, rowObject) 
{
	return "<a href='#' title='View Data' onClick='feedByDetailOnClick("+rowObject.id+")'><font color='blue'>"+cellvalue+"</font></a>";
}

function feedByDetailOnClick(complainId) 
{
	$.ajax
	({
		type : "post",
		url  : "view/Over2Cloud/HelpDeskOver2Cloud/Activity_Board/getComplaintActivityDeatil.action?complianId="+complainId+"&formaterOn=feed_by&mainTable=employee_basic",
		success : function(data)
		{
			$("#takeActionGrid").dialog({title: 'Feedback By Detail',width: 300,height: 200});
			$("#takeActionGrid").dialog('open');
			$("#takeActionGrid").html(data);
		},
		error : function()
		{
			alert("Error on data fetch");
		} 
	});
}

function allotToDetail(cellvalue, options, rowObject) 
{
	return "<a href='#' title='View Data' onClick='allotToDetailOnClick("+rowObject.id+")'><font color='blue'>"+cellvalue+"</font></a>";
}

function allotToDetailOnClick(complainId) 
{
	$.ajax
	({
		type : "post",
		url  : "view/Over2Cloud/HelpDeskOver2Cloud/Activity_Board/getComplaintActivityDeatil.action?complianId="+complainId+"&formaterOn=allot_to&mainTable=employee_basic",
		success : function(data)
		{
			$("#takeActionGrid").dialog({title: 'Allot To Detail',width: 300,height: 200});
			$("#takeActionGrid").dialog('open');
			$("#takeActionGrid").html(data);
		},
		error : function()
		{
			alert("Error on data fetch");
		} 
	});
}

function lodgeUserDetail(cellvalue, options, rowObject) 
{
	return "<a href='#' title='View Data' onClick='lodgeUserDetailOnClick("+rowObject.id+")'><font color='blue'>"+cellvalue+"</font></a>";
}

function lodgeUserDetailOnClick(complainId) 
{
	var mode = jQuery("#gridedittable").jqGrid('getCell',complainId,'lodgemode');
	var ticketNo = jQuery("#gridedittable").jqGrid('getCell',complainId,'ticketno');
	$.ajax
	({
		type : "post",
		url  : "view/Over2Cloud/HelpDeskOver2Cloud/Activity_Board/getComplaintActivityDeatil.action?complianId="+complainId+"&formaterOn=lodgeMode&mainTable=employee_basic",
		success : function(data)
		{
			$("#takeActionGrid").dialog({title:'Lodge Mode: '+mode+' for '+ticketNo,width: 300,height: 200});
			$("#takeActionGrid").dialog('open');
			$("#takeActionGrid").html(data);
		},
		error : function()
		{
			alert("Error on data fetch");
		} 
	});
}

function statusDetail11(cellvalue, options, rowObject) 
{
	return "<a href='#' title='View Data' onClick='statusDetailOnClick11("+rowObject.id+")'><font color='blue'>"+cellvalue+"</font></a>";
}

function statusDetailOnClick11(complainId) 
{
	if(typeof complainId!='undefined')
	{
		var status = jQuery("#gridedittableHistory").jqGrid('getCell',complainId,'status');
		var level = jQuery("#gridedittableHistory").jqGrid('getCell',complainId,'level');
		var openOn = jQuery("#gridedittableHistory").jqGrid('getCell',complainId,'openat');
		$.ajax
		({
			type : "post",
			url  : "view/Over2Cloud/HelpDeskOver2Cloud/Activity_Board/getComplaintActivityDeatil.action?complianId="+complainId+"&formaterOn=status&mainTable=feedback_status",
			success : function(data)
			{
				$("#takeActionGrid").dialog({title: 'Current Status '+status+' at '+level+', Open on '+openOn,width: 450,height: 350});
				$("#takeActionGrid").dialog('open');
				$("#takeActionGrid").html(data);
			},
			error : function()
			{
				alert("Error on data fetch");
			} 
		});
	}
}

function statusDetailOnClick(complainId) 
{
	getPrintData(complainId);
	 
}

function totalHistory(cellvalue, options, rowObject) 
{
	if(rowObject.asset_id=='NA')
		return "<a href='#' title='No Data'>"+cellvalue+"</a>";
	else
		return "<a href='#' title='View Data' onClick='totalHistoryOnClick("+rowObject.id+")'><font color='blue'>"+cellvalue+"</font></a>";
}

function totalHistoryOnClick(complainId) 
{
	$("#takeActionGrid").dialog({title: 'Breakdown History',width: 1200,height: 300});
	$("#takeActionGrid").dialog('open');
	$("#takeActionGrid").html("<br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax
	({
		type : "post",
		url  : "view/Over2Cloud/AssetOver2Cloud/Asset_Complaint/getComplaintActivityDeatil.action?complianId="+complainId+"&formaterOn=total&mainTable=asset_complaint_status",
		success : function(data)
		{
			$("#takeActionGrid").html(data);
		},
		error : function()
		{
			alert("Error on data fetch");
		} 
	});
}

function tatDetail(cellvalue, options, rowObject) 
{
 	if(cellvalue=="Level1")
	{
		return "<font color='black'>"+cellvalue+"</font>";
	}
	else
	{
		return "<font color='red'>"+cellvalue+"</font>";
	}


}
function resendSMS(cellvalue, options, rowObject) 
{
	var context_path = '<%=request.getContextPath()%>';
	return "<img title='Take Action' src='"+ context_path +"/images/SMSGreen.png' height='20' width='20' onClick='resendSMSOnClick("+rowObject.id+")'> ";
}

function resendSMSOnClick(complainId) 
{
	$.ajax
	({
		type : "post",
		url  : "view/Over2Cloud/HelpDeskOver2Cloud/Activity_Board/getComplaintActivityDeatil.action?formaterOn=smsResend",
		success : function(data)
		{
			$("#takeActionGrid").dialog({title: 'SMS Resend Alert!!!',width: 400,height: 100});
			$("#takeActionGrid").dialog('open');
			$("#takeActionGrid").html(data);
		},
		error : function()
		{
			alert("Error on data fetch");
		} 
	});

	 
 
}

function smsCode() 
{
	 	$.ajax
		({
			type : "post",
			url  : "view/Over2Cloud/HelpDeskOver2Cloud/Activity_Board/getComplaintActivityDeatil.action?formaterOn=smsCode",
			success : function(data)
			{
				$("#takeActionGrid").dialog({title: 'SMS Send On 9266605050',width: 400,height: 200});
				$("#takeActionGrid").dialog('open');
				$("#takeActionGrid").html(data);
			},
			error : function()
			{
				alert("Error on data fetch");
			} 
		});
	
}
function minimizeWindow() 
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Activity_Board/viewActivityBoardHeader.action",
	    success : function(feeddraft) {
       $("#"+"data_part").html(feeddraft);
	},
	   error: function() {
            alert("error");
        }
	 });
	
}
function getPrintData(feedid)

 {
	 // var feedid   = jQuery("#gridedittable").jqGrid('getGridParam', 'selrow');
	 $("#printSelect").dialog('open');
		 $("#printSelect").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		 $("#printSelect").load("/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Lodge_Feedback/printTicketInfo.action?feedId="+feedid );
 }
/* $.subscribe('getStatusForm', function(event,data)
{
	var feedStatus = $("#feedStatus").val();
	var feedid    = jQuery("#gridedittable").jqGrid('getGridParam', 'selrow');
	var fieldsvalues = jQuery("#gridedittable").jqGrid('getCell',feedid,'status');
	var status=null;
	if(fieldsvalues!=null && fieldsvalues!="")
	{
		status = fieldsvalues.split(">")[1].split("<")[0];
	}
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax
	({
		type : "post",
		url  : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset_Complaint/feedAction.action?feedid="+feedid+"&status="+status,
		success : function(data)
		{
			$("#data_part").html(data);
		},
		error : function()
		{
			alert("Error on data fetch");
		} 
	});
});

*/
$.subscribe('getStatusForm', function(event,data)
		{
			var feedStatus = $("#feedStatus").val();
			var id = jQuery("#gridedittable").jqGrid('getGridParam', 'selarrrow');
			var feedid    = jQuery("#gridedittable").jqGrid('getGridParam', 'selrow');
			 if(id.length==0 )
			{
	     		alert("Please select atleast one check box...");        
	     		 
			}else if(id.length>1)
			{
		 		alert("Please select only one check box...");        
	     		 
			}
			else if(feedid!=null)
				 {
		  	  $("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
			 $("#data_part").load("/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Lodge_Feedback/feedAction.action?feedStatus="+feedStatus.split(" ").join("%20")+"&feedId="+feedid.split(" ").join("%20"));
			  
				 }
			  
	});


function getAssetByOutletId(outletid,targetid)
{
	var outletId = $("#"+outletid).val();
	$.ajax
	({
		type :"post",
		url :"view/Over2Cloud/AssetOver2Cloud/Asset_Complaint/getMappedAsset?outletId="+outletId,
		success : function(empData){
		$('#'+targetid+' option').remove();
    	$(empData).each(function(index)
		{
		   $('#'+targetid).append($("<option></option>").attr("value",this.id).text(this.name));
		});
	    },
	    error : function () {
			alert("Somthing is wrong to get Data");
		}
	});
}
function getProductivty(moduleName,dataFor)
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Analytical_Report/getAnalytics.action?dataFor="+dataFor+"&moduleName="+moduleName,
	    success : function(data) {
       $("#"+"data_part").html(data);
	},
	   error: function() {
            alert("error");
        }
	 });
}

$.subscribe('getPrintData', function(event,data)
		  {
				 
		 	  var feedid   = jQuery("#gridedittable").jqGrid('getGridParam', 'selrow');
			 if(feedid!=null)
				 {
		 	//  if (feedStatus=='Pending' || feedStatus=='Snooze' || feedStatus=='High Priority' || feedStatus=='Ignore') {
		 	 	  $("#printSelect").load("/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Lodge_Feedback/printTicketInfo.action?feedId="+feedid );
		 			
		 	 	/*  }
			   else if(feedStatus=='Resolved') {
				  var allotto1  = jQuery("#gridedittable").jqGrid('getCell',feedid,'resolve_by');
				  var resolveon = jQuery("#gridedittable").jqGrid('getCell',feedid,'resolve_date');
				  var resolveat = jQuery("#gridedittable").jqGrid('getCell',feedid,'resolve_time');
				  var actiontaken = jQuery("#gridedittable").jqGrid('getCell',feedid,'resolve_remark');
				  var spareused = jQuery("#gridedittable").jqGrid('getCell',feedid,'spare_used');
				  $("#printSelect").load(connection+"/view/Over2Cloud/HelpDeskOver2Cloud/Lodge_Feedback/printTicketInfo.action?feedStatus="+feedStatus+"&ticket_no="+ticketNo+"&feedbackBy="+feedby.split(" ").join("%20")+"&mobileno="+feedmob.split(" ").join("%20")+"&open_date="+opendate.split(" ").join("%20")+"&open_time="+opentime.split(" ").join("%20")+"&catg="+catg.split(" ").join("%20")+"&subCatg="+subcatg.split(" ").join("%20")+"&feed_brief="+feedbrief.split(" ").join("%20")+"&allotto="+allotto1.split(" ").join("%20")+"&todept="+todept.split(" ").join("%20")+"&tosubdept="+feedtosubdept.split(" ").join("%20")+"&location="+location.split(" ").join("%20")+"&addrDate="+addrdate+"&addrTime="+addrtime+"&resolveon="+resolveon+"&resolveat="+resolveat+"&spareused="+spareused.split(" ").join("%20")+"&actiontaken="+actiontaken.split(" ").join("%20"));
				  }
			  alert("hello");*/
			  
			  $("#printSelect").dialog('open');
				 }
			 else
				 alert("Please Select Row...!!!!");
		  });

function editCloseMode(cellvalue, options, rowObject) 
{
	if(cellvalue=='SMS')
	{
		return "<a href='#' title='Edit Data' onClick='editCloseModeOnClick("+rowObject.id+")'><font color='blue'>"+cellvalue+"</font></a>";
	}
	else
	{
		return "<a>"+cellvalue+"</a>";
	}
}

function editCloseModeOnClick(complainId) 
{
	var ticketNo = jQuery("#gridedittable").jqGrid('getCell',complainId,'ticketno');
	$.ajax
	({
		type : "post",
		url  : "view/Over2Cloud/HelpDeskOver2Cloud/Activity_Board/getComplaintActivityDeatil.action?complianId="+complainId+"&formaterOn=closeMode&mainTable=feedback_status",
		success : function(data)
		{
			$("#takeActionGrid").dialog({title:'Edit Resolution Detail for Ticket: '+ticketNo,width: 1200,height: 400});
			$("#takeActionGrid").dialog('open');
			$("#takeActionGrid").html(data);
		},
		error : function()
		{
			alert("Error on data fetch");
		} 
	});
}
/* function refreshActivityBoard()
{
	  setInterval("HDMActivityRefresh()", (60*1000));
} */
function HDMActivityRefresh()
{
	onloadData();
}
//refreshActivityBoard();
onloadData();
</SCRIPT>
</head>
<body onunload="StopRefresh();">
 
							
<sj:dialog id="printSelect" title="Print Ticket" autoOpen="false"  modal="true" height="370" width="700" showEffect="drop"></sj:dialog>
<sj:dialog
          id="takeActionGrid"
          showEffect="slide"
          hideEffect="explode"
          autoOpen="false"
          title="Seek Approval Action"
          modal="true"
          closeTopics="closeEffectDialog"
          width="1000"
          height="400"
          >
</sj:dialog>
<sj:dialog id="printSelect" title="Print Ticket" autoOpen="false"  modal="true" height="370" width="700" showEffect="drop">
<sj:dialog id="feed_status" modal="true" effect="slide" autoOpen="false"  width="1100" hideEffect="explode" position="['center','top']"></sj:dialog>
<div id="ticketsInfo"></div>
</sj:dialog>
<div class="clear"></div>
<div class="middle-content">
<div class="list-icon">
	<div class="head">Helpdesk </div>
	<div class="head">
		<img alt="" src="images/forward.png" style="margin-top:10px;margin-right:3px; float: left;">Activity 
	</div>
	<%-- <div class="head">
		Pending: <s:textfield id="pendingDiv" cssClass="textfieldbgcolr" cssStyle="width: 50px;" disabled="true"/>
		Parked: <s:textfield id="snoozeDiv" cssClass="textfieldbgcolr" cssStyle="width: 50px;" disabled="true"/> 
		High Priority: <s:textfield id="HighPriorityDiv" cssClass="textfieldbgcolr" cssStyle="width: 50px;" disabled="true"/> 
		Seek Approval: <s:textfield id="holdDiv" cssClass="textfieldbgcolr" cssStyle="width: 50px;" disabled="true"/>
		Re-Assign: <s:textfield id="transferDiv" cssClass="textfieldbgcolr" cssStyle="width: 50px;" disabled="true"/>
			Re-Opened: <s:textfield id="reopenDiv" cssClass="textfieldbgcolr" cssStyle="width: 50px;" disabled="true"/>
	
		
		Ignore: <s:textfield id="ignoreDiv" cssClass="textfieldbgcolr" cssStyle="width: 50px;" disabled="true"/>
		Resolved: <s:textfield id="resolveDiv" cssClass="textfieldbgcolr" cssStyle="width: 70px;" disabled="true"/>
		Total:<s:textfield id="totalDiv" cssClass="textfieldbgcolr" cssStyle="width: 70px;" disabled="true"/>
	</div>	 --%>
	<%-- <sj:a  button="true" cssClass="button" style="margin-top:4px; float: right;" buttonIcon="ui-icon-plus"  onclick="getOnlinePage()">Feedback</sj:a>
	 --%> 
	 	<a href='#'  style="margin-top:6px; float: right;margin-right:3px;"  onclick="getProductivty('HDM','Employee')"><img src="images/productivity.jpg" width="32px" height="25px" title="Productivity"/></a>
	 	
	</div>								
<div class="clear"></div>
<div class="listviewBorder" style="margin-top: 10px;width: 97%;margin-left: 20px;" align="center">
<!-- Code For Header Strip -->
<div  onmouseover="StopRefresh()" onmouseout="StartRefresh()" style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
<div class="pwie">
 <table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%">
	 	    <tbody>
				    <tr>
				    <td >
				    		 <sj:datepicker cssStyle="height: 16px; width: 58px;" cssClass="button" id="minDateValue" name="minDateValue" size="20" maxDate="0" value="%{minDateValue}" readonly="true"   yearRange="2013:2050" showOn="focus" displayFormat="dd-mm-yy" Placeholder="Select From Date" onchange="onloadData();"/>
		     				 <sj:datepicker cssStyle="height: 16px; width: 58px;" cssClass="button" id="maxDateValue" name="maxDateValue" size="20" value="%{maxDateValue}"   readonly="true" yearRange="2013:2050" showOn="focus" displayFormat="dd-mm-yy" Placeholder="Select To Date" onchange="onloadData();"/>
				           <sj:datepicker cssStyle="height: 16px; width: 58px;" timepickerOnly="true" timepicker="true" timepickerAmPm="false" cssClass="button" id="fromTime" name="fromTime" size="20"   readonly="true"   showOn="focus"   Placeholder="From Time" onchange="onloadData();"/>
		     			  <sj:datepicker cssStyle="height: 16px; width: 58px;" timepickerOnly="true" timepicker="true" timepickerAmPm="false" cssClass="button" id="toTime" name="toTime" size="20"   readonly="true"   showOn="focus"   Placeholder="To Time" onchange="onloadData();"/>
		     			
			     	      	
				             <s:select  
						    		id					=		"taskType"
						    		name				=		"taskType"
						    		list				=		"#{'all':'Group','byMe':'By Me','toMe':'To Me'}"
						      		theme				=		"simple"
						      		cssClass			=		"button"
						      		cssStyle			=		"height: 28px;margin-top: 3px;margin-left: -2px;width: 88px;"	
						      		onchange			=		"onloadData()"
						      		>
					      	 </s:select> 
					      	 
				             <%-- <s:select  
						    		id					=		"fromDept1"
						    		name				=		"fromDept1"
						    		list				=		"fromDept"
						      		theme				=		"simple"
						      		cssClass			=		"button"
						      		cssStyle			=		"height: 28px;margin-top: 3px;margin-left: -2px;width: 103px;"	
						      		onchange			=		"onloadData();"
						      		>
					      	 </s:select>  --%>
					      	 	 
							  <s:select  
						    		id					=		"feedStatus"
						    		name				=		"feedStatus"
						    		list	=	"#{'All':'All Status','Pending':'Pending','Snooze':'Parked','High Priority':'High Priority','Re-Assign':'Re-Assign','ignore':'Ignore','Resolved':'Resolved','Re-Opened':'Re-Opened','Escalated':'Escalated','Re-OpenedR':'Re-Opened History','SnoozeH':'Parked History'}"
	     							theme				=		"simple"
						      		cssClass			=		"button"
						      		headerKey			=		"-1"
	      							headerValue			=		"Status"
						      		cssStyle			=		"height: 28px;margin-top: 3px;margin-left: -2px;width: 95px;"
						      		onchange			=		"onloadData();"
						      		>
					      	 </s:select>
					      	 
					      	  <s:select  
						    		id					=		"escLevel"
						    		name				=		"escLevel"
						    		list				=		"#{'All':'All Level','Level1':'Level-1','Level2':'Level-2','Level3':'Level-3','Level4':'Level-4','Level5':'Level-5','Level6':'Level-6'}"
						      		theme				=		"simple"
						      		cssClass			=		"button"
						      		cssStyle			=		"height: 28px;margin-top: 3px;margin-left: -2px;width: 96px;"
						      		onchange			=		"onloadData();"	
						      		>
					      	 </s:select>
					      	 
					      	 <s:select  
						    		id					=		"escTAT"
						    		name				=		"escTAT"
						    		list				=		"#{'All':'All Time','onTime':'On Time','offTime':'Off Time'}"
						      		theme				=		"simple"
						      		cssClass			=		"button"
						      		cssStyle			=		"height: 28px;margin-top: 3px;margin-left: -2px;width: 105px;"
						      		onchange			=		"onloadData();"	
						      		>
					      	 </s:select>
					      	   <s:select  
						    		id					=		"dept_id"
						    		name				=		"dept_id"
						    		list				=		"deptList"
						      		theme				=		"simple"
						      		cssClass			=		"button"
						      		headerKey			=		"-1"
						      		headerValue			=		"All Department"
						      		cssStyle			=		"height: 28px;margin-top: 3px;margin-left: -2px;width: 105px;"
						      		onchange			=		"onloadData(),getSubCategory(this.value,'category_widget');"	
						      		>
					      	 </s:select> 
					   
								      	   <sj:autocompleter
							    
								 				id					=		"category"
												name				=		"category"
							   					list				=		"{'No Sub-Subdepartment'}"
							 					selectBox			=		"true"
												selectBoxIcon		=		"true"
												cssStyle="width:10% "
												onChangeTopics		=		"autocompleteChange"
												onFocusTopics		=		"autocompleteFocus"
												
									
								/> 
					      			     	<%--  <sj:autocompleter
					 id="ticketValue"
					 name="ticketValue"
				 	 list="TicketNo"
				 	 selectBox="true"
				  	 selectBoxIcon="true"
				  	 onChangeTopics="autocompleteChange"
				 	  onSelectTopics="getSelectedTicketDetail"
				 	      
					 
				 	/>   --%>
				 	
			     	           	
			     	         	<s:textfield  id="wildSearch" name="wildSearch" onkeyup="wildSearch();" cssClass="button" cssStyle="width: 100px;height: 17px;margin-top: -28px ;margin-left: 2px;" Placeholder="Enter Any Value" theme="simple"/>
			       		  </td>
			       		  <td>
					     		<!-- <a href='#'  style="height:26px; width:32px;"onclick="smsCode();"> <img src="images/sms1.jpg" width="32px" height="27px" style="margin-top:3px;margin-left:-135px;;margin-right:7px" title="SMS Code"/></a> --> 
		    			 	</td>
			       		 <%--  <td>
			     	          	<sj:a  button="true"  cssStyle="height: 27px;margin-top:3px;margin-left:-68px;width:31px;margin-right: 2px;"  cssClass="button" buttonIcon="ui-icon-print" title="Print" onClickTopics="getPrintData"></sj:a>
			       		     	</td>
			       		 --%>     	<td>
			       		     	<sj:a  button="true" cssClass="button" cssStyle="margin-top: 7px;margin-left: -115px;height:27px; width:31px;margin-right: 2px;"  title="Download"   onClick="downloadFeedStatus();" buttonIcon="ui-icon-arrowstop-1-s"></sj:a>
			     	          </td>
			     	          <td>
			     	           <sj:a  button="true" cssClass="button" cssStyle="margin-top: 0px;margin-left: 2px;height:27px; width:31px;margin-right: 2px;"  title="Minimize"   onClick="minimizeWindow();" buttonIcon=" ui-icon-arrowthick-1-sw"></sj:a>
		    			 	</td>
					      </tr>
				        
				        <tr >
				    	 <td  > 
			       		 </td>	 
  							 
			     	           	 
				    </tr>
				</tbody>
			 </table>
		    
</div>
</div>
  <div   style="overflow:scroll;  max-height:400px; " align="center">
  <div id="viewDataDiv" ></div>
</div>
</div>
</div>
</body>
</html>