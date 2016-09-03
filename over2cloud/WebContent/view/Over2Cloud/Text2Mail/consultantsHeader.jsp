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


<SCRIPT type="text/javascript">


var row=0;
$.subscribe('rowselect', function(event, data) {
	row = event.originalEvent.id;
});	
function createConsultants()
{

	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/Text2Mail/beforeConsultants.action?modifyFlag=1",
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
	
	// wild search for ConsultNmae
	function getSearchDataC(data)
{

	
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/Text2Mail/ConsultantView.action?consW="+data,
	    success : function(data) 
	    {
			$("#datapart").html(data);
	    },
	    error: function() 
	    {
            alert("error");
        }
	 });
 
	}
	
	// wild search for Mobile No.
	function getSearchDataM(data)
{

	
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/Text2Mail/ConsultantView.action?mobW="+data,
	    success : function(feeddraft) 
	    {
			$("#datapart").html(feeddraft);
	    },
	    error: function() 
	    {
            alert("error");
        }
	 });
 
	}
	
	


function formater(cellvalue, options, row)
{ 
return "<a href='#' onClick='javascript:openDialog("+row.id+")'>" + cellvalue + "</a>";

}
function openDialog(id) 
{		
	 $("#leadActionDialog").load("<%=request.getContextPath()%>/view/Over2Cloud/KRLibraryOver2Cloud/modifyPrvlg?id="+id);
	 $('#leadActionDialog').dialog('open');

}

function loadGrid()
{
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/Text2Mail/ConsultantView.action",
	    success : function(feeddraft) {
       $("#datapart").html(feeddraft);
	},
	   error: function() {
            alert("error");
        }
	 });
}

loadGrid();


function counter(data, data0)
{
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/Text2Mail/ConsultantCount.action?cname="+data+"&cmob="+data0,
	    success : function(feeddraft) {
       $("#counttotal").html(feeddraft);
	},
	   error: function() {
            alert("error");
        }
	 });
}


</SCRIPT>
<script type="text/javascript">
var row=0;
$.subscribe('rowselect', function(event, data) {
	row = event.originalEvent.id;
});	
function editRow()
{
	if(row==0)
	{
		alert("Please select atleast one row to edit.");
	}
	else
	{
		jQuery("#gridBasicDetails").jqGrid('editGridRow', row ,{height:230, width:425,reloadAfterSubmit:false,closeAfterEdit:true,top:0,left:350,modal:true , afterSubmit: function () {
			reload();
	    }});
		
	}
}

function deleteRow()
{
	  var status = jQuery("#gridBasicDetails").jqGrid('getCell',row,'flag');
	if(row==0)
	{
		alert("Please select atleast one row to Inactive.");
	}
	else if(status=='Inactive')
	{
		alert("The selected data is already Inactive.");
	}
	else
	{
		$("#gridBasicDetails").jqGrid('delGridRow',row, {height:120, width:425,reloadAfterSubmit:true,closeAfterEdit:false,top:150,left:350,modal:true,caption:'Inactive',msg: "Inactive selected record(s)?",bSubmit: "Inactive" , afterSubmit: function () {
			reload();
	    }});
		
	}	
}

function reload(rowid, result) {
	  $("#gridBasicDetails").trigger("reloadGrid"); 
	}


function searchData()
{
	jQuery("#gridBasicDetails").jqGrid('searchGrid', {multipleSearch:false,reloadAfterSubmit:true,top:0,left:350,modal:true, cssClass:"textField"} );
	
}


function details(cellvalue, options, rowObject) 
{
	//alert("hii ");
	return "<a href='#' title='View Data' onClick='detailsView("+rowObject.id+")'><font color='blue'>"+cellvalue+"</font></a>";
}

function detailsView(id) 
{
	
	var name1 = jQuery("#gridBasicDetails").jqGrid('getCell',id,'empName');
	//alert("name "+name1);
	
	$("#takeActionGrid").dialog({title: ' Details of '+name1,width: 700,height: 350});
	$("#takeActionGrid").html("<br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$("#takeActionGrid").dialog('open');
	$.ajax
	({
		type : "post",
		url  : "view/Over2Cloud/Text2Mail/consDetails.action?idcons="+id,
		success : function(data)
		{
			$("#takeActionGrid").html(data);
		},
		error : function()
		{
			alert("Error on data fetch");
		} 
	});
} 
</script>
</head>
<body>

<sj:dialog
          id="takeActionGrid"
          showEffect="slide"
          hideEffect="explode"
          autoOpen="false"
          title="history"
          modal="true"
          closeTopics="closeEffectDialog"
          width="20000"
          height="400"
          >
</sj:dialog>
 <sj:dialog 
    	id="leadActionDialog" 
    	autoOpen="false" 
    	modal="true" 
    	title="Shared Details"
    	
    >
    </sj:dialog>
<div class="clear"></div>




<div class="list-icon">
<div class="head">Consultants</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div><div class="head">View</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div><div class="head">Total: </div><div id="counttotal" class="head"> <s:property value="%{count}"/></div>
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
					<sj:a id="editButton" title="Edit"  buttonIcon="ui-icon-pencil" cssStyle="height:25px; width:32px" cssClass="button" button="true" onclick="editRow()"></sj:a>
					<sj:a id="delButton" title="Deactivate" buttonIcon="ui-icon-trash" cssStyle="height:25px; width:32px" cssClass="button" button="true"  onclick="deleteRow()"></sj:a>
					<sj:a id="searchButton" title="Search" buttonIcon="ui-icon-search" cssStyle="height:25px; width:32px" cssClass="button" button="true"  onclick="searchData()"></sj:a>
					<sj:a id="refButton" title="Reload Grid" buttonIcon="ui-icon-refresh" cssStyle="height:25px; width:32px" cssClass="button" button="true"  onclick="reload()"></sj:a>
			
					    </td></tr>
					                <s:textfield 
            id="searching" 
            name="searching" 
            onclick="gridActivityData();" 
            theme="simple" cssClass="button" 
            cssStyle="margin-left:0px;margin-top: 1px;height: 15px;width:90px;"
             Placeholder="Consultant Name" onchange="getSearchDataC(this.value);counter(this.value,'')"/>
             		                <s:textfield 
            id="searching" 
            name="searching" 
            onclick="gridActivityData();" 
            theme="simple" cssClass="button" 
            cssStyle="margin-left:11px;margin-top: 1px;height: 15px;width:70px;"
             Placeholder="Mobile No." onchange="getSearchDataM(this.value);counter('',this.value)"/>
        
             </tr>
					    </tbody>
					  </table>
				  </td>
				  
				  <!-- Block for insert Right Hand Side Button -->
				  <td class="alignright printTitle">
				    <sj:a button="true" cssClass="button" cssStyle="height:25px;"  title="Add"  buttonIcon="ui-icon-plus" onclick="createConsultants();">Add</sj:a> 
				  </td>
			</tr>
		</tbody>
	</table>
</div>
</div>
<s:url id="viewCons" action="viewConsInGrid" escapeAmp="false" >
<s:param name="type" value="%{type}"></s:param>
</s:url>

<div id="datapart"></div>
</div>
</body>
</html>
