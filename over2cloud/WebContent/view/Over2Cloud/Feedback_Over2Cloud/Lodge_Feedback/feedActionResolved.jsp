<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<script type="text/javascript" src="<s:url value="/js/helpdesk/common.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/helpdesk/fbdraft.js"/>"></script>
<SCRIPT type="text/javascript" src="js/feedback/feedback.js"></SCRIPT>
<SCRIPT type="text/javascript">
function viewTicketsPage()
{
	//alert("Hello dude ...");
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/Feedback_Over2Cloud/Lodge_Feedback/beforeFeedActionDept.action?feedStatus=Pending",
	    success : function(subdeptdata) {
		$("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
   alert("error");
}
	 });
}

function resetForm(formId)
{
	$('#'+formId).trigger("reset");
}
</SCRIPT>
</head>
<body>
<div class="list-icon">
	 <div class="head">Take Action on Resolved Ticket </div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div><div class="head"><s:property value="%{#parameters.ticketNo}"></s:property></div> 
</div>
 <div class="clear"></div>
  <div class="clear"></div>
     <div style="width: 100%; center; padding-top: 10px;" align="center">
       <div class="border" style="height: 50%" align="center">
		<s:hidden id="feedStatus" value="%{#parameters.feedStatus}"></s:hidden>
		<s:form id="formone" name="formone" action="commentOnFeedback"  theme="css_xhtml"  method="post" enctype="multipart/form-data">
		    <s:hidden name="ticketno" id="ticketno" value="%{#parameters.ticketNo}"></s:hidden>
		    <s:hidden name="feedid" id="feedid" value="%{#parameters.feedId}"></s:hidden>
		    
		    <center><div style="float:center; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;"><div id="errZone" style="float:center; margin-left: 7px"></div></div></center>
		    
		    
		    <div class="newColumn">
		       <div class="leftColumn"><B>MRD No:</B></div>
		            <div class="rightColumn">
		                	 <s:property value="%{crNo}" />
		            </div>
            </div>
            
            <div class="newColumn">
		       <div class="leftColumn"><B>Patient Type:</B></div>
		            <div class="rightColumn">
		                	 <s:property value="%{patTyp}" />
		            </div>
            </div>
            
		    <div class="newColumn">
		       <div class="leftColumn"><B>Doctor Name:</B></div>
		            <div class="rightColumn">
		                	 <s:property value="%{docName}" />
		            </div>
            </div>
		    <div class="newColumn">
		       <div class="leftColumn"><B>Patient Name:</B></div>
		            <div class="rightColumn">
		                	 	 <s:property value="%{#parameters.feedbackBy}" />
		            </div>
            </div>
		    <div class="clear"></div>
		    
		    <div class="newColumn">
		       <div class="leftColumn"><B>Mobile No:</B></div>
		            <div class="rightColumn">
		                	  <s:property value="%{mobileno}" />
		            </div>
            </div>
		    <div class="newColumn">
		       <div class="leftColumn"><B>Email Id:</B></div>
		            <div class="rightColumn">
		                	 <s:property value="%{emailId}" />
		            </div>
            </div>
            
            <div class="newColumn">
		       <div class="leftColumn"><B>Room No:</B></div>
		            <div class="rightColumn">
		                	  <s:property value="%{roomNo}" />
		            </div>
            </div>
            
            <div class="newColumn">
		       <div class="leftColumn"><B>Discharge Date Time:</B></div>
		            <div class="rightColumn">
		                	 <s:property value="%{dischargeDateTime}" />
		            </div>
            </div>
            
            <div class="newColumn">
		       <div class="leftColumn"><B>Sub Category:</B></div>
		            <div class="rightColumn">
		                	 <s:property value="%{#parameters.subCatg}" />
		            </div>
            </div>
		    <div class="newColumn">
		       <div class="leftColumn"><B>Brief:</B></div>
		            <div class="rightColumn">
		                	 <s:property value="%{#parameters.feedBreif}" />
		            </div>
            </div>
            <div class="newColumn">
		       <div class="leftColumn"><B>Open Date & Time :</B></div>
		            <div class="rightColumn">
		                	 <s:property value="%{#parameters.openDate}" />, <s:property  value="%{#parameters.openTime}" />
		            </div>
            </div>
            
            <div class="newColumn">
		       <div class="leftColumn"><B>Resolved Date & Time :</B></div>
		            <div class="rightColumn">
		                	 <s:property value="%{#parameters.resolvedOn}" />, <s:property  value="%{#parameters.resolvedAt}" />
		            </div>
            </div>
             
               <div class="newColumn">
		       <div class="leftColumn"><B>RCA :</B></div>
		            <div class="rightColumn">
		                	 <s:property value="%{#parameters.rca}" />
		            </div>
            </div>
            <div class="newColumn">
		       <div class="leftColumn"><B>CAPA :</B></div>
		            <div class="rightColumn">
		                	 <s:property value="%{#parameters.capa}" />
		            </div>
            </div>
            <div class="newColumn">
		       <div class="leftColumn"><B>Comments :</B></div>
		            <div class="rightColumn">
		                	 <s:textarea cols="50" rows="10" name="actionComments" id="actionComments"></s:textarea>
		            </div>
            </div>
               
   <!-- Buttons -->
   <div class="clear"></div>
   <div class="fields" align="center">
   <center><img id="indicator1" src="<s:url value="/images/ajax-loader_small.gif"/>" alt="Loading..." style="display:none"/></center>
   <ul><li class="submit">
   <div class="type-button">
   <div id="bt" style="display: block;">
   <sj:submit 
	           targets="target1" 
	           clearForm="true"
	           value="  Action  " 
	           effect="highlight"
	           effectOptions="{ color : '#222222'}"
	           effectDuration="5000"
	           button="true"
	           onBeforeTopics="validateAction"
	           onCompleteTopics="closeProgressbar"
	           cssClass="submit"/>
	           &nbsp;&nbsp;
						<sj:submit 
		                     value="Reset" 
		                     button="true"
		                     cssStyle="margin-left: 172px;margin-top: -43px;"
		                     onclick="resetForm('formone');"
			            />
			            &nbsp;&nbsp;
						<sj:a 
		                     value="Back" 
		                     button="true"
		                     cssStyle="margin-left: 303px;margin-top: -76px;"
		                     onclick="viewTicketsPage();"
			            >Back</sj:a>
   </div>
   </div>
   </li>
   </ul>
   </div>
   <div id="target1"></div>
</s:form>
</div>
</div>
</body>
</html>