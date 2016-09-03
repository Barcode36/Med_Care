<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
</head>
<body>
<s:form name="packGRequest"  id="packGRequest" action="packageRequest"  theme="css_xhtml" theme="simple"  method="post">
<s:hidden id="um" name="um" value="%{um}"> </s:hidden>
<s:hidden id="kcf" name="kcf" value="%{kcf}"> </s:hidden>
<s:hidden id="id" name="id" value="%{id}"> </s:hidden>
<s:hidden id="uid" name="uid" value="%{uid}"> </s:hidden>
<s:hidden id="contryid" name="contryid" value="%{contryid}"> </s:hidden>

 				  <div class="form_menubox">
                  <div class="user_form_text">No Of Users:*</div>
                  <div class="user_form_input"><s:textfield name="noOfUser" id="noOfUser" cssClass="form_menu_inputtext" placeholder="Enter No Of Users Package"/></div>
				  <div class="user_form_text1">Application:*</div>
                  <div class="user_form_input">
                  <s:select 
                              id="appIntrstd"
                              name="appIntrstd" 
                              list="appNames"
                              headerKey="-1"
                              headerValue="--Select Application--" 
                              cssClass="form_menu_inputselect"
                              />
                  </div>
				 </div>
</s:form>
</body>
</html>