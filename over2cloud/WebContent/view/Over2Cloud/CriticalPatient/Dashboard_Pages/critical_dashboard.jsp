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
 <script type="text/javascript" src="<s:url value="/js/dashboard/criticalPatient/dashboardbar.js"/>"></script>
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
<title>Critical Patient Dashboard</title>
<script type="text/javascript">

$(document).ready(function(){
	 $('#button1').jqxSwitchButton({ height: 20, width: 81, checked: false });
	    $('#button1').bind('checked', function (event) { 
	   	showTimeDiv(false);
	   	
	   });
	   $('#button1').bind('unchecked', function (event) { 
	   	showTimeDiv(true);
	   	
	   });
	   
	   $('#button2').jqxSwitchButton({ height: 20, width: 81, checked: false });
	    $('#button2').bind('checked', function (event) { 
	    	showTimeDivForReport(false);
	   	
	   });
	   $('#button2').bind('unchecked', function (event) { 
		   showTimeDivForReport(true);
	   	
	   });
	document.getElementById('sdate').value=$("#hfromDate").val();
   document.getElementById('edate').value=$("#htoDate").val();
   document.getElementById('fromDateSearch').value=$("#hfromDate").val();
   document.getElementById('toDateSearch').value=$("#htoDate").val();
    
   
});
chartTab();
function chartTab()
{
	  
	showStatusStackedBar('statusChart', 'specialty', '', 'All', 'All', '#09A1A4');
	showProductivityStackedBar('productivityChart', 'specialtyProductivity', '', 'All', 'All', '#09A1A4');

}

</script>


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
            title="CRC Data" 
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
  <center>
<sj:dialog 
            id="criticalReportDialog" 
            autoOpen="false"  
            closeOnEscape="true" 
            modal="true" 
            title="CRC Report" 
            width="1200" 
            height="450" 
            showEffect="slide" 
            hideEffect="explode" >
             
             <span style="float:left;margin-left: 20px;margin-right: 8px;font-size: 15px;">Patient Type:</span>
            <div style="float:left;"> 
            <s:select 
		   	id="serviceID"
		   	name="service"
		    list="#{'All':'All','IPD':'IPD','OPD':'OPD'}"
		    cssClass="select"
		     cssStyle="width:100%;height:22px;  background: #EDECEC;"
		    theme="simple"
		   onchange="crcReport();"
		    >
		     </s:select>
		     </div>
		     
		     
			<div class="statusTime" style="float: left;margin-left: 180px;display: block;"> 
		    <span style="float:left;margin-left: 10px;margin-bottom: 20px; font-size: 15px;">From:<sj:datepicker id="fromDateSearch"     cssClass="button" cssStyle="width: 70px;border: 1px solid #e2e9ef;border-top: 1px solid #cbd3e2;padding: 1px;font-size: 12px;outline: medium none;height: 18px;text-align: center;" readonly="true"  size="20" changeMonth="true" changeYear="true"  yearRange="2013:2050" showOn="focus" displayFormat="dd-mm-yy" Placeholder="From Date" onchange="crcReport();"/></span>
		   <span style="float:left;margin-left: 10px;margin-bottom: 20px; font-size: 15px;">To:<sj:datepicker id="toDateSearch"    cssClass="button" cssStyle="width: 70px;border: 1px solid #e2e9ef;border-top: 1px solid #cbd3e2;padding: 1px;font-size: 12px;outline: medium none;height: 18px;text-align: center;" readonly="true"  size="20" changeMonth="true" changeYear="true"  yearRange="2013:2050" showOn="focus" displayFormat="dd-mm-yy" Placeholder="To Date" onchange="crcReport();"/></span>
		    </div>
		    
		    
		    <div style="float:left;"><span style="float:left;margin-left: 10px;margin-bottom: 20px; font-size: 15px;">Time Wise:</span>
			<div style="float: left;" id="button2"></div>
			</div>
 			<div class="statusTime1" style="float: left;margin-left: 20px;display: none;"> 
           <span style="float:left;margin-left: 10px;margin-bottom: 20px; font-size: 15px;">From: <sj:datepicker id="fTime" placeholder="From Time" cssStyle="width: 70px;border: 1px solid #e2e9ef;border-top: 1px solid #cbd3e2;padding: 1px;font-size: 12px;outline: medium none;height: 18px;text-align: center;"   cssClass="button" timepickerOnly="true" timepicker="true" timepickerAmPm="false" size="20"   readonly="true"   showOn="focus" onchange="crcReport();" /></span>
	        <span style="float:left;margin-left: 10px;margin-bottom: 20px; font-size: 15px;">To: <sj:datepicker id="tTime" placeholder="To Time" cssStyle="width: 70px;border: 1px solid #e2e9ef;border-top: 1px solid #cbd3e2;padding: 1px;font-size: 12px;outline: medium none;height: 18px;text-align: center;"   cssClass="button" timepickerOnly="true" timepicker="true" timepickerAmPm="false"  size="20"   readonly="true"   showOn="focus" onchange="crcReport();"/></span>
			</div>
            
            
             
            <div id="criticalReport"></div>
