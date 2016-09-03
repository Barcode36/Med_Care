<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjr" uri="/struts-jquery-richtext-tags"%>
<style type="text/css">

.user_form_input{
    margin-bottom:10px;
}

</style>
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>

<script src="<s:url value="/js/common/commonvalidation.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/communication/Communicationmsgdraft.js"/>"></script>

<SCRIPT type="text/javascript">
function textCounter(field, countfield, maxlimit) {
	if (field.value.length < maxlimit) 
	field.value = field.value.substring(0, maxlimit);
	// otherwise, update 'characters left' counter
	else 
	countfield.value = maxlimit + field.value.length;
	}




function messageDescriptionLimit()
{
	
	if (document.formtwo.writeMessage.value.length>160 && document.formtwo.writeMessage.value.length<320)
	 {
	   		errMsgDescription.innerHTML = "<div class='user_form_inputError2'>Second SMS Started.</div>";
		 	document.formtwo.writeMessage.focus();
		 	event.originalEvent.options.submit = true;
	 }
	else if (document.formtwo.writeMessage.value.length>320 && document.formtwo.writeMessage.value.length<480)
	 {
	   		errMsgDescription.innerHTML = "<div class='user_form_inputError2'>Third SMS Started.</div>";
		 	document.formtwo.writeMessage.focus();
		 	event.originalEvent.options.submit = true;
	 }
	else if (document.formtwo.writeMessage.value.length>480 && document.formtwo.writeMessage.value.length<640)
	 {
	   		errMsgDescription.innerHTML = "<div class='user_form_inputError2'>Fourth SMS Started.</div>";
		 	document.formtwo.writeMessage.focus();
		 	event.originalEvent.options.submit = true;
	 }
	else if (document.formtwo.writeMessage.value.length>640)
	 {
	   		errMsgDescription.innerHTML = "<div class='user_form_inputError2'>Fifth SMS Started.</div>";
		 	document.formtwo.msgDescription.focus();
		 	event.originalEvent.options.submit = true;
	 }

	}

</script>
</head>
<body>
<div class="list-icon">
	<div class="head">Templates</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div><div class="head">Add</div>

</div>
	<div class="clear"></div>
<div style=" float:left; padding:10px 0%; width:100%;">
<div class="clear"></div>
<div class="border">

<div class="container_block">
<div style=" float:left; padding:20px 1%; width:98%;">


<s:form id="formtwo" name="formtwo" namespace="/view/Over2Cloud/CommunicationOver2Cloud/Comm" action="insertData" theme="css_xhtml"  method="post" >
<div style="float:right; border-color: black; background-color: rgb(255,99,71); color: white;width: 90%; font-size: small; border: 0px solid red; border-radius: 8px;">
  <div id="errZone" style="float:left; margin-left: 7px"></div>        
        </div>
             	
   	
   	
   	
						
						<div class="clear"></div>
   	<!-- Text box -->
   	
               <s:iterator value="messageName1" status="counter">
                   <s:if test="%{mandatory}">
                      <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                 </s:if>  
           
				 	 <s:if test="#counter.count%2 != 0">
				 
				 <s:if test="%{mandatory}">
	                   <div class="rightColumn">
						<div class="leftColumn1"><s:property value="%{value}"/></div>
						<div class="rightColumn">
					     <span class="needed"></span><s:textfield name="%{key}"  id="%{key}"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;"/>
					     </div></div>
						  </s:if>
					     <s:else>
					     <div class="rightColumn">
						<div class="leftColumn1"><s:property value="%{value}"/></div>
						<div class="rightColumn"><s:textfield name="%{key}"  id="%{key}"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;"/>
					     </div></div>
					     </s:else>
					  </s:if>
					   </s:iterator>
					  
   	 <div class="clear"></div>	

<s:iterator value="messageDescriptionTextBox" status="counter">
					  <s:if test="%{mandatory}">
                      <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                 </s:if>  
           
				 	 <s:if test="#counter.count%2 != 0">
					   <s:if test="%{mandatory}">
					    <div class="rightColumn">
						<div class="leftColumn1"><s:property value="%{value}"/>:</div>
						<div class="rightColumn"><span class="needed"></span>
					     <s:textarea name="%{key}"  id="%{key}"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px; " onkeydown="textCounter(this.form.writeMessage,this.form.numberOfCharacters,0)" onkeypress="messageDescriptionLimit()" maxlength="800" />
					     </div>
					     <div id="errMsgDescription" class="errordiv"></div>
					     </div>
					    </s:if>
					    <s:else>
					    <div class="rightColumn">
						<div class="leftColumn1"><s:property value="%{value}"/>:</div>
						<div class="rightColumn">
					    <s:textarea name="%{key}" cols="65" rows="30" id="%{key}" class="form_menu_inputtext" style="margin: 0px 0px 10px; width: 380px; height: 60px;" maxlength="160" />

					     </div></div>
					    </s:else>
					 
			</s:if>
				 </s:iterator>

     <div class="clear"></div>	
     <s:iterator value="numOfChar" status="counter">
					   <div class="newColumn">
								<div class="leftColumn1">Number Of Characters</div>
								<div class="rightColumn">
								<input readonly type="text" id="%{key}" name="numberOfCharacters" size=3 maxlength="3" value="0" style="border:1px solid #e2e9ef;border-top:1px solid #cbd3e2;outline:medium none;padding:2px;" >
						           
			                  		<div id="errorlevel2org" class="errordiv"></div>
								</div>
							</div>
            </s:iterator>

               
<!-- Buttons -->
<div class="clear"></div>
 <center><img id="indicator3" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/></center>


<div class="type-button" style="margin-left: -200px;">
<center> 
       <sj:submit 
           targets="leadresult" 
                       clearForm="true"
                       value="Save" 
                       effect="highlight"
                       effectOptions="{ color : '#222222'}"
                       effectDuration="5000"
                       button="true"
                       onCompleteTopics="level1"
                       cssClass="submit"
                       cssStyle="margin-right: 65px;margin-bottom: -9px;"
                       indicator="indicator2"
                       onBeforeTopics="validate"
         />
         
         <sj:a 
				     	href="#" 
		               	button="true" 
		               	onclick="viewMessageDraft1();"
						cssStyle="margin-top: -28px;"						>
					  	Reset
		   </sj:a>
		   
		   <sj:a 
				     	href="#" 
		               	button="true" 
		               	onclick="viewMessageDraft();"
						cssStyle="margin-right: -137px;margin-top: -28px;"
						>
					  	Back
		   </sj:a>
		   
         
         
      </center>   
         
   </div>

 
</s:form>  
               



  
							
               


  


</div>
</div>
</div></div>

</body>
</html>
