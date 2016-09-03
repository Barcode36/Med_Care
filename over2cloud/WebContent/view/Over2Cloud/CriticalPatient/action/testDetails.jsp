<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style type="text/css">

	td
	{
		padding: 2px;
		text-align: center;
	}
</style>
</head>
<body>

<div class="contentdiv-small" style="overflow: auto;  height:355px; width:98%;  margin-left:0%; margin-right:1px;   border-color: black; background-color: #D8D8D8" >
    <div class="clear"></div>
           <div style="height:10px; margin-bottom:2px;" id='1stBlock'></div>
                       <table  align="center" border="0" width="98%" height="7%" style="border-style:dotted solid;">
                               <tbody>
                                    <tr bgcolor="black" bordercolor="black" >
	                                       <td align="center" width="10%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>UHID</b></font></td>
	                                       <td align="center" width="16%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Test Type</b></font></td>
	                                       <td align="center" width="16%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Test Name</b></font></td>
	                                       <td align="center" width="16%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Test Value</b></font></td>
	                                       <td align="center" width="16%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Test Unit</b></font></td>
	                                       <td align="center" width="18%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Test Comment</b></font></td>
	                                </tr>
	                                
	                                <s:iterator value="testDetails" var="listVal1" status="counter1">
 									<tr>  <td align="center" width="10%"><s:property value="%{#listVal1[0]}" /></td>
                                           <td align="center" width="16%"><s:property value="%{#listVal1[1]}" /></td>
                                           <td align="center" width="16%"><s:property value="%{#listVal1[2]}" /></td>
                                           <td align="center" width="16%"><s:property value="%{#listVal1[3]}" /></td>
                                           <td align="center" width="16%"><s:property value="%{#listVal1[4]}" /></td>
                                           <td align="center" width="16%"><s:property value="%{#listVal1[5]}" /></td>
                                    </tr>
                                   
                                   </s:iterator>
                                    
                               </tbody>
                       </table>
                 </div>


</body>
</html>