<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<html>
<head>
<s:url id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}" />
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<script>
function updateProfile()
{
	var empName=$('#empName').val();
	var comName=$('#comName').val();
	var mobOne=$('#mobOne').val();
	var emailIdOne=$('#emailIdOne').val();
	var empId=$('#empId').val();
	var id=$('#id').val();

	$.ajax( {
		type :"post",
		url :"updatePersonalInfo.action?empName="+empName+"&comName="+comName+"&mobOne="+mobOne+"&emailIdOne="+emailIdOne+"&empId="+empId+"&id="+id, 
		success : function(subdeptdata) {
		},
		error : function() {
		}
	});
}

</script>
  <script type="text/javascript">
$.subscribe('showResult', function(event,data)
{
        $("#confirmEscalationDialog").load("<%=request.getContextPath()%>/pic.action");
    	$("#confirmEscalationDialog").dialog('open');
    	$("#confirmEscalationDialog").dialog({title:'Profile Image Upload',height:'130',width:'320'});
    	//setTimeout(function(){ $("#confirmEscalationDialog").dialog('close'); }, 10000);
});
function showEditDetails()
{
	var conP = "<%=request.getContextPath()%>";
	$("#empDiv").html("<br><br><center><img src=images/ajax-loaderNew.gif></center>");
    $.ajax({
        type : "post",
        url : conP+"/view/Over2Cloud/hr/beforeupdatePersonalInfo.action?modifyFlag=0&deleteFlag=0",
        success : function(subdeptdata) 
        {
        	$("#resetDiv").hide();
      	 $("#"+"empDiv").html(subdeptdata);
      },
       error: function() {
            alert("error");
        }
     });
}
function resetPassword()
{
	    $("#resetDialog").dialog({title: 'Change Password'});
	    $("#resetDialog").dialog('open');
		$("#resetDialog").html("<br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/commonModules/beforeChangePwdFromAccount.action",
		    success : function(subdeptdata) {
	       $("#"+"resetDialog").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });

}
</script>
</head>
<body>
<sj:dialog 
loadingText="Please wait..."  
id="resetDialog" 
autoOpen="false"  
closeOnEscape="true" 
modal="true" 
width="1000" 
height="300" 
showEffect="slide" 
hideEffect="explode" >
</sj:dialog>
	<sj:dialog loadingText="Please wait..."  
	id="confirmEscalationDialog" 
	autoOpen="false"  
	closeOnEscape="true" 
	modal="true" width="1000" 
	cssStyle="overflow:hidden;" 
	height="300" showEffect="slide" hideEffect="explode" >
	                           <sj:div id="foldeffectExcel"  effect="fold"><div id="saveExcel"></div></sj:div><div id="confirmingEscalation"></div>
	</sj:dialog>
<div class="list-icon">
	 <div class="head">My Account</div>
</div>      
<div class="clear"></div>
<div style="width: 100%; center; padding-top: 10px;" align="center">
	<div class="border" style="height: 50%" align="center">
		
		<table width="100%" border="0" cellspacing="0" cellpadding="0" class="mt15">
			<tbody>
          			<tr>
          				<td width="300px" height="300px" valign="top" align="center">
            					<a class="userphotocontainer" href="javascript:;">
									<s:if test="%{profilePicName == null || profilePicName == ''}">
											<img src="images/profile-pic.png" width="150px" height="150px"/>
									</s:if>
									<s:else>
										<img src="<s:property value="%{profilePicName}" />" width="200px" height="200px"/>
									</s:else>
								</a>
								<br>
								<br>
									<sj:submit id="profileImage" button="true" href="#" value="Change Profile Image" onClickTopics="showResult"></sj:submit>
						</td>
						<td align="right">
							<div id="empDiv">
							<table width="80%" height="100%">
								<tr>
									<td>
											<table width="100%" cellspacing="0" cellpadding="0">
 											<tbody>
 												<tr>
  													<td class="cell">Name</td> 
   													<td class="cell"><strong><s:property value="%{empName}" ></s:property></strong></td>
    											</tr>
    											<tr>
    												<td class="cell">Communication Name</td>
    												<td class="cell">
     													<strong><s:property value="%{comName}" /></strong>
     												</td>
     											</tr>
    											<tr>
    												<td class="cell">Mobile No
    												</td>
													<td class="cell">
     													<strong><s:property value="%{mobOne}" /></strong>
     												</td>
     											</tr>
     											<tr>
     												<td class="cell">Email ID
     											</td> 
      											<td class="cell"><strong><s:property value="%{emailIdOne}" /></strong></td>
       											</tr>
       											<tr>
       												<td class="cell">Employee ID</td> 
       												<td class="cell"><strong><s:property value="%{empId}" /></strong></td>
       											</tr>
       											<tr>
       												<td class="cell">User Name</td> 
       												<td class="cell"><strong><s:property value="%{userName}" /></strong></td>
       											</tr>
											</tbody>
										</table>
									</td>
								</tr>
								<tr>
									<td>
										<sj:a 
					                     value="Edit" 
					                     button="true"
					                     cssStyle="margin-left: 86px;margin-top: 11px;"
					                     onclick="showEditDetails();"
			           					>
			           					Edit
			           					</sj:a>
									</td>
									<td>
									</td>
								</tr>
									
							</table>
							</div>
						</td>
					</tr>
			</tbody>
		</table>
		<div id="resetDiv" style="display: block;">
				<sj:a 
                    value="Reset" 
                    button="true"
                    cssStyle="margin-left: 180px;margin-top: -38px;"
                    onclick="resetPassword();"
        					>
        					Change Password
   			   </sj:a>
   	   </div>
   	</div>
</div>
</body>
 </html>
