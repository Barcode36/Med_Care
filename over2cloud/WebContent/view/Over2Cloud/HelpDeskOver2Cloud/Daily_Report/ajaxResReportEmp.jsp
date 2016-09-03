<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
<link type="text/css" href="<s:url value="/css/style.css"/>" rel="stylesheet" />
</head>
<body>
    <s:select 
          id="empId%{destination}" 
          name="empId" 
          list="empList"
          headerKey="-1"
          headerValue="Select Employee"
          cssClass="select"
	      cssStyle="width:82%"
          />
    </body>
</html>