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
<script type="text/javascript" src="<s:url value="/js/dashboard/LocationHDM/dashboardLoc.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/dashboard/LocationHDM/dashboardbarLoc.js"/>"></script>
<%-- <script type="text/javascript" src="<s:url value="/js/dashboard/dashboardBeforeChartLoc.js"/>"></script> --%>
<link rel="stylesheet" href="jqwidgets/styles/jqx.base.css" type="text/css" />
<script type="text/javascript" src="jqwidgets/jqxcore.js"></script>
<script type="text/javascript" src="jqwidgets/jqxdata.js"></script>
<script type="text/javascript" src="jqwidgets/jqxchart.js"></script>
    <script type="text/javascript" src="jqwidgets/jqxswitchbutton.js"></script>
    <script type="text/javascript" src="jqwidgets/jqxcheckbox.js"></script>
    <script type="text/javascript" src="jqwidgets/jqxdropdownlist.js"></script>
       <script type="text/javascript" src="jqwidgets/jqxlistbox.js"></script>
         <script type="text/javascript" src="jqwidgets/jqxscrollbar.js"></script>
             <script type="text/javascript" src="jqwidgets/jqxbuttons.js"></script>
    <script type="text/javascript" src="jqwidgets/jqxcheckbox.js"></script>
         
       
    
    



<s:url var="chartDataUrl" action="json-chart-data"/>
<script type="text/javascript" src="js/html2canvas.js"></script>
<script type="text/javascript" src="js/graphDownload.js"></script>
<script type="text/javascript" src="js/canvas2image.js"></script>

<link rel="stylesheet" type="text/css" href="css/chartdownload.css" />
	
 <script  src="js/jquery-ui.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	document.getElementById('sdate').value=$("#hfromDate").val();
    document.getElementById('edate').value=$("#htoDate").val();
    $('#button1').jqxSwitchButton({ height: 22, width: 81,  checked: false });
    $('#button2').jqxSwitchButton({ height: 22, width: 81,  checked: false });
    
    $('#button1').bind('checked', function (event) { 
    	getEscalated(false);
    	
    });
    $('#button1').bind('unchecked', function (event) { 
    	getEscalated(true);
    	
    });
    
 $('#button2').bind('checked', function (event) { 
	 getReopen(false);
    	
    });
 $('#button2').bind('unchecked', function (event) { 
	 getReopen(true);
 	
 }); 
 

    
    $("#UlDrag li" ).draggable({ 
        opacity:0.5,	 
       	  revert: true, // when not dropped, the item will revert back to its initial position
       	  containment: $('#dashboard'),
             cursor: "crosshair",
             grid: [ 50, 20 ],
             start:function (){
           	 //alert("dragging!!");
           	 
          
             }
             
         });  
    
    $('#jqxChart').droppable({
   	 hoverClass:'border',
   	 accept:'#UlDrag li',
   	 drop:function(event,ui)
   	 {
   		 
   		 var xaxis=$(ui.draggable).text().trim();
   		getChartDefiner(xaxis);
   	 }
    
    });  
   
});

