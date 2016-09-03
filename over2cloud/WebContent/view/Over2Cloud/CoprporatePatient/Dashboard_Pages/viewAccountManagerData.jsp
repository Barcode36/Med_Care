<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript">

function actionLink(cellvalue, options, rowObject) 
{
	if(rowObject.status=='Stage-1 Pending Request' || rowObject.status=='Stage-1 Parked'){
		return '<a href="#"><img src="/over2cloud/images/action.jpg" onClick="actionServices('+rowObject.id+')"></img></a>';
	}
	else if(rowObject.status=='Resolved')
	{
return '<a href="#"><img src="/over2cloud/images/action.jpg" onClick="takeActionOnClickResolved('+rowObject.id+')"></img></a>';
	}
	else{

		return '<a href="#"><img src="/over2cloud/images/assign.png" onClick="actionOnAssign('+rowObject.id+')"></img></a>';
	}
}
function takeActionOnClickResolved(complainId) 
{
	//$("#takeActionGrid").dialog({title:"Alert!",height:150,width:400,dialogClass:'transparent'});
	//$("#takeActionGrid").html('<div class="alert alert-danger" role="alert"><span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span><span class="sr-only"></span>This order is already been resolved.</div>');
	//$("#takeActionGrid").dialog('open');  

	var feedStatus = jQuery("#gridedittableeee").jqGrid('getCell',complainId,'services');
	$("#takeActionGrid").dialog({title:"Alert: "+feedStatus,height:150,width:400,dialogClass:'transparent'});
	$("#takeActionGrid").html('<div class="alert alert-danger" role="alert"><span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span><span class="sr-only"></span>This order is already been resolved.</div>');
	$("#takeActionGrid").dialog('open');  
 
}
function actionServices(servicesId)
{
    $("#cpsServicesIdd").load("view/Over2Cloud/CorporatePatientServices/cpservices/beforeServicesAdd?id="+servicesId);
    var ticket = jQuery("#gridedittableeee").jqGrid('getCell',servicesId,'services');
    $("#cpsServicesIdd").dialog('open');
    $("#cpsServicesIdd").dialog({title: 'Book Schedule For  <b>'+ticket,width: 1000,height: 350});
}

function actionOnAssign(servicesId)
{
	
	 $("#cpsServicesIdd").load("view/Over2Cloud/CorporatePatientServices/cpservices/takeActionOnAssign?id="+servicesId);
	 var ticket = jQuery("#gridedittableeee").jqGrid('getCell',servicesId,'services');
	    $("#cpsServicesIdd").dialog('open');
	    $("#cpsServicesIdd").dialog({title: 'Take Action For  <b>'+ticket,width: 1000,height: 440});
	
}

function levelCode(cellvalue, options, rowObject)
{

	var str = '';
if(rowObject.level!='Level-1'){
	
	str = "<font color='Red'><b>"+cellvalue+"</b></font>";
	
}
else{
	str = "<font color='black'><b>"+cellvalue+"</b></font>";
}
	return str;
}
var cooOfficeTicketColorCoding=[];
var cmdOfficeTicketColorCoding=[];

function viewHistory(cellvalue, options, rowObject) 
{
	if(rowObject.ticket.indexOf("COO") > -1)
	{	
			cooOfficeTicketColorCoding.push(rowObject.id);
	 }
	if(rowObject.ticket.indexOf("CMD") > -1)
	{	
			cmdOfficeTicketColorCoding.push(rowObject.id);
	 }
	var ticketId=[];
	var ticketId=cellvalue.substring(0, 3);
	if(ticketId=="CMD")
	{
		return "<a href='#' title='View History' onClick='viewHistoryOnClickData("+rowObject.id+")'><span class='cellWithoutBackground' style='background-color:green;'><font color='#ffffff'><b>"+cellvalue+"</b></font></span></a>";
	}
	else if(ticketId=="COO")
	{
		return "<a href='#' title='View History' onClick='viewHistoryOnClickData("+rowObject.id+")'><span class='cellWithoutBackground' style='background-color:#ff9900'><font color='#ffffff'><b>"+cellvalue+"<b></font></span></a>";
	}
	else
	{
		return "<a href='#' title='View History' onClick='viewHistoryOnClickData("+rowObject.id+")'><font color='blue'>"+cellvalue+"</font></a>";
	}
	
}

