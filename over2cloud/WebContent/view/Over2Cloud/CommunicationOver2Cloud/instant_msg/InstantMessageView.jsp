<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script type="text/javascript" src="<s:url value="/js/communication/CommunicationInstantMessage.js"/>"></script>
<script type="text/javascript">
function associateHyperlink(cellvalue, options, row)
{
	return "<a href='#' onClick='javascript:openAssociateCatView("+row.id+")'>" + cellvalue + "</a>";
}
function openAssociateCatView(id)
{
	$("#categoryOfferingDiv").html("");
	$("#categoryOfferingDiv").load("<%=request.getContextPath()%>/view/Over2Cloud/wfpm/masters/beforeFetchAssociateCatCost.action?id="+id);
}

function addinstantmsggg()
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif><br/>Loading...</center>");
	$.ajax({
	    type : "post",
	    url  : "view/Over2Cloud/CommunicationOver2Cloud/Comm/beforeInstantMessageAdd.action",
	    success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });

	}
function selectstatus(id)
{
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/CommunicationOver2Cloud/Comm/getdataselectstatus.action?status="+id,
	    success : function(subdeptdata) {
       $("#"+"ajaxresponcediv").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });

	}
function selectsmstype(id)
{
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/CommunicationOver2Cloud/Comm/getdataselectstatus.action?smsType="+id,
	    success : function(subdeptdata) {
       $("#"+"ajaxresponcediv").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });
	}
	function getmobilenumberdata(mobile){
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/CommunicationOver2Cloud/Comm/getdataselectstatus.action?mobileNo="+mobile,
		    success : function(subdeptdata) {
	       $("#"+"ajaxresponcediv").html(subdeptdata);
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
		    url : "view/Over2Cloud/CommunicationOver2Cloud/Comm/getdataselecteddate.action?from_date="+from_date+"&to_date="+to_date,
		    success : function(subdeptdata) {
	       $("#"+"ajaxresponcediv").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	}
</script>
<script type="text/javascript">
function editRow()
{
     var row = $("#gridedittabless").jqGrid('getGridParam','selarrrow');
	jQuery("#gridedittabless").jqGrid('editGridRow', row ,{height:240, width:425,reloadAfterSubmit:false,closeAfterEdit:true,top:0,left:350,modal:true});
}
	function deleteRow(){
		 var row = $("#gridedittabless").jqGrid('getGridParam','selarrrow');
		$("#gridedittabless").jqGrid('delGridRow',row, {height:120,reloadAfterSubmit:true,top:0,left:50,modal:true});}
	
	function searchRow(){
		jQuery("#gridedittabless").jqGrid('searchGrid', {multipleSearch:false,reloadAfterSubmit:true,top:0,left:350,modal:true} );
	}
	function refreshRow(row, result) {
		  $("#gridedittabless").trigger("reloadGrid"); 
	}
</script>

<script type="text/javascript">
function resendsms()
{
     var row = $("#gridedittabless").jqGrid('getGridParam','selarrrow');
     var mobileno = jQuery("#gridedittabless").jqGrid('getCell', row, 'msisdn');
     var messageroot = jQuery("#gridedittabless").jqGrid('getCell', row, 'messageroot');
 	var message = jQuery("#gridedittabless").jqGrid('getCell', row, 'writeMessage');
	 
	$("#resendsms_dailog").load(encodeURI("view/Over2Cloud/CommunicationOver2Cloud/Comm/resendsms.action?mobileno="+mobileno+"&message="+message+"&root="+messageroot));
	$("#resendsms_dailog").dialog('open');
}
</script>



</head>
<body>
	<sj:dialog
				id="resendsms_dailog" 
				autoOpen="false" 
				closeOnEscape="true" 
				modal="true"
				title="Resend " 
				width="850"
				height="240" 
				showEffect="slide" 
				hideEffect="explode"
				>
				</sj:dialog>
<div class="clear"></div>
<div class="middle-content">
<div class="list-icon">
	<div class="head">Sent SMS </div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div><div class="head">View</div>

</div>
<div style=" float:left; padding:10px 1%; width:98%;">
<center>
<div class="clear"></div>

<br><div class="listviewBorder">
<div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
<div class="pwie">
<table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%">
<tbody><tr></tr><tr><td></td></tr><tr><td> 
<table class="floatL" border="0" cellpadding="0" cellspacing="0">
<tbody><tr><td class="pL10"> 
                  
					<sj:a id="delButton" title="Deactivate" buttonIcon="ui-icon-trash" cssStyle="height:25px; width:32px" cssClass="button" button="true"  onclick="deleteRow()"></sj:a>
					<sj:a id="searchButton" title="Search" buttonIcon="ui-icon-search" cssStyle="height:25px; width:32px" cssClass="button" button="true"  onclick="searchRow()"></sj:a>
                     From:<sj:datepicker name="from_date"  id="from_date"  showOn="focus"  displayFormat="dd-mm-yy"  value="today"   onchange ="getselecteddate();"     yearRange="2014:2020"   theme="simple"   readonly="true" cssClass="button" style="margin: 0px 6px 10px; width: 8%;"/>
					
					To:<sj:datepicker name="to_date"  id="to_date"  showOn="focus"  displayFormat="dd-mm-yy"   value="today"   onchange="getselecteddate();"   yearRange="2014:2020" readonly="true"  theme="simple"   cssClass="button"   style="margin: 0px 6px 10px; width: 8%;"/>
				    
                    
                    <s:select id="status" name="status"  list="#{'-1':'Select All Status','3':'Sent SMS','0':'Unsent SMS'}"   onchange="selectstatus(this.value);"  cssClass="button"  theme="simple"       cssStyle="margin-top: -26px;margin-left: 2px;height: 26px;"></s:select>
                 
                    <s:select id="smstype" name="smstype" list="#{'-1':'Select All SMS Type','Instant ':'Instant SMS','Schedule':'Schedule SMS'}"     onchange="selectsmstype(this.value);"     theme="simple"    cssClass="button"  cssStyle="margin-top: -26px;margin-left: 2px;height: 26px;"/>
                  
                    &nbsp;Mobile No:<s:textfield name="mobileNo" id="mobileNo"  cssClass="button"    onkeyup="getmobilenumberdata(this.value);"  cssStyle="margin-top: 1px;margin-left: 2px;height: 16px; width: 8%;"  theme="simple"/>
                   
</td></tr></tbody></table>
</td>
<td class="alignright printTitle">
<sj:a  button="true" cssClass="button" cssStyle="height:25px; width:32px"  title="Download" buttonIcon="ui-icon-arrowstop-1-s" onclick="excelDownload();" />
 <sj:a id="resend" title="Resend" buttonIcon="ui-icon-mail-closed"  cssClass="button" button="true" onclick="resendsms();">Resend</sj:a>
<sj:a id="addButton"  button="true"  buttonIcon="ui-icon-mail-open" cssClass="button" onclick="addinstantmsggg();">Send </sj:a>
</td> 
</tr></tbody></table></div></div>
<s:url id="viewInstantMessageGridurl" action="instantviewmsgdata" >
</s:url>
<center>
<div id="ajaxresponcediv">
<sjg:grid 
		  id="gridedittabless"
          href="%{viewInstantMessageGridurl}"
          gridModel="masterViewList"
          groupField="['msgType']"
    	groupColumnShow="[true]"
    	groupCollapse="true"
    	groupText="['<b>{0}: {1} </b>']"
          navigator="true"
          navigatorAdd="false"
          navigatorView="false"
          navigatorDelete="%{deleteFlag}"
          navigatorEdit="%{modifyFlag}"
          navigatorSearch="true"
          rowList="15,25,50"
          rownumbers="-1"
          viewrecords="true"       
          pager="true"
          pagerButtons="true"
          pagerInput="false"   
          multiselect="true"  
          loadingText="Requesting Data..."  
          rowNum="15"
          
          navigatorSearchOptions="{sopt:['eq','ne','cn','bw','ne','ew']}"
          editurl="%{viewModifyGrid}"
          navigatorEditOptions="{reloadAfterSubmit:true}"
          shrinkToFit="false"
          autowidth="true"
          >
          <s:iterator value="viewInstantMsgGrid" id="viewInstantMsgGrid" >  
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
		</s:iterator> 
			<sjg:gridColumn 
		name="msgType"
		index="msgType"
		title="Message Type"
		width="100"
		align="center"
		editable="false"
		formatter="false"
		search="false"
		hidden="false"
		/>
</sjg:grid>
</div>
</center>
</div></center>
</div>
</div>

</body>
</html>