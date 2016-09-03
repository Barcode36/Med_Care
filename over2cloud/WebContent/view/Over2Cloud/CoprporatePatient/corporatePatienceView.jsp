<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<%
	String userRights = session.getAttribute("userRights") == null ? "" : session.getAttribute("userRights").toString();
%>
<%String validApp = session.getAttribute("validApp") == null ? "" : session.getAttribute("validApp").toString();
%>
<%
String dbName=(String)session.getAttribute("Dbname");
int levelid=1;
String namesofDepts[]=new String[3];
String names=(String)session.getAttribute("deptLevel");
String tempnamesofDepts[]=null;
if(names!=null && !names.equalsIgnoreCase(""))
{
	tempnamesofDepts=names.split(",");
	levelid=Integer.parseInt(tempnamesofDepts[0]);
	namesofDepts=tempnamesofDepts[1].split("#");
}
String userTpe=(String)session.getAttribute("userTpe");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript">

function actionLink(cellvalue, options, rowObject) 
{
	if(rowObject.status=='Appointment' || rowObject.status=='Appointment Parked' || rowObject.status=='Appointment Close'){
		return '<a href="#"><img src="/over2cloud/images/action.jpg" onClick="actionServices('+rowObject.id+')"></img></a>';
	}
	else if(rowObject.status=='Service Out')
	{
	return '<a href="#"><img src="/over2cloud/images/action.jpg" onClick="takeActionOnClickResolved('+rowObject.id+')"></img></a>';
	}
	else if(rowObject.status=='Scheduled' || rowObject.status=='Re-Scheduled')
	{
	return '<a href="#"><img src="/over2cloud/images/reshedule.png" onClick="actionOnAssign('+rowObject.id+')"></img></a>';
	}
	else if(rowObject.status=='Scheduled Parked')
	{
		return '<a href="#"><img src="/over2cloud/images/park.jpg" onClick="actionOnAssign('+rowObject.id+')"></img></a>';
	}
	else if(rowObject.status=='Cancel')
	{
		return '<a href="#"><img src="/over2cloud/images/cancil.jpg" onClick="takeActionOnClickCancel('+rowObject.id+')"></img></a>';
	}
	else{

		return '<a href="#"><img src="/over2cloud/images/serviceIN.jpg" onClick="actionOnAssign('+rowObject.id+')"></img></a>';
	}
}
function takeActionOnClickResolved(complainId) 
{
	 
	var feedStatus = jQuery("#gridedittableeee").jqGrid('getCell',complainId,'services');
	$("#takeActionGrid").dialog({title:"Alert: "+feedStatus,height: "auto",width:400,dialogClass:'transparent'});
	$("#takeActionGrid").html('<div class="alert alert-danger" role="alert"><span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span><span class="sr-only"></span>This order is already been resolved.</div>');
	$("#takeActionGrid").dialog('open');  
 
}


function takeActionOnClickCancel(complainId) 
{
	 
	var feedStatus = jQuery("#gridedittableeee").jqGrid('getCell',complainId,'services');
	$("#takeActionGrid").dialog({title:"Alert: "+feedStatus,height: "auto",width:400,dialogClass:'transparent'});
	$("#takeActionGrid").html('<div class="alert alert-danger" role="alert"><span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span><span class="sr-only"></span>This order is already been Cancel.</div>');
	$("#takeActionGrid").dialog('open');  
 
}
function actionServices(servicesId)
{
    


    var ticket = jQuery("#gridedittableeee").jqGrid('getCell',servicesId,'services');
    $("#cpsServicesIdd").dialog({title: 'Book Schedule For  <b>'+ticket,width: 1000,height: "auto"});
	$("#cpsServicesIdd").html("<br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");

	$("#cpsServicesIdd").dialog('open');
	 $.ajax
		({
		type : "post",
		url  : "view/Over2Cloud/CorporatePatientServices/cpservices/beforeServicesAdd?id="+servicesId,
		success : function(data)
		{
		$("#cpsServicesIdd").html(data);
		},
		error : function()
		{
		alert("Error on data fetch");
		} 
		});
}

