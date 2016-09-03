<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link type="text/css" href="<s:url value="/css/style.css"/>" rel="stylesheet" />
<link  type="text/css" href="<s:url value="/css/table.css"/>" rel="stylesheet" />
<script type="text/javascript" src="<s:url value="/js/js.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/compliance/compliance.js"/>"></script>
<SCRIPT type="text/javascript">
function complTakeAction(cellvalue, options, rowObject) 
{
	if(rowObject.actionStatus=='Done')
	{
		return "Done";
	}
	else
	{
		return "<a href='#' onClick='complianceTakeAction("+rowObject.id+")'><b>Take Action</b></a>";
	}
}
function complianceTakeAction(valuepassed) 
{
	   var departId=$("#departId").val();
	   var data4=$("#data4").val();
	   var frequency=$("#frequency").val();
	   var totalOrMissed=$("#totalOrMissed").val();
	   var status=$("#status").val();
	   
	   $("#takeActionGrid").load("<%=request.getContextPath()%>/view/Over2Cloud/Compliance/compliance_pages/beforeTakeAction?complID="+valuepassed+"&departId="+departId+"&data4="+data4+"&frequency="+frequency+"&totalOrMissed="+totalOrMissed+"&status="+status);
	   $("#takeActionGrid").dialog('open');
	   return false;
}
function complianceAction()
{
	var valuepassed;
	valuepassed = $("#gridedittable").jqGrid('getGridParam','selarrrow');
	var departId=$("#departId").val();
	var data4=$("#data4").val();
	var frequency=$("#frequency").val();
	var totalOrMissed=$("#totalOrMissed").val();
	var status=$("#status").val();
	if(valuepassed=="")
	{
	     alert("Please select atleast one check box...");        
	     return false;
	}
	 $("#takeActionGrid").load("<%=request.getContextPath()%>/view/Over2Cloud/Compliance/compliance_pages/beforeTakeAction?complID="+valuepassed+"&departId="+departId+"&data4="+data4+"&frequency="+frequency+"&totalOrMissed="+totalOrMissed+"&status="+status);
	 $("#takeActionGrid").dialog('open');
	   return false;
}
$.subscribe('getCompFormData', function(event,data)
{
	var id;
	id = $("#gridedittable").jqGrid('getGridParam','selarrrow');
	if(id=="")
	{
	     alert("Please select atleast one check box...");        
	     return false;
    }
	else
	{
 	var deptName = jQuery("#gridedittable").jqGrid('getCell',id,'dept.deptName');
	var taskName = jQuery("#gridedittable").jqGrid('getCell',id,'ctn.taskName');
	var taskType = jQuery("#gridedittable").jqGrid('getCell',id,'ctyp.taskType');
	var frequency = jQuery("#gridedittable").jqGrid('getCell',id,'comp.frequency');
	var dueDate = jQuery("#gridedittable").jqGrid('getCell',id,'comp.dueDate');
	var dueTime = jQuery("#gridedittable").jqGrid('getCell',id,'comp.dueTime');
	var reminderFor = jQuery("#gridedittable").jqGrid('getCell',id,'comp.reminder_for');
	var status = jQuery("#gridedittable").jqGrid('getCell',id,'actionStatus');
	$("#printSelect").load("<%=request.getContextPath()%>/view/Over2Cloud/Compliance/compliance_pages/printCompInfo?deptName="+deptName.split(" ").join("%20")+"&taskName="+taskName.split(" ").join("%20")+"&frequency="+frequency.split(" ").join("%20")+"&dueDate="+dueDate.split(" ").join("%20")+"&reminderFor="+reminderFor.split(" ").join("%20")+"&dueTime="+dueTime.split(" ").join("%20")+"&taskType="+taskType.split(" ").join("%20")+"&status="+status.split(" ").join("%20"));
	$("#printSelect").dialog('open');
	}
	  		 
});