</sj:dialog>
</center>
 <div style="margin: 4px 4px 0px 325px;">
 <button id="floorStatusbttn" type="button" style="float:right" class="btn btn-default btn-sm" onclick="searchCriticalData();">
			  <span class="	glyphicon glyphicon-search" aria-hidden="true"></span>Search
			</button>
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
 
 
 
 
  <div style="overflow: auto;" id="dashDataPart">
         
            
<!--Ticket Status Counter Dashboard Start -->
  <div class="contentdiv-smallNewDash" style="overflow: hidden;width: 99%;height: 300px;" id="service">
		<div id="counterChart1">
		
 <div style="  float: right;    margin-right: 45px;">
			 
			 
			<button id="spec1" type="button" class="btn btn-default btn-sm" onclick="showStatusStackedBar('statusChart', 'specialty', '', 'All', 'All', '#09A1A4');">
			  <span   aria-hidden="true"></span> Specialty Wise
			</button>
			<button  id="loc1" type="button" class="btn btn-default btn-sm" onclick="showStatusStackedBar('statusChart', 'location', '', 'All', 'All', '#09A1A4');">
			  <span   aria-hidden="true"></span> Location Wise
			</button>
			<button  id="test1" type="button" class="btn btn-default btn-sm" onclick="showStatusStackedBar('statusChart', 'testType', '', 'All', 'All', '#09A1A4');">
			  <span   aria-hidden="true"></span> Test Type Wise
			</button>
			 
  
 </div>
	 <div  class="backbttnDiv1" style="  float: left;">
			 
			 
		 	<button  id="backbttn1"  style="float:left;display:none"  type="button" class="btn btn-default btn-sm" >
			  <span class="glyphicon glyphicon-arrow-left" aria-hidden="true"></span> Back
			</button>
			 
  
 </div>	
 <span  id="x1" style="float:left;margin-top: 3px;margin-right:3px"> Patient Type:</span><div style="float:left;">   <s:select  
				                                id="statusFor"
				                              name="statusFor"
				                              list="#{'All':'All','IPD':'IPD','OPD':'OPD'}"
				                                cssClass="select"
					                          cssStyle="width:100%;height:22px;  background: #EDECEC;"
					                          onchange=" getDataForStatusCounter();"
				                              >
				                  </s:select> </div>
 
 <div class="statusSpecialtyPanel">
<div class="headdingtest" style="margin-right: 50px;">Ticket Counter Status Specialty Wise</div>
 </div>
 
  <div class="statusDoctorPanel">
<div class="headdingtest" style="margin-right: 50px;">Ticket Counter Status Doctor Wise</div>
 </div>
 
 <div class="statusTestTypePanel">
