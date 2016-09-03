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
function oncheckcapacha(jsapercode)
{
        var conP = "<%=request.getContextPath()%>";
		var capchavalue=document.getElementById(jsapercode).value;
		document.getElementById("indicator2").style.display="block";
	     $.getJSON(conP+"/doCaptcha.action?j_captcha_response="+capchavalue,
			function(capchadata){
				 $("#userStatus").html(capchadata.msg);
				 document.getElementById("indicator2").style.display="none";
				 document.getElementById("status").value=capchadata.status;
	    });
}

$.subscribe('complete', function(event,data)
        {
          setTimeout(function(){ $("#foldeffect1").fadeIn(); }, 50);
          setTimeout(function(){ $("#foldeffect1").fadeOut(); }, 4000);
        });
</script>
</head>
<body>
<div class="clear"></div>

<div class="middle-content">

	<div class="list-icon">
	<div class="head">Registration Process</div>
	</div>
	<div class="clear"></div>	
	<div style="min-height: 10px; height: auto; width: 100%;"></div>
	
	<div class="border">
		<div style="float:right; border-color: black; background-color: rgb(255,99,71); color: white;width: 90%; font-size: small; border: 0px solid red; border-radius: 8px;">
  		<div id="errZone" style="float:left; margin-left: 7px"></div>        
        </div>
        <div class="clear"></div>	
		<div>
			<div class="secHead">Registration Process</div>
		<s:form name="formone" id="formOne" id="AddRegistation_Url" action="addRegistationAssociate"  theme="css_xhtml" theme="simple"  method="post">
				<div class="form_menubox">
				  <div class="inputmain">
				   <span id="mandatoryFields" class="pIds" style="display: none; ">regName#Name#T#a,</span>
	                  <div class="user_form_text">Name <font color="red"> *</font>:</div>
	                  <div class="user_form_input inputarea"><s:textfield name="regName" id="regName" maxlength="150" placeholder="Enter Name" cssClass="form_menu_inputtext"/>
	                  </div>
                  </div>
                  <div class="inputmain">
                   <span id="mandatoryFields" class="pIds" style="display: none; ">mobile#Mobile#T#m,</span>
	                  <div class="user_form_text1">Mobile<font color="red"> *</font>:</div>
	                  <div class="user_form_input inputarea"><s:textfield name="mobile" id="mobile" maxlength="50" placeholder="Enter Mobile Number" cssClass="form_menu_inputtext"/>
	                  </div>
             	  </div>
             	</div>
             	<div class="form_menubox">
             	  <div class="inputmain">
             	  <span id="mandatoryFields" class="pIds" style="display: none; ">emailaddress#EmailAddress#T#e,</span>
	                  <div class="user_form_text">EmailAddress <font color="red"> *</font>:</div>
	                  <div class="user_form_input inputarea"><s:textfield name="emailaddress" id="emailaddress" maxlength="150" placeholder="Enter Email Id" cssClass="form_menu_inputtext"/>
	                  </div>
                  </div>
                  <div class="inputmain">
                  <span id="mandatoryFields" class="pIds" style="display: none; ">orgnizationName#Organisation Name#T#a,</span>
	                  <div class="user_form_text1">Organisation Name <font color="red"> *</font>:</div>
	                  <div class="user_form_input inputarea"><s:textfield name="orgnizationName" id="orgnizationName" maxlength="50" placeholder="Enter Organisation Name " cssClass="form_menu_inputtext"/>
	                  </div>
             	  </div>
             	</div>
             	
             	<div class="form_menubox">
             	  <div class="inputmain">
                  <div class="user_form_text">Business Phone:</div>
                  <div class="user_form_input inputarea"><s:textfield name="bPhonenumber" id="bPhonenumber" maxlength="150" placeholder="Business Phone Number" cssClass="form_menu_inputtext"/>
                  </div>
                  </div>
                  <div class="inputmain">
                  <div class="user_form_text1">Job Title:</div>
                  <div class="user_form_input inputarea"><s:textfield name="jobtitle" id="jobtitle" maxlength="50" placeholder="Job Title/Designation" cssClass="form_menu_inputtext"/>
                  </div>
             	  </div>
             	</div>
             	
             	<div class="form_menubox">
             	  <div class="inputmain">
             	   <span id="mandatoryFields" class="pIds" style="display: none; ">jobfunctionalArea#Job Function Area#D#a,</span>
	                  <div class="user_form_text">Job Function Area <font color="red"> *</font>:</div>
	                  <div class="user_form_input inputarea">
	                   		<s:select 
	                               id="jobfunctionalArea"
	                              name="jobfunctionalArea" 
	                              list="jobFunctionalArea" 
	                              listKey="id"
	                              listValue="jobfunction"
	                              headerKey="-1"
	                              headerValue="Job Function Area" 
	                              cssClass="select"
		                          cssStyle="width:82%"
	                              >
	                      </s:select>
	                  </div>
                  </div>
                  <div class="inputmain">
                   <span id="mandatoryFields" class="pIds" style="display: none; ">companysize#Company Size#D#a,</span>
	                  <div class="user_form_text1">Company Size<font color="red"> *</font>:</div>
	                  <div class="user_form_input inputarea">
		                  <s:select 
		                              id="companysize"
		                              name="companysize" 
		                              list="{'Less Then 50','50-99','100-299','300-499','500-999','1000-1499','1500-2499','2500-4999','5000-9999','10000-19999','20000 & Over'}" 
		                              headerKey="-1"
		                              headerValue="Company Employee Size"  
		                              cssClass="select"
		                              cssStyle="width:82%"
		                              >
		                  </s:select>
	                  </div>
                  </div>
             	</div>
             	
             	
             	
             	
             	<div class="form_menubox">
             	  <div class="inputmain">
             	    <span id="mandatoryFields" class="pIds" style="display: none; ">accountType#Account Type#D#a,</span>
	                  <div class="user_form_text">Account Type<font color="red"> *</font>:</div>
	                  <div class="user_form_input inputarea">
	                   <s:select 
	                              id="accountType"
	                              name="accountType" 
	                              list="#{'CAA':'Cloud Associate Account','COA':'Cloud Organization Account','LOA':'Local Organization Account','D':'Demo'}" 
	                              headerKey="-1"
	                              headerValue="Account Type" 
                                  cssClass="select"
	                              cssStyle="width:82%"
	                              >
	                  </s:select>
	                  </div>
                  </div>
                  <div class="inputmain">
                  <span id="mandatoryFields" class="pIds" style="display: none; ">companyType#Organisation Type#D#a,</span>
	                  <div class="user_form_text1">Organisation Type<font color="red"> *</font>:</div>
	                  <div class="user_form_input inputarea">
	                  <s:select 
	                              id="companyType"
	                              name="companyType" 
	                              list="#{'PUL':'Public Limited','PRL':'Private Limited','Pro':'Proprietary','O':'Other'}" 
	                              headerKey="-1"
	                              headerValue="Type Of Organisation" 
                                  cssClass="select"
	                              cssStyle="width:82%"
	                              >
	                  </s:select>
	                  </div>
                  </div>
             	</div>
             	
             	<div class="form_menubox">
             	  <div class="inputmain">
             	   <span id="mandatoryFields" class="pIds" style="display: none; ">industry#Industry#D#a,</span>
	                  <div class="user_form_text">Industry<font color="red"> *</font>:</div>
	                  <div class="user_form_input">
	                   <s:select 
	                              id="industry"
	                              name="industry" 
	                              list="industrylist" 
	                              listKey="industryid"
	                              listValue="industryname"
	                              headerKey="-1"
	                              headerValue="Select Industry" 
	                              multiple="true"
                                  cssClass="form_menu_inputselect"
	                              cssStyle="width:82%"
	                              >
	                  </s:select>
	                 
	                  </div>
                  </div>
                  <div class="inputmain">
                  <span id="mandatoryFields" class="pIds" style="display: none; ">regAddress#Registered Address#T#a,</span>
	                  <div class="user_form_text1">Registered Address<font color="red"> *</font>:</div>
	                  <div class="user_form_input inputarea">
	                   <s:textfield name="regAddress" id="regAddress" maxlength="150" placeholder="Enter Server Ip/Name" cssClass="form_menu_inputtext"/>
	                  </div>
                  </div>
             	</div>
             	
             	<div class="form_menubox">
             	  <div class="inputmain">
             	  <span id="mandatoryFields" class="pIds" style="display: none; ">city#City#T#a,</span>
	                  <div class="user_form_text">City<font color="red"> *</font>:</div>
	                  <div class="user_form_input inputarea"><s:textfield name="city" id="city" maxlength="150" placeholder="Enter City" cssClass="form_menu_inputtext"/>
	                  </div>
                  </div>
                  <div class="inputmain">
                  <span id="mandatoryFields" class="pIds" style="display: none; ">country#Country#D#a,</span>
	                  <div class="user_form_text1">Country<font color="red"> *</font>:</div>
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
             	<div class="form_menubox">
             	  <div class="inputmain">
             	    <span id="mandatoryFields" class="pIds" style="display: none; ">pincode#Pincode#T#n,</span>
                  <div class="user_form_text">Pincode <font color="red"> *</font>:</div>
                  <div class="user_form_input inputarea"><s:textfield name="pincode" id="pincode" maxlength="150" placeholder="Enter Pincode" cssClass="form_menu_inputtext"/>
                  </div>
                  </div>
                  <div class="inputmain">
                  <div class="user_form_text1">Requirements:</div>
                  <div class="user_form_input inputarea"><s:textfield name="brief" id="brief" maxlength="50" placeholder="Enter Requirements" cssClass="form_menu_inputtext"/></div>
             	  </div>
             	</div>
             	
             	<div class="form_menubox">
             	  <div class="inputmain">
                  <div class="user_form_text">Display Code :</div>
                  <div class="user_form_input inputarea"><div class="captcha"><img src="Captcha.jpg" width="79" height="19" alt="Over2Cloud" title="Over2Cloud" /></div></div>
                  </div>
                  <div class="inputmain">
                  <span id="mandatoryFields" class="pIds" style="display: none; ">j_captcha_response#Code#T#an,</span>
                  <div class="user_form_text1">Code <font color="red"> *</font>:</div>
                  <div class="user_form_input inputarea"><s:textfield name="j_captcha_response" id="j_captcha_response" maxlength="50" placeholder="Enter Display Code" cssClass="form_menu_inputtext"  onblur="return oncheckcapacha('j_captcha_response');" />
                  </div>
             	  </div>
             	</div>
             	
             <div style=" font-size:12px;color:#ce2525; text-align:center;" class="sub_field_main">
					<img id="indicator2" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/>
                    <div id="userStatus"> </div>
                    <s:hidden id="status" name="status"> </s:hidden>
			 </div>
             	
             	
             	
             	
				<!-- Buttons -->
		           	<div class="clear"></div>
                   <div style="width: 100%; text-align: center; padding-bottom: 10px;">
	                <img id="indicator1" src="<s:url value="/images/ajax-loader.gif"/>" alt="Loading..."   style="display:none"/>
	                
	                 <sj:submit 
                                targets="Result" 
							    clearForm="true"
								id="submit"
								value="Register" 
								href="%{AddRegistation_Url}"
								effect="foldeffect"
								effectOptions="{float:'right'}"
								effectDuration="5000"
								cssClass="submit"
								button="true"
								onCompleteTopics="complete"
								indicator="indicator1"
								onBeforeTopics="validate"
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