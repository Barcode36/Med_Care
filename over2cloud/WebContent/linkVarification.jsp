<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<div id="Result1">
<s:form name="clientInformationAdd"  id="AddUserInfo_url" action="doClientinformationDone"  theme="css_xhtml" theme="simple"  method="post">
<s:hidden id="um" name="um" value="%{um}"> </s:hidden>
<s:hidden id="kcf" name="kcf" value="%{kcf}"> </s:hidden>
<s:hidden id="id" name="id" value="%{id}"> </s:hidden>
<s:hidden id="username" name="username" value="%{username}"> </s:hidden>
<s:hidden id="accountid" name="accountid" value="%{accountid}"> </s:hidden>
<s:hidden id="password" name="password" value="%{password}"> </s:hidden>
<div class="lightbox" style="left:28%;">
	<div class="popoupdiv" style="width: 730px;">
		<div class="pop_content">
			<div class="pop_content_block"><img src="images/pop_up_top.png" width="744" height="20" alt="" title="" /></div>
			<div class="pop_up_content_inner">
				<div class="pop_up_content_inner_sub">
				<h1>Account Id & Login Details</h1>
					<div class="pop_text"><p>Please enter the following details that we have mailed to your registered email address.</p></div>
					<div class="pop_up_form">
						<div class="sub_field_main" style=" width:100%;">
						 <s:textfield  value="%{accountid}                 [Account Id]" cssClass="field" maxlength="50" onfocus="javascript:if(this.placeholder=='Account ID') this.placeholder = '';" onblur="javascript:if(this.placeholder=='') this.placeholder = 'Account ID';" placeholder="Account ID" readonly="true" cssStyle="margin-left: 250px;"/>
						</div>
						<div class="sub_field_main" style=" width:100%;">
						<s:textfield   value="%{username}                      [Auto Username]" cssClass="field" maxlength="50" onfocus="javascript:if(this.placeholder=='Username') this.placeholder = '';" onblur="javascript:if(this.placeholder=='') this.placeholder = 'Username';" placeholder="Username" readonly="true" cssStyle="margin-left: 250px;"/>
						</div>
						<div class="sub_field_main" style=" width:100%;">
						<s:textfield  value="%{password}                  [Auto Password]" cssClass="field" maxlength="50" onfocus="javascript:if(this.placeholder=='Password') this.placeholder = '';" onblur="javascript:if(this.placeholder=='') this.placeholder = 'Password';" placeholder="Password" readonly="true" cssStyle="margin-left: 250px;"/>
						</div>
						<div class="sub_field_main" style=" width:100%;">
						<s:textfield name="newusername" id="newusername" cssClass="field" maxlength="50" onfocus="javascript:if(this.placeholder=='New Username') this.placeholder = '';" onblur="javascript:if(this.placeholder=='') this.placeholder = 'New Username';" placeholder="New Username" cssStyle="margin-left: 250px;"/>
						</div>
						<div class="sub_field_main" style=" width:100%;">
						<s:password name="newpassword" id="newpassword" cssClass="field" maxlength="50" onfocus="javascript:if(this.placeholder=='New Password') this.placeholder = '';" onblur="javascript:if(this.placeholder=='') this.placeholder = 'New Password';" placeholder="New Password" cssStyle="margin-left: 250px;"/>
						</div>
						
					</div>
					<div class="pop_up_forgot"><a href="javascript:void();" style="margin-left: 274px;">problems receiving the email !!! <span>click here</span></a></div>
					<div class="sub_field_main" style="text-align:center;"><img id="indicator2" src="<s:url value="/images/ajax-loader.gif"/>" alt="Loading..."  width="70%"  style="display:none"/></div>
					<div class="pop_up_submit">
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