<div class="headdingtest" style="margin-right: 50px;">Ticket Counter Status Test Type Wise</div>
 </div>
 
  <div class="statusTestNamePanel">
<div class="headdingtest" style="margin-right: 50px;">Ticket Counter Status Test Name Wise</div>
 </div>
 <div class="statusLocationPanel">
<div class="headdingtest" style="margin-right: 50px;">Ticket Counter Status Location Wise</div>
 </div>
 
  <div class="statusNursingUnitPanel">
<div class="headdingtest" style="margin-right: 50px;">Ticket Counter Status Nursing Unit Wise</div>
 </div>
   
 
 <ul class="nav_links">
	<li style="top: -6px;"><a href="#">
	<span class="threeLiner" style="     margin-right: 10px" ></span>
		 </a> 
			
			
	<div class="dropdown profile_dropdown">
	<div class="arrow_dropdown">&nbsp;</div>
	<div class="one_column" >
	<ul> 
	<div class="statusSpecialtyPanel">
		<li id="threelinerDataGraph1"><a href="#" onclick="showData('statusChart','','specialty');">
		<span class="glyphicon glyphicon-list-alt" aria-hidden="true" style="margin-right:4px;"></span>View Data</a></li>
		<li id="threelinerDataGraph"><a href="#" onclick="showStatusStackedBar('statusChart', 'specialty', '', 'All', 'All', '#09A1A4' );">
		<span class="glyphicon glyphicon-equalizer" aria-hidden="true" style="margin-right:4px;"></span>View Bar Graph</a></li>
		<li id="threelinerDataGraph"><a href="#" onclick="crcReport();">
		<span class="glyphicon glyphicon-list-alt" aria-hidden="true" style="margin-right:4px;"></span>CRC Report</a></li>
	
	 
	</div>
	<div class="statusDoctorPanel">
		<li id="threelinerDataGraph1"><a href="#" onclick="showData('statusChart','','doctor');">
		<span class="glyphicon glyphicon-list-alt" aria-hidden="true" style="margin-right:4px;"></span>View Data</a></li>
		<li id="threelinerDataGraph"><a href="#" onclick="showStatusStackedBar('statusChart', 'doctor', '', 'All', 'All', '#09A1A4',specialty1 );">
		<span class="glyphicon glyphicon-equalizer" aria-hidden="true" style="margin-right:4px;"></span>View Bar Graph</a></li>
		<li id="threelinerDataGraph"><a href="#" onclick="crcReport();">
		<span class="glyphicon glyphicon-list-alt" aria-hidden="true" style="margin-right:4px;"></span>CRC Report</a></li>
	
	</div>
	<div class="statusTestTypePanel">
		<li id="threelinerDataGraph1"><a href="#" onclick="showData('statusChart','','testType');">
		<span class="glyphicon glyphicon-list-alt" aria-hidden="true" style="margin-right:4px;"></span>View Data</a></li>
		<li id="threelinerDataGraph"><a href="#" onclick="showStatusStackedBar('statusChart', 'testType', '', 'All', 'All', '#09A1A4' );">
		<span class="glyphicon glyphicon-equalizer" aria-hidden="true" style="margin-right:4px;"></span>View Bar Graph</a></li>
		<li id="threelinerDataGraph"><a href="#" onclick="crcReport();">
		<span class="glyphicon glyphicon-list-alt" aria-hidden="true" style="margin-right:4px;"></span>CRC Report</a></li>
	
	</div>
	<div class="statusTestNamePanel">
		<li id="threelinerDataGraph1"><a href="#" onclick="showData('statusChart','','testName');">
		<span class="glyphicon glyphicon-list-alt" aria-hidden="true" style="margin-right:4px;"></span>View Data</a></li>
		<li id="threelinerDataGraph"><a href="#" onclick="showStatusStackedBar('statusChart', 'testName', '', 'All', 'All', '#09A1A4',testType1 );">
		<span class="glyphicon glyphicon-equalizer" aria-hidden="true" style="margin-right:4px;"></span>View Bar Graph</a></li>
	<li id="threelinerDataGraph"><a href="#" onclick="crcReport();">
		<span class="glyphicon glyphicon-list-alt" aria-hidden="true" style="margin-right:4px;"></span>CRC Report</a></li>
	
	</div>
	<div class="statusLocationPanel">
		<li id="threelinerDataGraph1"><a href="#" onclick="showData('statusChart','','location');">
		<span class="glyphicon glyphicon-list-alt" aria-hidden="true" style="margin-right:4px;"></span>View Data</a></li>
		<li id="threelinerDataGraph"><a href="#" onclick="showStatusStackedBar('statusChart', 'location', '', 'All', 'All', '#09A1A4' );">
		<span class="glyphicon glyphicon-equalizer" aria-hidden="true" style="margin-right:4px;"></span>View Bar Graph</a></li>
	<li id="threelinerDataGraph"><a href="#" onclick="crcReport();">
		<span class="glyphicon glyphicon-list-alt" aria-hidden="true" style="margin-right:4px;"></span>CRC Report</a></li>
	
	</div>
	<div class="statusNursingUnitPanel">
		<li id="threelinerDataGraph1"><a href="#" onclick="showData('statusChart','','nursingUnit');">
		<span class="glyphicon glyphicon-list-alt" aria-hidden="true" style="margin-right:4px;"></span>View Data</a></li>
		<li id="threelinerDataGraph"><a href="#" onclick="showStatusStackedBar('statusChart', 'nursingUnit', '', 'All', 'All', '#09A1A4',location1 );">
		<span class="glyphicon glyphicon-equalizer" aria-hidden="true" style="margin-right:4px;"></span>View Bar Graph</a></li>
	<li id="threelinerDataGraph"><a href="#" onclick="crcReport();">
		<span class="glyphicon glyphicon-list-alt" aria-hidden="true" style="margin-right:4px;"></span>CRC Report</a></li>
	
	</div>
	 
  
		 </ul>
	</div>
	<div class="clear"></div>
	</div>
	</li>
