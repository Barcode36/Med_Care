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
<SCRIPT type="text/javascript">
 $.subscribe('makeEffect', function(event,data)
  {
	 setTimeout(function(){ $("#complTarget").fadeIn(); }, 10);
	 setTimeout(function(){ $("#complTarget").fadeOut(); }, 400);
  });

 function viewComplTemplate()
	{
	 $("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/Compliance/compliance_pages/BeforeCompTemplateView.action",
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
</head>
<body>
<div class="clear"></div>
<div class="middle-content">
<div class="list-icon">
<div class="head">Operation Task >> Template</div>
</div>
<div class="clear"></div>
<div style="overflow:auto; height:380px; width: 96%; margin: 1%; border: 1px solid #e4e4e5; -webkit-border-radius: 6px; -moz-border-radius: 6px; border-radius: 6px; -webkit-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); -moz-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); box-shadow: 0 1px 4px rgba(0, 0, 0, .10); padding: 1%;">


    <s:form id="compTemplateForm" name="compTemplateForm" action="ComplTemplateAction" namespace="/view/Over2Cloud/Compliance/compliance_pages" theme="simple"  method="post"enctype="multipart/form-data" >
	   <center>
	  	<div style="float:center; border-color: black; background-color: rgb(255,99,71); color: white;width: 50%; height: 10%; font-size: small; border: 0px solid red; border-radius: 6px;">
			<div id="errZone" style="float:center; margin-left: 7px"></div>
	  	</div>
	  </center>
	   
		  <s:iterator value="compTemplateList" status="status" >
		     <s:if test="%{mandatory}">
		     		<span id="complSpan" class="pIds" style="display: none; "><s:property value="%{field_value}"/>#<s:property value="%{field_name}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
             </s:if>
			 <s:if test="#status.odd">
			             <div class="newColumn">
			  			 <div class="leftColumn"><s:property value="%{field_name}"/>:</div>
	            		 <div class="rightColumn">
				              <s:if test="%{mandatory}"><span class="needed"></span></s:if>
				              		<s:textfield name="%{field_value}" id="%{field_value}"   cssClass="textField" placeholder="Enter Data"/>
				         </div>
			             </div>
			 </s:if>
			 <s:else>
			           	 <div class="newColumn">
			  			 <div class="leftColumn"><s:property value="%{field_name}"/>:</div>
	            		 <div class="rightColumn">
				            <s:if test="%{mandatory}"><span class="needed"></span></s:if>
				            	<s:textfield name="%{field_value}" id="%{field_value}"    cssClass="textField" placeholder="Enter Data"/>
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
		<div class="fields">
		<center>
		 <ul>
			<li class="submit" style="background:none;">
			<div class="type-button">
	        <sj:submit 
         				targets			=	"complTemplate" 
         				clearForm		=	"true"
         				value			=	" Add " 
         				button			=	"true"
         				cssClass		=	"submit"
                 		indicator		=	"indicator2"
                 		effect			=	"highlight"
	                    effectOptions	=	"{ color : '#222222'}"
                 		effectDuration	=	"5000"
                 		onCompleteTopics=	"makeEffect"
                        indicator		=	"indicator2"
                        
     		  	  />
     		  	  <sj:a 
						button="true" href="#"
						onclick="viewComplTemplate();"
						>
						Cancel
					</sj:a>
	        </div>
	        </li>
		 </ul>
		 </center>
		 <sj:div id="complTemplate"  effect="fold">
   	     </sj:div>
		 </div>
		 </center>
   </s:form>
</div>
</div>
</body>
</html>