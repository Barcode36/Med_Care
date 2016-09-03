<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>

<html>
<head>
<title>Insert title here</title>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<SCRIPT type="text/javascript" src="js/feedback/feedbackForm.js"></SCRIPT>
<script type="text/javascript" src="/js/common/topclient.js"></script>
<script type='text/javascript' src='js/jquery.colorbox.js'></script>

<style>
	.heading
	{
		font-size: 15px;
		font-size: 1.2em;
		font-family: Tahoma,Arail,Times New Roman;
		color: #000000;
	}
</style>
<script type="text/javascript">
function showEditDetails()
{
	
	$("#dataShowDiv").html("<br><br><center><img src=images/ajax-loaderNew.gif></center>");
    $.ajax({
        type : "post",
        url :"view/Over2Cloud/commonModules/beforeOrganizationModifyTable.action",
        success : function(subdeptdata) {
       $("#"+"dataShowDiv").html(subdeptdata);
    },
       error: function() {
            alert("error");
        }
     });
}

function showOrgDetails()
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/commonModules/beforeOrganizationView.action",
	    success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
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
      	id="downloadpdfid" 
    	showEffect="slide" 
    	hideEffect="explode" 
    	autoOpen="false" 
    	modal="true" 
    	title="Upload Logo"
    	openTopics="openEffectDialog"
    	closeTopics="closeEffectDialog"
    	modal="true" 
    	width="520"
		height="300"
    >
    <div id="testDiv"></div>
    </sj:dialog>
<div class="clear"></div>
<div class="middle-content">
	<div class="list-icon">
		<!-- <div class="head">Associate History</div> -->
		<div class="head">Organization</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div><div class="head">Basic Details</div>
	</div>
	<div class="clear"></div>
	<div style="min-height: 10px; height: auto; width: 100%;"></div>
	<div class="border">
			<table width="100%" height="100%" align="center" border="0">
				<tr>
					<td valign="top" width="30%" align="center">
					<div class="logo1" id="PageRefresh" style="float: left;">
						<a href="#" onclick="uploadPic();">
							<div class="logo1" id="PageRefresh" style="float: left;">
                                                                <img src="<s:property value="%{orgImgPath}" />" width="180px"
                        height="65px" />
        </div>
						</a>
					</div>
					 <br />
					</td>
					
					<td valign="top" align="center" width="70%">
						<div id="dataShowDiv">
						<table align="center" width="100%" height="80%" border="0">
							<tr>
								<td class="heading"><b>
									Name:</b>
								</td>
								<td class="heading"  style="margin-left: 75px">
									<s:property value="%{orgName}" />
								</td>
							</tr>
							<tr>
								<td class="heading"><b>
									Address:</b>
								</td>
								<td class="heading"  style="margin-left: 60px">
						<s:property value="%{orgAddress}" />
								</td>
							</tr>
							<tr>
								<td class="heading"><b>
									City:</b>
								</td>
								<td class="heading"  style="margin-left: 85px">
									<s:property value="%{orgCity}" />
								</td>
							</tr>
							<tr>
								<td class="heading"><b>
									Country:</b>
								</td>
								<td class="heading"  style="margin-left: 60px">
									<s:property value="%{orgCountry}" />
								</td>
							</tr>
							<tr>
								<td class="heading"><b>
									Pin&nbsp;Code:</b>
								</td>
								<td class="heading"  style="margin-left: 54px">
									<s:property value="%{orgPin}" />
								</td>
							</tr>
							<tr>
								<td class="heading"><b>
									Support&nbsp;No:</b>
								</td>
								<td class="heading" style="margin-left: 40px" >
									<s:property value="%{orgContactNo}" />
								</td>
							</tr>
							
							<tr>
								<td class="heading"><b>
									Support&nbsp;Email ID:</b>
								</td>
								<td class="heading" style="margin-left:0px">
									<s:property value="%{companyEmail}" />
								</td>
							</tr>
							
							
							<tr>
								<td colspan="2">
								
								<br>
								 <sj:a cssStyle="margin-left: 100px;margin-top: 11px;" 
						button="true" href="#" value="Edit"  cssClass="button"
						onclick="showEditDetails();"
					>
						Edit
					</sj:a>
					
					<br><br><br>
					<tr>
								<td class="heading" style="margin-left: -250px"><b>
									Note:</b>
								</td>
								<td class="heading" style="margin-left: -200px">
									<p><i>Please provide valid and active contact details as it is being used in various modules for user's support.</i></p> 
								</td>
							</tr>
					
			            <!--<sj:a
	                        cssStyle="margin-left: 276px;margin-top: -58px;"
						button="true" href="#" value="View" onclick="showOrgDetails();" cssStyle="margin-left: -9px;margin-top:11px;" 
						>Back
					</sj:a>
								--></td>
							</tr>
						</table>
							</div>
					</td>
					<td valign="top" width="10%" align="left">
						<a href="#" onclick="showOrgDetails()">
			<img src="images/table.jpg" width="18" height="18" alt="Show View" title="Show Counters" />
		</a>
					<br />
					</td>
				</tr>
			</table>
			
	</div>
	<br>
	<br>
	<br>
</div>
</body>

<script>
	        function uploadPic()
	        {
	        	$("#downloadpdfid").dialog('open');
	        	$("#testDiv").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	        	$.ajax({
	        	    type : "post",
	        	    url : "/over2cloud/logoUpload.action",
	        	    success : function(subdeptdata) {
	               $("#"+"testDiv").html(subdeptdata);
	        	},
	        	   error: function() {
	                    alert("error");
	                }
	        	 });
	        }
</script>
</html>