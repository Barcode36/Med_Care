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
		<th colspan="2">Detail of Ticket No: <s:property value="%{FP.ticket_no}"/></th>
	</tr>
 	<tr>
 			<td align="left" width="40%">By&nbsp;Whom</td>
 			<td width="60%"><s:property value="%{FP.feed_by}"/></td>
 	</tr>
      		<tr>
      			<td align="left" width="40%">By Department</td>
      			<td width="60%"><s:property value="%{FP.feedback_by_dept}"/></td>
      		</tr>
       		
       		<tr>
                 <td align="left" width="40%">Alloted To</td>
                 <td width="60%"><s:property value="%{FP.feedback_allot_to}"/></td>
            </tr>
       		<tr>
       			<td align="left" width="40%">To Department</td>
       			<td width="60%"><s:property value="%{FP.feedback_to_dept}"/></td>
       		</tr>
       		<tr>
       			<td align="left" width="40%">To Sub-Dept</td>
       			<td width="60%"><s:property value="%{FP.feedback_to_subdept}"/></td>
       		</tr>
       		<tr>
       			<td align="left" width="40%">Category</td>
       			<td width="60%"><s:property value="%{FP.feedback_catg}"/></td>
       		</tr>
       		<tr>
       			<td align="left" width="40%">Sub-Category</td>
       			<td width="60%"><s:property value="%{FP.feedback_subcatg}"/></td>
       		</tr>
       		<tr>
       			<td align="left" width="40%">Feedback Brief</td>
       			<td width="60%"><s:property value="%{FP.feed_brief}"/></td>
       		</tr>
       		<tr>
       			<td align="left" width="40%">Open Date</td>
       			<td width="60%"><s:property value="%{FP.open_date}"/></td>
       		</tr>
       		<tr>
       			<td align="left" width="40%">Open Time</td>
       			<td width="60%"><s:property value="%{FP.open_time}"/></td>
       		</tr>
       		<tr>
       			<td align="left" width="40%">Escalation Time</td>
       			<td width="60%"><s:property value="%{FP.escalation_date}"/>&nbsp;&nbsp;(<s:property value="%{FP.escalation_time}"/>)</td>
       		</tr>
       		<tr>
       			<td align="left" width="40%">Addressing Time</td>
       			<td width="60%"><s:property value="%{FP.feedaddressing_time}"/></td>
       		</tr>
       		<tr>
       			<td align="left" width="40%">Level</td>
       			<td width="60%"><s:property value="%{FP.level}"/></td>
       		</tr>       
       		</table>
    </sj:div>
</body>
</html>