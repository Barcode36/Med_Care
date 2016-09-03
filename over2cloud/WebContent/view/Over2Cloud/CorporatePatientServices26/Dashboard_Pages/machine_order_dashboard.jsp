<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjc" uri="/struts-jquery-chart-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<% 
String userTpe=(String)session.getAttribute("userTpe");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<s:url id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
 <script type="text/javascript" src="<s:url value="/js/dashboard/machineorder/dashboardbar.js"/>"></script>
 <link  type="text/css" href="amcharts/plugins/export/export.css" rel="stylesheet">
<link rel="stylesheet" href="bootstrap-3.3.4-dist/css/bootstrap.min.css">
<link rel="stylesheet" href="bootstrap-3.3.4-dist/css/bootstrap-theme.min.css">
     <script src="amcharts/amcharts.js"></script>
	<script src="amcharts/serial.js"></script>
	<script src="amcharts/themes/light.js"></script>
	<script src="amcharts/amstock.js"></script>
	<script src="amcharts/pie.js"></script>
    <script  src="amcharts/plugins/export/export.js"></script>
<s:url var="chartDataUrl" action="json-chart-data"/>
<script type="text/javascript" src="js/html2canvas.js"></script>
<script type="text/javascript" src="js/canvas2image.js"></script>
<link rel="stylesheet" type="text/css" href="css/chartdownload.css" />
<link rel="stylesheet" href="jqwidgets/styles/jqx.base.css" type="text/css">
<script type="text/javascript" src="js/html2canvas.js"></script>
<script type="text/javascript" src="js/canvas2image.js"></script>
<script type="text/javascript" src="jqwidgets/jqxcore.js"></script>
<script type="text/javascript" src="jqwidgets/jqxdata.js"></script>
<script type="text/javascript" src="jqwidgets/jqxchart.js"></script>
<script type="text/javascript" src="jqwidgets/jqxswitchbutton.js"></script>
<script type="text/javascript" src="jqwidgets/jqxcheckbox.js"></script>
<script type="text/javascript" src="jqwidgets/jqxbuttons.js"></script>	
 
<script type="text/javascript">
$(document).ready(function(){
	document.getElementById('sdate').value=$("#hfromDate").val();
    document.getElementById('edate').value=$("#htoDate").val();
    $('#button2').jqxSwitchButton({ height: 20, width: 81, checked: false });
    $('#button2').bind('checked', function (event) { 
    	 $('#esc').val("false");
    	 if(maxDivId1=='3rdStackedBar')
    	 {
    		
    		showNursingStackedBar('nursingChart', '', '', 'total', 'All', '#09A1A4',locationId,locationName);
    	 }
    	else if(maxDivId1=='4thStackedBar')
    	 {
    		showNursingStackedBarOrderType('nursingChart', '', '', 'total', 'All', '#09A1A4',locationId,locationName);
    	 }
    	else if(maxDivId1=='7thStackedBar')
    	 {
    		
    		showFloorStatusStackedBar('nursingChart', '', '', '', 'All', '#09A1A4');
    	 }
    	else if(maxDivId1=='8thStackedBar')
    	 {
    		showFloorOrderTypeStackedBar('nursingChart', '', '', '', 'All', '#09A1A4');
    	 }
    	else if(maxDivId1=='9thStackedBar')
    	 {
    		showTimeStatusStackedBar('nursingChart', '', '', '', 'All', '#09A1A4');
    	 }
    	else if(maxDivId1=='10thStackedBar')
    	 {
    		showTimeFloorStackedBar('nursingChart', '', '', 'total', 'All', '#09A1A4',timeId,timeName);
    	 }
    	else if(maxDivId1=='11thStackedBar')
    	 {
    		 showTimeFloorNursingUnitStackedBar('nursingChart', '', '','total', 'All', '#09A1A4',locationId,locationName,timeId,timeName);
     	 }
    	 else if(maxDivId1=='3rdTable')
    	 {
    		 showData('nursingChart','','status');
    	 }
    	 else if(maxDivId1=='4thTable')
    	 {
    		 showData('nursingChart','','order');
    	 }
    	 else if(maxDivId1=='7thTable')
    	 {
    		 showData('nursingChart','','statusFloor');
    	 }
    	 else if(maxDivId1=='8thTable')
    	 {
    		 showData('nursingChart','','orderFloor');
    	 }
    	 else if(maxDivId1=='9thTable')
    	 {
    		 showData('nursingChart','','statusTime');
    	 }
    	 else if(maxDivId1=='10thTable')
    	 {
    		 showData('nursingChart','','floorTime');
    	 }
    	 else if(maxDivId1=='11thTable')
    	 {
    		 showData('nursingChart','','nursingUnitTime');
    	 }
   	
   });
   $('#button2').bind('unchecked', function (event) { 
	   $('#esc').val("true");
	   if(maxDivId1=='3rdStackedBar')
		 {
			
			showNursingStackedBar('nursingChart', '', '', 'total', 'All', '#09A1A4',locationId,locationName);
		 }
		else if(maxDivId1=='4thStackedBar')
		 {
			showNursingStackedBarOrderType('nursingChart', '', '', 'total', 'All', '#09A1A4',locationId,locationName);
		 }
		else if(maxDivId1=='7thStackedBar')
		 {
			
			showFloorStatusStackedBar('nursingChart', '', '', '', 'All', '#09A1A4');
		 }
		else if(maxDivId1=='8thStackedBar')
		 {
			showFloorOrderTypeStackedBar('nursingChart', '', '', '', 'All', '#09A1A4');
		 }
		else if(maxDivId1=='9thStackedBar')
		 {
			showTimeStatusStackedBar('nursingChart', '', '', '', 'All', '#09A1A4');
		 }
		else if(maxDivId1=='10thStackedBar')
		 {
			showTimeFloorStackedBar('nursingChart', '', '', 'total', 'All', '#09A1A4',timeId,timeName);
		 }
		else if(maxDivId1=='11thStackedBar')
		 {
			 showTimeFloorNursingUnitStackedBar('nursingChart', '', '','total', 'All', '#09A1A4',locationId,locationName,timeId,timeName);
	 	 }
		 else if(maxDivId1=='3rdTable')
		 {
			 showData('nursingChart','','status');
		 }
		 else if(maxDivId1=='4thTable')
		 {
			 showData('nursingChart','','order');
		 }
		 else if(maxDivId1=='7thTable')
		 {
			 showData('nursingChart','','statusFloor');
		 }
		 else if(maxDivId1=='8thTable')
		 {
			 showData('nursingChart','','orderFloor');
		 }
		 else if(maxDivId1=='9thTable')
		 {
			 showData('nursingChart','','statusTime');
		 }
		 else if(maxDivId1=='10thTable')
		 {
			 showData('nursingChart','','floorTime');
		 }
		 else if(maxDivId1=='11thTable')
		 {
			 showData('nursingChart','','nursingUnitTime');
		 }
   	
   });
  
});
 
