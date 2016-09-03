<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<style type="text/css">

.user_form_input{
    margin-bottom:10px;
}

</style>
<html>
<head>
<script src="<s:url value="/js/employeeOver2Cloud.js"/>"></script>
</head>
<sj:dialog
          id="docDownloadDialog"
          showEffect="slide"
          hideEffect="explode"
          autoOpen="false"
          modal="true"
          title="Download" 
          closeTopics="closeEffectDialog"
          width="400"
          height="200"
          >
          <div id="docDownloadDataDiv"></div>
</sj:dialog>

<sj:dialog
          id="bankdocDownloadDialog"
          showEffect="slide"
          hideEffect="explode"
          autoOpen="false"
          modal="true"
          title="Download" 
          closeTopics="closeEffectDialog"
          width="400"
          height="200"
          >
          <div id="bankdocDownloadDataDiv"></div>
</sj:dialog>

<sj:dialog
          id="basicdocDownloadDialog"
          showEffect="slide"
          hideEffect="explode"
          autoOpen="false"
          modal="true"
          title="Download" 
          closeTopics="closeEffectDialog"
          width="400"
          height="200"
          >
          <div id="basicdocDownloadDataDiv"></div>
</sj:dialog>

<sj:dialog
          id="empPerdocDownloadDialog"
          showEffect="slide"
          hideEffect="explode"
          autoOpen="false"
          modal="true"
          title="Download" 
          closeTopics="closeEffectDialog"
          width="400"
          height="200"
          >
          <div id="empPerdocDownloadDataDiv"></div>
</sj:dialog>

<sj:dialog
          id="empWorkexpdocDownloadDialog"
          showEffect="slide"
          hideEffect="explode"
          autoOpen="false"
          modal="true"
          title="Download" 
          closeTopics="closeEffectDialog"
          width="400"
          height="200"
          >
          <div id="empWorkexpdocDownloadDataDiv"></div>
</sj:dialog>


<body>
<div class="container_block">
<div style=" float:left; padding:10px 1%; width:98%;">
	
	<s:if test="%{record==1}">
     <div class="list-icon">
	 <div class="head"><center><B>Records For <s:property value="%{fetchEmpName}"/></B></center> </div> 
</div>
<br>
  <br>
  <br>
  </s:if>
  
  
