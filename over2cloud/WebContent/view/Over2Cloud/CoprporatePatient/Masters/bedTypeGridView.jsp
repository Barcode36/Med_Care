<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<%
	String userRights = session.getAttribute("userRights") == null ? "" : session.getAttribute("userRights").toString();
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<s:url id="viewDedTypeData" action="viewDedTypeData" >
<s:param name="wildSearchs" value="%{wildsearch}"></s:param>
<s:param name="status" value="%{status}"></s:param>
<!--
	<s:param name="SearchValue" value="%{SearchValue}"></s:param>

--></s:url>
<s:url id="editBedDataGrid" action="editBedDataGrid" />
<sjg:grid 
		  id="gridedittable"
          href="%{viewDedTypeData}"
          gridModel="deptDataViewShow"
          navigator="true"
          navigatorAdd="false"
          navigatorView="true"
          navigatorDelete="true"
          navigatorEdit="true"
          navigatorSearch="true"
          rowList="50,100,200,500"
          rownumbers="-1"
          rownumbers="true"
          viewrecords="true"       
          pager="true"
          pagerButtons="true"
          pagerInput="false"   
          multiselect="false"  
          loadingText="Requesting Data..."  
          navigatorSearchOptions="{sopt:['eq','cn']}"
          rowNum="50"
          autowidth="true"
          editurl="%{editBedDataGrid}"
          shrinkToFit="false"
          onSelectRowTopics="rowselect"
          >
          
		<s:iterator value="level1ColmnNames" id="level1ColmnNames" > 
		<s:if test="%{colomnName=='status'}">
					<sjg:gridColumn 
						name="%{colomnName}"
						index="%{colomnName}"
						title="%{headingName}"
						width="%{width}"
						align="%{align}"
						editable="true"
						search="%{search}"
						hidden="%{hideOrShow}"
						edittype="select"
						editoptions="{value:'Active:Active;Inactive:Inactive'}"
						/>
				
				</s:if>
				<s:else>
				<sjg:gridColumn 
					name="%{colomnName}"
					index="%{colomnName}"
					title="%{headingName}"
					width="%{width}"
					align="%{align}"
					editable="%{editable}"
					search="%{search}"
					hidden="%{hideOrShow}"
					/>
					</s:else>
			</s:iterator>  
</sjg:grid>

</body>
</html>