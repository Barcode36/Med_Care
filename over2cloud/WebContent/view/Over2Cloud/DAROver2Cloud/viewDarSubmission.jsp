<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<%String userRights = session.getAttribute("userRights") == null ? "" : session.getAttribute("userRights").toString(); %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<s:url  id="contextz" value="/template/theme" />
<link   type="text/css" href="<s:url value="/css/style.css"/>" rel="stylesheet" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script type="text/javascript" src="<s:url value="/js/task_js/task.js"/>"></script>
<script type="text/javascript">
function createTaskType()
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
    $("#data_part").load("<%=request.getContextPath()%>/view/Over2Cloud/DAROver2Cloud/beforeDarAddAction.action");
}

function setAttachment(cellvalue, options, row) {
	return "<a href='#' onClick='javascript:openDialog("+row.id+")'>" + cellvalue + "</a>";
}
function openDialog(id)
{
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/DAROver2Cloud/getAttachment.action?id="+id,
	    success : function(clientdata) 
	    {
		       $("#"+"download").html(clientdata);
	    },
	   error: function() {
	        alert("error");
	    }
	 }); 
}
function getStatusForm()
{
	    var taskid            = jQuery("#gridedittable22").jqGrid('getGridParam', 'selrow');
	    var taskName          = jQuery("#gridedittable22").jqGrid('getCell',taskid, 'tasknameee');
	    var allotedby         = jQuery("#gridedittable22").jqGrid('getCell',taskid, 'allotedbyy'); 
	    var allotedTo         = jQuery("#gridedittable22").jqGrid('getCell',taskid, 'allotedtoo'); 
	    var initiation_date   = jQuery("#gridedittable22").jqGrid('getCell',taskid, 'initiondate'); 
	    var completion_date   = jQuery("#gridedittable22").jqGrid('getCell',taskid, 'comlpetiondate');
	    var status            = jQuery("#gridedittable22").jqGrid('getCell',taskid, 'statuss');
	     
		$("#task_status").load("view/Over2Cloud/DAROver2Cloud/snoozeTaskAction.action?taskNamee="+taskName.split(" ").join("%20")+"&allotedbyy="+allotedby.split(" ").join("%20")+"&allotedToo="+allotedTo.split(" ").join("%20")+"&initiation_datee="+initiation_date+"&completion_datee="+completion_date+"&statuss="+status.split(" ").join("%20")+"&feedId="+taskid);
	    $("#task_status").dialog('open');

}
</script>
<SCRIPT type="text/javascript">
	 /* * Format a Column as Image */
	 function formatImage(cellvalue, options, row) {
		 var context_path = '<%=request.getContextPath()%>';
		 cellvalue="Search";
		return "<img title='Download Document' src='"+ context_path +"/images/download.png' onClick='openDialog("+row.id+");' />"+
		       "&nbsp&nbsp<img title='Snooze Task ' src='"+ context_path +"/images/snooze_lead.png' onClick='getStatusForm()' />"
	 	;}

	 function formatLink(cellvalue, options, rowObject) {
		  return "<a href='myEdit.action?id="+cellvalue +"'>"+cellvalue+"</a>"; }
</script>

</head>
<body>
<div class="list-icon">
	 <div class="head">Activity Submission  </div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div><div class="head">View</div> 
