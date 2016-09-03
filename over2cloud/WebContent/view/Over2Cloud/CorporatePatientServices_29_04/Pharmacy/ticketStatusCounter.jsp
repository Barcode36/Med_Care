
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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
</head>
<body>
<br>
 <div id="pharmacy" style="width: 100%;overflow: auto;height: 220px;margin-top: -15px; "   >
	<table border="1" width="100%" style="border-collapse: collapse;" class="CSSTableGenerator">
   		
   		<s:if test="tableFor=='statusBar'">
   	 	    <tr>
		    	<td colspan="2"><b>Status Counter</b></td>
	 	    </tr>
		 
		    <tr>
		      
		     	<td><b>STATUS</b></td>
		     	<td><b> COUNTER</b></td>
	  		</tr>
	 	
	 	<s:iterator id="rsCompl"  status="status" value="%{data_list}" >
			 	 	<tr>
			   	   		<td><b><s:property value="#rsCompl.name"/></b></td>
			 	  		<td><b><s:property value="#rsCompl.counter"/></b></td> 
			 	  		 
	 				</tr>
 		</s:iterator>
 	</s:if>
	 
	 
	 
	 <s:elseif test="tableFor=='productivityBar'">
	 
	 		<tr>
		    	<td colspan="2"><b>Level Wise Counter</b></td>
	 	    </tr>
		 
		    <tr>
		      
		     	<td><b>Level</b></td>
		     	<td><b> COUNTER</b></td>
	  		</tr>
	 	
	 	<s:iterator id="rsCompl"  status="status" value="%{data_list}" >
			 	 	<tr>
			   	   		<td><b><s:property value="#rsCompl.name"/></b></td>
			 	  		<td><b><s:property value="#rsCompl.counter"/></b></td> 
			 	  		 
	 				</tr>
 		</s:iterator>
	 
	 </s:elseif>
	 
	 <s:elseif test="tableFor=='locationStatusBar' || tableFor=='nursingUnitStatusBar' ">
	 	<s:if test="tableFor=='nursingUnitStatusBar' ">
	 		<tr>
		    	<td colspan="7"><b>Nursing Unit Wise Status</b></td>
	 	    </tr>
		</s:if> 
			<s:if test="tableFor=='locationStatusBar' ">
	 		<tr>
		    	<td colspan="7"><b>Location Wise Status</b></td>
	 	    </tr>
		</s:if> 
		 
		    <tr>
		      
		     	<td><b>LOCATION</b></td>
		     	<td><b> ORDERED</b></td>
		     	<td><b> DISPATCH</b></td>
		     	<td><b> DISPATCH ERROR</b></td>
		      	<td><b> CLOSE</b></td>
		     	<td><b> ALL</b></td>
	  		</tr>
	 	
	 	<s:iterator id="rsCompl"  status="status" value="%{data_list}" >
			 	 	<tr>
			   	   		<td><b><s:property value="#rsCompl.name"/></b></td>
			   	   		<td><b><s:property value="#rsCompl.ordered"/></b></td> 
			   	   		<td><b><s:property value="#rsCompl.dispatch"/></b></td> 
			   	   		<td><b><s:property value="#rsCompl.dispatchError"/></b></td> 
			   	   		<td><b><s:property value="#rsCompl.close"/></b></td> 
			 	  		<td><b><s:property value="#rsCompl.counter"/></b></td> 
			 	  		 
	 				</tr>
 		</s:iterator>
	 
	 </s:elseif>
	 
	  <s:elseif test="tableFor=='locationProductivityBar' || tableFor=='nursingUnitProductivityBar' ">
	 
	 	<s:if test="tableFor=='nursingUnitProductivityBar' ">
	 		<tr>
		    	<td colspan="7"><b>Nursing Unit Wise Productivity</b></td>
	 	    </tr>
		</s:if> 
			<s:if test="tableFor=='locationProductivityBar' ">
	 		<tr>
		    	<td colspan="7"><b>Location Wise Productivity</b></td>
	 	    </tr>
		</s:if> 
		    <tr>
 		     	<td><b>LOCATION</b></td>
		     	<td><b> LEVEL-1</b></td>
		     	<td><b> LEVEL-2</b></td>
		     	<td><b> LEVEL-3</b></td>
		      	<td><b> LEVEL-4</b></td>
		     	<td><b> LEVEL-5</b></td>
		     	<td><b> ALL</b></td>
	  		</tr>
	 	
	 	<s:iterator id="rsCompl"  status="status" value="%{data_list}" >
			 	 	<tr>
			   	   		<td><b><s:property value="#rsCompl.name"/></b></td>
			   	   		<td><b><s:property value="#rsCompl.level1"/></b></td> 
			   	   		<td><b><s:property value="#rsCompl.level2"/></b></td> 
			   	   		<td><b><s:property value="#rsCompl.level3"/></b></td> 
			   	   		<td><b><s:property value="#rsCompl.level4"/></b></td> 
			   	   		<td><b><s:property value="#rsCompl.level5"/></b></td> 
			 	  		<td><b><s:property value="#rsCompl.counter"/></b></td> 
			 	  		 
	 				</tr>
 		</s:iterator>
	 
	 </s:elseif>
	 
	</table>
	</div>



</body>
</html>