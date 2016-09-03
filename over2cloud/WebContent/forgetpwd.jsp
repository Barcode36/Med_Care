<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>

<meta   http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link   type="text/css" href="<s:url value="/css/style.css"/>" rel="stylesheet" />
<link   type="text/css" href="<s:url value="/css/validation.css"/>" rel="stylesheet" />
<script type="text/javascript" src="<s:url value="/js/js.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/showhide.js"/>"></script>
<script src="js/jquery.js"></script>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>

<link rel="stylesheet" type="text/css" href="css/stylesheet.css" />
<link href="http://fonts.googleapis.com/css?family=Open+Sans" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="JavaScript/show_hide.js"></script>

<style type="text/css">
.pop_up_content_inner_sub h1{
	font-size:14px; font-weight:bold; text-align:center; padding:0px 0px 20px 0px;
}
.pop_up_submit_here{
	background: url("images/submit_here.png") no-repeat scroll 0 0 transparent;
    float: left;
    height: 38px;
    transition: background 0.15s ease-in-out 0s;
    width: 106px;
}
.pop_up_submit_here:hover{
	background: url("images/submit_here.png") no-repeat scroll 0 -38px transparent;
}
.pop_up_submit_here input{
	width:106px; height:38px; cursor:pointer; background:none; border:none;
}
.popoupdiv{
	height:auto!important;
}

#pop_up_form{
	height:100px; overflow:auto; padding:0px 0px 20px 0px;
}
.sub_field_main input.field{
	padding:2px 4px;
}
.sub_field_main input.validity{
	background: none repeat scroll 0 0 #F5F9FC;
    border: 1px solid #64A4D2;
    border-radius: 5px 5px 5px 5px;
    color: #222222;
    font-family: 'Open Sans',sans-serif;
    font-size: 13px;
    padding:2px 4px; width:100px;
}
</style>

<script type="text/javascript">
$.subscribe('complete', function(event,data)
        {
         
        });

function toggle_me(){
	// on Submit Java Script Done For Client Side.....
	var sub_disable1	=	document.getElementById("disableAccountid");
	sub_disable.style.display	=	"none";
}

</script>
<script language="javascript" src="JavaScript/selectBox.js"></script>
<script type="text/javascript" language="javascript" src="JavaScript/pop_up.js"></script>
<link rel="stylesheet" type="text/css" href="css/pop_up.css" />
</head>
<body onload="fadePopIn();" >
<div class="overlay_shadow" id="overlay_shadow"></div>
<div id="Result">
<s:form name="productregistation"  id="ForgetPassword_url" action="forgetpassword"  theme="css_xhtml" theme="simple"  method="post">
<div class="lightbox">
	<div class="popoupdiv" style=" width:455px;">
		<div class="pop_content">
			<div class="pop_content_block"><img src="images/pop_up_top.png" width="744" height="20" alt="" title="" /></div>
			<div class="pop_up_content_inner">
			<div class="pop_up_content_inner_sub">
				<h1>Given Below Details</h1>
					<div class="pop_text"><p>following details is required for retrieving the password.</p></div>
					<div class="pop_up_form">
						<div class="sub_field_main" style=" width:100%;">
						 <s:textfield name="accountid" cssClass="field" maxlength="50" onfocus="javascript:if(this.placeholder=='Account ID') this.placeholder = '';" onblur="javascript:if(this.placeholder=='') this.placeholder = 'Account ID';" placeholder="Account ID" />
						</div>
						<div class="sub_field_main" style=" width:100%;">
						<s:password name="newpasswordc" id="newpasswordc" cssClass="field" maxlength="50" onfocus="javascript:if(this.placeholder=='New Password') this.placeholder = '';" onblur="javascript:if(this.placeholder=='') this.placeholder = 'New Password';" placeholder="New Password" />
						</div>
						<div class="sub_field_main" style=" width:100%;">
						<s:password name="confirmpassword" id="confirmpassword" cssClass="field" maxlength="50" onfocus="javascript:if(this.placeholder=='Confirm Password') this.placeholder = '';" onblur="javascript:if(this.placeholder=='') this.placeholder = 'Confirm Password';" placeholder="Confirm Password" />
						</div>
					</div>
					<div class="pop_up_forgot"><a href="javascript:void();">problems receiving the email !!! <span>click here</span></a></div>
					<div class="sub_field_main" style="text-align:center;"><img id="indicator2" src="<s:url value="/images/ajax-loader.gif"/>" alt="Loading..."  width="70%"  style="display:none"/></div>
					<div class="pop_up_submit">
                        <div class="pop_up_submit_here closepopup"><input type="submit" name="" value="" /></div>
						<div class="pop_up_reg">
						 <sj:submit 
                              targets="Result" 
                              clearForm="true"
                              id="form_submit"
                              value="" 
                              href="%{ForgetPassword_url}"
                              effect="highlight"
                              cssClass="form_submit"
                              effectOptions="{float:'right'}"
                              effectDuration="5000"
                              button="true"
                              onCompleteTopics="complete;"
                              onBeforeTopics="validate"
                              onclick="javascript:toggle_me();"
                              indicator="indicator2"
                            />
						</div>     
					</div>
				</div>
			</div>
			<div class="pop_content_block"><img src="images/pop_up_bottom.png" width="744" height="18" alt="" title="" /></div>
		</div>
	</div>
</div>
</s:form>
 </div>
</body>
</html>