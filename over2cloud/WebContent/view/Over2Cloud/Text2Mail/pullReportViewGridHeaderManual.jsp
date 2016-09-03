<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<%
	String userRights = session.getAttribute("userRights") == null ? "" : session.getAttribute("userRights").toString();
%>
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript" src="<s:url value="/js/communication/Communicationmsgdraft.js"/>"></script>
<script type="text/javascript">
	

	var row=0;
	$.subscribe('rowselect', function(event, data) {
		row = event.originalEvent.id;
	});	
		

function editRow()
{

   if(row==0)
	{
		alert("Please select atleast one row.");
	}
	jQuery("#gridedittable").jqGrid('editGridRow', row ,{height:320, width:425,reloadAfterSubmit:false,closeAfterEdit:true,top:0,left:350,modal:true});
}

function deleteRow()
{

   if(row==0)
	{
		alert("Please select atleast one row.");
	}
	$("#gridedittable").jqGrid('delGridRow',row, {height:120,reloadAfterSubmit:true});
}
function searchRow()
{
	 $("#gridedittable").jqGrid('searchGrid', {sopt:['eq','cn']} );
}
function refreshRow()
{
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/hr/beforeEmailSettingView.action",
	    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
	            alert("error");
	        }
	 });
}
function addNewGroup()
{
	
	$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif><br/>Loading...</center>");
	$.ajax({
	    type : "post",
	    url : "/view/Over2Cloud/WFPM/Communication/beforeGroupAdd.action",
	    success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });

	}

function getAction()
{
	//alert("id0"+id);
	var id = jQuery("#gridedittable").jqGrid('getGridParam', 'selrow');
	alert("id"+id);
	var sts = jQuery("#gridedittable").jqGrid('getCell',id,'history');
	var name = jQuery("#gridedittable").jqGrid('getCell',id,'name');
	var date = jQuery("#gridedittable").jqGrid('getCell',id,'date');
	var mobileNo = jQuery("#gridedittable").jqGrid('getCell',id,'mobileNo');
	var autoAck = jQuery("#gridedittable").jqGrid('getCell',id,'autoAck');
	var speciality = jQuery("#gridedittable").jqGrid('getCell',id,'speciality');
	var location = jQuery("#gridedittable").jqGrid('getCell',id,'location');
	var excecutive = jQuery("#gridedittable").jqGrid('getCell',id,'excecutive');
	alert("sts"+sts);
	if(id.length==0 )
	{
 		alert("Please select atleast one check box...");
 		
 		 
	}
	
 else if(sts=="Discharged")
	{
 		alert("This Patient is already Discharged...");        
 		 
	}
	 
	else{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/Text2Mail/takeActionPull.action?id="+id+"&sts="+sts+"&name="+name+"&date="+date+"&mobileNo="+mobileNo+"&autoAck="+autoAck+"&speciality="+speciality+"&location="+location+"&excecutive="+excecutive,
	    success : function(data) {
     		$("#takeActionDiv12").html(data);
     		
		},
	    error: function() {
          alert("error");
      }
	 });
	$("#takeActionGridPull").dialog({title:'Take Action on Refer Patient',width: 900,height: 320});
	$("#takeActionGridPull").dialog('open');
	}
	
	}

function addDailyConfigure()
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/hr/beforeEmailSettingAdd.action",
	    success : function(data) {
       		$("#data_part").html(data);
       		
		},
	    error: function() {
            alert("error");
        }
	 });

	}
// fro deshboard

function getGraphDataMaxKeyword(type,keyword,todate122,fromdate122,location,spc,cons,kws,chExc)
{
	var todate122=$("#"+todate122).val();
	var fromdate122=$("#"+fromdate122).val();
	 
	  
		$("#dialogOpen").dialog({title: 'Executive Vs No. of Keywords',width: 1000,height: 750 });
		$("#dialogOpen").dialog('open');
		$("#dialogOpen").html("<br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	    
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/Text2Mail/getAnalyticsGraph.action?toDate122="+todate122+"&fromDate122="+fromdate122,
		    success : function(data) {
		     	
	       $("#dialogOpen").html(data);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	 
}



//for location wise search
function chLocation(data){
	var fromDate=$("#fromDate").val();
	var toDate=$("#toDate").val();
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/Text2Mail/beforePullReportView.action?location="+data+"&fromDate122="+fromDate+"&toDate122="+toDate,
	    success : function(feeddraft) {
       $("#datapart").html(feeddraft);
	},
	   error: function() {
            alert("error");
        }
	 });
}function editRequest()
{
	var id = jQuery("#gridedittable").jqGrid('getGridParam', 'selarrrow');
	var feedid    = jQuery("#gridedittable").jqGrid('getGridParam', 'selrow');
	var sts = jQuery("#gridedittable").jqGrid('getCell',id,'status');
	 if(id.length==0 )
	{
 		alert("Please select atleast one check box...");
 		
 		 
	}
	 else if(id.length>1)
		{
	 		alert("Please select only one check box...");        
	 		 
		}
	 else if(sts=="Cancel")
		{
	 		alert("This leave request is already cancel...");        
	 		 
		}
	 else if(sts=="Reschedule")
		{
	 		alert("This leave request is already Reschedule...");        
	 		 
		}
	else{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/leaveManagement/beforeEditView.action?idE="+row,
	    success : function(data) {
     		$("#"+"data_part").html(data);
     		
		},
	    error: function() {
          alert("error");
      }
	 });
	}

	}
