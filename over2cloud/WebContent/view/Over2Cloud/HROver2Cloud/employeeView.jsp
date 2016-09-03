<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<%String userRights = session.getAttribute("userRights") == null ? "" : session.getAttribute("userRights").toString();%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

</head>
<body>

<s:url id="viewCommonContact" action="employeeViewInGrid" escapeAmp="false">
<s:param name="statusCheck" value="%{statusCheck}"></s:param>
    <s:param name="fieldser" value="%{fieldser}"></s:param>
	<s:param name="fieldval" value="%{fieldval}"></s:param>
	<s:param name="searchFields" value="%{searchFields}"></s:param>
	<s:param name="SearchValue" value="%{SearchValue}"></s:param>
<s:url id="editEmployeeContact" action="editEmployee"/>
</s:url>
<div style="overflow: scroll; height: 430px;">
		 	<sjg:grid 
				  id="gridedittable"
		          href="%{viewCommonContact}"
		          gridModel="masterViewList"
		          navigator="true"
		          navigatorAdd="false"
		          navigatorView="true"
		          navigatorDelete="true"
		          navigatorEdit="true"
		          navigatorSearch="true"
		          rowList="15,30,45,60"
		          rownumbers="-1"
		          viewrecords="true"       
		          multiselect="false"  
		          loadingText="Requesting Data..."  
		          rowNum="15"
		          navigatorSearchOptions="{sopt:['eq','cn']}"
		          editurl="%{editEmployeeContact}"
		          navigatorEditOptions="{reloadAfterSubmit:true}"
		          autowidth="true"
		          loadonce="true"
		          pager="true"
		          rownumbers="true"
		          shrinkToFit="false"
		          onSelectRowTopics="rowselect"
		          >
				<s:iterator value="masterViewProp" id="masterViewProp" >  
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
		</div>
		
		<sj:dialog
          id="takeActionGridss"
          showEffect="slide"
          hideEffect="explode"
          autoOpen="false"
          title="Employee Details"
          modal="true"
          closeTopics="closeEffectDialog"
          width="1000"
          height="400"
          draggable="true"
    	  resizable="true"
    	 
          >
</sj:dialog>
<sj:dialog
	id="leadFullViewDialog"
	title="Full View"
	modal="true"
	autoOpen="false"
	width="900"
	height="400"	
	></sj:dialog>



</body>
</html>