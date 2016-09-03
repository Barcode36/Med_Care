
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<% 
String userTpe=(String)session.getAttribute("userTpe");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<style type="text/css">
#categ tr td{
box-shadow:1px 1px 5px 0px black;
}


</style>
</head>
<body>
<div id="prntTable" style="margin-top: 10px;">
<table id="categ" border="1" width="100%" align="center" bordercolor="#e1e1e1" cellspacing="5px">
    <s:if test="dashFor=='floor'">
		    <tr>
		    
		    	<td align="center"   class="title"><b>&nbsp;Row Labels</b></td>
				<td align="center"   class="title" title="1st Floor"><b>&nbsp;1st Floor</b></td>
				<td align="center"   class="title" title="Floor"><b>&nbsp;2nd Floor</b></td>
				<td align="center"   class="title" title=" Floor"><b>&nbsp;3rd Floor</b></td>
				<td align="center"   class="title" title=" Floor"><b>&nbsp;4th Floor</b></td>
				<td align="center"   class="title" title=" Floor"><b>&nbsp;5th Floor</b></td>
				<td align="center"   class="title" title=" Floor"><b>&nbsp;6th Floor</b></td>
				<td align="center"   class="title" title=" Floor"><b>&nbsp;7th Floor</b></td>
				<td align="center"   class="title" title=" Floor"><b>&nbsp;8th Floor</b></td>
				<td align="center"   class="title" title="Floor"><b>&nbsp;9th Floor</b></td>
				<td align="center"   class="title" title="Floor"><b>&nbsp;10th Floor</b></td>
				<td align="center"   class="title" title=" Floor"><b>&nbsp;11th Floor</b></td>
				<td align="center"   class="title" title=" Floor"><b>&nbsp;12th Floor</b></td>
				<td align="center"   class="title" title=" Floor"><b>&nbsp;14th Floor</b></td>
				<td align="center"   class="title" title=" Floor"><b>&nbsp;15th Floor</b></td>
				<td align="center"   class="title" title=" Floor"><b>&nbsp;LG </b></td>
				<td align="center"   class="title" title=" Floor"><b>&nbsp;UG</b></td>
				<td align="center"   class="title" title=" Floor"><b>&nbsp;Basement</b></td>
				<td align="center"   class="title" title=" Floor"><b>&nbsp;Out Side</b></td>
				<td align="center"   class="title" title=" Floor"><b>&nbsp;Terrace</b></td>
				<td align="center"   class="title" title=" Floor"><b>&nbsp;Total</b></td>
				
			</tr>
		
		<s:iterator id="rsCompl"  status="status" value="%{categPojo.categList}" >
			
			 	<tr>
			 	    <td align="left"    class="titleData" style="color:#004276;background:#EAEAEA " ><b><s:property value="#rsCompl.categName"/></b></td>
			 	    <td align="center"   class="titleData"  ><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.flr1"/></b></a></td>
					<td align="center"   class="titleData"  ><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.flr2"/></b></a></td>
					<td align="center"   class="titleData"  ><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.flr3"/></b></a></td>
					<td align="center"   class="titleData"  ><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.flr4"/></b></a></td>
					<td align="center"   class="titleData"  ><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.flr5"/></b></a></td>
					<td align="center"   class="titleData"  ><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.flr6"/></b></a></td>
					<td align="center"   class="titleData"  ><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.flr7"/></b></a></td>
					<td align="center"   class="titleData"  ><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.flr8"/></b></a></td>
					<td align="center"   class="titleData"  ><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.flr9"/></b></a></td>
					<td align="center"   class="titleData"  ><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.flr10"/></b></a></td>
					<td align="center"   class="titleData"  ><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.flr11"/></b></a></td>
					<td align="center"   class="titleData"  ><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.flr12"/></b></a></td>
					<td align="center"   class="titleData"  ><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.flr14"/></b></a></td>
					<td align="center"   class="titleData"  ><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.flr15"/></b></a></td>
					<td align="center"   class="titleData"  ><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.flrLG"/></b></a></td>
					<td align="center"   class="titleData"  ><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.flrUG"/></b></a></td>
					<td align="center"   class="titleData"  ><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.flrBase"/></b></a></td>
					<td align="center"   class="titleData"  ><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.flrOut"/></b></a></td>
					<td align="center"   class="titleData"  ><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.flrTerr"/></b></a></td>
					<td align="center"   class="titleData"  ><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.total"/></b></a></td>
			 	</tr>
		 	
		</s:iterator>
