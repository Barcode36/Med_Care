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
	
 
<script type="text/javascript">
$(document).ready(function(){
	document.getElementById('sdate').value=$("#hfromDate").val();
    document.getElementById('edate').value=$("#htoDate").val();
  
});
 
chartTab();
function chartTab()
{
	StatckedChartStatus('jqxChart', '', '', '', 'All', '#09A1A4');
	showNursingStackedBar('nursingChart', '', '', '', 'All', '#09A1A4');
	showLeveStackedBar('mytest', '', '', '', 'All', '#09A1A4');
	 
}
 
 
function getDataForAllBoard()
{
	
 
	StatckedChartStatus('jqxChart', '', '', '', 'All', '#09A1A4');
	showNursingStackedBar('nursingChart', '', '', '', 'All', '#09A1A4');
	showLeveStackedBar('mytest', '', '', '', 'All', '#09A1A4');
	 
	
}
function getsubdeptChart(deptId,deptName)
{
	beforeMaximiseFilter('Ticket Count Status For: '+deptName,400,900,'Ticket Count Status','G',deptId,"H");
}
function getTicketByDept(deptId)
{
	if(maxDivId1=='1stStackedBar')
	 {
		StatckedChartStatus('jqxChart','','','','All','#5cb85c');
	 }
	 
	 else if(maxDivId1=='1stTable')
	 {
		 showData('jqxChart','');
	 }
}

function getLevelByDept(deptId)
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

