<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<%String userRights = session.getAttribute("userRights") == null ? "" : session.getAttribute("userRights").toString();%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<SCRIPT type="text/javascript" src="js/common/commonContact.js"></SCRIPT>
<script type="text/javascript" src="js/hr/commonHr.js"></script>

<script type="text/javascript">
function pageisNotAvibale(){
$("#facilityisnotavilable").dialog('open');
}
</script>

<SCRIPT type="text/javascript">
var row=0;
$.subscribe('rowselect', function(event, data) {
	row = event.originalEvent.id;
});

function searchRow()
{
	 $("#gridedittable11").jqGrid('searchGrid', {sopt:['eq','cn']} );
}
function editRow()
{
	if(row==0)
	{
		alert("Please select atleast one row.");
	}
	else
	{
		jQuery("#gridedittable11").jqGrid('editGridRow', row ,{height:230, width:425,reloadAfterSubmit:false,closeAfterEdit:true,top:0,left:350,modal:true , afterSubmit: function () {
			reloadPage();
	    }});
		
	}
}
function deleteRow()
{
	  var status = jQuery("#gridedittable11").jqGrid('getCell',row,'status');
	if(row==0)
	{
		alert("Please select atleast one row.");
	}
	else if(status=='Inactive')
	{
		alert("The selected data is already Inactive.");
	}
	else
	{
		$("#gridedittable11").jqGrid('delGridRow',row, {height:120, width:425,reloadAfterSubmit:true,closeAfterEdit:false,top:150,left:350,modal:true,caption:'Inactive',msg: "Inactive selected record(s)?",bSubmit: "Inactive" , afterSubmit: function () {
			reloadPage();
	    }});
		
	}
	
}
function reloadGrid()
{
	loadGrid();
}


function reloadPage()
{
	row=0;
	$.ajax({
	    type : "post",
	    //url : "/over2cloud/view/Over2Cloud/commonModules/makeHistory.action",
	    url : "view/Over2Cloud/hr/beforeGroupHeaderView.action",
	    success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });
}

function addNewGroup() {

	var conP = "<%=request.getContextPath()%>";
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
    $.ajax({
        type : "post",
        url : conP+"/view/Over2Cloud/hr/beforegroupCreate.action",
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
	    //url : "/over2cloud/view/Over2Cloud/commonModules/makeHistory.action",
	    url : "view/Over2Cloud/hr/beforegroupView.action?fieldName=status&fieldvalue=Active",
	    success : function(subdeptdata) {
       $("#result_partss").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });
}
function getOnChangePrimaryData(fieldName,fieldvalue)
{
	
	$.ajax({
	    type : "post",
	    //url : "/over2cloud/view/Over2Cloud/commonModules/makeHistory.action",
	    url : "view/Over2Cloud/hr/beforegroupView.action?fieldName="+fieldName+"&fieldvalue="+fieldvalue,
	    success : function(subdeptdata) {
       $("#result_partss").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });
	
}

loadGrid();

</SCRIPT>
</head>
<body>
<div class="list-icon">
	 <div class="head">View Contact Type </div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div><div class="head"><s:iterator value="totalCount">
	<s:property value="%{key}"/>: <s:property value="%{value}"/>   
</s:iterator>
</div> 
</div> 
<div class="clear"></div>
<div style="align:center; padding-top: 10px;padding-left: 10px;padding-right: 10px;">
<div class="listviewBorder">
<!-- Code For Header Strip -->
<div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
<div class="pwie">
	<table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%">
		<tbody>
			<tr>
				  <!-- Block for insert Left Hand Side Button -->
				  <td>
					  <table class="floatL" border="0" cellpadding="0" cellspacing="0">
					    <tbody><tr><td class="pL10">
					     <sj:a id="editButton" title="Edit"  buttonIcon="ui-icon-pencil" cssClass="button" cssStyle="height:25px; width:32px" button="true" onclick="editRow()"></sj:a>	
						     <sj:a id="delButton" title="Inactive" buttonIcon="ui-icon-trash" cssClass="button" cssStyle="height:25px; width:32px" button="true"  onclick="deleteRow()"></sj:a>
							<sj:a id="searchButton" title="Search" buttonIcon="ui-icon-search" cssStyle="height:25px; width:32px" cssClass="button" button="true" onclick="searchRow();"></sj:a>
							<sj:a id="refButton" title="Refresh" buttonIcon="ui-icon-refresh" cssStyle="height:25px; width:32px" cssClass="button" button="true" onclick="reloadGrid();"></sj:a>  
				             <s:if test="%{mgtStatus}">
				  			 <s:select 
		                              id="office"
		                              list="officeMap"
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
		                              list="officeMap"
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
						      	   onchange			    =		"getOnChangePrimaryData('id',this.value) "
						      	 >
						      	 </s:select>
						      	   <s:select  
						    		id					=		"status"
						    		name				=		"status"
						    		list				=		"#{'Active':'Active','Inactive':'Inactive','-1':'All'}"
							       cssClass             =      "button"
							       cssStyle             =      "margin-top: -29px;margin-left:3px"
							      theme                 =       "simple"
						      	   onchange			    =		"getOnChangePrimaryData('status',this.value) "
						      	 >
						      	 </s:select>
					         </td></tr></tbody>
					  </table>
				  </td>
				  
				  <!-- Block for insert Right Hand Side Button -->
				  <td class="alignright printTitle">
        	<!-- <sj:a id="downloadButton"  button="true"  buttonIcon="ui-icon-arrowstop-1-s" cssClass="button" title="Download" cssStyle="height:25px; width:32px" onclick="setDownloadType('downloadExcel')"  ></sj:a> -->
				      <%if(userRights.contains("depa_ADD")) 
					{
			%>
			<sj:a cssClass="btn createNewBtnFeedbck" buttonIcon="ui-icon-plus" button="true" onclick="addNewGroup();" cssClass="button" title="Add">Add</sj:a>
			<%
			}
			%>
        
				  
				  </td>
			</tr>
		</tbody>
	</table>
</div>
</div>
<div id="result_partss"></div>
		</div></div>
</body>
</html>