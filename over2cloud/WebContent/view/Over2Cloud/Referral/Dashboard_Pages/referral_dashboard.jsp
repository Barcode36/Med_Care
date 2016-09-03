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
 <title>Referral Dashboard</title>
<s:url id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
 <script type="text/javascript" src="<s:url value="/js/dashboard/referral/dashboardbar.js"/>"></script>
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
<script type="text/javascript">
$(document).ready(function(){
	 
    $('#button2').jqxSwitchButton({ height: 20, width: 81, checked: false });
     $('#button2').bind('checked', function (event) { 
    	showTimeDiv('statusTime',false);
    	
    });
    $('#button2').bind('unchecked', function (event) { 
    	showTimeDiv('statusTime',true);
    	
    });
    
    
    $('#button1').jqxSwitchButton({ height: 20, width: 81, checked: false });
    $('#button1').bind('checked', function (event) { 
    	$('#emergency').val("false");
    	getRange();
     });
   $('#button1').bind('unchecked', function (event) { 
	   $('#emergency').val("true");
	   getRange();
   	
   });
   $('#button4').jqxSwitchButton({ height: 20, width: 81, checked: false });
   $('#button4').bind('checked', function (event) { 
  	showTimeDiv1('statusTime',false);
  	
  });
  $('#button4').bind('unchecked', function (event) { 
  	showTimeDiv1('statusTime',true);
  	
  });
  
  
  $('#button5').jqxSwitchButton({ height: 20, width: 81, checked: false });
  $('#button5').bind('checked', function (event) { 
  	$('#emergency').val("false");
  	getRangeForSpeciality();
   });
 $('#button5').bind('unchecked', function (event) { 
	   $('#emergency').val("true");
	   getRangeForSpeciality();
 	
 });
    
    });
chartTab();
function chartTab()
{
	 
	 showReferralOrderCounter('referralOrder', '', 'Day', 'total', 'All', '#09A1A4');
	 showReferralStatusCounter('referralStatus', '', 'Day', 'total', 'All', '#09A1A4');
	// showReferralAttendedCounter('referralStatus', '', 'Day', 'total', 'All', '#09A1A4');
}

function ChangeRefferedByMeToMe(value)
{
	 if(value=='1')
	 {
		 $('#refDoc').val("toMe"); 
		 getRange();
	 }
	 if(value=='2')
	{
		 $('#refDoc').val("byMe");
		 getRange();
			 
	}
	  
}
 
function showTimeDiv(timeDiv,status)
{
	 
	 if(timeDiv=='statusTime' && status==true)
		 {
	  		$(".statusTime").show();
	  		$("#fromTime").val("");
	  		$("#toTime").val("");
	  		
		 }
	 else if(timeDiv=='statusTime' && status==false)
		 {
 	 		$(".statusTime").hide();
 	 		$("#fromTime").val("");
	  		$("#toTime").val("");
 	 		getRange();
		 }
	  
}
function showTimeDiv1(timeDiv,status)
{
	 
	 if(timeDiv=='statusTime' && status==true)
		 {
	  		$(".statusTime1").show();
	  		$("#fromTime").val("");
	  		$("#toTime").val("");
	  		
		 }
	 else if(timeDiv=='statusTime' && status==false)
		 {
 	 		$(".statusTime1").hide();
 	 		$("#fromTime").val("");
	  		$("#toTime").val("");
 	 		getRange();
		 }
	  
}
 
function getShowHideDiv(divType)
{
	 	  if(divType=='Status')
	 		  {
	 		 dataForStatus='dashboard1';
	 		 	$(".referralStatusPanel").show();
				$(".referralAttendedPanel").hide();
	 		  }
	   	  if(divType=='Attended')
	   		  {
	   		dataForStatus='dashboard2';
	   			$(".referralAttendedPanel").show();
				$(".referralStatusPanel").hide();
	   		  }
}
function getshowReferralCounter(type)
{
	  var RangeStatus=$("#dateRangeStatus").val();
	 if(type=='Order')
	  		showReferralOrderCounter('referralOrder', '', RangeStatus, 'total', 'All', '#09A1A4');
	  else if(type=='Status')
	 	  		showReferralStatusCounter('referralStatus', '', RangeStatus, 'total', 'All', '#09A1A4');
		  else if(type=='Attended')
			  showReferralAttendedCounter('referralStatus', '', RangeStatus, 'total', 'All', '#09A1A4');
		 
}
function getshowData(type)
{
	 var RangeStatus=$("#dateRangeStatus").val();
 	  if(type=='Order')
		 showData('referralOrder',RangeStatus,'Order');
	  else if(type=='Status')
	 	 	 	showData('referralStatus',RangeStatus,'Status');
	  else if(type=='Attended')
			 	 showData('referralStatus',RangeStatus,'Attended');
	  else if(type=='Speciality')
		 	 showData('referralStatus',RangeStatus,'Speciality');
	 
}
 
