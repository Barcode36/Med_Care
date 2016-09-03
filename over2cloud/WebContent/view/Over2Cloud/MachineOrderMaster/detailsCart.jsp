<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>

<%
String userRights = session.getAttribute("userRights") == null ? "" : session.getAttribute("userRights").toString();
%>
<html>
<head>
<SCRIPT type="text/javascript">
function createMainKeyword()
{

	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/Text2Mail/beforemainKeywords.action",
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

</SCRIPT>
<script type="text/javascript">

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

function actionOnCart()
{

	var id = jQuery("#gridBasicDetails").jqGrid('getGridParam', 'selarrrow');
	if(id.length<0)
	{
		alert("Please select atleast one row to Take Action.");
	}
	else
	{
		$.ajax({
			  type : "post",
			  url  : "view/Over2Cloud/CorporatePatientServices/Lodge_Feedback/bulkCartTakeAtcion.action?complaintidAll="+id,
			    success : function(data) {
			    //	alert(data);
			    	$("#takeActionCart").dialog({title: 'Cart Action Take ',width: 850,height: 400});
			    	$("#takeActionCart").dialog('open');
			    	
			    	$("#takeActionCart").html(data);
			   	 	
			       	},
			    error: function() {
			            alert("error");
			        }
			 });
		
	}
}



function reload(rowid, result) {
	  $("#gridBasicDetails").trigger("reloadGrid"); 
	}


function searchData()
{
	jQuery("#gridBasicDetails").jqGrid('searchGrid', {multipleSearch:false,reloadAfterSubmit:true,top:0,left:150,modal:true, cssClass:"textField"} );
	
}

function lodgeOnGridData(searchFields)
{
	
	//alert(searchFields);
	if(searchFields=='1'){
		$("#action").show('slow');
	}
	else{
		$("#action").hide('slow');
	}
	cartId  = $("#cartID").val();
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/CorporatePatientServices/Lodge_Feedback/beforDetailsViewCart.action?allocateTo="+searchFields+"&cartID="+cartId+"&fromDate="+$("#minDateValue").val()+"&toDate="+$("#maxDateValue").val(),
	    success : function(subdeptdata) {
       $("#result_part").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });
	
}
lodgeOnGridData('1');
</script>
</head>
<body>

<sj:dialog
          id="takeActionCart"
          showEffect="slide"
          hideEffect="explode"
          autoOpen="false"
          title="Cart Take Action"
          modal="true"
          closeTopics="closeEffectDialog"
          width="1000"
          height="400"
          >
</sj:dialog>



<s:hidden id="cartID" value="%{cartID}"></s:hidden>
 
<div class="clear"></div>




<div class="list-icon">
<div class="head">Cart <s:property value="%{cartID}"/> </div> <div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div><div class="head">Details</div>
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
					
					
					
					
					<sj:a id="searchButton" title="Search" buttonIcon="ui-icon-search" cssStyle="height:25px; width:32px" cssClass="button" button="true"  onclick="searchData()"></sj:a>
					 
					<sj:a id="refButton" title="Reload Grid" buttonIcon="ui-icon-refresh" cssStyle="height:25px; width:32px" cssClass="button" button="true"  onclick="reload()"></sj:a>
			
			<s:select  
							    		id					=		"flag"
							    		name				=		"flag"
							    		list				=		"#{'1':'Assign', '2':'Close', '3':'Parked', '4':'Ignore', '0':'Unassign'}"
							            cssClass            =      "button"
							            cssStyle            =      "margin-top: -29px;margin-left:3px"
							            theme               =       "simple"
							      	 	onchange			=		"lodgeOnGridData(this.value) "
						      	 >
						      	 </s:select>
					    </td></tr>
					    </tbody>
					  </table>
				  </td>
				  
				  
				   
				  <!-- Block for insert Right Hand Side Button -->
				  <td class="alignright printTitle">
				  <div id="action" style="display: none;">
				    <sj:a button="true" cssClass="button" cssStyle="height:25px;"  title="Add"   onclick="actionOnCart();">Action</sj:a> 
				</div>
				  </td>
			</tr>
		</tbody>
	</table>
</div>
</div>
<div id="result_part"></div>

</div>

</body>
</html>