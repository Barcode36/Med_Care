<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
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
 
		background:-webkit-gradient( linear, left top, left bottom, color-stop(0.05, rgb(211, 211, 211)), color-stop(1, rgb(9, 51, 80)) );
	background:-moz-linear-gradient( center top, #d62a2a 5%, #ffaaaa 100% );
	filter:progid:DXImageTransform.Microsoft.gradient(startColorstr="#d62a2a", endColorstr="#ffaaaa");	background: -o-linear-gradient(top,#d62a2a,ffaaaa);

	background-color:#d62a2a;
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
</head>
<body>
 <div style="width: 100%; center; padding-top: 10px;">
       <div class="border" style="height: 50%" id="1">
	      
               <table width="100%" border="1">
	    		<tr  bgcolor="lightgrey" style="height: 25px">
	    			<td align="left" ><strong>UHID:</strong></td>
					<td align="left" ><s:property value="#parameters.uhid"/></td>
					<td align="left" ><strong>Patient Name:</strong></td>
					<td align="left" ><s:property value="#parameters.patName"/></td>
				 	<td align="left" ><strong>Location:</strong></td>
					<td align="left" ><s:property value="%{location}"/></td>
					<td align="left" ><strong>Nursing Unit:</strong></td>
					 <td align="left" ><s:property value="#parameters.nursing"/></td>
			    </tr>
			    <tr  bgcolor="white" style="height: 25px">
			          <td align="left" ><strong>Bed No:</strong></td>
					  <td align="left" ><s:property value="#parameters.bedNo"/></td>
					  <td align="left" ><strong>Adm Doctor:</strong></td>
					  <td align="left" ><s:property value="#parameters.admDoc"/></td>
					  <td align="left" ><strong>Speciality:</strong></td>
					  <td align="left" ><s:property value="#parameters.admSpec"/></td>
					  <td align="left" ><strong>Admission At:</strong></td>
					  <td align="left" ><s:property value="#parameters.admAt"/></td>
			   </tr>
			 </table>
<br>
<br>
<table class="CSSTableGenerator"> 
		 	<tr>
		     	<td colspan="6"><b>History</b></td>
	 		</tr>
	 	<tr>
		 
				 <td style="background-color: lavender;color: firebrick; width: 15%;"><b>Status</b></td>
				 <td style="background-color: lavender;color: firebrick; width: 15%;"><b>Level</b></td>
			   	 <td style="background-color: lavender;color: firebrick; width: 15%;"><b>Action At</b></td>
			   	  <td style="background-color: lavender;color: firebrick; width: 15%;"><b>Action By</b></td>
			   	  <td style="background-color: lavender;color: firebrick; width: 15%;"><b>Close By</b></td>
			   	 <td style="background-color: lavender;color: firebrick; width: 20%;" ><b>Comment</b></td>
  				 
  				 
	 	</tr>
		
		<s:iterator id="rsCompl"  status="status" value="%{history_list}" >
			 	 	<tr>
				 	 	<td><b><s:property value="#rsCompl.status"/></b></td> 
				 	 <td><b><s:property value="#rsCompl.level"/></b></td> 
				 	   	<td><b><s:property value="#rsCompl.actionDate"/>&nbsp;&nbsp;&nbsp;<s:property value="#rsCompl.actionTime"/></b></td>
				 	   		<td><b><s:property value="#rsCompl.action_by"/></b></td>   
				 	   			<td><b><s:property value="#rsCompl.close_by"/></b></td>   
				 	   	<td><b><s:property value="#rsCompl.comment"/></b></td> 
	 				</tr>
		</s:iterator>
 
</table>
				    
</div>
</div>
</body>
</html>
