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
<s:if test="dashFor=='Speciality'">
<div id="ref" style="overflow: auto;width:1185px;"> 
</s:if>
<s:else><div id="ref" style="overflow: auto;"> </s:else>
<table class="bordered" border="2" width="100%" align="center" bordercolor="#0C0C0C" >
 

<s:if test="dashFor=='Order'">
<tr >
	<th class="thcls" colspan="31" align="center" class="title" style="color: rgba(53, 54, 58, 0);background: rgba(33, 32, 99, 0.75); " ><b>Priority Wise</b></th>
	 
</tr>	
 <tr >
	<th class="thcls" align="center"  class="title"   ><b>Priority</b></th>
	<s:iterator id="rsCompl12"  status="status" value="%{dashObj.dashList}" >
		<th colspan="2" class="thcls" align="center"   class="titleData"  ><b><s:property value="#rsCompl12.time"/></b></th>
	</s:iterator>
</tr>	
 <tr>
	<th class="thcls" align="center"  class="title"   ><b> Routine</b></th>
	<s:iterator id="rsCompl12"  status="status" value="%{dashObj.dashList}" >
		<td align="center"   class="titleData" style="color:#004276;" onclick="getData('<s:property value="#rsCompl12.time"/>','Routine','Order');" ><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl12.rc"/></b></a></td>
	 	<td align="center" class="titleData" style="color:#004276;" ><b><s:property value="#rsCompl12.rcPer"/>%</b></td>
 </s:iterator>
</tr>	
<tr>
	<th class="thcls" align="center"   class="title"   ><b>Urgent</b></th>
	<s:iterator id="rsCompl12"  status="status" value="%{dashObj.dashList}" >
		<td align="center"   class="titleData" style="color:#004276;" onclick="getData('<s:property value="#rsCompl12.time"/>','Urgent','Order');" ><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl12.hpc"/></b></a></td>
		<td align="center"  class="titleData" style="color:#004276;" ><b><s:property value="#rsCompl12.hpcPer"/>%</b></td>
	</s:iterator>
</tr>
<tr>
	<th class="thcls" align="center"   class="title"   ><b> Stat</b></th>
	<s:iterator id="rsCompl12"  status="status" value="%{dashObj.dashList}" >
		<td align="center"   class="titleData" style="color:#004276;" onclick="getData('<s:property value="#rsCompl12.time"/>','Stat','Order');"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl12.pc"/></b></a></td>
		<td align="center"   class="titleData" style="color:#004276;"  ><b><s:property value="#rsCompl12.pcPer"/>%</b></td>
	</s:iterator>
</tr>
<tr>
	<th class="thcls" align="center"   class="title"  style="color:#004276;background:#C2C2CC "  ><b>Total</b></th>
	<s:iterator id="rsCompl12"  status="status" value="%{dashObj.dashList}" >
		<td align="center"  class="titleData" style="color:#004276;background:#C2C2CC " onclick="getData('<s:property value="#rsCompl12.time"/>','All','Order');" ><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl12.total"/></b></a></td>
		<td align="center"  class="titleData" style="color:#004276;background:#C2C2CC " ><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl12.totalPer"/>%</b></a></td>
	</s:iterator>
</tr>
		 
</s:if>
 
 
 
<s:if test="dashFor=='Attended'">
<tr >
	<th class="thcls" colspan="31" align="center" width="2%" class="title" style="color: rgba(53, 54, 58, 0);background: rgba(33, 32, 99, 0.75); " ><b>Referral Attended By</b></th>
	 
</tr>	
 <tr >
	<th class="thcls" align="center"   class="title"   ><b>Doctor Visited</b></th>
	<s:iterator id="rsCompl12"  status="status" value="%{dashObj.dashList}" >
		<th colspan="2" class="thcls" align="center"   class="titleData"  ><b><s:property value="#rsCompl12.time"/></b></th>
	</s:iterator>
</tr>	
 <tr>
	<th class="thcls" align="center"   class="title"   ><b> Resident</b></th>
	<s:iterator id="rsCompl12"  status="status" value="%{dashObj.dashList}" >
		<td align="center"    class="titleData" style="color:#004276;" onclick="getData('<s:property value="#rsCompl12.time"/>','Resident','Attended');" ><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl12.rc"/></b></a></td>
	 	<td align="center"   class="titleData" style="color:#004276;" ><b><s:property value="#rsCompl12.rcPer"/>%</b></td>
 </s:iterator>
</tr>	
<tr>
	<th class="thcls" align="center"   class="title"   ><b>&nbsp; Medical Officer</b></th>
	<s:iterator id="rsCompl12"  status="status" value="%{dashObj.dashList}" >
		<td align="center"   class="titleData" style="color:#004276;" onclick="getData('<s:property value="#rsCompl12.time"/>','Medical Officer','Attended');" ><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl12.hpc"/></b></a></td>
		<td align="center"   class="titleData" style="color:#004276;" ><b><s:property value="#rsCompl12.hpcPer"/>%</b></td>
	</s:iterator>
