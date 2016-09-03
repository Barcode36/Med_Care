<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<link type="text/css" href="<s:url value="/css/style.css"/>" rel="stylesheet" />
<script type="text/javascript" src="<s:url value="/js/compliance/compliance.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/helpdesk/common.js"/>"></script>
<link href="js/multiselect/jquery-ui.css" rel="stylesheet" type="text/css" />
<link href="js/multiselect/jquery.multiselect.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<s:url value="/js/multiselect/jquery-ui.min.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/multiselect/jquery.multiselect.js"/>"></script>
<meta   http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<SCRIPT type="text/javascript">
$(document).ready(function()
		 {
		 	$("#proxyEmpId").multiselect({
		 		   show: ["", 200],
		 		   hide: ["explode", 1000]
		 		});
		 });

function back()
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/FloorSettingOver2Cloud/BeforeViewFloorSetting.action",
	    success : function(data) {
       		$("#data_part").html(data);
		},
	    error: function() {
            alert("error");
        }
	 });
}

function getDeptBySubGroup(dropDownId,value)
{
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/Compliance/compl_contacts_pages/getDeptBySubGroup.action",
	    data : "selectedId="+value,
	    success : function(data) 
	    {
			$('#'+dropDownId+' option').remove();
			$('#'+dropDownId).append($("<option></option>").attr('value',-1).text('Department'));
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

function addEmployee()
{
	var sel_id;
	var empId=$("#empId").val() || [];
    var fromDept=$("#fromDept_id").val();
	sel_id=$("#gridedittable").jqGrid('getGridParam','selarrrow');
	if(sel_id=="")
	{
	     alert("Please select atleast one check box...");        
	     return false;
	}
	else
	{
		var mappWith=$("#mappWith").val();
		if(mappWith =="-1")
		{
			alert("Please Select Mapping With Dropdown...")
		}
		else if(mappWith =="deptWise")
		{
			$.ajax({
			    type : "post",
			    url :"/over2cloud/view/Over2Cloud/FloorSettingOver2Cloud/MapDeptCC.action?deptId="+sel_id+"&empId="+empId,
			    success : function(data) 
			    {
					$("#resultDiv").html(data);
					$("#viewempDiv").html("<br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
					$.ajax({
					type :"post",
					url :"/over2cloud/view/Over2Cloud/FloorSettingOver2Cloud/BeforeGetUnMappedData.action?mappWith="+mappWith+"&deptId="+fromDept+"&empId="+empId,
					success : function(empData)
					{
						$("#viewempDiv").html(empData);
					    },
					    error : function () 
					    {
						alert("Somthing is wrong to get Employee Data");
					}
					});
				},
			    error: function()
			    {
			        alert("error");
			    }
			 });
		}
		else if(mappWith =="roomWise")
		{
			$.ajax({
			    type : "post",
			    url :"/over2cloud/view/Over2Cloud/FloorSettingOver2Cloud/MapRoomCC.action?roomno="+sel_id+"&empId="+empId,
			    success : function(data) 
			    {
					$("#resultDiv").html(data);
					$("#viewempDiv").html("<br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
					$.ajax({
					type :"post",
					url :"/over2cloud/view/Over2Cloud/FloorSettingOver2Cloud/BeforeGetUnMappedData.action?mappWith="+mappWith+"&deptId="+fromDept+"&empId="+empId,
					success : function(empData)
					{
						$("#viewempDiv").html(empData);
					    },
					    error : function () 
					    {
						alert("Somthing is wrong to get Employee Data");
					}
					});
				},
			    error: function()
			    {
			        alert("error");
			    }
			 });
		}
	}	
}


 $.subscribe('makeEffect', function(event,data)
  {
	 setTimeout(function(){ $("#complTarget").fadeIn(); }, 10);
	 setTimeout(function(){ $("#complTarget").fadeOut(); }, 400);
  });
 function getEmpData(deptId,replacedDiv,group)
 {
	 var groupId = $("#"+group).val();
	 $.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/FloorSettingOver2Cloud/getEmployee.action?deptId="+deptId+"&groupId="+groupId,
		    success : function(data) {
	       		$("#"+replacedDiv).html(data);
			},
		    error: function() {
	            alert("error");
	        }
		 });
 }
 
 function onloadData(divID)
	{
        var mappWith=$("#mappWith").val();
        var fromDept=$("#fromDept_id").val();
     	var empId=$("#"+divID).val() || [];
         
		$("#viewempDiv").html("<br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		type :"post",
		url :"/over2cloud/view/Over2Cloud/FloorSettingOver2Cloud/BeforeGetUnMappedData.action?mappWith="+mappWith+"&deptId="+fromDept+"&empId="+empId,
		success : function(empData)
		{
			$("#viewempDiv").html(empData);
		    },
		    error : function () 
		    {
				alert("Somthing is wrong to get Employee Data");
			}
		});
	}
 onloadData('empId');
</SCRIPT>
</head>
<div class="clear"></div>
<div class="middle-content">
<div class="list-icon">
	 <div class="head">
	  CC</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div>
	 <div class="head">Mapping </div> 
</div>
<div class="clear"></div>
<center><div id="resultDiv"></div></center>
	   <div class="listviewBorder" style="margin-top: 10px;width: 97%;margin-left: 20px;" align="center">
	    <div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
			<div class="pwie">
				<table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%">
					<tbody>
						<tr>
							  <td>
								  <table class="floatL" border="0" cellpadding="0" cellspacing="0">
								    <tbody><tr>
								    <td>  
							           <s:select  
					                              	id					=		"group"
					                              	list				=		"floorMap"
					                              	headerKey			=		"-1"
					                              	headerValue			=		"Select Group" 
					                              	cssClass			=		"button"
					                              	cssStyle			=		"margin-left: 3px;width: 143px;height: 30px;"
					                              	theme 				=		"simple"
					                              	onchange			=		"getDeptBySubGroup('fromDept_id',this.value);"
					                              >
							            </s:select>
							            <s:select  
					                              	id					=		"fromDept_id"
					                              	name				=		"fromDept_id"
					                              	list				=		"{'No Data'}"
					                              	headerKey			=		"-1"
					                              	headerValue			=		"Department" 
					                              	cssClass			=		"button"
					                              	cssStyle			=		"margin-left: 3px;width: 143px;height: 30px;"
					                              	theme 				=		"simple"
					                              	onchange			=		"getEmpData(this.value,'replacedEmpId','group');"
					                              >
							            </s:select>
							             </td>
							             <td>
							            <div id="replacedEmpId">
							            <s:select  
					                              	id					=		"proxyEmpId"
					                              	name				=		"proxyEmpId"
					                              	list				=		"#{'-1':'Employee'}"
					                              	cssClass			=		"button"
					                              	cssStyle			=		"margin-left: 3px;width: 143px;height: 80px;"
					                              	theme 				=		"simple"
					                              >
							            </s:select>
							            </div>
		      					  </td>
		      					  <td>
		      					  		<s:select  
					                              	id					=		"mappWith"
					                              	name				=		"mappWith"
					                              	list				=		"#{'deptWise':'Department Wise','roomWise':'Room Wise'}"
					                              	headerKey			=		"-1"
					                              	headerValue			=		"Mapping With" 
					                              	cssClass			=		"button"
					                              	cssStyle			=		"margin-left: 3px;width: 143px;height: 30px;"
					                              	theme 				=		"simple"
					                              	onchange			=		"onloadData('empId');"
					                              >
							            </s:select>
		      					  </td>
		     			 		  </tr></tbody>
								  </table>
								  <sj:a id="add" cssStyle="margin-left: 1px;height: 29px;" buttonIcon="" cssClass="button" button="true" onclick="addEmployee()"> Save </sj:a>
								 <sj:a id="cancel" cssStyle="margin-left: 1px;height: 29px;" buttonIcon="" cssClass="button" button="true"  onclick="back()">Back</sj:a>
							  </td>
							  <td class="alignright printTitle">
							  
							  </td>
						</tr>
					</tbody>
				</table>
			</div>
</div>
<div style="overflow: scroll; height: 430px;">
 <div id="viewempDiv"></div>
 </div>
</div>
</div>
</html>