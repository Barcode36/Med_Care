<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
<link type="text/css" href="<s:url value="/css/style.css"/>" rel="stylesheet" />
</head>
<body>
    			<s:select 
          			id="emp_id%{destination}" 
          			name="empName" 
          			list="employeeList"
          			headerKey="-1"
          			headerValue="Select Employee"
         			cssClass			=		"select"
					cssStyle			=		"width:82%"
          		/>
    </body>
</html>