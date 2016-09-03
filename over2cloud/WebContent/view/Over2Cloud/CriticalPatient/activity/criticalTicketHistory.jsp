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
	
	function viewData()
	{
		//alert($("#data").val());
		
		$("#viewData").html($("#data").val());
		
		
		
	}
	
	
	viewData();

</script>
</head>
<sj:dialog
          id="viewTest"
          showEffect="slide" 
          hideEffect="explode" 
          autoOpen="false"
           width="1000"
          height="350"
          draggable="true"
          resizable="true"
           modal="true"
         onCloseTopics="cancelButtonDialog"
          position="center"
          >
</sj:dialog>
<body>
	<sj:a  button="true" cssStyle="height:25px; width:32px;float:right;"  cssClass="button" buttonIcon="ui-icon-print" title="Print" onClick="getDataForPrint()" ></sj:a>
			<s:hidden name="viewData" id="data" value="%{viewData}"></s:hidden>
			
	<div id="dataForPrint">
		<div id="viewData"></div>
		
	<div class="contentdiv-small" style="overflow: auto;  height:355px; width:98%;  margin-left:0%; margin-right:1px;   border-color: black; background-color: #D8D8D8" >
    <div class="clear"></div>
           <div style="height:10px; margin-bottom:2px;" id='1stBlock'></div>
                       <table  align="center" border="0" width="98%" height="7%" style="border-style:dotted solid;">
                               <tbody>
                                    <tr bgcolor="black" bordercolor="black" >
	                                       <td align="center" width="10%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Status</b></font></td>
	                                       <td align="center" width="16%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Action Date</b></font></td>
	                                       <td align="center" width="16%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Escalate Level</b></font></td>
	                                       <td align="center" width="18%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Action By</b></font></td>
	                                       <td align="center" width="26%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Informed/Seen/Ignore/Park By</b></font></td>
	                             		   <td align="center" width="10%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Nursing Mobile</b></font></td>
	                                       <td align="center" width="16%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Nursing Name</b></font></td>
	                                       <td align="center" width="16%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Doctor Mobile</b></font></td>
	                                       <td align="center" width="18%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Doctor Name</b></font></td>
	                             	       <td align="center" width="16%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Nursing Reason</b></font></td>
	                                       <td align="center" width="16%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Doctor Reason</b></font></td>
	                                       <td align="center" width="16%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Instruction/Patient Mobile</b></font></td>
	                                       <td align="center" width="16%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Comment</b></font></td>
	                                </tr>
	                                
	                                <s:iterator value="dataList" var="listVal1" status="counter1">
 									<tr> 
 										   <td align="center" width="10%"><s:property value="%{#listVal1[0]}" /></td>
                                           <td align="center" width="16%"><s:property value="%{#listVal1[1]}" /></td>
                                           <td align="center" width="16%"><s:property value="%{#listVal1[3]}" /></td>
                                           <td align="center" width="16%"><s:property value="%{#listVal1[4]}" /></td>
                                           <td align="center" width="16%"><s:property value="%{#listVal1[5]}" /></td>
                                            <td align="center" width="10%"><s:property value="%{#listVal1[6]}" /></td>
                                           <td align="center" width="16%"><s:property value="%{#listVal1[12]}" /></td>
                                           <td align="center" width="16%"><s:property value="%{#listVal1[8]}" /></td>
                                           <td align="center" width="16%"><s:property value="%{#listVal1[13]}" /></td>
                                           <td align="center" width="16%"><s:property value="%{#listVal1[10]}" /></td>
                                           <td align="center" width="16%"><s:property value="%{#listVal1[11]}" /></td>
                                           <td align="center" width="16%"><s:property value="%{#listVal1[14]}" /></td>
                                           <td align="center" width="16%"><s:property value="%{#listVal1[15]}" /></td>
                                    </tr>
                                   
                                   </s:iterator>
                                   
                                   
                                    
                               </tbody>
                       </table>
                 </div>
                
       </div>          
</body>
</html>