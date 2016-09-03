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
<script type="text/javascript" src="<s:url value="/js/compliance/ComplianceMenuCtrl.js"/>"></script>
<script type="text/javascript">
var row=0;
$.subscribe('rowselect', function(event, data) {
	row = event.originalEvent.id;
});	


$.subscribe('editRow', function(event,data)
{
	if(row==0)
	{
		alert("Please select atleast one row.");
	}
	else
	{
		var id   = jQuery("#gridedittableContact").jqGrid('getGridParam', 'selrow');
		var levelName = jQuery("#gridedittableContact").jqGrid('getCell',id,'level');
		var test=levelName.split("-");
		var currentLevel=parseInt(test[1]);
		var targetid='level';
		$('#'+targetid+' option').remove();
		for (var i=currentLevel; i<=6; i++)
		{
			$('#'+targetid).append($("<option></option>").attr("value",i).text("Level "+i));
		}
		
		jQuery("#gridedittableContact").jqGrid('editGridRow', row ,{height:200, width:300,reloadAfterSubmit:false,closeAfterEdit:true,top:0,left:100,modal:true});
		row=0;
		var targetid='level';
	}
	
});

function deleteRow()
{
	if(row==0)
	{
		alert("Please select atleast one row.");
	}
	else
	{
		$("#gridedittableContact").jqGrid('delGridRow',row, {height:120,reloadAfterSubmit:true});
	}
}
function searchRow()
{
	 $("#gridedittableContact").jqGrid('searchGrid', {sopt:['eq','cn']} );
}
function reloadRow()
{
	var grid = $("#gridedittableContact");
	grid.trigger("reloadGrid",[{current:true}]);
}


function getSearchData()
{
	var moduleName=$("#moduleName").val();
	var subType=$("#subType").val();
	var subDepartment=$("#subDepartment").val();
	var location=$("#location").val();
	if(moduleName=='CRF' || moduleName=='CPS' ||  moduleName=='Pharmacy' ||  moduleName=='LPS' ||  moduleName=='LabOrd')
	{
		 $("#location").show();
	}
	else
	{
		$("#location").hide();
	}
		
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/Compliance/compl_contacts_pages/beforeComplContactSearchView.action",
		data: "modifyFlag=0&deleteFlag=1&moduleName="+moduleName+"&subType="+subType+"&forSubDept_id="+subDepartment+"&location="+location,
	    success : function(data) 
	    {
			$("#"+"contactDiv").html(data);
	    },
	    error: function() 
	    {
            alert("error");
        }
	 });
	
}
function getLocationList()
{
	var moduleName=$("#moduleName").val();
	var subType=$("#subType").val();
	var subDepartment=$("#subDepartment").val();
	      $.ajax({
           type : "post",
           url : "view/Over2Cloud/Compliance/compl_contacts_pages/getLocationList.action?moduleName="+moduleName+"&subType="+subType+"&forSubDept_id="+subDepartment,
           success : function(data) 
           {
                 $('#location option').remove();
               $('#location').append($("<option></option>").attr('value',-1).text('Select Location'));
               $(data).each(function(index)
               {
                  $('#location').append($("<option></option>").attr("value",this.id).text(this.name));
               });
           },
           error: function() 
           {
               alert("error");
           }
        });
}

function getSubDepartmentList()
{
	var moduleName=$("#moduleName").val();
	var subType=$("#subType").val();
	      $.ajax({
           type : "post",
           url : "view/Over2Cloud/Compliance/compl_contacts_pages/getSubDepartmentList.action?moduleName="+moduleName+"&subType="+subType,
           success : function(data) 
           {
                 $('#subDepartment option').remove();
               $('#subDepartment').append($("<option></option>").attr('value',-1).text('Select Sub-Department'));
               $(data).each(function(index)
               {
                  $('#subDepartment').append($("<option></option>").attr("value",this.id).text(this.name));
               });
           },
           error: function() 
           {
               alert("error");
           }
        });
}
function getDepartmentList()
{
	var moduleName=$("#moduleName").val();
 	      $.ajax({
           type : "post",
           url : "view/Over2Cloud/Compliance/compl_contacts_pages/getDepartmentList.action?moduleName="+moduleName,
           success : function(data) 
           {
                 $('#subType option').remove();
               $('#subType').append($("<option></option>").attr('value',-1).text('Select Department'));
               $(data).each(function(index)
               {
                  $('#subType').append($("<option></option>").attr("value",this.id).text(this.name));
               });
           },
           error: function() 
           {
               alert("error");
           }
        });
}
getSearchData();
</script>
</head>
<body>
<%-- <s:hidden id="dataFor" value="%{moduleName}"/> --%>
<sj:dialog
          id="takeActionGrid"
          showEffect="slide"
          hideEffect="explode"
          autoOpen="false"
          title="Edit Compliance Contact"
          modal="true"
          closeTopics="closeEffectDialog"
          width="1000"
          height="350"
          draggable="true"
    	  resizable="true"
          >
          <div id="dataPart"></div>