</div>
<div style=" float:left; width:100%;">
<!--<%if(userRights.contains("dasu_ADD")){ %>
<div class="rightHeaderButton">
<sj:a  button="true" onclick="createTaskType();" buttonIcon="ui-icon-plus">Add</sj:a>
</div>
<%} %>
--><div id="download" align="center"></div>
<%if(userRights.contains("dasu_VIEW")){ %>
 <div class="clear"></div>
  <div class="clear"></div>
    <div style=" float:left; padding:10px 1%; width:98%;">
    <div class="listviewBorder">
    <div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
	<div class="pwie"><table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%"><tbody><tr></tr><tr><td></td></tr><tr><td> <table class="floatL" border="0" cellpadding="0" cellspacing="0"><tbody><tr><td class="pL10"> 
    <%if(userRights.contains("dasu_MODIFY")){ %>
    <!--<sj:a id="editButton" buttonIcon="ui-icon-pencil" cssClass="button" button="true" onclick="editRow();">Edit</sj:a>	
	--><!--<sj:a id="editButton" title="Edit"  buttonIcon="ui-icon-pencil" cssClass="button" cssStyle="height:25px; width:32px" button="true" onclick="editRow()"></sj:a>
	--><%} %>
	<sj:a id="delButton" title="Deactivate" buttonIcon="ui-icon-trash" cssClass="button" cssStyle="height:25px; width:32px" button="true"  onclick="deleteRow()"></sj:a>
	<sj:a id="searchButton" title="Search" buttonIcon="ui-icon-search" cssClass="button" cssStyle="height:25px; width:32px" button="true"  onclick="searchRow()"></sj:a>
	<sj:a id="refButton" title="Refresh" buttonIcon="ui-icon-refresh" cssClass="button" cssStyle="height:25px; width:32px" button="true"  onclick="reloadRow()"></sj:a>
	</td></tr></tbody></table>
	</td>
	 <td class="alignright printTitle"><!--
	  <sj:a id="downloadExceProc"  button="true" buttonIcon="ui-icon-arrowthickstop-1-s" cssClass="button" onclick="getCurrentColumn('downloadExcel','darSubmission','downloadColumnDarSub','downloadDarSubColumnDiv')">Download</sj:a> 
	 --><sj:a  button="true" cssClass="button" cssStyle="height:25px; width:32px"  title="Download" buttonIcon="ui-icon-arrowstop-1-s" onclick="getCurrentColumn('downloadExcel','darSubmission','downloadColumnDarSub','downloadDarSubColumnDiv');" />
				            
		
	 <%if(userRights.contains("dasu_ADD")){ %>

<sj:a id="addButton"  button="true"  buttonIcon="ui-icon-plus" cssClass="button" onclick="createTaskType();">Add</sj:a>


<%} %>
	 </td>   
	</tr></tbody></table>
	</div>
	</div>
    </div>
 <div style="overflow: scroll; height: 430px;">
<s:url id="darGridDataView1" action="darGridDataViewDetails"></s:url>

<sjg:grid 
		  id="gridedittable22"
          href="%{darGridDataView1}"
          gridModel="viewList"
          loadonce="true"
          navigatorEdit="false"
          navigator="true"
          navigatorAdd="false"
          navigatorView="false"
          navigatorDelete="true"
          navigatorSearch="true"
          rowList="10,15,20,25"
          viewrecords="true"       
          pager="true"
          pagerButtons="true"
          pagerInput="true"
          loadingText="Requesting Data..." 
          rownumbers="-1" 
          rowNum="10"
          autowidth="true"
          navigatorSearchOptions="{sopt:['eq','cn']}"
          shrinkToFit="false"
          navigatorViewOptions="{width:100}"
          onSelectRowTopics="rowselect"
          >
           <sjg:gridColumn 
            name="imagename"
            name="imagename"
     		title="Actions"
     		editable="false"
     		sortable="false"
    		align="center"
    		width="50"
    		formatter="formatImage"
    		/>
		<s:iterator value="taskTypeViewProp" id="taskTypeViewProp" >  
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
		/>
		</s:iterator>  
</sjg:grid>

          <sj:dialog
          id="task_status"
          showEffect="slide"
          hideEffect="explode"
          autoOpen="false"
          title="Snooze Task Action"
          modal="true"
          closeTopics="closeEffectDialog"
          width="900"
          height="400"
          draggable="true"
    	  resizable="true"
          >
          </sj:dialog>
           
</div>
</div>
</div>
<%} %>
</body>
<sj:dialog 
		id="downloadColumnDarSub" 
		modal="true" 
		effect="slide" 
		autoOpen="false" 
		width="300" 
		height="590" 
		title="DAR Detail Column Name" 
		loadingText="Please Wait" 
		overlayColor="#fff" 
		overlayOpacity="1.0" 
		position="['center','center']" >
	<div id="downloadDarSubColumnDiv"></div>
</sj:dialog>

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