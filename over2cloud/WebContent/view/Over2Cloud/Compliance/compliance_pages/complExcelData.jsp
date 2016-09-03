<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link type="text/css" href="<s:url value="/css/style.css"/>" rel="stylesheet" />
<link  type="text/css" href="<s:url value="/css/table.css"/>" rel="stylesheet" />
<script type="text/javascript" src="<s:url value="/js/js.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/compliance/compliance.js"/>"></script>
<s:url id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}" />
<SCRIPT type="text/javascript">

</SCRIPT>
</head>
<body>
<div class="page_title"><h1><s:property value="%{mainHeaderName}"/> </h1></div>
<div class="container_block"><div style=" float:left; padding:20px 5%; width:90%;">
<div class="form_inner" id="form_reg">
<div class="page_form">
<div class="container_block"><div style=" float:left; padding:20px 5%; width:90%;">
	
<s:form name="saveCompRecord" action="saveExcelCompRecord">
<s:url id="excelData_URL" action="excelDataInGrid" escapeAmp="false" />
<sjg:grid 
		  id					= "gridedittable"
          caption				= "%{mainHeaderName}"
          href					= "%{excelData_URL}"
          gridModel				= "gridmodulelistObject"
          navigator				= "true"
          navigatorAdd			= "false"
          navigatorView			= "true"
          navigatorDelete		= "%{deleteFlag}"
          navigatorEdit			= "%{modifyFlag}"
          navigatorSearch		= "false"
          rowList				= "10,15,20,25"
          rownumbers			= "-1"
          viewrecords			= "true"       
          pager					= "true"
          pagerButtons			= "true"
          pagerInput			= "false"   
          multiselect			= "true"  
          loadingText			= "Requesting Data..."  
          rowNum				= "5"
          navigatorEditOptions	= "{height:230,width:400}"
          navigatorSearchOptions= "{sopt:['eq','ne','cn','bw','ne','ew']}"
          editurl				= "%{viewModifyMessageDraft}"
          navigatorEditOptions	= "{reloadAfterSubmit:true}"
          shrinkToFit			= "false"
          width					= "850"
          >
		  <s:iterator value="masterViewProp" id="masterViewProp">  
			<sjg:gridColumn 
						name		=		"%{colomnName}"
						index		=		"%{colomnName}"
						title		=		"%{headingName}"
						width		=		"%{width}"
						align		=		"%{align}"
						editable	=		"%{editable}"
						formatter	=		"%{formatter}"
						search		=		"%{search}"
						hidden		=		"%{hideOrShow}"
			 />
		 </s:iterator>  
		 
</sjg:grid>
<br>
		<sj:submit
		  			targets		    ="result"
		  			id 			    ="downloadCompliance"
	 	  			button		    ="true"
	 	  			buttonIcon      ="ui-icon-Save"
		  			value			="Save"
		  			indicator		="indicator"
		  			onSuccessTopics ="complete"
		/>
<div id="result"></div>
</s:form>
</div>
</div>
</div>
</div>
</div>
</div>
</body>
</html>
