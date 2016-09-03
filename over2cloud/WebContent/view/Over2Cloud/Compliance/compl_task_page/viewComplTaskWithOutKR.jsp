<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<%
	String userRights = session.getAttribute("userRights") == null ? "" : session.getAttribute("userRights").toString();
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script type="text/javascript" src="<s:url value="/js/compliance/ComplianceMenuCtrl.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/helpdesk/common.js"/>"></script>

<meta   http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
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
	jQuery("#gridedittable").jqGrid('editGridRow', row ,{height:230, width:425,reloadAfterSubmit:false,closeAfterEdit:true,top:0,left:350,modal:true});
}

function deleteRow()
{
	if(row==0)
	{
		alert("Please select atleast one row.");
	}
	$("#gridedittable").jqGrid('delGridRow',row, {height:120, width:425,reloadAfterSubmit:true,closeAfterEdit:false,top:150,left:350,modal:true});
}
function searchRow()
{
	 $("#gridedittable").jqGrid('searchGrid', {sopt:['eq','cn']} );
}
function reloadRow()
{
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/Compliance/compl_task_page/beforeViewComplTaskAction.action?modifyFlag=1&deleteFlag=0",
	    success : function(data) 
	    {
			$("#"+"data_part").html(data);
	    },
	    error: function() 
	    {
            alert("error");
        }
	 });
	var grid = $("#gridedittable");
	grid.trigger("reloadGrid",[{current:true}]);
}
$.subscribe('addKRInTaskName',function(event,data)
		{
			
			var rowObject=jQuery("#gridedittable").jqGrid('getGridParam','selrow');
			var taskId = jQuery("#gridedittable").jqGrid('getCell',rowObject,'id');
			if(taskId=="")
			{
				alert("Please Select One Row.");
			}
			else
			{
				$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
				$.ajax({
				    type : "post",
				    url : "view/Over2Cloud/Compliance/compl_task_page/beforeAddKRCompTipInTask.action?taskId="+taskId,
				    success : function(data) 
				    {
						$("#data_part").html(data);
				    },
				    error: function() 
				    {
			            alert("error");
			        }
					 });
			}
			
		}
		);

function getKRName(subGroupId)
{
	 $.ajax({
		    type : "post",
		    url : "view/Over2Cloud/Compliance/compl_task_page/getKRNameAction.action?subGroupId="+subGroupId,
		    success : function(data) 
		    {
				$("#krNameDiv").html(data);
		    },
		    error: function() 
		    {
	            alert("error");
	        }
		 });
}
$("#viewtaskname").click(function() {
    $("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
    $.ajax({
        type : "post",
        url : "view/Over2Cloud/Compliance/compl_task_page/beforeViewComplTaskAction.action",
        success : function(subdeptdata) {
            $("#" + "data_part").html(subdeptdata);
        },
        error : function() {
            alert("error");
        }
    });
});
</script>

</head>
<body>
<sj:dialog
          id="krCompletionTip"
          showEffect="slide"
          hideEffect="explode"
          autoOpen="false"
          title="Take Action on Compliance"
          modal="true"
          closeTopics="closeEffectDialog"
          width="900"
          height="350"
          >
</sj:dialog>
<div class="clear"></div>
<div class="middle-content">
<div class="list-icon">
	 <div class="head">
	 Un-Mapped KR </div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div>
	 <div class="head">View</div> 
</div>
<div class="clear"></div>
<div class="listviewBorder" style="margin-top: 10px;width: 97%;margin-left: 20px;" align="center">
<!-- Code For Header Strip -->
<div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
<div class="pwie">
	<table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%">
		<tbody>
			<tr>
				  <td>
					  <table class="floatL" border="0" cellpadding="0" cellspacing="0">
					    <tbody><tr><td class="pL10">   
					     <%if(userRights.contains("tana_ADD")) {%>
								<sj:a id="addButton"  button="true"  buttonIcon="ui-icon-plus" cssClass="button" onClickTopics="addKRInTaskName">Add</sj:a>
	      				 <%}%>
	      				 
					      </td>
					      <td>
                                  <sj:a id="addButton"  button="true"  cssClass="button" id="viewtaskname" >Back</sj:a> 
                          </td>
					      </tr></tbody>
					  </table>
				  </td>
				  <td class="alignright printTitle">
				   
				  </td>
			</tr>
		</tbody>
	</table>
</div>
</div>
<div style="overflow: scroll; height: 430px;">
<s:url id="viewTaskWithoutKR" action="viewTaskWithoutKR" />
<sjg:grid 
		  id="gridedittable"
          href="%{viewTaskWithoutKR}"
          gridModel="masterViewList"
          navigator="true"
          navigatorAdd="false"
          navigatorView="true"
          navigatorSearch="true"
          navigatorDelete="true"
          navigatorEdit="true"
          groupField="['taskType']"
          groupColumnShow="[false]"
          groupCollapse="true"
          groupText="['<b>{0}-Total Task {1}</b>']"
          rowList="100,500,1000"
          rowNum="100"
          viewrecords="true"       
          pager="true"
          pagerButtons="true"
          pagerInput="true"   
          rownumbers="true"
          loadingText="Requesting Data..."  
          navigatorViewOptions="{height:230,width:400}"
          editurl="%{viewModifyTaskType}"
          navigatorEditOptions="{closeAfterEdit:true,reloadAfterSubmit:true}"
          navigatorSearchOptions="{sopt:['eq','cn']}"
          shrinkToFit="false"
          autowidth="true"
          loadonce="true"
          multiselect="false"
          onSelectRowTopics="rowselect"
          >
		<s:iterator value="masterViewProp" id="masterViewProp" >  
		<s:if test="colomnName=='departName' || colomnName=='completion' || colomnName=='taskType'">
		<sjg:gridColumn 
			name="%{colomnName}"
			index="%{colomnName}"
			title="%{headingName}"
			width="131"
			align="%{align}"
			editable="%{editable}"
			formatter="%{formatter}"
			search="%{search}"
			hidden="%{hideOrShow}"
			/>
		
		</s:if>
		<s:else>
		<sjg:gridColumn 
			name="%{colomnName}"
			index="%{colomnName}"
			title="%{headingName}"
			width="285"
			align="%{align}"
			editable="%{editable}"
			formatter="%{formatter}"
			search="%{search}"
			hidden="%{hideOrShow}"
			/>
		</s:else>
			
		</s:iterator>  
</sjg:grid>
</div>
</div>
</div>
</body>
</html>