
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>::: Over 2 Cloud : Second Step :::</title>
<link rel="stylesheet" type="text/css" href="css/stylesheet.css" />
<link href="http://fonts.googleapis.com/css?family=Open+Sans" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="JavaScript/show_hide.js"></script>
<link rel="stylesheet" type="text/css" href="css/pop_up.css" />
<style type="text/css">
.pop_up_content_inner_sub h1{
	font-size:14px; font-weight:bold; text-align:center; padding:0px 0px 20px 0px;
}
.pop_up_submit_here{
	background: url("/images/submit_here.png") no-repeat scroll 0 0 transparent;
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
</style>

<!-- Second Step script starts here -->


<!-- Second step ends here -->
</head>
<body>
<div id="Result1">
<s:form name="clientInformationAdd"  id="AddUserInfo_url" action="demoUserPwdChage"  theme="css_xhtml" theme="simple"  method="post">
<s:hidden id="tempLAAOAAIAAD" name="tempLAAOAAIAAD" value="%{tempLAAOAAIAAD}"> </s:hidden>
<s:hidden id="tempAAPAAWAAD" name="tempAAPAAWAAD" value="%{tempAAPAAWAAD}"> </s:hidden>
<s:hidden id="tempAAAAACAAOAAUAANAANOAA" name="tempAAAAACAAOAAUAANAANOAA" value="%{tempAAAAACAAOAAUAANAANOAA}"> </s:hidden>
<div class="lightbox" style="left:28%;">
	<div class="popoupdiv" style="width: 715px; height: 48%;">
		<div class="pop_content"  style="width: 715px;">
			<div class="pop_content_block"><img src="images/pop_up_top.png" width="744" height="20" alt="" title="" /></div>
			<div class="pop_up_content_inner" style="width: 715px;">
				<div class="pop_up_content_inner_sub" style="width: 715px;">
				<h1>Account Id & Login Details</h1>
					<div class="pop_text"><p>Please enter the following details that we have mailed to your registered email address.</p></div>
					<div class="pop_up_form">
						<div class="sub_field_main" style=" width:100%;">
						 <s:textfield  value="%{tempAAAAACAAOAAUAANAANOAA}                 [Account Id]" cssClass="field" maxlength="50" onfocus="javascript:if(this.placeholder=='Account ID') this.placeholder = '';" onblur="javascript:if(this.placeholder=='') this.placeholder = 'Account ID';" placeholder="Account ID" readonly="true" />
						</div>
						<div class="sub_field_main" style=" width:100%;">
						<s:textfield   value="%{tempLAAOAAIAAD}                      [Auto Username]" cssClass="field" maxlength="50" onfocus="javascript:if(this.placeholder=='Username') this.placeholder = '';" onblur="javascript:if(this.placeholder=='') this.placeholder = 'Username';" placeholder="Username" readonly="true" />
						</div>
						<div class="sub_field_main" style=" width:100%;">
						<s:textfield  value="%{tempAAPAAWAAD}                  [Auto Password]" cssClass="field" maxlength="50" onfocus="javascript:if(this.placeholder=='Password') this.placeholder = '';" onblur="javascript:if(this.placeholder=='') this.placeholder = 'Password';" placeholder="Password" readonly="true" />
						</div>
						<div class="sub_field_main" style=" width:100%;">
						<s:textfield name="newusername" id="newusername" cssClass="field" maxlength="50" onfocus="javascript:if(this.placeholder=='New Username') this.placeholder = '';" onblur="javascript:if(this.placeholder=='') this.placeholder = 'New Username';" placeholder="New Username" />
						</div>
						<div class="sub_field_main" style=" width:100%;">
						<s:password name="newpassword" id="newpassword" cssClass="field" maxlength="50" onfocus="javascript:if(this.placeholder=='New Password') this.placeholder = '';" onblur="javascript:if(this.placeholder=='') this.placeholder = 'New Password';" placeholder="New Password" />
						</div>
						
					</div>
					<div class="sub_field_main" style="text-align:center;"><img id="indicator2" src="<s:url value="/images/ajax-loader.gif"/>" alt="Loading..."  width="70%"  style="display:none"/></div>
					<div class="pop_up_submit">
                        <div class="pop_up_submit_here closepopup"><input type="submit" name="" value="" /></div>
						<div class="pop_up_reg">
						 <sj:submit 
                              targets="Result1" 
                              clearForm="true"
                              id="form_submit"
                              value="" 
                              href="%{AddUserInfo_url}"
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