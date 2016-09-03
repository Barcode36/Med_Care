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
<script type="text/javascript" src="<s:url value="/js/asset/AssetCommon.js"/>"></script>
<script type="text/javascript">
 
var row=0;
$.subscribe('rowselect', function(event, data) {
	row = event.originalEvent.id;
});	
 
function deleteRow()
{
	if(row==0)
	{
		alert("Please select atleast one row.");
	}
	$("#gridedittable").jqGrid('delGridRow',row, {height:120,reloadAfterSubmit:true,top:0,left:350,modal:true});
}
 
function reloadRow()
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/CorporatePatientServices/Pharmacy/viewPharmacyAlert.jsp",
	    success : function(feeddraft) {
       $("#"+"data_part").html(feeddraft);
	},
	   error: function() {
            alert("error");
        }
	 });
}	

 
function addEmpForPharmacyAlert()
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
 	    url : "view/Over2Cloud/CorporatePatientServices/Pharmacy/beforeAddEmpForPharmacyAlert.action",
	    success : function(data) {
       		$("#data_part").html(data);
		},
	    error: function() {
            alert("error");
        }
	 });
}
 
</script>
</head>
<body>
<div class="clear"></div>
<div class="middle-content">
<div class="list-icon">
	 <div class="head">
	Pharmacy Alert</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div>
	 <div class="head">View</div> 
</div>
<div class="rightHeaderButton"></div>
<div class="clear"></div>
<div>
<div id="alphabeticalLinks"></div>
<!-- Code For Header Strip -->
<div class="listviewBorder" style="width: 97%;margin-left: 20px;" align="center">
<div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
<div class="pwie">
	<table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%">
		<tbody>
			<tr>
				  <td>
					  <table class="floatL" border="0" cellpadding="0" cellspacing="0">
					    <tbody><tr><td class="pL10">   
					     	 
								<sj:a id="delButton" title="Deactivate" buttonIcon="ui-icon-trash" cssClass="button" cssStyle="height:25px; width:32px" button="true"  onclick="deleteRow()"></sj:a>
				 	 			<sj:a id="refButton" title="Refresh" buttonIcon="ui-icon-refresh" cssClass="button" cssStyle="height:25px; width:32px" button="true" onclick="reloadRow();"></sj:a>
		
					      </td></tr></tbody>
					  </table>
				  </td>
			 
				  <td class="alignright printTitle">
				  <sj:a id="addButton"  button="true"  buttonIcon="ui-icon-plus" cssClass="button" onclick="addEmpForPharmacyAlert();">Add</sj:a>
				  </td>
			</tr>
		</tbody>
	</table>
</div>
</div>
<div style="overflow: scroll; height: 400px;">
<s:url id="viewModifyEsc" action="editPharmacyAlertData"></s:url>
<s:url id="viewPart" action="getPharmacyAlertData">
</s:url>
 
<sjg:grid 
		 
          id="gridedittable"
          href="%{viewPart}"
          gridModel="masterViewProp"
          navigator="false"
          navigatorAdd="false"
          navigatorView="true"
          navigatorSearch="true"
          editinline="false"
          rowList="15,50,100"
          rowNum="15"
          viewrecords="true"       
          pager="true"
          pagerButtons="true"
          rownumbers="-1"
          pagerInput="true"   
          multiselect="false"
          editurl="%{viewModifyEsc}"
          navigatorSearchOptions="{sopt:['eq','cn']}"  
          loadingText="Requesting Data..."  
          navigatorEditOptions="{height:230,width:400,closeAfterEdit:true,reloadAfterSubmit:false}"
          shrinkToFit="true"
          autowidth="true"
          loadonce="true"
          onSelectRowTopics="rowselect"
          
          >
       
        <sjg:gridColumn 
		name="id"
		index="id"
		title="Id"
		width="165"
		align="left"
		editable="false"
		hidden="true"
		/>
	
		<sjg:gridColumn 
		name="dept"
		index="dept"
		title="Department"
		width="140"
		align="center"
		editable="false"
		/>
		
		<sjg:gridColumn 
		name="sub_dept"
		index="sub_dept"
		title="Sub Department"
		width="140"
		align="center"
		editable="false"
		/>
		
		<sjg:gridColumn 
		name="emp_name"
		index="emp_name"
		title="Employee Name"
		width="150"
		align="center"
		editable="true"
		edittype="select"
		editoptions="{value:'Customised:Customised;Multiplicative:Multiplicative'}"
		/>
		 
		
</sjg:grid>
</div>
</div>
</div>
</div>
</body>

</html>