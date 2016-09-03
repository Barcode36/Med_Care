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
 function getCompAllotedEmp(deptId,divId)
 {
	 $.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/Compliance/compliance_pages/getEmpData.action",
		data: "deptId="+deptId, 
		success : function(data) 
		{   
		$('#'+divId+' option').remove();
		$('#'+divId).append($("<option></option>").attr("value",-1).text('Select Employee'));
		$(data).each(function(index)
		{
		   $('#'+divId).append($("<option></option>").attr("value",this.ID).text(this.NAME));
		});
		},
		   error: function() {
	            alert("error");
	        }
		 });
 }
</SCRIPT>
</head>
<div class="clear"></div>
<div class="middle-content">
<div class="list-icon">
<div class="head">Operation Task >> Port</div>
</div>
<div class="clear"></div>
<div style="overflow:auto; height:390px; width: 96%; margin: 1%; border: 1px solid #e4e4e5; -webkit-border-radius: 6px; -moz-border-radius: 6px; border-radius: 6px; -webkit-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); -moz-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); box-shadow: 0 1px 4px rgba(0, 0, 0, .10); padding: 1%;">

     <s:form id="complTransfer" name="complTransfer" action="complTransferAction"  theme="simple"  method="post"enctype="multipart/form-data" >
	   <center>
	  		<div style="float:center; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
				<div id="errZone" style="float:center; margin-left: 7px"></div>
	  		</div>
	  </center> 
          <s:iterator value="complTransferColMap" status="status" begin="0" end="1">
	          <s:if test="%{mandatory}">
			     		<span id="complSpan" class="pIds" style="display: none; "><s:property value="%{key}"/>1#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
	          </s:if>
	          <s:if test="#status.odd">
	                   <div class="newColumn">
	                   <div class="leftColumn"><s:property value="%{value}"/>:</div>
	                   <div class="rightColumn">
	                   <s:if test="%{mandatory}"><span class="needed"></span></s:if>
	                   <s:select  
	                              	id					=		"%{key}1"
	                              	name				=		"%{value}"
	                              	list				=		"deptMap"
	                              	headerKey			=		"-1"
	                              	headerValue			=		"Select Department" 
	                              	cssClass			=		"select"
									cssStyle			=		"width:82%"
	                              	onchange			=		"getCompAllotedEmp(this.value,'emp_id1')"
	                              >
	                   </s:select>
	                   </div>
	                   </div>
	          </s:if>
	          <s:else>
	          <div class="newColumn">
	          <div class="leftColumn"><s:property value="%{value}"/>:</div>
	               <div class="rightColumn">
	               <s:if test="%{mandatory}"><span class="needed"></span></s:if>
	               		<s:select  
	                              	id					=		"%{key}1"
	                              	name				=		"%{value}"
	                              	list				=		"#{'-1':'Select Employee'}"
	                              	headerValue			=		"Select Employee" 
	                              	cssClass			=		"select"
									cssStyle			=		"width:82%"
	                              >
	                   </s:select>
	               </div>
	          </div>
	          </s:else>
          </s:iterator>
        <br>
		<center>	
	 	<div class="fields">
			<ul>
		  		<li class="submit" style="background: none;">
			 		<div class="type-button">
	            		<sj:submit 
	                        		targets			 =	"complTarget" 
	                        		clearForm		 =	"false"
	                        		value			 =	" Get Alloted Comp " 
	                        		button			 =	"true"
	                        		cssClass		 =	"submit"
	                        		indicator		 =	"indicator2"
	                        		onBeforeTopics	 =	"validateTaskType"	
	                        		indicator		 =	"indicator2"
	                        		openDialog		 =  "transferActionGrid1"
	                  />
	         		</div>
	    	 </ul>
   		</div>
   		</center>
   </s:form>
<br><br><br><br>
<div id="complTarget">
<center>
<img id="indicator2" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/>
</center>
</div>
</div>
</div>
</html>