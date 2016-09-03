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
<meta   http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<SCRIPT type="text/javascript">
 $.subscribe('makeEffect', function(event,data)
  {
	 setTimeout(function(){ $("#complTarget").fadeIn(); }, 10);
	 setTimeout(function(){ $("#complTarget").fadeOut(); }, 400);
  });
 function viewContactMaster()
	{
	 $("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/Compliance/compl_contacts_pages/beforeComplContactView.action?modifyFlag=0&deleteFlag=1",
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

 function getEmpData(fromDept,destAjaxDiv,forDeptTextId,dataForId) 
 {
 	$("#viewempDiv").html("<br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
 	$.ajax({
 	type :"post",
 	url :"/over2cloud/view/Over2Cloud/Text2Mail/beforeConsultantsView2.action?destination="+ destAjaxDiv+"&mapWith="+fromDept,
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
	
</SCRIPT>
</head>
<div class="clear"></div>
<div class="middle-content">
<div class="list-icon">
	 <div class="head">
	 Mapping</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div>
	 <div class="head">Executive With Consultants</div> 
</div>
<div class="clear"></div>
<br>
<br>
    <s:form id="complContacts" name="complContacts" action="complContactAction" namespace="/view/Over2Cloud/Compliance/compl_contacts_pages" theme="simple"  method="post"enctype="multipart/form-data" >
	  
          
          
          	   <div class="newColumn">
			   <div class="leftColumn">L1-Executive:</div>
	           <div class="rightColumn">
               		<s:select  
                              	id					=		"executive"
                              	name				=		"executive"
                              	list				=		"locationMasterMap"
                              	headerKey			=		"-1"
                              	headerValue			=		"From Executive" 
                              	cssClass			=		"select"
								cssStyle			=		"width:82%"
								onchange			=		"getEmpData(this.value,'EmpDiv1','forDeptId');"
                              >
                   </s:select>
               </div>
          </div>
		<center>
	 	 	<img id="indicator2" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/>
		</center>
		<div class="clear"></div>
		<div class="clear"></div>		
	 	<div class="fields">
	 	<sj:div id="complTarget"  effect="fold"> </sj:div>
   		</div>
   </s:form>
   <div id="viewempDiv"></div>
</div>
</html>