function getChartDefiner(xaxis){
		 if(xaxis=="Location" && ! faFlag && ! waFlag)
		 {
			 maxDivIdLoc1="Location";
			 $("#titleChart").html("Location Wise Ticket Status");
			 $("#Iconbubble").attr('onclick','StatckedChartStatus("jqxChart","bubble")');
			 $("#IconLine").attr('onclick','StatckedChartStatus("jqxChart","line")');
			 $("#IconColumn").attr('onclick','StatckedChartStatus("jqxChart","column")');
			 $("#IconStacked").attr('onclick','StatckedChartStatus("jqxChart","stackedcolumn")');
			 $("#IconData").attr('onclick','showData("jqxChart")');
			 StatckedChartStatus('jqxChart',maxDivIdLoc2);
		 }else if(xaxis=="Time" && ! faFlag && ! waFlag)
		 {
			 maxDivIdLoc1="Time";
			 $("#titleChart").html("Time Wise Ticket Status");
			 $("#Iconbubble").attr('onclick','StatckedChartTime("jqxChart","bubble")');
			 $("#IconLine").attr('onclick','StatckedChartTime("jqxChart","line")');
			 $("#IconColumn").attr('onclick','StatckedChartTime("jqxChart","column")');
			 $("#IconStacked").attr('onclick','StatckedChartTime("jqxChart","stackedcolumn")');
			 $("#IconData").attr('onclick','showData("jqxChart1")');
			 StatckedChartTime('jqxChart',maxDivIdLoc2);
		 }else if (xaxis=="Staff" && ! faFlag && ! waFlag)
		 {
			 maxDivIdLoc1="Staff";
			 $("#titleChart").html("Staff Wise Ticket Status");
			 $("#Iconbubble").attr('onclick','StatckedChartStaff("jqxChart","bubble")');
			 $("#IconLine").attr('onclick','StatckedChartStaff("jqxChart","line")');
			 $("#IconColumn").attr('onclick','StatckedChartStaff("jqxChart","column")');
			 $("#IconStacked").attr('onclick','StatckedChartStaff("jqxChart","stackedcolumn")');
			 $("#IconData").attr('onclick','showData("jqxChart2")');
			 StatckedChartStaff('jqxChart',maxDivIdLoc2);
		 }
		 else if(xaxis=="Location" && faFlag && ! waFlag)
		 {
			 xaxis1="Location";
			 $("#Iconbubble").attr('onclick','StatckedFloorAnalysis("jqxChart","bubble","Location")');
			 $("#IconLine").attr('onclick','StatckedFloorAnalysis("jqxChart","line","Location")');
			 $("#IconColumn").attr('onclick','StatckedFloorAnalysis("jqxChart","column","Location")');
			 $("#IconStacked").attr('onclick','StatckedFloorAnalysis("jqxChart","stackedcolumn","Location")');
			 $("#IconData").attr('onclick','');
			 StatckedFloorAnalysis('jqxChart',maxDivIdLoc2,xaxis);
		 }
		 else if(xaxis=="Time" && faFlag && ! waFlag)
		 {
			 xaxis1="Time";
			 $("#Iconbubble").attr('onclick','StatckedFloorAnalysis("jqxChart","bubble","Time")');
			 $("#IconLine").attr('onclick','StatckedFloorAnalysis("jqxChart","line","Time")');
			 $("#IconColumn").attr('onclick','StatckedFloorAnalysis("jqxChart","column","Time")');
			 $("#IconStacked").attr('onclick','StatckedFloorAnalysis("jqxChart","stackedcolumn","Time")');
			 $("#IconData").attr('onclick','');
			 StatckedFloorAnalysis('jqxChart',maxDivIdLoc2,xaxis);
		 }
		 else if(xaxis=="Staff" && faFlag && ! waFlag)
		 {
			 xaxis1="Staff";
			 $("#Iconbubble").attr('onclick','StatckedFloorAnalysis("jqxChart","bubble","Staff")');
			 $("#IconLine").attr('onclick','StatckedFloorAnalysis("jqxChart","line","Staff")');
			 $("#IconColumn").attr('onclick','StatckedFloorAnalysis("jqxChart","column","Staff")');
			 $("#IconStacked").attr('onclick','StatckedFloorAnalysis("jqxChart","stackedcolumn","Staff")');
			 $("#IconData").attr('onclick','');
			 StatckedFloorAnalysis('jqxChart',maxDivIdLoc2,xaxis);
		 }
		 else if(xaxis=="Location"   &&  waFlag  && !faFlag)
		 {
			 xaxis2="Location";
			 $("#Iconbubble").attr('onclick','StatckedWingAnalysis("jqxChart","bubble","Location")');
			 $("#IconLine").attr('onclick','StatckedWingAnalysis("jqxChart","line","Location")');
			 $("#IconColumn").attr('onclick','StatckedWingAnalysis("jqxChart","column","Location")');
			 $("#IconStacked").attr('onclick','StatckedWingAnalysis("jqxChart","stackedcolumn","Location")');
			 $("#IconData").attr('onclick','');
			 StatckedWingAnalysis('jqxChart',maxDivIdLoc2,xaxis);
		 }
		 else if(xaxis=="Time"  &&  waFlag  && !faFlag)
		 {
			 xaxis2="Time";
			 $("#Iconbubble").attr('onclick','StatckedWingAnalysis("jqxChart","bubble","Time")');
			 $("#IconLine").attr('onclick','StatckedWingAnalysis("jqxChart","line","Time")');
			 $("#IconColumn").attr('onclick','StatckedWingAnalysis("jqxChart","column","Time")');
			 $("#IconStacked").attr('onclick','StatckedWingAnalysis("jqxChart","stackedcolumn","Time")');
			 $("#IconData").attr('onclick','');
			 StatckedWingAnalysis('jqxChart',maxDivIdLoc2,xaxis);
		 }
		 else if(xaxis=="Staff"  &&  waFlag  && !faFlag)
		 {
			 xaxis2="Staff";
			 $("#Iconbubble").attr('onclick','StatckedWingAnalysis("jqxChart","bubble","Staff")');
			 $("#IconLine").attr('onclick','StatckedWingAnalysis("jqxChart","line","Staff")');
			 $("#IconColumn").attr('onclick','StatckedWingAnalysis("jqxChart","column","Staff")');
			 $("#IconStacked").attr('onclick','StatckedWingAnalysis("jqxChart","stackedcolumn","Staff")');
			 $("#IconData").attr('onclick','');
			 StatckedWingAnalysis('jqxChart',maxDivIdLoc2,xaxis);
		 }
}
function getDataForAllBoard(){
	StatckedChartStatus('jqxChart','column');
	//StatckedChartTime('levelChart','column');
	 allLocationCount=0;
	 allTimeCount=0;
	 countFlag=true;
	 countFlag1=true;
//	showLeveStackedBar('levelChart','','') ;
	// showPieCateg('CategChart');
	
}
function getsubdeptChart(deptId,deptName){
	beforeMaximiseFilter('Ticket Count Status For: '+deptName,400,900,'Ticket Count Status','G',deptId,"H");
}
</script>

