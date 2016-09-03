	

     <%@ taglib prefix="s" uri="/struts-tags" %>	
	 <s:iterator id="settingconfigBean" value="settingconfigBean" var="variable" >
	 <li><a href="javascript:void();" name="${CommonMappedtablele}" onclick="OnlickFunctionsetting(${id},name);"><b><s:property value="field_value"/></b></a>
	 </s:iterator>
											