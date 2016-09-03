<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<script type="text/javascript" src="<s:url value="/js/helpdesk/common.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/helpdesk/fbdraft.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/helpdesk/feedback.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/offeringComplaint/actionComplaint.js"/>"></script>
</head>
<body>
<div class="clear"></div>
<div class="middle-content">
<div class="list-icon">
	<div class="head"><s:property value="%{feedStatus}"/> Feedback</div><img alt="" src="images/forward.png" style="margin-top:10px; float: left;"><div class="head"> Action</div>
</div>
<div class="clear"></div>
<div style="overflow:auto; height:420px; width: 96%; margin: 1%; border: 1px solid #e4e4e5; -webkit-border-radius: 6px; -moz-border-radius: 6px; border-radius: 6px; -webkit-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); -moz-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); box-shadow: 0 1px 4px rgba(0, 0, 0, .10); padding: 1%;">

		<s:form id="formone" name="formone" action="actionOnComplaint"  theme="css_xhtml"  method="post" enctype="multipart/form-data">
			<s:hidden name="old_status" id="old_status" value="%{#parameters.feedStatus}"></s:hidden>
		    <s:hidden name="ticketno" id="ticketno" value="%{#parameters.ticketNo}"></s:hidden>
		    <s:hidden name="feedid" id="feedid" value="%{#parameters.feedId}"></s:hidden>
		    <s:hidden id="feedStatus" value="%{#parameters.feedStatus}"></s:hidden>
		     <s:hidden  id="moduleName" value="%{#parameters.moduleName}"></s:hidden>
		    <center><div style="float:center; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;"><div id="errZone" style="float:center; margin-left: 7px"></div></div></center>
		    
		    <div class="newColumn">
		       <div class="leftColumn"><b>Ticket No:</b></div>
		            <div class="rightColumn">
		                	 <s:property value="%{#parameters.ticketNo}"/>
			                 <s:hidden name="ticket_no" id="ticket_no" value="%{#parameters.ticketNo}"   />
		            </div>
            </div>
            
             <div class="newColumn">
		       <div class="leftColumn"><b>Open Date/Time:</b></div>
		            <div class="rightColumn">
		             <s:property value="%{#parameters.openDate}"/>,  <s:property value="%{#parameters.openTime}"/>
			                 <s:hidden name="open_date" id="open_date" value="%{#parameters.openDate}" cssClass="textField" />
		            </div>
            </div>
            
             <div class="newColumn">
		       <div class="leftColumn"><b>Feedback By:</b></div>
		            <div class="rightColumn">
		            		 <s:property value="%{#parameters.feedBY}"/>
		            </div>
            </div>
            
            <div class="newColumn">
		       <div class="leftColumn"><b>Feedback CC:</b></div>
		            <div class="rightColumn">
		            		 <s:property value="%{#parameters.feedCC}"/>
		            </div>
            </div>
            
		    <div class="newColumn">
		       <div class="leftColumn"><b>Relationship Type:</b></div>
		            <div class="rightColumn">
		            		 <s:property value="%{#parameters.feedbackBy}"/>
			                 <s:hidden name="feedback_by" id="feedback_by" value="%{#parameters.feedbackBy}"  cssClass="textField" />
		            </div>
            </div>
            
		    
		    <div class="newColumn">
		       <div class="leftColumn"><b>Organization Name:</b></div>
		            <div class="rightColumn">
		           			  <s:property value="%{#parameters.mobileno}"/>
			                 <s:hidden name="feedback_by_mobno" id="feedback_by_mobno" value="%{#parameters.mobileno}"  cssClass="textField" />
		            </div>
            </div>
		    <div class="newColumn">
		       <div class="leftColumn"><b>Offering Name:</b></div>
		            <div class="rightColumn">
		           			 <s:property value="%{#parameters.emailId}"/>
			                 <s:hidden name="feedback_by_emailid" id="feedback_by_emailid" value="%{#parameters.emailId}"  cssClass="textField" />
		            </div>
            </div>
            
             <div class="newColumn">
		       <div class="leftColumn"><b>Feedback Type:</b></div>
		            <div class="rightColumn">
		             <s:property value="%{#parameters.feedType}"/>
		            </div>
            </div>
            
             <div class="newColumn">
		       <div class="leftColumn"><b>Category:</b></div>
		            <div class="rightColumn">
		             <s:property value="%{#parameters.feedCategory}"/>
		            </div>
            </div>
            <div class="newColumn">
		       <div class="leftColumn"><b>Sub Category:</b></div>
		            <div class="rightColumn">
		             <s:property value="%{#parameters.subCatg}"/>
			                 <s:hidden name="feedback_subcatg" id="feedback_subcatg" value="%{#parameters.subCatg}"  cssClass="textField" />
		            </div>
            </div>
		    <div class="newColumn">
		       <div class="leftColumn"><b>Brief:</b></div>
		            <div class="rightColumn">
		             <s:property value="%{#parameters.feedBreif}"/>
			                 <s:hidden name="feedback_brief" id="feedback_brief" value="%{#parameters.feedBreif}"  cssClass="textField" />
		            </div>
            </div>
            
           
            <s:hidden name="open_time" id="open_time" value="%{#parameters.openTime}" cssClass="textField" />
            
             <div class="newColumn">
		       <div class="leftColumn">Action:</div>
		            <div class="rightColumn">
		               <span class="needed"></span>
		                <s:select 
		                             id="status"
		                             name="status" 
		                             list="statusList" 
		                             headerKey="-1"
		                             headerValue="Select Status"
		                             onchange="getFeedbackStatus('status','resolver');getRCAName(this.value,'moduleName','rcaName','feedid')"
		                             value="%{#parameters.status}"
		                             cssClass="select"
		                             cssStyle="width:82%"
		                              >
		                              
                  		</s:select>    
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
			              <span id="highPriority" class="hPpIds" style="display: none; ">hpcomment#Comment#T#an,</span>
			                	<span class="needed"></span>
				                 <s:textfield name="hpcomment" id="hpcomment" placeholder="Enter High Priority Comment" cssClass="textField" />
			            </div>
	            </div>
	            </div>
           
	           <div id="ignoreDiv" style="display:none">
	           <div class="newColumn">
			       <div class="leftColumn">Reason/ RCA:</div>
			            <div class="rightColumn">
			            <span id="ignoreSpan" class="iGpIds" style="display: none; ">ignorecomment#Ignore Comment#T#an,</span>
			                	<span class="needed"></span>
				                 <s:textfield name="ignorecomment" id="ignorecomment" placeholder="Enter Ignore Comment" cssClass="textField" />
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
               
               <span id="snoozeSpan" class="sZpIds" style="display: none; ">snoozecomment#Snooze Comment#T#an,</span>
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
		                	 <span class="needed"></span>
			                 <s:textfield name="allotto" id="allotto" value="%{#parameters.allotto}" readonly="true"  cssClass="textField" />
		            </div>
               </div>
               
               <div class="newColumn">
               <span id="resolveSpan" class="reSpIds" style="display: none; ">resolver#Resolver#D#,</span> 
		       <div class="leftColumn">Resolve By:</div>
		            <div class="rightColumn">
		                	<span class="needed"></span>
		                        <s:select 
		                                  id="resolver"
		                                  name="resolver"
		                                  list="{'No Data'}"
		                                  headerKey="-1"
		                                  headerValue="Select Resolver" 
		                                  cssClass="select"
		                                  cssStyle="width:82%"
		                                  >
		                        </s:select>
		            </div>
               </div>
                <div class="clear"></div>
               <div class="newColumn">
		       <div class="leftColumn">Action Taken:</div>
		        <span id="resolveSpan" class="reSpIds" style="display: none; ">remark#Remark#T#sc,</span>
		            <div class="rightColumn">
		                	 <span class="needed"></span>
			                 <s:textfield name="remark" id="remark" maxlength="1000" cssClass="textField" />
		            </div>
               </div>
               <div class="newColumn">
		       <div class="leftColumn">Resources Used:</div>
		            <div class="rightColumn">
			                 <s:textfield name="spareused" id="spareused"  cssClass="textField" />
		            </div>
               </div>
                <div class="newColumn">
		       <div class="leftColumn">RCA:</div>
		       
		            <div class="rightColumn">
		                	
			                <s:select 
                                 id="rcaName"
                                 name="rca"
                                 list="{'No Data'}"
                                 headerKey="-1"
                                 headerValue="Select RCA" 
                                 cssClass="select"
                                 cssStyle="width:82%"
                                 >
		                    </s:select>
		            </div>
               </div>
               <div class="newColumn">
		       <div class="leftColumn">CAPA:</div>
		       <span id="resolveSpan" class="reSpIds" style="display: none; ">capa#CAPA#T#an,</span>
		            <div class="rightColumn">
			                 <s:textfield name="capa" id="capa"  cssClass="textField" />
		            </div>
               </div>
               
               </div>
               
               <span id="normalSpan" class="reAssignPIds" style="display: none; ">status#status#D#,deptname3#todept#D#,subdeptname3#tosubdept#D#,fbType3#feedTypeId#D#,reAssignRemark#reAssignRemark#T#an,</span>
               <div id="reAssign" style="display:none"></div>
            
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
	           value="  Save  " 
	           effect="highlight"
	           effectOptions="{ color : '#222222'}"
	           effectDuration="5000"
	           button="true"
	           onBeforeTopics="validateAction"
	           cssStyle="margin-left: -30px;"
			   />
	            <sj:a cssStyle="margin-left: 203px;margin-top: -45px;" button="true" href="#" onclick="resetForm('formone');">Reset</sj:a>
	            <sj:a cssStyle="margin-left: 5px;margin-top: -45px;" button="true" href="#" onclick="viewPendingComplaint();">View</sj:a>
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