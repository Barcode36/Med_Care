<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script type="text/javascript" src="<s:url value="/js/helpdesk/reportconfig.js"/>"></script>
<script type="text/javascript">
timePick = function(elem) {
        $(elem).timepicker({timeFormat: 'hh:mm'});
        $('#time_picker_div').css("z-index", 2000);
}

var datePick = function(elem) {
    $(elem).datepicker({dateFormat: 'dd-mm-yy'});
    $('#ui-datepicker-div').css("z-index", 2000);
}
</script>
<script type="text/javascript">
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
		jQuery("#gridreport").jqGrid('editGridRow', row ,{height:320, width:425,reloadAfterSubmit:false,closeAfterEdit:true,top:0,left:350,modal:true , afterSubmit: function () {
			reloadPage();
	    }});
		
	}
}

function deleteRow()
{
	  var status = jQuery("#gridreport").jqGrid('getCell',row,'status');
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
			$("#gridreport").jqGrid('delGridRow',row, {height:120, width:425,reloadAfterSubmit:true,closeAfterEdit:false,top:150,left:350,modal:true,caption:'Inactive',msg: "Inactive selected record(s)?",bSubmit: "Inactive" , afterSubmit: function () {
				reloadPage();
		    }});
			
		}
}
function searchRow()
{
	 $("#gridreport").jqGrid('searchGrid', {sopt:['eq','cn']} );
}
function refreshRow()
{
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Daily_Report/beforeDailyReportConfigViewHeader.action?pageType=SC",
	    success : function(subdeptdata) {
       $("#"+"moreSettingDiv").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });
	
}
function addNewReportConfig(page)
{
	var pagetype = $("#"+page).val();
	$("#moreSettingDiv").html("<br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Daily_Report/beforeDailyReportConfig.action?pageType="+pagetype,
	    success : function(subdeptdata) {
       $("#"+"moreSettingDiv").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });
}
function gridLoad()
{
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Daily_Report/beforeDailyReportConfigView.action?pageType=SC&searchField=status&searchString=Active&searchOper=eq",
	    success : function(subdeptdata) {
       $("#"+"result").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });
}
gridLoad();
function getOnChangePrimaryData(field,string)
{
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Daily_Report/beforeDailyReportConfigView.action?pageType=SC&searchField="+field+"&searchString="+string+"&searchOper=eq",
	    success : function(subdeptdata) {
       $("#"+"result").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });
}
function reloadPage()
{
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Daily_Report/beforeDailyReportConfigViewHeader.action?pageType=SC",
	    success : function(subdeptdata) {
       $("#"+"moreSettingDiv").html(subdeptdata);
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
	 <div class="head">Configured&nbsp;Report</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div><div class="head"><s:iterator value="count">
	<s:property value="%{key}"/>: <s:property value="%{value}"/>   
</s:iterator>
</div> 
</div> 

<div class="clear"></div>
<div class="listviewBorder" style="margin-top: 10px;width: 97%;margin-left: 20px;" align="center">
<!-- Code For Header Strip -->
<div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
<div class="pwie">
	<table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%">
		<tbody>
			<tr>
				  <!-- Block for insert Left Hand Side Button -->
				  <td>
					  <table class="floatL" border="0" cellpadding="0" cellspacing="0">
					    <tbody><tr><td class="pL10">    <!-- Insert Code Here -->  
					<sj:a id="editButton" title="Edit"  buttonIcon="ui-icon-pencil" cssStyle="height:25px; width:32px" cssClass="button" button="true" onclick="editRow()"></sj:a>
					<sj:a id="delButton" title="Inactive" buttonIcon="ui-icon-trash" cssStyle="height:25px; width:32px" cssClass="button" button="true"  onclick="deleteRow()"></sj:a>
					<sj:a id="searchButton" title="Search" buttonIcon="ui-icon-search" cssStyle="height:25px; width:32px" cssClass="button" button="true"  onclick="searchRow()"></sj:a>
					<sj:a id="refButton" title="Refresh" buttonIcon="ui-icon-refresh" cssStyle="height:25px; width:32px" cssClass="button" button="true"  onclick="refreshRow()"></sj:a>
					   	 <s:select  
							    		id					=		"module"
							    		name				=		"module"
							    		list				=		"moduleMap"
							      	    headerKey           =       "-1"
							            headerValue         =       "Select Module"
							            cssClass            =      "button"
							            cssStyle            =      "margin-top: -29px;margin-left:3px"
							            theme               =       "simple"
							      	 	onchange			=		"getOnChangePrimaryData('module',this.value) "
							      	 
						      	 
						      	 >
						      	 </s:select>
						      	  <s:select  
						    		id					=		"flag"
						    		name				=		"flag"
						    		list				=		"#{'Active':'Active','Inactive':'Inactive','-1':'All'}"
							       cssClass             =      "button"
							       cssStyle             =      "margin-top: -29px;margin-left:3px"
							      theme                 =       "simple"
						      	 onchange			=		"getOnChangePrimaryData('status',this.value) "
						      	 >
						      	 </s:select>
					           <s:select  
						      		id					=		"frequency"
						    		name				=		"frequency"
						    		list				=		"#{'D': 'Daily', 'W' : 'Weekly', 'M': 'Monthly', 'Q' : 'Quarterly', 'H' : 'Half Yearly' }"
						      		headerKey           =       "-1"
							        headerValue         =       "Select Frequency"
							       cssClass             =      "button"
							       cssStyle             =      "margin-top: -29px;margin-left:3px"
							      theme                 =       "simple"
						      	 onchange			=		"getOnChangePrimaryData('report_type',this.value) "
						      	 >
						      	 </s:select>
					    
					     </td></tr></tbody>
					  </table>
				  </td>
				  
				  <!-- Block for insert Right Hand Side Button -->
				  <td class="alignright printTitle">
				      <sj:a id="addButton"  button="true"  cssClass="button" cssStyle="height:25px;" title="Add" buttonIcon="ui-icon-plus" onclick="addNewReportConfig('pageType');">Configure Report</sj:a>
				  </td>
			</tr>
		</tbody>
	</table>
</div>
</div>
<!-- Code End for Header Strip -->
<div style="overflow: scroll; height: 280px;">
<s:hidden id="pageType" value="%{pageType}"/>
<div id="result"></div>
</div>
</div>
</div>
</body>
</html>