chartTab();
function chartTab()
{
	StatckedChartStatus('jqxChart', '', '', '', 'All', '#09A1A4');
 	showLeveStackedBar('mytest', '', '', '', 'All', '#09A1A4');
	showFloorStatusStackedBar('nursingChart', '', '', '', 'All', '#09A1A4');
}
 
 
function getDataForAllBoard()
{
	
 
	StatckedChartStatus('jqxChart', '', '', '', 'All', '#09A1A4');
	showLeveStackedBar('mytest', '', '', '', 'All', '#09A1A4');
	showFloorStatusStackedBar('nursingChart', '', '', '', 'All', '#09A1A4');
	
}
function getsubdeptChart(deptId,deptName)
{
	beforeMaximiseFilter('Ticket Count Status For: '+deptName,400,900,'Ticket Count Status','G',deptId,"H");
}
function getTicketByDept( )
{
	 	if(maxDivId3=='1stStackedBar')
	 {
	 	StatckedChartStatus('jqxChart','','','','All','#09A1A4');
	 }
	 
	 else if(maxDivId3=='1stTable')
	 {
		 showData('jqxChart','','');
	 }
}

function getLevelByDept( )
{
	if(maxDivId2=='2ndStackedBar')
	 {
		showLeveStackedBar('mytest','','','','All','#09A1A4');
		
	 }
	 
	if(maxDivId2=='6thStackedBar')
	 {
		StatckedChartLevelLocation('mytest','','','','All','#09A1A4');
		
	 }
	 else if(maxDivId2=='2ndTable')
	 {
		 showData('mytest','statusLevel','');
	 }
	 else if(maxDivId2=='6thTable')
	 {
		 showData('mytest','locationLevel','');
	 }

}
function fetchMachineSerial(targetId,deptList){
 	var targetid=targetId;
	var deptId=$("#"+deptList).val();
	 		$.ajax({
			type :"post",
			url  : "view/Over2Cloud/CorporatePatientServices/Dashboard_Pages/fetchMachineSerial.action?filterDeptId="+deptId,
			success : function(empData){
			$('#'+targetid+' option').remove();
			$('#'+targetid).append($("<option></option>").attr("value",-1).text("All Machine"));
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
function getNursingByDept()
{
	 if(maxDivId1=='3rdStackedBar')
	 {
		
		showNursingStackedBar('nursingChart', '', '', 'total', 'All', '#09A1A4',locationId,locationName);
	 }
	else if(maxDivId1=='4thStackedBar')
	 {
		showNursingStackedBarOrderType('nursingChart', '', '', 'total', 'All', '#09A1A4',locationId,locationName);
	 }
	else if(maxDivId1=='7thStackedBar')
	 {
		
		showFloorStatusStackedBar('nursingChart', '', '', '', 'All', '#09A1A4');
	 }
	else if(maxDivId1=='8thStackedBar')
	 {
		showFloorOrderTypeStackedBar('nursingChart', '', '', '', 'All', '#09A1A4');
	 }
	else if(maxDivId1=='9thStackedBar')
	 {
		showTimeStatusStackedBar('nursingChart', '', '', '', 'All', '#09A1A4');
	 }
	else if(maxDivId1=='10thStackedBar')
	 {
		showTimeFloorStackedBar('nursingChart', '', '', 'total', 'All', '#09A1A4',timeId,timeName);
	 }
	else if(maxDivId1=='11thStackedBar')
	 {
		 showTimeFloorNursingUnitStackedBar('nursingChart', '', '','total', 'All', '#09A1A4',locationId,locationName,timeId,timeName);
 	 }
 	 else if(maxDivId1=='3rdTable')
	 {
		 showData('nursingChart','','status');
	 }
	 else if(maxDivId1=='4thTable')
	 {
		 showData('nursingChart','','order');
	 }
	 else if(maxDivId1=='7thTable')
	 {
		 showData('nursingChart','','statusFloor');
	 }
	 else if(maxDivId1=='8thTable')
	 {
		 showData('nursingChart','','orderFloor');
	 }
	 else if(maxDivId1=='9thTable')
	 {
		 showData('nursingChart','','statusTime');
	 }
	 else if(maxDivId1=='10thTable')
	 {
		 showData('nursingChart','','floorTime');
	 }
	 else if(maxDivId1=='11thTable')
	 {
		 showData('nursingChart','','nursingUnitTime');
	 }
}

$.subscribe('closeDialog',function(event, data) 
		{
			isMaxDept=false;
		});

function changeHeader(deptId,setDiv,firstHead)
{

	//alert(deptId+" "+setDiv+"  "+firstHead);
	//alert($("#deptnameCateg").attr('value'));
	//alert($("#deptnameCateg").find('option:selected').text());
	var str=$("#"+deptId).find('option:selected').text();
	//alert(str);
	$("#"+setDiv).empty();
	//alert($("#"+setDiv).html);
	$("#"+setDiv).html(firstHead+' '+str);
}
 
</script>

<s:url var="chartDataUrl" action="json-chart-data"/>

<title>Machine Order Dashboard</title>
<style type="text/css">
.ui-datepicker-month,.ui-datepicker-year{
color: rgb(22, 66, 118);
}
#dashDataPart ul.nav_links li div.dropdown ul li:HOVER {
  display: block;
  float: none;
  position: relative;
  font-size: 12px;
  text-shadow: none;
  background-color: #F0F0F0;
}

#dashDataPart ul.nav_links li:hover div.dropdown ul li {
  display: block;
  float: none;
  position: relative;
  font-size: 12px;
  text-shadow: none;
  text-align: center;
  border-bottom: 1px solid #f2f2f2;
}

	
#dashDataPart ul.nav_links li div.profile_dropdown{
width: 150px;
top: 24px;
}
#dashDataPart .wrap_nav{

float: right;
  height: auto;
   width: 6%; 
  padding-top: 0px;
  /* margin-right: 1%; */
}

