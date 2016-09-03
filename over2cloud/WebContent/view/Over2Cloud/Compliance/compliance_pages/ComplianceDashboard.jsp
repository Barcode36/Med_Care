<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjc" uri="/struts-jquery-chart-tags"%>

<%
int count=0;
%>
<html>
<head>
<s:url id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}" />
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>Over2cloud admin</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript" src="<s:url value="/js/compliance/ComplianceMenuCtrl.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/compliance/complianceDashv2.js"/>"></script>
<link rel="stylesheet" href="jqwidgets/styles/jqx.base.css" type="text/css" />
<script type="text/javascript" src="jqwidgets/jqxcore.js"></script>
<script type="text/javascript" src="jqwidgets/jqxdata.js"></script>
<script type="text/javascript" src="jqwidgets/jqxchart.js"></script>
<script type="text/javascript">

function complTakeAction(valuepassed) 
{
	
   $("#takeActionGrid111").load("<%=request.getContextPath()%>/view/Over2Cloud/Compliance/compliance_pages/beforeTakeAction?complID="+valuepassed);
   $("#takeActionGrid111").dialog('open');
}

</script>
</head>
<sj:dialog
          id="takeActionGrid111"
          showEffect="slide"
          hideEffect="explode"
          autoOpen="false"
          title="Take Action on Operation Task"
          modal="true"
          closeTopics="closeEffectDialog"
          width="900"
          height="350"
          >
</sj:dialog>

<sj:dialog
          id="maxmizeViewDialog"
          showEffect="slide"
          hideEffect="explode"
          autoOpen="false"
          modal="true"
          closeTopics="closeEffectDialog"
         
          >
          <div id="maximizeDataDiv"></div>
</sj:dialog>

<body>
<div class="maincontainer">
<div class="clear"></div>

<div class="middle-content" id="data_part" >
<div class="middle-content">
<div class="contentdiv-small" style="overflow: hidden;">

<div class="headding">Operation Task&nbsp;Status</div>

<!-- <div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="refresh('1stRefresh')"><img src="images/refresh-icon.jpg" width="15" height="15" alt="Refresh" title="Refresh" /></a></div> -->
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="beforeMaximise('1stColumn2AxesBar',400,900,'Operation Task Status')"><img src="images/maximize-icon.jpg" width="15" height="15" alt="Maximize" title="Maximize" /></a></div>
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="showChart('1stBubbleGraph','jqxChart1')"><img src="images/chart_bubble.png" width="20" height="15" alt="Show Bubble Chart" title="Show Bubble Chart" border="1" /></a></div>
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="showChart('1stLine','jqxChart1')"><img src="images/line-graph.jpg" width="15" height="15" alt="Get Data" title="Show Line Chart" /></a></div>
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="showPieStauts('Pending','Peding Ticket Status','For All Dept')"><img src="images/Pie-chart-icon.png" width="15" height="15" alt="Get Data" title="Get Pie Chart" /></a></div>
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="showChart('1stColumn2Axes','jqxChart1')"><img src="images/Column-Chart-2Axes.png" width="20" height="15" alt="Show Bar Graph" title="Show Column Chart 2 Axes" border="1" /></a></div>
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="showChart('1stBarGraph','jqxChart1')"><img src="images/chart-bar-stacked-icon.png" width="20" height="15" alt="Show Bar Graph 2 Axes" title="Show Stacked-Column Chart" border="0" /></a></div>
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="showData('jqxChart1')"><img src="images/table.jpg" width="18" height="18" alt="Show Counters" title="Show Counters" /></a></div>
<div class="clear"></div>
<!--  -->
<div id="prevPie" style="display: none;float: left;margin-right: 5px;"><sj:a  cssStyle="width:10px;height:200px;" cssClass="button" button="true" buttonIcon="ui-icon-carat-1-w" onclick="prev()"  title="Previous"></sj:a></div>
<div id='jqxChart1' style="width: 100%; height: 200px" align="center"></div>
<div id="nextPie" style="display: none;float: right;margin-left: 5px;"><sj:a  cssStyle="width:10px;height:200px;" cssClass="button" button="true" buttonIcon="ui-icon-carat-1-e" onclick="next()" title="Next"></sj:a></div>

</div>


<div class="contentdiv-small" style="overflow: hidden;">