//for consultant wise search
function chCons(data){
	var fromDate=$("#fromDate").val();
	var toDate=$("#toDate").val();
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/Text2Mail/beforePullReportView.action?cons="+data+"&fromDate122="+fromDate+"&toDate122="+toDate,
	    success : function(feeddraft) {
       $("#datapart").html(feeddraft);
	},
	   error: function() {
            alert("error");
        }
	 });
}

//search by keyword 

function chKw(data){
	
	var fromDate=$("#fromDate").val();
	var toDate=$("#toDate").val();
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/Text2Mail/beforePullReportView.action?kws="+data+"&fromDate122="+fromDate+"&toDate122="+toDate,
	    success : function(feeddraft) {
       $("#datapart").html(feeddraft);
	},
	   error: function() {
            alert("error");
        }
	 });
}

//search for Excutive
function chExc(data){
	var fromDate=$("#fromDate").val();
	var toDate=$("#toDate").val();
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/Text2Mail/beforePullReportView.action?chExc="+data+"&fromDate122="+fromDate+"&toDate122="+toDate,
	    success : function(feeddraft) {
       $("#datapart").html(feeddraft);
	},
	   error: function() {
            alert("error");
        }
	 });
}
// for spaciality wise search
function chSpc(data){
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/Text2Mail/beforePullReportView.action?spc="+data,
	    success : function(feeddraft) {
       $("#datapart").html(feeddraft);
	},
	   error: function() {
            alert("error");
        }
	 });
}



function count1(){
	var fromDate=$("#fromDate").val();
	var toDate=$("#toDate").val();
	var location=$("#location").val();
	var cons=$("#consultant").val();
	var keyword=$("#keyword").val();
	
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/Text2Mail/counter.action?fromDate122="+fromDate+"&toDate122="+toDate+"&location="+location+"&cons="+cons+"&kws="+keyword,
	    success : function(feeddraft) {
       $("#countrr").html(feeddraft);
	},
	   error: function() {
            alert("error");
        }
	 });
}



function onChangeDate()
{
	var fromDate=$("#fromDate").val();
	var toDate=$("#toDate").val();
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/Text2Mail/beforePullReportView.action?fromDate122="+fromDate+"&toDate122="+toDate,
	    success : function(feeddraft) {
       $("#datapart").html(feeddraft);
	},
	   error: function() {
            alert("error");
        }
	 });
}

//for manual entry ticket

function onloadTicketLodge( )
{
	$("#TicketDiv").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");

	 	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/Text2Mail/beforePullReportLodge.action",
	    success : function(feeddraft) {
       $("#"+"TicketDiv").html(feeddraft);
	},
	   error: function() {
            alert("error");
        }
	 });
}

onChangeDate();
onloadTicketLodge();

