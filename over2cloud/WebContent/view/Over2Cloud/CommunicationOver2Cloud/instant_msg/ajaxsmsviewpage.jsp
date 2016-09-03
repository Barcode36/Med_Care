<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<s:url id="urlsearchviewinstantmsgss" action="searchviewdata"  escapeAmp="false">
<s:param name="status"  value="%{#parameters.status}"></s:param>
<s:param name="smsType"  value="%{#parameters.smsType}"></s:param>
<s:param name="mobileNo"  value="%{#parameters.mobileNo}"></s:param>
<s:param name="from_date"  value="%{#parameters.from_date}"></s:param>
<s:param name="to_date"  value="%{#parameters.to_date}">

</s:param>
</s:url>
<sjg:grid 
		  id="gridedittabless"
          href="%{urlsearchviewinstantmsgss}"
          gridModel="masterViewList"
          navigator="true"
          navigatorAdd="false"
          navigatorView="false"
          navigatorDelete="%{deleteFlag}"
          navigatorEdit="%{modifyFlag}"
          navigatorSearch="true"
          rowList="15,25,50"
          rownumbers="-1"
          viewrecords="true"       
          pager="true"
          pagerButtons="true"
          pagerInput="false"   
          multiselect="true"  
          loadingText="Requesting Data..."  
          rowNum="10"
          navigatorSearchOptions="{sopt:['eq','ne','cn','bw','ne','ew']}"
          editurl="%{viewModifyGrid}"
          shrinkToFit="false"
          autowidth="true"
          >
          <s:iterator value="viewInstantMsgGrid" id="viewInstantMsgGrid" >  
		<sjg:gridColumn 
		name="%{colomnName}"
		index="%{colomnName}"
		title="%{headingName}"
		width="%{width}"
		align="%{align}"
		editable="%{editable}"
		formatter="%{formatter}"
		search="%{search}"
		hidden="%{hideOrShow}"
		/>
		</s:iterator> 
	          
          
		
</sjg:grid>

</body>
</html>