<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
<link type="text/css" href="<s:url value="/css/style.css"/>" rel="stylesheet" />
</head>
<body>
    <s:select 
          id="subdeptname%{destination}" 
          name="subdept" 
          list="subDeptList"
          headerKey="-1"
          headerValue="Select Sub-Department"
          cssClass="select"
		  cssStyle="width:82%"
          onchange="getEmployee(this.value,'empName%{destination}');"
          />
    </body>
</html>