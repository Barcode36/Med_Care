<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>

<html>
<head>
<script type="text/javascript"
	src="<s:url value="/js/referral/referralValidation.js"/>"></script>
<STYLE type="text/css">
/* td.tdAlign {
	//padding: 5px;
}
 */
tr.color {
	background-color: #E6E6E6;
}


</STYLE>
<SCRIPT type="text/javascript">
	$.subscribe('level12', function(event, data)
	{
		$('#takeActionGrid').dialog('close');
		//$("#completionResult1").dialog('open');
		setTimeout(function()
		{
			$("#result1").fadeIn();
		}, 10);
		setTimeout(function()
		{
			$("#result1").fadeOut();
			cancelButton();
		}, 1000);
		//resetTaskType('taskTypeForm');
	});
	function cancelButton()
	{
		//$('#completionResult1').dialog('close');
		onSearchData();
	}
	function viewMainPage()
	{
		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax(
		{
			type : "post",
			url : "view/Over2Cloud/Referral/beforeReferralViewHeader.action",
			success : function(data)
			{
				$("#" + "data_part").html(data);
			},
			error : function()
			{
				alert("error");
			}
		});
	}
	function resetTaskType(formId)
	{
		$('#' + formId).trigger("reset");
	}
	
	function changeView(value)
	{
		if(value=="2")
		{
			$('#mobId').show();
		}	
		else
		{
			$('#mobId').hide();
		}	
	}

	
</script>
</head>
<div style="float: left; padding: 5px 0%; width: 100%;">

	<div class="border">

		<sj:dialog id="completionResult1" showEffect="slide"
			hideEffect="explode" autoOpen="false" 
			cssStyle="overflow:hidden;text-align:center;margin-top:10px;"
			modal="true">
			<div id="result1"></div>
		</sj:dialog>
		<s:form id="formone1" name="formone1"
			namespace="/view/Over2Cloud/Critical" action="esacateActionOnCritical"
			method="post" enctype="multipart/form-data" theme="simple">
			<div
				style="float: center; border-color: black; background-color: rgb(255, 99, 71); color: white; width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
				<div id="errZone12" style="float: center; margin-left: 7px"
					align="center"></div>
			</div>
			<s:hidden name="rid" id="rid" value="%{id}"></s:hidden>
			<s:hidden name="level" id="level" value="%{level}"></s:hidden>
			<s:hidden name="uhid" id="uhid" value="%{uhid}"></s:hidden>
			
			<table cellpadding="10px" rules="rows" width="99%" align="center">

				<tr>
					<td class="tdAlign"><b>Action:</b></td>
					<td class="tdAlign"><s:radio label="actionVia" name="actionVia" list="#{'1':'By Call','2':'By SMS'}" value="1" onchange="changeView(this.value)"/></td>
					<td class="tdAlign"><b>Escalate To:</b><span class="escIds" style="display: none;">escalate_to#Escalate To#T#sc,</span></td>
					<td class="tdAlign"><span class="needed"></span><s:textfield id="escalate_to" name="escalate_to" cssClass="textField" theme="simple" placeHolder="Enter Name"/></td>
				</tr>
				<tr style="margin-top: 10px;display: none;" id="mobId" >
					<td class="tdAlign"><b>Mobile No:</b><span class="escIds" style="display: none;">escalate_to_mob#Mobile No#T#m,</span></td>
					<td class="tdAlign"><span class="needed"></span><s:textfield id="escalate_to_mob" name="escalate_to_mob" cssClass="textField" theme="simple" maxlength="10" placeHolder="Enter Mobile No"/></td>
				</tr>
			</table>	
			<center>
				<img id="indicator2" src="<s:url value="/images/indicator.gif"/>"
					alt="Loading..." style="display: none" />
			</center>
			<div class="clear"></div>
			<center>
				<div class="type-button" style="margin-top: 10px;">
					<sj:submit targets="result12" clearForm="true" value="Escalate"
						effect="highlight" effectOptions="{ color : '#FFFFFF'}"
						effectDuration="100" button="true" cssClass="submit"
						indicator="indicator2" onCompleteTopics="level12"
						onBeforeTopics="validateEsc" />
				</div>
			</center>
		</s:form>
	</div>
</div>
</html>