<s:url var="chartDataUrl" action="json-chart-data"/>

<title>HDM Dashboard</title>
<style type="text/css">
.myDiv{

width: 19%;
height: 500px;
margin-top: 12px;
font-family: sans-serif;
font-weight: bold;
color: black;
  float: left;
  border: 1px solid #e4e4e5;  
  border-radius: 6px;
  box-shadow: 0 1px 4px rgba(0,0,0,.10);

}
.border{
            border-width: 1px;
            border: dotted;
            
        }
.headdingtest{
font-weight: bold;
line-height: 30px;
margin-left: 30px; 
}
.draggableLi{
  border: 1px solid rgb(241, 241, 241);
  height: 16px;
  border-radius: 3px;
  box-shadow: gray 1px 1px 3px 0px;
  margin: 3%;
  background: #F04E2F;

}

.draggableLi:HOVER{
  border: 1px solid rgb(241, 241, 241);
  height: 16px;
  border-radius: 3px;
  box-shadow: gray 1px 1px 3px 0px;
  margin: 3%;
  background:#F09381;
 cursor: move;
}
</style>
</head>
<body id="dashboard">
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
<div id="ticketStatusTab">
<s:hidden id="dataFor" value="%{loginType}"/>
<div class="middle-content">

<s:hidden id="hfromDate" value="%{fromDate}"></s:hidden>
<s:hidden id="htoDate" value="%{toDate}"></s:hidden>
<div class="myDiv">

	 <div style="float: left;line-height: inherit;padding: 2px;margin: 1%;width: 96%;  border-bottom: 1px solid #f1f1f1;background: #F04E2F;  height: 15px;color: white;border-radius: 3px;"><center>Search Parameters</center>
			
	
	</div>

	 <div style="float: left;line-height: inherit;padding: 2px;margin: 4%;width: 92%;  border-bottom: 1px solid #f1f1f1">Data From 
			<sj:datepicker id="sdate"  cssClass="button" cssStyle="width: 70px;border: 1px solid #e2e9ef;border-top: 1px solid #cbd3e2;padding: 1px;font-size: 12px;outline: medium none;height: 18px;text-align: center;" readonly="true"  size="20" changeMonth="true" changeYear="true"  yearRange="2013:2050" showOn="focus" displayFormat="dd-mm-yy" Placeholder="Select From Date" onchange="getDataForAllBoard()" value=" "/>
			To
			<sj:datepicker id="edate"  cssClass="button" cssStyle="width: 70px;border: 1px solid #e2e9ef;border-top: 1px solid #cbd3e2;padding: 1px;font-size: 12px;outline: medium none;height: 18px;text-align: center;" readonly="true"  size="20" changeMonth="true" changeYear="true"  yearRange="2013:2050" showOn="focus" displayFormat="dd-mm-yy" Placeholder="Select TO Date" onchange="getDataForAllBoard()" value=" " />
	
	
	</div>
	<% if(userTpe.equalsIgnoreCase("M"))
	{%>
	<div style="float:left; padding:1%;width: 94%;margin: 3%;border-bottom: 1px solid #f1f1f1;">
							<s:select  
					                              id="deptname"
					                              name="deptname"
					                              list="serviceDeptList"
					                              headerKey="-1"
					                              headerValue="All Department" 
					                              cssClass="select"
						                          cssStyle="width:100%;height:25px; "
						                          onchange="getDeptFloorAnalysis();"
					                              >
					                  </s:select>
    </div>
    <% }else { %> 
<s:hidden id="deptname" value="%{dept_id}"></s:hidden>
<s:hidden id="dept" value="%{dept}"></s:hidden>
<%} %>
	<div style="float:left;width: 94%;margin: 3%;border-bottom: 1px solid #f1f1f1;"><span style="float:left;padding:2%;">Escalated: </span>
		<div style="float: left;" id="button1"></div>
	</div>
	
	<div style="float:left;width: 94%;margin: 3%;border-bottom: 1px solid #f1f1f1;"><span style="float:left;padding:2%;">Re-Opened:</span>	
	<div style="float: left;" id="button2"></div>
	</div>
	 
	
			                  
	