function getSearchData()
{
	var type=$("#type").val();
	$.ajax({
 		type :"post",
 		url : "view/Over2Cloud/Text2Mail/beforePullReportView.action",
	    data:"type="+type,
 		success : function(data) 
	    {
			$("#datapart").html(data);
		},
	    error: function()
	    {
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
	var keyword = jQuery("#gridedittable").jqGrid('getCell',id,'incomingKeyword');
	var mobile = jQuery("#gridedittable").jqGrid('getCell',id,'mobileNo');
	
	$("#takeActionGrid").dialog({title: ' History for '+keyword+' from '+mobile,width: 450,height: 600});
	$("#takeActionGrid").html("<br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$("#takeActionGrid").dialog('open');
	$.ajax
	({
		type : "post",
		url  : "view/Over2Cloud/Text2Mail/beforeHistoryView.action?idH="+id,
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


// for autoAck
function viewAutoAck(cellvalue, options, rowObject) 
{
	return "<a href='#' title='View Data' onClick='viewAutoAckClick("+rowObject.id+")'><font color='blue'>"+cellvalue+"</font></a>";
}

 function viewAutoAckClick(id) 
{
	var keyword = jQuery("#gridedittable").jqGrid('getCell',id,'incomingKeyword');
	var mobile = jQuery("#gridedittable").jqGrid('getCell',id,'mobileNo');
	
	$("#takeActionGridAutoAck").dialog({title: ' AutoAck massage  for '+mobile +' on behalf of Keyword '+keyword,width: 550,height: 200});
	$("#takeActionGridAutoAck").html("<br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$("#takeActionGridAutoAck").dialog('open');
	$.ajax
	({
		type : "post",
		url  : "view/Over2Cloud/Text2Mail/AutoAckClickView.action?idA="+id,
		success : function(data)
		{
			$("#takeActionGridAutoAck").html(data);
		},
		error : function()
		{
			alert("Error on data fetch");
		} 
	});
} 
// for excel download 
function getCurrentColumn()
{
	var fromDate=$("#fromDate").val();
	var toDate=$("#toDate").val();
	var location=$("#location").val();
	var cons=$("#consultant").val();
	var keyword=$("#keyword").val();
		$("#takeActionGrid1").dialog({title: 'Check Column ',width: 350,height: 600});
		$("#takeActionGrid1").dialog('open');
		$("#takeActionGrid1").html("<br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax
		({
		    type : "post",
		    url : "view/Over2Cloud/Text2Mail/exclelColumn.action?fromDate122="+fromDate+"&toDate122="+toDate+"&location="+location+"&cons="+cons+"&kws="+keyword,
		    success : function(data) 
		    {
	 			$("#takeActionGrid1").html(data);
			},
		    error: function() 
		    {
		        alert("error");
		    }
		 });
	
	}

// for send mail
function getBeforeSendMail( ) 
{
	var fromDate=$("#fromDate").val();
	var toDate=$("#toDate").val();
	var location=$("#location").val();
	var cons=$("#consultant").val();
	var keyword=$("#keyword").val();
		$("#takeActionGrid1").dialog({title: 'Select Employee to Send Mail from '+fromDate+' to '+toDate,width: 1000,height: 400});
		$("#takeActionGrid1").dialog('open');
		$("#takeActionGrid1").html("<br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax
		({
		    type : "post",
		    url : "view/Over2Cloud/Text2Mail/beforeSendMail.action?fromDate122="+fromDate+"&toDate122="+toDate+"&location="+location+"&cons="+cons+"&kws="+keyword,
		    success : function(data) 
		    {
	 			$("#takeActionGrid1").html(data);
			},
		    error: function() 
		    {
		        alert("error");
		    }
		 });
}


var DischargedColor=[];
var colorAdmitted=[];
var colorRefer = [];

$.subscribe('colorStatus',function(event,data)
{
	for(var i=0;i<DischargedColor.length;i++){
		$("#"+DischargedColor[i]).css('background','#FFDBDB');
	}
	
	for(var i=0;i<colorAdmitted.length;i++){
		$("#"+colorAdmitted[i]).css('background','#FFDBDB');
	}

	for(var i=0;i<colorRefer.length;i++){
		$("#"+colorRefer[i]).css('background','#FFDBDB');
	}
	
	
	DischargedColor=[];
	colorAdmitted=[];
	colorRefer = [];
}); 
function viewStage(cellvalue, options, row)
{
		
	 	if(row.history=='Discharged')
		{
	 		DischargedColor.push(row.id);
		}
		else if(row.history=='Admitted')
		{
			colorAdmitted.push(row.id);
		}
		
		else 
		{
			colorRefer.push(row.id);
		}	
	 	
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

function getAction()
{
	//alert("id0"+id);
	var id = jQuery("#gridedittable").jqGrid('getGridParam', 'selrow');
	var sts = jQuery("#gridedittable").jqGrid('getCell',id,'history');
	var name = jQuery("#gridedittable").jqGrid('getCell',id,'name');
	var date = jQuery("#gridedittable").jqGrid('getCell',id,'date');
	var mobileNo = jQuery("#gridedittable").jqGrid('getCell',id,'mobileNo');
	var autoAck = jQuery("#gridedittable").jqGrid('getCell',id,'autoAck');
	var speciality = jQuery("#gridedittable").jqGrid('getCell',id,'speciality');
	var location = jQuery("#gridedittable").jqGrid('getCell',id,'location');
	var excecutive = jQuery("#gridedittable").jqGrid('getCell',id,'excecutive');
	if(id.length==0 )
	{
 		alert("Please select atleast one check box...");
 		
 		 
	}
	
 else if(sts=="Discharged")
	{
 		alert("This Patient is already Discharged...");        
 		 
	}
	 
	else{
		$("#takeActionGrid12").dialog('open');
		$("#takeActionGrid12").html("<br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/Text2Mail/takeActionPull.action?id="+id+"&sts="+sts+"&name="+name+"&date="+date+"&mobileNo="+mobileNo+"&autoAck="+autoAck+"&speciality="+speciality+"&location="+location+"&excecutive="+excecutive,
	    success : function(data) {
     		$("#takeActionGrid12").html(data);
     		
		},
	    error: function() {
          alert("error");
      }
	 });
	
	}
	
	}


</script>

</head>
<body>
<sj:dialog
          id="takeActionGrid1"
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
          position="center"
          >
</sj:dialog>

<sj:dialog
          id="takeActionGrid12"
          showEffect="slide" 
          hideEffect="explode" 
          openTopics="openEffectDialog"
          closeTopics="closeEffectDialog"
          autoOpen="false"
          title="Take Action on Referal Patient Entry"
          modal="true"
          width="1000"
          height="350"
          draggable="true"
          resizable="true"
          position="center"
          >
</sj:dialog>



<sj:dialog
          id="dialogOpen"
          showEffect="slide" 
    	  hideEffect="explode" 
    	  openTopics="openEffectDialog"
    	  closeTopics="closeEffectDialog"
          autoOpen="false"
          title="Executive Vs No. Of Keywords"
          modal="true"
          width="1000"
          height="750"
          draggable="true"
    	  resizable="true"
          >
</sj:dialog>
<sj:dialog
          id="takeActionGrid"
          showEffect="slide"
          hideEffect="explode"
          autoOpen="false"
          title="history"
          modal="true"
          closeTopics="closeEffectDialog"
          width="20000"
          height="400"
          >
</sj:dialog>

<sj:dialog 
		id="downloadColumnViewPullReport" 
		modal="true" 
		effect="slide" 
		autoOpen="false" 
		width="300" 
		height="530" 
		title="Pull report Column Name" 
		loadingText="Please Wait" 
		overlayColor="#fff" 
		overlayOpacity="1.0" 
		position="['center','center']" >
	<div id="downloadcolumnView"></div>
</sj:dialog>
<div class="clear"></div>

<sj:dialog
          id="takeActionGridAutoAck"
          showEffect="slide"
          hideEffect="explode"
          autoOpen="false"
          title="history"
          modal="true"
          closeTopics="closeEffectDialog"
          width="20000"
          height="400"
          >
</sj:dialog>


<div class="clear"></div>
<div class="middle-content">
<div class="list-icon">
	<div class="head">Keyword Report Data</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div><div class="head">Total:</div>

<div class="head" id="countrr"><s:property value="%{kw}"/></div> <div class="head" id="countrr2"></div> </div>



<div class="clear"></div>
<table width="100%" height="400px">
<tbody>
	<tr   >
	
		<td>
					
					
			
			<div class="listviewBorder" style="width: 90%" >
			<div  class="listviewButtonLayer" id="listviewButtonLayerDiv" style="width: 100%">
			<div >
			<table >
			<tbody><tr></tr><tr><td></td></tr><tr><td> 
			<table >
			<tbody><tr><td ><!-- 
			                   <%if(userRights.contains("vemail_MODIFY")) {%>
								<sj:a id="editButton" title="Edit"  buttonIcon="ui-icon-pencil" cssStyle="height:25px; width:32px" cssClass="button" button="true" onclick="editRow()"></sj:a>
								<%} %>
								<%if(userRights.contains("vemail_MODIFY")) {%>
								<sj:a id="delButton" title="Deactivate" buttonIcon="ui-icon-trash" cssStyle="height:25px; width:32px" cssClass="button" button="true"  onclick="deleteRow()"></sj:a>
								<%} %>
								<sj:a id="searchButton" title="Search" buttonIcon="ui-icon-search" cssStyle="height:25px; width:32px" cssClass="button" button="true"  onclick="searchRow()"></sj:a>
								<sj:a id="refButton" title="Refresh" buttonIcon="ui-icon-refresh" cssStyle="height:25px; width:32px" cssClass="button" button="true"  onclick="refreshRow()"></sj:a>
			                    
								-->
								<sj:datepicker cssStyle="height: 12px; width: 60px;" cssClass="button" id="fromDate" name="fromDate" value="%{fromDate}" size="20" maxDate="0"   readonly="true" onchange="onChangeDate();count1()"   yearRange="2013:2050" showOn="focus" displayFormat="dd-mm-yy" />
								<sj:datepicker cssStyle="height: 12px; width: 60px;" cssClass="button" id="toDate" name="toDate"  value="%{toDate}" size="20" maxDate="0"   readonly="true" onchange="onChangeDate();count1()"   yearRange="2013:2050" showOn="focus" displayFormat="dd-mm-yy" />
			                     
			                     
			                    <%--  <sj:datepicker cssStyle="height: 13px;margin-left: 9px; width: 60px;" onchange="gridActivityData();"  cssClass="button" id="fromDate" name="fromDate" size="20" value="%{fromDate}" maxDate="0"   readonly="true"   yearRange="2013:2050" showOn="focus" displayFormat="dd-mm-yy" Placeholder="From Date"/>
			         <sj:datepicker cssStyle="height: 13px; width: 60px;" onchange="gridActivityData();" cssClass="button" id="toDate" name="toDate" size="20" value="%{toDate}"   readonly="true" yearRange="2013:2050" showOn="focus" displayFormat="dd-mm-yy" Placeholder="To Date"/> --%>
			         
								 
									   <s:select
									   		id="location" 
											name="location" 
											list="locationMap"
											headerKey="-1" 
											headerValue="Location"
											cssClass="button"
										    cssStyle="margin-top: -30px;margin-left:333px; width:105px;"
										    onchange="chLocation(this.value);count1();">
									    </s:select>
									    
									     <s:select
									     
									     	id="Speciality" 
											name="Speciality" 
											list="SpecialityMap"
											headerKey="-1" 
											headerValue="Speciality"
											cssClass="button"
										    cssStyle="margin-top: -30px;margin-left:223px; width:109px;"
										    onchange="chSpc(this.value),count()">
									    </s:select>
									    
									 <%-- 
									    <s:select
									     
									     	id="Exceutive" 
											name="Exceutive" 
											list="%{exe}"
											headerKey="-1" 
											headerValue="Select Exceutive"
											cssClass="button"
										    cssStyle="margin-top: -30px;margin-left:370px"
										    onchange="chExc(this.value),count1()"
										    >
									    </s:select> --%>
									    
									    <s:select
											id="consultant" 
											name="consultant" 
											list="consultantMap"
											headerKey="-1" 
											headerValue="Consultant"
											cssClass="button"
										    cssStyle="margin-top: -30px;margin-left:3px; width:109px;"
										    onchange="chCons(this.value),count1()">
									    </s:select>
									    
									     <s:select
											id="keyword" 
											name="keyword" 
											list="#{'All Status':'All Status','Inform':'Inform','Not Inform':'Not Inform','Escaleted':'Escaleted','Closed':'Closed'}"
											headerKey="-1" 
											headerValue="Status"
											cssClass="button"
											cssStyle="margin-top: -30px;margin-left:113px; width:109px;"
										    onchange="chKw(this.value),count1()">
									    </s:select>
									    
									    <sj:a  button="true" 
							           cssClass="button" 
							           title="Send Mail"
							           value=" Register " 
							           cssStyle="height: 23px;width: 89px;margin-left: 3px;"
							           onclick="getAction()">Action</sj:a>
									    
									    <sj:a  button="true" 
							           cssClass="button" 
							           title="Download"
							           buttonIcon="ui-icon-arrowstop-1-s" 
							           cssStyle="height: 23px;width: 32px;margin-left: 2px;"
							           onclick="getCurrentColumn()"/>
							           
							           <sj:a  button="true" 
							           cssClass="button" 
							           title="Send Mail"
							           value=" Register " 
							           cssStyle="height: 23px;width: 89px;margin-left: 3px;"
							           onclick="getBeforeSendMail()">Send Mail</sj:a>
							           
							           
							           
							            <sj:a  button="true" 
							           cssClass="button" 
							           title="Dashboard"
							           
							             cssStyle="height: 23px;width: 90px ;align:right;"
							           onclick="getGraphDataMaxKeyword('type','kewword','toDate','fromDate','location','spc','cons','kws','chExc');">Dashboard</sj:a>
							           
				        
				           
				          
						    
 
					
					
</td>



</tr></tbody></table>
</td>
<td class="alignright printTitle">

<%if(userRights.contains("vemail_ADD")) {%>
<!--<sj:a id="addButton"  button="true"  buttonIcon="ui-icon-plus" cssClass="button" onclick="addDailyConfigure();">Add</sj:a>
--><%} %>
</td> 
</tr></tbody></table></div></div>


<div id="datapart"></div>

</div>
		</td>
		
		<td>
			<div id="TicketDiv" style="  width: 153%; margin-left: -94%;" >
 			</div>
		</td>
	</tr>
</tbody>

</table>

	

</body>
</html>

