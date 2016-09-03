<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib  prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script type="text/javascript" src="<s:url value="/js/helpdesk/feedback.js"/>"></script>
<script>
function printDiv(divName) 
{
    var printContents = document.getElementById(divName).innerHTML;
    $("#printTicket").dialog('open');
    var myWindow = window.open("","myWindow","width=900,height=600"); 
    myWindow.document.write(printContents);
    myWindow.moveTo(300,200); 
    myWindow.print();
    myWindow.close();
    $("#printTicket").dialog('close');
}

function  CancelPrint()
{

   $("#printTicket").dialog('close');
   $("#confirmDialogBox").dialog('close');
   
}

</script>

</head>
<body>
<sj:dialog
	          id="printTicket"
	          showEffect="slide" 
	    	  hideEffect="explode" 
	    	  openTopics="openEffectDialog"
	    	  closeTopics="closeEffectDialog"
	          autoOpen="false"
	          title="Print Ticket"
	          modal="true"
	          width="1000"
	          height="350"
	          draggable="true"
	    	  resizable="true"
          >
		</sj:dialog>
<div id="print">
<table cellpadding="0" border="0" align="center" cellspacing="0" style="border-collapse: collapse" width="100%">
<tr>
   <td width="30%" align="left"><img src="<s:url value="/images/dreamsol.jpg"/>" border="0" width="150" height="50" /></td>
   <td width="70%" align="left">
      <table cellpadding="0" border="0" align="center" cellspacing="0" style="border-collapse: collapse" width="100%">
      <tr>
          <td width="60%" align="center"><font face="Arial, Helvetica, sans-serif" color="#000000" size="4"><b><u><s:property value="%{orgName}" /></u></b></font></td>
      </tr>
      <tr>
          <td width="50%" align="center"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b><u><s:property value="%{address}" />,&nbsp;<s:property value="%{city}" /> - <s:property value="%{pincode}" /></u></b></font></td>
          <td width="50%" align="left"><font face="Arial, Helvetica, sans-serif"  color="#000000" size="1">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>Lodge&nbsp;Time:</b></font>&nbsp;<font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><s:property value="%{open_time}" /></font></td>
                  
     </tr>
      <tr>
           <td width="50%" align="center"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><u><b>Job Card&nbsp;-&nbsp;<s:property value="%{ticketNo}" /></b></u></font></td>
          <td width="50%" align="left"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>Lodge&nbsp;Date:</b></font>&nbsp;<font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><s:property value="%{open_date}" /></font></td>
     </tr>
    
</table>
   </td>
