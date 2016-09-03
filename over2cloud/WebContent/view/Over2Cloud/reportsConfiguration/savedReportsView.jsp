<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script>
function showReports(cellvalue, options, rowObject)
{
	return "<a href='#' onClick='showReportsDetail("+ rowObject.id +")'>View</a>";
}

function showReportsDetail(id)
{
	var conP = "<%=request.getContextPath()%>";
	$("#compReport").load(conP+"/view/Over2Cloud/reportsConfiguration/getSelectedReport.action?id="+id);
	$("#compReport").dialog('open');

	return false;
	function cancelButton4() {
		$('#compReport').dialog('close');
	};
}
</script>
</head>
<body>

<sj:dialog
          id="compReport"
          showEffect="slide"
          hideEffect="explode"
          autoOpen="false"
          title="Reports Detail"
          modal="true"
          closeTopics="closeEffectDialog"
          width="1000"
          height="300"
          draggable="true"
    	  resizable="true"
    	  buttons="{ 
			'Cancel':function() { cancelButton4(); } 
			}"
          >
</sj:dialog>
<div class="page_title"><h1><s:property value="%{headerName}"/> </h1></div>
<div class="container_block"><div style=" float:left; padding:20px 5%; width:90%;">
<center>
<div class="form_inner" id="form_reg">
<div class="page_form">
<s:url id="viewReport" action="saveReportsViewInGrid" />
reportDataViewShow: <s:property value="reportDataViewShow.size()"/>
<center>
<sjg:grid 
		  id="gridedittable"
          caption="Customer Examples (Editable/Multiselect)"
          href="%{viewReport}"
          gridModel="reportDataViewShow"
          navigator="true"
          navigatorAdd="false"
          navigatorView="true"
          navigatorDelete="false"
          navigatorEdit="%{editDeptData}"
          navigatorSearch="true"
          rowList="10,15,20,25"
          rownumbers="-1"
          viewrecords="true"       
          pager="true"
          pagerButtons="true"
          pagerInput="false"   
          multiselect="false"  
          loadingText="Requesting Data..."  
          rowNum="5"
          width="650"
          navigatorSearchOptions="{sopt:['eq','ne','cn','bw','ne','ew']}"
          editurl="%{editDeptDataGrid}"
          shrinkToFit="false"
          >
		<sjg:gridColumn name="id" index="id" title="Id" formatter="integer" editable="false" sortable="false" search="true" hidden="true" width="50" />
    	<sjg:gridColumn name="name" index="name" title="Name" editable="true" edittype="text" sortable="true" search="false" width="100" align="center"/> 
    	<sjg:gridColumn name="querry" index="querry" title="Querry" editable="true" edittype="text" sortable="true" search="false" width="300" align="center"/>
    	<sjg:gridColumn name="savedOn" index="savedOn" title="Saved On" editable="true" edittype="text" sortable="true" search="false" width="100" align="center"/>
    	<sjg:gridColumn name="savedAt" index="savedAt" title="Saved At" editable="true" edittype="text" sortable="true" search="false" width="100" align="center"/>
    	<sjg:gridColumn name="savedAt" index="savedAt" title="Saved At" editable="true" edittype="text" sortable="true" search="false" width="100" align="center"/>
    	<sjg:gridColumn name="takeAction" index="takeAction" title="Show Data" editable="true" edittype="text" sortable="true" search="false" width="100" align="center" formatter="showReports"/>
</sjg:grid>
</center>
</div>
</div>
</center>
</div>
</div>
</body>
</html>