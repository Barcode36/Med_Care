<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<script type="text/javascript" src="<s:url value="/js/helpdesk/common.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/helpdesk/fbdraft.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/helpdesk/feedback.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/helpdesk/actionValidation.js"/>"></script>
<script type="text/javascript">
function setReasonVal(statusId)
{
	var statusType=$("#"+statusId).val();
	var divId;
	if(statusType=='High Priority')
	{
		divId = 'hpcomment';
	}
	else if(statusType=='Snooze')
	{
		divId = 'snoozecomment';
	}
	else if(statusType=='Resolved')
	{
		divId = 'remark';
	}
	else if(statusType=='Ignore')
	{
		divId = 'ignorecomment';
	}
	else if(statusType=='Re-Opened')
	{
		divId = 'reallotedcomment';
	}
	else if(statusType=='Re-Assign')
	{
		divId = 'reasonReassign';
	}
	
	 $.ajax({
         type : "post",
         async:false,
         url : "/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Lodge_Feedback/fetchReasonData.action?feedStatus="+statusType,
         success : function(feeddata) 
         {
      	   $('#'+divId+' option').remove();
      	   console.log(feeddata);
	      	 if(statusType=='Snooze')
	     	{
	      		 $('#'+divId).append($("<option></option>").attr("value",-1).text("Select Parked"));
	     	}
	     	else
	     	{
	     		 $('#'+divId).append($("<option></option>").attr("value",-1).text("Select "+statusType));
	     	}
         	$(feeddata).each(function(index)
     		{
     		   $('#'+divId).append($("<option></option>").attr("value",this.id).text(this.name));
     		});
     	    },
     	    error : function () {
     			alert("Somthing is wrong to get Data");
     		}
});
}
function getFeedbackStatus(statusId)
{
	var statusType=$("#"+statusId).val();
	 if(statusType=='High Priority')
	  {
		 $("#fromkrview").hide('slow');
		showHideFeedDiv('highPriorityDiv','snoozedDiv','resolvedDiv','ignoredDiv','reAssign','seekDiv','reOpenedDiv');
	  }
	 else if(statusType=='Snooze')
	  {
		 $("#fromkrview").hide('slow');
		showHideFeedDiv('snoozedDiv','highPriorityDiv','resolvedDiv','ignoredDiv','reAssign','seekDiv','reOpenedDiv');
	  }
	 else if(statusType=='Ignore')
	  {
		 $("#fromkrview").hide('slow');
	      showHideFeedDiv('ignoredDiv','highPriorityDiv','resolvedDiv','snoozedDiv','reAssign','seekDiv','reOpenedDiv');
	  }	  
	 else if(statusType=='Re-Opened')
	  {
		 $("#fromkrview").hide('slow');
		    var	feedId=$("#feedid").val();
		      	showHideFeedDiv('reOpenedDiv','highPriorityDiv','resolvedDiv','snoozedDiv','reAssign','seekDiv','ignoredDiv');
	    	 $.ajax({
		           type : "post",
		           url : "/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Lodge_Feedback/getReAlloted.action?feedid="+feedId,
		           success : function(feeddata) {
		        	  /*  $('#reallotedto_widget option').remove();
		        	  $('#reallotedto_widget').append($("<option></option>").attr("value",-1).text("Select Employee")); */
		       	    
		           	$(feeddata).each(function(index)
		       		{
		               	 $("#allotedTo").val(this.id);
		               	 $("#allotedToName").val(this.name);
		       		  /*  $('#reallotedto_widget').append($("<option></option>").attr("value",this.id).text(this.name)); */
		       		});
		       	    },
		       	    error : function () {
		       			alert("Somthing is wrong to get Data");
		       		}
		 });
	  }	  
	 else if(statusType=='Resolved')
	  {
		 $("#fromkrview").show('slow');
	    var	feedId=$("#feedid").val();
	    var sel_val = $("#allotto").val();
        showHideFeedDiv('resolvedDiv','highPriorityDiv','snoozedDiv','ignoredDiv','reAssign','seekDiv','reOpenedDiv');
        $.ajax({
	           type : "post",
	           url : "/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Lodge_Feedback/getResolver.action?feedid="+feedId,
	           success : function(feeddata) {
            $("#resolverAjaxDiv").html(feeddata);
            $("#resolver option[value='"+sel_val+"']").attr("selected", "selected");
	         },
	   error: function() {
         alert("error");
     }
	 });
	}
  else if(statusType=='Re-Assign')
    {
	  $("#fromkrview").hide('slow');
	  var	feedId=$("#feedid").val();
	  
	    var feedStatus = 'Re-Assign';
         showHideFeedDiv('reAssign','highPriorityDiv','resolvedDiv','snoozedDiv','ignoredDiv','seekDiv','reOpenedDiv');
             $.ajax({
             type : "post",
             async:false,
             url : "/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Lodge_Feedback/reAllotTicket.action?feedStatus="+feedStatus+"&dataFor=HDM&feedid="+feedId,
             success : function(feeddata) {
             $("#reAssign").html(feeddata);
            },
            error: function()
             {
               alert("error");
             }
         });
    }
  else if(statusType=='hold')
	 {
	  $("#fromkrview").hide('slow');
	  	showHideFeedDiv('seekDiv','reAssign','highPriorityDiv','resolvedDiv','snoozedDiv','ignoredDiv','reOpenedDiv'); 
  		var	feedId=$("#feedid").val();
  		$("#seekDiv").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
     	 $.ajax
      	({
	          type : "post",
	          url  : "/over2cloud/view/Over2Cloud/SeekApproval/beforeSeekApproval.action?complaintId="+feedId+"&moduleName=HDM",
	          success : function(htmlData)
	          {
	          	$("#seekDiv").html(htmlData);
	          },
	          error : function()
	          {
		            alert("Error Seek Approval Data Fetch");
	          }
      });
	}
}