.amcharts-graph-graph2 .amcharts-graph-stroke {
  stroke-dasharray: 4px 5px;
  stroke-linejoin: round;
  stroke-linecap: round;
  -webkit-animation: am-moving-dashes 1s linear infinite;
  animation: am-moving-dashes 1s linear infinite;
}

@-webkit-keyframes am-moving-dashes {
  100% {
    stroke-dashoffset: -28px;
  }
}
@keyframes am-moving-dashes {
  100% {
    stroke-dashoffset: -28px;
  }
}
.colorme{
color:black;
}
.title{
		color:#FFFFFF; 
		background:#0C0C0C url("images/ui-bg_highlight-soft_15_F04E2F_1x100.png") 50% 50% repeat-x; 
		font-size:11px; 
		font-family:Verdana, Arial, Helvetica, sans-serif;
		}
			.titleData
		{
		color: #EAEAEA ;
		background:#ffffff url("images/ui-bg_flat_75_ffffff_40x100.png") 50% 50% repeat-x; 
		font-size:11px; 
		font-family:Verdana, Arial, Helvetica, sans-serif;
		}
		
		
		#dashboard1 td:HOVER {
	background:#EAEAEA;
}

.headdingtest{
font-weight: bold;
line-height: 30px;
margin-left: 30px; 
}
.datePart{
margin: 0px 5px 3px 293px;
}
.threeLiner{
 
   width: 29px;
  height: 21px;
  padding: 0;
  background-repeat: no-repeat;
  background-image: url(images/threeline.png;);
  background-color: #fff;
  background-position: center;
  -webkit-box-shadow: 1px 1px 3px 0px rgba(0,0,0,0.5);
  -moz-box-shadow: 1px 1px 3px 0px rgba(0,0,0,0.5);
  box-shadow: 1px 1px 3px 0px rgba(0,0,0,0.5);
  border-radius: 2px;
  margin: 0px -5px 0 0px;
  display: block;
  background-size: 21px;
}
.threeLiner:HOVER{
 
   width: 29px;
  height: 21px;
  padding: 0;
  background-repeat: no-repeat;
  background-image: url(images/threeline.png;);
  background-color: #e1e1e1;
  background-position: center;
  -webkit-box-shadow: 1px 1px 3px 0px rgba(0,0,0,0.5);
  -moz-box-shadow: 1px 1px 3px 0px rgba(0,0,0,0.5);
  box-shadow: 1px 1px 3px 0px rgba(0,0,0,0.5);
  border-radius: 2px;
  margin: 0px -5px 0 0px;
  display: block;
  background-size: 21px;
}
</style>
</head>
<body id="dashboard1">
<sj:dialog 
	    	id="ticketDialog" 
	    	autoOpen="false" 
	    	modal="true" 
	    	showEffect="slide" 
	    	hideEffect="explode" 
	    	position="['center','top']"
    >
<center><div id="ticketsInfo"></div></center>
</sj:dialog>
    