</ul>
 
<div class="clear"></div>
   
<div id="statusChart" style=" width: 99%; height: 210px;"></div>
</div>
 
<div class="statusSpecialtyPanel">
<a href="#"><span class="label label-default" style="background-color: #09A1A4;" id="statusAll" onclick="showStatusStackedBar('statusChart', 'specialty', '', 'All', 'All', '#09A1A4' );">All</span></a>
<a href="#"><span class="label label-danger" style="background-color:#F04E2F;" id="statusApp" onclick="showStatusStackedBar('statusChart','specialty','','Open','Open','#F04E2F');">Open</span></a>
<a href="#"><span class="label label-success" style="background-color:#5cb85c;"  id="statusSch" onclick="showStatusStackedBar('statusChart','specialty','','Close','Close','#5cb85c');">Close</span></a>
</div>
   
   
<div class="statusDoctorPanel">
<a href="#"><span class="label label-default" style="background-color: #09A1A4;" id="statusAll" onclick="showStatusStackedBar('statusChart', 'doctor', '', 'All', 'All', '#09A1A4',specialty1 );">All</span></a>
<a href="#"><span class="label label-danger" style="background-color:#F04E2F;" id="statusApp" onclick="showStatusStackedBar('statusChart','doctor','','Open','Open','#F04E2F',specialty1);">Open</span></a>
<a href="#"><span class="label label-success" style="background-color:#5cb85c;"  id="statusSch" onclick="showStatusStackedBar('statusChart','doctor','','Close','Close','#5cb85c',specialty1);">Close</span></a>
</div>

