<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>
<body>
  
<s:url id="report_URL" action="loginReport" escapeAmp="false">
 <s:param name="searchField" value="%{searchField}"></s:param>
  <s:param name="searchString" value="%{searchString}"></s:param>
   
</s:url>
        <sjg:grid 
		  id="gridreport"
          href="%{report_URL}"
          gridModel="reportData"
          groupField="['status']"
    	  groupColumnShow="[true]"
    	  groupCollapse="true"
    	  groupText="['<b>{0}: {1}User(s) </b>']"
       
          navigator="true"
          navigatorAdd="false"
          navigatorView="true"
          navigatorDelete="false"
          navigatorEdit="false"
          navigatorSearch="true"
          rowList="500,750,800,1200"
          rownumbers="-1"
          viewrecords="true"       
          pager="true"
          pagerButtons="true"
          pagerInput="true"   
          multiselect="true"  
          loadingText="Requesting Data..."  
          rowNum="500"
          autowidth="true"
        
          shrinkToFit="true"
          onSelectRowTopics="rowselect"
          >
		           <sjg:gridColumn 
		      						name="id"
		      						index="id"
		      						title="ID"
		      						width="100"
		      						align="center"
		      						editable="true"
		      						hidden="true"
		      						key="true"
		 							/>
		 							
		 							<sjg:gridColumn 
		      						name="empName"
		      						index="empName"
		      						title="Employee"
		      						width="150"
		      						align="center"
		      						editable="false"
		      						hidden="false"
		 							/>				
		 				
		 				<sjg:gridColumn 
		      						name="empId"
		      						index="empId"
		      						title="Contact"
		      						width="150"
		      						align="center"
		      						editable="false"
		      						hidden="false"
		 							/>		
		 							
		 			<sjg:gridColumn 
		      						name="dept"
		      						index="dept"
		      						title="Department"
		      						width="100"
		      						align="center"
		      						editable="false"
		      						hidden="false"
		 							/>
		 							
		 							<sjg:gridColumn 
		      						name="status"
		      						index="status"
		      						title="Status"
		      						width="100"
		      						align="center"
		      						editable="false"
		      						hidden="false"
		 							/>
		 							
		 			 			
		 		  
		 			
		 																		
	</sjg:grid>
</body>
</html>