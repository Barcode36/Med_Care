<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
    <link type="text/css" href="<s:url value="/css/style.css"/>" rel="stylesheet" />
</head>
<body>
    <s:select 
          id="feedType%{destination}" 
          name="feedTypeId" 
          list="feedTypelist"
          headerKey="-1"
          headerValue="Select Feedback Type"
          cssClass="form_menu_inputselect"
          onchange="getFeedCategory(this.value,'categoryDiv%{destination}');"
          />
    </body>
</html>