</tr>
<tr>
	<th class="thcls" align="center"   class="title"   ><b> Consultant</b></th>
	<s:iterator id="rsCompl12"  status="status" value="%{dashObj.dashList}" >
		<td align="center"   class="titleData" style="color:#004276;" onclick="getData('<s:property value="#rsCompl12.time"/>','Consultant','Attended');" ><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl12.pc"/></b></a></td>
		<td align="center"   class="titleData" style="color:#004276;" ><b><s:property value="#rsCompl12.pcPer"/>%</b></td>
	</s:iterator>
</tr>
 <tr>
<th class="thcls" align="center"   class="title"  style="color:#004276;background:#C2C2CC "  ><b>Total</b></th>
	<s:iterator id="rsCompl12"  status="status" value="%{dashObj.dashList}" >
		<td align="center"  class="titleData" style="color:#004276;background:#C2C2CC " onclick="getData('<s:property value="#rsCompl12.time"/>','All','Order');" ><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl12.total"/></b></a></td>
		<td align="center"  class="titleData" style="color:#004276;background:#C2C2CC " ><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl12.totalPer"/>%</b></a></td>
	</s:iterator> 
</tr>		 
</s:if>

 	 
<s:if test="dashFor=='Speciality'">
<tr >
<s:if test="attendedBy=='-1'">
	<th class="thcls" colspan="31" align="center"   class="title" style="color: rgba(53, 54, 58, 0);background: rgba(33, 32, 99, 0.75); " ><b>Referral Speciality Wise For All Designation</b></th>
 </s:if>
 <s:elseif test="attendedBy!='-1'">
 	<th class="thcls" colspan="31" align="center"  class="title" style="color: rgba(53, 54, 58, 0);background: rgba(33, 32, 99, 0.75); " ><b>Referral Speciality Wise For <s:property value="%{attendedBy}"/> Designation</b></th>
 
 </s:elseif>
</tr>	
 <tr >
	<th class="thcls" align="center"   class="title"   ><b>&nbsp; Speciality</b></th>
	<s:iterator id="rsCompl12"  status="status" value="%{dashSpecObj.dashList}" >
	<s:if test="attendedBy!='-1'">
		<th  colspan="2"  width="1%" class="thcls" align="center"  class="titleData"  ><b><s:property value="#rsCompl12.time"/></b></th>
	</s:if>
	<s:elseif test="attendedBy=='-1'">
		<th  colspan="1" class="thcls"  width="1%" align="center" class="titleData"  ><b><s:property value="#rsCompl12.time"/></b></th>
 	</s:elseif>
	</s:iterator>
</tr>	
 <tr>
	<th class="thcls" align="center"   class="title"   ><b>&nbsp; Phlebotomy</b></th>
	<s:iterator id="rsCompl12"  status="status" value="%{dashSpecObj.dashList}" >
		<td align="center"   width="1%"  class="titleData" style="color:#004276;" onclick="getData('<s:property value="#rsCompl12.time"/>','Phlebotomy','Speciality');" ><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl12.phlebotomy"/></b></a></td>
	 	<s:if test="attendedBy!='-1'">
 		<td align="center"  width="1%" class="titleData" style="color:#004276;" ><b><s:property value="#rsCompl12.phlebotomy_Per"/>%</b></td>
	 	  </s:if>
  </s:iterator>
  
 
</tr>	
<tr>
	<th class="thcls" align="center" width="2%" class="title"   ><b>&nbsp;Radiation Oncology</b></th>
	<s:iterator id="rsCompl12"  status="status" value="%{dashSpecObj.dashList}" >
		<td align="center"  width="1%" class="titleData" style="color:#004276;" onclick="getData('<s:property value="#rsCompl12.time"/>','Radiation Oncology','Speciality');" ><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl12.radiation_Oncology"/></b></a></td>
		<s:if test="attendedBy!='-1'">
 		<td align="center"  width="1%" class="titleData" style="color:#004276;" ><b><s:property value="#rsCompl12.radiation_Oncology_Per"/>%</b></td>
		 
 	</s:if> 
	</s:iterator>
	 
</tr>
<tr>
	<th class="thcls" align="center" width="2%" class="title"   ><b>&nbsp; Critical Care</b></th>
	<s:iterator id="rsCompl12"  status="status" value="%{dashSpecObj.dashList}" >
		<td align="center"  width="1%" class="titleData" style="color:#004276;" onclick="getData('<s:property value="#rsCompl12.time"/>','Critical Care','Speciality');" ><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl12.critical_Care"/></b></a></td>
	<s:if test="attendedBy!='-1'">
	 	<td align="center"  width="1%" class="titleData" style="color:#004276;" ><b><s:property value="#rsCompl12.critical_Care_Per"/>%</b></td>
		 
	 	</s:if>	 
	</s:iterator>
	 
