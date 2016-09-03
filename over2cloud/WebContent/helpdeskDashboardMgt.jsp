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
<script type="text/javascript" src="<s:url value="/js/dashboard/mgtHDM/dashboard.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/dashboard/mgtHDM/dashboardbar.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/dashboard/mgtHDM/dashboardBeforeChart.js"/>"></script>
<link  type="text/css" href="amcharts/plugins/export/export.css" rel="stylesheet">
<link rel="stylesheet" href="bootstrap-3.3.4-dist/css/bootstrap.min.css">
<link rel="stylesheet" href="bootstrap-3.3.4-dist/css/bootstrap-theme.min.css">
<%--  <script src="js/bootstrap.js"></script> 
 --%>
     
    <script src="amcharts/amcharts.js"></script>
	<script src="amcharts/serial.js"></script>
	<script src="amcharts/themes/light.js"></script>
	<script src="amcharts/amstock.js"></script>
	<script src="amcharts/gauge.js"></script>
	<script src="amcharts/pie.js"></script>
    <script  src="amcharts/plugins/export/export.js"></script>
  
 
 


<s:url var="chartDataUrl" action="json-chart-data"/>
<script type="text/javascript" src="js/html2canvas.js"></script>
<script type="text/javascript" src="js/graphDownload.js"></script>
<script type="text/javascript" src="js/canvas2image.js"></script>

<link rel="stylesheet" type="text/css" href="css/chartdownload.css" />
	
 
<script type="text/javascript">
$(document).ready(function(){
	document.getElementById('sdate').value=$("#hfromDate").val();
    document.getElementById('edate').value=$("#htoDate").val();
});



function chartTab(){
	StatckedChartStatus('jqxChart','','','','All','#5cb85c');
	showLeveStackedBar('levelChart','','','','Pending','#F04E2F ');
	showPieCateg('CategChart');
	StatckedChartReOpen('reopneChart','','','','Reopened','#5cb85c');
	//reOpenGuaze();
	//StatckedChartStatusLoc12('floor');
}

	 function showTrend()
	 {
			 maximizeDiv4Analytics(500,1250,"Trend For All Department","");
			 getChartForTrend('maximizeDataDiv');
		 
	 } 
 chartTab();
  
function getReOpenData()
{
	if(maxDivId4=='1stStackedBar')
	 {
		StatckedChartReOpen('reopneChart','','','','Re-Opened','#5cb85c');
		
	 }
	else if(maxDivId4=='1stTable')
	 {
		showData('reopneChart','','');
	 }
	 
}		



function getDataForAllBoard()
{
	StatckedChartStatus('jqxChart','','','','All','#5cb85c');
	showLeveStackedBar('levelChart','','','','Pending','#F04E2F');
	StatckedChartReOpen('reopneChart','','','','Reopened','#5cb85c');
	showPieCateg('CategChart');
	//reOpenGuaze();
	//StatckedChartStatusLoc12('floor');
	
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
		 showData('jqxChart','','');
	 }
	 
	
	
}

