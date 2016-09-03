<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<script type="text/javascript" src="<s:url value="/js/task_js/task.js"/>"></script>
<script type="text/javascript">
$.subscribe('completeData', function(event,data)
        {
          setTimeout(function(){ $("#foldeffect1").fadeIn(); }, 10);
          setTimeout(function(){ $("#foldeffect1").fadeOut(); }, 2000);
         
        });
function getTaskStatus(statusId)
     {
		showHideFeedDiv('highPriorityDiv');
     }
function showHideFeedDiv(showDiv)
{
	if($("#"+showDiv).css('display') == 'none')
		$("#"+showDiv).show('slow');
}
</script>
</head>
<body>

<div style=" float:left; padding:5px 0%; width:100%;">
<div class="border" style="overflow: auto; width: 850px;">
		<s:form id="formtwo" name="formtwo" action="addsnoozeTask"  theme="css_xhtml"  method="post"  namespace="/view/Over2Cloud/DAROver2Cloud">
		      <div style="float:left; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
               <div id="errZone" style="float:left; margin-left: 7px">
               </div> 
             </div> 
		      <s:hidden name="feedid" id="feedid" value="%{#parameters.feedId}"></s:hidden>
		      
		      <div class="form_menubox">
               <div class="user_form_text" style="text-indent:20px;">Application Name:</div>
               <div class="user_form_input"><s:textfield name="taskno" id="taskno" value="%{#parameters.taskNamee}" cssClass="form_menu_inputtext"/></div>
               <div class="user_form_text1" style="text-indent:10px;">Alloted By:</div>
               <div class="user_form_input"><s:textfield name="allotedby" id="allotedby" value="%{#parameters.allotedbyy}" cssClass="form_menu_inputtext"/></div>
              </div>
            
            <div class="form_menubox">
               <div class="user_form_text" style="text-indent:20px;">Alloted To:</div>
               <div class="user_form_input"><s:textfield name="allotedto" id="allotedto" value="%{#parameters.allotedToo}" cssClass="form_menu_inputtext" /></div>
               <div class="user_form_text1" style="text-indent:10px;">Initiation date:</div>
               <div class="user_form_input"><s:textfield name="initiation" id="initiation" value="%{#parameters.initiation_datee}" cssClass="form_menu_inputtext"/></div>
            </div>
            
            <div class="form_menubox">
               <div class="user_form_text" style="text-indent:20px;">Completion Date:</div>
               <div class="user_form_input"><s:textfield name="completion" id="completion" value="%{#parameters.completion_datee}" cssClass="form_menu_inputtext" /></div>
               <div class="user_form_text1" style="text-indent:10px;">Status:</div>
               <div class="user_form_input"><s:textfield name="status" id="status" value="%{#parameters.statuss}" cssClass="form_menu_inputtext"/></div>
            </div>
            
            <div class="form_menubox">
              <span id="mandatoryFields" class="pIds" style="display: none; ">status1#Status#D#a,</span>
              <div class="user_form_text" style="text-indent:20px;">Status:*</div>
              <div class="user_form_input"><span class="needed"></span>
                  <s:select 
                             id="status1"
                             name="status1" 
                             list="{'Snooze'}" 
                             headerKey="-1"
                             headerValue="Select Status"
                             onchange="getTaskStatus('status1');"
                             cssClass="select"
                             cssStyle="width:130%"
                              >
                  </s:select>
             </div>
            </div>
                 <div id="highPriorityDiv" style="display:none">
                      <div class="form_menubox">
	                      <span id="mandatoryFields" class="pIds" style="display: none; ">newDate#New Date#D#a,</span>
	                      <div class="user_form_text">New Date:*</div>
	                      <div class="user_form_input"> <span class="needed"></span>
	                      <sj:datepicker name="newDate" id="newDate" cssStyle="margin:0px 0px 10px 0px"   changeMonth="true" cssStyle="margin:0px 0px 10px 0px" changeYear="true" yearRange="1890:2020" showOn="focus" displayFormat="dd-mm-yy" cssClass="form_menu_inputtext" placeholder="Select Date"/>
	                      </div>
	                      <span id="mandatoryFields" class="pIds" style="display: none; ">reason#Reason of Snooze#D#a,</span>
	                      <div class="user_form_text1" style="text-indent:10px;">Reason of Snooze:*</div>
	                      <div class="user_form_input"><span class="needed"></span>
	                      <s:textfield name="reason" id="reason" placeholder="Enter  Comment" cssClass="form_menu_inputtext"/>
	                      </div>
                      </div>
                 </div>
            	<!-- Buttons -->
				<div class="fields" align="center">
				<ul>
				 <li class="submit" style="background:none;">
					<div class="type-button">
	                <sj:submit 
	                        targets="target1"
	                        clearForm="true"
	                        value="Register" 
	                        effect="highlight"
	                        effectOptions="{ color : '#222222'}"
	                        button="true"
	                        effectOptions="{ color : '#222222'}"
	                        effectDuration="5000"
	                        onCompleteTopics="completeData"
	                        onBeforeTopics="validate"
	                        cssClass="submit"
	                        />
	               </div>
	               </li>
				</ul>
				</div>
				<sj:div id="foldeffect1" cssClass="sub_class1"  effect="fold">
				<div id="target1"></div>
				</sj:div>
	 </s:form>
</div>
</div>
</body>
</html>
