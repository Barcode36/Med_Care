<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<SCRIPT type="text/javascript" src="js/commanValidation/validation.js"></SCRIPT>
<SCRIPT type="text/javascript" src="js/feedback/feedbackForm.js"></SCRIPT>
<script>

$.subscribe('level1', function(event,data)
	       {
	         setTimeout(function(){ $("#orglevel1Div").fadeIn(); }, 10);
	         setTimeout(function(){ $("#orglevel1Div").fadeOut(); }, 4000);
	       });



function viewSMSConfigView() {
	var conP = "<%=request.getContextPath()%>";
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
    $.ajax({
        type : "post",
        url : conP+"/view/Over2Cloud/feedback/beforeFeedbackSMSConfig.action",
        success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
    },
       error: function() {
            alert("error");
        }
     });
	
}
</script>
</head>
<body>
<div class="list-icon">
	 <div class="head">Configure SMS</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div><div class="head">Add</div> 
</div>
<div class="clear"></div>
     <div style="width: 100%; center; padding-top: 10px;">
       <div class="border" style="height: 50%">
   <s:form id="formone" name="formone" namespace="/view/Over2Cloud/feedback" action="feedbackConfig" theme="css_xhtml"  method="post" enctype="multipart/form-data" >
                           <center>
			                 <div style="float:center; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
                             <div id="errZone" style="float:center; margin-left: 7px"></div>        
                             </div>
                           </center>
				 <s:iterator value="feedbackTextBox" status="counter">
				  <s:if test="%{mandatory}">
                      <span class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                 </s:if>
				  <s:if test="#counter.count%2 !=0">
		         <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
								<s:if test="%{mandatory}">
				  	<span class="needed">
				  	</span>
				  </s:if>
								<s:textfield name="%{key}" id="%{key}" maxlength="200" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"  cssClass="textField"/>				
								</div>
			      </div>
			      </s:if>
			      <s:else>
		          <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
								<s:if test="%{mandatory}">
				  	<span class="needed">
				  	</span>
				  </s:if>
						<s:textfield name="%{key}" id="%{key}"maxlength="200" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"  cssClass="textField"/>				
				  </div>
			      </div>
				 </s:else>
				 </s:iterator>
				 <s:iterator value="feedbackTimeBox" status="counter">
				  <s:if test="%{mandatory}">
                      <span class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                 </s:if>
				  <s:if test="#counter.count%2 !=0">
		         <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
								<s:if test="%{mandatory}">
				  	<span class="needed">
				  	</span>
				  </s:if>
								<sj:datepicker id="%{key}" name="%{key}" onChangeTopics="clearDatePicker" readonly="true" cssClass="textField" size="20" timepicker="true"  timepickerOnly="true" showOn="focus"  Placeholder="Select Time"/>				
								</div>
			      </div>
			      </s:if>
			      <s:else>
		          <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
								<s:if test="%{mandatory}">
				  	<span class="needed">
				  	</span>
				  </s:if>
						<sj:datepicker id="%{key}" name="%{key}" onChangeTopics="clearDatePicker" readonly="true" cssClass="textField" size="20" timepicker="true" showOn="focus" timepickerOnly="true"  Placeholder="Select Time"/>				
				  </div>
			      </div>
				 </s:else>
				 </s:iterator>
				 <s:iterator value="feedbackDateBox" status="counter">
				  <s:if test="%{mandatory}">
                      <span class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                 </s:if>
				  <s:if test="#counter.count%2 !=0">
		         	<div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
								<s:if test="%{mandatory}">
				  					<span class="needed">
				  					</span>
				 				</s:if>
										<sj:datepicker id="%{key}" name="%{key}" onChangeTopics="clearDatePicker" readonly="true" cssClass="textField" size="20" timepicker="true" showOn="focus" timepickerOnly="true"  Placeholder="Select Time"/>		
								</div>
			      </div>
			      </s:if>
			      <s:else>
		          <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
								<s:if test="%{mandatory}">
				  	<span class="needed">
				  	</span>
				  </s:if>
					<s:select name="type" id="type" list="#{'0':'Positive', '1':'Negative'}" headerKey="-1" headerValue=" Select Keyword Type " cssStyle="width:82%;"></s:select>					
				  </div>
			      </div>
				 </s:else>
				 </s:iterator>
			    <center><img id="indicator2" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/></center>
				<div class="clear" >
				<div style="padding-bottom: 10px;margin-left:-80px" align="center">
						<sj:submit 
				             targets="level123" 
		                     clearForm="true"
		                     value="Save" 
		                     effect="highlight"
		                     effectOptions="{ color : '#222222'}"
		                     effectDuration="5000"
		                     button="true"
		                     onBeforeTopics="validate"
		                     onCompleteTopics="level1"
			            />
			            &nbsp;&nbsp;
						<sj:submit 
		                     value="Reset" 
		                     button="true"
		                     cssStyle="margin-left: 139px;margin-top: -43px;"
		                     onclick="resetForm('formone');"
			            />&nbsp;&nbsp;
			            <sj:a
	                        cssStyle="margin-left: 276px;margin-top: -58px;"
						button="true" href="#" value="Back" cssStyle="margin-left: 266px;margin-top: -74px;" 
						onclick="viewSMSConfigView();"
						>Back
					</sj:a>
					</div>
               </div>
               <sj:div id="orglevel1Div"  effect="fold">
                   <sj:div id="level123">
                   </sj:div>
               </sj:div>
   </s:form>          
</div>
</div>
</body>
</html>