</tr>
 <tr>
	<th class="thcls" align="center" width="2%" class="title"   ><b>&nbsp; Cardiology</b></th>
	<s:iterator id="rsCompl12"  status="status" value="%{dashSpecObj.dashList}" >
		<td align="center"  width="1%" class="titleData" style="color:#004276;" onclick="getData('<s:property value="#rsCompl12.time"/>','Cardiology','Speciality');" ><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl12.cardiology"/></b></a></td>
		<s:if test="attendedBy!='-1'">
	 	<td align="center" width="1%" class="titleData" style="color:#004276;" ><b><s:property value="#rsCompl12.cardiology_Per"/>%</b></td>
 	</s:if> 
	</s:iterator>
	 
</tr>
<tr>
	<th class="thcls" align="center" width="2%" class="title"   ><b>&nbsp; Orthopaedics</b></th>
	<s:iterator id="rsCompl12"  status="status" value="%{dashSpecObj.dashList}" >
		<td align="center" width="1%" class="titleData" style="color:#004276;" onclick="getData('<s:property value="#rsCompl12.time"/>','Orthopaedics','Speciality');" ><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl12.orthopaedics"/></b></a></td>
		 <s:if test="attendedBy!='-1'">
 		<td align="center" width="1%" class="titleData" style="color:#004276;" ><b><s:property value="#rsCompl12.orthopaedics_Per"/>%</b></td>
		 
 	</s:if>
	</s:iterator>
	 
</tr>
<tr>
	<th class="thcls" align="center" width="2%" class="title"   ><b>&nbsp; Liver Transplant</b></th>
	<s:iterator id="rsCompl12"  status="status" value="%{dashSpecObj.dashList}" >
		<td align="center" width="1%" class="titleData" style="color:#004276;" onclick="getData('<s:property value="#rsCompl12.time"/>','Liver Transplant','Speciality');" ><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl12.liver_Transplant"/></b></a></td>
		<s:if test="attendedBy!='-1'">
 		<td align="center" width="1%" class="titleData" style="color:#004276;" ><b><s:property value="#rsCompl12.liver_Transplant_Per"/>%</b></td>
 	</s:if> 
	</s:iterator>
	 
</tr>
	
	<tr>
	<th class="thcls" align="center" width="2%" class="title"   ><b>&nbsp; Pediatric Surgery</b></th>
	<s:iterator id="rsCompl12"  status="status" value="%{dashSpecObj.dashList}" >
		<td align="center" width="1%" class="titleData" style="color:#004276;" onclick="getData('<s:property value="#rsCompl12.time"/>','Pediatric Surgery','Speciality');" ><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl12.pediatric_Surgery"/></b></a></td>
		<s:if test="attendedBy!='-1'">
 		<td align="center" width="1%" class="titleData" style="color:#004276;" ><b><s:property value="#rsCompl12.pediatric_Surgery_Per"/>%</b></td>
 </s:if> 
	</s:iterator>
	 
</tr>
<tr>
	<th class="thcls" align="center" width="2%" class="title"   ><b>&nbsp;Cardio-Thoracic Vascular Surgery</b></th>
	<s:iterator id="rsCompl12"  status="status" value="%{dashSpecObj.dashList}" >
		<td align="center" width="1%" class="titleData" style="color:#004276;" onclick="getData('<s:property value="#rsCompl12.time"/>','Cardio-Thoracic Vascular Surg.','Speciality');" ><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl12.cardio_Surg"/></b></a></td>
	<s:if test="attendedBy!='-1'">
 		<td align="center" width="1%" class="titleData" style="color:#004276;" ><b><s:property value="#rsCompl12.cardio_Surg_Per"/>%</b></td>
 	</s:if>	 
	</s:iterator>
	 
</tr>
<tr>
	<th class="thcls" align="center" width="2%" class="title"   ><b>&nbsp; Neurology</b></th>
	<s:iterator id="rsCompl12"  status="status" value="%{dashSpecObj.dashList}" >
		<td align="center" width="1%" class="titleData" style="color:#004276;" onclick="getData('<s:property value="#rsCompl12.time"/>','Neurology','Speciality');" ><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl12.neurology"/></b></a></td>
		 	<s:if test="attendedBy!='-1'">
 		<td align="center" width="1%" class="titleData" style="color:#004276;" ><b><s:property value="#rsCompl12.neurology_Per"/>%</b></td>
 	</s:if>
	</s:iterator>
 
</tr>
<tr>
	<th class="thcls" align="center" width="2%" class="title"   ><b>&nbsp; Endocrinology</b></th>
	<s:iterator id="rsCompl12"  status="status" value="%{dashSpecObj.dashList}" >
		<td align="center" width="1%" class="titleData" style="color:#004276;" onclick="getData('<s:property value="#rsCompl12.time"/>','Endocrinology','Speciality');" ><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl12.endocrinology"/></b></a></td>
		 <s:if test="attendedBy!='-1'">
 		<td align="center" width="1%" class="titleData" style="color:#004276;" ><b><s:property value="#rsCompl12.endocrinology_Per"/>%</b></td>
	 	</s:if>
	</s:iterator>
	 
