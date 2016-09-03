<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%String userRights = session.getAttribute("userRights") == null ? "" : session.getAttribute("userRights").toString();%>
<%//String validApp = session.getAttribute("validApp") == null ? "" : session.getAttribute("validApp").toString();
//String empIdofuser = (String) session.getAttribute("empIdofuser");
///String allotTOId=(String)session.getAttribute("AllotToId");
//System.out.println("Emp ID "+empIdofuser+" AllotToID "+allotTOId);
%>
<%String validApp = session.getAttribute("validApp") == null ? "" : session.getAttribute("validApp").toString();
%>
<%

//Code for checking the level of departments or sub departments
String dbName=(String)session.getAttribute("Dbname");
int levelid=1;
String namesofDepts[]=new String[3];
String names=(String)session.getAttribute("deptLevel");
String tempnamesofDepts[]=null;
////deptlevel,dept1Name#dept2Name#
if(names!=null && !names.equalsIgnoreCase(""))
{
	tempnamesofDepts=names.split(",");
	levelid=Integer.parseInt(tempnamesofDepts[0]);
	namesofDepts=tempnamesofDepts[1].split("#");
}

String userTpe=(String)session.getAttribute("userTpe");

%>
<html>
<head>

<s:url id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme"
	customBasepath="%{contextz}" />
<script type="text/javascript"
	src="<s:url value="/js/order/feedback.js"/>"></script>
<link rel="stylesheet" href="bootstrap-3.3.4-dist/css/bootstrap.min.css">
<link rel="stylesheet"
	href="bootstrap-3.3.4-dist/css/bootstrap-theme.min.css">
<STYLE>
.textfieldbgcolr {
	background-color: #E8E8E8;
}
</style>
<STYLE>
.ui-autocomplete-input {
	width: 130px;
	height: 26px;
	margin-top: 10px;
}

.ui-autocomplete {
	height: 200px;
	overflow-y: scroll;
	overflow-x: hidden;
}

.ui-jqgrid .ui-jqgrid-bdiv {
	overflow-y: scroll;
	overflow-x: scroll;
}

.badge {
	background-color: #C00 !important;
	display: inline-block;
	min-width: 10px;
	padding: 3px 7px;
	font-size: 9px;
	font-weight: 700;
	color: #fff;
	text-align: center;
	white-space: nowrap;
	vertical-align: sub;
	/* background-color: #777; */
	border-radius: 10px;
	margin-right: 27%;
	float: right;
}

.cart {
	background: white;
	box-shadow: 0 0 5px rgba(0, 0, 0, 0.2);
	overflow: auto;
}

.icon-cart {
	width: 48px;
	height: 48px;
	position: relative;
	overflow: hidden;
}

.icon-cart .cart-line-1 {
	width: 15%;
	height: 7%;
	position: absolute;
	left: 8%;
	top: 25%;
	-webkit-transform: rotate(5deg);
	-moz-transform: rotate(5deg);
	-ms-transform: rotate(5deg);
	transform: rotate(5deg);
	background-color: #000;
	border-bottom-left-radius: 35%;
}

.icon-cart .cart-line-2 {
	width: 35%;
	height: 7%;
	position: absolute;
	left: 6%;
	top: 40%;
	-webkit-transform: rotate(80deg);
	-moz-transform: rotate(80deg);
	-ms-transform: rotate(80deg);
	transform: rotate(80deg);
	background-color: #000;
}

.icon-cart .cart-line-2:before {
	content: "";
	width: 120%;
	height: 100%;
	position: absolute;
	left: 45%;
	top: -280%;
	-webkit-transform: rotate(-80deg);
	-moz-transform: rotate(-80deg);
	-ms-transform: rotate(-80deg);
	transform: rotate(-80deg);
	background-color: inherit;
}

.icon-cart .cart-line-2:after {
	content: "";
	width: 70%;
	height: 100%;
	position: absolute;
	left: 59%;
	top: -670%;
	background-color: inherit;
	-webkit-transform: rotate(40deg);
	-moz-transform: rotate(40deg);
	-ms-transform: rotate(40deg);
	transform: rotate(40deg);
	border-top-left-radius: 50%;
	border-bottom-left-radius: 25%;
}

.icon-cart .cart-line-3 {
	width: 30%;
	height: 7%;
	position: absolute;
	left: 33%;
	top: 45%;
	background-color: #000;
}

.icon-cart .cart-line-3:after {
	content: "";
	width: 124%;
	height: 100%;
	position: absolute;
	top: -150%;
	left: -5%;
	background-color: inherit;
}

.icon-cart .cart-wheel {
	width: 12%;
	height: 12%;
	background-color: #000;
	border-radius: 100%;
	position: absolute;
	left: 28%;
	bottom: 20%;
}

.icon-cart .cart-wheel:after {
	content: "";
	width: 100%;
	height: 100%;
	background-color: inherit;
	border-radius: 100%;
	position: absolute;
	left: 200%;
	bottom: 0;
}

.cartItem li {
	border: 1px solid #e1e1e1;
	margin: 1%;
	border-left: 3px solid #56CEC3;
	width: 86%;
	margin-left: 3%;
	padding: 1%;
}

.cartItem1 li {
	border: 1px solid #e1e1e1;
	margin: 1%;
	border-left: 3px solid #02A7D7;
	width: 86%;
	margin-left: 3%;
	padding: 1%;
}

.cartItem2 li {
	border: 1px solid #e1e1e1;
	margin: 1%;
	border-left: 3px solid #D37ABC;
	width: 86%;
	margin-left: 3%;
	padding: 1%;
}

.cartItem1 {
	padding: 5%;
}

.cartItem {
	padding: 5%;
}

.closeSpan {
	width: 9px;
	height: 9px;
	float: right;
	margin: 6px 0px 0px 0px;
}

