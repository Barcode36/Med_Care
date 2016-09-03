<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<SCRIPT type="text/javascript">
/*
function addEmployee()
	{
	var sel_id;
	var fromDept_id=$("#fromDept_id1").val();
	var fromDept_id=$("#fromDept_id1").val();
	var forDept_id=$("#forDeptId").val();
	var data4=$("#dataFor").val();
	
	//var module=$("#m_name").val();
	sel_id=$("#gridedittable").jqGrid('getGridParam','selarrrow');
	if(sel_id=="")
	{
	     alert("Please select atleast one check box...");        
	     return false;
	}
	else
	{
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/Compliance/compl_contacts_pages/complContactAction.action?empName="+sel_id+"&fromDept="+fromDept_id+"&forDept="+forDept_id+"&moduleName="+data4,
		    success : function(data) 
		    {
				$("#resultDiv").html(data);
				$("#viewempDiv").html("<br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
				$.ajax({
				type :"post",
				url :"/over2cloud/view/Over2Cloud/Compliance/compl_contacts_pages/beforeEmpData.action?fromDept="+fromDept_id+"&forDeptId="+forDept_id+"&moduleName="+data4,
				success : function(empData)
				{
					$("#viewempDiv").html(empData);
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
*/

function addEmployee()
{
var sel_id;
var fromDept_id=$("#fromDept_id1").val();
var location=$("#location").val();
var forDept_id=$("#forDeptId").val();
var data4=$("#m_Name").val();
sel_id=$("#gridedittable").jqGrid('getGridParam','selarrrow');
var forSubDept_id=$("#forDeptId").val();
if(sel_id=="")
{
     alert("Please select atleast one check box...");        
     return false;
}
if(data4=="-1"){
    
     alert("Please select Module Name from Dropdown...");        
     return false;
}
else
{
    $.ajax({
        type : "post",
        url : "view/Over2Cloud/Compliance/compl_contacts_pages/complContactAction.action?empName="+sel_id+"&fromDept="+fromDept_id+"&forDept="+forDept_id+"&moduleName="+data4+"&forSubDept_id="+forSubDept_id+"&location="+location,
        success : function(data) 
        {
            $("#resultDiv").html(data);
            $("#viewempDiv").html("<br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
            $.ajax({
            type :"post",
            url :"/over2cloud/view/Over2Cloud/Compliance/compl_contacts_pages/beforeEmpData.action?fromDept="+fromDept_id+"&forDeptId="+forDept_id+"&moduleName="+data4+"&forSubDept_id="+forSubDept_id,
            success : function(empData)
            {
                $("#viewempDiv").html(empData);
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
function viewContactMaster()
{
	var data4=$("#dataFor").val();
 $("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/Compliance/compl_contacts_pages/beforeComplContactView.action?modifyFlag=0&deleteFlag=1&moduleName="+data4,
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
</SCRIPT>
</head>
<body>


<s:hidden id="dataFor" value="%{moduleName}" />
<s:url id="veiwEmpDetail" action="veiwEmpDetail" escapeAmp="false">
	<s:param name="fromDept" value="{fromDept}"></s:param>
	<s:param name="forDeptId" value="{forDeptId}"></s:param>
	<s:param name="moduleName" value="{moduleName}"></s:param>
</s:url>
<center>
<sjg:grid 
		  id="gridedittable"
          href="%{veiwEmpDetail}"
          gridModel="masterViewList"
          navigator="true"
          navigatorAdd="false"
          navigatorView="true"
          navigatorDelete="false"
          navigatorEdit="false"
          navigatorSearch="true"
          rowList="15,20,25"
          rowNum="15"
          viewrecords="true"       
          pager="true"
          pagerButtons="true"
          pagerInput="true"   
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
				width="403"
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