</tr>
<tr>
	<th class="thcls" align="center" width="2%" class="title"   ><b>&nbsp; Laboratory Services</b></th>
	<s:iterator id="rsCompl12"  status="status" value="%{dashSpecObj.dashList}" >
		<td align="center" width="1%" class="titleData" style="color:#004276;" onclick="getData('<s:property value="#rsCompl12.time"/>','Laboratory Services','Speciality');" ><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl12.laboratory_Services"/></b></a></td>
	<s:if test="attendedBy!='-1'">
 		<td align="center" width="1%" class="titleData" style="color:#004276;" ><b><s:property value="#rsCompl12.laboratory_Services_Per"/>%</b></td>
 	</s:if>	 
	</s:iterator>
	 
</tr>

<tr>
	<th class="thcls" align="center" width="2%" class="title"   ><b>&nbsp; Anaesthesia</b></th>
	<s:iterator id="rsCompl12"  status="status" value="%{dashSpecObj.dashList}" >
		<td align="center" width="1%" class="titleData" style="color:#004276;" onclick="getData('<s:property value="#rsCompl12.time"/>','Anaesthesia','Speciality');" ><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl12.anaesthesia"/></b></a></td>
	<s:if test="attendedBy!='-1'">
 		<td align="center" width="1%" class="titleData" style="color:#004276;" ><b><s:property value="#rsCompl12.anaesthesia_Per"/>%</b></td>
	 	</s:if>	 
	</s:iterator>
	 
</tr>
	
	<tr>
	<th class="thcls" align="center" width="2%" class="title"   ><b>&nbsp; Ophthalmology</b></th>
	<s:iterator id="rsCompl12"  status="status" value="%{dashSpecObj.dashList}" >
		<td align="center" width="1%" class="titleData" style="color:#004276;" onclick="getData('<s:property value="#rsCompl12.time"/>','Ophthalmology','Speciality');" ><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl12.ophthalmology"/></b></a></td>
	<s:if test="attendedBy!='-1'">
	 		<td align="center" width="1%" class="titleData" style="color:#004276;" ><b><s:property value="#rsCompl12.ophthalmology_Per"/>%</b></td>
		 
	 	</s:if>	 
	</s:iterator>
	 
</tr>
<tr>
	<th class="thcls" align="center" width="2%" class="title"   ><b>&nbsp; Pediatric Gastro and Hepatology</b></th>
	<s:iterator id="rsCompl12"  status="status" value="%{dashSpecObj.dashList}" >
		<td align="center" width="1%" class="titleData" style="color:#004276;" onclick="getData('<s:property value="#rsCompl12.time"/>','Pediatric Gastro and Hepatology','Speciality');" ><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl12.pediatric_Gastro_Hepatology"/></b></a></td>
	<s:if test="attendedBy!='-1'">
	 	<td align="center" width="1%" class="titleData" style="color:#004276;" ><b><s:property value="#rsCompl12.pediatric_Gastro_Hepatology_Per"/>%</b></td>
		 
 	</s:if>	 
	</s:iterator>
	 
</tr>
<tr>
	<th class="thcls" align="center" width="2%" class="title"   ><b>&nbsp; Medical Oncology and Hematology</b></th>
	<s:iterator id="rsCompl12"  status="status" value="%{dashSpecObj.dashList}" >
		<td align="center" width="1%" class="titleData" style="color:#004276;" onclick="getData('<s:property value="#rsCompl12.time"/>','Medical Oncology and Hematology','Speciality');" ><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl12.medical_Oncology_Hematology"/></b></a></td>
	<s:if test="attendedBy!='-1'">
	 	<td align="center" width="1%" class="titleData" style="color:#004276;" ><b><s:property value="#rsCompl12.medical_Oncology_Hematology_Per"/>%</b></td>
		 
	 	</s:if>	 
	</s:iterator>
	 
</tr> 
<tr>
	<th class="thcls" align="center" width="2%" class="title"   ><b>&nbsp; Plastic Surgery</b></th>
	<s:iterator id="rsCompl12"  status="status" value="%{dashSpecObj.dashList}" >
		<td align="center" width="1%" class="titleData" style="color:#004276;" onclick="getData('<s:property value="#rsCompl12.time"/>','Plastic Surgery','Speciality');" ><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl12.plastic_Surgery"/></b></a></td>
		<s:if test="attendedBy!='-1'">
	 	<td align="center" width="1%" class="titleData" style="color:#004276;" ><b><s:property value="#rsCompl12.plastic_Surgery_Per"/>%</b></td>
 	</s:if>	 
	</s:iterator>
 
</tr> 
    	 

<tr>
	<th class="thcls" align="center" width="2%" class="title"   ><b>&nbsp; Radiology</b></th>
	<s:iterator id="rsCompl12"  status="status" value="%{dashSpecObj.dashList}" >
		<td align="center" width="1%" class="titleData" style="color:#004276;" onclick="getData('<s:property value="#rsCompl12.time"/>','Radiology','Speciality');" ><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl12.radiology"/></b></a></td>
	<s:if test="attendedBy!='-1'">
	 	<td align="center" width="1%" class="titleData" style="color:#004276;" ><b><s:property value="#rsCompl12.radiology_Per"/>%</b></td>
 	</s:if>	 
	</s:iterator>
	 
