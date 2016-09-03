
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<head>
    <s:url id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
</head>
  
		

<sj:accordion id="accordion" autoHeight="false" >

	<sj:accordionItem title="Basic Information">
	       	<div>
								<div class="steps_content">
									<div class="form_content">
			<ul>
									<s:iterator id="ConfigurationUtilBean" value="basicconfigBean" >
										<s:set name="conid" value="%{id}" />
									<li><s:checkbox name="titles" title="%{id}-%{queryNames}" id="titles" value="true" fieldValue="%{conid}" onclick="OnlickEdite(this);" theme="simple"/>&nbsp;&nbsp;<s:property value="field_value"/>
									</li>
									<li style="display: none;"><span id="queryNames"><s:property value="queryNames"/></span>
									</li>
									</s:iterator>
										</ul>
										</div></div></div>
	</sj:accordionItem>

<sj:accordionItem title="Address Information">
  	                   	<div>
								<div class="steps_content">
						
									<div class="form_content">
  	                   	<ul>
									
									<s:iterator id="ConfigurationUtilBean" value="addressconfigBean" >
										<s:set name="conid" value="%{id}" />
									<li><s:checkbox name="titles" title="%{id}-%{queryNames}" id="titles" value="true" fieldValue="%{conid}" onclick="OnlickEdite(this);" theme="simple"/>&nbsp;&nbsp;<s:property value="field_value"/>
									</li>
									<li style="display: none;"><span id="queryNames"><s:property value="queryNames"/></span>
									</li>
									</s:iterator>
										</ul>
										</div></div></div>
									</sj:accordionItem>
									  <sj:accordionItem title="Custom Information">
									   	<div>
								<div class="steps_content">
						
									<div class="form_content">
									<ul>
									
									<s:iterator id="ConfigurationUtilBean" value="customconfigBean" >
										<s:set name="conid" value="%{id}" />
									<li><s:checkbox name="titles" id="titles" value="true" fieldValue="%{conid}" onclick="OnlickEdite();" theme="simple"/>&nbsp;&nbsp;<s:property value="field_value"/>
									</li>
									</s:iterator>
									<li style="display: none;"><span id="queryNames"><s:property value="queryNames"/></span>
									</li>
										</ul>
										</div></div></div>
									</sj:accordionItem>
									
												  <sj:accordionItem title="Description Information">
									  zscvjkdncjdsnjcndj
									</sj:accordionItem>
								
  </sj:accordion>