<div style="border: 1px solid #f1f1f1;width: 88%;height: 100px;margin: 3%;float: left;padding: 3%" >
<span style="margin:3%">X- Axis:</span>
	<ul id="UlDrag" type="square">
	 	<li id="Loc" class="draggableLi"><center style="color:white;"> Location</center></li>
	 	<li class="draggableLi"><center  style="color:white;">Time</center> </li>
	 	<li class="draggableLi" ><center  style="color:white;">Staff</center> </li>
	</ul>
</div>


<div style="border: 1px solid #f1f1f1;width: 88%;height: 50px;margin: 3%;float: left;padding: 3%" >
<span style="margin:3%">Y- Axis:</span>
	<ul type="square">
	 	<li style=" border: 1px solid rgb(241, 241, 241); height: 16px; border-radius: 3px;box-shadow: gray 1px 1px 3px 0px; margin:3%; background:#F04E2F;"><center style="color:white;"> Ticket Counter</center></li>
	</ul>
</div>
</div>
<!-- Scrolling Ticket Status Div -->

<!-- Scrolling Ticket Status Div End For HOD OR Particular Department -->

<!--Ticket Counter By Status Dashboard Start -->
<div  style="overflow: auto;width: 76%;  padding: 0 1% 0 1%;margin: 1%;border: 1px solid #e4e4e5;  border-radius: 6px;box-shadow: 0 1px 4px rgba(0,0,0,.10);height: 500px;float: left;" id="counter_dash">
<div id="counterChart">
<div class="headdingtest" id="titleChart">Location Wise Ticket Status</div>
<!-- <div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="refresh('3rdBlock')"><img src="images/refresh-icon.jpg" width="15" height="15" alt="Refresh" title="Refresh" /></a></div> -->
<!-- <div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="beforeMaximise('ticket_graph',400,900,'Ticket Count Status','G')"><img src="images/maximize-icon.jpg" width="15" height="15" alt="Maximize" title="Maximize" /></a></div> -->
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" id="Iconbubble" onclick="StatckedChartStatus('jqxChart','bubble')"><img src="images/chart_bubble.png" width="20" height="15" alt="Show Bubble Chart" title="Show Bubble Chart" border="1" /></a></div>
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#"  id="IconLine" onclick="StatckedChartStatus('jqxChart','line')"><img src="images/line-graph.jpg" width="15" height="15" alt="Get Data" title="Show Line Chart" /></a></div>
<!-- <div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="showPieStatus('jqxChart','Pending','Pending Ticket Status','For All Departments')"><img src="images/pie_chart_icon.jpg" width="15" height="15" alt="Get Data" title="Get Pie Chart" /></a></div> -->
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" id="IconColumn" onclick="StatckedChartStatus('jqxChart','column')"><img src="images/Column-Chart-2Axes.png" width="20" height="15" alt="Show Bar Graph" title="Show Column Chart 2 Axes" border="1" /></a></div>
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" id="IconStacked" onclick="StatckedChartStatus('jqxChart','stackedcolumn')"><img src="images/chart-bar-stacked-icon.png" width="20" height="15" alt="Show Bar Graph 2 Axes" title="Show Stacked-Column Chart" border="0" /></a></div>
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" id="IconData" onclick="showData('jqxChart')"><img src="images/table.jpg" width="18" height="18" alt="Show Counters" title="Show Counters" /></a></div>
<div id="btnSave12" style="float:right; width:auto; padding:0px 4px 0 0;" ><a href="#" ><img src="images/download.png" width="18" height="18" alt="Show Counters" title="Download JPG" onclick="download('jqxChart');"/></a></div>

