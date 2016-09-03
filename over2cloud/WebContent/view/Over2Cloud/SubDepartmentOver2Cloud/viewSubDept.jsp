<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>

<script type="text/javascript">
	function addNewSubDept1111()
	{
	    var conP = "<%=request.getContextPath()%>";
	    $("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : conP+"/view/Over2Cloud/commonModules/beforeSubDepartment.action?existFlag=1&subDeptfalg=1",
		    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	}
	
	function addNewSubDept()
	{
	    var conP = "<%=request.getContextPath()%>";
		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/commonModules/beforesubdeptAdd.action ",
		    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });

	}
	
	
</script>
<script type="text/javascript">
function pageisNotAvibale(){
$("#facilityisnotavilable").dialog('open');
}

function searchData()
{
	jQuery("#gridedittable").jqGrid('searchGrid', {multipleSearch:false,reloadAfterSubmit:true,top:0,left:350,modal:true} );
}
</script>
</head>
<body>
<div class="list-icon">
<div class="head">Sub Department</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div><div class="head">View</div>
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
								<sj:a id="refButton" title="Refresh" buttonIcon="ui-icon-refresh" cssStyle="height:25px; width:32px" cssClass="button" button="true"  onclick="reload()"></sj:a>
						      
					    </td></tr>
					    </tbody>
					  </table>
				  </td>
				  
				  <!-- Block for insert Right Hand Side Button -->
				  <td class="alignright printTitle">
				     <sj:a button="true" cssClass="button" cssStyle="height:25px;" title="Add" buttonIcon="ui-icon-plus" onclick="addNewSubDept();" >Add</sj:a>
				  </td>
			</tr>
		</tbody>
	</table>
</div>
</div>
<!-- Code End for Header Strip -->

 
<div style="overflow: scroll; height: 430px;">
<s:url id="viewSubDeptData" action="viewSubDeptData" />
<s:url id="editSubDeptDataGrid" action="editSubDeptDataGrid" />
<sjg:grid 
		  id="gridedittable"
          href="%{viewSubDeptData}"
          gridModel="subDeptDataGridView"
          groupField="['deptid']"
          groupCollapse="true"
    	  groupColumnShow="[false]"
    	  groupText="['<b>{0}: {1} Sub-Departments</b>']"
    	  navigator="true"
          navigatorAdd="false"
          navigatorView="true"
          navigatorDelete="true"
    	  navigatorEdit="true"
          navigatorSearch="true"
          rowList="500,1000"
          viewrecords="true"
          pager="true"
    	  pagerButtons="true"
          pagerInput="true"
    	  multiselect="false"  
          loadingText="Requesting Data..."  
    	  rowNum="500"
          autowidth="true"  
          navigatorSearchOptions="{sopt:['eq','cn']}"
          editurl="%{editSubDeptDataGrid}"
          onSelectRowTopics="rowselect"
          shrinkToFit="false"
          navigatorEditOptions="{closeAfterEdit:true,reloadAfterSubmit:false}"
          navigatorViewOptions="{width:500}"
          editinline="false"
          
          >
          
		<s:iterator value="level1ColmnNames" id="level1ColmnNames" >  
			<sjg:gridColumn 
							name="%{colomnName}"
							index="%{colomnName}"
							title="%{headingName}"
							width="420"
							align="%{align}"
							editable="%{editable}"
							search="%{search}"
							hidden="%{hideOrShow}"
							formatter="%{formatter}"
							frozen="%{frozenValue}"
							edittype="%{edittype}"
							editoptions="%{editoptions}"
						    searchoptions="{sopt:['eq','cn']}"
			/>
		</s:iterator>  
</sjg:grid>
</div>
</body>
<script type="text/javascript">
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
		jQuery("#gridedittable").jqGrid('editGridRow', row ,{width:425,reloadAfterSubmit:false,closeAfterEdit:true,top:0,left:350,modal:true});
	}
}

function deleteRow()
{
	if(row==0)
	{
		alert("Please select atleast one row to delete.");
	}
	else
	{
		$("#gridedittable").jqGrid('delGridRow',row, {height:120,reloadAfterSubmit:true,top:0,left:350,modal:true});
	}
}
function reload(rowid, result) {
	 $("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		var conP = "<%=request.getContextPath()%>";
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/commonModules/beforeSubDepartmentView.action?subDeptfalg=1",
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