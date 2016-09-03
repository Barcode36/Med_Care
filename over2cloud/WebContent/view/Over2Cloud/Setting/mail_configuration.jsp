<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script type="text/javascript">
$.subscribe('complete', function(event,data)
        {
          setTimeout(function(){ $("#foldeffect").fadeIn(); }, 50);
          setTimeout(function(){ $("#foldeffect").fadeOut(); }, 4000);
        });

</script>
</head>
<body>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>

<div class="clear"></div>

<div class="middle-content">

	<div class="list-icon">
	<div class="head">Mail Configuration</div>
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
			<div class="secHead">Mail Configuration</div>
		<s:form name="mailconfiguration"  id="Mailconfiguration_url" action="mailConfiguration"  theme="css_xhtml" theme="simple"  method="post">
				<div class="form_menubox">
				
				  <div class="inputmain">
                  <div class="user_form_text">Mail Server:</div>
                  <div class="user_form_input inputarea"><span class="needed"/><s:textfield name="mailServer" id="mailServer" cssClass="form_menu_inputtext" maxlength="20" placeholder="Enter Mail Server"></s:textfield>
                  </div>
                  </div>
                  
                  <div class="inputmain">
                  <div class="user_form_text1">Mail Port:</div>
                  <div class="user_form_input inputarea"><span class="needed"/>
                  <s:textfield name="mailport" id="mailport" cssClass="form_menu_inputtext" maxlength="20" placeholder="Enter Mail port"></s:textfield>
                  </div>
             	  </div>
             	</div>
             	
             	<div class="form_menubox">
             	  <div class="inputmain">
                  <div class="user_form_text">Email Id:</div>
                  <div class="user_form_input inputarea"><span class="needed"/><s:textfield name="emailid" id="emailid" cssClass="form_menu_inputtext" maxlength="20" placeholder="Enter Mail port"></s:textfield>
                  </div>
                  </div>
                  
                  <div class="inputmain">
                  <div class="user_form_text1">Password:</div>
                  <div class="user_form_input inputarea"><span class="needed"/>
                  <s:password name="mailpwd" id="mailpwd" cssClass="form_menu_inputtext" maxlength="20" placeholder="Enter Mail port"/>
                  </div>
                  </div>
             	</div>
             	
		     
				<!-- Buttons -->
				<center><img id="indicator2" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/></center>
							 <div class="clear"></div>
				<div style="width: 100%; text-align: center; padding-bottom: 10px;">
												 <sj:submit 
								                              targets="Result" 
								                              clearForm="true"
								                              id="submit"
								                              value="Save" 
								                              href="%{Mailconfiguration_url}"
								                              effect="foldeffect"
								                              cssClass="submit"
								                              effectOptions="{float:'right'}"
								                              effectDuration="5000"
								                              button="true"
								                              onCompleteTopics="complete;"
								                              indicator="indicator"
								                              onBeforeTopics="validate"
								                            />
										   <sj:submit 
							 value="Cancel" 
							 cssClass="submit" 
							 effect="highlight"
							 effectOptions="{ color : '#222222'}"  
							 button="true"  
							 onclick="designationView();"
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
