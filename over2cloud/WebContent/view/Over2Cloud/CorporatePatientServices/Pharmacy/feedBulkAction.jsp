<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<script type="text/javascript" src="<s:url value="/js/order/feedback.js"/>"></script>
<SCRIPT type="text/javascript"> 
function getDetailsData(value,divId,divId1,errDiv,sourceDiv) {
	value=value.toUpperCase();
  	if(value=='NA')
	{
		$("#"+divId).attr("readonly", false);
		$("#"+divId).focus();
	 
	}	
	else
	{
		$.ajax({
			type : "post",
			url : "view/Over2Cloud/CorporatePatientServices/Pharmacy/fetchEmpNameById.action?uhid=" + value,
			success : function(data) {
		  			if(data[0].fName==undefined)
					{
						$("#"+errDiv).html("Please Enter Valid Emp ID!!!");
						$("#" + sourceDiv).css("background-color", "#ff701a");
						setTimeout(function() {$("#"+errDiv).fadeIn();}, 10);
						setTimeout(function() {$("#"+errDiv).fadeOut();}, 4000);
					}	
					else
					{
						$("#" + divId).val(data[0].fName+ " " + data[0].lName);
						/*$("#" + divId1).val(data[0].mobile);*/
					}	
				 
			},
			error : function() {
				alert("error");
			}
		});
	}	
}
 

$.subscribe('reload', function(event, data) {

	setTimeout(function(){$("#result1").fadeIn();}, 10);
	setTimeout(function(){$("#result1").fadeOut();ORDERActivityRefresh();$("#takeActionGrid").dialog('close');}, 2000); 
    
});
   
</script>
</head>
<body onload="StopRefresh();">
<div class="clear"></div>
<div class="middle-content">
<div class="clear"></div>
<div style="overflow:auto; height:auto; width: 96%; margin: 1%; border: 1px solid #e4e4e5; -webkit-border-radius: 6px; -moz-border-radius: 6px; border-radius: 6px; -webkit-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); -moz-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); box-shadow: 0 1px 4px rgba(0, 0, 0, .10); padding: 1%;">
 
	 		<s:form id="formone1" name="formone" action="actionOnPharmacyBulkClose"  theme="css_xhtml"  method="post" enctype="multipart/form-data">
	  	    <s:hidden name="complianId" id="complianId" value="%{complianId}"></s:hidden>
	  	      <s:hidden name="nursingCode" id="nursingCode" value="%{nursingCode}"></s:hidden>
	   	    <s:hidden id="feedStatus" name="feedStatus" value="%{feedStatus}"></s:hidden>
	 	     <s:hidden id="uhid" name="uhid" value="%{uhid}"></s:hidden>
	 	 	 <center><div style="float:center; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;"><div id="errZone1" style="float:center; margin-left: 7px"></div></div></center>
	   <s:if test="feedStatus=='Dispatch' || feedStatus=='Dispatch-P' ">
           <s:if test="feedStatus=='Dispatch'"> 
	       	 	<s:hidden id="actionStatus" name="actionStatus" value="Close"></s:hidden>
	        </s:if>
	        
	        <s:if test="feedStatus=='Dispatch-P'"> 
	       	 	<s:hidden id="actionStatus" name="actionStatus" value="Close-P"></s:hidden>
	        </s:if>
	        
	   			<span class="pIds1" style="display: none; ">closeById#Close By Id#T#sc,</span>
				 <div class="newColumn"  >
				 <div class="leftColumn">Close By Id:</div>
			 		<div class="rightColumn"  >
						<span class="needed"></span>
					      <s:textfield
								id="closeById" name="closeById" autoFocus="true" cssClass="textField" theme="simple" placeHolder="Caller Emp ID"   onblur="getDetailsData(this.value,'closeByName','errZone','closeById');resetColor('.pIds');"/>
					</div>
				 </div>
			 		 
			 		 <div class="newColumn"  >
					 <div class="leftColumn">Close By Name:</div>
				 		<div class="rightColumn" >
						      <s:textfield id="closeByName" name="closeByName" cssClass="textField"  placeHolder="Caller Emp Name" readonly="true" theme="simple"/>
						</div>
					 </div>
			 
			 
                 <div class="newColumn">
		       <div class="leftColumn">Comment:</div>
		            <div class="rightColumn">
		  	                 <s:textfield name="comment" id="comment" placeholder="Enter Comment"  cssClass="textField" />
		            </div>
               </div>
   		</s:if>
      
      <div class="clear"></div>
		<div class="clear"></div>
		    
		   
    <div class="fields" align="center">
   <center><img id="indicator1" src="<s:url value="/images/ajax-loader_small.gif"/>" alt="Loading..." style="display:none"/></center>
   <ul><li class="submit">
   <div class="type-button">
   <div id="bt" style="display: block;">
     		<sj:submit 
   				id ="actionId1"
	           targets="result1" 
	           clearForm="true"
	           value="Save" 
	           effect="highlight"
	           effectOptions="{ color : '#222222'}"
	           effectDuration="5000"
	           button="true"
	           onCompleteTopics="reload"
	           onBeforeTopics="validateActionForPharma"
	          	cssStyle=" margin-top: 12px;"
			   />
	   
   </div>
   </div>
   </li>
   </ul>
   </div>
   <div id="result1"></div>
</s:form>
</div>
</div>
</body>
</html>