<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<% String userRights = session.getAttribute("userRights") == null ? "" : session.getAttribute("userRights").toString();%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script type="text/javascript" src="<s:url value="/js/helpdesk/ticketSeries.js"/>"></script>
</head>
<body>
<div class="clear"></div>
<div class="middle-content">
<!-- Header Strip Div Start -->
<div class="list-icon">
	 <div class="head">Working Hours</div><img alt="" src="images/forward.png" style="margin-top:10px; float: left;"><div class="head">View</div> 
</div>
<div class="clear"></div>
<div class="listviewBorder"  style="margin-top: 10px;width: 97%;margin-left: 20px;" align="center">
<!-- Code For Header Strip -->
<div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
<div class="pwie">
	<table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%">
		<tbody>
			<tr>
				  <!-- Block for insert Left Hand Side Button -->
				  <td>
					  <table class="floatL" border="0" cellpadding="0" cellspacing="0">
					    <tbody>
					    <tr><td class="pL10"></td></tr>
				        </tbody>
					  </table>
				  </td>
				  
				  <!-- Block for insert Right Hand Side Button -->
				  <td class="alignright printTitle">
				  <%if(userRights.contains("tick_ADD")){ %>
				      <sj:a id="addButton"  button="true"  cssClass="button" cssStyle="height:25px;" title="Add" buttonIcon="ui-icon-plus" onclick="addNewWorkingTime('dataFor');">Add</sj:a>
				  <%}%>
				  </td>
			</tr>
		</tbody>
	</table>
</div>
</div>
<!-- Code End for Header Strip -->

<div style="overflow: scroll; height: 430px;">
<s:hidden id="dataFor" value="%{dataFor}"/>
<s:url id="workingtime_URL" action="viewDeptWorkTiming" escapeAmp="false">
<s:param name="dataFor" value="%{dataFor}"></s:param>
</s:url>
<s:url id="editWorkingTiming_URL" action="editDeptWorkingTime">
</s:url>
<center>
<sjg:grid 
		  id="gridtable222"
          href="%{workingtime_URL}"
          gridModel="masterViewList"
          navigator="true"
          navigatorAdd="false"
          navigatorView="false"
          navigatorDelete="false"
          navigatorEdit="true"
          navigatorSearch="false"
          rowList="10,25,50"
          rownumbers="-1"
          viewrecords="true"       
          pager="true"
          pagerButtons="true"
          pagerInput="true"   
          multiselect="false"  
          loadonce="%{loadonce}"
          loadingText="Requesting Data..."  
          rowNum="10"
          autowidth="true"
          editinline="false"
          editurl="%{editWorkingTiming_URL}" 
          onEditInlineAfterSaveTopics="oneditsuccess"
          navigatorSearchOptions="{sopt:['cn','eq']}"
          shrinkToFit="true"
          >
          <s:iterator value="viewColumnList" id="normalWorking" >  
          <s:if test="%{colomnName=='dept'}">
          <sjg:gridColumn 
		      			   name="%{colomnName}"
		      			   index="%{colomnName}"
		      			   title="%{headingName}"
		      			   width="%{width}"
		      			   align="%{align}"
		      			   editable="%{editable}"
		      			   formatter="%{formatter}"
		      			   search="%{search}"
		      			   hidden="false"
		      			   frozen="%{frozenValue}"
		 				   />
          </s:if>
          <s:elseif test="%{colomnName=='day'}">
          <sjg:gridColumn 
		      			   name="%{colomnName}"
		      			   index="%{colomnName}"
		      			   title="%{headingName}"
		      			   width="500"
		      			   align="%{align}"
		      			   editable="%{editable}"
		      			   formatter="%{formatter}"
		      			   search="%{search}"
		      			   hidden="false"
		      			   frozen="%{frozenValue}"
		 				   />
          </s:elseif>
          <s:elseif test="%{colomnName=='holiday'}">
          <sjg:gridColumn 
		      			   name="%{colomnName}"
		      			   index="%{colomnName}"
		      			   title="%{headingName}"
		      			   width="500"
		      			   align="%{align}"
		      			   editable="%{editable}"
		      			   formatter="%{formatter}"
		      			   search="%{search}"
		      			   hidden="true"
		      			   frozen="%{frozenValue}"
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
		      			   formatter="%{formatter}"
		      			   search="%{search}"
		      			   hidden="%{hideOrShow}"
		      			   frozen="%{frozenValue}"
		 				   />
		 				    </s:else>
		   </s:iterator> 
		  
</sjg:grid>
</center>
</div>
</div>
</div>
</body>
</html>