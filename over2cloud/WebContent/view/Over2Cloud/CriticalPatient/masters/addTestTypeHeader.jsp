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

<SCRIPT type="text/javascript">
function createTestMaster()
{

	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    //url : "view/Over2Cloud/Text2Mail/beforemainKeywords.action",
	    url : "view/Over2Cloud/CriticalMaster/viewAddTestType.action",
	    success : function(data) 
	    {
			$("#"+"data_part").html(data);
	    },
	    error: function() 
	    {
            alert("error");
        }
	 });
 
	}

</SCRIPT>
<script type="text/javascript">

function editRow()
{
	if(row==0)
	{
		alert("Please select atleast one row to edit.");
	}
	else
	{
		jQuery("#addTypeTest").jqGrid('editGridRow', row ,{height:122, width:270,reloadAfterSubmit:true,closeAfterEdit:true,top:0,left:350,modal:true , afterSubmit: function () {
			getOnChangeMachineData('Active');
	    }});
		
	}
}

function deleteRow()
{
	  var status = jQuery("#addTypeTest").jqGrid('getCell',row,'flag');
	if(row==0)
	{
		alert("Please select atleast one row to Inactive.");
	}
	else if(status=='Inactive')
	{
		alert("The selected data is already Inactive.");
	}
	else
	{
		$("#addTypeTest").jqGrid('delGridRow',row, {height:122, width:270,reloadAfterSubmit:true,closeAfterEdit:false,top:0,left:350,modal:true,caption:'Inactive',msg: "Inactive selected record(s)?",bSubmit: "Inactive" , afterSubmit: function () {
			getOnChangeMachineData('Active');
	    }});
		
	}	
}



function reload(rowid, result) {
	  $("#addTypeTest").trigger("reloadGrid"); 
	}


function searchData()
{
	jQuery("#addTypeTest").jqGrid('searchGrid', {multipleSearch:false,reloadAfterSubmit:true,top:0,left:350,modal:true, cssClass:"textField"} );
	
}

function getOnChangeMachineData()
{
	$.ajax({
	    type : "post",
	    //url : "/over2cloud/view/Over2Cloud/commonModules/makeHistory.action",
	    url : "view/Over2Cloud/CriticalMaster/beforeTestTypeMaterGrid.action",
	    success : function(subdeptdata) {
       $("#result_part").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });
	
}
getOnChangeMachineData();
</script>
</head>
<body>
 
<div class="clear"></div>




<div class="list-icon">
 <div class="head">Test Type </div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div><div class="head">
	 <div class="head">View </div>

</div>

<!-- Code For Header Strip -->
<div class="clear"></div>
<div class="listviewBorder" style="margin-top: 10px;width: 97%;margin-left: 20px;" align="center">

<div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
<div class="pwie">
	<table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%">
		<tbody>
			<tr>
				  <!-- Block for insert Left Hand Side Button -->
				  <td>
					  <table class="floatL" border="0" cellpadding="0" cellspacing="0">
					    <tbody><tr>
					 <td class="pL10">
					
					<sj:a id="editButton" title="Edit"  buttonIcon="ui-icon-pencil" cssStyle="height:25px; width:32px" cssClass="button" button="true" onclick="editRow()"></sj:a>
					
					<sj:a id="delButton" title="Deactivate" buttonIcon="ui-icon-trash" cssStyle="height:25px; width:32px" cssClass="button" button="true"  onclick="deleteRow()"></sj:a>
					
					
					<sj:a id="searchButton" title="Search" buttonIcon="ui-icon-search" cssStyle="height:25px; width:32px" cssClass="button" button="true"  onclick="searchData()"></sj:a>
					 
					<sj:a id="refButton" title="Reload Grid" buttonIcon="ui-icon-refresh" cssStyle="height:25px; width:32px" cssClass="button" button="true"  onclick="reload()"></sj:a>
			
		
					    </td></tr>
					    </tbody>
					  </table>
				  </td>
				  
				  
				   
				  <!-- Block for insert Right Hand Side Button -->
				  <td class="alignright printTitle">
				  
				    <sj:a button="true" cssClass="button" cssStyle="height:25px;"  title="Add"  buttonIcon="ui-icon-plus" onclick="createTestMaster();">Add</sj:a> 
				
				  </td>
			</tr>
		</tbody>
	</table>
</div>
</div>
<div id="result_part"></div>

</div>

</body>
</html>