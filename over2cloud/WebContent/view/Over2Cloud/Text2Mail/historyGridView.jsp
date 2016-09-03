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

<div style="overflow: scroll; height:430px;">
<s:url id="viewGridshistory12" action="viewhisData" escapeAmp="false" >
<s:param name="idv" value="%{idv}"></s:param>
</s:url>
    <sjg:grid
    	id="gridtable12"
    	dataType="json"
    	href="%{viewGridshistory12}"
    	pager="true"
    	gridModel="gridModelHis1"
    	rowList="10,15,20"
    	rowNum="15"
    	rownumbers="true"
    	scroll="false"
    >
    <s:property value="gridModelHis1"/>
   		 <sjg:gridColumn name="id" index="id" title="Id"  sortable="false" hidden="true" align="center" width="20"/>
    	<sjg:gridColumn name="incomingKeyword" index="incomingKeyword" title="Sub Keyword"  sortable="false" align="center" />
    	<sjg:gridColumn name="date" index="date" title="Date" sortable="true" align="center" width="120" />
    	<sjg:gridColumn name="time" index="time" title="Time" sortable="false" align="center" width="100"/>
    	
    </sjg:grid>

</div>

</body>
</html>



