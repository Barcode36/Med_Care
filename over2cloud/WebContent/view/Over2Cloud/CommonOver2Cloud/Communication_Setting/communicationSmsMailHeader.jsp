<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<% String userRights=session.getAttribute("userRights")== null ? "" : session.getAttribute("userRights").toString();%>
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link type="text/css" href="<s:url value="/css/style.css"/>" rel="stylesheet" />
<script type="text/javascript" src="<s:url value="/js/js.js"/>"></script>
<SCRIPT type="text/javascript">
$.subscribe('sendsms', function(event,data) {
	var sel_id;
	sel_id = $("#smsgrid").jqGrid('getGridParam','selarrrow');
	if(sel_id=="")
	   {
	     alert("Please select atleast a ckeck box...");        
	     return false;
	   }
	 $.ajax({
         type : "post",
         url : "/over2cloud/view/Over2Cloud/CommonOver2Cloud/Communication_Setting/updateCommunication.action?selectedId="+sel_id+"&via_mode=sms",
         success : function(feeddata) {
      $("#resolverAjaxDiv").html(feeddata);
       },
 error: function() {
   alert("error");
}
});
    	
	});
function viewEmail()
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/CommonOver2Cloud/Communication_Setting/getCommunicationDataHeaderEmail.action",
	    success : function(feeddraft) {
       $("#"+"data_part").html(feeddraft);
	},
	   error: function() {
            alert("error");
        }
	 });
}
function onChangeDate()
{
	var inTimeDate=$("#inTimeDate").val();
	var updateTimeDate=$("#updateTimeDate").val();
	var module=$("#moduleName").val();
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/CommonOver2Cloud/Communication_Setting/getCommunicationData.action?inTimeDate="+inTimeDate+"&updateTimeDate="+updateTimeDate+"&moduleName="+module,
	    success : function(feeddraft) {
       $("#datapart").html(feeddraft);
	},
	   error: function() {
            alert("error");
        }
	 });
}

function loadGrid()
{
	var module=$("#moduleName").val();
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/CommonOver2Cloud/Communication_Setting/getCommunicationData.action?moduleName="+module,
	    success : function(feeddraft) {
       $("#datapart").html(feeddraft);
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
<div class="clear"></div>
<div class="middle-content">
<div class="list-icon">
	<div class="head">SMS Resend</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div><div class="head">Total Records: <s:property value="%{counter}"/></div>
</div>
<div class="clear"></div>
<div class="listviewBorder" style="margin-top: 10px;width: 97%;margin-left: 20px;" align="center">
<div style="overflow: scroll; height: 480px;">
<div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
<div class="pwie">
	<table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%">
		<tbody>
			<tr>
				  <!-- Block for insert Left Hand Side Button -->
				  <td>
					  <table class="floatL" border="0" cellpadding="0" cellspacing="0">
					    <tbody><tr><td class="pL10">
					    	 
						       <sj:datepicker cssStyle="height: 16px; width: 70px;" cssClass="button" id="inTimeDate" name="inTimeDate" size="20" maxDate="0"  value="%{inTimeDate}" readonly="true"   yearRange="2013:2050" showOn="focus" displayFormat="dd-mm-yy" Placeholder="Insert Date"/>
						      	<sj:datepicker cssStyle="height: 16px; width: 78px;" cssClass="button" id="updateTimeDate" name="updateTimeDate" size="20" maxDate="0" onchange="onChangeDate()" value="today" readonly="true"   yearRange="2013:2050" showOn="focus" displayFormat="dd-mm-yy" Placeholder="Update Date"/> 
					       </td></tr></tbody>
					  </table>
				  </td>
				  <td class="alignright printTitle">
				    <sj:a id="addButton14"  button="true"  buttonIcon="ui-icon-plus" cssClass="button" onclick="viewEmail();">Email</sj:a>
				  </td>
			</tr>
		</tbody>
	</table>
</div>
</div>
<s:hidden id="moduleName" value="%{moduleName}"/>
<div id="datapart"></div>
<div class="clear"></div>
<br>
<div align="center">
<sj:submit value=" Resend " button="true" onClickTopics="sendsms"/>
</div>
<br>
<div id="resolverAjaxDiv"></div>
</div>
</div>
</div>
</body>
</html>