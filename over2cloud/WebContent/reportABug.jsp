<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj"  uri="/struts-jquery-tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<link type="text/css" href="<s:url value="/css/style.css"/>" rel="stylesheet" />
<script src="<s:url value="/js/lead/LeadCommon.js"/>"></script>
<style>
	td {
    padding-top: .4em;
}	
</style>
<script type="text/javascript">

$.subscribe('level1', function(event,data)
        {
          setTimeout(function(){ $("#leadgntion").fadeIn(); }, 10);
          setTimeout(function(){ $("#leadgntion").fadeOut(); }, 4000);
          $('select').find('option:first').attr('selected', 'selected');
         
 
});
function setBugrepotingUrl(){

	alert("Hi");
        var userEmialId=$("#userEmialId").val(); 
        var userMobNo=$("#userMobNo").val(); 
	    var modulename=$("#modulename").val(); 
	    var submodule=$("#submodule").val(); 
	    var feedback123=$("#feedback123").val(); 
	 	
	    alert("Going to submit Bug"+modulename);
	 	$.ajax({
		    type : "post",
		    url : "bugRepoting.action",
			data: "userEmialId="+userEmialId+"&modulename="+modulename+"&submodule="+submodule+"&feedback123="+feedback123+"&userMobNo="+userMobNo, 
			success : function(data) {   
	 		$("#leadresult123").html(data);
			$("#img").hide();
		},
		   error: function() {
	            alert("error");
	        }
		 });
		 	 
   }

</script>
</head>
<body>
<div class="list-icon">
	 <div class="head" style="overflow-x:hidden;height: 200px">
	 Report A Bug</div><img src="images/forward.png" style="margin-top: 8px; float: left;"><div class="head">Submit</div> 
</div>
<div class="clear"></div>
		<div style="width: 96%; margin: 1%; border: 1px solid #e4e4e5; -webkit-border-radius: 6px; -moz-border-radius: 6px; border-radius: 6px; -webkit-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); -moz-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); box-shadow: 0 1px 4px rgba(0, 0, 0, .10); padding: 1%;">
			<form action="bugRepoting" name="formOne" >

			<table width="100%" align="center" height="80%" border="0">
				<tr>
					<td width="25%" valign="top">
						Email ID :
					</td>
					<td width="25%" valign="top">
						<s:textfield cssStyle="margin-top: -23px;" value="%{userEmialId}" name="userEmialId"  id="userEmialId"  cssClass="textField" placeholder="Enter Data" readonly="true" />
					</td>
				</tr>
				<tr>
					<td width="25%" valign="top">
						Mobile No. :
					</td>
					<td width="25%" valign="top">
						<s:textfield value="%{userMobNo}" cssStyle="margin-top: -23px;" name="userMobNo"  id="userMobNo" cssClass="textField" maxlength="12" placeholder="Enter 10 digit Mobile No." readonly="true"></s:textfield>
					</td>
				</tr>
				<tr>
					<td width="25%" valign="top"> 
						Module Name :
					</td>
					<td width="25%" valign="top">
						<s:select 
                      id="modulename" 
			          name="modulename" 
			          list="appDetails"
			          headerKey="-1"
			          headerValue="Select Module Name"
			          cssClass="select"
			          cssStyle="width:81%;margin-top: -23px;"
         			 />
					</td>
				</tr>
				<tr>
					<td width="25%" valign="top">
						Sub Module :
					</td>
					<td width="25%" valign="top">
						<s:textfield name="submodule" cssStyle="margin-top: -23px;" id="submodule" cssClass="textField" placeholder="Enter Sub Module Name"></s:textfield>
					</td>
				</tr>
				<tr>
					<td width="25%" valign="top">
						Feedback :
					</td>
					<td  width="75%" valign="top">
						<s:textarea name="feedback123" cssStyle="margin-top: -23px; width: 340px" id="feedback123"   placeholder="Enter Feedback" rows="4" cols="50"  id="feedback123" cssClass="form_menu_inputtext1" />
					</td>
				</tr><!--
				<tr>
					<td width="25%" valign="top">
						Upload :
					</td>
					<td  width="75%" valign="top">
						<s:file name="uploadDoc" id="uploadDoc" placeholder="Select File"  cssClass="textField"/>
					</td>
				</tr>
				--><tr>
					<td colspan="2" width="100%" align="center" valign="bottom">
					<br/>
						<sj:submit 
	                        targets="leadresult123" 
	                        clearForm="true"
	                        value="  Submit  " 
	                        effect="highlight"
	                        effectOptions="{ color : '#222222'}"
	                        effectDuration="5000"
	                        button="true"
	                        onCompleteTopics="level1"
	                        cssClass="submit"
	                        indicator="indicator2"
	                        onBeforeTopics="validate"
	                        onclick="setBugrepotingUrl();"
	                        />
					</td>
				</tr>
				<tr>
					<td colspan="4" width="100%">
						<sj:div id="leadresult123"  effect="fold">
                    		<div id="leadgntion"></div>
               			</sj:div>
					</td>
				</tr>
			</table>
			</form>
		</div>
</body>
</html>