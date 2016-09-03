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
<script type="text/javascript" src="<s:url value="/js/communication/Communicationmsgdraft.js"/>"></script>
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
		jQuery("#gridedittable").jqGrid('editGridRow', row ,{height:450, width:425,reloadAfterSubmit:false,closeAfterEdit:true,top:0,left:350,modal:true , afterSubmit: function () {
			refreshRow();
	    }});
		
	}}

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
				refreshRow();
		    }});
			
		}
}
function searchRow()
{
	 $("#gridedittable").jqGrid('searchGrid', {sopt:['eq','cn']} );
}
function refreshRow()
{
	 $.ajax({
	     type : "post",
	     url : "/over2cloud/view/Over2Cloud/SMSEmailDraft/viewEmaildrafts.action?searchField=status&searchString=Active&searchOper=eq",
	     success : function(data) {
	     	$("#result").html(data);
	    },
	   error: function() {
	          alert("error");
	      }
	});
}

function loadGrid(field,value)
{
	  $.ajax({
		     type : "post",
		     url : "/over2cloud/view/Over2Cloud/SMSEmailDraft/viewEmaildrafts.action?searchField="+field+"&searchString="+value+"&searchOper=eq",
		     success : function(data) {
		     	$("#result").html(data);
		    },
		   error: function() {
		          alert("error");
		      }
		});

	}
	loadGrid('status','Active');
function addEmailDraft()
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/SMSEmailDraft/addEmaildraft.action",
	    success : function(data) {
	       	$("#data_part").html(data);
	       	
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
	 <div class="head">Email Draft </div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div><div class="head"><s:iterator value="totalMap">
	<s:property value="%{key}"/>: <s:property value="%{value}"/>   
</s:iterator>
</div> 
</div>
<div class="clear"></div>
<div class="listviewBorder" style="margin-top: 10px;width: 97%;margin-left: 20px;" align="center">
<div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
<div class="pwie">
<table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%">
<tbody><tr></tr><tr><td></td></tr><tr><td> 
<table class="floatL" border="0" cellpadding="0" cellspacing="0">
<tbody><tr><td class="pL10"> 
                   <%if(userRights.contains("vemail_MODIFY")) {%>
					<sj:a id="editButton" title="Edit"  buttonIcon="ui-icon-pencil" cssStyle="height:25px; width:32px" cssClass="button" button="true" onclick="editRow()"></sj:a>
					<%} %>
					<%if(userRights.contains("vemail_MODIFY")) {%>
					<sj:a id="delButton" title="Inactive" buttonIcon="ui-icon-trash" cssStyle="height:25px; width:32px" cssClass="button" button="true"  onclick="deleteRow()"></sj:a>
					<%} %>
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
							      	 	onchange			=		"loadGrid('module',this.value) "
							      	 
						      	 
						      	 >
						      	 </s:select>
						      	  <s:select  
						    		id					=		"status"
						    		name				=		"status"
						    		list				=		"#{'Active':'Active','Inactive':'Inactive','-1':'All'}"
							       cssClass             =      "button"
							       cssStyle             =      "margin-top: -29px;margin-left:3px"
							      theme                 =       "simple"
						      	 onchange			=		"loadGrid('status',this.value) "
						      	 >
						      	 </s:select>
</td></tr></tbody></table>
</td>
<td class="alignright printTitle">

<sj:a id="addButton"  button="true"  buttonIcon="ui-icon-plus" cssClass="button" onclick="addEmailDraft();">Add</sj:a>

</td> 
</tr></tbody></table></div></div>
<div style="overflow: scroll; height:430px;">
<div id="result"></div>
</div>
</div>
</div>

</body>
</html>
