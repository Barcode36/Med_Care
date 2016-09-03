<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<script type="text/javascript" src="<s:url value="/js/order/feedback.js"/>"></script>
<SCRIPT type="text/javascript">

$.subscribe('reload', function(event, data) {
    
    ORDERActivityRefresh();
    
});


</SCRIPT>
<script type="text/javascript">


	$(document).ready(function () {
		  //to get list of all department
		 
		  var ordtype = $("#ordType").val();
		 		if(ordtype.indexOf("XR")==-1){
				$("#tech").hide();
				$("#tech").val('NA');
			}
else{
$("#docUltra").hide();}
		    	 });

function setReasonVal(statusId)
{
	var statusType=$("#"+statusId).val();
	///alert(statusType);
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
	//alert(statusType);
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

function getTechnician(data){

	var ordtype = $("#ordType").val();
	//$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/MachineOrder/fetchMachingMapping.action?slNo="+data+"&ordType="+ordtype,
	    async:false,
		success : function(empData)
		{
			if(empData.length != 0)
			{


				   
				        if(ordtype.indexOf('XR')> -1)
					        {
							$("#technician").val(empData[0].empName);
							$("#technicianId").val(empData[0].empID);
							}
							else
							{
								 
								for(var i=0;i<empData.length;i++)
									{
								 		if(empData[i].dept=='Technician')
										{
											$("#technician").val(empData[i].techName);
										}
									else if(empData[i].dept=='Doctor'  )
										{
										$("#doc").val(empData[i].docName);
										$("#technicianId").val(empData[i].docId);
										}
									else if(empData[i].dept=='CT Scan Doctor'  )
									{
									$("#doc").val(empData[i].docName);
									$("#technicianId").val(empData[i].docId);
									}
									else if(empData[i].dept=='Echo Doctor'  )   //aarif3
									{
									$("#doc").val(empData[i].docName);
									$("#technicianId").val(empData[i].docId);
									}
										
									else{
										errZone1.innerHTML="<div class='user_form_inputError2'>May Be No Doctor/Technician Is Mapped To The Current Shift </div>";
										}
									
									}
							}
			}
			else
			{
				$("#technician").val("");
				$("#technicianId").val("");
				$("#doc").val("");
				indicateMap();
				
			}
	    },
	    error : function () {
			alert("Somthing is wrong to get Data");
		}
	});
}

function indicateMap(){
	alert("No one is map for this machine !!");
}
function closeback(){
	$("#takeActionGrid").dialog('close');
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/CorporatePatientServices/Activity_Board/viewActivityBoardHeaderCPS.action",
	    success : function(feeddraft) {
       $("#"+"data_part").html(feeddraft);
	},
	   error: function() {
            alert("error");
        }
	 });
}

</script>
</head>
<body onload="StopRefresh();">
<div class="clear"></div>
<div class="middle-content">
<%-- <div class="list-icon">
	<div class="head">Take Action</div> <img alt="" src="images/forward.png" style="margin-top:10px; float: left;"> <div class="head">Ticket ID: <s:property value="%{ticket_no}"/>, Opened On: <s:property value="%{open_date}"/> & <s:property value="%{open_time}"/></div>
