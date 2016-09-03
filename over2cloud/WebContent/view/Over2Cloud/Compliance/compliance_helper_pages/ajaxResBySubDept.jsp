<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
<link type="text/css" href="<s:url value="/css/style.css"/>" rel="stylesheet" />
</head>
<body>
    <s:select 
          id="bysubdept" 
          name="bysubdept" 
          list="subDeptList"
          headerKey="-1"
          headerValue="Select Sub-Department"
          cssClass="form_menu_inputselect"
          />
    </body>
</html>