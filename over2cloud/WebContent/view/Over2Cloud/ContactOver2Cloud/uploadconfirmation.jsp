<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link type="text/css" href="<s:url value="/css/style.css"/>" rel="stylesheet" />
<script type="text/javascript" src="<s:url value="/js/js.js"/>"></script>
<title>Resource ERP: View Employee</title>
<s:url id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<%
boolean isMasterAdmin=false;
%>
<script type="text/javascript">
$.subscribe('getSelected4Save', function(event,data) {
	var sel_id;
	sel_id = $("#gridedittable").jqGrid('getGridParam','selarrrow');
    	alert('Selected Rows : '+sel_id);
    	$.ajax({
			 type : "post",
			 url : "<%=request.getContextPath()%>/view/contactOver2Cloud/saveExcelDataURL_data.action?selectedId="+sel_id,
			 success : function(saveData) {

    				 alert("sucess");
    				 $("#excelFBUploadResult").html(saveData);
				 
				},
				error: function() {
				            alert("error");
				}
				});
				 
	});
</script>
</head>
<body>
<s:url  id ="showconfirmationdata" action="showconfirmationdata"></s:url>
    <div id="excelFBUploadResult">
<center>
<sjg:grid id="gridedittable"
          dataType="json"
          href="%{showconfirmationdata}"
          pager="true"
          pagerButtons="true"
          navigator="true"
          navigatorSearch="true"
          navigatorSearchOptions="{sopt:['cn','eq','bw','ne','ew'],reloadAfterSubmit:true,closeAfterSearch:true,closeOnEscape:true,jqModal:true,caption:'Search Service data'}"
          navigatorView="true"
          navigatorEdit="%{#parameters.modifyLink}" 
          navigatorDelete="%{#parameters.deleteLink}" 
          navigatorAdd="false"
          navigatorDeleteOptions="{height:200,reloadAfterSubmit:true}"
          gridModel="gridmodulelistObject"
    	  rowList="50,100,200,300,400,500,600,700,800,900,1000,2000,5000"
    	  rowNum="10"
    	  multiselect="true"
    	  hoverrows="true"
    	  autoencode="true"
    	  loadingText="Requesting Data..."
    	  onGridCompleteTopics="setButtons" 
    	  viewrecords="true"
    	  shrinkToFit="false"
    	  width="1000"
          >
        
  <s:iterator value="mappclumObject" id="mappclumObject" >  
		<sjg:gridColumn 
		name="%{key}"
		index="%{key}"
		title="%{value}"
		width="150"
		align="center"
		editable="false"
		search="trye"
		hidden="false"
		/>
		</s:iterator>
                   
         
   </sjg:grid>
   <br>
       <sj:submit id="saveExcelButton" value="Save" onClickTopics="getSelected4Save" button="true"  />
    <sj:submit id="grid_edit_searchbutton" value="Search" onClickTopics="searchgrid" button="true"/>
    <sj:submit id="grid_edit_colsbutton" value="Show/Hide Columns" onClickTopics="showcolumns" button="true"/>
	<br/>
	<br/>
    <div id="gridinfo" class="ui-widget-content ui-corner-all"><p>Edit Mode for Row :</p></div>
</center>
</body>
</html>