<center>
<sj:dialog 
            id="confirmEscalationDialog" 
            autoOpen="false"  
            closeOnEscape="true" 
            modal="true" 
            title="Action On Tickets" 
            width="900" 
            height="350" 
            showEffect="slide" 
            hideEffect="explode" >
            <div id="confirmingEscalation"></div>
</sj:dialog>
 
</center>

<sj:dialog
          id="maxmizeViewDialog"
          showEffect="slide"
          hideEffect="explode"
          autoOpen="false"
          modal="true"
          closeTopics="closeEffectDialog"
          onCloseTopics="closeDialog"
          >
        
          <div id="maximizeDataDiv" style="width: 100%; height: 100%;">
          </div>
</sj:dialog>
 


<s:hidden id="dataFor" value="%{loginType}"/>
<div class="middle-content">
<div class="list-icon">
	<div class="head">Dashboard </div>
	<div class="head">
	<img alt="" src="images/forward.png" style="margin-top:10px;margin-right:3px; float: left;">Machine Order 
	</div>
</div>
<s:hidden id="esc" value="false" name="esc" ></s:hidden>
<s:hidden id="hfromDate" value="%{fromDate}"></s:hidden>
<s:hidden id="htoDate" value="%{toDate}"></s:hidden>
<div id="trendFor" style="display: none;">Total</div>
<ul class="nav nav-tabs" id="myTab" style="padding: 5px;">
<!--   <li role="presentation" onclick="getTDash();"><a href="#">Trend</a></li> -->
  <li class="datePart" style="margin-left: 520px;"><b>Data From</b></li>
  <li style=""><sj:datepicker id="sdate"   cssClass="button" cssStyle="width: 70px;border: 1px solid #e2e9ef;border-top: 1px solid #cbd3e2;padding: 1px;font-size: 12px;outline: medium none;height: 18px;text-align: center;" readonly="true"  size="20" changeMonth="true" changeYear="true"  yearRange="2013:2050" showOn="focus" displayFormat="dd-mm-yy" Placeholder="Select From Date" onchange="getDataForAllBoard()" value=" "/></li>
  <li style=""><b style="margin-left: 4px;margin-right: 6px;"> To</b></li>
  <li style=""><sj:datepicker id="edate"  cssClass="button" cssStyle="width: 70px;border: 1px solid #e2e9ef;border-top: 1px solid #cbd3e2;padding: 1px;font-size: 12px;outline: medium none;height: 18px;text-align: center;" readonly="true"  size="20" changeMonth="true" changeYear="true"  yearRange="2013:2050" showOn="focus" displayFormat="dd-mm-yy" Placeholder="Select TO Date" onchange="getDataForAllBoard()" value=" " /></li>
</ul>
        
  <div style="overflow: auto;" id="dashDataPart">
         
            
<!--Ticket Counter By Status Dashboard Start -->
<div class="contentdiv-smallNewDash" style="overflow: auto;width: 34%" id="counter_dash">
<span id="headerIdHidden" style="display:none;"><s:property value="%{headerValue}"/></span>
 
<div class="headdingtest" id="ticketDiv">Ticket Counter Status For All Departments</div>
	<div style="margin-left: 1%;margin-top: -3%;float: left: ;">
			<button  id="backBtnDept" style="display: none;position: absolute;top: 26px;left: 5px;"  type="button" class="btn btn-default btn-sm" title="Back">
			  <span class="glyphicon glyphicon-arrow-left" aria-hidden="true"></span></button>
	</div>
<div class="wrap_nav" style="float: right;margin-top: 3%;">
<ul class="nav_links">
	<li ><a href="#">
	<span class="threeLiner" ></span>
		 </a> 
			
			
	<div class="dropdown profile_dropdown">
	<div class="arrow_dropdown">&nbsp;</div>
	<div class="one_column" >
	<ul>
		<li><a href="#" onclick="beforeMaximise('ticket_graph',400,900,'Ticket Count Status')" >
		<span class="glyphicon glyphicon-fullscreen" aria-hidden="true" style="margin-right:4px;"></span>Maximize</a></li>
	
		<li id="threelinerDataGraph"><a href="#" onclick="showData('jqxChart','','');">
		<span class="glyphicon glyphicon-list-alt" aria-hidden="true" style="margin-right:4px;"></span>View Data</a></li>
		<li id="threelinerDataGraph"><a href="#" onclick="StatckedChartStatus('jqxChart',filterFlag,filterDeptId,'','All','#09A1A4');">
		<span class="glyphicon glyphicon-equalizer" aria-hidden="true" style="margin-right:4px;"></span>View Bar Graph</a></li>
		<%-- <li id="threelinerDataGraph"><a href="#" onclick="StatckedChartStatus('jqxChart','','','','Resolve','#5cb85c','stack');">
		<span class="glyphicon glyphicon-stats" aria-hidden="true" style="margin-right:4px;"></span>View Stack Graph</a></li>
 --%>		<%-- <li id="threelinerDataGraph"><a href="#" onclick="showPieStatus('jqxChart','Pending','Pending Ticket Status','For All Departments');">
		<span class="glyphicon glyphicon-adjust" aria-hidden="true" style="margin-right:4px;"></span>View Pie Graph</a></li> --%>
		<%-- <li id="threelinerDataGraph"><a href="#" onclick="showTrend();">
		<span class="glyphicon glyphicon-road" aria-hidden="true" style="margin-right:4px;"></span>Show Trend</a></li>
		 --%>
	</ul>
	</div>
	<div class="clear"></div>
	</div>
	</li>
