<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<script type="text/javascript" src="<s:url value="/js/helpdesk/common.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/helpdesk/fbdraft.js"/>"></script>
<SCRIPT type="text/javascript" src="js/feedback/feedback.js"></SCRIPT>
<script type="text/javascript">
function getFeedbackStatus(statusId)
{
	var statusType=$("#"+statusId).val();
	 if(statusType=='High Priority')
	  {
		showHideFeedDiv('highPriorityDiv','snoozeDiv','resolvedDiv','ignoreDiv','reAssign','notedDiv');
	  }
	 else if(statusType=='Snooze')
	  {
		showHideFeedDiv('snoozeDiv','highPriorityDiv','resolvedDiv','ignoreDiv','reAssign','notedDiv');
	  }
	 else if(statusType=='Ignore')
	  {
	      showHideFeedDiv('ignoreDiv','highPriorityDiv','resolvedDiv','snoozeDiv','reAssign','notedDiv');
	  }	  
	 else if(statusType=='Resolved')
	  {
        showHideFeedDiv('resolvedDiv','highPriorityDiv','snoozeDiv','ignoreDiv','reAssign','notedDiv');
	  }
	 else if(statusType=='Re-Assign')
	    {
		   var feedStatus = 'reAllot';
	       showHideFeedDiv('reAssign','highPriorityDiv','resolvedDiv','snoozeDiv','ignoreDiv','notedDiv');
	       $.ajax({
	                type : "post",
	                url : "/over2cloud/view/Over2Cloud/feedback/reAllotTicket.action?feedStatus="+feedStatus+"&dataFor=HDM",
	                success : function(feeddata) {
	                $("#reAssign").html(feeddata);
	                },
	                error: function()
	                {
	                  alert("error");
	                }
	             });
	    }
	 else if(statusType=='Noted')
	 {
		 showHideFeedDiv('notedDiv','reAssign','highPriorityDiv','resolvedDiv','snoozeDiv','ignoreDiv');
	 }
}

function showHideFeedDiv(showDiv,hideDiv1,hideDiv2,hideDiv3,hideDiv4,hideDiv5)
{
	if($("#"+showDiv).css('display') == 'none')
		$("#"+showDiv).show('slow');
	 
	if($("#"+hideDiv1).css('display') != 'none')
		$("#"+hideDiv1).hide('slow');

	if($("#"+hideDiv2).css('display') != 'none')
	$("#"+hideDiv2).hide('slow');
	
    if($("#"+hideDiv3).css('display') != 'none')
	$("#"+hideDiv3).hide('slow');

    if($("#"+hideDiv4).css('display') != 'none')
        $("#"+hideDiv4).hide('slow');
    
    if($("#"+hideDiv5).css('display') != 'none')
        $("#"+hideDiv5).hide('slow');
}

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
function showDoc(div)
{
	$("#"+div).show();
}
</script>
</head>
<body>
<div class="list-icon">
	 <div class="head">Take Action on Ticket </div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div><div class="head"><s:property value="%{#parameters.ticketNo}"></s:property></div> 
