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
<div id="cpsId" style="width: 99%; " >
<table border="1" width="100%"   align="center" bordercolor="#0C0C0C" >
    <s:if test="tableFor=='accountManager'">
		    <tr>
		    	<th class="thcls" align="center"  style="text-align: center;"  colspan="7"  width="2%"  style="color: rgba(53, 54, 58, 0);background: rgba(33, 32, 99, 0.75); " ><b>Account Manager Wise Counter</b></th>
		     
		    </tr>
		    <tr>
		      
		     	<th class="thcls" align="center"  style="text-align: center;color: blue"  title="Account Manager"><b>Account Manager</b></th>
		     	<th class="thcls" align="center" style="text-align: center;color: blue"   title="Appointment"><b> Appointment</b></th>
		     	<th class="thcls" align="center" style="text-align: center;color: blue"   title="Scheduled"><b> Scheduled</b></th>
		     	<th class="thcls" align="center" style="text-align: center;color: blue"   title="Service In"><b> Service In</b></th>
		     	<th class="thcls" align="center" style="text-align: center;color: blue"   title="Service Out"><b> Service Out</b></th>
		     	<th class="thcls" align="center" style="text-align: center;color: blue"   title="Cancel"><b> Cancel</b></th>
				<th class="thcls" align="center" style="text-align: center;color: blue"   title="Total"><b>Total</b></th>
			  
			 	</tr>
		
		
		<s:iterator id="rsCompl"  status="status" value="%{dashObj.dashList}" >
			
			 	<tr>
			 	   		<th class="thcls" align="center"  style="text-align: center;"><b><s:property value="#rsCompl.name"/></b></th>
			 	    	<td align="center"   class="titleData" onclick="getData('<s:property value="#rsCompl.id"/>','Appointment','ac_manager','','');" title="Get Appointment Data"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.appointment"/></b></a></td>
	 					<td align="center"   class="titleData" onclick="getData('<s:property value="#rsCompl.id"/>','Scheduled','ac_manager','','');" title="Get Scheduled Data"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.scheduled"/></b></a></td>
		 				<td align="center"   class="titleData" onclick="getData('<s:property value="#rsCompl.id"/>','Service In','ac_manager','','');" title="Get Service In Data"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.serviceIn"/></b></a></td>
		 				<td align="center"   class="titleData" onclick="getData('<s:property value="#rsCompl.id"/>','Service Out','ac_manager','','');" title="Get Service Out Data"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.serviceOut"/></b></a></td>
						<td align="center"   class="titleData" onclick="getData('<s:property value="#rsCompl.id"/>','Cancel','ac_manager','','');" title="Get Cancel Data"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.cancel"/></b></a></td>
						<td align="center" style="color:#004276;background:#C2C2CC "   class="titleData"  onclick="getData('<s:property value="#rsCompl.id"/>','All','ac_manager','','');" title="Get Total Data"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.counter"/></b></a></td>
		 		  	
				</tr>
		 	
		</s:iterator>
