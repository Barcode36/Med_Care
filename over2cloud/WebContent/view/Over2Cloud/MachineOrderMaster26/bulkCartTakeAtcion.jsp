<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<script type="text/javascript" src="<s:url value="/js/order/feedback.js"/>"></script>
<SCRIPT type="text/javascript">

$.subscribe('reloadData', function(event, data) {
    
 //   ORDERActivityRefresh();
    lodgeOnGridData('1');
    
});
</script>
<script type="text/javascript">

$.subscribe('closeDilog', function(event, data) {
	 $("#takeActionCart").dialog('close');
	
});	
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
    
    if($("#"+hideDiv6).css('display') != 'none')
        $("#"+hideDiv6).hide('slow');
}

//function for change div

function changeDiv(data){

if(data=="Resolved"){
	$("#resolve").show();
	$("#snooze").hide();
	fetchReolveBy();
}
if(data=="Snooze"){
	$("#snooze").show();
	$("#resolve").hide();
}

if(data=="ignore"){
	$("#snooze").hide();
	$("#resolve").hide();
}
if(data!=-1){
	getReason(data);	
}
}

function getReason(dataFor)
{
	var targetid="reason";
	// var id = jQuery("#gridBasicDetails").jqGrid('getGridParam', 'selarrrow');
	// var order = jQuery("#gridBasicDetails").jqGrid('getCell',id,'order');
	 var action=$("#status").val();
	$.ajax({
		type :"post",
		url  : "view/Over2Cloud/CorporatePatientServices/Lodge_Feedback/fetchReason.action?feedId=XR&actiontaken="+action,
		success : function(empData){
		$('#'+targetid+' option').remove();
		$('#'+targetid).append($("<option></option>").attr("value",-1).text("Select Reason"));
    	$(empData).each(function(index)
		{
		   $('#'+targetid).append($("<option></option>").attr("value",this.id).text(this.name));
		});
	    },
	    error : function () {
			alert("Somthing is wrong to get Data");
		}
	});
} 

function fetchReolveBy(){
	var targetid="resolveBy";
	$.ajax({
		type :"post",
		url  : "view/Over2Cloud/CorporatePatientServices/Lodge_Feedback/fetchResolveEmpList.action?moduleName=XR",
		success : function(empData){
		$('#'+targetid+' option').remove();
		$('#'+targetid).append($("<option></option>").attr("value",-1).text("Select Technician"));
    	$(empData).each(function(index)
		{
		   $('#'+targetid).append($("<option></option>").attr("value",this.id).text(this.name));
		});
	    },
	    error : function () {
			alert("Somthing is wrong to get Data");
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
<div style="overflow:auto; height:300px; width: 96%; margin: 1%; border: 1px solid #e4e4e5; -webkit-border-radius: 6px; -moz-border-radius: 6px; border-radius: 6px; -webkit-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); -moz-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); box-shadow: 0 1px 4px rgba(0, 0, 0, .10); padding: 1%;">

    
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
		
		<s:form id="formone1" name="formone" action="actionOnCartFinal"  theme="css_xhtml"  method="post" enctype="multipart/form-data">
			<s:hidden name="complaintidAll" id="complaintidAll" value="%{complaintidAll}"></s:hidden>
		    <center><div style="float:center; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;"><div id="errZone1" style="float:center; margin-left: 7px"></div></div></center>
	     
	  
		 
	
	<div class="clear"></div>
	
	<span id="status1" class="pIds1" style="display: none; ">status#Status#D#,</span>
             <div class="newColumn">
		       <div class="leftColumn">Status:</div>
		            <div class="rightColumn">
		               <span class="needed"></span>
		               
		                <s:select 
		                             id="status"
		                             name="status" 
		                           	 list="#{'Resolved':'Close','Snooze':'Parked','Ignore':'Ignore'}"
		                             headerKey="-1"
		                             headerValue="Select Status"
		                             onchange="changeDiv(this.value)"
		                             cssClass="select"
		                             cssStyle="width:82%"
		                              >
		                              
                  		</s:select>    
		              </div>
		       </div>  
	
		   
		          
	        
		         <div id="resolve" style="display: none;">
		           <span id="resolveBy1" class="reSpIds" style="display: none; ">resolveBy#Resolve By#T#an,</span>
		           <div class="newColumn"> 
			       <div class="leftColumn">Resolve By:</div>
			       <span class="needed"></span>
			            <div class="rightColumn">
			                	
				                  <s:select 
		                             id="resolveBy"
		                             name="resolveBy" 
		                           	 list="{'NO DATA'}"
		                             headerKey="-1"
		                             headerValue="Select Status"
		                             cssClass="select"
		                             cssStyle="width:82%"
		                              >
		                              
                  		</s:select>   
			            </div>
	            </div>
	            
	            </div>
	            
	             <div id="snooze" style="display: none;">
	             <span id="parkedTill1" class="sZpIds" style="display: none; ">parkedTill#Parked Till#Date#,</span>
	            <div class="newColumn"> 
			       <div class="leftColumn">Parked Till:</div>
			       <span class="needed"></span>
			            <div class="rightColumn"><!--
			               <sj:datepicker timepicker="true" timepickerOnly="false" readonly="true" name="parkedTill" id="parkedTill"  showOn="focus" timepickerOnly="true" timepickerGridHour="4" timepickerGridMinute="10" timepickerStepMinute="10"   cssClass="textField" placeholder="Select From Time"/> 	
			           --><sj:datepicker name="parkedTill" id="parkedTill"   timepickerOnly="false"  timepicker="true" timepickerAmPm="false" Class="button"  size="15" displayFormat="dd-mm-yy"  readonly="true"   showOn="focus"   Placeholder="Select Time"/>
			            </div>
	            </div>
	            
	            </div>
	             <span id="parkedTill1" class="reSpIds" style="display: none; ">reason#Reason#D#,</span>
	           <span id="parkedTill1" class="sZpIds" style="display: none; ">reason#Reason#D#,</span>
	             <span id="parkedTill1" class="iGpIds" style="display: none; ">reason#Reason#D#,</span>
	             <div class="newColumn"> 									
			       <div class="leftColumn">Reason:</div>
			      
			            <div class="rightColumn">
			             <span class="needed"></span> 
			               	
				                <s:select 
		                             id="reason"
		                             name="reason" 
		                           	 list="{'NO DATA'}"
		                             headerKey="-1"
		                             headerValue="Select Reason"
		                             cssClass="select"
		                             cssStyle="width:82%"
		                              >
		                              
                  		</s:select>   
			            </div>
	            </div>
	           
              <span id="parkedTill1" class="reSpIds" style="display: none; ">reason#Reason#D#,</span>
	           <span id="parkedTill1" class="sZpIds" style="display: none; ">reason#Reason#D#,</span>
	            <span id="remark1" class="iGpIds" style="display: none; ">remark#Remarks By#T#,</span>
	             <div class="newColumn"> 
			       <div class="leftColumn">Remarks:</div>
			       
			            <div class="rightColumn">
			               <span class="needed"></span> 	
				                 <s:textfield name="remark" id="remark" placeholder="Enter Data" cssClass="textField" />
			            </div>
	            </div>
	           
            
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
	           onBeforeTopics="validateActionCart"
	           onCompleteTopics="reloadData,closeDilog"
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