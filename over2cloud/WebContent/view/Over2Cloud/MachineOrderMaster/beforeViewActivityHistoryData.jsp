<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script type="text/javascript">
function printDiv(divName) {
    var printContents = document.getElementById(divName).innerHTML;
   
    var myWindow = window.open("","myWindow","width=900,height=600"); 
    myWindow.document.write(printContents);
    myWindow.moveTo(300,200); 
    myWindow.print();
    myWindow.close();
}

</script>
</head>
<body>
<div id="printDiv">
<table border="1" width="100%" style="border-collapse: collapse;">
	   
				<tr bgcolor="#D8D8D8" style="height: 25px">
					<s:iterator value="dataMap">
						<s:if test="key=='Order Name' || key=='Order Type' || key=='Order By'">
							<td width="10%"><b><s:property value="%{key}"/>: <b></td>
							<td width="10%"><s:property value="%{value}"/></td>
						</s:if>
					</s:iterator>
				</tr>
				
				<tr  style="height: 25px">
					<s:iterator value="dataMap">
						<s:if test="key=='UHID' || key=='Patient Name' || key=='Room No'">
							<td width="10%"><b><s:property value="%{key}"/>: <b></td>
							<td width="10%"><s:property value="%{value}"/></td>
						</s:if>
					</s:iterator>
				</tr>
				
				<tr bgcolor="#D8D8D8" style="height: 25px">
					<s:iterator value="dataMap">
						<s:if test="key=='Nursing Unit' || key=='Priority' || key=='Adm. Doctor'">
							<td width="10%"><b><s:property value="%{key}"/>: <b></td>
							<td width="10%"><s:property value="%{value}"/></td>
						</s:if>
					</s:iterator>
				</tr>
				
				<tr  style="height: 25px">
					<s:iterator value="dataMap">
						<s:if test="key=='Adm. Spec.' || key=='Order Date' || key=='Order Time'">
							<td width="10%"><b><s:property value="%{key}"/>: <b></td>
							<td width="10%"><s:property value="%{value}"/></td>
						</s:if>
					</s:iterator>
				</tr>
				
		 		 
		</table>
		
		
		
		
		<div class="contentdiv-small" style="overflow: hidden;  height:375px; width:100%;  margin-left:0%; margin-right:1px;   border-color: black; background-color: #D8D8D8" >
    <div class="clear"></div>
           <div style="height:14px; margin-bottom:2px;" id='1stBlock'></div>
                  <div style="height:8%; margin-bottom:2px;" align="center"><B> Order Action History</B> </div>
                       <table  align="center" border="0" width="100%" height="7%" style="border-style:dotted solid;">
                               <tbody>
                                    <tr bgcolor="black" bordercolor="black" >
	                                       <td align="center" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Status</b></font></td>
	                                       <td align="center"  height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Action Date</b></font></td>
	                                       <td align="center"  height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Action Time</b></font></td>
	                                       <td align="center" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Action Reason</b></font></td>
	                                       <td align="center" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Escalate Level</b></font></td>
	                                       <td align="center"  height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Action By</b></font></td>
	                                	   <td align="center" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Allocate To</b></font></td>
	                                	   <td align="center" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Resolved By</b></font></td>
	                                </tr>
	                                
	                                
	                                <s:iterator value="ordHisList" var="listVal1" status="counter1">
 									<tr>  <td align="center" ><b><s:property value="%{#listVal1[0]}" /></b></td>
                                           <td align="center" ><s:property value="%{#listVal1[1]}" /></td>
                                           <td align="center" ><s:property value="%{#listVal1[2]}" /></td>
                                           <td align="center"><s:property value="%{#listVal1[3]}" /></td>
                                           <td align="center" ><s:property value="%{#listVal1[4]}" /></td>
                                           <td align="center"><s:property value="%{#listVal1[5]}" />
                                           <td align="center"><s:property value="%{#listVal1[6]}" /></td>
                                           <td align="center"><s:property value="%{#listVal1[7]}" /></td>
                                           
                                    </tr>
                                   
                                   </s:iterator>
                                  
                                    
                                   
                                    
                                    
                               </tbody>
                       </table>
                       </div>
                 </div>
              <center>     <sj:submit 
                        clearForm="true"
                        value="  Print  " 
                        button="true"
                        onclick="printDiv('printDiv')"
                        /></center>
</body>
</html>