</div>
 <div class="clear"></div>
  <div class="clear"></div>
     <div style="width: 100%; center; padding-top: 10px;" align="center">
       <div class="border" style="height: 50%" align="center">
		<s:hidden id="feedStatus" value="%{#parameters.feedStatus}"></s:hidden>
		<s:form id="formone" name="formone" action="actionOnFeedback"  theme="css_xhtml"  method="post" enctype="multipart/form-data">
		    <s:hidden name="ticketno" id="ticketno" value="%{#parameters.ticketNo}"></s:hidden>
		    <s:hidden name="feedid" id="feedid" value="%{#parameters.feedId}"></s:hidden>
		    
		    <center><div style="float:center; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;"><div id="errZone" style="float:center; margin-left: 7px"></div></div></center>
		    
		    
		    <div class="newColumn">
		       <div class="leftColumn"><B>CR No:</B></div>
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
		       <div class="leftColumn"><B>Feedback By :</B></div>
		            <div class="rightColumn">
		                	 <s:property value="%{#parameters.feed_registerby}" />
		            </div>
            </div>
            
            <div>     
		           <span id="normalSpan" class="pIds" style="display: none; ">status#status#D#,</span>
		           <span id="highPrioritySpan" class="hPpIds" style="display: none; ">status#status#D#,</span>
		           <span id="ignoreSpan" class="iGpIds" style="display: none; ">status#status#D#,</span>
		           <span id="snoozeSpan" class="sZpIds" style="display: none; ">status#status#D#,</span>
		           <span id="resolveSpan" class="reSpIds" style="display: none; ">status#status#D#,</span>         
           
	           <div id="highPriorityDiv" style="display:none">
	           <div class="newColumn">
			       <div class="leftColumn">Reason/ RCA:</div>
			            <div class="rightColumn">
			              <span id="highPriority" class="hPpIds" style="display: none; ">hpcomment#Comment#T#sc,</span>
			                	<span class="needed"></span>
				                 <s:textfield name="hpcomment" id="hpcomment" placeholder="Enter High Priority Comment" cssClass="textField" />
			            </div>
	            </div>
	            </div>
           
	           <div id="ignoreDiv" style="display:none">
	           <div class="newColumn">
			       <div class="leftColumn">Ignore Reason/ RCA:</div>
			            <div class="rightColumn">
			            <span id="ignoreSpan" class="iGpIds" style="display: none; ">ignorecomment#Ignore Comment#T#sc,</span>
			                	<span class="needed"></span>
				                 <s:textfield name="ignorecomment" id="ignorecomment" placeholder="Enter Ignore Comment" cssClass="textField" />
			            </div>
	            </div>
	            </div>
	           
	           <div id="notedDiv" style="display:none">
	           <div class="newColumn">
		       <div class="leftColumn">Allot To:</div>
		            <div class="rightColumn">
		                	 <s:property value="%{#parameters.allotto}" />
		            </div>
               </div>
	           <div class="newColumn">
			       <div class="leftColumn">Comments:</div>
			            <div class="rightColumn">
			            <span id="notedSpan" class="nTpIds" style="display: none; ">actionComments#Comment#T#sc,</span>
			                	<span class="needed"></span>
				                 <s:textfield name="actionComments" id="actionComments" placeholder="Enter Comment" cssClass="textField" />
			            </div>
	            </div>
	            
	            <div class="newColumn">
		       <div class="leftColumn">Resolve Doc 1:</div>
		            <div class="rightColumn">
			                 <s:file name="resolveDoc" id="resolveDoc" onchange="showDoc('resolveDocDiv')" rows="5" cols="30"/>
		            </div>
               </div>
               <div class="clear"></div>
               <div id="resolveDocDiv" style="display: none;">
                  <div class="newColumn">
		       <div class="leftColumn">Resolve Doc 2:</div>
		            <div class="rightColumn">
			                 <s:file name="resolveDoc1" id="resolveDoc1"  rows="5" cols="30"/>
		            </div>
               </div>
               </div>
	            </div>
	            
           
             </div>
             
             <div class="clear"></div>
             
                 <div id="snoozeDiv" style="display:none">
               <div>
               <span id="snoozeSpan" class="sZpIds" style="display: none; ">snoozeDate#Snooze Date#Date#,</span> 
               <div class="newColumn">
		       <div class="leftColumn">Snooze Date:</div>
		            <div class="rightColumn">
		                	 <span class="needed"></span>
			                 <sj:datepicker id="snoozeDate" name="snoozeDate" readonly="true" placeholder="Select Snooze Date"  showOn="focus"  displayFormat="dd-mm-yy"  minDate="0" cssClass="textField"/>
		            </div>
               </div>
               <div class="newColumn">
		       <div class="leftColumn">Snooze Time:</div>
		            <div class="rightColumn">
		            <span id="snoozeSpan" class="sZpIds" style="display: none; ">snoozeTime#Snooze Time#Time#,</span> 
		                	 <span class="needed"></span>
			                 <sj:datepicker id="snoozeTime" name="snoozeTime" readonly="true" placeholder="Select Snooze Time"  showOn="focus" timepicker="true" timepickerOnly="true" timepickerGridHour="4" timepickerGridMinute="10" timepickerStepMinute="5" cssClass="textField"/>
		            </div>
               </div>
               </div>
               
               <span id="snoozeSpan" class="sZpIds" style="display: none; ">snoozecomment#Snooze Comment#T#sc,</span>
               <div class="newColumn">
		       <div class="leftColumn">Reason/ RCA:</div>
		            <div class="rightColumn">
		                	<span class="needed"></span>
			                 <s:textfield name="snoozecomment" id="snoozecomment" placeholder="Enter Snooze Comment"  cssClass="textField" />
		            </div>
               </div>
               </div>
               
               <div id="resolvedDiv" style="display:none">
               <div class="newColumn">
		       <div class="leftColumn">Allot To:</div>
		            <div class="rightColumn">
		                	 <s:property value="%{#parameters.allotto}" />
		            </div>
               </div>
               <div class="newColumn">
		       <div class="leftColumn">RCA:</div>
		            <div class="rightColumn">
			                 <s:textarea name="spareused" id="spareused"   rows="10" cols="30"/>
		            </div>
               </div>
               <div class="newColumn">
		       <div class="leftColumn">CAPA:</div>
		        <span id="resolveSpan" class="reSpIds" style="display: none; ">remark#CAPA#T#sc,</span>
		            <div class="rightColumn">
		                	 <span class="needed"></span>
			                 <s:textarea name="remark" id="remark"  rows="10" cols="30"/>
		            </div>
               </div>
               <div class="newColumn">
		       <div class="leftColumn">Resolve Doc 1:</div>
		            <div class="rightColumn">
			                 <s:file name="resolveDoc" id="resolveDoc" onchange="showDoc('resolveDocDiv')" rows="5" cols="30"/>
		            </div>
               </div>
               <div class="clear"></div>
               <div id="resolveDocDiv" style="display: none;">
                  <div class="newColumn">
		       <div class="leftColumn">Resolve Doc 2:</div>
		            <div class="rightColumn">
			                 <s:file name="resolveDoc1" id="resolveDoc1"  rows="5" cols="30"/>
		            </div>
               </div>
               </div>
               </div>
               
               <span id="normalSpan" class="reAssignPIds" style="display: none; ">status#status#D#,deptId#To Department#D#,feedType#Feedback Type#D#,category#Category#D#,subCategory3#Sub Category#T#D,</span>
               <div id="reAssign" style="display:none"></div>
             <div class="newColumn">
		       <div class="leftColumn"><B>Status:</B></div>
		            <div class="rightColumn">
		               <span class="needed"></span>
		                <s:select 
		                             id="status"
		                             name="status" 
		                             list="statusList" 
		                             headerKey="-1"
		                             headerValue="Select Status"
		                             onchange="getFeedbackStatus('status');"
		                             value="%{#parameters.status}"
		                             cssClass="select"
		                             cssStyle="width:82%"
		                              >
                  		</s:select>    
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
