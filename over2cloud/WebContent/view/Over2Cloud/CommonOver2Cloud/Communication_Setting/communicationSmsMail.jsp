<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>
<body>
<s:url id="smshref_URL" action="viewSMSData" escapeAmp="false">
 <s:param name="inTimeDate" value="%{inTimeDate}"></s:param>
  <s:param name="updateTimeDate" value="%{updateTimeDate}"></s:param>
  <s:param name="moduleName" value="%{moduleName}"></s:param>
 </s:url>
 
<sjg:grid 
		  id="smsgrid"
          href="%{smshref_URL}"
          gridModel="smsList"
          groupField="['module']"
    	  groupColumnShow="[false]"
    	  groupCollapse="true"
    	  groupText="['<b>Via {0} - {1} SMS</b>']"
          navigator="true"
          navigatorAdd="false"
          navigatorView="true"
          navigatorDelete="false"
          navigatorEdit="false"
          navigatorSearch="true"
          rowList="2500,3500,5000"
          rownumbers="-1"
          viewrecords="true"       
          pager="true"
          pagerButtons="true"
          pagerInput="true"   
          multiselect="true"  
          loadonce="%{loadonce}"
          loadingText="Requesting Data..."  
          rowNum="2500"
          width="true"
          navigatorEditOptions="{height:230,width:500}"
          navigatorSearchOptions="{sopt:['eq','ne','cn','bw','ne','ew']}"
          navigatorEditOptions="{reloadAfterSubmit:true}"
          shrinkToFit="false"
          >
		  <sjg:gridColumn 
		      			   name="id"
		      			   index="id"
		      			   title="ID"
		      			   width="100"
		      			   align="center"
		      			   hidden="true"
		 				   />
		 				   
		  <sjg:gridColumn 
		      			   name="name"
		      			   index="name"
		      			   title="Name"
		      			   width="100"
		      			   align="left"
		      			   search="true"
		 				   />
		 		
		  <sjg:gridColumn 
		      			   name="dept"
		      			   index="deept"
		      			   title="Department"
		      			   width="100"
		      			   align="center"
		      			   search="true"
		 				   />
		 				   				   
		  <sjg:gridColumn 
		      			   name="mobOne"
		      			   index="mobOne"
		      			   title="Mobile No"
		      			   width="100"
		      			   align="center"
		      			   search="true"
		 				   />
		 				   
		 <sjg:gridColumn 
		      			   name="sms"
		      			   index="sms"
		      			   title="Message"
		      			   width="308"
		      			   align="left"
		      			   search="true"
		 				   />	
		 				   
		 	<sjg:gridColumn 
		      			   name="status"
		      			   index="status"
		      			   title="Status"
		      			   width="60"
		      			   align="center"
		      			   search="true"
		 				   />				   
		 				   
		  <sjg:gridColumn 
		      			   name="module"
		      			   index="module"
		      			   title="Module"
		      			   width="120"
		      			   align="center"
		      			   search="true"
		 				   />			
		 				   
		 	<sjg:gridColumn 
		      			   name="insertdate_time"
		      			   index="insertdate_time"
		      			   title="Insert Time"
		      			   width="120"
		      			   align="center"
		      			   search="false"
		 				   />			
		 				   
		 	<sjg:gridColumn 
		      			   name="updatedate_time"
		      			   index="updatedate_time"
		      			   title="Update Time"
		      			   width="120"
		      			   align="center"
		      			   search="false"
		 				   />			
</sjg:grid>
</body>
</html>