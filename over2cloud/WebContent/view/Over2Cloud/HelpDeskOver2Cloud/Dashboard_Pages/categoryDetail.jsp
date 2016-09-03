<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<% String userRights=session.getAttribute("userRights")== null ? "" : session.getAttribute("userRights").toString();%>
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link type="text/css" href="<s:url value="/css/style.css"/>" rel="stylesheet" />
<script type="text/javascript" src="<s:url value="/js/js.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/helpdesk/feedback.js"/>"></script>

</head>
<body>
<sj:dialog id="printSelect" title="Print Ticket" autoOpen="false"  modal="true" height="370" width="700" showEffect="drop">
<sj:dialog id="feed_status" modal="true" effect="slide" autoOpen="false"  width="1100" hideEffect="explode" position="['center','top']"></sj:dialog>
<div id="ticketsInfo"></div>
</sj:dialog>
<s:hidden id="feedStatusValue" value="%{feedStatus}"></s:hidden>
<input type="hidden" id="conString"  value="<%=request.getContextPath()%>"/>
<div class="clear"></div>
<div class="middle-content">
<div class="list-icon">
	<div class="head"><s:property value="%{feedStatus}"/> Feedback</div><img alt="" src="images/forward.png" style="margin-top:10px; float: left;"><div class="head"> View</div> 
</div>
<div class="clear"></div>
<div class="listviewBorder" style="margin-top: 10px;width: 97%;margin-left: 20px;" align="center">
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
					    <tr>
					      <td class="pL10">
					    
					      </td>
					    </tr>
					    </tbody>
					  </table>
				  </td>
				  <!-- Block for insert Right Hand Side Button -->
			</tr>
		</tbody>
	</table>
</div>
</div>
<!-- Code End for Header Strip -->

<s:url id="href_URL" action="viewCategoryDetail" escapeAmp="false">
<s:param name="id" value="%{id}"></s:param>
</s:url>
<sjg:grid 
		  id="gridedittable"
          href="%{href_URL}"
          gridModel="feedbackList"
          navigator="true"
          navigatorAdd="false"
          navigatorView="true"
          navigatorDelete="false"
          navigatorEdit="false"
          navigatorSearch="true"
          rowList="250,350,500"
          viewrecords="true"       
          pager="true"
          pagerButtons="true"
          pagerInput="true"   
          multiselect="false"  
          loadonce="%{loadonce}"
          loadingText="Requesting Data..."  
          rowNum="250"
          autowidth="true"
          navigatorEditOptions="{height:230,width:500}"
          navigatorSearchOptions="{sopt:['cn','eq']}"
          navigatorEditOptions="{reloadAfterSubmit:true}"
          shrinkToFit="false"
          >
		  <sjg:gridColumn 
		      			   name="id"
		      			   index="id"
		      			   title="ID"
		      			   width="100"
		      			   align="center"
		      			   editable="false"
		      			   hidden="true"
		 				   />
		    <sjg:gridColumn 
		      			   name="feedback_catg"
		      			   index="feedback_catg"
		      			   title="Category"
		      			   width="150"
		      			   align="center"
		      			   editable="false"
		 				   />
		 	<sjg:gridColumn 
		      			   name="feedback_subcatg"
		      			   index="feedback_subcatg"
		      			   title="Sub Category"
		      			   width="200"
		      			   align="center"
		      			   editable="false"
		 				   />
		 	<sjg:gridColumn 
		      			   name="feed_brief"
		      			   index="feed_brief"
		      			   title="Brief"
		      			   width="200"
		      			   align="center"
		      			   editable="false"
		 				   />
		 	<sjg:gridColumn 
		      			   name="status"
		      			   index="status"
		      			   title="Status"
		      			   width="100"
		      			   align="center"
		      			   editable="false"
		 				   />
		 	<sjg:gridColumn 
		      			   name="level"
		      			   index="level"
		      			   title="Level"
		      			   width="100"
		      			   align="center"
		      			   editable="false"
		 				   />
		 	<sjg:gridColumn 
		      			   name="via_from"
		      			   index="via_from"
		      			   title="Via From"
		      			   width="100"
		      			   align="center"
		      			   editable="false"
		 				   />
</sjg:grid>
</div>
</div>
</body>
</html>