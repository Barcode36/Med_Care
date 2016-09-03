<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sj" uri="/struts-jquery-tags" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<link href="css/style.css" rel="stylesheet" type="text/css" media="all" />

<script type="text/javascript">
	function toggle(){
		var	tabular1				=	document.getElementById("login_click");
		var tabular2				=	document.getElementById("register_click");
		tabular1.style.display		=	"none";
		tabular2.style.display		=	"block";	
		var	sub_content1			=	document.getElementById("login");
		var	sub_content2			=	document.getElementById("register");
		sub_content1.style.display	=	"block";
		sub_content2.style.display	=	"none";
	}
	function toggle1(){
		var	tabular1				=	document.getElementById("login_click");
		var tabular2				=	document.getElementById("register_click");
		tabular1.style.display		=	"block";
		tabular2.style.display		=	"none";	
		var	sub_content1			=	document.getElementById("login");
		var	sub_content2			=	document.getElementById("register");
		sub_content1.style.display	=	"none";
		sub_content2.style.display	=	"block";
	}
	function toggle_me(){
		// on Submit Java Script Done For Client Side.....
		var sub_disable1	=	document.getElementById("form_submit");
		var sub_disable		=	document.getElementById("login_button");
		sub_disable.style.display	=	"none";
		sub_disable1.style.display	=	"none";
	}
</script>

<script type="text/javascript">


function validate()
{
document.getElementById("indicator2").style.display="block";
loginform.submit;
 }



function oncheckcapacha(jsapercode)
{
	
		var capchavalue=document.getElementById(jsapercode).value;
		document.getElementById("indicator2").style.display="block";
	     $.getJSON("/over2cloud/doCaptcha.action?j_captcha_response="+capchavalue,
			function(capchadata){
				 $("#userStatus").html(capchadata.msg);
				 document.getElementById("indicator2").style.display="none";
				 document.getElementById("status").value=capchadata.status;
	    });
}
$.subscribe('complete', function(event,data)
	    {
	        setTimeout(function(){ $("#foldeffect").fadeIn(); }, 1000);
	        setTimeout(function(){ $("#foldeffect").fadeOut(); }, 4000);
	    });

$.subscribe('complete1', function(event,data)
	    {
	        setTimeout(function(){ $("#foldeffect").fadeIn(); }, 1000);
	        setTimeout(function(){ $("#foldeffect").fadeOut(); }, 4000);
	    });


</script>
<style>
.errorMessage {
color: red;
font-size: 0.8em;
} 
.label {
color:white;
}
</style>
</head>

<body>
<div style="height:auto; width:auto; float:left;">

  <s:form name="formone" id="formOne" id="AddRegistation_Url" action="donewUserRegistation"  theme="css_xhtml" theme="simple"  method="post">
<div class="contact-box2">
<div class="headdings-inner" style="margin-top:0px;">Registration</div>
<div class="clear"></div>

  <div class="input-main-container2"> 
<div class="input-name2">Name : </div>
<div class="input-container2">
	<s:textfield name="regName" id="regName" cssClass="input4" maxlength="50" onfocus="javascript:if(this.placeholder=='Name') this.placeholder = '';"  onblur="javascript:if(this.placeholder=='') this.placeholder = 'Name';"  placeholder="Name"></s:textfield>
</div>
</div>
<div class="clear"></div>
<div class="input-main-container2" style="margin-bottom:0px"> 
<div class="input-name2">Contact No. :</div>
<div class="input-container2">
  <div style="float:left; width:109px; margin-right:7px;">
  	<s:textfield name="mobile" id="mobile" cssStyle="width:105px;" cssClass="input4" maxlength="50" onfocus="javascript:if(this.placeholder=='Mobile') this.placeholder = '';"  onblur="javascript:if(this.placeholder=='') this.placeholder = 'Mobile';"  placeholder="Mobile"></s:textfield>
  </div>
  <div style="float:left; width:110px">
  	<s:textfield name="bPhonenumber" id="bPhonenumber" cssStyle="width:105px;" cssClass="input4" maxlength="50" onfocus="javascript:if(this.placeholder=='Business Phone Number') this.placeholder = '';"  onblur="javascript:if(this.placeholder=='') this.placeholder = 'Business Phone Number';"   placeholder="Business Phone Number"></s:textfield>
  </div>
  
  <div style="float:left; width:111px; padding-right:5px; font-size:11px;">Mobile</div>
  <div style="float:left; width:110px; font-size:11px;">Business Phone No.</div>
  