</ul>
</div>
 
<% if(userTpe.equalsIgnoreCase("M"))
	{%>
<div style="float:right; width:auto; margin: 14px -2px 0px 0px;">
	
<s:select  
				                              id="deptname"
				                              name="deptname"
				                              headerKey="-1"
				                              headerValue="All Department"
				                              list="serviceDeptList"
				                              cssClass="select"
					                          cssStyle="width:100%;height:22px;  background: #EDECEC;"
					                          onchange="getTicketByDept();changeHeader('deptname','ticketDiv','Ticket Counter Status For ');"
				                              >
				                  </s:select>
				                  
				                  </div>
<% }else { %> 
<s:hidden id="deptname" value="%{dept}"></s:hidden>
<%} %>
<div class="clear"></div>
  
<div id='jqxChart' style="width: 100%; height: 210px;overflow: auto !important;"  ></div>
 
 <div class="statusPanel1">
<a href="#"><span class="label label-default" style="background-color: #09A1A4;" id="statusAll" onclick="StatckedChartStatus('jqxChart','','','','All','#09A1A4');">All</span></a>
 <a href="#"><span class="label label-danger" style="background:#F04E2F;" id="statusPN" onclick="StatckedChartStatus('jqxChart','','','Pending','Pending','#F04E2F');">Pending</span></a>
<a href="#"><span class="label label-success" id="statusRS" onclick="StatckedChartStatus('jqxChart','','','Resolved','Resolved','#5cb85c');">Resolve</span></a>
<a href="#"><span class="label label-warning" id="statusPA" onclick="StatckedChartStatus('jqxChart','','','Snooze','Park','#f0ad4e');">Park</span></a>
 
 </div>
   
</div>
<!-- Ticket Counter By Status Dashboard End -->


<!-- Nursing unit Ticket Statuswise For Particular department Div Start -->

 
<div class="contentdiv-smallNewDash" style="overflow: auto;width: 64%"  id="hod_level_status">
<div id="lable_graph">
 
<div class="levelPanel">
<div class="headdingtest" id="levelDiv" > Level Wise Status <span id="headerId" ><s:property  value="%{headerValue}"/></span></div>
</div>
 <%-- <div class="levelPanelLoc">
<div class="headdingtest">Locationwise Level For <s:property value="%{headerValue}"/></div>
</div> --%>
 
 
<ul class="nav_links">
	<li><a href="#">
	<span class="threeLiner" style="margin:0px;" ></span>
		 </a> 
			
			
	<div class="dropdown profile_dropdown">
	<div class="arrow_dropdown">&nbsp;</div>
	<div class="one_column" >
	<ul>
	<div class="levelPanel">
 
		<li id="threelinerDataGraph1"><a href="#" onclick="showData('mytest','statusLevel','')">
		<span class="glyphicon glyphicon-list-alt" aria-hidden="true" style="margin-right:4px;"></span>View Data</a></li>
		<li id="threelinerDataGraph"><a href="#" onclick="showLeveStackedBar('mytest','','','','All','#09A1A4');">
		<span class="glyphicon glyphicon-equalizer" aria-hidden="true" style="margin-right:4px;"></span>View Bar Graph</a></li>
	
	</div>
	 
	</ul>
	</div>
	<div class="clear"></div>
	</div>
	</li>
</ul>
<div style="float:right; width:auto; margin: 0px 4px 0px 0px;">
				                   <s:select  
				                                id="machinename1"
				                              name="machinename1"
				                              list="#{'':''}"
				                               headerKey="-1"
				                              headerValue="All Machine"
				                              cssClass="select"
					                          cssStyle="width:100%;height:22px;  background: #EDECEC;"
					                          onchange="getLevelByDept(); "
				                              >
				                  </s:select> </div>
<% if(userTpe.equalsIgnoreCase("M"))
	{%>
<div style="float:right; width:auto; margin: 0px 4px 0px 0px;">
<s:select  
				                                id="deptname1"
				                              name="deptname1"
				                              list="serviceDeptList"
				                              cssClass="select"
				                              headerKey="-1"
				                              headerValue="All Departments"
				                              cssStyle="width:100%;height:22px;  background: #EDECEC;"
					                          onchange="getLevelByDept();fetchMachineSerial('machinename1','deptname1');changeHeader('deptname1','levelDiv','Level Wise Status For ');"
				                              >
				                  </s:select>
				                  </div>
 
				                  <% } else { %> 
<s:hidden id="deptname1" value="%{dept}"></s:hidden>
<%} %>
<div class="clear"></div>

<div id='mytest' style="width: 100%; height: 217px" align="center"></div>

</div>
 <div class="levelPanel">
 
  <a href="#"><span class="label label-default" style="background-color: #09A1A4;" id="statusAllL" onclick="showLeveStackedBar('mytest','','','','All','#09A1A4');">All</span></a>
 <a href="#"><span class="label label-danger" style="background:#F04E2F;" id="statusPNL" onclick="showLeveStackedBar('mytest','','','Pending','Pending','#F04E2F');">Pending</span></a>