<div class="headding">Frequency wise Operation Task Status</div>
<div id="2ndDataBlockDiv">
<!-- <div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="refresh('2ndRefresh')"><img src="images/refresh-icon.jpg" width="15" height="15" alt="Refresh" title="Refresh" /></a></div> -->
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="beforeMaximise1('2ndPieGraph',400,900,'Frequency wise Operation Task Status')"><img src="images/maximize-icon.jpg" width="15" height="15" alt="Maximize" title="Maximize" /></a></div>
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="showChart('2ndBubbleGraph','jqxChart2')"><img src="images/chart_bubble.png" width="20" height="15" alt="Show Bubble Chart" title="Show Bubble Chart" border="1" /></a></div>
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="showChart('2ndLine','jqxChart2')"><img src="images/line-graph.jpg" width="15" height="15" alt="Get Data" title="Show Line Chart" /></a></div>
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="showPieFreq('YearlyTotal','Yearly Total Task Status','For All Department')"><img src="images/Pie-chart-icon.png" width="15" height="15" alt="Get Data" title="Get Pie Chart" /></a></div>
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="showChart('2ndColumn2AxesGraph','jqxChart2')"><img src="images/Column-Chart-2Axes.png" width="20" height="15" alt="Show Bar Graph" title="Show Column Chart 2 Axes" border="1" /></a></div>
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="showChart('2ndStackBarGraph','jqxChart2')"><img src="images/chart-bar-stacked-icon.png" width="20" height="15" alt="Show Bar Graph 2 Axes" title="Show Stacked-Column Chart" border="0" /></a></div>
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="showData('jqxChart2')"><img src="images/table.jpg" width="18" height="18" alt="Show Counters" title="Show Counters" /></a></div>
<div class="clear"></div>
<div id="prevPie1" style="display: none;float: left;margin-right: 5px;"><sj:a  cssStyle="width:10px;height:200px;" cssClass="button" button="true" buttonIcon="ui-icon-carat-1-w" onclick="prev1()"  title="Previous"></sj:a></div>
<div id='jqxChart2' style="width: 100%; height: 200px" align="center"></div>
<div id="nextPie1" style="display: none;float: right;margin-left: 5px;"><sj:a  cssStyle="width:10px;height:200px;" cssClass="button" button="true" buttonIcon="ui-icon-carat-1-e" onclick="next1()" title="Next"></sj:a></div>
</div>
</div>

<div class="contentdiv-small" style="overflow: hidden;">

<div class="headding">Operation Task Ageing</div>
<div id="3rdDataBlockDiv">
<!-- <div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="refresh('3rdRefresh')"><img src="images/refresh-icon.jpg" width="15" height="15" alt="Refresh" title="Refresh" /></a></div> -->
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="beforeMaximise2('3rdBubbleGraph',400,900,'Operation Task Ageing')"><img src="images/maximize-icon.jpg" width="15" height="15" alt="Maximize" title="Maximize" /></a></div>
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="showChart('3rdBubbleGraph','jqxChart3')"><img src="images/chart_bubble.png" width="20" height="15" alt="Show Bubble Chart" title="Show Bubble Chart" border="1" /></a></div>
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="showChart('3rdLine','jqxChart3')"><img src="images/line-graph.jpg" width="15" height="15" alt="Get Data" title="Show Line Chart" /></a></div>
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="showPieAgieng('ThisYear','This Year Agieng Details','For All Department')"><img src="images/Pie-chart-icon.png" width="15" height="15" alt="Get Data" title="Get Pie Chart" /></a></div>
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="showChart('3rdColumn2AxesGraph','jqxChart3')"><img src="images/Column-Chart-2Axes.png" width="20" height="15" alt="Show Bar Graph" title="Show Column Chart 2 Axes" border="1" /></a></div>
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="showChart('3rdStackBarGraph','jqxChart3')"><img src="images/chart-bar-stacked-icon.png" width="20" height="15" alt="Show Bar Graph 2 Axes" title="Show Stacked-Column Chart" border="0" /></a></div>
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="showData('jqxChart3')"><img src="images/table.jpg" width="18" height="18" alt="Show Counters" title="Show Counters" /></a></div>

<div class="clear"></div>
<div id="prevPie2" style="display: none;float: left;margin-right: 5px;"><sj:a  cssStyle="width:10px;height:200px;" cssClass="button" button="true" buttonIcon="ui-icon-carat-1-w" onclick="prev2()"  title="Previous"></sj:a></div>
<div id='jqxChart3' style="width: 100%; height: 200px" align="center"></div>
<div id="nextPie2" style="display: none;float: right;margin-left: 5px;"><sj:a  cssStyle="width:10px;height:200px;" cssClass="button" button="true" buttonIcon="ui-icon-carat-1-e" onclick="next2()" title="Next"></sj:a></div>
</div>

