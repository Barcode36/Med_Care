<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
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
</script>
</head>
<body>
<div class="clear"></div>
<div class="middle-content">
<div class="list-icon">
	 <div class="head">
	 Employee</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div>
	 <div class="head">Productivity</div> 
</div>

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
			
		     <sj:datepicker cssStyle="height: 16px; width: 60px;" cssClass="button" id="fromDate" name="fromDate" size="20" maxDate="0" readonly="true"   yearRange="2013:2050" showOn="focus" displayFormat="dd-mm-yy" Placeholder="Select From Date"/>
		     <sj:datepicker cssStyle="height: 16px; width: 60px;" cssClass="button" id="toDate" name="toDate" size="20" value="today"   readonly="true" yearRange="2013:2050" showOn="focus" displayFormat="dd-mm-yy" Placeholder="Select To Date"/>
		     <s:select  
	    		id					=		"employee"
	    		name				=		"employee"
	    		headerKey			=		"-1"
	    		headerValue			=		"All Employee"
	    		list				=		"{'No Data'}"
	      		theme				=		"simple"
	      		cssClass			=		"button"
	      		cssStyle			=		"height: 28px;width: 110px;"
	      	 >
	      	 </s:select>
     	    </td>
	
		   <sj:a  button="true" cssClass="button" cssStyle="height: 25px;margin-left: 4px;margin-top: 2px;" onclick="getSearchAnalyticalData('fromDate','toDate','deptId','subdeptId','dataFor','resultData','byDeptId','moduleName');">OK</sj:a>
		</tr>
		</tbody>
		</table>
		<td class="alignright printTitle">
		     <sj:a id="downloadButton" buttonIcon="ui-icon-arrowthickstop-1-s" cssClass="button" button="true" title="Download" cssStyle="height:25px; width:32px" onclick="downloadAction('dataFor');"></sj:a>
		 	 <sj:a id="sendMailButton"  cssClass="button" buttonIcon="ui-icon-mail-closed" button="true" cssStyle="height:25px; width:32px" title="Send Mail" onclick="openDialog();"></sj:a>
		     <sj:a id="graphButton" href="#"  cssStyle="height:25px; width:32px" ><img src='images/pie_chart_icon.jpg' style="margin-bottom: -8px;margin-right: 3px;" alt='Graph' width='32' height='25' title="Employee Graph" onclick="getGraphDataProductivity('fromDate','toDate','deptId','subdeptId','dataFor','byDeptId','moduleName');"></sj:a>
		</td>
		</tr>

</tbody></table></div>
</div>
<div style="overflow: scroll; height: 430px;">
	<div id="resultData">
		  <jsp:include page="productivityGrid.jsp"/>
	</div>
</div>
</div>
</div>
</body>
</html>