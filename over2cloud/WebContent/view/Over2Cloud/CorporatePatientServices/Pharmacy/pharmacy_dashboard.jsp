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
 <script type="text/javascript" src="<s:url value="/js/dashboard/pharmacy/dashboardbar.js"/>"></script>
<link  type="text/css" href="amcharts/plugins/export/export.css" rel="stylesheet">
<link rel="stylesheet" href="bootstrap-3.3.4-dist/css/bootstrap.min.css">
<link rel="stylesheet" href="bootstrap-3.3.4-dist/css/bootstrap-theme.min.css">
<link href="js/multiselect/jquery.multiselect.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<s:url value="/js/multiselect/jquery-ui.min.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/multiselect/jquery.multiselect.js"/>"></script>
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
<link rel="stylesheet" href="css/tabs.css" type="text/css" />
<s:url var="chartDataUrl" action="json-chart-data"/>
<title>Critical Patient Dashboard</title>
<script type="text/javascript"> 

$(document).ready(function(){
	 $('#button1').jqxSwitchButton({ height: 20, width: 81, checked: false });
	    $('#button1').bind('checked', function (event) { 
	    	showTimeDivForReport(false);
	   	
	   });
	   $('#button1').bind('unchecked', function (event) { 
		   showTimeDivForReport(true);
	   	
	   });
	   $(".tabs-menu a").click(function(event)
				{
					event.preventDefault();
					$(this).parent().addClass("current");
					$(this).parent().siblings().removeClass("current");
					var tab = $(this).attr("href");
					$(".tab-content").not(tab).css("display", "none");
					$(tab).fadeIn();
				});
	  
	document.getElementById('sdate').value=$("#hfromDate").val();
   document.getElementById('edate').value=$("#htoDate").val();
   
   $("#location_search").multiselect({
 	   show: ["", 200],
 	   hide: ["explode", 1000]
 	});

   $("#nursing_search").multiselect({
 	   show: ["", 200],
 	   hide: ["explode", 1000]
 	});
   
});
 
var currentTab="7";
getDataForAllBoard();
 
function getDataForAllBoard()
{
 	if(currentTab=="1")
	{
 			showStatusStackedBar('1stBlock_1', 'statusBar', '', 'All', 'All', '#09A1A4','' );
 			 
	}
	else if(currentTab=="2")
	{
			showStatusStackedBar('2ndBlock_1', 'productivityBar', '', 'All', 'All', '#09A1A4','');
	}
 	else if(currentTab=="3")
	{
 			showStatusStackedBar('3rdBlock_1', 'locationStatusBar', '', 'All', 'All', '#09A1A4','' );
	}
 	else if(currentTab=="4")
	{
 			showStatusStackedBar('4thBlock_1', 'priorityBar', '', 'All', 'All', '#09A1A4','' );
	}
 	else if(currentTab=="5")
	{
 			showTATStackedBar('5thBlock_1', 'tatBar', '', 'All', 'All', '#09A1A4','' );
	}
 	else if(currentTab=="6")
	{
			showStatusStackedBar('6thBlock_1', 'specialityBar', '', 'All', 'All', '#09A1A4','');
	}
 	else if(currentTab=="7")
	{
			showStatusStackedBar('7thBlock_1', 'statusBarTAT', '', 'All', 'All', '#09A1A4','');
	}
 	else if(currentTab=="8")
	{
			showTATPieChart('8thBlock_1','tatPie');
	}
 	 
}

 
 </script>