function getLevelByDept(deptId)
{
	if(maxDivId2=='2ndStackedBar')
	 {
		showLeveStackedBar('levelChart','','','','Pending','#d9534f');
		
	 }
	else if(maxDivId2=='2ndTable')
	 {
		 showData('levelChart','','');
	 }
	 
}

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
margin: 11px 5px 3px 293px;
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
#CategChart tspan {
font-weight: bold;
}
reopneChart tspan {
font-weight: bold;
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
          <div id="maximizeCatgDiv"></div>
</sj:dialog>

<s:hidden id="dataFor" value="%{loginType}"/>
<div class="middle-content">
<div class="list-icon">
	<div class="head">Dashboard </div>
	<div class="head">
	<img alt="" src="images/forward.png" style="margin-top:10px;margin-right:3px; float: left;">Helpdesk(M)
	</div>
</div>
<s:hidden id="hfromDate" value="%{fromDate}"></s:hidden>
<s:hidden id="htoDate" value="%{toDate}"></s:hidden>
<div id="trendFor" style="display: none;">Total</div>
<ul class="nav nav-tabs" id="myTab">
  <li role="presentation" class="active" onclick="ticket_normal_newdashboard();"><a href="#">Department</a></li>
<!--   <li role="presentation" onclick="getTDash();"><a href="#">Trend</a></li> -->
  <li class="datePart"><b>Data From</b></li>
  <li style="margin-top: 11px;"><sj:datepicker id="sdate"  cssClass="button" cssStyle="width: 70px;border: 1px solid #e2e9ef;border-top: 1px solid #cbd3e2;padding: 1px;font-size: 12px;outline: medium none;height: 18px;text-align: center;" readonly="true"  size="20" changeMonth="true" changeYear="true"  yearRange="2013:2050" showOn="focus" displayFormat="dd-mm-yy" Placeholder="Select From Date" onchange="getDataForAllBoard()" value=" "/></li>
  <li style="margin-top: 11px;"><b>To</b></li>
  <li style="margin-top: 11px;"><sj:datepicker id="edate"  cssClass="button" cssStyle="width: 70px;border: 1px solid #e2e9ef;border-top: 1px solid #cbd3e2;padding: 1px;font-size: 12px;outline: medium none;height: 18px;text-align: center;" readonly="true"  size="20" changeMonth="true" changeYear="true"  yearRange="2013:2050" showOn="focus" displayFormat="dd-mm-yy" Placeholder="Select TO Date" onchange="getDataForAllBoard()" value=" " /></li>
</ul>
        
  <div style="overflow: auto;" id="dashDataPart">
         
            
<!--Ticket Counter By Status Dashboard Start -->
<div class="contentdiv-smallNewDash" style="overflow: auto;width: 49%" id="counter_dash">

<div class="headdingtest" id="ticketDiv">Ticket Counter Status For <s:property value="%{headerValue}"/></div>
<div class="wrap_nav" style="float: right;">
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
		<li id="threelinerDataGraph"><a href="#" onclick="StatckedChartStatus('jqxChart','','','','All','#5cb85c');">
		<span class="glyphicon glyphicon-equalizer" aria-hidden="true" style="margin-right:4px;"></span>View Bar Graph</a></li>
		
		
		
	</ul>
	</div>
	<div class="clear"></div>
	</div>
	</li>
</ul>
</div>


<% if(userTpe.equalsIgnoreCase("M"))
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
					                          onchange="getTicketByDept(this.value);changeHeader('deptname','ticketDiv','Ticket Counter Status For ');"
				                              >
				                  </s:select>
				                  
				                  </div>
<% }else { %> 
<s:hidden id="deptname" value="%{dept_id}"></s:hidden>
<%} %>
<div class="clear"></div>
  
<div id='jqxChart' style="width: 100%; height: 217px;"  ></div>
 

</div>
<!-- Ticket Counter By Status Dashboard End -->


<!-- Level wise Ticket Status For Particular department Div Start -->
<div class="contentdiv-smallNewDash" style="overflow: hidden;width: 49.5%;" id="hod_level_status">
<div id="lable_graph">
<div class="headdingtest" id="pendingDiv">Pending Tickets Levelwise For <s:property value="%{headerValue}"/></div>

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
		
	
		<li id="threelinerDataGraph1"><a href="#" onclick="showData('levelChart','','')">
		<span class="glyphicon glyphicon-list-alt" aria-hidden="true" style="margin-right:4px;"></span>View Data</a></li>
		
		<li id="threelinerDataGraph"><a href="#" onclick="showLeveStackedBar('levelChart','','','','Pending','#d9534f');">
		<span class="glyphicon glyphicon-equalizer" aria-hidden="true" style="margin-right:4px;"></span>View Bar Graph</a></li>
	
		
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
					                          onchange="getLevelByDept(this.value);changeHeader('deptname1','pendingDiv','Pending Tickets Levelwise For ');"
				                              >
				                  </s:select></div>
				                  <% } else { %> 
<s:hidden id="deptname1" value="%{dept_id}"></s:hidden>
<%} %>
<div class="clear"></div>

<div id='levelChart' style="width: 100%; height: 217px" align="center"></div>

</div>

</div>

<!-- Level wise Ticket Status For Particular department Div End -->


<!-- Category wise Ticket Status Div Start for particular Department-->
<div class="contentdiv-smallNewDash" style="overflow: auto;width: 49%;margin: 7px 1px 0px 6px;" id="hod_catg_status">


