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
	  
	document.getElementById('sdate').value=$("#hfromDate").val();
   document.getElementById('edate').value=$("#htoDate").val();
   
    
   
});
chartTab();
function chartTab()
{
	  
	showStatusPieChart('statusChart','statusPie');
	showProductivityPieChart('productivityChart','productivityPie');

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
 <div class="list-icon">
	<div class="head">Dashboard </div>
	<div class="head">
	<img alt="" src="images/forward.png" style="margin-top:10px;margin-right:3px; float: left;">Pharmacy 
	</div>
</div>
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
 
 
 
 
<div style="overflow: auto;" id="dashDataPart">
         
  <!--Ticket Status Counter Dashboard Start -->
  <div class="contentdiv-smallNewDash" style="overflow: hidden;width: 99%;height: 365px;" id="service">
		<div id="counterChart1">
	<input checked id="one" name="tabs" type="radio" style="opacity:0" onclick="showStatusStackedBar('statusChart', 'statusBar', '', 'All', 'All', '#09A1A4','' );">
    <label class="lable1" for="one">Status Wise</label>
    <input id="two" name="tabs" type="radio" value="Two " style="opacity:0" onclick="showStatusStackedBar('statusChart', 'locationStatusBar', '', 'All', 'All', '#09A1A4','' );">
    <label class="lable1" for="two">Location Wise Status</label>
<div  class="backbttnDiv1" style="  float: left;">
	<button  id="backbttn1"  style="float:left;display:none"  type="button" class="btn btn-default btn-sm" >
	<span class="glyphicon glyphicon-arrow-left" aria-hidden="true"></span> Back
	</button>
</div>	     
 <div class="statusCounter">
<div class="headdingtest" style="margin-top: -25px;"> Status Counter </div>
 </div>
 
<div class="locationWiseStatusCounter">
<div class="headdingtest" style="margin-top: -25px;"> Location Wise Status</div>
 </div>
 
 
<div class="nursingUnitWiseStatusCounter">
<div class="headdingtest" style="margin-top: -25px;"> Nursing Unit Wise Status</div>
</div>
  
 
 <ul class="nav_links">
	<li style="top: -25px;"><a href="#">
	<span class="threeLiner" style="  margin-right: 10px" ></span>
		 </a> 
			
			
	<div class="dropdown profile_dropdown">
	<div class="arrow_dropdown">&nbsp;</div>
	<div class="one_column" >
	<ul> 
	<div class="statusCounter">
		<li id="threelinerDataGraph1"><a href="#" onclick="showData('statusChart', 'statusBar');">
		<span class="glyphicon glyphicon-list-alt" aria-hidden="true" style="margin-right:4px;"></span>View Data</a></li>
		<li id="threelinerDataGraph"><a href="#" onclick="showStatusStackedBar('statusChart', 'statusBar', '', 'All', 'All', '#09A1A4','' );">
		<span class="glyphicon glyphicon-equalizer" aria-hidden="true" style="margin-right:4px;"></span>View Bar Graph</a></li>
		<li id="threelinerDataGraph"><a href="#" onclick="showStatusPieChart('statusChart','statusPie');">
		<span class="glyphicon glyphicon-list-alt" aria-hidden="true" style="margin-right:4px;"></span>View Pie Chart</a></li>
 	</div>
	
	
	<div class="locationWiseStatusCounter">
		<li id="threelinerDataGraph1"><a href="#" onclick="showData('statusChart', 'locationStatusBar');">
		<span class="glyphicon glyphicon-list-alt" aria-hidden="true" style="margin-right:4px;"></span>View Data</a></li>
		<li id="threelinerDataGraph"><a href="#" onclick="showStatusStackedBar('statusChart', 'locationStatusBar', '', 'All', 'All', '#09A1A4','' );">
		<span class="glyphicon glyphicon-equalizer" aria-hidden="true" style="margin-right:4px;"></span>View Bar Graph</a></li>
 	</div>
 	
 	<div class="nursingUnitWiseStatusCounter">
		<li id="threelinerDataGraph1"><a href="#" onclick="showData('statusChart', 'nursingUnitStatusBar');">
		<span class="glyphicon glyphicon-list-alt" aria-hidden="true" style="margin-right:4px;"></span>View Data</a></li>
		<li id="threelinerDataGraph"><a href="#" onclick="showStatusStackedBar('statusChart', 'nursingUnitStatusBar', '', 'All', 'All', '#09A1A4',location1 );">
		<span class="glyphicon glyphicon-equalizer" aria-hidden="true" style="margin-right:4px;"></span>View Bar Graph</a></li>
 	</div>
	
	
	
	
	</ul>
	</div>
	<div class="clear"></div>
	</div>
	</li>
</ul>
 
<div class="clear"></div>
   
<div id="statusChart" style=" height: 245px;"></div>


<div class="locationWiseStatusCounter">
	<a href="#"><span class="label label-default" style="background-color: #09A1A4;" id="statusAll" onclick="showStatusStackedBar('statusChart', 'locationStatusBar', '', 'All', 'All', '#09A1A4','');">All</span></a>
	<a href="#"><span class="label label-danger" style="background-color:#F04E2F;" id="statusApp" onclick="showStatusStackedBar('statusChart','locationStatusBar','','Ordered','Ordered','#F04E2F','');">Ordered</span></a>
	<a href="#"><span class="label label-success" style="background-color:#5cb85c;"  id="statusSch" onclick="showStatusStackedBar('statusChart','locationStatusBar','','Dispatch','Dispatch','#5cb85c','');">Dispatch</span></a>
	<a href="#"><span class="label label-warning" style="background-color:#f0ad4e;" id="statusSerIn" onclick="showStatusStackedBar('statusChart','locationStatusBar','','Dispatch Error','Dispatch Error','#f0ad4e','');">Dispatch Error</span></a>
	<a href="#"><span class="label label-primary" style="background-color:#4F84FC;"  id="statusSerOut" onclick="showStatusStackedBar('statusChart','locationStatusBar','','Close','Close','#4F84FC','');">Close</span></a>
 </div>


<div class="nursingUnitWiseStatusCounter">
	<a href="#"><span class="label label-default" style="background-color: #09A1A4;" id="statusAll" onclick="showStatusStackedBar('statusChart', 'nursingUnitStatusBar', '', 'All', 'All', '#09A1A4',location1);">All</span></a>
	<a href="#"><span class="label label-danger" style="background-color:#F04E2F;" id="statusApp" onclick="showStatusStackedBar('statusChart','nursingUnitStatusBar','','Ordered','Ordered','#F04E2F',location1);">Ordered</span></a>
	<a href="#"><span class="label label-success" style="background-color:#5cb85c;"  id="statusSch" onclick="showStatusStackedBar('statusChart','nursingUnitStatusBar','','Dispatch','Dispatch','#5cb85c',location1);">Dispatch</span></a>
	<a href="#"><span class="label label-warning" style="background-color:#f0ad4e;" id="statusSerIn" onclick="showStatusStackedBar('statusChart','nursingUnitStatusBar','','Dispatch Error','Dispatch Error','#f0ad4e',location1);">Dispatch Error</span></a>
	<a href="#"><span class="label label-primary" style="background-color:#4F84FC;"  id="statusSerOut" onclick="showStatusStackedBar('statusChart','nursingUnitStatusBar','','Close','Close','#4F84FC',location1);">Close</span></a>
 </div>

</div>
   
  
</div>
<!-- Ticket Status Counter Dashboard  End -->

 
   
   
   
   
   
   
   
         
  <!-- Productivity Counter  -->
   <div class="contentdiv-smallNewDash" style="overflow: hidden;width: 99%;height: 365px;" id="productivity">
		<div id="counterChart">
 <input checked id="one1" name="tabs" type="radio" style="opacity:0" onclick="showProductivityStackedBar('productivityChart', 'productivityBar', '', 'All', 'All', '#09A1A4','');">
    <label class="lable1" for="one1">Level Wise</label>
    <input id="two1" name="tabs" type="radio" value="Two " style="opacity:0" onclick="showProductivityStackedBar('productivityChart', 'locationProductivityBar', '', 'All', 'All', '#09A1A4','');">
    <label class="lable1" for="two1">Location Wise Level</label>
 <div  class="backbttnDiv2" style="  float: left;">
	<button  id="backbttn2"  style="float:left;display:none"  type="button" class="btn btn-default btn-sm" >
	<span class="glyphicon glyphicon-arrow-left" aria-hidden="true"></span> Back
	</button>
</div>	
<div class="productivityCounter">
<div class="headdingtest" style="margin-top: -25px;">Productivity Counter</div>
</div>
     
     
 <div class="locationWiseProductivityCounter">
<div class="headdingtest" style="margin-top: -25px;"> Location Wise Productivity Counter </div>
 </div>     
     
     
 <div class="nursingUnitWiseProductivityCounter">
<div class="headdingtest" style="margin-top: -25px;"> Nursing Unit Wise Productivity Counter </div>
 </div>        
     
 <ul class="nav_links">
	<li style="top: -25px;"><a href="#">
	<span class="threeLiner" style="margin-right: 10px" ></span>
		 </a> 
			
			
	<div class="dropdown profile_dropdown">
	<div class="arrow_dropdown">&nbsp;</div>
	<div class="one_column" >
	<ul> 
	<div class="productivityCounter">
		<li id="threelinerDataGraph1"><a href="#" onclick="showData('productivityChart', 'productivityBar');">
		<span class="glyphicon glyphicon-list-alt" aria-hidden="true" style="margin-right:4px;"></span>View Data</a></li>
		<li id="threelinerDataGraph"><a href="#" onclick="showProductivityStackedBar('productivityChart', 'productivityBar', '', 'All', 'All', '#09A1A4','');">
		<span class="glyphicon glyphicon-equalizer" aria-hidden="true" style="margin-right:4px;"></span>View Bar Graph</a></li>
		<li id="threelinerDataGraph"><a href="#" onclick="showProductivityPieChart('productivityChart','productivityPie');">
		<span class="glyphicon glyphicon-list-alt" aria-hidden="true" style="margin-right:4px;"></span>View Pie Chart</a></li>
		 
	</div>
	
	
	<div class="locationWiseProductivityCounter">
		<li id="threelinerDataGraph1"><a href="#" onclick="showData('productivityChart', 'locationProductivityBar');">
		<span class="glyphicon glyphicon-list-alt" aria-hidden="true" style="margin-right:4px;"></span>View Data</a></li>
		<li id="threelinerDataGraph"><a href="#" onclick="showProductivityStackedBar('productivityChart', 'locationProductivityBar', '', 'All', 'All', '#09A1A4','');">
		<span class="glyphicon glyphicon-equalizer" aria-hidden="true" style="margin-right:4px;"></span>View Bar Graph</a></li>
 	</div>
 	
 	<div class="nursingUnitWiseProductivityCounter">
		<li id="threelinerDataGraph1"><a href="#" onclick="showData('productivityChart', 'nursingUnitProductivityBar');">
		<span class="glyphicon glyphicon-list-alt" aria-hidden="true" style="margin-right:4px;"></span>View Data</a></li>
		<li id="threelinerDataGraph"><a href="#" onclick="showProductivityStackedBar('productivityChart', 'nursingUnitProductivityBar', '', 'All', 'All', '#09A1A4',location2);">
		<span class="glyphicon glyphicon-equalizer" aria-hidden="true" style="margin-right:4px;"></span>View Bar Graph</a></li>
	 		 
	</div>
	
	
	
	
 	 </ul>
	</div>
	<div class="clear"></div>
	</div>
	</li>
</ul>
 
<div class="clear"></div>
   
<div id="productivityChart" style="height: 245px;"></div>

<div class="locationWiseProductivityCounter">
	<a href="#"><span class="label label-default" style="background-color: #09A1A4;" id="statusAll" onclick="showProductivityStackedBar('productivityChart', 'locationProductivityBar', '', 'All', 'All', '#09A1A4','' );">All</span></a>
	<a href="#"><span class="label label-danger" style="background-color:#57595D;" id="statusApp" onclick="showProductivityStackedBar('productivityChart','locationProductivityBar','','1','Level1','#57595D','');">Level1</span></a>
	<a href="#"><span class="label label-success" style="background-color:#5cb85c;"  id="statusSch" onclick="showProductivityStackedBar('productivityChart','locationProductivityBar','','2','Level2','#5cb85c','');">Level2</span></a>
	<a href="#"><span class="label label-warning" style="background-color:#f0ad4e;" id="statusSerIn" onclick="showProductivityStackedBar('productivityChart','locationProductivityBar','','3','Level3','#f0ad4e','');">Level3</span></a>
	<a href="#"><span class="label label-primary" style="background-color:#4F84FC;"  id="statusSerOut" onclick="showProductivityStackedBar('productivityChart','locationProductivityBar','','4','Level4','#4F84FC','');">Level4</span></a>
	<a href="#"><span class="label label-info" style="background-color:#F04E2F;"  id="cancel" onclick="showProductivityStackedBar('productivityChart','locationProductivityBar','','5','Level5','#F04E2F','');">Level5</span></a>
</div>



<div class="nursingUnitWiseProductivityCounter">
	<a href="#"><span class="label label-default" style="background-color: #09A1A4;" id="statusAll" onclick="showProductivityStackedBar('productivityChart', 'nursingUnitProductivityBar', '', 'All', 'All', '#09A1A4',location2 );">All</span></a>
	<a href="#"><span class="label label-danger" style="background-color:#57595D;" id="statusApp" onclick="showProductivityStackedBar('productivityChart','nursingUnitProductivityBar','','1','Level1','#57595D',location2);">Level1</span></a>
	<a href="#"><span class="label label-success" style="background-color:#5cb85c;"  id="statusSch" onclick="showProductivityStackedBar('productivityChart','nursingUnitProductivityBar','','2','Level2','#5cb85c',location2);">Level2</span></a>
	<a href="#"><span class="label label-warning" style="background-color:#f0ad4e;" id="statusSerIn" onclick="showProductivityStackedBar('productivityChart','nursingUnitProductivityBar','','3','Level3','#f0ad4e',location2);">Level3</span></a>
	<a href="#"><span class="label label-primary" style="background-color:#4F84FC;"  id="statusSerOut" onclick="showProductivityStackedBar('productivityChart','nursingUnitProductivityBar','','4','Level4','#4F84FC',location2);">Level4</span></a>
	<a href="#"><span class="label label-info" style="background-color:#F04E2F;"  id="cancel" onclick="showProductivityStackedBar('productivityChart','nursingUnitProductivityBar','','5','Level5','#F04E2F',location2);">Level5</span></a>
</div>


</div>

 
         </div>
      </div>
      </div> 

 </body>
</html>