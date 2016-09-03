<%@taglib uri="/struts-tags"  prefix="s" %>
<div class="form"><div style=" font-size:14px; color:#181818; padding-top:20px; text-align:center;" class="sub_field_main">
<s:actionmessage  />
<s:actionerror />
<div style="float:right; width:100%; text-align:left;">

				<div class="heading"><h2>Login to your account<br /><span>Ride the cloud!!!  Its FREE</span></h2></div>
				<div class="sub_content">
				<s:form name="loginform1"  id="loginform1" action="loginaction"  theme="css_xhtml" theme="simple"  method="post">
					<div class="sub_field_main"><s:textfield name="accountid" id="accountid" cssClass="field" maxlength="10" onfocus="javascript:if(this.placeholder=='Account ID') this.placeholder = '';"   onblur="javascript:if(this.placeholder=='') this.placeholder = 'Account ID';"   placeholder="Account ID"></s:textfield></div>
					<div class="sub_field_main"><s:textfield name="username" id="username" cssClass="field" maxlength="20" onfocus="javascript:if(this.placeholder=='Username') this.placeholder = '';"   onblur="javascript:if(this.placeholder=='') this.placeholder = 'Username';"   placeholder="Username"></s:textfield></div>
					<div class="sub_field_main"><s:password name="password" id="password" cssClass="field" maxlength="20" onfocus="javascript:if(this.placeholder=='Password') this.placeholder = '';"   onblur="javascript:if(this.placeholder=='') this.placeholder = 'Password';"   placeholder="Password"/></div>
					<div class="sub_field_main">
						<div class="form_submit" style=" float:right;"><s:submit type="submit" cssClass="submit"/></div>
					</div>	
				</s:form>									
				</div>




	</div>
</div></div>