</div>

<div class="contentdiv-small" style="overflow:hidden;">
<div class="headding">Operation Task&nbsp;Status&nbsp;From&nbsp;<s:property value="fromDate"/>&nbsp;To&nbsp;<s:property value="toDate"/></div>
<div id="4thDataBlockDiv">
<!-- <div style="float:right;width:5px; padding:36px 4px 0px 0px;   "><a href="#" onclick="refresh('4thRefresh')"><img src="images/refresh-icon.jpg" width="15" height="15" alt="Refresh" title="Refresh" /></a></div> -->
<div style="float:right; width:auto; padding:36px 4px 0px 0; margin-right: -2px;"><a href="#" onclick="beforeMaximise3('4thDataBlockDiv',270,900,'Last Month Operation Task')"><img src="images/maximize-icon.jpg" width="15" height="15" alt="Maximize" title="Maximize" /></a></div>
<div style="float:right; width:auto; padding:36px 0px 0 0;"><a href="#" onclick="showChart('4thBubbleGraph','jqxChart4')"><img src="images/chart_bubble.png" width="20" height="15" alt="Show Bubble Chart" title="Show Bubble Chart" border="1" /></a></div>
<div style="float:right; width:auto; padding:36px 2px 0 0;"><a href="#" onclick="showChart('4thLine','jqxChart4')"><img src="images/line-graph.jpg" width="15" height="15" alt="Get Data" title="Show Line Chart" /></a></div>
<div style="float:right; width:auto; padding:36px 2px 0 0;"><a href="#" onclick="showPieStatusPrevMonth('Pending','Previous Month Pending Ticket Status','For All Department')"><img src="images/Pie-chart-icon.png" width="15" height="15" alt="Get Data" title="Get Pie Chart" /></a></div>
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="showChart('4thColumn2AxesGraph','jqxChart4')"><img src="images/Column-Chart-2Axes.png" width="20" height="15" alt="Show Bar Graph" title="Show Column Chart 2 Axes" border="1" /></a></div>
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="showChart('4thStackBarGraph','jqxChart4')"><img src="images/chart-bar-stacked-icon.png" width="20" height="15" alt="Show Bar Graph 2 Axes" title="Show Stacked-Column Chart" border="0" /></a></div>
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="showData('jqxChart4')"><img src="images/table.jpg" width="18" height="18" alt="Show Counters" title="Show Counters" /></a></div>

<div class="clear"></div>
<div id="prevPie3" style="display: none;float: left;margin-right: 5px;"><sj:a  cssStyle="width:10px;height:200px;" cssClass="button" button="true" buttonIcon="ui-icon-carat-1-w" onclick="prev3()"  title="Previous"></sj:a></div>
<div id='jqxChart4' style="width: 100%; height: 180px" align="center"></div>
<div id="nextPie3" style="display: none;float: right;margin-left: 5px;"><sj:a  cssStyle="width:10px;height:200px;" cssClass="button" button="true" buttonIcon="ui-icon-carat-1-e" onclick="next3()" title="Next"></sj:a></div>

</div>


</div>