</s:if>
 <s:elseif test="tableFor=='corporateServiceManager' || tableFor=='serviceManagerForService' || tableFor=='serviceManagerForAcntMng' || tableFor=='serviceManager'">
     <tr>
		    	<th class="thcls" align="center"  style="text-align: center;"  colspan="7"  width="2%"  style="color: rgba(53, 54, 58, 0);background: rgba(33, 32, 99, 0.75); " ><b>Service Manager Wise Counter</b></th>
		     
		    </tr>
		    <tr>
		      
		     	<th class="thcls" align="center"  style="text-align: center;color: blue"  title="Department"><b>Service Manager</b></th>
				<th class="thcls" align="center" style="text-align: center;color: blue"   title="Appointment"><b> Appointment</b></th>
		     	<th class="thcls" align="center" style="text-align: center;color: blue"   title="Scheduled"><b> Scheduled</b></th>
		     	<th class="thcls" align="center" style="text-align: center;color: blue"   title="Service In"><b> Service In</b></th>
		     	<th class="thcls" align="center" style="text-align: center;color: blue"   title="Service Out"><b> Service Out</b></th>
		     	<th class="thcls" align="center" style="text-align: center;color: blue"   title="Cancel"><b> Cancel</b></th>
				<th class="thcls" align="center" style="text-align: center;color: blue"   title="Total"><b>Total</b></th>
			  
			 	</tr>
		
		
		<s:iterator id="rsCompl"  status="status" value="%{dashObj.dashList}" >
			
			 	<tr>
			 			<th class="thcls" align="center"  style="text-align: center;"><b><s:property value="#rsCompl.name"/></b></th>
			 	    	<td align="center"   class="titleData" onclick="getData('<s:property value="#rsCompl.id"/>','Appointment','ser_manager','','');" title="Get Appointment Data"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.appointment"/></b></a></td>
	 					<td align="center"   class="titleData" onclick="getData('<s:property value="#rsCompl.id"/>','Scheduled','ser_manager','','');" title="Get Scheduled Data"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.scheduled"/></b></a></td>
		 				<td align="center"   class="titleData" onclick="getData('<s:property value="#rsCompl.id"/>','Service In','ser_manager','','');" title="Get Service In Data"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.serviceIn"/></b></a></td>
		 				<td align="center"   class="titleData" onclick="getData('<s:property value="#rsCompl.id"/>','Service Out','ser_manager','','');" title="Get Service Out Data"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.serviceOut"/></b></a></td>
						<td align="center"   class="titleData" onclick="getData('<s:property value="#rsCompl.id"/>','Cancel','ser_manager','','');" title="Get Cancel Data"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.cancel"/></b></a></td>
						<td align="center" style="color:#004276;background:#C2C2CC "   class="titleData"  onclick="getData('<s:property value="#rsCompl.id"/>','All','ser_manager','','');" title="Get Total Data"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.counter"/></b></a></td>
		 
				</tr>
		 	
		</s:iterator>
</s:elseif>
 					

  <s:elseif test="tableFor=='corporateForAcntMng' || tableFor=='corporate'">
     <tr>
		    	<th class="thcls" align="center"  style="text-align: center;"  colspan="7"  width="2%"  style="color: rgba(53, 54, 58, 0);background: rgba(33, 32, 99, 0.75); " ><b>Corporate Wise Counter</b></th>
		     
		    </tr>
		    <tr>
		      
		     	<th class="thcls" align="center"  style="text-align: center;color: blue"  title="Department"><b>Corporate</b></th>
				<th class="thcls" align="center" style="text-align: center;color: blue"   title="Appointment"><b> Appointment</b></th>
		     	<th class="thcls" align="center" style="text-align: center;color: blue"   title="Scheduled"><b> Scheduled</b></th>
		     	<th class="thcls" align="center" style="text-align: center;color: blue"   title="Service In"><b> Service In</b></th>
		     	<th class="thcls" align="center" style="text-align: center;color: blue"   title="Service Out"><b> Service Out</b></th>
		     	<th class="thcls" align="center" style="text-align: center;color: blue"   title="Cancel"><b> Cancel</b></th>
				<th class="thcls" align="center" style="text-align: center;color: blue"   title="Total"><b>Total</b></th>
			  
			 	</tr>
		
		
		<s:iterator id="rsCompl"  status="status" value="%{dashObj.dashList}" >
			
			 	<tr>
			 			<th class="thcls" align="center"  style="text-align: center;"><b><s:property value="#rsCompl.name"/></b></th>
			 	    	<td align="center"   class="titleData" onclick="getData('<s:property value="#rsCompl.id"/>','Appointment','corporate','','');" title="Get Appointment Data"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.appointment"/></b></a></td>
	 					<td align="center"   class="titleData" onclick="getData('<s:property value="#rsCompl.id"/>','Scheduled','corporate','','');" title="Get Scheduled Data"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.scheduled"/></b></a></td>
		 				<td align="center"   class="titleData" onclick="getData('<s:property value="#rsCompl.id"/>','Service In','corporate','','');" title="Get Service In Data"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.serviceIn"/></b></a></td>
		 				<td align="center"   class="titleData" onclick="getData('<s:property value="#rsCompl.id"/>','Service Out','corporate','','');" title="Get Service Out Data"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.serviceOut"/></b></a></td>
						<td align="center"   class="titleData" onclick="getData('<s:property value="#rsCompl.id"/>','Cancel','corporate','','');" title="Get Cancel Data"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.cancel"/></b></a></td>
						<td align="center" style="color:#004276;background:#C2C2CC "   class="titleData"  onclick="getData('<s:property value="#rsCompl.id"/>','All','corporate','','');" title="Get Total Data"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.counter"/></b></a></td>
		 
				</tr>
		 	
		</s:iterator>