</sj:dialog>
<div class="clear"></div>
<div class="middle-content">
<div class="list-icon">
	 <div class="head">
	 Contact</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div>
	 <div class="head">View For <s:property value="%{mainHeaderName}"/> Department</div> 
</div>
<div class="clear"></div>
<div class="listviewBorder"  style="margin-top: 10px;width: 97%;margin-left: 20px;" align="center">
<div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
<div class="pwie">
	<table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%">
		<tbody>
			<tr>	
				  <td>
					  <table class="floatL" border="0" cellpadding="0" cellspacing="0">
					    <tbody><tr><td class="pL10">   
						<%if(userRights.contains("coma_MODIFY")) {%>
						<sj:a id="editButton111" title="Edit" buttonIcon="ui-icon-pencil" cssStyle="height:25px; width:32px"  cssClass="button" button="true" onClickTopics="editRow"></sj:a>
					<%} %>
					<%if(userRights.contains("coma_DELETE")) {%>
						<sj:a id="delButton" title="Deactivate" buttonIcon="ui-icon-trash" cssStyle="height:25px; width:32px"  cssClass="button" button="true"  onclick="deleteRow()"></sj:a>
					<%} %>
						<sj:a id="searchButton" title="Search" buttonIcon="ui-icon-search" cssStyle="height:25px; width:32px"  cssClass="button" button="true"  onclick="searchRow()"></sj:a>	
						<sj:a id="refButton" title="Refresh" buttonIcon="ui-icon-refresh" cssStyle="height:25px; width:32px"  cssClass="button" button="true"  onclick="reloadRow()"></sj:a>
				    
				    <s:select 
				       id="moduleName"
				       name="moduleName"
				       list="#{'HDM':'Helpdesk','ORD':'Machine Order', 'CRF':'Cross Referral','CRC':'Critical Result','CPS':'Corporate Patient','Pharmacy':'Pharmacy','LPS':'LongTimePatientStay','LabOrd':'Lab Order'}"
				       cssClass="button"
				       cssClass="button"
			           cssStyle="margin-top: -29px;margin-left:3px"		
					   theme="simple"
					   onchange="getSearchData();getDepartmentList();getSubDepartmentList();getLocationList()"
					   
					 />
						
				
					 <s:select 
                      id="subType" 
			          name="subType" 
			          list="subTypeMap"
			          headerKey="-1"
			          headerValue="Select Department"
			          cssClass="button"
			          cssStyle="margin-top: -29px;margin-left:3px;width:157px;"
			          theme="simple"
			          onchange="getSearchData();getSubDepartmentList();"
         			 />
         			  <s:select 
                      id="subDepartment" 
			          name="subDepartment" 
			          list="subDepartmentMap"
			          headerKey="-1"
			          headerValue="Select Sub-Department"
			          cssClass="button"
			          cssStyle="margin-top: -29px;margin-left:3px;width:170px;"
			          theme="simple"
			          onchange="getSearchData();getLocationList();"
         			 />
         			 
         			 <s:select 
                      id="location" 
			          name="location" 
			          list="locationMap"
			          headerKey="-1"
			          headerValue="Select Location"
			          cssClass="button"
			          cssStyle="margin-top: -29px;margin-left:3px;width:157px;"
			          theme="simple"
			          onchange="getSearchData();"
         			 />
         			 
         			 <!--<sj:a  button="true" cssClass="button" cssStyle="height: 27px;margin-left: 4px;" onclick="getSearchData();">OK</sj:a>
					      --></td></tr></tbody>
					  </table>
				  </td>
				 <td class="alignright printTitle">
			 	
			 		 <%if(userRights.contains("mapcon_VIEW"))
		     			{%>
			 		<sj:a id="addButton12"  button="true"  buttonIcon="ui-icon-plus" cssClass="button" onclick="getContactMapping();">Map</sj:a>
			 			 <%} %>
	   	 			 <%if(userRights.contains("sharecon_VIEW"))
		    		 {%>
			 		<sj:a id="addButton13"  button="true"  buttonIcon="ui-icon-plus" cssClass="button" onclick="getContactSharing();">Share</sj:a>
			 		   <%} %>
			 		
			 			<%if(userRights.contains("coma_ADD")) {%> 
			 		<sj:a id="addButton14"  button="true"  buttonIcon="ui-icon-plus" cssClass="button" onclick="addNewContact();">Add</sj:a><%} %>
	       		</td>
			</tr>
		</tbody>
	</table>
</div>
</div>
<div style="overflow: scroll; height: 430px;">
<div id="contactDiv">
</div>
</div>
</div>
</div>
</body>
</html>