<s:hidden id="record" value="%{record}"/>
   
   	<s:if test="%{empBasicFullViewMap != null && empBasicFullViewMap.size > 0}">
	<table width="100%">
	<tr style="background-color: #999966"><td colspan="4" align="Center" ><STRONG>Basic records</STRONG> 
	<s:if test="%{!record==1}">
	<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="EmpBasicDocDetails('<s:property value="%{mobTwo}"/>')"><img src="images/docDownlaod.jpg" width="15" height="15" alt="Download" title="Download" /></a></div>
	</s:if>
	</tr>
   <tr  style="background-color: #CCCCB2">
	<s:iterator value="%{empBasicFullViewMap}" >
		<s:if test="key=='Contact Id' || key=='Comm Name'">
			<td align="left" width="25%"><strong><s:property value="%{key}"/>:</strong></td>
			<td align="left" width="25%"><s:property value="%{value}"/></td>
		</s:if>
	</s:iterator>
	</tr>
	
	<tr  style="background-color: #E0E0D1">
	<s:iterator value="%{empBasicFullViewMap}" >
		<s:if test="key=='Mobile No' || key=='Email Id'">
			<td align="left" width="25%"><strong><s:property value="%{key}"/>:</strong></td>
			<td align="left" width="25%"><s:property value="%{value}"/></td>
		</s:if>
	</s:iterator>
	</tr>
	
	<tr  style="background-color: #CCCCB2">
	<s:iterator value="%{empBasicFullViewMap}" >
		<s:if test="key=='Address' || key=='City'">
			<td align="left" width="25%"><strong><s:property value="%{key}"/>:</strong></td>
			<td align="left" width="25%"><s:property value="%{value}"/></td>
		</s:if>
	</s:iterator>
	</tr>
	
	<tr  style="background-color: #E0E0D1">
	<s:iterator value="%{empBasicFullViewMap}" >
		<s:if test="key=='Birthday' || key=='Anniversary'">
			<td align="left" width="25%"><strong><s:property value="%{key}"/>:</strong></td>
			<td align="left" width="25%"><s:property value="%{value}"/></td>
		</s:if>
	</s:iterator>
	</tr>
	<tr  style="background-color: #CCCCB2">
	<s:iterator value="%{empBasicFullViewMap}" >
		<s:if test="key=='Joining' || key=='Other'">
			<td align="left" width="25%"><strong><s:property value="%{key}"/>:</strong></td>
			<td align="left" width="25%"><s:property value="%{value}"/></td>
		</s:if>
	</s:iterator>
	</tr>
	<tr  style="background-color: #E0E0D1">
	<s:iterator value="%{empBasicFullViewMap}" >
		<s:if test="key=='Alternate Mobile No' || key=='Alternate Email Id'">
			<td align="left" width="25%"><strong><s:property value="%{key}"/>:</strong></td>
			<td align="left" width="25%"><s:property value="%{value}"/></td>
		</s:if>
	</s:iterator>
	</tr>
	
	</table>	
	</s:if>




	<s:if test="%{empPersonalFullViewMap != null && empPersonalFullViewMap.size > 0}">
	<table width="100%">
	<tr style="background-color: #999966"><td colspan="4" align="Center" ><STRONG>Personal & Medical Record</STRONG> 
	<s:if test="%{!record==1}">
	<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="empPerdocDownloadDetails('<s:property value="%{mobTwo}"/>')"><img src="images/docDownlaod.jpg" width="15" height="15" alt="Download" title="Download" /></a></div>
	</s:if>
	</tr>
	
	<tr  style="background-color: #E0E0D1">
	<s:iterator value="%{empPersonalFullViewMap}" >
		<s:if test="key=='Spouse Name' || key=='Father Name'">
			<td align="left" width="25%"><strong><s:property value="%{key}"/>:</strong></td>
			<td align="left" width="25%"><s:property value="%{value}"/></td>
		</s:if>
	</s:iterator>
	</tr>
	<tr  style="background-color: #CCCCB2">
	<s:iterator value="%{empPersonalFullViewMap}" >
		<s:if test="key=='Mother Name' || key=='Child One Name'">
			<td align="left" width="25%"><strong><s:property value="%{key}"/>:</strong></td>
			<td align="left" width="25%"><s:property value="%{value}"/></td>
		</s:if>
	</s:iterator>
	</tr>
	<tr  style="background-color: #E0E0D1">
	<s:iterator value="%{empPersonalFullViewMap}" >
		<s:if test="key=='Child One DOB' || key=='Child Two Name'">
			<td align="left" width="25%"><strong><s:property value="%{key}"/>:</strong></td>
			<td align="left" width="25%"><s:property value="%{value}"/></td>
		</s:if>
	</s:iterator>
	</tr>
	<tr  style="background-color: #CCCCB2">
	<s:iterator value="%{empPersonalFullViewMap}" >
		<s:if test="key=='Child Two DOB' || key=='Address'">
			<td align="left" width="25%"><strong><s:property value="%{key}"/>:</strong></td>
			<td align="left" width="25%"><s:property value="%{value}"/></td>
		</s:if>
	</s:iterator>
	</tr>
	<tr  style="background-color: #E0E0D1">
	<s:iterator value="%{empPersonalFullViewMap}" >
		<s:if test="key=='Landmark' || key=='City'">
			<td align="left" width="25%"><strong><s:property value="%{key}"/>:</strong></td>
			<td align="left" width="25%"><s:property value="%{value}"/></td>
		</s:if>
	</s:iterator>
	</tr>
	<tr  style="background-color: #CCCCB2">
	<s:iterator value="%{empPersonalFullViewMap}" >
		<s:if test="key=='PIN Code' || key=='Reference-1 Name'">
			<td align="left" width="25%"><strong><s:property value="%{key}"/>:</strong></td>
			<td align="left" width="25%"><s:property value="%{value}"/></td>
		</s:if>
	</s:iterator>
	</tr>
	<tr  style="background-color: #E0E0D1">
	<s:iterator value="%{empPersonalFullViewMap}" >
		<s:if test="key=='Refernce-1 Mobile' || key=='Reference-2 Name'">
			<td align="left" width="25%"><strong><s:property value="%{key}"/>:</strong></td>
			<td align="left" width="25%"><s:property value="%{value}"/></td>
		</s:if>
	</s:iterator>
	</tr>
	<tr  style="background-color: #CCCCB2">
	<s:iterator value="%{empPersonalFullViewMap}" >
		<s:if test="key=='Reference-2 Mobile' || key=='Family Mobile'">
			<td align="left" width="25%"><strong><s:property value="%{key}"/>:</strong></td>
			<td align="left" width="25%"><s:property value="%{value}"/></td>
		</s:if>
	</s:iterator>
	</tr>
	<tr  style="background-color: #E0E0D1">
	<s:iterator value="%{empPersonalFullViewMap}" >
		<s:if test="key=='Emergency No.' || key=='Blood Group'">
			<td align="left" width="25%"><strong><s:property value="%{key}"/>:</strong></td>
			<td align="left" width="25%"><s:property value="%{value}"/></td>
		</s:if>
	</s:iterator>
	</tr>
	<tr  style="background-color: #CCCCB2">
	<s:iterator value="%{empPersonalFullViewMap}" >
		<s:if test="key=='Alergic To' || key=='Other Medical'">
			<td align="left" width="25%"><strong><s:property value="%{key}"/>:</strong></td>
			<td align="left" width="25%"><s:property value="%{value}"/></td>
		</s:if>
	</s:iterator>
	</tr>
	</table>	
	</s:if>

	
	<s:if test="%{empProfessionalViewMap != null && empProfessionalViewMap.size > 0}">
	<table height="40%" width="100%" align="center" >
	<tr style="background-color: #999966"><td colspan="4" align="Center"><STRONG> Educational Record</STRONG>
	<s:if test="%{!record==1}">
	<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="docDownloadDetails('<s:property value="%{mobTwo}"/>')"><img src="images/docDownlaod.jpg" width="15" height="15" alt="Download" title="Download" /></a></div>
	</s:if>
	</tr>
	<s:iterator value="empProfessionalViewMap" status="status">
	
			<tr  style="background-color: #E0E0D1">
		<td align="left" width="25%"><strong>Academic:</strong></td>
		<td align="left" width="25%"><s:property value="%{Acadmic}"/>:</td>
		
		
		
		<td align="left" width="25%"><strong>Aggregate:</strong></td>
		<td align="left" width="25%"><s:property value="%{Aggregate}"/>:</td>
		</tr>
		
		<tr  style="background-color: #CCCCB2">
		<td align="left" width="25%"><strong>Subject:</strong></td>
		<td align="left" width="25%"><s:property value="%{Subject}"/>:</td>
		
		<td align="left" width="25%"><strong>Year:</strong></td>
		<td align="left" width="25%"><s:property value="%{Year}"/>:</td>
		</tr>
		
		<tr>
		<tr  style="background-color: #E0E0D1">
		<td align="left" width="25%"><strong>College:</strong></td>
		<td align="left" width="25%"><s:property value="%{College}"/>:</td>
		
		<td align="left" width="25%"><strong>University:</strong></td>
		<td align="left" width="25%"><s:property value="%{University}"/>:</td>
		</tr>
			
			
		
	
	
	</s:iterator>

