<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%
int temp=0;
%>
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script type="text/javascript" src="<s:url value="/js/showhide.js"/>"></script>
<SCRIPT type="text/javascript">

$.subscribe('level1', function(event,data)
	       {
			//document.getElementById("indicator1").style.display="none";
	         setTimeout(function(){ $("#orglevel1Div").fadeIn(); }, 10);
	         setTimeout(function(){ $("#orglevel1Div").fadeOut(); }, 4000);
	       });

function reset(formId) {
	  $("#formId").trigger("reset"); 
	}

function viewEmp()
{

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
<script src="<s:url value="/js/employeeOver2Cloud.js"/>"></script>
</head>
<body>
<div class="clear"></div>

<div class="middle-content">

	<div class="list-icon">
	<div class="head">Employee</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div><div class="head">Excel Upload</div>
	</div>
	<div class="clear"></div>
	<div class="border">
	<div style="min-height: 10px; height: auto; width: 100%;"></div>
    <div class="container_block">
    <div style=" float:left; padding:20px 5%; width:90%;">

<s:form id="empExcel" action="uploadEmpDetailExcel" namespace="/view/Over2Cloud/hr" theme="css_xhtml" method="post" enctype="multipart/form-data">
			<s:hidden name="upload4" value="empBasicDetail" />
			 <div class="form_menubox">
       		<div class="user_form_text" style="margin-top: -10px;">Select Excel</div>
       		<div class="user_form_input"><s:file name="excel"/><div id="errAssetExcel" style="display: inline; width: 20px;"></div></div>
       		<div class="user_form_text1">Excel Format</div>
       		<div class="user_form_input">
       		<a id='empBasicUpload' href="#" onclick="getCurrentColumn('uploadExcel','empBasicDetail','uploadColumnEmpDialog','uploadEmpColumnDiv')">
       			<font color="#194d65">Download</font>
       		</a>
       		</div>
    		</div>
    		
    		<div class="form_menubox" style="margin-left: 90px;"><br><br>
    		<center>
       		<div class="type-button">
        	<sj:submit
                  openDialog="EmployeeBasicDialog"
                  value=" Upload "
                  targets="createEmpBasic"
                  clearForm="true"
                  button="true"
                  onBeforeTopics="validExcelAsset"
                  >
        	</sj:submit>
        	<div id="reset" style="margin-left: 239px; margin-top: -26px;">
        	<sj:a 
							 button="true" href="#"
							 onclick="reset('empExcel');"
							 cssClass="submit"
						>
						
						Reset
					</sj:a>
					<sj:a 
							button="true" href="#"
							onclick="viewEmp();"
							cssClass="submit"
						>
						
						Back
					</sj:a>
					</div>
        	<sj:dialog
                  id="EmployeeBasicDialog"
                  autoOpen="false"
                  closeOnEscape="true"
                  modal="true"
                  title="Employee Basic Detail Upload Via Excel"
                  width="700"
                  height="350"
                  showEffect="slide"
                  hideEffect="explode">
                  <sj:div id="foldeffectExcel" effect="fold">
                     <div id="saveExcel"></div>
                  </sj:div>
                  <div id="createEmpBasic"></div>
        	</sj:dialog>
       		</div>
    		</center>
    		</div>
</s:form>

</div>
</div></div>
<sj:dialog 
	id="uploadColumnEmpDialog" 
	modal="true" 
	effect="slide" 
	autoOpen="false" 
	width="300" 
	height="500" 
	title="Employee Column Name" 
	loadingText="Please Wait" 
	overlayColor="#fff" 
	overlayOpacity="1.0" 
	position="['left','top']" >
	<div id="uploadEmpColumnDiv"></div>
</sj:dialog>
</body>
</html>
