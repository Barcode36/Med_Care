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
function editEscalation()
{
	
	if(row==0 )
	{
	     alert("Please select atleast one check box...");        
	     return false;
	}
    else
    {
    	var subDept = jQuery("#gridedittable").jqGrid('getCell',row,'subDept');
    	 $("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
        $.ajax
        (
            {
                type : "post",
                url : "view/Over2Cloud/labOrder/getEscalation4Edit.action?id="+row+"&subDept="+subDept,
                success : function(data)
                {
                    $('#data_part').html(data);
                },
                error : function()
                {
                    alert("Error");
                }    
            }
        );
    }
};


function tat(cellvalue, options, row)
{
	//alert(row.escalation_date);
	if(row.escalation_date == '00:00' )
	{
		return "<a href='#' title='Out of TAT' style='font-weight:bold' class='blink_me'><font color='RED' >"+cellvalue+"</font></a>";
	}
	
	
	else
		{
		return "<a href='#' title='' style='color:green' >"+cellvalue+"</font></a>";
		}
	
}
function deleteRow()
{
	if(row==0)
	{
		alert("Please select atleast one row.");
	}
	$("#gridedittable").jqGrid('delGridRow',row, {height:120,reloadAfterSubmit:true,top:0,left:350,modal:true});
}
function searchRow()
{
	 $("#gridedittable").jqGrid('searchGrid', {sopt:['eq','cn']} );
}
function reloadRow()
{
	labOrderViewEscalation();
}	

/* function searchResult(searchField, searchString, searchOper)
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
 */

function addEscalation()
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/labOrder/beforeConfigEscalation.action",
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
	 Lab Order Escalation</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div>
	 <div class="head">View</div> 
</div>
<div class="rightHeaderButton"></div>
<div class="clear"></div>
<div>
<div id="alphabeticalLinks"></div>
<!-- Code For Header Strip -->
<div class="listviewBorder" style="overflow:hidden;width: 97%;margin-left: 20px;" align="center">
<div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
<div class="pwie">
	<table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%">
		<tbody>
			<tr>
				  <td>
					  <table class="floatL" border="0" cellpadding="0" cellspacing="0">
					    <tbody><tr><td class="pL10">   
					     	<%-- <%if(userRights.contains("spde_MODIFY")) {%> --%>
								<sj:a id="editButton" title="Edit"  buttonIcon="ui-icon-pencil" cssClass="button" cssStyle="height:25px; width:32px" button="true" onclick="editEscalation()"></sj:a>
							<%-- <%} %> --%>
							<%-- <%if(userRights.contains("spde_DELETE")) {%> --%>
									<!--<sj:a id="delButton" title="Delete" buttonIcon="ui-icon-trash" cssClass="button" cssStyle="height:25px; width:32px" button="true"  onclick="deleteRow()"></sj:a>
							--><%-- <%} %> --%>	
							<%-- 	<sj:a id="searchButton" title="Search" buttonIcon="ui-icon-search" cssClass="button" cssStyle="height:25px; width:32px" button="true"  onclick="searchRow()"></sj:a> --%>
								<sj:a id="refButton" title="Refresh" buttonIcon="ui-icon-refresh" cssClass="button" cssStyle="height:25px; width:32px" button="true" onclick="reloadRow();"></sj:a>
		
					      </td></tr></tbody>
					  </table>
				  </td>
				  <!-- Block for insert Right Hand Side Button -->
				  <td class="alignright printTitle">
				  <sj:a id="addButton"  button="true"  buttonIcon="ui-icon-plus" cssClass="button" onclick="addEscalation();">Add</sj:a>
				  </td>
			</tr>
		</tbody>
	</table>
</div>
</div>
<div style="overflow: scroll; height: 400px;width:100%;">
<s:url id="viewPart" action="getEscalationDataOnGrid" namespace="/view/Over2Cloud/labOrder">
</s:url>
<div id="time_picker_div" style="display:none">
     <sj:datepicker id="res_Time_pick" showOn="focus" timepicker="true" timepickerOnly="true" timepickerGridHour="4" timepickerGridMinute="10" timepickerStepMinute="10" />
</div>
<sjg:grid 
		 
          id="gridedittable"
          href="%{viewPart}"
          gridModel="masterViewList"
          navigator="false"
          navigatorAdd="false"
          navigatorView="false"
          navigatorSearch="false"
          navigatorDelete="false"
          navigatorEdit="false"
          editinline="false"
          rowList="50,100,200"
          rowNum="50"
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
		name="orderStatus"
		index="orderStatus"
		title="Order Status"
		width="140"
		align="center"
		editable="false"
		hidden="false"
		/>
	
	<sjg:gridColumn 
		name="lab_name"
		index="lab_name"
		title="Lab Name"
		width="140"
		align="center"
		editable="false"
		hidden="false"
		/>
		
		<sjg:gridColumn 
		name="priority"
		index="priority"
		title="Priority"
		width="140"
		align="center"
		editable="true"
		edittype="select"
		editoptions="{value:'Urgent:Urgent;Routine:Routine;Stat:Stat'}"
		
		/>
		<sjg:gridColumn 
		name="l2"
		index="l2"
		title="L2"
		width="150"
		align="center"
		editable="true"
		edittype="select"
		editoptions="{value:'Customised:Customised;Multiplicative:Multiplicative'}"
		/>
		<sjg:gridColumn 
		name="tat2"
		index="tat2"
		title="L1 To L2 Time"
		width="130"
		align="center"
		editable="true"
		editoptions="{dataInit:timePick}"
		/>
		
		<sjg:gridColumn 
		name="fromTime"
		index="fromTime"
		title="From Time"
		width="130"
		align="center"
		editable="true"
		editoptions="{dataInit:timePick}"
		/>
		
		<sjg:gridColumn 
		name="toTime"
		index="toTime"
		title="To Time"
		width="130"
		align="center"
		editable="true"
		editoptions="{dataInit:timePick}"
		/>
		
		
		<sjg:gridColumn 
		name="firstEsc"
		index="firstEsc"
		title="First Escalation Time"
		width="130"
		align="center"
		editable="true"
		editoptions="{dataInit:timePick}"
		/>
		
		<sjg:gridColumn 
		name="firstEscFlag"
		index="firstEscFlag"
		title="Escalation Time Flag"
		width="130"
		align="center"
		editable="true"
		editoptions="{value:'1:Active;0:Deactive'}"
		/>
		
		
		
		
		
		<sjg:gridColumn 
		name="l3"
		index="l3"
		title="L3"
		width="150"
		align="center"
		editable="true"
		edittype="select"
		editoptions="{value:'Customised:Customised;Multiplicative:Multiplicative'}"
		/>
		<sjg:gridColumn 
		name="tat3"
		index="tat3"
		title="L2 To L3 Time"
		width="130"
		align="center"
		editable="true"
		editoptions="{dataInit:timePick}"
		/>
		
		<sjg:gridColumn 
		name="l4"
		index="l4"
		title="L4"
		width="140"
		align="center"
		editable="true"
		edittype="select"
		editoptions="{value:'Customised:Customised;Multiplicative:Multiplicative'}"
		/>
		<sjg:gridColumn 
		name="tat4"
		index="tat4"
		title="L3 To L4 Time"
		width="130"
		align="center"
		editable="true"
		editoptions="{dataInit:timePick}"
		/>
		
		<sjg:gridColumn 
		name="l5"
		index="l5"
		title="L5"
		width="140"
		align="center"
		editable="true"
		edittype="select"
		editoptions="{value:'Customised:Customised;Multiplicative:Multiplicative'}"
		/>
		<sjg:gridColumn 
		name="tat5"
		index="tat5"
		title="L4 To L5 Time"
		width="130"
		align="center"
		editable="true"
		editoptions="{dataInit:timePick}"
		/>
		<sjg:gridColumn 
		name="l6"
		index="l6"
		title="L6"
		width="140"
		align="center"
		editable="true"
		edittype="select"
		editoptions="{value:'Customised:Customised;Multiplicative:Multiplicative'}"
		/>
		<sjg:gridColumn 
		name="tat6"
		index="tat6"
		title="L5 To L6 Time"
		width="130"
		align="center"
		editable="true"
		editoptions="{dataInit:timePick}"
		/>
		
		<sjg:gridColumn 
		name="subDept"
		index="subDept"
		title="Sub Dept ID"
		width="100"
		align="center"
		editable="false"
		hidden="true"
		 
		/>
		
</sjg:grid>
</div>
</div>
</div>
</div>
</body>

</html>