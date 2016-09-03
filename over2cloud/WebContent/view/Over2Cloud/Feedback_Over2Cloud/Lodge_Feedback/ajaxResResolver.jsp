<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
    <link type="text/css" href="<s:url value="/css/style.css"/>" rel="stylesheet" />
</head>
<body>
    <s:select 
          id="resolver" 
          name="resolver" 
          list="resolverList"
          headerKey="-1"
          headerValue="Select Resolver"
          cssClass="select"
          cssStyle="width:82%"
          />
    </body>
</html>