</div> --%>
<div class="clear"></div>
<div style="overflow:auto; height:416px; width: 96%; margin: 1%; border: 1px solid #e4e4e5; -webkit-border-radius: 6px; -moz-border-radius: 6px; border-radius: 6px; -webkit-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); -moz-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); box-shadow: 0 1px 4px rgba(0, 0, 0, .10); padding: 1%;">

    
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
		<s:form id="formone1" name="formone" action="actionOnOrderLodge"  theme="css_xhtml"  method="post" enctype="multipart/form-data">
			<s:hidden name="old_status" id="old_status" value="%{#parameters.feedStatus}"></s:hidden>
		    <s:hidden name="ticketno" id="ticketno" value="%{#parameters.ticketNo}"></s:hidden>
		    <s:hidden name="feedid" id="feedid" value="%{#parameters.feedId}"></s:hidden>
		    <s:hidden id="feedStatus" name="feedStatus" value="%{#parameters.feedStatus}"></s:hidden>
		    <s:hidden id="technicianId" name="technicianId" value=""></s:hidden>
		    <s:iterator value="dataMap">
						<s:if test="key=='Order Name' ">
							
							<s:hidden id="ordType" value="%{value}"></s:hidden>
						</s:if>
					</s:iterator>
		    
		    
		    <center><div style="float:center; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;"><div id="errZone1" style="float:center; margin-left: 7px"></div></div></center>
	     
	  <table border="1" width="100%" style="border-collapse: collapse;">
	   
				<tr bgcolor="#D8D8D8" style="height: 25px">
					<s:iterator value="dataMap">
						<s:if test="key=='Order Name' || key=='Order Type' || key=='Order By'">
							<td width="10%"><b><s:property value="%{key}"/>: <b></td>
							<td width="10%"><s:property value="%{value}"/></td>
						</s:if>
					</s:iterator>
				</tr>
				
				<tr  style="height: 25px">
					<s:iterator value="dataMap">
						<s:if test="key=='UHID' || key=='Patient Name' || key=='Room No'">
							<td width="10%"><b><s:property value="%{key}"/>: <b></td>
							<td width="10%"><s:property value="%{value}"/></td>
						</s:if>
					</s:iterator>
				</tr>
				
				<tr bgcolor="#D8D8D8" style="height: 25px">
					<s:iterator value="dataMap">
						<s:if test="key=='Nursing Unit' || key=='Priority' || key=='Adm. Doctor'">
							<td width="10%"><b><s:property value="%{key}"/>: <b></td>
							<td width="10%"><s:property value="%{value}"/></td>
						</s:if>
					</s:iterator>
				</tr>
				
				<tr  style="height: 25px">
					<s:iterator value="dataMap">
						<s:if test="key=='Adm. Spec.' || key=='Order Date' || key=='Order Time'">
							<td width="10%"><b><s:property value="%{key}"/>: <b></td>
							<td width="10%"><s:property value="%{value}"/></td>
						</s:if>
					</s:iterator>
				</tr>
				
		 		 
		</table>
		 
	
		    <div class="clear"></div>
		       <div class="clear"></div>
   <div class="clear"></div>
   <span id="assignTo1" class="pIds1" style="display: none; ">assignTo#Assign Machine#D#,</span>
             <div class="newColumn">
		       <div class="leftColumn">Assign Machine:</div>
		            <div class="rightColumn">
		               <span class="needed"></span>
		                <s:select 
		                             id="assignTo"
		                             name="assignTo" 
		                          	list=	"machineSerialList"
	       							headerKey="-1"
		                             headerValue="Select Machine"
		                             cssClass="select"
		                             cssStyle="width:82%"
		                             onchange="getTechnician(this.value);"
		                              >
		                              
                  		</s:select>    
		              </div>
		       </div>       
		         
		         
		         <div class="newColumn" id="tech">
		       <div class="leftColumn">Technician:</div>
		       
		            <div class="rightColumn">
		            <span class="needed"></span>
		               
		                <s:textfield name="technician" id="technician" placeholder="Enter Brief" cssClass="textField" />    
		              </div>
		       </div>    
		         <div id="docUltra" style="display: block;">
		         <div  class="newColumn">
		       <div class="leftColumn">Doctor:</div>
		            <div class="rightColumn">
		               <span class="needed"></span>
		                <s:textfield name="doc" id="doc" placeholder="Enter Brief" cssClass="textField" />  
		              </div>
		       </div> 
		       </div> 
		         
		         
	            <div class="newColumn"> 
			       <div class="leftColumn">Brief:</div>
			            <div class="rightColumn">
			                	
				                 <s:textfield name="brief" id="brief" placeholder="Enter Brief" cssClass="textField" />
			            </div>
	            </div>
	            
	            
	            <!-- counter details -->
	            
	            

	<div >
	<div class="contentdiv-small" style="overflow: hidden;  height:132px; width:45%; float: LEFT; margin-left:5%; margin-right:1px;   border-color: black" >
    <div class="clear"></div>
           <div style="height:14px; margin-bottom:2px;" id='1stBlock'></div>
                  <div style="height:8%; margin-bottom:5px; margin-top:-16px;" align="center"><B> Machine Counter</B> </div>
                       <table  align="center" border="0" width="100%" height="10%" style="border-style:dotted solid;">
                               <tbody>
                                    <tr bgcolor="black" bordercolor="black" >
	                                       <td align="center" width="20%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Machine</b></font></td>
	                                       <td align="center" width="20%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Technician</b></font></td>
	                                       <td align="center" width="20%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Counter</b></font></td>
	                                </tr>
	                                
	                                
	                                <s:iterator value="ordCount" var="listVal1" status="counter1">
 									<tr>  <td align="center" width="12%"><s:property value="%{#listVal1[3]}" /></td>
                                           <td align="center" width="5%"><s:property value="%{#listVal1[1]}" /></td>
                                           <td align="center" width="5%"><s:property value="%{#listVal1[0]}" /></td>
                                           
                                    </tr>
                                   
                                   </s:iterator>
                                    
                                    
                                   
                                    
                                    
                               </tbody>
                       </table>
                 </div>
	
	
		
		</div>
	   
	   
	     
   
   <div >
   
	<div class="contentdiv-small" style="overflow: hidden;  height:132px; width:45%; float: RIghT; margin-left:1px; margin-right:1px;   border-color: black" >
    <div class="clear"></div>
           <div style="height:14px; margin-bottom:2px;" id='1stBlock'></div>
                  <div style="height:8%; margin-bottom:5px; margin-top:-16px;" align="center"><B> Machine Cart Counter </B> </div>
                       <table  align="center" border="0" width="100%" height="10%" style="border-style:dotted solid;">
                               <tbody>
                                    <tr bgcolor="black" bordercolor="black" >
	                                       <td align="center" width="20%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Machine</b></font></td>
	                                       <td align="center" width="20%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Technician</b></font></td>
	                                       <td align="center" width="20%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Counter</b></font></td>
	                                </tr>
	                                
	                                <s:iterator value="cartCount" var="listVal1" status="counter1">
	                                
 									<tr>  <td align="center" width="12%"><s:property value="%{#listVal1[3]}" /></td>
                                           <td align="center" width="5%"><s:property value="%{#listVal1[1]}" /></td>
                                           <td align="center" width="5%"><s:property value="%{#listVal1[0]}" /></td>
                                           
                                    </tr>
                                   
                                   </s:iterator>   
                                    
                                    
                                   
                                    
                                    
                               </tbody>
                       </table>
                 </div>
	
	
		</div>
		    
	            
	     
   <!-- Buttons -->
   <div class="clear"></div>
   <div class="clear"></div>
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
	           value="  Assign  " 
	           effect="highlight"
	           effectOptions="{ color : '#222222'}"
	           effectDuration="5000"
	           button="true"
	           onCompleteTopics="reload"
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