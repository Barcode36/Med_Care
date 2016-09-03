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
<script type="text/javascript" src="<s:url value="/js/leaveManagement/leave.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/communication/CommuincationBlackList.js"/>"></script>

<SCRIPT type="text/javascript">

$.subscribe('level1', function(event,data)
	       {
			//document.getElementById("indicator1").style.display="none";
	         setTimeout(function(){ $("#orglevel1Div").fadeIn(); }, 10);
	         setTimeout(function(){ $("#orglevel1Div").fadeOut(); }, 4000);
	       });

</script>
</head>
<body>
<div class="clear"></div>

<div class="middle-content">

	<div class="list-icon">
	<div class="head">Exclusion >> Excel Upload</div>
	</div>
	<div class="clear"></div>
	<div class="border">
	<div style="min-height: 10px; height: auto; width: 100%;"></div>
    <div class="container_block">
    <div style=" float:left; padding:20px 5%; width:90%;">

<s:form id="formtwo" action="uploadExcelblack" theme="css_xhtml" method="post" enctype="multipart/form-data">
			<s:hidden name="upload4" value="blacklistList" />
			<div class="form_menubox">
       		<div class="user_form_text">Select Excel</div>
       		<div class="user_form_input"><s:file name="excel"/><div id="errAssetExcel" style="display: inline; width: 20px;"></div></div>
       		<div class="user_form_text1">Excel Format</div>
       		<div class="user_form_input">
       		 <a href="<%=request.getContextPath()%>/view/Over2Cloud/CommunicationOver2Cloud/Comm/BlackList.xlsx" ><font color="#194d65">Download </font></a>
       		</div>
    		</div>
    		
    		<div class="form_menubox">
    		<center>
       		<div class="type-button">
        	<sj:submit
                  openDialog="BlacklistBasicDialog"
                  value=" Upload "
                  targets="blackList"
                  clearForm="true"
                  button="true"
                 
                  >
        	</sj:submit>
        	
        	<sj:dialog
                  id="BlacklistBasicDialog"
                   autoOpen="false"
                  closeOnEscape="true"
                  modal="true"
                  title="Black List Upload Via Excel"
                  width="1050"
                  height="450"
                  showEffect="slide"
                  hideEffect="explode">
                  <sj:div id="foldeffectExcel" effect="fold">
                     <div id="saveExcel"></div>
                  </sj:div>
                  <div id="blackList"></div>
        	</sj:dialog>
       		</div>
    		</center>
    		</div>
</s:form>

</div>
</div>
</div>
</div>

</body>
</html>