<div class="contentdiv-small" style="overflow: hidden;">
<div id="5thblock">
<div class="headding">Pending Operation Task </div>
<div style="float:right; width:auto; padding:0px 4px 0 0; margin-right: -2px;"><a href="#" onclick="maximizeDetails('5thMaxData','Pending Compliance')"><img src="images/maximize-icon.jpg" width="15" height="15" alt="Maximize" title="Maximize" /></a></div>
<div style="float:right; width:auto; padding:0px 4px 0 0; margin-right: -2px; "><a href="#" onclick="refresh('5thMaxData')"><img src="images/refresh-icon.jpg" width="15" height="15" alt="Refresh" title="Refresh" /></a></div>
<div id="5thMaxData">
<div class="clear"></div>
<table align="center" border="0" width="100%" style="border-style:dotted solid;">
               <tr height="20px">
                   <td valign="bottom">
                       <table height="10px" align="center" border="1" style="border-style:dotted solid;" width="100%">
                        	  <tr bgcolor="#06C0C9" height="17px">
                        			<td align="center" width="40%" bgcolor="#138F6A" style=" color:#ffffff; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;"><b>Task&nbsp;Name</b></td>
									<td align="center" width="25%" bgcolor="#138F6A" style=" color:#ffffff; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;"><b>Due&nbsp;Date</b></td>
									<td align="center" width="20%" bgcolor="#138F6A" style=" color:#ffffff; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;"><b>Status</b></td>
									<td align="center" width="20%" bgcolor="#138F6A" style=" color:#ffffff; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;"><b>Esc&nbsp;Level</b></td>
							  </tr>
                       </table>
                    </td>
               </tr>
               
               <tr>
                   <td valign="top" style="padding-top: 10px" align="left">
                   <marquee onmouseover="this.stop()" onmouseout="this.start()" scrollamount="1" scrolldelay="2" direction="up">
					   <table align="left" border="0" width="100%" style="border-style:dotted solid;">                        		
                              <s:iterator id="userPendingCompl"  status="status" value="%{userWiseUpcomeingComplList}" >
								<tr bordercolor="#ffffff">
                        			<td align="left" width="40%" bgcolor="#C3E8D1" style=" color:#125EA9; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;"><b><a href="#" onclick="complTakeAction('<s:property value="#userPendingCompl.compId"/>')"><s:property value="#userPendingCompl.taskName"/></a></b></td>
                        			<td align="center" width="25%" bgcolor="#C3E8D1" style=" color:#125EA9; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;"><b><a href="#" onclick="complTakeAction('<s:property value="#userPendingCompl.compId"/>')"><s:property value="#userPendingCompl.dueDate"/></a></b></td>
                        			<td align="center" width="20%" bgcolor="#C3E8D1" style=" color:#125EA9; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;"><b><a href="#" onclick="complTakeAction('<s:property value="#userPendingCompl.compId"/>')"><s:property value="#userPendingCompl.actionStatus"/></a></b></td>
                        			<td align="center" width="20%" bgcolor="#C3E8D1" style=" color:#125EA9; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;"><b><a href="#" onclick="complTakeAction('<s:property value="#userPendingCompl.compId"/>')"><s:property value="#userPendingCompl.currentEscLevel"/></a></b></td>
                        		</tr>
                        	   </s:iterator>
                        </table>
                   </marquee>
                   </td>
               </tr>
 </table>	
              </div>
              </div>

</div>
<div class="contentdiv-small" style="overflow:hidden;" >
<div class="headding">Today Pending Operation Task</div>
<div id="7thDataBlockDiv">
<!-- <div style="float:right; width:auto; padding:0px 4px 0 0; margin-right: -2px; "><a href="#" onclick="refresh('7thRefresh')"><img src="images/refresh-icon.jpg" width="15" height="15" alt="Refresh" title="Refresh" /></a></div> -->
<div style="float:right; width:auto; padding:0px 4px 0 0; margin-right: -2px;"><a href="#" onclick="beforeMaximise4('7thLine',400,900,'Today Operation Task')"><img src="images/maximize-icon.jpg" width="15" height="15" alt="Maximize" title="Maximize" /></a></div>
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="showChart('7thBubbleGraph','jqxChart7')"><img src="images/chart_bubble.png" width="20" height="15" alt="Show Bubble Chart" title="Show Bubble Chart" border="1" /></a></div>
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="showChart('7thLine','jqxChart7')"><img src="images/line-graph.jpg" width="15" height="15" alt="Get Data" title="Show Line Chart" /></a></div>
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="showPieTodays('SelfDue','Self Due Todays Ticket Details','For All Department')"><img src="images/Pie-chart-icon.png" width="15" height="15" alt="Get Data" title="Get Pie Chart" /></a></div>
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="showChart('7thColumn2AxesGraph','jqxChart7')"><img src="images/Column-Chart-2Axes.png" width="20" height="15" alt="Show Bar Graph" title="Show Column Chart 2 Axes" border="1" /></a></div>
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="showChart('7thStackBarGraph','jqxChart7')"><img src="images/chart-bar-stacked-icon.png" width="20" height="15" alt="Show Bar Graph 2 Axes" title="Show Stacked-Column Chart" border="0" /></a></div>
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="showData('jqxChart7')"><img src="images/table.jpg" width="18" height="18" alt="Show Counters" title="Show Counters" /></a></div>
<div class="clear"></div>
<div id="prevPie4" style="display: none;float: left;margin-right: 5px;"><sj:a  cssStyle="width:10px;height:200px;" cssClass="button" button="true" buttonIcon="ui-icon-carat-1-w" onclick="prev4()"  title="Previous"></sj:a></div>
<div id='jqxChart7' style="width: 100%; height: 200px" align="center"></div>
<div id="nextPie4" style="display: none;float: right;margin-left: 5px;"><sj:a  cssStyle="width:10px;height:200px;" cssClass="button" button="true" buttonIcon="ui-icon-carat-1-e" onclick="next4()" title="Next"></sj:a></div>
</div>



