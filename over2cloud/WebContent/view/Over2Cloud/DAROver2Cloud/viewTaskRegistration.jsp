
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<%String userRights = session.getAttribute("userRights") == null ? "" : session.getAttribute("userRights").toString(); %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script type="text/javascript" src="<s:url value="/js/task_js/task.js"/>"></script>
<script type="text/javascript">

function setAttachment(cellvalue, options, row) 
{
	return "<a href='#' onClick='javascript:openDialog("+row.id+")'>" + cellvalue + "</a>";
}
function openDialog(id)
{
	$('#filePath').val(id);
	$("#downloadFile").submit();
}
var timePicker = function(elem) {
    $(elem).timepicker({timeFormat: 'hh:mm'});
    $('#time_picker_div').css("z-index", 2000);
}
var datePicker = function(elem) {
    $(elem).datepicker({dateFormat:'dd-mm-yy'});
    $('#date_picker_div').css("z-index", 2000);
}
</SCRIPT>

<SCRIPT type="text/javascript">
	 /* * Format a Column as Image */
	   function formatImage(cellvalue, options, row) 
	    {
		 var context_path = '<%=request.getContextPath()%>';
		 cellvalue="Search";
	 	return "&nbsp&nbsp<img title='Download Document' src='"+ context_path +"/images/download.png' onClick='openDialog("+row.id+");' />"
	 	;}

	  function formatLink(cellvalue, options, rowObject) {
		  return "<a href='myEdit.action?id="+cellvalue +"'>"+cellvalue+"</a>"; }
</SCRIPT>
</head>
<body>
<s:form action="download" id="downloadFile" theme="css_xhtml" name="forms">
<s:hidden name="fileName" id="filePath"/>
</s:form>
<div class="list-icon">
	 <div class="head">Project Registration </div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div><div class="head">View</div> 
</div>
<div style=" float:left; width:100%;">

<div id="time_picker_div" style="display:none">
     <sj:datepicker  value="today" id="date3" name="date3" timepicker="true" timepickerOnly="true"   timepickerGridHour="4"  timepickerGridMinute="10" timepickerStepMinute="10" />
