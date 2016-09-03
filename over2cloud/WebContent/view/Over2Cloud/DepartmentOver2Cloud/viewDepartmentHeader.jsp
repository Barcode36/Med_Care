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
<SCRIPT type="text/javascript" src="js/department/department.js"></SCRIPT>
<SCRIPT type="text/javascript" src="js/common/commonContact.js"></SCRIPT>
<script type="text/javascript" src="js/hr/commonHr.js"></script>
<script type="text/javascript">
	function addNewDept()
	{
		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		var conP = "<%=request.getContextPath()%>";
		$.ajax({
		    type : "post",
		    url : conP+"/view/Over2Cloud/commonModules/beforeDepartment.action?deptFlag=1",
		    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	}
	
	function loadGrid()
	{
		row=0;
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/commonModules/beforeDepartmentView.action?deptFlag=1&searchFields=flag&SearchValue=Active",
		    success : function(subdeptdata) {
	       $("#"+"result_part").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	}
	function getOnChangePrimaryData(searchFields,SearchValue)
	{
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/commonModules/beforeDepartmentView.action?deptFlag=1",
		    data:"searchFields="+searchFields+"&SearchValue="+SearchValue,
		    success : function(subdeptdata) {
	       $("#result_part").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
		
	}
	loadGrid();
	function reloadPage()
	{
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/commonModules/beforeDepartmentViewHeader.action?deptFlag=1",
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

function searchData()
{
	jQuery("#gridedittable").jqGrid('searchGrid', {multipleSearch:false,reloadAfterSubmit:true,top:0,left:350,modal:true, cssClass:"textField"} );
	
}
</script>
</head>

<body>
<div class="clear"></div>
<div class="middle-content">

<div class="list-icon">
	 <div class="head">View Contact Sub Type </div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div><div class="head"><s:iterator value="totalCount">
	<s:property value="%{key}"/>: <s:property value="%{value}"/>   
</s:iterator>
</div> 
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
					 <%if(userRights.contains("sude_MODIFY")) {%>
					<sj:a id="editButton" title="Edit"  buttonIcon="ui-icon-pencil" cssStyle="height:25px; width:32px" cssClass="button" button="true" onclick="editRow()"></sj:a>
					<%} %>
					
					<%if(userRights.contains("sude_DELETE")) {%>
					<sj:a id="delButton" title="Inactive" buttonIcon="ui-icon-trash" cssStyle="height:25px; width:32px" cssClass="button" button="true"  onclick="deleteRow()"></sj:a>
					<%} %>
					<sj:a id="searchButton" title="Search" buttonIcon="ui-icon-search" cssStyle="height:25px; width:32px" cssClass="button" button="true"  onclick="searchData()"></sj:a>
					<sj:a id="refButton" title="Reload Grid" buttonIcon="ui-icon-refresh" cssStyle="height:25px; width:32px" cssClass="button" button="true"  onclick="reloadView()"></sj:a>
			        <s:if test="%{mgtStatus}">
				  			 <s:select 
		                              id="office"
		                              list="deptMap"
		                              headerKey="-1"
		                              headerValue="Select Country Office" 
		                              cssClass="button"
		                              theme="simple"
		                              onchange="getheadoffice(this.value,'headOfficeId');"
		                              >
		                  </s:select>
						  <s:select 
		                              id="headOfficeId"
		                              list="{'No Data'}"
		                              headerKey="-1"
		                              headerValue="Select Head Office" 
		                              cssClass="button"
		                              theme="simple"
		                              onchange="getbranchoffice(this.value,'regLevel')"
		                              >
		                   </s:select>
		                   </s:if>
		                     <s:if test="%{hodStatus}">
		                  		<s:select 
		                              id="regLevel"
		                              name="regLevel"
		                              list="deptMap"
		                              headerKey="-1"
		                              headerValue="Select Branch Office" 
		                              cssClass="button"
		                              theme="simple"
		                              onchange="getGroupNamesForMappedOffice('groupType',this.value);"
		                              >
		                      </s:select>
		                      </s:if>
			         
						      	   <s:select  
						    		id					=		"groupType"
						    		name				=		"groupType"
						    		list				=		"groupMap"
						      		headerKey           =       "-1"
							        headerValue         =       "Select Contact Type"
							       cssClass             =      "button"
							       cssStyle             =      "margin-top: -29px;margin-left:3px"
							      theme                 =       "simple"
						      	   onchange			    =		"getDeptBySubGroup('contactSubType',this.value);getOnChangePrimaryData('groupId',this.value)"
						      	 >
						      	 </s:select>
						      	 <s:select  
							    		id					=		"contactSubType"
							    		name				=		"contactSubType"
							    		list				=		"{'No Data'}"
							      	    headerKey           =       "-1"
							            headerValue         =       "Select Contact Sub-Type"
							            cssClass            =      "button"
							            cssStyle            =      "margin-top: -29px;margin-left:3px"
							            theme               =       "simple"
							      	 	onchange			=		"getOnChangePrimaryData('id',this.value) "
							      	 
						      	 
						      	 >
						      	 </s:select>
						      	  <s:select  
						    		id					=		"flag"
						    		name				=		"flag"
						    		list				=		"#{'Active':'Active','Inactive':'Inactive','-1':'All'}"
							       cssClass             =      "button"
							       cssStyle             =      "margin-top: -29px;margin-left:3px"
							      theme                 =       "simple"
						      	 onchange			=		"getOnChangePrimaryData('flag',this.value) "
						      	 >
						      	 </s:select>
			
					    </td></tr>
					    </tbody>
					  </table>
				  </td>
				  
				  <!-- Block for insert Right Hand Side Button -->
				  <td class="alignright printTitle">
				   <%if(userRights.contains("sude_ADD") || true) 
					{%>
				    <sj:a button="true" cssClass="button" cssStyle="height:25px;"  title="Add"  buttonIcon="ui-icon-plus" onclick="addNewDept();">Add</sj:a> 
				  <%} %>
				  </td>
			</tr>
		</tbody>
	</table>
</div>
</div>
<!-- Code End for Header Strip -->

 <div id="result_part"></div>
 </div>
 </div>
</body>

<script type="text/javascript">
var row=0;
$.subscribe('rowselect', function(event, data) {
	row = event.originalEvent.id;
});	


var selRowIds = jQuery('#gridedittable').jqGrid('rowselect', 'selarrrow');

function editRow()
{
	if(row==0)
	{
		alert("Please select atleast one row to edit.");
	}
	else
	{
		jQuery("#gridedittable").jqGrid('editGridRow', row ,{height:230, width:425,reloadAfterSubmit:false,closeAfterEdit:true,top:0,left:350,modal:true , afterSubmit: function () {
			reloadPage();
	    }});
		
	}
}

function deleteRow()
{
	  var status = jQuery("#gridedittable").jqGrid('getCell',row,'flag');
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
		$("#gridedittable").jqGrid('delGridRow',row, {height:120, width:425,reloadAfterSubmit:true,closeAfterEdit:false,top:150,left:350,modal:true,caption:'Inactive',msg: "Inactive selected record(s)?",bSubmit: "Inactive" , afterSubmit: function () {
			reloadPage();
	    }});
		
	}	
}

</script>
</html>