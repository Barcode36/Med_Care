<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script type="text/javascript" src="<s:url value="/js/communication/Communicationmsgdraft.js"/>"></script>

<script type="text/javascript">

var row=0;
$.subscribe('rowselect', function(event, data) {
	row = event.originalEvent.id;
});

function templateAction()
{
	var valuepassed;
	valuepassed = $("#gridedittable").jqGrid('getGridParam','selarrrow');
	if(valuepassed=="")
	{
	     alert("Please select atleast one check box...");        
	     return false;
	}
 	$("#takeActionGrid").load("<%=request.getContextPath()%>/view/Over2Cloud/CommunicationOver2Cloud/template/addResponse.action?id="+valuepassed);
	$("#takeActionGrid").dialog('open');

}


function editRow()
{
	var  row = $("#gridedittable").jqGrid('getGridParam','selarrrow');
	if(row!="null" && row!=""){
	jQuery("#gridedittable").jqGrid('editGridRow', row ,{height:250, width:425,reloadAfterSubmit:false,closeAfterEdit:true,top:0,left:350,modal:true});
	}else{
	alert("Please Select atleast one Row.");
		
	}
}
	
function searchRow(){
	jQuery("#gridedittable").jqGrid('searchGrid', {multipleSearch:false,reloadAfterSubmit:true,top:0,left:350,modal:true} );
}
	
	function deleteRow(){
		 var row = $("#gridedittable").jqGrid('getGridParam','selarrrow');
		$("#gridedittable").jqGrid('delGridRow',row, {height:120,reloadAfterSubmit:true,top:0,left:50,modal:true});}
	
	
	function refreshRow(row, result) {
		  $("#gridedittable").trigger("reloadGrid");}


function addTemplate()
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif><br/>Loading...</center>");
	$.ajax({
	    type : "post",
	    url  : "view/Over2Cloud/CommunicationOver2Cloud/template/addTemplateData.action",
	    success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });

	}
