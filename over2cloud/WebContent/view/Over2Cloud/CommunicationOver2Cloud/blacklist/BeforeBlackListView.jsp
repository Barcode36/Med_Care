<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>

<script type="text/javascript" src="<s:url value="/js/communication/CommuincationBlackList.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/helpdesk/feedback.js"/>"></script>
<script type="text/javascript">
function editRow()
{
     var row = $("#gridblacklistid").jqGrid('getGridParam','selarrrow');
	jQuery("#gridblacklistid").jqGrid('editGridRow', row ,{height:240, width:425,reloadAfterSubmit:false,closeAfterEdit:true,top:0,left:350,modal:true});
}
	function deleteRow(){
		 var row = $("#gridblacklistid").jqGrid('getGridParam','selarrrow');
		$("#gridblacklistid").jqGrid('delGridRow',row, {height:120,reloadAfterSubmit:false,top:0,left:50,modal:true});}
	
	function searchRow(){
		jQuery("#gridblacklistid").jqGrid('searchGrid', {multipleSearch:false,reloadAfterSubmit:true,top:0,left:350,modal:true} );
	}
	function refreshRow(row, result) {
		  $("#gridblacklistid").trigger("reloadGrid"); 
	}
	
function getmobilenumberdata(mobile){
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/CommunicationOver2Cloud/Comm/BlackListGridView.action?mobileNo="+mobile,
	    success : function(subdeptdata) {
       $("#"+"viewDataDiv").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });
}
function getselecteddate(){

	var from_date= $('#from_date').val();
	var  to_date= $("#to_date").val();
	 	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/CommunicationOver2Cloud/Comm/BlackListGridView.action?from_date="+from_date+"&to_date="+to_date,
	    success : function(subdeptdata) {
       $("#"+"viewDataDiv").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });
}

function viewBlackList() {
	 
	       $.ajax({
            type : "post",
            url : "view/Over2Cloud/CommunicationOver2Cloud/Comm/BlackListGridView.action",
            success : function(subdeptdata) {
       $("#"+"viewDataDiv").html(subdeptdata);
        },
           error: function() {
            alert("error");
        }
         });

}
StopRefresh();
viewBlackList();
</script>
<SCRIPT type="text/javascript">
var datePicker = function(elem) {
    $(elem).datepicker({dateFormat:'dd-mm-yy'});
    $('#date_picker_fromdate').css("z-index", 2000);
    $('#date_picker_todate').css("z-index", 2000);
    
}
</SCRIPT>

</head>
<body>
<div class="clear"></div>
<div class="middle-content">
<div class="list-icon">
	<div class="head">Exclusion</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div><div class="head">View</div>

</div>

<div class="clear"></div>
<div id="date_picker_fromdate" style="display:none">
 <sj:datepicker name="fromdate"  id="fromdates" value="fromdate"  cssStyle="margin:0px 0px 10px 0px"   changeMonth="true" cssStyle="margin:0px 0px 10px 0px" changeYear="true" yearRange="1890:2020" showOn="focus" displayFormat="dd-mm-yy" cssClass="form_menu_inputtext" />
 </div>
 <div id="date_picker_todate" style="display:none">
 <sj:datepicker name="todate"  id="todates"  value="todate" cssStyle="margin:0px 0px 10px 0px"   changeMonth="true" cssStyle="margin:0px 0px 10px 0px" changeYear="true" yearRange="1890:2020" showOn="focus" displayFormat="dd-mm-yy" cssClass="form_menu_inputtext" />
 </div>

<div class="listviewBorder" style="margin-top: 5px;width: 100%;"  align="center">
<div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
<div class="pwie">
<table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%">
<tbody><tr></tr><tr><td></td></tr><tr><td> 
<table class="floatL" border="0" cellpadding="0" cellspacing="0">
<tbody><tr><td class="pL10"> 
<sj:a id="editButton" title="Edit"  buttonIcon="ui-icon-pencil" cssStyle="height:25px; width:32px"   cssClass="button" button="true"      onclick="editRow();"  ></sj:a>
					<sj:a id="delButton" title="Deactivate" buttonIcon="ui-icon-trash" cssStyle="height:25px; width:32px" cssClass="button" button="true"  onclick="deleteRow()"></sj:a>
					<sj:a id="searchButton" title="Search" buttonIcon="ui-icon-search" cssStyle="height:25px; width:32px" cssClass="button" button="true"  onclick="searchRow()"></sj:a>
					<sj:a id="refButton" title="Refresh" buttonIcon="ui-icon-refresh" cssStyle="height:25px; width:32px" cssClass="button" button="true"  onclick="refreshRow()"></sj:a>	
					&nbsp;Mobile No:<s:textfield name="mobileNo" id="mobileNo"  cssClass="button"     onkeyup="getmobilenumberdata(this.value);"  cssStyle="margin-top: 1px;margin-left: 2px;height: 19px; width: 10%;"   theme="simple"/>
				    From:<sj:datepicker name="from_date"  id="from_date"  showOn="focus"  displayFormat="dd-mm-yy"  value="From date"   onchange ="getselecteddate();"     yearRange="2014:2020" readonly="true" cssClass="button" style="margin: 0px 6px 10px; width: 12%;"/>
					To:<sj:datepicker name="to_date"  id="to_date"  showOn="focus"  displayFormat="dd-mm-yy"   value="To Date"   onchange="getselecteddate();"   yearRange="2014:2020" readonly="true"   cssClass="button"   style="margin: 0px 6px 10px; width: 12%;"/>
				    
</td></tr></tbody></table>
</td>
<td class="alignright printTitle">
<sj:a  button="true" 
				           cssClass="button" 
				           cssStyle="height:25px; width:32px"
				           title="Download"
				           buttonIcon="ui-icon-arrowstop-1-s" 
				           onclick="excelDownload();" />
				           
 <sj:a  button="true" 
				           cssClass="button" 
			               cssStyle="height:25px; width:32px"
			               title="Upload"
				           buttonIcon="ui-icon-arrowstop-1-n" 
				           onclick="excelUpload();" />
<sj:a id="addButton"  button="true"  buttonIcon="ui-icon-plus" cssClass="button" onclick="addBlackList();">Add</sj:a>
</td> 
</tr></tbody></table></div></div></div>
 
  <div id="viewDataDiv" style="overflow:scroll ; max-height:400px; " align="center" ></div>
 
 
</body>
</html>