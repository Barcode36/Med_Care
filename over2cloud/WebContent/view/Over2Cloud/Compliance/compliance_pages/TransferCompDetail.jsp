<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<link type="text/css" href="<s:url value="/css/style.css"/>" rel="stylesheet" />
<script type="text/javascript" src="<s:url value="/js/compliance/compliance.js"/>"></script>
<meta   http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<SCRIPT type="text/javascript">
 $.subscribe('makeEffect', function(event,data)
  {
	 setTimeout(function(){ $("#complTransferTarget").fadeIn(); }, 10);
	 setTimeout(function(){ $("#complTransferTarget").fadeOut(); }, 400);
	 
	 $("#gridTransferComp").jqGrid('setGridParam', {
			url:"/view/Over2Cloud/Compliance/compliance_pages/complTransferAction.action?empName=2771"
			,datatype:'JSON'
			}).trigger("reloadGrid");
		
	 setTimeout(function(){ $("#transferActionGrid").dialog('close'); }, 800);
  });


 
</SCRIPT>
</head>
<div class="clear"></div>
<div class="middle-content">
<div class="clear"></div>
<div style="width: 96%; margin: 1%; border: 1px solid #e4e4e5; -webkit-border-radius: 6px; -moz-border-radius: 6px; border-radius: 6px; -webkit-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); -moz-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); box-shadow: 0 1px 4px rgba(0, 0, 0, .10); padding: 1%;">
    <s:form id="updateReminder" name="updateReminder" action="updateReminderToPort"  theme="simple"  method="post" enctype="multipart/form-data" >
	   <center>
	  		<div style="float:center; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
				<div id="errZone" style="float:center; margin-left: 7px"></div>
	  		</div>
	  </center> 
	  <s:hidden name="selectedId" value="%{selectedId}" />
	  <s:hidden name="empName" value="%{empName}" />
          <s:iterator value="complDDBox" status="status" begin="0" end="1">
          <s:if test="#status.odd">
                   <div class="newColumn">
                   <div class="leftColumn"><s:property value="%{field_name}"/>:</div>
                   <div class="rightColumn">
                   <s:if test="%{mandatory}"><span class="needed"></span></s:if>
                    <s:textfield name="deptName"  id="deptName" value="%{deptName}" cssClass="textField" readonly="true"/>
                    <s:hidden name="forDept_id"  id="forDept_id" value="%{forDept}" />
                   </div>
                   </div>
          </s:if>
          <s:else>
               <div class="newColumn">
          	   <div class="leftColumn"><s:property value="%{field_name}"/>:</div>
               <div class="rightColumn">
               <s:if test="%{mandatory}"><span class="needed"></span></s:if>
               		<s:select  
                              	id					=		"fromDept_id2"
                              	name				=		"fromDept_id"
                              	list				=		"deptMap"
                              	headerKey			=		"-1"
                              	headerValue			=		"From Department" 
                              	cssClass			=		"select"
								cssStyle			=		"width:82%"
                              	onchange			=		"getCompAllotedEmp(this.value,'emp_id2');"
                              >
                   </s:select>
               </div>
               </div>
          </s:else>
          </s:iterator>
         
      <s:iterator value="complDDBox" status="status" begin="2" end="2">
        	<s:if test="%{field_value == 'emp_id'}"> 
               <div class="newColumn">
          	   <div class="leftColumn"><s:property value="%{field_name}"/>:</div>
               <div class="rightColumn"><s:if test="%{mandatory}"><span class="needed"></span></s:if>
                        <s:select  
                              	id					=		"%{field_value }2"
                              	name				=		"empId"
	                            list				=		"#{'-1':'Select Employee'}" 
                              	cssClass			=		"select"
								cssStyle			=		"width:82%"
                              >
                        </s:select>
                </div>
                </div>
           </s:if>
        </s:iterator>
        <span id="complSpansss" class="pIdssss" style="display: none; ">forDept_id#For Department#D#,fromDept_id2#From Department#D#,emp_id2#Employee#D#,</span>
		<center>
	 	 	<img id="indicator2" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/>
		</center>
		<div class="clear"></div>
		<div class="clear"></div>
		<center>		
	 	<div class="fields">
			<ul>
		  		<li class="submit" style="background: none;">
			 		<div class="type-button">
	            		<sj:submit 
	                        		targets			 =	"complTransferTarget" 
	                        		clearForm		 =	"true"
	                        		value			 =	" Save " 
	                        		effect			 =	"highlight"
	                        		effectOptions	 =	"{ color : '#222222'}"
	                        		effectDuration	 =	"5000"
	                        		button			 =	"true"
	                        		cssClass		 =	"submit"
	                        		indicator		 =	"indicator2"
	                        		onCompleteTopics =  "makeEffect"
	                        		onBeforeTopics	 =	"validateTransfer"	
	                  />
	         		</div>
	    	 </ul>
	  	<sj:div id="complTransferTarget"  effect="fold"> </sj:div>
   		</div>
   		</center>
   </s:form>
</div>
</div>
</html>