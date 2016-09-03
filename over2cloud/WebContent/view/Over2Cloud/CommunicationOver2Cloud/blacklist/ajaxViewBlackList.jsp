<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>

</head>
<body>

<s:url id="searchblackListurl" action="searchblackListurl" escapeAmp="false">
<s:param name="mobileNo"  value="%{#parameters.mobileNo}"></s:param>
<s:param name="from_date"  value="%{#parameters.from_date}"></s:param>
<s:param name="to_date"  value="%{#parameters.to_date}"></s:param>
</s:url>
<sjg:grid 
		  id="gridblacklistid"
          href="%{searchblackListurl}"
          gridModel="viewblacklList"
          navigator="true"
          navigatorAdd="false"
          navigatorView="false"
          navigatorDelete="true"
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
          rowNum="10"
          
          navigatorSearchOptions="{sopt:['eq','ne','cn','bw','ne','ew']}"
          editurl="%{viewModifyblacklistGrid}"
          shrinkToFit="false"
          autowidth="true"
          >
          <s:iterator value="ViewBlackListedNumGrid" id="ViewBlackListedNumGrid" >  
		<s:if test="colomnName=='fromdate'">
		<sjg:gridColumn 
		name="%{colomnName}"
		index="%{colomnName}"
		title="%{headingName}"
		width="%{width}"
		align="%{align}"
		editable="%{editable}"
		search="%{search}"
		hidden="%{hideOrShow}"
	    editoptions="{dataInit:datePicker}"
		/>
		</s:if>
		<s:elseif test="colomnName=='todate'" >
        <sjg:gridColumn 
		name="%{colomnName}"
		index="%{colomnName}"
		title="%{headingName}"
		width="%{width}"
		align="%{align}"
		editable="%{editable}"
		search="%{search}"
		hidden="%{hideOrShow}"
		editoptions="{dataInit:datePicker}"
		/>
		</s:elseif>
		<s:elseif test="colomnName=='user'" >
        <sjg:gridColumn 
		name="%{colomnName}"
		index="%{colomnName}"
		title="%{headingName}"
		width="%{width}"
		align="%{align}"
		editable="false"
		search="%{search}"
		hidden="%{hideOrShow}"
		/>
		</s:elseif>
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