<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script src="<s:url value="/js/common/commonvalidation.js"/>"></script>
<script type="text/javascript">
$.subscribe('complete', function(event,data)
        {
          setTimeout(function(){ $("#foldeffect").fadeIn(); }, 50);
          setTimeout(function(){ $("#foldeffect").fadeOut(); }, 4000);
        });
</script>
</head>
<body>
<div class="clear"></div>
<div class="middle-content">
	<div class="list-icon">
	<div class="head">Application Registration</div>
	</div>
	<div class="clear"></div>
	<div style="min-height: 10px; height: auto; width: 100%;"></div>
	<div class="border">
		<div style="float:right; border-color: black; background-color: rgb(255,99,71); color: white;width: 90%; font-size: small; border: 0px solid red; border-radius: 8px;">
  		<div id="errZone" style="float:left; margin-left: 7px"></div>        
        </div>
          <div class="clear"></div>
		<div style="width: 100%; text-align: center; padding-top: 10px;">
	
		</div>
		<div>
			<div class="secHead">Application Registration</div>
		<s:form name="AddApplicationConfiguration"  id="AddApplication_url" action="AppApplicationForAll"  theme="css_xhtml" theme="simple"  method="post">
				
				
				<div class="form_menubox">
				  <div class="inputmain">
				  <span id="mandatoryFields" class="pIds" style="display: none; ">appname#Application Name#T#a,</span>
	                  <div class="user_form_text">Application Name<font color="red"> *</font>:</div>
	                  <div class="user_form_input inputarea"><s:textfield name="appname" id="appname" maxlength="150" placeholder="Enter Application Name" cssClass="form_menu_inputtext"/>
	                  </div>
                  </div>
                  <span id="mandatoryFields" class="pIds" style="display: none; ">appname#Application Code#T#a,</span>
                  <div class="inputmain">
	                  <div class="user_form_text1">Application Code<font color="red"> *</font>:</div>
	                  <div class="user_form_input inputarea"><s:textfield name="appcode" id="appcode" maxlength="50" placeholder="Enter Application Code" cssClass="form_menu_inputtext"/>
	                  </div>
             	  </div>
             	</div>
             	
             	
             	<div class="form_menubox">
	             	<div class="inputmain">
	             	<span id="mandatoryFields" class="pIds" style="display: none; ">country#Country#D#a,</span>
		                  <div class="user_form_text">Country<font color="red"> *</font>:</div>
		                  <div class="user_form_input inputarea">
			                  	<s:select 
			                              id="country"
			                              name="country" 
			                              list="countrylist"
			                              listKey="iso_code"
			                              listValue="contryName"
			                              headerKey="-1"
			                              headerValue="Country" 
			                              cssClass="select"
                                          cssStyle="width:82%"
			                              >
			                  </s:select>
		                  </div>
	                  </div>
                 </div>
				<!-- Buttons -->
				<div class="clear"></div>
				<div style="width: 100%; text-align: center; padding-bottom: 10px;">
	                <img id="indicator" src="<s:url value="/images/ajax-loader.gif"/>" alt="Loading..."   style="display:none"/>
	                
	                <sj:submit
	                    targets="Result" 
					    clearForm="true"
						id="submit"
						value="Register" 
						href="%{AddApplication_url}"
						effect="foldeffect"
						effectOptions="{float:'right'}"
						effectDuration="5000"
						cssClass="submit"
						button="true"
						onCompleteTopics="complete"
						indicator="indicator"
						onBeforeTopics="validate"
		             />
		               <sj:submit 
							 value="Cancel" 
							 cssClass="submit" 
							 effect="highlight"
							 effectOptions="{ color : '#222222'}"  
							 button="true"  
							 onclick="cancelappsview();"
							 />
				</div>
			
			<sj:div id="foldeffect"  effect="fold">
                     <div id="Result"></div>
          			</sj:div>
			</s:form>
</div>
</div>
</div>
</body>
</html>
