<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
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
<div id="printDiv" style="width:100%">
  
	 		  <table border="1" width="100%" style="border-collapse: collapse;">
	    		
	    		<tr bgcolor="#D8D8D8"  style="height: 25px">
					<s:iterator value="dataMap">
						<s:if test="key=='Ticket No' || key=='UHID'">
							<td width="10%"><b><s:property value="%{key}"/>: <b></td>
							<td width="10%"><s:property value="%{value}"/></td>
						</s:if>
					</s:iterator>
				</tr>
	    			<tr  style="height: 25px">
					<s:iterator value="dataMap">
						<s:if test="key=='Bed No' || key=='Patient Name'">
							<td width="10%"><b><s:property value="%{key}"/>: <b></td>
							<td width="10%"><s:property value="%{value}"/></td>
						</s:if>
					</s:iterator>
				</tr>
				
				<tr bgcolor="#D8D8D8" style="height: 25px">
					<s:iterator value="dataMap">
						<s:if test="key=='Order At' || key=='Nursing Unit'">
							<td width="10%"><b><s:property value="%{key}"/>: <b></td>
							<td width="10%"><s:property value="%{value}"/></td>
						</s:if>
					</s:iterator>
				</tr>
				
				<tr  style="height: 25px">
					<s:iterator value="dataMap">
						<s:if test="key=='Status' || key=='Document No' ">
							<td  width="10%"><b><s:property value="%{key}"/>: <b></td>
			 				<td width="10%"><s:property value="%{value}"/></td>
		 			</s:if>
						 
					</s:iterator>
				</tr>
				 
 		</table>
		
		<div class="clear"></div>
		<div class="clear"></div>
		
		
	 
		
	
	<div id="critical" style="width:auto;height: auto; margin-top: 20px;" >
	<table border="1" width="100%" style="border-collapse: collapse;" class="CSSTableGenerator">
   		    <tr>
		    	<td colspan="9"><b>Ordered Medicine Detail</b></td>
		     
		    </tr>
		    <tr>
		      
		     	<td><b>Order Id</b></td>
		     	<td><b> Medicine Name</b></td>
		     	<td><b> Ordered Qty</b></td>
		     	<td><b> Order At</b></td>
		     	<td><b> Dispatch Qty</b></td>
  				<td><b>Dispatch At</b></td>
  				<td><b> Remaining Qty</b></td>
  				<td><b> Received Qty</b></td>
  				<td><b>Status</b></td>
	 	</tr>
		
		
		<s:iterator id="rsCompl"  status="status" value="%{data_list}" >
			 	 	<tr>
			   	   		<td><b><s:property value="#rsCompl.id"/></b></td>
			 	  		<td><b><s:property value="#rsCompl.name"/></b></td> 
			 	  		<td><b><s:property value="#rsCompl.quantity"/></b></td>  
			 	  		<td><b><s:property value="#rsCompl.orderAt"/></b></td>  
			 	  		<td><b><s:property value="#rsCompl.dispatchQuantity"/></b></td>  
			 	  		<td><b><s:property value="#rsCompl.dispatchAt"/></b></td>  
			 	  		<td><b><s:property value="#rsCompl.remainingQuantity"/></b></td> 
			 	  		<td><b><s:property value="#rsCompl.receivedQuantity"/></b></td>  
			 	  		<td><b><s:property value="#rsCompl.status"/></b></td>  
	 				</tr>
				 
				 
		 	
		</s:iterator>
 
</table>
</div>
<div class="clear"></div>
	 	
	 	
	 	<div id="critical1" style="width: auto;height: auto;margin-top: 20px; " >
		<table border="1" width="100%" style="border-collapse: collapse;" class="CSSTableGenerator">
   		    <tr>
		    	<td colspan="7"><b>Ordered Action History</b></td>
		     
		    </tr>
		    <tr>
		      
		     	<td><b>Status</b></td>
		     	<td><b> Action Date</b></td>
		     	<td><b> Action Time</b></td>
		     	<td><b> Action By</b></td>
		     	<td><b> Close By Id</b></td>
  				<td><b>Close By Name</b></td>
  				<td><b> Comment</b></td>
  				 
	 	</tr>
		
		
		 <s:iterator value="ordHisList" var="listVal1" status="counter1">
			 	 	<tr>
			   	   		<td align="center" ><b><s:property value="%{#listVal1[0]}" /></b></td>
                        <td align="center" ><s:property value="%{#listVal1[1]}" /></td>
                        <td align="center" ><s:property value="%{#listVal1[2]}" /></td>
                        <td align="center"><s:property value="%{#listVal1[3]}" /></td>
                        <td align="center" ><s:property value="%{#listVal1[4]}" /></td>
                        <td align="center"><s:property value="%{#listVal1[5]}" /></td>
                        <td align="center"><s:property value="%{#listVal1[6]}" /></td>				
	 				</tr>
				 
				 
		 	
		</s:iterator>
 
</table>
</div>
	 	 
  </div>
  <div class="clear"></div>
  <div class="clear"></div>
  
              			<center>     
              			<sj:submit 
                        clearForm="true"
                        value="  Print  " 
                        button="true"
                        onclick="printDiv('printDiv')"
                        />
                        </center>
</body>
</html>