function viewHistoryOnClickData(id)
{
//alert(id);

var ticket = jQuery("#gridedittableeee").jqGrid('getCell',id,'ticket');
var patient_name = jQuery("#gridedittableeee").jqGrid('getCell',id,'patient_name');
var pat_mobile = jQuery("#gridedittableeee").jqGrid('getCell',id,'pat_mobile');
	$("#takeActionDilouge").dialog('open');
	$("#takeActionDilouge").html("<br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$("#takeActionDilouge").dialog({title: 'Activity History for <b>'+patient_name+'</b> Mobile No: <b>'+pat_mobile+'<b> Ticket ID: '+ticket,width: 1050,height: 550});
  $.ajax
	({
	type : "post",
	url  : "view/Over2Cloud/CorporatePatientServices/cpservices/beforeViewActivityHistoryData?id="+id,
	success : function(data)
	{
	$("#takeActionDilouge").html(data);
	},
	error : function()
	{
	alert("Error on data fetch");
	} 
	});
}
function viewPatientHistory(cellvalue, options, rowObject) 
{
	return "<a href='#' title='View Patience History' onClick='viewPatienceHistoryOnClick("+rowObject.id+")'><font color='blue'>"+cellvalue+"</font></a>";
}
function priorityVal(cellvalue, options, rowObject) 
{
 	if(cellvalue=="VVIP")
	{
		return "<span class='cellWithoutBackground' style='background-color:#E60000'><font color='#ffffff'><b>"+cellvalue+"</b></font></span>";
	}
	else
	{
		return "<font color='black'>"+cellvalue+"</font>";
	}
}
</script>
</head>
<body>
<sj:dialog 
        id="cpsServicesIdd" 
        title="Book Schedule" 
        autoOpen="false" 
        modal="true"
        width="1029"
        height="450"
        >
</sj:dialog>

<sj:dialog id="takeActionGrid" showEffect="slide" hideEffect="explode"
		autoOpen="false" title="Seek Approval Action" modal="true"
		closeTopics="closeEffectDialog" width="1000" height="400">
	</sj:dialog>
	
<div class="clear"></div>
<div class="middle-content">

<div class="clear"></div>
 <div style="width:99%;    margin: 5px 0px 0px 6px; border: 1px solid #e4e4e5; -webkit-border-radius: 6px; -moz-border-radius: 6px; border-radius: 6px; ">
 <div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
 <s:url id="veiwCPSDetail" action="veiwAccountManager" escapeAmp="false">
<s:param name="id" value="%{id}"></s:param>
<s:param name="servId" value="%{servId}"></s:param>
<s:param name="corpId" value="%{corpId}"></s:param>
<s:param name="location" value="%{location}"></s:param>
<s:param name="patient" value="%{patient}"></s:param>
<s:param name="fromDate" value="%{fromDate}"></s:param>
<s:param name="toDate" value="%{toDate}"></s:param>
    </s:url>
    <s:url id="viewModifyPartNo" action="viewModifyPartNo" /> 
    <sjg:grid 
		   id="gridedittableeee"
          href="%{veiwCPSDetail}"
          groupField="['status']"
	      groupColumnShow="[false]"
	      groupText="['<b>{0} - {1}</b>']"
          groupColumnShow="[false]"
	      groupCollapse="false"
	      groupOrder="['asc']"
          gridModel="cpsGridData"
          navigator="false"
          navigatorAdd="false"
          navigatorView="true"
          navigatorDelete="false"
          navigatorEdit="false"
          navigatorSearch="false"
          editinline="false"
          rowList="100,250,500,1000"
          rowNum="15"
          viewrecords="true"       
          pager="true"
          pagerButtons="true"
          rownumbers="true"
          pagerInput="false"   
          multiselect="false"
          navigatorSearchOptions="{sopt:['eq','cn']}"  
          loadingText="Requesting Data..."  
          navigatorEditOptions="{height:250,width:600}"
          editurl="%{viewModifyPartNo}"
          navigatorEditOptions="{closeAfterEdit:true,reloadAfterSubmit:true}"
          shrinkToFit="false"
          autowidth="true"
          loadonce="true"
          onCompleteTopics="colorEscalation"
          >
		<s:iterator value="masterViewPropCPS" id="masterViewPropCPS" >  
		  <sjg:gridColumn 
			name="%{colomnName}"
			index="%{colomnName}"
			title="%{headingName}"
		    width="%{width}"
			align="%{align}"
			editable="%{editable}"
			formatter="%{formatter}"
			search="%{search}"
			hidden="%{hideOrShow}"
			/>
		  </s:iterator>
	</sjg:grid>
    <!-------------------- End grid -------------------------> 


 
<!-- Code End for Header Strip -->

</div>
</div>
</div>
  <!------------------- Grid Part start -------------------------->                  
  


</body>
</html>
                   
                                    