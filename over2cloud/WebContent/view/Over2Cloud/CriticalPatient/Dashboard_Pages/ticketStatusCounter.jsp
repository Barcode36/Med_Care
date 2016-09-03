
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
<div class="clear"></div>
<div class="clear"></div>
<div id="critical" style="width: 99%;overflow: auto;height: 190px; " >
<table border="1" width="100%"   align="center" bordercolor="#0C0C0C" >
    <s:if test="tableFor=='specialty'">
		    <tr>
		    	<th class="thcls" align="center"  style="text-align: center;"  colspan="4"  width="2%"  style="color: rgba(53, 54, 58, 0);background: rgba(33, 32, 99, 0.75); " ><b>Ticket Counter Status Specialty Wise</b></th>
		     
		    </tr>
		    <tr>
		      
		     	<th class="thcls" align="center"  style="text-align: center;color: blue"  title="Account Manager"><b>Specialty</b></th>
		     	<th class="thcls" align="center" style="text-align: center;color: blue"   title="Open"><b> Open</b></th>
		     	<th class="thcls" align="center" style="text-align: center;color: blue"   title="Close"><b> Close</b></th>
  				<th class="thcls" align="center" style="text-align: center;color: blue"   title="Total"><b>Total</b></th>
			  
			 	</tr>
		
		
		<s:iterator id="rsCompl"  status="status" value="%{dashObj.dashList}" >
			
			 	<tr>
			   	   		<th class="thcls" align="center"  style="text-align: center;"><b><s:property value="#rsCompl.name"/></b></th>
			 	    	<td align="center"   class="titleData" onclick="getData('<s:property value="#rsCompl.id"/>','Open','specialty','','');" title="Get Open Tickets"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.open"/></b></a></td>
	 					<td align="center"   class="titleData" onclick="getData('<s:property value="#rsCompl.id"/>','Close','specialty','','');" title="Get Close Tickets"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.close"/></b></a></td>
  					<td align="center" style="color:#004276;background:#C2C2CC "   class="titleData"  onclick="getData('<s:property value="#rsCompl.id"/>','All','specialty','','');" title="Get All Tickets"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.counter"/></b></a></td>
		 		  	
				</tr>
		 	
		</s:iterator>
</s:if>
 <s:elseif test="tableFor=='doctor'">
     <tr>
		    	<th class="thcls" align="center"  style="text-align: center;"  colspan="4"  width="2%"  style="color: rgba(53, 54, 58, 0);background: rgba(33, 32, 99, 0.75); " ><b>Ticket Counter Status Doctor Wise</b></th>
		     
		    </tr>
		    <tr>
		      
		     	<th class="thcls" align="center"  style="text-align: center;color: blue"  title="Account Manager"><b>Doctor</b></th>
		     	<th class="thcls" align="center" style="text-align: center;color: blue"   title="Open"><b> Open</b></th>
		     	<th class="thcls" align="center" style="text-align: center;color: blue"   title="Close"><b> Close</b></th>
  				<th class="thcls" align="center" style="text-align: center;color: blue"   title="Total"><b>Total</b></th>
			  
			 	</tr>
		
		
		<s:iterator id="rsCompl"  status="status" value="%{dashObj.dashList}" >
			
			 	<tr>
			 	   		<th class="thcls" align="center"  style="text-align: center;"><b><s:property value="#rsCompl.name"/></b></th>
			 	    	<td align="center"   class="titleData" onclick="getData('<s:property value="#rsCompl.id"/>','Open','doctor','','');" title="Get Open Tickets"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.open"/></b></a></td>
	 					<td align="center"   class="titleData" onclick="getData('<s:property value="#rsCompl.id"/>','Close','doctor','','');" title="Get Close Tickets"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.close"/></b></a></td>
  					<td align="center" style="color:#004276;background:#C2C2CC "   class="titleData"  onclick="getData('<s:property value="#rsCompl.id"/>','All','doctor','','');" title="Get All Tickets"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.counter"/></b></a></td>
		 		  	
				</tr>
		 	
		</s:iterator>
