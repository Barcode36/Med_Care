<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<%
	String userRights = session.getAttribute("userRights") == null ? "" : session.getAttribute("userRights").toString();
%>
<html>
<head>

</head>
<body>

<div style="overflow: scroll; height:430px;">
<s:url id="viewGrids" action="viewPullReportss" escapeAmp="false" >
<s:param name="type" value="%{type}"></s:param>
<s:param name="keyword" value="%{keyword}"></s:param>
<s:param name="fromDate" value="%{fromDate}"></s:param>
<s:param name="toDate" value="%{toDate}"></s:param>

<s:param name="fromDate122" value="%{fromDate122}"></s:param>
<s:param name="toDate122" value="%{toDate122}"></s:param>
<s:param name="location" value="%{location}"></s:param>
<s:param name="spc" value="%{spc}"></s:param>
<s:param name="cons" value="%{cons}"></s:param>
<s:param name="kws" value="%{kws}"></s:param>
<s:param name="chExc" value="%{chExc}"></s:param>
</s:url>

<sjg:grid 
		  id="gridedittable"
          href="%{viewGrids}"
          gridModel="viewPullData"
          navigator="false"
          navigatorAdd="false"
          navigatorView="false"
          navigatorDelete="true"
          navigatorEdit="true"
          navigatorSearch="true"
          rowList="15,30,50,100,150"
          viewrecords="true"       
          pager="true"
          pagerButtons="true"
          pagerInput="true"   
          multiselect="true"  
          loadingText="Requesting Data..."  
          rowNum="15"
          navigatorSearchOptions="{sopt:['eq','cn']}"
          editurl="%{modifyGrid}"
          navigatorEditOptions="{height:400,width:400}"
          shrinkToFit="false"
          autowidth="true"
          onSelectRowTopics="rowselect"
          onCompleteTopics="colorStatus"
    		
          >
         
          <s:iterator value="viewPageColumns" id="viewPageColumns" >  
		<sjg:gridColumn 
					name="%{colomnName}"
					index="%{colomnName}"
					title="%{headingName}"
					width="%{width}"
					align="center"
					editable="%{editable}"
					formatter="%{formatter}"
					search="%{search}"
					hidden="%{hideOrShow}"
					
				/>
		</s:iterator> 
	          
          
		
</sjg:grid>
</div>

</body>
</html>



