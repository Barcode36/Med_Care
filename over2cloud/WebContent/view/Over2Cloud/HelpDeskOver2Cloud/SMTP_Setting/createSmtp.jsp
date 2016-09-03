<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script type="text/javascript" src="<s:url value="/js/helpdesk/common.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/helpdesk/smtpvalidation.js"/>"></script>

</head>
<body>
<div class="page_title"><h1>SMTP Detail >> Add</h1><span class="needed_block">= indicates mandatory fields</span></div>
<div class="container_block"><div style=" float:left; padding:20px 5%; width:90%;">
<sj:accordion id="accordion" autoHeight="false">
<sj:accordionItem title="SMTP Detail >> Add" id="acor_item1">  
<div class="form_inner" id="form_reg">
<div class="page_form">
	<s:form id="formone" name="formone" action="addSmtpDetail" theme="css_xhtml"  method="post" enctype="multipart/form-data" >
	<s:hidden name="deptHierarchy" id="deptHierarchy" value="%{deptHierarchy}"></s:hidden>
    <center><div style="float:center; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;"><div id="errZone" style="float:center; margin-left: 7px"></div></div></center>        
    <% int temp=0;%>	
    <s:iterator value="smtpConfigColumns">
    <span id="smtpvalidate" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
    <%if(temp%2==0){ %>
    <div class="form_menubox">
         <s:if test="key=='server'">
               <div class="user_form_text"><s:property value="%{value}"/>:</div>
               <s:if test="%{mandatory}"><span class="needed"></span></s:if>
               <div class="user_form_input inputarea"><s:textfield name="%{key}" id="%{key}" placeholder="Enter Server Detail"  cssClass="form_menu_inputtext"/></div>
         </s:if>
         <s:elseif test="key=='emailid'">
               <div class="user_form_text"><s:property value="%{value}"/>:</div>
               <s:if test="%{mandatory}"><span class="needed"></span></s:if>
               <div class="user_form_input inputarea"><s:textfield name="%{key}" id="%{key}" placeholder="Enter Email Id"  cssClass="form_menu_inputtext"/></div>
         </s:elseif>
         <s:elseif test="key=='subject'">
               <div class="user_form_text"><s:property value="%{value}"/>:</div>
               <s:if test="%{mandatory}"><span class="needed"></span></s:if>
               <div class="user_form_input inputarea"><s:textfield name="%{key}" id="%{key}" placeholder="Enter Subject"  cssClass="form_menu_inputtext"/></div>
         </s:elseif>
    <%}else{%>
          <s:if test="key=='port'">
               <div class="user_form_text1"><s:property value="%{value}"/>:</div>
               <s:if test="%{mandatory}"><span class="needed"></span></s:if>
               <div class="user_form_input inputarea"><s:textfield name="%{key}" id="%{key}"  placeholder="Enter Port" cssClass="form_menu_inputtext"/></div>
          </s:if>
          <s:elseif test="key=='pwd'">
               <div class="user_form_text1"><s:property value="%{value}"/>:</div>
               <s:if test="%{mandatory}"><span class="needed"></span></s:if>
               <div class="user_form_input inputarea"><s:password name="%{key}" id="%{key}"  placeholder="Enter Password" cssClass="form_menu_inputtext"/></div>
          </s:elseif>
    </div>
    <%} temp++;%>
    </s:iterator>
     
    <div class="fields">
	<ul>
		 <li class="submit" style="background:none;">
		 <div class="type-button">
	          <sj:submit 
	                        targets="target1" 
	                        clearForm="true"
	                        value=" Add " 
	                        button="true"
	                        cssClass="submit"
	                        onCompleteTopics="beforeFirstAccordian"
	                        onBeforeTopics="validateSmtp"
	                        />
	      </div>
	      </li>
    </ul>
    <sj:div id="foldeffect1" effect="fold"><div id="target1"></div></sj:div>
    </div>
 </s:form>
</div>
</div>
</sj:accordionItem>
</sj:accordion>
</div>
</div>
</body>
</html>
