<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<link type="text/css" href="<s:url value="/css/style.css"/>" rel="stylesheet" />
<script type="text/javascript" src="<s:url value="/js/compliance/ComplianceMenuCtrl.js"/>"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script type="text/javascript">
function getUnMappedEmp()
{
	var empName=$("#empDD").val();
	var dataFor=$("#data4").val();
	var mappedLevel=$("#level").val();
	var cntMapping="contactMapping";
	$("#unMappedTarget").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/Compliance/compl_contacts_pages/getUnMappedEmp.action?empName="+empName+"&moduleName="+dataFor+"&mappedLevel="+mappedLevel+"&mapping_sharing="+cntMapping,
	    success : function(data) 
	    {
			$("#"+"unMappedTarget").html(data);
	    },
	    error: function() 
	    {
            alert("error");
        }
	 });
}
getUnMappedEmp();
</script>
</head>
<div class="middle-content">
<div class="clear"></div>
<div class="list-icon">
	 <div class="head">
	  Map Contact</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div>
	 <div class="head">For <s:property value="%{deptName}"/> Department</div>  
</div>
	<div class="clear"></div>
	<center><div id="resultDiv"></div></center>
	   <s:hidden id="data4" name="moduleName" value="%{moduleName}"/>
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
									                              	id					=		"level"
									                              	name				=		"contactLevel"
									                              	list				=		"level"
									                              	headerKey			=		"-1"
									                              	headerValue			=		"Select Level" 
									                              	cssClass			=		"button"
									                              	cssStyle			=		"margin-left: 8px;width: 125px;height: 29px;"
																	theme 				=		"simple"
									                              	onchange			=		"getLevelMappedEmp(this.value,'data4');"
									                              >
									                	</s:select>
									               		<s:select  
								                              	id					=		"empDD"
								                              	name				=		"empId"
								                              	list				=		"{'No Data'}"
								                              	headerKey			=		"-1"
								                              	headerValue			=		"Select Mapped Emp" 
								                              	cssClass			=		"button"
								                              	cssStyle			=		"width: 128px;height: 29px;"
																theme 				=		"simple"
								                              	onchange			=		"getUnMappedEmp('empDD','data4','level','contactMapping');"
								                              >
								                	</s:select>
         						
							  			     <sj:a id="add" buttonIcon="" cssClass="button" button="true" onclick="mappedContact()"> Save </sj:a>
											 <sj:a id="back" buttonIcon="" cssClass="button" button="true" onclick="backMappingData('dataFor')"> Back </sj:a>
								      </td>
								      
								      </tr></tbody>
								  </table>
							  </td>
							  <td class="alignright printTitle">
							  </td>
						</tr>
					</tbody>
				</table>
			</div>
</div>
<div style="overflow: scroll; height: 430px;">
	  <div id="unMappedTarget"></div>
 </div>
</div>
</div>
</html>