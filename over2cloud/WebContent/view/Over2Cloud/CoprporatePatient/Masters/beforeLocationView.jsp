<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<%String userRights = session.getAttribute("userRights") == null ? "" : session.getAttribute("userRights").toString(); 
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<s:url  id="contextz" value="/template/theme" />
<link   type="text/css" href="<s:url value="/css/style.css"/>" rel="stylesheet" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script type="text/javascript">
function addNew(){
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/CorporatePatientServices/cpservices/beforeAddLocation.action",
	    success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });
}

function getGridView()
{
	$.ajax({
 		type :"post",
 		url :"view/Over2Cloud/CorporatePatientServices/cpservices/locationGridView.action?status="+$("#flag").val()+"&location_name="+$("#location_name").val(),
 		success : function(data) 
	    {
			$("#middleDiv").html(data);
		},
	    error: function()
	    {
	        alert("error viewLinsence");
	    }
 	}); 	
}
getGridView();

</script>
</head>

<body>
<div class="list-icon">
	 <div class="head">Service Master</div>
	 <div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div>
	 <div class="head">View</div> 
</div>

 <div class="clear"></div>
  
    <div class="listviewBorder"  style="margin-top: 10px;width: 97%;margin-left: 20px;" align="center">
   
    <div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
	<div class="pwie">
	<table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%">
	<tbody><tr></tr><tr><td></td></tr><tr><td>
	 <table class="floatL" border="0" cellpadding="0" cellspacing="0"><tbody><tr><td class="pL10"> 
	  <sj:a id="editButton" title="Edit"  buttonIcon="ui-icon-pencil" cssClass="button" cssStyle="height:25px; width:32px" button="true" onclick="editRow()"></sj:a>
   	  <sj:a id="delButton" title="Deactivate" buttonIcon="ui-icon-trash" cssClass="button" cssStyle="height:25px; width:32px" button="true"  onclick="deleteRow()"></sj:a>
 	<sj:a id="searchButton" title="Search" buttonIcon="ui-icon-search" cssClass="button" cssStyle="height:25px; width:32px" button="true"  onclick="searchRow()"></sj:a>
	<sj:a id="refButton" title="Refresh" buttonIcon="ui-icon-refresh" cssClass="button" cssStyle="height:25px; width:32px" button="true"  onclick="reloadPage()"></sj:a>
	<s:select  
	id="flag"
	name="flag"
	list="#{'-1':'All','Active':'Active','Inactive':'Inactive'}"
	cssClass="button"
	cssStyle="margin-top: -29px;margin-left:3px"
	theme="simple"
	onchange="getGridView() "
	>
	</s:select>
	<s:select  
	id="location_name"
	name="location_name"
	list="location"
	headerKey="-1"
    headerValue="Select Location" 
	cssClass="button"
	cssStyle="margin-top: -29px;margin-left:3px"
	theme="simple"
	onchange="getGridView() "
	>
	</s:select>
	
	</td></tr></tbody></table>
	</td>
	<td class="alignright printTitle">
	<sj:a id="addButton"  button="true"  buttonIcon="ui-icon-plus" cssClass="button" onclick="addNew();">Add</sj:a>
    </td>   
	</tr></tbody></table>
	</div>
	</div>
   <%-- "#{'All':'All','Active':'Active','DActive':'InActive'}" --%>
  
<div style="height: 430px;">
				<div id="middleDiv"></div>
			</div>
		</div>

</body>

<script type="text/javascript">
var row=0;
$.subscribe('rowselect', function(event, data) {
	row = event.originalEvent.id;
});	

var selRowIds = jQuery('#gridBasicDetails').jqGrid('rowselect', 'selarrrow');

function editRow()
{
	if(row==0)
	{
		alert("Please select atleast one row to edit.");
	}
	else
	{
			jQuery("#gridBasicDetails").jqGrid('editGridRow', row ,{reloadAfterSubmit:false,closeAfterEdit:true,top:0,left:350,modal:true});
			reloadPage();
	    
	}
}

function deleteRow()
{
	
var status = jQuery("#gridBasicDetails").jqGrid('getCell',row,'status');
if(row==0)
{
	alert("Please select atleast one row to delete.");
}
else if(status=='Inactive')
{
	alert("The selected data is already delete.");
}
else
{
$("#gridBasicDetails").jqGrid('delGridRow',row, {height:120,reloadAfterSubmit:true,top:0,left:350,modal:true});
reloadPage();
}
}
function searchRow()
{
	 $("#gridBasicDetails").jqGrid('searchGrid',row, {height:120,reloadAfterSubmit:true,top:0,left:350,modal:true});
}
 
function reloadPage()
{
var grid = $("#gridBasicDetails");
grid.trigger("reloadGrid",[{current:true}]);
}


</script>
</html>