</s:elseif>
 <s:elseif test="tableFor=='serviceForAcntMng' || tableFor=='serviceForLocation' || tableFor=='service' || tableFor=='serviceDOC' || tableFor=='corporateService'">
     <tr>
		    	<th class="thcls" align="center"  style="text-align: center;"  colspan="7"  width="2%"  style="color: rgba(53, 54, 58, 0);background: rgba(33, 32, 99, 0.75); " ><b>Service Wise Counter</b></th>
		     
		    </tr>
		    <tr>
		      
		     	<th class="thcls" align="center"  style="text-align: center;color: blue"  title="Department"><b>Services</b></th>
				<th class="thcls" align="center" style="text-align: center;color: blue"   title="Appointment"><b> Appointment</b></th>
		     	<th class="thcls" align="center" style="text-align: center;color: blue"   title="Scheduled"><b> Scheduled</b></th>
		     	<th class="thcls" align="center" style="text-align: center;color: blue"   title="Service In"><b> Service In</b></th>
		     	<th class="thcls" align="center" style="text-align: center;color: blue"   title="Service Out"><b> Service Out</b></th>
		     	<th class="thcls" align="center" style="text-align: center;color: blue"   title="Cancel"><b> Cancel</b></th>
				<th class="thcls" align="center" style="text-align: center;color: blue"   title="Total"><b>Total</b></th>
			  
			 	</tr>
		
		
		<s:iterator id="rsCompl"  status="status" value="%{dashObj.dashList}" >
			
			 	<tr>
			 	 		<th class="thcls" align="center"  style="text-align: center;"><b><s:property value="#rsCompl.name"/></b></th>
			 	    	<td align="center"   class="titleData" onclick="getData('<s:property value="#rsCompl.id"/>','Appointment','service','','');" title="Get Appointment Data"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.appointment"/></b></a></td>
	 					<td align="center"   class="titleData" onclick="getData('<s:property value="#rsCompl.id"/>','Scheduled','service','','');" title="Get Scheduled Data"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.scheduled"/></b></a></td>
		 				<td align="center"   class="titleData" onclick="getData('<s:property value="#rsCompl.id"/>','Service In','service','','');" title="Get Service In Data"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.serviceIn"/></b></a></td>
		 				<td align="center"   class="titleData" onclick="getData('<s:property value="#rsCompl.id"/>','Service Out','service','','');" title="Get Service Out Data"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.serviceOut"/></b></a></td>
						<td align="center"   class="titleData" onclick="getData('<s:property value="#rsCompl.id"/>','Cancel','service','','');" title="Get Cancel Data"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.cancel"/></b></a></td>
						<td align="center" style="color:#004276;background:#C2C2CC "   class="titleData"  onclick="getData('<s:property value="#rsCompl.id"/>','All','service','','');" title="Get Total Data"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.counter"/></b></a></td>
		 
			 	
				</tr>
		 	
		</s:iterator>
</s:elseif>
 
