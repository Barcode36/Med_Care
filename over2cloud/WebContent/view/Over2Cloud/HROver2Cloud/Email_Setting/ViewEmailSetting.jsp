<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>
<body>
<s:url id="viewGrid" action="ViewEmailSetting" escapeAmp="false" >
<s:param name="searchField" value="%{searchField}" />
<s:param name="searchString" value="%{searchString}" />
<s:param name="searchOper" value="%{searchOper}" />
<s:param name="id" value="%{id}" />
</s:url>
<s:url id="modifyGridEmail" action="modifyEmailGrid"/>
<sjg:grid 
		  id="gridedittable"
          href="%{viewGrid}"
          gridModel="viewEmailData"
          navigator="true"
          navigatorAdd="false"
          navigatorView="true"
          navigatorDelete="true"
          navigatorEdit="true"
          navigatorSearch="true"
          rowList="15,20,25,30"
          rownumbers="-1"
          viewrecords="true"       
          pager="true"
          pagerButtons="true"
          pagerInput="true"   
          multiselect="false"  
          loadingText="Requesting Data..."  
          rowNum="10"
          navigatorSearchOptions="{sopt:['eq','cn']}"
          editurl="%{modifyGridEmail}"
               navigatorEditOptions="{height:350,width:400,reloadAfterSubmit:true, afterSubmit: function () {
									reloadPage();
	  			  }}"
		          navigatorDeleteOptions="{caption:'Inactive',msg: 'Inactive selected record(s)?',bSubmit: 'Inactive', afterSubmit: function () {
			reloadPage();
	    }}"
          navigatorViewOptions="{height:400,width:350}"
          shrinkToFit="false"
          autowidth="true"
          onSelectRowTopics="rowselect"
          
          >
          <s:iterator value="viewEmailGrid" id="viewEmailGrid" >  
	     <s:if test="%{colomnName=='status'}">
				<sjg:gridColumn 
					name="%{colomnName}"
					index="%{colomnName}"
					title="Status"
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
