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
<SCRIPT type="text/javascript" src="js/common/commonContact.js"></SCRIPT>
<script type="text/javascript" src="js/hr/commonHr.js"></script>
<SCRIPT type="text/javascript">


$.subscribe('completeData', function(event,data)
        {
          setTimeout(function(){ $("#foldeffect").fadeIn(); }, 10);
          setTimeout(function(){ $("#foldeffect").fadeOut(); }, 4000);
         
        });
$.subscribe('editgrid', function() 
{
	var id = jQuery("#gridedittable").jqGrid('getGridParam', 'selrow');
	var userId = jQuery("#gridedittable").jqGrid('getCell', id, 'ua.userID');
	var tempPass = jQuery("#gridedittable").jqGrid('getCell', id, 'ua.password');
	var name=jQuery("#gridedittable").jqGrid('getCell', id, 'ua.name');
	var user=jQuery("#gridedittable").jqGrid('getCell', id, 'ua.userType');
	
    if (userId!=null && id!=null)
	{
		var mobileNo = jQuery("#gridedittable").jqGrid('getCell', id, 'ua.mobileNo');
		$("#userID").val(userId);
		$("#mobileNo").val(mobileNo);
		$("#id").val(id);
		$("#tempPass").val(tempPass);
		$("#passwordNew").val(tempPass);
		$("#password").val(tempPass);
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/hr/usermodifyBefore.action?userID="+userId+"&mobileNo="+mobileNo+"&id="+id+"&tempPass="+tempPass+"&userType="+user,
		    success : function(subdeptdata)
		    {
		    	 $("#downloadpdfid").dialog({title: 'Edit User Rights of '+name});
		    	$("#downloadpdfid").dialog('open');
		    	
	       $("#"+"downloadpdfid").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
		

		document.getElementById("hide").style.display="none";
		//$('#takeActionDialog').dialog('open');
		//$("#downloadpdfid").dialog('open');
	}
	else
	{
		$("#dialogDiv").show();
		$("#checkSelect").dialog('open');
	}
});
function formatLink(cellvalue, options, row) 
{
	return "<a href='#' onClick='javascript:openDialog(" + row.id
	+ ")'>" + cellvalue + "</a>";
}

function openDialog(userId) 
{
	$("#userShow_Rights").load("<%=request.getContextPath()%>/view/Over2Cloud/hr/userShowRights.action?id=" + userId);
	$("#userShow_Rights").dialog('open');
}

$.subscribe('checkPass', function(event,data)
{
	var passwordNew = $("#passwordNew").val();
	var password = $("#password").val();
	if(passwordNew == "" || password == "")
	{
		$("#passwordNew").val($("#tempPass").val());
		$("#password").val($("#tempPass").val());
	}
	else
	{
		if(passwordNew != password)
		{
			alert("Passwords do not match !");
			event.originalEvent.options.submit = false;
		}
	}
	//Put decrepted password in field '' so that it can be 
	//incripted in Action class later
});
function searchData()
{
	jQuery("#gridedittable").jqGrid('searchGrid', {multipleSearch:false,reloadAfterSubmit:true,top:0,left:350,modal:true} );
}
</SCRIPT>
<SCRIPT type="text/javascript">
	function user()
	{
		
	var conP = "<%=request.getContextPath()%>";
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		   url : conP+"/view/Over2Cloud/hr/beforeUser.action?userFlag=1",
		    success : function(data) {
	       $("#"+"data_part").html(data);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	}
</SCRIPT>
<script type="text/javascript">
function getDeptBySubGroup(dropDownId,value)
{
	
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/Compliance/compl_contacts_pages/getDeptBySubGroup.action",
	    data : "selectedId="+value,
	    success : function(data) 
	    {
			$('#'+dropDownId+' option').remove();
			$('#'+dropDownId).append($("<option></option>").attr('value',-1).text('Select Department'));
			$(data).each(function(index)
			{
			   $('#'+dropDownId).append($("<option></option>").attr("value",this.id).text(this.name));
			});
	    },
	    error: function() 
	    {
            alert("error");
        }
	 });
}

