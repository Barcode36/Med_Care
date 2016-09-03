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
function emailAction(cellvalue, options, rowObject) 
{
   return "<a href='#' onClick='complianceTakeAction("+rowObject.id+")'>See Email Content</a>";
}
function complianceTakeAction(valuepassed) 
{
   $("#emailDialog").load("<%=request.getContextPath()%>/view/Over2Cloud/CommonOver2Cloud/Communication_Setting/printEmail.action?id="+valuepassed);
   $("#emailDialog").dialog('open');
   return false;
}

$.subscribe('sendemail', function(event,data) {
	var sel_id;
	sel_id = $("#emailgrid").jqGrid('getGridParam','selarrrow');
	if(sel_id=="")
	   {
	     alert("Please select atleast a ckeck box...");        
	     return false;
	   }
	 $.ajax({
         type : "post",
         url : "/over2cloud/view/Over2Cloud/CommonOver2Cloud/Communication_Setting/updateCommunication.action?selectedId="+sel_id+"&via_mode=mail",
         success : function(feeddata) {
      $("#resolverAjaxDiv").html(feeddata);
       },
 error: function() {
   alert("error");
}
});   
    	
	});
	
function onchangeDateforMail()
{
	var insertDate=$("#insertDate").val();
	var updateDateForMail=$("#updateDateForMail").val();
	var module=$("#moduleName").val();
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/CommonOver2Cloud/Communication_Setting/getCommunicationDataForMail.action?insertDate="+insertDate+"&updateDateForMail="+updateDateForMail+"&moduleName="+module,
	    success : function(feeddraft) {
       $("#data_partsss").html(feeddraft);
	},
	   error: function() {
            alert("error");
        }
	 });
}
	
function loadGridforMail()
{
	var module=$("#moduleName").val();
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/CommonOver2Cloud/Communication_Setting/getCommunicationDataForMail.action?moduleName="+module,
	    success : function(feeddraft) {
       $("#data_partsss").html(feeddraft);
	},
	   error: function() {
            alert("error");
        }
	 });
}

loadGridforMail();

</SCRIPT>

</head>
<body>
<sj:dialog
          id="emailDialog"
          showEffect="slide"
          hideEffect="explode"
          autoOpen="false"
          title="Email Content"
          modal="true"
          closeTopics="closeEffectDialog"
          width="750"
          height="550"
          >
</sj:dialog>
<div id="ticketsInfo"></div>


<div class="clear"></div>
<div class="middle-content">
<div class="list-icon">
	<div class="head">Mail Resend</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div><div class="head">Total Records: <s:property value="%{counter}"/></div>
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
					    	 
						       <sj:datepicker cssStyle="height: 16px; width: 70px;" cssClass="button" id="insertDate" name="insertDate"  value="%{insertDate}" size="20" maxDate="0"   readonly="true"   yearRange="2013:2050" showOn="focus" displayFormat="dd-mm-yy" Placeholder="Insert Date"/>
						      	<sj:datepicker cssStyle="height: 16px; width: 78px;" cssClass="button" id="updateDateForMail" name="updateDateForMail" size="20" maxDate="0" value="today" readonly="true" onchange="onchangeDateforMail()"  yearRange="2013:2050" showOn="focus" displayFormat="dd-mm-yy" Placeholder="Update Date"/>
					       </td></tr></tbody>
					  </table>
				  </td>
				  <td class="alignright printTitle">
				 
				  </td>
			</tr>
		</tbody>
	</table>
</div>
</div>
<div id="data_partsss"></div>
<div class="clear"></div>
<s:hidden id="moduleName" value="%{moduleName}"/>
<br>
<div align="center">
<sj:submit value=" Resend " button="true" onClickTopics="sendemail"/>
</div>
<br>
<div id="resolverAjaxDiv"></div>
</div>
</div>
</div>
</body>
</html>