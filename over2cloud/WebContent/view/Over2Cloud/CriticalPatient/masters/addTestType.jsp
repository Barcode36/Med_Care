<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
	<s:url  id="contextz" value="/template/theme" />
	<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
	<link type="text/css" href="<s:url value="/css/style.css"/>" rel="stylesheet" />
	<script type="text/javascript" src="<s:url value="/js/criticalPatient/criticalcommonvalidation.js"/>"></script>
	<meta   http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	
	

<script type="text/javascript">
		$.subscribe('makeEffect', function(event, data) 
		{
			
			setTimeout(function(){ $("#complTarget").fadeIn(); }, 10);
            setTimeout(function(){ $("#complTarget").fadeOut(); cancelButton();}, 1000);
				
			});

		 function cancelButton()
		 {
		 $('#completionResult').dialog('close');
		 addTestType();
		 }
</script>
</head>
<body>
<sj:dialog
          id="completionResult"
          showEffect="slide" 
          hideEffect="explode" 
          openTopics="openEffectDialog"
          closeTopics="closeEffectDialog"
          autoOpen="false"
          title="Confirmation Message"
          modal="true"
          draggable="true"
          resizable="true"
          buttons="{ 
                    'Close':function() { cancelButton(); } 
                                                            }" 
          >
          <sj:div id="complTarget" cssStyle="" effect="fold"></sj:div>
</sj:dialog>

<div class="clear"></div>
<div class="middle-content">
<div class="list-icon">
	 <div class="head">
	 Test Type</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div>
	 <div class="head">Add</div> 
</div>
<div class="clear"></div> 

<div style="overflow:auto; width: 96%; margin: 1%; border: 1px solid #e4e4e5; -webkit-border-radius: 6px; -moz-border-radius: 6px; border-radius: 6px; -webkit-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); -moz-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); box-shadow: 0 1px 4px rgba(0, 0, 0, .10); padding: 1%;">
   
    <s:form id="testType" name="testType" action="addTestType" namespace="/view/Over2Cloud/CriticalMaster" theme="simple"  method="post"enctype="multipart/form-data" >
	  
	  <div class="clear"></div>
	   <div class="clear"></div> 
	   <center>
	  	<div style="float:center; border-color: black; background-color: rgb(255,99,71); color: white;width: 50%; height: 10%; font-size: small; border: 0px solid red; border-radius: 6px;">
			<div id="errZone" style="float:center; margin-left: 7px"></div>
	  	</div>
	  </center>
			             
		  <s:iterator value="reportTypeList" status="status" >
		     <s:if test="%{mandatory}">
		     	<span id="complSpan" class="pIds" style="display: none; "><s:property value="%{field_value}"/>#<s:property value="%{field_name}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
             </s:if>
			       	<div class="newColumn" style="float: left;margin-left: -7%;">
			  		<div class="leftColumn"><s:property value="%{field_name}"/>:</div>
	           		<div class="rightColumn">
				   		 <s:if test="%{mandatory}"><span class="needed"></span></s:if>
				         	<s:textfield cssStyle="width: 92%" name="%{field_value}" id="%{field_value}"  maxlength="50" cssClass="textField" placeholder="Enter Data"/>
				    </div>
			        </div>
		  </s:iterator>
		  
		
	
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
	                    effectOptions	=	"{ color : '#ffffff'}"
                 		effectDuration	=	"5000"
                 		onCompleteTopics=	"makeEffect"
                 		onBeforeTopics="validate"
                      
                       
                       
     		  	  />
     		  	    <sj:a 
						button="true" href="#" 
						onclick="resetTaskType('testType');resetColor('.pIds');"
						>
						Reset
					</sj:a>
     		  	  <sj:a 
						button="true" href="#" 
						onclick="viewtestType();"
						>
						Back
					</sj:a>
	        </div>
	        </li>
		 </ul>
		 </center>
		 <br>
		
		 </div>
		 </center>
		 
   </s:form>
   </div>
</div>
</body>
</html>