</tr> 
<tr>
	<th class="thcls" align="center" width="2%" class="title"   ><b>&nbsp; Nuclear Medicine</b></th>
	<s:iterator id="rsCompl12"  status="status" value="%{dashSpecObj.dashList}" >
		<td align="center" width="1%" class="titleData" style="color:#004276;" onclick="getData('<s:property value="#rsCompl12.time"/>','Nuclear Medicine','Speciality');" ><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl12.nuclear_Medicine"/></b></a></td>
	<s:if test="attendedBy!='-1'">
 		<td align="center" width="1%" class="titleData" style="color:#004276;" ><b><s:property value="#rsCompl12.nuclear_Medicine_Per"/>%</b></td>
 	</s:if>	 
	</s:iterator>
	 
</tr> 
<tr>
	<th class="thcls" align="center" width="2%" class="title"   ><b>&nbsp; Gastroenterology</b></th>
	<s:iterator id="rsCompl12"  status="status" value="%{dashSpecObj.dashList}" >
		<td align="center" width="1%" class="titleData" style="color:#004276;" onclick="getData('<s:property value="#rsCompl12.time"/>','Gastroenterology','Speciality');" ><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl12.gastroenterology"/></b></a></td>
	<s:if test="attendedBy!='-1'">
 		<td align="center" width="1%" class="titleData" style="color:#004276;" ><b><s:property value="#rsCompl12.gastroenterology_Per"/>%</b></td>
 	</s:if>	 
	</s:iterator>
	 
</tr> 
 
 
<tr>
	<th class="thcls" align="center" width="2%" class="title"   ><b>&nbsp; Nephrology</b></th>
	<s:iterator id="rsCompl12"  status="status" value="%{dashSpecObj.dashList}" >
		<td align="center" width="1%" class="titleData" style="color:#004276;" onclick="getData('<s:property value="#rsCompl12.time"/>','Nephrology','Speciality');" ><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl12.nephrology"/></b></a></td>
	<s:if test="attendedBy!='-1'">
	 		<td align="center" width="1%" class="titleData" style="color:#004276;" ><b><s:property value="#rsCompl12.nephrology_Per"/>%</b></td>
	 </s:if>	 
	</s:iterator>
	 
</tr> 
<tr>
	<th class="thcls" align="center" width="2%" class="title"   ><b>&nbsp; GI Surgery</b></th>
	<s:iterator id="rsCompl12"  status="status" value="%{dashSpecObj.dashList}" >
		<td align="center" width="1%" class="titleData" style="color:#004276;" onclick="getData('<s:property value="#rsCompl12.time"/>','GI Surgery','Speciality');" ><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl12.gI_Surgery"/></b></a></td>
	<s:if test="attendedBy!='-1'">
 		<td align="center" width="1%" class="titleData" style="color:#004276;" ><b><s:property value="#rsCompl12.gI_Surgery_Per"/>%</b></td>
	 </s:if>	 
	</s:iterator>
	 
</tr> 
<tr>
	<th class="thcls" align="center" width="2%" class="title"   ><b>&nbsp; Neurosurgery</b></th>
	<s:iterator id="rsCompl12"  status="status" value="%{dashSpecObj.dashList}" >
		<td align="center" width="1%" class="titleData" style="color:#004276;" onclick="getData('<s:property value="#rsCompl12.time"/>','Neurosurgery','Speciality');" ><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl12.neurosurgery"/></b></a></td>
	<s:if test="attendedBy!='-1'">
 		<td align="center" width="1%" class="titleData" style="color:#004276;" ><b><s:property value="#rsCompl12.neurosurgery_Per"/>%</b></td>
		 
 	</s:if>	 
	</s:iterator>
	 
</tr> 
<tr>
	<th class="thcls" align="center" width="2%" class="title"   ><b>&nbsp; Urology Andrology</b></th>
	<s:iterator id="rsCompl12"  status="status" value="%{dashSpecObj.dashList}" >
		<td align="center" width="1%" class="titleData" style="color:#004276;" onclick="getData('<s:property value="#rsCompl12.time"/>','Urology Andrology','Speciality');" ><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl12.urology_Andrology"/></b></a></td>
	<s:if test="attendedBy!='-1'">
 		<td align="center" width="1%" class="titleData" style="color:#004276;" ><b><s:property value="#rsCompl12.urology_Andrology_Per"/>%</b></td>
 	</s:if>	 
	</s:iterator>
	 