</div>

</div>
<div class="clear"></div>

<div class="input-main-container2"> 
<div class="input-name2">Email Address : </div>
<div class="input-container2">
	<s:textfield name="emailaddress" id="emailaddress" cssClass="input4" maxlength="50" onfocus="javascript:if(this.placeholder=='Email Address') this.placeholder = '';"  onblur="javascript:if(this.placeholder=='') this.placeholder = 'Email Address';"   placeholder="Email Address"></s:textfield>
</div>
</div>
<div class="clear"></div>

<div class="input-main-container2"> 
<div class="input-name2">Job Title / Designation : </div>
<div class="input-container2">
	<s:textfield name="jobtitle" id="jobtitle" cssClass="input4" maxlength="50" onfocus="javascript:if(this.placeholder=='Job Title/Designation') this.placeholder = '';"  onblur="javascript:if(this.placeholder=='') this.placeholder = 'Job Title/Designation';"  placeholder="Job Title/Designation"></s:textfield>
</div>
</div>
<div class="clear"></div>


<div class="input-main-container2"> 
<div class="input-name2"> Your Organisation Name : </div>
<div class="input-container2">
	<s:textfield name="orgnizationName" id="orgnizationName" cssClass="input4" maxlength="50" onfocus="javascript:if(this.placeholder=='Name of your organisation') this.placeholder = '';"  onblur="javascript:if(this.placeholder=='') this.placeholder = 'Name of your organisation';"   placeholder="Name of your organisation"></s:textfield>
</div>
</div>
<div class="clear"></div>

<div class="input-main-container2"> 
<div class="input-name2">Choose your account type :</div>
<div class="input-container2">
	<s:select 
      id="accountType"
      name="accountType" 
      list="#{'CAA':'Cloud Associate Account','COA':'Cloud Organization Account','LOA':'Local Organization Account','D':'Demo'}" 
      headerKey="-1"
      headerValue="Please Choose Your Account Type" 
      cssClass="input4"
      cssStyle="width:232px; height:29px; line-height:29px;"
      >
	</s:select>
</div>
</div>

<div class="clear"></div>

<div class="input-main-container2"> 
<div class="input-name2">Your Registered Address  :</div>
<div class="input-container2">
	<s:textfield name="regAddress" id="regAddress" cssClass="input4" maxlength="50" onfocus="javascript:if(this.placeholder=='Your Registered Address') this.placeholder = '';"  onblur="javascript:if(this.placeholder=='') this.placeholder = 'Your Registered Address';"   placeholder="Your Registered Address"></s:textfield>
</div>
</div>
<div class="clear"></div>

<div class="input-main-container2" style="margin-bottom:0px"> 
<div class="input-name2"></div>
<div class="input-container2">
  <div style="float:left; width:109px; margin-right:7px;"><s:textfield name="city" id="city" cssClass="input4" maxlength="50" onfocus="javascript:if(this.placeholder=='City') this.placeholder = '';"  onblur="javascript:if(this.placeholder=='') this.placeholder = 'City';"  placeholder="City"></s:textfield></div>
  <div style="float:left; width:110px"><s:textfield name="pincode" cssStyle="width:105px;" id="pincode" cssClass="input4" maxlength="6" onfocus="javascript:if(this.placeholder=='PinCode') this.placeholder = '';"   onblur="javascript:if(this.placeholder=='') this.placeholder = 'PinCode';"   placeholder="PinCode"></s:textfield></div>
  
  <div style="float:left; width:111px; padding-right:5px; font-size:11px;">City</div>
  <div style="float:left; width:110px; font-size:11px;">Pin Code</div>
  
