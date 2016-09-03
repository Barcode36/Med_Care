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
<script src="<s:url value="/js/feedback/feedback.js"/>"></script>
<SCRIPT type="text/javascript">

var datePicker = function(elem) {
    $(elem).datepicker({dateFormat:'dd-mm-yy'});
    $('#date_picker_div').css("z-index", 2000);
}

function addNewSubDept() {
	var conP = "<%=request.getContextPath()%>";
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
    $.ajax({
        type : "post",
        url : conP+"/view/Over2Cloud/newsAlertsConfig/beforeNewsAdd.action",
        success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
    },
       error: function() {
            alert("error");
        }
     });
	
}
function searchData()
{
	jQuery("#gridmultitable1").jqGrid('searchGrid', {multipleSearch:false,reloadAfterSubmit:true,top:0,left:350,modal:true} );
}
</SCRIPT>

<script type="text/javascript">
var row=0;
$.subscribe('rowselect', function(event, data) {
	row = event.originalEvent.id;
});	
function editRow()
{
	jQuery("#gridmultitable1").jqGrid('editGridRow', row ,{width:425,reloadAfterSubmit:false,closeAfterEdit:true,top:0,left:350,modal:true});
}

function deleteRow()
{
	$("#gridmultitable1").jqGrid('delGridRow',row, {reloadAfterSubmit:true,top:0,left:350,modal:true});
}
function reload(rowid, result) {
	  $("#gridmultitable1").trigger("reloadGrid"); 
	}
</script>
</head>
<body>
<div class="list-icon">
<div class="head">News & Alerts</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div><div class="head">View  </div>
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
						 <%if(userRights.contains("desi_MODIFY")) {%>       
                   <sj:a id="editButton" title="Edit"  buttonIcon="ui-icon-pencil" cssStyle="height:25px; width:32px" cssClass="button" button="true" onclick="editRow()"></sj:a>
			<%} %>
			<!-- <sj:a id="delButton" title="Deactivate" buttonIcon="ui-icon-trash" cssStyle="height:25px; width:32px" cssClass="button" button="true"  onclick="deleteRow()"></sj:a> -->
					
					<sj:a id="searchButton" title="Search" buttonIcon="ui-icon-search" cssStyle="height:25px; width:32px" cssClass="button" button="true"  onclick="searchData()"></sj:a>
					<sj:a id="refButton" title="Refresh" buttonIcon="ui-icon-refresh" cssStyle="height:25px; width:32px" cssClass="button" button="true"  onclick="reload()"></sj:a>
				
					    </td></tr>
					    </tbody>
					  </table>
				  </td>
				  
				  <!-- Block for insert Right Hand Side Button -->
				  <td class="alignright printTitle">
				   <%if(userRights.contains("desi_ADD")) {%>
				     <sj:a
							button="true"
							cssClass="button" 
							cssStyle="height:25px;"
			                title="Add"
				            buttonIcon="ui-icon-plus"
							onclick="addNewSubDept();"
							>Add</sj:a> 
							<%} %>
				  </td>
			</tr>
		</tbody>
	</table>
</div>
</div>

<!-- Code End for Header Strip -->
 <div class="clear"></div>
<div style="overflow: scroll; height: 430px;">
<div id="date_picker_div" style="display:none">
 <sj:datepicker name="date1" id="date1" value="datetoday" cssStyle="margin:0px 0px 10px 0px"   changeMonth="true" cssStyle="margin:0px 0px 10px 0px" changeYear="true" yearRange="1890:2020" showOn="focus" displayFormat="dd-mm-yy" cssClass="form_menu_inputtext" />
 </div>
<s:url id="viewNews" action="showAllNewsAlerts"/>
<s:url id="editNewsndevents" action="editNewsndevents"/>
		<sjg:grid
    	id="gridmultitable1"
    	dataType="json"
    	href="%{viewNews}"
    	pager="true"
    	navigator="true"
    	navigatorSearch="true"
    	navigatorSearchOptions="{sopt:['eq','cn']}"
    	navigatorAddOptions="{height:280,reloadAfterSubmit:true}"
    	navigatorEditOptions="{height:280,closeAfterEdit:true,reloadAfterSubmit:false}"
    	navigatorView="true"
    	navigatorDelete="true"
    	navigatorAdd="false"
    	navigatorEdit="true"
    	gridModel="newsAlertsList"
    	multiselect="false"
    	rowList="15,20,30"
    	rowNum="15"
    	autowidth="true"
    	rownumbers="true"
    	editurl="%{editNewsndevents}"
    	onSelectRowTopics="rowselect"
    	shrinkToFit="false"
    >
    <sjg:gridColumn name="module_name" index="module_name" title="Module" editable="false" sortable="true" search="true" searchoptions="{sopt:['eq','cn']}" width="150" />
    	<sjg:gridColumn name="id" index="id" title="ID" editable="true" sortable="false" hidden="true" />
    	<sjg:gridColumn name="newsName" index="name" title="Name" editable="true" sortable="true" search="true" searchoptions="{sopt:['eq','cn']}" width="265"/>
    	<sjg:gridColumn name="newsDesc" index="desc" title="Description" editable="true" sortable="true" search="true" searchoptions="{sopt:['eq','cn']}" width="500" />
    	<sjg:gridColumn name="addedBy" index="addedBy" title="Visible To" editable="false" sortable="true" search="true" searchoptions="{sopt:['eq','cn']}" width="500" />
    	<sjg:gridColumn name="startDate" index="startDate" align="center" title="Start Date" editable="true" editoptions="{dataInit:datePicker}" sortable="true" search="true" searchoptions="{sopt:['eq','cn']}" width="100" />
    	<sjg:gridColumn name="endDate" index="endDate" align="center"  title="End Date" editable="true" editoptions="{dataInit:datePicker}" sortable="true" search="true" searchoptions="{sopt:['eq','cn']}" width="100" />
    </sjg:grid>
    </div>
   
</div>
</body>
</html>