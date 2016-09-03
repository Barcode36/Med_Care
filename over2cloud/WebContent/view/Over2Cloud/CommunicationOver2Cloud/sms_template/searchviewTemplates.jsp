<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<s:url id="ViewTemplatesss" action="viewTemplateDataGrid" >
<s:param name="actionStatus"  value="%{#parameters.actionStatus}" />
</s:url>
<s:url id="modifyTemplatesss" action="modifyTemplate" >
<s:param name="id" value="%{id}" />
</s:url>
<sjg:grid 
		  id="gridedittable"
          href="%{ViewTemplatesss}"
          gridModel="viewTemplateData"
          loadonce="true"
          navigator="true"
          navigatorAdd="false"
          navigatorView="false"
          navigatorDelete="true"
          groupField="['status']"
          groupColumnShow="[true]"
          groupCollapse="false"
          groupText="['<b>{0}-{1}</b>']"
          navigatorEdit="true"
          navigatorSearch="true"
          rowList="15,20,25,30"
          rownumbers="-1"
          viewrecords="true"       
          pager="true"
          pagerButtons="true"
          pagerInput="false"   
          multiselect="true"  
          loadingText="Requesting Data..."  
          rowNum="15"
          navigatorSearchOptions="{sopt:['eq','cn']}"
          editurl="%{modifyTemplatesss}"
          navigatorEditOptions="{reloadAfterSubmit:true}"
          shrinkToFit="false"
          autowidth="true"
          onSelectRowTopics="rowselect"
          
          >
          <sjg:gridColumn 
		name="template_type"
		index="template_type"
		title="Template Msg Type"
		width="100"
		align="center"
		editable="false"
		search="false"
		hidden="false"
		/> 
		<sjg:gridColumn 
		name="template_name"
		index="template_name"
		title="Template Name"
		width="100"
		align="center"
		editable="false"
		search="false"
		hidden="false"
		/> 
          <s:iterator value="viewTemplateGrid" id="viewTemplateGrid" >  
		<sjg:gridColumn 
		name="%{colomnName}"
		index="%{colomnName}"
		title="%{headingName}"
		width="%{width}"
		align="%{align}"
		editable="%{editable}"
		formatter="%{formatter}"
		search="%{search}"
		hidden="%{hideOrShow}"
		/>
		</s:iterator> 
	          
          
		
</sjg:grid>

</body>
</html>