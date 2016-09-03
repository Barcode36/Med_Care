 <%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script type="text/javascript" src="<s:url value="/js/helpdesk/reportconfig.js"/>"></script>
<script type="text/javascript">
function getSearchAnalyticalData(fdate,tdate,dept,subdept,report,divID,byDept,mName) 
{
	var fromdate=$("#"+fdate).val();
	var todate=$("#"+tdate).val();
	var deptID=$("#"+dept).val();
	var subDept=$("#"+subdept).val();
	var dataFor=$("#dataFor").val();
	var byDeptId=$("#"+byDept).val();
	var moduleName=$("#"+mName).val();
	
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Analytical_Report/getAnalyticsGrid.action?fromDate="+fromdate+"&toDate="+todate+"&deptId="+deptID+"&subdeptId="+subDept+"&dataFor="+dataFor+"&byDeptId="+byDeptId+"&moduleName="+moduleName,
	    success : function(subdeptdata) {
       $("#"+divID).html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });
}

function getProductivty(moduleName,dataFor)
{
	moduleName=$("#module").val();
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Analytical_Report/getAnalytics.action?dataFor="+dataFor+"&moduleName="+moduleName,
	    success : function(data) {
       $("#"+"data_part").html(data);
	},
	   error: function() {
            alert("error");
        }
	 });
}

function viewActivityBoard()
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Activity_Board/viewActivityBoardHeader.action",
	    success : function(feeddraft) {
       $("#"+"data_part").html(feeddraft);
	},
	   error: function() {
            alert("error");
        }
	 });
}

</script>
</head>
<body>
<s:hidden id="module" value='%{moduleName}' ></s:hidden>
<div class="clear"></div>
<div class="middle-content">
<s:if test="dataFor=='Employee'">
<div class="list-icon">
	 <div class="head">
	 Employee</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div>
	 <div class="head">Productivity</div> 
	  	<sj:a style="margin-top: 4px;float: right;"   cssClass="button"  button="true"  onclick="viewActivityBoard();">Activity Board</sj:a>
	     <sj:a  button="true" cssClass="button"  cssStyle="margin-top: 4px;float: right;" onclick="getProductivty('HDM','Category')">Category&nbsp;Productivity</sj:a>
	 </div>
</s:if>
<s:if test="dataFor=='Category'">
<div class="list-icon">
	 <div class="head">
	 Category</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div>
	 <div class="head">Productivity</div> 
	<sj:a style="margin-top: 4px;float: right;" button="true" cssClass="button" onclick="viewActivityBoard();">Activity Board</sj:a>
	 <sj:a  button="true" cssClass="button" cssStyle="margin-top: 4px;float: right;"  onclick="getProductivty('HDM','Employee')">Employee&nbsp;Productivity</sj:a>
