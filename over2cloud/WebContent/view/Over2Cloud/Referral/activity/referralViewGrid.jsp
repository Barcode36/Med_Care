<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<%
	String userRights = session.getAttribute("userRights") == null ? "" : session.getAttribute("userRights").toString();
%>
<html>
<head>
</head>
<body>
<div id="view1">
<s:url id="viewGrids" action="viewReferralData" escapeAmp="false" namespace="/view/Over2Cloud/Referral">
<s:param name="fromDate" value="%{fromDate}"></s:param>
<s:param name="toDate" value="%{toDate}"></s:param>
<s:param name="status" value="%{status}"></s:param>
<s:param name="priority" value="%{priority}"></s:param>
<s:param name="searchString" value="%{searchString}"></s:param>
<s:param name="nursingUnit" value="%{nursingUnit}"></s:param>
<s:param name="specialty" value="%{specialty}"></s:param>
<s:param name="level" value="%{level}"></s:param>
<s:param name="reffby" value="%{reffby}"></s:param>
<s:param name="refDocTo" value="%{refDocTo}"></s:param>
<s:param name="refDoc" value="%{refDoc}"></s:param>
<s:param name="locationWise" value="%{locationWise}"></s:param>
</s:url>
<sjg:grid 
		  id="gridedittable"
          href="%{viewGrids}"
          gridModel="viewReferralData"
          groupField="['status']"
	      groupColumnShow="[false]"
	      groupCollapse="false"
	      groupText="['<b>{0}: {1}</b>']"
          navigator="true"
          navigatorAdd="false"
          navigatorView="false"
          navigatorDelete="false"
          navigatorEdit="false"
          navigatorSearch="false"
          rowList="1000,2000,4000"
          viewrecords="true"       
          pager="true"
          pagerButtons="true"
          pagerInput="true"   
          multiselect="true"  
          loadingText="Requesting Data..."  
          rowNum="50"
          scrollrows="false"
          navigatorSearchOptions="{sopt:['eq','cn']}"
          editurl="%{modifyGrid}"
          navigatorEditOptions="{height:400,width:400}"
          shrinkToFit="false"
          onSelectRowTopics="rowselect"
          sortable="true"
          
          		pager="true" 

		       rownumbers="-1"
		          scrollrows="false"
		          autowidth="true"
		          loadonce="true"
		          rownumbers="true"
		         
		         
		         
		         
          >
         <s:iterator value="viewPageColumns" id="viewPageColumns">  
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

<script type="text/javascript">
$(document).ready(function() {
	$('.ui-jqgrid-bdiv').css({ 
		"overflow": 'scroll',
		"height": '360px'
		});
	});
</script>
 <style type="text/css">
   	.ui-jqgrid-bdiv{
	overflow: scroll;
	height: 360px;
	}
   </style>