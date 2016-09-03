<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<link type="text/css" href="<s:url value="/css/style.css"/>" rel="stylesheet" />
<script type="text/javascript" src="<s:url value="/js/compliance/compliance.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/helpdesk/common.js"/>"></script>
<meta   http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<SCRIPT type="text/javascript">

function getDeptBySubGroup(dropDownId,value)
{
$.ajax({
   type : "post",
   url : "/over2cloud/view/Over2Cloud/Compliance/compl_contacts_pages/getDeptBySubGroup.action",
   data : "selectedId="+value,
   success : function(data) 
   {
$('#'+dropDownId+' option').remove();
$('#'+dropDownId).append($("<option></option>").attr('value',-1).text('Department'));
$(data).each(function(index)
{
  $('#'+dropDownId).append($("<option></option>").attr("value",this.id).text(this.name));
});
   },
   error: function() 
   {
            alert("error");
        }
});
}



 $.subscribe('makeEffect', function(event,data)
  {
setTimeout(function(){ $("#complTarget").fadeIn(); }, 10);
setTimeout(function(){ $("#complTarget").fadeOut(); }, 400);
  });
 function viewContactMaster()
{
$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
$.ajax({
   type : "post",
   url : "/over2cloud/view/Over2Cloud/Compliance/compl_contacts_pages/beforeComplContactView.action?modifyFlag=0&deleteFlag=1",
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
 
 function getSearchModData(forDeptId)
 {

	 var moduleName=$("#m_Name").val();
	 $("#location").hide();
	 if(moduleName=="CRF")
	 {
		 $("#location").show();
		 getAllLocationList('location');
	}
	 else if(moduleName=="CPS")
	 {
		 $("#location").show();
		 getAllLocationList('location');
	}

	 else if(moduleName=="Pharmacy")
	 {
		 $("#location").show();
		 getAllLocationList('location');
	}
	 else if(moduleName=="LPS")
	 {
		 $("#location").show();
		 getAllLocationList('location');
	}
	 else if(moduleName=="LabOrd")
	 {
		 $("#location").show();
		 getAllLocationList('location');
	}
	 
      if(moduleName=="HDM"){
     		$("#forDeptId").show();
     		 
         var subType=$("#subType").val();
        $.ajax({
            type : "post",
            url : "view/Over2Cloud/Compliance/compl_contacts_pages/getDeptSubdeptList.action",
            data: "moduleName="+moduleName,
            async:false,
            success : function(data) 
            {
                  $('#'+forDeptId+' option').remove();
                $('#'+forDeptId).append($("<option></option>").attr('value',68).text('For All Sub-Department'));
                $(data).each(function(index)
                {
                   $('#'+forDeptId).append($("<option></option>").attr("value",this.id).text(this.name));
                });
            },
            error: function() 
            {
                alert("error");
            }
         });
        }
 }
 function getAllLocationList(Id)
 {
	 var moduleName=$("#m_Name").val();
	 var subDept=$('#forDeptId option:selected').text();
	       $.ajax({
            type : "post",
            url : "view/Over2Cloud/Compliance/compl_contacts_pages/getAllLocationList.action",
            data: "moduleName="+moduleName+"&forSubDept_id="+subDept,
            async:false,
            success : function(data) 
            {
                  $('#'+Id+' option').remove();
                $('#'+Id).append($("<option></option>").attr('value','All').text('For All Location'));
                $(data).each(function(index)
                {
                   $('#'+Id).append($("<option></option>").attr("value",this.id).text(this.name));
                });
            },
            error: function() 
            {
                alert("error");
            }
         });
        }
  
 function employeeDetails()
{
    // $("#forDeptId").hide();
        var data4=$("#moduleName").val();
        var fromDept=$("#fromDept_id").val();
        var destAjaxDiv="viewempDiv";
        var forDeptId=$("#forDeptId").val();
        
$("#viewempDiv").html("<br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
$.ajax({
type :"post",
url :"/over2cloud/view/Over2Cloud/Compliance/compl_contacts_pages/beforeEmpData.action?destination="+ destAjaxDiv+"&fromDept="+fromDept+"&forDeptId="+forDeptId+"&moduleName="+data4,
success : function(empData)
{
$("#viewempDiv").html(empData);
   },
   error : function () 
   {
alert("Somthing is wrong to get Employee Data");
}
});
}

 getSearchModData('forDeptId');
 
 employeeDetails();
</SCRIPT>
</head>
<div class="clear"></div>
<div class="middle-content">
<div class="list-icon">
<div class="head">
 Add Contact</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div>
<div class="head">For <s:property value="%{userDeptName}"/> Department </div> 
</div>
<div class="clear"></div>
<center><div id="resultDiv"></div></center>
  <s:hidden id="moduleName" name="moduleName" value="%{moduleName}"/>
  <div class="listviewBorder" style="margin-top: 10px;width: 97%;margin-left: 20px;" align="center">
   <div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
<div class="pwie">
<table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%">
<tbody>
<tr>
 <td>
 <table class="floatL" border="0" cellpadding="0" cellspacing="0">
   <tbody><tr>
    
                                    <td> <b>Add For</b>
                                      <s:select 
                                                                       id="m_Name"
                                                                       name="moduleName"
                                                                        list="#{'HDM':'Helpdesk','ORD':'Machine Order','CRF':'Cross Referral','CPS':'Corporate Patient Services','CRC':'Critical Result','Pharmacy':'Pharmacy','LPS':'LongTimePatientStay','LabOrd':'Lab Order'}"
                                                                       cssClass="button"
                                                                       cssClass="button"
                                                                          cssStyle            =        "margin-left: 3px;width: 143px;height: 30px;"
                                                                       theme="simple"
                                                                       onchange="getSearchModData('forDeptId');"

/> 
                                </td>   
   <td>  
    
          <s:iterator value="complDDBox" status="status">
    
         <s:if test="%{mandatory}">
    <span id="complSpan" class="pIds" style="display: none; "><s:property value="%{field_value}"/>1#<s:property value="%{field_name}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
         </s:if>
                  <s:if test="%{field_value=='group'}">
          
           
           <s:select 
                                                                       id                    =        "forDeptId" 
                                                                      name                =        "forSubDept_id"
                                                                      list                =        "{'No Data'}"
                                                                      headerKey            =        "-1"
                                                                      headerValue            =        "For All Sub-Department" 
                                                                      cssClass            =        "button"
                                                                    cssStyle    =    "margin-left: 3px;width: 143px;margin-top: -30px;height: 30px;"
                                                                      onchange="getAllLocationList('location');"
                                                                  >
                                                       </s:select>
                                                        <s:select 
                                                                       id                    =        "location" 
                                                                      name                =        "location"
                                                                      list                =        "{'No Data'}"
                                                                      headerKey            =        "All"
                                                                      headerValue            =        "For All Location" 
                                                                      cssClass            =        "button"
                                                                    cssStyle    =    "margin-left: 150px;width: 143px;margin-top: -30px;height: 30px;"
                                                                  >
                                                       </s:select>
                                                  
<%-- <s:select 
      id="moduleN"
      name="moduleName"
      list="moduleList"
      headerKey="-1"
      headerValue="Select Module Name"
      cssClass="button"
      cssClass="button"
          cssStyle="margin-top: -29px;margin-left:3px"    
  theme="simple"
  
/>  --%>&nbsp;<b>From</b>
          <s:select  
                                 id    =    "group"
                                 list    =    "groupMap"
                                 headerKey    =    "-1"
                                 headerValue    =    "Group" 
                                 cssClass    =    "button"
                                 cssStyle    =    "margin-left: 3px;width: 143px;height: 30px;"
                                 theme =    "simple"
                                 onchange    =    "getDeptBySubGroup('fromDept_id1',this.value);"
                             >
           </s:select>
          </s:if>
          <!--<s:elseif test="%{field_value=='subgroup'}">
          <s:select  
                                 id    =    "subgroup"
                                 list    =    "{'No Data'}"
                                 headerKey    =    "-1"
                                 headerValue    =    "Sub-Group" 
                                 cssClass    =    "button"
                                 cssStyle    =    "margin-left: 3px;width: 143px;height: 30px;"
                                 theme =    "simple"
                                 
                             >
           </s:select>
          </s:elseif>
          --><s:elseif test="%{field_value=='fromDept_id'}">
          <s:select  
                                 id    =    "%{field_value}1"
                                 name    =    "fromDept_id"
                                 list    =    "{'No Data'}"
                                 headerKey    =    "-1"
                                 headerValue    =    "Department" 
                                 cssClass    =    "button"
                                 cssStyle    =    "margin-left: 3px;width: 143px;height: 30px;"
                                 theme =    "simple"
                                 onchange    =    "getEmpData(this.value,'EmpDiv1','forDeptId','module');"
                             >
           </s:select>
           &nbsp;
          </s:elseif>
             </s:iterator>
     </td>
    
     </tr></tbody>
 </table>
 <sj:a id="add" cssStyle="margin-left: 1px;height: 28px;" buttonIcon="" cssClass="button" button="true" onclick="addEmployee()"> Save </sj:a>
<sj:a id="cancel" cssStyle="margin-left: 1px;height: 28px;" buttonIcon="" cssClass="button" button="true"  onclick="viewContactMaster()">Back</sj:a>
 </td>
 <td class="alignright printTitle">
 
 </td>
</tr>
</tbody>
</table>
</div>
</div>
<div style="overflow: scroll; height: 430px;">
 <div id="viewempDiv"></div>
 </div>
</div>
  
</div>
</html>