</s:if>
<s:elseif test="dashFor=='time'">
	    <tr>
		    
		    	<td align="center"   class="title"><b>&nbsp;Row Labels</b></td>
				<td align="center"   class="title" ><b>&nbsp;12 AM</b></td>
				<td align="center"   class="title" ><b>&nbsp;1 AM</b></td>
				<td align="center"   class="title" ><b>&nbsp;2 AM</b></td>
				<td align="center"   class="title" ><b>&nbsp;3 AM</b></td>
				<td align="center"   class="title" ><b>&nbsp;4 AM</b></td>
				<td align="center"   class="title" ><b>&nbsp;5 AM</b></td>
				<td align="center"   class="title" ><b>&nbsp;6 AM</b></td>
				<td align="center"   class="title" ><b>&nbsp;7 AM</b></td>
				<td align="center"   class="title" ><b>&nbsp;8 AM</b></td>
				<td align="center"   class="title" ><b>&nbsp;9 AM</b></td>
				<td align="center"   class="title" ><b>&nbsp;10 AM</b></td>
				<td align="center"   class="title" ><b>&nbsp;11 AM</b></td>
				<td align="center"   class="title" ><b>&nbsp;12 PM</b></td>
				<td align="center"   class="title" ><b>&nbsp;1 PM</b></td>
				<td align="center"   class="title" ><b>&nbsp;2 PM</b></td>
				<td align="center"   class="title" ><b>&nbsp;3 PM</b></td>
				<td align="center"   class="title" ><b>&nbsp;4 PM</b></td>
				<td align="center"   class="title" ><b>&nbsp;5 PM</b></td>
				<td align="center"   class="title" ><b>&nbsp;6 PM</b></td>
				<td align="center"   class="title" ><b>&nbsp;7 PM</b></td>
				<td align="center"   class="title" ><b>&nbsp;8 PM</b></td>
				<td align="center"   class="title" ><b>&nbsp;9 PM</b></td>
				<td align="center"   class="title" ><b>&nbsp;10 PM</b></td>
				<td align="center"   class="title" ><b>&nbsp;11 PM</b></td>
				<td align="center"   class="title"><b>&nbsp;Total</b></td>
			</tr>
		
		<s:iterator id="rsCompl"  status="status" value="%{categPojo.categList}" >
			
			 	<tr>
			 	    <td align="left"    class="titleData" style="color:#004276;background:#EAEAEA " ><b><s:property value="#rsCompl.categName"/></b></td>
			 	    <td align="center"   class="titleData"  ><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.am12"/></b></a></td>
					<td align="center"   class="titleData"  ><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.am1"/></b></a></td>
					<td align="center"   class="titleData"  ><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.am2"/></b></a></td>
					<td align="center"   class="titleData"  ><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.am3"/></b></a></td>
					<td align="center"   class="titleData"  ><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.am4"/></b></a></td>
					<td align="center"   class="titleData"  ><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.am5"/></b></a></td>
					<td align="center"   class="titleData"  ><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.am6"/></b></a></td>
					<td align="center"   class="titleData"  ><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.am7"/></b></a></td>
					<td align="center"   class="titleData"  ><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.am8"/></b></a></td>
					<td align="center"   class="titleData"  ><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.am9"/></b></a></td>
					<td align="center"   class="titleData"  ><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.am10"/></b></a></td>
					<td align="center"   class="titleData"  ><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.am11"/></b></a></td>
					<td align="center"   class="titleData"  ><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.pm12"/></b></a></td>
					<td align="center"   class="titleData"  ><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.pm1"/></b></a></td>
					<td align="center"   class="titleData"  ><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.pm2"/></b></a></td>
					<td align="center"   class="titleData"  ><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.pm3"/></b></a></td>
					<td align="center"   class="titleData"  ><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.pm4"/></b></a></td>
					<td align="center"   class="titleData"  ><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.pm5"/></b></a></td>
					<td align="center"   class="titleData"  ><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.pm6"/></b></a></td>
					<td align="center"   class="titleData"  ><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.pm7"/></b></a></td>
					<td align="center"   class="titleData"  ><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.pm8"/></b></a></td>
					<td align="center"   class="titleData"  ><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.pm9"/></b></a></td>
					<td align="center"   class="titleData"  ><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.pm10"/></b></a></td>
					<td align="center"   class="titleData"  ><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.pm11"/></b></a></td>
					<td align="center"   class="titleData"  ><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.total"/></b></a></td>
			 	</tr>
		 	
		</s:iterator>

</s:elseif>


</table>
</div>


</body>
</html>