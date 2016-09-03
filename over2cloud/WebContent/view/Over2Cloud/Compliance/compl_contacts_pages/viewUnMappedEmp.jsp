<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript" src="<s:url value="/js/compliance/ComplianceMenuCtrl.js"/>"></script>
<title>Insert title here</title>

<SCRIPT type="text/javascript">
function mappedContact()
	{
	var sel_id;
	var empId=$("#empId").val();
	var dataFor=$("#dataFor").val();
	var mappedLevel=$("#mappedLvl").val();
	var mapping_sharing=$("#mapping_sharing").val();
	
	sel_id=$("#UnMappedEmp123").jqGrid('getGridParam','selarrrow');
	if(sel_id=="")
	{
	     alert("Please select atleast a ckeck box...");        
	     return false;
	}
	else
	{
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/Compliance/compl_contacts_pages/mapUnMappedContact.action?contactId="+sel_id+"&empName="+empId+"&mapping_sharing="+mapping_sharing,
		    success : function(data) 
		    {
				$("#resultDiv").html(data);
				$("#unMappedTarget").html("<br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
				$.ajax({
				type :"post",
				url : "/over2cloud/view/Over2Cloud/Compliance/compl_contacts_pages/getUnMappedEmp.action?empName="+empId+"&moduleName="+dataFor+"&mappedLevel="+mappedLevel+"&mapping_sharing="+mapping_sharing,
				success : function(empData)
				{
					 $("#unMappedTarget").html(empData);
					 setTimeout(function(){ $("#resultDiv").fadeIn(); }, 10);
					 setTimeout(function(){ $("#resultDiv").fadeOut(); }, 900);
			    },
			    error : function () 
			    {
					alert("Somthing is wrong to get Employee Data");
				}
				});
			},
		    error: function()
		    {
		        alert("error");
		    }
		 });
	}
}

</SCRIPT>

</head>
<body>
<s:hidden id="empId" value="%{empName}"/>
<s:hidden id="dataFor" value="%{moduleName}"/>
<s:hidden id="mappedLvl" value="%{mappedLevel}"/>
<s:hidden id="mapping_sharing" value="%{mapping_sharing}"/>

<s:url id="veiwUnMappedEmpDetail123" action="veiwUnMappedEmpDetail" escapeAmp="false">
	<s:param name="empName" value="%{empName}"></s:param>
	<s:param name="moduleName" value="%{moduleName}"></s:param>
	<s:param name="mappedLevel" value="%{mappedLevel}"></s:param>
	<s:param name="mapping_sharing" value="%{mapping_sharing}"></s:param>
</s:url>
<center>
<sjg:grid 
		  id="UnMappedEmp123"
          href="%{veiwUnMappedEmpDetail123}"
          gridModel="masterViewList1"
          navigator="true"
          navigatorAdd="false"
          navigatorView="true"
          navigatorDelete="false"
          navigatorEdit="false"
          navigatorSearch="true"
          rowList="25,50,100"
          viewrecords="true"       
          pager="true"
          pagerButtons="true"
          pagerInput="false"   
          multiselect="true"  
          loadingText="Requesting Data..."  
          navigatorEditOptions="{height:230,width:400}"
          navigatorSearchOptions="{sopt:['eq','cn']}"
          navigatorEditOptions="{reloadAfterSubmit:true}"
          shrinkToFit="false"
          autowidth="true"
          loadonce="true"
          >
		<s:iterator value="masterViewProp" id="masterViewProp" > 
		<s:if test="colomnName=='id'">
			<sjg:gridColumn 
				name="%{colomnName}"
				index="%{colomnName}"
				title="%{headingName}"
				width="200"
				align="%{align}"
				editable="%{editable}"
				formatter="%{formatter}"
				search="%{search}"
				hidden="%{hideOrShow}"
				key="true"
			/>
		</s:if>
		<s:else>
			<sjg:gridColumn 
				name="%{colomnName}"
				index="%{colomnName}"
				title="%{headingName}"
				width="293"
				align="%{align}"
				editable="%{editable}"
				formatter="%{formatter}"
				search="%{search}"
				hidden="%{hideOrShow}"
			/>
		</s:else> 
		
		</s:iterator>  
</sjg:grid>
</center>

</body>
</html>