</tr>
</table>
<hr />
<table border="1" width="100%" align="center">

    <tr>
		<td width="20%">
			<font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><b>&nbsp;Relationship Type:&nbsp;</b></font>
		</td>
		<td width="30%">
			<font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><b>&nbsp;<s:property value="%{clientfor}" /></b></font>
		</td>
		<td width="20%">
			<font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><b>&nbsp;Organization Name:&nbsp;</b></font>
		</td>
		<td width="30%">
			<font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><b>&nbsp;<s:property value="%{clientName}" /></b></font>
		</td>
	</tr>
	 <tr>
		<td width="20%">
			<font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><b>&nbsp;Offering:&nbsp;</b></font>
		</td>
		<td width="30%">
			<font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><b>&nbsp;<s:property value="%{offering}" /></b></font>
		</td>
	</tr>
	
		<tr>
		<td width="20%">
			<font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><b>&nbsp;Feedback By:&nbsp;</b></font>
		</td>
		<td width="30%">
			<font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><b>&nbsp;<s:property value="%{feedbackTo}" /></b></font>
		</td>
		<td width="20%">
			<font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><b>&nbsp;Feedback CC:&nbsp;</b></font>
		</td>
		<td width="30%">
			<font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><b>&nbsp;<s:property value="%{feedbackCC}" /></b></font>
		</td>
	</tr>

	<tr>
		<td width="20%">
			<font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><b>&nbsp;To Dept:&nbsp;</b></font>
		</td>
		<td width="30%">
			<font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><b>&nbsp;<s:property value="%{forDept}" /></b></font>
		</td>
		<td width="20%">
			<font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><b>&nbsp;Alloted To:&nbsp;</b></font>
		</td>
		<td width="30%">
			<font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><b>&nbsp;<s:property value="%{allotTo}" /></b></font>
		</td>
	</tr>
	
	<tr>
		<td width="20%">
			<font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><b>&nbsp;Category:&nbsp;</b></font>
		</td>
		<td width="30%">
			<font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><b>&nbsp;<s:property value="%{feedCat}" /></b></font>
		</td>
		<td width="20%">
			<font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><b>&nbsp;Sub-Category:&nbsp;</b></font>
		</td>
		<td width="30%">
			<font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><b>&nbsp;<s:property value="%{subCategory}" /></b></font>
		</td>
	</tr>
	<tr>
		<td width="20%" valign="top">
			<font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><b>&nbsp;Brief:&nbsp;</b></font>
		</td>
		<td width="30%" valign="top">
			<font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><b>&nbsp;<s:property value="%{feed_brief}" /></b></font>
		</td>
		<td width="20%" valign="top">
			<font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><b>&nbsp;Addressing Time:&nbsp;</b></font>
		</td>
		<td width="30%" valign="top">
			<font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><b>&nbsp;<s:property value="%{escTime}" /></b></font>
		</td>
	</tr>
	
   <%--  <tr>
          <td width="20%">
                      <font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><b>&nbsp;Resolved On:&nbsp;</b></font>
          </td>
          <td width="30%">
                     <font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><b> <c:out value="${resDat}"/></b></font>
          </td>
          <td width="20%">
                     <font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><b>&nbsp;Resolved At:&nbsp;</b></font>
          </td>
          <td width="30%">
                    <font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><b> <c:out value="${resTime}"/></b></font>
          </td>
    </tr> --%>
  
    
	<%-- <tr>
		<td width="20%" colspan="1">
			<font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><b>&nbsp;Action Taken:&nbsp;</b></font>
		</td>
		<td width="80%" colspan="3">
                    <font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><b> <c:out value="${resCmnt}"/></b></font>
        </td>
	</tr> --%>
	
<%-- 	<tr>
		<td width="20%" colspan="1">
			<font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><b>&nbsp;Resources Used:&nbsp;</b></font>
		</td>
		<td width="80%" colspan="3">
                    <font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><b><c:out value="${spareused}"/></b></font>
        </td>
	</tr> --%>
	
	<tr>
		<td width="20%" colspan="1">
			<font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><b>&nbsp;Feedback:&nbsp;</b></font>
		</td>
		<td width="80%" colspan="3">
            <font face="Arial, Helvetica, sans-serif" color="#000000" size="1"></font>
        </td>
	</tr>
</table>
<br />
<table width="100%" align="center">
	<tr>
		<td width="30%" align="center">
			<font face="Arial, Helvetica, sans-serif" color="#000000" size="1">
<b>Complainant Sign</b></font>
		</td>
		<td width="30%" align="center">
			<font face="Arial, Helvetica, sans-serif" color="#000000" size="1">
<b>Attendent Sign</b></font>
		</td>
		<td width="30%" align="center">
			<font face="Arial, Helvetica, sans-serif" color="#000000" size="1">
<b>Shift Manager</b></font>
		</td>
	</tr>
</table>     
      <table align="center" width="100%">
         <tr>
          <td width="100%" align="center"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1">
-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------</font></td> 
        </tr>
	</table>
	</div>
	<br>
	<div class="type-button">
       		<center>
              <sj:submit 
                        clearForm="true"
                        value="  Print  " 
                        button="true"
                        onclick="printDiv('print')"
                        />
              <sj:submit 
                        clearForm="true"
                        value="  Cancel  " 
                        button="true"
                        onclick="CancelPrint();"
                        />          
              </center>
              </div> 
	
</body>
</html>

