<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>::: Over 2 Cloud :::</title>


<link href="css/colorbox.css" rel="stylesheet" type="text/css" media="all" />
<link href="css/style.css" rel="stylesheet" type="text/css" />
<link href="css/login-style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/jquery.min.js"></script>

<script type="text/javascript">
$(document).ready(function() {
	$(".username").focus(function() {
		$(".user-icon").css("left","-48px");
	});
	$(".username").blur(function() {
		$(".user-icon").css("left","0px");
	});
	
	$(".password").focus(function() {
		$(".pass-icon").css("left","-48px");
	});
	$(".password").blur(function() {
		$(".pass-icon").css("left","0px");
	});
});
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
<style>
.errorMessage {
color: red;
font-size: 0.8em;
} 
.label {
color:black;
}
</style>

</head>
<body>
<div class="maincontainer">
 
 <div class="clear"></div>

<div style="float:left; font-size:26px; width:98%; padding:2% 1% 2% 1%; color: #FFF;"></div>

<div class="clear"></div>

<div class="middle-content">

<div id="container-login">

	<!--SLIDE-IN ICONS--><!--
    <div class="user-icon"></div>
    <div class="pass-icon"></div>
    --><!--END SLIDE-IN ICONS-->

<!--LOGIN FORM-->
<s:form name="loginform" cssClass="login-form" id="loginform" action="loginaction"  theme="css_xhtml" theme="simple"  method="post">
	<!--HEADER-->
    <div class="header" style="font-family: inherit;font-size: 24px;">Over2Cloud Login
    <div class="clear" style="height:10px;"></div>
    </div>
    <!--END HEADER-->
	
	<!--CONTENT-->
	
    <div class="content">
    	<!--ACCOUNT ID    input username -->
	    	<s:textfield name="accountid" id="accountid" value="IN-4"  readonly="false" cssClass="input username" maxlength="20" onfocus="javascript:if(this.placeholder=='Account ID') this.placeholder = '';"   onblur="javascript:if(this.placeholder=='') this.placeholder = 'Account ID';"   placeholder="Account ID"></s:textfield>
	    <!--ACCOUNT ID-->
    	<!--USERNAME-->
    		<s:textfield name="username" id="username" cssClass="input password" maxlength="30" onfocus="javascript:if(this.placeholder=='Username') this.placeholder = '';"   onblur="javascript:if(this.placeholder=='') this.placeholder = 'Username';"   placeholder="Username"></s:textfield>
    	<!--END USERNAME-->
    	
	    <!--PASSWORD-->
	    	<s:password name="password" id="password" cssClass="input password" maxlength="30" onfocus="javascript:if(this.placeholder=='Password') this.placeholder = '';"   onblur="javascript:if(this.placeholder=='') this.placeholder = 'Password';"   placeholder="Password"/>
	    <!--END PASSWORD-->
	    
	    
    </div>
    <!--END CONTENT-->
    
    <!--FOOTER-->
    <div class="footer-2" >
    <!--LOGIN BUTTON--><input  type="submit" name="submit" value="Login" class="button" /><!--END LOGIN BUTTON-->
    <!--REGISTER BUTTON-->
    <!-- <a class="booking" href="registerUser.action"><input type="submit" name="Submit" value="Sign Up" class="button" style="float:left" /></a> -->
    <font color="red" face="verdana"><s:property value="%{errorMessege}"/></font>
   <div class="clear"></div>
 
   <div class="clear"></div>
   <a class="booking" href="fgtPassword.action">Forgot your password?</a>
    </div>
   
    <!--END FOOTER-->

 </s:form>
<!--END LOGIN FORM-->

</div>

<div class="gradient"></div>

</div>

<div class="clear"></div>

</div>
<a href="" target="new"><img src="images/login.jpg" style="
    margin-top: 59px;
    width: 100%;
    margin-left: -101%;
" /></a>
<script type='text/javascript' src='js/jquery4.js'></script>
<script type='text/javascript' src='js/jquery.colorbox.js'></script>
<script>
			$(document).ready(function(){
				//Examples of how to assign the ColorBox event to elements
				$(".booking").colorbox({rel:'booking', transition:"elastic",width:"58%"});
								
			});
</script>

</body>
</html>