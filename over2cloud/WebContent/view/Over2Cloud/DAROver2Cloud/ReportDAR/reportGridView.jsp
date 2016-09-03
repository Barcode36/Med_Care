<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<title>Insert title here</title>
</head>
<body>
 <div style="overflow: scroll; height: 430px;">
<s:url id="reportGridDataView" action="reportGridDataView" escapeAmp="false">
<s:param name="fdate" value="%{fdate}"/>
<s:param name="tdate" value="%{tdate}"/>
<s:param name="allotTo" value="%{allotTo}"/>
<s:param name="clientFor" value="%{clientFor}"/>
<s:param name="taskTyp" value="%{taskTyp}"/>
<s:param name="taskPriority" value="%{taskPriority}"/>
<s:param name="statusWork" value="%{statusWork}"/>
</s:url>

<sjg:grid 
		  id="gridedittableReport"
          href="%{reportGridDataView}"
          gridModel="viewList"
          loadonce="true"
          navigatorEdit="false"
          navigator="true"
          navigatorAdd="false"
          navigatorView="false"
          navigatorDelete="true"
          navigatorSearch="true"
          rowList="10,15,20,25"
          viewrecords="true"       
          pager="true"
          pagerButtons="true"
          pagerInput="true"
          loadingText="Requesting Data..." 
          rownumbers="-1" 
          rowNum="10"
          autowidth="true"
          navigatorSearchOptions="{sopt:['eq','cn']}"
          shrinkToFit="false"
          navigatorViewOptions="{width:100}"
          onSelectRowTopics="rowselect"
          >
        
		<s:iterator value="reportViewProp" id="reportViewProp" >  
		<sjg:gridColumn 
	    name="%{colomnName}"
		index="%{colomnName}"
		title="%{headingName}"
		width="%{width}"
		align="%{align}"
		editable="%{editable}"
		search="%{search}"
		hidden="%{hideOrShow}"
		formatter="%{formatter}"
		/>
		</s:iterator>  
</sjg:grid>
</div>
</body>
</html>