<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib  prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script type="text/javascript" src="<s:url value="/js/helpdesk/feedback.js"/>"></script>
<script>
function printDiv(divName) {
    var printContents = document.getElementById(divName).innerHTML;
    $("#printSelect").dialog('close');
    var originalContents = document.body.innerHTML;
 	 document.body.innerHTML = printContents;
    window.print();
    document.body.innerHTML = originalContents;
    window.close(); 
}

function  CancelPrint()
{
   $("#printSelect").dialog('close');
   $("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
 	$.ajax({
 	    type : "post",
 	    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset_Complaint/afterTackAction.action?",
 	    success : function(data) 
 	    {
 			$("#"+"data_part").html(data);
 	    },
 	    error: function() 
 	    {
             alert("error");
         }
 	 });	
  
}
 
</script>

</head>
<body>

<c:set var="asset_Category" value="${fstatus.asset_Category}" />
<c:set var="serial_No" value="${fstatus.serial_No}" />
<c:set var="brand" value="${fstatus.brand}" /> 
<c:set var="vendor" value="${fstatus.vendor}" /> 
<c:set var="asset_SubCategory" value="${fstatus.asset_SubCategory}"/>
<c:set var="offsite_Person" value="${fstatus.offsite_Person}"/>
<c:set var="offsite_PersonMob" value="${fstatus.offsite_personMob}"/>
<c:set var="return_Date" value="${fstatus.return_Date}"/>
<c:set var="return_Time" value="${fstatus.return_Time}"/>
<c:set var="assetName" value="${fstatus.assetName}"/>
<c:set var="asset_Model" value="${fstatus.asset_Model}"/>
<c:set var="asset_Vendor" value="${fstatus.asset_Vendor}"/>
<c:set var="offsite_On_Date" value="${fstatus.offsite_On_Date}"/>
<c:set var="offsite_On_Time" value="${fstatus.offsite_On_Time}"/>
<c:set var="offsite_Reason" value="${fstatus.offsite_Reason}"/>
<c:set var="offsite_AllotTo" value="${fstatus.offsite_AllotTo}"/>
<c:set var="offsite_ToDept" value="${fstatus.offsite_ToDept}"/>
<c:set var="asset_Id" value="${fstatus.assetId}"/>


 <c:set var="orgName" value="${orgName}" />
<c:set var="address" value="${address}" />
<c:set var="city" value="${city}" />
<c:set var="pincode" value="${pincode}" />
<div id="printDiv">
<table cellpadding="0" border="0" align="center" cellspacing="0" style="border-collapse: collapse" width="100%">
<tr>
   <td width="30%" align="left"><img src="<s:url value="/images/company.jpg"/>" border="0" width="150" height="50" /></td>
   <td width="70%" align="left">
      <table cellpadding="0" border="0" align="center" cellspacing="0" style="border-collapse: collapse" width="100%">
      <tr>
          <td width="60%" align="center"><font face="Arial, Helvetica, sans-serif" color="#000000" size="3"><b><u><c:out value="${orgName}" /></u></b></font></td>
      </tr>
      <tr>
          <td width="50%" align="center"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b><u><c:out value="${address}" />,&nbsp;<c:out value="${city}" /></u></b></font></td>
                    
     </tr>
       
    
</table>
   </td>
</tr>
</table>
<hr />
<table border="1" width="100%" align="center">

   
   
   <tr>
		<td width="20%">
			<font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><b>&nbsp;Asset Name:&nbsp;</b></font>
		</td>
		<td width="30%">
			<font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><b>&nbsp;<c:out value="${assetName}" /></b></font>
		</td>
		<td width="20%">
			<font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><b>&nbsp;Bar Code:&nbsp;</b></font>
		</td>
		<td width="100%">
	 	  
		 <img id="img_barcode" border="0" src="<s:url value='/images/barCodeImage/%{fstatus.assetId}.gif'/>" align="right" width="200"  height="80"/>
	 	</td>
	</tr>
 
   
    <tr>
		 <td width="20%">
			<font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><b>&nbsp;Serial No.:&nbsp;</b></font>
		</td>
		<td width="30%">
			<font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><b>&nbsp;<c:out value="${serial_No}" /></b></font>
		</td>
	 
		<td width="20%">
			<font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><b>&nbsp;Brand:&nbsp;</b></font>
		</td>
		<td width="30%">
			<font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><b>&nbsp;<c:out value="${brand}" /></b></font>
		</td>
		
	</tr>


<tr>

	<td width="20%">
			<font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><b>&nbsp;Asset Model:&nbsp;</b></font>
		</td>
		<td width="30%">
			<font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><b>&nbsp;<c:out value="${asset_Model}" /></b></font>
		</td>
	<td width="20%">
			<font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><b>&nbsp;Asset Category:&nbsp;</b></font>
		</td>
		<td width="30%">
			<font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><b>&nbsp;<c:out value="${asset_Category}" /></b></font>
		</td>
</tr>



<tr>

	<td width="20%">
			<font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><b>&nbsp;Allot To:&nbsp;</b></font>
		</td>
		<td width="30%">
			<font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><b>&nbsp;<c:out value="${offsite_AllotTo}" /></b></font>
		</td>
	<td width="20%">
			<font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><b>&nbsp;To Department:&nbsp;</b></font>
		</td>
		<td width="30%">
			<font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><b>&nbsp;<c:out value="${offsite_ToDept}" /></b></font>
		</td>
		
</tr>


<tr>

	<td width="20%">
			<font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><b>&nbsp;Asset Sub-Category:&nbsp;</b></font>
		</td>
		<td width="30%">
			<font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><b>&nbsp;<c:out value="${asset_SubCategory}" /></b></font>
		</td>
	<td width="20%">
			<font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><b>&nbsp;Reason:&nbsp;</b></font>
		</td>
		<td width="30%">
			<font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><b>&nbsp;<c:out value="${offsite_Reason}" /></b></font>
		</td>
</tr>




<tr>
		 
		 
	<td width="20%">
			<font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><b>&nbsp;Date & Time:&nbsp;</b></font>
		</td>
		<td width="30%">
			<font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><b>&nbsp;<c:out value="${offsite_On_Date}" /></b></font>
		</td>
		<td width="20%">
			<font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><b>&nbsp;vendor:&nbsp;</b></font>
		</td>
		<td width="30%">
			<font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><b>&nbsp;<c:out value="${vendor}" /></b></font>
		</td>
</tr>
	
	
	
	
	
	<tr>
		 
		<td width="20%">
			<font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><b>&nbsp;Person Name:&nbsp;</b></font>
		</td>
		<td width="30%">
			<font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><b>&nbsp;<c:out value="${offsite_Person}" /></b></font>
		</td>
		
		<td width="20%">
			<font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><b>&nbsp;Mobile No.:&nbsp;</b></font>
		</td>
		<td width="30%">
			<font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><b>&nbsp;<c:out value="${offsite_PersonMob}" /></b></font>
		</td>
</tr>
	
	<tr>
		 
		<td width="20%">
			<font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><b>&nbsp;Return Date & Time:&nbsp;</b></font>
		</td>
		<td width="30%">
			<font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><b>&nbsp;<c:out value="${return_Date}" /></b></font>
		</td>
	</tr>
	 
    
</table>
<br />
<table width="100%" align="center">
	<tr>
		<td width="30%" align="center">
			<font face="Arial, Helvetica, sans-serif" color="#000000" size="1">
			<b>Resolver Sign</b></font>
		</td>
		<td width="30%" align="center">
		</td>
		<td width="30%" align="center">
			<font face="Arial, Helvetica, sans-serif" color="#000000" size="1">
				<b>Authorization Sign</b></font>
		</td>
	</tr>
</table>     
      <table align="center" width="100%">
         <tr>
          <td width="100%" align="center"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1">
-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------</font></td> 
        </tr>
	</table>
	</div>
	<br>
	<div class="type-button">
       		<center>
              <sj:submit 
                        clearForm="true"
                        value="  Print  " 
                        button="true"
                        onclick="printDiv('printDiv')"
                        />
              <sj:submit 
                        clearForm="true"
                        value="  Cancel  " 
                        button="true"
                        onclick="CancelPrint()"
                        />          
              </center>
              </div> 
	
</body>
</html>