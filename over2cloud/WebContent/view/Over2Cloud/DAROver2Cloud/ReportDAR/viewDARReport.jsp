<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<link   type="text/css" href="<s:url value="/css/style.css"/>" rel="stylesheet" />
<script type="text/javascript" src="<s:url value="/js/task_js/task.js"/>"></script>
<script type="text/javascript">
function setAttachment(cellvalue, options, row) {
	return "<a href='#' onClick='javascript:openDialog("+row.id+")'>" + cellvalue + "</a>";
}
getReportDataDAR();
function openDialog(id)
{
}
</script>
</head>
<body>
<div class="list-icon">
	 <div class="head">Activity</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div><div class="head">Report</div> 
</div>
<div style=" float:left; width:100%;">
 <div class="clear"></div>
  <div class="clear"></div>
    <div style=" float:left; padding:10px 1%; width:98%;">
    <div class="listviewBorder">
    <div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
	<div class="pwie">
	
	<table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%"><tbody><tr></tr><tr><td></td></tr><tr>
	<td>
	 
<table class="floatL" border="0" cellpadding="0" cellspacing="0">
		<tbody>
		<tr>
		<td class="pL10"> 
			 <sj:a id="searchButton"  title="Search" buttonIcon="ui-icon-search" cssStyle="height:25px; width:32px"  cssClass="button" button="true"  onclick="searchRow()"></sj:a>
			 <sj:a id="refButton"  title="Refresh" buttonIcon="ui-icon-refresh" cssStyle="height:25px; width:32px"  cssClass="button" button="true"  onclick="getData('Refresh');"></sj:a>
		     
		     <sj:datepicker cssStyle="height: 16px; width: 60px;" value="%{fromDate}"  cssClass="button" id="fromDate" name="fromDate" size="20" maxDate="0"  readonly="true"   yearRange="2013:2050" showOn="focus" displayFormat="dd-mm-yy" Placeholder="Select From Date"/>
		     <sj:datepicker cssStyle="height: 16px; width: 60px;" value="today" cssClass="button" id="toDate" name="toDate" size="20"   readonly="true" yearRange="2013:2050" showOn="focus" displayFormat="dd-mm-yy" Placeholder="Select To Date"/>
		     <s:select  
	    		id					=		"allotedTo"
	    		name				=		"allotedTo"
	    		
	    		list				=		"allotedTo"
	      		theme				=		"simple"
	      		cssClass			=		"button"
	      		cssStyle			=		"height: 28px;margin-top: 3px;margin-left: 3px;width: 110px;"
	      	 >
	      	 </s:select>
		     <s:select  
		    	id					=		"clientFor"
		    	name				=		"clientFor"
		    	headerKey           =       "-1"
	    		headerValue         =		"All Client"
		    	list				=		"#{'PC':'Prospect Client','EC':'Existing Client','PA':'Prospect Associate','EA':'Existing Associate','IN':'Internal','N':'Other'}"
      		    theme  				=		"simple"
      		    cssClass			=		"button"
      		    cssStyle			=		"height: 28px;width: 110px;"
      		 >
     	     </s:select>
     	    </td>
		
     	    <td class="pL10"> 
     	    <s:select  
	    	id					=		"tType"
	    	name				=		"tType"
	    	headerKey           =       "-1"
	        headerValue         =		"All Task Type"
	    	list				=		"taskType"
	      	theme				=		"simple"
	      	cssClass			=		"button"
	      	cssStyle			=		"height: 28px;margin-top: 3px;margin-left: -7px;width: 110px;"
	       >
	      </s:select>
	     	</td> 
	      
	      <s:select  
	    	id					=		"taskPriority"
	    	name				=		"taskPriority"
	    	headerKey           =       "-1"
	        headerValue         =		"All Task Priority"
	    	list				=		"#{'Low':'Low','Medium':'Medium','High':'High'}"
	      	theme				=		"simple"
	      	cssClass			=		"button"
	      	cssStyle			=		"height: 28px;margin-top: 3px;margin-left: 3px;width: 110px;"
	       >
	      </s:select>
	      <s:select  
	    	id					=		"workStatus"
	    	name				=		"workStatus"
	    	headerKey           =       "-1"
	        headerValue         =		"All Status"
	    	list				=		"#{'Pending':'Pending','Partially Done':'Partially Done','Done':'Done','Snooze':'Snooze'}"
	      	theme				=		"simple"
	      	cssClass			=		"button"
	      	cssStyle			=		"height: 28px;margin-top: 3px;margin-left: 3px;width: 110px;"
	       >
	      </s:select>
		  <sj:a  button="true" cssClass="button" cssStyle="height: 27px;margin-left: 4px;" onclick="getReportDataDAR();">OK</sj:a>
		</tr>
		  <%-- <s:textfield name="searchData" id="searchData" cssClass="button" cssStyle="width: 54%;"/> --%> 
		</tbody>
		</table>
	</td>
	  <td class="alignright printTitle">
	   <sj:a  button="true" cssClass="button" cssStyle="height:25px; width:32px"  title="Download" buttonIcon="ui-icon-arrowstop-1-s" onclick="getCurrentColumn('downloadExcel','darSubmission','downloadColumnDarSub','downloadDarSubColumnDiv');" />
	 </td>   
	</tr></tbody>
	</table>
	</div>
	</div>
    </div>
<div id="resultReportDataDar">
</div>
</div>
</div>
</body>
<script type="text/javascript">
var row=0;
$.subscribe('rowselect', function(event, data) {
	row = event.originalEvent.id;
});	
function editRow()
{
	if(row==0)
	{
		alert("Please select atleast one row.");
	}
	jQuery("#gridedittable22").jqGrid('editGridRow', row ,{height:170, width:425,reloadAfterSubmit:true,closeAfterEdit:true,top:0,left:350,modal:true});
}
function deleteRow()
{
	if(row==0)
	{
		alert("Please select atleast one row.");
	}
	$("#gridedittable22").jqGrid('delGridRow',row, {height:120,reloadAfterSubmit:true,top:0,left:350,modal:true});
}
function searchRow()
{
	 $("#gridedittable22").jqGrid('searchGrid', {sopt:['eq','cn']} );
}
function reloadRow()
{
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/DAROver2Cloud/darView.action",
	    success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });
}
</script>
</html>