</div>

</div>
<div class="clear"></div>


<div class="input-main-container2"> 
<div class="input-name2">Country :</div>
<div class="input-container2">

	<s:select 
        id="country"
        name="country" 
        list="countrylist"
        listKey="iso_code"
        listValue="contryName"
        headerKey="-1"
        headerValue="Country"
        cssClass="input4"
        cssStyle="width:232px; height:29px; line-height:29px;" 
        >
  </s:select>
   
</div>
</div>
<div class="clear"></div>

<div class="input-main-container2"> 
<div class="input-name2">Company Employee Size :</div>
<div class="input-container2">
	<s:select 
        id="companysize"
        name="companysize" 
        list="{'Less Then 50','50-99','100-299','300-499','500-999','1000-1499','1500-2499','2500-4999','5000-9999','10000-19999','20000 & Over'}" 
        headerKey="-1"
        headerValue="Company Employee Size"
        cssClass="input4"
        cssStyle="width:232px; height:29px; line-height:29px;" 
        >
    </s:select>
</div>
</div>

<div class="clear"></div>

<div class="input-main-container2" style="margin-bottom:0px"> 
<div class="input-name2"></div>
<div class="input-container2">
  <div style="float:left; width:109px; margin-right:7px;">
  	<div class="captcha"><img src="Captcha.jpg" width="79" height="19" alt="Over2Cloud" title="Over2Cloud" /></div>
  </div>
  <div style="float:left; width:110px"><s:textfield name="j_captcha_response"  size="10" maxlength="10" id="j_captcha_response" cssClass="input4"  onfocus="javascript:if(this.placeholder=='Enter the code as shown') this.placeholder = '';" onblur="javascript:if(this.placeholder=='') this.placeholder = 'Enter the code as shown';" placeholder="Enter the code as shown" onblur="return oncheckcapacha('j_captcha_response');" cssStyle="width:105px;"/></div>
  
  <div style="float:left; width:111px; padding-right:5px; font-size:11px;">Captcha</div>
  <div style="float:left; width:110px; font-size:11px;">Enter the code</div>
  
</div>

</div>

<div class="clear"></div>

<div class="input-main-container2"> 
<div class="input-name2">I accept Terms & Conditions :</div>
<div class="input-container2">
   <s:checkbox id="ageryterm" name="ageryterm" value="yes"/>
</div>
</div>
<div class="clear"></div>

<div class="input-main-container2"> 
<div class="input-name2"></div>
<div class="input-container2 signup ">
	<sj:submit
		 targets="Result" 
         clearForm="true"
         id="form_submit"
         value="Submit" 
         href="%{AddRegistation_Url}"
         effect="highlight"
         cssClass="button"
         effectOptions="{float:'right'}"
         effectDuration="5000"
         button="true"
         onCompleteTopics="complete;"
         onclick="javascript:toggle_me();"
         indicator="indicator1"
         onBeforeTopics="validate"
		>
	</sj:submit>
  	<input type="button" name="refresh" value="Refresh" class="button" style="float:left" />
</div>

</div>
<div class="clear"></div>
<div class="sub_field_main" style="text-align:center;"><img id="indicator1" src="<s:url value="/images/ajax-loader.gif"/>" alt="Loading..."   style="display:none"/></div>
<div class="clear"></div>
	<div style=" font-size:12px;color:#ce2525; text-align:center;" class="sub_field_main">
	<img id="indicator2" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/>
		<div id="userStatus"> </div>
		<s:hidden id="status" name="status"> </s:hidden>
	</div>
</div>
  </s:form>
</div>

</body>
</html>
