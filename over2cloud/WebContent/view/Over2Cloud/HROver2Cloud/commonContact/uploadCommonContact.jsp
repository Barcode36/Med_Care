<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<SCRIPT type="text/javascript" src="js/commanValidation/excelvalidation.js"></SCRIPT>
<SCRIPT type="text/javascript" src="js/feedback/feedbackForm.js"></SCRIPT>
<SCRIPT type="text/javascript" src="js/common/commonContact.js"></SCRIPT>
<SCRIPT type="text/javascript" src="js/designation/designation.js"></SCRIPT>
<link href="css/style.css" rel="stylesheet" type="text/css" />
<SCRIPT type="text/javascript">
$.subscribe('level1', function(event,data)
	       {
	         setTimeout(function(){ $("#orglevel1Div").fadeIn(); }, 10);
	         setTimeout(function(){ $("#orglevel1Div").fadeOut(); }, 4000);
	       });


function viewGroup() {
	var conP = "<%=request.getContextPath()%>";
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
    $.ajax({
        type : "post",
        url : conP+"/view/Over2Cloud/hr/beforeCommonContactView.action",
        success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
    },
       error: function() {
            alert("error");
        }
     });
}

function viewCommonContact()
{
$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    //url : "/over2cloud/view/Over2Cloud/commonModules/makeHistory.action",
	    url : "view/Over2Cloud/hr/beforeCommonContactViewHeader.action",
	    success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });

}

function getDDValue(textValue)
{
	
	var value=document.getElementById("groupId").options.item(document.getElementById("groupId").selectedIndex).text;
	$("#groupName").val(value);
	$("#groupName4Down").val(value);
	if(value!='Employee')
	{
		$("#displayDivId").show();
	}
	else
	{
		$("#displayDivId").hide();
	}
}

function validatePrimaryExcel()
{

	var group=$("#groupId").val();
	var subGroup=null;
	if(group!='1')
		{
		 subGroup=$("#subGroupId").val();
		}
	
	if(group=='-1')
	{
		   errZone.innerHTML="<div class='user_form_inputError2'>Please Select Contact Type </div>";
           setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
           setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
           $("#groupId").focus();
           $("#groupId").css("background-color","#ff701a");
           event.originalEvent.options.submit = false;
	}
	else if(subGroup!=null && subGroup=='-1')
	{
		errZone.innerHTML="<div class='user_form_inputError2'>Please Select Contact Sub Type </div>";
        setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
        setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
        $("#subGroupId").focus();
        $("#subGroupId").css("background-color","#ff701a");
        event.originalEvent.options.submit = false;
	}
	else
	{
		$("#formmatDownload").submit();
	}
}
</script>
</head>
<body>
<div class="list-icon">
	 <div class="head">Uplaod Contact</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div><div class="head">Add</div> 
</div>
<div class="clear"></div>
     <div style="width: 100%; center; padding-top: 10px;">
       <div class="border" style="height: 50%">
       
   <s:form id="formone" name="formone" namespace="/view/Over2Cloud/hr" action="uploadContact" theme="css_xhtml"  method="post"enctype="multipart/form-data" >
          <center>
			    <div style="float:center; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
                <div id="errZone" style="float:center; margin-left: 7px"></div>        
                </div>
                </center>
				<s:iterator value="contactDD" status="counter">
				<s:if test="%{mandatory}">
                </s:if>
                <s:if test="%{statusFlag}">
				<div class="newColumn">
                <div class="leftColumn"><s:property value="%{value}"/> :</div>
                <div class="rightColumn">
                <s:select 
                       id="regLevel"
                       name="regLevel" 
                       list="officeMap"
                       headerKey="-1"
                       headerValue="Select Mapped Office" 
                       cssClass="select"
                       cssStyle="width:82%"
                       onchange="getGroupNamesForMappedOffice('groupId',this.value);"
                       >
                </s:select>
                </div>
             	</div>
             	</s:if>
             	<s:else>
             	<s:if test="%{key=='groupId'}">
             	<span class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
             	<div class="newColumn">
                <div class="leftColumn"><s:property value="%{value}"/> :</div>
                <div class="rightColumn"><span class="needed"></span>
                <s:select 
                       id="groupId"
                       name="groupId" 
                       list="groupMap"
                       headerKey="-1"
                       headerValue="Select Contact Type" 
                       cssClass="select"
                       cssStyle="width:82%"
                       onchange="getsubGroupByGroup('subGroupId',this.value);getDDValue();"
                       >
                </s:select>
                <s:hidden name="groupName" id="groupName"/>
                </div>
             	</div>
             	</s:if>
             	<s:elseif test="%{key=='deptname'}">
             	<div id="displayDivId" style="display: none">
             	<div class="newColumn">
                <div class="leftColumn"><s:property value="%{value}"/> :</div>
                <div class="rightColumn"><span class="needed"></span>
                <s:select 
                       id="subGroupId"
                       name="subGroupId" 
                       list="{'No Data'}"
                       headerKey="-1"
                       headerValue="Select Contact Sub-Type" 
                       cssClass="select"
                       cssStyle="width:82%"
                       onchange="getContactNamesForUser(this.value,'contactName');"
                       >
                </s:select>
                </div>
             	</div>
             	</div>
             	</s:elseif>
             	</s:else>
				</s:iterator>
				
				 <div class="newColumn">
		   <div class="leftColumn">Download Format:</div>
		   <div class="rightColumn">
		   <sj:a id="formatDownload"  onclick="validatePrimaryExcel();" button="true" cssStyle="margin-top: 0px; margin-top: 3px; height: 20px;"><center>Click Here</center></sj:a>
		   </div>
	   </div>
				
	<div class="clear"></div>	
				 
       <div class="newColumn"> <span class="pIds" style="display: none; ">contactExcel#Excel File#T#sc,</span>
		   <div class="leftColumn">Excel File&nbsp;:</div>
		   <div class="rightColumn"><span class="needed"></span>
	       <s:file name="contactExcel" id="contactExcel"/></div>
	   </div>
	  
	   <div class="clear"></div>
        <div class="clear"></div>
        <div class="fields" align="center">
		   
		   		<sj:submit 
	                        targets="level123" 
	                        clearForm="true"
	                        value="Upload" 
	                        effect="highlight"
	                        effectOptions="{ color : '#222222'}"
	                        effectDuration="5000"
	                        button="true"
	                        onCompleteTopics="level1"
	                        cssClass="submit"
	                        indicator="indicator22"
	                        onBeforeTopics="validate"
	                        />
		   		
		   		<div id=reset style="margin-top: -28px; margin-left: 209px;"> 
	                      <sj:a 
							 button="true" href="#"
							 onclick="reset('formone');"
							 cssClass="submit"
						>
						
						Reset
					</sj:a>
                          <sj:a 
							button="true" href="#"
							onclick="viewCommonContact();"
							cssClass="submit"
						>
						
						Back
					</sj:a>
					</div>
	   </div>
	   
	   <sj:div id="orglevel1Div"  effect="fold">
       <sj:div id="level123">
       </sj:div>
       </sj:div>      
   
 </s:form>
   
   <div id="test" class="rightColumn" style="position: fixed; margin-left: 638px; margin-top:0px;">    
	   <s:form id="formmatDownload" name="formmatDownload" action="downloadFormat" namespace="/view/Over2Cloud/hr" theme="simple"  method="post" enctype="multipart/form-data" >
	   		<s:hidden id="groupName4Down" name="groupName"/>
	   </s:form>
   </div>
   </div>
</div>
</body>
</html>