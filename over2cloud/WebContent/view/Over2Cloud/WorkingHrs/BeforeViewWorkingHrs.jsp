<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<% String userRights = session.getAttribute("userRights") == null ? "" : session.getAttribute("userRights").toString();%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script type="text/javascript" src="<s:url value="/js/helpdesk/ticketSeries.js"/>"></script>
<script type="text/javascript">
function onloadData()
{
	var dataFor = $("#dataFor").val();
	var dept = $("#dept").val();
	$("#dataDiv").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/WorkingHrs/beforeView.action?dataFor="+dataFor+"&dept="+dept,
	    success : function(confirmation) {
       $("#"+"dataDiv").html(confirmation);
	},
	   error: function() {
            alert("error");
        }
	 });
}
function addNewWorking()
{
	$("#dataDiv").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/WorkingHrs/beforeAdd.action",
	    success : function(confirmation) {
       $("#"+"data_part").html(confirmation);
	},
	   error: function() {
            alert("error");
        }
	 });
}

function getApplyedWorkingHrsDept(dataFor, targetid)
{
	$.ajax(
			{
				type :"post",
				url :"/over2cloud/view/Over2Cloud/WorkingHrs/getApplyedWorkingHrsDept.action?dataFor="+dataFor,
				success : function(data)
				{
					$('#'+targetid+' option').remove();
					$('#'+targetid).append($("<option></option>").attr("value","All").text("All Department"));
			    	$(data).each(function(index)
					{
		  		 		$('#'+targetid).append($("<option></option>").attr("value",this.id).text(this.name));
					});
			    	
	   				},
		    		error : function () 
		    		{
						alert("Somthing is wrong to get Data");
					}
			});
}


onloadData();
</script>
</head>
<body>
<div class="clear"></div>
<div class="middle-content">
<!-- Header Strip Div Start -->
<div class="list-icon">
	 <div class="head">Working Hours</div><img alt="" src="images/forward.png" style="margin-top:10px; float: left;"><div class="head">View</div> 
</div>
<div class="clear"></div>
<div class="listviewBorder"  style="margin-top: 10px;width: 97%;margin-left: 20px;" align="center">
<!-- Code For Header Strip -->
<div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
<div class="pwie">
	<table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%">
		<tbody>
			<tr>
				  <!-- Block for insert Left Hand Side Button -->
				  <td>
					  <table class="floatL" border="0" cellpadding="0" cellspacing="0">
					    <tbody>
					    <tr>
					    <td class="pL10">
								<sj:a id="editButton" title="Edit"  buttonIcon="ui-icon-pencil" cssClass="button" cssStyle="height:25px; width:32px" button="true" onclick="editRow()"></sj:a>
								<sj:a id="delButton" title="Deactivate" buttonIcon="ui-icon-trash" cssClass="button" cssStyle="height:25px; width:32px" button="true"  onclick="deleteRow()"></sj:a>
					    	<s:select  
						    		id					=		"dataFor"
						    		name				=		"dataFor"
						    		list				=		"appDetails"
						    		headerKey			=		"all"
						    		headerValue			=		"All Module"
						      		theme				=		"simple"
						      		cssClass			=		"button"
						      		cssStyle			=		"height: 28px;margin-top: 3px;margin-left: 3px;width: 129px;"	
						      		onchange			=		"onloadData();getApplyedWorkingHrsDept(this.value,'dept');"
						      		>
					      	 </s:select> 
					      	 <s:select  
						    		id					=		"dept"
						    		name				=		"dept"
						    		list				=		"deptList"
						      		theme				=		"simple"
						      		cssClass			=		"button"
						      		cssStyle			=		"height: 28px;margin-top: 3px;margin-left: 3px;width: 164px;"	
						      		onchange			=		"onloadData();"
						      		>
					      	 </s:select>
					    </td>
					    </tr>
				        </tbody>
					  </table>
				  </td>
				  
				  <!-- Block for insert Right Hand Side Button -->
				  <td class="alignright printTitle">
				  <%if(true){ %>
				      <sj:a id="addButton"  button="true"  cssClass="button" cssStyle="height:25px;" title="Add" buttonIcon="ui-icon-plus" onclick="addNewWorking();">Add</sj:a>
				  <%}%>
				  </td>
			</tr>
		</tbody>
	</table>
</div>
</div>
<!-- Code End for Header Strip -->

<div style="overflow: scroll; height: 430px;">
	<div id="dataDiv"></div>
</div>
</div>
</div>
</body>
</html>