function showHideFeedDiv(showDiv,hideDiv1,hideDiv2,hideDiv3,hideDiv4,hideDiv5,hideDiv6)
{
	//if($("#"+showDiv).css('display') == 'none')
		$("#"+showDiv).show();
	 
	//if($("#"+hideDiv1).css('display') != 'none')
		$("#"+hideDiv1).hide();

	//if($("#"+hideDiv2).css('display') != 'none')
	$("#"+hideDiv2).hide();
	
   // if($("#"+hideDiv3).css('display') != 'none')
	$("#"+hideDiv3).hide();

  //  if($("#"+hideDiv4).css('display') != 'none')
        $("#"+hideDiv4).hide();
    
   // if($("#"+hideDiv5).css('display') != 'none')
        $("#"+hideDiv5).hide();
    
  //  if($("#"+hideDiv6).css('display') != 'none')
        $("#"+hideDiv6).hide();
}
function KR() 
{
	var subCatId=$("#subCat").val();
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Configure_Check_List/ViewCompletionTipKRAction.action?dataId="+subCatId,
	    success : function(data) 
	    {
			$("#krCompletionTip").dialog({title: 'Completion Tip',width: 300,height: 200});
			$("#krCompletionTip").dialog('open');
			$("#krCompletionTip").html(data);
		},
	    error: function()
	    {
	        alert("error");
	    }
	 });
}


</script>
</head>
<body>
<div class="clear"></div>
<div class="middle-content">
<%-- <div class="list-icon">
	<div class="head">Take Action</div> <img alt="" src="images/forward.png" style="margin-top:10px; float: left;"> <div class="head">Ticket ID: <s:property value="%{ticket_no}"/>, Opened On: <s:property value="%{open_date}"/> & <s:property value="%{open_time}"/></div>
</div> --%>
<div class="clear"></div>
<div style="overflow:auto; height:460px; width: 96%; margin: 1%; border: 1px solid #e4e4e5; -webkit-border-radius: 6px; -moz-border-radius: 6px; border-radius: 6px; -webkit-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); -moz-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); box-shadow: 0 1px 4px rgba(0, 0, 0, .10); padding: 1%;">

    
<sj:dialog
          id="krCompletionTip"
          showEffect="slide"
          hideEffect="explode"
          autoOpen="false"
          title="Take Action on Feedback Status"
          modal="true"
          closeTopics="closeEffectDialog"
          width="700"
          height="350"
          >
