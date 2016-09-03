<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>

<%
String status=request.getParameter("status");
String userRights = session.getAttribute("userRights") == null ? "" : session.getAttribute("userRights").toString();
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script src="<s:url value="/js/PullEmailReport.js"/>"></script>
<script type="text/javascript">

function viewPage()
{

	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/Text2Mail/beforePullSendEmailReport.action",
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
	
	function finishOffering(emailId,emailText,me)
	{
		
		if(me.checked==true)
	    { 
			
			
			alert(emailId);
		    alert(emailText);
		    $("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		    $("#data_part").load(encodeURI("view/Over2Cloud/Text2Mail/emailResend.action?emailId="+emailId+"&emailText="+emailText));
	    }
	}
</script>
<title>Insert title here</title>
</head>
<body><br><br><br><br><br>
<table width="98%" cellspacing="0" cellpadding="3" align="center">
<tr><td bgcolor="#0e0e0e" class="tabular_head" valign="middle" colspan="100" style="color: white;">Pull Send Email Report</td></tr>
	<tr><td bgcolor="#252525" style=" border-bottom:1px solid #e7e9e9; color:#ffffff;" valign="top" class="tabular_cont">
		<table cellspacing="0" cellpadding="0" width="100%"><tbody>
			<tr>
				<td valign="middle" width="2.5%">&nbsp;</td>
				<td valign="middle" width="20%">Email Id</td>
				<td valign="middle" width="20%">Email Text</td>
				<td valign="middle" width="20%">Send Date</td>
				<td valign="middle" width="20%">Send Time</td>
				<td valign="middle" width="20%">Email Sending Flag</td>
				<td valign="middle" width="20%">Email Resend </td>
				
			</tr>
		</tbody></table>
	</td>
	</tr>
<s:iterator value="parentTakeaction" id="parentTakeaction" status="counter">  
<s:if test="#counter.count%2 != 0">
	<tr><td bgcolor="#ffffff" style=" border-bottom:1px solid #e7e9e9; color:#181818;" valign="top" class="tabular_cont">
		<table cellspacing="0" cellpadding="0" width="100%"><tbody>
			<tr>
				<td valign="middle" width="2.5%" bgcolor="#252525" style=" color:#ffffff; text-align:center;"><s:property value="#counter.count" /></td>
				 <s:iterator value="childList">
				<td valign="middle" width="20%"><s:property value="name"/></td>
				</s:iterator>
				<td style="text-align:center;  padding:4px; border-bottom:1px solid #e7e9e9;">
									<input type="checkbox" onclick="finishOffering('${childList[0].name}','${childList[1].name}',this)">	
								
							</td>
			</tr>
			</tbody>
		</table>
	</td></tr>
</s:if>
<s:else>
<tr><td bgcolor="#e2e4e4" style=" border-bottom:1px solid #e7e9e9; color:#181818;" valign="top" class="tabular_cont">
		<table cellspacing="0" cellpadding="0" width="100%"><tbody>
			<tr>
				<td valign="middle" width="2.5%" bgcolor="#252525" style=" color:#ffffff; text-align:center;"><s:property value="#counter.count" /></td>
				<s:iterator value="childList">
				<td valign="middle" width="20%"><s:property value="name"/></td>
				</s:iterator>
				<td style="text-align:center;  padding:4px; border-bottom:1px solid #e7e9e9;">
									<input type="checkbox" onclick="finishOffering('${childList[0].name}','${childList[1].name}',this)">
								
							</td>
			</tr></tbody>
		</table>
	</td></tr>
</s:else>
</s:iterator> 
</table>
<table width="98%" cellspacing="0" cellpadding="3" align="center">
<tr><td bgcolor="#0e0e0e" class="tabular_head" valign="middle" colspan="8" style="color: black;">Lead History</td></tr>
</table><br>
<sj:a 
						button="true" href="#"
						onclick="viewPage();"
						>
						
						Back
					</sj:a>
					<sj:a 
						button="true" href="#"
						onclick="getCurrentColumn('downloadExcel','pullEmailReport','downloadColumnAllModDialog','downloadAllModColumnDiv');"
						>
						
						Download
					</sj:a>
					<sj:dialog id="downloadColumnAllModDialog"  buttons="{'Cancel':function() { },}" modal="true" effect="slide" autoOpen="false" width="300" height="500" title="Pull Email Report" loadingText="Please Wait" overlayColor="#fff" overlayOpacity="1.0" position="['left','top']" >
<div id="downloadAllModColumnDiv"></div>
</sj:dialog>
</body>
</html>