</s:elseif>
 					

  <s:elseif test="tableFor=='testType'">
     <tr>
		    	<th class="thcls" align="center"  style="text-align: center;"  colspan="4"  width="2%"  style="color: rgba(53, 54, 58, 0);background: rgba(33, 32, 99, 0.75); " ><b>Ticket Counter Status Test Type Wise</b></th>
		     
		    </tr>
		    <tr>
		      
		     	<th class="thcls" align="center"  style="text-align: center;color: blue"  title="Account Manager"><b>Test Type</b></th>
		     	<th class="thcls" align="center" style="text-align: center;color: blue"   title="Open"><b> Open</b></th>
		     	<th class="thcls" align="center" style="text-align: center;color: blue"   title="Close"><b> Close</b></th>
  				<th class="thcls" align="center" style="text-align: center;color: blue"   title="Total"><b>Total</b></th>
			  
			 	</tr>
		
		
		<s:iterator id="rsCompl"  status="status" value="%{dashObj.dashList}" >
			
			 	<tr>
			 	   		<th class="thcls" align="center"  style="text-align: center;"><b><s:property value="#rsCompl.name"/></b></th>
			 	    	<td align="center"   class="titleData" onclick="getData('<s:property value="#rsCompl.id"/>','Open','testType','','');" title="Get Open Tickets"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.open"/></b></a></td>
	 					<td align="center"   class="titleData" onclick="getData('<s:property value="#rsCompl.id"/>','Close','testType','','');" title="Get Close Tickets"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.close"/></b></a></td>
  					<td align="center" style="color:#004276;background:#C2C2CC "   class="titleData"  onclick="getData('<s:property value="#rsCompl.id"/>','All','testType','','');" title="Get All Tickets"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.counter"/></b></a></td>
		 		  	
				</tr>
		 	
		</s:iterator>
</s:elseif>

<s:elseif test="tableFor=='testName'">
     <tr>
		    	<th class="thcls" align="center"  style="text-align: center;"  colspan="4"  width="2%"  style="color: rgba(53, 54, 58, 0);background: rgba(33, 32, 99, 0.75); " ><b>Ticket Counter Status Test Name Wise</b></th>
		     
		    </tr>
		    <tr>
		      
		     	<th class="thcls" align="center"  style="text-align: center;color: blue"  title="Account Manager"><b>Test Name</b></th>
		     	<th class="thcls" align="center" style="text-align: center;color: blue"   title="Open"><b> Open</b></th>
		     	<th class="thcls" align="center" style="text-align: center;color: blue"   title="Close"><b> Close</b></th>
  				<th class="thcls" align="center" style="text-align: center;color: blue"   title="Total"><b>Total</b></th>
			  
			 	</tr>
		
		
		<s:iterator id="rsCompl"  status="status" value="%{dashObj.dashList}" >
			
			 	<tr>
			 	   		<th class="thcls" align="center"  style="text-align: center;"><b><s:property value="#rsCompl.name"/></b></th>
			 	    	<td align="center"   class="titleData" onclick="getData('<s:property value="#rsCompl.id"/>','Open','testName','','');" title="Get Open Tickets"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.open"/></b></a></td>
	 					<td align="center"   class="titleData" onclick="getData('<s:property value="#rsCompl.id"/>','Close','testName','','');" title="Get Close Tickets"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.close"/></b></a></td>
  					<td align="center" style="color:#004276;background:#C2C2CC "   class="titleData"  onclick="getData('<s:property value="#rsCompl.id"/>','All','status','','');" title="Get All Tickets"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.counter"/></b></a></td>
		 		  	
				</tr>
		 	
		</s:iterator>
</s:elseif>


