<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>

<html>
<head>
<s:url  id="contextz" value="/template/theme" />
</head>

<body>

<sj:tabbedpanel id="localtabs">
      <sj:tab id="tab1" target="tone" label="Graph"/>
      <sj:tab id="tab2" target="ttwo" label="Data"/>
      <div id="tone">
      
      </div>
      <div id="ttwo">
      
      <s:url id="viewReport" action="viewSavedReportDataGrid" escapeAmp="false">
<s:param name="id" value="%{id}"/>
</s:url>
<div class="container_block"><div style=" float:left; padding:20px 5%; width:90%;">
<div class="form_inner" id="form_reg">
<div class="page_form">
<sjg:grid 
		  id="gridedittable1"
          caption="%{mainHeaderName}"
          href="%{viewReport}"
          gridModel="masterViewList"
          navigator="true"
          navigatorAdd="false"
          navigatorView="true"
          navigatorSearch="false"
          navigatorDelete="false"
          rowList="10,15,20,25"
          rownumbers="-1"
          viewrecords="true"       
          pager="true"
          pagerButtons="true"
          pagerInput="false"   
          multiselect="false"  
          loadingText="Requesting Data..."  
          rowNum="5"
          navigatorEditOptions="{height:230,width:400}"
          navigatorSearchOptions="{sopt:['eq','ne','cn','bw','ne','ew']}"
          navigatorEditOptions="{reloadAfterSubmit:true}"
          shrinkToFit="false"
          width="835"
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
</div>
</div>
</div>
      
      
      </div>
    </sj:tabbedpanel>
</body>
</html>