function getRange()
{
	 
 	var divFor=$("#dateRangeStatus").val();
	 	if(divFor!='Date')
		{
		$("#fromDate").val('');
		$("#toDate").val('');
		}
	 	 if(divFor=='Day')
	 {
	 		 
		 	$("#dayPickerStatus").show();
			$("#monthPickerStatus").hide();
			$("#datePickerStatus").hide();
			$("#yearPickerStatus").hide();
			$("#weekPickerStatus").hide();
	 		if(dataForStatus=='dashboard1')
				showReferralStatusCounter('referralStatus', '', divFor, 'total', 'All', '#09A1A4');
						else if(dataForStatus=='table1')
				showData('referralStatus',divFor,'Status');
						else if(dataForStatus=='dashboard2')
				showReferralAttendedCounter('referralStatus', '', divFor, 'total', 'All', '#09A1A4');
						else if(dataForStatus=='table2')
				showData('referralStatus',divFor,'Attended');
						else if(dataForStatus=='table3')
				showData('referralStatus',divFor,'Speciality');
	 		
	 		if(dataForOrder=='dashboard1')
				showReferralOrderCounter('referralOrder', '',divFor, 'total', 'All', '#09A1A4');
			else if(dataForOrder=='table1')
				showData('referralOrder',divFor,'Order');
			 
	 }
	 
	 else if(divFor=='Month')
	 {
		 	$("#monthPickerStatus").show();
			$("#dayPickerStatus").hide();
			$("#datePickerStatus").hide();
			$("#yearPickerStatus").hide();
			$("#weekPickerStatus").hide();
			if(dataForStatus=='dashboard1')
				showReferralStatusCounter('referralStatus', '', divFor, 'total', 'All', '#09A1A4');
						else if(dataForStatus=='table1')
				showData('referralStatus',divFor,'Status');
						else if(dataForStatus=='dashboard2')
				showReferralAttendedCounter('referralStatus', '', divFor, 'total', 'All', '#09A1A4');
						else if(dataForStatus=='table2')
				showData('referralStatus',divFor,'Attended');
						else if(dataForStatus=='table3')
				showData('referralStatus',divFor,'Speciality');
			if(dataForOrder=='dashboard1')
				showReferralOrderCounter('referralOrder', '', divFor, 'total', 'All', '#09A1A4');
			else if(dataForOrder=='table1')
				showData('referralOrder',divFor,'Order'); 
		
	 }
	 else if(divFor=='Week')
	 {
		 	$("#weekPickerStatus").show();
		 	$("#yearPickerStatus").hide();
		 	$("#datePickerStatus").hide();
			$("#dayPickerStatus").hide();
			$("#monthPickerStatus").hide();
			if(dataForStatus=='dashboard1')
				showReferralStatusCounter('referralStatus', '', divFor, 'total', 'All', '#09A1A4');
						else if(dataForStatus=='table1')
				showData('referralStatus',divFor,'Status');
						else if(dataForStatus=='dashboard2')
				showReferralAttendedCounter('referralStatus', '', divFor, 'total', 'All', '#09A1A4');
						else if(dataForStatus=='table2')
				showData('referralStatus',divFor,'Attended');
						else if(dataForStatus=='table3')
				showData('referralStatus',divFor,'Speciality');
			
			if(dataForOrder=='dashboard1')
				showReferralOrderCounter('referralOrder', '', divFor, 'total', 'All', '#09A1A4');
			else if(dataForOrder=='table1')
				showData('referralOrder',divFor,'Order');
		
	 }
	 else if(divFor=='Year')
	 {
		 	$("#yearPickerStatus").show();
		 	$("#datePickerStatus").hide();
			$("#dayPickerStatus").hide();
			$("#monthPickerStatus").hide();
			$("#weekPickerStatus").hide();
			if(dataForStatus=='dashboard1')
				showReferralStatusCounter('referralStatus', '', divFor, 'total', 'All', '#09A1A4');
						else if(dataForStatus=='table1')
				showData('referralStatus',divFor,'Status');
						else if(dataForStatus=='dashboard2')
				showReferralAttendedCounter('referralStatus', '', divFor, 'total', 'All', '#09A1A4');
						else if(dataForStatus=='table2')
				showData('referralStatus',divFor,'Attended');
						else if(dataForStatus=='table3')
				showData('referralStatus',divFor,'Speciality');
			if(dataForOrder=='dashboard1')
				showReferralOrderCounter('referralOrder', '', divFor, 'total', 'All', '#09A1A4');
			else if(dataForOrder=='table1')
				showData('referralOrder',divFor,'Order');
	 }
	 else if(divFor=='Date')
	 {
		 	  	$("#datePickerStatus").show();
	 	 	$("#monthPickerStatus").hide();
			$("#dayPickerStatus").hide();
			$("#yearPickerStatus").hide();
			$("#weekPickerStatus").hide();
		 
		if($("#fromDate").val()!=null  && $("#toDate").val()!=null && $("#fromDate").val()!='' && $("#toDate").val()!='' )
			{
			if(dataForStatus=='dashboard1')
				showReferralStatusCounter('referralStatus', '', divFor, 'total', 'All', '#09A1A4');
						else if(dataForStatus=='table1')
				showData('referralStatus',divFor,'Status');
						else if(dataForStatus=='dashboard2')
				showReferralAttendedCounter('referralStatus', '', divFor, 'total', 'All', '#09A1A4');
						else if(dataForStatus=='table2')
				showData('referralStatus',divFor,'Attended');
						else if(dataForStatus=='table3')
				showData('referralStatus',divFor,'Speciality');
						 
			if(dataForOrder=='dashboard1')
				showReferralOrderCounter('referralOrder', '', divFor, 'total', 'All', '#09A1A4');
			else if(dataForOrder=='table1')
				showData('referralOrder',divFor,'Order'); 
			}
			 
	 }
}
function getRangeForSpeciality()
{
	var divFor=$("#dateRangeStatus1").val();
 	if(divFor!='Date')
		{
		$("#fromDateSpec").val('');
		$("#toDateSpec").val('');
		}
	 	 if(divFor=='Day')
	 {
	 		 
		 	$("#dayPickerStatus1").show();
			$("#monthPickerStatus1").hide();
			$("#datePickerStatus1").hide();
			$("#yearPickerStatus1").hide();
			$("#weekPickerStatus1").hide();
			if(dataForStatus=='table4')
					showSpeciality(divFor);
				 
			 
	 }
	 
	 else if(divFor=='Month')
	 {
		 	$("#monthPickerStatus1").show();
			$("#dayPickerStatus1").hide();
			$("#datePickerStatus1").hide();
			$("#yearPickerStatus1").hide();
			$("#weekPickerStatus1").hide();
			if(dataForStatus=='table4')
					showSpeciality(divFor);
				 
		
	 }
	 else if(divFor=='Week')
	 {
		 	$("#weekPickerStatus1").show();
		 	$("#yearPickerStatus1").hide();
		 	$("#datePickerStatus1").hide();
			$("#dayPickerStatus1").hide();
			$("#monthPickerStatus1").hide();
			if(dataForStatus=='table4')
					showSpeciality(divFor);
				 
		
	 }
	 else if(divFor=='Year')
	 {
		 	$("#yearPickerStatus1").show();
		 	$("#datePickerStatus1").hide();
			$("#dayPickerStatus1").hide();
			$("#monthPickerStatus1").hide();
			$("#weekPickerStatus1").hide();
			if(dataForStatus=='table4')
					showSpeciality(divFor);
				 
	 }
	 else if(divFor=='Date')
	 {
		 
		  	$("#datePickerStatus1").show();
	 	 	$("#monthPickerStatus1").hide();
			$("#dayPickerStatus1").hide();
			$("#yearPickerStatus1").hide();
			$("#weekPickerStatus1").hide();
		 
		if($("#fromDateSpec").val()!=null  && $("#toDateSpec").val()!=null && $("#fromDateSpec").val()!='' && $("#toDateSpec").val()!='' )
			{
			if(dataForStatus=='table4')
	 							showSpeciality(divFor);
			 
			}
			 
	 }
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
line-height: 15px;
margin-left: 30px; 
font-size: 15px;
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
          id="maxmizeSpecDialog"
          showEffect="slide"
          hideEffect="explode"
          autoOpen="false"
          modal="true"
           closeTopics="closeEffectDialog"
          >
  <div class="contentdiv-smallNewDash" style="overflow: auto;width: 99.5%;height: 560px;" id="dashDataPart">        
  <div class="contentdiv-smallNewDash" style="width: 99%;height:40px;" id="dashDataPart">     
 <div style="margin: 4px 4px 0px 50px;">
 
<div style="float:left; width:auto; margin: 0px 4px 0px 0px;">
				                   <s:select  
				                              id="designation"
				                              name="designation"
				                             	list="#{'Consultant':'Consultant','Resident':'Resident','Medical Officer':'Medical Officer'}"
				                              headerKey="-1"
				                              headerValue="All Designation"
				                              cssClass="select"
					                          cssStyle="height:22px;  background: #EDECEC;"
					                          onchange="getRangeForSpeciality();"
				                              >
				                  </s:select>
				                   <s:select  
				                               id="dateRangeStatus1"
				                              name="dateRangeStatus1"
				                              list="#{'Day':'Day Wise','Week':'Week Wise','Month':'Month Wise','Year':'Year Wise','Date':'Date Wise'}"
				                              cssClass="select"
					                          cssStyle="height:22px;  background: #EDECEC;"
					                          onchange="getRangeForSpeciality();"
				                              >
				                  </s:select> </div>
<div id="dayPickerStatus1" style="float: left;">
<s:select  
				                               id="fromMonthStatusSpec1"
				                              name="fromMonthStatusSpec1"
				                              list="#{'01':'Jan','02':'Feb','03':'Mar','04':'Apr','05':'May','06':'Jun','07':'Jul','08':'Aug','09':'Sep','10':'Oct','11':'Nov','12':'Dec'}"
				                              headerValue="%{currentDayMonthValue}"
				                              headerKey="%{currentDayMonthKey}"
				                              cssClass="select"
					                          cssStyle="height:22px;  background: #EDECEC;"
					                           onchange="getRangeForSpeciality();"
				                              >
				                  </s:select>  
				                  <s:select  
				                               id="fromYearStatusSpec1"
				                              name="fromYearStatusSpec1"
				                              list="#{'2011':'2011','2012':'2012','2013':'2013','2014':'2014','2015':'2015','2016':'2016','2017':'2017','2018':'2018','2019':'2019','2020':'2020'}"
				                              headerValue="%{currentYear}"
				                              headerKey="%{currentYear}"
				                              cssClass="select"
					                          cssStyle="height:22px;  background: #EDECEC;"
					                           onchange="getRangeForSpeciality();"
				                              >
				                  </s:select>  
 
</div>	 

<div id="datePickerStatus1" style="float: left;display: none;">
From:<sj:datepicker placeholder="From Date" cssStyle="margin:0px 0px 2px 0px;width:88px;height: 20px;"   onchange="getRangeForSpeciality();" cssClass="button"   id="fromDateSpec" name="fromDateSpec" size="20"   readonly="false"   changeMonth="true" changeYear="true"  yearRange="2013:2050" showOn="focus" displayFormat="dd-mm-yy"/>
To:<sj:datepicker placeholder="To Date" cssStyle="margin:0px 0px 2px 0px;width:88px;height: 20px;"  onchange="getRangeForSpeciality();"  cssClass="button"  id="toDateSpec" name="toDateSpec"   size="20"   readonly="false"  changeMonth="true" changeYear="true"  yearRange="2013:2050" showOn="focus" displayFormat="dd-mm-yy"/>
</div>	
 
<div id="weekPickerStatus1" style="float: left;display: none;">
<s:select  
				                               id="fromMonthStatusSpec3"
				                              name="fromMonthStatusSpec3"
				                              list="#{'Jan':'Jan','Feb':'Feb','Mar':'Mar','Apr':'Apr','May':'May','Jun':'Jun','Jul':'Jul','Aug':'Aug','Sep':'Sep','Oct':'Oct','Nov':'Nov','Dec':'Dec'}"
				                                headerValue="%{currentDayMonthValue}"
				                              headerKey="%{currentDayMonthValue}"
				                              cssClass="select"
					                          cssStyle="height:22px;  background: #EDECEC;"
					                           onchange="getRangeForSpeciality();"
				                              >
				                  </s:select>  
				                 
				                  <s:select  
				                               id="fromYearStatusSpec3"
				                              name="fromYearStatusSpec3"
				                                list="#{'2011':'2011','2012':'2012','2013':'2013','2014':'2014','2015':'2015','2016':'2016','2017':'2017','2018':'2018','2019':'2019','2020':'2020'}"
				                           cssClass="select"
					                            headerValue="%{currentYear}"
				                              headerKey="%{currentYear}"
				                                cssStyle="height:22px;  background: #EDECEC;"
					                           onchange="getRangeForSpeciality();"
				                              >
				                  </s:select>  
 
</div>	 
<div id="monthPickerStatus1" style="float: left;display: none;">
 
				                    <s:select  
				                               id="fromYearStatusSpec2"
				                              name="fromYearStatusSpec2"
				                                 list="#{'2011':'2011','2012':'2012','2013':'2013','2014':'2014','2015':'2015','2016':'2016','2017':'2017','2018':'2018','2019':'2019','2020':'2020'}"
				                          headerValue="%{currentYear}"
				                              headerKey="%{currentYear}"
				                               cssClass="select"
					                          cssStyle="height:22px;  background: #EDECEC;"
					                           onchange="getRangeForSpeciality();"
				                              >
				                  </s:select>  
		 
</div>

<div id="yearPickerStatus1" style="float: left;display: none;">
 
				                 From:   <s:select  
				                               id="fromYearStatusSpec4"
				                              name="fromYearStatusSpec4"
				                                 list="#{'2011':'2011','2012':'2012','2013':'2013','2014':'2014','2015':'2015','2016':'2016','2017':'2017','2018':'2018','2019':'2019','2020':'2020'}"
				                             headerValue="%{currentYear}"
				                              headerKey="%{currentYear}"
				                                cssClass="select"
					                          cssStyle="height:22px;  background: #EDECEC;"
					                           onchange="getRangeForSpeciality();"
				                              >
				                  </s:select>  
				                   To:   <s:select  
				                               id="toYearStatusSpec"
				                              name="toYearStatusSpec"
				                               list="#{'2011':'2011','2012':'2012','2013':'2013','2014':'2014','2015':'2015','2016':'2016','2017':'2017','2018':'2018','2019':'2019','2020':'2020'}"
				                                headerValue="%{currentYear}"
				                              headerKey="%{currentYear}"
				                                cssClass="select"
					                          cssStyle="height:22px;  background: #EDECEC;"
					                           onchange="getRangeForSpeciality();"
				                              >
				                  </s:select>  
		 
</div>
 <div style="float:left;"><span style="float:left;margin-left: 20px;margin-right: 8px;font-size: 15px;">Emergency:</span>
		<div style="float: left;" id="button5"></div>
</div>
 <div style="float:left;"><span style="float:left;margin-left: 20px;margin-right: 8px;font-size: 15px;">Time Wise:</span>
		<div style="float: left;" id="button4"></div>
</div>
 <div class="statusTime1" style="float: left;margin-left: 20px;display: none;">From: 
              <sj:datepicker placeholder="From Time" cssStyle="margin:0px 0px 2px 0px;width:85px;height: 20px;"  onchange="getRangeForSpeciality();"  cssClass="button" timepickerOnly="true" timepicker="true" timepickerAmPm="false" id="fromTimeSpec" name="fromTimeSpec" size="20"   readonly="false"   showOn="focus"/>
                  			To:
	           <sj:datepicker placeholder="To Time" cssStyle="margin:0px 0px 2px 0px;width:85px;height: 20px;"  onchange="getRangeForSpeciality();"  cssClass="button" timepickerOnly="true" timepicker="true" timepickerAmPm="false" id="toTimeSpec" name="toTimeSpec" size="20"   readonly="false"   showOn="focus"/>
</div>
</div>
</div> 
 <div id="maximizeSpecDiv" style="width: 99%; "></div>  
        
 </div>
</sj:dialog>



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
<div class="list-icon">
	<div class="head">Dashboard </div>
	<div class="head">
	<img alt="" src="images/forward.png" style="margin-top:10px;margin-right:3px; float: left;">Referral 
	</div>
</div>
  <s:hidden id="emergency" value="false" name="emergency" ></s:hidden>
  <s:hidden id="refDoc" value="toMe" name="refDoc" ></s:hidden>
  <s:hidden id="departmentView" value="%{departmentView}" name="departmentView" ></s:hidden>
<div class="contentdiv-smallNewDash" style="overflow: auto;width: 99.5%;height: 770px;" id="dashDataPart">
<div style="margin: 0px 4px 0px 50px;">
 
<div style="float:left; width:auto; margin: 0px 4px 0px 0px;">
<%
	if(userTpe.equalsIgnoreCase("M") || userTpe.equalsIgnoreCase("H"))
	{%>
				                   <s:select  
				                              id="specialty"
				                              name="specialty"
				                              list="specList"
				                              headerKey="-1"
				                              headerValue="All Speciality"
				                              cssClass="select"
					                          cssStyle="height:22px;  background: #EDECEC;"
					                          onchange="getRange();"
				                              >
				                  </s:select>
				                  <%} %>
				                  <%
	if(userTpe.equalsIgnoreCase("N"))
	{%>
	 
	<s:hidden id="subDept" value="%{subDepartment}"></s:hidden>
	<%} %>
				                   <s:select  
				                               id="dateRangeStatus"
				                              name="dateRangeStatus"
				                              list="#{'Day':'Day Wise','Week':'Week Wise','Month':'Month Wise','Year':'Year Wise','Date':'Date Wise'}"
				                              cssClass="select"
					                          cssStyle="height:22px;  background: #EDECEC;"
					                          onchange="getRange();"
				                              >
				                  </s:select> </div>
<div id="dayPickerStatus" style="float: left;">
<s:select  
				                               id="fromMonthStatus1"
				                              name="fromMonthStatus1"
				                              list="#{'01':'Jan','02':'Feb','03':'Mar','04':'Apr','05':'May','06':'Jun','07':'Jul','08':'Aug','09':'Sep','10':'Oct','11':'Nov','12':'Dec'}"
				                              headerValue="%{currentDayMonthValue}"
				                              headerKey="%{currentDayMonthKey}"
				                              cssClass="select"
					                          cssStyle="height:22px;  background: #EDECEC;"
					                           onchange="getRange();"
				                              >
				                  </s:select>  
				                  <s:select  
				                               id="fromYearStatus1"
				                              name="fromYearStatus1"
				                              list="#{'2011':'2011','2012':'2012','2013':'2013','2014':'2014','2015':'2015','2016':'2016','2017':'2017','2018':'2018','2019':'2019','2020':'2020'}"
				                              headerValue="%{currentYear}"
				                              headerKey="%{currentYear}"
				                              cssClass="select"
					                          cssStyle="height:22px;  background: #EDECEC;"
					                           onchange="getRange();"
				                              >
				                  </s:select>  
 
</div>	 

 <div id="datePickerStatus" style="float: left;display: none;">
From:<sj:datepicker placeholder="From Date" cssStyle="margin:0px 0px 2px 0px;width:88px;height: 20px;"   onchange="getRange();" cssClass="button"   id="fromDate" name="fromDate" size="20"   readonly="false"   changeMonth="true" changeYear="true"  yearRange="2013:2050" showOn="focus" displayFormat="dd-mm-yy"/>
To:<sj:datepicker placeholder="To Date" cssStyle="margin:0px 0px 2px 0px;width:88px;height: 20px;"  onchange="getRange();"  cssClass="button"  id="toDate" name="toDate"   size="20"   readonly="false"  changeMonth="true" changeYear="true"  yearRange="2013:2050" showOn="focus" displayFormat="dd-mm-yy"/>
</div>
 
<div id="weekPickerStatus" style="float: left;display: none;">
<s:select  
				                               id="fromMonthStatus3"
				                              name="fromMonthStatus3"
				                              list="#{'Jan':'Jan','Feb':'Feb','Mar':'Mar','Apr':'Apr','May':'May','Jun':'Jun','Jul':'Jul','Aug':'Aug','Sep':'Sep','Oct':'Oct','Nov':'Nov','Dec':'Dec'}"
				                                headerValue="%{currentDayMonthValue}"
				                              headerKey="%{currentDayMonthValue}"
				                              cssClass="select"
					                          cssStyle="height:22px;  background: #EDECEC;"
					                           onchange="getRange();"
				                              >
				                  </s:select>  
				                 
				                  <s:select  
				                               id="fromYearStatus3"
				                              name="fromYearStatus3"
				                                list="#{'2011':'2011','2012':'2012','2013':'2013','2014':'2014','2015':'2015','2016':'2016','2017':'2017','2018':'2018','2019':'2019','2020':'2020'}"
				                           cssClass="select"
					                            headerValue="%{currentYear}"
				                              headerKey="%{currentYear}"
				                                cssStyle="height:22px;  background: #EDECEC;"
					                           onchange="getRange();"
				                              >
				                  </s:select>  
 
</div>	 
<div id="monthPickerStatus" style="float: left;display: none;">
 
				                    <s:select  
				                               id="fromYearStatus2"
				                              name="fromYearStatus2"
				                                 list="#{'2011':'2011','2012':'2012','2013':'2013','2014':'2014','2015':'2015','2016':'2016','2017':'2017','2018':'2018','2019':'2019','2020':'2020'}"
				                          headerValue="%{currentYear}"
				                              headerKey="%{currentYear}"
				                               cssClass="select"
					                          cssStyle="height:22px;  background: #EDECEC;"
					                           onchange="getRange();"
				                              >
				                  </s:select>  
		 
</div>

<div id="yearPickerStatus" style="float: left;display: none;">
 
				                 From:   <s:select  
				                               id="fromYearStatus4"
				                              name="fromYearStatus4"
				                                 list="#{'2011':'2011','2012':'2012','2013':'2013','2014':'2014','2015':'2015','2016':'2016','2017':'2017','2018':'2018','2019':'2019','2020':'2020'}"
				                             headerValue="%{currentYear}"
				                              headerKey="%{currentYear}"
				                                cssClass="select"
					                          cssStyle="height:22px;  background: #EDECEC;"
					                           onchange="getRange();"
				                              >
				                  </s:select>  
				                   To:   <s:select  
				                               id="toYearStatus"
				                              name="toYearStatus"
				                               list="#{'2011':'2011','2012':'2012','2013':'2013','2014':'2014','2015':'2015','2016':'2016','2017':'2017','2018':'2018','2019':'2019','2020':'2020'}"
				                                headerValue="%{currentYear}"
				                              headerKey="%{currentYear}"
				                                cssClass="select"
					                          cssStyle="height:22px;  background: #EDECEC;"
					                           onchange="getRange();"
				                              >
				                  </s:select>  
		 
</div>
 <div style="float:left;"><span style="float:left;margin-left: 20px;margin-right: 8px;font-size: 15px;">Emergency:</span>
		<div style="float: left;" id="button1"></div>
</div>
 <div style="float:left;"><span style="float:left;margin-left: 20px;margin-right: 8px;font-size: 15px;">Time Wise:</span>
		<div style="float: left;" id="button2"></div>
</div>
 <div class="statusTime" style="float: left;margin-left: 20px;display: none;">From: 
              <sj:datepicker placeholder="From Time" cssStyle="margin:0px 0px 2px 0px;width:85px;height: 20px;"  onchange="getRange();"  cssClass="button" timepickerOnly="true" timepicker="true" timepickerAmPm="false" id="fromTime" name="fromTime" size="20"   readonly="false"   showOn="focus"/>
                  			To:
	           <sj:datepicker placeholder="To Time" cssStyle="margin:0px 0px 2px 0px;width:85px;height: 20px;"  onchange="getRange();"  cssClass="button" timepickerOnly="true" timepicker="true" timepickerAmPm="false" id="toTime" name="toTime" size="20"   readonly="false"   showOn="focus"/>
</div>
<s:if test="%{departmentView=='DepartmentView'}">
<div style="float:left;"><span style="float:left;margin-left: 20px;margin-right: 8px;font-size: 15px;">Referred:</span>
		<s:radio  id="referralDoc"   name="referralDoc"  list="#{'1':'To Me','2':'By Me'}" value="1" theme="simple"  onchange="ChangeRefferedByMeToMe(this.value)"/>
</div>
</s:if>
	<s:if test="%{locationWise=='LocationManagerView'}">
		<td>		
		 <div style="float: left">
	 	 <span style="float:left;margin-left: 20px;margin-right: 8px;font-size: 15px;">Location Wise:</span><input type="checkbox" style= "width: 19px; height: 19px;" title="Location Wise Referral" onchange="getRange();" id="locationView"/>
		</div>
			</td>	
			</s:if>
</div>




 <!-- Referral Order Type Start -->
<div class="contentdiv-smallNewDash" style="overflow: hidden;width: 99.5%;height: 354px;" id="hod_level_status">
<div id="lable_graph">
 <div class="referralOrderPanel" style="float: center;">
<div class="headdingtest">Referral Order Wise</div>
</div> 
  
  
 <div   style="float: right;">
<ul class="nav_links">
	<li ><a href="#">
	<span class="threeLiner" ></span>
		 </a> 
 	<div class="dropdown profile_dropdown">
	<div class="arrow_dropdown">&nbsp;</div>
	<div class="one_column" >
	<ul>
		 	<li id="threelinerDataGraph"><a href="#" onclick="getshowData('Order');">
		<span class="glyphicon glyphicon-list-alt" aria-hidden="true" style="margin-right:4px;"></span>View Data</a></li>
		<li id="threelinerDataGraph"><a href="#" onclick="getshowReferralCounter('Order');">
		<span class="glyphicon glyphicon-equalizer" aria-hidden="true" style="margin-right:4px;"></span>View Graph</a></li>
		<%
	if(userTpe.equalsIgnoreCase("M") || userTpe.equalsIgnoreCase("H"))
	{%>
	 <li id="threelinerDataGraph"><a href="#" onclick="beforeSpecialityAnalysis()">
		<span class="glyphicon glyphicon-list-alt" aria-hidden="true" style="margin-right:4px;"></span>Referral Speciality</a></li>
		<%} %>
	</ul>
	</div>
	<div class="clear"></div>
	</div>
	</li>
</ul>
</div>

 
<div id='referralOrder' style="width: 99%; height: 310px" align="center"></div>
</div>
  
</div>  

<!-- OrderType wise Ticket Status For Particular department Div End -->

 
 
 
 
 
 
 
 
 
         
  <!-- Referral Status and Attended By Start -->
 
<div class="contentdiv-smallNewDash" style="overflow: hidden;width: 99.5%;height: 354px;" id="hod_catg_status">
<div id="lable_graph">

<div class="referralStatusPanel" style="float: center;">
  <div class="headdingtest">Referral Status Wise</div>
</div>
<div class="referralAttendedPanel" style="float: center;display: none;">
  <div class="headdingtest">Referral Attended By</div>
</div>
  
  
<div  style="float: right;">
 		 	<button  id="refStatus" type="button" class="btn btn-default btn-sm" onclick="getShowHideDiv('Status');getRange();">
			  <span aria-hidden="true"></span> Referral Status
			</button>
			<button  id="refAtnd" type="button" class="btn btn-default btn-sm" onclick="getShowHideDiv('Attended');getRange();">
			  <span  aria-hidden="true"></span>Referral Attended By
			</button>
			 
 <div class="referralStatusPanel" style="  float: right;">
			<ul class="nav_links">
	<li ><a href="#">
	<span class="threeLiner" ></span>
		 </a> 
 	<div class="dropdown profile_dropdown">
	<div class="arrow_dropdown">&nbsp;</div>
	<div class="one_column" >
	<ul>
		 	<li id="threelinerDataGraph"><a href="#" onclick="getshowData('Status');">
		<span class="glyphicon glyphicon-list-alt" aria-hidden="true" style="margin-right:4px;"></span>View Data</a></li>
		<li id="threelinerDataGraph"><a href="#" onclick=" getshowReferralCounter('Status');">
		<span class="glyphicon glyphicon-equalizer" aria-hidden="true" style="margin-right:4px;"></span>View Bar Graph</a></li>
	 
	</ul>
	</div>
	<div class="clear"></div>
	</div>
	</li>
</ul>
</div>

<div class="referralAttendedPanel" style="  float: right;display: none;">
			<ul class="nav_links">
	<li ><a href="#">
	<span class="threeLiner" ></span>
		 </a> 
 	<div class="dropdown profile_dropdown">
	<div class="arrow_dropdown">&nbsp;</div>
	<div class="one_column" >
	<ul>
		 	<li id="threelinerDataGraph"><a href="#" onclick="getshowData('Attended');">
		<span class="glyphicon glyphicon-list-alt" aria-hidden="true" style="margin-right:4px;"></span>View Data</a></li>
		<li id="threelinerDataGraph"><a href="#" onclick=" getshowReferralCounter('Attended');">
		<span class="glyphicon glyphicon-equalizer" aria-hidden="true" style="margin-right:4px;"></span>View Bar Graph</a></li>
	<%-- 	<li id="threelinerDataGraph"><a href="#" onclick="getshowData('Speciality');">
		<span class="glyphicon glyphicon-list-alt" aria-hidden="true" style="margin-right:4px;"></span>Data For Speciality</a></li>
	 --%> 
	</ul>
	</div>
	<div class="clear"></div>
	</div>
	</li>
</ul>
 </div>
</div>
 
 
  
  
<div id="referralStatus"style="width: 99%; height: 310px"></div>
</div>
  
</div>     
</div>
</body>
</html>