<s:elseif test="tableFor=='locationForAcntMng' ||  tableFor=='locationForSpeciality' || tableFor=='location' || tableFor=='locationForService' || tableFor=='corporateLocation'">
     <tr>
		    	<th class="thcls" align="center"  style="text-align: center;"  colspan="7"  width="2%"  style="color: rgba(53, 54, 58, 0);background: rgba(33, 32, 99, 0.75); " ><b>Location Wise Counter</b></th>
		     
		    </tr>
		    <tr>
		      
		     	<th class="thcls" align="center"  style="text-align: center;color: blue"  title="Department"><b>Location</b></th>
				<th class="thcls" align="center" style="text-align: center;color: blue"   title="Appointment"><b> Appointment</b></th>
		     	<th class="thcls" align="center" style="text-align: center;color: blue"   title="Scheduled"><b> Scheduled</b></th>
		     	<th class="thcls" align="center" style="text-align: center;color: blue"   title="Service In"><b> Service In</b></th>
		     	<th class="thcls" align="center" style="text-align: center;color: blue"   title="Service Out"><b> Service Out</b></th>
		     	<th class="thcls" align="center" style="text-align: center;color: blue"   title="Cancel"><b> Cancel</b></th>
				<th class="thcls" align="center" style="text-align: center;color: blue"   title="Total"><b>Total</b></th>
			  
			 	</tr>
		
		
		<s:iterator id="rsCompl"  status="status" value="%{dashObj.dashList}" >
			
			 	<tr>
			 	 		<th class="thcls" align="center"  style="text-align: center;"><b><s:property value="#rsCompl.name"/></b></th>
			 	    	<td align="center"   class="titleData" onclick="getData('<s:property value="#rsCompl.id"/>','Appointment','location','','');" title="Get Appointment Data"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.appointment"/></b></a></td>
	 					<td align="center"   class="titleData" onclick="getData('<s:property value="#rsCompl.id"/>','Scheduled','location','','');" title="Get Scheduled Data"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.scheduled"/></b></a></td>
		 				<td align="center"   class="titleData" onclick="getData('<s:property value="#rsCompl.id"/>','Service In','location','','');" title="Get Service In Data"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.serviceIn"/></b></a></td>
		 				<td align="center"   class="titleData" onclick="getData('<s:property value="#rsCompl.id"/>','Service Out','location','','');" title="Get Service Out Data"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.serviceOut"/></b></a></td>
						<td align="center"   class="titleData" onclick="getData('<s:property value="#rsCompl.id"/>','Cancel','location','','');" title="Get Cancel Data"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.cancel"/></b></a></td>
						<td align="center" style="color:#004276;background:#C2C2CC "   class="titleData"  onclick="getData('<s:property value="#rsCompl.id"/>','All','location','','');" title="Get Total Data"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.counter"/></b></a></td>
		 
			 	
				</tr>
		 	
		</s:iterator>
</s:elseif>


<!-- Speciality -->
<s:elseif test="tableFor=='locationForSpecialitys' || tableFor=='corporateServiceSpeciality'">
     <tr>
		    	<th class="thcls" align="center"  style="text-align: center;"  colspan="7"  width="2%"  style="color: rgba(53, 54, 58, 0);background: rgba(33, 32, 99, 0.75); " ><b>Speciality Wise Counter</b></th>
		     
		    </tr>
		    <tr>
		      
		     	<th class="thcls" align="center"  style="text-align: center;color: blue"  title="Department"><b>Speciality</b></th>
				<th class="thcls" align="center" style="text-align: center;color: blue"   title="Appointment"><b> Appointment</b></th>
		     	<th class="thcls" align="center" style="text-align: center;color: blue"   title="Scheduled"><b> Scheduled</b></th>
		     	<th class="thcls" align="center" style="text-align: center;color: blue"   title="Service In"><b> Service In</b></th>
		     	<th class="thcls" align="center" style="text-align: center;color: blue"   title="Service Out"><b> Service Out</b></th>
		     	<th class="thcls" align="center" style="text-align: center;color: blue"   title="Cancel"><b> Cancel</b></th>
				<th class="thcls" align="center" style="text-align: center;color: blue"   title="Total"><b>Total</b></th>
			  
			 	</tr>
		
		
		<s:iterator id="rsCompl"  status="status" value="%{dashObj.dashList}" >
			
			 	<tr>
			 	   		<th class="thcls" align="center"  style="text-align: center;"><b><s:property value="#rsCompl.name"/></b></th>
			 	    	<td align="center"   class="titleData" onclick="getData('<s:property value="#rsCompl.id"/>','Appointment','speciality','','');" title="Get Appointment Data"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.appointment"/></b></a></td>
	 					<td align="center"   class="titleData" onclick="getData('<s:property value="#rsCompl.id"/>','Scheduled','speciality','','');" title="Get Scheduled Data"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.scheduled"/></b></a></td>
		 				<td align="center"   class="titleData" onclick="getData('<s:property value="#rsCompl.id"/>','Service In','speciality','','');" title="Get Service In Data"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.serviceIn"/></b></a></td>
		 				<td align="center"   class="titleData" onclick="getData('<s:property value="#rsCompl.id"/>','Service Out','speciality','','');" title="Get Service Out Data"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.serviceOut"/></b></a></td>
						<td align="center"   class="titleData" onclick="getData('<s:property value="#rsCompl.id"/>','Cancel','speciality','','');" title="Get Cancel Data"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.cancel"/></b></a></td>
						<td align="center" style="color:#004276;background:#C2C2CC "   class="titleData"  onclick="getData('<s:property value="#rsCompl.id"/>','All','speciality','','');" title="Get Total Data"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.counter"/></b></a></td>
		 
			 	
				</tr>
		 	
		</s:iterator>