<s:elseif test="tableFor=='location'">
     <tr>
		    	<th class="thcls" align="center"  style="text-align: center;"  colspan="4"  width="2%"  style="color: rgba(53, 54, 58, 0);background: rgba(33, 32, 99, 0.75); " ><b>Ticket Counter Status Location Wise</b></th>
		     
		    </tr>
		    <tr>
		      
		     	<th class="thcls" align="center"  style="text-align: center;color: blue"  title="Account Manager"><b>Location</b></th>
		     	<th class="thcls" align="center" style="text-align: center;color: blue"   title="Open"><b> Open</b></th>
		     	<th class="thcls" align="center" style="text-align: center;color: blue"   title="Close"><b> Close</b></th>
  				<th class="thcls" align="center" style="text-align: center;color: blue"   title="Total"><b>Total</b></th>
			  
			 	</tr>
		
		
		<s:iterator id="rsCompl"  status="status" value="%{dashObj.dashList}" >
			
			 	<tr>
			 	   		<th class="thcls" align="center"  style="text-align: center;"><b><s:property value="#rsCompl.name"/></b></th>
			 	    	<td align="center"   class="titleData" onclick="getData('<s:property value="#rsCompl.id"/>','Open','location','','');" title="Get Open Tickets"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.open"/></b></a></td>
	 					<td align="center"   class="titleData" onclick="getData('<s:property value="#rsCompl.id"/>','Close','location','','');" title="Get Close Tickets"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.close"/></b></a></td>
  					<td align="center" style="color:#004276;background:#C2C2CC "   class="titleData"  onclick="getData('<s:property value="#rsCompl.id"/>','All','location','','');" title="Get All Tickets"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.counter"/></b></a></td>
		 		  	
				</tr>
		 	
		</s:iterator>
</s:elseif>

<s:elseif test="tableFor=='nursingUnit'">
     <tr>
		    	<th class="thcls" align="center"  style="text-align: center;"  colspan="4"  width="2%"  style="color: rgba(53, 54, 58, 0);background: rgba(33, 32, 99, 0.75); " ><b>Ticket Counter Status Nursing Unit Wise</b></th>
		     
		    </tr>
		    <tr>
		      
		     	<th class="thcls" align="center"  style="text-align: center;color: blue"  title="Account Manager"><b>Test Name</b></th>
		     	<th class="thcls" align="center" style="text-align: center;color: blue"   title="Open"><b> Open</b></th>
		     	<th class="thcls" align="center" style="text-align: center;color: blue"   title="Close"><b> Close</b></th>
  				<th class="thcls" align="center" style="text-align: center;color: blue"   title="Total"><b>Total</b></th>
			  
			 	</tr>
		
		
		<s:iterator id="rsCompl"  status="status" value="%{dashObj.dashList}" >
			
			 	<tr>
			 	   		<th class="thcls" align="center"  style="text-align: center;"><b><s:property value="#rsCompl.name"/></b></th>
			 	    	<td align="center"   class="titleData" onclick="getData('<s:property value="#rsCompl.id"/>','Open','nursingUnit','','');" title="Get Open Tickets"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.open"/></b></a></td>
	 					<td align="center"   class="titleData" onclick="getData('<s:property value="#rsCompl.id"/>','Close','nursingUnit','','');" title="Get Close Tickets"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.close"/></b></a></td>
  					<td align="center" style="color:#004276;background:#C2C2CC "   class="titleData"  onclick="getData('<s:property value="#rsCompl.id"/>','All','nursingUnit','','');" title="Get All Tickets"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.counter"/></b></a></td>
		 		  	
				</tr>
		 	
		</s:iterator>
</s:elseif>

  
 <s:elseif test="tableFor=='specialtyProductivity'">
     <tr>
		    	<th class="thcls" align="center"  style="text-align: center;"  colspan="4"  width="2%"  style="color: rgba(53, 54, 58, 0);background: rgba(33, 32, 99, 0.75); " ><b>Productivity Counter Specialty Wise</b></th>
		     
		    </tr>
		    <tr>
		      
		     	<th class="thcls" align="center"  style="text-align: center;color: blue"  title="Account Manager"><b>Specialty</b></th>
		     	<th class="thcls" align="center" style="text-align: center;color: blue"   title="Open"><b> On Time</b></th>
		     	<th class="thcls" align="center" style="text-align: center;color: blue"   title="Close"><b> Off Time</b></th>
  				<th class="thcls" align="center" style="text-align: center;color: blue"   title="Total"><b>Total</b></th>
			  
			 	</tr>
		
		
		<s:iterator id="rsCompl"  status="status" value="%{dashObj.dashList}" >
			
			 	<tr>
			 	   		<th class="thcls" align="center"  style="text-align: center;"><b><s:property value="#rsCompl.name"/></b></th>
			 	    	<td align="center"   class="titleData" onclick="getData('<s:property value="#rsCompl.id"/>','On Time','specialtyProductivity','','');" title="Get On Time Tickets"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.onTime"/></b></a></td>
	 					<td align="center"   class="titleData" onclick="getData('<s:property value="#rsCompl.id"/>','Off Time','specialtyProductivity','','');" title="Get Off Time Tickets"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.offTime"/></b></a></td>
  						<td align="center" style="color:#004276;background:#C2C2CC "   class="titleData"  onclick="getData('<s:property value="#rsCompl.id"/>','All','specialtyProductivity','','');" title="Get All Tickets"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.counter"/></b></a></td>
		 		  	
				</tr>
		 	
		</s:iterator>
