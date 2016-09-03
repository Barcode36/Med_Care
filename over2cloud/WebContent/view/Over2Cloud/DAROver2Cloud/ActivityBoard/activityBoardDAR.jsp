<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
<html>
<head>
</head>
<body>
<div class="clear"></div>

	<s:url id="darGrid" action="activityBoardGridView" escapeAmp="false">
	     <s:param name="fromdate"  value="%{fromdate}" />
	     <s:param name="todate"  value="%{todate}" />
	     <s:param name="allot"  value="%{allot}" />
	     <s:param name="taskType"  value="%{taskType}" />
	     <s:param name="taskPriority"  value="%{taskPriority}" />
	      <s:param name="search"  value="%{search}" />
	      <s:param name="taskStatus"  value="%{taskStatus}" />
	      <s:param name="searchBy"  value="%{searchBy}" />
	      <s:param name="searchByName" value="%{searchByName}"/>
	      <s:param name="mileStone" value="%{mileStone}"/>
	      <s:param name="allotedBy" value="%{allotedBy}"/>
	      <s:param name="timeStatus" value="%{timeStatus}"/>
	      <s:param name="clientfor" value="%{clientfor}"/>
	</s:url>
	
	    <sjg:grid 
		  id="grid1234"
		  href="%{darGrid}"
          gridModel="masterViewList"
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
