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
System.out.println("userRightsList ::::" +userRights);
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
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<style type="text/css">
.TitleBorder {
	float: left;
    height: 27px;
    border: 1px solid #e0bc27;
    margin: 2px 1px 0px 2px;
    background: #EFC3C3;
    box-shadow: 5px 2px 3px 0px rgba(0, 0, 0, 0.2);
    /* overflow: auto; */
    /* -webkit-transform: rotate(5deg); */
    -moz-transform: rotate(5deg);
    -ms-transform: rotate(5deg);
    transform: rotate(2deg);
    /* background-color: #000; */
    /* border-bottom-left-radius: 35%; */
	
}

.TitleBorderSer {
	float: left;
    height: 27px;
    border: 1px solid #A5E244;
    margin: 2px 1px 0px 2px;
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
    margin-top: -53px;
      box-shadow: 5px 2px 3px 0px rgba(0, 0, 0, 0.2);
    
}

.circleSer {
	width: 22px;
    height: 22px;
    border-radius: 60px;
    font-size: 12px;
    color: #fff;
    line-height: 22px;
    text-align: center;
    background: #2F7BDA;
    margin-left: 65px;
    margin-top: -20px;
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

<script type="text/javascript" src="js/criticalPatient/criticalLodgeValidation.js"></script>
<script type="text/javascript">
 	
  	$.subscribe('rowselect', function(event, data) {
		row = event.originalEvent.id;
	});
	
  	function takeBulkActionOnClick() 
	{
	    var	complainIds = $("#gridedittable").jqGrid('getGridParam','selarrrow');
	   // alert(complainIds);
	  	var ids=complainIds.toString().split(",");
	  	var flag=true;
		 
	 	 if(complainIds.length<=1 )
		{
		     	alert("Please select more than one check box...");    
		     	 flag=false;
	 	} 
	 	 else 
	 		 {
	 		for(var i=0;i<ids.length;i++)
			 {
			 var status = jQuery("#gridedittable").jqGrid('getCell',ids[i],'status');
			 var patient_status = jQuery("#gridedittable").jqGrid('getCell',ids[i],'patient_status');
		 	 if((status!='Not Informed' && status!='Informed-P' )&& patient_status.trim()!='IPD')
		 		 {
		      	alert("Please select only Not Informed/ Informed-P check box for IPD patient only...."); 
		 		 flag=false;
		 		 }
			 }
	 		 }
	 	  
		  if(flag)
		 {
	  	$("#takeActionGrid").dialog({title: 'Take Action',width: 1000,height: 400});
	 	$("#takeActionGrid").dialog('open');
		$("#takeActionGrid").html("<br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		
		  $.ajax
		({
		type : "post",
		url : "view/Over2Cloud/Critical/beforeBulkActionCritical.action?status="+status+"&complainIds="+complainIds,
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
	}

//for manual entry ticket


	clearInt();
 	getStatusCounter();
	fetchDropDownData();
	refreshActivityBoard(); 
	
	<%if(userRights.contains("lodgeBoardCRC_VIEW")) 
	{%>
	criticalTicketLodge();
	maximizeWindow('lodge');

	<%}%>

	<%if(userRights.contains("ticketLodgeGridCRC_VIEW") || userTpe.contains("M") ) 
		
	{%>
	criticalTicketLodge();
	onSearchData();

	<%}%>
<%if(userRights.contains("gridLodgeCRC_VIEW") )  
		
	{%>
	
	onSearchData();
	maximizeWindow('grid');
	<%}%>

	function maximizeWindow(id)
	{
	if(id=='both')
		{
		$("#both").show();
		$("#grid").hide();
		$("#lodge").hide();
 		$("#TicketDiv").show();
		$("#listPart").show();
 		}
	else if(id=='grid')
		{
		$("#grid").show();
		$("#both").hide();
		$("#lodge").hide();
		$("#TicketDiv").show();
 		$("#TicketDiv").hide();

		}
	else if(id=='lodge')
	{
		$("#lodge").show();
		$("#grid").hide();
		$("#both").hide();
			$("#TicketDiv").show();
			$("#listPart").hide();
			
	}
}


	
</script>
<script type="text/javascript">

$( document ).ready(function() {
	$("#hide111").val("max");
});
</script>
</head>
<body>
<sj:dialog
          id="takeActionGrid"
          showEffect="slide" 
          hideEffect="explode" 
          autoOpen="false"
           width="1000"
          height="350"
          draggable="true"
          resizable="true"
           modal="true"
         onCloseTopics="cancelButtonDialog"
          position="center"
          >
</sj:dialog>

<div class="clear"></div>
<div class="middle-content">
<div class="list-icon">
	<div id="statusCounter"></div>
	
	<%if(userRights.contains("lodgeBoardCRC_VIEW")) 
							{%>
		<div id="both">
		<sj:a  button="true" cssClass="button" tabindex="-1" cssStyle="height:22px; width:30px;float:right;"  title="Lodge Window"   onClick="maximizeWindow('lodge');" buttonIcon="ui-icon-transferthick-e-w"></sj:a>
		</div>
		<%}%>
		<%if(userRights.contains("gridLodgeCRC_VIEW")) 
							{%>
		<div id="lodge" style="display: none">
	<sj:a  button="true"  cssClass="button" tabindex="-1" cssStyle="height:22px; width:30px;float:right;"  title="Grid Window"   onClick="maximizeWindow('grid');" buttonIcon="ui-icon-transferthick-e-w"></sj:a>
	</div>
		<%}%>
	<%if(userRights.contains("ticketLodgeGridCRC_VIEW") || userTpe.contains("M") ) 
							{%>
							<div id="both">
		<sj:a  button="true" cssClass="button" tabindex="-1" cssStyle="height:22px; width:30px;float:right;"  title="Lodge Window"   onClick="maximizeWindow('lodge');" buttonIcon="ui-icon-transferthick-e-w"></sj:a>
		</div>
		<div id="lodge" style="display: none">
	<sj:a  button="true"  cssClass="button" tabindex="-1" cssStyle="height:22px; width:30px;float:right;"  title="Grid Window"   onClick="maximizeWindow('grid');" buttonIcon="ui-icon-transferthick-e-w"></sj:a>
	</div>
	<div id="grid" style="display: none">
	<sj:a  button="true" cssClass="button" tabindex="-1" cssStyle="height:22px; width:30px;float:right;"  title="Lodge & Grid Window"   onClick="maximizeWindow('both');" buttonIcon="ui-icon-extlink"></sj:a>
	</div>
	<%}%>
	
	
	</div>	
<div class="clear"></div>
<div style="overflow:hidden;height:100%; width:99%;    margin: 5px 0px 0px 6px; border: 1px solid #e4e4e5; -webkit-border-radius: 6px; -moz-border-radius: 6px; border-radius: 6px; ">
<div id="TicketDiv" style="overflow:hidden;border-radius:6px; height:100%; width:100%; border: 1px solid #e4e4e5; margin: 3px 6px 4px 5px;border-right: 1px solid rgba(176, 165, 165, 0.54);"></div>

<div id="listPart"  style="border-radius:6px; height:99%; width:100%;float:top;border: 1px solid #e4e4e5; margin: 0px 0px 0px 0px;border-right: 1px solid rgba(176, 165, 165, 0.54);">
			<div  class="listviewButtonLayer" id="listviewButtonLayerDiv" style="width: 100%">
			<div >
			<table >
			<tbody><tr><th>
			<table >
			<tbody>
			<tr class="alignright printTitle">
				<td >
				    <sj:datepicker  cssStyle="height: 13px; width: 59px;float:left;" cssClass="button" id="fromDate" name="fromDate" size="20" maxDate="0" value="%{fromDate}" readonly="true"   yearRange="2013:2050" showOn="focus" displayFormat="dd-mm-yy" Placeholder="From Date" onchange="onSearchData();fetchDropDownData();getStatusCounter();"/>
			
			         
				</td>
				<td>
					 <sj:datepicker cssStyle="height: 13px; width: 59px;float:left;" cssClass="button" id="toDate" name="toDate" size="20" value="%{toDate}"   readonly="true" yearRange="2013:2050" showOn="focus" displayFormat="dd-mm-yy" Placeholder="To Date" onchange="onSearchData();fetchDropDownData();getStatusCounter();"/>
				</td>
				<td>
				<s:select
						id="status" 
						name="status" 
						list="#{'AllStatus':'All Status','Informed':'Informed','Not Informed':'Not Informed','Snooze':'Snooze','Informed-P':'Informed-P','Close':'Close','No Further Action Required':'No Further Action'}"
						cssClass="button"
						theme="simple"
						headerKey = "-1"
						headerValue = "Status"
					 	cssStyle="width: 91px;height:25px;float:left;"
						onchange="onSearchData();fetchDropDownData();"
						>
					</s:select>
					</td>
					
					<td>
				<s:select
						id="level" 
						name="level" 
						list="#{'Level1':'Level1','Level2':'Level2','Level3':'Level3','Level4':'Level4','Level5':'Level5'}"
						cssClass="button"
						theme="simple"
						headerKey = "-1"
						headerValue = "Level"
					 	cssStyle="width: 91px;height:25px;float:left;"
						onchange="onSearchData();fetchDropDownData();"
						>
					</s:select>
					</td>
					
					<td>
				<s:select
						id="patient_statusA" 
						name="patient_statusA" 
						list="#{'IPD':'IPD','OPD':'OPD','Other':'Other'}"
						cssClass="button"
						theme="simple"
						headerKey = "-1"
						headerValue = "Type"
					 	cssStyle="width: 91px;height:25px;float:left;"
						onchange="onSearchData();fetchDropDownData();">
						
					</s:select>
					</td>
					
					<td>
				<s:select
						id="patient_speciality" 
						name="patient_speciality" 
						list="{'No Data'}"
						cssClass="button"
						theme="simple"
						headerKey = "-1"
						headerValue = "Specialty"
					 	cssStyle="width: 110px;height:25px;float:left;"
						onchange="fetchDropDownData_NU_DocName();onSearchData();">
						
					</s:select>
					</td>
					
					<td>	
					 <s:select
						id="nursingUnit" 
						name="nursingUnit" 
						list="{'No Nursing Unit'}"
						headerKey="-1"
						headerValue="Nursing"
						cssClass="button"
						theme="simple"
						cssStyle="width: 100px;height:25px;float:left;"
						onchange="fetchDropDownData_Doc_name();onSearchData();"
						tabindex="-1">
					</s:select> 
				</td>
				
				<td>	
					 <s:select
						id="doc_name1" 
						name="doc_name1" 
						list="{'No Doctor'}"
						headerKey="-1"
						headerValue="Doctor"
						cssClass="button"
						theme="simple"
						cssStyle="width: 91px;height:25px;float:left;"
						onchange="onSearchData();"
						tabindex="-1">
					</s:select> 
				</td>
			
				<td>	
					 <s:textfield name="searchStr" id="searchStr" tabindex="-1" placeholder="Search Any Value..." theme="simple" cssClass="button" onkeyup="onSearchData()" cssStyle="height:13px; width:90px;float:left;"/>
				</td>
				
				
				<td>
					<sj:a button="true" cssClass="button" tabindex="-1" title="Refresh" onclick="onSearchData();getStatusCounter();resetFields();fetchDropDownData()" cssStyle="height:22px; width:30px;float:left;" buttonIcon="ui-icon-refresh"/>
				</td>
				
				<td>
					<sj:a  button="true" cssClass="button" tabindex="-1" cssStyle="height:22px; width:30px;float:left;"  title="Download"   onClick="downloadFeedStatus();" buttonIcon="ui-icon-arrowstop-1-s"></sj:a>
				</td>
				<td>
					<sj:a  button="true" cssClass="button" tabindex="-1" cssStyle="margin-top: 0px;height:23px; width:111px;float:left;"  title="Bulk Close"  onClick="takeBulkActionOnClick();" ><font style="font-size: 12px;">Bulk Action</font></sj:a>
				</td>
				<%-- <td>
					<sj:a  button="true" cssClass="button" tabindex="-1" cssStyle="height:22px; width:30px;float:left;"  title="Download"   onClick="downloadFeedStatus();" buttonIcon="ui-icon-arrowstop-1-s"></sj:a>
				</td>
				 --%>

				 
				

</tr></tbody></table>
</th>

</tr></tbody></table></div></div>
<div id="datapart" onmouseover="StopRefresh()" tabindex="0" onmouseout="StartRefresh()" style="overflow:hidden;border-radius:6px; height:100%; width:100%; float:left;border: 1px solid #e4e4e5; margin: 0px 0px 0px 0px;border-right: 1px solid rgba(176, 165, 165, 0.54);">
 </div>
 </div>
 	</div> 
</div>
</body>
</html>