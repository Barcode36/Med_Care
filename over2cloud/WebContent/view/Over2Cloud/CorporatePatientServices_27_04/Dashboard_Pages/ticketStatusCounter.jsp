
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<style type="text/css">
.bordered {
    border-spacing: 0;
    border: solid #ccc 1px;
    -moz-border-radius: 6px;
    -webkit-border-radius: 6px;
    border-radius: 6px;
    -webkit-box-shadow: 0 1px 1px #ccc;
    -moz-box-shadow: 0 1px 1px #ccc;
    box-shadow: 0 1px 1px #ccc;
    float: left;
    margin: 4px 0px 0px 1px;
}

.thcls {

    background-color: #dce9f9;
    background-image: -webkit-gradient(linear, left top, left bottom, from(#ebf3fc), to(#dce9f9));
    background-image: -webkit-linear-gradient(top, #ebf3fc, #dce9f9);
    background-image: -moz-linear-gradient(top, #ebf3fc, #dce9f9);
    background-image: -ms-linear-gradient(top, #ebf3fc, #dce9f9);
    background-image: -o-linear-gradient(top, #ebf3fc, #dce9f9);
    background-image: linear-gradient(top, #ebf3fc, #dce9f9);
    -webkit-box-shadow: 0 1px 0 rgba(255,255,255,.8) inset;
    -moz-box-shadow: 0 1px 0 rgba(255,255,255,.8) inset;
    box-shadow: 0 1px 0 rgba(255,255,255,.8) inset;
    border-top: none;
    text-shadow: 0 1px 0 rgba(255,255,255,.5);
    
}
.bordered td, .bordered th {
    border-left: 1px solid #ccc;
    border-top: 1px solid #ccc;
    padding: 10px;
    text-align: center;
}
.bordered th:first-child {
    -moz-border-radius: 6px 0 0 0;
    -webkit-border-radius: 6px 0 0 0;
    border-radius: 6px 0 0 0;
}
.bordered td:first-child, .bordered th:first-child {
    border-left: none;
}
.bordered tr:hover {
    background: #fbf8e9;
    -o-transition: all 0.1s ease-in-out;
    -webkit-transition: all 0.1s ease-in-out;
    -moz-transition: all 0.1s ease-in-out;
    -ms-transition: all 0.1s ease-in-out;
    transition: all 0.1s ease-in-out;
}
</style>
</head>
<body>
<br>
<div id="machineord"style="width: 99%; " >
<table border="1" width="100%"   align="center" bordercolor="#0C0C0C" >
    <s:if test="dashFor=='Status'">
		    <tr>
		    	<th class="thcls" align="center"  style="text-align: center;"  colspan="5"  width="2%"  style="color: rgba(53, 54, 58, 0);background: rgba(33, 32, 99, 0.75); " ><b>Status Wise Counter</b></th>
		     
		    </tr>
		    <tr>
		      
		     	<th class="thcls" align="center"  style="text-align: center;color: blue"  title="Department"><b>Machine</b></th>
				<th class="thcls" align="center" style="text-align: center;color: blue"   title="Pending"><b> Pending</b></th>
			 	<th class="thcls" align="center"  style="text-align: center;color: blue"   title="Park"><b> Park</b></th>
				<th class="thcls" align="center" style="text-align: center;color: blue"    title="Resolve"><b> Resolve</b></th>
				<th class="thcls" align="center"  style="text-align: center;color: blue"    title="Total"><b> Total</b></th>
			 
			 	</tr>
		
		
		<s:iterator id="rsCompl"  status="status" value="%{dashObj.dashList}" >
			 	<tr>
		    <th class="thcls" align="center"  style="text-align: center;"><b><s:property value="#rsCompl.deptName"/></b></th>
			<s:if test="#rsCompl.deptName=='Total'">
			 		<td align="center"  style="color:#004276;background:#C2C2CC"  class="titleData" onclick="getData('<s:property value="#rsCompl.id"/>','Pending','status','','');" title="Get Pending Data"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.pct"/></b></a></td>
					<td align="center"  style="color:#004276;background:#C2C2CC"  class="titleData" onclick="getData('<s:property value="#rsCompl.id"/>','Snooze','status','','');" title="Get Park Data"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.sct"/></b></a></td>
					<td align="center"  style="color:#004276;background:#C2C2CC"  class="titleData"  onclick="getData('<s:property value="#rsCompl.id"/>','Resolved','status','','');" title="Get Resolve Data"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.rct"/></b></a></td>
				 	<td align="center" style="color:#004276;background:#C2C2CC" class="titleData"  onclick="getData('<s:property value="#rsCompl.id"/>','All','status','','');" title="Get Total Data"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.grand"/></b></a></td>
			</s:if>
			<s:else>
			 		<td align="center"   class="titleData" onclick="getData('<s:property value="#rsCompl.id"/>','Pending','status','','');" title="Get Pending Data"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.pc"/></b></a></td>
					<td align="center"   class="titleData" onclick="getData('<s:property value="#rsCompl.id"/>','Snooze','status','','');" title="Get Park Data"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.sc"/></b></a></td>
					<td align="center"   class="titleData"  onclick="getData('<s:property value="#rsCompl.id"/>','Resolved','status','','');" title="Get Resolve Data"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.rc"/></b></a></td>
				 	 <td align="center" style="color:#004276;background:#C2C2CC "   class="titleData"  onclick="getData('<s:property value="#rsCompl.id"/>','All','status','','');" title="Get Total Data"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.total"/></b></a></td>
			</s:else>
			
				
				</tr>
		 	
		</s:iterator>
</s:if>

 					

  <s:elseif test="dashFor=='Level'">
     <tr>
		    	<th class="thcls" align="center"  style="text-align: center;"  colspan="5"  width="2%"  style="color: rgba(53, 54, 58, 0);background: rgba(33, 32, 99, 0.75); " ><b>Level Status Wise Counter</b></th>
		     
	 </tr>
 <tr>
		<th   class="thcls" align="center" style="text-align: center;color: blue"><b> Level</b></th>
		<th  class="thcls" align="center" style="text-align: center;color: blue"title="Resolve"><b>Resolve</b></th>
		<th  class="thcls" align="center" style="text-align: center;color: blue"title="Pending"><b>Pending</b></th>
		<th   class="thcls" align="center" style="text-align: center;color: blue"title="Park"><b>Park</b></th>
		<th   class="thcls" align="center" style="text-align: center;color: blue" title="Total"><b>Total</b></th>
	
	</tr>


<s:iterator id="rsCompl12"  status="status" value="%{leveldashObj.dashList}" >
	
 	<tr>
 	 <th class="thcls" align="center"    style="text-align: center;"    ><b><s:property value="#rsCompl12.level"/></b></th>
 	 
 	 <s:if test="#rsCompl12.level=='Total'">
		<td align="center"  style="color:#004276;background:#C2C2CC"  class="titleData" onclick="getData('<s:property value="#rsCompl12.level"/>','Resolved','level','','');" title="Get Resolve Data"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl12.rct"/></b></a></td>
		<td align="center"  style="color:#004276;background:#C2C2CC"  class="titleData" onclick="getData('<s:property value="#rsCompl12.level"/>','Pending','level','','');" title="Get Pending Data"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl12.pct"/></b></a></td>
		<td align="center"  style="color:#004276;background:#C2C2CC"  class="titleData" onclick="getData('<s:property value="#rsCompl12.level"/>','Snooze','level','','');" title="Get Park Data"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl12.sct"/></b></a></td>
		<td align="center"  style="color:#004276;background:#C2C2CC "  class="titleData" onclick="getData('<s:property value="#rsCompl12.level"/>','All','level','','');" title="Get Total Data"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl12.grand"/></b></a></td>
	</s:if>
 	 <s:else>
 	 <td align="center"  class="titleData" onclick="getData('<s:property value="#rsCompl12.level"/>','Resolved','level','','');" title="Get Resolve Data"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl12.rc"/></b></a></td>
		<td align="center"   class="titleData" onclick="getData('<s:property value="#rsCompl12.level"/>','Pending','level','','');" title="Get Pending Data"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl12.pc"/></b></a></td>
		<td align="center"   class="titleData" onclick="getData('<s:property value="#rsCompl12.level"/>','Snooze','level','','');" title="Get Park Data"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl12.sc"/></b></a></td>
		<td align="center"  style="color:#004276;background:#C2C2CC "  class="titleData" onclick="getData('<s:property value="#rsCompl12.level"/>','All','level','','');" title="Get Total Data"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl12.total"/></b></a></td>
 	 </s:else>
		
	
		  	</tr>
 
	</s:iterator>
</s:elseif>


 

<s:elseif test="dashFor=='NursingStatus'">
	<tr>
		    	<th class="thcls" align="center"  style="text-align: center;"  colspan="5"  width="2%"  style="color: rgba(53, 54, 58, 0);background: rgba(33, 32, 99, 0.75); " ><b>Nursing Unit Status Wise Counter</b></th>
		     
	 </tr>
 <tr>
		<th   class="thcls" align="center" style="text-align: center;color: blue" ><b>Nursing Unit</b></th>
		<th   class="thcls" align="center" style="text-align: center;color: blue"  title="ICU"><b>Resolved</b></th>
		<th   class="thcls" align="center" style="text-align: center;color: blue"  title="USG"><b>Pending</b></th>
		<th   class="thcls" align="center" style="text-align: center;color: blue"  title="IPD"><b>Parked</b></th>
		<th   class="thcls" align="center" style="text-align: center;color: blue"  title="Total"><b>Total</b></th>
	
	</tr>


<s:iterator id="rsCompl12"  status="status" value="%{dashObj.dashList}" >
	
 	<tr>
 	    <th class="thcls"  align="center"    style="text-align: center;"   ><b><s:property value="#rsCompl12.deptName"/></b></th>
 	    
 	     <s:if test="#rsCompl12.deptName=='Total'">
 	     <td align="center"   class="titleData" onclick="getData('<s:property value="#rsCompl12.id"/>','Resolved','nursingStatus','','');" title="Get Resolved Data" ><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl12.rct"/></b></a></td>
		<td align="center"   class="titleData" onclick="getData('<s:property value="#rsCompl12.id"/>','Pending','nursingStatus','','');" title="Get Pending Data" ><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl12.pct"/></b></a></td>
		<td align="center"   class="titleData" onclick="getData('<s:property value="#rsCompl12.id"/>','Snooze','nursingStatus','','');" title="Get Parked Data" ><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl12.sct"/></b></a></td>
		<td align="center"  class="titleData"  style="color:#004276;background:#C2C2CC "  onclick="getData('<s:property value="#rsCompl12.id"/>','All','nursingStatus','','');" title="Get Total Data" ><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl12.grand"/></b></a></td>
 	     </s:if>
		<s:else>
		<td align="center"  style="color:#004276;background:#C2C2CC"  class="titleData" onclick="getData('<s:property value="#rsCompl12.id"/>','Resolved','nursingStatus','','');" title="Get Resolved Data" ><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl12.rc"/></b></a></td>
		<td align="center"  style="color:#004276;background:#C2C2CC"  class="titleData" onclick="getData('<s:property value="#rsCompl12.id"/>','Pending','nursingStatus','','');" title="Get Pending Data" ><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl12.pc"/></b></a></td>
		<td align="center"  style="color:#004276;background:#C2C2CC"  class="titleData" onclick="getData('<s:property value="#rsCompl12.id"/>','Snooze','nursingStatus','','');" title="Get Parked Data" ><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl12.sc"/></b></a></td>
		<td align="center" style="color:#004276;background:#C2C2CC"  class="titleData"  style="color:#004276;background:#C2C2CC "  onclick="getData('<s:property value="#rsCompl12.id"/>','All','nursingStatus','','');" title="Get Total Data" ><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl12.total"/></b></a></td>
		</s:else>
	</tr>
 
	</s:iterator>
</s:elseif>

<s:elseif test="dashFor=='statusTime'">
<tr>
		    	<th class="thcls" align="center"  style="text-align: center;"  colspan="5"  width="2%"  style="color: rgba(53, 54, 58, 0);background: rgba(33, 32, 99, 0.75); " ><b>Time Wise Status Counter</b></th>
		     
	 </tr>
 <tr>
		<th   class="thcls" align="center" style="text-align: center;color: blue" ><b>Time</b></th>
		<th   class="thcls" align="center" style="text-align: center;color: blue" ><b>Resolved</b></th>
		<th   class="thcls" align="center" style="text-align: center;color: blue" ><b>Pending</b></th>
		<th   class="thcls" align="center" style="text-align: center;color: blue" ><b>Parked</b></th>
		<th   class="thcls" align="center" style="text-align: center;color: blue" ><b>Total</b></th>
</tr>

<s:iterator id="rsCompl12"  status="status" value="%{dashObj.dashList}" >
	
 	<tr>
 	     <th class="thcls" align="center"    style="text-align: center;" ><b><s:property value="#rsCompl12.time"/></b></th>
 	      
 	      <s:if test="#rsCompl12.time=='Total'">
		<td align="center" style="color:#004276;background:#C2C2CC"  class="titleData" onclick="getData('<s:property value="#rsCompl12.id"/>','Resolved','statusTime','','');" title="Get Resolved Data" ><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl12.rct"/></b></a></td>
		<td align="center"  style="color:#004276;background:#C2C2CC"  class="titleData" onclick="getData('<s:property value="#rsCompl12.id"/>','Pending','statusTime','','');" title="Get Pending Data" ><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl12.pct"/></b></a></td>
		<td align="center" style="color:#004276;background:#C2C2CC"  class="titleData" onclick="getData('<s:property value="#rsCompl12.id"/>','Snooze','statusTime','','');" title="Get Parked Data" ><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl12.sct"/></b></a></td>
		<td align="center"  class="titleData" style="color:#004276;background:#C2C2CC" onclick="getData('<s:property value="#rsCompl12.id"/>','All','statusTime','','');" title="Get Total Data" ><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl12.grand"/></b></a></td>
	</s:if>
	<s:else>
			<td align="center"  class="titleData" onclick="getData('<s:property value="#rsCompl12.id"/>','Resolved','statusTime','','');" title="Get Resolved Data" ><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl12.rc"/></b></a></td>
		<td align="center"   class="titleData" onclick="getData('<s:property value="#rsCompl12.id"/>','Pending','statusTime','','');" title="Get Pending Data" ><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl12.pc"/></b></a></td>
		<td align="center"  class="titleData" onclick="getData('<s:property value="#rsCompl12.id"/>','Snooze','statusTime','','');" title="Get Parked Data" ><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl12.sc"/></b></a></td>
		<td align="center"  class="titleData" style="color:#004276;background:#C2C2CC" onclick="getData('<s:property value="#rsCompl12.id"/>','All','statusTime','','');" title="Get Total Data" ><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl12.total"/></b></a></td>
	</s:else>
	</tr>
 
	</s:iterator>
</s:elseif>

<s:elseif test="dashFor=='floorTime'">
<tr>
		    	<th class="thcls" align="center"  style="text-align: center;"  colspan="5"  width="2%"  style="color: rgba(53, 54, 58, 0);background: rgba(33, 32, 99, 0.75); " ><b>Location Time Wise Status Counter</b></th>
		     
	 </tr>
 <tr>
		<th class="thcls" align="center" style="text-align: center;color: blue" ><b> Floor</b></th>
		<th   class="thcls" align="center" style="text-align: center;color: blue" ><b>Resolved</b></th>
		<th   class="thcls" align="center" style="text-align: center;color: blue" ><b>Pending</b></th>
		<th   class="thcls" align="center" style="text-align: center;color: blue" ><b>Parked</b></th>
		<th   class="thcls" align="center" style="text-align: center;color: blue" ><b>Total</b></th>
</tr>

<s:iterator id="rsCompl12"  status="status" value="%{dashObj.dashList}" >
	
 	<tr>
 	     <th class="thcls" align="center"    style="text-align: center;"   ><b><s:property value="#rsCompl12.deptName"/></b></th>
 	      <s:if test="#rsCompl12.deptName=='Total'">
		<td align="center"  style="color:#004276;background:#C2C2CC"  class="titleData" onclick="getData('<s:property value="#rsCompl12.id"/>','Resolved','floorTime','','');" title="Get Resolved Data" ><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl12.rct"/></b></a></td>
		<td align="center"  class="titleData"style="color:#004276;background:#C2C2CC"  onclick="getData('<s:property value="#rsCompl12.id"/>','Pending','floorTime','','');" title="Get Pending Data" ><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl12.pct"/></b></a></td>
		<td align="center"  class="titleData" style="color:#004276;background:#C2C2CC" onclick="getData('<s:property value="#rsCompl12.id"/>','Snooze','floorTime','','');" title="Get Parked Data" ><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl12.sct"/></b></a></td>
		<td align="center"   style="color:#004276;background:#C2C2CC" class="titleData" onclick="getData('<s:property value="#rsCompl12.id"/>','All','floorTime','','');" title="Get Total Data" ><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl12.grand"/></b></a></td>
		</s:if>
		<s:else>
		<td align="center"   class="titleData" onclick="getData('<s:property value="#rsCompl12.id"/>','Resolved','floorTime','','');" title="Get Resolved Data" ><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl12.rc"/></b></a></td>
		<td align="center"  class="titleData" onclick="getData('<s:property value="#rsCompl12.id"/>','Pending','floorTime','','');" title="Get Pending Data" ><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl12.pc"/></b></a></td>
		<td align="center"  class="titleData" onclick="getData('<s:property value="#rsCompl12.id"/>','Snooze','floorTime','','');" title="Get Parked Data" ><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl12.sc"/></b></a></td>
		<td align="center"   style="color:#004276;background:#C2C2CC" class="titleData" onclick="getData('<s:property value="#rsCompl12.id"/>','All','floorTime','','');" title="Get Total Data" ><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl12.total"/></b></a></td>
		</s:else>
	</tr>
 
	</s:iterator>
</s:elseif>

<s:elseif test="dashFor=='nursingUnitTime'">
	<tr>
		    	<th class="thcls" align="center"  style="text-align: center;"  colspan="5"  width="2%"  style="color: rgba(53, 54, 58, 0);background: rgba(33, 32, 99, 0.75); " ><b>Nursing Unit Time Wise Status Counter</b></th>
		     
	 </tr>
 <tr>
		<th class="thcls" align="center" style="text-align: center;color: blue"><b> Nursing Unit</b></th>
		<th class="thcls" align="center" style="text-align: center;color: blue"><b>Resolved</b></th>
		<th class="thcls" align="center" style="text-align: center;color: blue"><b>Pending</b></th>
		<th class="thcls" align="center" style="text-align: center;color: blue"><b>Parked</b></th>
		 <th class="thcls" align="center" style="text-align: center;color: blue"><b>Total</b></th>
</tr>

<s:iterator id="rsCompl12"  status="status" value="%{dashObj.dashList}" >
	
 	<tr>
 	    <th class="thcls" align="center"    style="text-align: center;"    ><b><s:property value="#rsCompl12.deptName"/></b></th>
 	    
 	      <s:if test="#rsCompl12.deptName=='Total'">
 	    <td align="center" style="color:#004276;background:#C2C2CC"   class="titleData" onclick="getData('<s:property value="#rsCompl12.id"/>','Resolved','nursingUnitTime','','');" title="Get Resolved Data" ><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl12.rct"/></b></a></td>
		<td align="center"  style="color:#004276;background:#C2C2CC"  class="titleData" onclick="getData('<s:property value="#rsCompl12.id"/>','Pending','nursingUnitTime','','');" title="Get Pending Data" ><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl12.pct"/></b></a></td>
		<td align="center"  style="color:#004276;background:#C2C2CC"  class="titleData" onclick="getData('<s:property value="#rsCompl12.id"/>','Snooze','nursingUnitTime','','');" title="Get Parked Data" ><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl12.sct"/></b></a></td>
		<td align="center" style="color:#004276;background:#C2C2CC"  class="titleData" onclick="getData('<s:property value="#rsCompl12.id"/>','All','nursingUnitTime','','');" title="Get Total Data" ><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl12.grand"/></b></a></td>
		</s:if>
		<s:else>
		<td align="center"   class="titleData" onclick="getData('<s:property value="#rsCompl12.id"/>','Resolved','nursingUnitTime','','');" title="Get Resolved Data" ><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl12.rc"/></b></a></td>
		<td align="center"   class="titleData" onclick="getData('<s:property value="#rsCompl12.id"/>','Pending','nursingUnitTime','','');" title="Get Pending Data" ><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl12.pc"/></b></a></td>
		<td align="center"   class="titleData" onclick="getData('<s:property value="#rsCompl12.id"/>','Snooze','nursingUnitTime','','');" title="Get Parked Data" ><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl12.sc"/></b></a></td>
		<td align="center" style="color:#004276;background:#C2C2CC"  class="titleData" onclick="getData('<s:property value="#rsCompl12.id"/>','All','nursingUnitTime','','');" title="Get Total Data" ><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl12.total"/></b></a></td>
		</s:else>
	</tr>
 
	</s:iterator>
</s:elseif>


<s:elseif test="dashFor=='statusFloor'">
<tr>
		    	<th class="thcls" align="center"  style="text-align: center;"  colspan="5"  width="2%"  style="color: rgba(53, 54, 58, 0);background: rgba(33, 32, 99, 0.75); " ><b>Location  Wise Status Counter</b></th>
		     
	 </tr>
 <tr>
		<th class="thcls" align="center" style="text-align: center;color: blue"><b> Location</b></th>
		<th class="thcls" align="center" style="text-align: center;color: blue"><b>Resolved</b></th>
		<th class="thcls" align="center" style="text-align: center;color: blue"><b>Pending</b></th>
		<th class="thcls" align="center" style="text-align: center;color: blue"><b>Parked</b></th>
		<th class="thcls" align="center" style="text-align: center;color: blue"><b>Total</b></th>
		 
	</tr>


<s:iterator id="rsCompl12"  status="status" value="%{dashObj.dashList}" >
	
 	<tr>
 	    <th class="thcls" align="center"    style="text-align: center;" ><b><s:property value="#rsCompl12.deptName"/></b></th>
		
		  <s:if test="#rsCompl12.deptName=='Total'">
		<td align="center" style="color:#004276;background:#C2C2CC"   class="titleData" onclick="getData('<s:property value="#rsCompl12.id"/>','Resolved','floorStatus','','');" title="Get Resolved Data" ><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl12.rct"/></b></a></td>
		<td align="center" style="color:#004276;background:#C2C2CC"   class="titleData" onclick="getData('<s:property value="#rsCompl12.id"/>','Pending','floorStatus','','');" title="Get Pending Data" ><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl12.pct"/></b></a></td>
		<td align="center" style="color:#004276;background:#C2C2CC"  class="titleData" onclick="getData('<s:property value="#rsCompl12.id"/>','Snooze','floorStatus','','');" title="Get Parked Data" ><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl12.sct"/></b></a></td>
		<td align="center" style="color:#004276;background:#C2C2CC" class="titleData" onclick="getData('<s:property value="#rsCompl12.id"/>','All','floorStatus','','');" title="Get Total Data" ><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl12.grand"/></b></a></td>
		</s:if>
		<s:else>
		<td align="center"   class="titleData" onclick="getData('<s:property value="#rsCompl12.id"/>','Resolved','floorStatus','','');" title="Get Resolved Data" ><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl12.rc"/></b></a></td>
		<td align="center"   class="titleData" onclick="getData('<s:property value="#rsCompl12.id"/>','Pending','floorStatus','','');" title="Get Pending Data" ><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl12.pc"/></b></a></td>
		<td align="center"  class="titleData" onclick="getData('<s:property value="#rsCompl12.id"/>','Snooze','floorStatus','','');" title="Get Parked Data" ><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl12.sc"/></b></a></td>
		<td align="center" style="color:#004276;background:#C2C2CC" class="titleData" onclick="getData('<s:property value="#rsCompl12.id"/>','All','floorStatus','','');" title="Get Total Data" ><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl12.total"/></b></a></td>
		</s:else>
	</tr>
 
	</s:iterator>
</s:elseif>

<s:elseif test="dashFor=='NursingOrder'">
<tr>
		    	<th class="thcls" align="center"  style="text-align: center;"  colspan="5"  width="2%"  style="color: rgba(53, 54, 58, 0);background: rgba(33, 32, 99, 0.75); " ><b>Nursing Unit Order Wise Counter</b></th>
		     
	 </tr>
 <tr>
		<th class="thcls" align="center" style="text-align: center;color: blue"><b>Nursing Unit</b></th>
		<th class="thcls" align="center" style="text-align: center;color: blue"><b>Routine</b></th>
		<th class="thcls" align="center" style="text-align: center;color: blue"><b>Urgent</b></th>
		<th class="thcls" align="center" style="text-align: center;color: blue"><b>Stat</b></th>
		<th class="thcls" align="center" style="text-align: center;color: blue"><b>Total</b></th>
		 
	</tr>


<s:iterator id="rsCompl12"  status="status" value="%{dashObj.dashList}" >
	
 	<tr>
 	     <th class="thcls" align="center"    style="text-align: center;"  ><b><s:property value="#rsCompl12.deptName"/></b></th>
 	       <s:if test="#rsCompl12.deptName=='Total'">
		<td align="center"  style="color:#004276;background:#C2C2CC"  class="titleData" onclick="getData('<s:property value="#rsCompl12.id"/>','Routine','nursingOrder','','');" title="Get Routine Data"  ><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl12.rct"/></b></a></td>
		<td align="center" style="color:#004276;background:#C2C2CC"   class="titleData" onclick="getData('<s:property value="#rsCompl12.id"/>','Urgent','nursingOrder','','');" title="Get Urgent Data"  ><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl12.pct"/></b></a></td>
		<td align="center" style="color:#004276;background:#C2C2CC"  class="titleData" onclick="getData('<s:property value="#rsCompl12.id"/>','Stat','nursingOrder','','');" title="Get Stat Data"  ><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl12.sct"/></b></a></td>
  		<td align="center" style="color:#004276;background:#C2C2CC" class="titleData" onclick="getData('<s:property value="#rsCompl12.id"/>','All','nursingOrder','','');" title="Get Total Data"  ><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl12.grand"/></b></a></td>
  </s:if>
  <s:else>
  	<td align="center"   class="titleData" onclick="getData('<s:property value="#rsCompl12.id"/>','Routine','nursingOrder','','');" title="Get Routine Data"  ><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl12.rc"/></b></a></td>
		<td align="center"   class="titleData" onclick="getData('<s:property value="#rsCompl12.id"/>','Urgent','nursingOrder','','');" title="Get Urgent Data"  ><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl12.pc"/></b></a></td>
		<td align="center"  class="titleData" onclick="getData('<s:property value="#rsCompl12.id"/>','Stat','nursingOrder','','');" title="Get Stat Data"  ><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl12.sc"/></b></a></td>
  		<td align="center" style="color:#004276;background:#C2C2CC" class="titleData" onclick="getData('<s:property value="#rsCompl12.id"/>','All','nursingOrder','','');" title="Get Total Data"  ><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl12.total"/></b></a></td>
  </s:else>
  </tr>
 
	</s:iterator>
</s:elseif>

<s:elseif test="dashFor=='orderFloor'">
	<tr>
		    	<th class="thcls" align="center"  style="text-align: center;"  colspan="5"  width="2%"  style="color: rgba(53, 54, 58, 0);background: rgba(33, 32, 99, 0.75); " ><b>Location Order Wise Counter</b></th>
		     
	 </tr>
 <tr>
		<th class="thcls" align="center" style="text-align: center;color: blue"><b> Location</b></th>
		<th class="thcls" align="center" style="text-align: center;color: blue"><b>Routine</b></th>
		<th class="thcls" align="center" style="text-align: center;color: blue"><b>Urgent</b></th>
		<th class="thcls" align="center" style="text-align: center;color: blue"><b>Stat</b></th>
		 <th class="thcls" align="center" style="text-align: center;color: blue"><b>Total</b></th>
	
	</tr>


<s:iterator id="rsCompl12"  status="status" value="%{dashObj.dashList}" >
	
 	<tr>
 	   <th class="thcls" align="center"    style="text-align: center;"  ><b><s:property value="#rsCompl12.deptName"/></b></th>
 	   
 	     <s:if test="#rsCompl12.deptName=='Total'">
		<td align="center"  style="color:#004276;background:#C2C2CC"  class="titleData" onclick="getData('<s:property value="#rsCompl12.id"/>','Routine','floorOrder','','');" title="Get Routine Data"  ><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl12.rct"/></b></a></td>
		<td align="center"  style="color:#004276;background:#C2C2CC"  class="titleData" onclick="getData('<s:property value="#rsCompl12.id"/>','Urgent','floorOrder','','');" title="Get Urgent Data"  ><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl12.pct"/></b></a></td>
		<td align="center"  style="color:#004276;background:#C2C2CC"  class="titleData" onclick="getData('<s:property value="#rsCompl12.id"/>','Stat','floorOrder','','');" title="Get Stat Data"  ><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl12.sct"/></b></a></td>
		<td align="center" style="color:#004276;background:#C2C2CC" class="titleData" onclick="getData('<s:property value="#rsCompl12.id"/>','All','floorOrder','','');" title="Get Total Data"  ><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl12.grand"/></b></a></td>
		</s:if>
		<s:else>
		<td align="center"   class="titleData" onclick="getData('<s:property value="#rsCompl12.id"/>','Routine','floorOrder','','');" title="Get Routine Data"  ><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl12.rc"/></b></a></td>
		<td align="center"   class="titleData" onclick="getData('<s:property value="#rsCompl12.id"/>','Urgent','floorOrder','','');" title="Get Urgent Data"  ><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl12.pc"/></b></a></td>
		<td align="center"   class="titleData" onclick="getData('<s:property value="#rsCompl12.id"/>','Stat','floorOrder','','');" title="Get Stat Data"  ><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl12.sc"/></b></a></td>
		<td align="center" style="color:#004276;background:#C2C2CC" class="titleData" onclick="getData('<s:property value="#rsCompl12.id"/>','All','floorOrder','','');" title="Get Total Data"  ><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl12.total"/></b></a></td>
		</s:else>
	
	</tr>
 
	</s:iterator>
</s:elseif>

 
  
</table>
</div>
</body>
</html>