<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>

<body>

<!-- Dept Data VIEW URL CALLING Part Starts HERE -->
<s:url id="viewUserData" action="viewUserData" escapeAmp="false">
<s:param name="searchField" value="%{searchField}"></s:param>
	<s:param name="searchString" value="%{searchString}"></s:param>
	<s:param name="searchOper" value="%{searchOper}"></s:param>
	<s:param name="userType" value="%{userType}"></s:param>
	<s:param name="selectedId" value="%{selectedId}"></s:param>
	<s:param name="searchMob" value="%{searchMob}"></s:param>
	</s:url>
<s:url id="editUserDataGrid" action="editUserDataGrid" />

<sjg:grid 
		  id="gridedittable"
          href="%{viewUserData}"
          gridModel="userDataList"
          groupField="['dept.deptName']"
          groupCollapse="true"
    	  groupColumnShow="[false]"
    	  groupText="['<b>{0}: {1} Users</b>']"
          navigator="true"
          navigatorAdd="false"
          navigatorView="true"
          navigatorDelete="true"
          navigatorEdit="false"
          navigatorSearch="true"
          rowList="1000,2000,5000"
          viewrecords="true"       
          pager="true"
          pagerButtons="true"
          pagerInput="false"   
          multiselect="false"  
          loadingText="Requesting Data..."  
          rowNum="1000"
          rownumbers="true"
          navigatorSearchOptions="{sopt:['eq','cn']}"
          editurl="%{editUserDataGrid}"
          shrinkToFit="false"
          navigatorViewOptions="{width:520,position:'center'}"
          navigatorDeleteOptions="{caption:'Inactive',msg: 'Inactive selected record(s)?',bSubmit: 'Inactive', afterSubmit: function () {
			reloadPage();
	    }}"
          editinline="false"
          loadingText="Requesting Data..." 
          onSelectRowTopics="rowselect"
          autowidth="true"
          navigatorEditOptions="{reloadAfterSubmit:true}"
          loadonce="true"
          >
          
		<s:iterator value="userSelectedColnName" id="userSelectedColnName" >  
		<sjg:gridColumn 
		name="%{colomnName}"
		index="%{colomnName}"
		title="%{headingName}"
		width="182"
		align="%{align}"
		editable="%{editable}"
		search="%{search}"
		hidden="%{hideOrShow}"
		formatter="%{formatter}"
		searchoptions="{sopt:['eq','cn']}"
		/>
		</s:iterator>  
</sjg:grid>





</body>
</html>