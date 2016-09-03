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
<script type="text/javascript">
function escalationY(cellvalue, options, rowObject) 
{
	if(rowObject.actionStatus=='Done')
	{
		return "Done";
	}
	else
	{
		return "<a href='#' onClick='escalationYAction("+rowObject.id+")'>"+cellvalue+"</a>";
	}
}
function dueDateY(cellvalue, options, rowObject) 
{
	if(rowObject.actionStatus=='Done')
	{
		return "Done";
	}
	else
	{
		return "<a href='#' onClick='dueDateYAction("+rowObject.id+")'>"+cellvalue+"</a>";
	}
}
function escalationYAction(valuepassed) 
{
 	   var escValue = jQuery("#gridedittable").jqGrid('getCell',valuepassed,'comp.escalation');
 	   var a = escValue.split(">");
 	   var b = a[1].split("<");
 	   if (b[0]=='Yes') 
 	   {
 			$("#complianceDialog1111").dialog({title: 'Escalation Level',height:'350' , width:'700'});
 			$("#complianceDialog1111").dialog('open');
 			$("#datadivesc").html("<br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
 			$.ajax({
 			    type : "post",
 			    url : "/over2cloud/view/Over2Cloud/Compliance/compliance_pages/beforeEscalationData.action?complId="+valuepassed,
 			    success : function(data) 
 			    {
 			    	$("#datadivesc").html(data);
 			    },
 			    error: function() 
 			    {
 			       alert("error");
 			    }
 			 });
	   }
}
function dueDateYAction(valuepassed) 
{ 
	var reportId = jQuery("#gridedittable").jqGrid('getCell',valuepassed,'crid');
	$("#complianceDialog1111").dialog({title: 'Reminder',height:'350' , width:'700'});
	$("#complianceDialog1111").dialog('open');
	$("#datadivesc").html("<br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/Compliance/compliance_pages/beforeReminder.action?complId="+reportId+"&dataFor=productivity",
	    success : function(data) 
	    {
	    	$("#datadivesc").html(data);
	    },
	    error: function() 
	    {
	       alert("error");
	    }
	 });
}
function searchRow()
{
	 $("#gridedittable").jqGrid('searchGrid', {sopt:['eq','cn']} );
}
function reloadRow()
{
	var grid = $("#gridedittable");
	grid.trigger("reloadGrid",[{current:true}]);
}
</script>
</head>
<body>
<div class="listviewBorder" style="margin-top: 10px;width: 97%;margin-left: 20px;" align="center">
<div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
<div class="pwie">
	<table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%">
		<tbody>
			<tr>
				  <td>
					  <table class="floatL" border="0" cellpadding="0" cellspacing="0">
					    <tbody><tr><td class="pL10">   
					      <sj:a id="searchButton12222"  title="Search" buttonIcon="ui-icon-search" cssStyle="height:25px; width:32px"  cssClass="button" button="true"  onclick="searchRow()"></sj:a>
			              <sj:a id="refButton2222"  title="Refresh" buttonIcon="ui-icon-refresh" cssStyle="height:25px; width:32px"  cssClass="button" button="true"  onclick="reloadRow()"></sj:a>
		       
					      </td></tr></tbody>
					  </table>
				  </td>
				  <!-- Block for insert Right Hand Side Button -->
				  <td class="alignright printTitle">
				
				  </td>
			</tr>
		</tbody>
	</table>
</div>
</div>
<div style="overflow: scroll; height: 440px;">
	<s:url id="veiwTaskType" action="getProductivityGridData" escapeAmp="false">
	<s:param name="dataFor"  value="%{dataFor}" />
	<s:param name="complId"  value="%{complId}" />
	</s:url>
	<s:hidden id="dataFor" value="%{dataFor}" />
	<s:hidden id="complId" value="%{complId}" />
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
          rowList="5,10,20,25"
          viewrecords="true"       
          pager="true"
          pagerButtons="true"
          pagerInput="false"   
          multiselect="true"  
          loadingText="Requesting Data..."  
          navigatorEditOptions="{height:230,width:400}"
          navigatorSearchOptions="{sopt:['eq','cn']}"
          navigatorEditOptions="{reloadAfterSubmit:true}"
          shrinkToFit="true"
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
							search="%{search}"
							hidden="true"
							key="true"
							formatter="%{formatter}"
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
</sjg:grid>
</div>
</div>
<br>
<sj:dialog 
	 			id				=	"complianceDialog1111" 
	 			modal			=	"true" 
	 			effect			=	"slide" 
	 			autoOpen		=	"false" 
	 			width			=	"1140" 
	 			height			=	"533"
	 			position="['center','center']"
	 			>
	 			<div id="datadivesc"></div>
     		</sj:dialog>
</body>
</html>
