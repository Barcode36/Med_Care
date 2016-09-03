<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>
<body>
<div class="clear"></div>
	<s:property value="%{taskType}"/>
	<s:url id="activityBoardGrid" action="activityBoardGridView" escapeAmp="false">
	     <s:param name="fromDate"  value="%{fromDate}" />
	     <s:param name="toDate"  value="%{toDate}" />
	     <s:param name="searchPeriodOn"  value="%{searchPeriodOn}" />
	     <s:param name="actionStatus"  value="%{actionStatus}" />
	     <s:param name="frequency"  value="%{frequency}" />
	     <s:param name="deptId"  value="%{deptId}" />
	     <s:param name="budget"  value="%{budget}" />
	     <s:param name="timmings"  value="%{timmings}" />
	     <s:param name="shareStatus"  value="%{shareStatus}" />
	     <s:param name="searching"  value="%{searching}" />
	      <s:param name="type"  value="%{type}" />
	        <s:param name="taskName"  value="%{taskName}" />
	</s:url>
	
	    <sjg:grid 
		  id="activityBoardGrid"
		  href="%{activityBoardGrid}"
          gridModel="masterViewList"
          groupField="['comp.actionStatus']"
          groupColumnShow="[true]"
          groupCollapse="true"
          groupText="['<b>{0}: {1}</b>']"
          groupPlusIcon=""
          navigator="true"
          navigatorAdd="false"
          navigatorView="true"
          navigatorEdit="false"
          navigatorDelete="false"
          navigatorSearch="true"
          rowList="150,200,250,300,350"
          rowNum="140"
          viewrecords="true"       
          pager="true"
          pagerButtons="true"
          pagerInput="true"   
          multiselect="true"  
          loadingText="Requesting Data..."  
          navigatorEditOptions="{height:230,width:400}"
          navigatorSearchOptions="{sopt:['eq','bw']}"
          navigatorEditOptions="{reloadAfterSubmit:true}"
          shrinkToFit="false"
          autowidth="true"
          loadonce="true"
          onSelectRowTopics="rowselect"
          >
          
		<s:iterator value="viewPageColumns" id="viewPageColumns" >  
		<s:if test="colomnName=='id'">
		<sjg:gridColumn 
							name="%{colomnName}"
							index="%{colomnName}"
							title="%{headingName}"
							width="%{width}"
							align="%{align}"
							editable="%{editable}"
							formatter="%{formatter}"
							search="%{search}"
							hidden="true"
							key="true"
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
</body>
</html>