</div>
</s:if>
<div class="clear"></div>
<div class="listviewBorder" style="margin-top: 10px;width: 97%;margin-left: 20px;" align="center">
<div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv" >
	<div class="pwie">
		<table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%">
		<tbody><tr></tr><tr><td></td></tr><tr><td> 
			<table class="floatL" border="0" cellpadding="0" cellspacing="0">
		<tbody>
		<tr>
		<td class="pL10"> 
			 <%-- <sj:a id="searchButton"  title="Search" buttonIcon="ui-icon-search" cssStyle="height:25px; width:32px"  cssClass="button" button="true"  onclick="searchRow()"></sj:a>
			 <sj:a id="refButton"  title="Refresh" buttonIcon="ui-icon-refresh" cssStyle="height:25px; width:32px"  cssClass="button" button="true"  onclick="getData('Refresh');"></sj:a>
		      --%>
		     <sj:datepicker cssStyle="height: 16px; width: 60px;" cssClass="button" id="fromDate" name="fromDate" size="20" maxDate="0" readonly="true"   yearRange="2013:2050" showOn="focus" displayFormat="dd-mm-yy" Placeholder="Select From Date" onchange="getSearchAnalyticalData('fromDate','toDate','deptId','subdeptId','dataFor','resultData','byDeptId','moduleName');"/>
		     <sj:datepicker cssStyle="height: 16px; width: 60px;" cssClass="button" id="toDate" name="toDate" size="20" value="today"   readonly="true" yearRange="2013:2050" showOn="focus" displayFormat="dd-mm-yy" Placeholder="Select To Date"/>
		     <s:if test="dataFor=='Category'">
		      <s:select  
	    		id					=		"byDeptId"
	    		name				=		"byDeptId"
	    		headerKey			=		"-1"
	    		headerValue			=		"By All Department"
	    		list				=		"deptList"
	      		theme				=		"simple"
	      		cssClass			=		"button"
	      		cssStyle			=		"height: 28px;margin-top: 0px;margin-left: 3px;width: 150px;"
	      	 >
	      	 </s:select>
	      	 </s:if>
		     <s:select  
	    		id					=		"deptId"
	    		name				=		"deptId"
	    		headerKey			=		"-1"
	    		headerValue			=		"To All Department"
	    		list				=		"serviceDeptList"
	      		theme				=		"simple"
	      		cssClass			=		"button"
	      		cssStyle			=		"height: 28px;width: 150px;"
	      		onchange			=		"getServiceSubDept(this.value,'subdeptId','HDM');getSearchAnalyticalData('fromDate','toDate','deptId','subdeptId','dataFor','resultData','byDeptId','moduleName');"
	      	 >
	      	 </s:select>
		     
     	    </td>
		<s:if test="moduleName=='HDM'">
     	    <td class="pL10"> 
     	      <s:select  
		    	id					=		"subdeptId"
		    	name				=		"subdeptId"
	    		headerKey			=		"-1"
	    		headerValue			=		"All Sub Department"
		    	list				=		"{'NO DATA'}"
		    	multiple			=		"false	"
      		    theme  				=		"simple"
      		    cssClass			=		"button"
      		    cssStyle			=		"height: 28px;width: 170px;margin-left: -7px;"
      		    onchange			=		"getSearchAnalyticalData('fromDate','toDate','deptId','subdeptId','dataFor','resultData','byDeptId','moduleName');"
      		 >
     	     </s:select>
	     	</td> 
	    </s:if>
	  	</tr>
		</tbody>
		</table>
		<td class="alignright printTitle">
		     <sj:a id="downloadButton" buttonIcon="ui-icon-arrowthickstop-1-s" cssClass="button" button="true" title="Download" cssStyle="height:25px; width:32px" onclick="downloadAction('dataFor');"></sj:a>
		 	 <s:if test="dataFor=='Employee'">
			    <sj:a id="graphButton" href="#"  cssStyle="height:25px; width:32px" ><img src='images/pie_chart_icon.jpg' style="margin-bottom: -8px;margin-right: 3px;" alt='Graph' width='32' height='25' title="Employee Graph" onclick="getGraphDataProductivity('fromDate','toDate','deptId','subdeptId','dataFor','byDeptId','moduleName');"></sj:a>
		 	</s:if>	
			<s:if test="dataFor=='Category'">
			    <sj:a id="graphButton" href="#"  cssStyle="height:25px; width:32px" ><img src='images/pie_chart_icon.jpg' style="margin-bottom: -8px;margin-right: 3px;" alt='Graph' width='32' height='25' title="Category Graph" onclick="getGraphDataProductivity('fromDate','toDate','deptId','subdeptId','dataFor','byDeptId','moduleName');"></sj:a>
			 	</s:if>
		</td>
		</tr>

</tbody></table></div>
</div>
<div style="overflow: scroll; height: 430px;">
<s:hidden id="dataFor" name="dataFor" value="%{dataFor}"  /> 
	<div id="resultData">
		<s:if test="dataFor=='Employee'">
		  <jsp:include page="grid_emp_analytics.jsp"/>
		 </s:if>
		 <s:if test="dataFor=='Category'">
		  <jsp:include page="grid_catg_analytics.jsp"/>
		 </s:if>
	</div>
</div>
</div>
</div>
</body>
</html>