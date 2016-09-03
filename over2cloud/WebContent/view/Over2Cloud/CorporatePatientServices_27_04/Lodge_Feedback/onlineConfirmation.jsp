<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib  prefix="s" uri="/struts-tags"%>
<html>
<head>
<title>Insert title here</title>
</head>
<body>
<div id="1" align="center" >
	<font face="Arial, Helvetica, sans-serif" color="#000000" size="1">
	 <b>
	 <s:if test="%{exist_ticket}">
		  Dear <s:property value="%{feedbackBy}" />, your Ticket ID <s:property value="%{ticket_no}" /> has been registered successfully. 
		 <s:property value="%{allotto}" />, Mobile No. <s:property value="%{allot_to_mobno}" /> will contact you with in 
		 <s:property value="%{escTime}" />. Thanks for giving us opportunity to serve you.
	 </s:if>
	 <s:else>
	      Dear <s:property value="%{feedbackBy}" />, The Ticket for this location and Sub Category is Already in  <s:property value="%{feedStatus}" /> Mode Kindly
	     
	      Here is the brief
	      Ticket No: <s:property value="%{ticket_no}" />, 
	      Alloted To:  <s:property value="%{allotto}" />, 
	      Allot To Mobile No:  <s:property value="%{allot_to_mobno}" />, 
	      Open Date:  <s:property value="%{open_date}" />, 
	      Open Time:  <s:property value="%{open_time}" />, 
	     <s:hidden id="feedidssss" value="%{id}"/>
	 </s:else>
	 </b>
	 </font>
	 </div>
</body>
</html>