function downloadDashData()
{
	var sel_id;
	sel_id = $("#gridedittable").jqGrid('getGridParam','selarrrow');
	if(sel_id=="")
	{
	     alert("Please select atleast one check box...");        
	     return false;
	}
	else
	{
		$("#takeActionGrid").dialog({title: 'Check Column For Download',width: 350,height: 600});
		$("#takeActionGrid").dialog('open');
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/Compliance/compliance_pages/sendMailConfirmAction.action?selectedId="+sel_id,
		    success : function(data) 
		    {
	 			$("#takeActionGrid").html(data);
			},
		   error: function() {
		        alert("error");
		    }
		 });
	}	
}
function openDialog()
{
	var sel_id;
	sel_id = $("#gridedittable").jqGrid('getGridParam','selarrrow');
	
	if(sel_id=="")
	{
	     alert("Please select atleast one check box...");        
	     return false;
	}
	else
	{
		$("#takeActionGrid").dialog({title: 'Send Mail ',width: 950,height: 250});
		$("#takeActionGrid").dialog('open');
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/Compliance/compliance_pages/beforeSendMail.action?selectedId="+sel_id+"&actionName=sendMailConfirmAction",
		    success : function(data) 
		    {
	 			$("#takeActionGrid").html(data);
			},
		   error: function() {
		        alert("error");
		    }
		 });
	}
}
function ViewCompletionTip(cellvalue, options, rowObject) 
{
	return "<a href='#' title='Download' onClick='CompletionTip("+rowObject.id+")'>View</a>";
}
function ViewKR(cellvalue, options, rowObject) 
{
	return "<a href='#' title='Download' onClick='KR("+rowObject.id+")'>View</a>";
}

function CompletionTip(taskId) 
{
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/Compliance/compl_task_page/ViewCompletionTipKRAction.action?dataId="+taskId+"&dataFor=CompletionTip&module=Action",
	    success : function(data) 
	    {
			$("#krCompletionTip").dialog({title: 'Completion Tip',width: 300,height: 200});
			$("#krCompletionTip").dialog('open');
			$("#krCompletionTip").html(data);
		},
	    error: function()
	    {
	        alert("error");
	    }
	 });
}
function KR(taskId) 
{
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/Compliance/compl_task_page/ViewCompletionTipKRAction.action?dataId="+taskId+"&dataFor=KRName&module=Action",
	    success : function(data) 
	    {
		$("#krCompletionTip").dialog({title: 'KR Name',width: 300,height: 200});
		$("#krCompletionTip").dialog('open');
		$("#krCompletionTip").html(data);
		},
	    error: function()
	    {
	        alert("error");
	    }
	 });
}
</SCRIPT>
</head>
<body>
<sj:dialog
          id="krCompletionTip"
          showEffect="slide"
          hideEffect="explode"
          autoOpen="false"
          title="Take Action on Operation Task"
          modal="true"
          closeTopics="closeEffectDialog"
          width="700"
          height="350"
          >
</sj:dialog>
<sj:dialog id="printSelect" title="Print Ticket" autoOpen="false"  modal="true" height="370" width="700" showEffect="drop"></sj:dialog>
<sj:dialog
          id="takeActionGrid"
          showEffect="slide"
          hideEffect="explode"
          autoOpen="false"
          title="Operation Task Action"
          modal="true"
          closeTopics="closeEffectDialog"
          width="1000"
          height="400"
          >
</sj:dialog>

 <div class="clear"></div>
 <div class="listviewBorder" style="margin-top: 10px;width: 97%;margin-left: 20px;" align="center">
 <div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
<div class="pwie">
	<table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%">
		<tbody>
			<tr>
				  <td>
					  <table class="floatL" border="0" cellpadding="0" cellspacing="0">
					    <tbody><tr><td class="pL10"> 
					    <s:if test="%{status == 'Done'}">
					    </s:if>
					    <s:else>
					     <sj:a id="action" cssClass="button" button="true" title="Action" onClick="complianceAction();">Action</sj:a>
					    </s:else>
					      </td></tr></tbody>
					  </table>
				  </td>
				  <td class="alignright printTitle">
					<sj:a id="sendButton111" buttonIcon="ui-icon-arrowthickstop-1-s" cssClass="button" button="true" title="Download" cssStyle="height:25px; width:32px" onclick="downloadDashData();"></sj:a>
					<sj:a  button="true" cssClass="button" buttonIcon="ui-icon-print" cssStyle="height:25px; width:32px" title="Print" onClickTopics="getCompFormData"></sj:a>
				 	<sj:a id="sendMailButton"  cssClass="button" buttonIcon="ui-icon-mail-closed" button="true" cssStyle="height:25px; width:32px" title="Send Mail" onclick="openDialog();"></sj:a>
				  </td>
			</tr>
		</tbody>
	</table>
