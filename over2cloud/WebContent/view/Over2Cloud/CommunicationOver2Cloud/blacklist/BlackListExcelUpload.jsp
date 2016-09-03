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
<script type="text/javascript" src="<s:url value="/js/communication/CommuincationBlackList.js"/>"></script>

<SCRIPT type="text/javascript">

$.subscribe('level1', function(event,data)
	       {
			//document.getElementById("indicator1").style.display="none";
	         setTimeout(function(){ $("#orglevel1Div").fadeIn(); }, 10);
	         setTimeout(function(){ $("#orglevel1Div").fadeOut(); }, 4000);
	       });
function resetForm(formId)
{
	$('#'+formId).trigger("reset");
}

function okdownloadtextformate(){
	  $("#downloadtext").submit();
	}
	function okdownloadexcelformate(){
		  $("#downloadexcel").submit();
		}

</script>

</head>
<body>
<div class="clear"></div>

	<div class="list-icon">
	<div class="head">Exclusion >> Excel Upload</div>
	</div>
	<div class="clear"></div>
	<div class="border">
	<div style="min-height: 10px; height: auto; width: 100%;"></div>
    <div class="container_block">
    <div style=" float:left; padding:20px 5%; width:90%;">
<div style="float:right; border-color: black; background-color: rgb(255,99,71); color: white;width: 90%; font-size: small; border: 0px solid red; border-radius: 8px;">
  <div id="errZone" style="float:left; margin-left: 7px"></div>  
   <div id="excelError"></div>   
    <div id="txtError"></div> 
    </div>
    	<s:form action="blacklistdowloadtextformate" id="downloadtext"   namespace="/view/Over2Cloud/CommunicationOver2Cloud/Comm"    theme="css_xhtml" enctype="multipart/form-data" name="forms">
		</s:form>
		<s:form action="blacklistdowloadexcelformate" id="downloadexcel"   namespace="/view/Over2Cloud/CommunicationOver2Cloud/Comm"   theme="css_xhtml" enctype="multipart/form-data" name="forms">
		</s:form>
<s:form id="upload_BL_excel"  namespace="/view/Over2Cloud/CommunicationOver2Cloud/Comm"     action="uploadExcelblack"    theme="css_xhtml" method="post" enctype="multipart/form-data">
        	   <div class="newColumn">
       <div class="leftColumn1">Select Excel File:</div>
       <div class="rightColumn">
	   <s:if test="%{mandatory}"><span class="needed"></span></s:if>
       <s:file name="excel" id="excel"  placeholder="Enter Data"  cssClass="textField" cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px; width:73%" />
   
    <sj:a id="searchButton" title="Search" title="Download" buttonIcon="ui-icon-arrowstop-1-s"  cssStyle="height:25px; width:32px;margin-top: -40px;margin-left: 223px;" cssClass="button" button="true" onclick="okdownloadexcelformate();"></sj:a>
 </div>
 	<sj:submit 
				targets="uploadExcelblack"
				clearForm="true" 
				value="  Upload  " 
				button="true"
				onBeforeTopics="validateExcel" 
				cssStyle="margin-left: -18px;margin-top: 2px;"
				 /> 
       </div>
        	
        	<sj:dialog
                  id="ASExcelDialog"
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
                  <div id="uploadExcelblack"></div>
        	</sj:dialog>
</s:form>

   <s:form id="upload_BL_text"  theme="css_xhtml"    namespace="/view/Over2Cloud/CommunicationOver2Cloud/Comm"   action="blacklistuploadTextFile" method="post" enctype="multipart/form-data" name="upload_TS_excel">
       <div class="newColumn">
       <div class="leftColumn1">Select Text File:</div>
       <div class="rightColumn">
	   <s:if test="%{mandatory}"><span class="needed"></span></s:if>
       <s:file name="text" id="text"   placeholder="Enter Data"  cssClass="textField" cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px; width:73%" />
      <sj:a id="searchButtonss" title="Search" title="Download" buttonIcon="ui-icon-arrowstop-1-s"  cssStyle="height:25px; width:32px;margin-top: -40px;margin-left: 223px;" cssClass="button" button="true"  onclick="okdownloadtextformate();"></sj:a>
       </div>
       
      <sj:submit 
			   targets="blacklistuploadTextFile"
				clearForm="true" 
				value="  Upload  " 
				button="true"
				onBeforeTopics="varifyFile" 
				cssStyle="margin-left: -18px;margin-top: 2px;"
				/> 
       </div>
     
				<sj:dialog
				id="bLTextDialog" 
				autoOpen="false" 
				closeOnEscape="true" 
				modal="true"
				title="Black List Upload Via Text" 
				width="920"
				height="400" 
				showEffect="slide" 
				hideEffect="explode"
				>
				<sj:div id="foldeffectExcel" effect="fold">
				<div id="saveExcel"></div>
				</sj:div>
				<div id="blacklistuploadTextFile"></div>
				</sj:dialog>
       </s:form>

<div class="type-button" style="margin-left: -200px;">
<center> 
<sj:a 
				     	href="#" 
		               	button="true" 
		               	onclick="resetForm('upload_BL_excel');"
						>
					  	Reset
		   </sj:a>
		   
		   <sj:a 
				     	href="#" 
		               	button="true" 
		               	onclick="viewBlackList();"
						>
					  	Back
		   </sj:a>
		   </center></div>
</div>
</div>

</body>
</html>
