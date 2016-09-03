<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<link rel="stylesheet" media="screen,projection" type="text/css" href="<s:url value="/css/main.css"/>" />
<link type="text/css" href="<s:url value="/css/style.css"/>" rel="stylesheet" />
</head>
<body>
<s:url id="fileDownload" action="download" ></s:url>
<div id="main">
<ol class="results">
<s:iterator value="attachFileMap" status="status">
     <s:set name="id" value="%{attachFileMap['id']}"> </s:set>
     <s:set name="filePath" value="%{attachFileMap['File Path']}"> </s:set>
</s:iterator>
            <li>            
                <a href="<%=request.getContextPath()%>/view/Over2Cloud/DAROver2Cloud/download.action?id=<s:property value="filePath"/>"><B>Download File</B></a>
            </li>
</ol>
</div>
</body>
</html>