</div>
<div id="date_picker_div" style="display:none">
 <sj:datepicker name="date1" id="date1" value="datetoday" cssStyle="margin:0px 0px 10px 0px"   changeMonth="true" cssStyle="margin:0px 0px 10px 0px" changeYear="true" yearRange="1890:2020" showOn="focus" displayFormat="dd-mm-yy" cssClass="form_menu_inputtext" />
 </div>
  <s:url id="taskResgistrationGridDataView" action="taskResgistrationGridDataView"> </s:url>
  <s:url id="modifyTaskRegisData" action="modifyTaskRegisData" />
  <s:url id="employeeList" action="employeeDrop.action" />
 
  <%if(userRights.contains("tare_VIEW")){ %>
  <div class="clear"></div>
  <div class="clear"></div>
   
    <div class="listviewBorder" style="margin-top: 10px;width: 97%;margin-left: 20px;" align="center">
    <div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
	<div class="pwie">
	<table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%"><tbody><tr></tr><tr><td></td></tr><tr><td> <table class="floatL" border="0" cellpadding="0" cellspacing="0"><tbody><tr><td class="pL10"> 
     <%if(userRights.contains("tare_MODIFY")){ %>
   		<sj:a id="editButton" title="Edit"  buttonIcon="ui-icon-pencil" cssClass="button" cssStyle="height:25px; width:32px" button="true" onclick="editRow()"></sj:a>	
	 <%} %>
	   <%if(userRights.contains("tare_DELETE")){ %>
		<sj:a id="delButton" title="Deactivate" buttonIcon="ui-icon-trash" cssClass="button" cssStyle="height:25px; width:32px" button="true"  onclick="deleteRow()"></sj:a>
	<%} %>
	<sj:a id="searchButton" title="Search" buttonIcon="ui-icon-search" cssClass="button" cssStyle="height:25px; width:32px" button="true"  onclick="searchRow()"></sj:a>
	<sj:a id="refButton" title="Refresh" buttonIcon="ui-icon-refresh" cssClass="button" cssStyle="height:25px; width:32px" button="true"  onclick="reloadRow()"></sj:a>
	
	</td></tr></tbody></table>
	</td>
	<td class="alignright printTitle">
	 <!--<sj:a id="downloadExceProc"  button="true" buttonIcon="ui-icon-arrowthickstop-1-s" cssClass="button" onclick="getCurrentColumn('downloadExcel','taskRegistration','downloadColumnTaskRegistration','downloadTaskRegistrationColumnDiv')">Download</sj:a>
	-->
	<sj:a  button="true" cssClass="button" cssStyle="height:25px; width:32px"  title="Download" buttonIcon="ui-icon-arrowstop-1-s" onclick="getCurrentColumn('downloadExcel','taskRegistration','downloadColumnTaskRegistration','downloadTaskRegistrationColumnDiv');" />
	<%if(userRights.contains("tare_ADD")){ %>
<sj:a id="addButton"  button="true"  buttonIcon="ui-icon-plus" cssClass="button" onclick="createTask('main');">Add</sj:a>
<sj:a id="addAllotButton"  button="true"  buttonIcon="ui-icon-plus" cssClass="button" onclick="createTaskAllot('main');">Allot</sj:a>
<%} %>
	</td>   
	</tr></tbody></table>
	</div>
	</div>
  
   
     <div style="overflow: scroll; height: 430px;">
<sjg:grid 
		  id="gridedittable22"
          href="%{taskResgistrationGridDataView}"
          gridModel="viewList"
          loadonce="true"
          navigator="true"
          navigatorAdd="false"
          navigatorView="true"
          navigatorEdit="true"
          editurl="%{modifyTaskRegisData}"
          navigatorDelete="true"
          navigatorSearch="true"
          rowList="15,20,25"
          rownumbers="-1"
          viewrecords="true"       
          pager="true"
          pagerButtons="true"
          pagerInput="true"   
          loadingText="Requesting Data..."  
          rowNum="15"
          autowidth="true"
          navigatorSearchOptions="{sopt:['eq','cn']}"
          shrinkToFit="false"
          navigatorViewOptions="{width:100}"
          onSelectRowTopics="rowselect"
          >
         <sjg:gridColumn 
            name="imagename"
            name="imagename"
     		title="Download"
     		editable="false"
     		sortable="false"
    		align="center"
    		width="50"
    		formatter="formatImage"
    		/>
		<s:iterator value="taskTypeViewProp" id="taskTypeViewProp" status="test" >  
		<s:if test="colomnName == 'completion' || colomnName=='technical_Date' || colomnName=='functional_Date'">
		<sjg:gridColumn 
	    name="%{colomnName}"
		index="%{colomnName}"
		title="%{headingName}"
		width="%{width}"
		align="%{align}"
		editable="%{editable}"
		search="%{search}"
		hidden="%{hideOrShow}"
		formatter="%{formatter}"
		editoptions="{dataInit:datePicker}"
		/>
		</s:if>
		<s:elseif test="colomnName=='functional_Time'" >
		<sjg:gridColumn 
	    name="%{colomnName}"
		index="%{colomnName}"
		title="%{headingName}"
		width="%{width}"
		align="%{align}"
		editable="%{editable}"
		search="%{search}"
		hidden="%{hideOrShow}"
		editoptions="{dataInit:timePicker}"
		/>
		</s:elseif>
		
		<s:elseif test="colomnName=='guidance'" >
		<sjg:gridColumn 
	    name="%{colomnName}"
		index="%{colomnName}"
		title="%{headingName}"
		width="%{width}"
		align="%{align}"
		editable="%{editable}"
		search="%{search}"
		hidden="%{hideOrShow}"
        edittype='select' 
        editoptions="{dataUrl:'%{employeeList}'}"
		/>
		</s:elseif>
		<s:elseif test="colomnName=='techniacal_Time'" >
		<sjg:gridColumn 
	    name="%{colomnName}"
		index="%{colomnName}"
		title="%{headingName}"
		width="%{width}"
		align="%{align}"
		editable="%{editable}"
		search="%{search}"
		hidden="%{hideOrShow}"
		editoptions="{dataInit:timePicker}"
		/>
		</s:elseif>
		<s:else>
			 <sjg:gridColumn 
	    name="%{colomnName}"
		index="%{colomnName}"
		title="%{headingName}"
		width="%{width}"
		align="%{align}"
		editable="%{editable}"
		search="%{search}"
		hidden="%{hideOrShow}"
		formatter="%{formatter}"
		sortable="true"
		/>
		</s:else>
		</s:iterator> 
		
</sjg:grid>
</div>
</div>  </div>
<%} %>
</body>
<sj:dialog 
		id="downloadColumnTaskRegistration" 
		modal="true" 
		effect="slide" 
		autoOpen="false" 
		width="300" 
		height="530" 
		title="Task Detail Column Name" 
		loadingText="Please Wait" 
		overlayColor="#fff" 
		overlayOpacity="1.0" 
		position="['center','center']" >
	<div id="downloadTaskRegistrationColumnDiv"></div>
</sj:dialog>
<script type="text/javascript">
var row=0;
$.subscribe('rowselect', function(event, data) 
{
	row = event.originalEvent.id;
});	
function editRow()
{
	jQuery("#gridedittable22").jqGrid('editGridRow', row ,{height:150, width:425,reloadAfterSubmit:true,closeAfterEdit:true,top:0,left:350,modal:true});
}
function deleteRow()
{
	$("#gridedittable22").jqGrid('delGridRow',row, {height:120,reloadAfterSubmit:true,top:0,left:350,modal:true});
}

function searchRow()
{
	 $("#gridedittable22").jqGrid('searchGrid', {sopt:['eq','cn']} );
}
function reloadRow()
{
	var grid = $("#gridedittable22");
	grid.trigger("reloadGrid",[{current:true}]);
}

</script>
</html>