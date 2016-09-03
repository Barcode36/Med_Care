<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<html>
<head>
<s:url id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}" />
<title>Insert title here</title>
<SCRIPT type="text/javascript" src="js/feedback/feedbackForm.js"></SCRIPT>
<link href="css/style.css" rel="stylesheet" type="text/css" />
<SCRIPT type="text/javascript">

$.subscribe('level1', function(event,data)
	       {
	         setTimeout(function(){ $("#orglevel1Div").fadeIn(); }, 10);
	         setTimeout(function(){ $("#orglevel1Div").fadeOut(); }, 4000);
	       });

function viewOrgInfoPage()
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/commonModules/beforeOrganizationViewTable.action",
	    success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });
}


</SCRIPT>
</head>
<body>
<s:form theme="simple" name="organizationModifyTable" method="post" namespace="/view/Over2Cloud/commonModules" action="organizationModifyTable">
<table align="left" width="60%" border="0">
					<s:hidden name="id" value="%{id}"></s:hidden>
							<tr >
								<td height="20px" width="30%">
									<div class="heading">Name :</div>
								</td>
								<td width="70%">
									<s:textfield theme="simple" size="30" id="orgName" name="orgName" value="%{orgName}" cssStyle="margin:0px 0px 10px 0px" cssClass="textField"></s:textfield>
								</td>
							</tr>
							<tr>
								<td height="20px">
									<div class="heading">Address :</div>
								</td>
								<td >
									<s:textfield  theme="simple"  id="orgAddress" size="30" name="orgAddress" value="%{orgAddress}"  cssStyle="margin:0px 0px 10px 0px" readonly="false" cssClass="textField"></s:textfield>
								</td>
							</tr>
							<tr>
								<td >
									<div class="heading">City :</div>
								</td>
								<td >
									<s:textfield  size="30" theme="simple"  id="orgCity" name="orgCity" value="%{orgCity}"  cssStyle="margin:0px 0px 10px 0px" readonly="false" cssClass="textField"></s:textfield>
								</td>
							</tr>
							<tr>
								<td >
									<div class="heading">Country :</div>
								</td>
								<td >
								<s:textfield  size="30" theme="simple"  id="orgCountry" name="orgCountry" value="%{orgCountry}"  cssStyle="margin:0px 0px 10px 0px" readonly="false" cssClass="textField"></s:textfield>
									<%-- <s:property value="%{orgCountry}" /> --%>
								</td>
							</tr>
							<tr>
								<td >
									<div class="heading">Pin&nbsp;Code :</div>
								</td>
								<td  >
									<s:textfield  theme="simple" size="30" id="orgPin" name="orgPin" value="%{orgPin}" cssStyle="margin:0px 0px 10px 0px" readonly="false" cssClass="textField" ></s:textfield>
								</td>
							</tr>
							<tr>
								<td  >
									<div class="heading">Support&nbsp;No :</div>
								</td>
								<td  >
									<s:textfield  readonly="false"  theme="simple" size="30" id="orgContactNo" name="orgContactNo" value="%{orgContactNo}"  cssStyle="margin:0px 0px 10px 0px" cssClass="textField"></s:textfield>
								</td>
							</tr>
							<tr>
								<td  >
									<div class="heading">Support&nbsp;Email :</div>
								</td>
								<td  >
									<s:textfield  readonly="false"  theme="simple" size="30" id="companyEmail" name="companyEmail" value="%{companyEmail}"  cssStyle="margin:0px 0px 10px 0px" cssClass="textField"></s:textfield>
								</td>
							</tr>
							<tr>
								<td colspan="2">
									<sj:submit 
			 	value="Update" 
			 	button="true"
			 	cssStyle="margin-left: 115px;margin-top: 11px;"
			 	 targets="level123"
			 	onCompleteTopics="level1"
				/>
				<sj:submit 
		                     value="Reset" 
		                     button="true"
		                     cssStyle="margin-left:5px;margin-top: 10px;"
		                     onclick="resetForm('orgModify');"
			            />
			            <sj:a
	                        cssStyle="margin-left: 5px;margin-top: 10px;" 
						button="true" href="#" value="View" 
						onclick="viewOrgInfoPage();"
					>
						Back
					</sj:a>
								</td>
							</tr>
							<tr>
			<td colspan="2">
				<sj:div id="orglevel1Div"  effect="fold">
                   <sj:div id="level123">
                   </sj:div>
               </sj:div>
			</td>
		</tr>
						</table>
						</s:form>
</body>
</html>