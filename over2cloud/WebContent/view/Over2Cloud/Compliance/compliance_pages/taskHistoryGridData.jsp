<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
<html>
<head>
<meta   http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link type="text/css" href="<s:url value="/css/style.css"/>" rel="stylesheet" />
<script type="text/javascript" src="<s:url value="/js/compliance/compliance.js"/>"></script>
<script type="text/javascript">

function downloadHistory()
{
	var sel_id;
	sel_id = $("#gridCompHistory").jqGrid('getGridParam','selarrrow');
	if(sel_id=="")
   {
     alert("Please select atleast one check box...");        
     return false;
   }
	else
	{
		$("#takeActionGrid1111").dialog({title: 'Check Column',width: 350,height: 600});
		$("#takeActionGrid1111").dialog('open');
	 	$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/Compliance/compliance_pages/sendReportMailConfirmAction.action?mainHeaderName=taskHistory&selectedId="+sel_id,
		    success : function(data) 
		    {
	 			$("#dataPart").html(data);
			},
		   error: function() {
		        alert("error");
		    }
		 });
	}	
}

$.subscribe('openDialog', function(event,data) 
{
	var sel_id;
	sel_id = $("#gridCompHistory").jqGrid('getGridParam','selarrrow');
	if(sel_id=="")
   {
     alert("Please select atleast one check box...");        
     return false;
   }
	else
	{
		$("#sendMailDialog").dialog('open');
		$("#mailId").val("");
	}
});
function openDialog()
{
	var sel_id;
	sel_id = $("#gridCompHistory").jqGrid('getGridParam','selarrrow');
	
	if(sel_id=="")
	{
	     alert("Please select atleast one check box...");        
	     return false;
	}
	else
	{
		$("#takeActionGrid1111").dialog({title: 'Send Mail ',width: 950,height: 250});
		$("#takeActionGrid1111").dialog('open');
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/Compliance/compliance_pages/beforeSendMail.action?selectedId="+sel_id+"&actionName=sendReportMailConfirmAction&mainHeaderName=taskHistory",
		    success : function(data) 
		    {
	 			$("#takeActionGrid1111").html(data);
			},
		   error: function() {
		        alert("error");
		    }
		 });
	}
}
function searchRow()
{
	 $("#gridCompHistory").jqGrid('searchGrid', {sopt:['eq','cn']} );
}
function reloadRow()
{
	var grid = $("#gridCompHistory");
	grid.trigger("reloadGrid",[{current:true}]);
}
</script>
<script type="text/javascript">
</script>
</head>
<body>
<sj:dialog
          id="takeActionGrid1111"
          showEffect="slide"
          hideEffect="explode"
          autoOpen="false"
          title="Take Action on Operation Task"
          modal="true"
          closeTopics="closeEffectDialog"
          width="1000"
          height="400"
          >
          <div id="dataPart"></div>
</sj:dialog>

 <s:iterator value="taskMap" status="status">
	 <s:if test="key=='Task Name'">
	  <div class="newColumn">
		                  <div class="leftColumn"><s:property value="%{key}"/>:</div>
		                  <div class="rightColumn">
		                  <label id="%{key}"><s:property value="%{value}"/></label>
		                  </div>
		                  </div>
	 
	 </s:if>
	 <s:if test="key=='Department'">
	  <div class="newColumn">
		                  <div class="leftColumn"><s:property value="%{key}"/>:</div>
		                  <div class="rightColumn">
		                  <label id="%{key}"><s:property value="%{value}"/></label>
		                  </div>
		                  </div>
	 
	 </s:if>
	  <s:if test="key=='Frequency'">
	  <div class="newColumn">
		                  <div class="leftColumn"><s:property value="%{key}"/>:</div>
		                  <div class="rightColumn">
		                  <label id="%{key}"><s:property value="%{value}"/></label>
		                  </div>
		                  </div>
	 
	 </s:if>
	  <s:if test="key=='Start From Due Date'">
	  <div class="newColumn">
		                  <div class="leftColumn"><s:property value="%{key}"/>:</div>
		                  <div class="rightColumn">
		                  <label id="%{key}"><s:property value="%{value}"/></label>
		                  </div>
		                  </div>
	 
	 </s:if>
</s:iterator>
<div class="middle-content">

<div class="listviewBorder" style="margin-top: 10px;width: 97%;margin-left: 20px;" align="center">
<div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
<div class="pwie">
	<table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%">
		<tbody>
			<tr>
				  <td>
					  <table class="floatL" border="0" cellpadding="0" cellspacing="0">
					    <tbody><tr><td class="pL10"> 
					    <sj:a id="searchButtonHistory"  title="Search" buttonIcon="ui-icon-search" cssStyle="height:25px; width:32px"  cssClass="button" button="true"  onclick="searchRow()"></sj:a>
			            <sj:a id="refButtonHistory"  title="Refresh" buttonIcon="ui-icon-refresh" cssStyle="height:25px; width:32px"  cssClass="button" button="true"  onclick="reloadRow()"></sj:a>
		       
					      </td></tr></tbody>
					  </table>
				  </td>
				  <!-- Block for insert Right Hand Side Button -->
				  <td class="alignright printTitle">
				   	<sj:a id="sendBuffffftton111History" buttonIcon="ui-icon-arrowthickstop-1-s" cssClass="button" button="true" title="Download" cssStyle="height:25px; width:32px" onclick = "downloadHistory();"></sj:a>
				 	<sj:a id="sendMailButtonHistory"  cssClass="button" buttonIcon="ui-icon-mail-closed" button="true" cssStyle="height:25px; width:32px" title="Send Mail" onclick="openDialog()"></sj:a>
				  </td>
			</tr>
		</tbody>
	</table>
</div>
</div>

<div style="overflow: scroll; height: 200px;">
	<s:url id="viewCompHistory" action="viewCompHistoryReport" escapeAmp="false">
	<s:param name="fromDate"  value="%{fromDate}" />
	<s:param name="toDate"  value="%{toDate}" />
	<s:param name="complId" value="%{complId}"></s:param>
	</s:url>
	    <sjg:grid 
		  id="gridCompHistory"
          href="%{viewCompHistory}"
          gridModel="masterViewList"
          navigator="true"
          navigatorAdd="false"
          navigatorView="true"
          navigatorEdit="false"
          navigatorDelete="false"
          navigatorSearch="true"
          rowList="15,20,25,30,40,50,100"
          rowNum="15"
          viewrecords="true"       
          pager="true"
          pagerButtons="true"
          pagerInput="false"  
          multiselect="true" 
          loadingText="Requesting Data..."  
          navigatorEditOptions="{height:230,width:400}"
          navigatorSearchOptions="{sopt:['eq','cn']}"
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
<br>
</div>
</div>
</div>
</body>
</html>