</table>
	</s:if>
	<s:if test="%{empworkExpFullViewMap != null && empworkExpFullViewMap.size > 0}">
	<table width="100%">
	
	<tr style="background-color: #999966 " ><td colspan="4" align="Center"  width="200%"><STRONG>Professional Record</STRONG> 
	<s:if test="%{!record==1}">
	<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="empworkExpdocDownloadDetails('<s:property value="%{mobTwo}"/>')"><img src="images/docDownlaod.jpg" width="15" height="15" alt="Download" title="Download" /></a></div>
	</s:if>
	</tr>
   <tr  style="background-color: #E0E0D1">
	<s:iterator value="%{empworkExpFullViewMap}" >
		<s:if test="key=='Total Work Exp' || key=='Organisation Name'">
			<td align="left" width="25%"><strong><s:property value="%{key}"/>:</strong></td>
			<td align="left" width="25%"><s:property value="%{value}"/></td>
		</s:if>
	</s:iterator>
	
	</tr>
	<tr  style="background-color: #CCCCB2">
	<s:iterator value="%{empworkExpFullViewMap}" >
		<s:if test="key=='Salary' || key=='Phone No'">
			<td align="left" width="25%"><strong><s:property value="%{key}"/>:</strong></td>
			<td align="left" width="25%"><s:property value="%{value}"/></td>
		</s:if>
	</s:iterator>
	
	</tr>
	<tr  style="background-color: #E0E0D1">
	<s:iterator value="%{empworkExpFullViewMap}" >
		<s:if test="key=='Designation' || key=='Department'">
			<td align="left" width="25%"><strong><s:property value="%{key}"/>:</strong></td>
			<td align="left" width="25%"><s:property value="%{value}"/></td>
		</s:if>
	</s:iterator>
	
	</tr>
	<tr  style="background-color: #CCCCB2">
	<s:iterator value="%{empworkExpFullViewMap}" >
		<s:if test="key=='From' || key=='To'">
			<td align="left" width="25%"><strong><s:property value="%{key}"/>:</strong></td>
			<td align="left" width="25%"><s:property value="%{value}"/></td>
		</s:if>
	</s:iterator>
	
	</tr>
		<tr  style="background-color: #E0E0D1">
	<s:iterator value="%{empworkExpFullViewMap}" >
		<s:if test="key=='Reference-1 Name' || key=='Refernce-1 Mobile'">
			<td align="left" width="25%"><strong><s:property value="%{key}"/>:</strong></td>
			<td align="left" width="25%"><s:property value="%{value}"/></td>
		</s:if>
	</s:iterator>
	
	</tr>
		<tr  style="background-color: #CCCCB2">
	<s:iterator value="%{empworkExpFullViewMap}" >
		<s:if test="key=='Reference-2 Name' || key=='Reference-2 Mobile'">
			<td align="left" width="25%"><strong><s:property value="%{key}"/>:</strong></td>
			<td align="left" width="25%"><s:property value="%{value}"/></td>
		</s:if>
	</s:iterator>
	
	</tr>
	
			<tr  style="background-color: #E0E0D1">
	<s:iterator value="%{empworkExpFullViewMap}" >
		<s:if test="key=='Address' ">
			<td align="left" width="25%"><strong><s:property value="%{key}"/>:</strong></td>
			<td align="left" width="25%"><s:property value="%{value}"/></td>
		</s:if>
	</s:iterator>
	
	</tr>
	
	</table>	
	</s:if>
	
	
		<s:if test="%{empBankFullViewMap != null && empBankFullViewMap.size > 0}">
	<table width="100%">
	
	<tr style="background-color: #999966" ><td colspan="4" align="Center"  width="200%"><STRONG> Bank Record</STRONG> 
	<s:if test="%{!record==1}">
	<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="bankdocDownloadDetails('<s:property value="%{mobTwo}"/>')"><img src="images/docDownlaod.jpg" width="15" height="15" alt="Download" title="Download" /></a></div>
	</s:if>
	</tr>
   <tr  style="background-color: #E0E0D1">
	<s:iterator value="%{empBankFullViewMap}" >
		<s:if test="key=='Bank Name' || key=='Bank Address'">
			<td align="left" width="25%"><strong><s:property value="%{key}"/>:</strong></td>
			<td align="left" width="25%"><s:property value="%{value}"/></td>
		</s:if>
	</s:iterator>
	
	</tr>
	<tr  style="background-color: #CCCCB2">
	<s:iterator value="%{empBankFullViewMap}" >
		<s:if test="key=='Bank Phone' || key=='Account No'">
			<td align="left" width="25%"><strong><s:property value="%{key}"/>:</strong></td>
			<td align="left" width="25%"><s:property value="%{value}"/></td>
		</s:if>
	</s:iterator>
	
	</tr>
	<tr  style="background-color: #E0E0D1">
	<s:iterator value="%{empBankFullViewMap}" >
		<s:if test="key=='IFSC Code' || key=='MIRC No.'">
			<td align="left" width="25%"><strong><s:property value="%{key}"/>:</strong></td>
			<td align="left" width="25%"><s:property value="%{value}"/></td>
		</s:if>
	</s:iterator>
	
	</tr>
	<tr  style="background-color: #CCCCB2">
	<s:iterator value="%{empBankFullViewMap}" >
		<s:if test="key=='Account Type' || key=='PAN Card'">
			<td align="left" width="25%"><strong><s:property value="%{key}"/>:</strong></td>
			<td align="left" width="25%"><s:property value="%{value}"/></td>
		</s:if>
	</s:iterator>
	
	</tr>
	
	</table>	
	</s:if>
	
	<s:else>
		<b>No Record Found</b>
	</s:else>

	
	
	
	
	
	
	
</div>
</div>
</body>
</html>