<div class="statusTestTypePanel">
<a href="#"><span class="label label-default" style="background-color: #09A1A4;" id="statusAll" onclick="showStatusStackedBar('statusChart', 'testType', '', 'All', 'All', '#09A1A4' );">All</span></a>
<a href="#"><span class="label label-danger" style="background-color:#F04E2F;" id="statusApp" onclick="showStatusStackedBar('statusChart','testType','','Open','Open','#F04E2F');">Open</span></a>
<a href="#"><span class="label label-success" style="background-color:#5cb85c;"  id="statusSch" onclick="showStatusStackedBar('statusChart','testType','','Close','Close','#5cb85c');">Close</span></a>
</div>
   
   
<div class="statusTestNamePanel">
<a href="#"><span class="label label-default" style="background-color: #09A1A4;" id="statusAll" onclick="showStatusStackedBar('statusChart', 'testName', '', 'All', 'All', '#09A1A4',testType1 );">All</span></a>
<a href="#"><span class="label label-danger" style="background-color:#F04E2F;" id="statusApp" onclick="showStatusStackedBar('statusChart','testName','','Open','Open','#F04E2F',testType1);">Open</span></a>
<a href="#"><span class="label label-success" style="background-color:#5cb85c;"  id="statusSch" onclick="showStatusStackedBar('statusChart','testName','','Close','Close','#5cb85c',testType1);">Close</span></a>
</div>

<div class="statusLocationPanel">
<a href="#"><span class="label label-default" style="background-color: #09A1A4;" id="statusAll" onclick="showStatusStackedBar('statusChart', 'location', '', 'All', 'All', '#09A1A4' );">All</span></a>
<a href="#"><span class="label label-danger" style="background-color:#F04E2F;" id="statusApp" onclick="showStatusStackedBar('statusChart','location','','Open','Open','#F04E2F');">Open</span></a>
<a href="#"><span class="label label-success" style="background-color:#5cb85c;"  id="statusSch" onclick="showStatusStackedBar('statusChart','location','','Close','Close','#5cb85c');">Close</span></a>
</div>
   
   
<div class="statusNursingUnitPanel">
<a href="#"><span class="label label-default" style="background-color: #09A1A4;" id="statusAll" onclick="showStatusStackedBar('statusChart', 'nursingUnit', '', 'All', 'All', '#09A1A4',location1 );">All</span></a>
<a href="#"><span class="label label-danger" style="background-color:#F04E2F;" id="statusApp" onclick="showStatusStackedBar('statusChart','nursingUnit','','Open','Open','#F04E2F',location1);">Open</span></a>
<a href="#"><span class="label label-success" style="background-color:#5cb85c;"  id="statusSch" onclick="showStatusStackedBar('statusChart','nursingUnit','','Close','Close','#5cb85c',location1);">Close</span></a>
</div>
 
  
</div>
<!-- Ticket Status Counter Dashboard  End -->

 
   
   
   
   
   
   
   
         
  <!-- Productivity Counter  -->
   <div class="contentdiv-smallNewDash" style="overflow: hidden;width: 99%;height: 300px;" id="productivity">
		<div id="counterChart">
		<div style="  float: right;margin-right: 45px;">
			 
			 
			<button id="spec2" type="button" class="btn btn-default btn-sm" onclick="showProductivityStackedBar('productivityChart', 'specialtyProductivity', '', 'All', 'All', '#09A1A4');">
			  <span  aria-hidden="true"></span> Specialty Wise
			</button>
			<button  id="loc2" type="button" class="btn btn-default btn-sm" onclick="showProductivityStackedBar('productivityChart', 'locationProductivity', '', 'All', 'All', '#09A1A4');">
			  <span  aria-hidden="true"></span> Location Wise
			</button>
			<button  id="test2" type="button" class="btn btn-default btn-sm" onclick="showProductivityStackedBar('productivityChart', 'testTypeProductivity', '', 'All', 'All', '#09A1A4');">
			  <span  aria-hidden="true"></span> Test Type Wise
			</button>
			 
  
 </div>
	 <div  class="backbttnDiv2" style="  float: left;">
			 
			 
		 	<button  id="backbttn2"  style="float:left;display:none"  type="button" class="btn btn-default btn-sm" >
			  <span class="glyphicon glyphicon-arrow-left" aria-hidden="true"></span> Back
			</button>
			 
  
 </div>	
  <span  id="x2" style="float:left;margin-top: 3px;margin-right:3px"> Patient Type:</span><div style="float:left;">   
  <s:select  
				                                id="statusForProductivity"
				                              name="statusForProductivity"
				                              list="#{'All':'All','IPD':'IPD','OPD':'OPD'}"
				                                cssClass="select"
					                          cssStyle="width:100%;height:22px;  background: #EDECEC;"
					                          onchange="getDataForProductivity();"
				                              >
				                  </s:select> </div>