<style type="text/css">
.ui-datepicker-month,.ui-datepicker-year{
color: rgb(22, 66, 118);
}
 
 
 ul.nav_links li div.profile_dropdownDash {
    width: 170px;!important;
}
ul.nav_links li div.dropdown {
    top: 20px;
    right: 5px;
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
background: -webkit-linear-gradient(#666, #5D6DAC);
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
            title="Pharmacy Data View" 
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
 <div class="list-icon">
	<div class="head">Dashboard </div>
	<div class="head">
	<img alt="" src="images/forward.png" style="margin-top:10px;margin-right:3px; float: left;">Pharmacy 
	</div>
	<div style="margin: 4px 4px 0px 325px;">
<div id="datePickerStatus" style="float: left;">
From:<sj:datepicker placeholder="From Date" cssStyle="margin:0px 0px 2px 0px;width:88px;height: 20px;"   onchange="getDataForAllBoard();UpdateDropFilters();" cssClass="button"   id="sdate" name="sdate" size="20"   readonly="false"   changeMonth="true" changeYear="true"  yearRange="2013:2050" showOn="focus" displayFormat="dd-mm-yy"/>
To:<sj:datepicker placeholder="To Date" cssStyle="margin:0px 0px 2px 0px;width:88px;height: 20px;"  onchange="getDataForAllBoard();UpdateDropFilters();"  cssClass="button"  id="edate" name="edate"   size="20"   readonly="false"  changeMonth="true" changeYear="true"  yearRange="2013:2050" showOn="focus" displayFormat="dd-mm-yy"/>
</div>
  <div style="float:left;"><span style="float:left;margin-left: 20px;margin-right: 8px;font-size: 15px;">Time Wise:</span>
		<div style="float: left;" id="button1"></div>
</div>
 <div class="statusTime" style="float: left;margin-left: 20px;display: none;">
 From: <sj:datepicker placeholder="From Time" cssStyle="margin:0px 0px 2px 0px;width:85px;height: 20px;"  onchange="getDataForAllBoard();UpdateDropFilters();"  cssClass="button" timepickerOnly="true" timepicker="true" timepickerAmPm="false" id="fromTime" name="fromTime" size="20"   readonly="false"   showOn="focus"/>
 To:<sj:datepicker placeholder="To Time" cssStyle="margin:0px 0px 2px 0px;width:85px;height: 20px;"  onchange="getDataForAllBoard();UpdateDropFilters();"  cssClass="button" timepickerOnly="true" timepicker="true" timepickerAmPm="false" id="toTime" name="toTime" size="20"   readonly="false"   showOn="focus"/>
</div> 
</div>
</div>
 
 
<div class="middle-content">
  
<s:hidden id="hfromDate" value="%{fromDate}"></s:hidden>
<s:hidden id="htoDate" value="%{toDate}"></s:hidden>
<div id="tabs-container" style="margin-top: 5px;">
			<ul class="tabs-menu">
				<!--<li class="current" onclick="showStatusStackedBar('1stBlock_1', 'statusBar', '', 'All', 'All', '#09A1A4','' );"><a href="#tab-1">Overall</a></li>
				-->
				<li class="current" onclick="showStatusStackedBar('7thBlock_1', 'statusBarTAT', '', 'All', 'All', '#09A1A4','' );"><a href="#tab-1">OverAll</a></li>
				<li onclick="showTATPieChart('8thBlock_1','tatPie');"><a href="#tab-8">TAT Wise</a></li>
				<!--
				<li onclick="showStatusStackedBar('2ndBlock_1', 'productivityBar', '', 'All', 'All', '#09A1A4','');"><a href="#tab-2">Escalation Level Wise</a></li>
				-->
				<li onclick="showStatusStackedBar('3rdBlock_1', 'locationStatusBar', '', 'All', 'All', '#09A1A4','' );"><a href="#tab-3">Location Wise</a></li>
				<li onclick="showStatusStackedBar('4thBlock_1', 'priorityBar', '', 'All', 'All', '#09A1A4','' );"><a href="#tab-4">Priority Wise</a></li><!--
				<li onclick="showTATStackedBar('5thBlock_1', 'tatBar', '', 'All', 'All', '#09A1A4','' );"><a href="#tab-5">TAT Wise</a></li>
				--><li onclick="showStatusStackedBar('6thBlock_1', 'specialityBar', '', 'All', 'All', '#09A1A4','' );"><a href="#tab-6">Speciality Wise</a></li>
  		   			</ul>
<div id="nursingDiv" style="float: right;margin-right:20px"> </div>	
<div id="locationDiv" style="float: right;"></div>
<div class="tab">
<!--<div id="tab-1" class="tab-content">
<ul class="nav_links">
	<li style="top: 12px;margin-right:12px;">
	<a href="#"><span class="threeLiner" style="  margin-right: -13px" ></span></a> 
 	<div class="dropdown profile_dropdownDash"  >
	<div class="arrow_dropdown">&nbsp;</div>
	<div class="one_column" >
	<ul> 
	<div class="overAllAndRecomDiv">
		<li id="threelinerDataGraph1"><a href="#" onclick="showData('1stBlock_1', 'statusBar');">
		<span class="glyphicon glyphicon-list-alt" aria-hidden="true" style="margin-right:4px;"></span>View Data</a></li>
		<li id="threelinerDataGraph"><a href="#" onclick="showStatusStackedBar('1stBlock_1', 'statusBar', '', 'All', 'All', '#09A1A4','' );">
		<span class="glyphicon glyphicon-equalizer" aria-hidden="true" style="margin-right:4px;"></span>View Bar Graph</a></li>
		<li id="threelinerDataGraph"><a href="#" onclick="showStatusPieChart('1stBlock_1','statusPie');">
		<span class="glyphicon glyphicon-list-alt" aria-hidden="true" style="margin-right:4px;"></span>View Pie Chart</a></li>
 	</div>
 	</ul>
  	</div>
	<div class="clear"></div>
	</div>
	</li>
</ul>
<div class="contentdiv-small" style= "width:99%;height:380px;margin-top: -15px;margin-left: 5px;">
<div style="overflow:auto;width:98%;height:350px;" id="1stBlock_1"></div>
</div>
</div>





--><div id="tab-2" class="tab-content">
<ul class="nav_links">
	<li style="top: 12px;margin-right:12px;">
	<a href="#"><span class="threeLiner" style="  margin-right: 2px" ></span></a> 
 	<div class="dropdown profile_dropdownDash" >
	<div class="arrow_dropdown">&nbsp;</div>
	<div class="one_column" >
	<ul> 
	<div class="productivityCounter">
		<li id="threelinerDataGraph1"><a href="#" onclick="showData('2ndBlock_1', 'productivityBar');">
		<span class="glyphicon glyphicon-list-alt" aria-hidden="true" style="margin-right:4px;"></span>View Data</a></li>
		<li id="threelinerDataGraph"><a href="#" onclick="showStatusStackedBar('2ndBlock_1', 'productivityBar', '', 'All', 'All', '#09A1A4','');">
		<span class="glyphicon glyphicon-equalizer" aria-hidden="true" style="margin-right:4px;"></span>View Bar Graph</a></li>
		<li id="threelinerDataGraph"><a href="#" onclick="showStatusPieChart('2ndBlock_1','productivityPie');">
		<span class="glyphicon glyphicon-adjust" aria-hidden="true" style="margin-right:4px;"></span>View Pie Chart</a></li>
		 
	</div>
 	</ul>
  	</div>
	<div class="clear"></div>
	</div>
	</li>
</ul>
<div class="contentdiv-small" style= "width:99%;height:380px;margin-top: -15px;margin-left: 5px;">
<div style="overflow:auto;width:98%;height:320px;" id="2ndBlock_1"></div>
</div>
</div>	



<div id="tab-3" class="tab-content">
<ul class="nav_links">
	<li style="top: 12px;margin-right:12px;">
	<a href="#"><span class="threeLiner" style="  margin-right: 2px" ></span></a> 
 	<div class="dropdown profile_dropdownDash"  >
	<div class="arrow_dropdown">&nbsp;</div>
	<div class="one_column" >
	<ul> 
	<div class="locationWiseStatusCounter">
		<li id="threelinerDataGraph1"><a href="#" onclick="showData('3rdBlock_1', 'locationStatusBar');">
		<span class="glyphicon glyphicon-list-alt" aria-hidden="true" style="margin-right:4px;"></span>View Data</a></li>
		<li id="threelinerDataGraph"><a href="#" onclick="showStatusStackedBar('3rdBlock_1', 'locationStatusBar', '', 'All', 'All', '#09A1A4','' );">
		<span class="glyphicon glyphicon-equalizer" aria-hidden="true" style="margin-right:4px;"></span>View Bar Graph</a></li>
 	</div>
 	
 	<div class="nursingUnitWiseStatusCounter">
		<li id="threelinerDataGraph1"><a href="#" onclick="showData('3rdBlock_1', 'nursingUnitStatusBar');">
		<span class="glyphicon glyphicon-list-alt" aria-hidden="true" style="margin-right:4px;"></span>View Data</a></li>
		<li id="threelinerDataGraph"><a href="#" onclick="showStatusStackedBar('3rdBlock_1', 'nursingUnitStatusBar', '', 'All', 'All', '#09A1A4','' );">
		<span class="glyphicon glyphicon-equalizer" aria-hidden="true" style="margin-right:4px;"></span>View Bar Graph</a></li>
 	</div>
 	</ul>
  	</div>
	<div class="clear"></div>
	</div>
	</li>
</ul>
<div class="contentdiv-small" style= "width:99%;height:380px;margin-top: -15px;margin-left: 5px;">
<div  class="backbttnDiv1" style="  float: left;">
	<button  id="backbttn1"  style="float:left;display:none"  type="button" class="btn btn-default btn-sm" >
	<span class="glyphicon glyphicon-arrow-left" aria-hidden="true"></span> Back
	</button>
</div>
<div style="overflow:auto;width:98%;height:320px;" id="3rdBlock_1"></div>
<div class="locationWiseStatusCounter"  style="margin-left: 525px;">
	<a href="#"><span class="label label-default" style="background-color: #09A1A4;" id="statusAll" onclick="showStatusStackedBar('3rdBlock_1', 'locationStatusBar', '', 'All', 'All', '#09A1A4','');">All</span></a>
	<a href="#"><span class="label label-danger" style="background-color:#F04E2F;" id="statusApp" onclick="showStatusStackedBar('3rdBlock_1','locationStatusBar','','Ordered','Ordered','#F04E2F','');">Ordered</span></a>
	<a href="#"><span class="label label-success" style="background-color:#5cb85c;"  id="statusSch" onclick="showStatusStackedBar('3rdBlock_1','locationStatusBar','','Dispatch','Dispatch','#5cb85c','');">Dispatch</span></a>
	<a href="#"><span class="label label-warning" style="background-color:#f0ad4e;" id="statusSerIn" onclick="showStatusStackedBar('3rdBlock_1','locationStatusBar','','Dispatch Error','Dispatch Error','#f0ad4e','');">Dispatch Error</span></a>
	<a href="#"><span class="label label-primary" style="background-color:#4F84FC;"  id="statusSerOut" onclick="showStatusStackedBar('3rdBlock_1','locationStatusBar','','Close','Close','#4F84FC','');">Close</span></a>
	<a href="#"><span class="label label-primary" style="background-color:#4DD4FF;"  id="statusSerOut" onclick="showStatusStackedBar('3rdBlock_1','locationStatusBar','','Force Close','Force Close','#4DD4FF',location1);">Force Close</span></a>
 	<a href="#"><span class="label label-primary" style="background-color:#4F8HHC;"  id="statusSerOut" onclick="showStatusStackedBar('3rdBlock_1','locationStatusBar','','Auto Close','Auto Close','#4F8HHC',location1);">Auto Close</span></a>
 	<a href="#"><span class="label label-primary" style="background-color:#4FAAFD;"  id="statusSerOut" onclick="showStatusStackedBar('3rdBlock_1','locationStatusBar','','Close-P','Close-P','#4FAAFD',location1);">Close-P</span></a>

 </div>


<div class="nursingUnitWiseStatusCounter" style="margin-left: 525px;">
	<a href="#"><span class="label label-default" style="background-color: #09A1A4;" id="statusAll" onclick="showStatusStackedBar('3rdBlock_1', 'nursingUnitStatusBar', '', 'All', 'All', '#09A1A4',location1);">All</span></a>
	<a href="#"><span class="label label-danger" style="background-color:#F04E2F;" id="statusApp" onclick="showStatusStackedBar('3rdBlock_1','nursingUnitStatusBar','','Ordered','Ordered','#F04E2F',location1);">Ordered</span></a>
	<a href="#"><span class="label label-success" style="background-color:#5cb85c;"  id="statusSch" onclick="showStatusStackedBar('3rdBlock_1','nursingUnitStatusBar','','Dispatch','Dispatch','#5cb85c',location1);">Dispatch</span></a>
	<a href="#"><span class="label label-warning" style="background-color:#f0ad4e;" id="statusSerIn" onclick="showStatusStackedBar('3rdBlock_1','nursingUnitStatusBar','','Dispatch Error','Dispatch Error','#f0ad4e',location1);">Dispatch Error</span></a>
	<a href="#"><span class="label label-primary" style="background-color:#4F84FC;"  id="statusSerOut" onclick="showStatusStackedBar('3rdBlock_1','nursingUnitStatusBar','','Close','Close','#4F84FC',location1);">Close</span></a>
 	<a href="#"><span class="label label-primary" style="background-color:#4DD4FF;"  id="statusSerOut" onclick="showStatusStackedBar('3rdBlock_1','nursingUnitStatusBar','','Force Close','Force Close','#4DD4FF',location1);">Force Close</span></a>
 	<a href="#"><span class="label label-primary" style="background-color:#4F8HHC;"  id="statusSerOut" onclick="showStatusStackedBar('3rdBlock_1','nursingUnitStatusBar','','Auto Close','Auto Close','#4F8HHC',location1);">Auto Close</span></a>
 	<a href="#"><span class="label label-primary" style="background-color:#4FAAFD;"  id="statusSerOut" onclick="showStatusStackedBar('3rdBlock_1','nursingUnitStatusBar','','Close-P','Close-P','#4FAAFD',location1);">Close-P</span></a>
 
 </div>

</div>
</div>
		
 
 
 
<div id="tab-4" class="tab-content">
<ul class="nav_links">
	<li style="top: 12px;margin-right:12px;">
	<a href="#"><span class="threeLiner" style="  margin-right: 2px" ></span></a> 
 	<div class="dropdown profile_dropdownDash"  >
	<div class="arrow_dropdown">&nbsp;</div>
	<div class="one_column" >
	<ul> 
	<div class="priorityStatusCounter">
		<li id="threelinerDataGraph1"><a href="#" onclick="showData('4thBlock_1', 'priorityBar');">
		<span class="glyphicon glyphicon-list-alt" aria-hidden="true" style="margin-right:4px;"></span>View Data</a></li>
		<li id="threelinerDataGraph"><a href="#" onclick="showStatusStackedBar('4thBlock_1', 'priorityBar', '', 'All', 'All', '#09A1A4','' );">
		<span class="glyphicon glyphicon-equalizer" aria-hidden="true" style="margin-right:4px;"></span>View Bar Graph</a></li>
 	</div>
 	</ul>
  	</div>
	<div class="clear"></div>
	</div>
	</li>
</ul>
<div class="contentdiv-small" style= "width:99%;height:380px;margin-top: -15px;margin-left: 5px;">
<div style="overflow:auto;width:98%;height:350px;" id="4thBlock_1"></div>
</div>
</div>
 
 
 
 
 
<div id="tab-5" class="tab-content">
<ul class="nav_links">
	<li style="top: 12px;margin-right:12px;">
	<a href="#"><span class="threeLiner" style="  margin-right: -13px" ></span></a> 
 	<div class="dropdown profile_dropdownDash"  >
	<div class="arrow_dropdown">&nbsp;</div>
	<div class="one_column" >
	<ul> 
	<div class="tatStatusCounter">
		<li id="threelinerDataGraph1"><a href="#" onclick="showData('5thBlock_1', 'tatBar');">
		<span class="glyphicon glyphicon-list-alt" aria-hidden="true" style="margin-right:4px;"></span>View Data</a></li>
		<li id="threelinerDataGraph"><a href="#" onclick="showTATStackedBar('5thBlock_1', 'tatBar', '', 'All', 'All', '#09A1A4','' );">
		<span class="glyphicon glyphicon-equalizer" aria-hidden="true" style="margin-right:4px;"></span>View Bar Graph</a></li>
 	</div>
 	</ul>
  	</div>
	<div class="clear"></div>
	</div>
	</li>
</ul>
<div class="contentdiv-small" style= "width:99%;height:380px;margin-top: -15px;margin-left: 5px;">
<div style="overflow:auto;width:98%;height:350px;" id="5thBlock_1"></div>
</div>
</div>
 
 
<div id="tab-6" class="tab-content">
<ul class="nav_links">
	<li style="top: 12px;margin-right:12px;">
	<a href="#"><span class="threeLiner" style="  margin-right: 2px" ></span></a> 
 	<div class="dropdown profile_dropdownDash" >
	<div class="arrow_dropdown">&nbsp;</div>
	<div class="one_column" >
	<ul> 
	<div class="specialityWiseStatusCounter">
		<li id="threelinerDataGraph1"><a href="#" onclick="showData('6thBlock_1', 'specialityBar');">
		<span class="glyphicon glyphicon-list-alt" aria-hidden="true" style="margin-right:4px;"></span>View Data</a></li>
		<li id="threelinerDataGraph"><a href="#" onclick="showStatusStackedBar('6thBlock_1', 'specialityBar', '', 'All', 'All', '#09A1A4','' );">
		<span class="glyphicon glyphicon-equalizer" aria-hidden="true" style="margin-right:4px;"></span>View Bar Graph</a></li>
 	</div>
 	
 	<div class="doctorWiseStatusCounter">
		<li id="threelinerDataGraph1"><a href="#" onclick="showData('6thBlock_1', 'doctorBar');">
		<span class="glyphicon glyphicon-list-alt" aria-hidden="true" style="margin-right:4px;"></span>View Data</a></li>
		<li id="threelinerDataGraph"><a href="#" onclick="showStatusStackedBar('6thBlock_1', 'doctorBar', '', 'All', 'All', '#09A1A4','' );">
		<span class="glyphicon glyphicon-equalizer" aria-hidden="true" style="margin-right:4px;"></span>View Bar Graph</a></li>
 	</div>
 	</ul>
  	</div>
	<div class="clear"></div>
	</div>
	</li>
</ul>
<div class="contentdiv-small" style= "width:99%;height:380px;margin-top: -15px;margin-left: 5px;">
<div  class="backbttnDiv1" style="  float: left;">
	<button  id="backbttn2"  style="float:left;display:none"  type="button" class="btn btn-default btn-sm" >
	<span class="glyphicon glyphicon-arrow-left" aria-hidden="true"></span> Back
	</button>
</div>
<div style="overflow:auto;width:98%;height:320px;" id="6thBlock_1"></div>
<div class="specialityWiseStatusCounter"  style="margin-left: 525px;">
	<a href="#"><span class="label label-default" style="background-color: #09A1A4;" id="statusAll" onclick="showStatusStackedBar('6thBlock_1', 'specialityBar', '', 'All', 'All', '#09A1A4','');">All</span></a>
	<a href="#"><span class="label label-danger" style="background-color:#F04E2F;" id="statusApp" onclick="showStatusStackedBar('6thBlock_1','specialityBar','','Ordered','Ordered','#F04E2F','');">Ordered</span></a>
	<a href="#"><span class="label label-success" style="background-color:#5cb85c;"  id="statusSch" onclick="showStatusStackedBar('6thBlock_1','specialityBar','','Dispatch','Dispatch','#5cb85c','');">Dispatch</span></a>
	<a href="#"><span class="label label-warning" style="background-color:#f0ad4e;" id="statusSerIn" onclick="showStatusStackedBar('6thBlock_1','specialityBar','','Dispatch Error','Dispatch Error','#f0ad4e','');">Dispatch Error</span></a>
	<a href="#"><span class="label label-primary" style="background-color:#4F84FC;"  id="statusSerOut" onclick="showStatusStackedBar('6thBlock_1','specialityBar','','Close','Close','#4F84FC','');">Close</span></a>
 	<a href="#"><span class="label label-primary" style="background-color:#4DD4FF;"  id="statusSerOut" onclick="showStatusStackedBar('6thBlock_1','specialityBar','','Force Close','Force Close','#4DD4FF',location1);">Force Close</span></a>
 	<a href="#"><span class="label label-primary" style="background-color:#4F8HHC;"  id="statusSerOut" onclick="showStatusStackedBar('6thBlock_1','specialityBar','','Auto Close','Auto Close','#4F8HHC',location1);">Auto Close</span></a>
 	<a href="#"><span class="label label-primary" style="background-color:#4FAAFD;"  id="statusSerOut" onclick="showStatusStackedBar('6thBlock_1','specialityBar','','Close-P','Close-P','#4FAAFD',location1);">Close-P</span></a>
 
 </div>


<div class="doctorWiseStatusCounter" style="margin-left: 525px;">
	<a href="#"><span class="label label-default" style="background-color: #09A1A4;" id="statusAll" onclick="showStatusStackedBar('6thBlock_1', 'doctorBar', '', 'All', 'All', '#09A1A4',location1);">All</span></a>
	<a href="#"><span class="label label-danger" style="background-color:#F04E2F;" id="statusApp" onclick="showStatusStackedBar('6thBlock_1','doctorBar','','Ordered','Ordered','#F04E2F',location1);">Ordered</span></a>
	<a href="#"><span class="label label-success" style="background-color:#5cb85c;"  id="statusSch" onclick="showStatusStackedBar('6thBlock_1','doctorBar','','Dispatch','Dispatch','#5cb85c',location1);">Dispatch</span></a>
	<a href="#"><span class="label label-warning" style="background-color:#f0ad4e;" id="statusSerIn" onclick="showStatusStackedBar('6thBlock_1','doctorBar','','Dispatch Error','Dispatch Error','#f0ad4e',location1);">Dispatch Error</span></a>
	<a href="#"><span class="label label-primary" style="background-color:#4F84FC;"  id="statusSerOut" onclick="showStatusStackedBar('6thBlock_1','doctorBar','','Close','Close','#4F84FC',location1);">Close</span></a>
 	<a href="#"><span class="label label-primary" style="background-color:#4DD4FF;"  id="statusSerOut" onclick="showStatusStackedBar('6thBlock_1','doctorBar','','Force Close','Force Close','#4DD4FF',location1);">Force Close</span></a>
 	<a href="#"><span class="label label-primary" style="background-color:#4F8HHC;"  id="statusSerOut" onclick="showStatusStackedBar('6thBlock_1','doctorBar','','Auto Close','Auto Close','#4F8HHC',location1);">Auto Close</span></a>
 	<a href="#"><span class="label label-primary" style="background-color:#4FAAFD;"  id="statusSerOut" onclick="showStatusStackedBar('6thBlock_1','doctorBar','','Close-P','Close-P','#4FAAFD',location1);">Close-P</span></a>
 
 </div>

</div>
</div>





<div  id="tab-1" class="tab-content">
<div class="contentdiv-small" style= "width:99%;height:380px;margin-top:">
<ul class="nav_links">
	<li style="top: 12px;margin-right:12px;">
	<a href="#"><span class="threeLiner" style="  margin-right: -13px" ></span></a> 
 	<div class="dropdown profile_dropdownDash"  >
	<div class="arrow_dropdown">&nbsp;</div>
	<div class="one_column" >
	<ul> 
	<div class="overAllAndRecomDiv">
		<li id="threelinerDataGraph1"><a href="#" onclick="showData('7thBlock_1', 'statusBar');">
		<span class="glyphicon glyphicon-list-alt" aria-hidden="true" style="margin-right:4px;"></span>View Data</a></li>
		<li id="threelinerDataGraph"><a href="#" onclick="showStatusStackedBar('7thBlock_1', 'statusBar', '', 'All', 'All', '#09A1A4','' );">
		<span class="glyphicon glyphicon-equalizer" aria-hidden="true" style="margin-right:4px;"></span>View Bar Graph</a></li>
		<li id="threelinerDataGraph"><a href="#" onclick="showStatusPieChart('7thBlock_1','statusPie');">
		<span class="glyphicon glyphicon-adjust" aria-hidden="true" style="margin-right:4px;"></span>View Pie Chart</a></li>
 	</div>
 	</ul>
  	</div>
	<div class="clear"></div>
	</div>
	</li>
</ul> 
<div style="overflow:auto;width:98%;height:350px;" id="7thBlock_1"></div>
</div>
 
</div>

<div  id="tab-8" class="tab-content">
<div class="contentdiv-small" style= "width:99%;height:380px;margin-top:">
<ul class="nav_links">
	<li style="top: 12px;margin-right:12px;">
	<a href="#"><span class="threeLiner" style="  margin-right: -13px" ></span></a> 
 	<div class="dropdown profile_dropdownDash"  >
	<div class="arrow_dropdown">&nbsp;</div>
	<div class="one_column" >
	<ul> 
	<div class="tatStatusCounter">
		<li id="threelinerDataGraph1"><a href="#" onclick="showData('8thBlock_1', 'tatBar');">
		<span class="glyphicon glyphicon-list-alt" aria-hidden="true" style="margin-right:4px;"></span>View Data</a></li>
		<li id="threelinerDataGraph"><a href="#" onclick="showTATStackedBar('8thBlock_1', 'tatBar', '', 'All', 'All', '#09A1A4','' );">
		<span class="glyphicon glyphicon-equalizer" aria-hidden="true" style="margin-right:4px;"></span>View Bar Graph</a></li>
		<li id="threelinerDataGraph"><a href="#" onclick="showTATPieChart('8thBlock_1','tatPie');">
		<span class="glyphicon glyphicon-adjust" aria-hidden="true" style="margin-right:4px;"></span>View Pie Graph</a></li>
 	</div>
 	</ul>
  	</div>
	<div class="clear"></div>
	</div>
	</li>
</ul>
Priority:<s:select  
	    	id	=	"prioritySearch"
	    	name	=	"prioritySearch"
	    	list	=	"#{'All':'All','Routine':'Routine','Urgent':'Urgent'}"
	      	theme	=	"simple"
	      	cssClass	=	"button"
	      	cssStyle	=	"height: 28px;margin-left: -2px;width: 100px;"
	      	 onchange	=	"showTATPieChart('8thBlock_1','tatPie');">
</s:select>
<div style="overflow:auto;width:98%;height:330px;" id="8thBlock_1"></div>
</div>

</div>
 
 
 </div>
 </div>

 
      
      </div> 

 </body>
</html>