</s:elseif>

<s:elseif test="tableFor=='pecialitysToDoc' || tableFor=='corporateServiceDoctor'">
     <tr>
		    	<th class="thcls" align="center"  style="text-align: center;"  colspan="7"  width="2%"  style="color: rgba(53, 54, 58, 0);background: rgba(33, 32, 99, 0.75); " ><b>Doctor Wise Counter</b></th>
		     
		    </tr>
		    <tr>
		      
		     	<th class="thcls" align="center"  style="text-align: center;color: blue"  title="Department"><b>Doctor Name</b></th>
				<th class="thcls" align="center" style="text-align: center;color: blue"   title="Appointment"><b> Appointment</b></th>
		     	<th class="thcls" align="center" style="text-align: center;color: blue"   title="Scheduled"><b> Scheduled</b></th>
		     	<th class="thcls" align="center" style="text-align: center;color: blue"   title="Service In"><b> Service In</b></th>
		     	<th class="thcls" align="center" style="text-align: center;color: blue"   title="Service Out"><b> Service Out</b></th>
		     	<th class="thcls" align="center" style="text-align: center;color: blue"   title="Cancel"><b> Cancel</b></th>
				<th class="thcls" align="center" style="text-align: center;color: blue"   title="Total"><b>Total</b></th>
			  
			 	</tr>
		
		
		<s:iterator id="rsCompl"  status="status" value="%{dashObj.dashList}" >
			
			 	<tr>
			 	   		<th class="thcls" align="center"  style="text-align: center;"><b><s:property value="#rsCompl.name"/></b></th>
			 	    	<td align="center"   class="titleData" onclick="getData('<s:property value="#rsCompl.id"/>','Appointment','doctor','','');" title="Get Appointment Data"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.appointment"/></b></a></td>
	 					<td align="center"   class="titleData" onclick="getData('<s:property value="#rsCompl.id"/>','Scheduled','doctor','','');" title="Get Scheduled Data"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.scheduled"/></b></a></td>
		 				<td align="center"   class="titleData" onclick="getData('<s:property value="#rsCompl.id"/>','Service In','doctor','','');" title="Get Service In Data"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.serviceIn"/></b></a></td>
		 				<td align="center"   class="titleData" onclick="getData('<s:property value="#rsCompl.id"/>','Service Out','doctor','','');" title="Get Service Out Data"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.serviceOut"/></b></a></td>
						<td align="center"   class="titleData" onclick="getData('<s:property value="#rsCompl.id"/>','Cancel','doctor','','');" title="Get Cancel Data"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.cancel"/></b></a></td>
						<td align="center" style="color:#004276;background:#C2C2CC "   class="titleData"  onclick="getData('<s:property value="#rsCompl.id"/>','All','doctor','','');" title="Get Total Data"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.counter"/></b></a></td>
		 
			 	
				</tr>
		 	
		</s:iterator>
</s:elseif> 
<s:if test="tableFor=='accountManagerProductivity'">
		    <tr>
		    	<th class="thcls" align="center"  style="text-align: center;"  colspan="7"  width="2%"  style="color: rgba(53, 54, 58, 0);background: rgba(33, 32, 99, 0.75); " ><b>Account Manager Wise Productivity Counter</b></th>
		     
		    </tr>
		  	  	<tr>
		      
		     	<th class="thcls" align="center"  style="text-align: center;color: blue"  title="Account Manager"><b>Account Manager</b></th>
		     	<th class="thcls" align="center" style="text-align: center;color: blue"   title="On Time Counter"><b> On Time</b></th>
		     	<th class="thcls" align="center" style="text-align: center;color: blue"   title="Off Time Counter"><b> Off Time</b></th>
		     	<th class="thcls" align="center" style="text-align: center;color: blue"   title="Total Counter"><b>Total</b></th>
			  
			 	</tr>
		
		
		<s:iterator id="rsCompl"  status="status" value="%{dashObj.dashList}" >
			
			 	<tr>
			 	   		<th class="thcls" align="center"  style="text-align: center;"><b><s:property value="#rsCompl.name"/></b></th>
			 	    	<td align="center"   class="titleData" onclick="getData('<s:property value="#rsCompl.id"/>','On Time','ac_manager','','');" title="Get On Time Data"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.onTime"/></b></a></td>
	 					<td align="center"   class="titleData" onclick="getData('<s:property value="#rsCompl.id"/>','Off Time','ac_manager','','');" title="Get Off Time Data"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.offTime"/></b></a></td>
		 			 	<td align="center" style="color:#004276;background:#C2C2CC "   class="titleData"  onclick="getData('<s:property value="#rsCompl.id"/>','All','ac_manager','','');" title="Get Total Data"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.counter"/></b></a></td>
		 		  	
				</tr>
		 	
		</s:iterator>