<%-- <div id="6thDataBlockDiv">
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="refresh('1stBlock')"><img src="images/refresh-icon.jpg" width="15" height="15" alt="Refresh" title="Refresh" /></a></div>
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="maximizeDetails('6thDataBlockDiv','Level wise Compliance')"><img src="images/maximize-icon.jpg" width="15" height="15" alt="Maximize" title="Maximize" /></a></div>
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="showDetailsPie('6thDataBlockDiv','6thGraphBlockDiv')"><img src="images/pie_chart_icon.jpg" width="15" height="15" alt="Get Data" title="Get Pie Chart" /></a></div>
<div class="clear"></div>
<table border="1" width="100%" align="center">
<tbody>

 <tr bgcolor="#B27ACF">
	<td  align="center" width="25%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#ffffff" size="2"><b>Department</b></font></td>
	<td  align="center" width="15%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#ffffff" size="2"><b>L-1</b></font></td>
	<td  align="center" width="15%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#ffffff" size="2"><b>L-2</b></font></td>
	<td  align="center" width="15%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#ffffff" size="2"><b>L-3</b></font></td>
	<td  align="center" width="15%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#ffffff" size="2"><b>L-4</b></font></td>
	<td  align="center" width="15%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#ffffff" size="2"><b>L-5</b></font></td>
 </tr>

<s:iterator id="levelWiseCompl"  status="status" value="%{escLevelCompDetailObj.complList}" >
 <tr bgcolor="#E2DAE6">
	<td align="center" width="25%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;" href="#"><s:property value="#levelWiseCompl.departName" /></a></font></td>
	<s:if test="#levelWiseCompl.esc1Count==0">
		<td align="center" width="15%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="#levelWiseCompl.esc1Count"/></a></font></td>
	</s:if>
	<s:else>
		<td align="center" width="15%" onclick="lastMonthCompDetail('<s:property value="#levelWiseCompl.departId"/>','Level1','Level wise Compliance');"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="#levelWiseCompl.esc1Count"/></a></font></td>
	</s:else>
	
	<s:if test="#levelWiseCompl.esc2Count==0">
		<td align="center" width="15%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;" href="#"><s:property value="#levelWiseCompl.esc2Count"/></a></font></td>
	</s:if>
	<s:else>
		<td align="center" width="15%" onclick="lastMonthCompDetail('<s:property value="#levelWiseCompl.departId"/>','Level2','Level wise Compliance');"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;" href="#"><s:property value="#levelWiseCompl.esc2Count"/></a></font></td>
	</s:else>
	
	<s:if test="#levelWiseCompl.esc3Count==0">
		<td align="center" width="15%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="#levelWiseCompl.esc3Count"/></a></font></td>
	</s:if>
	<s:else>
		<td align="center" width="15%" onclick="lastMonthCompDetail('<s:property value="#levelWiseCompl.departId"/>','Level3','Level wise Compliance');"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="#levelWiseCompl.esc3Count"/></a></font></td>
	</s:else>
	
	<s:if test="#levelWiseCompl.esc4Count==0">
		<td align="center" width="15%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;" href="#"><s:property value="#levelWiseCompl.esc4Count"/></a></font></td>
	</s:if>
	<s:else>
		<td align="center" width="15%" onclick="lastMonthCompDetail('<s:property value="#levelWiseCompl.departId"/>','Level4','Level wise Compliance');"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;" href="#"><s:property value="#levelWiseCompl.esc4Count"/></a></font></td>
	</s:else>
	
	<s:if test="#levelWiseCompl.esc5Count==0">
		<td align="center" width="15%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;" href="#"><s:property value="#levelWiseCompl.esc5Count"/></a></font></td>
	</s:if>
	<s:else>
		<td align="center" width="15%" onclick="lastMonthCompDetail('<s:property value="#levelWiseCompl.departId"/>','Level5','Level wise Compliance');"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;" href="#"><s:property value="#levelWiseCompl.esc5Count"/></a></font></td>
	</s:else>
	
 </tr>
</s:iterator>

