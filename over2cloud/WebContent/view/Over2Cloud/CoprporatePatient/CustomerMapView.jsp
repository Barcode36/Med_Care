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
timePick = function(elem) {
    $(elem).timepicker({timeFormat: 'hh:mm'});
    $('#time_picker_div').css("z-index", 2000);
}

var row=0;
$.subscribe('rowselect', function(event, data) {
	row = event.originalEvent.id;
});	
function editRow()
{
	if(row==0)
	{
		alert("Please select atleast one row.");
	}
	else
	{
		jQuery("#gridedittable").jqGrid('editGridRow', row ,{height:230, width:425,reloadAfterSubmit:false,closeAfterEdit:true,top:0,left:350,modal:true , afterSubmit: function () {
			reloadRow();
	    }});
		
	}
}
function deleteRow()
{
	  var status = jQuery("#gridedittable").jqGrid('getCell',row,'status');
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
		$("#gridedittable").jqGrid('delGridRow',row, {height:120, width:425,reloadAfterSubmit:true,closeAfterEdit:false,top:150,left:350,modal:true,caption:'Inactive',msg: "Inactive selected record(s)?",bSubmit: "Inactive" , afterSubmit: function () {
			reloadRow();
	    }});
		
	}
	
}
function searchRow()
{
	 $("#gridedittable").jqGrid('searchGrid', {sopt:['eq','cn']} );
}
function reloadRow()
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/CoprporatePatient/CustomerMapView.jsp",
	    success : function(data) {
       		$("#data_part").html(data);
		},
	    error: function() {
            alert("error");
        }
	 });
}	

function getOnChangeMachineData(status)
{
	
$.ajax({
    type : "post",
    url : "view/Over2Cloud/CorporatePatientServices/cpservices/ViewCustomerMap.action?status="+status,
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

function searchResult(searchField, searchString, searchOper)
{
	
	//alert( searchOper);
	$("#mailandsmsdiv").html("");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/MachineOrder/createSpareViewPage.action",
	    data : "&searchField="+searchField+"&searchString="+searchString+"&searchOper="+searchOper,
	    success : function(data) {
       		$("#data_part").html(data);
		},
	    error: function() {
            alert("error");
        }
	 });
}

//commonSelectAjaxCall2
function mapCustomerwithPack()
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/CorporatePatientServices/cpservices/mapCustomerwithPack.action",
	    success : function(data) {
       		$("#data_part").html(data);
		},
	    error: function() {
            alert("error");
        }
	 });
}
//fillAlphabeticalLinks();


</script>
</head>
<body>
<div class="clear"></div>
<div class="middle-content">
<div class="list-icon">
	 <div class="head">
	Customer Map with Package</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div>
	 <div class="head">View</div> 
	 <div class="head">
	<s:iterator value="totalCount">
	<font size="3" color="red"><s:property value="%{key}"/>: <s:property value="%{value}"/>   </font>
</s:iterator>
</div>

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
					     	<%-- <%if(userRights.contains("spde_MODIFY")) {%> --%>
								<sj:a id="editButton" title="Edit"  buttonIcon="ui-icon-pencil" cssClass="button" cssStyle="height:25px; width:32px" button="true" onclick="editRow()"></sj:a>
							<%-- <%} %> --%>
							<%-- <%if(userRights.contains("spde_DELETE")) {%> --%>
								<sj:a id="delButton" title="Deactivate" buttonIcon="ui-icon-trash" cssClass="button" cssStyle="height:25px; width:32px" button="true"  onclick="deleteRow()"></sj:a>
							<%-- <%} %> --%>	
								<sj:a id="searchButton" title="Search" buttonIcon="ui-icon-search" cssClass="button" cssStyle="height:25px; width:32px" button="true"  onclick="searchRow()"></sj:a>
								<sj:a id="refButton" title="Refresh" buttonIcon="ui-icon-refresh" cssClass="button" cssStyle="height:25px; width:32px" button="true" onclick="reloadRow();"></sj:a>
								<s:select  
						    		id					=		"status"
						    		name				=		"status"
						    		list				=		"#{'Active':'Active','Inactive':'Inactive', '0':'Un-mapped', '-1':'All'}"
							       cssClass             =      "button"
							       cssStyle             =      "margin-top: -29px;margin-left:3px"
							      theme                 =       "simple"
						      	 onchange			=		"getOnChangeMachineData(this.value) "
						      	 >
						      	 </s:select>
					      </td></tr></tbody>
					  </table>
				  </td>
				  <!-- Block for insert Right Hand Side Button -->
				  <td class="alignright printTitle">
				  <sj:a id="addButton"  button="true"  buttonIcon="ui-icon-plus" cssClass="button" onclick="mapCustomerwithPack();">Add</sj:a>
				  </td>
			</tr>
		</tbody>
	</table>
</div>
</div>
<div style="overflow: scroll; height: 400px;">
<s:url id="viewModifyEsc" action="editCustomerMapdata" namespace="/view/Over2Cloud/CorporatePatientServices/cpservices"></s:url>


<s:url id="viewPart" action="getCustomerMapdata" namespace="/view/Over2Cloud/CorporatePatientServices/cpservices">
<s:param name="status" value="%{status}"/>
</s:url>
<div id="time_picker_div" style="display:none">
     <sj:datepicker id="res_Time_pick" showOn="focus" timepicker="true" timepickerOnly="true" timepickerGridHour="4" timepickerGridMinute="10" timepickerStepMinute="10" />
</div>
<sjg:grid 
		 
          id="gridedittable"
          href="%{viewPart}"
          gridModel="masterViewList"
          navigator="true"
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
          pagerInput="false"   
          multiselect="false"
          editurl="%{viewModifyEsc}"
          navigatorSearchOptions="{sopt:['eq','cn']}"  
          loadingText="Requesting Data..."  
          navigatorEditOptions="{height:230,width:400,closeAfterEdit:true,reloadAfterSubmit:false}"
          shrinkToFit="false"
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
		name="customer_name"
		index="customer_name"
		title="Customer Name"
		width="140"
		align="center"
		editable="false"
		/>
		
		<sjg:gridColumn 
		name="customer_id"
		index="customer_id"
		title="Customer Code"
		width="140"
		align="center"
		editable="false"
		/>
		
		<sjg:gridColumn 
		name="discount"
		index="discount"
		title="Discount"
		width="140"
		align="center"
		editable="false"
		/>
		
		<sjg:gridColumn 
		name="package_id"
		index="package_id"
		title="Health Checkup Name"
		width="140"
		align="center"
		editable="false"
		/>
		
		<sjg:gridColumn 
		name="billing_grp_id"
		index="billing_grp_id"
		title="Billing Group Name"
		width="140"
		align="center"
		editable="false"
		/>
		<sjg:gridColumn 
		name="bil_id"
		index="bil_id"
		title="Account Manager"
		width="140"
		align="center"
		editable="false"
		/>

		
		<sjg:gridColumn 
		name="user"
		index="user"
		title="User Name"
		width="150"
		align="center"
		editable="false"
		edittype="select"
		/>
		<sjg:gridColumn 
		name="date"
		index="date"
		title="Date"
		width="130"
		align="center"
		editable="true"
		/>
		
		<sjg:gridColumn 
		name="time"
		index="time"
		title="Time"
		width="150"
		align="center"
		editable="false"
		/>
		
		<sjg:gridColumn 
		name="status"
		index="status"
		title="Status"
		width="150"
		align="center"
		editable="true"
		edittype="select"
		editoptions="{value:'Active:Active;InActive:InActive'}"
		/>
		
		
</sjg:grid>
</div>
</div>
</div>
</div>
</body>

</html>