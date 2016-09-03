<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>
<body>
<s:url id="emailhref_URL" action="viewEmailData" escapeAmp="false">
<s:param name="insertDate" value="%{insertDate}"></s:param>
  <s:param name="updateDateForMail" value="%{updateDateForMail}"></s:param>
    <s:param name="moduleName" value="%{moduleName}"></s:param>
</s:url>

<sjg:grid 
		  id="emailgrid"
          href="%{emailhref_URL}"
          gridModel="emailList"
          groupField="['module']"
    	  groupColumnShow="[false]"
    	  groupCollapse="true"
    	  groupText="['<b>Via {0} - {1} Mail</b>']"
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
		      			   width="150"
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
		      			   name="emailId"
		      			   index="emailId"
		      			   title="Email Id"
		      			   width="188"
		      			   align="center"
		      			   editable="false"
		 				   />
		 				   
		 <sjg:gridColumn 
		      			   name="email"
		      			   index="email"
		      			   title="Mail Text"
		      			   width="150"
		      			   align="center"
		      			   formatter="emailAction"
		 				   />	
		 				   
		 	<sjg:gridColumn 
		      			   name="status"
		      			   index="status"
		      			   title="Status"
		      			   width="80"
		      			   align="center"
		      			   search="true"
		 				   />				   
		 				   			   
		 				   
		  <sjg:gridColumn 
		      			   name="module"
		      			   index="module"
		      			   title="Module"
		      			   width="110"
		      			   align="center"
		      			   editable="false"
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