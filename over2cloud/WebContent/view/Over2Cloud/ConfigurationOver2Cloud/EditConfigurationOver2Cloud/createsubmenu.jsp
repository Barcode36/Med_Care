	

     <%@ taglib prefix="s" uri="/struts-tags" %>	

	 <s:iterator id="settingconfigBean" value="settingconfigBean" var="variable" >
	 <li>
	 <a href="javascript:void();" title="${queryNames}" name="${CommonMappedtablele}" onclick="OnlickFunctionviewmastersetting(${id},name,title);"><s:property value="field_value"/></a>
	 </li>
	 </s:iterator>

							