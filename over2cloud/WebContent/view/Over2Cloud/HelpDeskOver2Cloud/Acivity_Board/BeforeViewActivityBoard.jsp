<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%String userRights = session.getAttribute("userRights") == null ? "" : session.getAttribute("userRights").toString();%>
<%//String validApp = session.getAttribute("validApp") == null ? "" : session.getAttribute("validApp").toString();
//String empIdofuser = (String) session.getAttribute("empIdofuser");
///String allotTOId=(String)session.getAttribute("AllotToId");
//System.out.println("Emp ID "+empIdofuser+" AllotToID "+allotTOId);
%>
<html>
<head>

<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script type="text/javascript" src="<s:url value="/js/helpdesk/feedback.js"/>"></script>
<STYLE>
.textfieldbgcolr {
    background-color: #E8E8E8;
}
</style>
<STYLE>
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


$( document ).ready(function() {
	$("#hide111").val("max");
});

var ticketValue='0';
function hideMe(divId)
{
	$("#"+divId).css('display','none');
}
function downloadFeedStatus()
{
 	var sel_id;
 	var searchField = null;
 	sel_id = $("#gridedittable").jqGrid('getGridParam','selarrrow');
 	{
 	 	
 	var feedStatus = $("#feedStatus").val();
	var minDateValue = $("#minDateValue").val();
	var maxDateValue = $("#maxDateValue").val();
	var taskType = $("#taskType").val();
	var escLevel = $("#escLevel").val();
	var escTAT = $("#escTAT").val();
	var fromTime = $("#fromTime").val();
	var toTime = $("#toTime").val();
	var feedRegUser = $("#feedRegUser").val();
	var searchValue = $("#searchValue").val();
	 var wildSearch= $("#wildSearch").val();
	 var Dept = $("#dept_id").val();
	 if(searchValue!=null && searchValue!='')
	{
		searchField="feed_brief";
	}
	if(ticketValue!=null && ticketValue!='' && ticketValue!='undefined' && ticketValue!='0')
	{
	 	searchValue=ticketValue;
		searchField="ticket_no";
	}
	 if(wildSearch!=null && wildSearch!='')
	{
		searchValue=wildSearch;
		searchField="All";
	} 
 	$("#takeActionGrid").dialog({title: 'Check Column',width: 350,height: 600});
	$("#takeActionGrid").dialog('open');
	$("#takeActionGrid").html("<br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");

	$.ajax({
	    type : "post",
	 	 	 url : "/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Activity_Board/feedbackStatusDownload.action?fromDepart=all&feedStatus="+feedStatus+"&minDateValue="+minDateValue+"&maxDateValue="+maxDateValue+"&taskType="+taskType+"&lodgeMode=All&closeMode=All&escLevel="+escLevel+"&escTAT="+escTAT+"&severityLevel=All&fromTime="+fromTime+"&toTime="+toTime+"&regUser="+feedRegUser+"&sel_id="+sel_id+"&searchField="+searchField+"&searchString="+searchValue+"&dept="+Dept,
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
function closeMe()
{
	$("#takeActionGrid").dialog('close');
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
	alert("hi");
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
	$("#takeActionGrid1").dialog({title: 'Feedback By Detail',width: 300,height: 200});
	$("#takeActionGrid1").dialog('open');
	$("#takeActionGrid1").html(data);
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

 
function takeAction(cellvalue, options, rowObject) 
{
	var context_path = '<%=request.getContextPath()%>';
	return "<a href='#'><img title='Take Action' src='"+ context_path +"/images/action.jpg' height='20' width='20' onClick='takeActionOnClick("+rowObject.id+")'> </a>";
}

function takeActionOnClick(complainId) 
{
	 
	var feedStatus = jQuery("#gridedittable").jqGrid('getCell',complainId,'status') ;
	var ticketNo = jQuery("#gridedittable").jqGrid('getCell',complainId,'ticketno') ;
	var dateTime = jQuery("#gridedittable").jqGrid('getCell',complainId,'openat') ;
	 
	$("#takeActionGrid").dialog({title: 'Take Action On: '+ticketNo+ ' Opened On: '+dateTime+ '',width: 1000,height: 600});
	$("#takeActionGrid").html("<br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$("#takeActionGrid").dialog('open');
	$.ajax
	({
	type : "post",
	url  : "view/Over2Cloud/HelpDeskOver2Cloud/Lodge_Feedback/feedAction.action?feedStatus="+feedStatus+"&feedId="+complainId,
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

 function sendSMS(cellvalue, options, rowObject) 
{
	var context_path = '<%=request.getContextPath()%>';
	return "<img title='Take Action' src='"+ context_path +"/images/SMSGreen.png' height='20' width='20' onClick='resendSMSOnClick("+rowObject.id+")'> ";
} 

function resendSMS(cellvalue, options, rowObject) 
{
	 	var context_path = '<%=request.getContextPath()%>';
 	  if(cellvalue=='unsent')
	return "<img title='Take Action' src='"+ context_path +"/images/SMSred12.png' height='20' width='20' onClick='resendSMSOnClick("+rowObject.id+")'> ";
	else if(cellvalue=='sent') 
	return "<img title='Take Action' src='"+ context_path +"/images/SMSGreen12.png' height='20' width='20' onClick='resendSMSOnClick("+rowObject.id+")'> ";

}
function resendSMSOnClick(complainId) 
{
	 	$.ajax
	({
	type : "post",
	url  : "view/Over2Cloud/HelpDeskOver2Cloud/Activity_Board/getComplaintActivityDeatil.action?formaterOn=smsResend&SMSResend_feedId="+complainId,
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
	$("#takeActionGrid1").dialog({title: 'Current Status '+status+' at '+level+', Opened on '+openOn,width: 450,height: 350});
	$("#takeActionGrid1").dialog('open');
	$("#takeActionGrid1").html(data);
	},
	error : function()
	{
	alert("Error on data fetch");
	} 
	});
	}
}

function statusDetail(cellvalue, options, rowObject) 
{
	return "<a href='#' title='View Data' onClick='statusDetailOnClick("+rowObject.id+")'><font color='blue'>"+cellvalue+"</font></a>";
}

function statusDetailOnClick(complainId) 
{
	getPrintData(complainId);
	/* if(typeof complainId!='undefined')
	{
	var status = jQuery("#gridedittable").jqGrid('getCell',complainId,'status');
	var level = jQuery("#gridedittable").jqGrid('getCell',complainId,'TAT');
	var openOn = jQuery("#gridedittable").jqGrid('getCell',complainId,'openat');
	$.ajax
	({
	type : "post",
	url  : "view/Over2Cloud/HelpDeskOver2Cloud/Activity_Board/getComplaintActivityDeatil.action?complianId="+complainId+"&formaterOn=status&mainTable=feedback_status",
	success : function(data)
	{
	 	$("#takeActionGrid").dialog({title: 'Current Status '+status+' at '+level+', Opened on '+openOn,width: 450,height: 550});
	$("#takeActionGrid").dialog('open');
	$("#takeActionGrid").html(data);
	},
	error : function()
	{
	alert("Error on data fetch");
	} 
	}); 
	}*/
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

function maximizeWindow() 
{
var tes =$("#hide111").val();
if(tes=='max')
{
	$("#hide111").val("min");
	$("#TicketDiv").hide(true);
	$("#newalertDiv").hide(true);
	$("#presentEmpDiv").hide(true);
	onloadData();
	document.getElementById('mainviewdiv').setAttribute("style","height:498px; width:100%;  float:left;  -webkit-border-radius: 6px; -moz-border-radius: 6px; border-radius: 6px;  padding: 0.5%;border-right: 1px solid rgba(176, 165, 165, 0.54)");
}
else{
	$("#TicketDiv").show(true);
	$("#newalertDiv").show(true);
	$("#presentEmpDiv").show(true);
	$("#hide111").val("max");
	onloadData();
	document.getElementById('mainviewdiv').setAttribute("style","height:498px; width:55%;  float:left;  -webkit-border-radius: 6px; -moz-border-radius: 6px; border-radius: 6px;  padding: 0.5%;border-right: 1px solid rgba(176, 165, 165, 0.54)");
	
}
}

 
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
$.subscribe('closeTop', function(event,data)
	{
	
	jQuery.each(ids, function (id,data) {
        $("#jqgrid_id").expandSubGridRow(data);
        jQuery("#gridedittable").closest(".ui-jqgrid-bdiv").scrollTop(scrollPosition);
   });
	});
$.subscribe('getSelectedTicketDetail', function(event,data)
{
	 	ticketValue=data.value;
	onloadData();
});

$.subscribe('categoryChange', function(event, data) {
	 
	 onloadData(data.value);
	 
});


function getSubCategory(data,divId) {
	
 	$.ajax({
	type :"post",
	url :"/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Lodge_Feedback/getSubCategory.action?bydept_subdept="+data,
	success : function(data){
	$('#'+divId+' option').remove();
	 $(".uiInput .ui-autocomplete-input").val('');
	 $("#category").val('');
	$('#'+divId).append($("<option></option>").attr("value",-1).text("Select Sub Category"));
	    $(data).each(function(index)
	{
	  $('#'+divId).append($("<option></option>").attr("value",this.id).text(this.name));
	});
	},
	error : function () {
	alert("Somthing is wrong to get Data");
	}
	});
	 
	 
}

	

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
function viewLocation(cellvalue, options, rowObject) 
{
	return "<a href='#' title='View Data' onClick='viewLocationOnClick("+rowObject.id+")'><font color='blue'>"+cellvalue+"</font></a>";
}

function viewLocationOnClick(id) 
{
	
	$.ajax
	({
	type : "post",
	url  : "view/Over2Cloud/HelpDeskOver2Cloud/Activity_Board/fetchLocationInfo.action?complianId="+id,
	success : function(data)
	{
	
	$("#takeActionGrid").dialog({title: 'Location Details: ',width: 550,height: 100});
	$("#takeActionGrid").dialog('open');
	var temp='<table id="empinfo" width="100%">';
	temp+='<tr><td class="TitleBorder" align="center">Floor Name</td><td class="TitleBorder" align="center">Wing Name</td><td class="TitleBorder" align="center">Room No</td></tr>';
	$(data).each(function(index)
	{
	temp +='<tr><td align="center">'+data[index].floor+'</td>';
	temp +='<td align="center" >'+data[index].wing+'</td>';
	temp +='<td align="center" >'+data[index].room+'</td>';
	});
	temp +='</table>';
	$("#takeActionGrid").html(temp);
	},
	error : function()
	{
	alert("Error on data fetch");
	} 
	});
}
function onloadTicketLodge( )
{  
	chkDeptFlag=0;
	 	$.ajax({
	    type : "post",
	    async:false,
	    url : "/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Lodge_Feedback/beforeFeedViaOnline.action?feedStatus=online&dataFor=HDM",
	    success : function(feeddraft) {
       $("#"+"TicketDiv").html(feeddraft);
	},
	   error: function() {
            alert("error");
        }
	 });
	 	
	$.ajax({
	    type : "post",
	    url : "/over2cloud/viewNewsandalerts.action?dataFor=HDM",
	    success : function(feeddraft) {
	       $("#"+"newalertDiv").html(feeddraft);
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
function getPresentEmployee() 
{
	
	$.ajax
	({
	type : "post",
	url  : "view/Over2Cloud/HelpDeskOver2Cloud/Activity_Board/getPresentEmployee.action",
	async:false,
	success : function(data)
	{
	//console.log(data);
	$("#TableEmpDept").empty();
	var temp='';
	$(data).each(function(index)
	{
	temp +='<tr  ><td class="contents" id="subdept'+data[index].subdeptId+'" align="center">'+data[index].subdept+'</td><a href="#" ><td align="center" class="contents" onclick="getEmployeeInfo('+data[index].subdeptId+')"><a href="#" style="color:white" >'+data[index].counter+'</a></td></a></tr>';
	});
	$("#TableEmpDept").append(temp);
	},
	error : function()
	{
	alert("Error on data fetch");
	} 
	});
}
function getEmployeeInfo(subdept){
	var subDeptName=$("#subdept"+subdept).text();
 	$.ajax
	({
	type : "post",
	url  : "view/Over2Cloud/HelpDeskOver2Cloud/Activity_Board/getEmployeeInfo.action?toDepart="+subdept,
	async:false,
	success : function(data)
	{
	
	$("#takeActionGrid").dialog({title: 'Employee Details Working Today For: '+subDeptName,width: 950,height: 400});
	$("#takeActionGrid").dialog('open');
	var temp='<table id="empinfo1" width="100%">';
	temp+='<tr><td class="TitleBorder" align="center">Employee Name</td><td class="TitleBorder" align="center">Mobile Number</td><td class="TitleBorder" align="center">Email ID</td><td class="TitleBorder" align="center">Department</td><td class="TitleBorder" align="center">Level</td><td class="TitleBorder" align="center">Shift From</td><td class="TitleBorder" align="center">Shift To</td><td class="TitleBorder" align="center">Location</td></tr>';
	$(data).each(function(index)
	{
	temp +='<tr><td>'+data[index].empName+'</td>';
	temp +='<td align="center" >'+data[index].mobile+'</td>';
	temp +='<td align="center" >'+data[index].email+'</td>';
	temp +='<td align="center" >'+data[index].dept+'</td>';
	temp +='<td align="center" >'+data[index].level+'</td>';
	temp +='<td align="center" >'+data[index].shiftFrom+'</td>';
	temp +='<td align="center" >'+data[index].shiftTo+'</td>';
	temp +='<td align="center" ><a href="#" onclick="fetchLocationData('+data[index].compid+','+subdept+')"><img class="ui-icon tree-wrap-ltr ui-icon-info" /></a></td>';
	
	});
	temp +='</table>';
	$("#takeActionGrid").html(temp);
	},
	error : function()
	{
	alert("Error on data fetch");
	} 
	});
}

function fetchLocationData(compid,subdept)
{

 	$.ajax
	({
	type : "post",
	url  : "view/Over2Cloud/HelpDeskOver2Cloud/Activity_Board/getEmployeeLocationInfo.action?toDepart="+subdept+"&sel_id="+compid,
	async:false,
	success : function(data)
	{
	
	$("#takeActionGrid1").dialog({title: 'Location Details: ',width: 550,height: 300});
	$("#takeActionGrid1").dialog('open');
	var temp='<table id="empinfo" width="100%">';
	temp+='<tr><td class="TitleBorder" align="center">Floor Name</td><td class="TitleBorder" align="center">Wing Name</td></tr>';
	$(data).each(function(index)
	{
	temp +='<tr><td align="center">'+data[index].floor+'</td>';
	temp +='<td align="center" >'+data[index].wing+'</td>';
	
	});
	temp +='</table>';
	$("#takeActionGrid1").html(temp);
	},
	error : function()
	{
	alert("Error on data fetch");
	} 
	});
}
function getfreeEmployee() 
{
	
	$.ajax
	({
	type : "post",
	url  : "view/Over2Cloud/HelpDeskOver2Cloud/Activity_Board/getfreeEmployee.action",
	async:false,
	success : function(data)
	{
	console.log(data);
	$("#TablefreEmpDept").empty();
	var temp='';
	$(data).each(function(index)
	{
	temp +='<tr  ><td class="contentsfree" id="freesubdept'+data[index].subdeptId+' align="center">'+data[index].subdept+'</td><a href="#"><td align="center" class="contentsfree" onclick="getFreeEmployeeInfo('+data[index].subdeptId+')">'+data[index].counter+'</td></a></tr>';
	});
	$("#TablefreEmpDept").append(temp);
	},
	error : function()
	{
	alert("Error on data fetch");
	} 
	});
}

function getFreeEmployeeInfo(subdept){
	var subDeptName=$("#freesubdept"+subdept).text();
 	$.ajax
	({
	type : "post",
	url  : "view/Over2Cloud/HelpDeskOver2Cloud/Activity_Board/getfreeEmployeeInfo.action?toDepart="+subdept,
	success : function(data)
	{
	$("#takeActionGrid").dialog({title: 'Employee Details Free Now For: '+subDeptName,width: 800,height: 400});
	$("#takeActionGrid").dialog('open');
	var temp='<table width="100%">';
	temp+='<tr><td class="TitleBorder">Employee Name</td><td class="TitleBorder">Mobile Number</td><td class="TitleBorder">Email ID</td><td class="TitleBorder">Department</td><td class="TitleBorder">Sub-Department</td><td class="TitleBorder">Organization Level</td></tr>';
	$(data).each(function(index)
	{
	temp +='<tr  ><td class="contentsfree">'+data[index].empName+'</td>';
	temp +='<td align="center" class="contentsfree">'+data[index].mobile+'</td>';
	temp +='<td align="center" class="contentsfree">'+data[index].email+'</td>';
	temp +='<td align="center" class="contentsfree">'+data[index].dept+'</td>';
	temp +='<td align="center" class="contentsfree">'+data[index].subdept+'</td>';
	temp +='<td align="center" class="contentsfree">'+data[index].level+'</td></tr>';
	});
	$("#takeActionGrid").html(temp);
	},
	error : function()
	{
	alert("Error on data fetch");
	} 
	});
}

function closeAlertPopup() 
{
	$("#alertPopup").hide('slow');
	$("#resolveAlertPopup").hide('slow');
}

function gridCurrentPage()
{
	
	

	RefreshGridData();
	  onloadData();
	} 
	
 
var scrollPosition = 0
var ids = new Array();
function RefreshGridData() {
    ids = new Array();
    $('tr:has(.sgexpanded)').each(function(){ids.push($(this).attr('id'))});
    scrollPosition = jQuery("#gridedittable").closest(".ui-jqgrid-bdiv").scrollTop()
    $("#gridedittable").trigger("reloadGrid");
}
function wildSearch()
{
	 delay(function()
	{
	  onloadData();
	    }, 500 );
}
var delay = (function(){
	  var timer = 0;
	  return function(callback, ms){
	    clearTimeout (timer);
	    timer = setTimeout(callback, ms);
	  };
	})();
 
<%if(userRights.contains("actBoard_VIEW")) 
{%>
clearInt();
refreshActivityBoard();
onloadData();
<%}%>
<%if(userRights.contains("lodgetkt_VIEW")) 
{%>
onloadTicketLodge();
<%}%>
<%if(userRights.contains("anouce_VIEW")) 
{%>
getPresentEmployee();
<%}%>
//getfreeEmployee();
</SCRIPT>
<style type="text/css">


.TitleBorder{
font-weight: bold;
}

.contents {
  width: 21px;
  color: white;
  background: rgb(33, 140, 255);
  box-shadow: 4px 3px 2px 2px rgb(224, 224, 209);}
  
  .contentsfree {
  width: 21px;
  color: white;
  background: rgb(231, 113, 92);
  box-shadow: 4px 3px 2px 2px rgb(224, 224, 209);}
  #empinfo tr:nth-child(even) {background: #CCC}
#empinfo tr:nth-child(odd) {background: #FFF}

 #empinfo1 tr:nth-child(even) {background: #CCC}
#empinfo1 tr:nth-child(odd) {background: #CCC}
</style>
</head>

<body onunload="StopRefresh()"  >
 <s:hidden id="resolve_alert" value="%{resolve_Alert}"/>
 <s:hidden id="hide111"></s:hidden>
 <s:property value="%{resolve_Alert}"/> 
<sj:dialog id="printSelect" title="Print Ticket" autoOpen="false"  modal="true" height="870" width="1200" showEffect="drop"></sj:dialog>
 

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
<sj:dialog
          id="takeActionGrid1"
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
  
<sj:dialog id="printSelect" title="Print Ticket" autoOpen="false"  modal="true" height="600" width="1200" showEffect="drop">
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
	
	<%-- <sj:a  button="true" cssClass="button" style="margin-top:4px; float: right;" buttonIcon="ui-icon-plus"  onclick="getOnlinePage()">Feedback</sj:a>
 --%>	 
	 	<a href='#'   style="margin-top:6px; float: right;margin-right:3px;"  onclick="getProductivty('HDM','Employee')"><img src="images/productivity.jpg" width="32px" height="25px" title="Productivity"/></a>
	 	
	</div>	
	</div>	
<div class="clear"></div>

<div style="  height:498px; width:99%;    margin: 5px 0px 0px 6px; border: 1px solid #e4e4e5; -webkit-border-radius: 6px; -moz-border-radius: 6px; border-radius: 6px; ">
  
  <%if(userRights.contains("lodgetkt_VIEW") && !userRights.contains("anouce_VIEW") && userRights.contains("actBoard_VIEW")) 
	 
{%>  
 
<div onmouseover="StopRefresh()" onmouseout="StartRefresh()" style="  height:498px; width:70%;    margin: 5px 20px 0px 6px;float:left;  -webkit-border-radius: 6px; -moz-border-radius: 6px; border-radius: 6px;  padding: 0.5%;border-right: 1px solid rgba(176, 165, 165, 0.54);"   >
 <%}%>
  <%  if(!userRights.contains("lodgetkt_VIEW") && userRights.contains("actBoard_VIEW") && userRights.contains("anouce_VIEW")) 
	
{%>  
  
<div onmouseover="StopRefresh()" onmouseout="StartRefresh()" style="  height:498px; width:79%;  float:left;  -webkit-border-radius: 6px; -moz-border-radius: 6px; border-radius: 6px;  padding: 0.5%;border-right: 1px solid rgba(176, 165, 165, 0.54);"   >
 <%}%>
  
  <% if(userRights.contains("lodgetkt_VIEW") && userRights.contains("anouce_VIEW") && userRights.contains("actBoard_VIEW")) 
  {%>  
 
<div onmouseover="StopRefresh()" onmouseout="StartRefresh()" id="mainviewdiv"  style="  height:498px; width:55%;  float:left;  -webkit-border-radius: 6px; -moz-border-radius: 6px; border-radius: 6px;  padding: 0.5%;border-right: 1px solid rgba(176, 165, 165, 0.54);"   >
 <%}%>
 <div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
<div class="pwie">
 <table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%">
	 	    <tbody>
	    <tr>
	    <td class='uiInput'>
	    	 <sj:datepicker  cssStyle="height: 16px; width: 58px;" cssClass="button" id="minDateValue" name="minDateValue" size="20" maxDate="0" value="%{minDateValue}" readonly="true"   yearRange="2013:2050" showOn="focus" displayFormat="dd-mm-yy" Placeholder="Select From Date" onchange="onloadData();"/>
	     	 <sj:datepicker cssStyle="height: 16px; width: 58px;" cssClass="button" id="maxDateValue" name="maxDateValue" size="20" value="%{maxDateValue}"   readonly="true" yearRange="2013:2050" showOn="focus" displayFormat="dd-mm-yy" Placeholder="Select To Date" onchange="onloadData();"/>
	           <sj:datepicker cssStyle="height: 16px; width: 58px;" timepickerOnly="true" timepicker="true" timepickerAmPm="false" cssClass="button" id="fromTime" name="fromTime" size="20"   readonly="true"   showOn="focus"   Placeholder="From Time" onchange="onloadData();"/>
	     	  <sj:datepicker cssStyle="height: 16px; width: 58px;" timepickerOnly="true" timepicker="true" timepickerAmPm="false" cssClass="button" id="toTime" name="toTime" size="20"   readonly="true"   showOn="focus"   Placeholder="To Time" onchange="onloadData();"/>
	             
	             <s:select  
	    	id	=	"taskType"
	    	name	=	"taskType"
	    	list	=	"#{'all':'Group','byMe':'By Me','toMe':'To Me'}"
	      	theme	=	"simple"
	      	cssClass	=	"button"
	      	cssStyle	=	"height: 28px;margin-top: 3px;margin-left: -2px;width: 88px;"	
	      	onchange	=	"onloadData()"
	      	>
	      	 </s:select> 
	      	 
	             <%-- <s:select  
	    	id	=	"fromDept1"
	    	name	=	"fromDept1"
	    	list	=	"fromDept"
	      	theme	=	"simple"
	      	cssClass	=	"button"
	      	cssStyle	=	"height: 28px;margin-top: 3px;margin-left: -2px;width: 103px;"	
	      	onchange	=	"onloadData();"
	      	>
	      	 </s:select>  --%>
	      	 	 
	  <s:select  
	    	id	=	"feedStatus"
	    	name	=	"feedStatus"
	    	list	=	"#{'All':'All Status','Pending':'Pending','Snooze':'Parked','High Priority':'High Priority','Re-Assign':'Re-Assign','ignore':'Ignore','Resolved':'Resolved','Re-Opened':'Re-Opened','Escalated':'Escalated','Re-OpenedR':'Re-Opened History','SnoozeH':'Parked History'}"
	      	theme	=	"simple"
	      	cssClass	=	"button"
	      	headerKey="-1"
	      	headerValue="Status"
	      	cssStyle	=	"height: 28px;margin-top: 3px;margin-left: -2px;width: 95px;"
	      	onchange	=	"onloadData();"
	      	>
	      	 </s:select>
	   	 <s:select  
	    	id	=	"escLevel"
	    	name	=	"escLevel"
	    	list	=	"#{'All':'All Level','Level1':'Level-1','Level2':'Level-2','Level3':'Level-3','Level4':'Level-4','Level5':'Level-5','Level6':'Level-6'}"
	      	theme	=	"simple"
	      	cssClass	=	"button"
	      	cssStyle	=	"height: 28px;margin-top: 3px;margin-left: -2px;width: 96px;"
	      	onchange	=	"onloadData();"	
	      	>
	      	 </s:select>
	      	 <sj:a  button="true" cssClass="button" cssStyle="margin-top: 0px;margin-left: 2px;height:27px; width:31px;margin-right: 2px;"  title="Maximise"   onClick="maximizeWindow();" buttonIcon=" ui-icon-arrowthick-2-ne-sw"></sj:a>
	      	 
	      	 <s:select  
	    	id	=	"escTAT"
	    	name	=	"escTAT"
	    	list	=	"#{'All':'All Time','onTime':'On Time','offTime':'Off Time'}"
	      	theme	=	"simple"
	      	cssClass	=	"button"
	      	cssStyle	=	"height: 28px;margin-top: 3px;margin-left: -2px;width: 105px;"
	      	onchange	=	"onloadData();"	
	      	>
	      	 </s:select>
	      
	          	   <s:select  
	    	id	=	"dept_id"
	    	name	=	"dept_id"
	    	list	=	"deptList"
	      	theme	=	"simple"
	      	cssClass	=	"button"
	      	headerKey	=	"-1"
	      	headerValue	=	"All Department"
	      	cssStyle	=	"height: 28px;margin-top: 3px;margin-left: -2px;width: 105px;"
	      	onchange	=	"onloadData(),getSubCategory(this.value,'category_widget');"	
	      	>
	      	 </s:select> 
	   
	      
	        
	      	 <%-- <s:select  
	    	id	=	"severityLevel"
	    	name	=	"severityLevel"
	    	list	=	"#{'all':'All Severity Level','1':'Severity Level 1','2':'Severity Level 2','3':'Severity Level 3','4':'Severity Level 4','5':'Severity Level 5'}"
	      	theme	=	"simple"
	      	cssClass	=	"button"
	      	cssStyle	=	"height: 28px;margin-top: 3px;margin-left: -2px;width: 108px;"	
	      	onchange	=	"onloadData();"
	      	>
	      	 </s:select> --%>
	      	   <sj:autocompleter
	    
	 	id	=	"category"
	name	=	"category"
	   	list	=	"{'No Sub-Subdepartment'}"
	 	selectBox	=	"true"
	selectBoxIcon	=	"true"
	cssStyle="width:10% "
	onSelectTopics="categoryChange"

	
	
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
	     	         	
	     	         <%--    	<sj:a  button="true"  cssStyle="height: 27px;margin-top:0px;margin-left:2px;width:31px;margin-right: 2px;"  cssClass="button" buttonIcon="ui-icon-print" title="Print" onClick="getPrintData()" ></sj:a>
	       	  --%>     	<sj:a  button="true" cssClass="button" cssStyle="margin-top: 0px;margin-left: 2px;height:27px; width:31px;margin-right: 2px;"  title="Download"   onClick="downloadFeedStatus();" buttonIcon="ui-icon-arrowstop-1-s"></sj:a>
	       	      	<sj:a  button="true" cssClass="button" cssStyle="margin-top: 0px;margin-left: 2px;height:27px; width:31px;margin-right: 2px;"  title="Refresh"   onClick="gridCurrentPage();" buttonIcon="ui-icon-refresh"></sj:a>
	     	     	
	     	<!-- 	<a href='#'  style="height:26px; width:32px;"onclick="smsCode();"> <img src="images/sms1.jpg" width="32px" height="27px" style="margin-top:0px;margin-left:33px;;margin-right:7px" title="SMS Code"/></a> --> 
	    	 
	     	        
	     	<!-- <a href='#'  style="height:26px; width:32px;"onclick="maximizeWindow();"> <img src="images/maximize.png" width="32px" height="27px" style="margin-top:3px;margin-left:-75px;;margin-right:7px" title="Maximize"/></a> --> 
	    	 	</td>
	    	 
	    	 	    	<s:checkbox id="advncSearch" name="advncSearch"      cssStyle=" margin-top: -25px; margin-left: -142px;width: 23px;height: 23px;" title="Advance Search" onchange="onloadData()"></s:checkbox>
	     	   
	      </tr>
	        </tbody>
	 </table>
	 
	<%--  <table border="0" cellpadding="0" cellspacing="0" width="100%">
	    <tbody>
	        <tr >
	    	 <td style="padding: 0.3%" > 
	    	  
	      	 <s:select  
	    	id	=	"lodgeMode"
	    	name	=	"lodgeMode"
	    	list	=	"#{'All':'Lodge Mode','online':'Online','call':'Call'}"
	      	theme	=	"simple"
	      	cssClass	=	"button"
	      	cssStyle	=	"height: 28px;margin-top: 3px;margin-left: -2px;width: 97px;"
	      	onchange	=	"onloadData();"	
	      	>
	      	 </s:select>
	      	 
	      	 <s:select  
	    	id	=	"closeMode"
	    	name	=	"closeMode"
	    	list	=	"#{'All':'Close Mode','online':'Online','sms':'SMS'}"
	      	theme	=	"simple"
	      	cssClass	=	"button"
	      	cssStyle	=	"height: 28px;margin-top: 3px;margin-left: -2px;width: 105px;"
	      	onchange	=	"onloadData();"	
	      	>
	      	 </s:select>
	      
	      
	      
	      
	     <sj:autocompleter
	    
	 	id	=	"Subcategory"
	name	=	"category"
	   	list	=	"{'No Data'}"
	 	selectBox	=	"true"
	selectBoxIcon	=	"true"
	onChangeTopics	=	"autocompleteChange"
	onFocusTopics	=	"autocompleteFocus"
	
	
	/>  
	      
	     <s:select 
                                      id	=	"Sub_category"
                                      name	=	"category" 
                                      list	=	"{}"
                                      headerKey	=	"-1"
                                      headerValue	=	"Select Category"
                                     theme	=	"simple"
	      	 cssClass	=	"button"
                                      >
                          </s:select>
	      
	      <s:select  
	    	id	=	"feedRegUser"
	    	name	=	"feedRegUser"
	    	list	=	"FeedRegUser"
	    	headerKey	=	"-1"
	    	headerValue	=	"Select User Name"
	    	theme	=	"simple"
	      	cssClass	=	"button"
	      	cssStyle	=	"height: 28px;margin-top: 3px;margin-left: -2x;width: 105px;"
	      	onchange	=	"onloadData();"	
	      	>
	      	 </s:select>
	       
	       	 </td>	 
  	  <td>
	      	  	 <s:textfield  id="searchValue" name="searchValue" onkeyup="onloadData();" cssClass="button" style="width: 60px;height: 17px;margin-top: -29px;margin-left: -610px;  " Placeholder="Enter Brief"/>
	     	         	     </td>
	     	      </td  >     <td align="right"  style="padding: 0.3%"  >  	     

	     	           	</td>  
	     	       
	     	          	 
	    </tr>
	</tbody>
	 </table> --%>
	
</div>
 
  <div id="viewDataDiv" style="overflow:scroll ; max-height:400px; " align="center" ></div>
 
<!-- Code End for Header Strip -->

</div>
</div>
<%if(userRights.contains("lodgetkt_VIEW")) 
{%>
<div id="TicketDiv" style="overflow:auto;border-radius:6px; height:443px; width:24%; float:left;border: 1px solid #e4e4e5; margin: 0px 6px 0px 5px;border-right: 1px solid rgba(176, 165, 165, 0.54);">
   
 </div>
      <div id="roomDiv"  style="display:none;">
	   
    <div id="roomTktDiv" style="overflow:auto; height:60px;margin-top: -16px;margin-left: 25px;border: 1px solid rgb(255, 255, 255); float:left; background: rgb(250, 250, 250);  border-radius: 8px;box-shadow: 5px 5px 1px -1px rgb(221, 221, 221); width:22%; ">
	 <table width="98%" align="center" style="margin: ">
	<tbody id="TableRoomWise" >

	</tbody>
	</table>
 </div>
 </div>
 <%}%>
 <%if(userRights.contains("anouce_VIEW")) 
{%>
  <div id="newalertDiv" style="overflow:auto; height:310px;border: 1px solid rgb(255, 255, 255);  background: rgb(250, 250, 250);  border-radius: 8px;box-shadow: 5px 5px 1px -1px rgb(221, 221, 221); width:18%; ">
 </div>
  
 <div id="presentEmpDiv" style="overflow:auto; height:142px;margin-top: 11px;border: 1px solid rgb(255, 255, 255);  background: rgb(250, 250, 250);  border-radius: 8px;box-shadow: 5px 5px 1px -1px rgb(221, 221, 221); width:18%; ">
	<table width="100%"><tr class="TitleBorder"><td colspan="2">Resource Availability</td></tr></table>
	<table width="96%" align="center" style="margin: ">
	<tbody id="TableEmpDept" >

	</tbody>
	</table>
 </div>
  <%}%>
 <!--  <div id="freeEmpDiv" style="overflow:auto; height:142px;margin-top: 11px;border: 1px solid rgb(255, 255, 255);  background: rgb(250, 250, 250);  border-radius: 8px;box-shadow: 5px 5px 1px -1px rgb(221, 221, 221); width:18%;">
<table width="100%"><tr class="TitleBorder"><td>Sub Department</td><td align="right">Free Now</td></tr></table>
	<table width="96%" align="center" style="margin: ">
	<tbody id="TablefreEmpDept" >

	</tbody>
	</table>
 </div> -->
</div>
 <!-- main div close -->
  
  <div id="alertPopup"  style="overflow:auto;  background-color: #E5E4E2; display:none;height:150px; width:135px;  margin-left:1223px;margin-top:-163px;  border: 1px solid #e4e4e5; -webkit-border-radius: 6px; -moz-border-radius: 6px; border-radius: 6px; -webkit-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); -moz-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); box-shadow: 0 1px 4px rgba(0, 0, 0, .10) ;">
 <s:label textAlign="center" ><center><font  color="blue" ><b>Ticket For You </b></font></center></s:label>
 <div class="clear"></div>
  <s:label id="ticketLabel1"  textAlign="left" ><font color="#000000" ><b>T.NO:</b></font></s:label> 
  <div class="clear"></div>
 <s:label id="location1"  textAlign="left" ><font color="#000000" ><b>Location:</b></font></s:label>
  <div class="clear"></div>
 <s:label id="requestFor1"  textAlign="left" ><font color="#000000" ><b>Request For:</b></font></s:label>
  <div class="clear"></div>
 <s:label id="TAT"  textAlign="left" ><font color="#000000"><b>TAT:</b></font></s:label>
 <div class="clear"></div>
<sj:a  button="true" cssClass="button" cssStyle=" height:21px; width:21px;margin-top:40px;" onClick="closeAlertPopup();"   buttonIcon="ui-icon-check"></sj:a>
 </div>

 <div id="resolveAlertPopup"  style="overflow:auto; background-color: rgba(135, 232, 177, 0.52); display:none;height:95px; width:223px;  margin-left:1036px;margin-top:-128px;  border: 1px solid #e4e4e5; -webkit-border-radius: 6px; -moz-border-radius: 6px; border-radius: 6px; -webkit-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); -moz-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); box-shadow: 0 1px 4px rgba(0, 0, 0, .10) ;">
 <s:label ><center><font color="" size="2" style="font-weight: bold;color: rgb(40, 102, 102);">Request Resolved</font></center></s:label>
  <div class="clear"></div>
  <div>
 <s:label id="ticketLabel2"  textAlign="left" ><font color="#000000"  ><b>T.NO:</b></font></s:label>
  <div class="clear"></div>
 <s:label id="location2"  textAlign="left" ><font color="#000000"  ><b>Location:</b></font></s:label>
  <div class="clear"></div>
 <s:label id="requestFor2"  textAlign="left" ><font color="#000000"  ><b>Request For:</b></font></s:label>
  <div class="clear"></div>
  <s:label id="actionTaken"  textAlign="left" ><font color="#000000"  ><b>Action Taken:</b></font></s:label>
  </div>
   <div class="clear"></div>
 <sj:a  button="true" cssClass="button" cssStyle=" height:15px; width:30px;margin-top:7px;" onClick="closeAlertPopup();"   buttonIcon="ui-icon-close"></sj:a>
 </div>

</body>
</html>