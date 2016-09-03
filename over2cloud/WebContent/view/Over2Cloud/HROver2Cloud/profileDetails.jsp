<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<SCRIPT type="text/javascript" src="js/feedback/feedbackForm.js"></SCRIPT>
<SCRIPT type="text/javascript">
$.subscribe('completeData', function(event,data)
		{
			  setTimeout(function(){ $("#foldeffect").fadeIn(); }, 10);
		      setTimeout(function(){ $("#foldeffect").fadeOut(); }, 4000);
		      $('select').find('option:first').attr('selected', 'selected');
		});


function viewAccountPage() {
	var conP = "<%=request.getContextPath()%>";
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    //url : "/over2cloud/view/Over2Cloud/commonModules/makeHistory.action",
	    url : "/over2cloud/view/Over2Cloud/hr/beforemyaccountPages.action",
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
<s:form id="profileUpdate" name="profileUpdate" namespace="/view/Over2Cloud/hr" action="updatePersonalInfo" theme="css_xhtml"  method="post" enctype="multipart/form-data" >
<s:hidden id="id" name="id" value="%{id}"></s:hidden>
	<table width="80%" height="100%">
		<tr>
			<td valign="top">
				<table width="80%" cellspacing="0" cellpadding="0">
 					<tbody>
 						<tr>
  							<td class="cell" valign="top">Name</td> 
   							<td class="cell" valign="top"><s:textfield  theme="simple"  cssClass="textField"   id="empName" name="empName" value="%{empName}"  ></s:textfield></td>
    					</tr>
    					<tr>
    						<td class="cell" valign="top">Communication Name</td>
							<td class="cell" valign="top">
								<s:textfield id="comName" name="comName"  theme="simple"  cssClass="textField"  value="%{comName}"  ></s:textfield>
     						</td>
     					</tr>
    					<tr>
    						<td class="cell" valign="top">Mobile No
    						</td>
							<td class="cell" valign="top">
     							<s:textfield id="mobOne" name="mobOne" theme="simple"  cssClass="textField"  value="%{mobOne}" maxlength="10" ></s:textfield>
     						</td>
     					</tr>
     					<tr>
     						<td class="cell" valign="top">Email ID
     						</td> 
      						<td class="cell" valign="top"><s:textfield id="emailIdOne" theme="simple"  cssClass="textField"  name="emailIdOne" value="%{emailIdOne}" ></s:textfield></td>
       					</tr>
       					<tr>
       						<td class="cell" valign="top">Employee ID</td> 
       						<td class="cell" valign="top"><s:textfield id="empId" theme="simple"  cssClass="textField"  name="empId" value="%{empId}"  ></s:textfield></td>
       					</tr>
					</tbody>
				</table>
			</td>
		</tr>
		<tr>
			<td>
				<sj:submit 
			 	value="Update" 
			 	button="true"
			 	cssStyle="margin-left: 115px;margin-top: 11px;"
			 	targets="target1"
			 	onCompleteTopics="completeData"
				/>
				<sj:submit 
		                     value="Reset" 
		                     button="true"
		                     cssStyle="margin-left: 197px;margin-top: -28px;"
		                     onclick="resetForm('profileUpdate');"
			            />
			            <sj:a
	                        cssStyle="margin-left: 276px;margin-top: -58px;" 
						button="true" href="#" value="View" cssStyle="margin-left: 269px;margin-top: -28px;" 
						onclick="viewAccountPage();"
					>
						Back
					</sj:a>
			</td>
		</tr>
		<tr>
			<td>
			  <sj:div id="foldeffect" effect="fold">
		     <div id="target1"></div>
		     </sj:div>
			</td>
		</tr>
	</table>
</s:form>							
</body>
</html>