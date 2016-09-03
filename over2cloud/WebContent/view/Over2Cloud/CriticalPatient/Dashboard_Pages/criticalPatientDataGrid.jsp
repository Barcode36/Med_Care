<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<html>
<head>
</head>
<body>
<div id="grid" style="width:100%; height:100%;">
<s:url id="viewGrids" action="viewCriticalDataPatient" escapeAmp="false" namespace="/view/Over2Cloud/Critical">
<s:param name="fromDate" value="%{fromDate}"></s:param>
<s:param name="searchString" value="%{searchString}"></s:param>
<s:param name="level" value="%{level}"></s:param>
<s:param name="patient_status" value="%{patient_status}"></s:param>
<s:param name="status" value="%{status}"></s:param>
<s:param name="doc_name1" value="%{doc_name1}"></s:param>
<s:param name="toDate" value="%{toDate}"></s:param>
<s:param name="uhidStatus" value="%{uhidStatus}"></s:param>
<s:param name="nursingUnit" value="%{nursingUnit}"></s:param>
<s:param name="patient_speciality" value="%{patient_speciality}"></s:param>

</s:url>

<sjg:grid 
		  id="gridedittable"
          href="%{viewGrids}"
          gridModel="viewCriticalData"
          groupField="['status']"
	      groupColumnShow="[false]"
	      groupCollapse="false"
	      groupText="['<b>{0}: {1}</b>']"
          navigator="false"
          navigatorAdd="false"
          navigatorView="false"
          navigatorDelete="true"
          navigatorEdit="true"
          navigatorSearch="true"
          rowList="5000,6050,7500,8000"
          viewrecords="true"       
          pager="true"
          pagerButtons="true"
          pagerInput="true"   
          multiselect="true"  
          loadingText="Requesting Data..."  
          rowNum="5000"
          navigatorSearchOptions="{sopt:['eq','cn']}"
          editurl="%{modifyGrid}"
          navigatorEditOptions="{height:400,width:400}"
          shrinkToFit="false"
          onSelectRowTopics="rowselect"
          sortable="true"
    		
          >
         <s:iterator value="masterViewProp" id="viewPageColumns">  
		<sjg:gridColumn 
					name="%{colomnName}"
					index="%{colomnName}"
					title="%{headingName}"
					width="%{width}"
					align="center"
					editable="%{editable}"
					formatter="%{formatter}"
					search="%{search}"
					hidden="%{hideOrShow}"
				/>
		</s:iterator> 
</sjg:grid>
</div>
</body>
</html>