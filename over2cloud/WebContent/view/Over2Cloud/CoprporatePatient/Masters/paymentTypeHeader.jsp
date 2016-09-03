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

<script type="text/javascript">
paymentTypeData();

	var row=0;
	$.subscribe('rowselect', function(event, data) {
		row = event.originalEvent.id;
	});	

	function wildSearch()
	{
		 delay(function()
		{
			 paymentTypeData();
		    }, 3000 );
	}
	var delay = (function(){
		  var timer = 0;
		  return function(callback, ms){
		    clearTimeout (timer);
		    timer = setTimeout(callback, ms);
		  };
		})();

	function paymentTypeData()
	{

		var searchParameter=$("#wildSearch").val();
		//alert("value of wild search..."+searchParameter);
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/CorporatePatientServices/cpservices/paymentTypeView.action?wildSearch="+searchParameter+"&status="+$("#status").val(),
		    success : function(subdeptdata) {
	       $("#"+"result_part").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	}


	function addNewDept()
	{
		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		var conP = "<%=request.getContextPath()%>";
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/CorporatePatientServices/cpservices/beforeAddPaymentType.action",
		    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	}

	function editRow()
	{
		if(row==0)
		{
			alert("Please select atleast one row to edit.");
		}
		else
		{
			jQuery("#gridBasicDetails").jqGrid('editGridRow', row ,{reloadAfterSubmit:false,closeAfterEdit:true,top:0,left:350,modal:true});
			reloadPage();
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
			$("#gridBasicDetails").jqGrid('delGridRow',row, {height:120,reloadAfterSubmit:true,top:0,left:350,modal:true});
			reloadPage();
		}	
	}
	function searchRow()
	{
		 $("#gridBasicDetails").jqGrid('searchGrid',row, {height:120,reloadAfterSubmit:true,top:0,left:350,modal:true});
	}
	 
	function reloadPage()
	{
	var grid = $("#gridBasicDetails");
	grid.trigger("reloadGrid",[{current:true}]);
	}

	
</script>
<script type="text/javascript">


</script>
</head>

<body>
<div class="clear"></div>
<div class="middle-content">

<div class="list-icon">
	 <div class="head">Payment Type View</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div><div class="head"><s:iterator value="totalCount">
	
</s:iterator>
</div> 
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
					<sj:a id="delButton" title="Inactive" buttonIcon="ui-icon-trash" cssStyle="height:25px; width:32px" cssClass="button" button="true"  onclick="deleteRow()"></sj:a>
					<sj:a id="refButton" title="Reload Grid" buttonIcon="ui-icon-refresh" cssStyle="height:25px; width:32px" cssClass="button" button="true"  onclick="reloadPage()"></sj:a>
					    <s:select  
				id="status"
				name="status"
				list="#{'-1':'All','Active':'Active','Inactive':'Inactive'}"
				cssClass="button"
				cssStyle="margin-top: -29px;margin-left:3px"
				theme="simple"
				onchange="paymentTypeData();"
				/>			
					    </td></tr>
					    </tbody>
					  </table>
				  </td>
				  
				  <!-- Block for insert Right Hand Side Button -->
				  <td class="alignright printTitle">
				   <sj:a button="true" cssClass="button" cssStyle="height:25px;"  title="Add"  buttonIcon="ui-icon-plus" onclick="addNewDept();">Add</sj:a>
				  </td>
			</tr>
		</tbody>
	</table>
</div>
</div>
<!-- Code End for Header Strip -->

 <div id="result_part"></div>
 </div>
 </div>
</body>

<script type="text/javascript">
</script>
</html>