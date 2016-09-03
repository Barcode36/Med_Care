<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<script type="text/javascript">
function printDiv(divName) {
    var printContents = document.getElementById(divName).innerHTML;
    $("#takeActionDilouge").dialog('close');
    var myWindow = window.open("","myWindow","width=900,height=600"); 
    myWindow.document.write(printContents);
    myWindow.moveTo(300,200); 
    myWindow.print();
    myWindow.close();
}

</script>
<STYLE type="text/css">
.CSSTableGenerator {
	margin:0px;padding:0px;
	width:100%;
 
	border:1px solid #0d0f0d;
	
	-moz-border-radius-bottomleft:0px;
	-webkit-border-bottom-left-radius:0px;
	border-bottom-left-radius:0px;
	
	-moz-border-radius-bottomright:0px;
	-webkit-border-bottom-right-radius:0px;
	border-bottom-right-radius:0px;
	
	-moz-border-radius-topright:0px;
	-webkit-border-top-right-radius:0px;
	border-top-right-radius:0px;
	
	-moz-border-radius-topleft:0px;
	-webkit-border-top-left-radius:0px;
	border-top-left-radius:0px;
}.CSSTableGenerator table{
    border-collapse: collapse;
        border-spacing: 0;
	width:100%;
	height:100%;
	margin:0px;padding:0px;
}.CSSTableGenerator tr:last-child td:last-child {
	-moz-border-radius-bottomright:0px;
	-webkit-border-bottom-right-radius:0px;
	border-bottom-right-radius:0px;
}
.CSSTableGenerator table tr:first-child td:first-child {
	-moz-border-radius-topleft:0px;
	-webkit-border-top-left-radius:0px;
	border-top-left-radius:0px;
}
.CSSTableGenerator table tr:first-child td:last-child {
	-moz-border-radius-topright:0px;
	-webkit-border-top-right-radius:0px;
	border-top-right-radius:0px;
}.CSSTableGenerator tr:last-child td:first-child{
	-moz-border-radius-bottomleft:0px;
	-webkit-border-bottom-left-radius:0px;
	border-bottom-left-radius:0px;
}.CSSTableGenerator tr:hover td{
	background-color:#ffdbdb;
}
.CSSTableGenerator td{
	vertical-align:middle;
	background-color:#ffffff;
	border:1px solid #0d0f0d;
	border-width:0px 1px 1px 0px;
	text-align:center;
	padding:4px;
	font-size:10px;
	font-family:Arial;
	font-weight:bold;
	color:#000000;
}.CSSTableGenerator tr:last-child td{
	border-width:0px 1px 0px 0px;
}.CSSTableGenerator tr td:last-child{
	border-width:0px 0px 1px 0px;
}.CSSTableGenerator tr:last-child td:last-child{
	border-width:0px 0px 0px 0px;
}
.CSSTableGenerator tr:first-child td{
 
		background:-webkit-gradient( linear, left top, left bottom, color-stop(0.05, rgba(27, 82, 239, 0.25)), color-stop(1, rgb(9, 60, 80)) );
	background:-moz-linear-gradient( center top, #d62a2a 5%, #ffaaaa 100% );
	filter:progid:DXImageTransform.Microsoft.gradient(startColorstr="#d62a2a", endColorstr="#ffaaaa");	background: -o-linear-gradient(top,#d62a2a,ffaaaa);

	background-color:rgba(10, 38, 88, 0.07);
	border:0px solid #0d0f0d;
	text-align:center;
	border-width:0px 0px 1px 1px;
	font-size:14px;
	font-family:Arial;
	font-weight:bold;
	color:#edf7f2;
}
.CSSTableGenerator tr:first-child:hover td{
	background:-o-linear-gradient(bottom, #d62a2a 5%, #ffaaaa 100%);	background:-webkit-gradient( linear, left top, left bottom, color-stop(0.05, #d62a2a), color-stop(1, #ffaaaa) );
	background:-moz-linear-gradient( center top, #d62a2a 5%, #ffaaaa 100% );
	filter:progid:DXImageTransform.Microsoft.gradient(startColorstr="#d62a2a", endColorstr="#ffaaaa");	background: -o-linear-gradient(top,#d62a2a,ffaaaa);

	background-color:#d62a2a;
}
.CSSTableGenerator tr:first-child td:first-child{
	border-width:0px 0px 1px 0px;
}
.CSSTableGenerator tr:first-child td:last-child{
	border-width:0px 0px 1px 1px;
}
</STYLE>
<body>

<sj:a button="true"style="height: 17px;margin-top:-5px;margin-right: 10px;width:30px;float: right;"cssClass="button" buttonIcon="ui-icon-print" title="Print"onclick="printDiv('printDiv')"></sj:a>
<div id='printDiv'>
<table border="1" width="99%" style="border-collapse: collapse;" align="center">
	   
	   <tr  style="height: 25px">
					<s:iterator value="dataMap">
						<s:if test="key=='Ac. Manager' || key=='Ac. Manager No' || key=='Corporate Name'">
							<td width="10%"><b><s:property value="%{key}"/>: <b></td>
							<td width="10%"><s:property value="%{value}"/></td>
						</s:if>
					</s:iterator>
				</tr>
	   
				<tr bgcolor="#D8D8D8" style="height: 25px">
					<s:iterator value="dataMap">
						<s:if test="key=='Patient Type' || key=='Patient Name' || key=='Patient Mobile'">
							<td width="10%"><b><s:property value="%{key}"/>: <b></td>
							<td width="10%"><s:property value="%{value}"/></td>
						</s:if>
					</s:iterator>
				</tr>
				
				<tr  style="height: 25px">
					<s:iterator value="dataMap">
						<s:if test="key=='Gender' || key=='Age' || key=='Patient Email'">
							<td width="10%"><b><s:property value="%{key}"/>: <b></td>
							<td width="10%"><s:property value="%{value}"/></td>
						</s:if>
					</s:iterator>
				</tr>
				
				<tr bgcolor="#D8D8D8" style="height: 25px">
					<s:iterator value="dataMap">
						<s:if test="key=='Country' || key=='State' || key=='City'">
							<td width="10%"><b><s:property value="%{key}"/>: <b></td>
							<td width="10%"><s:property value="%{value}"/></td>
						</s:if>
					</s:iterator>
				</tr>
				
				
				<tr  style="height: 25px">
					<s:iterator value="dataMap">
							<s:if test="key=='Service' || key=='Preferred Schedule' || key=='Remarks'">
							<td width="10%"><b><s:property value="%{key}"/>: <b></td>
							<td width="10%"><s:property value="%{value}"/></td>
						</s:if>
					</s:iterator>
				</tr>
	 	</table>
     <div class="clear"></div>
     <div style="height:2px; margin-bottom:2px;" id='1stBlock'></div>
                        <table class="CSSTableGenerator">
                                    <tr >
	                                       <td align="center" width="10%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Status</b></font></td>
	                                       <td align="center" width="10%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Action Date & Time</b></font></td>
	                                       <td align="center" width="10%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Action Reason</b></font></td>
	                                       <td align="center" width="8%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Escalate Level</b></font></td>
	                                       <td align="center" width="10" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Action By</b></font></td>
	                                      <!--  <td align="center" width="10" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b></b>Allocate To</font></td> -->
	                                	   <td align="center" width="10" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b></b>Book Schedule</font></td>
	                                	   <td align="center" width="10" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b></b>Service Manager</font></td>
	                                	   <td align="center" width="10" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b></b>Park Till</font></td>
	                                	   <td align="center" width="10" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b></b>Resolve By</font></td>
	                                	   <td align="center" width="10" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b></b>Location</font></td>
	                                	   <s:iterator value="dataMap">
											<s:if test="value=='EHC'">
											<td align="center" width="10" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b></b>EHC Packages</font></td>
											<td align="center" width="10" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b></b>Packages</font></td>
											</s:if>
											<s:elseif test="value=='Emergency'">
											<td align="center" width="10" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b></b>Specialty</font></td>
											<td align="center" width="10" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b></b>Emergency</font></td>
											</s:elseif>
											 
											<s:elseif test="value=='Radiology'">
											<td align="center" width="10" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b></b>Modality</font></td>
											</s:elseif>
											
											<!--<s:elseif test="value=='Facilitation'">
											<td align="center" width="10" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b></b>Facilitation For</font></td>
											</s:elseif>
											
											<s:elseif test="value=='Telemedicine'">
											<td align="center" width="10" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b></b>Telemedicine For</font></td>
											</s:elseif>
											 
											--><s:elseif test="value=='OPD'">
											<td align="center" width="10" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b></b>Specialty</font></td>
											<td align="center" width="10" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b></b>Doctor Name</font></td>
											</s:elseif>
											
											<s:elseif test="value=='DayCare'">
											<td align="center" width="10" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b></b>Specialty</font></td>
											<td align="center" width="10" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b></b>Doctor Name</font></td>
											</s:elseif>
											
											<s:elseif test="value=='IPD'">
											<td align="center" width="10" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b></b>Specialty</font></td>
											<td align="center" width="10" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b></b>Doctor Name</font></td>
											<td align="center" width="10" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b></b>Bed Type</font></td>
											<td align="center" width="10" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b></b>Payment Type</font></td>
											</s:elseif>
											
											<!--<s:elseif test="value=='Laboratory'">
											<td align="center" width="10" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b></b>Modality</font></td>
											</s:elseif>
							 				-->
							 				
							 				<s:elseif test="value=='Diagnostics'">
											<td align="center" width="10" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b></b>Diagnostics Test</font></td>
											</s:elseif>
											
											<s:elseif test="value=='New Information Request'">
											<td align="center" width="10" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b></b>New Information Comment</font></td>
											</s:elseif>
				 							</s:iterator>
	                                </tr>
	                                <s:iterator value="cpsHisList" var="listVal1" status="counter1">
 									<tr>  
 										<td align="center" width="10%"><b><s:property value="%{#listVal1[0]}" /></b></td>
                                       	<td align="center" width="10%"><s:property value="%{#listVal1[1]}" /></td>
                                     	<td align="center" width="10%"><s:property value="%{#listVal1[3]}" /></td>
                                        <td align="center" width="10%"><s:property value="%{#listVal1[4]}" /></td>
                                        <td align="center" width="10%"><s:property value="%{#listVal1[5]}" /></td>
                                        <!-- <td align="center" width="8%" ><s:property value="%{#listVal1[6]}" /></td> -->
                                        <td align="center" width="10%"><s:property value="%{#listVal1[7]}" /></td>  
                                        <td align="center" width="10%" ><s:property value="%{#listVal1[8]}" /></td>  
                                        <td align="center" width="10%" ><s:property value="%{#listVal1[9]}" /></td> 
                                        <td align="center" width="10%" ><s:property value="%{#listVal1[10]}" /></td>
                                        <td align="center" width="10%" ><s:property value="%{#listVal1[11]}" /></td>    
                                        <s:iterator value="dataMap">
                                        	<s:if test="value=='EHC'">
												<td align="center" width="10%" ><s:property value="%{#listVal1[12]}" /></td>
												<td align="center" width="10%" ><s:property value="%{#listVal1[13]}" /></td>
											</s:if>
											<s:elseif test="value=='Emergency'">
												<td align="center" width="10%" ><s:property value="%{#listVal1[14]}" /></td>
												<td align="center" width="10%" ><s:property value="%{#listVal1[15]}" /></td>
											</s:elseif>
											  
											<s:elseif test="value=='Radiology'">
												<td align="center" width="10%" ><s:property value="%{#listVal1[16]}" /></td>
											</s:elseif>
											
											<!--<s:elseif test="value=='Facilitation'">
												<td align="center" width="10%" ><s:property value="%{#listVal1[28]}" /></td>
											</s:elseif>
											
											<s:elseif test="value=='Telemedicine'">
												<td align="center" width="10%" ><s:property value="%{#listVal1[29]}" /></td>
											</s:elseif>
											 
											--><s:elseif test="value=='OPD'">
												<td align="center" width="10%" ><s:property value="%{#listVal1[17]}" /></td>
												<td align="center" width="10%" ><s:property value="%{#listVal1[18]}" /></td>
											</s:elseif>
											<s:elseif test="value=='DayCare'">
												<td align="center" width="10%" ><s:property value="%{#listVal1[19]}" /></td>
												<td align="center" width="10%" ><s:property value="%{#listVal1[20]}" /></td>
											</s:elseif>
											<s:elseif test="value=='IPD'">
												<td align="center" width="10%" ><s:property value="%{#listVal1[21]}" /></td>
												<td align="center" width="10%" ><s:property value="%{#listVal1[22]}" /></td>
												<td align="center" width="10%" ><s:property value="%{#listVal1[23]}" /></td>
												<td align="center" width="10%" ><s:property value="%{#listVal1[24]}" /></td>
											</s:elseif>
											
											<!--<s:elseif test="value=='Laboratory'">
												<td align="center" width="10%" ><s:property value="%{#listVal1[25]}" /></td>
											</s:elseif>
											-->
											
											<s:elseif test="value=='Diagnostics'">
												<td align="center" width="10%" ><s:property value="%{#listVal1[26]}" /></td>
											</s:elseif>
											<s:elseif test="value=='New Information Request'">
												<td align="center" width="10%" ><s:property value="%{#listVal1[27]}" /></td>
											</s:elseif>
										</s:iterator>
                                  </tr>
                             </s:iterator>
                        </table>
</div>
</body>
</html>