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
	<script type="text/javascript" src="<s:url value="/js/KRAKPI/krakpi.js"/>"></script>
	<meta   http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	
<SCRIPT type="text/javascript">
 $.subscribe('makeEffect', function(event,data)
  {
	 setTimeout(function(){ $("#complTarget").fadeIn(); }, 10);
	 setTimeout(function(){ $("#complTarget").fadeOut(); }, 400);
	 
	 resetTaskType('bandForm');
  });
 
 function checkValues()
	{
		var dept= ($("#departName").val());
		if(dept.length>1)
		{
			document.getElementById('newDiv').style.display = 'none';
		}
		else
		{
			document.getElementById('newDiv').style.display = 'block';
		}	
	 }

 function viewBand()
	{
	 $("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	 $.ajax({
			type:"post",
			 url : "view/Over2Cloud/Compliance/Location/beforebranchOfficeConfig.action",
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

</head>
<body>
<div class="clear"></div>
<div class="middle-content">
<div class="list-icon">
	 <div class="head">
	 Head Office</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div>
	 <div class="head">Add</div> 
</div>
<div class="clear"></div> 
<div style="overflow:auto; width: 96%; margin: 1%; border: 1px solid #e4e4e5; -webkit-border-radius: 6px; -moz-border-radius: 6px; border-radius: 6px; -webkit-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); -moz-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); box-shadow: 0 1px 4px rgba(0, 0, 0, .10); padding: 1%;">
    <s:form id="HeadOfficeForm" name="HeadOfficeForm" action="addHeadOffice" namespace="/view/Over2Cloud/Compliance/Location" theme="simple"  method="post" >
	  <div class="clear"></div>
		<div class="clear"></div> 
	   <center>
	  	<div style="float:center; border-color: black; background-color: rgb(255,99,71); color: white;width: 50%; height: 10%; font-size: small; border: 0px solid red; border-radius: 6px;">
			<div id="errZone" style="float:center; margin-left: 7px"></div>
	  	</div>
	  </center>
 			<s:iterator value="bandDropdown" status="status" >
		     <s:if test="%{mandatory}">
		     	<span id="complSpan" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{key}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
             </s:if>
			       	<div class="newColumn" >
			  		<div class="leftColumn1"><s:property value="%{value}"/>:</div>
	           		<div class="rightColumn1">
				    <s:if test="%{mandatory}"><span class="needed"></span></s:if>
				          <s:select 
                                      id="%{key}"
                                      name="%{key}" 
                                      list="%{deptNameMap}"
                                      headerKey="-1"
                                      headerValue="Select Country"
                                      cssClass="select"
                                      cssStyle="width:82%"
                                      theme="simple"
                                      >
                          </s:select>
				    </div>
			        </div>
		  </s:iterator>
		  <s:iterator value="bandTextBox" status="status" >
		     <s:if test="%{mandatory}">
		     	<span id="complSpan" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{key}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
             </s:if>
			       	<div class="newColumn" >
			  		<div class="leftColumn1"><s:property value="%{value}"/>:</div>
	           		<div class="rightColumn1">
				    <s:if test="%{mandatory}"><span class="needed"></span></s:if>
				         <s:textfield  name="%{key}" id="%{key}"  maxlength="50" cssClass="textField" placeholder="Enter Data"/>
				    </div>
			        </div>
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
                        onBeforeTopics	=	""
                        indicator		=	"indicator2"
                        formIds="HeadOfficeForm"
     		  	  />
     		  	    <sj:a 
						button="true" href="#" 
						onclick="resetTaskType('HeadOfficeForm');resetColor('.pIds');"
						>
						Reset
					</sj:a>
     		  	  <sj:a 
						button="true" href="#" 
						onclick="viewBand();"
						>
						Back
					</sj:a>
	        </div>
	        </li>
		 </ul>
		 </center>
		 <br>
		  <center>
		 <sj:div id="complTarget"  effect="fold">
   	     </sj:div>
   	      </center>
		 </div>
		 </center>
   </s:form>
   </div>
</div>
</body>
</html>