function getUserType(dropDownId,value)
{
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/hr/fetchUserType.action",
	    data : "selectedId="+value,
	    success : function(data) 
	    {
			$('#'+dropDownId+' option').remove();
			$('#'+dropDownId).append($("<option></option>").attr('value',-1).text('Select User'));
			$(data.UserTypeMap).each(function(index)
			{
			   $('#'+dropDownId).append($("<option></option>").attr("value",this.id).text(this.name));
			});
	    },
	    error: function() 
	    {
            alert("error");
        }
	 });
}

getOnChangePrimaryData('ua.active','1');
function getOnChangePrimaryData(searchFields,SearchValue)
{
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/hr/beforeUserView.action?empModuleFalgForDeptSubDept=1",
	    data:"searchField="+searchFields+"&searchString="+SearchValue+"&searchOper=eq",
	   success : function(data) {
       $("#result_part").html(data);
	},
	   error: function() {
            alert("error");
        }
	 });
}


</script>
</head>

<body>
<div  id="dialogDiv" style="display: none;">
<sj:dialog id="checkSelect"  title="Warning" autoOpen="false" modal="true" width="250" height="100" showEffect="drop"><center>Please select a row to edit.</center></sj:dialog>
</div>
 <sj:dialog 
      	id="downloadpdfid" 
    	showEffect="slide" 
    	hideEffect="explode" 
    	autoOpen="false" 
    	modal="true" 
    	title="Edit User Rights"
    	openTopics="openEffectDialog"
    	closeTopics="closeEffectDialog"
    	modal="true" 
    	width="1100"
		height="500"
    />
<sj:dialog id="facilityisnotavilable" buttons="{ 'OK ':function() { },'Cancel':function() { },}"  showEffect="slide" hideEffect="explode" autoOpen="false" modal="true" title="This facility is not available in Existing Module"openTopics="openEffectDialog"closeTopics="closeEffectDialog"modal="true" width="390" height="100"/>
<div class="list-icon">
	 <div class="head">View User </div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div><div class="head"><s:iterator value="toatlcount">
	<s:property value="%{key}"/>: <s:property value="%{value}"/>   
</s:iterator>
</div> 
</div> 

 <div class="clear"></div>
 <div style="align:center; padding-top: 10px;padding-left: 10px;padding-right: 10px;">
