<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<style type="text/css">

	td
	{
		padding: 2px;
		text-align: center;
	}
</style>

<script type="text/javascript">
	function getDataForPrint() 
	{
		$("#takeActionGrid").dialog('close');
		 var printContents = document.getElementById("dataForPrint").innerHTML;
		 var myWindow = window.open("","myWindow","width=900,height=600"); 
		 myWindow.document.write(printContents);
		 myWindow.moveTo(300,200); 
		 myWindow.print();
		 myWindow.close();
	}

</script>
</head>
<body>
	<sj:a  button="true" cssStyle="height:25px; width:32px;float:right;"  cssClass="button" buttonIcon="ui-icon-print" title="Print" onClick="getDataForPrint()" ></sj:a>
	<div id="dataForPrint">
		<table border="1" width="100%" style="border-collapse: collapse;">
		
					<tr  style="height: 25px">
						<th width="10%"><b>Caller Emp ID: </b></th><td width="10%"><s:property value="%{empId}"/></td>
						<th width="10%"><b>Caller Name: </b></th><td width="10%"><s:property value="%{empName}"/></td>
				</tr>
	   
				 <tr bgcolor="#D8D8D8" style="height: 25px">
						<th width="10%"><b>Referral By: </b></th><td width="10%"><s:property value="%{refBy}"/></td>
						<th width="10%"><b>Referral Specialty: </b></th><td width="10%"><s:property value="%{refBySpec}"/></td>
				</tr>
				
				<tr  style="height: 25px">
						<th width="10%"><b>Referred To: </b></th><td width="10%"><s:property value="%{refTo}"/></td>
						<th width="10%"><b>Referred Specialty: </b></th><td width="10%"><s:property value="%{refToSpec}"/></td>
				</tr>
				<tr bgcolor="#D8D8D8" style="height: 25px">
						<th width="10%"><b>UHID: </b></th><td width="10%"><s:property value="%{uhid}"/></td>
						<th width="10%"><b>Patient Name: </b></th><td width="10%"><s:property value="%{patientName}"/></td>
				</tr>
				
				<tr  style="height: 25px">
						<th width="10%"><b>Room No: </b></th><td width="10%"><s:property value="%{bed}"/></td>
						<th width="10%"><b>Nursing Unit: </b></th><td width="10%"><s:property value="%{nursingUnit}"/></td>
				</tr>
				<tr bgcolor="#D8D8D8" style="height: 25px">
						<th width="10%"><b>Status: </b></th><td width="10%"><s:property value="%{status}"/></td>
						<th width="10%"><b>Priority: </b></th><td width="10%"><s:property value="%{priority}"/></td>
				</tr>
				<tr  style="height: 25px">
						<th width="10%"><b>Open On: </b></th><td width="10%"><s:property value="%{openDate}"/></td>
						<th width="10%"><b>Sub Specialty</b></th><td width="10%"><s:property value="%{subSpec}"/></td>
				</tr>
				
		</table>
		
	<div class="contentdiv-small" style="overflow: auto;  height:355px; width:98%;  margin-left:0%; margin-right:1px;   border-color: black; background-color: #D8D8D8" >
    <div class="clear"></div>
           <div style="height:10px; margin-bottom:2px;" id='1stBlock'></div>
                       <table  align="center" border="0" width="98%" height="7%" style="border-style:dotted solid;">
                               <tbody>
                                    <tr bgcolor="black" bordercolor="black" >
	                                       <td align="center" width="10%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Status</b></font></td>
	                                       <td align="center" width="16%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Action Date</b></font></td>
	                                       <td align="center" width="16%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Action Reason</b></font></td>
	                                       <td align="center" width="16%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Action Remarks</b></font></td>
	                                       <td align="center" width="16%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Escalate Level</b></font></td>
	                                       <td align="center" width="18%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Action By</b></font></td>
	                                       <td align="center" width="26%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Informed/Seen/Ignore/Park By</b></font></td>
	                                </tr>
	                                
	                                <s:iterator value="dataList" var="listVal1" status="counter1">
 									<tr>  <td align="center" width="10%"><s:property value="%{#listVal1[0]}" /></td>
                                           <td align="center" width="16%"><s:property value="%{#listVal1[1]}" /></td>
                                           <td align="center" width="16%"><s:property value="%{#listVal1[2]}" /></td>
                                            <td align="center" width="16%"><s:property value="%{#listVal1[6]}" /></td>
                                           <td align="center" width="16%"><s:property value="%{#listVal1[3]}" /></td>
                                           <td align="center" width="16%"><s:property value="%{#listVal1[4]}" /></td>
                                           <td align="center" width="16%"><s:property value="%{#listVal1[5]}" /></td>
                                    </tr>
                                   
                                   </s:iterator>
                                    
                               </tbody>
                       </table>
                 </div>
       </div>          
</body>
</html>