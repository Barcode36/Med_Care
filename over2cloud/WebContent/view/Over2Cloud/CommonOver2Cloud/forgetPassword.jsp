<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>

<html>

<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<SCRIPT type="text/javascript" src="js/commanValidation/validation.js"></SCRIPT>
<script type="text/javascript" src="<s:url value="/js/tabConfig/tabAdd.js"/>"></script>
<script src="<s:url value="/js/organization.js"/>"></script>
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



function ffffffff()
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

$.subscribe('level1', function(event,data)
	    {
	        setTimeout(function(){ $("#orglevel1Div").fadeIn(); }, 1000);
	        setTimeout(function(){ $("#orglevel1Div").fadeOut(); }, 4000);
	    });


$.subscribe('complete1', function(event,data)
	    {
	        setTimeout(function(){ $("#foldeffect").fadeIn(); }, 1000);
	        setTimeout(function(){ $("#foldeffect").fadeOut(); }, 4000);
	    });


</script>
<SCRIPT type="text/javascript">



$.subscribe('thirdValidate', function(event,data)
{
var mystring = $(".aspIds").text();
var fieldtype = mystring.split(",");
var pattern = /^\d{10}$/;
for(var i=0; i<fieldtype.length; i++)
{
	
	var fieldsvalues = fieldtype[i].split("#")[0];
	var fieldsnames = fieldtype[i].split("#")[1];
	var colType = fieldtype[i].split("#")[2];   //fieldsType[i]=first_name#t
	var validationType = fieldtype[i].split("#")[3];
      
	$("#"+fieldsvalues).css("background-color","");
	aserrZone.innerHTML="";
	if(fieldsvalues!= "" )
	{
	    if(colType=="D"){
	    if($("#"+fieldsvalues).val()=="" || $("#"+fieldsvalues).val()=="-1")
	    {
	    aserrZone.innerHTML="<div class='user_form_inputError2'>Please Select "+fieldsnames+" Value from Drop Down </div>";
        setTimeout(function(){ $("#errsaserrZone.fadeIn(); ")}, 10);
	    setTimeout(function(){ $("#aserrZone").fadeOut(); }, 2000);
        $("#"+fieldsvalues).focus();
        $("#"+fieldsvalues).css("background-color","#ff701a");
        event.originalEvent.options.submit = false;
		break;	
		  }
		}
		else if(colType =="T"){
		if(validationType=="n"){
		var numeric= /^[0-9]+$/;
		if(!(numeric.test($("#"+fieldsvalues).val()))){
		aserrZone.innerHTML="<div class='user_form_inputError2'>Please Enter Numeric Values of "+fieldsnames+"</div>";
        setTimeout(function(){ $("#aserrZone").fadeIn(); }, 10);
	    setTimeout(function(){ $("#aserrZone").fadeOut(); }, 2000);
        $("#"+fieldsvalues).focus();
        $("#"+fieldsvalues).css("background-color","#ff701a");
        event.originalEvent.options.submit = false;
        break;
          }	
	     }
		else if(validationType=="an"){
	     var allphanumeric = /^[A-Za-z0-9 -]{3,20}$/;
		if(!(allphanumeric.test($("#"+fieldsvalues).val()))){
		aserrZone.innerHTML="<div class='user_form_inputError2'>Please Enter Alpha Numeric of "+fieldsnames+"</div>";
        setTimeout(function(){ $("#aserrZone").fadeIn(); }, 10);
	    setTimeout(function(){ $("#aserrZone").fadeOut(); }, 2000);
        $("#"+fieldsvalues).focus();
        $("#"+fieldsvalues).css("background-color","#ff701a");
        event.originalEvent.options.submit = false;
        break;
          }
		}
		else if(validationType=="a"){
		if(!(/^[a-zA-Z ]+$/.test($("#"+fieldsvalues).val()))){
	    aserrZone.innerHTML="<div class='user_form_inputError2'>Please Enter Alphabate Values of "+fieldsnames+"</div>";
        setTimeout(function(){ $("#aserrZone").fadeIn(); }, 10);
	    setTimeout(function(){ $("#aserrZone").fadeOut(); }, 2000);
        $("#"+fieldsvalues).focus();
        $("#"+fieldsvalues).css("background-color","#ff701a");
        event.originalEvent.options.submit = false;
        break;     
          }
		 }
		else if(validationType=="m"){
	   if($("#"+fieldsvalues).val().length > 10 || $("#"+fieldsvalues).val().length < 10)
		{
			aserrZone.innerHTML="<div class='user_form_inputError2'>Enter 10 digit mobile number ! </div>";
			$("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
			$("#"+fieldsvalues).focus();
			setTimeout(function(){ $("#aserrZone").fadeIn(); }, 10);
	        setTimeout(function(){ $("#aserrZone").fadeOut(); }, 2000);
	        event.originalEvent.options.submit = false;
			break;
		}
	    else if (!pattern.test($("#"+fieldsvalues).val())) {
		    aserrZone.innerHTML="<div class='user_form_inputError2'>Please Entered Valid Mobile Number </div>";
			$("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
			$("#"+fieldsvalues).focus();
			setTimeout(function(){ $("#aserrZone").fadeIn(); }, 10);
	        setTimeout(function(){ $("#aserrZone").fadeOut(); }, 2000);
	        event.originalEvent.options.submit = false;
			break;
		 }
			else if(($("#"+fieldsvalues).val().charAt(0)!="9") && ($("#"+fieldsvalues).val().charAt(0)!="8") && ($("#"+fieldsvalues).val().charAt(0)!="7") && ($("#"+fieldsvalues).val().charAt(0)!="6"))
		 {
			aserrZone.innerHTML="<div class='user_form_inputError2'>Entered Mobile Number Started with 9,8,7,6.  </div>";
			$("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
			$("#"+fieldsvalues).focus();
			setTimeout(function(){ $("#aserrZone").fadeIn(); }, 10);
	        setTimeout(function(){ $("#aserrZone").fadeOut(); }, 2000);
	        event.originalEvent.options.submit = false;
			break;
		   }
	     } 
		 else if(validationType =="e"){
	     if (/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test($("#"+fieldsvalues).val())){
	     }else{
	    aserrZone.innerHTML="<div class='user_form_inputError2'>Please Enter Valid Email Id ! </div>";
	    $("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
		$("#"+fieldsvalues).focus();
		setTimeout(function(){ $("#aserrZone").fadeIn(); }, 10);
	    setTimeout(function(){ $("#aserrZone").fadeOut(); }, 2000);
	    event.originalEvent.options.submit = false;
		break;
		   } 
	     }
	     else if(validationType =="w"){
	     
	     
	     
	     }
	   }
	   
		else if(colType=="TextArea"){
		if(validationType=="an"){
	    var allphanumeric = /^[A-Za-z0-9 ]{3,20}$/;
		if(!(allphanumeric.test($("#"+fieldsvalues).val()))){
		aserrZone.innerHTML="<div class='user_form_inputError2'>Please Enter Alpha Numeric of "+fieldsnames+"</div>";
        setTimeout(function(){ $("#aserrZone").fadeIn(); }, 10);
	    setTimeout(function(){ $("#aserrZone").fadeOut(); }, 2000);
        $("#"+fieldsvalues).focus();
        $("#"+fieldsvalues).css("background-color","#ff701a");
        event.originalEvent.options.submit = false;
        break;
           }
		 }
		else if(validationType=="mg"){
		 
		 
		 }	
		}
		else if(colType=="Time"){
		if($("#"+fieldsvalues).val()=="")
	    {
	    aserrZone.innerHTML="<div class='user_form_inputError2'>Please Select Time "+fieldsnames+" Value </div>";
        setTimeout(function(){ $("#aserrZone").fadeIn(); }, 10);
	    setTimeout(function(){ $("#aserrZone").fadeOut(); }, 2000);
        $("#"+fieldsvalues).focus();
        $("#"+fieldsvalues).css("background-color","#ff701a");
        event.originalEvent.options.submit = false;
		break;	
		  }	
		}
		else if(colType=="Date"){
		if($("#"+fieldsvalues).val()=="")
	    {
	    aserrZone.innerHTML="<div class='user_form_inputError2'>Please Select Date "+fieldsnames+" Value  </div>";
        setTimeout(function(){ $("#aserrZone").fadeIn(); }, 10);
	    setTimeout(function(){ $("#aserrZone").fadeOut(); }, 2000);
        $("#"+fieldsvalues).focus();
        $("#"+fieldsvalues).css("background-color","#ff701a");
        event.originalEvent.options.submit = false;
		break;	
		  }
		 }  
	}
}		
	
});


</SCRIPT>


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

  <s:form name="formone" id="formOne" id="AddRegistation_Url" action="newPassReq"  theme="css_xhtml" theme="simple"  method="post">
  
<div class="contact-box2">
<div class="headdings-inner" style="margin-top:0px; margin-left: 191px;" align="center">Forgot Password</div>
<div class="clear"></div>
<center><div style="float:center; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;"><div id="aserrZone" style="float:center; margin-left: 7px"></div></div></center>
  <br><br><div class="clear"></div>  <div class="clear"></div>  <div class="clear"></div>  <div class="clear"></div>
  <div class="input-main-container2"> 
<div class="input-name2" style="margin-left: -2px;">Account ID : </div>
<span id="hhhhh" class="aspIds" style="display: none; ">accountID#accountID#T#an#,</span>
<div class="input-container2"> 
	<s:textfield name="accountID" id="accountID" value="IN-1" readonly="true" cssClass="input4" maxlength="50"  placeholder="Account ID"></s:textfield>
</div>
</div>
<div class="clear"></div>
<div class="input-main-container2"> 
<div class="input-name2" style="margin-left: -2px;">Username : </div>
<span id="hhhhh" class="aspIds" style="display: none; ">userName#userName#T#a#,</span>
<div class="input-container2">
	<s:textfield name="userName" id="userName" cssClass="input4" maxlength="50"  placeholder="User Name"></s:textfield>
</div>
</div>
<div class="clear"></div>
<div class="input-main-container2"> 
<div class="input-name2" style="margin-left: -2px;"> Mobile No : </div>
<span id="hhhhh" class="aspIds" style="display: none; ">mobileNo#mobileNo#T#m#,</span>
<div class="input-container2">
	<s:textfield name="mobileNo" id="mobileNo" cssClass="input4" maxlength="50"     placeholder="Enter MobileNo"></s:textfield>
</div>
</div>
                                               
<div class="input-main-container2"> 
<div class="input-name2" style="margin-left: 336px;"> OR  </div>
<div class="input-main-container2"> 
<div class="input-name2" style="margin-left: -2px;"> Email ID : </div>
<span id="hhhhh" class="aspIds" style="display: none; ">emailId#emailId#T#e#,</span>
<div class="input-container2">
	<s:textfield name="emailId" id="emailId" cssClass="input4" maxlength="50"     placeholder="Enter Email Id"></s:textfield>
</div>
</div>
</div>

<div class="input-main-container2" style="margin-bottom:0px"> 
<div class="input-name2"></div>
<div class="input-container2">
  <div style="float:left; width:109px; margin-right:7px;">
  	<div class="captcha"><img src="Captcha.jpg" width="79" height="25" alt="Over2Cloud" title="Over2Cloud" style="margin-left: -2px;" /></div>
  </div>
  <div style="float:left; width:110px"><s:textfield name="j_captcha_response"  size="10" maxlength="10" id="j_captcha_response"  cssClass="input4"  onfocus="javascript:if(this.placeholder=='Enter the code as shown') this.placeholder = '';" onblur="javascript:if(this.placeholder=='') this.placeholder = 'Enter the code as shown';" placeholder="Enter the code as shown" onblur="return oncheckcapacha('j_captcha_response');" cssStyle="width:135px; margin-left:-34px"/></div><!--
  
  <div style="float:left; width:111px; padding-right:5px; font-size:11px;">Captcha</div>
  <div style="float:left; width:110px; font-size:11px;">Enter the code</div>
  
--></div>

</div>
<br><br><br><br>
     
 <div class="clear"></div>
<div class="sub_field_main" style="text-align:center;"><img id="indicator1" src="<s:url value="/images/ajax-loader.gif"/>" alt="Loading..."   style="display:none"/></div>
<div class="clear"></div>
	<div style=" font-size:12px;color:#ce2525; text-align:center;" class="sub_field_main">
	<img id="indicator2" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/>
		<div id="userStatus" style="margin-left: 199px;"> </div>
		<s:hidden id="status" name="status"> </s:hidden>
	</div>



<br><br><br><br>
<div class="clear"></div>

<div class="input-main-container2"> 
<div class="input-name2"></div>
<div class="input-container2 signup ">
	<sj:submit
		 targets="orglevel1" 
         clearForm="true"
         id="form_submit"
         value="Submit" 
         href="%{AddRegistation_Url}"
         effect="highlight"
         cssClass="button"
         effectOptions="{float:'right'}"
         effectDuration="5000"
         button="true"
         onCompleteTopics="level1"
         indicator="indicator1"
         onBeforeTopics="thirdValidate"
		>
	</sj:submit>
</div>

</div><sj:div id="orglevel1Div"  effect="fold">
      <div id="orglevel1"></div>
               </sj:div>
          
<br><br><br><br><br><br>
	
</div>
  </s:form>
</div>

</body>
</html>
