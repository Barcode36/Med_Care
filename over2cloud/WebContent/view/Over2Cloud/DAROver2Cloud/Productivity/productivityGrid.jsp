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

function formatGraph(cellvalue, options, row) 
{
	return "<a href='#' title='Graph' onClick='totalDataFunction11("+row.onTime+","+row.offTime+","+row.missed+","+row.snooze+","+row.ignore+")'><img src='images/pie_chart_icon.jpg' alt='Graph' width='20' height='20'></a>";
}
function formatCounter(cellvalue, options, row) 
{
	return "<a href='#' title='Total Task' onClick='totalDataFunction("+row.empId+")'>"+cellvalue+"</a>";
}
function formatOntime(cellvalue, options, row) 
{
	return "<a href='#' title='On Time Task' onClick='onTimeDataFunction("+row.empId+")'>"+cellvalue+"</a>";
}
function formatOfftime(cellvalue, options, row) 
{
	return "<a href='#' title='Off Time Task' onClick='offTimeDataFunction("+row.empId+")'>"+cellvalue+"</a>";
}
function formatMissed(cellvalue, options, row) 
{
	return "<a href='#' title='Missed Task' onClick='missedDataFunction("+row.empId+")'>"+cellvalue+"</a>";
}
function formatSnooze(cellvalue, options, row) 
{
	return "<a href='#' title='Snooze Task' onClick='snoozeDataFunction("+row.empId+")'>"+cellvalue+"</a>";
}
function formatIgnore(cellvalue, options, row) 
{
	return "<a href='#' title='Ignore Task' onClick='ignoreDataFunction("+row.empId+")'>"+cellvalue+"</a>";
}
function totalDataFunction(value)
{
	if(value=='0')
	{
	}
	else
	{
		var taskName = jQuery("#empProductivity").jqGrid('getCell',value,'empName');
		var dialogTitle="Productivity For "+taskName;
		$("#dialogOpen").dialog('open');
		var fromDate=$("#fromDate").val();
		var subdeptId=$("#subdeptId").val();
		var toDate=$("#toDate").val();
		var deptId=$("#deptId").val();
		var moduleName=$("#moduleName").val();
		$("#dialogOpen").dialog({title: dialogTitle});
		$("#dialogOpen").html("<br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Analytical_Report/getProductivityData.action?id="+value+"&fromDate="+fromDate+"&toDate="+toDate+"&deptId="+deptId+"&subdeptId="+subdeptId+"&dataFor=Employee&moduleName="+moduleName,
		    success : function(data) 
		    {
	       		$("#dialogOpen").html(data);
		    },
		   error: function() {
	            alert("error");
	        }
		 });
	}
}
function onTimeDataFunction(value)
{
	if (value=='0') 
	{
	} 
	else 
	{
		var taskName = jQuery("#empProductivity").jqGrid('getCell',value,'empName');
		var dialogTitle="Productivity For "+taskName;
		$("#dialogOpen").dialog('open');
		var fromDate=$("#fromDate").val();
		var subdeptId=$("#subdeptId").val();
		var toDate=$("#toDate").val();
		var deptId=$("#deptId").val();
		var moduleName=$("#moduleName").val();
		$("#dialogOpen").dialog({title: dialogTitle});
		$("#dialogOpen").html("<br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Analytical_Report/getProductivityData.action?id="+value+"&fromDate="+fromDate+"&toDate="+toDate+"&deptId="+deptId+"&subdeptId="+subdeptId+"&dataFor=Employee&moduleName="+moduleName+"&searchField=OnTime",
		    success : function(data) 
		    {
	       		$("#dialogOpen").html(data);
		    },
		   error: function() {
	            alert("error");
	        }
		 });
	}
}
function offTimeDataFunction(value)
{
	if (value=='0') 
	{
	} 
	else 
	{
		var taskName = jQuery("#empProductivity").jqGrid('getCell',value,'empName');
		var dialogTitle="Productivity For "+taskName;
		$("#dialogOpen").dialog('open');
		var fromDate=$("#fromDate").val();
		var subdeptId=$("#subdeptId").val();
		var toDate=$("#toDate").val();
		var deptId=$("#deptId").val();
		var moduleName=$("#moduleName").val();
		$("#dialogOpen").dialog({title: dialogTitle});
		$("#dialogOpen").html("<br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Analytical_Report/getProductivityData.action?id="+value+"&fromDate="+fromDate+"&toDate="+toDate+"&deptId="+deptId+"&subdeptId="+subdeptId+"&dataFor=Employee&moduleName="+moduleName+"&searchField=Offtime",
		    success : function(data) 
		    {
	       		$("#dialogOpen").html(data);
		    },
		   error: function() {
	            alert("error");
	        }
		 });
	}
}
function missedDataFunction(value)
{
	if (value=='0') 
	{
	} 
	else 
	{
		var taskName = jQuery("#empProductivity").jqGrid('getCell',value,'empName');
		var dialogTitle="Productivity For "+taskName;
		$("#dialogOpen").dialog('open');
		var fromDate=$("#fromDate").val();
		var subdeptId=$("#subdeptId").val();
		var toDate=$("#toDate").val();
		var deptId=$("#deptId").val();
		var moduleName=$("#moduleName").val();
		$("#dialogOpen").dialog({title: dialogTitle});
		$("#dialogOpen").html("<br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Analytical_Report/getProductivityData.action?id="+value+"&fromDate="+fromDate+"&toDate="+toDate+"&deptId="+deptId+"&subdeptId="+subdeptId+"&dataFor=Employee&moduleName="+moduleName+"&searchField=Missed",
		    success : function(data) 
		    {
	       		$("#dialogOpen").html(data);
		    },
		   error: function() {
	            alert("error");
	        }
		 });
	}
}
function snoozeDataFunction(value)
{
	if (value=='0') 
	{
	} 
	else 
	{
		var taskName = jQuery("#empProductivity").jqGrid('getCell',value,'empName');
		var dialogTitle="Productivity For "+taskName;
		$("#dialogOpen").dialog('open');
		var fromDate=$("#fromDate").val();
		var subdeptId=$("#subdeptId").val();
		var toDate=$("#toDate").val();
		var deptId=$("#deptId").val();
		var moduleName=$("#moduleName").val();
		$("#dialogOpen").dialog({title: dialogTitle});
		$("#dialogOpen").html("<br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Analytical_Report/getProductivityData.action?id="+value+"&fromDate="+fromDate+"&toDate="+toDate+"&deptId="+deptId+"&subdeptId="+subdeptId+"&dataFor=Employee&moduleName="+moduleName+"&searchField=Snooze",
		    success : function(data) 
		    {
	       		$("#dialogOpen").html(data);
		    },
		   error: function() {
	            alert("error");
	        }
		 });
	}
}
function ignoreDataFunction(value)
{
	if (value=='0') 
	{
	} 
	else 
	{
		var taskName = jQuery("#empProductivity").jqGrid('getCell',value,'empName');
		var dialogTitle="Productivity For "+taskName;
		$("#dialogOpen").dialog('open');
		var fromDate=$("#fromDate").val();
		var subdeptId=$("#subdeptId").val();
		var toDate=$("#toDate").val();
		var deptId=$("#deptId").val();
		var moduleName=$("#moduleName").val();
		$("#dialogOpen").dialog({title: dialogTitle});
		$("#dialogOpen").html("<br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Analytical_Report/getProductivityData.action?id="+value+"&fromDate="+fromDate+"&toDate="+toDate+"&deptId="+deptId+"&subdeptId="+subdeptId+"&dataFor=Employee&moduleName="+moduleName+"&searchField=Ignore",
		    success : function(data) 
		    {
	       		$("#dialogOpen").html(data);
		    },
		   error: function() {
	            alert("error");
	        }
		 });
	}
}
</script>
</head>
<body>
<sj:dialog
          id="dialogOpen"
          showEffect="slide" 
    	  hideEffect="explode" 
    	  openTopics="openEffectDialog"
    	  closeTopics="closeEffectDialog"
          autoOpen="false"
          title="Productivity"
          modal="true"
          width="1000"
          height="600"
          draggable="true"
    	  resizable="true"
          >