</div>
</div>
 <div class="clear"></div>
 <div style="overflow: scroll; height: 430px;">
	<s:url id="veiwTaskType" action="getUpCommingCompliancesOnDash" escapeAmp="false">
	<s:param name="departId"  value="%{departId}" />
	<s:param name="data4"  value="%{data4}" />
	<s:param name="frequency"  value="%{frequency}" />
	<s:param name="totalOrMissed"  value="%{totalOrMissed}" />
	<s:param name="status"  value="%{status}" />
	<s:param name="compId"  value="%{compId}" />
	</s:url>
	<s:hidden id="departId" value="%{departId}" />
	<s:hidden id="data4" value="%{data4}" />
	<s:hidden id="frequency" value="%{frequency}" />
	<s:hidden id="totalOrMissed" value="%{totalOrMissed}" />
	<s:hidden id="status" value="%{status}" />
	<s:hidden id="compId" value="%{compId}" />
	    <sjg:grid 
		  id="gridedittable"
          href="%{veiwTaskType}"
          gridModel="masterViewList"
          navigator="true"
          navigatorAdd="false"
          navigatorView="true"
          navigatorEdit="false"
          navigatorDelete="false"
          navigatorSearch="true"
          rowList="15,20,25,30"
          rowNum="15"
          viewrecords="true"       
          pager="true"
          pagerButtons="true"
          pagerInput="false"   
          multiselect="true"  
          loadingText="Requesting Data..."  
          navigatorEditOptions="{height:230,width:400}"
          navigatorSearchOptions="{sopt:['eq','bw']}"
          navigatorEditOptions="{reloadAfterSubmit:true}"
          shrinkToFit="false"
          autowidth="true"
          loadonce="true"
          >
		<s:iterator value="masterViewProp" id="masterViewProp" >
		<s:if test="colomnName=='id'">  
			<sjg:gridColumn 
							name="%{colomnName}"
							index="%{colomnName}"
							title="%{headingName}"
							width="200"
							align="%{align}"
							editable="%{editable}"
							formatter="%{formatter}"
							search="%{search}"
							hidden="true"
							key="true"
		/>
		</s:if>
		<s:else>
			<sjg:gridColumn 
							name="%{colomnName}"
							index="%{colomnName}"
							title="%{headingName}"
							width="%{width}"
							align="%{align}"
							editable="%{editable}"
							formatter="%{formatter}"
							search="%{search}"
							hidden="%{hideOrShow}"
		/>
		</s:else>
		</s:iterator> 
		<sjg:gridColumn 
				name="completion_tip"
				index="completion_tip"
				title="Completion Tip"
				width="100"
				align="center"
				editable="false"
				formatter="ViewCompletionTip"
				search="false"
				hidden="false"
		    />
		    
		    <sjg:gridColumn 
				name="kr_name"
				index="kr_name"
				title="KR"
				width="100"
				align="center"
				editable="false"
				formatter="ViewKR"
				search="false"
				hidden="false"
		    />
		
   	</sjg:grid> 
</div>
</div>
	<!--<sj:submit value=" Send Mail " button="true" cssStyle="height: 30px;width: 10%;" onClickTopics="getSelectedId"/><sj:submit value=" Print  " button="true" cssStyle="height: 30px;width: 10%;" onClickTopics="getCompFormData"/><sj:submit id="Download" value="Download" button="true" cssStyle="height: 30px;width: 10%;" onClickTopics="getCompFormData"/>
--><center>
</center>
</body>
</html>