</s:if>

 					

  <s:elseif test="tableFor=='corporateForAcntMngProductivity' || tableFor=='corporateProductivity'">
    		 <tr>
		    	<th class="thcls" align="center"  style="text-align: center;"  colspan="7"  width="2%"  style="color: rgba(53, 54, 58, 0);background: rgba(33, 32, 99, 0.75); " ><b>Corporate Wise Productivity Counter</b></th>
		     
		    </tr>
		   		 <tr>
		      
		     	<th class="thcls" align="center"  style="text-align: center;color: blue"  title="Department"><b>Corporate</b></th>
				<th class="thcls" align="center" style="text-align: center;color: blue"   title="On Time Counter"><b> On Time</b></th>
		     	<th class="thcls" align="center" style="text-align: center;color: blue"   title="Off Time Counter"><b> Off Time</b></th>
		     	<th class="thcls" align="center" style="text-align: center;color: blue"   title="Total Counter"><b>Total</b></th>
			 
			 	</tr>
		
		
		<s:iterator id="rsCompl"  status="status" value="%{dashObj.dashList}" >
			
			 	<tr>
			 			<th class="thcls" align="center"  style="text-align: center;"><b><s:property value="#rsCompl.name"/></b></th>
			 	     	<td align="center"   class="titleData" onclick="getData('<s:property value="#rsCompl.id"/>','On Time','corporate','','');" title="Get On Time Data"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.onTime"/></b></a></td>
	 					<td align="center"   class="titleData" onclick="getData('<s:property value="#rsCompl.id"/>','Off Time','corporate','','');" title="Get Off Time Data"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.offTime"/></b></a></td>
		 			 	<td align="center" style="color:#004276;background:#C2C2CC "   class="titleData"  onclick="getData('<s:property value="#rsCompl.id"/>','All','corporate','','');" title="Get Total Data"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.counter"/></b></a></td>
		 		
				</tr>
		 	
		</s:iterator>
</s:elseif>
  
 <s:elseif test="tableFor=='serviceForAcntMngProductivity' || tableFor=='serviceForLocationProductivity' || tableFor=='serviceProductivity' || tableFor=='serviceDOCProductivity' || tableFor=='corporateServiceProductivity'">
    		 <tr>
		    	<th class="thcls" align="center"  style="text-align: center;"  colspan="7"  width="2%"  style="color: rgba(53, 54, 58, 0);background: rgba(33, 32, 99, 0.75); " ><b>Service Wise Productivity Counter</b></th>
		     
		    </tr>
		  	 	 <tr>
		      
		     	<th class="thcls" align="center"  style="text-align: center;color: blue"  title="Department"><b>Services</b></th>
				<th class="thcls" align="center" style="text-align: center;color: blue"   title="On Time Counter"><b> On Time</b></th>
		     	<th class="thcls" align="center" style="text-align: center;color: blue"   title="Off Time Counter"><b> Off Time</b></th>
		     	<th class="thcls" align="center" style="text-align: center;color: blue"   title="Total Counter"><b>Total</b></th>
			  
			 	</tr>
		
		
		<s:iterator id="rsCompl"  status="status" value="%{dashObj.dashList}" >
			
			 	<tr>
			 	 		<th class="thcls" align="center"  style="text-align: center;"><b><s:property value="#rsCompl.name"/></b></th>
			 	     	<td align="center"   class="titleData" onclick="getData('<s:property value="#rsCompl.id"/>','On Time','service','','');" title="Get On Time Data"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.onTime"/></b></a></td>
	 					<td align="center"   class="titleData" onclick="getData('<s:property value="#rsCompl.id"/>','Off Time','service','','');" title="Get Off Time Data"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.offTime"/></b></a></td>
		 			 	<td align="center" style="color:#004276;background:#C2C2CC "   class="titleData"  onclick="getData('<s:property value="#rsCompl.id"/>','All','service','','');" title="Get Total Data"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.counter"/></b></a></td>
		 		
			 	
				</tr>
		 	
		</s:iterator>
