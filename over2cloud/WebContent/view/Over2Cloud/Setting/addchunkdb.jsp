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
	<div class="head">Client Chunk Space Configuration</div>
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
			<div class="secHead">Client Chunk Space Configuration Add</div>
		<s:form name="AddServerchunkDb_url"  id="AddServerchunkDb_url" action="AddChunkDataBaseServer"  theme="css_xhtml" theme="simple"  method="post">
				<div class="form_menubox">
				  <div class="inputmain">
	                  <div class="user_form_text">Host Name/IP<font color="red"> *</font>:</div>
	                  <div class="user_form_input inputarea"><s:textfield name="dbServerip" id="dbServerip" maxlength="150" placeholder="Enter Server Ip/Name" cssClass="form_menu_inputtext"/>
	                  </div>
                  </div>
                  <div class="inputmain">
                  <span id="mandatoryFields" class="pIds" style="display: none; ">dbServerport#DB Port#T#n,</span>
	                  <div class="user_form_text1">DB Port<font color="red"> *</font>:</div>
	                  <div class="user_form_input inputarea"><s:textfield name="dbServerport" id="dbServerport" maxlength="50" placeholder="Enter Server Port" cssClass="form_menu_inputtext"/>
	                  </div>
             	  </div>
             	</div>
             	<div class="form_menubox">
             	  <div class="inputmain">
             	   <span id="mandatoryFields" class="pIds" style="display: none; ">dbname#DB Name#T#a,</span>
	                  <div class="user_form_text">DB Name<font color="red"> *</font>:</div>
	                  <div class="user_form_input inputarea"><s:textfield name="dbname" id="dbname" value="clouddb" maxlength="150" placeholder="Enter Server Ip/Name" cssClass="form_menu_inputtext"/>
	                  </div>
             	  </div>
             	</div>
             	<div class="form_menubox">
             	  <div class="inputmain">
             	  <span id="mandatoryFields" class="pIds" style="display: none; ">dbusername#User Name#T#a,</span>
	                  <div class="user_form_text">User Name<font color="red"> *</font>:</div>
	                  <div class="user_form_input inputarea"><s:textfield name="dbusername" id="dbusername" maxlength="150" placeholder="Enter Server User Name" cssClass="form_menu_inputtext"/>
	                  </div>
                  </div>
                  <div class="inputmain">
                  <span id="mandatoryFields" class="pIds" style="display: none; ">dbpassword#Password#T#a,</span>
	                  <div class="user_form_text1">Password<font color="red"> *</font>:</div>
	                  <div class="user_form_input inputarea"><s:password name="dbpassword" id="dbpassword" maxlength="50" placeholder="Enter Server Password" cssClass="form_menu_inputtext"/>
	                  </div>
             	  </div>
             	</div>
             	
             	<div class="form_menubox">
             	  <div class="inputmain">
             	  <span id="mandatoryFields" class="pIds" style="display: none; ">serverStatus#Status#D#a,</span>
	                  <div class="user_form_text">Status<font color="red"> *</font>:</div>
	                  <div class="user_form_input inputarea">
	                   <s:select 
	                              id="serverStatus"
	                              name="serverStatus" 
	                              list="#{'A':'Active','D':'Enactive'}" 
	                              headerKey="-1"
	                              headerValue="Choose Server Status" 
	                              cssClass="select"
                                  cssStyle="width:82%"
	                              />
	                  </div>
                  </div>
                  
                  <div class="inputmain">
                  <span id="mandatoryFields" class="pIds" style="display: none; ">country#Country#D#a,</span>
	                  <div class="user_form_text1">Country<font color="red"> *</font>:</div>
	                  <div class="user_form_input inputarea">
	                  <s:select 
	                              id="country"
							      name="country" 
							      list="countryList"
							      listKey="iso_code"
							      listValue="contryName"
							      headerKey="-1"
							      headerValue="Country" 
	                              cssClass="select"
                                  cssStyle="width:82%"
	                   ></s:select>
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
						href="%{AddServerchunkDb_url}"
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
							 onclick="designationView();"
							 />
				<sj:div id="foldeffect"  effect="fold">
                     <div id="Result"></div>
          			</sj:div>
				</div>
			
			</s:form>
</div>
</div>
</div>
</body>
</html>