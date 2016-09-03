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
<script type="text/javascript" src="<s:url value="/js/dashboard/dashboard.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/dashboard/dashboardbar.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/dashboard/dashboardBeforeChart.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/dashboard/dashboardTrend.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/dashboard/LocationHDM/dashboardbarLoc.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/dashboard/LocationHDM/dashboardLoc.js"/>"></script>
<link  type="text/css" href="amcharts/plugins/export/export.css" rel="stylesheet">
<link rel="stylesheet" href="bootstrap-3.3.4-dist/css/bootstrap.min.css">
<link rel="stylesheet" href="bootstrap-3.3.4-dist/css/bootstrap-theme.min.css">
<%--  <script src="js/bootstrap.js"></script> 
 --%>
     
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
    document.getElementById('sdate1').value=$("#hfromDate").val();
    document.getElementById('edate1').value=$("#htoDate").val();
    $('#limit option').remove();
    $('#limit').append($("<option></option>").attr("value",-1).text("#Count"));
    for(var i=0;i<=200;i=i+5){
    	 $('#limit').append($("<option></option>").attr("value",i).text(i));
    }
    if($("#dataFor").val()=="H")
	{
		dataForDept="subdept";
		filterFlag="H";
	}
});

function getReopenMain(val)
{
	if(val){getReopen(true);}else{ getReopen(false);}
}
function getEscalatedMain(val){
	if(val){getEscalated(true);}else{ getEscalated(false);}
}

function getReopenLevel()
{
	showLeveStackedBar('levelChart', '', '', '', 'All', '#09A1A4');
}
function getEscalatedLevel()
{
	showLeveStackedBar('levelChart', '', '', '', 'All', '#09A1A4');
}

tabData();
function chartTab()
{
	StatckedChartStatus('jqxChart', '', '', '', 'All', '#09A1A4');
	showLeveStackedBar('levelChart', '', '', '', 'All', '#09A1A4');
	//showPieCateg('CategChart');
	StatckedChartStatusLoc12(measure1);
}

function showTrend()
{
	//maximizeDiv4Analytics(500, 1250, "Trend For All Department", "");
	getChartForTrend('maximizeDataDiv');
}

function tabData()
{
	chartTab();
}
function getBeforChartForTrend(dataFor){
	$("#trendFor").empty();
	$("#trendFor").html(dataFor);
	 $.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Dashboard_Pages/maximizeBlock.jsp",
		    success : function(data) 
		    {
		    	
		$("#trendChart").html(data);
		    },
		   error: function() {
		        alert("error");
		    }
		 }); 
}
function getDataForAllBoard()
{
	

	 if($("#dataFor").val()=="H")
	{
			dataForDept='subdept';
			filterFlag='H';
	}
	else
	{
		dataForDept='dept';
		filterFlag='M';
	}	 
	status1='total';
	title1='All';
	color1='#09A1A4';
	status2='total';
	title2='All';
	color2='#0CD172';
	StatckedChartStatus('jqxChart', '', '', '', 'All', '#09A1A4');
	showLeveStackedBar('levelChart', '', '', '', 'All', '#09A1A4');
	//showPieCateg('CategChart');
	StatckedChartStatusLoc12(measure1);
	
}
function getsubdeptChart(deptId,deptName)
{
	beforeMaximiseFilter('Ticket Count Status For: '+deptName,400,900,'Ticket Count Status','G',deptId,"H");
}
function getTicketByDept(deptId)
{
	if(maxDivId1=='1stStackedBar')
	 {
		StatckedChartStatus('jqxChart','','','','Resolve','#5cb85c');
	 }
	/* else if(maxDivId1=='1stColumnBar')
	 {
		 Column2AxesChartStatus('jqxChart','',deptId);
	 }
	 else if(maxDivId1=='1stLine')
	 {
		 lineStatus('jqxChart','',deptId);
	 }
	 else if(maxDivId1=='1stBubble')
	 {
		 bubbleStatus('jqxChart','',deptId);
	 } */
	 else if(maxDivId1=='1stTable')
	 {
		 showData('jqxChart','');
	 }
}

