<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>

<html>

<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<SCRIPT type="text/javascript" src="js/commanValidation/validation.js"></SCRIPT>
<script type="text/javascript" src="<s:url value="/js/tabConfig/tabAdd.js"/>"></script>
<script src="<s:url value="/js/organization.js"/>"></script>

<SCRIPT type="text/javascript">
$.subscribe('level1', function(event,data)
	       {
			//document.getElementById("indicator1").style.display="none"; tabConfig
	         setTimeout(function(){ $("#orglevel1Div").fadeIn(); }, 10);
	         setTimeout(function(){ $("#orglevel1Div").fadeOut(); }, 4000);
	         $('select').find('option:first').attr('selected', 'selected');
	       });

function getEmpName(subDeptID, divId) {

	$
			.ajax( {
				type :"post",
				url :"/over2cloud/view/Over2Cloud/commonModules/getEmpName.action?subDeptIDgggggggggg="
						+ subDeptID,
				success : function(companyData) {
					$('#' + divId + ' option').remove();
					$('#' + divId).append(
							$("<option></option>").attr("value", -1).text(
									'Select'));
					$(companyData).each(
							function(index) {
								//alert(this.id + " " + this.empName);
								$('#' + divId).append(
										$("<option></option>").attr(
												"value", this.id).text(
												this.empName));
							});

				},
				error : function() {
					alert("error");
				}
			});

}

function reset(formId) {
	  $("#"+formId).trigger("reset"); 
	}

function viewDeactivate()
{ url : "/over2cloud/view/Over2Cloud/hr/beforeEmployeeView.action?makeHistory=1",
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		var conP = "<%=request.getContextPath()%>";
    $.ajax({
	    type : "post",
	    url : conP+"/view/Over2Cloud/hr/beforeEmployeeView.action?makeHistory=0",
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
<div class="list-icon">
<div class="head">Employee</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div><div class="head">Make History</div>
</div><div class="clear"></div>
	<div style="min-height: 10px; height: auto; width: 100%;"></div>
		<div class="border">
<div class="container_block"><div style=" float:left; padding:20px 5%; width:90%;">
<s:form id="formone" name="formone" namespace="/view/Over2Cloud/commonModules" action="makeHistoryEmp" theme="css_xhtml"  method="post"enctype="multipart/form-data" >
<div class="form_inner" id="form_reg" style="margin-top: 33px;">
<div class="page_form" style="margin-top: -58px;">

<div style="float:left; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px; margin-bottom: 23px;">
                  <div id="errZone" style="float:left; margin-left: 7px"></div>        
           </div>
             
                  <div class="newColumn">
             	 <div class="leftColumn1">Sub Department:</div>
				<div class="rightColumn1">
                  <span id="normalSubdept" class="pIds" style="display: none; ">subdept#Sub-Department#D#,</span>
                  <span class="needed"></span>
             	 <div id="subDeptId">
                  <s:select 
                              id="subdept"
                              name="subdept"
                              list="getSubDeptMake"
                              headerKey="-1"
                              headerValue="Select Sub-Department" 
                               cssClass="select"
                              cssStyle="margin-top: -25px;width:82%"
                              onchange="getEmpName(this.value,'empName');">
                              >
                  </s:select>
                  </div>
                  <div id="errorsubdept" class="errordiv"></div>
                  </div>
                  </div>
             
                   <div class="newColumn">
             	  <div class="leftColumn1">Employee Name:</div>
					 <div class="rightColumn1">
					 <span id="normalSubdept" class="pIds" style="display: none; ">empName#Employee#D#,</span>
	                  <span class="needed"></span>
	                  <div id="AjaxDivp">
	                  <s:select 
	                               cssClass="select"
                                  cssStyle="width:82%"
	                              id="empName"
	                              name="empName" 
	                              list="#{'-1':'Select Employee Name'}"
	                              headerKey="-1"
	                              headerValue="Select Employee Name" 
	                               cssClass="select"
					               cssStyle="width:82%"
	                              > 
	                  </s:select>
	                  </div>
	                  </div></div>
             	
			   <br><br><br><br><br>
			    <center><img id="indicator2" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/></center>
				<div class="fields">
				<ul>
	               <div class="fields" align="center">
				<sj:submit 
		                    targets="orglevel1" 
		                    clearForm="true"
	                        value="Save" 
	                        effect="highlight"
	                        effectOptions="{ color : '#222222'}"
	                        effectDuration="5000"
	                        button="true"
	                        onCompleteTopics="level1"
	                        cssClass="submit"
	                        indicator="indicator2"
	                        style="margin-left: -108px;"
	                        onBeforeTopics="validate"
				/>
					<div id=reset style="margin-top: -28px; margin-left: 95px;">
					<sj:a 
							 button="true" href="#"
							 onclick="reset('formone');"
							 cssClass="submit"
						>
						
						Reset
					</sj:a>
					<sj:a 
							button="true" href="#"
							onclick="viewDeactivate();"
							cssClass="submit"
						>
						
						Back
					</sj:a>
				 <sj:div id="foldeffect"  effect="fold">
                    <div id="Result"></div>
               </sj:div>
				</div>
	               </div>
	               
				</ul>
				<sj:div id="orglevel1Div"  effect="fold">
                    <div id="orglevel1"></div>
               </sj:div>
               </div>
             

</div>	</div>
</s:form>
</div>
</div></div>
</html>