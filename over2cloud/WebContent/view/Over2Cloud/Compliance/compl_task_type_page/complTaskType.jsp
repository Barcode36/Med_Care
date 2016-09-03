<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%
int temp=0;
%>
<html>
<head>
	<s:url  id="contextz" value="/template/theme" />
	<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
	<link type="text/css" href="<s:url value="/css/style.css"/>" rel="stylesheet" />
	<script type="text/javascript" src="<s:url value="/js/compliance/compliance.js"/>"></script>
	<meta   http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<link href="js/multiselect/jquery-ui.css" rel="stylesheet" type="text/css" />
<link href="js/multiselect/jquery.multiselect.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<s:url value="/js/multiselect/jquery-ui.min.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/multiselect/jquery.multiselect.js"/>"></script>
	
<SCRIPT type="text/javascript">
 $.subscribe('makeEffect', function(event,data)
  {
	 setTimeout(function(){ $("#complTarget").fadeIn(); }, 10);
	 setTimeout(function(){ $("#complTarget").fadeOut(); }, 400);
	 
	 resetTaskType('taskTypeForm');
  });

 function viewTaskType()
	{
	 $("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/Compliance/compl_task_type_page/beforeComplTaskTypeView.action?modifyFlag=1&deleteFlag=0",
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
 function resetTaskType(formId) 
 {
	 $('#'+formId).trigger("reset");
 }
</SCRIPT>
<script type="text/javascript">
$(document).ready(function()
	{
	$("#deptIdDD").multiselect({
		   show: ["", 200],
		   hide: ["explode", 1000]
		});
	});
</script>
</head>
<body>
<div class="clear"></div>
<div class="middle-content">
<div class="list-icon">
	 <div class="head">
	 Task Type</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div>
	 <div class="head">Add</div> 
</div>
<div class="clear"></div> 
<div style="overflow:auto; width: 96%; margin: 1%; border: 1px solid #e4e4e5; -webkit-border-radius: 6px; -moz-border-radius: 6px; border-radius: 6px; -webkit-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); -moz-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); box-shadow: 0 1px 4px rgba(0, 0, 0, .10); padding: 1%;">
    <s:form id="taskTypeForm" name="taskTypeForm" action="taskType" namespace="/view/Over2Cloud/Compliance/compl_task_type_page" theme="simple"  method="post"enctype="multipart/form-data" >
	  <div class="clear"></div>
		<div class="clear"></div> 
	   <center>
	  	<div style="float:center; border-color: black; background-color: rgb(255,99,71); color: white;width: 50%; height: 10%; font-size: small; border: 0px solid red; border-radius: 6px;">
			<div id="errZone" style="float:center; margin-left: 7px"></div>
	  	</div>
	  </center>
	   	  <s:if test="%{allDeptName.size()==0}">
	   	  		<div class="newColumn">
			  			 <div class="leftColumn">Department Name:</div>
	            		 <div class="rightColumn">
				              <span class="needed"></span>
				              		<s:textfield id="deptName"  maxlength="50" value="%{deptName}" cssClass="textField" disabled="true" placeholder="Enter Data" cssStyle="width: 92%"/>
				              		<s:hidden id="deptId" name="deptName" maxlength="50" value="%{deptId}" cssClass="textField" placeholder="Enter Data"/>
				         </div>
			     </div>
	   	  </s:if>
	   	  <s:else>
	   	  <span id="complSpan" class="pIds" style="display: none; ">deptIdDD#Department Name#D#,</span>
	   	  		<div class="newColumn">
			  			 <div class="leftColumn">Department Name:</div>
	            		 <div class="rightColumn">
				              <span class="needed"></span>
				              <s:select 
				                   cssStyle="width:5%"
				                   id="deptIdDD"
				                   name="multiDept" 
				                   list="allDeptName" 
				                   multiple="true"
				                   cssClass="textField"
				                   
                    		>
        					</s:select>
				          </div>
			             </div>
	   	  </s:else>
	   
		  <s:iterator value="taskTypeList" status="status" >
		     <s:if test="%{mandatory}">
		     	<s:if test="field_value=='taskType' || field_value=='taskTypeBrief'">
		     		<span id="complSpan" class="pIds" style="display: none; "><s:property value="%{field_value}"/>#<s:property value="%{field_name}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                </s:if>
             </s:if>
			 <s:if test="#status.odd">
			             <div class="newColumn">
			  			 <div class="leftColumn"><s:property value="%{field_name}"/>:</div>
	            		 <div class="rightColumn">
				              <s:if test="%{mandatory}"><span class="needed"></span></s:if>
				              		<s:textfield cssStyle="width: 92%" name="%{field_value}" id="%{field_value}"  maxlength="50" cssClass="textField" placeholder="Enter Data"/>
				         </div>
			             </div>
			 </s:if>
			 <s:else>
			           	 <div class="newColumn">
			  			 <div class="leftColumn"><s:property value="%{field_name}"/>:</div>
	            		 <div class="rightColumn">
				            <s:if test="%{mandatory}"><span class="needed"></span></s:if>
				            	<s:textfield cssStyle="width: 92%" name="%{field_value}" id="%{field_value}"  maxlength="50"  cssClass="textField" placeholder="Enter Data"/>
				         </div>
			             </div>
			 </s:else>
		  </s:iterator>
		<center>
	 	 	<img id="indicator2" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/>
		</center>
		<center>
		<div class="clear"></div>
        <div class="clear"></div>
        <br>
		<div class="fields">
		<center>
		 <ul>
			<li class="submit" style="background:none;">
			<div class="type-button">
	        <sj:submit 
         				targets			=	"complTarget" 
         				clearForm		=	"true"
         				value			=	" Save " 
         				button			=	"true"
                 		indicator		=	"indicator2"
                 		effect			=	"highlight"
	                    effectOptions	=	"{ color : '#222222'}"
                 		effectDuration	=	"5000"
                 		onCompleteTopics=	"makeEffect"
                        onBeforeTopics	=	"validateTaskType"
                        indicator		=	"indicator2"
                        formIds="taskTypeForm"
                        id="sjhfgdhgsdh"
                        
     		  	  />
     		  	    <sj:a 
						button="true" href="#" 
						onclick="resetTaskType('taskTypeForm');resetColor('.pIds');"
						>
						Reset
					</sj:a>
     		  	  <sj:a 
						button="true" href="#" 
						onclick="viewTaskType();"
						>
						Back
					</sj:a>
	        </div>
	        </li>
		 </ul>
		 </center>
		 <sj:div id="complTarget"  effect="fold">
   	     </sj:div>
		 </div>
		 </center>
   </s:form>
   </div>
</div>
</body>
</html>