<span  id="x3" style="float:left;margin-top: 3px;margin-right:3px;margin-left:6px"> Productivity For:</span><div style="float:left;">   
<s:select  
				                                id="productivityFor"
				                              name="productivityFor"
				                              list="#{'Department':'Department','OCC':'OCC','Lab':'Lab'}"
				                                cssClass="select"
					                          cssStyle="width:100%;height:22px;  background: #EDECEC;"
					                          onchange="getDataForProductivity();"
				                              >
				                  </s:select> </div>	
<span  id="x3" style="float:left;margin-top: 3px;margin-right:3px;margin-left:6px"> On Time Limit:</span><div style="float:left;">    
<sj:datepicker placeholder="On Time Limit" cssStyle="margin:0px 0px 2px 0px;width:100px;height: 20px;"  onchange="getDataForProductivity();"  cssClass="button" timepickerOnly="true" timepicker="true" timepickerAmPm="false" id="productivityLimit" name="productivityLimit" size="20"   readonly="false"   showOn="focus"/>

</div>	
			                  			                
<div class="specialtyProductivityPanel">
<div class="headdingtest" style="margin-right: 450px;">Productivity Counter Specialty Wise </div>
</div>
   
 <div class="doctorProductivityPanel"  style="margin-right: 450px;">
<div class="headdingtest">Productivity Counter Doctor Wise</div>
 </div>
   
   			                  			                
<div class="locationProductivityPanel">
<div class="headdingtest" style="margin-right: 450px;">Productivity Counter Location Wise </div>
</div>
   
 <div class="nursingUnitProductivityPanel"  style="margin-right: 450px;">
<div class="headdingtest">Productivity Counter Nursing Unit Wise</div>
 </div>
   
   			                  			                
<div class="testTypeProductivityPanel">
<div class="headdingtest" style="margin-right: 450px;">Productivity Counter Test Type Wise </div>
</div>
   
 <div class="testNameProductivityPanel"  style="margin-right: 450px;">
