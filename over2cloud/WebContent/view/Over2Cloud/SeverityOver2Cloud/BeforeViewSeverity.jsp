<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%String userRights = session.getAttribute("userRights") == null ? "" : session.getAttribute("userRights").toString();%>
<%String validApp = session.getAttribute("validApp") == null ? "" : session.getAttribute("validApp").toString();%>
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link type="text/css" href="<s:url value="/css/style.css"/>" rel="stylesheet" />
<script type="text/javascript" src="<s:url value="/js/js.js"/>"></script>
<title>Insert title here</title>
<SCRIPT type="text/javascript">
var row=0;
$.subscribe('rowselect', function(event, data) {
	row = event.originalEvent.id;
});	
function editRow()
{
	if(row==0)
	{
		alert("Please select atleast one row to edit.");
	}
	else
	{
		jQuery("#gridedittable").jqGrid('editGridRow', row ,{width:425,reloadAfterSubmit:false,closeAfterEdit:true,top:0,left:850,modal:true});
	}
}
function searchRow()
{
	jQuery("#gridedittable").jqGrid('searchGrid', {multipleSearch:false,reloadAfterSubmit:true,top:0,left:850,modal:true} );
}

function deleteRow()
{
	if(row==0)
	{
		alert("Please select atleast one row to delete.");
	}
	else
	{
		$("#gridedittable").jqGrid('delGridRow',row, {height:120,reloadAfterSubmit:true,top:0,left:850,modal:true});
	}
}
function onloadData()
{
	var moduleName=$("#moduleName").val();
	$.ajax
	({
		type : "post",
		url  : "/over2cloud/view/Over2Cloud/SeverityOver2Cloud/beforeViewSeverityInGrid.action?moduleName="+moduleName,
		success : function(data)
		{
			$("#viewDataDiv").html(data);
		},
		error : function()
		{
			alert("Error on data fetch");
		} 
	});
}
function addNewData()
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	var moduleName=$("#moduleName").val();
	$.ajax
	({
		type : "post",
		url  : "/over2cloud/view/Over2Cloud/SeverityOver2Cloud/createSeverity.action?moduleName="+moduleName,
		success : function(data)
		{
			$("#data_part").html(data);
		},
		error : function()
		{
			alert("Problem in data fetch");
		}
	});
}


onloadData();
</SCRIPT>
</head>
<body>
<div class="clear"></div>
<div class="middle-content">
<div class="list-icon">
	<div class="head">Severity Level</div>
	<img alt="" src="images/forward.png" style="margin-top:10px; float: left;">
	<div class="head"> View</div> 
</div>
<div class="clear"></div>
<div class="listviewBorder" style="margin-top: 10px;width: 97%;margin-left: 20px;" align="center">
<!-- Code For Header Strip -->
<div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
<div class="pwie">

<s:hidden id="moduleName" value="%{moduleName}"/>
 <table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%">
	<tbody>
	   <tr>
		   <!-- Block for insert Left Hand Side Button -->
		   <td>
			  <table class="floatL" border="0" cellpadding="0" cellspacing="0">
			    <tbody>
				    <tr>
				    <td class="pL10">
						<sj:a id="editButton" title="Edit"  buttonIcon="ui-icon-pencil" cssClass="button" cssStyle="height:25px; width:32px" button="true" onclick="editRow()"></sj:a>
						<sj:a id="delButton" title="Deactivate" buttonIcon="ui-icon-trash" cssClass="button" cssStyle="height:25px; width:32px" button="true"  onclick="deleteRow()"></sj:a>
					    <sj:a id="searchButton" title="Search" buttonIcon="ui-icon-search" cssClass="button" cssStyle="height:25px; width:32px" button="true"  onclick="searchRow()"></sj:a>
						<sj:a id="refButton" title="Refresh" buttonIcon="ui-icon-refresh" cssClass="button" cssStyle="height:25px; width:32px" button="true" onclick="onloadData();"></sj:a>
					 </td> 
				    </tr>
				</tbody>
			 </table>
		   </td>
				  
		  <!-- Block for insert Right Hand Side Button -->
		  <td class="alignright printTitle" id="actionID">
		     <sj:a  button="true" cssClass="button" cssStyle="height: 28px;margin-top: 3px;margin-left: 3px;width: 70px;" buttonIcon="ui-icon-plus" onclick="addNewData();">Add</sj:a>
		  </td>
			</tr>
		</tbody>
	</table>
</div>
</div>
<!-- Code End for Header Strip -->
<div id="viewDataDiv"></div>
</div>
</div>
</body>
</html>