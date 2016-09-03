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
function formatImage(cellvalue, options, row) 
{
	return "<a href='#' title='Graph' onClick='totalDataFunction11("+row.onTime+","+row.offTime+","+row.missed+","+row.snooze+","+row.ignore+")'><img src='images/pie_chart_icon.jpg' alt='Graph' width='20' height='20'></a>";
}
function formatCounter(cellvalue, options, row) 
{
	return "<a href='#' title='Total Task' onClick='totalDataFunction("+row.feedId+")'>"+cellvalue+"</a>";
}
function formatOntime(cellvalue, options, row) 
{
	return "<a href='#' title='On Time Task' onClick='onTimeDataFunction("+row.feedId+")'>"+cellvalue+"</a>";
}
function formatOfftime(cellvalue, options, row) 
{
	return "<a href='#' title='Off Time Task' onClick='offTimeDataFunction("+row.feedId+")'>"+cellvalue+"</a>";
}
function formatMissed(cellvalue, options, row) 
{
	return "<a href='#' title='Missed Task' onClick='missedDataFunction("+row.feedId+")'>"+cellvalue+"</a>";
}
function formatSnooze(cellvalue, options, row) 
{
	return "<a href='#' title='Snooze Task' onClick='snoozeDataFunction("+row.feedId+")'>"+cellvalue+"</a>";
}
function formatIgnore(cellvalue, options, row) 
{
	return "<a href='#' title='Ignore Task' onClick='ignoreDataFunction("+row.feedId+")'>"+cellvalue+"</a>";
}
function totalDataFunction(value)
{
	if(value == '0')
	{
	}
	else
	{
		var dialogTitle="Category Productivity ";
		$("#dialogOpen").dialog('open');
		var fromDate=$("#fromDate").val();
		var subdeptId=$("#subdeptId").val();
		var toDate=$("#toDate").val();
		var deptId=$("#deptId").val();
		var byDept =$("#byDeptId").val();
		var moduleName=$("#moduleName").val();
		$("#dialogOpen").dialog({title: dialogTitle});
		$("#dialogOpen").html("<br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Analytical_Report/getProductivityData.action?id="+value+"&fromDate="+fromDate+"&toDate="+toDate+"&deptId="+deptId+"&subdeptId="+subdeptId+"&byDeptId="+byDept+"&dataFor=Category&moduleName="+moduleName,
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
		var dialogTitle="Category Productivity ";
		$("#dialogOpen").dialog('open');
		var fromDate=$("#fromDate").val();
		var subdeptId=$("#subdeptId").val();
		var toDate=$("#toDate").val();
		var deptId=$("#deptId").val();
		var byDept =$("#byDeptId").val();
		var moduleName=$("#moduleName").val();
		$("#dialogOpen").dialog({title: dialogTitle});
		$("#dialogOpen").html("<br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Analytical_Report/getProductivityData.action?id="+value+"&fromDate="+fromDate+"&toDate="+toDate+"&deptId="+deptId+"&subdeptId="+subdeptId+"&byDeptId="+byDept+"&dataFor=Category&moduleName="+moduleName+"&searchField=OnTime",
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
		var dialogTitle="Category Productivity ";
		$("#dialogOpen").dialog('open');
		var fromDate=$("#fromDate").val();
		var subdeptId=$("#subdeptId").val();
		var toDate=$("#toDate").val();
		var deptId=$("#deptId").val();
		var byDept =$("#byDeptId").val();
		var moduleName=$("#moduleName").val();
		$("#dialogOpen").dialog({title: dialogTitle});
		$("#dialogOpen").html("<br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Analytical_Report/getProductivityData.action?id="+value+"&fromDate="+fromDate+"&toDate="+toDate+"&deptId="+deptId+"&subdeptId="+subdeptId+"&byDeptId="+byDept+"&dataFor=Category&moduleName="+moduleName+"&searchField=Offtime",
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
		var dialogTitle="Category Productivity ";
		$("#dialogOpen").dialog('open');
		var fromDate=$("#fromDate").val();
		var subdeptId=$("#subdeptId").val();
		var toDate=$("#toDate").val();
		var deptId=$("#deptId").val();
		var byDept =$("#byDeptId").val();
		var moduleName=$("#moduleName").val();
		$("#dialogOpen").dialog({title: dialogTitle});
		$("#dialogOpen").html("<br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Analytical_Report/getProductivityData.action?id="+value+"&fromDate="+fromDate+"&toDate="+toDate+"&deptId="+deptId+"&subdeptId="+subdeptId+"&byDeptId="+byDept+"&dataFor=Category&moduleName="+moduleName+"&searchField=Missed",
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
		var dialogTitle="Category Productivity ";
		$("#dialogOpen").dialog('open');
		var fromDate=$("#fromDate").val();
		var subdeptId=$("#subdeptId").val();
		var toDate=$("#toDate").val();
		var deptId=$("#deptId").val();
		var byDept =$("#byDeptId").val();
		var moduleName=$("#moduleName").val();
		$("#dialogOpen").dialog({title: dialogTitle});
		$("#dialogOpen").html("<br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Analytical_Report/getProductivityData.action?id="+value+"&fromDate="+fromDate+"&toDate="+toDate+"&deptId="+deptId+"&subdeptId="+subdeptId+"&byDeptId="+byDept+"&dataFor=Category&moduleName="+moduleName+"&searchField=Snooze",
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
		var dialogTitle="Category Productivity ";
		$("#dialogOpen").dialog('open');
		var fromDate=$("#fromDate").val();
		var subdeptId=$("#subdeptId").val();
		var toDate=$("#toDate").val();
		var deptId=$("#deptId").val();
		var byDept =$("#byDeptId").val();
		var moduleName=$("#moduleName").val();
		$("#dialogOpen").dialog({title: dialogTitle});
		$("#dialogOpen").html("<br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Analytical_Report/getProductivityData.action?id="+value+"&fromDate="+fromDate+"&toDate="+toDate+"&deptId="+deptId+"&subdeptId="+subdeptId+"&byDeptId="+byDept+"&dataFor=Category&moduleName="+moduleName+"&searchField=Ignore",
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
<s:hidden id="byDeptId" name="byDeptId" value="%{byDeptId}"/>
<s:hidden id="moduleName" name="moduleName" value="%{moduleName}"/>

<s:url id="analysisConf_URL" action="viewAnalysisDeatil" escapeAmp="false">
<s:param name="dataFor" value="%{dataFor}"></s:param>
<s:param name="deptId" value="%{deptId}"/>
<s:param name="subdeptId" value="%{subdeptId}"/>
<s:param name="fromDate" value="%{fromDate}"></s:param>
<s:param name="toDate" value="%{toDate}"></s:param>
<s:param name="byDeptId" value="%{byDeptId}"></s:param>
<s:param name="moduleName" value="%{moduleName}"></s:param>
</s:url>
<sjg:grid 
		  id="cateProductivity"
          href="%{analysisConf_URL}"
          gridModel="feedbackList"
          navigator="true"
          navigatorAdd="false"
          navigatorView="true"
          navigatorDelete="false"
          navigatorEdit="false"
          navigatorSearch="true"
          rowList="10,25,50"
          viewrecords="true"       
          pager="true"
          rownumbers="-1"
          pagerButtons="true"
          pagerInput="true"   
          multiselect="true"  
          loadonce="%{loadonce}"
          loadingText="Requesting Data..."  
          rowNum="12"
          autowidth="true"
          navigatorEditOptions="{height:230,width:400}"
          navigatorSearchOptions="{sopt:['eq','cn']}"
          navigatorEditOptions="{reloadAfterSubmit:true}"
          shrinkToFit="false"
          userDataOnFooter="true"
          footerrow="true"
          >
		  <sjg:gridColumn 
		      				name="feedId"
		      				index="feedId"
		      				title="feed Id"
		      				width="80"
		      				align="center"
		      				hidden="true"
		      				key="true"
		 					/>
		 
		 <sjg:gridColumn 
		      				name="feedback_catg"
		      				index="feedback_catg"
		      				title="Category"
		      				width="155"
		      				align="center"
		 					/>
		 					
		 <sjg:gridColumn 
		      				name="feedback_subcatg"
		      				index="feedback_subcatg"
		      				title="Sub Category"
		      				width="155"
		      				align="center"
		 					/>
		 
		 <sjg:gridColumn 
		      				name="counter"
		      				index="counter"
		      				title="Total"
		      				width="90"
		      				align="center"
		      				formatter="formatCounter"
		 					/>
		  <sjg:gridColumn 
		      				name="onTime"
		      				index="onTime"
		      				title="On Time"
		      				width="90"
		      				align="center"
		      				formatter="formatOntime"
		      				
		 					/>
		 					
		  <sjg:gridColumn 
		      				name="offTime"
		      				index="offTime"
		      				title="Off Time"
		      				width="90"
		      				align="center"
		      				formatter="formatOfftime"
		 					/>
		 					
		   <sjg:gridColumn 
		      				name="missed"
		      				index="missed"
		      				title="Missed"
		      				width="90"
		      				align="center"
		      				formatter="formatMissed"
		 					/>
		 					
		  <sjg:gridColumn 
		      				name="snooze"
		      				index="snooze"
		      				title="Snooze"
		      				width="90"
		      				align="center"
		      				formatter="formatSnooze"
		 					/>	
		 					
		 <sjg:gridColumn 
		      				name="ignore"
		      				index="ignore"
		      				title="Ignore"
		      				width="90"
		      				align="center"
		      				formatter="formatIgnore"
		 					/>
		 										
		 <sjg:gridColumn 
		      				name="perOnTime"
		      				index="perOnTime"
		      				title="On Time (%)"
		      				width="90"
		      				align="center"
		 					/>
		 					
		  <sjg:gridColumn 
		      				name="perOffTime"
		      				index="perOffTime"
		      				title="Off Time (%)"
		      				width="90"
		      				align="center"
		 					/>
		 					
		   <sjg:gridColumn 
		      				name="perMissed"
		      				index="perMissed"
		      				title="Missed (%)"
		      				width="90"
		      				align="center"
		 					/>				
		  <sjg:gridColumn 
		      				name="graph"
		      				index="graph"
		      				title="Graph"
		      				width="50"
		      				align="center"
		      				formatter="formatImage"
		 					/>
  </sjg:grid>

</body>
</html>