.closeSpan:HOVER {
	background-image: linear-gradient(to bottom, #fff 0, #e0e0e0 100%);
}
</style>
<title>Insert title here</title>
<SCRIPT type="text/javascript"> 
function hideMe(divId)
{
	$("#"+divId).css('display','none');
}
function closeMe()
{
	$("#takeActionGrid").dialog('close');
}

function reload(rowid, result) {
	//MachineorderRequestView();
	onloadDataCPS('', '', 'XR', '' ,'');
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


var PriorityColorUrgent=[];
var PriorityColorStat=[];
var colorRefer = [];
$.subscribe('colorStatus',function(event,data)
{
	for(var i=0;i<PriorityColorUrgent.length;i++){
	$("#"+PriorityColorUrgent[i]).css('color','rgb(251, 163, 218)');
	}
	
	for(var i=0;i<PriorityColorStat.length;i++){
	$("#"+PriorityColorStat[i]).css('background','rgb(255, 140, 108)');
	}

	for(var i=0;i<colorRefer.length;i++){
	$("#"+colorRefer[i]).css('background','#FFDBDB');
	}
	
	
	PriorityColorRoutine=[];
	colorAdmitted=[];
	colorRefer = [];
});

 
 
 


function historyView(cellvalue, options, rowObject) 
{
	return "<a href='#' title='View Data' onClick='viewHistoryOnClick("+rowObject.id+")'><font color='blue'>"+cellvalue+"</font></a>";
}

function viewHistoryOnClick(id) 
{
	$("#takeActionGrid").dialog('open');
	$("#takeActionGrid").html("<br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$("#takeActionGrid").dialog({title: 'Order History for Action ',width: 1000,height: "auto"});
  $.ajax
	({
	type : "post",
	url  : "view/Over2Cloud/CorporatePatientServices/Lodge_Feedback/beforeViewActivityHistoryData?id="+id,
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

 


function priorityVal(cellvalue, options, rowObject) 
{
 	if(cellvalue=="Stat")
	{
	return "<font color='#E60000'><b>"+cellvalue+"</b></font>";
	}
	else if(cellvalue=="Urgent")
	{
	return "<font color='#CC00CC'><b>"+cellvalue+"</b></font>";
	}
	else
	{
	return "<font color='black'><b>"+cellvalue+"</b></font>";

	//$ cellvalue.css('text-shadow: 2px 2px #ff0000');
	
	}


}
 
function takeAction(cellvalue, options, rowObject) 
{

	
	var context_path = '<%=request.getContextPath()%>';

	if(rowObject.priority=='Urgent'){
	//alert("This order is already Resolved");
	//return "<a href='#'><img title='Take Action' src='"+ context_path +"/images/action.jpg' height='20' width='20' onClick='takeActionOnClickResolved("+rowObject.id+")'> </a>";
	//PriorityColorUrgent.push(cellvalue);
	//return "<font color='red'>"+cellvalue+"</font>";
	}

	if(rowObject.priority=='Stat'){
	//alert("This order is already Resolved");
	//return "<a href='#'><img title='Take Action' src='"+ context_path +"/images/action.jpg' height='20' width='20' onClick='takeActionOnClickResolved("+rowObject.id+")'> </a>";
	//PriorityColorStat.push(rowObject.id);
	}
	
	if(rowObject.status=='Resolved' || rowObject.status=='Ignore'){
	//alert("This order is already Resolved");
	return "<a href='#'><img title='Take Action' src='"+ context_path +"/images/action.jpg' height='20' width='20' onClick='takeActionOnClickResolved("+rowObject.id+")'> </a>";
	}
	else{
 	 if(rowObject.assignMchn=='NA-NA')
	return "<a href='#'><img title='Assign Request' src='"+ context_path +"/images/action.jpg' height='20' width='20' onClick='assignOnClick("+rowObject.id+")'> </a>";
	else if(rowObject.assignMchn !='NA-NA')
	return "<a href='#'><img title='Take Action' src='"+ context_path +"/images/assign.png' height='20' width='20' onClick='takeActionOnClick("+rowObject.id+")'> </a>";

	}
 }

$.subscribe('fetchPharmacyData', function(event,data)
	{
 	dateClick();
	 
	});

 

 

function takeActionForPharmacy(cellvalue, options, rowObject) 
{
	var context_path = '<%=request.getContextPath()%>';
	var actionStatus='';
	 
	 
	if(rowObject.status=='Close' || rowObject.status=='Close-P' || rowObject.status=='HIS Cancel')
	{
	return "<a href='#'><img title='Take Action' src='"+ context_path +"/images/action.jpg' height='20' width='20' onClick=takeActionOnPharmaClose()> </a>";
	}
	else
	{
	<%if(userRights.contains("Pharma_Ordered_Action_VIEW") && userRights.contains("Pharma_Close_Action_VIEW")   ) 
	{%>
	if(rowObject.status!='Parked')
	 return "<a href='#'><img title='Take Action' src='"+ context_path +"/images/action.jpg' height='20' width='20' onClick=takeActionOnOrdered('"+rowObject.id+"','"+rowObject.status+"','"+rowObject.encounter_id+"');> </a>";
	 else
	 return "<a href='#'><img title='Take Action' src='"+ context_path +"/images/action.jpg' height='20' width='20' onClick=noAnyTakeActionRight()> </a>";
	 	 
 	<%}%>

 	<%if(userRights.contains("Pharma_Ordered_Action_VIEW")   ) 
	{%>
	if(rowObject.status=='Ordered')
 	 	return "<a href='#'><img title='Take Action' src='"+ context_path +"/images/action.jpg' height='20' width='20' onClick=takeActionOnOrdered('"+rowObject.id+"','Ordered','"+rowObject.encounter_id+"')> </a>";
 	 else
 	 return "<a href='#'><img title='Take Action' src='"+ context_path +"/images/action.jpg' height='20' width='20' onClick=noAnyTakeActionRight()> </a>";
 	 	
 	<%}%>

 	<%if(userRights.contains("Pharma_Close_Action_VIEW")  ) 
	{%>
	if(rowObject.status=='Dispatch' || rowObject.status=='Dispatch-P')
	{
 	 	return "<a href='#'><img title='Take Action' src='"+ context_path +"/images/action.jpg' height='20' width='20' onClick=takeActionOnOrdered('"+rowObject.id+"','"+rowObject.status+"','"+rowObject.encounter_id+"')> </a>";
	}
	else
	 	return "<a href='#'><img title='Take Action' src='"+ context_path +"/images/action.jpg' height='20' width='20' onClick=noAnyTakeActionRight() > </a>";
	
 	<%}%>
 	}
}
 
function takeMedicineDetail(cellvalue, options, rowObject) 
{
 	return "<a href='#' title='View Data' onClick=viewMedicineDetail('"+rowObject.encounter_id+"')><font color='blue'>Medicine</font></a>";	 
}

 
function viewMedicineDetail(id) 
{
	 
	$("#takeActionGrid1").dialog('open');
	$("#takeActionGrid1").html("<br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$("#takeActionGrid1").dialog({title: 'Medicine Detail ',width: 1000,height: "auto"});
  	$.ajax
	({
	type : "post",
	url  : "view/Over2Cloud/CorporatePatientServices/Pharmacy/viewMedicineDetailItem.action?encounterId="+id,
 	success : function(data)
	{
	$("#takeActionGrid1").html(data);
	},
	error : function()
	{
	alert("Error on data fetch");
	} 
	});
}
function pharmaPriorityColorCode(cellvalue, options, rowObject) 
{
	 
	if(rowObject.priority=='Urgent'){
	return "<span class='cellWithoutBackground'   style='display: block;background-color:#E60000';><font color='#ffffff'><b>"+rowObject.priority+"</b></font></span>";
	}
	if(rowObject.priority=='Routine'){
	return "<span class='cellWithoutBackground'   style='display: block;background-color:white';><font color='#000000'><b>"+rowObject.priority+"</b></font></span>";
	}
}
function pharmaLevelColorCode(cellvalue, options, rowObject) 
{
	 
	if(rowObject.level!='Level1'){
	return "<span class='cellWithoutBackground'><font color='#E60000'><b>"+rowObject.level+"</b></font></span>";
	}
	 if(rowObject.level=='Level1'){
	return "<span class='cellWithoutBackground'><font color='#000000'><b>"+rowObject.level+"</b></font></span>";
	}
}
function pharmaHistory(cellvalue, options, rowObject) 
{
	 
	 	return "<a href='#' title='View Data' onClick=viewPharmaHistoryDetail('"+rowObject.id+"')><font color='blue'>"+rowObject.encounter_id+"</font></a>";	 
}


function viewPharmaHistoryDetail(id) 
{
	 
	$("#takeActionGrid1").dialog('open');
	$("#takeActionGrid1").html("<br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$("#takeActionGrid1").dialog({title: 'Order History',width: 1000,height: "auto"});
  	$.ajax
	({
	type : "post",
	url  : "view/Over2Cloud/CorporatePatientServices/Pharmacy/viewPharmaHistoryDetail.action?complianId="+id,
 	success : function(data)
	{
	$("#takeActionGrid1").html(data);
	},
	error : function()
	{
	alert("Error on data fetch");
	} 
	});
} 


function takePharmacyBulkAction() 
{
    var	complainIds = $("#gridedittable").jqGrid('getGridParam','selarrrow');
  	var ids=complainIds.toString().split(",");
  	var flag=true;
   	if(complainIds.length<=1 )
	{
	     	alert("Please select more than one check box...");    
	     	 flag=false;
 	} 
 	 else 
 	 {
 	var temp = jQuery("#gridedittable").jqGrid('getCell',ids[0],'status');
  	for(var i=0;i<ids.length;i++)
	 {
	 var status = jQuery("#gridedittable").jqGrid('getCell',ids[i],'status');
	 if((status!=temp))
	 	 {
	 	 flag=false;
	 	 }
	 if((status!='Dispatch' && status!='Dispatch-P'))
 	 {
 	 flag=false;
 	 }
	 }
 	 }
 	  
	  if(flag)
	 {
  	$("#takeActionGrid").dialog({title: 'Take Bulk Action',width: 1000,height:"auto"});
 	$("#takeActionGrid").dialog('open');
	$("#takeActionGrid").html("<br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	
	  $.ajax
	({
	type : "post",
 	url  : "view/Over2Cloud/CorporatePatientServices/Pharmacy/beforeTakeActionInBulk.action?feedStatus="+status+"&complianId="+complainIds,
	async:false,
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
	  else
	  {
	  alert("Please Select Check Box of Same Type Status(Dispatch or Dispatch-P)....");
	  }
}


function takeActionOnPharmaClose() 
{
	 
	$("#takeActionGrid").dialog({title:"Alert!!!",height:"auto",width:400,dialogClass:'transparent'});
	$("#takeActionGrid").html('<div class="alert alert-danger" role="alert"><span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span><span class="sr-only">Error:</span>This order is already Close!!</div>');
	$("#takeActionGrid").dialog('open');  
 
}
function noAnyTakeActionRight() 
{
	 
	$("#takeActionGrid").dialog({title:"Alert!!!",height:"auto",width:400,dialogClass:'transparent'});
	$("#takeActionGrid").html('<div class="alert alert-danger" role="alert"><span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span><span class="sr-only">Error:</span>No any Rights for this Action!!</div>');
	$("#takeActionGrid").dialog('open');  
 
}

function startStopRefresh(flag) 
{
	 
	if(flag=='pause')
	{
	$("#startRefresh").hide();
	$("#pauseRefresh").show();
	StopRefresh();
	}
	else
	{
	$("#startRefresh").show();
	$("#pauseRefresh").hide();
	dateClick('');
	StartRefresh();
	
	}
	
 
}

getStatusCounter();
function getStatusCounter()
{
	  $.ajax
	({
	type : "post",
	url  : "view/Over2Cloud/CorporatePatientServices/Lodge_Feedback/getCounterStatus.action?fromDate="+$("#minDateValue").val()+"&toDate="+$("#maxDateValue").val()+"&moduleName="+$("#clickFlag").val(),
	
	async:false,
	success : function(data)
	{
	if(data.length>0){
	var total=0;
	$("#statusCounter").empty();
	var temp="";
	if(data[0].Assign>0)
	{
	temp+='<div class="TitleBorder"><h1>Assigned</h1><div class="circle">'+data[0].Assign+'</div></div>';
	total+=parseInt(data[0].Assign);
	}
	if(data[0].UnAssign>0)
	{
	temp+='<div class="TitleBorder"><h1>Un-Assign</h1><div class="circle">'+data[0].UnAssign+'</div></div>';
	total+=parseInt(data[0].UnAssign);
	}
	if(data[0].ACart1>0)
	{
	temp+='<div class="TitleBorder"><h1>Cart 1 Assigned</h1><div class="circle">'+data[0].ACart1+'</div></div>';
	total+=parseInt(data[0].ACart1);
	}
	if(data[0].ACart2>0)
	{
	temp+='<div class="TitleBorder"><h1>Cart 2 Assigned</h1><div class="circle">'+data[0].ACart2+'</div></div>';
	total+=parseInt(data[0].ACart2);
	}
	if(data[0].ACart3>0)
	{
	temp+='<div class="TitleBorder"><h1>Cart 2 Assigned</h1><div class="circle">'+data[0].ACart3+'</div></div>';
	total+=parseInt(data[0].ACart3);
	}
	if(data[0].UACart1>0)
	{
	temp+='<div class="TitleBorder"><h1>Cart 1 Un-Assigned</h1><div class="circle">'+data[0].UACart1+'</div></div>';
	total+=parseInt(data[0].UACart1);
	}
	if(data[0].UACart2>0)
	{
	temp+='<div class="TitleBorder"><h1>Cart 2 Un-Assigned</h1><div class="circle">'+data[0].UACart2+'</div></div>';
	total+=parseInt(data[0].UACart2);
	}
	if(data[0].UACart3>0)
	{
	temp+='<div class="TitleBorder"><h1>Cart 3 Un-Assigned</h1><div class="circle">'+data[0].UACart3+'</div></div>';
	total+=parseInt(data[0].UACart3);
	}
	if(data[0].Ignore>0)
	{
	temp+='<div class="TitleBorder"><h1>Ignore</h1><div class="circle">'+data[0].Ignore+'</div></div>';
	total+=parseInt(data[0].Ignore);
	}
	if(data[0].Resolved>0)
	{
	temp+='<div class="TitleBorder"><h1>Resolved</h1><div class="circle">'+data[0].Resolved+'</div></div>';
	total+=parseInt(data[0].Resolved);
	}
	if(data[0].CancelHIS>0)
	{
	temp+='<div class="TitleBorder"><h1>Cancelled in His</h1><div class="circle">'+data[0].CancelHIS+'</div></div>';
	total+=parseInt(data[0].CancelHIS);
	}
	if(data[0].Snooze>0)
	{
	temp+='<div class="TitleBorder"><h1>Snoozed</h1><div class="circle">'+data[0].Snooze+'</div></div>';
	total+=parseInt(data[0].Snooze);
	}
	if(total>0)
	{
	temp+='<div class="TitleBorder"><h1>Total</h1><div class="circle">'+total+'</div></div>';
	}
	$("#statusCounter").append(temp);
	}
	
	},
	error : function()
	{
	alert("Error on data fetch");
	} 
	}); 
	
}
function takeActionOnClick(complainId) 
{
	//var orderId = jQuery("#gridedittable1").jqGrid('getCell',id[i],'orderid');
	var feedStatus = jQuery("#gridedittable").jqGrid('getCell',complainId,'status');

 $("#takeActionGrid").dialog({title: 'Take Action',width: 1000,height: 400});
	$("#takeActionGrid").html("<br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$("#takeActionGrid").dialog('open');
	  $.ajax
	({
	type : "post",
	url  : "view/Over2Cloud/CorporatePatientServices/Lodge_Feedback/feedActionForTakeActionCPS.action?feedStatus="+feedStatus+"&feedId="+complainId,
	async:false,
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

function takeBulkActionOnClick() 
{
 var complainIds = $("#gridedittable1").jqGrid('getGridParam','selarrrow');
 $("#takeActionGrid").dialog({title: 'Take Action',width: 1000,height: 400});
	$("#takeActionGrid").html("<br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$("#takeActionGrid").dialog('open');
	  $.ajax
	({
	type : "post",
	url  : "view/Over2Cloud/CorporatePatientServices/Lodge_Feedback/feedActionForTakeActionCPS.action?feedStatus=Resolved&feedId="+complainIds,
	async:false,
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


 
 
 
function takeActionOnOrdered(complainId,status,encounterId) 
{
	$("#takeActionGrid").dialog({title: 'Take Action',width: 1000,height: "auto"});
	$("#takeActionGrid").html("<br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$("#takeActionGrid").dialog('open');
	  $.ajax
	({
	type : "post",
	url  : "view/Over2Cloud/CorporatePatientServices/Pharmacy/beforeTakeActionOnOrder.action?feedStatus="+status+"&complianId="+complainId+"&encounterId="+encounterId,
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




function takeActionOnClickResolved(complainId) 
{
	var feedStatus = jQuery("#gridedittable1").jqGrid('getCell',complainId,'status');
	$("#takeActionGrid").dialog({title:"Alert!!!",height:150,width:400,dialogClass:'transparent'});
	$("#takeActionGrid").html('<div class="alert alert-danger" role="alert"><span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span><span class="sr-only">Error:</span>This order is already '+feedStatus+'!!.</div>');
	$("#takeActionGrid").dialog('open');  
 
}

 
function assignOnClick(complainId) 
{
	 	 
	$("#takeActionGrid").dialog({title: 'Assign Request',width: 1000,height: 500});
	$("#takeActionGrid").html("<br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$("#takeActionGrid").dialog('open');
	  $.ajax
	({
	type : "post",
	url  : "view/Over2Cloud/CorporatePatientServices/Lodge_Feedback/feedActionForAssignCPS.action?feedStatus=assign&feedId="+complainId,
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


/* function getfreeEmployee() 
{
	
	$.ajax
	({
	type : "post",
	url  : "view/Over2Cloud/HelpDeskOver2Cloud/Activity_Board/getfreeEmployee.action",
	async:false,
	success : function(data)
	{
	//console.log(data);
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
 */
/* function getFreeEmployeeInfo(subdept){
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
} */

function closeAlertPopup() 
{
	$("#alertPopup").hide('slow');
	$("#resolveAlertPopup").hide('slow');
}

/* function gridCurrentPage()
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
}*/
var delay = (function(){
	  var timer = 0;
	  return function(callback, ms){
	    clearTimeout (timer);
	    timer = setTimeout(callback, ms);
	  };
	})(); 
 function manualEntryOrder(){
	$("#mAdd").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/MachineOrderMaster/AddManualMachineOrder.jsp",
	    success : function(feeddraft) {
	 $("#viewGridTable").css('width','70%');
	 $("#mAdd").show('slow').css('overflow','auto');
	 $("#mAdd").show('slow');
	 $("#cart1").hide('slow');
	 $("#cart2").hide('slow');
	 $("#cart3").hide('slow');
	 $("#manualbuttn").attr('onclick','restoreManualAdd()');
	       $("#mAdd").html(feeddraft).css('background-image','url(images/gradient.png)');
	},
	   error: function() {
	            alert("error");
	        }
	 });
	}

	function restoreManualAdd(){
	 $("#viewGridTable").css('width','98%');
	 $("#mAdd").hide('slow');
	 
	 $("#manualbuttn").attr('onclick','manualEntryOrder()');
	 }

	function priorityClick(data)
	{
	//priority = 'All';
	$("#priorityFlag").val(data);
	ORDERActivityRefresh();
	
	
	
	} 

	function dateClick(data)
	{
	  	ORDERActivityRefresh();
	}
	
	function UpdateDropFilters ()
	{
	
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/CorporatePatientServices/Lodge_Feedback/getFilterStatus.action?fromDate="+$("#minDateValue").val()+"&toDate="+$("#maxDateValue").val(),
	    async:false,
	    success : function(feeddraft) {
	 	console.log(feeddraft);
	$("#orderStatus option").remove();
	 	$("#orderStatus").append($("<option></option>").attr("value",-1).text("Status"));
	$("#orderStatus").append($("<option></option>").attr("value","All").text("All Status"));
	//console.log(feeddraft);
	$(feeddraft).each(function(index)
	{
	   $("#orderStatus").append($("<option></option>").attr("value",this.name).text(this.name));
	});
	    
	},
	   error: function() {
	            alert("error");
	        }
	 });
	
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/CorporatePatientServices/Lodge_Feedback/getFilterNunit.action?fromDate="+$("#minDateValue").val()+"&toDate="+$("#maxDateValue").val(),
	    success : function(feeddraft) {
	    	$('#nursingUnit option').remove();
	$('#nursingUnit').append($("<option></option>").attr("value",-1).text("Select Nursing Unit"));
	    	$(feeddraft).each(function(index)
	{
	   $('#nursingUnit').append($("<option></option>").attr("value",this.id).text(this.name));
	});
	},
	   error: function() {
	            alert("error");
	        }
	 });

	}

	function UpdateDropFiltersForPharmacy()
	{
	 	$.ajax({	
	type : "post",
	url : "view/Over2Cloud/CorporatePatientServices/Pharmacy/updateDropFiltersForPharmacy.action?minDateValue="+$("#minDateValue1").val()+"&maxDateValue="+$("#maxDateValue1").val(),
	success : function(data) 
	{
	console.log(data);
	 	$('#roomNo_widget option').remove();
	$('#roomNo_widget').append($("<option></option>").attr("value",-1).text("Select Room No"));
 	    	$(data).each(function(index)
	{
	   $('#roomNo_widget').append($("<option></option>").attr("value",this.assign_bed_num).text(this.assign_bed_num));
	});
	 


	    $('#uhid_widget option').remove();
	$('#uhid_widget').append($("<option></option>").attr("value",-1).text("Select UHID"));
	     	$(data).each(function(index)
	{
	 
	   $('#uhid_widget').append($("<option></option>").attr("value",this.uhid).text(this.uhid));
	});


	    	$('#patientName_widget option').remove();
	$('#patientName_widget').append($("<option></option>").attr("value",-1).text("Select Patient Name"));
	 	    	$(data).each(function(index)
	{
	 
	   $('#patientName_widget').append($("<option></option>").attr("value",this.patient_name).text(this.patient_name));
	});


	    	
	    	$('#nursingUnit1_widget option').remove();
	$('#nursingUnit1_widget').append($("<option></option>").attr("value",-1).text("Select Nursing Unit"));
	     	$(data).each(function(index)
	{
	 
	   $('#nursingUnit1_widget').append($("<option></option>").attr("value",this.nursing_unit).text(this.nursing_unit));
	});


	    $('#encounterId_widget option').remove();
	$('#encounterId_widget').append($("<option></option>").attr("value",-1).text("Select Ticket No"));
	     	$(data).each(function(index)
	{
	 
	   $('#encounterId_widget').append($("<option></option>").attr("value",this.encounter_id).text(this.encounter_id));
	});
	    	 
	
 	 	},
	error: function() {
	    alert("error");
	}
	});
	}
	
	
function onloadDataCPS(wildData, heart, xray, ultra,nunit)
{
	
	//alert(nunit);
	
	priority = $("#priorityFlag").val();
	var minDateValue = $("#minDateValue").val();
	var maxDateValue = $("#maxDateValue").val();
	var status = $("#orderStatus").val();
	var level=$("#escLevel").val();
	if(heart.length>0){
	$("#clickFlag").val(heart);
	}
	else if (xray.length>0){
	$("#clickFlag").val(xray);
	}	
	else if (ultra.length>0){
	$("#clickFlag").val(ultra);
	}
	else{
	$("#refreshTime").val('30');
	//refreshActivityBoard('5', '10');
	}

	if(wildData.length>0 || heart.length>0 || xray.length>0 || ultra.length>0){
	
	    $.ajax
	({
	type : "post",
	url  : "view/Over2Cloud/CorporatePatientServices/Activity_Board/viewActivityBoardColumnCPS.action?nursingUnit="+nunit+"&dataWild="+wildData+"&heart="+heart+"&xray="+xray+"&ultra="+ultra+"&priority="+priority+"&minDateValue="+minDateValue+"&maxDateValue="+maxDateValue+"&feedStatus="+status+"&escLevel="+level,
	async:false,
	success : function(data)
	{
	 	// console.log(data);
	 	 	$("#viewDataDiv").html(data);
	 	 	$("#GridDiv").show();
	 	 	$("#PharmacySearchCriteriaDiv").hide();
	 	 
	},
	error : function()
	{
	alert("Error on data fetch");
	} 
	});
	
	}
	    else{
	    	$("#GridDiv").hide();
	    	$("#PharmacySearchCriteriaDiv").hide();
	    	
	    }
}

function onloadDataPharmacy(flag)
{
	 
	var priority = $("#priorityFlag").val();
	var nursingUnit = $("#nursingUnit1_widget").val();
	var minDateValue = $("#minDateValue1").val();
	var maxDateValue = $("#maxDateValue1").val();
	var status = $("#pharmacyStatus").val();
	var uhid = $("#uhid_widget").val();
	var roomNo = $("#roomNo_widget").val();
	//var patientName = $("#patientName_widget").val();
	var encounterId = $("#encounterId_widget").val();
	 
 	 if(flag.length>0) 
	$("#clickFlag").val("Pharmacy");
	 else 
	$("#refreshTime").val('30');
	if(flag.length>0){
	 
 	 $.ajax
	({
	type : "post",
	url  : "view/Over2Cloud/CorporatePatientServices/Pharmacy/beforeActivityBoardDataForPharmacy.action?nursingUnit="+nursingUnit+"&priority="+priority+"&minDateValue="+minDateValue+"&maxDateValue="+maxDateValue+"&feedStatus="+status+"&uhid="+uhid+"&roomNo="+roomNo+"&encounterId="+encounterId+"&dataWild="+flag,
	async:false,
	success : function(data)
	{
	   	 	$("#viewDataDivForPharmacy").html(data);
	 	 	 	$("#GridDiv").hide();
	 	 	 	$("#statusCounter").hide();
	 	 	 	 
	 	 	 	$("#PharmacySearchCriteriaDiv").show();
	 	 
	},
	error : function()
	{
	alert("Error on data fetch");
	} 
	});
	
	}
	 else{
	    	$("#GridDiv").hide();
	    	$("#PharmacySearchCriteriaDiv").hide();
	    	$("#statusCounter").show();
	    }
}


 //StopRefresh();

function autoRefreshontime(){
	// var timeFlag = $("#refreshTime").val();
	 refreshActivityBoard(30);
 }
 //autoRefreshontime();
 
//onloadDataCPS(wildData, xray, heart);

//for machine cart
function getCart(){
	 getCartItem(1,'cartItem1');
	 getCartItem(2,'cartItem2');
	 getCartItem(3,'cartItem3');
	 $("#viewGridTable").css('width','70%');
	 $("#cart1").show('slow').css('overflow','auto');
	 $("#cart2").show('slow');
	 $("#cart3").show('slow');
	 $("#mAdd").hide('slow');
	 $("#cartbuttn").attr('onclick','restore()');
	 
 }
 function restore(){
	 $("#viewGridTable").css('width','96%');
	 $("#cart1").hide('slow');
	 $("#cart2").hide('slow');
	 $("#cart3").hide('slow');
	 $("#cartbuttn").attr('onclick','getCart()');
 }
 function getSearchedDataBefore(val,cartItemId,id)
 {
 	 delay(function()
 	{
 	getSearchedData(val,cartItemId,id);
 	    }, 500 );
 }
 
  function googleSearch(data)
 {
 	 delay(function()
 	{
 	onloadDataCPS(data,'','','','');
 	    }, 1500 );
 }
  function googleSearchPharmacy(data)
  {
  	 delay(function()
  	{
  		onloadDataPharmacy(data)
  	    }, 1500 );
  }
 function getSearchedData(val,cartItemId,id)
 {
	 $("#"+cartItemId).empty();
	 $.ajax({
	  type : "post",
	  url  : "view/Over2Cloud/CorporatePatientServices/Activity_Board/viewMachineCart.action",
	    data:{cartId:id,searchString:val.trim()},
	    async:false,
	    success : function(data) 
	    	{
	    //	console.log(data);
	    	$(data).each(function(index)
	    {
	    	 $("#"+cartItemId).append('<li id='+cartItemId+'kk'+data[index].orderId+'>'+data[index].orderUId+': '+data[index].orderName+'<a href="#"><span class="closeSpan"  onclick="removeItem(\''+cartItemId+'kk'+data[index].orderId+'\')"><img alt="closeimage" src="images/hide_feed.png" title="Remove"></span></a></li>');
	    	   	 
	   	 });
	    	  	
	   	 	
	       	},
	    error: function() {
	            alert("error");
	        }
	 });
	 cartItemCount('cart'+id+'Badge',cartItemId);
 }
 function getCartItem(cartId,cartItemId)
 {
	 $("#"+cartItemId).empty();
	 $.ajax({
	  type : "post",
	  url  : "view/Over2Cloud/CorporatePatientServices/Activity_Board/viewMachineCart.action",
	    data:{cartId:cartId},
	    async:false,
	    success : function(data) 
	    	{
	    	
	    	$(data).each(function(index)
	    {
	    	 $("#"+cartItemId).append('<li id='+cartItemId+'kk'+data[index].orderId+'>'+data[index].orderUId+': '+data[index].orderName+'<a href="#"><span class="closeSpan"  onclick="removeItem(\''+cartItemId+'kk'+data[index].orderId+'\')"><img alt="closeimage" src="images/hide_feed.png" title="Remove"></span></a></li>');
	    	   	 
	   	 });
	    	  	
	   	 	
	       	},
	    error: function() {
	            alert("error");
	        }
	 });
	 cartItemCount('cart'+cartId+'Badge',cartItemId);
 }
 function addcart1(cartId,cartItemId){
	 var id = jQuery("#gridedittable1").jqGrid('getGridParam', 'selarrrow');
	
	 var liIds = $('#'+cartItemId+' li').map(function(i,n) {
	 return $(n).attr('id');
	}).get().join(',');
	 var liIdsarray = liIds.split(',');
	if(id.length>0 ){
	
	 for(var i=0;i<=id.length-1;i++)
	 {
	 if(liIdsarray.indexOf(id[i])=='-1'){
	 var orderId = jQuery("#gridedittable1").jqGrid('getCell',id[i],'orderid');
	 var order = jQuery("#gridedittable1").jqGrid('getCell',id[i],'order');
	 var status=jQuery("#gridedittable1").jqGrid('getCell',id[i],'status');
	 var orderIdTxt=$(orderId).text();
	 var escTAT=jQuery("#gridedittable1").jqGrid('getCell',id[i],'openOn');
	 if(status=='Un-assigned')
	 {
	 ORDERActivityRefresh();
	 $("#"+cartItemId).append('<li id='+cartItemId+'kk'+id[i]+'>'+orderId+': '+order+'<a href="#"><span class="closeSpan"  onclick="removeItem(\''+cartItemId+'kk'+id[i]+'\')"><img alt="closeimage" src="images/hide_feed.png" title="Remove"></span></a></li>');
	 
	 saveCartOrder(id[i],cartId,order,orderId,escTAT.trim());
	 }
	 else
	 {
	alert("Please Select Unassigned Order to Add!!!");
	 }
	
	 
	 }
	
	 }
	}
	else
	{
	alert("Please Select Unassigned Order to Add!!!");
	}
	
	cartItemCount('cart'+cartId+'Badge',cartItemId);
	 
	
 }

	
 
 function removeItem(item){
	 $("#"+item).fadeOut('slow').remove();
	  var arr=item.split("kk");
	  //alert(cartId);
	 $.ajax({
	  type : "post",
	  url  : "view/Over2Cloud/CorporatePatientServices/Activity_Board/removeCartItem.action",
	    data:{cartId:arr[0].substring(arr[0].indexOf('m')+1,arr[0].length),orderId:arr[1]},
	    async:false,
	    success : function(data) {
	    //	alert(data);
	    ORDERActivityRefresh();
	    	  	
	   	 	
	       	},
	    error: function() {
	            alert("error");
	        }
	 }); 
 }
 function saveCartOrder(orderId,cartId,orderName,orderUId,escTAT){
	 $.ajax({
	  type : "post",
	  url  : "view/Over2Cloud/CorporatePatientServices/Activity_Board/saveMachineCart.action",
	    data:{cartId:cartId,orderId:orderId,orderName:orderName,orderUId:orderUId,escTAT:escTAT},
	    async:false,
	    success : function(data) {
	    //	alert(data);
	    	
	    	  	
	   	 	
	       	},
	    error: function() {
	            alert("error");
	        }
	 });
 }
 
 function cartItemCount(badgeId,cartItemId)
 {
	 $("#"+badgeId).html($('ul#'+cartItemId+' li').length);
 }
var check=false;
 function hidShowGrid(){
	 if(!check){
	 check=true;
	 $.extend($.jgrid.defaults, {
	    groupingView: {
	        groupCollapse: true
	    }
	});
	onloadDataCPS('', '', 'XR', '' ,'');
	 }else{
	 check=false;
	 $.extend($.jgrid.defaults, {
	    groupingView: {
	        groupCollapse: false
	    }
	});
	onloadDataCPS('', '', 'XR', '' ,'');
	 }
	 
	 }
 function downloadFeedStatusORD(){

	 	var sel_id;
	 	var searchField = null;
	 	var searchValue=null;
	 	sel_id = $("#gridedittable1").jqGrid('getGridParam','selarrrow');
	 	var feedStatus = $("#orderStatus").val();
	var minDateValue = $("#minDateValue").val();
	var maxDateValue = $("#maxDateValue").val();
	var escLevel = $("#escLevel").val();
	 var wildSearch= $("#wildSearch").val();
	 if(wildSearch!=null && wildSearch!='')
	{
	searchValue=wildSearch;
	searchField="All";
	} 
	var nunit=$("#nursingUnit").val();
	
	 	$("#takeActionGrid").dialog({title: 'Check Column',width: 350,height: 600});
	$("#takeActionGrid").dialog('open');
	$("#takeActionGrid").html("<br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");

	$.ajax({
	    type : "post",
	 	 	 url : "view/Over2Cloud/CorporatePatientServices/Activity_Board/feedbackStatusDownloadORD.action?feedStatus="+feedStatus+"&minDateValue="+minDateValue+"&maxDateValue="+maxDateValue+"&escLevel="+escLevel+"&sel_id="+sel_id+"&nursingUnit="+nunit+"&searchField="+searchField+"&searchString="+searchValue,
	    success : function(data) 
	    {
	 	$("#takeActionGrid").html(data);
	},
	   error: function() {
	        alert("error");
	    }
	 });

	 }
 function assignTouser(id,cartItemId){
	 var liIds = $('#'+cartItemId+' li').map(function(i,n) {
	 return $(n).attr('id');
	}).get().join(',');
	 if(liIds.length!=0){
	 $.ajax({
	  type : "post",
	  url  : "view/Over2Cloud/CorporatePatientServices/Lodge_Feedback/ManualCartAssign.action?complaintid="+liIds,
	    success : function(data) {
	    //	alert(data);
	    	$("#takeActionGrid").dialog({title: 'Assign Cart ',width: 650,height: 400});
	    	$("#takeActionGrid").dialog('open');
	    	
	    	$("#takeActionGrid").html(data);
	   	 	
	       	},
	    error: function() {
	            alert("error");
	        }
	 });
	 }else{
	 alert("Opps No Order To Assgin!");
	 }
	
 }

 function showCartDetails(cartNo){

	 $("#showCartDetails").dialog('open');
	$("#showCartDetails").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$("#showCartDetails").load("/over2cloud/view/Over2Cloud/CorporatePatientServices/Lodge_Feedback/detailsCartPage.action?cartID="+cartNo );


 }
 
 	function printData()
	{
	var ids = $("#gridedittable1").jqGrid('getGridParam','selarrrow');
	if(ids.length==0)	{
	alert("Please Select Atleast One Row.");
	}else{
	$("#printSelect").dialog('open');
	$("#printSelect").dialog({title: 'Print Data',width: 600,height: 500});
	$("#printSelect").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	
	$("#printSelect").load("/over2cloud/view/Over2Cloud/CorporatePatientServices/Lodge_Feedback/printCartPage.action?complaintid="+ids+"&dashFor=gridPrint" );
	}
	
	}

 	function requestingInfoDiv1(value)
 	{
 	
 	if(value=="request")
 	{
 	
 	$("#takeActionDiv").show();
 	}
 	else
 	{
 	
 	$("#takeActionDiv").show();
 	
 	$('#status').val("-1");
 	
 	}
 	}
 	function assignTouserCart(id,cartItemId)
 	{
 	$("#takeActionDiv").show();
 	$("#cartName11").val(id);
 	 $("#showCartDetailsDialog").dialog('open');
 	// $("#showCartDetailsDialog").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
 	$.ajax({
 	    type : "post",
 	    url : "view/Over2Cloud/MachineOrder/machineCartTimeView.action?id="+id,
 	    success : function(data) {
 	$("#"+"viewDiv").empty();
 	      var temp="";
 	      temp+='<table width="100%"><tbody><tr><td>From Time</td>';
 	      temp+='<td>'+data[0].from+'</td></tr>';
 	      temp+='<tr><td>To Time</td>';
 	      temp+='<td>'+data[0].to+'</td></tr></tbody></table>';
 	      $("#"+"viewDiv").append(temp);
 	},
 	   error: function() {
 	            alert("error");
 	        }
 	 });
 	}

 	function fetchOrdDetails() 
 	{
 	 	 
 	$("#foceDataFetch").dialog({title: 'Order Fetch From HIS Status',width: 1000,height: 500});
 	$("#foceDataFetch").html("<br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
 	$("#foceDataFetch").dialog('open');
 	  $.ajax
 	({
 	type : "post",
 	url  : "view/Over2Cloud/CorporatePatientServices/Lodge_Feedback/orderFetchDetails.action",
 	success : function(data)
 	{
 	$("#foceDataFetch").html(data);
 	},
 	error : function()
 	{
 	alert("Error on data fetch");
 	} 
 	});  
 	 
 	}


 	function downloadFeedStatus()
 	{ 
 	var priority = $("#priorityFlag").val();
 	var nursingUnit = $("#nursingUnit1_widget").val();
 	var minDateValue = $("#minDateValue1").val();
 	var maxDateValue = $("#maxDateValue1").val();
 	var status = $("#pharmacyStatus").val();
 	var uhid = $("#uhid_widget").val();
 	var roomNo = $("#roomNo_widget").val();
 	//var patientName = $("#patientName_widget").val();
 	var encounterId = $("#encounterId_widget").val();
 	 	
 	$("#takeActionGrid").dialog({title: 'Check Column',width: 350,height: "auto"});
 	$("#takeActionGrid").dialog('open');
 	$("#takeActionGrid").html("<br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");

 	$.ajax({
 	    type : "post",
 	 	url  : "view/Over2Cloud/CorporatePatientServices/Pharmacy/fetchCurrentPharmacy.action?nursingUnit="+nursingUnit+"&priority="+priority+"&minDateValue="+minDateValue+"&maxDateValue="+maxDateValue+"&feedStatus="+status+"&uhid="+uhid+"&roomNo="+roomNo+"&encounterId="+encounterId,
 	 	success : function(data) 
 	    {
 	 	$("#takeActionGrid").html(data);
 	},
 	   error: function() {
 	        alert("error");
 	    }
 	 });
 	}
 	
//getfreeEmployee(); Order Request Status 
</SCRIPT>
<style type="text/css">
.TitleBorder {
	float: left;
    height: 27px;
    border: 1px solid #e0bc27;
    margin: 2px 0px 0px 2px;
    background: white;
    box-shadow: 5px 2px 3px 0px rgba(0, 0, 0, 0.2);
    /* overflow: auto; */
    /* -webkit-transform: rotate(5deg); */
    -moz-transform: rotate(5deg);
    -ms-transform: rotate(5deg);
    transform: rotate(2deg);
    /* background-color: #000; */
    /* border-bottom-left-radius: 35%; */
	
}

.TitleBorder h1 {
	padding-top: 0px;
	margin: 2px 5px 32px 0px;
	
}

h1 {
	font-size: 12px;
	    font-weight: bold;
}

.circle {
	width: 22px;
    height: 22px;
    border-radius: 60px;
    font-size: 12px;
    color: #fff;
    line-height: 22px;
    text-align: center;
    background: #989A8F;
    margin-left: 65px;
    margin-top: -30px;
      box-shadow: 5px 2px 3px 0px rgba(0, 0, 0, 0.2);
    
}

.contents {
	width: 21px;
	color: white;
	background: rgb(33, 140, 255);
	box-shadow: 4px 3px 2px 2px rgb(224, 224, 209);
}

.contentsfree {
	width: 21px;
	color: white;
	background: rgb(231, 113, 92);
	box-shadow: 4px 3px 2px 2px rgb(224, 224, 209);
}

#empinfo tr:nth-child(even) {
	background: #CCC
}

#empinfo tr:nth-child(odd) {
	background: #FFF
}

#empinfo1 tr:nth-child(even) {
	background: #CCC
}

#empinfo1 tr:nth-child(odd) {
	background: #CCC
}
</style>
</head>


<body onunload="StopRefresh();">


	<sj:dialog id="printSelect" title="Print Ticket" autoOpen="false"
	modal="true" height="870" width="1200" showEffect="drop"></sj:dialog>


	<sj:dialog id="takeActionGrid" showEffect="slide" hideEffect="explode"
	autoOpen="false" title="Seek Approval Action" modal="true"
	closeTopics="closeEffectDialog" width="1000" height="400">
	</sj:dialog>
	
	
	<sj:dialog id="foceDataFetch" showEffect="slide" hideEffect="explode"
	autoOpen="false" title="Order Fetch From HIS Status" modal="true"
	closeTopics="closeEffectDialog" width="1000" height="400">
	</sj:dialog>

	<sj:dialog id="showCartDetails" title="Cart Details" autoOpen="false"
	modal="true" height="500" width="800" showEffect="drop">
	</sj:dialog>
	
	<sj:dialog id="takeActionGrid1" showEffect="slide" hideEffect="explode"
	autoOpen="false" title="Seek Approval Action" modal="true"
	closeTopics="closeEffectDialog" width="1000" height="400">
	</sj:dialog>

	<sj:dialog id="printSelect" title="Print Ticket" autoOpen="false"
	modal="true" height="600" width="1200" showEffect="drop">
	<sj:dialog id="feed_status" modal="true" effect="slide"
	autoOpen="false" width="1100" hideEffect="explode"
	position="['center','top']"></sj:dialog>
	<div id="ticketsInfo"></div>
	</sj:dialog>
	
	<sj:dialog id="showCartDetailsDialog" 
	title="Cart Time" 
	autoOpen="false"
	modal="true" 
	height="250" 
	width="377" 
	showEffect="drop">
	<!-- Button -->
	<div class="clear"></div>
   	<div class="fields">
	<div style="width: 100%; text-align: right; padding-bottom: 10px;">
	<button type="button" class="btn btn-default btn-xs"style="margin-left: 287px;"
	title="Timer" onclick="requestingInfoDiv1('request');" >
	<span class="glyphicon glyphicon-pencil" aria-hidden="true"
	style="padding: 0px 0px 3px 1px;"></span>
	</button>
	</div>
	</div>
	<div id="viewDiv"></div>
	<div id="formDiv" >
	  <center><div style="float:center; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;"><div id="errZone" style="float:center; margin-left: 7px"></div></div></center>
	<s:form id="formone" name="formone" namespace="/view/Over2Cloud/MachineOrder" action="machineChartTime" theme="simple" method="post" enctype="multipart/form-data">
	<div id="takeActionDiv" style="display: none;  margin-top: 42px;" >
	<s:hidden id="cartName11" name="cart_name" ></s:hidden>
	<s:property value="cartName"/>
	
	From Time<sj:datepicker name="from_time" id="from_time" placeholder="Enter From Time" readonly="true"  showOn="focus"  timepicker="true"  timepickerOnly="true" timepickerGridHour="3" timepickerGridMinute="5"  timepickerStepMinute="5" showAnim="slideDown" duration="fast" cssClass="textField"  style="width: 119px;"/>
	To Time<sj:datepicker name="to_time" id="to_time" placeholder="Enter To Time" readonly="true" showOn="focus"  timepicker="true"  timepickerOnly="true" timepickerGridHour="3" timepickerGridMinute="5" timepickerStepMinute="5" showAnim="slideDown" duration="fast" cssClass="textField"style="width: 119px;"/>
	
	<div class="clear"></div>
	<div style="width: 100%; text-align: center; padding-bottom: 10px;margin-top: 9px;">
	<sj:submit 
	     targets="result1" 
	     clearForm="true"
	     value="  Update  " 
	     effect="highlight"
	     effectOptions="{ color : '#222222'}"
	     effectDuration="100"
	     button="true"	
	     cssClass="submit"
	     />
	</div>
	</div>
	<!-- End -->
	<div class="clear"></div>
	<sj:div id="resultDiv1"  effect="fold">
	                    <div id="result1"></div>
	                </sj:div>
	</s:form>
	
	</div>
	</sj:dialog>
	
	<div class="clear"></div>
	<div class="middle-content">
	<!--<div class="list-icon">
	<div class="head">Order Request Status </div>
	<div class="head">
	<img alt="" src="images/forward.png" style="margin-top:10px;margin-right:3px; float: left;">Activity 
	</div>
	 	 
	  	
	</div>	
	-->
	</div>
	<div class="clear"></div>

	<div style="border: blue; height: 67px;">


	<%if(userRights.contains("ORDWILD_VIEW") || userTpe.equalsIgnoreCase("M")) 
	{%>

	<div style="float: left;">
	<s:textfield id="wildSearch" name="wildSearch" cssClass="button"
	cssStyle="  width: 122px;height: 36px;margin-top: 1%;"
	Placeholder="Enter Any Value For Machine Order" theme="simple"
	onkeyup="googleSearch(this.value)" onmouseover="StopRefresh()" />
	</div>
	<div style="float: left;">
			<s:textfield id="wildSearch" name="wildSearch" cssClass="button"
				cssStyle="  width: 122px;height: 36px;margin-top: 1%;"
				Placeholder="Enter Any Value For Pharmacy" theme="simple"
				onkeyup="googleSearchPharmacy(this.value)" onmouseover="StopRefresh()" />
		</div>

	<%}%>
<%if(userRights.contains("forceFetch") ) 
	{%>
	<div style="float: right;">
	<a href="#" onclick="fetchOrdDetails();"><img
	alt="" src="images/fetch.png"
	style="margin-top: 0px; width: 69px; height: 59px; padding: 0px;appearance: button;
    -moz-appearance: button;
    -webkit-appearance: button; margin: 0px 5px 0px 0px;"
	title="Fetch data"></a>
	</div>


	<%}%>
	<div id="statusCounter"></div>
	<%if(userRights.contains("ORDECHO_VIEW") || userTpe.equalsIgnoreCase("M")) 
	{%><!--commented by aarif

	<div style="float: right;">
	<a href="#" onclick="onloadDataCPS('', 'CARD', '', '', '');"><img
	alt="" id="CARD" src="images/Echo2272015.png"
	style="margin-top: 0px; width: 69px; height: 59px; padding: 0px;appearance: button;
    -moz-appearance: button;
    -webkit-appearance: button; margin: 0px 5px 0px 0px;"
	title="Echo"></a>
	</div>

	--> <div style="float: right;">
	<!--chsnge_by_aarif_echo_to_hsme_due_to_HISview-->
	<a href="#" onclick="onloadDataCPS('', '', '', 'ECHO' ,'');getStatusCounter();"><img
	alt="" id="CARD" src="images/Echo2272015.png"
	style="margin-top: 0px; width: 69px; height: 59px; padding: 0px;appearance: button;
    -moz-appearance: button;
    -webkit-appearance: button; margin: 0px 5px 0px 0px;"
	title="Echo"></a>
	</div>
	
	
	<%}%>

	<%if(userRights.contains("ORDXR_VIEW") || userTpe.equalsIgnoreCase("M")) 
	{%>
	<div style="float: right;">
	<a href="#" onclick="onloadDataCPS('', '', 'XR', '' ,'');getStatusCounter();"><img
	alt="" src="images/xray2272015.png"
	style="margin-top: 0px; width: 69px; height: 59px; padding: 0px;appearance: button;
    -moz-appearance: button;
    -webkit-appearance: button; margin: 0px 5px 0px 0px;"
	title="X-Ray"></a>
	</div>


	<%}%>


	<%if(userRights.contains("ORDUSG_VIEW") || userTpe.equalsIgnoreCase("M")) 
	{%>
	<div style="float: right;">
	<a href="#" onclick="onloadDataCPS('', '', '', 'ULTRA' ,'');getStatusCounter();"><img
	alt="" src="images/ultrasound2272015.png"
	style="margin-top: 0px; width: 69px; height: 59px; padding: 2px;appearance: button;
    -moz-appearance: button;
    -webkit-appearance: button;margin: 0px 5px 0px 0px;"
	title="Ultrasound"></a>
	</div>


	<%}%>
<%if(userRights.contains("ORDCT_VIEW") || userTpe.equalsIgnoreCase("M")) 
	{%>
	<div style="float: right;">
	<a href="#" onclick="onloadDataCPS('', '', '', 'CT' ,'');getStatusCounter();"><img
	alt="" src="images/ctscan.png"
	style="margin-top: 0px; width: 69px; height: 59px; padding: 2px;appearance: button;
    -moz-appearance: button;
    -webkit-appearance: button;margin: 0px 5px 0px 0px;"
	title="CT Scan"></a>
	</div>


	<%}%>
	
	
	<%if(userRights.contains("Pharma_Activity_Board_VIEW") || userTpe.equalsIgnoreCase("M")) 
	{%>
	<div style="float: right;">
	<a href="#" onclick="onloadDataPharmacy('Pharmacy');"><img
	alt="" src="images/pharmacy.png"
	style="margin-top: 0px; width: 69px; height: 59px; padding: 2px;appearance: button;
    -moz-appearance: button;
    -webkit-appearance: button;margin: 0px 5px 0px 0px;"
	title="Pharmacy"></a>
	</div>


	<%}%>

	</div>
	<s:hidden id="clickFlag" value=""></s:hidden>
	<s:hidden id="priorityFlag" value="All"></s:hidden>
	<s:hidden id="refreshTime" value="30"></s:hidden>

	<div class="clear"></div>

	<div id="GridDiv" style="display: none">
	<div id="viewGridTable"
	style="float: left; height: 600px; width: 100%; margin: -6px 0px 0px 0px; border: 1px solid #e4e4e5; -webkit-border-radius: 6px; -moz-border-radius: 6px; border-radius: 6px;">

	<div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
	<div class="pwie">
	<table class="lvblayerTable secContentbb" border="0"
	cellpadding="0" cellspacing="0" width="100%">
	<tbody>
	<tr>
	<td><sj:datepicker cssStyle="height: 26px; width: 93px;"
	cssClass="button" id="minDateValue" name="minDateValue"
	size="20" maxDate="0" value="%{minDateValue}" readonly="true"
	yearRange="2013:2050" onchange="dateClick(this.value);UpdateDropFilters();"
	showOn="focus" displayFormat="dd-mm-yy"
	Placeholder="Select From Date" /> 
	<sj:datepicker
	cssStyle="height: 26px; width: 93px;" cssClass="button"
	id="maxDateValue" name="maxDateValue" size="20"
	value="%{maxDateValue}" readonly="true" yearRange="2013:2050"
	onchange="dateClick(this.value);UpdateDropFilters();" showOn="focus"
	displayFormat="dd-mm-yy" Placeholder="Select To Date" /> 
	<s:select
	id="orderStatus" name="orderStatus" list="dataMap"
	theme="simple" cssClass="button" headerKey="-1"
	headerValue="Status"
	cssStyle="height: 28px;margin-top: 3px;margin-left: -2px;width: 95px;"
	onchange="dateClick('')">
	</s:select> <!--<sj:datepicker cssStyle="height: 26px; width: 93px;" timepickerOnly="true" timepicker="true" timepickerAmPm="false" cssClass="button" id="fromTime" name="fromTime" size="20"   readonly="true"   showOn="focus"   Placeholder="From Time" />
	     	  <sj:datepicker cssStyle="height: 26px; width: 93px;" timepickerOnly="true" timepicker="true" timepickerAmPm="false" cssClass="button" id="toTime" name="toTime" size="20"   readonly="true"   showOn="focus"   Placeholder="To Time"  />
	             
	       
	  <s:select  
	    	id	=	"orderStatus"
	    	name	=	"orderStatus"
	    	list	=	"dataMap"
	      	theme	=	"simple"
	      	cssClass	=	"button"
	      	headerKey="-1"
	      	headerValue="Status"
	      	cssStyle	=	"height: 28px;margin-top: 3px;margin-left: -2px;width: 95px;"
	      	onchange="dateClick('')"
	       	>
	      	 </s:select><!--
	   	 <s:select  
	    	id	=	"escLevel"
	    	name	=	"escLevel"
	    	list	=	"#{'All':'All Level','Level1':'Level-1','Level2':'Level-2','Level3':'Level-3','Level4':'Level-4','Level5':'Level-5','Level6':'Level-6'}"
	      	theme	=	"simple"
	      	cssClass	=	"button"
	      	cssStyle	=	"height: 28px;margin-top: 3px;margin-left: -2px;width: 96px;"
	        	>
	      	 </s:select>
	       	 
	      	 <s:select  
	    	id	=	"escTAT"
	    	name	=	"escTAT"
	    	list	=	"#{'All':'All Time','onTime':'On Time','offTime':'Off Time'}"
	      	theme	=	"simple"
	      	cssClass	=	"button"
	      	cssStyle	=	"height: 28px;margin-top: 3px;margin-left: -2px;width: 105px;"
	        	>
	      	 </s:select>
	      
	        	         	<s:textfield  id="wildSearch" name="wildSearch"  cssClass="button" cssStyle="width: 100px;height: 25px;margin-top: -28px ;margin-left: 2px;" Placeholder="Enter Any Value" theme="simple"/>
	     	          	--> <s:select id="nursingUnit" name="nursingUnit"
	list="nursingUnitList" theme="simple" cssClass="button"
	cssStyle="height: 28px;width: 155px;"
	onchange="dateClick(this.value); ">
	</s:select> 
	 <s:select  
	    	id	=	"escLevel"
	    	name	=	"escLevel"
	    	list	=	"#{'All':'All Level','Level1':'Level-1','Level2':'Level-2','Level3':'Level-3','Level4':'Level-4','Level5':'Level-5','Level6':'Level-6'}"
	      	theme	=	"simple"
	      	cssClass	=	"button"
	      	cssStyle	=	"height: 28px;margin-top: 3px;margin-left: -2px;width: 100px;"
	      	  	onchange	=	"dateClick(this.value); "	
	        	>
	      	 </s:select>
	       	 
	<sj:a button="true" cssClass="button"
	cssStyle="margin-top: 0px;margin-left: 2px;height:27px; width:31px;margin-right: 2px;"
	title="Refresh" onclick="dateClick(this.value);"
	buttonIcon="ui-icon-refresh"></sj:a> 
	<sj:a  button="true" cssClass="button" cssStyle="margin-top: 0px;margin-left: 2px;height:27px; width:31px;margin-right: 2px;"  title="Download"   onClick="downloadFeedStatusORD();" buttonIcon="ui-icon-arrowstop-1-s"></sj:a>
	<%if(userRights.contains("ORD_Action_VIEW") || true) 
	{%>
	<sj:a  button="true" cssClass="button" cssStyle="margin-top: 0px;margin-left: 2px;height:27px; width:106px;margin-right: 2px;"  title="Download"   onClick="takeBulkActionOnClick();" >Bulk Close</sj:a>	
	<%}%>
	<a href="#"
	style="height: 26px; width: 32px;"> <img
	src="images/SMSGreen12.png" width="20px" height="20px"
	style="margin-top: 3px; float:right; margin-right: 2px"
	title="ALL" onclick="priorityClick('All');"></a> <a href="#"
	style="height: 26px; width: 32px;"> <img
	src="images/SMSPink12.png" width="20px" height="20px"
	style="margin-top: 3px; margin-left: 2px; float:right; margin-right: 2px"
	title="Urgent" onclick="priorityClick('Urgent');"></a> <a
	href="#" style="height: 26px; width: 32px;"> <img
	src="images/SMSWhite12.png" width="20px" height="20px"
	style="margin-top: 3px; margin-left: 2px; float:right; margin-right: 2px"
	title="Routine" onclick="priorityClick('Routine');"></a> <a
	href="#" style="height: 26px; width: 32px;"> <img
	src="images/SMSred12.png" width="20px" height="20px"
	style="margin-top: 3px; margin-left: 2px; float:right; margin-right: 2px"
	title="Stat" onclick="priorityClick('Stat');"></a>
	 <!--<sj:a  button="true" cssClass="button" cssStyle="margin-top: 0px;margin-left: 2px;height:27px; width:31px;margin-right: 2px;"  title="Download"     buttonIcon="ui-icon-arrowstop-1-s"></sj:a>
	       	      	-->
	<!--<sj:a id="manualbuttn" button="true" cssClass="button"
	cssStyle="margin-top: 0px;margin-left: 2px;height:27px; width:31px;margin-right: 2px; float:right;"
	title="Manual Assign" buttonIcon="ui-icon-plus"
	onclick="manualEntryOrder();"></sj:a> 
	--><sj:a button="true"
	style="height: 27px;margin-top:1px;margin-left: 3px;width:31px;margin-right: 2px; float:right;"
	cssClass="button" buttonIcon="ui-icon-print" title="Print"
	onclick="printData()"></sj:a> 
	<sj:a id="cartbuttn"
	button="true" cssClass="button"
	cssStyle="margin-top: 0px;margin-left: 2px;height:27px; width:31px;margin-right: 2px; float:right;"
	title="Bulk Assign" buttonIcon="ui-icon-cart"
	onclick="getCart();"></sj:a></td>
	</tr>
	</tbody>
	</table>


	</div>
	<div onmouseover="StopRefresh()" onmouseout="StartRefresh()"
	style="overflow: scroll; max-height: 575px;" align="center">
	<div id="viewDataDiv"></div>
	</div>
	<!-- Code End for Header Strip -->

	</div>
	</div>

	






	<div style="float: left; width: 27%; margin: -5px 0px 0px 11px;">
	<div class="cart" id="cart1" style="height: 210px; display: none;border-left: 3px solid #2CC3B5;">
	<button type="button" class="btn btn-default btn-xs"
	title="Add Order" onclick="addcart1(1,'cartItem1');">
	<span class="glyphicon glyphicon-plus" aria-hidden="true"
	style="padding: 0px 0px 3px 1px;"></span>
	</button>
	<button type="button" class="btn btn-default btn-xs"
	title="Allot cart" onclick="assignTouser(1,'cartItem1');">
	<span class="glyphicon glyphicon-user" aria-hidden="true"
	style="padding: 0px 0px 3px 1px;"></span>
	</button>
	<b>&nbsp;UG</b>
	<div class="icon-cart" style="float: right"
	onclick="showCartDetails('1')">
	<span id="cart1Badge" class="badge"></span>
	<div class="cart-line-1" style="background-color: #2CC3B5"></div>
	<div class="cart-line-2" style="background-color: #2CC3B5"></div>
	<div class="cart-line-3" style="background-color: #2CC3B5"></div>
	<div class="cart-wheel" style="background-color: #2CC3B5"></div>
	</div>
	<div>
	<s:textfield id="searchCartItem" cssClass="button"
	cssStyle="width:133px;height:18px;margin: -19px 12px 3px 151px;"
	placeholder="Search Order...  "
	onkeyup="getSearchedDataBefore(this.value,'cartItem1',1);"></s:textfield>
	</div>
	
	
	<%if(userRights.contains("timerCart_VIEW")){ %>	<button type="button" class="btn btn-default btn-xs"style="margin-left: 287px;margin-top: -21px;"
	title="Timer" onclick="assignTouserCart(1,'cartTimeDetails');">
	<span class="glyphicon glyphicon-time" aria-hidden="true"
	style="padding: 0px 0px 3px 1px;"></span>
	</button><% }%>
	<ul id="cartItem1" class="cartItem">

	</ul>
	</div>
	<div class="cart" id="cart2" style="height: 210px; display: none;border-right: 3px solid #02A7D7;">
	<button type="button" class="btn btn-default btn-xs"
	title="Add Order" onclick="addcart1(2,'cartItem2');">
	<span class="glyphicon glyphicon-plus" aria-hidden="true"
	style="padding: 0px 0px 3px 1px;"></span>
	</button>
	<button type="button" class="btn btn-default btn-xs"
	title="Allot Cart" onclick="assignTouser(2,'cartItem2');">
	<span class="glyphicon glyphicon-user" aria-hidden="true"
	style="padding: 0px 0px 3px 1px;"></span>
	</button>
	<b>&nbsp;ICU</b>
	<div class="icon-cart" style="float: right"
	onclick="showCartDetails('2')">
	<span id="cart2Badge" class="badge"></span>
	<div class="cart-line-1" style="background-color: #02A7D7"></div>
	<div class="cart-line-2" style="background-color: #02A7D7"></div>
	<div class="cart-line-3" style="background-color: #02A7D7"></div>
	<div class="cart-wheel" style="background-color: #02A7D7"></div>
	</div>
	<div>
	<s:textfield id="searchCartItem" cssClass="button"
	cssStyle="width:133px;height:18px;margin: -19px 12px 3px 151px;"
	placeholder="Search Order...  "
	onkeyup="getSearchedDataBefore(this.value,'cartItem2',2);"></s:textfield>
	</div>
	<%if(userRights.contains("timerCart_VIEW")){ %>	<button type="button" class="btn btn-default btn-xs"style="margin-left: 287px;margin-top: -21px;"
	title="Timer" onclick="assignTouserCart(2,'cartTimeDetails');">
	<span class="glyphicon glyphicon-time" aria-hidden="true"
	style="padding: 0px 0px 3px 1px;"></span>
	</button><% }%>
	<ul id="cartItem2" class="cartItem1">

	</ul>

	</div>
	<div class="cart" id="cart3" style="height: 210px; display: none;border-left: 3px solid #D37ABC;">
	<button type="button" class="btn btn-default btn-xs"
	title="Add Order" onclick="addcart1(3,'cartItem3');">
	<span class="glyphicon glyphicon-plus" aria-hidden="true"
	style="padding: 0px 0px 3px 1px;"></span>
	</button>
	<button type="button" class="btn btn-default btn-xs"
	title="Allot Cart" onclick="assignTouser(3,'cartItem3');">
	<span class="glyphicon glyphicon-user" aria-hidden="true"
	style="padding: 0px 0px 3px 1px;"></span>
	</button>
	<b>&nbsp;IPD</b>
	<div class="icon-cart" style="float: right"
	onclick="showCartDetails('3')">
	<span id="cart3Badge" class="badge"></span>
	<div class="cart-line-1" style="background-color: #D37ABC"></div>
	<div class="cart-line-2" style="background-color: #D37ABC"></div>
	<div class="cart-line-3" style="background-color: #D37ABC"></div>
	<div class="cart-wheel" style="background-color: #D37ABC"></div>
	</div>
	<div>
	<s:textfield id="searchCartItem" cssClass="button"
	cssStyle="width:133px;height:18px;margin: -19px 12px 3px 151px;"
	placeholder="Search Order...  "
	onkeyup="getSearchedDataBefore(this.value,'cartItem3',3);"></s:textfield>
	</div>
	<%if(userRights.contains("timerCart_VIEW")){ %>	<button type="button" class="btn btn-default btn-xs"style="margin-left: 287px;margin-top: -21px;"
	title="Timer" onclick="assignTouserCart(3,'cartTimeDetails');">
	<span class="glyphicon glyphicon-time" aria-hidden="true"
	style="padding: 0px 0px 3px 1px;"></span>
	</button><% }%>
	<ul id="cartItem3" class="cartItem2">

	</ul>
	</div>

	</div>

	</div>
	
	
	
	
	
	
	
	
	
	
	
	
	
<div id="PharmacySearchCriteriaDiv" style="display: none">
	<div id="viewGridTable"
	style="float: left; height: 600px; width: 100%; margin: -6px 0px 0px 0px; border: 1px solid #e4e4e5; -webkit-border-radius: 6px; -moz-border-radius: 6px; border-radius: 6px;">

	<div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
	<div class="pwie">
	<table class="lvblayerTable secContentbb" border="0"
	cellpadding="0" cellspacing="0" width="100%">
	<tbody>
	<tr>
	<td>
	
	
	From:<sj:datepicker cssStyle="height: 26px; width: 93px;"
	cssClass="button" id="minDateValue1" name="minDateValue1"
	size="20" maxDate="0" value="%{minDateValue}" readonly="true"
	yearRange="2013:2050" onchange="dateClick(this.value);UpdateDropFiltersForPharmacy();"
	showOn="focus" displayFormat="dd-mm-yy"
	Placeholder="Select From Date" /> 
	
	To:<sj:datepicker
	cssStyle="height: 26px; width: 93px;" cssClass="button"
	id="maxDateValue1" name="maxDateValue1" size="20"
	value="%{maxDateValue}" readonly="true" yearRange="2013:2050"
	onchange="dateClick(this.value);UpdateDropFiltersForPharmacy(); " showOn="focus"
	displayFormat="dd-mm-yy" Placeholder="Select To Date" /> 
	
	<s:select
	id="pharmacyStatus" name="pharmacyStatus" 
	list="#{'Ordered':'Ordered','Dispatch':'Dispatch','Dispatch-P':'Partial Dispatch','Dispatch Error':'Dispatch Error','Close':'Close','Close-P':'Partial Close','HIS Cancel':'HIS Cancel','Parked':'Parked'}"
	 	theme="simple" cssClass="button" 
	 	headerKey="-1"
	headerValue="Status"
	cssStyle="height: 28px;margin-top: 3px;margin-left: -2px;width: 95px;"
	onchange="dateClick('')">
	</s:select>  
	 
	 	Nursing Unit:<sj:autocompleter 
	 	id="nursingUnit1"
	  	name="nursingUnit1"
	list="nursingUnitListForPharmacy" 
	  	onSelectTopics="fetchPharmacyData"
	       	cssClass="textField" 
	cssStyle="height: 28px;width:100px;"
	  />
	 
	Ticket No:<sj:autocompleter 
	id="encounterId" 
	name="encounterId"
	list="encounterIdList" 
	 	 	onSelectTopics="fetchPharmacyData"
	       	cssClass="textField" 
	cssStyle="height: 28px;width:100px;"
	 />
	 
	 
	 	UHID:<sj:autocompleter 
	 	id="uhid" 
	 	name="uhid"
	list="uhidList" 
	  	onSelectTopics="fetchPharmacyData"
	       	cssClass="textField" 
	cssStyle="height: 28px;width:100px;"
	  />
	  
	  <!-- 
	 	
	Patient Name:<sj:autocompleter 
	id="patientName" 
	name="patientName"
	list="patientNameList" 
	   	onSelectTopics="getPharmacyData"
	       	cssClass="textField" 
	cssStyle="height: 28px;width:100px;"
	  />
	 -->
	
	
	Room No: <sj:autocompleter 
	id="roomNo" 
	name="roomNo"
	list="roomNoList" 
	 	 	onSelectTopics="fetchPharmacyData"
	       	cssClass="textField" 
	cssStyle="height: 28px;width:100px;"
	 />
	 
	 	 <!--
	 	 	<s:select  
	    	id	=	"escLevel1"
	    	name	=	"escLevel"
	    	list	=	"#{'All':'All Level','Level1':'Level-1','Level2':'Level-2','Level3':'Level-3','Level4':'Level-4','Level5':'Level-5','Level6':'Level-6'}"
	      	theme	=	"simple"
	      	cssClass	=	"button"
	      	cssStyle	=	"height: 28px;margin-top: 3px;margin-left: -2px;width: 80px;"
	      	  	onchange	=	"dateClick(this.value);">
	      	 	</s:select>
	       	 
	-->
	
	<div id="startRefresh" >
	<sj:a button="true" cssClass="button"
	cssStyle="margin-top: -30px;margin-left: 2px;height:27px; width:31px;margin-right: 215px;float:right;"
	title="Pause Refresh" onclick="startStopRefresh('pause');"
	buttonIcon="ui-icon-refresh"></sj:a>
	</div>
	
	<div id="pauseRefresh" style="display: none">
	<sj:a button="true" cssClass="button"
	cssStyle="margin-top: -30px;margin-left: 2px;height:27px; width:31px;margin-right: 215px;float:right;"
	title="Start Refresh" onclick="startStopRefresh('start');"
	buttonIcon="ui-icon-pause"></sj:a>
	</div>
	
	<sj:a  button="true" cssClass="button" tabindex="-1" cssStyle="margin-right: 2px;float: right;margin-right: 180px;margin-top: -30px;height:26px; width:30px;"  buttonIcon="ui-icon-arrowstop-1-s" onClick="downloadFeedStatus();" ></sj:a>
	<%if(userRights.contains("Pharma_Close_Action_VIEW")) 
	{%>
	 	<sj:a  button="true" cssClass="button" tabindex="-1" cssStyle="height:23px; width:94px;margin-right: 82px;float: right;margin-top: -30px;"  title="Bulk Action"  onClick="takePharmacyBulkAction();" ><font style="font-size: 12px;">Bulk Close</font></sj:a>
	 	 	<%}%>
	
	 <a href="#"
	style="height: 26px; width: 32px; "> <img
	src="images/SMSGreen12.png" width="20px" height="20px"
	style="  float:right; margin-right: 52px;float: right; margin-top: -30px;"
	title="ALL" onclick="priorityClick('All');"></a> 
	 	 
	 <a
	href="#" style="height: 26px; width: 32px;"> <img
	src="images/SMSWhite12.png" width="20px" height="20px"
	style="  float:right; float: right;margin-right: 28px;margin-top: -30px;"
	title="Routine" onclick="priorityClick('Routine');"></a>
	
	 <a
	href="#" style="height: 26px; width: 32px; "> <img
	src="images/SMSred12.png" width="20px" height="20px"
	style="  float:right ;float: right;margin-right: 5px;margin-top: -30px;"
	title="Urgent" onclick="priorityClick('Urgent');">
	</a>  
	 <!--<sj:a button="true"
	style="height: 27px;margin-top:1px;margin-left: 3px;width:31px;margin-right: 2px; float:right;"
	cssClass="button" buttonIcon="ui-icon-print" title="Print"
	onclick="printData()"></sj:a> 
	
	 -->
	 </td>
	</tr>
	</tbody>
	</table>


	</div>
	<div id="viewDataDivForPharmacy" onmouseover="StopRefresh();" onmouseout="StartRefresh();"  style="overflow: scroll; max-height: 575px;" align="center">
	 
	</div>
	<!-- Code End for Header Strip -->

	</div>
	</div>
 
	</div>
	

</body>
</html>