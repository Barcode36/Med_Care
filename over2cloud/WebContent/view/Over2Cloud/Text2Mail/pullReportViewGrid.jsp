<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<%
	String userRights = session.getAttribute("userRights") == null ? "" : session.getAttribute("userRights").toString();
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>
<body>
<s:url id="viewGrids" action="viewPullReportss" escapeAmp="false" >
<s:param name="type" value="%{type}"></s:param>
<s:param name="keyword" value="%{keyword}"></s:param>
<s:param name="fromDate" value="%{fromDate}"></s:param>
<s:param name="toDate" value="%{toDate}"></s:param>
<s:param name="fromDate12" value="%{fromDate12}"></s:param>
<s:param name="toDate12" value="%{toDate12}"></s:param>
<s:param name="location" value="%{location}"></s:param>
<s:param name="spc" value="%{spc}"></s:param>
<s:param name="cons" value="%{cons}"></s:param>
<s:param name="kws" value="%{kws}"></s:param>
<s:param name="chExc" value="%{chExc}"></s:param>


</s:url>

<center>
<div style="overflow: scroll; height:430px;">
<sjg:grid 
		  id="gridedittable"
          href="%{viewGrids}"
          gridModel="viewPullData"
          navigator="true"
          navigatorAdd="false"
          navigatorView="false"
          navigatorDelete="true"
          navigatorEdit="true"
          navigatorSearch="true"
          rowList="15,20,25,30"
          rownumbers="-1"
          viewrecords="true"       
          pager="true"
          pagerButtons="true"
          pagerInput="false"   
          multiselect="true"  
          loadingText="Requesting Data..."  
          rowNum="10"
          navigatorSearchOptions="{sopt:['eq','cn']}"
          editurl="%{modifyGrid}"
          navigatorEditOptions="{height:400,width:400}"
          shrinkToFit="false"
          autowidth="true"
          onSelectRowTopics="rowselect"
          
          >
          <s:iterator value="viewPullReportGrid" id="viewPullReportGrid" >  
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
</div>
</center>
</body>
</html>