function actionOnAssign(servicesId)
{
	
	

	    var ticket = jQuery("#gridedittableeee").jqGrid('getCell',servicesId,'services');
	    $("#cpsServicesIdd").dialog({title: 'Take Action For  <b>'+ticket,width: 1000,height: "auto"});
		$("#cpsServicesIdd").html("<br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$("#cpsServicesIdd").dialog('open');
		 $.ajax
			({
			type : "post",
			url  : "view/Over2Cloud/CorporatePatientServices/cpservices/takeActionOnAssign?id="+servicesId,
			success : function(data)
			{
			$("#cpsServicesIdd").html(data);
			},
			error : function()
			{
			alert("Error on data fetch");
			} 
			});
	
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
function colorCode(cellvalue, options, rowObject)
{
	var str = '';
	if(rowObject.Timer!='00:00' && rowObject.Timer!='0:0')
	{
		str = "<span class='cellWithoutBackground' style=' background-color:#EF00FF'; ><font color='#ffffff'><b>"+cellvalue+"<b></font></span></a>";
	}
	else
	{
		str = "<font color='black'>"+cellvalue+"</font>";
	}
	return str;
}

/*
$.subscribe('colorEscalation',function(event,data)
{
	for(var i=0;i<cooOfficeTicketColorCoding.length;i++)
	{
		$("#"+cooOfficeTicketColorCoding[i]).css('background','rgb(255, 102, 179)');
	}
	cooOfficeTicketColorCoding=[];
	for(var i=0;i<cmdOfficeTicketColorCoding.length;i++)
	{
		$("#"+cmdOfficeTicketColorCoding[i]).css('background','rgb(255, 101, 51)');
	}
	cmdOfficeTicketColorCoding=[];
});
*/
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
	if(ticketId=="CMD")// return '<span class="cellWithoutBackground" style="background-color:#B6B7BA; display: block;">' + cellValue + '</span>'; <span class='cellWithoutBackground' style='background-color:#ff9900'>"+cellvalue+"</span></a>";
	{
		return "<a href='#' title='View History' onClick='viewHistoryOnClickData("+rowObject.id+")'><span class='cellWithoutBackground' style='background-color:green;'><font color='#ffffff'><b>"+cellvalue+"</b></font></span></a>";
	}
	else if(ticketId=="COO")
	{
		return "<a href='#' title='View History' onClick='viewHistoryOnClickData("+rowObject.id+")'><span class='cellWithoutBackground' style=' background-color:#ff9900'; ><font color='#ffffff'><b>"+cellvalue+"<b></font></span></a>";
	}
	else
	{
		return "<a href='#' title='View History' onClick='viewHistoryOnClickData("+rowObject.id+")'><font color='blue'>"+cellvalue+"</font></a>";
	}
	
}

function viewHistoryOnClickData(id)
{
var ticket = jQuery("#gridedittableeee").jqGrid('getCell',id,'ticket');
var patient_name = jQuery("#gridedittableeee").jqGrid('getCell',id,'patient_name');
var pat_mobile = jQuery("#gridedittableeee").jqGrid('getCell',id,'pat_mobile');
	$("#takeActionDilouge").dialog({title: 'Activity History for <b>'+patient_name+'</b> Mobile No: <b>'+pat_mobile+'<b> Ticket ID: '+ticket,width: 1140,height: "auto",position:top});
	$("#takeActionDilouge").html("<br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$("#takeActionDilouge").dialog('open');
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
  <!------------------- Grid Part start -------------------------->                  
   <s:url id="veiwCPSDetail" action="veiwCPSDetail" escapeAmp="false">
    <s:param name="minDateValue" value="%{minDateValue}"></s:param>
	<s:param name="maxDateValue" value="%{maxDateValue}"></s:param>
	<s:param name="feed_status" value="%{feed_status}"></s:param>
	<s:param name="corp_type" value="%{corp_type}"></s:param>
	<s:param name="corp_name" value="%{corp_name}"></s:param>
	<s:param name="services" value="%{services}"></s:param>
	<s:param name="ac_manager" value="%{ac_manager}"></s:param>
	<s:param name="med_location" value="%{med_location}"></s:param>
	<s:param name="patienceSearch" value="%{patienceSearch}"></s:param>
	<s:param name="wildSearch" value="%{wildSearch}"></s:param>
	<s:param name="searchData" value="%{searchData}"></s:param>
	<s:param name="service_manager" value="%{service_manager}"></s:param>
	<s:param name="status" value="%{status}"></s:param>
	
	
    </s:url>
    <s:url id="viewModifyPartNo" action="viewModifyPartNo" /> 
    <sjg:grid 
 		   id="gridedittableeee"
          href="%{veiwCPSDetail}"
          groupField="['status']"
	      groupColumnShow="[false]"
	      groupText="['<b>{0} : {1}</b>']"
          groupColumnShow="[false]"
	      groupCollapse="false"
	      groupOrder="['asc']"
          gridModel="cpsGridData"
          navigator="false"
          navigatorAdd="false"
          navigatorView="false"
          navigatorDelete="true"
          navigatorEdit="true"
          navigatorSearch="true"
          rowList="500,750,1500,2000"
          viewrecords="true"       
          pager="true"
          pagerButtons="true"
          pagerInput="true"   
          multiselect="true"  
          loadingText="Requesting Data..."  
          rowNum="500"
          navigatorSearchOptions="{sopt:['eq','cn']}"
          editurl="%{viewModifyPartNo}"
          navigatorEditOptions="{height:400,width:400}"
          shrinkToFit="false"
          onSelectRowTopics="rowselect"
          sortable="true"
          onCompleteTopics="colorEscalation"
          onEditInlineSuccessTopics="oneditsuccess"
          
          >
         <%if(userRights.contains("cpsAction_VIEW")) 
							{%>
           <sjg:gridColumn 
                 name="imagename"
                 name="imagename"
     		     title="Action"
     		     editable="false"
     		     sortable="false"
    		     align="center"
    		     width="80"
    		     formatter="actionLink"
    		     searchoptions="{sopt:['eq','cn']}"
    		/>
			<%}%>
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


</body>
</html>