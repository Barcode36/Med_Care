<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Over2Cloud: Help Desk Manager</title>
</head>
<body>
<sj:div id="withclick" events="click" effect="highlight" effectDuration="4000" effectOptions="{ color: '#000000', mode: 'show' }" cssClass="result ui-widget-content ui-corner-all">
<table align="center" width="100%">
	 <tr>
		<th colspan="2">Lodge By Detail</th>
	 </tr>
 	 <tr>
 			<td align="left" width="40%">By&nbsp;Whom</td>
 			<td width="60%"><s:property value="%{FP.feed_by}"/></td>
 	 </tr>
     <tr>
     	     <td align="left" width="40%">By&nbsp;Department</td>
     		 <td width="60%"><s:property value="%{FP.feedback_by_dept}"/></td>
     </tr>
      <tr>
     	     <td align="left" width="40%">Mobile&nbsp;No</td>
     		 <td width="60%"><s:property value="%{FP.feedback_by_mobno}"/></td>
     </tr>
     <tr>
     	     <td align="left" width="40%">Email Id</td>
     		 <td width="60%"><s:property value="%{FP.feedback_by_emailid}"/></td>
     </tr>
</table>
</sj:div>
</body>
</html>