</tr> 
<tr>
	<th class="thcls" align="center" width="2%" class="title"   ><b>&nbsp; Internal Medicine</b></th>
	<s:iterator id="rsCompl12"  status="status" value="%{dashSpecObj.dashList}" >
		<td align="center" width="1%" class="titleData" style="color:#004276;" onclick="getData('<s:property value="#rsCompl12.time"/>','Internal Medicine','Speciality');" ><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl12.internal_Medicine"/></b></a></td>
	<s:if test="attendedBy!='-1'">
 		<td align="center" width="1%" class="titleData" style="color:#004276;" ><b><s:property value="#rsCompl12.internal_Medicine_Per"/>%</b></td>
	 	</s:if>	 
	</s:iterator>
	 
</tr> 
<tr>
	<th class="thcls" align="center" width="2%" class="title"   ><b>&nbsp; Vascular and Endovascular Surgery</b></th>
	<s:iterator id="rsCompl12"  status="status" value="%{dashSpecObj.dashList}" >
		<td align="center" width="1%" class="titleData" style="color:#004276;" onclick="getData('<s:property value="#rsCompl12.time"/>','Vascular and Endovascular Surgery','Speciality');" ><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl12.vascular_Endovascular_Surgery"/></b></a></td>
	<s:if test="attendedBy!='-1'">
	 		<td align="center" width="1%" class="titleData" style="color:#004276;" ><b><s:property value="#rsCompl12.vascular_Endovascular_Surgery_Per"/>%</b></td>
	 	</s:if>	 
	</s:iterator>
	 
</tr> 

 	 
<tr>
	<th class="thcls" align="center" width="2%" class="title"   ><b>&nbsp; Rheumatology</b></th>
	<s:iterator id="rsCompl12"  status="status" value="%{dashSpecObj.dashList}" >
		<td align="center" width="1%" class="titleData" style="color:#004276;" onclick="getData('<s:property value="#rsCompl12.time"/>','Rheumatology','Speciality');" ><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl12.rheumatology"/></b></a></td>
	<s:if test="attendedBy!='-1'">
 		<td align="center" width="1%" class="titleData" style="color:#004276;" ><b><s:property value="#rsCompl12.rheumatology_Per"/>%</b></td>
 	</s:if>	 
	</s:iterator>
	 
</tr> 
<tr>
	<th class="thcls" align="center" width="2%" class="title"   ><b>&nbsp; Dental</b></th>
	<s:iterator id="rsCompl12"  status="status" value="%{dashSpecObj.dashList}" >
		<td align="center" width="1%" class="titleData"  style="color:#004276;" onclick="getData('<s:property value="#rsCompl12.time"/>','Dental','Speciality');" ><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl12.dental"/></b></a></td>
	<s:if test="attendedBy!='-1'">
 		<td align="center" width="1%" class="titleData" style="color:#004276;" ><b><s:property value="#rsCompl12.dental_Per"/>%</b></td>
	 	</s:if>	 
	</s:iterator>
	 
</tr> 
<tr>
	<th class="thcls" align="center" width="2%" class="title"   ><b>&nbsp; Ear,Nose and Throat</b></th>
	<s:iterator id="rsCompl12"  status="status" value="%{dashSpecObj.dashList}" >
		<td align="center" width="1%" class="titleData" style="color:#004276;" onclick="getData('<s:property value="#rsCompl12.time"/>','Ear,Nose,Throat','Speciality');" ><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl12.ear_Nose_Throat"/></b></a></td>
	<s:if test="attendedBy!='-1'">
	 		<td align="center" width="1%" class="titleData" style="color:#004276;" ><b><s:property value="#rsCompl12.ear_Nose_Throat_Per"/>%</b></td>
	 </s:if>	 
	</s:iterator>
	 
</tr> 

<tr>
	<th class="thcls" align="center" width="2%" class="title"   ><b>&nbsp; Respiratory Sleep Medicine</b></th>
	<s:iterator id="rsCompl12"  status="status" value="%{dashSpecObj.dashList}" >
		<td align="center" width="1%" class="titleData" style="color:#004276;" onclick="getData('<s:property value="#rsCompl12.time"/>','Respiratory Sleep Medicine','Speciality');" ><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl12.respiratory_Sleep_Medicine"/></b></a></td>
	<s:if test="attendedBy!='-1'">
	 	<td align="center" width="1%" class="titleData" style="color:#004276;" ><b><s:property value="#rsCompl12.respiratory_Sleep_Medicine_Per"/>%</b></td>
	 	</s:if>	 
	</s:iterator>
	 
</tr> 
<tr>
	<th class="thcls" align="center" width="2%" class="title"   ><b>&nbsp; Pediatric Cardiology</b></th>
	<s:iterator id="rsCompl12"  status="status" value="%{dashSpecObj.dashList}" >
		<td align="center" width="1%" class="titleData" style="color:#004276;" onclick="getData('<s:property value="#rsCompl12.time"/>','Pediatric Cardiology','Speciality');" ><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl12.pediatric_Cardiology"/></b></a></td>
	<s:if test="attendedBy!='-1'">
	 	<td align="center" width="1%" class="titleData" style="color:#004276;" ><b><s:property value="#rsCompl12.pediatric_Cardiology_Per"/>%</b></td>
	 </s:if>	 
	</s:iterator>
	 