</s:elseif>  
<s:elseif test="tableFor=='doctorProductivity'">
     <tr>
		    	<th class="thcls" align="center"  style="text-align: center;"  colspan="4"  width="2%"  style="color: rgba(53, 54, 58, 0);background: rgba(33, 32, 99, 0.75); " ><b>Productivity Counter Doctor Wise</b></th>
		     
		    </tr>
		    <tr>
		      
		     	<th class="thcls" align="center"  style="text-align: center;color: blue"  title="Account Manager"><b>Doctor</b></th>
		     	<th class="thcls" align="center" style="text-align: center;color: blue"   title="Open"><b> On Time</b></th>
		     	<th class="thcls" align="center" style="text-align: center;color: blue"   title="Close"><b> Off Time</b></th>
  				<th class="thcls" align="center" style="text-align: center;color: blue"   title="Total"><b>Total</b></th>
			  
			 	</tr>
		
		
		<s:iterator id="rsCompl"  status="status" value="%{dashObj.dashList}" >
			
			 	<tr>
			 	   		<th class="thcls" align="center"  style="text-align: center;"><b><s:property value="#rsCompl.name"/></b></th>
			 	    	<td align="center"   class="titleData" onclick="getData('<s:property value="#rsCompl.id"/>','On Time','doctorProductivity','','');" title="Get On Time Tickets"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.onTime"/></b></a></td>
	 					<td align="center"   class="titleData" onclick="getData('<s:property value="#rsCompl.id"/>','Off Time','doctorProductivity','','');" title="Get Off Time Tickets"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.offTime"/></b></a></td>
  						<td align="center" style="color:#004276;background:#C2C2CC "   class="titleData"  onclick="getData('<s:property value="#rsCompl.id"/>','All','doctorProductivity','','');" title="Get All Tickets"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.counter"/></b></a></td>
		 		  	
				</tr>
		 	
		</s:iterator>
</s:elseif>  
  
 <s:elseif test="tableFor=='testTypeProductivity'">
     <tr>
		    	<th class="thcls" align="center"  style="text-align: center;"  colspan="4"  width="2%"  style="color: rgba(53, 54, 58, 0);background: rgba(33, 32, 99, 0.75); " ><b>Productivity Counter Test Type Wise</b></th>
		     
		    </tr>
		    <tr>
		      
		     	<th class="thcls" align="center"  style="text-align: center;color: blue"  title="Account Manager"><b>Test Type</b></th>
		     	<th class="thcls" align="center" style="text-align: center;color: blue"   title="Open"><b> On Time</b></th>
		     	<th class="thcls" align="center" style="text-align: center;color: blue"   title="Close"><b> Off Time</b></th>
  				<th class="thcls" align="center" style="text-align: center;color: blue"   title="Total"><b>Total</b></th>
			  
			 	</tr>
		
		
		<s:iterator id="rsCompl"  status="status" value="%{dashObj.dashList}" >
			
			 	<tr>
			 	   		<th class="thcls" align="center"  style="text-align: center;"><b><s:property value="#rsCompl.name"/></b></th>
			 	    	<td align="center"   class="titleData" onclick="getData('<s:property value="#rsCompl.id"/>','On Time','testTypeProductivity','','');" title="Get On Time Tickets"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.onTime"/></b></a></td>
	 					<td align="center"   class="titleData" onclick="getData('<s:property value="#rsCompl.id"/>','Off Time','testTypeProductivity','','');" title="Get Off Time Tickets"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.offTime"/></b></a></td>
  						<td align="center" style="color:#004276;background:#C2C2CC "   class="titleData"  onclick="getData('<s:property value="#rsCompl.id"/>','All','testTypeProductivity','','');" title="Get All Tickets"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.counter"/></b></a></td>
		 		  	
				</tr>
		 	
		</s:iterator>