<div class="listviewBorder">
<div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
<div class="pwie"><table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%">
<tbody><tr></tr><tr><td></td></tr><tr><td> 
<table class="floatL" border="0" cellpadding="0" cellspacing="0"><tbody><tr><td class="pL10"> 

                    <sj:a id="gridUser_editbutton" buttonIcon="ui-icon-pencil" cssStyle="height:25px; width:32px" cssClass="button" onClickTopics="editgrid" button="true"></sj:a>	
					<sj:a id="delButton" title="Inactive" buttonIcon="ui-icon-trash" cssStyle="height:25px; width:32px" cssClass="button" button="true"  onclick="deleteRow()"></sj:a>
				<%-- 	<sj:a id="searchButton" title="Search" buttonIcon="ui-icon-search" cssStyle="height:25px; width:32px" cssClass="button" button="true"  onclick="searchData()"></sj:a> --%>
					<sj:a id="refButton" title="Reload Grid" buttonIcon="ui-icon-refresh" cssStyle="height:25px; width:32px" cssClass="button" button="true"  onclick="reload()"></sj:a>
			     <s:if test="%{mgtStatus}">
				  			 <s:select 
		                              id="office"
		                              list="deptMap"
		                              headerKey="-1"
		                              headerValue="Select Country Office" 
		                              cssClass="button"
		                              theme="simple"
		                              onchange="getheadoffice(this.value,'headOfficeId');"
		                              >
		                  </s:select>
						  <s:select 
		                              id="headOfficeId"
		                              list="{'No Data'}"
		                              headerKey="-1"
		                              headerValue="Select Head Office" 
		                              cssClass="button"
		                              theme="simple"
		                              onchange="getbranchoffice(this.value,'regLevel')"
		                              >
		                   </s:select>
		                   </s:if>
		                     <s:if test="%{hodStatus}">
		                  		<s:select 
		                              id="regLevel"
		                              name="regLevel"
		                              list="deptMap"
		                              headerKey="-1"
		                              headerValue="Select Branch Office" 
		                              cssClass="button"
		                              theme="simple"
		                              onchange="getGroupNamesForMappedOffice('groupType',this.value);"
		                              >
		                      </s:select>
		                      </s:if>
			
						      	
						      	  <s:select  
                                    id                    =        "groupType"
                                    name                =        "groupType"
                                    list                =        "groupMap"
                                      headerKey           =       "-1"
                                    headerValue         =       "Select Contact Type"
                                   cssClass             =      "button"
                                   cssStyle             =      "margin-top: -29px;margin-left:3px"
                                   theme                =       "simple"
                                     onchange                =        "getDeptBySubGroup('contactSubType',this.value);getOnChangePrimaryData('emp.groupId',this.value)"
                                   >
                                   </s:select>
                                   <s:select  
                                        id                    =        "contactSubType"
                                        name                =        "contactSubType"
                                        list                =        "{'No Data'}"
                                          headerKey           =       "-1"
                                        headerValue         =       "Select Contact Sub-Type"
                                        cssClass            =      "button"
                                        cssStyle            =      "margin-top: -29px;margin-left:3px"
                                        theme               =       "simple"
                                           onchange            =        "getOnChangePrimaryData('emp.deptname',this.value); "
                                       
                                   
                                   >
                                   </s:select>
                                   
                                   <s:select  
                                    id                    =        "userType"
                                    name                =        "usertType"
                                     list               =     "#{'N':'Normal','H':'HOD','M':'Management'}"
                                      headerKey           =       "-1"
                                    headerValue         =       "Select User Type"
                                   cssClass             =      "button"
                                   cssStyle             =      "margin-top: -29px;margin-left:3px"
                                  theme                 =       "simple"
                                     onchange                =        "getOnChangePrimaryData('ua.userType',this.value);"
                                   >
                                   </s:select>
                                          <s:select  
                                    id                    =        "status"
                                    name                =        "status"
                                    list                =        "#{'1':'Active','0':'Inactive','-1':'All'}"
                                   cssClass             =      "button"
                                   cssStyle             =      "margin-top: 3px;margin-left:3px"
                                  theme                 =       "simple"
                                     onchange                =        "getOnChangePrimaryData('ua.active',this.value) "
                                   >
                                   </s:select>
						      	  <s:textfield name="searchMob" id="searchMob" onchange="getOnChangePrimaryData('ua.mobileNo',this.value);" cssStyle="margin-top: -65px;margin-right: 20px;height:19px;"  placeholder="Enter Data"  cssClass="button"/>


</td></tr></tbody></table>
 	<td class="alignright printTitle">
   <%if(userRights.contains("sude_MODIFY")) {%>
    <sj:a
							button="true"
							cssClass="button" 
							cssStyle="height:25px;margin-top:-30px;"
			                title="Add"
				            buttonIcon="ui-icon-plus"
							onclick="user();"
							>Add</sj:a>  
							<%} %> 
</td></tr></tbody></table></div></div>
 <div class="clear"></div>
 
 
<div id="userResult">
<sj:dialog id="userShow_Rights" title="Details" autoOpen="false" modal="true" width="400" showEffect="slide" hideEffect="explode" height="300"></sj:dialog>
<div style="overflow: scroll; height: 430px;">
<div id="result_part"></div>
</div>
</div>
	</div>
</div>
</body>
<script type="text/javascript">
var row=0;
$.subscribe('rowselect', function(event, data) {
	row = event.originalEvent.id;
});	

function deleteRow()
{
	var status = jQuery("#gridedittable").jqGrid('getCell',row,'active');
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
			reloadPage('ua.active','1');
	    }});
		
	}
}
function reload(searchFields,SearchValue)
{
	 $.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/hr/beforeUserView.action?empModuleFalgForDeptSubDept=1",
		    data:"searchField="+searchFields+"&searchString="+SearchValue+"&searchOper=eq",
		    success : function(subdeptdata) {
	       $("#"+"result_part").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	}
</script>


</html>