<div class="headdingtest">Productivity Counter Test Name Wise</div>
 </div>
   
   
 <ul class="nav_links">
	<li style="top: -6px;"><a href="#">
	<span class="threeLiner" style="     margin-right: 10px" ></span>
		 </a> 
			
			
	<div class="dropdown profile_dropdown">
	<div class="arrow_dropdown">&nbsp;</div>
	<div class="one_column" >
	<ul> 
	<div class="specialtyProductivityPanel">
		<li id="threelinerDataGraph1"><a href="#" onclick="showData('productivityChart','','specialtyProductivity');">
		<span class="glyphicon glyphicon-list-alt" aria-hidden="true" style="margin-right:4px;"></span>View Data</a></li>
		<li id="threelinerDataGraph"><a href="#" onclick="showProductivityStackedBar('productivityChart', 'specialtyProductivity', '', 'All', 'All', '#09A1A4');">
		<span class="glyphicon glyphicon-equalizer" aria-hidden="true" style="margin-right:4px;"></span>View Bar Graph</a></li>
	
	</div>
	<div class="doctorProductivityPanel">
		<li id="threelinerDataGraph1"><a href="#" onclick="showData('productivityChart','','doctorProductivity');">
		<span class="glyphicon glyphicon-list-alt" aria-hidden="true" style="margin-right:4px;"></span>View Data</a></li>
		<li id="threelinerDataGraph"><a href="#" onclick="showProductivityStackedBar('productivityChart', 'doctorProductivity', '', 'All', 'All', '#09A1A4',specialty2);">
		<span class="glyphicon glyphicon-equalizer" aria-hidden="true" style="margin-right:4px;"></span>View Bar Graph</a></li>
	
	</div>
	<div class="testTypeProductivityPanel">
		<li id="threelinerDataGraph1"><a href="#" onclick="showData('productivityChart','','testTypeProductivity');">
		<span class="glyphicon glyphicon-list-alt" aria-hidden="true" style="margin-right:4px;"></span>View Data</a></li>
		<li id="threelinerDataGraph"><a href="#" onclick="showProductivityStackedBar('productivityChart', 'testTypeProductivity', '', 'All', 'All', '#09A1A4');">
		<span class="glyphicon glyphicon-equalizer" aria-hidden="true" style="margin-right:4px;"></span>View Bar Graph</a></li>
	
	</div>
	<div class="testNameProductivityPanel">
		<li id="threelinerDataGraph1"><a href="#" onclick="showData('productivityChart','','testNameProductivity');">
		<span class="glyphicon glyphicon-list-alt" aria-hidden="true" style="margin-right:4px;"></span>View Data</a></li>
		<li id="threelinerDataGraph"><a href="#" onclick="showProductivityStackedBar('productivityChart', 'testNameProductivity', '', 'All', 'All', '#09A1A4',specialty2);">
		<span class="glyphicon glyphicon-equalizer" aria-hidden="true" style="margin-right:4px;"></span>View Bar Graph</a></li>
	
	</div>
	<div class="locationProductivityPanel">
		<li id="threelinerDataGraph1"><a href="#" onclick="showData('productivityChart','','locationProductivity');">
		<span class="glyphicon glyphicon-list-alt" aria-hidden="true" style="margin-right:4px;"></span>View Data</a></li>
		<li id="threelinerDataGraph"><a href="#" onclick="showProductivityStackedBar('productivityChart', 'locationProductivity', '', 'All', 'All', '#09A1A4');">
		<span class="glyphicon glyphicon-equalizer" aria-hidden="true" style="margin-right:4px;"></span>View Bar Graph</a></li>
	
	</div>
	<div class="nursingUnitProductivityPanel">
		<li id="threelinerDataGraph1"><a href="#" onclick="showData('productivityChart','','nursingUnitProductivity');">
		<span class="glyphicon glyphicon-list-alt" aria-hidden="true" style="margin-right:4px;"></span>View Data</a></li>
		<li id="threelinerDataGraph"><a href="#" onclick="showProductivityStackedBar('productivityChart', 'nursingUnitProductivity', '', 'All', 'All', '#09A1A4',specialty2);">
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
</div>
 
<div class="specialtyProductivityPanel">
<a href="#"><span class="label label-default" style="background-color: #09A1A4;" id="statusAll" onclick="showProductivityStackedBar('productivityChart', 'specialtyProductivity', '', 'All', 'All', '#09A1A4' );">All</span></a>
<a href="#"><span class="label label-danger" style="background-color:#5cb85c;" id="statusApp" onclick="showProductivityStackedBar('productivityChart','specialtyProductivity','','On Time','On Time','#5cb85c');">On Time</span></a>
<a href="#"><span class="label label-success" style="background-color:#F04E2F;"  id="statusSch" onclick="showProductivityStackedBar('productivityChart','specialtyProductivity','','Off Time','Off Time','#F04E2F');">Off Time</span></a>
</div>

