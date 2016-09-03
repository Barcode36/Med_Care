<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjc" uri="/struts-jquery-chart-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
    <% 
String userTpe=(String)session.getAttribute("userTpe");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<div class="contentdiv-smallNewDash" style="overflow: auto;width: 66%" id="counter_dash">
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
		<li id="threelinerDataGraph"><a href="#" onclick="StatckedChartStatus('jqxChart',filterFlag,filterDeptId,'','Resolve','#5cb85c');">
		<span class="glyphicon glyphicon-equalizer" aria-hidden="true" style="margin-right:4px;"></span>View Bar Graph</a></li>
	
		<li id="threelinerDataGraph"><a href="#" onclick="showTrend();">
		<span class="glyphicon glyphicon-road" aria-hidden="true" style="margin-right:4px;"></span>Show Trend</a></li>
		
	</ul>
	</div>
	<div class="clear"></div>
	</div>
	</li>
</ul>
</div>

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

</div>
<!-- Ticket Counter By Status Dashboard End -->


<!-- Level wise Ticket Status For Particular department Div Start -->
<div class="contentdiv-smallNewDash" style="overflow: hidden;" id="hod_level_status">
<div id="lable_graph">
<div class="headdingtest">Levelwise Status For <s:property value="%{headerValue}"/></div>


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
		
		<li id="threelinerDataGraph"><a href="#" onclick="showLeveStackedBar('levelChart','','','','Pending','#F04E2F');">
		<span class="glyphicon glyphicon-equalizer" aria-hidden="true" style="margin-right:4px;"></span>View Bar Graph</a></li>
	
		<li id="threelinerDataGraph">
		<div style="float: left;margin: 1%;">
			Escalated: <input type="checkbox" onchange="getEscalatedLevel();" id="escLevel"/>
			Reopen: <input type="checkbox" onchange="getReopenLevel();" id="reopenLevel"/>
		</div>
		</li>
		
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
					                          onchange="getLevelByDept(this.value);"
				                              >
				                  </s:select></div>
				                  <% } else { %> 
<s:hidden id="deptname1" value="%{dept_id}"></s:hidden>
<%} %>
<div class="clear"></div>

<div id='levelChart' style="width: 100%; height: 217px" align="center"></div>

</div>
<div class="levelPanel">
<a href="#"><span class="label label-default" style="background-color: #0CD172;" id="statusAll" onclick="showLeveStackedBar('levelChart','','','total','All','#0CD172');">All</span></a>
<a href="#"><span class="label label-success" id="statusRS" onclick="showLeveStackedBar('levelChart','','','Resolved','Resolve','#5cb85c');">Resolve</span></a>
<a href="#"><span class="label label-danger" style="background:#F04E2F;"  id="statusPN" onclick="showLeveStackedBar('levelChart','','','Pending','Pending','#F04E2F');">Pending</span></a>
<a href="#"><span class="label label-warning" id="statusPA" onclick="showLeveStackedBar('levelChart','','','Snooze','Park','#f0ad4e');">Park</span></a>
<a href="#"><span class="label label-info" id="statusRE" onclick="showLeveStackedBar('levelChart','','','Reopened','Re-Open','#5bc0de');">Re-Open</span></a>
<a href="#"><span class="label label-primary" id="statusRA" onclick="showLeveStackedBar('levelChart','','','Reassigned','Re-Assign','#428bca');">Re-Assign</span></a>
<a href="#"><span class="label label-default" id="statusIG" onclick="showLeveStackedBar('levelChart','','','Ignore','Ignore','#777');">Ignore</span></a>
</div>


</div>


         
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
<div class="headdingtest" id="titleChart">Quick Stats</div>
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
					                          onchange="StatckedChartStatusLoc12Before(measure1);"
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

 
       
  

</body>
</html>