function getNursingByDept(deptId)
{
	 if(maxDivId1=='3rdStackedBar')
	 {
		
		showNursingStackedBar('nursingChart', '', '', '', 'All', '#09A1A4');
	 }
	else if(maxDivId1=='4thStackedBar')
	 {
		showNursingStackedBarOrderType('nursingChart', '', '', '', 'All', '#09A1A4');
	 }
	else if(maxDivId1=='7thStackedBar')
	 {
		
		showFloorStatusStackedBar('nursingChart', '', '', '', 'All', '#09A1A4');
	 }
	else if(maxDivId1=='8thStackedBar')
	 {
		showFloorOrderTypeStackedBar('nursingChart', '', '', '', 'All', '#09A1A4');
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
	 
}

$.subscribe('closeDialog',function(event, data) 
		{
			isMaxDept=false;
		});

 
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
 
<div class="headdingtest">Ticket Counter Status For <span id="headerId" ><s:property  value="%{headerValue}"/></span></div>
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
				                              list="serviceDeptList"
				                              cssClass="select"
					                          cssStyle="width:100%;height:22px;  background: #EDECEC;"
					                          onchange="getTicketByDept(this.value);"
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
<a href="#"><span class="label label-success" id="statusRS" onclick="StatckedChartStatus('jqxChart','','','Resolved','Resolve','#5cb85c');">Resolve</span></a>
<a href="#"><span class="label label-warning" id="statusPA" onclick="StatckedChartStatus('jqxChart','','','Snooze','Park','#f0ad4e');">Park</span></a>
 
 </div>
   
</div>
<!-- Ticket Counter By Status Dashboard End -->


<!-- Nursing unit Ticket Statuswise For Particular department Div Start -->

 
<div class="contentdiv-smallNewDash" style="overflow: auto;width: 64%"  id="hod_level_status">
<div id="lable_graph">
 
 
 
 	<div style="  float: left;margin: 1%;">
			 
			<%-- <button id="locationbttn" type="button" class="btn btn-default btn-sm" onclick="StatckedChartLevelLocation('Status','All');">
			  <span class="glyphicon glyphicon-globe" aria-hidden="true"></span> Location-Status
			</button> --%>
			<button  id="timebttn" type="button" class="btn btn-default btn-sm" onclick="StatckedChartLevelLocation('mytest', '', '', '', 'All', '#09A1A4');">
			  <span class="glyphicon glyphicon-signal " aria-hidden="true"></span> Location-Level
			</button>
		<button  id="staffbttn"  type="button" class="btn btn-default btn-sm" onclick="showLeveStackedBar('mytest', '', '', '', 'All', '#09A1A4');">
			  <span class="glyphicon glyphicon-user " aria-hidden="true"></span> Level-status
			</button>
			 	 
			<!-- Escalated: <input type="checkbox" onclick="getEscalatedMain(this.checked);"/> -->
		 	
		 	<%-- <button  id="backbttn" style="display: none;"  type="button" class="btn btn-default btn-sm" >
			  <span class="glyphicon glyphicon-arrow-left" aria-hidden="true"></span> Back
			</button> --%>
			
			
			
		</div>
<div class="levelPanel">
<div class="headdingtest">Levelwise Status For <s:property value="%{headerValue}"/></div>
</div>
 <div class="levelPanelLoc">
<div class="headdingtest">Locationwise Level For <s:property value="%{headerValue}"/></div>
</div>
<%-- <div class="statusPanelLoc">
<div class="headdingtest">Locationwise Status For <s:property value="%{headerValue}"/></div>
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
	<div class="levelPanelLoc">
 
		<li id="threelinerDataGraph1"><a href="#" onclick="showData('mytest','locationLevel','')">
		<span class="glyphicon glyphicon-list-alt" aria-hidden="true" style="margin-right:4px;"></span>View Data</a></li>
		
		<li id="threelinerDataGraph"><a href="#" onclick="StatckedChartLevelLocation('mytest','','','','All','#09A1A4');">
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
					                          onchange="getLevelByDept(this.value);"
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
					                          cssStyle="width:100%;height:22px;  background: #EDECEC;"
					                          onchange="getLevelByDept(this.value);"
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
 
  <a href="#"><span class="label label-default" style="background-color: #09A1A4;" id="statusAll" onclick="showLeveStackedBar('mytest','','','','All','#09A1A4');">All</span></a>
 <a href="#"><span class="label label-danger" style="background:#F04E2F;" id="statusPN" onclick="showLeveStackedBar('mytest','','','Pending','Pending','#F04E2F');">Pending</span></a>
<a href="#"><span class="label label-success" id="statusRS" onclick="showLeveStackedBar('mytest','','','Resolved','Resolve','#5cb85c');">Resolve</span></a>
<a href="#"><span class="label label-warning" id="statusPA" onclick="showLeveStackedBar('mytest','','','Snooze','Park','#f0ad4e');">Park</span></a>
 
</div> 
<div class="levelPanelLoc">
  <a href="#"><span class="label label-default" style="background-color: #09A1A4;" id="statusAll" onclick="StatckedChartLevelLocation('mytest','','','','All','#09A1A4');">All</span></a>
 <a href="#"><span class="label label-danger" style="background:#F04E2F;" id="statusPN" onclick="StatckedChartLevelLocation('mytest','','','Level1','Level1','#F04E2F');">Level1</span></a>
<a href="#"><span class="label label-success" id="statusRS" onclick="StatckedChartLevelLocation('mytest','','','Level2','Level2','#5cb85c');">Level2</span></a>
<a href="#"><span class="label label-warning" id="statusPA" onclick="StatckedChartLevelLocation('mytest','','','Level3','Level3','#f0ad4e');">Level3</span></a>
  <a href="#"><span class="label label-danger" style="background-color: #F04EFF;"  style="background:#F04E2F;" id="statusPN4" onclick="StatckedChartLevelLocation('mytest','','','Level4','Level4','#F04EFF');">Level4</span></a>
<a href="#"><span class="label label-success"  style="background-color: #5cbEEF;" id="statusRS5" onclick="StatckedChartLevelLocation('mytest','','','Level5','Level5','#5cbEEF');">Level5</span></a>
<a href="#"><span class="label label-warning" style="background-color: #f0aaFe;" id="statusPA6" onclick="StatckedChartLevelLocation('mytest','','','Level6','Level6','#f0aaFe');">Level6</span></a>
   </div>
 
 
</div>  

<!-- Level wise Ticket Status For Particular department Div End -->

 
         
  <!-- location -->
   <div class="contentdiv-smallNewDash" style="overflow: hidden;width: 99%;height: 400px;" id="hod_catg_status">
		<div id="counterChart">
		
		 
 <div style="  float: left;margin: 1%;">
			 
			<button id="nursingStatusbttn" type="button" class="btn btn-default btn-sm" onclick="showNursingStackedBar('nursingChart', '', '', '', 'All', '#09A1A4');">
			  <span class="glyphicon glyphicon-globe" aria-hidden="true"></span> NursingUnit-Status
			</button>
			<button  id="nursingOrderbttn" type="button" class="btn btn-default btn-sm" onclick="showNursingStackedBarOrderType('nursingChart', '', '', '', 'All', '#09A1A4');">
			  <span class="glyphicon glyphicon-signal " aria-hidden="true"></span> NursingUnit-OrderType
			</button>
			<button id="floorStatusbttn" type="button" class="btn btn-default btn-sm" onclick="showFloorStatusStackedBar('nursingChart', '', '', '', 'All', '#09A1A4');">
			  <span class="glyphicon glyphicon-globe" aria-hidden="true"></span> Location-Status
			</button>
			<button  id="floorOrderbttn" type="button" class="btn btn-default btn-sm" onclick="showFloorOrderTypeStackedBar('nursingChart', '', '', '', 'All', '#09A1A4');">
			  <span class="glyphicon glyphicon-signal " aria-hidden="true"></span> Location-OrderType
			</button>
			 </div>

<div class="nursingStatusPanel">
<div class="headdingtest">Nursing Unit Statuswise For <s:property value="%{headerValue}"/></div>
 </div>
 <div class="nursingOrderPanel">
<div class="headdingtest">Nursing Unit Orderwise For <s:property value="%{headerValue}"/></div>
 </div>
 <div class="floorStatusPanel">
<div class="headdingtest">Location Statuswise For <s:property value="%{headerValue}"/></div>
 </div>
 <div class="floorOrderPanel">
<div class="headdingtest">Location Orderwise For <s:property value="%{headerValue}"/></div>
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
		<li id="threelinerDataGraph"><a href="#" onclick="showNursingStackedBar('nursingChart', '', '', '', 'All', '#09A1A4');">
		<span class="glyphicon glyphicon-equalizer" aria-hidden="true" style="margin-right:4px;"></span>View Bar Graph</a></li>
	
	</div>
	<div class="nursingOrderPanel">
		<li id="threelinerDataGraph1"><a href="#" onclick="showData('nursingChart','','order')">
		<span class="glyphicon glyphicon-list-alt" aria-hidden="true" style="margin-right:4px;"></span>View Data</a></li>
		<li id="threelinerDataGraph"><a href="#" onclick="showNursingStackedBarOrderType('nursingChart', '', '', '', 'All', '#09A1A4');">
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
					                          onchange="getNursingByDept(this.value);"
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
					                          cssStyle="width:100%;height:22px;  background: #EDECEC;"
					                          onchange="getNursingByDept(this.value);"
				                              >
				                  </s:select>
				                   </div>
				                  <% } else { %> 
<s:hidden id="deptname2" value="%{dept}"></s:hidden>
<%} %>
<div class="clear"></div>
     <div style="float: left;  margin-left: 1%;" id='jqxdropdownlist'>