function searchshowleaddata()
{
	
	var actionStatus;
	actionStatus=$("#actionStatus").val();
	
	if(actionStatus == 'All')
	{	
		$.ajax({
	 		type :"post",
	 		url :"view/Over2Cloud/CommunicationOver2Cloud/template/beforesearchTemplateView.action?&actionStatus="+actionStatus,
	 		success : function(subdeptdata) 
		    {
				$("#datapartresponce").html(subdeptdata);
			},
		    error: function()
		    {
		        alert("error");
		    }
	 	});
	}

	if(actionStatus == 'Approve')
	{	
		$.ajax({
	 		type :"post",
	 		url :"view/Over2Cloud/CommunicationOver2Cloud/template/beforesearchTemplateView.action?&actionStatus="+actionStatus,
	 		success : function(subdeptdata) 
		    {
				$("#datapartresponce").html(subdeptdata);
			},
		    error: function()
		    {
		        alert("error");
		    }
	 	});
	}
	
	if(actionStatus=='Pending')
	{	
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/CommunicationOver2Cloud/template/beforesearchTemplateView.action?actionStatus="+actionStatus,
		    success : function(subdeptdata) {
	       $("#"+"datapartresponce").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	}
	
	if(actionStatus=='Reject')
	{	
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/CommunicationOver2Cloud/template/beforesearchTemplateView.action?actionStatus="+actionStatus,
		    success : function(subdeptdata) {
	       $("#"+"datapartresponce").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	}
	
}
</script>
<script type="text/javascript">
function templateFormatter(cellValue,options,rowObject)
{
return "<a href='#' onClick='templateFormatterss("+rowObject.id+")'>Use Template</a>";
}
function templateFormatterss(valuepassed) 
{
	var status=jQuery("#gridedittable").jqGrid('getCell',valuepassed,'status');
	if (status=='Approve') 
	{
		$("#takeActionGridss").load("<%=request.getContextPath()%>/view/Over2Cloud/CommunicationOver2Cloud/template/templateSubmission.action?id="+valuepassed);
	    $("#takeActionGridss").dialog('open');
	} 
	else 
	{
		alert("You are not Authorize to use Template!!!");
	}
		return false;
}

</script>

</head>
<body>
<div class="clear"></div>
<div class="list-icon">
	<div class="head">Template</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div><div class="head">View</div>

</div>
<div class="clear"></div>
<div id="date_picker_fromdate" style="display:none">
 <sj:datepicker name="fromdate"  id="fromdates" value="fromdate"  cssStyle="margin:0px 0px 10px 0px"   changeMonth="true" cssStyle="margin:0px 0px 10px 0px" changeYear="true" yearRange="1890:2020" showOn="focus" displayFormat="dd-mm-yy" cssClass="form_menu_inputtext" />
 </div>
 <div id="date_picker_todate" style="display:none">
 <sj:datepicker name="todate"  id="todates"  value="todate" cssStyle="margin:0px 0px 10px 0px"   changeMonth="true" cssStyle="margin:0px 0px 10px 0px" changeYear="true" yearRange="1890:2020" showOn="focus" displayFormat="dd-mm-yy" cssClass="form_menu_inputtext" />
 </div>
<br>
<div style=" float:left; padding:10px 1%; width:98%;">
<div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
<div class="pwie">
<table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%">
<tbody><tr></tr><tr><td></td></tr><tr><td> 
<table class="floatL" border="0" cellpadding="0" cellspacing="0">
<tbody><tr><td class="pL10"> 
					<sj:a id="editButton" title="Edit"  buttonIcon="ui-icon-pencil" cssStyle="height:25px; width:32px" cssClass="button" button="true" onclick="editRow()"></sj:a>
					<sj:a id="delButton" title="Deactivate" buttonIcon="ui-icon-trash" cssStyle="height:25px; width:32px" cssClass="button" button="true"  onclick="deleteRow()"></sj:a>
					<sj:a id="searchButton" title="Search" buttonIcon="ui-icon-search" cssStyle="height:25px; width:32px" cssClass="button" button="true"  onclick="searchRow()"></sj:a>
					<sj:a id="refButton" title="Refresh" buttonIcon="ui-icon-refresh" cssStyle="height:25px; width:32px" cssClass="button" button="true"  onclick="refreshRow();"></sj:a>
					
                   <s:select id="actionStatus" name="actionStatus"  list="#{'All':'All Status','Approve':'Approve','Pending':'Pending','Reject':'Reject'}" cssClass="button" onchange="searchshowleaddata(this.value);" cssStyle="margin-top: -26px;margin-left: 2px;height: 26px;"/>	     
                    <sj:a id="action" cssClass="button" button="true" title="Action" onclick="templateAction();">Action</sj:a> 
	                   
				 
</td>
</tr>
</tbody>
</table>
</td>
<td class="alignright printTitle">
<sj:a id="addButton1"  button="true"  buttonIcon="ui-icon-plus" cssClass="button" onclick="addTemplate();">Add</sj:a>
</td> 
</tr>
</tbody>
</table>
</div>
</div>

<s:url id="ViewTemplatesss" action="viewTemplateDataGrid" >
</s:url>
<s:url id="modifyTemplatesss" action="modifyTemplate" >
</s:url>
<div id="datapartresponce">
<sjg:grid 
		  id="gridedittable"
          href="%{ViewTemplatesss}"
          gridModel="viewTemplateData"
          navigator="true"
          navigatorAdd="false"
          navigatorView="false"
          navigatorDelete="true"
          groupField="['status']"
          groupColumnShow="[true]"
          groupCollapse="false"
          groupText="['<b>{0}: {1}</b>']"
          navigatorEdit="true"
          navigatorSearch="true"
          rowList="15,20,25,30"
          rownumbers="-1"
          viewrecords="true"       
          pager="true"
          pagerButtons="true"
          pagerInput="false"   
          multiselect="true"  
          loadingText="Requesting Data..."  
          rowNum="15"
          navigatorSearchOptions="{sopt:['eq','cn']}"
          editurl="%{modifyTemplatesss}"
          navigatorEditOptions="{reloadAfterSubmit:true}"
          shrinkToFit="false"
          autowidth="true"
          onSelectRowTopics="rowselect"
          
          >
       
		
          <s:iterator value="viewTemplateGrid" id="viewTemplateGrid" >  
       <s:if test="colomnName=='template_type'">
          <sjg:gridColumn 
			name="%{colomnName}"
		index="%{colomnName}"
		title="%{headingName}"
		width="%{width}"
		align="%{align}"
		editable="%{editable}"
		formatter="%{formatter}"
		search="%{search}"
		hidden="%{hideOrShow}"
		/> 
          </s:if>
          <s:elseif test="colomnName=='template_name'">
          <sjg:gridColumn 
			name="%{colomnName}"
		index="%{colomnName}"
		title="%{headingName}"
		width="%{width}"
		align="%{align}"
		editable="%{editable}"
		formatter="%{formatter}"
		search="%{search}"
		hidden="%{hideOrShow}"
		/> 
          </s:elseif>
          <s:else>
		<sjg:gridColumn 
		name="%{colomnName}"
		index="%{colomnName}"
		title="%{headingName}"
		width="%{width}"
		align="%{align}"
		editable="%{editable}"
		formatter="%{formatter}"
		search="%{search}"
		hidden="%{hideOrShow}"
		/>
		</s:else>
		</s:iterator> 
</sjg:grid>
</div>
</div>

<sj:dialog
          id="takeActionGrid"
          showEffect="slide"
          hideEffect="explode"
          autoOpen="false"
          title="Take Action For Template"
          modal="true"
          closeTopics="closeEffectDialog"
          width="930"
          height="350"
          draggable="true"
    	  resizable="true"
    	 buttons="{ 
				'Cancel':function() { cancelButtonsendsms(); } 
				}"
          >
</sj:dialog>
<sj:dialog
          id="takeActionGridss"
          showEffect="slide"
          hideEffect="explode"
          autoOpen="false"
          title="Use Template"
          modal="true"
          closeTopics="closeEffectDialog"
          width="600"
          height="300"
          draggable="true"
    	  resizable="true"
    	 buttons="{ 
				'Cancel':function() { cancelButtonsendsms(); } 
				}"
    	 
          >
</sj:dialog>

</body>
</html>