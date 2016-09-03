<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<s:url id="href_URL" action="ViewSeekApproval" escapeAmp="false">
<s:param name="moduleName" value="%{moduleName}"></s:param>
<s:param name="approvalStatus" value="%{approvalStatus}"></s:param>
<s:param name="actionBy" value="%{actionBy}"></s:param>
<s:param name="minDateValue" value="%{minDateValue}"></s:param>
<s:param name="maxDateValue" value="%{maxDateValue}"></s:param>
<s:param name="reqBy" value="%{reqBy}"></s:param>
<s:param name="reqTo" value="%{reqTo}"></s:param>
</s:url>
<sjg:grid 
		  id="gridedittable"
          href="%{href_URL}"
          gridModel="masterViewList"
          navigator="true"
          navigatorAdd="false"
          navigatorView="true"
          navigatorDelete="false"
          navigatorEdit="false"
          navigatorSearch="true"
          groupField="['status']"
   	 	  groupColumnShow="[true]"
   	  	  groupCollapse="true"
   	  	  groupText="['<b>{0}: {1} Tickets</b>']"
          rowList="250,350,500"
          viewrecords="true"       
          pager="true"
          pagerButtons="true"
          pagerInput="true"   
          multiselect="true"  
          loadonce="%{loadonce}"
          loadingText="Requesting Data..."  
          rowNum="250"
          autowidth="true"
          navigatorEditOptions="{height:230,width:500}"
          navigatorSearchOptions="{sopt:['cn','eq']}"
          navigatorEditOptions="{reloadAfterSubmit:true}"
          shrinkToFit="false"
          >
		  <s:iterator value="viewPageColumns" id="viewPageColumns" >  
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
		   </s:iterator>  
</sjg:grid>
</body>
</html>