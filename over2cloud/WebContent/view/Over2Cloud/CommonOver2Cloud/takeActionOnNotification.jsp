<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<script src="<s:url value="/js/organization.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/showhide.js"/>"></script>
<SCRIPT type="text/javascript">
$.subscribe('completeData', function(event,data)
        {
          setTimeout(function(){ $("#Result").fadeIn(); }, 10);
          setTimeout(function(){ $("#Result").fadeOut(); }, 4000);
         
        });
</script>
</head>
<body>
		<s:form id="formone" name="formone" cssClass="cssn_form" namespace="/view/Over2Cloud/commonModules" action="takeActionOnReuqest" theme="simple"  method="post"enctype="multipart/form-data" >
   				<s:hidden name="id" value="%{id}"></s:hidden>
    			
    			
    			<div class="form_menubox" style="width: 100%;" align="left">
                  <div class="user_form_text" >Action Desc:</div>
                  <div class="user_form_input"><s:textarea name="desc" id="desc"maxlength="50"  cssClass="form_menu_inputtext" placeholder="Enter Brief"/></div>
				 </div>
				 
				 
				<!-- Buttons -->
				<center><img id="indicator2" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/></center>
				<div class="fields">
				<ul>
				<li class="submit" style="background: none;">
					<div class="type-button">
	                <sj:submit 
	                        targets="foldeffect" 
	                        clearForm="true"
	                        value="  Register  " 
	                        effect="highlight"
	                        effectOptions="{ color : '#222222'}"
	                        effectDuration="5000"
	                        button="true"
	                        onCompleteTopics="completeData"
	                        cssClass="submit"
	                        indicator="indicator2"
	                        />
	               </div>
				</ul>
				 <sj:div id="foldeffect"  effect="fold">
                    <div id="Result"></div>
               </sj:div>
				</div>
			</s:form>
</body>
</html>