</s:elseif>  
<s:elseif test="tableFor=='testNameProductivity'">
     <tr>
		    	<th class="thcls" align="center"  style="text-align: center;"  colspan="4"  width="2%"  style="color: rgba(53, 54, 58, 0);background: rgba(33, 32, 99, 0.75); " ><b>Productivity Counter Test Name Wise</b></th>
		     
		    </tr>
		    <tr>
		      
		     	<th class="thcls" align="center"  style="text-align: center;color: blue"  title="Account Manager"><b>Test Name</b></th>
		     	<th class="thcls" align="center" style="text-align: center;color: blue"   title="Open"><b> On Time</b></th>
		     	<th class="thcls" align="center" style="text-align: center;color: blue"   title="Close"><b> Off Time</b></th>
  				<th class="thcls" align="center" style="text-align: center;color: blue"   title="Total"><b>Total</b></th>
			  
			 	</tr>
		
		
		<s:iterator id="rsCompl"  status="status" value="%{dashObj.dashList}" >
			
			 	<tr>
			 	   		<th class="thcls" align="center"  style="text-align: center;"><b><s:property value="#rsCompl.name"/></b></th>
			 	    	<td align="center"   class="titleData" onclick="getData('<s:property value="#rsCompl.id"/>','On Time','testNameProductivity','','');" title="Get On Time Tickets"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.onTime"/></b></a></td>
	 					<td align="center"   class="titleData" onclick="getData('<s:property value="#rsCompl.id"/>','Off Time','testNameProductivity','','');" title="Get Off Time Tickets"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.offTime"/></b></a></td>
  						<td align="center" style="color:#004276;background:#C2C2CC "   class="titleData"  onclick="getData('<s:property value="#rsCompl.id"/>','All','testNameProductivity','','');" title="Get All Tickets"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.counter"/></b></a></td>
		 		  	
				</tr>
		 	
		</s:iterator>
</s:elseif>   

<s:elseif test="tableFor=='locationProductivity'">
     <tr>
		    	<th class="thcls" align="center"  style="text-align: center;"  colspan="4"  width="2%"  style="color: rgba(53, 54, 58, 0);background: rgba(33, 32, 99, 0.75); " ><b>Productivity Counter Location Wise</b></th>
		     
		    </tr>
		    <tr>
		      
		     	<th class="thcls" align="center"  style="text-align: center;color: blue"  title="Account Manager"><b>Test Type</b></th>
		     	<th class="thcls" align="center" style="text-align: center;color: blue"   title="Open"><b> On Time</b></th>
		     	<th class="thcls" align="center" style="text-align: center;color: blue"   title="Close"><b> Off Time</b></th>
  				<th class="thcls" align="center" style="text-align: center;color: blue"   title="Total"><b>Total</b></th>
			  
			 	</tr>
		
		
		<s:iterator id="rsCompl"  status="status" value="%{dashObj.dashList}" >
			
			 	<tr>
			 	   		<th class="thcls" align="center"  style="text-align: center;"><b><s:property value="#rsCompl.name"/></b></th>
			 	    	<td align="center"   class="titleData" onclick="getData('<s:property value="#rsCompl.id"/>','On Time','locationProductivity','','');" title="Get On Time Tickets"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.onTime"/></b></a></td>
	 					<td align="center"   class="titleData" onclick="getData('<s:property value="#rsCompl.id"/>','Off Time','locationProductivity','','');" title="Get Off Time Tickets"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.offTime"/></b></a></td>
  						<td align="center" style="color:#004276;background:#C2C2CC "   class="titleData"  onclick="getData('<s:property value="#rsCompl.id"/>','All','locationProductivity','','');" title="Get All Tickets"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.counter"/></b></a></td>
		 		  	
				</tr>
		 	
		</s:iterator>
