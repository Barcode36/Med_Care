<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>
<body>
<s:url id="viewCommonContact" action="viewCommonContactInGrid" escapeAmp="false">
   <s:param name="searchFields" value="%{searchFields}"></s:param>
	<s:param name="SearchValue" value="%{SearchValue}"></s:param>
	</s:url>
	
<s:url id="editCommonContact" action="editCommonContact"/>
<div style="overflow: scroll; height: 430px;">
		 	<sjg:grid 
				  id="gridedittable"
		          href="%{viewCommonContact}"
		          groupField="['groupId']"
          		  groupColumnShow="[false]"
				 groupText="['<b>{0}: {1}</b>']"
		          gridModel="masterViewList"
		          groupCollapse="true"
		          navigator="true"
		          navigatorAdd="false"
		          navigatorView="true"
		          navigatorDelete="true"
		          navigatorEdit="true"
		          navigatorSearch="true"
		          rowList="20,30,45,60,100,200,500,1000,1500,2000"
		          rownumbers="-1"
		          viewrecords="true"       
		          multiselect="false"  
		          loadingText="Requesting Data..."  
		          rowNum="500"
		           navigatorEditOptions="{height:230,width:400,closeAfterEdit:true,reloadAfterSubmit:false}"
		          navigatorDeleteOptions="{caption:'Inactive',msg: 'Inactive selected record(s)?',bSubmit: 'Inactive'}"
		          navigatorSearchOptions="{sopt:['eq','cn']}"
		          navigatorViewOptions="{width:500}"
		          editurl="%{editCommonContact}"
		          shrinkToFit="false"
		          autowidth="true"
		          loadonce="true"
		          pager="true"
		          rownumbers="true"
		          onSelectRowTopics="rowselect"
		          >
				<s:iterator value="masterViewProp" id="masterViewProp" >  
				<s:if test="%{colomnName=='flag'}">
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
					formatter="%{formatter}"
					search="%{search}"
					hidden="%{hideOrShow}"
					/>
				</s:else>
				</s:iterator>  
		</sjg:grid>
</div>
</body>
</html>