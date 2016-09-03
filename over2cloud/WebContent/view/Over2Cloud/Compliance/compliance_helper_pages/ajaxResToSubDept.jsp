<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
    <link type="text/css" href="<s:url value="/css/style.css"/>" rel="stylesheet" />
</head>
<body>
    <s:select 
          id="tosubdept" 
          name="tosubdept" 
          list="subDeptList"
          headerKey="-1"
          headerValue="Select Sub-Department"
          cssClass="form_menu_inputselect"
          onchange="getFeedType('tosubdept','feedType1');"
          />
    </body>
</html>