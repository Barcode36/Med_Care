<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript">

function saveClick()
{
	
	var row;
	row = $("#employeeGrid").jqGrid('getGridParam','selrow');
	
	if (row==null || row=='null' || row=="") 
	{
		alert("Please select one Employee !!!");
	}
	else 
	{
		var clientName =$("#clientName").val();
		$("#escalationMapResult").html("<br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
			 type : "post",
			 url : "/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Lodge_Complaint/sendFeedbackCall.action?rowId="+row+"&forDept=<s:property value="%{#parameters.dept}"/>&viaFrom=call&clientName="+clientName+"&feedbackTo=<s:property value="%{#parameters.toContactPerson}"/>&feedbackCC=<s:property value="%{#parameters.ccContactPerson}"/>&clientfor=<s:property value="%{#parameters.clientfor}"/>&category=<s:property value="%{#parameters.categoryName}"/>&fbtype=<s:property value="%{#parameters.fbType}"/>&offering=<s:property value="%{#parameters.offering}"/>&subCategory=<s:property value="%{#parameters.subCategoryName}"/>&feed_brief=<s:property  value="%{#parameters.feedBreif}"/>&recvSMS=<s:property  value="%{#parameters.sms}"/>&recvEmail=<s:property  value="%{#parameters.mail}"/>",
			 success : function(saveData) 
			 {
   			 $("#escalationMapResult").html(saveData);
			 },
			 error: function() 
			 {
				 alert("error");
			 }
			});
	}
}
</script>
</head>
<body>
<s:hidden id="clientName" value="%{#parameters.cName}"/>
<s:url id="emp4escalation_URL" action="getEmployee4Escalation" escapeAmp="false">
<s:param name="clientfor" value="%{#parameters.clientfor}"/>
<s:param name="cName" value="%{#parameters.cName}"/>
<s:param name="offering" value="%{#parameters.offering}"/>
<s:param name="toContactPerson" value="%{#parameters.toContactPerson}"/>
<s:param name="ccContactPerson" value="%{#parameters.ccContactPerson}"/>
<s:param name="subCategory" value="%{#parameters.subCategoryName}"/>
<s:param name="forDept" value="%{#parameters.dept}"/>
<s:param name="category" value="%{#parameters.categoryName}"/>
<s:param name="fbtype" value="%{#parameters.fbType}"/>
</s:url>
<div id="escalationMapResult" align="center">
<div align="center">
<sjg:grid 
		  id="employeeGrid"
          caption="Employee Detail"
          href="%{emp4escalation_URL}"
          gridModel="empData4Escalation"
          navigator="true"
          navigatorAdd="false"
          navigatorView="true"
          navigatorDelete="false"
          navigatorEdit="false"
          navigatorSearch="false"
          rowList="10,25,50"
          rownumbers="-1"
          viewrecords="true"       
          pager="true"
          pagerButtons="true"
          pagerInput="true"   
          multiselect="false"  
          loadingText="Requesting Data..."  
          rowNum="10"
          autowidth="true"
          navigatorSearchOptions="{sopt:['eq','ne','cn','bw','ne','ew']}"
          navigatorEditOptions="{reloadAfterSubmit:true}"
          shrinkToFit="false"
          >
		 		   <sjg:gridColumn 
		      						name="id"
		      						index="id"
		      						title="ID"
		      						align="center"
		      						width="75"
		      						hidden="true"
		 							/>
		       
		 		 <sjg:gridColumn 
		      						name="empName"
		      						index="empName"
		      						title="Employee Name"
		      						width="200"
		      						align="left"
		 							/>	
		 		 <sjg:gridColumn 
		      						name="mobOne"
		      						index="mobOne"
		      						title="Mobile No"
		      						width="200"
		      						align="center"
		 							/>	
		 		 <sjg:gridColumn 
		      						name="emailIdOne"
		      						index="emailIdOne"
		      						title="Email ID"
		      						width="350"
		      						align="left"
		 							/>				
		  </sjg:grid>
		  </div>
		  <br>
		  <div id="bt" style="display: block">
  		<sj:submit id="saveButton" value="Save" onclick="saveClick()" button="true"  />
  	</div>
   </div>
</body>
</html>