</sj:dialog>
<s:hidden id="deptId" name="deptId" value="%{deptId}"/>
<s:hidden id="subdeptId" name="subdeptId" value="%{subdeptId}"/>
<s:hidden id="fromDate" name="fromDate" value="%{fromDate}"/>
<s:hidden id="toDate" name="toDate" value="%{toDate}"/>
<s:hidden id="moduleName" name="moduleName" value="%{moduleName}"/>

<s:url id="analysisConf_URL" action="viewAnalysisDeatil" escapeAmp="false">
<s:param name="dataFor" value="%{dataFor}"></s:param>
<s:param name="deptId" value="%{deptId}"/>
<s:param name="subdeptId" value="%{subdeptId}"/>
<s:param name="fromDate" value="%{fromDate}"></s:param>
<s:param name="toDate" value="%{toDate}"></s:param>
<s:param name="moduleName" value="%{moduleName}"></s:param>
</s:url>

<sjg:grid 
		  id="empProductivity"
          href="%{analysisConf_URL}"
          gridModel="feedbackList"
          navigator="true"
          navigatorAdd="false"
          navigatorView="true"
          navigatorDelete="false"
          navigatorEdit="false"
          navigatorSearch="true"
          rowList="15,25,50"
          rownumbers="-1"
          viewrecords="true"       
          pager="true"
          pagerButtons="true"
          pagerInput="true"   
          multiselect="true"  
          loadonce="%{loadonce}"
          loadingText="Requesting Data..."  
          rowNum="12"
          autowidth="true"
          navigatorEditOptions="{height:230,width:400}"
          navigatorSearchOptions="{sopt:['eq','ne','cn','bw','ne','ew']}"
          navigatorEditOptions="{reloadAfterSubmit:true}"
          shrinkToFit="false"
          userDataOnFooter="true"
          footerrow="true"
          
          >
		  <sjg:gridColumn 
		      				name="empId"
		      				index="empId"
		      				title="Id"
		      				width="80"
		      				align="center"
		      				hidden="true"
		      				key="true"
		 					/>
		
		  <sjg:gridColumn 
		      				name="empName"
		      				index="empName"
		      				title="Employee Name"
		      				width="160"
		      				align="center"
		 					/>
					
		 <sjg:gridColumn 
		      				name="counter"
		      				index="counter"
		      				title="Total Task"
		      				width="100"
		      				align="center"
		 					/>
		   <sjg:gridColumn 
		      				name="onTime"
		      				index="onTime"
		      				title="On Time"
		      				width="100"
		      				align="center"
		      				
		 					/>
		 					
		  <sjg:gridColumn 
		      				name="offTime"
		      				index="offTime"
		      				title="Off Time"
		      				width="100"
		      				align="center"
		 					/>
		 					
		   <sjg:gridColumn 
		      				name="missed"
		      				index="missed"
		      				title="Missed"
		      				width="100"
		      				align="center"
		 					/>
		 					
		  <sjg:gridColumn 
		      				name="snooze"
		      				index="snooze"
		      				title="Snooze"
		      				width="100"
		      				align="center"
		 					/>	
		 					
		 <sjg:gridColumn 
		      				name="ignore"
		      				index="ignore"
		      				title="Ignore"
		      				width="100"
		      				align="center"
		 					/>
		 					
		 <sjg:gridColumn 
		      				name="perOnTime"
		      				index="perOnTime"
		      				title="On Time (%)"
		      				width="100"
		      				align="center"
		 					/>
		 					
		  <sjg:gridColumn 
		      				name="perOffTime"
		      				index="perOffTime"
		      				title="Off Time (%)"
		      				width="100"
		      				align="center"
		 					/>
		 					
		   <sjg:gridColumn 
		      				name="perMissed"
		      				index="perMissed"
		      				title="Missed (%)"
		      				width="95"
		      				align="center"
		 					/>				
		  <sjg:gridColumn 
		      				name="graph"
		      				index="graph"
		      				title="Graph"
		      				width="50"
		      				align="center"
		      				formatter="formatGraph"
		 					/>
		 					
		  </sjg:grid>
		  
</body>
</html>