</s:elseif>

<s:elseif test="tableFor=='locationForAcntMngProductivity' || tableFor=='locationProductivity' || tableFor=='locationForServiceProductivity' || tableFor=='locationForSpecialityProductivity' || tableFor=='corporateLocationProductivity'">
     		<tr>
		    	<th class="thcls" align="center"  style="text-align: center;"  colspan="7"  width="2%"  style="color: rgba(53, 54, 58, 0);background: rgba(33, 32, 99, 0.75); " ><b>Location Wise Productivity Counter</b></th>
		     
		    </tr>
		    	<tr>
		      
		     	<th class="thcls" align="center"  style="text-align: center;color: blue"  title="Department"><b>Location</b></th>
				<th class="thcls" align="center" style="text-align: center;color: blue"   title="On Time Counter"><b> On Time</b></th>
		     	<th class="thcls" align="center" style="text-align: center;color: blue"   title="Off Time Counter"><b> Off Time</b></th>
		     	<th class="thcls" align="center" style="text-align: center;color: blue"   title="Total Counter"><b>Total</b></th>
			 
			 	</tr>
		
		
		<s:iterator id="rsCompl"  status="status" value="%{dashObj.dashList}" >
			
			 	<tr>
			 	 		<th class="thcls" align="center"  style="text-align: center;"><b><s:property value="#rsCompl.name"/></b></th>
			 	    	<td align="center"   class="titleData" onclick="getData('<s:property value="#rsCompl.id"/>','On Time','location','','');" title="Get On Time Data"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.onTime"/></b></a></td>
	 					<td align="center"   class="titleData" onclick="getData('<s:property value="#rsCompl.id"/>','Off Time','location','','');" title="Get Off Time Data"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.offTime"/></b></a></td>
		 			 	<td align="center" style="color:#004276;background:#C2C2CC "   class="titleData"  onclick="getData('<s:property value="#rsCompl.id"/>','All','location','','');" title="Get Total Data"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.counter"/></b></a></td>
		 		
			 	
				</tr>
		 	
		</s:iterator>
</s:elseif>

<s:elseif test="tableFor=='serviceManagerForAcntMngProductivity'  || tableFor=='serviceManagerProductivity'  || tableFor=='serviceManagerForServiceProductivity'">
     		<tr>
		    	<th class="thcls" align="center"  style="text-align: center;"  colspan="7"  width="2%"  style="color: rgba(53, 54, 58, 0);background: rgba(33, 32, 99, 0.75); " ><b>Service Manager Wise Productivity Counter</b></th>
		     
		    </tr>
		    <tr>
		      
		     	<th class="thcls" align="center"  style="text-align: center;color: blue"  title="Department"><b>Service Manager</b></th>
				<th class="thcls" align="center" style="text-align: center;color: blue"   title="On Time Counter"><b> On Time</b></th>
		     	<th class="thcls" align="center" style="text-align: center;color: blue"   title="Off Time Counter"><b> Off Time</b></th>
		     	<th class="thcls" align="center" style="text-align: center;color: blue"   title="Total Counter"><b>Total</b></th>
			  
			</tr>
		
		
		<s:iterator id="rsCompl"  status="status" value="%{dashObj.dashList}" >
			
			 	<tr>
			 	   		<th class="thcls" align="center"  style="text-align: center;"><b><s:property value="#rsCompl.name"/></b></th>
			 	   		<td align="center"   class="titleData" onclick="getData('<s:property value="#rsCompl.id"/>','On Time','ser_manager','','');" title="Get On Time Data"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.onTime"/></b></a></td>
	 					<td align="center"   class="titleData" onclick="getData('<s:property value="#rsCompl.id"/>','Off Time','ser_manager','','');" title="Get Off Time Data"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.offTime"/></b></a></td>
		 			 	<td align="center" style="color:#004276;background:#C2C2CC "   class="titleData"  onclick="getData('<s:property value="#rsCompl.id"/>','All','ser_manager','','');" title="Get Total Data"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.counter"/></b></a></td>
		 		
			 	
				</tr>
		 	
		</s:iterator>