<div class="doctorProductivityPanel">
<a href="#"><span class="label label-default" style="background-color: #09A1A4;" id="statusAll" onclick="showProductivityStackedBar('productivityChart', 'doctorProductivity', '', 'All', 'All', '#09A1A4',specialty2 );">All</span></a>
<a href="#"><span class="label label-danger" style="background-color:#5cb85c;" id="statusApp" onclick="showProductivityStackedBar('productivityChart','doctorProductivity','','On Time','On Time','#5cb85c',specialty2);">On Time</span></a>
<a href="#"><span class="label label-success" style="background-color:#F04E2F;"  id="statusSch" onclick="showProductivityStackedBar('productivityChart','doctorProductivity','','Off Time','Off Time','#F04E2F',specialty2);">Off Time</span></a>
</div>

<div class="testTypeProductivityPanel">
<a href="#"><span class="label label-default" style="background-color: #09A1A4;" id="statusAll" onclick="showProductivityStackedBar('productivityChart', 'testTypeProductivity', '', 'All', 'All', '#09A1A4' );">All</span></a>
<a href="#"><span class="label label-danger" style="background-color:#5cb85c;" id="statusApp" onclick="showProductivityStackedBar('productivityChart','testTypeProductivity','','On Time','On Time','#5cb85c');">On Time</span></a>
<a href="#"><span class="label label-success" style="background-color:#F04E2F;"  id="statusSch" onclick="showProductivityStackedBar('productivityChart','testTypeProductivity','','Off Time','Off Time','#F04E2F');">Off Time</span></a>
</div>

<div class="testNameProductivityPanel">
<a href="#"><span class="label label-default" style="background-color: #09A1A4;" id="statusAll" onclick="showProductivityStackedBar('productivityChart', 'testNameProductivity', '', 'All', 'All', '#09A1A4',testType2 );">All</span></a>
<a href="#"><span class="label label-danger" style="background-color:#5cb85c;" id="statusApp" onclick="showProductivityStackedBar('productivityChart','testNameProductivity','','On Time','On Time','#5cb85c',testType2);">On Time</span></a>
<a href="#"><span class="label label-success" style="background-color:#F04E2F;"  id="statusSch" onclick="showProductivityStackedBar('productivityChart','testNameProductivity','','Off Time','Off Time','#F04E2F',testType2);">Off Time</span></a>
</div>

<div class="locationProductivityPanel">
<a href="#"><span class="label label-default" style="background-color: #09A1A4;" id="statusAll" onclick="showProductivityStackedBar('productivityChart', 'locationProductivity', '', 'All', 'All', '#09A1A4' );">All</span></a>
<a href="#"><span class="label label-danger" style="background-color:#5cb85c;" id="statusApp" onclick="showProductivityStackedBar('productivityChart','locationProductivity','','On Time','On Time','#5cb85c');">On Time</span></a>
<a href="#"><span class="label label-success" style="background-color:#F04E2F;"  id="statusSch" onclick="showProductivityStackedBar('productivityChart','locationProductivity','','Off Time','Off Time','#F04E2F');">Off Time</span></a>
</div>

<div class="nursingUnitProductivityPanel">
<a href="#"><span class="label label-default" style="background-color: #09A1A4;" id="statusAll" onclick="showProductivityStackedBar('productivityChart', 'nursingUnitProductivity', '', 'All', 'All', '#09A1A4',location2 );">All</span></a>
<a href="#"><span class="label label-danger" style="background-color:#5cb85c;" id="statusApp" onclick="showProductivityStackedBar('productivityChart','nursingUnitProductivity','','On Time','On Time','#5cb85c',location2);">On Time</span></a>
<a href="#"><span class="label label-success" style="background-color:#F04E2F;"  id="statusSch" onclick="showProductivityStackedBar('productivityChart','nursingUnitProductivity','','Off Time','Off Time','#F04E2F',location2);">Off Time</span></a>
</div>


           </div>
      </div>
      </div> 

 </body>
</html>