</sj:dialog>
		<s:hidden  id="subCat" value="%{subCatg}"></s:hidden>
		<s:form id="formone1" name="formone" action="actionOnFeedback1"  theme="css_xhtml"  method="post" enctype="multipart/form-data">
			<s:hidden name="old_status" id="old_status" value="%{#parameters.feedStatus}"></s:hidden>
		    <s:hidden name="ticketno" id="ticketno" value="%{#parameters.ticketNo}"></s:hidden>
		    <s:hidden name="feedid" id="feedid" value="%{#parameters.feedId}"></s:hidden>
		    <s:hidden id="feedStatus" value="%{#parameters.feedStatus}"></s:hidden>
		    <center><div style="float:center; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;"><div id="errZone1" style="float:center; margin-left: 7px"></div></div></center>
	     
	  <table border="1" width="100%" style="border-collapse: collapse;">
	   
				<tr bgcolor="#D8D8D8" style="height: 25px">
					<s:iterator value="dataMap">
						<s:if test="key=='From Department' || key=='Feedback By' || key=='Mobile No.'">
							<td width="10%"><b><s:property value="%{key}"/>: <b></td>
							<td width="10%"><s:property value="%{value}"/></td>
						</s:if>
					</s:iterator>
				</tr>
				
				<tr  style="height: 25px">
					<s:iterator value="dataMap">
						<s:if test="key=='To Department' || key=='Allotted To' || key=='Mobile No'">
							<td width="10%"><b><s:property value="%{key}"/>: <b></td>
							<td width="10%"><s:property value="%{value}"/></td>
						</s:if>
					</s:iterator>
				</tr>
				
				<tr bgcolor="#D8D8D8" style="height: 23px">
					<s:iterator value="dataMap">
						<s:if test="key=='Category' || key=='Sub-Category' || key=='Brief'">
							<td width="10%"><b><s:property value="%{key}"/>: <b></td>
							<td width="10%"><s:property value="%{value}"/></td>
						</s:if>
					</s:iterator>
				</tr>
				
				<tr style="height: 25px">
					<s:iterator value="dataMap">
						<s:if test=" key=='Current Status' || key == 'Current Level' ||  key == 'Lapse Time' ">
							<td width="10%"><b><s:property value="%{key}"/>: <b></td>
							<td width="10%"><s:property value="%{value}"/></td>
						</s:if>
					</s:iterator>
				</tr>
				
				<tr bgcolor="#D8D8D8" style="height: 25px">
					<s:iterator value="dataMap">
						<s:if test=" key=='Next Escalation On' || key=='Next Escalation To'">
							<td width="10%"><b><s:property value="%{key}"/>: <b></td>
							<td width="10%"><s:property value="%{value}"/></td>
						</s:if>
					</s:iterator>
					<td width="10%"> </td>
					<td width="10%"> </td>
					<td width="10%"> </td>
					<td width="10%"> </td>
				</tr>
				
				 
		</table>
		<%--  <div id="fromkrview" style="display:none">
    
		 <br>
		<s:if test="%{checkListMap.size>0}">
   			<strong>Completion Tip:</strong>
 	    			<div  style="margin-left: 96px;margin-top: -14px;">
 	    			<s:iterator value="checkListMap" var="var" status="status">
 	    			<tr>
 	    			 	<s:checkbox theme="simple"  name="checkid" id="%{#status.count}" value="%{key}" fieldValue="%{key}"/><s:property value="%{value}"/> <br>
 	    			 </tr>
 	    		 </s:iterator>
 	    	</div>
 	   </s:if>
 	     </div> --%>
 	     <div class="clear"></div>
       
      <!--  <div class="newColumn">
       <div class="leftColumn">KR&nbsp;Name:</div>
       <div class="rightColumn">
       <img id="krName" alt="Completion List" src="images/share.jpg" height="25" width="50" onclick="KR();">
       </div>
       </div> -->
	 <span id="highPrioritySpan" class="pIds1" style="display: none; ">status#Status#D#,</span>
		    <div class="clear"></div>
             <div class="newColumn">
		       <div class="leftColumn">Status:</div>
		            <div class="rightColumn">
		               <span class="needed"></span>
		                <s:select 
		                             id="status"
		                             name="status" 
		                             list="statusList" 
		                             headerKey="-1"
		                             headerValue="Select Status"
		                             onchange="getFeedbackStatus('status');Reset('.reSpIds');setReasonVal('status')"
		                             value="%{#parameters.status}"
		                             cssClass="select"
		                             cssStyle="width:82%"
		                              >
		                              
                  		</s:select>    
		              </div>
		       </div>       
		           <span id="highPrioritySpan" class="hPpIds" style="display: none; ">status#Status#D#,</span>
		           <span id="ignoreSpan" class="iGpIds" style="display: none; ">status#Status#D#,</span>
		           <span id="snoozeSpan" class="sZpIds" style="display: none; ">status#Status#D#,</span>
		           <span id="resolveSpan" class="reSpIds" style="display: none; ">status#Status#D#,</span>         
               <span id="reopened" class="reOpIds" style="display: none; ">status#Status#D#,</span>         
           
	           <div id="highPriorityDiv" style="display:none">
	            <div class="newColumn">
			       <div class="leftColumn">Reason:</div>
			            <div class="rightColumn">
			              <span id="highPriority" class="hPpIds" style="display: none; ">hpcomment#Comment#T\D#,</span>
			                	<span class="needed"></span>
				                 <s:select name="hpcomment" id="hpcomment" list="{'No Data'}" headerKey="-1"
		                             headerValue="Select High Priority" cssClass="select" />
			            </div>
	            </div>
	            
	            <div class="newColumn"> 
			       <div class="leftColumn">Remarks:</div>
			            <div class="rightColumn">
			                	
				                 <s:textfield name="hpremark" id="hpremark12" placeholder="Enter Remark" cssClass="textField" />
			            </div>
	            </div>
	            
	            </div>
           
	           <div id="ignoredDiv" style="display:none">
	           <div class="newColumn">
			       <div class="leftColumn">Reason:</div>
			            <div class="rightColumn">
			            <span id="ignoreSpan" class="iGpIds" style="display: none; ">ignorecomment#Ignore Comment#D#,</span>
			                	<span class="needed"></span>
				                 <s:select name="ignorecomment" id="ignorecomment"   list="{'No Data'}" headerKey="-1"
		                             headerValue="Select Ignore Reason" cssClass="select" />
			            </div>
	            </div>
	             <div class="newColumn">
			       <div class="leftColumn">Remarks:</div>
			            <div class="rightColumn">
			            
			                	
				                 <s:textfield name="ignoreremark" id="ignoreremark" placeholder="Enter Remark" cssClass="textField" />
			            </div>
	            </div>
	            </div>
           
             
              <div id="reOpenedDiv" style="display:none">
                   
	             <div class="newColumn">
			       <div class="leftColumn">Reason:</div>
			            <div class="rightColumn">
			              <span id="reopened" class="reOpIds" style="display: none; ">reallotedcomment#Comment#D#,</span>
			                	<span class="needed"></span>
				              <s:select name="reallotedcomment" id="reallotedcomment"   list="{'No Data'}" headerKey="-1"
		                             headerValue="Select ReAllot Reason" cssClass="select" />
			            </div>
	            </div>
	             
	            <div class="newColumn">
			       <div class="leftColumn">Alloted To:</div>
			            <div class="rightColumn">
			                   	<span class="needed"></span>
				                 <s:hidden name="reallotedto" id="allotedTo"  readonly="true" cssClass="textField" />
				                 <s:textfield  id="allotedToName"  readonly="true" cssClass="textField" />
			            </div>
	            </div>
	             <div class="newColumn">
		       <div class="leftColumn">Remark:</div>
		            <div class="rightColumn">
			                 <s:textfield name="reopenremark" id="reopenremark" placeholder="Enter Remark" cssClass="textField" />
		            </div>
               </div>
	        
	            </div>
           
               <div id="snoozedDiv" style="display:none">
               <span id="snoozeSpan" class="sZpIds" style="display: none; ">snoozeDate#Snooze Date#Date#,</span> 
               <div class="newColumn">
		       <div class="leftColumn">Parked Date:</div>
		            <div class="rightColumn">
		                	 <span class="needed"></span>
			                 <sj:datepicker id="snoozeDate" name="snoozeDate"   readonly="true" placeholder="Select Parked Date"  showOn="focus"  displayFormat="dd-mm-yy"  minDate="0" cssClass="textField"/>
		            </div>
               </div>
               <div class="newColumn">
		       <div class="leftColumn">Parked Time:</div>
		            <div class="rightColumn">
		            <span id="snoozeSpan" class="sZpIds" style="display: none; ">snoozeTime#Snooze Time#Time#,</span> 
		                	 <span class="needed"></span>
			                 <sj:datepicker id="snoozeTime" name="snoozeTime"  readonly="true" placeholder="Select Parked Time"  showOn="focus" timepicker="true" timepickerOnly="true" timepickerGridHour="4" timepickerGridMinute="10" timepickerStepMinute="5" cssClass="textField"/>
		            </div>
               </div>
               
               <span id="snoozeSpan" class="sZpIds" style="display: none; ">snoozecomment#Snooze Comment#D#,</span>
               <div class="newColumn">
		       <div class="leftColumn">Reason:</div>
		            <div class="rightColumn">
		                	<span class="needed"></span>
			                 <s:select name="snoozecomment"   id="snoozecomment" list="{'No Data'}" headerKey="-1"
		                             headerValue="Select Parked Reason" cssClass="select" />
		            </div>
               </div>
               
                <div class="newColumn">
		       <div class="leftColumn">Remark:</div>
		            <div class="rightColumn">
			                 <s:textfield name="snoozeremark" id="snoozeremark" placeholder="Enter Remark" cssClass="textField" />
		            </div>
               </div>
               </div>
               
               <div id="resolvedDiv" style="display:none">
               <s:hidden  id="allotto" value="%{allotto}" readonly="true"  cssClass="textField" />
               <div class="newColumn">
               <span id="resolveSpan" class="reSpIds" style="display: none; ">resolver#Resolver#D#,remark#Reason#D#,</span> 
		       <div class="leftColumn">Resolve By:</div>
		            <div class="rightColumn">
		                	<span class="needed"></span>
			                 <div id="resolverAjaxDiv">
		                        <s:select 
		                                  id="resolver"
		                                  name="resolver"
		                                  list="{'No Data'}"
		                                  headerKey="-1"
		                                  headerValue="Select Resolver" 
		                                  cssClass="select"
		                                  cssStyle="width:82%"
		                                  Onchange="Reset('.reSpIds')">
		                        </s:select>
                   </div>
		            </div>
               </div>
               <div class="newColumn">
		       <div class="leftColumn">Reason:</div>
		       
		            <div class="rightColumn">
		                	 <span class="needed"></span>
			                 <s:select name="remark" id="remark" list="{'No Data'}" headerKey="-1"
		                             headerValue="Select Action Taken" cssClass="select" Onchange="Reset('.reSpIds');"/> 
		            </div>
               </div>
               <div class="newColumn">
		       <div class="leftColumn">Remark:</div>
		            <div class="rightColumn">
		            <span class="needed"></span>
			                 <s:textfield name="spareused" id="spareused" placeholder="Enter Remark" cssClass="textField" />
		            </div>
               </div>
               </div>
               
               <span id="normalSpan" class="reAssignPIds" style="display: none; ">status#status#D#,</span>
               <div id="reAssign" style="display:none"></div>
               <div id="seekDiv" style="display:none"></div>
            
   <!-- Buttons -->
   <div class="clear"></div>
   <div class="clear"></div>
   <div class="fields" align="center">
   <center><img id="indicator1" src="<s:url value="/images/ajax-loader_small.gif"/>" alt="Loading..." style="display:none"/></center>
   <ul><li class="submit">
   <div class="type-button">
   <div id="bt" style="display: block;">
   <sj:submit 
   				id ="actionId"
	           targets="target1" 
	           clearForm="true"
	           value="  Save  " 
	           effect="highlight"
	           effectOptions="{ color : '#222222'}"
	           effectDuration="5000"
	           button="true"
	           onBeforeTopics="validateAction"
	                   
	             cssStyle="margin-left: -30px;"
			   />
	            <sj:a cssStyle="margin-left: 203px;margin-top: -45px;" button="true" href="#" onclick="resetForm('formone');Reset('.reSpIds');">Reset</sj:a>
	            <sj:a cssStyle="margin-left: 5px;margin-top: -45px;" button="true" href="#" onclick="closeMe();">Back</sj:a>
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