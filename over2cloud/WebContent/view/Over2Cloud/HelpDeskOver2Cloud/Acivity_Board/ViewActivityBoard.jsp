<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
 
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>

<body>

 
 <s:url id="viewActivityBoardData" action="viewActivityBoardData" escapeAmp="false">
	<s:param name="maxDateValue" value="%{maxDateValue}"></s:param>
	<s:param name="dept" value="%{dept}"></s:param>
		<s:param name="category" value="%{category}"></s:param>
		<s:param name="subcategory" value="%{subcategory}"></s:param>
    <s:param name="minDateValue" value="%{minDateValue}"></s:param>
    <s:param name="taskType" value="%{taskType}"></s:param>
    <s:param name="fromDepart" value="%{fromDepart}"></s:param>
    <s:param name="toDepart" value="%{toDepart}"></s:param>
    <s:param name="lodgeMode" value="%{lodgeMode}"></s:param>
    <s:param name="feedStatus" value="%{feedStatus}"></s:param>
    <s:param name="closeMode" value="%{closeMode}"></s:param>
    <s:param name="escLevel" value="%{escLevel}"></s:param>
    <s:param name="escTAT" value="%{escTAT}"></s:param>
    <s:param name="searchField" value="%{searchField}"></s:param>
    <s:param name="searchString" value="%{searchString}"></s:param>
    <s:param name="severityLevel" value="%{severityLevel}"></s:param>
    <s:param name="fromTime" value="%{fromTime}"></s:param>
    <s:param name="toTime" value="%{toTime}"></s:param>
   <s:param name="regUser" value="%{regUser}"></s:param>
   <s:param name="complianId" value="%{complianId}"></s:param>
    <s:param name="advncSearch" value="%{advncSearch}"></s:param>
  
    </s:url>
		<sjg:grid 
	          id="gridedittable"
	          href="%{viewActivityBoardData}"
	          gridModel="masterViewProp"
	          groupField="['status']"
	          groupColumnShow="[false]"
	          groupCollapse="false"
	          groupText="['<b>{0}: {1}</b>']"
	           groupOrder="['asc']"
	           navigator="true"
	          navigatorAdd="false"
	          navigatorView="true"
	          navigatorDelete="false"
	          navigatorEdit="false"
	          navigatorSearch="true"
	          editinline="false"
	          viewrecords="true"   
	          rowList="200,400,1000,2000,5000,8000"
	          rowNum="200"
	              
	          pager="true"
	          dataType="json"
	          gridview="true"
	          pagerButtons="true"
	          rownumbers="true"
	          pagerInput="true"   
	          navigatorSearchOptions="{sopt:['eq','cn']}"  
	          loadingText="Requesting Data..."  
	          groupSummary="true"
	          userDataOnFooter="true"
	          navigatorEditOptions="{height:230,width:400}"
	          editurl="%{modifyAsset}"
	          navigatorEditOptions="{closeAfterEdit:true,reloadAfterSubmit:true}"
	          shrinkToFit="false"
	          autowidth="true"
	          loadonce="true"
	          onSelectRowTopics="rowselect"
	          onEditInlineSuccessTopics="oneditsuccess"
	          multiselect="true"
	          onCompleteTopics="closeTop"
	       	 filter="true"
       		 filterOptions="{searchOnEnter:false,defaultSearch:'cn'}"
       		 
       		        	>
			<s:iterator value="viewPageColumns" id="viewPageColumns" >
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
 
 
	
		
</body>
</html>