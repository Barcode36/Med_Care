<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<link type="text/css" href="<s:url value="/css/style.css"/>" rel="stylesheet" />
<script type="text/javascript" src="<s:url value="/js/compliance/compliance.js"/>"></script>
</head>
<body>
<div class="clear"></div>
<div class="middle-content">
<div class="list-icon">
	 <div class="head">
	 Operations</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div>
	 <div class="head">Measuring Productivity</div>
	 <div id="test"  class="alignright printTitle">
	  	<sj:a id="sendActivity"  cssClass="button" button="true" cssStyle="margin-top: 4px;" title="Activity Board"  onclick="activtyBoard();">Activity Board</sj:a>
	 </div> 
	 
</div>
<div class="clear"></div>
<div class="listviewBorder"  style="margin-top: 10px;width: 97%;margin-left: 20px;height: 37px;" align="center">
	<div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
	   <div class="pwie">
		<table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%">
			<tbody>
				<tr>
					  <td>
						  <table class="floatL" border="0" cellpadding="0" cellspacing="0">
						    <tbody><tr><td class="pL10">   
						     <sj:datepicker id="fromDate" onchange			="getProductivity('fromDate','toDate','empName','proData')" name="fromDate" cssClass="button" size="20" cssStyle="width: 71px;"  value="%{fromDate}" readonly="true"   yearRange="2013:2050" showOn="focus" displayFormat="dd-mm-yy" Placeholder="Select From Date"/>
						     <sj:datepicker id="toDate" onchange			="getProductivity('fromDate','toDate','empName','proData')" name="toDate" cssClass="button" size="20" value="%{toDate}" cssStyle="width: 71px;" readonly="true" yearRange="2013:2050" showOn="focus" displayFormat="dd-mm-yy" Placeholder="Select To Date"/>
						       <s:select  
                                id                    =        "empName"
                                name                =        "empName"
                                headerKey            =        "-1"
                                headerValue            =        "Select Employee"
                                list                =        "employeeMap"
                                cssClass            =        "button"
                                cssStyle            =        "width:140px;;margin-left: 0px;margin-top: -60px;height: 26px;"
                                theme                =        "simple"
                              	onchange			="getProductivity('fromDate','toDate','empName','proData')"
                               >
						      </s:select>
						     
						    <%--   <sj:a id="proButton" cssStyle="height: 24px; margin-left: -3px;margin-top: -4px;" cssClass="button" button="true"  onclick="getProductivity('fromDate','toDate','empName','proData')">OK</sj:a> --%>
						      </td></tr></tbody>
						  </table>
					  </td>
					  <td class="alignright printTitle">
					  </td>
				</tr>
			</tbody>
		</table>
	</div>
