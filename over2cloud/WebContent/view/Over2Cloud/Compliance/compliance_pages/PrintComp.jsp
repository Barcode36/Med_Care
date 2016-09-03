<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib  prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script>

$.subscribe('printTickets', function(event,data) {

	if(confirm("Are You Sure Want to Take Print."))
	{
    	var printContents = document.getElementById(divName).innerHTML;
    	var originalContents = document.body.innerHTML;
	    document.body.innerHTML = printContents;
 		window.print();
		document.body.innerHTML = originalContents;
	}
});

function printDiv(divName) {
    var printContents = document.getElementById(divName).innerHTML;
    var originalContents = document.body.innerHTML;

    document.body.innerHTML = printContents;
    window.print();
    window.close();
}

</script>

</head>
<body>

<c:set var="orgName" value="${orgName}" />
<c:set var="address" value="${address}" />
<c:set var="city" value="${city}" />
<c:set var="pincode" value="${pincode}" />
<div id="1">
<table cellpadding="0" border="0" align="center" cellspacing="0" style="border-collapse: collapse" width="100%">
<tr>
   <td width="30%" align="left"><img src="<s:url value="/images/orgLogo/orgLogo.JPG"/>" border="0" width="150" height="50" /></td>
   <td width="70%" align="left">
      <table cellpadding="0" border="0" align="center" cellspacing="0" style="border-collapse: collapse" width="100%">
      <tr>
          <td width="60%" align="center"><font face="Arial, Helvetica, sans-serif" color="#000000" size="4"><b><u><c:out value="${orgName}" /></u></b></font></td>
      </tr>
      <tr>
          <td width="50%" align="center"><font face="Arial, Helvetica, sans-serif" color="#000000" size="3"><b><u><c:out value="${address}" />,&nbsp;<c:out value="${city}" /> - <c:out value="${pincode}" /></u></b></font></td>
     </tr>
</table>
   </td>
</tr>
</table>
<hr />
<table border="1" width="100%" align="center">

    <tr>
		<td width="20%">
			<font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b>&nbsp;Department</b></font>
		</td>
		<td width="30%">
			<font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b>&nbsp;<s:property value="%{deptName}"/></b></font>
		</td>
		<td>&nbsp;</td>
		<td width="20%">
			<font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b>&nbsp;Task Type</b></font>
		</td>
		<td width="30%">
			<font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b>&nbsp;<s:property value="%{taskType}"/></b></font>
		</td>
		<td>&nbsp;</td>
	</tr>

	<tr>
		<td width="20%">
			<font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b>&nbsp;Task Name</b></font>
		</td>
		<td width="30%">
			<font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b>&nbsp;<s:property value="%{taskName}"/></b></font>
		</td>
		<td>&nbsp;</td>
		<td width="20%">
			<font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b>&nbsp;Frequency</b></font>
		</td>
		<td width="30%">
			<font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b>&nbsp;<s:property value="%{frequency}"/></b></font>
		</td>
		<td>&nbsp;</td>
		
	</tr>
	
	<tr>
	
		<td width="20%">
			<font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b>&nbsp;Due Date</b></font>
		</td>
		<td width="30%">
			<font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b>&nbsp;<s:property value="%{dueDate}"/></b></font>
		</td>
		<td>&nbsp;</td>
		
		<td width="20%">
			<font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b>&nbsp;Due Time</b></font>
		</td>
		<td width="30%">
			<font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b>&nbsp;<s:property value="%{dueTime}"/></b></font>
		</td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td width="20%">
			<font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b>&nbsp;Status</b></font>
		</td>
		<td width="30%">
			<font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b>&nbsp;<s:property value="%{status}"/></b></font>
		</td>
		<td>&nbsp;</td>
		<td width="20%">
			<font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b>&nbsp;Remind To</b></font>
		</td>
		<td width="30%">
			<font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b>&nbsp;<s:property value="%{reminderFor}"/></b></font>
		</td>
		<td>&nbsp;</td>
	</tr>
	
	<s:if test="reportPrint">
		<tr>
		<td width="20%">
			<font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b>&nbsp;Action On</b></font>
		</td>
		<td width="30%">
			<font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b>&nbsp;<s:property value="%{actionTakenDate}"/></b></font>
		</td>
		<td>&nbsp;</td>
		<td width="20%">
			<font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b>&nbsp;Action By</b></font>
		</td>
		<td width="30%">
			<font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b>&nbsp;<s:property value="%{actionBy}"/></b></font>
		</td>
		<td>&nbsp;</td>
	</tr>
	
	</s:if>
</table>
<br />
      <table align="center" width="100%">
         <tr>
          <td width="100%" align="center"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1">
-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------</font></td> 
        </tr>
	</table>
	</div>
	<div class="type-button">
       		<center>
              <sj:submit 
                        clearForm="true"
                        value="  Print  " 
                        button="true"
                        onclick="printDiv('1')"
                        />
              </center>
              </div> 
	
</body>
</html>