<a href="#"><span class="label label-success" id="statusRSL" onclick="showLeveStackedBar('mytest','','','Resolved','Resolve','#5cb85c');">Resolve</span></a>
<a href="#"><span class="label label-warning" id="statusPAL" onclick="showLeveStackedBar('mytest','','','Snooze','Park','#f0ad4e');">Park</span></a>
 
</div> 
 
 
</div>  

<!-- Level wise Ticket Status For Particular department Div End -->

 
         
  <!-- location -->
   <div class="contentdiv-smallNewDash" style="overflow: hidden;width: 99%;height: 400px;" id="hod_catg_status">
		<div id="counterChart">
		
		 
 <div style="  float: left;margin: 1%;">
			 
			 
			<button id="floorStatusbttn" type="button" class="btn btn-default btn-sm" onclick="showFloorStatusStackedBar('nursingChart', '', '', '', 'All', '#09A1A4');">
			  <span class="glyphicon glyphicon-globe" aria-hidden="true"></span> Location-Status
			</button>
			<button  id="floorOrderbttn" type="button" class="btn btn-default btn-sm" onclick="showFloorOrderTypeStackedBar('nursingChart', '', '', '', 'All', '#09A1A4');">
			  <span class="glyphicon glyphicon-signal " aria-hidden="true"></span> Location-OrderType
			</button>
			<button  id="timebttn" type="button" class="btn btn-default btn-sm" onclick="showTimeStatusStackedBar('nursingChart', '', '', '', 'All', '#09A1A4');">
			  <span class="glyphicon glyphicon-time " aria-hidden="true"></span> Time
			</button>
			<button  id="backbttn"  style="float:left;display:none"  type="button" class="btn btn-default btn-sm" >
			  <span class="glyphicon glyphicon-arrow-left" aria-hidden="true"></span> Back
			</button>
			 <span style="margin-left: 20px;margin-right: 8px;font-size: 15px;">Escalated:</span>
			<div id="button2" style="float:right"></div>
  
 </div>
   

 <div class="nursingStatusPanel">
<div class="headdingtest">Nursing Unit Wise Status <span id="headerId" ><s:property  value="%{headerValue}"/></span></div>
 </div>
 <div class="nursingOrderPanel">
<div class="headdingtest">Order Type Nursing Unit Wise </div>
 </div>  
 <div class="floorStatusPanel">
<div class="headdingtest" id="locationDiv"> Location Wise Status <span id="headerId" ><s:property  value="%{headerValue}"/></span></div>
 </div>
 <div class="floorOrderPanel">
<div class="headdingtest"> Location Wise Order Type <span id="headerId" ><s:property  value="%{headerValue}"/></span> </div>
 </div>
 <div class="timeStatusPanel">
<div class="headdingtest"> Time Wise Status <span id="headerId" ><s:property  value="%{headerValue}"/></span></div>
 </div>
  <div class="floorTimeStatusPanel">
<div class="headdingtest"> Location Wise Status At Perticuler Time  <span id="headerId" ><s:property  value="%{headerValue}"/></span></div>
 </div>
 <div class="nursingUnitTimeStatusPanel">
<div class="headdingtest"> Nursing Unit Wise Status At Perticuler Time <span id="headerId" ><s:property  value="%{headerValue}"/></span></div>
 </div>