</div>
	<div id="proData">
	<table border="1" width="100%" align="center">
	<tbody>
	
	 <tr bgcolor="#D8D8D8" style="height: 25px;">
		<td style="height: 25px;" align="center" width="12%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b>Name</b></font></td>
		<td style="height: 25px;" align="center" width="12%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b>Total&nbsp;Task</b></font></td>
		<td style="height: 25px;" align="center" width="12%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b>On&nbsp;Time</b></font></td>
		<td style="height: 25px;" align="center" width="12%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b>Off&nbsp;Time</b></font></td>
		<td style="height: 25px;" align="center" width="12%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b>Snooze</b></font></td>
	 	<td style="height: 25px;" align="center" width="12%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b>Missed</b></font></td>
		<td style="height: 25px;" align="center" width="20%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b>%&nbsp;Achieved&nbsp;On&nbsp;Time</b></font></td>
		<td style="height: 25px;" align="center" width="20%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b>%&nbsp;Achieved&nbsp;Off&nbsp;Time</b></font></td>
		<td style="height: 25px;" style="height: 25px;"align="center" width="20%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b>%&nbsp;Missed</b></font></td>
	    <td style="height: 25px;" align="center" width="10%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b>%&nbsp;Graph</b></font></td>
	 </tr>
	
	<s:iterator id="totalCompl"  status="status" value="%{totalProductivity.complList}" >
	<s:if test="#status.odd">
	 <tr style="height: 25px;">
		<td align="center" width="12%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="1"><a style="cursor: pointer;text-decoration: none;" href="#"><s:property value="#totalCompl.name" /></a></font></td>
		
		<s:if test="#totalCompl.totalTask==0">
			<td align="center" width="12%" ><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="#totalCompl.totalTask"/></a></font></td>
		</s:if>
		<s:else>
			<td align="center" width="12%" onclick="showAllComplProductivityDetails('total','<s:property value="#totalCompl.compId"/>');"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="#totalCompl.totalTask"/></a></font></td>
		</s:else>
		
		<s:if test="#totalCompl.onTime==0">
			<td align="center" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;" href="#"><s:property value="#totalCompl.onTime"/></a></font></td>
		</s:if>
		<s:else>
			<td align="center" width="12%" onclick="showAllComplProductivityDetails('ontime','<s:property value="#totalCompl.compId"/>');"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;" href="#"><s:property value="#totalCompl.onTime"/></a></font></td>
		</s:else>
		
		<s:if test="#totalCompl.offTime==0">
			<td align="center" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="#totalCompl.offTime"/></a></font></td>
		</s:if>
		<s:else>
			<td align="center" width="12%" onclick="showAllComplProductivityDetails('offtime','<s:property value="#totalCompl.compId"/>');"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="#totalCompl.offTime"/></a></font></td>
		</s:else>
		<s:if test="#totalCompl.snooze==0">
			<td align="center" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="#totalCompl.snooze"/></a></font></td>
		</s:if>
		<s:else>
			<td align="center" width="12%" onclick="showAllComplProductivityDetails('snooze','<s:property value="#totalCompl.compId"/>');"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="#totalCompl.snooze"/></a></font></td>
		</s:else>
		<s:if test="#totalCompl.missed==0">
			<td align="center" width="12%" ><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="#totalCompl.missed"/></a></font></td>
		</s:if>
		<s:else>
			<td align="center" width="12%" onclick="showAllComplProductivityDetails('missed','<s:property value="#totalCompl.compId"/>');"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="#totalCompl.missed"/></a></font></td>
		</s:else>
		
		<s:if test="#totalCompl.perOnTime==0">
			<td align="center" width="20%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;" href="#"><s:property value="#totalCompl.perOnTime"/></a></font></td>
		</s:if>
		<s:else>
			<td align="center" width="20%" onclick="55showAllComplProducffffftivityDetails('perontime','<s:property value="#totalCompl.compId"/>');"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;" href="#"><s:property value="#totalCompl.perOnTime"/></a></font></td>
		</s:else>
		
		<s:if test="#totalCompl.perOffTime==0">
			<td align="center" width="20%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="#totalCompl.perOffTime"/></a></font></td>
		</s:if>
		<s:else>
			<td align="center" width="20%" onclick="555showAllComplProducffftivityDetails('perofftime','<s:property value="#totalCompl.compId"/>');"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="#totalCompl.perOffTime"/></a></font></td>
		</s:else>
		<s:if test="#totalCompl.perMissed==0">
			<td align="center" width="20%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="#totalCompl.perMissed"/></a></font></td>
		</s:if>
		<s:else>
			<td align="center" width="20%" onclick="55showAllComplProdfffuctivityDetails('perofftime','<s:property value="#totalCompl.compId"/>');"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="#totalCompl.perOffTime"/></a></font></td>
		</s:else>
		<td align="center" width="20%"><a href="#" onclick="showDetailsPieOfProduct('1stDataBlockDiv','<s:property value="#totalCompl.graph"/>')"><img src="images/pie_chart_icon.jpg" width="15" height="15" alt="Get Data" title="Get Pie Chart" /></a></td>
	 </tr>
	</s:if>
	<s:else>
	 <tr bgcolor="#D8D8D8" style="height: 25px;">
		<td align="center" width="12%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="1"><a style="cursor: pointer;text-decoration: none;" href="#"><s:property value="#totalCompl.name" /></a></font></td>
		
		<s:if test="#totalCompl.totalTask==0">
			<td align="center" width="12%" ><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="#totalCompl.totalTask"/></a></font></td>
		</s:if>
		<s:else>
			<td align="center" width="12%" onclick="showAllComplProductivityDetails('total','<s:property value="#totalCompl.compId"/>');"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="#totalCompl.totalTask"/></a></font></td>
		</s:else>
		
		<s:if test="#totalCompl.onTime==0">
			<td align="center" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;" href="#"><s:property value="#totalCompl.onTime"/></a></font></td>
		</s:if>
		<s:else>
			<td align="center" width="12%" onclick="showAllComplProductivityDetails('ontime','<s:property value="#totalCompl.compId"/>');"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;" href="#"><s:property value="#totalCompl.onTime"/></a></font></td>
		</s:else>
		
		<s:if test="#totalCompl.offTime==0">
			<td align="center" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="#totalCompl.offTime"/></a></font></td>
		</s:if>
		<s:else>
			<td align="center" width="12%" onclick="showAllComplProductivityDetails('offtime','<s:property value="#totalCompl.compId"/>');"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="#totalCompl.offTime"/></a></font></td>
		</s:else>
		<s:if test="#totalCompl.snooze==0">
			<td align="center" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="#totalCompl.snooze"/></a></font></td>
		</s:if>
		<s:else>
			<td align="center" width="12%" onclick="showAllComplProductivityDetails('snooze','<s:property value="#totalCompl.compId"/>');"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="#totalCompl.snooze"/></a></font></td>
		</s:else>
		<s:if test="#totalCompl.missed==0">
			<td align="center" width="12%" ><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="#totalCompl.missed"/></a></font></td>
		</s:if>
		<s:else>
			<td align="center" width="12%" onclick="showAllComplProductivityDetails('missed','<s:property value="#totalCompl.compId"/>');"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="#totalCompl.missed"/></a></font></td>
		</s:else>
		
		<s:if test="#totalCompl.perOnTime==0">
			<td align="center" width="20%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;" href="#"><s:property value="#totalCompl.perOnTime"/></a></font></td>
		</s:if>
		<s:else>
			<td align="center" width="20%" onclick="55showAllComplProducffffftivityDetails('perontime','<s:property value="#totalCompl.compId"/>');"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;" href="#"><s:property value="#totalCompl.perOnTime"/></a></font></td>
		</s:else>
		
		<s:if test="#totalCompl.perOffTime==0">
			<td align="center" width="20%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="#totalCompl.perOffTime"/></a></font></td>
		</s:if>
		<s:else>
			<td align="center" width="20%" onclick="555showAllComplProducffftivityDetails('perofftime','<s:property value="#totalCompl.compId"/>');"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="#totalCompl.perOffTime"/></a></font></td>
		</s:else>
		<s:if test="#totalCompl.perMissed==0">
			<td align="center" width="20%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="#totalCompl.perMissed"/></a></font></td>
		</s:if>
		<s:else>
			<td align="center" width="20%" onclick="55showAllComplProdfffuctivityDetails('perofftime','<s:property value="#totalCompl.compId"/>');"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="#totalCompl.perOffTime"/></a></font></td>
		</s:else>
		<td align="center" width="20%"><a href="#" onclick="showDetailsPieOfProduct('1stDataBlockDiv','<s:property value="#totalCompl.graph"/>')"><img src="images/pie_chart_icon.jpg" width="15" height="15" alt="Get Data" title="Get Pie Chart" /></a></td>
	 </tr>
	</s:else>
	
	</s:iterator>
	<tr bgcolor="#D8D8D8" style="height: 25px;">
	<td align="center" width="15%" height="40%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2">Total </font></td>
	<s:if test="totalTask==0">
		<td style="height: 25px;" align="center" width="15%" height="40%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="totalTask"/></a></font></td>
	</s:if>
	<s:else>
		<td style="height: 25px;" align="center" width="15%" height="40%" onclick="55showAllComplDetails('All','Pending','<s:property value="allCompId1st"/>');"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="totalTask"/></a></font></td>
	</s:else>
	
	<s:if test="totalOnTime==0">
		<td style="height: 25px;" align="center" width="15%" height="40%" ><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="totalOnTime"/></a></font></td>
	</s:if>
	<s:else>
		<td style="height: 25px;" align="center" width="15%" height="40%" onclick="55showAllComplDetails('All','Missed','<s:property value="allCompId1st"/>');"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="totalOnTime"/></a></font></td>
	</s:else>
	
	<s:if test="totalOffTime==0">
		<td style="height: 25px;" align="center" width="15%" height="40%" ><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="totalOffTime"/></a></font></td>
	</s:if>
	<s:else>
		<td style="height: 25px;" align="center" width="15%" height="40%" onclick="55showAllComplDetails('All','Done','<s:property value="allCompId1st"/>');"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="totalOffTime"/></a></font></td>
	</s:else>
	<s:if test="totalSnooze==0">
		<td  style="height: 25px;" align="center" width="15%" height="40%" ><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="totalSnooze"/></a></font></td>
	</s:if>
	<s:else>
		<td style="height: 25px;" align="center" width="15%" height="40%" onclick="55showAllComplDetails('All','Snooze','<s:property value="allCompId1st"/>');"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="totalSnooze"/></a></font></td>
	</s:else>
		<s:if test="totalOffTime==0">
		<td style="height: 25px;" align="center" width="20%" height="40%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="totalOffTime"/></a></font></td>
	</s:if>
	<s:else>
		<td style="height: 25px;" align="center" width="20%" height="40%" onclick="66showAllComplDetails('All','Pending','<s:property value="allCompId1st"/>');"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="totalOffTime"/></a></font></td>
	</s:else>
	
	<s:if test="totalPerOnTime==0">
		<td style="height: 25px;" align="center" width="20%" height="40%" ><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="totalPerOnTime"/></a></font></td>
	</s:if>
	<s:else>
		<td style="height: 25px;" align="center" width="20%" height="40%" onclick="66showAllComplDetails('All','Missed','<s:property value="allCompId1st"/>');"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="totalPerOnTime"/></a></font></td>
	</s:else>
	
	<s:if test="totalPerOffTime==0">
		<td style="height: 25px;" align="center" width="20%" height="40%" ><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="totalPerOffTime"/></a></font></td>
	</s:if>
	<s:else>
		<td style="height: 25px;" align="center" width="20%" height="40%" onclick="77showAllComplDetails('All','Done','<s:property value="allCompId1st"/>');"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="totalPerOffTime"/></a></font></td>
	</s:else>
	<s:if test="totalPerOffTime==0">
		<td style="height: 25px;" align="center" width="20%" height="40%" ><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="totalPerOffTime"/></a></font></td>
	</s:if>
	<s:else>
		<td style="height: 25px;" align="center" width="20%" height="40%" onclick="77showAllComplDetails('All','Snooze','<s:property value="allCompId1st"/>');"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="totalPerOffTime"/></a></font></td>
	</s:else>
	<s:if test="totalPerOffffTime==0">
		<td style="height: 25px;" align="center" width="20%" height="40%" ><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="totalPerOffffTime"/></a></font></td>
	</s:if>
	<s:else>
		<td style="height: 25px;" align="center" width="20%" height="40%" onclick="77showAllComplDetails('All','Snooze','<s:property value="allCompId1st"/>');"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="totafflPerOffTime"/></a></font></td>
	</s:else>
	
 </tr>
	</tbody>
	</table>
	</div>
   
       <br>
      <div id="empData"></div>
  

<sj:dialog 
	 			id				=	"complianceDialog" 
	 			modal			=	"true" 
	 			effect			=	"slide" 
	 			autoOpen		=	"false" 
	 			width			=	"1140" 
	 			height			=	"533"
	 			overlayColor	=	"#fff" 
	 			overlayOpacity	=	"1.0" 
	 			position="['center','center']"
	 			>
	 			<div id="datadiv"></div>
     		</sj:dialog>
    
</div>
  </div>
</body>
</html>