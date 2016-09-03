<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript">

function forPullReport(downloadType,moduleName,dialogId,dataDiv)
{
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/Text2Mail/forPullColumn.action?downloadType="+downloadType+"&download4="+moduleName,
	    success : function(data) {
		$("#"+dataDiv).html(data);
		$("#"+dialogId).dialog('open');
	},
	   error: function() {
	        alert("error");
	    }
	 });
}
function viewPage()
{

	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/Text2Mail/beforePullReport.action",
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
<title>Insert title here</title>
</head>
sss
<body><br><br><br><br><br>
<table width="98%" cellspacing="0" cellpadding="3" align="center">
<tr><td bgcolor="#0e0e0e" class="tabular_head" valign="middle" colspan="8" style="color: white;">Pull Report</td></tr>
	<tr><td bgcolor="#252525" style=" border-bottom:1px solid #e7e9e9; color:#ffffff;" valign="top" class="tabular_cont">
		<table cellspacing="0" cellpadding="0" width="100%"><tbody>
			<tr>
				<td valign="middle" width="2.5%">&nbsp;</td>
				<td valign="middle" width="20%">Name</td>
				<td valign="middle" width="20%">Mobile No</td>
				<td valign="middle" width="20%">Message</td>
				<td valign="middle" width="20%">Time</td>
				<td valign="middle" width="20%">Date</td>
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
				<td valign="middle" width="16.66%"><s:property value="name"/></td>
				</s:iterator>
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
				<td valign="middle" width="16.66%"><s:property value="name"/></td>
				</s:iterator>
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
						onclick="forPullReport('downloadExcel','pullReport','downloadColumnAllModDialog','downloadAllModColumnDiv');"
						>
						
						Download
					</sj:a>
					<sj:dialog id="downloadColumnAllModDialog"  buttons="{'Cancel':function() { },}" modal="true" effect="slide" autoOpen="false" width="300" height="500" title="Pull Report" loadingText="Please Wait" overlayColor="#fff" overlayOpacity="1.0" position="['left','top']" >
<div id="downloadAllModColumnDiv"></div>
</sj:dialog>
</body>
</html>