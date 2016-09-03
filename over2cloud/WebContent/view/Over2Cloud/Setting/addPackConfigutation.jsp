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

function GetApplicationName(getappsDiv,isocountrycode)
{
	var conP = "<%=request.getContextPath()%>";
	var countrycode=$("#"+isocountrycode).val();
	 $.ajax({
				    type : "post",
				    url : conP+"/view/Over2Cloud/Setting/ajexforApplicationGet.action?countryid="+countrycode+"",
				    success : function(domainIdData) {
			        $("#"+getappsDiv).html(domainIdData);
				},
				   error: function() {
			            alert("error");
			        }
				 });
}


function setTime(v)
{
	if(v.value == "D")
	{
		$("#time").html("In Day");
	}
	else if(v.value == "Y")
	{
		$("#time").html("In Year");
	}
	else if(v.value == "M")
	{
		$("#time").html("In Month");
	}
	else
	{
		$("#time").html(" ");
	}
	
}
</script>
</head>
<body>
<div class="clear"></div>
<div class="middle-content">
	<div class="list-icon">
	<div class="head">Pack Configuration</div>
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
		<div class="secHead">Pack Configuration</div>
		<s:form name="AddApplicationConfiguration"  id="AddPack_url" action="addpackconfiguration"  theme="css_xhtml" theme="simple"  method="post">
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
		                              onchange="GetApplicationName('getappsDiv','country');"
		                              >
		                  </s:select>
	                  </div>
                 </div>
                  <span id="mandatoryFields" class="pIds" style="display: none; ">applicationName#Application Name#D#a,</span>
	                  <div class="user_form_text1">Application Name<font color="red"> *</font>:</div>
		                  <div class="user_form_input inputarea"><span class="needed"/>
		                  <div id="getappsDiv">
		                    <s:select 
		                              id="applicationName"
		                              name="applicationName" 
		                              list="#{'noList':'No List'}" 
		                              headerKey="-1"
		                              headerValue="Select Application Name" 
		                              cssClass="select"
		                              cssStyle="width:82%"
		                              />
		                   </div>
		                  </div>
             	</div>
             	
             	<div class="form_menubox">
             	  <div class="inputmain">
             	  <span id="mandatoryFields" class="pIds" style="display: none; ">timeperiod#Time Period#D#a,</span>
	                  <div class="user_form_text">Time Period<font color="red"> *</font>:</div>
	                  <div class="user_form_input inputarea">
	                   <s:select 
	                              id="timeperiod"
	                              name="timeperiod" 
	                              list="#{'D':'In Day','M':'In Month','Y':'In Year'}" 
	                              headerKey="-1"
	                              headerValue="Select Time Period" 
	                              cssClass="select"
		                          cssStyle="width:82%" 
	                              onchange="setTime(this);"
	                              />
	                  </div>
                  </div>
                  <div class="inputmain">
                  
	                  <div class="user_form_text1">Time: <span id="time"></span> </div>
		                  <div class="user_form_input inputarea">
		                  <s:textfield name="timein" id="timein" maxlength="150" placeholder="Enter Time Duration" cssClass="form_menu_inputtext"/>
	                  </div>
                  </div>
             	</div>
             	
             	<div class="form_menubox">
             	  <div class="inputmain">
	                  <div class="user_form_text">User Counter:</div>
	                  <div class="user_form_input inputarea">
	                   <s:textfield name="appusercounter" id="appusercounter" maxlength="150" placeholder="Enter User Counter" cssClass="form_menu_inputtext"/>
	                  </div>
                  </div>
             	</div>
             	
             	<div class="form_menubox">
             	
             	<div class="inputmain">
             	<span id="mandatoryFields" class="pIds" style="display: none; ">currency#Currency Type#D#a,</span>
                  <div class="user_form_text">Currency Type<font color="red"> *</font>:</div>
	                  <div class="user_form_input inputarea">
	                   <s:select 
	                              id="currency"
	                              name="currency" 
	                              list="#{'RS':'Indian Rupee','EU':'Euro','$':'Dollar'}" 
	                              headerKey="-1"
	                              headerValue="Select Currency Type" 
	                              cssClass="select"
		                           cssStyle="width:82%"
	                              />
	                  </div>
                  </div>
                  <div class="inputmain">
                  <span id="mandatoryFields" class="pIds" style="display: none; ">cost#Cost#T#n,</span>
	                  <div class="user_form_text1">Cost<font color="red"> *</font>:</div>
		                  <div class="user_form_input inputarea"><span class="needed"/>
		                  <s:textfield name="cost" id="cost" maxlength="150" placeholder="Enter Cost" cssClass="form_menu_inputtext"/>
	                  </div>
                  </div>
             	</div>
             	
              <div class="form_menubox">
              
                  <div class="inputmain">
                  <span id="mandatoryFields" class="pIds" style="display: none; ">offerFrom#Offer From#T#Date,</span>
	                  <div class="user_form_text">Offer From<font color="red"> *</font>:</div>
		                  <div class="user_form_input inputarea">
		                  	<sj:datepicker name="offerFrom" id="offerFrom"   displayFormat="dd-mm-yy" showOn="focus" cssClass="form_menu_inputtext" maxlength="10" />
		                  </div>
                  </div>
                  
                  <div class="inputmain">
                   <span id="mandatoryFields" class="pIds" style="display: none; ">offerTo#Offer To#T#Date,</span>
	                  <div class="user_form_text1">Offer To<font color="red"> *</font>:<span id="time"></span> </div>
		                  <div class="user_form_input inputarea">
		                  	<sj:datepicker name="offerTo" id="offerTo"  displayFormat="dd-mm-yy" showOn="focus" cssClass="form_menu_inputtext" maxlength="10" />
		                  </div>
                  </div>
             	</div>
             	
             	
				 <div class="clear"></div>
				<div style="width: 100%; text-align: center; padding-bottom: 10px;">
	                <img id="indicator" src="<s:url value="/images/ajax-loader.gif"/>" alt="Loading..."   style="display:none"/>
	                <sj:submit
	                    targets="Result" 
					    clearForm="true"
						id="submit"
						value="Register" 
						href="%{AddPack_url}"
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
							 onclick="cancelpackview();"
							 />      
	               </div>
	           
				<sj:div id="foldeffect"  effect="fold">
                     <div id="Result"></div>
          			</sj:div>
			
			
			</s:form>
			
</div>
</div>
</body>
</html>