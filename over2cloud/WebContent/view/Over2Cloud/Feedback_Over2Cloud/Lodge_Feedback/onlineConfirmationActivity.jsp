<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib  prefix="s" uri="/struts-tags"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script type="text/javascript" src="<s:url value="/js/helpdesk/feedback.js"/>"></script>
</head>
<body>
<div id="1" align="center" ><font face="Arial, Helvetica, sans-serif"   color="#000000" style="font-size: 13px;">Dear <s:property value="%{fbp.getFeed_by()}" />, your Ticket ID <s:property value="%{fbp.getTicket_no()}" /> has been registered successfully. <s:property value="%{fbp.getFeedback_allot_to()}" /> of department <s:property value="%{fbp.getFeedback_to_dept()}" />, Mobile No. <s:property value="%{fbp.getMobOne()}" /> will contact you by   <s:property value="%{fbp.getEscalation_date()}" />. Thanks for giving us opportunity to serve you.</font></div>
</body>
</html>