function getLevelByDept(deptId)
{
	if(maxDivId2=='2ndStackedBar')
	 {
		showLeveStackedBar('levelChart','','','','All','#09A1A4');
		
	 }
	/* else if(maxDivId2=='2ndColumn2Axes')
	 {
		 showLevelColumn2Axes('levelChart','',deptId);
	 }
	 else if(maxDivId2=='LineLevel')
	 {
		 lineLevel('levelChart','',deptId);
	 }
	 else if(maxDivId2=='BubbleLevel')
	 {
		 bubbleLevel('levelChart','',deptId);
	 } */else if(maxDivId2=='2ndTable')
	 { 
		 showData('levelChart','');
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
	$("#"+setDiv).html(firstHead+' '+str+' Department');
}
function statusDetail(cellvalue, options, rowObject) 
{
	return "<a href='#' title='View Data' onClick='statusDetailOnClick("+rowObject.id+")'><font color='blue'>"+cellvalue+"</font></a>";
}

function statusDetailOnClick(complainId) 
{
	getPrintData(complainId);
	 
}
function getPrintData(feedid)

 {
$("#printSelect1").dialog('open');
$("#printSelect1").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
$("#printSelect1").load("/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Lodge_Feedback/printTicketInfo.action?feedId="+feedid );
}	

</script>

<s:url var="chartDataUrl" action="json-chart-data"/>

<title>HDM Dashboard</title>
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
		background:#F04E2F url("images/ui-bg_highlight-soft_15_F04E2F_1x100.png") 50% 50% repeat-x; 
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
			id="printSelect1"  
			autoOpen="false"  
            closeOnEscape="true" 
            modal="true" 
            title="Print Ticket" 
            width="900" 
            height="500" 
            showEffect="slide" 
            hideEffect="explode" >

</sj:dialog>
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

<sj:dialog
          id="maxmizeCatgDialog"
          showEffect="slide"
          hideEffect="explode"
          autoOpen="false"
          modal="true"
          width="900"
          height="400"
          closeTopics="closeEffectDialog"
          >
         <%--  <button id="locationbttn" type="button" class="btn btn-default btn-sm" onclick="categAnalysis('floor');">
			  <span class="glyphicon glyphicon-globe" aria-hidden="true"></span> Location
			</button>
			<button  id="timebttn" type="button" class="btn btn-default btn-sm" onclick="categAnalysis('time');">
			  <span class="glyphicon glyphicon-time " aria-hidden="true"></span> Time
			</button> --%>
			
			<div style="float: right;">
			
			Escalated: <input type="checkbox" onclick="getEscCat(this.checked);"/>
			Reopen: <input type="checkbox" onclick="getReoCat(this.checked);"/>
			<s:select  
							                              id="limit"
							                              name="limit"
							                              list="{'No data'}"
							                               headerKey="-1"
							                              headerValue="Count" 
							                              cssClass="select"
								                          cssStyle="width:17%;height:22px;  background: #EDECEC;" 
								                          onchange=""
							                              >
							                  </s:select>
			
  <sj:datepicker id="sdate1"   cssClass="button" cssStyle="width: 70px;border: 1px solid #e2e9ef;border-top: 1px solid #cbd3e2;padding: 1px;font-size: 12px;outline: medium none;height: 18px;text-align: center;" readonly="true"  size="20" changeMonth="true" changeYear="true"  yearRange="2013:2050" showOn="focus" displayFormat="dd-mm-yy" Placeholder="Select From Date" onchange="" value=" "/>
 <b> To</b>
 <sj:datepicker id="edate1"  cssClass="button" cssStyle="width: 70px;border: 1px solid #e2e9ef;border-top: 1px solid #cbd3e2;padding: 1px;font-size: 12px;outline: medium none;height: 18px;text-align: center;" readonly="true"  size="20" changeMonth="true" changeYear="true"  yearRange="2013:2050" showOn="focus" displayFormat="dd-mm-yy" Placeholder="Select TO Date" onchange="" value=" " />
			<button  id="bttnGet" type="button" class="btn btn-default btn-xs" onclick="categAnalysis('');">
			  <span class="glyphicon glyphicon-send " aria-hidden="true"></span> GO!
			</button>
			</div>
          <% if(userTpe.equalsIgnoreCase("M"))
				{%>
						<div style="float:right; width:auto; margin: 0px 4px 0px 0px;"><s:select  
							                              id="deptnameCat"
							                              name="deptnameCat"
							                              list="serviceDeptList"
							                               headerKey="-1"
							                              headerValue="All Department" 
							                              cssClass="select"
								                          cssStyle="width:100%;height:22px;  background: #EDECEC;" 
								                          onchange=""
							                              >
							                  </s:select></div>
							                  <% } else { %> 
<s:hidden id="deptnameCat" value="%{dept_id}"></s:hidden>

<%} %>
<s:select  
							                              id="dataMeasure"
							                              name="dataMeasure"
							                              list="#{'floor':'Floor','time':'Time'}"
							                              cssClass="select"
								                          cssStyle="width:12%;height:22px;  background: #EDECEC;float:left;" 
								                          onchange=""
							                              >
							                  </s:select>
							                  
							                  <button  type="button" class="btn btn-default btn-xs" onclick="download();">
			  <span class="glyphicon glyphicon-download-alt " aria-hidden="true"></span> Save
			</button>
          <div id="maximizeCatgDiv">
			    
          </div>
</sj:dialog>



<s:hidden id="dataFor" value="%{loginType}"/>
<div class="middle-content">
<div class="list-icon">
	<div class="head">Dashboard </div>
	<div class="head">
	<img alt="" src="images/forward.png" style="margin-top:10px;margin-right:3px; float: left;">Helpdesk(OCC)
	</div>
</div>
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
<div class="contentdiv-smallNewDash" style="overflow: auto;width: 58%" id="counter_dash">
<span id="headerIdHidden" style="display:none;"><s:property value="%{headerValue}"/></span>
<div class="headdingtest">Ticket Counter Status For <span id="headerId" ><s:property value="%{headerValue}"/></span></div>
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
		<li><a href="#" onclick="beforeMaximise('ticket_graph',400,900,'Ticket Count Status','G')" >
		<span class="glyphicon glyphicon-fullscreen" aria-hidden="true" style="margin-right:4px;"></span>Maximize</a></li>
	
		<li id="threelinerDataGraph"><a href="#" onclick="showData('jqxChart','','');">
		<span class="glyphicon glyphicon-list-alt" aria-hidden="true" style="margin-right:4px;"></span>View Data</a></li>
		<li id="threelinerDataGraph"><a href="#" onclick="StatckedChartStatus('jqxChart',filterFlag,filterDeptId,'','All','#09A1A4');">
		<span class="glyphicon glyphicon-equalizer" aria-hidden="true" style="margin-right:4px;"></span>View Bar Graph</a></li>
		<%-- <li id="threelinerDataGraph"><a href="#" onclick="StatckedChartStatus('jqxChart','','','','Resolve','#5cb85c','stack');">
		<span class="glyphicon glyphicon-stats" aria-hidden="true" style="margin-right:4px;"></span>View Stack Graph</a></li>
 --%>		<%-- <li id="threelinerDataGraph"><a href="#" onclick="showPieStatus('jqxChart','Pending','Pending Ticket Status','For All Departments');">
		<span class="glyphicon glyphicon-adjust" aria-hidden="true" style="margin-right:4px;"></span>View Pie Graph</a></li> --%>
		<li id="threelinerDataGraph"><a href="#" onclick="showTrend();">
		<span class="glyphicon glyphicon-road" aria-hidden="true" style="margin-right:4px;"></span>Show Trend</a></li>
		
	</ul>
	</div>
	<div class="clear"></div>
	</div>
	</li>
</ul>
</div>
<!-- <div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="refresh('3rdBlock')"><img src="images/refresh-icon.jpg" width="15" height="15" alt="Refresh" title="Refresh" /></a></div> -->
<!-- <div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="beforeMaximise('ticket_graph',400,900,'Ticket Count Status','G')"><img src="images/maximize-icon.jpg" width="15" height="15" alt="Maximize" title="Maximize" /></a></div>
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="bubbleStatus('jqxChart','','')"><img src="images/chart_bubble.png" width="20" height="15" alt="Show Bubble Chart" title="Show Bubble Chart" border="1" /></a></div>
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="lineStatus('jqxChart','','')"><img src="images/line-graph.jpg" width="15" height="15" alt="Get Data" title="Show Line Chart" /></a></div>
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="showPieStatus('jqxChart','Pending','Pending Ticket Status','For All Departments')"><img src="images/pie_chart_icon.jpg" width="15" height="15" alt="Get Data" title="Get Pie Chart" /></a></div>
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="Column2AxesChartStatus('jqxChart','','')"><img src="images/Column-Chart-2Axes.png" width="20" height="15" alt="Show Bar Graph" title="Show Column Chart 2 Axes" border="1" /></a></div>
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="StatckedChartStatus('jqxChart','','')"><img src="images/chart-bar-stacked-icon.png" width="20" height="15" alt="Show Bar Graph 2 Axes" title="Show Stacked-Column Chart" border="0" /></a></div>
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="showData('jqxChart','','')"><img src="images/table.jpg" width="18" height="18" alt="Show Counters" title="Show Counters" /></a></div>
<div id="btnSave" style="float:right; width:auto; padding:0px 4px 0 0;" ><a href="#" ><img src="images/download.png" width="18" height="18" alt="Show Counters" title="Download JPG" /></a></div> -->

<%-- <% if(userTpe.equalsIgnoreCase("M"))
	{%>
<div style="float:right; width:auto; padding:0px 4px 0 0;">
	
<s:select  
				                              id="deptname"
				                              name="deptname"
				                              list="serviceDeptList"
				                              headerKey="-1"
				                              headerValue="All Department" 
				                              cssClass="select"
					                          cssStyle="width:100%;height:22px;  background: #EDECEC;"
					                          onchange="getTicketByDept(this.value);"
				                              >
				                  </s:select>
				                  
				                  </div>
<% }else { %> 
<s:hidden id="deptname" value="%{dept_id}"></s:hidden>
<%} %> --%>
<div class="clear"></div>
  
<div id='jqxChart' style="width: 100%; height: 217px;overflow: auto !important;"  ></div>
 
<div class="statusPanel1">
<a href="#"><span class="label label-default" style="background-color: #09A1A4;" id="statusAll" onclick="StatckedChartStatus('jqxChart','','','total','All','#09A1A4');">All</span></a>
<a href="#"><span class="label label-success" id="statusRS" onclick="StatckedChartStatus('jqxChart','','','Resolved','Resolve','#5cb85c');">Resolve</span></a>
<a href="#"><span class="label label-danger" style="background:#F04E2F;" id="statusPN" onclick="StatckedChartStatus('jqxChart','','','Pending','Pending','#F04E2F');">Pending</span></a>
<a href="#"><span class="label label-warning" id="statusPA" onclick="StatckedChartStatus('jqxChart','','','Snooze','Park','#f0ad4e');">Park</span></a>
<a href="#"><span class="label label-info" id="statusRE" onclick="StatckedChartStatus('jqxChart','','','Reopened','Re-Open','#5bc0de');">Re-Open</span></a>
<a href="#"><span class="label label-primary" id="statusRA" onclick="StatckedChartStatus('jqxChart','','','Reassigned','Re-Assign','#428bca');">Re-Assign</span></a>
<a href="#"><span class="label label-default" id="statusIG" onclick="StatckedChartStatus('jqxChart','','','Ignore','Ignore','#777');">Ignore</span></a>
</div>
<%-- <div class="statusPanelPie">
<a href="#"><span class="label label-success" id="statusRS" onclick="showPieStatus('jqxChart','Resolved','Resolve Ticket Status','For All Departments');">Resolve</span></a>
<a href="#"><span class="label label-danger" id="statusPN" style="background:#F04E2F;"  onclick="showPieStatus('jqxChart','Pending','Pending Ticket Status','For All Departments');">Pending</span></a>
<a href="#"><span class="label label-warning" id="statusPA" onclick="showPieStatus('jqxChart','Snooze','Park Ticket Status','For All Departments');">Park</span></a>
<a href="#"><span class="label label-info" id="statusRE" onclick="showPieStatus('jqxChart','Reopened','Re-Open Ticket Status','For All Departments');">Re-Open</span></a>
<a href="#"><span class="label label-primary" id="statusRA" onclick="showPieStatus('jqxChart','Reassigned','Re-Assign Ticket Status','For All Departments');">Re-Assign</span></a>
<a href="#"><span class="label label-default" id="statusIG" onclick="showPieStatus('jqxChart','Ignore','Ignore Ticket Status','For All Departments');">Ignore</span></a>
</div> --%>
</div>
<!-- Ticket Counter By Status Dashboard End -->


<!-- Level wise Ticket Status For Particular department Div Start -->
<div class="contentdiv-smallNewDash" style="overflow: hidden;width: 40%;" id="hod_level_status">
<div id="lable_graph">
<div style="float: left;margin: 1%;">
			Escalated: <input type="checkbox" onchange="getEscalatedLevel();" id="escLevel"/>
			Reopen: <input type="checkbox" onchange="getReopenLevel();" id="reopenLevel"/>
		</div>
<div class="headdingtest" id="levelDiv">Level Wise Status For <s:property value="%{headerValue}"/></div>
<!-- <div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="refresh('3rdBlock')"><img src="images/refresh-icon.jpg" width="15" height="15" alt="Refresh" title="Refresh" /></a></div> -->
<!-- <div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="beforeMaximise1('level_graph',400,900,'Level wise Analysis','G')"><img src="images/maximize-icon.jpg" width="15" height="15" alt="Maximize" title="Maximize" /></a></div>
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="bubbleLevel('levelChart','','')"><img src="images/chart_bubble.png" width="20" height="15" alt="Show Bubble Chart" title="Show Bubble Chart" border="1" /></a></div>
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="lineLevel('levelChart','','')"><img src="images/line-graph.jpg" width="15" height="15" alt="Get Data" title="Show Line Chart" /></a></div>
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="showPieLevel('levelChart','Pending','Pending Ticket Status','For All Levels')"><img src="images/pie_chart_icon.jpg" width="15" height="15" alt="Get Data" title="Get Pie Chart" /></a></div>
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="showLevelColumn2Axes('levelChart','','')"><img src="images/Column-Chart-2Axes.png" width="20" height="15" alt="Show Bar Graph" title="Show Column Chart 2 Axes" border="1" /></a></div>
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="showLeveStackedBar('levelChart','','')"><img src="images/chart-bar-stacked-icon.png" width="20" height="15" alt="Show Bar Graph 2 Axes" title="Show Stacked-Column Chart" border="0" /></a></div>
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="showData('levelChart','')"><img src="images/table.jpg" width="18" height="18" alt="Show Counters" title="Show Counters" /></a></div>
<div id="btnSave1" style="float:right; width:auto; padding:0px 4px 0 0;" ><a href="#" ><img src="images/download.png" width="18" height="18" alt="Show Counters" title="Download JPG" /></a></div> -->

<ul class="nav_links">
	<li><a href="#">
	<span class="threeLiner" style="margin:0px;" ></span>
		 </a> 
			
			
	<div class="dropdown profile_dropdown">
	<div class="arrow_dropdown">&nbsp;</div>
	<div class="one_column" >
	<ul>
		<li><a href="#" onclick="beforeMaximise1('level_graph',400,900,'Level wise Analysis','G')" >
		<span class="glyphicon glyphicon-fullscreen" aria-hidden="true" style="margin-right:4px;"></span>Maximize</a></li>
		
	
		<li id="threelinerDataGraph1"><a href="#" onclick="showData('levelChart','')">
		<span class="glyphicon glyphicon-list-alt" aria-hidden="true" style="margin-right:4px;"></span>View Data</a></li>
		
		<li id="threelinerDataGraph"><a href="#" onclick="showLeveStackedBar('levelChart','','','','All','#09A1A4');">
		<span class="glyphicon glyphicon-equalizer" aria-hidden="true" style="margin-right:4px;"></span>View Bar Graph</a></li>
	
		<%-- <li id="threelinerDataGraph"><a href="#" onclick="showPieLevel('levelChart','Pending','Pending Ticket Status','For All Levels');">
		<span class="glyphicon glyphicon-adjust" aria-hidden="true" style="margin-right:4px;"></span>View Pie Graph</a></li> --%>
	
		
	</ul>
	</div>
	<div class="clear"></div>
	</div>
	</li>
</ul>
<% if(userTpe.equalsIgnoreCase("M"))
	{%>
<div style="float:right; width:auto; margin: 0px 4px 0px 0px;"><s:select  
				                              id="deptname1"
				                              name="deptname1"
				                              list="serviceDeptList"
				                              headerKey="-1"
				                              headerValue="All Department" 
				                              cssClass="select"
					                          cssStyle="width:100%;height:22px;  background: #EDECEC;"
					                          onchange="getLevelByDept(this.value);changeHeader('deptname1','levelDiv','Levelwise Status For ');"
				                              >
				                  </s:select></div>
				                  <% } else { %> 
<s:hidden id="deptname1" value="%{dept_id}"></s:hidden>
<%} %>
<div class="clear"></div>

<div id='levelChart' style="width: 100%; height: 217px" align="center"></div>

</div>
<div class="levelPanel">
<a href="#"><span class="label label-default" style="background-color: #09A1A4;" id="statusAll" onclick="showLeveStackedBar('levelChart','','','total','All','#09A1A4');">All</span></a>
<a href="#"><span class="label label-success" id="statusRS" onclick="showLeveStackedBar('levelChart','','','Resolved','Resolve','#5cb85c');">Resolve</span></a>
<a href="#"><span class="label label-danger" style="background:#F04E2F;"  id="statusPN" onclick="showLeveStackedBar('levelChart','','','Pending','Pending','#F04E2F');">Pending</span></a>
<a href="#"><span class="label label-warning" id="statusPA" onclick="showLeveStackedBar('levelChart','','','Snooze','Park','#f0ad4e');">Park</span></a>
<a href="#"><span class="label label-info" id="statusRE" onclick="showLeveStackedBar('levelChart','','','Reopened','Re-Open','#5bc0de');">Re-Open</span></a>
<a href="#"><span class="label label-primary" id="statusRA" onclick="showLeveStackedBar('levelChart','','','Reassigned','Re-Assign','#428bca');">Re-Assign</span></a>
<a href="#"><span class="label label-default" id="statusIG" onclick="showLeveStackedBar('levelChart','','','Ignore','Ignore','#777');">Ignore</span></a>
</div>
<%-- <div class="levelPanelPie">
<a href="#"><span class="label label-success" id="statusRS" onclick="showPieLevel('levelChart','Resolved','Resolve Ticket Status','For All Levels')">Resolve</span></a>
<a href="#"><span class="label label-danger" style="background:#F04E2F;" id="statusPN" onclick="showPieLevel('levelChart','Pending','Pending Ticket Status','For All Levels');">Pending</span></a>
<a href="#"><span class="label label-warning" id="statusPA" onclick="showPieLevel('levelChart','Snooze','Park Ticket Status','For All Departments');">Park</span></a>
<a href="#"><span class="label label-info" id="statusRE" onclick="showPieLevel('levelChart','Reopened','Re-Open Ticket Status','For All Departments');">Re-Open</span></a>
<a href="#"><span class="label label-primary" id="statusRA" onclick="showPieLevel('levelChart','Reassigned','Re-Assign Ticket Status','For All Departments');">Re-Assign</span></a>
<a href="#"><span class="label label-default" id="statusIG" onclick="showPieLevel('levelChart','Ignore','Ignore Ticket Status','For All Departments');">Ignore</span></a>
</div> --%>

</div>

<!-- Level wise Ticket Status For Particular department Div End -->


<!-- Category wise Ticket Status Div Start for particular Department-->
<%-- <div class="contentdiv-smallNewDash" style="overflow: auto;" id="hod_catg_status">


<!-- Category Dashboard Start -->
<div id="catg_hod_graph">
<div class="headdingtest">Subcategory Analysis For <s:property value="%{headerValue}"/></div>
<!-- <div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="refresh('3rdBlock')"><img src="images/refresh-icon.jpg" width="15" height="15" alt="Refresh" title="Refresh" /></a></div> -->
<!-- <div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="beforeMaximise2('catg_graph',500,900,'Category Analysis','G')"><img src="images/maximize-icon.jpg" width="15" height="15" alt="Maximize" title="Maximize" /></a></div>
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="DoughnutCateg('CategChart')"><img src="images/donut-chart.png" width="15" height="15" alt="Get Data" title="Show Doughnut Chart" /></a></div>
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="showPieCateg('CategChart')"><img src="images/pie_chart_icon.jpg" width="15" height="15" alt="Get Data" title="Get Pie Chart" /></a></div>
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="showData('CategChart','')"><img src="images/table.jpg" width="18" height="18" alt="Show Counters" title="Show Counters" /></a></div>
<div id="btnSave2" style="float:right; width:auto; padding:0px 4px 0 0;" ><a href="#" ><img src="images/download.png" width="18" height="18" alt="Show Counters" title="Download JPG" /></a></div> -->
<ul class="nav_links">
	<li><a href="#">
	<span class="threeLiner" style="margin:5px;" ></span>
		 </a> 
			
			
	<div class="dropdown profile_dropdown">
	<div class="arrow_dropdown">&nbsp;</div>
	<div class="one_column" >
	<ul>
		<li><a href="#" onclick="beforeMaximise2('catg_graph',650,1200,'Subcategory Analysis','G')" >
		<span class="glyphicon glyphicon-fullscreen" aria-hidden="true" style="margin-right:4px;"></span>Maximize</a></li>
		
		<li id="threelinerDataGraph1"><a href="#" onclick="showData('CategChart','')">
		<span class="glyphicon glyphicon-list-alt" aria-hidden="true" style="margin-right:4px;"></span>View Data</a></li>
		
		<li id="threelinerDataGraph"><a href="#" onclick="showBarCatg('CategChart');">
		<span class="glyphicon glyphicon-equalizer" aria-hidden="true" style="margin-right:4px;"></span>View Bar Graph</a></li>
		
		<li id="threelinerDataGraph"><a href="#" onclick="showPieCateg('CategChart')">
		<span class="glyphicon glyphicon-adjust" aria-hidden="true" style="margin-right:4px;"></span>View Pie Graph</a></li>
	
		<li id="threelinerDataGraph"><a href="#" onclick="DoughnutCateg('CategChart')">
		<span class="glyphicon glyphicon-adjust" aria-hidden="true" style="margin-right:4px;"></span>View Doughnut</a></li>
		
	</ul>
	
	</div>
	<div class="clear"></div>
	</div>
	</li>
</ul>
<% if(userTpe.equalsIgnoreCase("M"))
	{%>
<div style="float:right; width:auto; margin: 5px 4px 0px 0px;"><s:select  
				                              id="deptnameCateg"
				                              name="deptnameCateg"
				                              list="serviceDeptList"
				                              headerKey="-1"
				                              headerValue="All Department" 
				                              cssClass="select"
					                          cssStyle="width:100%;height:22px;  background: #EDECEC;"
					                          onchange="onCategDropDown();"
				                              >
				                  </s:select></div>
				                  <% } else { %> 
<s:hidden id="deptnameCateg" value="%{dept_id}"></s:hidden>
<%} %>
<div class="clear"></div>
<div id='CategChart' style="width: 100%; height: 217px;overflow: auto;" ></div>
</div>
<!-- Category wise Ticket Status Div End for particular Department-->
</div> --%>
         
  <!-- location -->
  <div class="contentdiv-smallNewDash" style="overflow: hidden;width: 99%;height: 400px;" id="hod_catg_status">
		<div id="counterChart">
		
		<div style="  float: left;margin: 1%;">
			<button id="locationbttn" type="button" class="btn btn-default btn-sm" onclick="StatckedChartStatusLoc12('Location');">
			  <span class="glyphicon glyphicon-globe" aria-hidden="true"></span> Location
			</button>
			<button  id="timebttn" type="button" class="btn btn-default btn-sm" onclick="StatckedChartStatusLoc12('time');">
			  <span class="glyphicon glyphicon-time " aria-hidden="true"></span> Time
			</button>
			<button  id="staffbttn"  type="button" class="btn btn-default btn-sm" onclick="StatckedChartStatusLoc12('staff');">
			  <span class="glyphicon glyphicon-user " aria-hidden="true"></span> Staff
			</button>
			Escalated: <input type="checkbox" onclick="getEscalatedMain(this.checked);"/>
			Reopen: <input type="checkbox" onclick="getReopenMain(this.checked);"/>
			
			<button  id="backbttn" style="display: none;"  type="button" class="btn btn-default btn-sm" >
			  <span class="glyphicon glyphicon-arrow-left" aria-hidden="true"></span> Back
			</button>
			
			
			
		</div>
<div class="headdingtest" id="titleChart">Quick Status For All Departments</div>
<ul class="nav_links">
	<li style="top: -6px;"><a href="#">
	<span class="threeLiner" style="margin:5px;" ></span>
		 </a> 
			
			
	<div class="dropdown profile_dropdown">
	<div class="arrow_dropdown">&nbsp;</div>
	<div class="one_column" >
	<ul>
		
	
		<li id="threelinerDataGraph1"><a href="#" onclick="showDataLoc('mytest')">
		<span class="glyphicon glyphicon-list-alt" aria-hidden="true" style="margin-right:4px;"></span>View Data</a></li>
		<li id="threelinerDataGraph1"><a href="#" onclick="beforeCategAnalysis()">
		<span class="glyphicon glyphicon-list-alt" aria-hidden="true" style="margin-right:4px;"></span>Category Analysis</a></li>
		
		
		
	</ul>
	</div>
	<div class="clear"></div>
	</div>
	</li>
</ul>

<% if(userTpe.equalsIgnoreCase("M"))
	{%>
<div style="float:right; width:auto; margin: 0px 4px 0px 0px;"><s:select  
				                              id="deptnameLoc"
				                              name="deptnameLoc"
				                              list="serviceDeptList"
				                              headerKey="-1"
				                              headerValue="All Department" 
				                              cssClass="select"
					                          cssStyle="width:100%;height:22px;  background: #EDECEC;" 
					                          onchange="StatckedChartStatusLoc12Before(measure1);changeHeader('deptnameLoc','titleChart','Quick Status For ');"
				                              >
				                  </s:select></div>
				                  <% } else { %> 
<s:hidden id="deptnameLoc" value="%{dept_id}"></s:hidden>
<s:hidden id="dept" value="%{dept}"></s:hidden>
<%} %>
<div class="clear"></div>
     <div style="float: left;  margin-left: 1%;" id='jqxdropdownlist'>
</div>
<div id="mytest"style="width: 99%; height: 300px;"></div>
</div>
</div>   

  
            </div>
        </div>
    
       
       
  


</div>

</body>
</html>