<tr bgcolor="#E2DAE6">
	<td align="center" width="25%" height="40%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2">Total</font></td>
	
	<s:if test="esc1Total==0">
		<td align="center" width="15%" height="40%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="esc1Total"/></a></font></td>
	</s:if>
	<s:else>
		<td align="center" width="15%" height="40%" onclick="lastMonthCompDetail('All','Level1','Level wise Compliance');"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="esc1Total"/></a></font></td>
	</s:else>
	
	<s:if test="esc2Total==0">
		<td align="center" width="15%" height="40%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="esc2Total"/></a></font></td>
	</s:if>
	<s:else>
		<td align="center" width="15%" height="40%" onclick="lastMonthCompDetail('All','Level2','Level wise Compliance');"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="esc2Total"/></a></font></td>
	</s:else>
	
	<s:if test="esc3Total==0">
		<td align="center" width="15%" height="40%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="esc3Total"/></a></font></td>
	</s:if>
	<s:else>
		<td align="center" width="15%" height="40%" onclick="lastMonthCompDetail('All','Level3','Level wise Compliance');"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="esc3Total"/></a></font></td>
	</s:else>
	
	<s:if test="esc4Total==0">
		<td align="center" width="15%" height="40%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="esc4Total"/></a></font></td>
	</s:if>
	<s:else>
		<td align="center" width="15%" height="40%" onclick="lastMonthCompDetail('All','Level4','Level wise Compliance');"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="esc4Total"/></a></font></td>
	</s:else>
	
	<s:if test="esc5Total==0">
		<td align="center" width="15%" height="40%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="esc5Total"/></a></font></td>
	</s:if>
	<s:else>
		<td align="center" width="15%" height="40%" onclick="lastMonthCompDetail('All','Level5','Level wise Compliance');"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="esc5Total"/></a></font></td>
	</s:else>
	
 </tr>
</tbody>
</table>
</div>
 --%>
<%-- <div id="6thGraphBlockDiv" style="display: none">
<!--<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="refresh('1stBlock')"><img src="images/refresh-icon.jpg" width="15" height="15" alt="Refresh" title="Refresh" /></a></div>
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="maximizeDetails('6thGraphBlockDiv','Level wise Compliance')"><img src="images/maximize-icon.jpg" width="15" height="15" alt="Maximize" title="Maximize" /></a></div>
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="showDetailsData('6thDataBlockDiv','6thGraphBlockDiv')"><img src="images/data.jpg" width="15" height="15" alt="Get Data" title="Get Data of Graph" /></a></div>
	--><div class="clear"></div>
	<sjc:chart
        id="levelwisechartPie"
        cssStyle="width: 405px; height: 190px;"
        pie="true"
        pieLabel="true"
        onClickTopics="getChatInfo"
        legendShow="false"
    >
    <s:iterator value="%{escLevelCompDetail}">
	    <s:if test="key=='Level 1'">
		    <sjc:chartData
		    		label="%{key}"
		    		data="%{value}"
		    		color="orange"
		    		/>	
		    		
		 </s:if>
		 <s:elseif test="key=='Level 2'">
		 	<sjc:chartData
		    		label="%{key}"
		    		data="%{value}"
		    		color="#9FF781"/>
		 </s:elseif>
		 <s:elseif test="key=='Level 3'">
		 	<sjc:chartData
		    		label="%{key}"
		    		data="%{value}"
		    		color="brown"/>
		 </s:elseif>
		 <s:elseif test="key=='Level 4'">
		 	<sjc:chartData
		    		label="%{key}"
		    		data="%{value}"
		    		color="brown"/>
		 </s:elseif>
		 <s:elseif test="key=='Level 5'">
		 	<sjc:chartData
		    		label="%{key}"
		    		data="%{value}"
		    		color="brown"/>
		 </s:elseif>
    	</s:iterator>
    </sjc:chart>
</div> --%>

</div>
</div>
</div>
<sj:dialog 
	 			id				=	"complianceDialog" 
	 			modal			=	"true" 
	 			effect			=	"slide" 
	 			autoOpen		=	"false" 
	 			width			=	"700" 
	 			height			=	"533"
	 			title			=	"Operation Task Management Dashboard  >>  View" 
	 			overlayColor	=	"#fff" 
	 			overlayOpacity	=	"1.0" 
	 			position="['center','center']"
	 			>
	 			<div id="datadiv"></div>
     		</sj:dialog>

</div>
</body>
</html>