</tr> 
 
<tr>
	<th class="thcls" align="center" width="2%" class="title"   ><b>&nbsp; Gynaecology Obstetrics</b></th>
	<s:iterator id="rsCompl12"  status="status" value="%{dashSpecObj.dashList}" >
		<td align="center" width="1%" class="titleData" style="color:#004276;" onclick="getData('<s:property value="#rsCompl12.time"/>','Gynaecology Obstetrics','Speciality');" ><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl12.gynaecology_Obstetrics"/></b></a></td>
	<s:if test="attendedBy!='-1'">
	 	<td align="center" width="1%" class="titleData" style="color:#004276;" ><b><s:property value="#rsCompl12.gynaecology_Obstetrics_Per"/>%</b> </td>
	 	</s:if>	 
	</s:iterator>
	 
</tr> 
 
<tr>
	<th class="thcls" align="center" width="2%" class="title"   ><b>&nbsp; Head Neck Oncology</b></th>
	<s:iterator id="rsCompl12"  status="status" value="%{dashSpecObj.dashList}" >
		<td align="center" width="1%" class="titleData" style="color:#004276;" onclick="getData('<s:property value="#rsCompl12.time"/>','Head Neck Oncology','Speciality');" ><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl12.head_Neck_Oncology"/></b></a></td>
	<s:if test="attendedBy!='-1'">
 		<td align="center" width="1%" class="titleData" style="color:#004276;" ><b><s:property value="#rsCompl12.head_Neck_Oncology_Per"/>%</b></td>
	 	</s:if>	 
	</s:iterator>
	 
</tr> 
     	 
<tr>
	<th class="thcls" align="center" width="2%" class="title"   ><b>&nbsp; Interventional Radiology</b></th>
	<s:iterator id="rsCompl12"  status="status" value="%{dashSpecObj.dashList}" >
		<td align="center" width="1%" class="titleData" style="color:#004276;" onclick="getData('<s:property value="#rsCompl12.time"/>','Interventional Radiology','Speciality');" ><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl12.interventional_Radiology"/></b></a></td>
	<s:if test="attendedBy!='-1'">
	 		<td align="center" width="1%" class="titleData" style="color:#004276;" ><b><s:property value="#rsCompl12.interventional_Radiology_Per"/>%</b></td>
	 	</s:if>	 
	</s:iterator>
	 
</tr> 
<tr>
	<th class="thcls" align="center" width="2%" class="title"   ><b>&nbsp; Breast Services</b></th>
	<s:iterator id="rsCompl12"  status="status" value="%{dashSpecObj.dashList}" >
		<td align="center" width="1%" class="titleData" style="color:#004276;" onclick="getData('<s:property value="#rsCompl12.time"/>','Breast Services','Speciality');" ><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl12.breast_Services"/></b></a></td>
		<s:if test="attendedBy!='-1'">
	 		<td align="center" width="1%" class="titleData" style="color:#004276;" ><b><s:property value="#rsCompl12.breast_Services_Per"/>%</b></td>
	 	</s:if> 
	</s:iterator>
	 
</tr> 
<tr>
	<th class="thcls" align="center" width="2%" class="title"   ><b>&nbsp; Thoracic Surgery</b></th>
	<s:iterator id="rsCompl12"  status="status" value="%{dashSpecObj.dashList}" >
		<td align="center" width="1%" class="titleData" style="color:#004276;" onclick="getData('<s:property value="#rsCompl12.time"/>','Thoracic Surgery','Speciality');" ><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl12.thoracic_Surgery"/></b></a></td>
	<s:if test="attendedBy!='-1'">
 		<td align="center" width="1%" class="titleData" style="color:#004276;" ><b><s:property value="#rsCompl12.thoracic_Surgery_Per"/>%</b></td>
 	</s:if>	 
	</s:iterator>
	 
</tr> 
<tr>
	<th class="thcls" align="center" width="2%" class="title"   ><b>&nbsp; Dermatology</b></th>
	<s:iterator id="rsCompl12"  status="status" value="%{dashSpecObj.dashList}" >
		<td align="center" width="1%" class="titleData" style="color:#004276;" onclick="getData('<s:property value="#rsCompl12.time"/>','Dermatology','Speciality');" ><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl12.dermatology"/></b></a></td>
	<s:if test="attendedBy!='-1'">
 		<td align="center" width="1%" class="titleData" style="color:#004276;" ><b><s:property value="#rsCompl12.dermatology_Per"/>%</b></td>
	 	</s:if>	 
	</s:iterator>
	 
</tr> 
<tr>
	<th class="thcls" align="center" width="2%" class="title"   ><b>&nbsp; Pediatrics</b></th>
	<s:iterator id="rsCompl12"  status="status" value="%{dashSpecObj.dashList}" >
		<td align="center" width="1%" class="titleData" style="color:#004276;" onclick="getData('<s:property value="#rsCompl12.time"/>','Pediatrics','Speciality');" ><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl12.pediatrics"/></b></a></td>
	<s:if test="attendedBy!='-1'">
	 	<td align="center" width="1%" class="titleData" style="color:#004276;" ><b><s:property value="#rsCompl12.pediatrics_Per"/>%</b></td>
 	</s:if>	 
	</s:iterator>
	 
