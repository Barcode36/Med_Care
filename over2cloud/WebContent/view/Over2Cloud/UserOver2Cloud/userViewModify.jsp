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

<script type="text/javascript">
function test()
{
	var userId=$("#userID").val();
	$("#editDivId").html("<br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
 		type :"post",
 		url : "/over2cloud/view/Over2Cloud/hr/beforeModifyUserData.action?id="+userId,
	    data:"userType="+userType,
 		success : function(data) 
	    {
			$("#editDivId").html(data);
		},
	    error: function()
	    {
	        alert("error");
	    }
 	});

}
test();
</script>
</head>
<body >
<s:form id="formoneEdit" name="formoneEdit" namespace="/view/Over2Cloud/hr" action="modifyUserData" theme="css_xhtml"  method="post" enctype="multipart/form-data" >
			<s:hidden id="id" name="id" value="%{id}"/>
			<s:hidden id="mobileNo" name="mobileNo" value="%{mobileNo}"/>
			<s:hidden id="userID" name="userId" value="%{userID}"/>
		
			     <div class="newColumn"> <span id="mandatoryFields" class="pIds" style="display: none; ">userType#userType#D,</span>
			  	<div class="leftColumn">User Type:</div>
								<div class="rightColumn"><span class="needed"></span>
								<s:select 
										id="userType" 
										name="userType"
										list="userMap"
										cssClass="select"
										cssStyle="width:89%;">
									</s:select></div>
	 		       </div>
	 		       
	 		        <div class="newColumn"> <span id="mandatoryFields" class="pIds" style="display: none; ">userType#userType#D,</span>
			  	<div class="leftColumn">Status:</div>
								<div class="rightColumn"><span class="needed"></span>
								 <s:select  
						    		id					=		"active"
						    		name				=		"active"
						    		list				=		"#{'1':'Active','0':'Inactive'}"
							     	cssClass="select"
							       cssStyle             =      "width:89%;"
						      	 >
						      	 </s:select>
									</div>
	 		       </div>    
	 		  	
						<div class="fields">
						<ul>
						<li class="submit" style="background: none;">
							<div class="type-button">
			                <sj:submit 
			                        targets="orglevel1Div12" 
			                        clearForm="true"
			                        value="  Modify User  " 
			                        effect="highlight"
			                        effectOptions="{ color : '#222222'}"
			                        effectDuration="5000"
			                        button="true"
			                        onCompleteTopics="completeData"
			                        cssClass="submit"
			                        indicator="indicator2222"
			                        cssStyle="margin-left:538px;"
			                        />
			               </div>
						</ul>
						</div>
					<br>
					<center><img id="indicator2222" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/></center>
					<br>
					<sj:div id="foldeffect"  effect="fold">
	                    <sj:div id="orglevel1Div12"></sj:div>
	               </sj:div>     
	 		       
	 		<div id="editDivId"></div>       
			
</s:form>
</body>
</html>