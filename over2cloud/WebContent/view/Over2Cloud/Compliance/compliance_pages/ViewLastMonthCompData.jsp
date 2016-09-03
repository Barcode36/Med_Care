<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript" src="<s:url value="/js/js.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/compliance/compliance.js"/>"></script>
<SCRIPT type="text/javascript">
function downloadDoc(cellvalue, options, rowObject) 
{
	var context_path = '<%=request.getContextPath()%>';
	//if(rowObject.document_config_1=='NA' && rowObject.document_config_2=='NA')
	//{
		//return "<img src='"+ context_path +"/images/noDoc.jpg' alt='No Record' />";
	//}
	//else
	//{
		return "<a href='#' onClick='complianceDownloadAction("+rowObject.id+")'><img src='"+ context_path +"/images/docDownlaod.jpg' alt='Download'></a>";
	//}
}
function complianceDownloadAction(compReportId) 
{
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/Compliance/compliance_pages/docDownloadAction.action?compReportId="+compReportId,
	    success : function(data) 
	    {
			$("#downloadLoc").html(data);
		},
	    error: function()
	    {
	        alert("error");
	    }
	 });
}
function downloadLastMonth()
{
	var sel_id;
	sel_id = $("#gridCompReport").jqGrid('getGridParam','selarrrow');
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
		    url : "/over2cloud/view/Over2Cloud/Compliance/compliance_pages/sendReportMailConfirmAction.action?selectedId="+sel_id,
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

$.subscribe('getPrintData', function(event,data) 
{
	var id;
	id = $("#gridCompReport").jqGrid('getGridParam','selarrrow');
	if(id=="")
	{
	     alert("Please select atleast one check box...");        
	     return false;
	}
	else
	{
 	var deptName = jQuery("#gridCompReport").jqGrid('getCell',id,'dept.deptName');
 	var taskType = jQuery("#gridCompReport").jqGrid('getCell',id,'ctyp.taskType');
 	var actionBy = jQuery("#gridCompReport").jqGrid('getCell',id,'userAC.name');
 	var actionTakenDate = jQuery("#gridCompReport").jqGrid('getCell',id,'compReport.actionTakenDate');
	var taskName = jQuery("#gridCompReport").jqGrid('getCell',id,'ctn.taskName');
	var frequency = jQuery("#gridCompReport").jqGrid('getCell',id,'comp.frequency');
	var dueDate = jQuery("#gridCompReport").jqGrid('getCell',id,'compReport.dueDate');
	var dueTime = jQuery("#gridCompReport").jqGrid('getCell',id,'compReport.dueTime');
	var reminderFor = jQuery("#gridCompReport").jqGrid('getCell',id,'comp.reminder_for');
	var status = jQuery("#gridCompReport").jqGrid('getCell',id,'compReport.actionTaken');
	$("#printSelect").load("<%=request.getContextPath()%>/view/Over2Cloud/Compliance/compliance_pages/printCompInfo?deptName="+deptName.split(" ").join("%20")+"&taskName="+taskName.split(" ").join("%20")+"&frequency="+frequency.split(" ").join("%20")+"&dueDate="+dueDate.split(" ").join("%20")+"&reminderFor="+reminderFor.split(" ").join("%20")+"&status="+status.split(" ").join("%20")+"&taskType="+taskType.split(" ").join("%20")+"&actionBy="+actionBy.split(" ").join("%20")+"&actionTakenDate="+actionTakenDate.split(" ").join("%20")+"&dueTime="+dueTime.split(" ").join("%20"));
	$("#printSelect").dialog('open');
	}
});

function openDialog()
{
	var sel_id;
	sel_id = $("#gridCompReport").jqGrid('getGridParam','selarrrow');
	
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
		    url : "/over2cloud/view/Over2Cloud/Compliance/compliance_pages/beforeSendMail.action?selectedId="+sel_id+"&actionName=sendReportMailConfirmAction",
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
function searchRow()
{
	 $("#gridCompReport").jqGrid('searchGrid', {sopt:['eq','cn']} );
}
function reloadRow()
{
	var grid = $("#gridCompReport");
	grid.trigger("reloadGrid",[{current:true}]);
}
</SCRIPT>
</head>
<body>
<sj:dialog id="printSelect" title="Print Ticket" autoOpen="false"  modal="true" height="320" width="700" showEffect="drop"></sj:dialog>
<sj:dialog
          id="takeActionGrid"
          showEffect="slide"
          hideEffect="explode"
          autoOpen="false"
          title="Take Action on Operation Task"
          modal="true"
          closeTopics="closeEffectDialog"
          width="1000"
          height="400"
          >
</sj:dialog>

<center>
<div id="downloadLoc"></div></center><br>
<div class="listviewBorder" style="margin-top: 10px;width: 97%;margin-left: 20px;" align="center">
<div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
<div class="pwie">
	<table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%">
		<tbody>
			<tr>
				  <td>
					  <table class="floatL" border="0" cellpadding="0" cellspacing="0">
					    <tbody><tr><td class="pL10">   
					   <sj:a id="searchButton"  title="Search" buttonIcon="ui-icon-search" cssStyle="height:25px; width:32px"  cssClass="button" button="true"  onclick="searchRow()"></sj:a>
			          <sj:a id="refButton"  title="Refresh" buttonIcon="ui-icon-refresh" cssStyle="height:25px; width:32px"  cssClass="button" button="true"  onclick="reloadRow()"></sj:a>
		     
					      </td></tr></tbody>
					  </table>
				  </td>
				  <!-- Block for insert Right Hand Side Button -->
				  <td class="alignright printTitle">
				   	<sj:a id="sendButton111" buttonIcon="ui-icon-arrowthickstop-1-s" cssClass="button" button="true" title="Download" cssStyle="height:25px; width:32px" onclick="downloadLastMonth();"></sj:a>
					<sj:a  button="true" cssClass="button" buttonIcon="ui-icon-print" cssStyle="height:25px; width:32px" title="Print" onClickTopics="getPrintData"></sj:a>
				 	<sj:a id="sendMailButton"  cssClass="button" buttonIcon="ui-icon-mail-closed" button="true" cssStyle="height:25px; width:32px" title="Send Mail" onclick="openDialog();"></sj:a>
				
				  </td>
			</tr>
		</tbody>
	</table>
</div>
</div>
<div style="overflow: scroll; height: 440px;">
	<s:url id="viewCompLastMonth" action="LastMonthCompAction" escapeAmp="false">
	<s:param name="deptId"  value="%{deptId}" />
	<s:param name="actionStatus"  value="%{actionStatus}" />
	<s:param name="compId"  value="%{compId}" />
	</s:url>
	    <sjg:grid 
		  id="gridCompReport"
          href="%{viewCompLastMonth}"
          gridModel="masterViewList"
          navigator="true"
          navigatorAdd="false"
          navigatorView="true"
          navigatorEdit="false"
          navigatorDelete="false"
          navigatorSearch="true"
          rowList="15,20,25,50,100"
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
          loadonce="true"
          autowidth="true"
          rownumbers="false"
          >
		<s:iterator value="masterViewProp" id="masterViewProp" >  
		<sjg:gridColumn 
							name="%{colomnName}"
							index="%{colomnName}"
							title="%{headingName}"
							width="140"
							align="%{align}"
							editable="%{editable}"
							formatter="%{formatter}"
							search="%{search}"
							hidden="%{hideOrShow}"
		/>
		</s:iterator>  
</sjg:grid>
</div>
</div>
<br>
<br>
</body>
</html>