<ul class="nav_links">
	<li style="top: -6px;"><a href="#">
	<span class="threeLiner" style="margin:5px;" ></span>
		 </a> 
			
			
	<div class="dropdown profile_dropdown">
	<div class="arrow_dropdown">&nbsp;</div>
	<div class="one_column" >
	<ul>
		
	 	 	
	  <div class="nursingStatusPanel">
		<li id="threelinerDataGraph1"><a href="#" onclick="showData('nursingChart','','status')">
		<span class="glyphicon glyphicon-list-alt" aria-hidden="true" style="margin-right:4px;"></span>View Data</a></li>
		<li id="threelinerDataGraph"><a href="#" onclick="showNursingStackedBar('nursingChart', '', '', 'total', 'All', '#09A1A4',locationId,locationName);">
		<span class="glyphicon glyphicon-equalizer" aria-hidden="true" style="margin-right:4px;"></span>View Bar Graph</a></li>
	
	</div>
	  <div class="nursingOrderPanel">
		<li id="threelinerDataGraph1"><a href="#" onclick="showData('nursingChart','','order')">
		<span class="glyphicon glyphicon-list-alt" aria-hidden="true" style="margin-right:4px;"></span>View Data</a></li>
		<li id="threelinerDataGraph"><a href="#" onclick="showNursingStackedBarOrderType('nursingChart', '', '', 'total', 'All', '#09A1A4',locationId,locationName);">
		<span class="glyphicon glyphicon-equalizer" aria-hidden="true" style="margin-right:4px;"></span>View Bar Graph</a></li>
	
	</div>	 
	
	<div class="floorStatusPanel">
		<li id="threelinerDataGraph1"><a href="#" onclick="showData('nursingChart','','statusFloor')">
		<span class="glyphicon glyphicon-list-alt" aria-hidden="true" style="margin-right:4px;"></span>View Data</a></li>
		<li id="threelinerDataGraph"><a href="#" onclick="showFloorStatusStackedBar('nursingChart', '', '', '', 'All', '#09A1A4');">
		<span class="glyphicon glyphicon-equalizer" aria-hidden="true" style="margin-right:4px;"></span>View Bar Graph</a></li>
	
	</div>
	<div class="floorOrderPanel">
		<li id="threelinerDataGraph1"><a href="#" onclick="showData('nursingChart','','orderFloor')">
		<span class="glyphicon glyphicon-list-alt" aria-hidden="true" style="margin-right:4px;"></span>View Data</a></li>
		<li id="threelinerDataGraph"><a href="#" onclick="showFloorOrderTypeStackedBar('nursingChart', '', '', '', 'All', '#09A1A4');">
		<span class="glyphicon glyphicon-equalizer" aria-hidden="true" style="margin-right:4px;"></span>View Bar Graph</a></li>
	
	</div>	
	
	<div class="timeStatusPanel">
		<li id="threelinerDataGraph1"><a href="#" onclick="showData('nursingChart','','statusTime')">
		<span class="glyphicon glyphicon-list-alt" aria-hidden="true" style="margin-right:4px;"></span>View Data</a></li>
		<li id="threelinerDataGraph"><a href="#" onclick="showTimeStatusStackedBar('nursingChart', '', '', '', 'All', '#09A1A4');">
		<span class="glyphicon glyphicon-equalizer" aria-hidden="true" style="margin-right:4px;"></span>View Bar Graph</a></li>
	
	</div>
	
	<div class="floorTimeStatusPanel">
		<li id="threelinerDataGraph1"><a href="#" onclick="showData('nursingChart','','floorTime')">
		<span class="glyphicon glyphicon-list-alt" aria-hidden="true" style="margin-right:4px;"></span>View Data</a></li>
		<li id="threelinerDataGraph"><a href="#" onclick="showTimeFloorStackedBar('nursingChart', '', '', 'total', 'All', '#09A1A4',timeId,timeName);">
		<span class="glyphicon glyphicon-equalizer" aria-hidden="true" style="margin-right:4px;"></span>View Bar Graph</a></li>
	
	</div>
	<div class="nursingUnitTimeStatusPanel">
		<li id="threelinerDataGraph1"><a href="#" onclick="showData('nursingChart','','nursingUnitTime')">
		<span class="glyphicon glyphicon-list-alt" aria-hidden="true" style="margin-right:4px;"></span>View Data</a></li>
		<li id="threelinerDataGraph"><a href="#" onclick="showTimeFloorNursingUnitStackedBar('nursingChart', '', '','total', 'All', '#09A1A4',locationId,locationName,timeId,timeName);">
		<span class="glyphicon glyphicon-equalizer" aria-hidden="true" style="margin-right:4px;"></span>View Bar Graph</a></li>
	
	</div>
	
	</ul>
	</div>
	<div class="clear"></div>
	</div>
	</li>
</ul>
<div style="float:right; width:auto; margin: 0px 4px 0px 0px;">
				                   <s:select  
				                                id="machinename2"
				                              name="machinename2"
				                              list="#{'':''}"
				                               headerKey="-1"
				                              headerValue="All Machine"
				                              cssClass="select"
					                          cssStyle="width:100%;height:22px;  background: #EDECEC;"
					                          onchange="getNursingByDept();"
				                              >
				                  </s:select> </div>
<% if(userTpe.equalsIgnoreCase("M"))
	{%>
<div style="float:right; width:auto; margin: 0px 4px 0px 0px;">
<s:select  
				                                id="deptname2"
				                              name="deptname2"
				                              list="serviceDeptList"
				                              cssClass="select"
				                               headerKey="-1"
				                              headerValue="All Departments"
					                          cssStyle="width:100%;height:22px;  background: #EDECEC;"
					                          onchange="getNursingByDept();fetchMachineSerial('machinename2','deptname2');"
				                              >
				                  </s:select>
				                   </div>
				                  <% } else { %> 
<s:hidden id="deptname2" value="%{dept}"></s:hidden>
<%} %>
<div class="clear"></div>
     <div style="float: left;  margin-left: 1%;" id='jqxdropdownlist'>
</div>
<div id="nursingChart"style="overflow: auto;width: 99%; height: 300px;"></div>
</div>
 
 
 <div class="nursingStatusPanel">
<a href="#"><span class="label label-default" style="background-color: #09A1A4;" id="statusAll" onclick="showNursingStackedBar('nursingChart', '', '', 'total', 'All', '#09A1A4',locationId,locationName);">All</span></a>
 <a href="#"><span class="label label-danger" style="background:#F04E2F;" id="statusPN" onclick="showNursingStackedBar('nursingChart','','','Pending','Pending','#F04E2F',locationId,locationName);">Pending</span></a>
<a href="#"><span class="label label-success" id="statusRS" onclick="showNursingStackedBar('nursingChart','','','Resolved','Resolve','#5cb85c',locationId,locationName);">Resolve</span></a>
<a href="#"><span class="label label-warning" id="statusPA" onclick="showNursingStackedBar('nursingChart','','','Snooze','Park','#f0ad4e',locationId,locationName);">Park</span></a>
 </div>
 
 
   <div class="nursingOrderPanel">
<a href="#"><span class="label label-default" style="background-color: #09A1A4;" id="statusAll" onclick="showNursingStackedBarOrderType('nursingChart', '', '', 'total', 'All', '#09A1A4',locationId,locationName);">All</span></a>
 <a href="#"><span class="label label-danger" style="background:#F04E2F;" id="statusPN" onclick="showNursingStackedBarOrderType('nursingChart','','','Routine','Routine','#F04E2F',locationId,locationName);">Routine</span></a>
