<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>
<body>
<div id="main">
		  <table>
		  <s:iterator value="docMap"  status="status">
		  	<tr><td><h3><B><s:property value="%{#status.count}" />. </B></h3></td>
                <td><a href="<%=request.getContextPath()%>/view/Over2Cloud/Compliance/compliance_pages/confirmDownload.action?fileName=<s:property value="key"/>"><h3><B><s:property value="value"/></B></h3></a>
		  	</td></tr>
		  </s:iterator>
		  </table>
</div>
</body>
</html>