</div>
<div id="nursingChart"style="width: 99%; height: 300px;"></div>
</div>
 
 
 <div class="nursingStatusPanel">
<a href="#"><span class="label label-default" style="background-color: #09A1A4;" id="statusAll" onclick="showNursingStackedBar('nursingChart', '', '', '', 'All', '#09A1A4');">All</span></a>
 <a href="#"><span class="label label-danger" style="background:#F04E2F;" id="statusPN" onclick="showNursingStackedBar('nursingChart','','','Pending','Pending','#F04E2F');">Pending</span></a>
<a href="#"><span class="label label-success" id="statusRS" onclick="showNursingStackedBar('nursingChart','','','Resolved','Resolve','#5cb85c');">Resolve</span></a>
<a href="#"><span class="label label-warning" id="statusPA" onclick="showNursingStackedBar('nursingChart','','','Snooze','Park','#f0ad4e');">Park</span></a>
 </div>
 
 
 <div class="nursingOrderPanel">
<a href="#"><span class="label label-default" style="background-color: #09A1A4;" id="statusAll" onclick="showNursingStackedBarOrderType('nursingChart', '', '', '', 'All', '#09A1A4');">All</span></a>
 <a href="#"><span class="label label-danger" style="background:#F04E2F;" id="statusPN" onclick="showNursingStackedBarOrderType('nursingChart','','','Routine','Routine','#F04E2F');">Routine</span></a>
<a href="#"><span class="label label-success" id="statusRS" onclick="showNursingStackedBarOrderType('nursingChart','','','Urgent','Urgent','#5cb85c');">Urgent</span></a>
<a href="#"><span class="label label-warning" id="statusPA" onclick="showNursingStackedBarOrderType('nursingChart','','','Stat','Stat','#f0ad4e');">Stat</span></a>
 </div>
 
 <div class="floorStatusPanel">
<a href="#"><span class="label label-default" style="background-color: #09A1A4;" id="statusAll" onclick="showFloorStatusStackedBar('nursingChart', '', '', '', 'All', '#09A1A4');">All</span></a>
 <a href="#"><span class="label label-danger" style="background:#F04E2F;" id="statusPN" onclick="showFloorStatusStackedBar('nursingChart','','','Pending','Pending','#F04E2F');">Pending</span></a>
<a href="#"><span class="label label-success" id="statusRS" onclick="showFloorStatusStackedBar('nursingChart','','','Resolved','Resolve','#5cb85c');">Resolve</span></a>
<a href="#"><span class="label label-warning" id="statusPA" onclick="showFloorStatusStackedBar('nursingChart','','','Snooze','Park','#f0ad4e');">Park</span></a>
 </div>
 
 
 <div class="floorOrderPanel">
<a href="#"><span class="label label-default" style="background-color: #09A1A4;" id="statusAll" onclick="showFloorOrderTypeStackedBar('nursingChart', '', '', '', 'All', '#09A1A4');">All</span></a>
 <a href="#"><span class="label label-danger" style="background:#F04E2F;" id="statusPN" onclick="showFloorOrderTypeStackedBar('nursingChart','','','Routine','Routine','#F04E2F');">Routine</span></a>
<a href="#"><span class="label label-success" id="statusRS" onclick="showFloorOrderTypeStackedBar('nursingChart','','','Urgent','Urgent','#5cb85c');">Urgent</span></a>
<a href="#"><span class="label label-warning" id="statusPA" onclick="showFloorOrderTypeStackedBar('nursingChart','','','Stat','Stat','#f0ad4e');">Stat</span></a>
 </div>
</div>     

  
            </div>
       

 </body>
</html>