<a href="#"><span class="label label-success" id="statusRS" onclick="showNursingStackedBarOrderType('nursingChart','','','Urgent','Urgent','#5cb85c',locationId,locationName);">Urgent</span></a>
<a href="#"><span class="label label-warning" id="statusPA" onclick="showNursingStackedBarOrderType('nursingChart','','','Stat','Stat','#f0ad4e',locationId,locationName);">Stat</span></a>
 </div>   
 
 <div class="floorStatusPanel">
<a href="#"><span class="label label-default" style="background-color: #09A1A4;" id="statusAllF" onclick="showFloorStatusStackedBar('nursingChart', '', '', '', 'All', '#09A1A4');">All</span></a>
 <a href="#"><span class="label label-danger" style="background:#F04E2F;" id="statusPNF" onclick="showFloorStatusStackedBar('nursingChart','','','Pending','Pending','#F04E2F');">Pending</span></a>
<a href="#"><span class="label label-success" id="statusRSF" onclick="showFloorStatusStackedBar('nursingChart','','','Resolved','Resolve','#5cb85c');">Resolve</span></a>
<a href="#"><span class="label label-warning" id="statusPAF" onclick="showFloorStatusStackedBar('nursingChart','','','Snooze','Park','#f0ad4e');">Park</span></a>
 </div>
 
 
 <div class="floorOrderPanel">
<a href="#"><span class="label label-default" style="background-color: #09A1A4;" id="statusAllO" onclick="showFloorOrderTypeStackedBar('nursingChart', '', '', '', 'All', '#09A1A4');">All</span></a>
 <a href="#"><span class="label label-danger" style="background:#F04E2F;" id="statusPNO" onclick="showFloorOrderTypeStackedBar('nursingChart','','','Routine','Routine','#F04E2F');">Routine</span></a>
<a href="#"><span class="label label-success" id="statusRSO" onclick="showFloorOrderTypeStackedBar('nursingChart','','','Urgent','Urgent','#5cb85c');">Urgent</span></a>
<a href="#"><span class="label label-warning" id="statusPAO" onclick="showFloorOrderTypeStackedBar('nursingChart','','','Stat','Stat','#f0ad4e');">Stat</span></a>
 </div>
 
 <div class="timeStatusPanel">
<a href="#"><span class="label label-default" style="background-color: #09A1A4;" id="statusAllT" onclick="showTimeStatusStackedBar('nursingChart', '', '', '', 'All', '#09A1A4');">All</span></a>
 <a href="#"><span class="label label-danger" style="background:#F04E2F;" id="statusPNT" onclick="showTimeStatusStackedBar('nursingChart','','','Pending','Pending','#F04E2F');">Pending</span></a>
<a href="#"><span class="label label-success" id="statusRST" onclick="showTimeStatusStackedBar('nursingChart','','','Resolved','Resolve','#5cb85c');">Resolve</span></a>
<a href="#"><span class="label label-warning" id="statusPAT" onclick="showTimeStatusStackedBar('nursingChart','','','Snooze','Park','#f0ad4e');">Park</span></a>
 </div>
 
 <div class="floorTimeStatusPanel">
<a href="#"><span class="label label-default" style="background-color: #09A1A4;" id="statusAllTF" onclick="showTimeFloorStackedBar('nursingChart', '', '', 'total', 'All', '#09A1A4',timeId,timeName);">All</span></a>
 <a href="#"><span class="label label-danger" style="background:#F04E2F;" id="statusPNTF" onclick="showTimeFloorStackedBar('nursingChart','','','Pending','Pending','#F04E2F',timeId,timeName);">Pending</span></a>
<a href="#"><span class="label label-success" id="statusRSTF" onclick="showTimeFloorStackedBar('nursingChart','','','Resolved','Resolve','#5cb85c',timeId,timeName);">Resolve</span></a>
<a href="#"><span class="label label-warning" id="statusPATF" onclick="showTimeFloorStackedBar('nursingChart','','','Snooze','Park','#f0ad4e',timeId,timeName);">Park</span></a>
 </div>
 
  
 <div class="nursingUnitTimeStatusPanel">
<a href="#"><span class="label label-default" style="background-color: #09A1A4;" id="statusAllTN" onclick="showTimeFloorNursingUnitStackedBar('nursingChart', '', '','total', 'All', '#09A1A4',locationId,locationName,timeId,timeName);">All</span></a>
 <a href="#"><span class="label label-danger" style="background:#F04E2F;" id="statusPNTN" onclick="showTimeFloorNursingUnitStackedBar('nursingChart','','','Pending','Pending','#F04E2F',locationId,locationName,timeId,timeName);">Pending</span></a>
<a href="#"><span class="label label-success" id="statusRSTN" onclick="showTimeFloorNursingUnitStackedBar('nursingChart','','','Resolved','Resolve','#5cb85c',locationId,locationName,timeId,timeName);">Resolve</span></a>
<a href="#"><span class="label label-warning" id="statusPATN" onclick="showTimeFloorNursingUnitStackedBar('nursingChart','','','Snooze','Park','#f0ad4e',locationId,locationName,timeId,timeName);">Park</span></a>
 </div>
 
</div>     

  
            </div>
   </div>    

 </body>
</html>