</s:elseif>  
<s:elseif test="tableFor=='nursingUnitProductivity'">
     <tr>
		    	<th class="thcls" align="center"  style="text-align: center;"  colspan="4"  width="2%"  style="color: rgba(53, 54, 58, 0);background: rgba(33, 32, 99, 0.75); " ><b>Productivity Counter Nursing Unit Wise</b></th>
		     
		    </tr>
		    <tr>
		      
		     	<th class="thcls" align="center"  style="text-align: center;color: blue"  title="Account Manager"><b>Test Name</b></th>
		     	<th class="thcls" align="center" style="text-align: center;color: blue"   title="Open"><b> On Time</b></th>
		     	<th class="thcls" align="center" style="text-align: center;color: blue"   title="Close"><b> Off Time</b></th>
  				<th class="thcls" align="center" style="text-align: center;color: blue"   title="Total"><b>Total</b></th>
			  
			 	</tr>
		
		
		<s:iterator id="rsCompl"  status="status" value="%{dashObj.dashList}" >
			
			 	<tr>
			 	   		<th class="thcls" align="center"  style="text-align: center;"><b><s:property value="#rsCompl.name"/></b></th>
			 	    	<td align="center"   class="titleData" onclick="getData('<s:property value="#rsCompl.id"/>','On Time','nursingUnitProductivity','','');" title="Get On Time Tickets"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.onTime"/></b></a></td>
	 					<td align="center"   class="titleData" onclick="getData('<s:property value="#rsCompl.id"/>','Off Time','nursingUnitProductivity','','');" title="Get Off Time Tickets"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.offTime"/></b></a></td>
  						<td align="center" style="color:#004276;background:#C2C2CC "   class="titleData"  onclick="getData('<s:property value="#rsCompl.id"/>','All','nursingUnitProductivity','','');" title="Get All Tickets"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.counter"/></b></a></td>
		 		  	
				</tr>
		 	
		</s:iterator>
</s:elseif>   
 
 
 <s:elseif test="tableFor=='crcReport'">
     <tr>
		    	<th class="thcls" align="center"  style="text-align: center;"  colspan="6"  width="2%"  style="color: rgba(53, 54, 58, 0);background: rgba(33, 32, 99, 0.75); " ><b>CRC Report </b>
		    	From&nbsp;&nbsp;<s:property value="%{#parameters.fromDate}"/>&nbsp;&nbsp;To&nbsp;&nbsp;<s:property value="%{#parameters.toDate}"/>
		    	</th>
		    </tr>	
		     	
		    <tr>
		     	<th class="thcls" align="center"  style="text-align: center;color: blue"  title="Account Manager"><b>Test Type</b></th>
		     	<th class="thcls" align="center" style="text-align: center;color: blue"   title="Total Critical Result"><b>  Total Critical Result</b></th>
		     	<th class="thcls" align="center" style="text-align: center;color: blue"   title="Informed Critical Result"><b> Informed Critical Result</b></th>
		     	<th class="thcls" align="center" style="text-align: center;color: blue"   title="Close Critical Result"><b> Close Critical Result</b></th>
  				<th class="thcls" align="center" style="text-align: center;color: blue"   title="TAT For OCC"><b> TAT For OCC</b></th>
  				<th class="thcls" align="center" style="text-align: center;color: blue"   title="TAT For User"><b> TAT For User</b></th>
			  
			 	</tr>
		
		<s:iterator id="rsCompl"  status="status" value="%{dashObj.dashList}" >
			
			 	<tr>
			 	   		<th class="thcls"	align="center"  style="text-align: center;"><b><s:property value="#rsCompl.name"/></b></th>
			 	    	<td align="center"	class="titleData" onclick="getData('<s:property value="#rsCompl.id"/>','All','crcReport','','');" title=" Get Total Critical Result"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.open"/></b></a></td>
	 					<td align="center"	class="titleData" onclick="getData('<s:property value="#rsCompl.id"/>','Informed','crcReport','','');" title="Get Informed Critical Result"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.informed"/></b></a></td>
	 					<td align="center"	class="titleData" onclick="getData('<s:property value="#rsCompl.id"/>','Close','crcReport','','');" title="Get Close Critical Result"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.close"/></b></a></td>
  						<td align="center"	class="titleData"    title="TAT For OCC"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.firstTAT"/></b></a></td>
		 		  	<td align="center"	class="titleData"    title="TAT For User"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.secondTAT"/></b></a></td>
				</tr>
		 	
		</s:iterator>
		 
</s:elseif>   
</table>
</div>
</body>
</html>