<!-- Category Dashboard Start -->
<div id="catg_hod_graph">
<div class="headdingtest" id="cateAnDiv">Category Analysis For <s:property value="%{headerValue}"/></div>

<ul class="nav_links">
	<li><a href="#">
	<span class="threeLiner" style="margin:5px;" ></span>
		 </a> 
			
			
	<div class="dropdown profile_dropdown">
	<div class="arrow_dropdown">&nbsp;</div>
	<div class="one_column" >
	<ul>
		<li><a href="#" onclick="beforeMaximise2('catg_graph',500,900,'Category Analysis','G')" >
		<span class="glyphicon glyphicon-fullscreen" aria-hidden="true" style="margin-right:4px;"></span>Maximize</a></li>
		
	
		<li id="threelinerDataGraph1"><a href="#" onclick="showData('CategChart','','')">
		<span class="glyphicon glyphicon-list-alt" aria-hidden="true" style="margin-right:4px;"></span>View Data</a></li>
		
		<li id="threelinerDataGraph"><a href="#" onclick="showPieCateg('CategChart')">
		<span class="glyphicon glyphicon-equalizer" aria-hidden="true" style="margin-right:4px;"></span>View Pie Graph</a></li>
	
		
		
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
					                          onchange="showPieCateg('CategChart');changeHeader('deptnameCateg','cateAnDiv','Category Analysis For ');"
				                              >
				                  </s:select></div>
				                  <% } else { %> 
<s:hidden id="deptnameCateg" value="%{dept_id}"></s:hidden>
<%} %>
<div class="clear"></div>
<div id='CategChart' style="width: 100%; height: 217px;overflow: auto;float: left;" ></div>
</div>
<!-- Category wise Ticket Status Div End for particular Department-->
</div>




 
<!-- Reopen Dashboard Start -->

<div class="contentdiv-smallNewDash" style="overflow: auto;width: 49%;margin: 7px 1px 0px 6px;" id="hod_catg_status">
<div id="catg_hod_graph">
<div class="headdingtest" id="reopenDiv">Reopen Quick Stats For <s:property value="%{headerValue}"/></div>
<div class="wrap_nav" style="float: right;">
<ul class="nav_links">
	<li ><a href="#">
	<span class="threeLiner" ></span>
		 </a> 
			
			
	<div class="dropdown profile_dropdown">
	<div class="arrow_dropdown">&nbsp;</div>
	<div class="one_column" >
	<ul>
		<li><a href="#" onclick="beforeMaximise4('ticket_graph',400,900,'Ticket Re-Opened Count Status','G')" >
		<span class="glyphicon glyphicon-fullscreen" aria-hidden="true" style="margin-right:4px;"></span>Maximize</a></li>
 		<li id="threelinerDataGraph"><a href="#" onclick="showData('reopneChart','','')">
		<span class="glyphicon glyphicon-list-alt" aria-hidden="true" style="margin-right:4px;"></span>View Data</a></li>
		<li id="threelinerDataGraph"><a href="#" onclick="StatckedChartReOpen('reopneChart','','','','Reopened','#5cb85c');">
		<span class="glyphicon glyphicon-equalizer" aria-hidden="true" style="margin-right:4px;"></span>View Bar Graph</a></li>
	 	</ul>
	</div>
	<div class="clear"></div>
	</div>
	</li>
</ul>
</div>

<% if(userTpe.equalsIgnoreCase("M"))
	{%>
<div style="float:right; width:auto; margin: 5px 4px 0px 0px;"><s:select  
				                              id="deptnameReopen"
				                              name="deptnameReopen"
				                              list="serviceDeptList"
				                              headerKey="-1"
				                              headerValue="All Department" 
				                              cssClass="select"
					                          cssStyle="width:100%;height:22px;  background: #EDECEC;"
					                          onchange="getReOpenData();changeHeader('deptnameReopen','reopenDiv','Reopen Quick Stats For ');"
	 
				                              >
				                  </s:select></div>
				                  <% } else { %> 
<s:hidden id="deptnameReopen" value="%{dept_id}"></s:hidden>
<%} %>
<div class="clear"></div>
<div id='reopneChart' style="width: 100%; height: 217px;overflow: auto;float: left;" ></div>
</div>


            </div>
        </div>
        
  

</body>
</html>