<div class="clear"></div>
     <div style="float: left;  margin-left: 1%;" id='jqxdropdownlist'>
</div>
<div id="chart"><sj:div id='jqxChart' style="width: 98%; height: 400px;border: 1px solid #f1f1f1;margin: 1%"  ></sj:div></div>

</div>

</div>
<!-- Ticket Counter By Status Dashboard End -->


<!-- Level wise Ticket Status For Particular department Div Start -->
<%-- <div class="contentdiv-smallnewHDMDash" style="overflow: auto;width: 96%" id="hod_level_status">
<div id="lable_graph">
<div class="headdingtest">Time Wise Ticket Status</div>
<!-- <div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="refresh('3rdBlock')"><img src="images/refresh-icon.jpg" width="15" height="15" alt="Refresh" title="Refresh" /></a></div> -->
<!-- <div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="beforeMaximise1('level_graph',400,900,'Level wise Analysis','G')"><img src="images/maximize-icon.jpg" width="15" height="15" alt="Maximize" title="Maximize" /></a></div> -->
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="StatckedChartTime('levelChart','bubble')"><img src="images/chart_bubble.png" width="20" height="15" alt="Show Bubble Chart" title="Show Bubble Chart" border="1" /></a></div>
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="StatckedChartTime('levelChart','line')"><img src="images/line-graph.jpg" width="15" height="15" alt="Get Data" title="Show Line Chart" /></a></div>
<!-- <div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="showPieLevel('levelChart','Pending','Pending Ticket Status','For All Levels')"><img src="images/pie_chart_icon.jpg" width="15" height="15" alt="Get Data" title="Get Pie Chart" /></a></div> -->
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="StatckedChartTime('levelChart','column')"><img src="images/Column-Chart-2Axes.png" width="20" height="15" alt="Show Bar Graph" title="Show Column Chart 2 Axes" border="1" /></a></div>
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="StatckedChartTime('levelChart','stackedcolumn')"><img src="images/chart-bar-stacked-icon.png" width="20" height="15" alt="Show Bar Graph 2 Axes" title="Show Stacked-Column Chart" border="0" /></a></div>
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="showData('levelChart')"><img src="images/table.jpg" width="18" height="18" alt="Show Counters" title="Show Counters" /></a></div>
<div id="btnSave12" style="float:right; width:auto; padding:0px 4px 0 0;" ><a href="#" ><img src="images/download.png" width="18" height="18" alt="Show Counters" title="Download JPG" onclick="downloadTime('levelChart');"/></a></div>


<div class="clear"></div>
<div id="prevPie1" style="display: none;float: left;margin-right: 5px;"><sj:a  cssStyle="width:10px;height:200px;" cssClass="button" button="true" buttonIcon="ui-icon-carat-1-w" onclick="prev1()"  title="Previous"></sj:a></div>
<div id='levelChart' style="width: 100%; height: 200px" align="center"></div>
<div id="nextPie1" style="display: none;float: right;margin-left: 5px;"><sj:a  cssStyle="width:10px;height:200px;" cssClass="button" button="true" buttonIcon="ui-icon-carat-1-e" onclick="next1()" title="Next"></sj:a></div>
</div>
</div> --%>
<!-- Level wise Ticket Status For Particular department Div End -->

</div>
</div>
<div id="StaffAndLocationTab"></div>
</body>
</html>