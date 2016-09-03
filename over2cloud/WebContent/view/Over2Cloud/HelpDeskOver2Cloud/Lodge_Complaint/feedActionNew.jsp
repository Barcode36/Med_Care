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
 <script type="text/javascript" src="<s:url value="/js/offeringComplaint/actionComplaint.js"/>"></script>


 <script type="text/javascript">

 $.subscribe('test', function(event,data)
		 {
	 alert("sdjkgjhdgcfd");
	  setTimeout(function(){ $("#foldeffect").fadeIn(); }, 10);
      setTimeout(function(){ $("#foldeffect").fadeOut(); }, 4000);
	 $("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/HelpDeskOver2Cloud/Lodge_Complaint/viewActivityBoardHeader.action",
		    success : function(data) 
		    {
		    	$("#"+"data_part").html(data);
		    },
		    error: function() 
		    {
	            alert("error");
	        }
		 });
		 });
 function backOnViewActivityBoard()
 
 {
    $("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/HelpDeskOver2Cloud/Lodge_Complaint/viewActivityBoardHeader.action",
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

	</script>
</head>
<div class="clear"></div>
<div class="middle-content">
<div class="list-icon">
	<div class="head">Take Action </div> <img alt="" src="images/forward.png" style="margin-top:10px; float: left;"> <div class="head">Ticket ID: <s:property value="%{ticket_no}"/>, Opened On: <s:property value="%{open_date}"/> & <s:property value="%{open_time}"/></div>
</div>
<div class="clear"></div>
<div style="overflow:auto; height:420px; width: 96%; margin: 1%; border: 1px solid #e4e4e5; -webkit-border-radius: 6px; -moz-border-radius: 6px; border-radius: 6px; -webkit-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); -moz-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); box-shadow: 0 1px 4px rgba(0, 0, 0, .10); padding: 1%;">
<body>
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
		        <s:form id="formone1" name="formone1" action="actionOnComplaint"  theme="css_xhtml"  method="post" enctype="multipart/form-data">
		     <s:hidden name="ticketno" id="ticketno" value="%{#parameters.ticketNo}"></s:hidden>
		    <s:hidden name="feedid" id="feedid" value="%{#parameters.feedid}"></s:hidden>
		    <s:hidden id="feedStatus" value="%{#parameters.feedStatus}"></s:hidden>
		     <s:hidden  id="moduleName" value="%{#parameters.moduleName}"></s:hidden>
		
		       
		       
		       
		        <s:hidden id="allotto" value="%{#parameters.allotto}"></s:hidden>
		
		     <center><div style="float:center; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;"><div id="errZone" style="float:center; margin-left: 7px"></div></div></center>
		    
	
	  <table border="1" width="100%" style="border-collapse: collapse;">
	   
				<tr bgcolor="#D8D8D8" style="height: 25px">
					<s:iterator value="dataMap">
						<s:if test="key=='Feedback By' || key=='Feedback CC' || key=='Feedback Type' ">
							<td width="10%"><b><s:property value="%{key}"/>: <b></td>
							<td width="10%"><s:property value="%{value}"/></td>
						</s:if>
					</s:iterator>
				</tr>
				
				<tr  style="height: 25px">
					<s:iterator value="dataMap">
						<s:if test="key=='Organization Name' || key=='Relationship Type' || key=='Offering For'">
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
						<s:if test="key == 'Current Status' ||  key == 'Current Level'">
							<td width="10%"><b><s:property value="%{key}"/>: <b></td>
							<td width="10%"><s:property value="%{value}"/></td>
						</s:if>
						
					</s:iterator>
					<td width="10%"> </td>
					<td width="10%"> </td>
				</tr>
				
			  <tr bgcolor="#D8D8D8" style="height: 25px">
					<s:iterator value="dataMap">
						<s:if test="key == 'Lapse Time' || key=='Next Escalation On' || key=='Next Escalation To'">
							<td width="10%"><b><s:property value="%{key}"/>: <b></td>
							<td width="10%"><s:property value="%{value}"/></td>
						</s:if>
					</s:iterator>
				</tr>       	
				
				 
		</table>
		  
		    <div class="clear"></div>
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
             
                 <div id="snoozeDiv1" style="display:none">
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
			                 <s:textfield name="allotto" id="allotto" value= "%{allotto}" readonly="true"  cssClass="textField" />
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
   <div class="clear"></div>
   <div class="fields" align="center">
   <center><img id="indicator1" src="<s:url value="/images/ajax-loader_small.gif"/>" alt="Loading..." style="display:none"/></center>
   <ul><li class="submit">
   <div class="type-button">
   <div id="bt" style="display: block;">
   <sj:submit 
       targets="resultDiv" 
       clearForm="true"
       id="taskADD"
       value=" Save " 
       effect="highlight"
       effectOptions="{ color : '#222222'}"
       effectDuration="5000"
       button="true"
       onBeforeTopics="validateActionForComplaintNew"
       cssClass="submit"
       indicator="indicator1"
       cssStyle="margin-left: -30px;"
/>
	            <sj:a cssStyle="margin-left: 203px;margin-top: -45px;" button="true" href="#" onclick="resetForm('formone1');Reset('.reSpIds');">Reset</sj:a>
	            <sj:a cssStyle="margin-left: 5px;margin-top: -45px;" button="true" href="#" onclick="backOnViewActivityBoard();">Back</sj:a>
   </div>
   </div>
   </li>
   </ul>
   </div>    
		  <div id="resultDiv"></div>
		  
</s:form>
</body>
</div>
</div>
</html>