</s:elseif>
<!-- Speciality -->
<s:elseif test="tableFor=='specialitysProductivity' || tableFor=='corporateServiceSpecialityProductivity'">
     <tr>
		    	<th class="thcls" align="center"  style="text-align: center;"  colspan="7"  width="2%"  style="color: rgba(53, 54, 58, 0);background: rgba(33, 32, 99, 0.75); " ><b>Speciality Wise Counter</b></th>
		     
		    </tr>
		    <tr>
		      
		     	<th class="thcls" align="center"  style="text-align: center;color: blue"  title="Department"><b>Speciality</b></th>
				<th class="thcls" align="center" style="text-align: center;color: blue"   title="On Time Counter"><b> On Time</b></th>
		     	<th class="thcls" align="center" style="text-align: center;color: blue"   title="Off Time Counter"><b> Off Time</b></th>
		     	<th class="thcls" align="center" style="text-align: center;color: blue"   title="Total Counter"><b>Total</b></th>
			  
			 	</tr>
		
		
		<s:iterator id="rsCompl"  status="status" value="%{dashObj.dashList}" >
			
			 	<tr>
			 	   		<th class="thcls" align="center"  style="text-align: center;"><b><s:property value="#rsCompl.name"/></b></th>
			 	   		<td align="center"   class="titleData" onclick="getData('<s:property value="#rsCompl.id"/>','On Time','speciality','','');" title="Get On Time Data"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.onTime"/></b></a></td>
	 					<td align="center"   class="titleData" onclick="getData('<s:property value="#rsCompl.id"/>','Off Time','speciality','','');" title="Get Off Time Data"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.offTime"/></b></a></td>
		 			 	<td align="center" style="color:#004276;background:#C2C2CC "   class="titleData"  onclick="getData('<s:property value="#rsCompl.id"/>','All','speciality','','');" title="Get Total Data"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.counter"/></b></a></td>
		 
			 	
				</tr>
		 	
		</s:iterator>
</s:elseif>

<s:elseif test="tableFor=='specialitysToDocProductivity' || tableFor=='corporateSpecialityDoctorProductivity'">
     <tr>
		    	<th class="thcls" align="center"  style="text-align: center;"  colspan="7"  width="2%"  style="color: rgba(53, 54, 58, 0);background: rgba(33, 32, 99, 0.75); " ><b>Speciality Wise Counter</b></th>
		     
		    </tr>
		    <tr>
		      
		     	<th class="thcls" align="center"  style="text-align: center;color: blue"  title="Department"><b>Doctor</b></th>
				<th class="thcls" align="center" style="text-align: center;color: blue"   title="On Time Counter"><b> On Time</b></th>
		     	<th class="thcls" align="center" style="text-align: center;color: blue"   title="Off Time Counter"><b> Off Time</b></th>
		     	<th class="thcls" align="center" style="text-align: center;color: blue"   title="Total Counter"><b>Total</b></th>
			  
			 	</tr>
		
		
		<s:iterator id="rsCompl"  status="status" value="%{dashObj.dashList}" >
			
			 	<tr>
			 	   		<th class="thcls" align="center"  style="text-align: center;"><b><s:property value="#rsCompl.name"/></b></th>
			 	   		<td align="center"   class="titleData" onclick="getData('<s:property value="#rsCompl.id"/>','On Time','doctor','','');" title="Get On Time Data"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.onTime"/></b></a></td>
	 					<td align="center"   class="titleData" onclick="getData('<s:property value="#rsCompl.id"/>','Off Time','doctor','','');" title="Get Off Time Data"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.offTime"/></b></a></td>
		 			 	<td align="center" style="color:#004276;background:#C2C2CC "   class="titleData"  onclick="getData('<s:property value="#rsCompl.id"/>','All','doctor','','');" title="Get Total Data"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.counter"/></b></a></td>
		 
			 	
				</tr>
		 	
		</s:iterator>
</s:elseif> 

</table>
</div>
</body>
</html>