</tr> 
 
 
 
<tr>
	<th class="thcls" align="center" width="2%" class="title"   ><b>&nbsp; Ayurvedic Medicine</b></th>
	<s:iterator id="rsCompl12"  status="status" value="%{dashSpecObj.dashList}" >
		<td align="center" width="1%" class="titleData" style="color:#004276;" onclick="getData('<s:property value="#rsCompl12.time"/>','Ayurvedic Medicine','Speciality');" ><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl12.ayurvedic_Medicine"/></b></a></td>
	<s:if test="attendedBy!='-1'">
	 	<td align="center" width="1%" class="titleData" style="color:#004276;" ><b><s:property value="#rsCompl12.ayurvedic_Medicine_Per"/>%</b></td>
	 	</s:if>	 
	</s:iterator>
	 
</tr> 
<tr>
	<th class="thcls" align="center" width="2%" class="title"   ><b>&nbsp; Physiotherapy</b></th>
	<s:iterator id="rsCompl12"  status="status" value="%{dashSpecObj.dashList}" >
		<td align="center" width="1%" class="titleData" style="color:#004276;" onclick="getData('<s:property value="#rsCompl12.time"/>','Physiotherapy','Speciality');" ><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl12.physiotherapy"/></b></a></td>
		<s:if test="attendedBy!='-1'">
	 		<td align="center" width="1%" class="titleData" style="color:#004276;" ><b><s:property value="#rsCompl12.physiotherapy_Per"/>%</b></td>
	 	</s:if> 
	</s:iterator>
	 
</tr> 
<tr>
	<th class="thcls" align="center" width="2%" class="title" style="color:#004276;background:#C2C2CC "   ><b>&nbsp; Total</b></th>
	<s:iterator id="rsCompl12"  status="status" value="%{dashSpecObj.dashList}" >
		<td align="center" width="1%" class="titleData" style="color:#004276;background:#C2C2CC " onclick="getData('<s:property value="#rsCompl12.time"/>','Total','Speciality');" ><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl12.total"/></b></a></td>
	  <s:if test="attendedBy!='-1'">
	 	<td align="center" width="1%" class="titleData" style="color:#004276;background:#C2C2CC " ><b><s:property value="#rsCompl12.total_Per"/>%</b></td>
 	 </s:if>
	 </s:iterator>
	 
</tr>
</s:if>
 
 
 <s:elseif test="dashFor=='Status'">
  <tr >
	<th class="thcls" colspan="31" align="center"   class="title" style="color: rgba(53, 54, 58, 0);background: rgba(33, 32, 99, 0.75); " ><b>Referral Status</b></th>
	 
</tr>	
  <tr >
	<th class="thcls" align="center"   class="title"   ><b>Status</b></th>
	<s:iterator id="rsCompl12"  status="status" value="%{dashObj.dashList}" >
		<th class="thcls" colspan="2" align="center"   class="titleData"  ><b><s:property value="#rsCompl12.time"/></b></th>
	</s:iterator>
</tr>	
 <tr>
	<th class="thcls" align="center"   class="title"   ><b>Close</b></th>
	<s:iterator id="rsCompl12"  status="status" value="%{dashObj.dashList}" >
		<td align="center"   class="titleData" style="color:#004276;" onclick="getData('<s:property value="#rsCompl12.time"/>','Close','Status');" ><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl12.rc"/></b></a></td>
		<td align="center"   class="titleData" style="color:#004276;" ><b><s:property value="#rsCompl12.rcPer"/>%</b></td>
	</s:iterator>
</tr>	
<tr>
	<th class="thcls" align="center"   class="title"   ><b>Open</b></th>
	<s:iterator id="rsCompl12"  status="status" value="%{dashObj.dashList}" >
		<td align="center"   class="titleData" style="color:#004276;" onclick="getData('<s:property value="#rsCompl12.time"/>','Open','Status');" ><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl12.pc"/></b></a></td>
		<td align="center"   class="titleData" style="color:#004276;" ><b><s:property value="#rsCompl12.pcPer"/>%</b></td>
	</s:iterator>
</tr>
<tr>
	<th class="thcls" align="center"   class="title" style="color:#004276;background:#C2C2CC " ><b>Total</b></th>
	<s:iterator id="rsCompl12"  status="status" value="%{dashObj.dashList}" >
		<td align="center"   class="titleData" style="color:#004276;background:#C2C2CC " onclick="getData('<s:property value="#rsCompl12.time"/>','All','Status');" ><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl12.total"/></b></a></td>
		<td align="center"   class="titleData" style="color:#004276;background:#C2C2CC " ><b><s:property value="#rsCompl12.totalPer"/>%</b></td>
	</s:iterator>
</tr>
 	 
</s:elseif>
 
</table>
</div>
</body>
</html>