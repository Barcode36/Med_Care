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
<s:url var="chartDataUrl" action="json-chart-data"/>
<s:url id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
 <script type="text/javascript" src="<s:url value="/js/dashboard/cps/dashboardbar.js"/>"></script>
<link  type="text/css" href="amcharts/plugins/export/export.css" rel="stylesheet">
<link rel="stylesheet" href="bootstrap-3.3.4-dist/css/bootstrap.min.css">
<link rel="stylesheet" href="bootstrap-3.3.4-dist/css/bootstrap-theme.min.css">
     <script src="amcharts/amcharts.js"></script>
	<script src="amcharts/serial.js"></script>
	<script src="amcharts/themes/light.js"></script>
	<script src="amcharts/amstock.js"></script>
	<script src="amcharts/pie.js"></script>
    <script  src="amcharts/plugins/export/export.js"></script>
<link rel="stylesheet" href="jqwidgets/styles/jqx.base.css" type="text/css">
<script type="text/javascript" src="js/html2canvas.js"></script>
<script type="text/javascript" src="js/canvas2image.js"></script>
<script type="text/javascript" src="jqwidgets/jqxcore.js"></script>
<script type="text/javascript" src="jqwidgets/jqxdata.js"></script>
<script type="text/javascript" src="jqwidgets/jqxchart.js"></script>
<script type="text/javascript" src="jqwidgets/jqxswitchbutton.js"></script>
<script type="text/javascript" src="jqwidgets/jqxcheckbox.js"></script>
<script type="text/javascript" src="jqwidgets/jqxbuttons.js"></script>
<link rel="stylesheet" type="text/css" href="css/chartdownload.css" />
<s:url var="chartDataUrl" action="json-chart-data"/>
<title>CPS Dashboard</title>
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
font-size:15px;
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

 
.lable1 {
    cursor: pointer;
background: -webkit-linear-gradient(#666, #AC5DA6);
color: #eee;
border-radius: 5px 5px 0 0;
padding: 0.5% 3%;
float: left;
margin-right: 2px;
font: sans-serif;
height: 30px;
}
.lable1:hover {
    background: -webkit-linear-gradient(#666, #ECCDEA);
}
</style>

<script type="text/javascript">
$(document).ready(function(){
	 $('#button1').jqxSwitchButton({ height: 20, width: 81, checked: false });
	    $('#button1').bind('checked', function (event) { 
	   	showTimeDiv(false);
	   	
	   });
	   $('#button1').bind('unchecked', function (event) { 
	   	showTimeDiv(true);
	   	
	   });
	   
	   
	document.getElementById('sdate').value=$("#hfromDate").val();
  document.getElementById('edate').value=$("#htoDate").val();
   
   
  
});


chartTab();
function chartTab()
{
	  
	showStatusStackedBar('statusChart', 'service', '', 'All', 'All', '#09A1A4');
	showProductivityStackedBar('productivityChart', 'serviceManagerProductivity', '', 'All', 'All', '#09A1A4');

}
</script>
 
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
            title="CPS Data" 
            width="1200" 
            height="450" 
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
  
 <div style="margin: 4px 4px 0px 325px;">
 
   <div id="datePickerStatus" style="float: left;">
From:<sj:datepicker placeholder="From Date" cssStyle="margin:0px 0px 2px 0px;width:88px;height: 20px;"   onchange="getDataForAllBoard()" cssClass="button"   id="sdate" name="sdate" size="20"   readonly="false"   changeMonth="true" changeYear="true"  yearRange="2013:2050" showOn="focus" displayFormat="dd-mm-yy"/>
To:<sj:datepicker placeholder="To Date" cssStyle="margin:0px 0px 2px 0px;width:88px;height: 20px;"  onchange="getDataForAllBoard()"  cssClass="button"  id="edate" name="edate"   size="20"   readonly="false"  changeMonth="true" changeYear="true"  yearRange="2013:2050" showOn="focus" displayFormat="dd-mm-yy"/>
</div>
  <div style="float:left;"><span style="float:left;margin-left: 20px;margin-right: 8px;font-size: 15px;">Time Wise:</span>
	<div style="float: left;" id="button1"></div>
</div>
 <div class="statusTime" style="float: left;margin-left: 20px;display: none;">
 From: <sj:datepicker placeholder="From Time" cssStyle="margin:0px 0px 2px 0px;width:85px;height: 20px;"  onchange="getDataForAllBoard();"  cssClass="button" timepickerOnly="true" timepicker="true" timepickerAmPm="false" id="fromTime" name="fromTime" size="20"   readonly="false"   showOn="focus"/>
 To:<sj:datepicker placeholder="To Time" cssStyle="margin:0px 0px 2px 0px;width:85px;height: 20px;"  onchange="getDataForAllBoard();"  cssClass="button" timepickerOnly="true" timepicker="true" timepickerAmPm="false" id="toTime" name="toTime" size="20"   readonly="false"   showOn="focus"/>
</div> 
</div>
 
<div class="middle-content">
<s:hidden id="hfromDate" value="%{fromDate}"></s:hidden>
<s:hidden id="htoDate" value="%{toDate}"></s:hidden>

 
<!--Ticket Status Counter Dashboard Start -->
<div class="contentdiv-smallNewDash" style="overflow: hidden;width: 99%;height: 370px;" id="service">
<div id="counterChart1">
	
<div style="  float: right;    margin-right: -6px;">
<!-- Start Faisal -->
	<!--<button id="corporate" type="button" class="btn btn-default btn-sm" onclick="showStatusStackedBar('statusChart', 'corporate', '', 'All', 'All', '#09A1A4');">
	<span   aria-hidden="true"></span> Corporate Wise
	</button>
	<button  id="Sertodoc" type="button" class="btn btn-default btn-sm" onclick="showStatusStackedBar('statusChart', 'serviceDOC', '', 'All', 'All', '#09A1A4');">
	<span   aria-hidden="true"></span> Service Wise-Doc
	</button>
	<button id="spec1" type="button" class="btn btn-default btn-sm" onclick="showStatusStackedBar('statusChart', 'service', '', 'All', 'All', '#09A1A4');">
	<span   aria-hidden="true"></span> Service Wise-Service Manager
	</button>
	<button  id="loc1" type="button" class="btn btn-default btn-sm" onclick="showStatusStackedBar('statusChart', 'location', '', 'All', 'All', '#09A1A4');">
	<span   aria-hidden="true"></span> Location Wise
	</button>
	<button  id="ac1" type="button" class="btn btn-default btn-sm" onclick="showStatusStackedBar('statusChart', 'accountManager', '', 'All', 'All', '#09A1A4');">
	<span   aria-hidden="true"></span> Account Manager Wise
	</button>
	<button  id="test1" type="button" class="btn btn-default btn-sm" onclick="showStatusStackedBar('statusChart', 'serviceManager', '', 'All', 'All', '#09A1A4');">
	<span   aria-hidden="true"></span> Service Manager Wise
	</button>
-->
	<input checked id="one" name="tabs" type="radio" style="opacity:0" onclick="showStatusStackedBar('statusChart', 'corporate', '', 'All', 'All', '#09A1A4');">
    <label class="lable1" for="one">Corporate Wise</label>
    <input id="two" name="tabs" type="radio" value="Two " style="opacity:0" onclick="showStatusStackedBar('statusChart', 'location', '', 'All', 'All', '#09A1A4');">
    <label class="lable1" for="two">Location Wise</label>
    <input id="three" name="tabs" type="radio" style="opacity:0" onclick="showStatusStackedBar('statusChart', 'accountManager', '', 'All', 'All', '#09A1A4');">
    <label class="lable1" for="three">Account Manager Wise</label>
 	<input id="four" name="tabs" type="radio" style="opacity:0" onclick="showStatusStackedBar('statusChart', 'serviceManager', '', 'All', 'All', '#09A1A4');">
    <label class="lable1" for="four">Service Manager Wise</label>
     <input id="five" name="tabs" type="radio" style="opacity:0" onclick="showStatusStackedBar('statusChart', 'serviceDOC', '', 'All', 'All', '#09A1A4');">
    <label class="lable1" for="five">Service Wise To Doctor</label>
    <input id="six" name="tabs" type="radio" style="opacity:0" onclick="showStatusStackedBar('statusChart', 'service', '', 'All', 'All', '#09A1A4');">
    <label class="lable1" for="six" style=" margin-top: -23px;">Service Wise To Service Manager</label>
  
<div  class="backbttnDiv1" style="  float: left;">
	<button  id="backbttn1"  style="float:left;display:none"  type="button" class="btn btn-default btn-sm" >
	<span class="glyphicon glyphicon-arrow-left" aria-hidden="true"></span> Back
	</button>
</div>	
<span  id="x1" style="float:left;margin-top: 3px;margin-right:3px"> Corporate Name:</span>
<div style="float:left;">   
	<s:select  
	id="statusFor"
	name="statusFor"
	list="corporateList"
	headerKey="-1"
	headerValue="All"
	cssClass="select"
	cssStyle="width:25%;height:22px;  background: #EDECEC;margin-left: -145px"
	onchange=" getDataForStatusCounter();"
	>
	</s:select>
 </div>
 
 
   
 
 
<!--<div class="allServicePanel">
	<div class="headdingtest">Service Wise Counter</div>
</div>
 
<div class="allLocationPanelForService">
	<div class="headdingtest">Location Wise Counter </div>
</div>
 
<div class="allServiceManagerPanelForService">
	<div class="headdingtest">Service Manager Wise Counter </div>
</div>
  
<div class="LocationPanel">
	<div class="headdingtest">Location Wise Counter</div>
</div>
 
<div class="ServicePanelForLocation">
	<div class="headdingtest">Service Wise Counter </div>
</div>
 
<div class="accountManagerPanel">
	<div class="headdingtest">Account Manager Wise Counter </div>
</div>

<div class="corporatePanelForAcntMng">
	<div class="headdingtest">Corporate Wise Counter </div>
</div>

<div class="servicePanelForAcntMng">
	<div class="headdingtest">Service Wise Counter  </div>
</div>

<div class="locationPanelForAcntMng">
	<div class="headdingtest">Location Wise Counter  </div>
</div>

<div class="serviceManagerPanelForAcntMng">
	<div class="headdingtest">Service Manager Wise Counter  </div>
</div>

<div class="serviceManagerPanel">
	<div class="headdingtest">Service Manager Wise Counter </div>
</div>

 Service to Doctor 
<div class="serviceSpecialityPanel">
	<div class="headdingtest">Service Wise Counter </div>
</div>
 
<div class="locationSpecialityPanel">
	<div class="headdingtest">Location Wise Counter </div>
</div>
   
<div class="specialityPanel">
	<div class="headdingtest">Speciality Wise Counter </div>
</div>
   
<div class="specialitytoDocPanel">
	<div class="headdingtest">Doctor Wise Counter </div>
</div>

 Corporate to Doctor 
<div class="corporatePanel">
	<div class="headdingtest">Corporate Wise Counter </div>
</div>
 
<div class="corporateServicePanel">
	<div class="headdingtest">Service Wise Counter </div>
</div>
   
<div class="corporateLocationPanel">
	<div class="headdingtest">Location Wise Counter </div>
</div>
   
<div class="corporateServiceSpecialityPanel">
	<div class="headdingtest">Speciality Wise Counter </div>
</div>

<div class="corporateServiceSpecialityDoctorPanel">
	<div class="headdingtest">Doctor Wise Counter </div>
</div>
 
-->

<ul class="nav_links">
	<li style="top: -6px;"><a href="#">
	<span class="threeLiner" style="     margin-right: 10px" ></span>
	</a> 
	<div class="dropdown profile_dropdown">
	<div class="arrow_dropdown">&nbsp;</div>
	<div class="one_column" >
	<ul> 
	<div class="allServicePanel">
	<li id="threelinerDataGraph1"><a href="#" onclick="showData('statusChart','','service');">
	<span class="glyphicon glyphicon-list-alt" aria-hidden="true" style="margin-right:4px;"></span>View Data</a></li>
	<li id="threelinerDataGraph"><a href="#" onclick="showStatusStackedBar('statusChart', 'service', '', 'All', 'All', '#09A1A4' );">
	<span class="glyphicon glyphicon-equalizer" aria-hidden="true" style="margin-right:4px;"></span>View Bar Graph</a></li>
	 
	 
	</div>
	<div class="allLocationPanelForService">
	<li id="threelinerDataGraph1"><a href="#" onclick="showData('statusChart','','locationForService');">
	<span class="glyphicon glyphicon-list-alt" aria-hidden="true" style="margin-right:4px;"></span>View Data</a></li>
	<li id="threelinerDataGraph"><a href="#" onclick="showStatusStackedBar('statusChart', 'locationForService', '', 'All', 'All', '#09A1A4');">
	<span class="glyphicon glyphicon-equalizer" aria-hidden="true" style="margin-right:4px;"></span>View Bar Graph</a></li>
	 
	</div>
	<div class="allServiceManagerPanelForService">
	<li id="threelinerDataGraph1"><a href="#" onclick="showData('statusChart','','serviceManagerForService');">
	<span class="glyphicon glyphicon-list-alt" aria-hidden="true" style="margin-right:4px;"></span>View Data</a></li>
	<li id="threelinerDataGraph"><a href="#" onclick="showStatusStackedBar('statusChart', 'serviceManagerForService', '', 'All', 'All', '#09A1A4' );">
	<span class="glyphicon glyphicon-equalizer" aria-hidden="true" style="margin-right:4px;"></span>View Bar Graph</a></li>
	 
	</div>
	
	<div class="LocationPanel">
	<li id="threelinerDataGraph1"><a href="#" onclick="showData('statusChart','','location');">
	<span class="glyphicon glyphicon-list-alt" aria-hidden="true" style="margin-right:4px;"></span>View Data</a></li>
	<li id="threelinerDataGraph"><a href="#" onclick="showStatusStackedBar('statusChart', 'location', '', 'All', 'All', '#09A1A4',testType1 );">
	<span class="glyphicon glyphicon-equalizer" aria-hidden="true" style="margin-right:4px;"></span>View Bar Graph</a></li>
	 
	</div>
	<div class="ServicePanelForLocation">
	<li id="threelinerDataGraph1"><a href="#" onclick="showData('statusChart','','serviceForLocation');">
	<span class="glyphicon glyphicon-list-alt" aria-hidden="true" style="margin-right:4px;"></span>View Data</a></li>
	<li id="threelinerDataGraph"><a href="#" onclick="showStatusStackedBar('statusChart', 'serviceForLocation', '', 'All', 'All', '#09A1A4' );">
	<span class="glyphicon glyphicon-equalizer" aria-hidden="true" style="margin-right:4px;"></span>View Bar Graph</a></li>
 	
	</div>
	
	<!-- serviceSpeciality -->
	<div class="serviceSpecialityPanel">
	<li id="threelinerDataGraph1"><a href="#" onclick="showData('statusChart','','serviceDOC');">
	<span class="glyphicon glyphicon-list-alt" aria-hidden="true" style="margin-right:4px;"></span>View Data</a></li>
	<li id="threelinerDataGraph"><a href="#" onclick="showStatusStackedBar('statusChart', 'serviceDOC', '', 'All', 'All', '#09A1A4' );">
	<span class="glyphicon glyphicon-equalizer" aria-hidden="true" style="margin-right:4px;"></span>View Bar Graph</a></li>
 	
	</div>
	
	<div class="locationSpecialityPanel">
	<li id="threelinerDataGraph1"><a href="#" onclick="showData('statusChart','','locationForSpeciality');">
	<span class="glyphicon glyphicon-list-alt" aria-hidden="true" style="margin-right:4px;"></span>View Data</a></li>
	<li id="threelinerDataGraph"><a href="#" onclick="showStatusStackedBar('statusChart', 'locationForSpeciality', '', 'All', 'All', '#09A1A4' );">
	<span class="glyphicon glyphicon-equalizer" aria-hidden="true" style="margin-right:4px;"></span>View Bar Graph</a></li>
 	
	</div>
	
	<div class="specialityPanel">
	<li id="threelinerDataGraph1"><a href="#" onclick="showData('statusChart','','locationForSpecialitys');">
	<span class="glyphicon glyphicon-list-alt" aria-hidden="true" style="margin-right:4px;"></span>View Data</a></li>
	<li id="threelinerDataGraph"><a href="#" onclick="showStatusStackedBar('statusChart', 'locationForSpecialitys', '', 'All', 'All', '#09A1A4' );">
	<span class="glyphicon glyphicon-equalizer" aria-hidden="true" style="margin-right:4px;"></span>View Bar Graph</a></li>
 	
	</div>
	
	<div class="specialitytoDocPanel">
	<li id="threelinerDataGraph1"><a href="#" onclick="showData('statusChart','','pecialitysToDoc');">
	<span class="glyphicon glyphicon-list-alt" aria-hidden="true" style="margin-right:4px;"></span>View Data</a></li>
	<li id="threelinerDataGraph"><a href="#" onclick="showStatusStackedBar('statusChart', 'pecialitysToDoc', '', 'All', 'All', '#09A1A4' );">
	<span class="glyphicon glyphicon-equalizer" aria-hidden="true" style="margin-right:4px;"></span>View Bar Graph</a></li>
 	
	</div>
	
	<!-- Corporate -->
	<div class="corporatePanel">
	<li id="threelinerDataGraph1"><a href="#" onclick="showData('statusChart','','corporate');">
	<span class="glyphicon glyphicon-list-alt" aria-hidden="true" style="margin-right:4px;"></span>View Data</a></li>
	<li id="threelinerDataGraph"><a href="#" onclick="showStatusStackedBar('statusChart', 'corporate', '', 'All', 'All', '#09A1A4' );">
	<span class="glyphicon glyphicon-equalizer" aria-hidden="true" style="margin-right:4px;"></span>View Bar Graph</a></li>
 	
	</div>
	<div class="corporateServicePanel">
	<li id="threelinerDataGraph1"><a href="#" onclick="showData('statusChart','','corporateService');">
	<span class="glyphicon glyphicon-list-alt" aria-hidden="true" style="margin-right:4px;"></span>View Data</a></li>
	<li id="threelinerDataGraph"><a href="#" onclick="showStatusStackedBar('statusChart', 'corporateService', '', 'All', 'All', '#09A1A4' );">
	<span class="glyphicon glyphicon-equalizer" aria-hidden="true" style="margin-right:4px;"></span>View Bar Graph</a></li>
 	
	</div>
	
	<div class="corporateLocationPanel">
	<li id="threelinerDataGraph1"><a href="#" onclick="showData('statusChart','','corporateLocation');">
	<span class="glyphicon glyphicon-list-alt" aria-hidden="true" style="margin-right:4px;"></span>View Data</a></li>
	<li id="threelinerDataGraph"><a href="#" onclick="showStatusStackedBar('statusChart', 'corporateLocation', '', 'All', 'All', '#09A1A4' );">
	<span class="glyphicon glyphicon-equalizer" aria-hidden="true" style="margin-right:4px;"></span>View Bar Graph</a></li>
 	
	</div>
	<div class="corporateServiceSpecialityPanel">
	<li id="threelinerDataGraph1"><a href="#" onclick="showData('statusChart','','corporateServiceSpeciality');">
	<span class="glyphicon glyphicon-list-alt" aria-hidden="true" style="margin-right:4px;"></span>View Data</a></li>
	<li id="threelinerDataGraph"><a href="#" onclick="showStatusStackedBar('statusChart', 'corporateServiceSpeciality', '', 'All', 'All', '#09A1A4' );">
	<span class="glyphicon glyphicon-equalizer" aria-hidden="true" style="margin-right:4px;"></span>View Bar Graph</a></li>
 	
	</div>
	<div class="corporateServiceSpecialityDoctorPanel">
	<li id="threelinerDataGraph1"><a href="#" onclick="showData('statusChart','','corporateServiceDoctor');">
	<span class="glyphicon glyphicon-list-alt" aria-hidden="true" style="margin-right:4px;"></span>View Data</a></li>
	<li id="threelinerDataGraph"><a href="#" onclick="showStatusStackedBar('statusChart', 'corporateServiceDoctor', '', 'All', 'All', '#09A1A4' );">
	<span class="glyphicon glyphicon-equalizer" aria-hidden="true" style="margin-right:4px;"></span>View Bar Graph</a></li>
 	
	</div>
	
	
	
	<div class="accountManagerPanel">
	<li id="threelinerDataGraph1"><a href="#" onclick="showData('statusChart','','accountManager');">
	<span class="glyphicon glyphicon-list-alt" aria-hidden="true" style="margin-right:4px;"></span>View Data</a></li>
	<li id="threelinerDataGraph"><a href="#" onclick="showStatusStackedBar('statusChart', 'accountManager', '', 'All', 'All', '#09A1A4' );">
	<span class="glyphicon glyphicon-equalizer" aria-hidden="true" style="margin-right:4px;"></span>View Bar Graph</a></li>
 	
	</div>
	 
	 <div class="corporatePanelForAcntMng">
	<li id="threelinerDataGraph1"><a href="#" onclick="showData('statusChart','','corporateForAcntMng');">
	<span class="glyphicon glyphicon-list-alt" aria-hidden="true" style="margin-right:4px;"></span>View Data</a></li>
	<li id="threelinerDataGraph"><a href="#" onclick="showStatusStackedBar('statusChart', 'corporateForAcntMng', '', 'All', 'All', '#09A1A4' );">
	<span class="glyphicon glyphicon-equalizer" aria-hidden="true" style="margin-right:4px;"></span>View Bar Graph</a></li>
	 
	 
	</div>
	<div class="servicePanelForAcntMng">
	<li id="threelinerDataGraph1"><a href="#" onclick="showData('statusChart','','serviceForAcntMng');">
	<span class="glyphicon glyphicon-list-alt" aria-hidden="true" style="margin-right:4px;"></span>View Data</a></li>
	<li id="threelinerDataGraph"><a href="#" onclick="showStatusStackedBar('statusChart', 'serviceForAcntMng', '', 'All', 'All', '#09A1A4' );">
	<span class="glyphicon glyphicon-equalizer" aria-hidden="true" style="margin-right:4px;"></span>View Bar Graph</a></li>
	 
	</div>
	<div class="locationPanelForAcntMng">
	<li id="threelinerDataGraph1"><a href="#" onclick="showData('statusChart','','locationForAcntMng');">
	<span class="glyphicon glyphicon-list-alt" aria-hidden="true" style="margin-right:4px;"></span>View Data</a></li>
	<li id="threelinerDataGraph"><a href="#" onclick="showStatusStackedBar('statusChart', 'locationForAcntMng', '', 'All', 'All', '#09A1A4' );">
	<span class="glyphicon glyphicon-equalizer" aria-hidden="true" style="margin-right:4px;"></span>View Bar Graph</a></li>
	 
	</div>
	<div class="serviceManagerPanelForAcntMng">
	<li id="threelinerDataGraph1"><a href="#" onclick="showData('statusChart','','serviceManagerForAcntMng');">
	<span class="glyphicon glyphicon-list-alt" aria-hidden="true" style="margin-right:4px;"></span>View Data</a></li>
	<li id="threelinerDataGraph"><a href="#" onclick="showStatusStackedBar('statusChart', 'serviceManagerForAcntMng', '', 'All', 'All', '#09A1A4' );">
	<span class="glyphicon glyphicon-equalizer" aria-hidden="true" style="margin-right:4px;"></span>View Bar Graph</a></li>
	 
	</div>
	
	<div class="serviceManagerPanel">
	<li id="threelinerDataGraph1"><a href="#" onclick="showData('statusChart','','serviceManager');">
	<span class="glyphicon glyphicon-list-alt" aria-hidden="true" style="margin-right:4px;"></span>View Data</a></li>
	<li id="threelinerDataGraph"><a href="#" onclick="showStatusStackedBar('statusChart', 'serviceManager', '', 'All', 'All', '#09A1A4' );">
	<span class="glyphicon glyphicon-equalizer" aria-hidden="true" style="margin-right:4px;"></span>View Bar Graph</a></li>
 	
	</div>
	</ul>
	</div>
	<div class="clear"></div>
	</div>
	</li>
</ul>
 
<div class="clear"></div>
   
<div id="statusChart" style=" width: 99%; height: 210px;"></div>
 
 
<div class="allServicePanel">
	<a href="#"><span class="label label-default" style="background-color: #09A1A4;" id="statusAll" onclick="showStatusStackedBar('statusChart', 'service', '', 'All', 'All', '#09A1A4' );">All</span></a>
	<a href="#"><span class="label label-danger" style="background-color:#F04E2F;" id="statusApp" onclick="showStatusStackedBar('statusChart','service','','Appointment','Appointment','#F04E2F');">Appointment</span></a>
	<a href="#"><span class="label label-success" style="background-color:#5cb85c;"  id="statusSch" onclick="showStatusStackedBar('statusChart','service','','Scheduled','Scheduled','#5cb85c');">Scheduled</span></a>
	<a href="#"><span class="label label-warning" style="background-color:#f0ad4e;" id="statusSerIn" onclick="showStatusStackedBar('statusChart','service','','Service In','Service In','#f0ad4e');">Service In</span></a>
	<a href="#"><span class="label label-primary" style="background-color:#4F84FC;"  id="statusSerOut" onclick="showStatusStackedBar('statusChart','service','','Service Out','Service Out','#4F84FC');">Service Out</span></a>
	<a href="#"><span class="label label-info" style="background-color:#57595D;"  id="cancel" onclick="showStatusStackedBar('statusChart','service','','Cancel','Cancel','#57595D');">Cancel</span></a>
</div>
<div class="allLocationPanelForService">
	<a href="#"><span class="label label-default" style="background-color: #09A1A4;" id="statusAll" onclick="showStatusStackedBar('statusChart', 'locationForService', '', 'All', 'All', '#09A1A4' );">All</span></a>
	<a href="#"><span class="label label-danger" style="background-color:#F04E2F;" id="statusApp" onclick="showStatusStackedBar('statusChart','locationForService','','Appointment','Appointment','#F04E2F');">Appointment</span></a>
	<a href="#"><span class="label label-success" style="background-color:#5cb85c;"  id="statusSch" onclick="showStatusStackedBar('statusChart','locationForService','','Scheduled','Scheduled','#5cb85c');">Scheduled</span></a>
	<a href="#"><span class="label label-warning" style="background-color:#f0ad4e;" id="statusSerIn" onclick="showStatusStackedBar('statusChart','locationForService','','Service In','Service In','#f0ad4e');">Service In</span></a>
	<a href="#"><span class="label label-primary" style="background-color:#4F84FC;"  id="statusSerOut" onclick="showStatusStackedBar('statusChart','locationForService','','Service Out','Service Out','#4F84FC');">Service Out</span></a>
	<a href="#"><span class="label label-info" style="background-color:#57595D;"  id="cancel" onclick="showStatusStackedBar('statusChart','locationForService','','Cancel','Cancel','#57595D');">Cancel</span></a>
</div>
<div class="allServiceManagerPanelForService">
	<a href="#"><span class="label label-default" style="background-color: #09A1A4;" id="statusAll" onclick="showStatusStackedBar('statusChart', 'serviceManagerForService', '', 'All', 'All', '#09A1A4' );">All</span></a>
	<a href="#"><span class="label label-danger" style="background-color:#F04E2F;" id="statusApp" onclick="showStatusStackedBar('statusChart','serviceManagerForService','','Appointment','Appointment','#F04E2F');">Appointment</span></a>
	<a href="#"><span class="label label-success" style="background-color:#5cb85c;"  id="statusSch" onclick="showStatusStackedBar('statusChart','serviceManagerForService','','Scheduled','Scheduled','#5cb85c');">Scheduled</span></a>
	<a href="#"><span class="label label-warning" style="background-color:#f0ad4e;" id="statusSerIn" onclick="showStatusStackedBar('statusChart','serviceManagerForService','','Service In','Service In','#f0ad4e');">Service In</span></a>
	<a href="#"><span class="label label-primary" style="background-color:#4F84FC;"  id="statusSerOut" onclick="showStatusStackedBar('statusChart','serviceManagerForService','','Service Out','Service Out','#4F84FC');">Service Out</span></a>
	<a href="#"><span class="label label-info" style="background-color:#57595D;"  id="cancel" onclick="showStatusStackedBar('statusChart','serviceManagerForService','','Cancel','Cancel','#57595D');">Cancel</span></a>
</div>







<div class="LocationPanel">
	<a href="#"><span class="label label-default" style="background-color: #09A1A4;" id="statusAll" onclick="showStatusStackedBar('statusChart', 'location', '', 'All', 'All', '#09A1A4' );">All</span></a>
	<a href="#"><span class="label label-danger" style="background-color:#F04E2F;" id="statusApp" onclick="showStatusStackedBar('statusChart','location','','Appointment','Appointment','#F04E2F');">Appointment</span></a>
	<a href="#"><span class="label label-success" style="background-color:#5cb85c;"  id="statusSch" onclick="showStatusStackedBar('statusChart','location','','Scheduled','Scheduled','#5cb85c');">Scheduled</span></a>
	<a href="#"><span class="label label-warning" style="background-color:#f0ad4e;" id="statusSerIn" onclick="showStatusStackedBar('statusChart','location','','Service In','Service In','#f0ad4e');">Service In</span></a>
	<a href="#"><span class="label label-primary" style="background-color:#4F84FC;"  id="statusSerOut" onclick="showStatusStackedBar('statusChart','location','','Service Out','Service Out','#4F84FC');">Service Out</span></a>
	<a href="#"><span class="label label-info" style="background-color:#57595D;"  id="cancel" onclick="showStatusStackedBar('statusChart','location','','Cancel','Cancel','#57595D');">Cancel</span></a>
</div>
<div class="ServicePanelForLocation">
	<a href="#"><span class="label label-default" style="background-color: #09A1A4;" id="statusAll" onclick="showStatusStackedBar('statusChart', 'serviceForLocation', '', 'All', 'All', '#09A1A4' );">All</span></a>
	<a href="#"><span class="label label-danger" style="background-color:#F04E2F;" id="statusApp" onclick="showStatusStackedBar('statusChart','serviceForLocation','','Appointment','Appointment','#F04E2F');">Appointment</span></a>
	<a href="#"><span class="label label-success" style="background-color:#5cb85c;"  id="statusSch" onclick="showStatusStackedBar('statusChart','serviceForLocation','','Scheduled','Scheduled','#5cb85c');">Scheduled</span></a>
	<a href="#"><span class="label label-warning" style="background-color:#f0ad4e;" id="statusSerIn" onclick="showStatusStackedBar('statusChart','serviceForLocation','','Service In','Service In','#f0ad4e');">Service In</span></a>
	<a href="#"><span class="label label-primary" style="background-color:#4F84FC;"  id="statusSerOut" onclick="showStatusStackedBar('statusChart','serviceForLocation','','Service Out','Service Out','#4F84FC');">Service Out</span></a>
	<a href="#"><span class="label label-info" style="background-color:#57595D;"  id="cancel" onclick="showStatusStackedBar('statusChart','serviceForLocation','','Cancel','Cancel','#57595D');">Cancel</span></a>
</div>

 <!-- Service to Speciality -->
  <div class="serviceSpecialityPanel">
	<a href="#"><span class="label label-default" style="background-color: #09A1A4;" id="statusAll" onclick="showStatusStackedBar('statusChart', 'locationForSpeciality', '', 'All', 'All', '#09A1A4' );">All</span></a>
	<a href="#"><span class="label label-danger" style="background-color:#F04E2F;" id="statusApp" onclick="showStatusStackedBar('statusChart','locationForSpeciality','','Appointment','Appointment','#F04E2F');">Appointment</span></a>
	<a href="#"><span class="label label-success" style="background-color:#5cb85c;"  id="statusSch" onclick="showStatusStackedBar('statusChart','locationForSpeciality','','Scheduled','Scheduled','#5cb85c');">Scheduled</span></a>
	<a href="#"><span class="label label-warning" style="background-color:#f0ad4e;" id="statusSerIn" onclick="showStatusStackedBar('statusChart','locationForSpeciality','','Service In','Service In','#f0ad4e');">Service In</span></a>
	<a href="#"><span class="label label-primary" style="background-color:#4F84FC;"  id="statusSerOut" onclick="showStatusStackedBar('statusChart','locationForSpeciality','','Service Out','Service Out','#4F84FC');">Service Out</span></a>
	<a href="#"><span class="label label-info" style="background-color:#57595D;"  id="cancel" onclick="showStatusStackedBar('statusChart','locationForSpeciality','','Cancel','Cancel','#57595D');">Cancel</span></a>
</div>

<div class="locationSpecialityPanel">
	<a href="#"><span class="label label-default" style="background-color: #09A1A4;" id="statusAll" onclick="showStatusStackedBar('statusChart', 'locationForSpeciality', '', 'All', 'All', '#09A1A4' );">All</span></a>
	<a href="#"><span class="label label-danger" style="background-color:#F04E2F;" id="statusApp" onclick="showStatusStackedBar('statusChart','locationForSpeciality','','Appointment','Appointment','#F04E2F');">Appointment</span></a>
	<a href="#"><span class="label label-success" style="background-color:#5cb85c;"  id="statusSch" onclick="showStatusStackedBar('statusChart','locationForSpeciality','','Scheduled','Scheduled','#5cb85c');">Scheduled</span></a>
	<a href="#"><span class="label label-warning" style="background-color:#f0ad4e;" id="statusSerIn" onclick="showStatusStackedBar('statusChart','locationForSpeciality','','Service In','Service In','#f0ad4e');">Service In</span></a>
	<a href="#"><span class="label label-primary" style="background-color:#4F84FC;"  id="statusSerOut" onclick="showStatusStackedBar('statusChart','locationForSpeciality','','Service Out','Service Out','#4F84FC');">Service Out</span></a>
	<a href="#"><span class="label label-info" style="background-color:#57595D;"  id="cancel" onclick="showStatusStackedBar('statusChart','locationForSpeciality','','Cancel','Cancel','#57595D');">Cancel</span></a>
</div>

<div class="specialityPanel">
	<a href="#"><span class="label label-default" style="background-color: #09A1A4;" id="statusAll" onclick="showStatusStackedBar('statusChart', 'locationForSpecialitys', '', 'All', 'All', '#09A1A4' );">All</span></a>
	<a href="#"><span class="label label-danger" style="background-color:#F04E2F;" id="statusApp" onclick="showStatusStackedBar('statusChart','locationForSpecialitys','','Appointment','Appointment','#F04E2F');">Appointment</span></a>
	<a href="#"><span class="label label-success" style="background-color:#5cb85c;"  id="statusSch" onclick="showStatusStackedBar('statusChart','locationForSpecialitys','','Scheduled','Scheduled','#5cb85c');">Scheduled</span></a>
	<a href="#"><span class="label label-warning" style="background-color:#f0ad4e;" id="statusSerIn" onclick="showStatusStackedBar('statusChart','locationForSpecialitys','','Service In','Service In','#f0ad4e');">Service In</span></a>
	<a href="#"><span class="label label-primary" style="background-color:#4F84FC;"  id="statusSerOut" onclick="showStatusStackedBar('statusChart','locationForSpecialitys','','Service Out','Service Out','#4F84FC');">Service Out</span></a>
	<a href="#"><span class="label label-info" style="background-color:#57595D;"  id="cancel" onclick="showStatusStackedBar('statusChart','locationForSpecialitys','','Cancel','Cancel','#57595D');">Cancel</span></a>
</div>



<div class="specialitytoDocPanel">
	<a href="#"><span class="label label-default" style="background-color: #09A1A4;" id="statusAll" onclick="showStatusStackedBar('statusChart', 'pecialitysToDoc', '', 'All', 'All', '#09A1A4' );">All</span></a>
	<a href="#"><span class="label label-danger" style="background-color:#F04E2F;" id="statusApp" onclick="showStatusStackedBar('statusChart','pecialitysToDoc','','Appointment','Appointment','#F04E2F');">Appointment</span></a>
	<a href="#"><span class="label label-success" style="background-color:#5cb85c;"  id="statusSch" onclick="showStatusStackedBar('statusChart','pecialitysToDoc','','Scheduled','Scheduled','#5cb85c');">Scheduled</span></a>
	<a href="#"><span class="label label-warning" style="background-color:#f0ad4e;" id="statusSerIn" onclick="showStatusStackedBar('statusChart','pecialitysToDoc','','Service In','Service In','#f0ad4e');">Service In</span></a>
	<a href="#"><span class="label label-primary" style="background-color:#4F84FC;"  id="statusSerOut" onclick="showStatusStackedBar('statusChart','pecialitysToDoc','','Service Out','Service Out','#4F84FC');">Service Out</span></a>
	<a href="#"><span class="label label-info" style="background-color:#57595D;"  id="cancel" onclick="showStatusStackedBar('statusChart','pecialitysToDoc','','Cancel','Cancel','#57595D');">Cancel</span></a>
</div>

<!-- Doc -->
<div class="corporatePanel">
	<a href="#"><span class="label label-default" style="background-color: #09A1A4;" id="statusAll" onclick="showStatusStackedBar('statusChart', 'corporate', '', 'All', 'All', '#09A1A4' );">All</span></a>
	<a href="#"><span class="label label-danger" style="background-color:#F04E2F;" id="statusApp" onclick="showStatusStackedBar('statusChart','corporate','','Appointment','Appointment','#F04E2F');">Appointment</span></a>
	<a href="#"><span class="label label-success" style="background-color:#5cb85c;"  id="statusSch" onclick="showStatusStackedBar('statusChart','corporate','','Scheduled','Scheduled','#5cb85c');">Scheduled</span></a>
	<a href="#"><span class="label label-warning" style="background-color:#f0ad4e;" id="statusSerIn" onclick="showStatusStackedBar('statusChart','corporate','','Service In','Service In','#f0ad4e');">Service In</span></a>
	<a href="#"><span class="label label-primary" style="background-color:#4F84FC;"  id="statusSerOut" onclick="showStatusStackedBar('statusChart','corporate','','Service Out','Service Out','#4F84FC');">Service Out</span></a>
	<a href="#"><span class="label label-info" style="background-color:#57595D;"  id="cancel" onclick="showStatusStackedBar('statusChart','corporate','','Cancel','Cancel','#57595D');">Cancel</span></a>
</div>
<div class="corporateServicePanel">
	<a href="#"><span class="label label-default" style="background-color: #09A1A4;" id="statusAll" onclick="showStatusStackedBar('statusChart', 'corporateService', '', 'All', 'All', '#09A1A4' );">All</span></a>
	<a href="#"><span class="label label-danger" style="background-color:#F04E2F;" id="statusApp" onclick="showStatusStackedBar('statusChart','corporateService','','Appointment','Appointment','#F04E2F');">Appointment</span></a>
	<a href="#"><span class="label label-success" style="background-color:#5cb85c;"  id="statusSch" onclick="showStatusStackedBar('statusChart','corporateService','','Scheduled','Scheduled','#5cb85c');">Scheduled</span></a>
	<a href="#"><span class="label label-warning" style="background-color:#f0ad4e;" id="statusSerIn" onclick="showStatusStackedBar('statusChart','corporateService','','Service In','Service In','#f0ad4e');">Service In</span></a>
	<a href="#"><span class="label label-primary" style="background-color:#4F84FC;"  id="statusSerOut" onclick="showStatusStackedBar('statusChart','corporate','','Service Out','Service Out','#4F84FC');">Service Out</span></a>
	<a href="#"><span class="label label-info" style="background-color:#57595D;"  id="cancel" onclick="showStatusStackedBar('statusChart','corporate','','Cancel','Cancel','#57595D');">Cancel</span></a>
</div>
 <div class="corporateLocationPanel">
	<a href="#"><span class="label label-default" style="background-color: #09A1A4;" id="statusAll" onclick="showStatusStackedBar('statusChart', 'corporateLocation', '', 'All', 'All', '#09A1A4' );">All</span></a>
	<a href="#"><span class="label label-danger" style="background-color:#F04E2F;" id="statusApp" onclick="showStatusStackedBar('statusChart','corporateLocation','','Appointment','Appointment','#F04E2F');">Appointment</span></a>
	<a href="#"><span class="label label-success" style="background-color:#5cb85c;"  id="statusSch" onclick="showStatusStackedBar('statusChart','corporateLocation','','Scheduled','Scheduled','#5cb85c');">Scheduled</span></a>
	<a href="#"><span class="label label-warning" style="background-color:#f0ad4e;" id="statusSerIn" onclick="showStatusStackedBar('statusChart','corporateLocation','','Service In','Service In','#f0ad4e');">Service In</span></a>
	<a href="#"><span class="label label-primary" style="background-color:#4F84FC;"  id="statusSerOut" onclick="showStatusStackedBar('statusChart','corporateLocation','','Service Out','Service Out','#4F84FC');">Service Out</span></a>
	<a href="#"><span class="label label-info" style="background-color:#57595D;"  id="cancel" onclick="showStatusStackedBar('statusChart','corporateLocation','','Cancel','Cancel','#57595D');">Cancel</span></a>
</div>
<div class="corporateServiceSpecialityPanel">
	<a href="#"><span class="label label-default" style="background-color: #09A1A4;" id="statusAll" onclick="showStatusStackedBar('statusChart', 'corporateServiceSpeciality', '', 'All', 'All', '#09A1A4' );">All</span></a>
	<a href="#"><span class="label label-danger" style="background-color:#F04E2F;" id="statusApp" onclick="showStatusStackedBar('statusChart','corporateServiceSpeciality','','Appointment','Appointment','#F04E2F');">Appointment</span></a>
 	<a href="#"><span class="label label-success" style="background-color:#5cb85c;"  id="statusSch" onclick="showStatusStackedBar('statusChart','corporateServiceSpeciality','','Scheduled','Scheduled','#5cb85c');">Scheduled</span></a>
	<a href="#"><span class="label label-warning" style="background-color:#f0ad4e;" id="statusSerIn" onclick="showStatusStackedBar('statusChart','corporateServiceSpeciality','','Service In','Service In','#f0ad4e');">Service In</span></a>
	<a href="#"><span class="label label-primary" style="background-color:#4F84FC;"  id="statusSerOut" onclick="showStatusStackedBar('statusChart','corporateServiceSpeciality','','Service Out','Service Out','#4F84FC');">Service Out</span></a>
	<a href="#"><span class="label label-info" style="background-color:#57595D;"  id="cancel" onclick="showStatusStackedBar('statusChart','corporateServiceSpeciality','','Cancel','Cancel','#57595D');">Cancel</span></a>
</div>
<div class="corporateServiceSpecialityDoctorPanel">
	<a href="#"><span class="label label-default" style="background-color: #09A1A4;" id="statusAll" onclick="showStatusStackedBar('statusChart', 'corporateServiceDoctor', '', 'All', 'All', '#09A1A4' );">All</span></a>
	<a href="#"><span class="label label-danger" style="background-color:#F04E2F;" id="statusApp" onclick="showStatusStackedBar('statusChart','corporateServiceDoctor','','Appointment','Appointment','#F04E2F');">Appointment</span></a>
	<a href="#"><span class="label label-success" style="background-color:#5cb85c;"  id="statusSch" onclick="showStatusStackedBar('statusChart','corporateServiceDoctor','','Scheduled','Scheduled','#5cb85c');">Scheduled</span></a>
	<a href="#"><span class="label label-warning" style="background-color:#f0ad4e;" id="statusSerIn" onclick="showStatusStackedBar('statusChart','corporateServiceDoctor','','Service In','Service In','#f0ad4e');">Service In</span></a>
	<a href="#"><span class="label label-primary" style="background-color:#4F84FC;"  id="statusSerOut" onclick="showStatusStackedBar('statusChart','corporateServiceDoctor','','Service Out','Service Out','#4F84FC');">Service Out</span></a>
	<a href="#"><span class="label label-info" style="background-color:#57595D;"  id="cancel" onclick="showStatusStackedBar('statusChart','corporateServiceDoctor','','Cancel','Cancel','#57595D');">Cancel</span></a>
</div>


<div class="accountManagerPanel">
	<a href="#"><span class="label label-default" style="background-color: #09A1A4;" id="statusAll" onclick="showStatusStackedBar('statusChart', 'accountManager', '', 'All', 'All', '#09A1A4' );">All</span></a>
	<a href="#"><span class="label label-danger" style="background-color:#F04E2F;" id="statusApp" onclick="showStatusStackedBar('statusChart','accountManager','','Appointment','Appointment','#F04E2F');">Appointment</span></a>
	<a href="#"><span class="label label-success" style="background-color:#5cb85c;"  id="statusSch" onclick="showStatusStackedBar('statusChart','accountManager','','Scheduled','Scheduled','#5cb85c');">Scheduled</span></a>
	<a href="#"><span class="label label-warning" style="background-color:#f0ad4e;" id="statusSerIn" onclick="showStatusStackedBar('statusChart','accountManager','','Service In','Service In','#f0ad4e');">Service In</span></a>
	<a href="#"><span class="label label-primary" style="background-color:#4F84FC;"  id="statusSerOut" onclick="showStatusStackedBar('statusChart','accountManager','','Service Out','Service Out','#4F84FC');">Service Out</span></a>
	<a href="#"><span class="label label-info" style="background-color:#57595D;"  id="cancel" onclick="showStatusStackedBar('statusChart','accountManager','','Cancel','Cancel','#57595D');">Cancel</span></a>
</div>
<div class="corporatePanelForAcntMng">
	<a href="#"><span class="label label-default" style="background-color: #09A1A4;" id="statusAll" onclick="showStatusStackedBar('statusChart', 'corporateForAcntMng', '', 'All', 'All', '#09A1A4' );">All</span></a>
	<a href="#"><span class="label label-danger" style="background-color:#F04E2F;" id="statusApp" onclick="showStatusStackedBar('statusChart','corporateForAcntMng','','Appointment','Appointment','#F04E2F');">Appointment</span></a>
	<a href="#"><span class="label label-success" style="background-color:#5cb85c;"  id="statusSch" onclick="showStatusStackedBar('statusChart','corporateForAcntMng','','Scheduled','Scheduled','#5cb85c');">Scheduled</span></a>
	<a href="#"><span class="label label-warning" style="background-color:#f0ad4e;" id="statusSerIn" onclick="showStatusStackedBar('statusChart','corporateForAcntMng','','Service In','Service In','#f0ad4e');">Service In</span></a>
	<a href="#"><span class="label label-primary" style="background-color:#4F84FC;"  id="statusSerOut" onclick="showStatusStackedBar('statusChart','corporateForAcntMng','','Service Out','Service Out','#4F84FC');">Service Out</span></a>
	<a href="#"><span class="label label-info" style="background-color:#57595D;"  id="cancel" onclick="showStatusStackedBar('statusChart','corporateForAcntMng','','Cancel','Cancel','#57595D');">Cancel</span></a>
</div>
<div class="servicePanelForAcntMng">
	<a href="#"><span class="label label-default" style="background-color: #09A1A4;" id="statusAll" onclick="showStatusStackedBar('statusChart', 'serviceForAcntMng', '', 'All', 'All', '#09A1A4' );">All</span></a>
	<a href="#"><span class="label label-danger" style="background-color:#F04E2F;" id="statusApp" onclick="showStatusStackedBar('statusChart','serviceForAcntMng','','Appointment','Appointment','#F04E2F');">Appointment</span></a>
	<a href="#"><span class="label label-success" style="background-color:#5cb85c;"  id="statusSch" onclick="showStatusStackedBar('statusChart','serviceForAcntMng','','Scheduled','Scheduled','#5cb85c');">Scheduled</span></a>
	<a href="#"><span class="label label-warning" style="background-color:#f0ad4e;" id="statusSerIn" onclick="showStatusStackedBar('statusChart','serviceForAcntMng','','Service In','Service In','#f0ad4e');">Service In</span></a>
	<a href="#"><span class="label label-primary" style="background-color:#4F84FC;"  id="statusSerOut" onclick="showStatusStackedBar('statusChart','serviceForAcntMng','','Service Out','Service Out','#4F84FC');">Service Out</span></a>
	<a href="#"><span class="label label-info" style="background-color:#57595D;"  id="cancel" onclick="showStatusStackedBar('statusChart','serviceForAcntMng','','Cancel','Cancel','#57595D');">Cancel</span></a>
</div>
<div class="locationPanelForAcntMng">
	<a href="#"><span class="label label-default" style="background-color: #09A1A4;" id="statusAll" onclick="showStatusStackedBar('statusChart', 'locationForAcntMng', '', 'All', 'All', '#09A1A4' );">All</span></a>
	<a href="#"><span class="label label-danger" style="background-color:#F04E2F;" id="statusApp" onclick="showStatusStackedBar('statusChart','locationForAcntMng','','Appointment','Appointment','#F04E2F');">Appointment</span></a>
	<a href="#"><span class="label label-success" style="background-color:#5cb85c;"  id="statusSch" onclick="showStatusStackedBar('statusChart','locationForAcntMng','','Scheduled','Scheduled','#5cb85c');">Scheduled</span></a>
	<a href="#"><span class="label label-warning" style="background-color:#f0ad4e;" id="statusSerIn" onclick="showStatusStackedBar('statusChart','locationForAcntMng','','Service In','Service In','#f0ad4e');">Service In</span></a>
	<a href="#"><span class="label label-primary" style="background-color:#4F84FC;"  id="statusSerOut" onclick="showStatusStackedBar('statusChart','locationForAcntMng','','Service Out','Service Out','#4F84FC');">Service Out</span></a>
	<a href="#"><span class="label label-info" style="background-color:#57595D;"  id="cancel" onclick="showStatusStackedBar('statusChart','locationForAcntMng','','Cancel','Cancel','#57595D');">Cancel</span></a>
</div>
<div class="serviceManagerPanelForAcntMng">
	<a href="#"><span class="label label-default" style="background-color: #09A1A4;" id="statusAll" onclick="showStatusStackedBar('statusChart', 'serviceManagerForAcntMng', '', 'All', 'All', '#09A1A4' );">All</span></a>
	<a href="#"><span class="label label-danger" style="background-color:#F04E2F;" id="statusApp" onclick="showStatusStackedBar('statusChart','serviceManagerForAcntMng','','Appointment','Appointment','#F04E2F');">Appointment</span></a>
	<a href="#"><span class="label label-success" style="background-color:#5cb85c;"  id="statusSch" onclick="showStatusStackedBar('statusChart','serviceManagerForAcntMng','','Scheduled','Scheduled','#5cb85c');">Scheduled</span></a>
	<a href="#"><span class="label label-warning" style="background-color:#f0ad4e;" id="statusSerIn" onclick="showStatusStackedBar('statusChart','serviceManagerForAcntMng','','Service In','Service In','#f0ad4e');">Service In</span></a>
	<a href="#"><span class="label label-primary" style="background-color:#4F84FC;"  id="statusSerOut" onclick="showStatusStackedBar('statusChart','serviceManagerForAcntMng','','Service Out','Service Out','#4F84FC');">Service Out</span></a>
	<a href="#"><span class="label label-info" style="background-color:#57595D;"  id="cancel" onclick="showStatusStackedBar('statusChart','serviceManagerForAcntMng','','Cancel','Cancel','#57595D');">Cancel</span></a>
</div>
 
<div class="serviceManagerPanel">
	<a href="#"><span class="label label-default" style="background-color: #09A1A4;" id="statusAll" onclick="showStatusStackedBar('statusChart', 'serviceManager', '', 'All', 'All', '#09A1A4' );">All</span></a>
	<a href="#"><span class="label label-danger" style="background-color:#F04E2F;" id="statusApp" onclick="showStatusStackedBar('statusChart','serviceManager','','Appointment','Appointment','#F04E2F');">Appointment</span></a>
	<a href="#"><span class="label label-success" style="background-color:#5cb85c;"  id="statusSch" onclick="showStatusStackedBar('statusChart','serviceManager','','Scheduled','Scheduled','#5cb85c');">Scheduled</span></a>
	<a href="#"><span class="label label-warning" style="background-color:#f0ad4e;" id="statusSerIn" onclick="showStatusStackedBar('statusChart','serviceManager','','Service In','Service In','#f0ad4e');">Service In</span></a>
	<a href="#"><span class="label label-primary" style="background-color:#4F84FC;"  id="statusSerOut" onclick="showStatusStackedBar('statusChart','serviceManager','','Service Out','Service Out','#4F84FC');">Service Out</span></a>
	<a href="#"><span class="label label-info" style="background-color:#57595D;"  id="cancel" onclick="showStatusStackedBar('statusChart','serviceManager','','Cancel','Cancel','#57595D');">Cancel</span></a>
</div>
  
 
 </div> 
</div>
</div>

<!-- Ticket Status Counter Dashboard  End -->
 
  <!-- Productivity Counter Start  -->
  
   <div class="contentdiv-smallNewDash" style="overflow: hidden;width: 99%;height: 370px;" id="productivity">
	<div id="counterChart1">
	
 <div style="  float: right;    margin-right: 45px;">
 
 <!--
	 
	 <button id="corp2" type="button" class="btn btn-default btn-sm" onclick="showProductivityStackedBar('productivityChart', 'corporateProductivity', '', 'All', 'All', '#09A1A4');">
	<span   aria-hidden="true"></span> Corporate Wise
	</button>
	<button  id="Sertodoc2" type="button" class="btn btn-default btn-sm" onclick="showProductivityStackedBar('productivityChart', 'serviceDOCProductivity', '', 'All', 'All', '#09A1A4');">
	<span   aria-hidden="true"></span> Service Wise-Doc
	</button>
	<button id="spec2" type="button" class="btn btn-default btn-sm" onclick="showProductivityStackedBar('productivityChart', 'serviceProductivity', '', 'All', 'All', '#09A1A4');">
	  <span   aria-hidden="true"></span> Service Wise 
	</button>
	<button  id="loc2" type="button" class="btn btn-default btn-sm" onclick="showProductivityStackedBar('productivityChart', 'locationProductivity', '', 'All', 'All', '#09A1A4');">
	  <span   aria-hidden="true"></span> Location Wise
	</button>
	<button  id="ac2" type="button" class="btn btn-default btn-sm" onclick="showProductivityStackedBar('productivityChart', 'accountManagerProductivity', '', 'All', 'All', '#09A1A4');">
	  <span   aria-hidden="true"></span> Account Manager Wise
	</button>
	<button  id="test3" type="button" class="btn btn-default btn-sm" onclick="showProductivityStackedBar('productivityChart', 'serviceManagerProductivity', '', 'All', 'All', '#09A1A4');">
	  <span   aria-hidden="true"></span> Service Manager Wise
	</button>
	 
-->
 <input checked id="one1" name="tabs1" type="radio" style="opacity:0" onclick="showProductivityStackedBar('productivityChart', 'corporateProductivity', '', 'All', 'All', '#09A1A4');">
    <label class="lable1" for="one1">Corporate Wise</label>
    <input id="two1" name="tabs1" type="radio" value="Two " style="opacity:0" onclick="showProductivityStackedBar('productivityChart', 'locationProductivity', '', 'All', 'All', '#09A1A4');">
    <label class="lable1" for="two1">Location Wise</label>
    <input id="three1" name="tabs1" type="radio" style="opacity:0" onclick="showProductivityStackedBar('productivityChart', 'accountManagerProductivity', '', 'All', 'All', '#09A1A4');">
    <label class="lable1" for="three1">Account Manager Wise</label>
 	<input id="four1" name="tabs1" type="radio" style="opacity:0" onclick="showProductivityStackedBar('productivityChart', 'serviceManagerProductivity', '', 'All', 'All', '#09A1A4');">
    <label class="lable1" for="four1">Service Manager Wise</label>
     <input id="five1" name="tabs1" type="radio" style="opacity:0" onclick="showProductivityStackedBar('productivityChart', 'serviceDOCProductivity', '', 'All', 'All', '#09A1A4');">
    <label class="lable1" for="five1">Service Wise To Doctor</label>
    <input id="six1" name="tabs1" type="radio" style="opacity:0" onclick="showProductivityStackedBar('productivityChart', 'serviceProductivity', '', 'All', 'All', '#09A1A4');">
    <label class="lable1" for="six1" style="margin-top: -23px;">Service Wise To Service Manager</label>
  
  	 <div  class="backbttnDiv2" style="  float: left;">
	 
	 
	 	<button  id="backbttn2"  style="float:left;display:none"  type="button" class="btn btn-default btn-sm" >
	  <span class="glyphicon glyphicon-arrow-left" aria-hidden="true"></span> Back
	</button>
	 
  
 </div>	
 <span  id="x2" style="float:left;margin-top: 3px;margin-right:3px"> Corporate Name:</span>
 <div style="float:left;">   <s:select  
	                                id="statusForProductivity"
	                              name="statusForProductivity"
	                              list="corporateList"
	                              headerKey="-1"
	                              headerValue="All"
	                                cssClass="select"
	                          cssStyle="width:25%;height:22px;  background: #EDECEC;margin-left: -145px"
	                          onchange=" getDataForProductivityCounter();"
	                              >
	                  </s:select> </div>
	                  
	                  
	                  
	                  
	                  <!--
 
 
   
 
 
  <div class="allServiceProductivityPanel">
<div class="headdingtest">Service Wise Productivity Counter</div>
 </div>
 
  <div class="allLocationProductivityPanelForService">
<div class="headdingtest">Location Wise Productivity Counter </div>
 </div>
 
  <div class="allServiceManagerProductivityPanelForService">
<div class="headdingtest">Service Manager Wise Productivity Counter </div>
 </div>
  
  <div class="LocationProductivityPanel">
<div class="headdingtest">Location Wise Productivity Counter</div>
 </div>
 
   <div class="ServiceProductivityPanelForLocation">
<div class="headdingtest">Service Wise Productivity Counter </div>
 </div>
 
   
   
   
 
  <div class="accountManagerProductivityPanel">
<div class="headdingtest">Account Manager Wise Productivity Counter </div>
 </div>
  <div class="corporateProductivityPanelForAcntMng">
<div class="headdingtest">Corporate Wise Productivity Counter </div>
 </div>
 <div class="serviceProductivityPanelForAcntMng">
<div class="headdingtest">Service Wise Productivity Counter  </div>
 </div>
  <div class="locationProductivityPanelForAcntMng">
<div class="headdingtest">Location Wise Productivity Counter  </div>
 </div>
 <div class="serviceManagerProductivityPanelForAcntMng">
<div class="headdingtest">Service Manager Wise Productivity Counter  </div>
 </div>

<div class="serviceManagerProductivityPanel">
<div class="headdingtest">Service Manager Wise Productivity Counter </div>
 </div>
 
 
  Service to Doctor 
<div class="serviceSpecialityProductivityPanel">
	<div class="headdingtest">Service Wise Productivity Counter </div>
</div>
 
<div class="locationSpecialityProductivityPanel">
	<div class="headdingtest">Location Wise Productivity Counter </div>
</div>
   
<div class="specialityProductivityPanel">
	<div class="headdingtest">Speciality Productivity Wise Counter </div>
</div>
   
<div class="specialitytoDocProductivityPanel">
	<div class="headdingtest">Doctor Wise Productivity Counter </div>
</div>

 Corporate to Doctor 
<div class="corporateProductivityPanel">
	<div class="headdingtest">Corporate Wise Productivity Counter </div>
</div>
 
<div class="corporateServiceProductivityPanel">
	<div class="headdingtest">Service Wise Productivity Counter </div>
</div>
   
<div class="corporateLocationProductivityPanel">
	<div class="headdingtest">Location Wise Productivity Counter </div>
</div>
   
<div class="corporateServiceSpecialityProductivityPanel">
	<div class="headdingtest">Service Manager Productivity Wise Counter </div>
</div>
 
 <div class="corporateServiceDoctorProductivityPanel">
	<div class="headdingtest">Service Manager Productivity Wise Counter </div>
</div>
 
 -->
 
 <ul class="nav_links">
	<li style="top: -6px;"><a href="#">
	<span class="threeLiner" style="margin-right: 10px" ></span>
	 </a> 
	
	
	<div class="dropdown profile_dropdown">
	<div class="arrow_dropdown">&nbsp;</div>
	<div class="one_column" >
	<ul> 
	<div class="allServiceProductivityPanel">
	<li id="threelinerDataGraph1"><a href="#" onclick="showData('productivityChart','','serviceProductivity');">
	<span class="glyphicon glyphicon-list-alt" aria-hidden="true" style="margin-right:4px;"></span>View Data</a></li>
	<li id="threelinerDataGraph"><a href="#" onclick="showProductivityStackedBar('productivityChart', 'serviceProductivity', '', 'All', 'All', '#09A1A4' );">
	<span class="glyphicon glyphicon-equalizer" aria-hidden="true" style="margin-right:4px;"></span>View Bar Graph</a></li>
	 
	 
	</div>
	<div class="allLocationProductivityPanelForService">
	<li id="threelinerDataGraph1"><a href="#" onclick="showData('productivityChart','','locationForServiceProductivity');">
	<span class="glyphicon glyphicon-list-alt" aria-hidden="true" style="margin-right:4px;"></span>View Data</a></li>
	<li id="threelinerDataGraph"><a href="#" onclick="showProductivityStackedBar('productivityChart', 'locationForServiceProductivity', '', 'All', 'All', '#09A1A4');">
	<span class="glyphicon glyphicon-equalizer" aria-hidden="true" style="margin-right:4px;"></span>View Bar Graph</a></li>
	 
	</div>
	<div class="allServiceManagerProductivityPanelForService">
	<li id="threelinerDataGraph1"><a href="#" onclick="showData('productivityChart','','serviceManagerForServiceProductivity');">
	<span class="glyphicon glyphicon-list-alt" aria-hidden="true" style="margin-right:4px;"></span>View Data</a></li>
	<li id="threelinerDataGraph"><a href="#" onclick="showProductivityStackedBar('productivityChart', 'serviceManagerForServiceProductivity', '', 'All', 'All', '#09A1A4' );">
	<span class="glyphicon glyphicon-equalizer" aria-hidden="true" style="margin-right:4px;"></span>View Bar Graph</a></li>
	 
	</div>
	
	<div class="LocationProductivityPanel">
	<li id="threelinerDataGraph1"><a href="#" onclick="showData('productivityChart','','locationProductivity');">
	<span class="glyphicon glyphicon-list-alt" aria-hidden="true" style="margin-right:4px;"></span>View Data</a></li>
	<li id="threelinerDataGraph"><a href="#" onclick="showProductivityStackedBar('productivityChart', 'locationProductivity', '', 'All', 'All', '#09A1A4',testType1 );">
	<span class="glyphicon glyphicon-equalizer" aria-hidden="true" style="margin-right:4px;"></span>View Bar Graph</a></li>
	 
	</div>
	<div class="ServiceProductivityPanelForLocation">
	<li id="threelinerDataGraph1"><a href="#" onclick="showData('productivityChart','','serviceForLocationProductivity');">
	<span class="glyphicon glyphicon-list-alt" aria-hidden="true" style="margin-right:4px;"></span>View Data</a></li>
	<li id="threelinerDataGraph"><a href="#" onclick="showProductivityStackedBar('productivityChart', 'serviceForLocationProductivity', '', 'All', 'All', '#09A1A4' );">
	<span class="glyphicon glyphicon-equalizer" aria-hidden="true" style="margin-right:4px;"></span>View Bar Graph</a></li>
 	
	</div>
	
	<div class="accountManagerProductivityPanel">
	<li id="threelinerDataGraph1"><a href="#" onclick="showData('productivityChart','','accountManagerProductivity');">
	<span class="glyphicon glyphicon-list-alt" aria-hidden="true" style="margin-right:4px;"></span>View Data</a></li>
	<li id="threelinerDataGraph"><a href="#" onclick="showProductivityStackedBar('productivityChart', 'accountManagerProductivity', '', 'All', 'All', '#09A1A4' );">
	<span class="glyphicon glyphicon-equalizer" aria-hidden="true" style="margin-right:4px;"></span>View Bar Graph</a></li>
 	
	</div>
	 
	 <div class="corporateProductivityPanelForAcntMng">
	<li id="threelinerDataGraph1"><a href="#" onclick="showData('productivityChart','','corporateForAcntMngProductivity');">
	<span class="glyphicon glyphicon-list-alt" aria-hidden="true" style="margin-right:4px;"></span>View Data</a></li>
	<li id="threelinerDataGraph"><a href="#" onclick="showProductivityStackedBar('productivityChart', 'corporateForAcntMngProductivity', '', 'All', 'All', '#09A1A4' );">
	<span class="glyphicon glyphicon-equalizer" aria-hidden="true" style="margin-right:4px;"></span>View Bar Graph</a></li>
	 
	 
	</div>
	<div class="serviceProductivityPanelForAcntMng">
	<li id="threelinerDataGraph1"><a href="#" onclick="showData('productivityChart','','serviceForAcntMngProductivity');">
	<span class="glyphicon glyphicon-list-alt" aria-hidden="true" style="margin-right:4px;"></span>View Data</a></li>
	<li id="threelinerDataGraph"><a href="#" onclick="showProductivityStackedBar('productivityChart', 'serviceForAcntMngProductivity', '', 'All', 'All', '#09A1A4' );">
	<span class="glyphicon glyphicon-equalizer" aria-hidden="true" style="margin-right:4px;"></span>View Bar Graph</a></li>
	 
	</div>
	<div class="locationProductivityPanelForAcntMng">
	<li id="threelinerDataGraph1"><a href="#" onclick="showData('productivityChart','','locationForAcntMngProductivity');">
	<span class="glyphicon glyphicon-list-alt" aria-hidden="true" style="margin-right:4px;"></span>View Data</a></li>
	<li id="threelinerDataGraph"><a href="#" onclick="showProductivityStackedBar('productivityChart', 'locationForAcntMngProductivity', '', 'All', 'All', '#09A1A4' );">
	<span class="glyphicon glyphicon-equalizer" aria-hidden="true" style="margin-right:4px;"></span>View Bar Graph</a></li>
	 
	</div>
	<div class="serviceManagerProductivityPanelForAcntMng">
	<li id="threelinerDataGraph1"><a href="#" onclick="showData('productivityChart','','serviceManagerForAcntMngProductivity');">
	<span class="glyphicon glyphicon-list-alt" aria-hidden="true" style="margin-right:4px;"></span>View Data</a></li>
	<li id="threelinerDataGraph"><a href="#" onclick="showProductivityStackedBar('productivityChart', 'serviceManagerForAcntMngProductivity', '', 'All', 'All', '#09A1A4' );">
	<span class="glyphicon glyphicon-equalizer" aria-hidden="true" style="margin-right:4px;"></span>View Bar Graph</a></li>
	 
	</div>
	<div class="serviceManagerProductivityPanel">
	<li id="threelinerDataGraph1"><a href="#" onclick="showData('productivityChart','','serviceManagerProductivity');">
	<span class="glyphicon glyphicon-list-alt" aria-hidden="true" style="margin-right:4px;"></span>View Data</a></li>
	<li id="threelinerDataGraph"><a href="#" onclick="showProductivityStackedBar('productivityChart', 'serviceManagerProductivity', '', 'All', 'All', '#09A1A4' );">
	<span class="glyphicon glyphicon-equalizer" aria-hidden="true" style="margin-right:4px;"></span>View Bar Graph</a></li>
 	
	</div>
	<!-- serviceSpeciality -->
	<div class="serviceSpecialityProductivityPanel">
	<li id="threelinerDataGraph1"><a href="#" onclick="showData('productivityChart','','serviceDOCProductivity');">
	<span class="glyphicon glyphicon-list-alt" aria-hidden="true" style="margin-right:4px;"></span>View Data</a></li>
	<li id="threelinerDataGraph"><a href="#" onclick="showProductivityStackedBar('productivityChart', 'serviceDOCProductivity', '', 'All', 'All', '#09A1A4' );">
	<span class="glyphicon glyphicon-equalizer" aria-hidden="true" style="margin-right:4px;"></span>View Bar Graph</a></li>
 	
	</div>
	
	<div class="locationSpecialityProductivityPanel">
	<li id="threelinerDataGraph1"><a href="#" onclick="showData('productivityChart','','locationForSpecialityProductivity');">
	<span class="glyphicon glyphicon-list-alt" aria-hidden="true" style="margin-right:4px;"></span>View Data</a></li>
	<li id="threelinerDataGraph"><a href="#" onclick="showProductivityStackedBar('productivityChart', 'locationForSpecialityProductivity', '', 'All', 'All', '#09A1A4' );">
	<span class="glyphicon glyphicon-equalizer" aria-hidden="true" style="margin-right:4px;"></span>View Bar Graph</a></li>
 	
	</div>
	
	<div class="specialityProductivityPanel">
	<li id="threelinerDataGraph1"><a href="#" onclick="showData('productivityChart','','specialitysProductivity');">
	<span class="glyphicon glyphicon-list-alt" aria-hidden="true" style="margin-right:4px;"></span>View Data</a></li>
	<li id="threelinerDataGraph"><a href="#" onclick="showProductivityStackedBar('productivityChart', 'specialitysProductivity', '', 'All', 'All', '#09A1A4' );">
	<span class="glyphicon glyphicon-equalizer" aria-hidden="true" style="margin-right:4px;"></span>View Bar Graph</a></li>
 	
	</div>
	
	<div class="specialitytoDocProductivityPanel">
	<li id="threelinerDataGraph1"><a href="#" onclick="showData('productivityChart','','specialitysToDocProductivity');">
	<span class="glyphicon glyphicon-list-alt" aria-hidden="true" style="margin-right:4px;"></span>View Data</a></li>
	<li id="threelinerDataGraph"><a href="#" onclick="showProductivityStackedBar('productivityChart', 'specialitysToDocProductivity', '', 'All', 'All', '#09A1A4' );">
	<span class="glyphicon glyphicon-equalizer" aria-hidden="true" style="margin-right:4px;"></span>View Bar Graph</a></li>
 	
	</div>
	
	<!-- Corporate -->
	<div class="corporateProductivityPanel">
	<li id="threelinerDataGraph1"><a href="#" onclick="showData('productivityChart','','corporateProductivity');">
	<span class="glyphicon glyphicon-list-alt" aria-hidden="true" style="margin-right:4px;"></span>View Data</a></li>
	<li id="threelinerDataGraph"><a href="#" onclick="showProductivityStackedBar('productivityChart', 'corporateProductivity', '', 'All', 'All', '#09A1A4' );">
	<span class="glyphicon glyphicon-equalizer" aria-hidden="true" style="margin-right:4px;"></span>View Bar Graph</a></li>
 	
	</div>
	<div class="corporateServiceProductivityPanel">
	<li id="threelinerDataGraph1"><a href="#" onclick="showData('productivityChart','','corporateServiceProductivity');">
	<span class="glyphicon glyphicon-list-alt" aria-hidden="true" style="margin-right:4px;"></span>View Data</a></li>
	<li id="threelinerDataGraph"><a href="#" onclick="showProductivityStackedBar('productivityChart', 'corporateServiceProductivity', '', 'All', 'All', '#09A1A4' );">
	<span class="glyphicon glyphicon-equalizer" aria-hidden="true" style="margin-right:4px;"></span>View Bar Graph</a></li>
 	
	</div>
	
	<div class="corporateLocationProductivityPanel">
	<li id="threelinerDataGraph1"><a href="#" onclick="showData('productivityChart','','corporateLocationProductivity');">
	<span class="glyphicon glyphicon-list-alt" aria-hidden="true" style="margin-right:4px;"></span>View Data</a></li>
	<li id="threelinerDataGraph"><a href="#" onclick="showProductivityStackedBar('productivityChart', 'corporateLocationProductivity', '', 'All', 'All', '#09A1A4' );">
	<span class="glyphicon glyphicon-equalizer" aria-hidden="true" style="margin-right:4px;"></span>View Bar Graph</a></li>
 	
	</div>
	<div class="corporateServiceSpecialityProductivityPanel">
	<li id="threelinerDataGraph1"><a href="#" onclick="showData('productivityChart','','corporateServiceSpecialityProductivity');">
	<span class="glyphicon glyphicon-list-alt" aria-hidden="true" style="margin-right:4px;"></span>View Data</a></li>
	<li id="threelinerDataGraph"><a href="#" onclick="showProductivityStackedBar('productivityChart', 'corporateServiceSpecialityProductivity', '', 'All', 'All', '#09A1A4' );">
	<span class="glyphicon glyphicon-equalizer" aria-hidden="true" style="margin-right:4px;"></span>View Bar Graph</a></li>
 	
	</div>
	
	<div class="corporateServiceDoctorProductivityPanel">
	<li id="threelinerDataGraph1"><a href="#" onclick="showData('productivityChart','','corporateSpecialityDoctorProductivity');">
	<span class="glyphicon glyphicon-list-alt" aria-hidden="true" style="margin-right:4px;"></span>View Data</a></li>
	<li id="threelinerDataGraph"><a href="#" onclick="showProductivityStackedBar('productivityChart', 'corporateSpecialityDoctorProductivity', '', 'All', 'All', '#09A1A4' );">
	<span class="glyphicon glyphicon-equalizer" aria-hidden="true" style="margin-right:4px;"></span>View Bar Graph</a></li>
 	
	</div>
  
	 </ul>
	</div>
	<div class="clear"></div>
	</div>
	</li>
</ul>
 
<div class="clear"></div>
   
<div id="productivityChart" style=" width: 99%; height: 210px;"></div>
   
 
<div class="allServiceProductivityPanel">
<a href="#"><span class="label label-default" style="background-color: #09A1A4;" id="statusAll" onclick="showProductivityStackedBar('productivityChart', 'serviceProductivity', '', 'All', 'All', '#09A1A4' );">All</span></a>
<a href="#"><span class="label label-danger" style="background-color:#5cb85c;" id="statusApp" onclick="showProductivityStackedBar('productivityChart','serviceProductivity','','On Time','On Time','#5cb85c');">On Time</span></a>
<a href="#"><span class="label label-success" style="background-color:#F04E2F;"  id="statusSch" onclick="showProductivityStackedBar('productivityChart','serviceProductivity','','Off Time','Off Time','#F04E2F');">Off Time</span></a>
</div>

<div class="allLocationProductivityPanelForService">
<a href="#"><span class="label label-default" style="background-color: #09A1A4;" id="statusAll" onclick="showProductivityStackedBar('productivityChart', 'locationForServiceProductivity', '', 'All', 'All', '#09A1A4' );">All</span></a>
<a href="#"><span class="label label-danger" style="background-color:#5cb85c;" id="statusApp" onclick="showProductivityStackedBar('productivityChart','locationForServiceProductivity','','On Time','On Time','#5cb85c');">On Time</span></a>
<a href="#"><span class="label label-success" style="background-color:#F04E2F;"  id="statusSch" onclick="showProductivityStackedBar('productivityChart','locationForServiceProductivity','','Off Time','Off Time','#F04E2F');">Off Time</span></a>
</div>

<div class="allServiceManagerProductivityPanelForService">
<a href="#"><span class="label label-default" style="background-color: #09A1A4;" id="statusAll" onclick="showProductivityStackedBar('productivityChart', 'serviceManagerForServiceProductivity', '', 'All', 'All', '#09A1A4' );">All</span></a>
<a href="#"><span class="label label-danger" style="background-color:#5cb85c;" id="statusApp" onclick="showProductivityStackedBar('productivityChart','serviceManagerForServiceProductivity','','On Time','On Time','#5cb85c');">On Time</span></a>
<a href="#"><span class="label label-success" style="background-color:#F04E2F;"  id="statusSch" onclick="showProductivityStackedBar('productivityChart','serviceManagerForServiceProductivity','','Off Time','Off Time','#F04E2F');">Off Time</span></a>
</div>







<div class="LocationProductivityPanel">
<a href="#"><span class="label label-default" style="background-color: #09A1A4;" id="statusAll" onclick="showProductivityStackedBar('productivityChart', 'locationProductivity', '', 'All', 'All', '#09A1A4' );">All</span></a>
<a href="#"><span class="label label-danger" style="background-color:#5cb85c;" id="statusApp" onclick="showProductivityStackedBar('productivityChart','locationProductivity','','On Time','On Time','#5cb85c');">On Time</span></a>
<a href="#"><span class="label label-success" style="background-color:#F04E2F;"  id="statusSch" onclick="showProductivityStackedBar('productivityChart','locationProductivity','','Off Time','Off Time','#F04E2F');">Off Time</span></a>
 </div>
 
 
<div class="ServiceProductivityPanelForLocation">
<a href="#"><span class="label label-default" style="background-color: #09A1A4;" id="statusAll" onclick="showProductivityStackedBar('productivityChart', 'serviceForLocationProductivity', '', 'All', 'All', '#09A1A4' );">All</span></a>
<a href="#"><span class="label label-danger" style="background-color:#5cb85c;" id="statusApp" onclick="showProductivityStackedBar('productivityChart','serviceForLocationProductivity','','On Time','On Time','#5cb85c');">On Time</span></a>
<a href="#"><span class="label label-success" style="background-color:#F04E2F;"  id="statusSch" onclick="showProductivityStackedBar('productivityChart','serviceForLocationProductivity','','Off Time','Off Time','#F04E2F');">Off Time</span></a>
 </div>






<div class="accountManagerProductivityPanel">
<a href="#"><span class="label label-default" style="background-color: #09A1A4;" id="statusAll" onclick="showProductivityStackedBar('productivityChart', 'accountManagerProductivity', '', 'All', 'All', '#09A1A4' );">All</span></a>
<a href="#"><span class="label label-danger" style="background-color:#5cb85c;" id="statusApp" onclick="showProductivityStackedBar('productivityChart','accountManagerProductivity','','On Time','On Time','#5cb85c');">On Time</span></a>
<a href="#"><span class="label label-success" style="background-color:#F04E2F;"  id="statusSch" onclick="showProductivityStackedBar('productivityChart','accountManagerProductivity','','Off Time','Off Time','#F04E2F');">Off Time</span></a>
</div>

<div class="corporateProductivityPanelForAcntMng">
<a href="#"><span class="label label-default" style="background-color: #09A1A4;" id="statusAll" onclick="showProductivityStackedBar('productivityChart', 'corporateForAcntMngProductivity', '', 'All', 'All', '#09A1A4' );">All</span></a>
<a href="#"><span class="label label-danger" style="background-color:#5cb85c;" id="statusApp" onclick="showProductivityStackedBar('productivityChart','corporateForAcntMngProductivity','','On Time','On Time','#5cb85c');">On Time</span></a>
<a href="#"><span class="label label-success" style="background-color:#F04E2F;"  id="statusSch" onclick="showProductivityStackedBar('productivityChart','corporateForAcntMngProductivity','','Off Time','Off Time','#F04E2F');">Off Time</span></a>
</div>

<div class="serviceProductivityPanelForAcntMng">
<a href="#"><span class="label label-default" style="background-color: #09A1A4;" id="statusAll" onclick="showProductivityStackedBar('productivityChart', 'serviceForAcntMngProductivity', '', 'All', 'All', '#09A1A4' );">All</span></a>
<a href="#"><span class="label label-danger" style="background-color:#5cb85c;" id="statusApp" onclick="showProductivityStackedBar('productivityChart','serviceForAcntMngProductivity','','On Time','On Time','#5cb85c');">On Time</span></a>
<a href="#"><span class="label label-success" style="background-color:#F04E2F;"  id="statusSch" onclick="showProductivityStackedBar('productivityChart','serviceForAcntMngProductivity','','Off Time','Off Time','#F04E2F');">Off Time</span></a>
</div>
  
<div class="locationProductivityPanelForAcntMng">
<a href="#"><span class="label label-default" style="background-color: #09A1A4;" id="statusAll" onclick="showProductivityStackedBar('productivityChart', 'locationForAcntMngProductivity', '', 'All', 'All', '#09A1A4' );">All</span></a>
<a href="#"><span class="label label-danger" style="background-color:#5cb85c;" id="statusApp" onclick="showProductivityStackedBar('productivityChart','locationForAcntMngProductivity','','On Time','On Time','#5cb85c');">On Time</span></a>
<a href="#"><span class="label label-success" style="background-color:#F04E2F;"  id="statusSch" onclick="showProductivityStackedBar('productivityChart','locationForAcntMngProductivity','','Off Time','Off Time','#F04E2F');">Off Time</span></a>
</div>

<div class="serviceManagerProductivityPanelForAcntMng">
<a href="#"><span class="label label-default" style="background-color: #09A1A4;" id="statusAll" onclick="showProductivityStackedBar('productivityChart', 'serviceManagerForAcntMngProductivity', '', 'All', 'All', '#09A1A4' );">All</span></a>
<a href="#"><span class="label label-danger" style="background-color:#5cb85c;" id="statusApp" onclick="showProductivityStackedBar('productivityChart','serviceManagerForAcntMngProductivity','','On Time','On Time','#5cb85c');">On Time</span></a>
<a href="#"><span class="label label-success" style="background-color:#F04E2F;"  id="statusSch" onclick="showProductivityStackedBar('productivityChart','serviceManagerForAcntMngProductivity','','Off Time','Off Time','#F04E2F');">Off Time</span></a>
</div>









<div class="serviceManagerProductivityPanel">
<a href="#"><span class="label label-default" style="background-color: #09A1A4;" id="statusAll" onclick="showProductivityStackedBar('productivityChart', 'serviceManagerProductivity', '', 'All', 'All', '#09A1A4' );">All</span></a>
<a href="#"><span class="label label-danger" style="background-color:#5cb85c;" id="statusApp" onclick="showProductivityStackedBar('productivityChart','serviceManagerProductivity','','On Time','On Time','#5cb85c');">On Time</span></a>
<a href="#"><span class="label label-success" style="background-color:#F04E2F;"  id="statusSch" onclick="showProductivityStackedBar('productivityChart','serviceManagerProductivity','','Off Time','Off Time','#F04E2F');">Off Time</span></a>
</div>
  
  
 <!-- Service to Speciality -->
  <div class="serviceSpecialityProductivityPanel">
	<a href="#"><span class="label label-default" style="background-color: #09A1A4;" id="statusAll" onclick="showProductivityStackedBar('productivityChart', 'serviceDOCProductivity', '', 'All', 'All', '#09A1A4' );">All</span></a>
	<a href="#"><span class="label label-danger" style="background-color:#5cb85c;" id="statusApp" onclick="showProductivityStackedBar('productivityChart','serviceDOCProductivity','','On Time','On Time','#5cb85c');">On Time</span></a>
	<a href="#"><span class="label label-success" style="background-color:#F04E2F;"  id="statusSch" onclick="showProductivityStackedBar('productivityChart','serviceDOCProductivity','','Off Time','Off Time','#F04E2F');">Off Time</span></a>
</div>

<div class="locationSpecialityProductivityPanel">
	<a href="#"><span class="label label-default" style="background-color: #09A1A4;" id="statusAll" onclick="showProductivityStackedBar('productivityChart', 'locationForSpecialityProductivity', '', 'All', 'All', '#09A1A4' );">All</span></a>
	<a href="#"><span class="label label-danger" style="background-color:#5cb85c;" id="statusApp" onclick="showProductivityStackedBar('productivityChart','locationForSpecialityProductivity','','On Time','On Time','#5cb85c');">On Time</span></a>
	<a href="#"><span class="label label-success" style="background-color:#F04E2F;"  id="statusSch" onclick="showProductivityStackedBar('productivityChart','locationForSpecialityProductivity','','Off Time','Off Time','#F04E2F');">Off Time</span></a>
</div>

<div class="specialityProductivityPanel">
	<a href="#"><span class="label label-default" style="background-color: #09A1A4;" id="statusAll" onclick="showProductivityStackedBar('productivityChart', 'specialitysProductivity', '', 'All', 'All', '#09A1A4' );">All</span></a>
	<a href="#"><span class="label label-danger" style="background-color:#5cb85c;" id="statusApp" onclick="showProductivityStackedBar('productivityChart','specialitysProductivity','','On Time','On Time','#5cb85c');">On Time</span></a>
	<a href="#"><span class="label label-success" style="background-color:#F04E2F;"  id="statusSch" onclick="showProductivityStackedBar('productivityChart','specialitysProductivity','','Off Time','Off Time','#F04E2F');">Off Time</span></a>
</div>



<div class="specialitytoDocProductivityPanel">
 	<a href="#"><span class="label label-default" style="background-color: #09A1A4;" id="statusAll" onclick="showProductivityStackedBar('productivityChart', 'specialitysToDocProductivity', '', 'All', 'All', '#09A1A4' );">All</span></a>
	<a href="#"><span class="label label-danger" style="background-color:#5cb85c;" id="statusApp" onclick="showProductivityStackedBar('productivityChart','specialitysToDocProductivity','','On Time','On Time','#5cb85c');">On Time</span></a>
	<a href="#"><span class="label label-success" style="background-color:#F04E2F;"  id="statusSch" onclick="showProductivityStackedBar('productivityChart','specialitysToDocProductivity','','Off Time','Off Time','#F04E2F');">Off Time</span></a>
</div>

<!-- Doc -->
<div class="corporateProductivityPanel">
	<a href="#"><span class="label label-default" style="background-color: #09A1A4;" id="statusAll" onclick="showProductivityStackedBar('productivityChart', 'serviceDOCProductivity', '', 'All', 'All', '#09A1A4' );">All</span></a>
	<a href="#"><span class="label label-danger" style="background-color:#5cb85c;" id="statusApp" onclick="showProductivityStackedBar('productivityChart','serviceDOCProductivity','','On Time','On Time','#5cb85c');">On Time</span></a>
	<a href="#"><span class="label label-success" style="background-color:#F04E2F;"  id="statusSch" onclick="showProductivityStackedBar('productivityChart','serviceDOCProductivity','','Off Time','Off Time','#F04E2F');">Off Time</span></a>
</div>
<div class="corporateServiceProductivityPanel">
	<a href="#"><span class="label label-default" style="background-color: #09A1A4;" id="statusAll" onclick="showProductivityStackedBar('productivityChart', 'serviceDOCProductivity', '', 'All', 'All', '#09A1A4' );">All</span></a>
	<a href="#"><span class="label label-danger" style="background-color:#5cb85c;" id="statusApp" onclick="showProductivityStackedBar('productivityChart','serviceDOCProductivity','','On Time','On Time','#5cb85c');">On Time</span></a>
	<a href="#"><span class="label label-success" style="background-color:#F04E2F;"  id="statusSch" onclick="showProductivityStackedBar('productivityChart','serviceDOCProductivity','','Off Time','Off Time','#F04E2F');">Off Time</span></a>
</div>
<div class="corporateLocationProductivityPanel">
	<a href="#"><span class="label label-default" style="background-color: #09A1A4;" id="statusAll" onclick="showProductivityStackedBar('productivityChart', 'serviceDOCProductivity', '', 'All', 'All', '#09A1A4' );">All</span></a>
	<a href="#"><span class="label label-danger" style="background-color:#5cb85c;" id="statusApp" onclick="showProductivityStackedBar('productivityChart','serviceDOCProductivity','','On Time','On Time','#5cb85c');">On Time</span></a>
	<a href="#"><span class="label label-success" style="background-color:#F04E2F;"  id="statusSch" onclick="showProductivityStackedBar('productivityChart','serviceDOCProductivity','','Off Time','Off Time','#F04E2F');">Off Time</span></a>
</div>
<div class="corporateServiceSpecialityProductivityPanel">
	<a href="#"><span class="label label-default" style="background-color: #09A1A4;" id="statusAll" onclick="showProductivityStackedBar('productivityChart', 'serviceDOCProductivity', '', 'All', 'All', '#09A1A4' );">All</span></a>
	<a href="#"><span class="label label-danger" style="background-color:#5cb85c;" id="statusApp" onclick="showProductivityStackedBar('productivityChart','serviceDOCProductivity','','On Time','On Time','#5cb85c');">On Time</span></a>
	<a href="#"><span class="label label-success" style="background-color:#F04E2F;"  id="statusSch" onclick="showProductivityStackedBar('productivityChart','serviceDOCProductivity','','Off Time','Off Time','#F04E2F');">Off Time</span></a>
</div>
  
  <div class="corporateServiceDoctorProductivityPanel">
	<a href="#"><span class="label label-default" style="background-color: #09A1A4;" id="statusAll" onclick="showProductivityStackedBar('productivityChart', 'serviceDOCProductivity', '', 'All', 'All', '#09A1A4' );">All</span></a>
	<a href="#"><span class="label label-danger" style="background-color:#5cb85c;" id="statusApp" onclick="showProductivityStackedBar('productivityChart','serviceDOCProductivity','','On Time','On Time','#5cb85c');">On Time</span></a>
	<a href="#"><span class="label label-success" style="background-color:#F04E2F;"  id="statusSch" onclick="showProductivityStackedBar('productivityChart','serviceDOCProductivity','','Off Time','Off Time','#F04E2F');">Off Time</span></a>
</div>
  </div>
  </div>
</div>
   <!-- Productivity Counter End  -->
   
      </div>
      

 </body>
</html>